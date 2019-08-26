package com.paylease.app.qa.smoke.tests;

import static com.paylease.app.qa.framework.pages.paymentflow.CardAccountDetailsPage.CARD_TYPE_CREDIT_VALID_AMEX_LITLE;
import static com.paylease.app.qa.framework.pages.paymentflow.CardAccountDetailsPage.CARD_TYPE_CREDIT_VALID_DISCOVER_CHASE;
import static com.paylease.app.qa.framework.pages.paymentflow.CardAccountDetailsPage.CARD_TYPE_CREDIT_VALID_DISCOVER_LITLE;
import static com.paylease.app.qa.framework.pages.paymentflow.CardAccountDetailsPage.CARD_TYPE_CREDIT_VALID_MC_CHASE;
import static com.paylease.app.qa.framework.pages.paymentflow.CardAccountDetailsPage.CARD_TYPE_CREDIT_VALID_MC_LITLE;
import static com.paylease.app.qa.framework.pages.paymentflow.CardAccountDetailsPage.CARD_TYPE_CREDIT_VALID_VISA_CHASE;
import static com.paylease.app.qa.framework.pages.paymentflow.CardAccountDetailsPage.CARD_TYPE_CREDIT_VALID_VISA_LITLE;
import static com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage.NEW_CREDIT;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.features.PaymentFlow;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.resident.ResLoginPage;
import org.testng.annotations.Test;

public class CcPaymentTest extends ScriptBase {

  private static final String REGION = "resident";
  private static final String FEATURE = "otp";

  //-----------------------------------Login Tests----------------------------------------------
  @Test(groups = {"smoke", "chase"})
  public void ccTestTc01() {
    verifyCcPayment("cc_tc1", CARD_TYPE_CREDIT_VALID_VISA_CHASE);
  }

  @Test(groups = {"smoke", "chase"})
  public void ccTestTc02() {
    verifyCcPayment("cc_tc2", CARD_TYPE_CREDIT_VALID_MC_CHASE);
  }

  @Test(groups = {"smoke", "chase"})
  public void ccTestTc03() {
    verifyCcPayment("cc_tc3", CARD_TYPE_CREDIT_VALID_DISCOVER_CHASE);
  }

  @Test(groups = {"smoke", "litle"})
  public void ccTestTc04() {
    verifyCcPayment("cc_tc4", CARD_TYPE_CREDIT_VALID_VISA_LITLE);
  }

  @Test(groups = {"smoke", "litle"})
  public void ccTestTc05() {
    verifyCcPayment("cc_tc5", CARD_TYPE_CREDIT_VALID_MC_LITLE);
  }

  @Test(groups = {"smoke", "litle"})
  public void ccTestTc06() {
    verifyCcPayment("cc_tc6", CARD_TYPE_CREDIT_VALID_DISCOVER_LITLE);
  }

  @Test(groups = {"smoke", "litle"})
  public void ccTestTc07() {
    verifyCcPayment("cc_tc7", CARD_TYPE_CREDIT_VALID_AMEX_LITLE);
  }

  //------------------------------------Test Method----------------------------------------------

  private void verifyCcPayment(String testCase, String cardType) {
    Logger.info("Verify CC payment");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();
    final String residentEmail = testSetupPage.getString("residentEmail");

    ResLoginPage resLoginPage = new ResLoginPage();

    resLoginPage.open();
    resLoginPage.login(residentEmail, null);

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_RES, PaymentFlow.SCHEDULE_ONE_TIME);
    paymentFlow.addAmount("Lease Payment", "1");
    paymentFlow.setPaymentMethod(NEW_CREDIT);
    paymentFlow.setCardType(cardType);
    paymentFlow.openStep(PaymentFlow.STEP_AMOUNT);
    paymentFlow.advanceToStep(PaymentFlow.STEP_RECEIPT);
  }

}
