package com.paylease.app.qa.functional.tests.backend;

import static com.paylease.app.qa.framework.AppConstant.PATH_TO_LOG_FILES;
import static com.paylease.app.qa.framework.pages.BrickApiStubPage.ALT_BRICK_FTP_URI;

import com.paylease.app.qa.framework.AppConstant;
import com.paylease.app.qa.framework.BrickFtpUtil;
import com.paylease.app.qa.framework.DataHelper;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ResourceFactory;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.BrickApiStubPage;
import com.paylease.app.qa.framework.pages.PageBase;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.utility.emailtool.EmailProcessor;
import com.paylease.app.qa.framework.utility.sshtool.SshUtil;
import java.io.File;
import java.io.FileInputStream;
import javax.mail.Message;
import javax.mail.MessagingException;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class FtpExportTest extends ScriptBase {

  public static final String REGION = "backend";
  public static final String FEATURE = "ftpExport";

  private static final int FILE_DOWNLOAD_RETRY_TIMEOUT = 5000;
  private static final int FILE_DOWNLOAD_RETRY_COUNT = 12;

  private static final int EMAIL_RETRY_TIMEOUT = 10000;
  private static final int EMAIL_RETRY_COUNT = 4;
  private static final String PL_NOTIFY_CRITICAL_LEVEL = "500";
  private static final String PL_NOTIFY_CRITICAL_KEY = "BrickFtpClient";
  private static final String PL_NOTIFY_CRITICAL_SUBJECT =
      "PLNotify level: " + PL_NOTIFY_CRITICAL_LEVEL + " key: " + PL_NOTIFY_CRITICAL_KEY;

  private static final String PATH_TO_ENV_FILES =
      ResourceFactory.getInstance().getProperty(ResourceFactory.WEB_APP_ROOT_DIR_KEY) + "dotenv"
          + File.separator;
  private static final String ENV_FILE_PREFIX = "0_autoTest_";

  private SshUtil sshUtil;
  private int pid = 0;
  private File file;

  @BeforeClass(alwaysRun = true)
  public void setUpSshUtil() {
    sshUtil = new SshUtil();
  }

  @AfterMethod(alwaysRun = true)
  public void killProcess() {
    if (pid > 0) {
      sshUtil.killProcess(pid);
    }
  }

  @AfterMethod(alwaysRun = true)
  public void removeEnvFiles() {
    sshUtil.runCommand("rm " + PATH_TO_ENV_FILES + ENV_FILE_PREFIX + "*.env");
  }

  @Test(groups = {"manual", "brickftp", "queue"})
  public void endToEnd() throws Exception {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc3592");
    testSetupPage.open();

    final String ftpUsername = testSetupPage.getString("ftpUsername");
    final String ftpPassword = testSetupPage.getString("ftpPassword");
    final String pmFtpDir = testSetupPage.getString("pmFtpDir");
    final String pmId = testSetupPage.getString("pmId");

    pid = sshUtil.runQueueWorker("ftp_export_worker");

    String ftpFileName = sshUtil.runAutoExportScript(Integer.parseInt(pmId));
    Assert.assertNotNull(ftpFileName, "File name should not be null");

    assertFileDownloaded(ftpFileName, "endToEnd", ftpUsername, ftpPassword, pmFtpDir);
  }

  @Test(groups = {"manual", "queue"})
  public void endToEndTimeout() {
    String testCaseId = "tc4175";
    String envFile = PATH_TO_ENV_FILES + ENV_FILE_PREFIX + testCaseId + ".env";
    String command = "echo BRICK_FTP_URI=" + PageBase.BASE_URL + " > " + envFile;
    command += " && echo DEV_SYSTEM_DEVELOPER=" + AppConstant.QA_MAILBOX + " >> " + envFile;

    sshUtil.runCommand(command);

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCaseId);
    testSetupPage.open();

    EmailProcessor emailProcessor = new EmailProcessor();

    final String pmId = testSetupPage.getString("pmId");

    pid = sshUtil.runQueueWorker("ftp_export_worker");

    String ftpFileName = sshUtil.runAutoExportScript(Integer.parseInt(pmId));
    Assert.assertNotNull(ftpFileName, "File name should not be null");

    Message message = waitForEmailToBePresent(ftpFileName);
    Assert.assertNotNull(message,
        "Queue consumer sends critical PLNotify email after max failed listeners attempts");

    String logMessage = "WARNING: BrickFtp beginUpload(.*/" + ftpFileName
        + ") failed due to unrecognizable response.";

    int logMessageCount = sshUtil
        .getStringMatchCountFromFile(logMessage, PATH_TO_LOG_FILES + "*_ftp_export.log");
    try {
      emailProcessor.deleteMessage(message);
    } catch (MessagingException e) {
      Logger.error(e.toString());
    }
    Assert.assertEquals(logMessageCount, 5, "Expect 5 retries logged");
  }

  @Test(groups = {"manual", "queue"})
  public void endToEndSomeFailures() {
    DataHelper dataHelper = new DataHelper();

    String testCaseId = "tc4176";
    String envFile = PATH_TO_ENV_FILES + ENV_FILE_PREFIX + testCaseId + ".env";
    String command = "echo BRICK_FTP_URI=" + ALT_BRICK_FTP_URI + " > " + envFile;
    int failureCount = 3;
    String testId = testCaseId + "_" + dataHelper.generateAlphanumericString(16);

    sshUtil.runCommand(command);

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCaseId);
    testSetupPage.open();

    final String pmId = testSetupPage.getString("pmId");

    BrickApiStubPage brickApiStubPage = new BrickApiStubPage();
    brickApiStubPage.init(failureCount, testId);

    pid = sshUtil.runQueueWorker("ftp_export_worker");

    String ftpFileName = sshUtil.runAutoExportScript(Integer.parseInt(pmId));
    Assert.assertNotNull(ftpFileName, "File name should not be null");

    sshUtil.waitForLogMessage(testId, PATH_TO_LOG_FILES + "*_ftp_export.log");

    String logMessage = "WARNING: BrickFtp beginUpload(.*/" + ftpFileName
        + ") failed due to unrecognizable response.";

    int logMessageCount = sshUtil
        .getStringMatchCountFromFile(logMessage, PATH_TO_LOG_FILES + "*_ftp_export.log");
    Assert
        .assertEquals(logMessageCount, failureCount, "Expect " + failureCount + " retries logged");

    Assert.assertEquals(brickApiStubPage.getRequestCount(), failureCount + 1,
        "Api request retried until valid");
  }

  private void assertFileDownloaded(
      String ftpFileName,
      String testMethod,
      String ftpUsername,
      String ftpPassword,
      String pmFtpDir
  ) throws Exception {
    String src = "export" + File.separator + ftpFileName;
    String dest =
        setUpDownloadPath(this.getClass().getName(), testMethod) + File.separator + ftpFileName;

    boolean fileDownloaded = didFileDownloadFromFtp(ftpUsername, ftpPassword, src, dest);
    Assert.assertTrue(fileDownloaded, "File not present on ftp server");

    FileInputStream fis = new FileInputStream(file);
    String actualMd5 = org.apache.commons.codec.digest.DigestUtils.md5Hex(fis);
    fis.close();

    String remoteFilePath =
        ResourceFactory.getInstance().getProperty(ResourceFactory.WEB_APP_ROOT_DIR_KEY)
            + "ftp" + File.separator + pmFtpDir + File.separator
            + "export" + File.separator + file.getName();
    String originalMd5 = sshUtil.getFileMd5(remoteFilePath);

    Assert.assertEquals(actualMd5, originalMd5, "Uploaded file matches downloaded file");
  }

  /**
   * Determine if given file is present on remote host.
   *
   * @param username The username to the ftp server
   * @param password The password to the ftp server
   * @param src Source file path to download from
   * @param dest Destination file path to download file to
   * @return True if file is downloaded
   */
  private boolean didFileDownloadFromFtp(String username, String password, String src,
      String dest) {
    BrickFtpUtil ftpUtil = new BrickFtpUtil(username, password);

    for (int i = 0; i < FILE_DOWNLOAD_RETRY_COUNT; i++) {
      Logger.trace("Attempt #" + (i + 1) + ": Download file on ftp.");

      if (ftpUtil.downloadFile(src, dest)) {
        file = new File(dest);

        return true;
      }

      try {
        Thread.sleep(FILE_DOWNLOAD_RETRY_TIMEOUT);
      } catch (InterruptedException e) {
        Logger.debug("Sleep problem: " + e.getMessage());
      }
    }

    return false;
  }

  /**
   * Wait for email message containing filename to be present in INBOX.
   *
   * @param filename filename to find in message body
   * @return Message that was found
   */
  private Message waitForEmailToBePresent(String filename) {
    EmailProcessor emailProcessor = new EmailProcessor();
    emailProcessor.connect();

    for (int i = 0; i < EMAIL_RETRY_COUNT; i++) {
      Logger.trace("Attempt #" + (i + 1) + ": Looking for message.");

      Message[] messages = emailProcessor.getMessagesWhereBodyContains(filename);
      if (messages != null) {
        Logger.trace("Found " + messages.length + " number of messages.");

        for (Message message : messages) {
          if (emailProcessor.getSubject(message).equals(PL_NOTIFY_CRITICAL_SUBJECT)) {

            return message;
          }
        }
      }


      try {
        Thread.sleep(EMAIL_RETRY_TIMEOUT);
      } catch (InterruptedException e) {
        Logger.debug("Sleep problem: " + e.getMessage());
      }
    }

    return null;
  }
}
