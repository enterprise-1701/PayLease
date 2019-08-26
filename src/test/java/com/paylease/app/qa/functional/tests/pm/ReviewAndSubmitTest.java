package com.paylease.app.qa.functional.tests.pm;

import com.paylease.app.qa.framework.DataBaseConnector;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.UtilityManager;
import com.paylease.app.qa.framework.features.PaymentFlow;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.paymentflow.ReviewAndSubmitPage;
import com.paylease.app.qa.framework.pages.pm.PmMenu;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ReviewAndSubmitTest extends ScriptBase {

  private static final String REGION = "Pm";
  private static final String FEATURE = "ReviewAndSubmit";

  //--------------------------------REVIEW AND SUBMIT PAGE TESTS------------------------------------

  @Test
  public void validatePaymentAmountAndServiceFee() {
    Logger.info("To validate that correct payment amount and service fee is displayed");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc2");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");
    final String amount = testSetupPage.getString("amount");
    final String feeAmount = testSetupPage.getString("feeAmount");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_ONE_TIME,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_REVIEW);

    ReviewAndSubmitPage reviewAndSubmitPage = new ReviewAndSubmitPage();

    Assert.assertEquals(reviewAndSubmitPage.getPaymentAmount(), "$" + amount,
        "The payment amount should be " + amount + " in Review and Submit page");

    Assert.assertEquals(reviewAndSubmitPage.getFeeAmount(), "$" + feeAmount,
        "The fee should be " + feeAmount + " in Review and Submit page");
  }

  @Test
  public void validateIfPaymentDetailsHasAllPaymentInfoAch() {
    Logger.info("To validate that Payment Details include all payment information");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc3");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");
    final String nameOnAccount = testSetupPage.getString("nameOnAccount");
    final String bankName = testSetupPage.getString("bankName");
    final String routingNumber = testSetupPage.getString("routingNumber");
    final String accountNumber = testSetupPage.getString("accountNumber");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_ONE_TIME,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_REVIEW);

    ReviewAndSubmitPage reviewAndSubmitPage = new ReviewAndSubmitPage();

    UtilityManager utilityManager = new UtilityManager();

    String accNumLastFour = utilityManager.getLastFourChar(accountNumber);

    Assert.assertEquals(reviewAndSubmitPage.getBankName(), bankName,
        "The bank name should be " + bankName + " in Review and Submit page");

    Assert.assertEquals(reviewAndSubmitPage.getAccountNumberLastFour(), "#" + accNumLastFour,
        "The account number last four should be #" + accNumLastFour + " in Review and Submit "
            + "page");

    Assert.assertEquals(reviewAndSubmitPage.getRoutingNumber(), routingNumber,
        "The routing number should be " + routingNumber + " in Review and Submit page");

    Assert.assertEquals(reviewAndSubmitPage.getNameOnAccount(), nameOnAccount,
        "The name on account should be " + nameOnAccount + " in Review and Submit page");
  }

  @Test
  public void validateIfPaymentDetailsHasAllPaymentInfoCc() {
    Logger.info("To validate that card type and last four digits of card number used is displayed");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc4");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");
    final String nameOnCard = testSetupPage.getString("nameOnCard");
    final String cardType = testSetupPage.getString("cardType");
    final String ccNumber = testSetupPage.getString("ccNumber");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_ONE_TIME,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_REVIEW);

    ReviewAndSubmitPage reviewAndSubmitPage = new ReviewAndSubmitPage();

    UtilityManager utilityManager = new UtilityManager();

    String ccNumLastFour = utilityManager.getLastFourChar(ccNumber);

    Assert.assertTrue(reviewAndSubmitPage.getCardType().equalsIgnoreCase(cardType),
        "The card type should be " + cardType + " in Review and Submit page");

    Assert.assertEquals(reviewAndSubmitPage.getCardNumberLastFour(), "#" + ccNumLastFour,
        "The cc number last four should be #" + ccNumLastFour + " in Review and Submit page");

    Assert.assertEquals(reviewAndSubmitPage.getNameOnCard(), nameOnCard,
        "The name on card should be " + nameOnCard + " in Review and Submit page");
  }

  /**
   * This test method is not currently running because it is failing. Please re-add the Test
   * annotation with ticket DEV-41625.
   */
  public void validatePaymentRecipientInformation() {
    Logger.info("To validate that Payment Recipient section includes all property information");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc5");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");
    final String propertyName = testSetupPage.getString("propertyName");
    final String propertyAddress = testSetupPage.getString("propertyAddress");
    final String propertyCity = testSetupPage.getString("propertyCity");
    final String propertyState = testSetupPage.getString("propertyState");
    final String propertyZipCode = testSetupPage.getString("propertyZip");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_ONE_TIME,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_REVIEW);

    ReviewAndSubmitPage reviewAndSubmitPage = new ReviewAndSubmitPage();

    Assert.assertTrue(reviewAndSubmitPage.getPropertyName().contains(propertyName),
        "The property name should be " + propertyName + " in Review and Submit page");

    Assert.assertEquals(reviewAndSubmitPage.getPropertyAddress(), propertyAddress,
        "The property address should be " + propertyAddress + " in Review and Submit page");

    Assert.assertEquals(reviewAndSubmitPage.getPropertyCity(), propertyCity,
        "The property city should be " + propertyCity + " in Review and Submit page");

    Assert.assertEquals(reviewAndSubmitPage.getPropertyState(), propertyState,
        "The property state should be " + propertyState + " in Review and Submit page");

    Assert.assertEquals(reviewAndSubmitPage.getPropertyZip(), propertyZipCode,
        "The property zip code should be " + propertyZipCode + " in Review and Submit page");
  }

  @Test
  public void validatePmNameIsPresentInPaymentAgreement() {
    Logger.info("To validate that name of PM submitting payment is displayed");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");
    final String pmName = testSetupPage.getString("pmName");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_ONE_TIME,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_REVIEW);

    ReviewAndSubmitPage reviewAndSubmitPage = new ReviewAndSubmitPage();

    Assert.assertTrue(reviewAndSubmitPage.getPaymentAgreementMessage().contains(pmName),
        "The PM name: " + pmName + " should be present in the payment agreement message");
  }

  @Test
  public void validateDateInPaymentAgreement() {
    Logger.info("To validate that date displayed for processing time is correct");

    ReviewAndSubmitPage reviewAndSubmitPage = testPrepGeneral("tc7");

    UtilityManager utilityManager = new UtilityManager();

    Date date = new Date();

    String dateToday = utilityManager.dateToString(date, "MM-dd-yy");

    Assert.assertTrue(reviewAndSubmitPage.getPaymentAgreementMessage().contains(dateToday),
        dateToday + " should be present in the payment agreement message");
  }

  @Test
  public void validateClickingPaymentAmountEditButtonPageIsNavigatedToPaymentAmountPage() {
    Logger.info("To validate that clicking the Edit button in Payment Amount box takes PM to "
        + "payment amount tab(Step 2) ");

    PaymentFlow paymentFlow = testPrepPaymentFlow("tc8");

    ReviewAndSubmitPage reviewAndSubmitPage = new ReviewAndSubmitPage();

    reviewAndSubmitPage.clickPaymentAmountEditButton();

    Assert.assertEquals(paymentFlow.getCurrentStep(), PaymentFlow.STEP_AMOUNT,
        "Should be on Payment amount page");
  }

  @Test
  public void validateClickingPaymentDetailsEditButtonPageIsNavigatedToPaymentMethodPage() {
    Logger.info("To validate that clicking the Edit button in Payment Details box takes PM to "
        + "payment method category (Step 3)");

    PaymentFlow paymentFlow = testPrepPaymentFlow("tc9");

    ReviewAndSubmitPage reviewAndSubmitPage = new ReviewAndSubmitPage();

    reviewAndSubmitPage.clickPaymentDetailsEditButton();

    Assert.assertEquals(paymentFlow.getCurrentStep(), PaymentFlow.STEP_METHOD,
        "Should be on Payment method page");
  }

  @Test
  public void validateChargebackAndNsfDisclosurePresence() {
    Logger.info("To validate that chargeback & NSF disclosure is visible at the bottom of "
        + "Review & Submit page");

    ReviewAndSubmitPage reviewAndSubmitPage = testPrepGeneral("tc10");

    Assert.assertTrue(reviewAndSubmitPage.getChargebackAndNsfDisclosureMessage().equals(
        "Please be advised that attempted chargebacks for Non-Fraudulent transactions through the PayLease system will be subject to criminal investigation and these individuals will be prosecuted to the fullest extent of the law.\n"
            + "E-check Transactions: In the event that my bank returns this transaction for Insufficient Funds (NSF), I authorize PayLease to assess and process an automatic $25.00 NSF Fee to the same account from which this payment was initiated."),
        "The Chargeback and NSF disclosure message should be present");
  }

  @Test
  public void validateNoPaymentProcessDueToPageNavigation() {
    Logger.info(
        "To validate that by navigating away from Review & Submit page, payment will not process");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc12");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_ONE_TIME,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_REVIEW);

    PmMenu pmMenu = new PmMenu();
    pmMenu.clickOneTimePaymentTab();

    String sqlQuery = "SELECT status "
        + "FROM transactions "
        + "WHERE trans_id = " + transactionId;

    DataBaseConnector dataBaseConnector = new DataBaseConnector();
    dataBaseConnector.createConnection();

    ResultSet assertQuery = dataBaseConnector.executeSqlQuery(sqlQuery);

    try {
      while (assertQuery.next()) {
        String expectedValue = "0";

        Assert.assertEquals(assertQuery.getString("status"), expectedValue, "The transaction "
            + "status should be '0'");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }


    dataBaseConnector.closeConnection();
  }

  @Test
  public void validateNoPaymentAmountIsDisplayedInCaseOfVap() {
    Logger.info("To validate that no payment amount details are displayed");

    ReviewAndSubmitPage reviewAndSubmitPage = testPrepGeneral("tc17");

    Assert.assertFalse(reviewAndSubmitPage.isPaymentAmountTablePresent(),
        "Payment amount table should not be present in Review and Submit page");
  }

  //--------------------------------TEST METHOD-----------------------------------------------------

  private ReviewAndSubmitPage testPrepGeneral(String testCase) {
    testPrepPaymentFlow(testCase);

    return new ReviewAndSubmitPage();
  }

  private PaymentFlow testPrepPaymentFlow(String testCase) {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_ONE_TIME, transactionId);

    paymentFlow.openStep(PaymentFlow.STEP_REVIEW);

    return paymentFlow;
  }
}
