package com.paylease.app.qa.functional.tests.pm;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.features.PaymentFlow;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.paymentflow.BankAccountDetailsPage;
import com.paylease.app.qa.framework.pages.paymentflow.CardAccountDetailsPage;
import com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage;
import com.paylease.app.qa.framework.pages.paymentflow.ReviewAndSubmitPage;
import com.paylease.app.qa.framework.pages.paymentflow.SchedulePage;
import com.paylease.app.qa.framework.pages.paymentflow.SelectResidentPage;
import com.paylease.app.qa.framework.pages.pm.PmHomePage;
import com.paylease.app.qa.framework.pages.pm.PmMenu;
import com.paylease.app.qa.framework.pages.pm.PmPaymentHistoryPage;
import com.paylease.app.qa.framework.pages.pm.PmVapListPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class VariableAutoPayTest extends ScriptBase {

  private static final String REGION = "Pm";
  private static final String FEATURE = "createVap";

  //--------------------------------VARIABLE AUTOPAY TESTS------------------------------------------

  @Test
  public void vapPmTc1() {
    Logger.info(
        "To validate that a PM can navigate to Create Variable AutoPay from the Payments tab");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();

    PmHomePage pmHomePage = new PmHomePage();

    pmHomePage.open();

    PmMenu pmMenu = new PmMenu();

    pmMenu.clickCreateVariableAutoPayTab();

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM,
        PaymentFlow.SCHEDULE_VARIABLE_AUTO);

    Assert.assertEquals(paymentFlow.getCurrentStep(), PaymentFlow.STEP_SELECT_RESIDENT,
        "Select Resident page should load");
  }

  @Test
  public void vapPmTc2() {
    Logger.info(
        "To validate that a PM can navigate to Create Variable AutoPay from the Variable AutoPay List page");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc2");
    testSetupPage.open();

    PmVapListPage pmVapListPage = new PmVapListPage();

    pmVapListPage.open();
    pmVapListPage.clickCreateAutoPayButton();

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM,
        PaymentFlow.SCHEDULE_VARIABLE_AUTO);

    Assert.assertEquals(paymentFlow.getCurrentStep(), PaymentFlow.STEP_SELECT_RESIDENT,
        "Select Resident page should load");
  }

  @Test
  public void vapPmTc3() {
    Logger.info(
        "To validate that a PM is navigated to Payment schedule page after clicking Schedule AutoPay link");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc3");
    testSetupPage.open();
    final String residentId = testSetupPage.getString("residentId");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM,
        PaymentFlow.SCHEDULE_VARIABLE_AUTO);
    paymentFlow.openStep(PaymentFlow.STEP_SELECT_RESIDENT);

    SelectResidentPage selectResidentPage = new SelectResidentPage();

    selectResidentPage.clickToBeginPayment(residentId);

    Assert.assertEquals(paymentFlow.getCurrentStep(), PaymentFlow.STEP_SCHEDULE,
        "Should be on Payment Schedule page");
  }

  @Test
  public void vapPmTc5() {
    Logger.info("To validate that a PM can navigate to Create Variable AutoPay using resident ID");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc5");
    testSetupPage.open();
    final String residentId = testSetupPage.getString("residentId");

    PmPaymentHistoryPage pmPaymentHistoryPage = new PmPaymentHistoryPage();

    pmPaymentHistoryPage.openWithResidentId(residentId, true);
    pmPaymentHistoryPage.clickCreateVariableAutoPayButton();

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM,
        PaymentFlow.SCHEDULE_VARIABLE_AUTO);

    Assert.assertEquals(paymentFlow.getCurrentStep(), PaymentFlow.STEP_SCHEDULE,
        "Should be on Payment Schedule page");
  }

  @Test
  public void vapPmTc6() {
    Logger.info(
        "To validate that a PM is navigated to Payment Method page after clicking on continue button");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_VARIABLE_AUTO,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_SCHEDULE);

    SchedulePage schedulePage = new SchedulePage();

    schedulePage.fillAndSubmitPaymentScheduleDetails();

    Assert.assertEquals(paymentFlow.getCurrentStep(), PaymentFlow.STEP_METHOD,
        "Should be on Payment Method page");
  }

  @Test
  public void vapPmTc7() {
    Logger.info(
        "To validate that a PM is navigated to Bank details page after clicking on continue button");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc7");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_VARIABLE_AUTO,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_METHOD);

    PaymentMethodPage paymentMethodPage = new PaymentMethodPage();

    paymentMethodPage.selectPaymentMethod(PaymentMethodPage.NEW_BANK);
    paymentMethodPage.clickContinueButton();

    Assert.assertEquals(paymentFlow.getCurrentStep(), PaymentFlow.STEP_NEW_BANK,
        "Should be on Bank Details page");
  }

  @Test
  public void vapPmTc8() {
    Logger.info(
        "To validate that a PM is navigated to Card details page after clicking on continue button");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc8");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_VARIABLE_AUTO,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_METHOD);

    PaymentMethodPage paymentMethodPage = new PaymentMethodPage();

    paymentMethodPage.selectPaymentMethod(PaymentMethodPage.NEW_CREDIT);
    paymentMethodPage.clickContinueButton();

    Assert.assertEquals(paymentFlow.getCurrentStep(), PaymentFlow.STEP_NEW_CREDIT,
        "Should be on Card Details page");
  }

  @Test
  public void vapPmTc9() {
    Logger.info(
        "To validate that a PM is navigated to Review and Submit page after clicking on continue button on Bank Details Page");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc9");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_VARIABLE_AUTO,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_NEW_BANK);

    BankAccountDetailsPage bankAccountDetailsPage = new BankAccountDetailsPage();

    bankAccountDetailsPage.fillBankDetailsAndSubmit();

    Assert.assertEquals(paymentFlow.getCurrentStep(), PaymentFlow.STEP_REVIEW,
        "Should be on Review and Submit page");
  }

  @Test
  public void vapPmTc10() {
    Logger.info(
        "To validate that a PM is navigated to Review and Submit page after clicking on continue button on Card Details page");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc10");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_VARIABLE_AUTO,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_NEW_CREDIT);

    CardAccountDetailsPage cardAccountDetailsPage = new CardAccountDetailsPage();

    cardAccountDetailsPage.fillAndSubmitCardDetailsWithCardNum();

    Assert.assertEquals(paymentFlow.getCurrentStep(), PaymentFlow.STEP_REVIEW,
        "Should be on Review and Submit page");
  }

  @Test
  public void vapPmTc11() {
    Logger.info(
        "To validate that a PM is navigated to Variable AutoPay List page after clicking on continue button");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc11");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_VARIABLE_AUTO,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_REVIEW);

    ReviewAndSubmitPage reviewAndSubmitPage = new ReviewAndSubmitPage();

    reviewAndSubmitPage.clickSubmitButton();

    PmVapListPage pmVapListPage = new PmVapListPage();

    Assert.assertTrue(pmVapListPage.pageIsLoaded(), "Should be on Variable AutoPay list page");
  }
}
