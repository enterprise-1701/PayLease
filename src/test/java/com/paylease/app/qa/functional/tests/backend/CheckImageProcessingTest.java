package com.paylease.app.qa.functional.tests.backend;

import static com.paylease.app.qa.framework.AppConstant.PATH_TO_LOG_FILES;

import com.paylease.app.qa.framework.AppConstant;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ResourceFactory;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.utility.emailtool.EmailProcessor;
import com.paylease.app.qa.framework.utility.emailtool.EmailProcessor.PlNotifyLevel;
import com.paylease.app.qa.framework.utility.listeners.Retry;
import com.paylease.app.qa.framework.utility.sshtool.SshUtil;
import java.io.File;
import java.util.HashMap;
import javax.mail.Message;
import javax.mail.MessagingException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CheckImageProcessingTest extends ScriptBase {

  public static final String REGION = "backend";
  public static final String FEATURE = "checkImageProcessing";

  private static final String QUEUE_NAME = "check_image_processing";

  @Test(groups = {"manual", "queue"})
  public void jobMissingRequiredData() throws Exception {
    Logger.info("Verify log message indicates missing required data (file_path).");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc4219");
    testSetupPage.open();

    final String jobData = testSetupPage.getString("jobData");

    runQueueWorker();

    SshUtil sshUtil = new SshUtil();

    String output = sshUtil.runCommand(
        "grep -A2 \"Missing 'file_path' in queue data:\" " + PATH_TO_LOG_FILES
            + "*_check_image_processing.log | grep -c \"" + jobData + "\"");
    int count = sshUtil.getCountFromOutput(output);

    Assert.assertEquals(count, 1, "There should be a log entry indicating missing required data.");
  }

  @Test(groups = {"manual", "queue"})
  public void processingJsonFile() throws Exception {
    Logger.info("Validate we have a log message that the batch file is processing.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc4221");
    testSetupPage.open();

    final String filePath = testSetupPage.getString("filePath");

    runQueueWorker();

    String message = "INFO: begin processing: " + filePath;

    Assert.assertTrue(
        doesLogContainMessage(message),
        "There should be a message that the JSON file is about to be processed."
    );
  }

  @Test(groups = {"manual", "queue"})
  public void jsonFileMissing() throws Exception {
    Logger.info("Validate we have a log message that json file not found.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc4221");
    testSetupPage.open();

    final String filePath = testSetupPage.getString("filePath");

    runQueueWorker();

    String message = "ERROR: could not find " + filePath + " for processing.";

    Assert.assertTrue(
        doesLogContainMessage(message),
        "There should be a message that the JSON file was missing."
    );
  }

  @Test(groups = {"manual", "queue"})
  public void jsonFileInvalid() throws Exception {
    Logger.info("Validate we have a log message that json file is invalid.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc4222");
    testSetupPage.open();

    final String filePath = testSetupPage.getString("filePath");

    runQueueWorker();

    String message = "ERROR: unable to read " + filePath + ".";

    Assert.assertTrue(
        doesLogContainMessage(message),
        "There should be a message that the JSON file could not be read."
    );
  }

  @Test(groups = {"manual", "queue"})
  public void jsonFileIsNotX937() throws Exception {
    Logger.info("Validate we have a log message that json file does not conform to X937.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc4223");
    testSetupPage.open();

    final String filePath = testSetupPage.getString("filePath");

    runQueueWorker();

    String message = "ERROR: " + filePath + " does not conform to X937 format.";

    Assert.assertTrue(
        doesLogContainMessage(message),
        "There should be a message that the JSON file does not conform to X937 format."
    );
  }

  @Test(groups = {"manual", "queue"})
  public void jsonFileHasCheckWithoutSequenceNumber() throws Exception {
    Logger.info("Validate we have a log message that check contains no sequence number.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc4259");
    testSetupPage.open();

    final String filePath = testSetupPage.getString("filePath");

    runQueueWorker();

    String message = "ERROR: " + filePath + " found check with no sequence number.";

    Assert.assertTrue(
        doesLogContainMessage(message),
        "There should be a message that the specific check does not have a sequence number."
    );
  }

  @DataProvider(parallel = true)
  public Object[][] provideImageTypesNoImage() {

    return new Object[][]{
        //TestCaseId, ImageType
        {"tc4226", "front"},
        {"tc4226", "back"},
    };
  }

  @Test(
      groups = {"manual", "queue"},
      dataProvider = "provideImageTypesNoImage",
      retryAnalyzer = Retry.class
  )
  public void jsonFileHasCheckWithoutImage(String testCaseId, String imageType) throws Exception {
    Logger.info("Validate we have a log message that json file has a check with no " + imageType
        + " image.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCaseId);
    testSetupPage.open();

    final String sequenceNumber = testSetupPage.getString("sequenceNumber");

    runQueueWorker();

    String message = "WARNING: check with sequence number: '" + sequenceNumber
        + "' does not have a " + imageType + " image.";

    Assert.assertTrue(
        doesLogContainMessage(message),
        "There should be a message that the specific check does not have a " + imageType + " image."
    );
  }

  @DataProvider(parallel = true)
  public Object[][] provideImageTypesWithImage() {

    return new Object[][]{
        //TestCaseId, ImageType
        {"tc4228", "front"},
        {"tc4228", "back"},
    };
  }

  @Test(
      groups = {"manual", "queue"},
      dataProvider = "provideImageTypesWithImage",
      retryAnalyzer = Retry.class
  )
  public void jsonFileHasCheckCannotSaveImage(String testCaseId, String imageType)
      throws Exception {
    Logger.info(
        "Validate we have a log message that the " + imageType
            + " check image cannot be saved as a TIFF.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCaseId);
    testSetupPage.open();

    final String sequenceNumber = testSetupPage.getString("sequenceNumber");
    final String checkSaveLocation = testSetupPage.getString("checkSaveLocation");
    final String imageLocationEnvVarName = testSetupPage.getString("imageLocationEnvVarName");

    HashMap<String, String> envVars = new HashMap<>();
    envVars.put(EMAIL_ENV_VAR_NAME, AppConstant.QA_MAILBOX);
    envVars.put(imageLocationEnvVarName, "/foo");

    runQueueWorker(envVars);

    String message = "check with sequence number: '" + sequenceNumber
        + "' could not save " + imageType + " image.";

    Assert.assertTrue(
        doesLogContainMessage("ERROR: " + message),
        "There should be a message that the specific check could not save " + imageType + " image."
    );

    assertPlNotifyEmail(message, PlNotifyLevel.CRITICAL);

    SshUtil sshUtil = new SshUtil();
    String filename = sequenceNumber + "_" + imageType + ".tiff";
    String location = checkSaveLocation + File.separator + filename;

    Assert.assertFalse(sshUtil.fileExists(location), "File should not have been created.");
  }

  @Test(
      groups = {"manual", "queue"},
      dataProvider = "provideImageTypesWithImage",
      retryAnalyzer = Retry.class
  )
  public void checkImageSaved(String testCaseId, String imageType) throws Exception {
    Logger.info(
        "Validate we have saved the " + imageType + " check image as a TIFF.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCaseId);
    testSetupPage.open();

    final String sequenceNumber = testSetupPage.getString("sequenceNumber");
    final String checkSaveLocation = testSetupPage.getString("checkSaveLocation");
    final String imageMd5 = testSetupPage.getString(imageType + "ImageMd5");

    runQueueWorker();

    String filename = sequenceNumber + "_" + imageType + ".tiff";
    String location = checkSaveLocation + File.separator + filename;

    Assert.assertTrue(
        doesLogContainMessage("INFO: check with sequence number: '" + sequenceNumber
            + "' " + imageType + " image saved to: '" + location + "'."),
        "There should be a message that the " + imageType + " check was saved."
    );

    assertFileMd5(imageMd5, location);
  }

  @Test(
      groups = {"manual", "queue"},
      dataProvider = "provideImageTypesWithImage",
      retryAnalyzer = Retry.class
  )
  public void checkImageNotConverted(String testCaseId, String imageType) throws Exception {
    Logger.info(
        "Validate we have not converted the " + imageType + " TIFF image to PNG.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCaseId);
    testSetupPage.open();

    final String sequenceNumber = testSetupPage.getString("sequenceNumber");
    final String checkSaveLocation = testSetupPage.getString("checkSaveLocation");

    HashMap<String, String> envVars = new HashMap<>();
    envVars.put(EMAIL_ENV_VAR_NAME, AppConstant.QA_MAILBOX);

    runQueueWorker(envVars);

    String message = "check with sequence number: '" + sequenceNumber + "' " + imageType
        + " image could not be converted to PNG.";

    Assert.assertTrue(
        doesLogContainMessage("ERROR: " + message),
        "There should be a message that the " + imageType + " image was not converted."
    );

    assertPlNotifyEmail(message, PlNotifyLevel.LOW);

    SshUtil sshUtil = new SshUtil();
    String filename = sequenceNumber + "_" + imageType + ".png";
    String location = checkSaveLocation + File.separator + filename;

    Assert.assertFalse(sshUtil.fileExists(location), "File should not have been created.");
  }

  @DataProvider(parallel = true)
  public Object[][] provideImageTypesWithValidImage() {

    return new Object[][]{
        //TestCaseId, ImageType
        {"tc4260", "front"},
        {"tc4260", "back"},
    };
  }

  @Test(
      groups = {"manual", "queue"},
      dataProvider = "provideImageTypesWithValidImage",
      retryAnalyzer = Retry.class
  )
  public void checkImageConverted(String testCaseId, String imageType) throws Exception {
    Logger.info(
        "Validate we have converted the " + imageType + " check image to a PNG.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCaseId);
    testSetupPage.open();

    final String sequenceNumber = testSetupPage.getString("sequenceNumber");

    runQueueWorker();

    Assert.assertTrue(
        doesLogContainMessage("INFO: check with sequence number: '" + sequenceNumber
            + "' " + imageType + " image converted to PNG."),
        "There should be a message that the " + imageType + " image was converted."
    );
  }

  @Test(
      groups = {"manual", "queue"},
      dataProvider = "provideImageTypesWithValidImage",
      retryAnalyzer = Retry.class
  )
  public void checkImageNotUploaded(String testCaseId, String imageType) throws Exception {
    Logger.info("Validate we have a log message that the " + imageType
        + " check image was unable to upload.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCaseId);
    testSetupPage.open();

    final String sequenceNumber = testSetupPage.getString("sequenceNumber");
    final String checkSaveLocation = testSetupPage.getString("checkSaveLocation");
    final String s3BucketEnvVarName = testSetupPage.getString("s3BucketEnvVarName");

    HashMap<String, String> envVars = new HashMap<>();
    envVars.put(EMAIL_ENV_VAR_NAME, AppConstant.QA_MAILBOX);
    envVars.put(s3BucketEnvVarName, "foo");

    runQueueWorker(envVars);

    String message = "check with sequence number: '" + sequenceNumber + "' " + imageType
        + " image could not be uploaded.";

    Assert.assertTrue(
        doesLogContainMessage("ERROR: " + message),
        "There should be a message that the " + imageType + " image was not uploaded."
    );

    assertPlNotifyEmail(message, PlNotifyLevel.LOW);

    SshUtil sshUtil = new SshUtil();

    Assert.assertTrue(
        sshUtil.fileExists(
            checkSaveLocation + File.separator + sequenceNumber + "_" + imageType + ".png"
        ),
        "File should be present on failed upload."
    );
  }

  @Test(
      groups = {"aws", "manual", "queue"},
      dataProvider = "provideImageTypesWithValidImage",
      retryAnalyzer = Retry.class
  )
  public void checkImageUploaded(String testCaseId, String imageType) throws Exception {
    Logger.info("Validate we have a log message that the " + imageType
        + " check image was uploaded.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCaseId);
    testSetupPage.open();

    final String sequenceNumber = testSetupPage.getString("sequenceNumber");
    final String checkSaveLocation = testSetupPage.getString("checkSaveLocation");

    runQueueWorker();

    Assert.assertTrue(
        doesLogContainMessage("INFO: check with sequence number: '" + sequenceNumber
            + "' " + imageType + " image was uploaded."),
        "There should be a message that the " + imageType + " image was uploaded."
    );

    SshUtil sshUtil = new SshUtil();
    Assert.assertFalse(
        sshUtil.fileExists(
            checkSaveLocation + File.separator + sequenceNumber + "_" + imageType + ".png"
        ),
        "File should be present on failed upload."
    );
  }

  @Test(groups = {"manual", "queue"})
  public void endProcessingJsonFile() throws Exception {
    Logger.info("Validate we have a log message that each batch file is processing.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc4221");
    testSetupPage.open();

    final String filePath = testSetupPage.getString("filePath");

    runQueueWorker();

    String message = "INFO: end processing: " + filePath;

    Assert.assertTrue(
        doesLogContainMessage(message),
        "There should be a message that the JSON file is about to be processed."
    );
  }

  private void runQueueWorker() throws Exception {
    runQueueWorker(new HashMap<>());
  }

  private void runQueueWorker(HashMap<String, String> envVars) throws Exception {
    SshUtil sshUtil = new SshUtil();
    sshUtil.setEnvVars(envVars);

    sshUtil.runQueueWorkerUntilComplete(QUEUE_NAME);
  }

  private boolean doesLogContainMessage(String message) {
    String path = ResourceFactory.getInstance().getProperty(ResourceFactory.WEB_APP_ROOT_DIR_KEY);
    path += "../logs/application/";

    SshUtil sshUtil = new SshUtil();
    int logMessageCount = sshUtil.getStringMatchCountFromFile(
        message,
        path + "*_check_image_processing.log"
    );

    return logMessageCount > 0;
  }

  private void assertPlNotifyEmail(String message, PlNotifyLevel level) {
    EmailProcessor emailProcessor = new EmailProcessor();

    Message email = emailProcessor.waitForPlNotifyEmailToBePresent(
        message,
        level,
        "X937JsonFileProcessor"
    );

    Assert.assertNotNull(
        email,
        "Script sends PLNotify email when image failed to save"
    );
    try {
      emailProcessor.deleteMessage(email);
    } catch (MessagingException e) {
      Logger.error(e.toString());
    }
  }

  private void assertFileMd5(String expectedMd5, String location) {
    SshUtil sshUtil = new SshUtil();

    Assert.assertTrue(sshUtil.fileExists(location), "Image file should be present.");
    Assert.assertEquals(
        sshUtil.getFileMd5(location), expectedMd5, "Image contents should be identical."
    );
  }
}
