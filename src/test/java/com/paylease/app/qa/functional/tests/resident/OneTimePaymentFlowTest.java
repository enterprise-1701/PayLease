package com.paylease.app.qa.functional.tests.resident;

import com.paylease.app.qa.ProgressBarTester;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.features.PaymentFlow;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.paymentflow.PaymentAmountPage;
import com.paylease.app.qa.framework.pages.paymentflow.ProgressBar;
import com.paylease.app.qa.framework.pages.paymentflow.ReceiptPage;
import com.paylease.app.qa.framework.pages.paymentflow.ReviewAndSubmitPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class OneTimePaymentFlowTest extends ScriptBase {

  private static final String REGION = "resident";
  private static final String FEATURE = "otp";

  //--------------------------------ONE TIME PAYMENT TESTS------------------------------------------

  @Test
  public void tc07() {
    Logger.info("To validate that a Resident is navigated to Receipt page after clicking on "
        + "continue button.");

    PaymentFlow paymentFlow = testPrepGeneral("tc7");

    paymentFlow.openStep(PaymentFlow.STEP_REVIEW);

    ReviewAndSubmitPage reviewAndSubmitPage = new ReviewAndSubmitPage();

    reviewAndSubmitPage.clickSubmitButton();

    ReceiptPage receiptPage = new ReceiptPage();

    Assert.assertTrue(receiptPage.pageIsLoaded());
  }

  @Test
  public void tc08() {
    Logger.info("Validate progress bar - Payment Amount page.");

    PaymentFlow paymentFlow = testPrepGeneral("tc8");

    paymentFlow.openStep(PaymentFlow.STEP_AMOUNT);

    verifyProgressBar(1, "Amount");
  }

  @Test
  public void tc09() {
    Logger.info("Validate progress bar - Payment Method page.");

    PaymentFlow paymentFlow = testPrepGeneral("tc9");

    paymentFlow.openStep(PaymentFlow.STEP_METHOD);

    verifyProgressBar(2, "Account");
  }

  @Test
  public void tc12() {
    Logger.info("Validate progress bar - Bank Details page.");

    PaymentFlow paymentFlow = testPrepGeneral("tc12");

    paymentFlow.openStep(PaymentFlow.STEP_NEW_BANK);

    verifyProgressBar(2, "Account");
  }

  @Test
  public void tc15() {
    Logger.info("Validate progress bar - CC Details page.");

    PaymentFlow paymentFlow = testPrepGeneral("tc15");

    paymentFlow.openStep(PaymentFlow.STEP_NEW_CREDIT);

    verifyProgressBar(2, "Account");
  }

  @Test
  public void tc18() {
    Logger.info("Validate progress bar - Review and Submit Page - Click on previous button.");

    PaymentFlow paymentFlow = testPrepGeneral("tc18");

    paymentFlow.openStep(PaymentFlow.STEP_REVIEW);

    ReviewAndSubmitPage reviewAndSubmitPage = new ReviewAndSubmitPage();

    reviewAndSubmitPage.clickPreviousButton();

    Assert.assertEquals(paymentFlow.getCurrentStep(), PaymentFlow.STEP_METHOD,
        "Should be on Payment Method page");
  }

  @Test
  public void tc19() {
    Logger.info("Validate progress bar - Review & Submit page.");

    PaymentFlow paymentFlow = testPrepGeneral("tc19");

    paymentFlow.openStep(PaymentFlow.STEP_REVIEW);

    verifyProgressBar(3, "Review");
  }

  @Test
  public void tc22() {
    Logger.info("Validate progress bar - Receipt page.");

    PaymentFlow paymentFlow = testPrepGeneral("tc22");

    paymentFlow.openStep(PaymentFlow.STEP_RECEIPT);

    verifyProgressBar(4, "Receipt");
  }

  @Test
  public void tc24() {
    Logger.info("Page navigation with Transaction ID - Payment Amount page.");

    assertDefaultStepForSuccessfulTransactions(PaymentFlow.STEP_AMOUNT);
  }

  @Test
  public void tc25() {
    Logger.info("Page navigation with Transaction ID - Payment Method page.");

    assertDefaultStepForSuccessfulTransactions(PaymentFlow.STEP_METHOD);
  }

  @Test
  public void tc26() {
    Logger.info("Page navigation with Transaction ID - Bank Details page.");

    assertDefaultStepForSuccessfulTransactions(PaymentFlow.STEP_NEW_BANK);
  }

  @Test
  public void tc27() {
    Logger.info("Page navigation with Transaction ID - CC/DC Details page.");

    assertDefaultStepForSuccessfulTransactions(PaymentFlow.STEP_NEW_CREDIT);
  }

  @Test
  public void tc28() {
    Logger.info("Page navigation with Transaction ID - Review and Submit page.");

    assertDefaultStepForSuccessfulTransactions(PaymentFlow.STEP_REVIEW);
  }

  @Test
  public void tc29() {
    Logger.info("Page navigation with Transaction ID - other resident in same property.");

    PaymentFlow paymentFlow = testPrepGeneral("tc29");

    paymentFlow.openStep(PaymentFlow.STEP_RECEIPT);

    PaymentAmountPage paymentAmountPage = new PaymentAmountPage();

    Assert.assertEquals(paymentAmountPage.getErrorMessage(), "Invalid item specified.",
        "Invalid item specified error message should display.");
  }

  @Test
  public void tc30() {
    Logger.info("Page navigation with Transaction ID - transaction id does not exist in database.");

    PaymentFlow paymentFlow = testPrepGeneral("tc30");

    paymentFlow.openStep(PaymentFlow.STEP_RECEIPT);

    PaymentAmountPage paymentAmountPage = new PaymentAmountPage();

    Assert.assertEquals(paymentAmountPage.getErrorMessage(), "Invalid item specified.",
        "Invalid item specified error message should display.");
  }

  @Test
  public void tc31() {
    Logger.info("Page navigation with Transaction ID - other resident in different pm.");

    PaymentFlow paymentFlow = testPrepGeneral("tc31");

    paymentFlow.openStep(PaymentFlow.STEP_RECEIPT);

    PaymentAmountPage paymentAmountPage = new PaymentAmountPage();

    Assert.assertEquals(paymentAmountPage.getErrorMessage(), "Invalid item specified.",
        "Invalid item specified error message should display.");
  }

  @Test
  public void showDisclosureTest() {
    Logger.info("Verify we see disclosure messages for One-time payment");

    CreditCardDetails creditCardDetails = new CreditCardDetails();
    creditCardDetails.assertDisclosureMessage(
        REGION, FEATURE, "tc21", PaymentFlow.SCHEDULE_ONE_TIME, PaymentFlow.STEP_NEW_CREDIT
    );
  }

  //------------------------------------TEST METHOD-------------------------------------------------

  private PaymentFlow testPrepGeneral(String testCase) {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);

    testSetupPage.open();

    final String transId = testSetupPage.getString("transactionId");

    return new PaymentFlow(PaymentFlow.UI_RES, PaymentFlow.SCHEDULE_ONE_TIME,
        transId);
  }

  private void verifyProgressBar(int index, String step) {
    ProgressBarTester progressBarTester = new ProgressBarTester();

    progressBarTester.assertStep(index, step, ProgressBar.CURRENT);

    if (step.equals("Receipt")) {
      progressBarTester.assertPreviousStepsDisabled();
    } else {
      progressBarTester.assertPreviousStepsEnabled(index);
      progressBarTester.assertFutureStepsDisabled(index);
    }
  }

  private void assertDefaultStepForSuccessfulTransactions(int paymentFlowStep) {
    PaymentFlow paymentFlow = testPrepGeneral("tc22");

    paymentFlow.openStep(paymentFlowStep);

    verifyProgressBar(4, "Receipt");
  }
}
