package com.paylease.app.qa.smoke.tests;

import static com.paylease.app.qa.framework.pages.paymentflow.BankAccountDetailsPage.CHECKING_ACCOUNT;
import static com.paylease.app.qa.framework.pages.paymentflow.BankAccountDetailsPage.SAVINGS_ACCOUNT;
import static com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage.NEW_BANK;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.features.PaymentFlow;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.resident.ResLoginPage;
import org.testng.annotations.Test;

public class AchPaymentTest extends ScriptBase {

  private static final String REGION = "resident";
  private static final String FEATURE = "otp";

  //-----------------------------------Login Tests----------------------------------------------
  @Test(groups = {"smoke"})
  public void achTestTc01() {
    verifyAchPayment("ach_tc1", CHECKING_ACCOUNT);
  }

  @Test(groups = {"smoke"})
  public void achTestTc02() {
    verifyAchPayment("ach_tc2", SAVINGS_ACCOUNT);
  }

  //------------------------------------Test Method----------------------------------------------

  private void verifyAchPayment(String testCase, String accountType) {
    Logger.info("Verify ACH Payment");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();
    final String residentEmail = testSetupPage.getString("residentEmail");

    ResLoginPage resLoginPage = new ResLoginPage();

    resLoginPage.open();
    resLoginPage.login(residentEmail, null);

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_RES, PaymentFlow.SCHEDULE_ONE_TIME);
    paymentFlow.addAmount("Lease Payment", "1");
    paymentFlow.setPaymentMethod(NEW_BANK);
    paymentFlow.setBankType(accountType);

    paymentFlow.openStep(PaymentFlow.STEP_AMOUNT);
    paymentFlow.advanceToStep(PaymentFlow.STEP_RECEIPT);
  }

}
