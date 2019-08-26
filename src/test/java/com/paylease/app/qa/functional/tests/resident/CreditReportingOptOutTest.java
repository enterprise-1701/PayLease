package com.paylease.app.qa.functional.tests.resident;

import static com.paylease.app.qa.framework.AppConstant.QA_MAILBOX;

import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.utility.emailtool.EmailProcessor;
import com.paylease.app.qa.framework.utility.emailtool.MessagePart;
import com.paylease.app.qa.framework.utility.envwriter.EnvWriterUtil;
import com.paylease.app.qa.framework.utility.listeners.Retry;
import com.paylease.app.qa.framework.utility.sshtool.SshUtil;
import com.paylease.app.qa.testbase.dataproviders.CreditReportingOptOutDataProvider;
import java.util.ArrayList;
import java.util.List;
import javax.mail.Message;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CreditReportingOptOutTest extends ScriptBase {

  private static final String REGION = "resident";
  private static final String FEATURE = "CreditReportingEmail";

  private static final String ENV_FILE_NAME = "0_test_setup.env";
  private static final String ENV_VARIABLE_NAME = "DEV_SYSTEM_DEVELOPER";

  private static final String EMAIL_SUBJECT = "You Have Been Opted Out From PayLease's Credit Reporting";
  private static final String EMAIL_HEADER = "Dear {fullName}";

  /**
   * This before class method ensures that the e-mails go to the QE mailbox so that the attachment
   * can be downloaded and the data can be verified.
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

  @Test(dataProvider = "creditReportingOptOut", dataProviderClass = CreditReportingOptOutDataProvider.class, groups =
      {"manual"}, retryAnalyzer = Retry.class)
  public void creditOptOutEmailTest(String testVariationNum, String testCase,
      String expectedErrorMessage) {
    String errorDescription = getErrorDescription(testCase);

    Assert.assertEquals(errorDescription, expectedErrorMessage,
        testVariationNum + " Error message does not match expected");
  }

  private String replaceEmailHeaderContents(String fullName) {
    return EMAIL_HEADER.replace("{fullName}", fullName);
  }

  private String getErrorDescription(String testCaseNum) {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCaseNum);
    testSetupPage.open();

    String fullName = testSetupPage.getString("fullName");

    SshUtil sshUtil = new SshUtil();
    EmailProcessor emailProcessor = new EmailProcessor();

    sshUtil.runBatchScript("credit_report_error_notify");
    try {
      sshUtil.runQueueWorkerUntilComplete("credit_reporting/credit_error_notification",
          "email_credit_error_notification", true);
    } catch (Exception e) {
      e.toString();
    }

    Message email = emailProcessor
        .waitForSpecifiedEmailToBePresent(EMAIL_SUBJECT, replaceEmailHeaderContents(fullName));

    List<MessagePart> emailParts = emailProcessor.getBody(email, new ArrayList<>(), false, false);
    MessagePart body = emailParts.get(1);

    String bodyContents = body.getContent();

    String[] bodySplitUp = bodyContents.replace("\r\n", "").split("<br />");

    return bodySplitUp[6];
  }
}
