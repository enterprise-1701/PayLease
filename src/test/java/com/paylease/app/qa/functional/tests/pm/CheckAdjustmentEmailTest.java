package com.paylease.app.qa.functional.tests.pm;

import static com.paylease.app.qa.framework.AppConstant.QA_MAILBOX;

import com.paylease.app.qa.framework.ExcelUtil;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.utility.emailtool.EmailProcessor;
import com.paylease.app.qa.framework.utility.emailtool.MessagePart;
import com.paylease.app.qa.framework.utility.envwriter.EnvWriterUtil;
import com.paylease.app.qa.framework.utility.sshtool.SshUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.mail.Message;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class CheckAdjustmentEmailTest extends ScriptBase {

  private static final String REGION = "pm";
  private static final String FEATURE = "checkAdjustmentEmail";

  private static final String CHECK_ADJUSTMENT_BATCH_SCRIPT = "email_check_adjustments_report";
  private static final String CHECK_ADJUSTMENT_QUEUE_WORKER = "check_adjustments_report_email";
  private static final String CHECK_ADJUSTMENT_QUEUE_NAME = "email_check_adjustments_report";
  private static final String CHECK_ADJUSTMENT_BATCH_SCRIPT_ARG = " --pm_id='{pmId}'";
  private static final String ENV_FILE_NAME = "0_test_stuff.env";
  private static final String ENV_VARIABLE_NAME = "DEV_SYSTEM_DEVELOPER";
  private static final String EMAIL_SUBJECT = "Your Check Adjustments Notification";
  private static final String EMAIL_BODY = "Dear {firstName} {lastName}";

  @BeforeClass(alwaysRun = true)
  public void setUpStub() {
    EnvWriterUtil envWriterUtil = new EnvWriterUtil();
    envWriterUtil.replaceEnvFileValue(ENV_FILE_NAME, ENV_VARIABLE_NAME, QA_MAILBOX);
  }

  @AfterClass(alwaysRun = true)
  public void revertEnv() {
    EnvWriterUtil envWriterUtil = new EnvWriterUtil();
    envWriterUtil.replaceEnvFileValue(ENV_FILE_NAME, ENV_VARIABLE_NAME, null);
  }

  @Test(groups = {"manual"})
  public void tc6499() throws Exception {
    Logger.info(
        "Verify that the email that the PM receives for Check Adjustment shows correct data Check21"
    );

    HashMap<String, String> testSetupValues = setUp("tc6499");

    EnvWriterUtil envWriterUtil = new EnvWriterUtil();
    EmailProcessor emailProcessor = new EmailProcessor();

    int startingEmailCount = emailProcessor.numOfMessagesInInbox();

    Logger.trace(String.valueOf(startingEmailCount));
    envWriterUtil.replaceEnvFileValue(ENV_FILE_NAME, ENV_VARIABLE_NAME, QA_MAILBOX);
    runBatchScriptsAndQueueWorker(testSetupValues.get("pmId"));

    Message email = emailProcessor.waitForSpecifiedEmailToBePresent(EMAIL_SUBJECT, replacePmNameBody(testSetupValues.get("pmFirstName"), testSetupValues.get("pmLastName")));
    List<MessagePart> emailParts = emailProcessor.getBody(email, new ArrayList<>(), false, true);

    String emailMessage = emailParts.get(0).getContent();
    MessagePart emailAttachment = emailParts.get(1);
    String attachmentFilePath = getEmailAttachmentFilePath(emailAttachment);

    String[][] checkAdjustmentDoc = ExcelUtil.getExcelData(attachmentFilePath, "Sheet1");
    String[] formattedEmailValues = parseEmailBody(emailMessage);

    validateEmailValues(checkAdjustmentDoc[1], testSetupValues);
    validateEmailValues(formattedEmailValues, testSetupValues);
    emailProcessor.deleteMessage(email);
  }

  @Test(groups = {"manual"})
  public void tc6505() throws Exception {
    Logger.info(
            "Verify that the email that the PM receives for Check Adjustment shows correct data RDC"
    );

    HashMap<String, String> testSetupValues = setUp("tc6505");

    EnvWriterUtil envWriterUtil = new EnvWriterUtil();
    EmailProcessor emailProcessor = new EmailProcessor();

    envWriterUtil.replaceEnvFileValue(ENV_FILE_NAME, ENV_VARIABLE_NAME, QA_MAILBOX);
    runBatchScriptsAndQueueWorker(testSetupValues.get("pmId"));

    Message email = emailProcessor.waitForSpecifiedEmailToBePresent(
        EMAIL_SUBJECT,
        replacePmNameBody(testSetupValues.get("pmFirstName"), testSetupValues.get("pmLastName")));
    List<MessagePart> emailParts = emailProcessor.getBody(email, new ArrayList<>(), false, true);

    String emailMessage = emailParts.get(0).getContent();
    MessagePart emailAttachment = emailParts.get(1);
    String attachmentFilePath = getEmailAttachmentFilePath(emailAttachment);

    String[][] checkAdjustmentDoc = ExcelUtil.getExcelData(attachmentFilePath, "Sheet1");
    String[] formattedEmailValues = parseEmailBody(emailMessage);

    validateEmailValues(checkAdjustmentDoc[1], testSetupValues);
    validateEmailValues(formattedEmailValues, testSetupValues);
    emailProcessor.deleteMessage(email);
  }

  @Test(groups = {"manual"})
  public void tc6506() throws Exception {
    Logger.info("Verify that no emails are sent when there are no external adjustments");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6506");
    testSetupPage.open();

    String pmId = testSetupPage.getString("pmId");
    String pmFirstName = testSetupPage.getString("pmFirstName");
    String pmLastName = testSetupPage.getString("pmLastName");

    EmailProcessor emailProcessor = new EmailProcessor();
    runBatchScriptsAndQueueWorker(pmId);


    Message email = emailProcessor.waitForSpecifiedEmailToBePresent(
        EMAIL_SUBJECT,
        replacePmNameBody(pmFirstName, pmLastName)
    );

    Assert.assertNull(email);
  }

  //--------------------------------------- TEST METHODS -------------------------------------------

  private HashMap<String, String> setUp(String testCase) {

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();

    String pmId = testSetupPage.getString("pmId");
    String pmFirstName = testSetupPage.getString("pmFirstName");
    String pmLastName = testSetupPage.getString("pmLastName");

    String transId = testSetupPage.getString("transId");
    String residentId = testSetupPage.getString("residentId");
    String residentName = testSetupPage.getString("residentName");
    String transactionDate = testSetupPage.getString("transactionDate");
    String adjustmentDate = testSetupPage.getString("adjustmentDate");
    String adjustmentAmount = testSetupPage.getString("adjustmentAmount");
    String currentTransAmount = testSetupPage.getString("currentTransactionAmount");
    String property = testSetupPage.getString("property");
    String unit = testSetupPage.getString("unit");

    HashMap<String, String> testSetupValues = new HashMap<>();
    testSetupValues.put("pmId", pmId);
    testSetupValues.put("pmFirstName", pmFirstName);
    testSetupValues.put("pmLastName", pmLastName);
    testSetupValues.put("transId", transId);
    testSetupValues.put("residentId", residentId);
    testSetupValues.put("residentName", residentName);
    testSetupValues.put("transactionDate", transactionDate);
    testSetupValues.put("adjustmentDate", adjustmentDate);
    testSetupValues.put("adjustmentAmount", adjustmentAmount);
    testSetupValues.put("currentTransactionAmount", currentTransAmount);
    testSetupValues.put("property", property);
    testSetupValues.put("unit", unit);

    return testSetupValues;
  }

  private String replacePmArg(String pmId) {
    return CHECK_ADJUSTMENT_BATCH_SCRIPT_ARG.replace("{pmId}", pmId);
  }

  private String replacePmNameBody(String firstName, String lastName) {
    return EMAIL_BODY.replace("{firstName}", firstName).replace("{lastName}", lastName);
  }

  private void runBatchScriptsAndQueueWorker(String pmId) throws Exception {
    SshUtil sshUtil = new SshUtil();

    sshUtil.runBatchScriptWithArgs(CHECK_ADJUSTMENT_BATCH_SCRIPT, replacePmArg(pmId));
    sshUtil.runQueueWorkerUntilComplete(CHECK_ADJUSTMENT_QUEUE_WORKER, CHECK_ADJUSTMENT_QUEUE_NAME,
        true);
  }

  private void validateEmailValues(String[] dataRow, HashMap<String, String> expectedValues) {

    SoftAssert softAssert = new SoftAssert();

    Double adjustedValue =
        Double.parseDouble(expectedValues.get("currentTransactionAmount")) + Double
            .parseDouble(expectedValues.get("adjustmentAmount"));

    softAssert.assertEquals(expectedValues.get("transId"), dataRow[0]);
    softAssert.assertEquals(expectedValues.get("residentId"), dataRow[1]);
    softAssert.assertEquals(expectedValues.get("residentName"), dataRow[2]);
    softAssert.assertEquals(expectedValues.get("transactionDate"), dataRow[3]);
    softAssert.assertEquals(expectedValues.get("currentTransactionAmount"), dataRow[4]);
    softAssert.assertEquals(expectedValues.get("adjustmentDate"), dataRow[5]);
    softAssert.assertEquals(expectedValues.get("adjustmentAmount"), dataRow[6]);
    softAssert.assertEquals(String.valueOf(adjustedValue), dataRow[7]);
    softAssert.assertEquals(expectedValues.get("property"), dataRow[8]);
    softAssert.assertEquals(expectedValues.get("unit"), dataRow[9]);

    softAssert.assertAll();
  }

  private String[] parseEmailBody(String message) {
    String[] linesOfMessage = message.split("\n");

    String table = linesOfMessage[5].substring(6);
    String[] rows = table.split("</tr>");
    String[] dataRow = rows[1].split("</td>");

    String[] formattedDataRow = new String[10];
    int i = 0;
    for (String data : dataRow) {
      data = data.replace("<tr>", "").replace("<td>", "");
      formattedDataRow[i] = data;
      i++;
    }

    return formattedDataRow;
  }

}
