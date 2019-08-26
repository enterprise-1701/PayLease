package com.paylease.app.qa.functional.tests.pm;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.features.PaymentFlow;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.paymentflow.BankAccountDetailsPage;
import com.paylease.app.qa.framework.pages.paymentflow.CardAccountDetailsPage;
import com.paylease.app.qa.framework.pages.paymentflow.PaymentAmountPage;
import com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage;
import com.paylease.app.qa.framework.pages.paymentflow.ReviewAndSubmitPage;
import com.paylease.app.qa.framework.pages.paymentflow.SchedulePage;
import com.paylease.app.qa.framework.pages.paymentflow.SelectResidentPage;
import com.paylease.app.qa.framework.pages.pm.PmFapListPage;
import com.paylease.app.qa.framework.pages.pm.PmHomePage;
import com.paylease.app.qa.framework.pages.pm.PmMenu;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FixedAutoPayTest extends ScriptBase {

  private static final String REGION = "Pm";
  private static final String FEATURE = "createFap";

  //--------------------------------FIXED AUTOPAY TESTS---------------------------------------------

  @Test
  public void fapPmTc1() {
    Logger.info("To validate that a PM can navigate to Create Fixed AutoPay from the Payments tab");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();

    PmHomePage pmHomePage = new PmHomePage();

    pmHomePage.open();

    PmMenu pmMenu = new PmMenu();

    pmMenu.clickCreateFixedAutoPayTab();

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_FIXED_AUTO);

    Assert.assertEquals(paymentFlow.getCurrentStep(), PaymentFlow.STEP_SELECT_RESIDENT,
        "Select Resident page should load");
  }

  @Test
  public void fapPmTc2() {
    Logger.info(
        "To validate that a PM can navigate to Create Fixed AutoPay from the Fixed AutoPay List page");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc2");
    testSetupPage.open();

    PmFapListPage pmFapListPage = new PmFapListPage();

    pmFapListPage.open();
    pmFapListPage.clickCreateAutoPayButton();

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_FIXED_AUTO);

    Assert.assertEquals(paymentFlow.getCurrentStep(), PaymentFlow.STEP_SELECT_RESIDENT,
        "Select Resident page should load");
  }

  @Test
  public void fapPmTc3() {
    Logger.info(
        "To validate that a PM is navigated to Payment amount page after clicking Schedule AutoPay link");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc3");
    testSetupPage.open();
    final String residentId = testSetupPage.getString("residentId");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_FIXED_AUTO);
    paymentFlow.openStep(PaymentFlow.STEP_SELECT_RESIDENT);

    SelectResidentPage selectResidentPage = new SelectResidentPage();

    selectResidentPage.clickToBeginPayment(residentId);

    Assert.assertEquals(paymentFlow.getCurrentStep(), PaymentFlow.STEP_AMOUNT,
        "Should be on Payment Amount page");
  }

  @Test
  public void fapPmTc6() {
    Logger.info(
        "To validate that a PM is navigated to Payment schedule page after clicking on continue button");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_FIXED_AUTO,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_AMOUNT);

    PaymentAmountPage paymentAmountPage = new PaymentAmountPage();

    paymentAmountPage.fillAndSubmit();

    Assert.assertEquals(paymentFlow.getCurrentStep(), PaymentFlow.STEP_SCHEDULE,
        "Should be on Payment Schedule page");
  }

  @Test
  public void fapPmTc7() {
    Logger.info(
        "To validate that a PM is navigated to Payment Method page after clicking on continue button");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc7");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_FIXED_AUTO,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_SCHEDULE);

    SchedulePage schedulePage = new SchedulePage();

    schedulePage.fillAndSubmitPaymentScheduleDetails();

    Assert.assertEquals(paymentFlow.getCurrentStep(), PaymentFlow.STEP_METHOD,
        "Should be on Payment Schedule page");
  }

  @Test
  public void fapPmTc8() {
    Logger.info(
        "To validate that a PM is navigated to Bank details page after clicking on continue button");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc8");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_FIXED_AUTO,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_METHOD);

    PaymentMethodPage paymentMethodPage = new PaymentMethodPage();

    paymentMethodPage.selectPaymentMethod(PaymentMethodPage.NEW_BANK);
    paymentMethodPage.clickContinueButton();

    Assert.assertEquals(paymentFlow.getCurrentStep(), PaymentFlow.STEP_NEW_BANK,
        "Should be on Bank Details page");
  }

  @Test
  public void fapPmTc9() {
    Logger.info(
        "To validate that a PM is navigated to Card details page after clicking on continue button");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc9");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_FIXED_AUTO,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_METHOD);

    PaymentMethodPage paymentMethodPage = new PaymentMethodPage();

    paymentMethodPage.selectPaymentMethod(PaymentMethodPage.NEW_CREDIT);
    paymentMethodPage.clickContinueButton();

    Assert.assertEquals(paymentFlow.getCurrentStep(), PaymentFlow.STEP_NEW_CREDIT,
        "Should be on Card Details page");
  }

  @Test
  public void fapPmTc10() {
    Logger.info(
        "To validate that a PM is navigated to Review and Submit page after clicking on continue button on Bank Details Page");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc10");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_FIXED_AUTO,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_NEW_BANK);

    BankAccountDetailsPage bankAccountDetailsPage = new BankAccountDetailsPage();

    bankAccountDetailsPage.fillBankDetailsAndSubmit();

    Assert.assertEquals(paymentFlow.getCurrentStep(), PaymentFlow.STEP_REVIEW,
        "Should be on Review and Submit page");
  }

  @Test
  public void fapPmTc11() {
    Logger.info(
        "To validate that a PM is navigated to Review and Submit page after clicking on continue button on Card Details page");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc11");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_FIXED_AUTO,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_NEW_CREDIT);

    CardAccountDetailsPage cardAccountDetailsPage = new CardAccountDetailsPage();

    cardAccountDetailsPage.fillAndSubmitCardDetailsWithCardNum();

    Assert.assertEquals(paymentFlow.getCurrentStep(), PaymentFlow.STEP_REVIEW,
        "Should be on Review and Submit page");
  }

  @Test
  public void fapPmTc12() {
    Logger.info(
        "To validate that a PM is navigated to Fixed AutoPay List page after clicking on continue button");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc12");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_FIXED_AUTO,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_REVIEW);

    ReviewAndSubmitPage reviewAndSubmitPage = new ReviewAndSubmitPage();

    reviewAndSubmitPage.clickSubmitButton();

    PmFapListPage pmFapListPage = new PmFapListPage();

    Assert.assertTrue(pmFapListPage.pageIsLoaded(), "Should be on Fixed AutoPay list page");
  }

  @Test
  public void fapPmTc15() {
    Logger.info("To validate that Previous Button is not present in the Payment amount page");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc15");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_FIXED_AUTO,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_AMOUNT);

    PaymentAmountPage paymentAmountPage = new PaymentAmountPage();

    Assert.assertFalse(paymentAmountPage.isPreviousButtonPresent());
  }
}
