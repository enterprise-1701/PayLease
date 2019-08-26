package com.paylease.app.qa.functional.tests.pm;

import static com.paylease.app.qa.framework.AppConstant.QA_MAILBOX;

import com.paylease.app.qa.framework.ExcelUtil;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.UtilityManager;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.utility.emailtool.EmailProcessor;
import com.paylease.app.qa.framework.utility.emailtool.MessagePart;
import com.paylease.app.qa.framework.utility.envwriter.EnvWriterUtil;
import com.paylease.app.qa.framework.utility.sshtool.SshUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.mail.Message;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class VoidReturnReversalReportTest extends ScriptBase {

  // Test Set Up Page
  private static final String FEATURE = "VoidReturnReversals";
  private static final String REGION = "Pm";

  // Excel Data
  private static final String KEYS = "column_keys";
  private static final String SHEET_NAME = "Voids, Returns and Reversals";

  // Environment Variables
  private static final String ENV_FILE_NAME = "0_test_stuff.env";
  private static final String ENV_VARIABLE_NAME = "DEV_SYSTEM_DEVELOPER";

  // Email Data
  private static final String EMAIL_SUBJECT = "Void, Return and Reversal Summary Report";
  private static final String EMAIL_BODY_CONTENTS = "Attached is a summary of your Voided,"
      + " Returned, and Reversed items from";

  // Script and Queue Worker Data.
  private static final String PARAMS = "--pm_id={pm_id} --report_date={date}";
  private static final String VRR_BATCH_SCRIPT = "email_reports_void_return_reversal";
  private static final String VRR_QUEUE_NAME = "email_void_return_reversal_report";
  private static final String VRR_QUEUE_WORKER = "void_return_reversal_report";

  /**
   * This before class method ensures that the e-mails go to the QE mailbox
   * so that the attachment can be downloaded and the data can be verified.
   */
  @BeforeClass(alwaysRun = true)
  public void setUpEnv() {
    EnvWriterUtil writeUtil = new EnvWriterUtil();
    writeUtil.replaceEnvFileValue(ENV_FILE_NAME, ENV_VARIABLE_NAME, QA_MAILBOX);
  }

  /**
   * This method simply deletes the .env file after all the tests have run.
   */
  @AfterClass(alwaysRun = true)
  public void deleteEnv() {
    EnvWriterUtil writerUtil = new EnvWriterUtil();
    // By passing in a null value, the EnvWriterUtil will look for the file name that
    // was passed and delete the file. The varName is never used or checked.
    writerUtil.replaceEnvFileValue(ENV_FILE_NAME, null, null);
  }

  @Test(groups = "manual")
  public void verifyEndToEndEmailVoidReturnReversal() {
    Logger.info(
        "Verify that an e-mail is sent to a PM containing all information about their voids,"
        + " returns, and reversals."
    );

    TestSetupPage testPage = new TestSetupPage(REGION, FEATURE, "tc6597");
    EmailProcessor ep = new EmailProcessor();

    testPage.open();

    String pmId = testPage.getString("pm_id");

    Logger.info("Run the needed batch script and queue worker to prepare the data and send e-mail");
    try {
      runBatchScriptAndQueueWorker(pmId);
    } catch (Exception e) {
      e.printStackTrace();
    }

    ArrayList<HashMap<String, String>> transactions = new ArrayList<>();
    String[] transIds = {"trans_1", "trans_2", "trans_3", "trans_4", "trans_5", "trans_6"};
    for (String trans : transIds) {
      transactions.add(getKeysAndExpectedValues(testPage, trans));
    }

    Message email = ep.waitForSpecifiedEmailToBePresent(EMAIL_SUBJECT, EMAIL_BODY_CONTENTS);
    List<MessagePart> emailParts = ep.getBody(email, new ArrayList<>(), false, true);

    MessagePart attachment = emailParts.get(2);
    String attachmentFilePath = getEmailAttachmentFilePath(attachment);

    String[][] vrrAttachment = ExcelUtil.getExcelData(attachmentFilePath, SHEET_NAME, 1);

    int j = 2;
    for (int i = 0; i < transactions.size(); i++) {
      verifyDataInAttachment(transactions.get(i), vrrAttachment[i + j]);
      if (isBlankRowNext(i)) {
        j = j + 2;
      }
    }
  }

  /**
   * This method checks to find if the next row of the report is going to be blank. If
   * it is blank, then return true so it can be handled.
   *
   * @param i current row
   * @return if a blank row is the next row, return true.
   */
  private boolean isBlankRowNext(int i) {
    return (((i - 1) % 2 == 0));
  }

  /**
   * This method fetches the data from the test set up page by grabbing the
   * expected values for each transaction that will be asserted against.
   *
   * @param tsp Test setup page, to find the elements.
   * @param trans the transaction number
   * @return A Hashmap of the key-value pairs for expected data.
   */
  private HashMap<String, String> getKeysAndExpectedValues(TestSetupPage tsp, String trans) {
    String[] keys = getKeysToBeChecked(tsp);
    String[] values = getExpectedValues(tsp, trans);

    HashMap<String, String> data = new HashMap<>();
    for (int i = 0; i < keys.length; i++) {
      data.put(keys[i], values[i]);
    }

    return data;
  }

  /**
   * A helper method for getKeysAndExpectedValues().
   *
   * @param tsp The test set up page object.
   * @return An array of strings containing the keys for the Hashmap.
   */
  private String[] getKeysToBeChecked(TestSetupPage tsp) {
    String keyString = tsp.getString(KEYS);
    return keyString.split(", ");
  }

  /**
   * A helper method for getKeysAndExpectedValues().
   *
   * @param tsp The test set up page object.
   * @param transaction The transaction that will be mined.
   * @return Array of strings containing the expected values.
   */
  private String[] getExpectedValues(TestSetupPage tsp, String transaction) {
    String valueString = tsp.getString(transaction);
    return valueString.split(", ");
  }

  /**
   * This will run the report script, and the queue worker to send the e-mail.
   *
   * @param pmId Report will run against this PM.
   * @throws Exception for running batch scripts.
   */
  private void runBatchScriptAndQueueWorker(String pmId) throws Exception {
    SshUtil ssh = new SshUtil();

    String param = fixParameters(pmId);
    ssh.runBatchScriptWithArgs(VRR_BATCH_SCRIPT, param);

    ssh.runQueueWorkerUntilComplete(VRR_QUEUE_WORKER, VRR_QUEUE_NAME, true);
  }

  /**
   * Helper method for the parameters to run the batch script.
   *
   * @param pmId The ID to run against.
   * @return String containing the extra parameters.
   */
  private String fixParameters(String pmId) {
    String date = UtilityManager.getCurrentDate(UtilityManager.YEAR_MONTH_DAY_DASH);
    return PARAMS.replace("{pm_id}", pmId).replace("{date}", '"' + date +  '"');
  }

  /**
   * A simple validator using SoftAssert.
   *
   * @param expected Hashmap of the expected values
   * @param actual A string array containing the values found.
   */
  private void verifyDataInAttachment(HashMap<String,String> expected, String[] actual) {
    SoftAssert softAssert = new SoftAssert();

    String actualTrans = String.valueOf(new BigDecimal(actual[0]).intValue());
    softAssert.assertEquals(expected.get("Transaction #"), actualTrans);
    softAssert.assertEquals(expected.get("Initiation Date"), actual[1]);
    softAssert.assertEquals(expected.get("Resident"), actual[2]);
    softAssert.assertEquals(expected.get("Resident Account #"), actual[3]);
    softAssert.assertEquals(expected.get("Property"), actual[4]);
    softAssert.assertEquals(expected.get("Unit"), actual[5]);
    softAssert.assertEquals(expected.get("Reason"), actual[6]);
    softAssert.assertEquals(expected.get("Return Code"), actual[7]);
    softAssert.assertEquals(expected.get("Bill Type"), actual[8]);

    String amount = actual[9];
    if (amount.contains(".")) {
      // Substring is exclusive on the end index. So we want the first 3 digits,
      // substring() will return indexes 0, 1, and 2 as a string.
      amount = amount.substring(0, 3);
    }
    softAssert.assertEquals(expected.get("Amount"), amount);

    softAssert.assertAll();
  }
}
