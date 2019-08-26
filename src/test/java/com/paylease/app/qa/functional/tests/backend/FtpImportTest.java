package com.paylease.app.qa.functional.tests.backend;

import com.github.javafaker.Faker;
import com.paylease.app.qa.framework.BrickFtpUtil;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ResourceFactory;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.automatedhelper.BrickFtpWebhookRequestHelperPage;
import com.paylease.app.qa.framework.pages.automatedhelper.BrickFtpWebhookRequestProcessingPage;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.backend.BrickFtpWebhookRequestPage;
import com.paylease.app.qa.framework.utility.sshtool.SshUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class FtpImportTest extends ScriptBase {

  public static final String REGION = "backend";
  public static final String FEATURE = "ftpImport";

  private static final int WEBHOOK_RETRY_TIMEOUT = 10000;
  private static final int WEBHOOK_RETRY_COUNT = 6;

  private static final int FILE_EXISTS_RETRY_TIMEOUT = 5000;
  private static final int FILE_EXISTS_RETRY_COUNT = 12;

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
  public void deleteFile() {
    if (file != null) {
      file.delete();
    }
  }

  @Test
  public void successWithFtp() {
    Faker faker = new Faker();

    String path = faker.bothify("/?????/??????/?#?#?#?#?.txt");
    String at = "2018-07-19T14:15:31-04:00";
    String username = faker.bothify("?#?#?#?#?#?#?####???");

    testWebhookRequestSavedToDb("create", "ftp", path, "", at, username, "file");
  }

  @Test
  public void successWithSftp() {
    Faker faker = new Faker();

    String path = faker.bothify("/?????/??????/?#?#?#?#?.txt");
    String at = "2018-07-19T14:15:31-04:00";
    String username = faker.bothify("?#?#?#?#?#?#?####???");

    testWebhookRequestSavedToDb("create", "sftp", path, "", at, username, "file");
  }

  @Test
  public void successWithWeb() {
    Faker faker = new Faker();

    String path = faker.bothify("/?????/??????/?#?#?#?#?.txt");
    String at = "2018-07-19T14:15:31-04:00";
    String username = faker.bothify("?#?#?#?#?#?#?####???");

    testWebhookRequestSavedToDb("create", "web", path, "", at, username, "file");
  }

  @Test
  public void failureWithAction() {
    Faker faker = new Faker();

    String path = faker.bothify("/?????/??????/?#?#?#?#?.txt");
    String at = "2018-07-19T14:15:31-04:00";
    String username = faker.bothify("?#?#?#?#?#?#?####???");

    testWebhookRequestNotSavedToDb("crea", "ftp", path, "", at, username, "file");
  }

  @Test
  public void failureWithInterface() {
    Faker faker = new Faker();

    String path = faker.bothify("/?????/??????/?#?#?#?#?.txt");
    String at = "2018-07-19T14:15:31-04:00";
    String username = faker.bothify("?#?#?#?#?#?#?####???");

    testWebhookRequestNotSavedToDb("create", "robot", path, "", at, username, "file");
  }

  @Test
  public void failureWithType() {
    Faker faker = new Faker();

    String path = faker.bothify("/?????/??????/?#?#?#?#?.txt");
    String at = "2018-07-19T14:15:31-04:00";
    String username = faker.bothify("?#?#?#?#?#?#?####???");

    testWebhookRequestNotSavedToDb("create", "ftp", path, "", at, username, "directory");
  }

  @Test(groups = {"manual", "brickftp", "queue"})
  public void endToEnd() throws IOException {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc3282");
    testSetupPage.open();

    final String ftpUsername = testSetupPage.getString("ftpUsername");
    final String ftpPassword = testSetupPage.getString("ftpPassword");
    final String ftpRootDir = testSetupPage.getString("ftpRootDir");
    final String pmFtpDir = testSetupPage.getString("pmFtpDir");

    pid = sshUtil.runQueueWorker("ftp_import_worker");

    BrickFtpUtil ftpUtil = new BrickFtpUtil(ftpUsername, ftpPassword);

    file = createUploadTestFile("This is a test file.");
    FileInputStream fis = new FileInputStream(file);
    String originalMd5 = org.apache.commons.codec.digest.DigestUtils.md5Hex(fis);
    fis.close();

    ftpUtil.uploadFile(file);

    String remoteFilePath =
        ResourceFactory.getInstance().getProperty(ResourceFactory.WEB_APP_ROOT_DIR_KEY)
            + "ftp" + File.separator + pmFtpDir + File.separator + file.getName();

    String path = ftpRootDir + File.separator + file.getName();

    if (!ResourceFactory.getInstance().getFlag(ResourceFactory.APP_URL_ACCESS_PUBLIC_KEY)) {
      String action = "create";
      String iface = "sftp";
      String destination = path;
      String at = "2018-07-19T14:15:31-04:00";
      String username = ftpUsername;
      String type = "file";

      BrickFtpWebhookRequestPage requestPage = new BrickFtpWebhookRequestPage(
          action, iface, path, destination, at, username, type
      );

      requestPage.open();
    }

    Assert.assertTrue(isWebhookInDb(ftpUsername, path), "The webhook was stored in database");

    sshUtil.runBatchScript("process_brick_ftp_webhook_requests");

    Assert.assertTrue(isFilePresentOnRemote(remoteFilePath), "File is on the server");

    String actualMd5 = sshUtil.getFileMd5(remoteFilePath);
    Assert.assertEquals(actualMd5, originalMd5, "Downloaded file matches uploaded file");
  }

  /**
   * testWebhookRequestSavedToDb.
   *
   * @param action Action
   * @param iface Interface
   * @param path Path
   * @param destination Destination
   * @param at At
   * @param username Username
   * @param type Type
   */
  protected void testWebhookRequestSavedToDb(String action, String iface, String path,
      String destination, String at, String username, String type) {
    BrickFtpWebhookRequestProcessingPage processingPage = submitWebhookRequestAndSearch(action,
        iface, path, destination, at, username, type);

    Assert.assertEquals(processingPage.getAction(), action, "Action matches request");
    Assert.assertEquals(processingPage.getInterface(), iface, "Interface matches request");
    Assert.assertEquals(processingPage.getPath(), path, "Path matches request");
    Assert
        .assertEquals(processingPage.getDestination(), destination, "Destination matches request");
    Assert.assertEquals(processingPage.getAt(), at, "At matches request");
    Assert.assertEquals(processingPage.getUsername(), username, "Username matches request");
    Assert.assertEquals(processingPage.getType(), type, "Type matches request");
  }

  /**
   * testWebhookRequestNotSavedToDb.
   *
   * @param action Action
   * @param iface Interface
   * @param path Path
   * @param destination Destination
   * @param at At
   * @param username Username
   * @param type Type
   */
  protected void testWebhookRequestNotSavedToDb(String action, String iface, String path,
      String destination, String at, String username, String type) {
    BrickFtpWebhookRequestProcessingPage processingPage = submitWebhookRequestAndSearch(action,
        iface, path, destination, at, username, type);

    Assert.assertEquals(processingPage.getId(), "", "Request not saved to DB");
  }

  /**
   * submitWebhookRequestAndSearch.
   *
   * @param action Action
   * @param iface Interface
   * @param path Path
   * @param destination Destination
   * @param at At
   * @param username Username
   * @param type Type
   */
  private BrickFtpWebhookRequestProcessingPage submitWebhookRequestAndSearch(String action,
      String iface, String path,
      String destination, String at, String username, String type) {
    BrickFtpWebhookRequestPage brickFtpWebhookRequestPage = new BrickFtpWebhookRequestPage(action,
        iface, path, destination, at, username, type);

    brickFtpWebhookRequestPage.open();

    BrickFtpWebhookRequestHelperPage brickFtpWebhookRequestHelperPage = new BrickFtpWebhookRequestHelperPage();
    brickFtpWebhookRequestHelperPage.open();

    return brickFtpWebhookRequestHelperPage.getDataForRequest(username, path);
  }

  /**
   * Determine if webhook data is in the database.
   *
   * @param username the username to look for
   * @param path the path to look for
   * @return true if data exist in the database
   */
  private boolean isWebhookInDb(String username, String path) {
    for (int i = 0; i < WEBHOOK_RETRY_COUNT; i++) {
      Logger.trace("Attempt #" + (i + 1) + ": Looking for webhook in database.");

      BrickFtpWebhookRequestHelperPage helperPage = new BrickFtpWebhookRequestHelperPage();
      helperPage.open();

      BrickFtpWebhookRequestProcessingPage processingPage = helperPage
          .getDataForRequest(username, path);

      if (null != processingPage.getUsername()) {
        return true;
      }

      try {
        Thread.sleep(WEBHOOK_RETRY_TIMEOUT);
      } catch (InterruptedException e) {
        Logger.debug("Sleep problem: " + e.getMessage());
      }
    }

    return false;
  }

  /**
   * Determine if given file is present on remote host.
   *
   * @param filePath File path to find
   * @return True if file is found
   */
  private boolean isFilePresentOnRemote(String filePath) {
    for (int i = 0; i < FILE_EXISTS_RETRY_COUNT; i++) {
      Logger.trace("Attempt #" + (i + 1) + ": Looking for a file on remote.");

      if (sshUtil.fileExists(filePath)) {
        return true;
      }

      try {
        Thread.sleep(FILE_EXISTS_RETRY_TIMEOUT);
      } catch (InterruptedException e) {
        Logger.debug("Sleep problem: " + e.getMessage());
      }
    }

    return false;
  }
}
