package com.paylease.app.qa.functional.tests.pm;

import static com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage.NEW_BANK;
import static com.paylease.app.qa.framework.pages.paymentflow.ProgressBar.CURRENT;
import static com.paylease.app.qa.framework.pages.paymentflow.ProgressBar.DISABLED;
import static com.paylease.app.qa.framework.pages.paymentflow.ProgressBar.ENABLED;

import com.paylease.app.qa.ProgressBarTester;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.features.PaymentFlow;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.paymentflow.BankAccountDetailsPage;
import com.paylease.app.qa.framework.pages.paymentflow.CardAccountDetailsPage;
import com.paylease.app.qa.framework.pages.paymentflow.PaymentAmountPage;
import com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage;
import com.paylease.app.qa.framework.pages.paymentflow.ReviewAndSubmitPage;
import com.paylease.app.qa.framework.pages.paymentflow.SelectResidentPage;
import com.paylease.app.qa.framework.pages.pm.PmHomePage;
import com.paylease.app.qa.framework.pages.pm.PmMenu;
import com.paylease.app.qa.framework.pages.pm.PmPaymentHistoryPage;
import com.paylease.app.qa.framework.pages.pm.PmResidentListPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class OneTimePaymentFlowTest extends ScriptBase {

  private static final String REGION = "Pm";
  private static final String FEATURE = "otp";

  //--------------------------------ONE TIME PAYMENT TESTS------------------------------------------

  @Test
  public void otpPmTc1() {
    Logger.info("To validate that a PM can navigate to One time payment from the Payments tab");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();

    PmHomePage pmHomePage = new PmHomePage();

    pmHomePage.open();

    PmMenu pmMenu = new PmMenu();
    pmMenu.clickOneTimePaymentTab();

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_ONE_TIME);

    Assert.assertEquals(paymentFlow.getCurrentStep(), PaymentFlow.STEP_SELECT_RESIDENT,
        "Select Resident page should load");
  }

  @Test
  public void otpPmTc2() {
    Logger.info(
        "To validate that a PM is navigated to Payment amount page after clicking make a payment link");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc2");
    testSetupPage.open();
    final String residentId = testSetupPage.getString("residentId");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_ONE_TIME);
    paymentFlow.openStep(PaymentFlow.STEP_SELECT_RESIDENT);

    SelectResidentPage selectResidentPage = new SelectResidentPage();

    selectResidentPage.clickToBeginPayment(residentId);

    Assert.assertEquals(paymentFlow.getCurrentStep(), PaymentFlow.STEP_AMOUNT,
        "Should be on Payment Amount page");
  }

  @Test
  public void otpPmTc3() {
    Logger.info("To validate that a PM can navigate to One time payment from resident list page");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc3");
    testSetupPage.open();
    final String residentId = testSetupPage.getString("residentId");

    PmResidentListPage pmResidentListPage = new PmResidentListPage();

    pmResidentListPage.open();
    pmResidentListPage.clickMakePayment(residentId, true);

    PmPaymentHistoryPage pmPaymentHistoryPage = new PmPaymentHistoryPage();

    Assert.assertTrue(pmPaymentHistoryPage.pageIsLoaded(), "PM History page should load");
    Assert.assertTrue(pmPaymentHistoryPage.getResidentId().equals(residentId),
        "Resident Id's should match");
  }

  @Test
  public void otpPmTc5() {
    Logger.info(
        "To validate that a PM is navigated to Payment method page after clicking on continue button");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc5");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_ONE_TIME,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_AMOUNT);

    PaymentAmountPage paymentAmountPage = new PaymentAmountPage();

    paymentAmountPage.fillAndSubmit();

    Assert.assertEquals(paymentFlow.getCurrentStep(), PaymentFlow.STEP_METHOD,
        "Should be on Payment Method page");
  }

  @Test
  public void otpPmTc7() {
    Logger.info(
        "To validate that a PM is navigated to Bank details page after clicking on continue button");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc7");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_ONE_TIME,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_METHOD);

    PaymentMethodPage paymentMethodPage = new PaymentMethodPage();

    paymentMethodPage.selectPaymentMethod(NEW_BANK);
    paymentMethodPage.clickContinueButton();

    Assert.assertEquals(paymentFlow.getCurrentStep(), PaymentFlow.STEP_NEW_BANK,
        "Should be on Bank Details page");
  }

  @Test
  public void otpPmTc8() {
    Logger.info(
        "To validate that a PM is navigated to Card details page after clicking on continue button");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc8");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_ONE_TIME,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_METHOD);

    PaymentMethodPage paymentMethodPage = new PaymentMethodPage();

    paymentMethodPage.selectPaymentMethod(PaymentMethodPage.NEW_CREDIT);
    paymentMethodPage.clickContinueButton();

    Assert.assertEquals(paymentFlow.getCurrentStep(), PaymentFlow.STEP_NEW_CREDIT,
        "Should be on Card Details page");
  }

  @Test
  public void otpPmTc9() {
    Logger.info(
        "To validate that a PM is navigated to Review and Submit page after clicking on continue button on Bank Details Page");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc9");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_ONE_TIME,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_NEW_BANK);

    BankAccountDetailsPage bankAccountDetailsPage = new BankAccountDetailsPage();

    bankAccountDetailsPage.fillBankDetailsAndSubmit();

    Assert.assertEquals(paymentFlow.getCurrentStep(), PaymentFlow.STEP_REVIEW,
        "Should be on Review and Submit page");
  }

  @Test
  public void otpPmTc10() {
    Logger.info(
        "To validate that a PM is navigated to Review and Submit page after clicking on continue button on Card Details page");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc10");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_ONE_TIME,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_NEW_CREDIT);

    CardAccountDetailsPage cardAccountDetailsPage = new CardAccountDetailsPage();

    cardAccountDetailsPage.fillAndSubmitCardDetailsWithCardNum();

    Assert.assertEquals(paymentFlow.getCurrentStep(), PaymentFlow.STEP_REVIEW,
        "Should be on Review and Submit page");
  }

  @Test
  public void otpPmTc11() {
    Logger.info(
        "To validate that a PM is navigated to Receipt page after clicking on continue button");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc11");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_ONE_TIME,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_REVIEW);

    ReviewAndSubmitPage reviewAndSubmitPage = new ReviewAndSubmitPage();

    reviewAndSubmitPage.clickSubmitButton();

    Assert.assertEquals(paymentFlow.getCurrentStep(), PaymentFlow.STEP_RECEIPT,
        "Should be on Receipt page");
  }

  @Test
  public void otpPmTc12() {
    Logger.info("Validate progress bar - Select Resident Page");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc12");
    testSetupPage.open();

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_ONE_TIME);
    paymentFlow.openStep(PaymentFlow.STEP_SELECT_RESIDENT);

    ProgressBarTester progressBarTester = new ProgressBarTester();

    progressBarTester.assertStep(1, "Select Resident", CURRENT);
    progressBarTester.assertStep(2, "Amount", DISABLED);
    progressBarTester.assertStep(3, "Account", DISABLED);
    progressBarTester.assertStep(4, "Review", DISABLED);
    progressBarTester.assertStep(5, "Receipt", DISABLED);
  }

  @Test
  public void otpPmTc13() {
    Logger.info("Validate progress bar - Payment Amount Page");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc13");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_ONE_TIME,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_AMOUNT);

    ProgressBarTester progressBarTester = new ProgressBarTester();

    progressBarTester.assertStep(1, "Select Resident", ENABLED);
    progressBarTester.assertStep(2, "Amount", CURRENT);
    progressBarTester.assertStep(3, "Account", DISABLED);
    progressBarTester.assertStep(4, "Review", DISABLED);
    progressBarTester.assertStep(5, "Receipt", DISABLED);
  }

  @Test
  public void otpPmTc15() {
    Logger.info("Validate progress bar - Payment Method Page");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc15");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_ONE_TIME,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_METHOD);

    ProgressBarTester progressBarTester = new ProgressBarTester();

    progressBarTester.assertStep(1, "Select Resident", ENABLED);
    progressBarTester.assertStep(2, "Amount", ENABLED);
    progressBarTester.assertStep(3, "Account", CURRENT);
    progressBarTester.assertStep(4, "Review", DISABLED);
    progressBarTester.assertStep(5, "Receipt", DISABLED);
  }

  @Test
  public void otpPmTc19() {
    Logger.info("Validate progress bar - Bank Details Page");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc19");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_ONE_TIME,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_NEW_BANK);

    ProgressBarTester progressBarTester = new ProgressBarTester();

    progressBarTester.assertStep(1, "Select Resident", ENABLED);
    progressBarTester.assertStep(2, "Amount", ENABLED);
    progressBarTester.assertStep(3, "Account", CURRENT);
    progressBarTester.assertStep(4, "Review", DISABLED);
    progressBarTester.assertStep(5, "Receipt", DISABLED);
  }

  @Test
  public void otpPmTc23() {
    Logger.info("Validate progress bar - Card Details Page");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc23");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_ONE_TIME,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_NEW_CREDIT);

    ProgressBarTester progressBarTester = new ProgressBarTester();

    progressBarTester.assertStep(1, "Select Resident", ENABLED);
    progressBarTester.assertStep(2, "Amount", ENABLED);
    progressBarTester.assertStep(3, "Account", CURRENT);
    progressBarTester.assertStep(4, "Review", DISABLED);
    progressBarTester.assertStep(5, "Receipt", DISABLED);
  }

  @Test
  public void otpPmTc27() {
    Logger.info("Validate progress bar - Review and Submit Page - Previous button");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc27");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_ONE_TIME,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_REVIEW);

    ReviewAndSubmitPage reviewAndSubmitPage = new ReviewAndSubmitPage();

    reviewAndSubmitPage.clickPreviousButton();

    Assert.assertEquals(paymentFlow.getCurrentStep(), PaymentFlow.STEP_METHOD,
        "Should be on Payment Method page");

  }

  @Test
  public void otpPmTc28() {
    Logger.info("Validate progress bar - Review and Submit Page");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc28");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_ONE_TIME,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_REVIEW);

    ProgressBarTester progressBarTester = new ProgressBarTester();

    progressBarTester.assertStep(1, "Select Resident", ENABLED);
    progressBarTester.assertStep(2, "Amount", ENABLED);
    progressBarTester.assertStep(3, "Account", ENABLED);
    progressBarTester.assertStep(4, "Review", CURRENT);
    progressBarTester.assertStep(5, "Receipt", DISABLED);
  }

  @Test
  public void otpPmTc32() {
    Logger.info("Validate progress bar - Receipt Page");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc32");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_ONE_TIME,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_RECEIPT);

    ProgressBarTester progressBarTester = new ProgressBarTester();

    progressBarTester.assertStep(1, "Select Resident", DISABLED);
    progressBarTester.assertStep(2, "Amount", DISABLED);
    progressBarTester.assertStep(3, "Account", DISABLED);
    progressBarTester.assertStep(4, "Review", DISABLED);
    progressBarTester.assertStep(5, "Receipt", CURRENT);
  }

  @Test
  public void otpPmTc34() {
    Logger.info("Page navigation with Transaction ID - Payment Amount Page");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc34");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_ONE_TIME,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_AMOUNT);

    Assert.assertEquals(paymentFlow.getCurrentStep(), PaymentFlow.STEP_RECEIPT,
        "Should be on Receipt page");
  }

  @Test
  public void otpPmTc35() {
    Logger.info("Page navigation with Transaction ID - Payment Method Page");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc35");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_ONE_TIME,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_METHOD);

    Assert.assertEquals(paymentFlow.getCurrentStep(), PaymentFlow.STEP_RECEIPT,
        "Should be on Receipt page");
  }

  @Test
  public void otpPmTc36() {
    Logger.info("Page navigation with Transaction ID - Bank Details Page");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc36");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_ONE_TIME,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_NEW_BANK);

    Assert.assertEquals(paymentFlow.getCurrentStep(), PaymentFlow.STEP_RECEIPT,
        "Should be on Receipt page");
  }

  @Test
  public void otpPmTc37() {
    Logger.info("Page navigation with Transaction ID - Card Details Page");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc37");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_ONE_TIME,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_NEW_CREDIT);

    Assert.assertEquals(paymentFlow.getCurrentStep(), PaymentFlow.STEP_RECEIPT,
        "Should be on Receipt page");
  }

  @Test
  public void otpPmTc38() {
    Logger.info("Page navigation with Transaction ID - Review and Submit Page");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc38");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_ONE_TIME,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_REVIEW);

    Assert.assertEquals(paymentFlow.getCurrentStep(), PaymentFlow.STEP_RECEIPT,
        "Should be on Receipt page");
  }

  @Test
  public void otpPmTc40() {
    Logger.info("Page navigation with Transaction ID - Other PM");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc40");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_ONE_TIME,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_RECEIPT);

    SelectResidentPage selectResidentPage = new SelectResidentPage();

    Assert.assertTrue(
        selectResidentPage.getInvalidItemErrorMessage().equals("Invalid item specified."),
        "Invalid item specified error message should show on Select Resident page");
  }

  @Test
  public void otpPmTc41() {
    Logger.info("Page navigation with Invalid Transaction ID");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc41");
    testSetupPage.open();

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_ONE_TIME,
        "10000001");
    paymentFlow.openStep(PaymentFlow.STEP_RECEIPT);

    SelectResidentPage selectResidentPage = new SelectResidentPage();

    Assert.assertTrue(
        selectResidentPage.getInvalidItemErrorMessage().equals("Invalid item specified."),
        "Invalid item specified error message should show on Select Resident page");
  }
}
