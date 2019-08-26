package com.paylease.app.qa.manual.tests.admin;

import com.paylease.app.qa.framework.AppConstant;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.pages.admin.ConfigureIntegrationPage;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.utility.emailtool.EmailProcessor;
import com.paylease.app.qa.functional.tests.admin.ConfigureIntegrationTest;
import javax.mail.Message;
import javax.mail.MessagingException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ConfigureIntegrationManualTest extends ConfigureIntegrationTest {

  private static final String BRICK_FTP_FROM_EMAIL = "no-reply@brickftp.com";
  private static final String BRICK_FTP_WELCOME_EMAIL_SUBJECT = "PayLease FTP - Welcome!";
  private static final int RETRY_TIMEOUT = 10000;
  private static final int RETRY_COUNT = 4;

  @Test(groups = {"brickftp","manual"})
  public void createBrickFtpAccountAndValidateWelcomeEmail() {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc3029");
    testSetupPage.open();

    EmailProcessor emailProcessor = new EmailProcessor();

    final String pmId = testSetupPage.getString("pm_id");

    ConfigureIntegrationPage integrationPage = new ConfigureIntegrationPage(pmId);
    integrationPage.open();
    integrationPage.selectIntegrationType("ASYST");
    integrationPage.enterEmailAddress(AppConstant.QA_MAILBOX);
    integrationPage.clickSubmit();

    Message message = waitForMessageToBePresent(pmId);
    Assert.assertNotNull(message, "BrickFTP sends welcome email when FTP account created");
    try {
      emailProcessor.deleteMessage(message);
    } catch (MessagingException e) {
      Logger.error(e.toString());
    }
  }

  @Test(groups = {"brickftp","manual"})
  public void createBrickFtpAccountAndValidateAdminEmail() {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc3029");
    testSetupPage.open();
    EmailProcessor emailProcessor = new EmailProcessor();

    final String pmId = testSetupPage.getString("pm_id");

    ConfigureIntegrationPage integrationPage = new ConfigureIntegrationPage(pmId);
    integrationPage.open();
    integrationPage.selectIntegrationType("ASYST");
    integrationPage.clickSubmit();

    Message message = waitForMessageToBePresent(pmId);
    Assert.assertNotNull(message, "BrickFTP sends welcome email when FTP account created");
    try {

      emailProcessor.deleteMessage(message);
    } catch (MessagingException e) {
      Logger.error(e.toString());
    }
  }

  /**
   * Wait for email message containing PM ID to be present in INBOX.
   *
   * @param pmId PM ID to find in message body
   * @return Message that was found
   */
  private Message waitForMessageToBePresent(String pmId) {
    EmailProcessor emailProcessor = new EmailProcessor();
    emailProcessor.connect();

    for (int i = 0; i < RETRY_COUNT; i++) {
      Logger.trace("Attempt #" + (i + 1) + ": Looking for message.");

      Message[] messages = emailProcessor
          .getMessagesWhereBodyContains(pmId);
      if (messages != null) {
        for (Message message : messages) {
          if (emailProcessor.getFromAddress(message).contains(BRICK_FTP_FROM_EMAIL)
              && emailProcessor.getSubject(message).equals(BRICK_FTP_WELCOME_EMAIL_SUBJECT)) {

            return message;
          }
        }
      }

      try {
        Thread.sleep(RETRY_TIMEOUT);
      } catch (InterruptedException e) {
        Logger.debug("Sleep problem: " + e.getMessage());
      }
    }

    return null;
  }
}
