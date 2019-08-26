package com.paylease.app.qa.functional.tests.integration;

import static com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage.NEW_BANK;
import static com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage.NEW_CREDIT;
import static com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage.NEW_DEBIT;

import com.paylease.app.qa.framework.DataBaseConnector;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.features.PaymentFlow;
import com.paylease.app.qa.framework.pages.automatedhelper.AutoPaysPage;
import com.paylease.app.qa.framework.pages.automatedhelper.AutoPaysProcessingPage;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.paymentflow.CardAccountDetailsPage;
import com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage;
import com.paylease.app.qa.framework.pages.paymentflow.ReceiptPage;
import com.paylease.app.qa.framework.pages.paymentflow.ReviewAndSubmitPage;
import com.paylease.app.qa.framework.pages.pm.PmPaymentHistoryPage;
import com.paylease.app.qa.framework.pages.pm.PmPaymentHistoryReceiptPage;
import com.paylease.app.qa.framework.pages.resident.ResPaymentHistoryPage;
import com.paylease.app.qa.framework.pages.resident.ResPaymentHistoryReceiptPage;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AccTieredFeeStructureTest extends ScriptBase {

  private static final String REGION = "integration";
  private static final String FEATURE = "AccTieredFeeStructure";

  //---------------------------------------------RESIDENT UI TESTS-----------------------------

  @Test
  public void otpTc1Res() {
    verifyPaymentMethodFees(PaymentFlow.UI_RES, PaymentFlow.SCHEDULE_ONE_TIME, "otp_tc1_res",
        "transactionId", NEW_BANK, "3.95");
  }

  @Test
  public void otpTc2Res() {
    verifyPaymentMethodFees(PaymentFlow.UI_RES, PaymentFlow.SCHEDULE_ONE_TIME, "otp_tc2_res",
        "transactionId", NEW_BANK, "19.95");
  }

  @Test
  public void otpTc3Res() {
    verifyPaymentMethodFees(PaymentFlow.UI_RES, PaymentFlow.SCHEDULE_ONE_TIME, "otp_tc3_res",
        "transactionId", NEW_DEBIT, "5.95");
  }

  @Test
  public void otpTc4Res() {
    verifyPaymentMethodFees(PaymentFlow.UI_RES, PaymentFlow.SCHEDULE_ONE_TIME, "otp_tc4_res",
        "transactionId", NEW_DEBIT, "19.95");
  }

  @Test
  public void otpTc5Res() {
    verifyPaymentMethodFees(PaymentFlow.UI_RES, PaymentFlow.SCHEDULE_ONE_TIME, "otp_tc5_res",
        "transactionId", NEW_CREDIT, "19.95");
  }

  @Test
  public void otpTc7Res() {
    verifyFeesAppliedToCompletedPayment(PaymentFlow.UI_RES, "otp_tc7_res", NEW_BANK, "99.99",
        "3.95");
  }

  @Test
  public void otpTc8Res() {
    verifyFeesAppliedToCompletedPayment(PaymentFlow.UI_RES, "otp_tc8_res", NEW_BANK, "100.01",
        "19.95");
  }

  @Test(groups = {"litle"})
  public void otpTc9Res() {
    verifyFeesAppliedToCompletedPayment(PaymentFlow.UI_RES, "otp_tc9_res", NEW_DEBIT, "99.99",
        "5.95");
  }

  @Test(groups = {"litle"})
  public void otpTc10Res() {
    verifyFeesAppliedToCompletedPayment(PaymentFlow.UI_RES, "otp_tc10_res", NEW_DEBIT, "100.01",
        "19.95");
  }

  @Test(groups = {"litle"})
  public void otpTc11Res() {
    verifyFeesAppliedToCompletedPayment(PaymentFlow.UI_RES, "otp_tc11_res", NEW_CREDIT, "100.01",
        "19.95");
  }

  @Test
  public void otpTc25Res() {
    verifyPopUpMessageDebitCredit(PaymentFlow.UI_RES, PaymentFlow.SCHEDULE_ONE_TIME, "otp_tc25_res",
        "transactionId");
  }

  @Test
  public void otpTc26Res() {
    verifyPopUpMessageDebitCredit(PaymentFlow.UI_RES, PaymentFlow.SCHEDULE_ONE_TIME, "otp_tc26_res",
        "transactionId");
  }

  @Test(groups = {"litle"})
  public void otpTc27Res() {
    verifyCcTableOneTime(PaymentFlow.UI_RES, "otp_tc27_res", false);
  }

  @Test(groups = {"litle"})
  public void otpTc28Res() {
    verifyCcTableOneTime(PaymentFlow.UI_RES, "otp_tc28_res", true);
  }

  @Test
  public void otpTc29ResBank() {
    verifyFeeWaiver(PaymentFlow.UI_RES, "otp_tc29_res", NEW_BANK, false);
  }

  @Test(groups = {"litle"})
  public void otpTc29ResCc() {
    verifyFeeWaiver(PaymentFlow.UI_RES, "otp_tc29_res", NEW_CREDIT, false);
  }

  @Test(groups = {"litle"})
  public void otpTc29ResDc() {
    verifyFeeWaiver(PaymentFlow.UI_RES, "otp_tc29_res", NEW_DEBIT, false);
  }

  @Test
  public void otpTc30ResBank() {
    verifyFeeWaiver(PaymentFlow.UI_RES, "otp_tc30_res", NEW_BANK, false);
  }

  @Test(groups = {"litle"})
  public void otpTc30ResCc() {
    verifyFeeWaiver(PaymentFlow.UI_RES, "otp_tc30_res", NEW_CREDIT, false);
  }

  @Test(groups = {"litle"})
  public void otpTc30ResDc() {
    verifyFeeWaiver(PaymentFlow.UI_RES, "otp_tc30_res", NEW_DEBIT, false);
  }

  @Test
  public void otpTc31ResBank() {
    verifyFeeWaiver(PaymentFlow.UI_RES, "otp_tc31_res", NEW_BANK, false);
  }

  @Test(groups = {"litle"})
  public void otpTc31ResCc() {
    verifyFeeWaiver(PaymentFlow.UI_RES, "otp_tc31_res", NEW_CREDIT, false);
  }

  @Test(groups = {"litle"})
  public void otpTc31ResDc() {
    verifyFeeWaiver(PaymentFlow.UI_RES, "otp_tc31_res", NEW_DEBIT, false);
  }

  @Test
  public void otpTc32ResBank() {
    verifyFeeWaiver(PaymentFlow.UI_RES, "otp_tc32_res", NEW_BANK, true);
  }

  @Test(groups = {"litle"})
  public void otpTc32ResCc() {
    verifyFeeWaiver(PaymentFlow.UI_RES, "otp_tc32_res", NEW_CREDIT, true);
  }

  @Test(groups = {"litle"})
  public void otpTc32ResDc() {
    verifyFeeWaiver(PaymentFlow.UI_RES, "otp_tc32_res", NEW_DEBIT, true);
  }

  @Test
  public void faTc1Res() {
    verifyPaymentMethodFees(PaymentFlow.UI_RES, PaymentFlow.SCHEDULE_FIXED_AUTO, "fa_tc1_res",
        "transactionId", NEW_BANK, "1.00");
  }

  @Test
  public void faTc2Res() {
    verifyPaymentMethodFees(PaymentFlow.UI_RES, PaymentFlow.SCHEDULE_FIXED_AUTO, "fa_tc2_res",
        "transactionId", NEW_DEBIT, "5.95");
  }

  @Test
  public void faTc3Res() {
    verifyPaymentMethodFees(PaymentFlow.UI_RES, PaymentFlow.SCHEDULE_FIXED_AUTO, "fa_tc3_res",
        "transactionId", NEW_CREDIT, "19.95");
  }

  @Test
  public void faTc4Res() {
    verifyFapTransactionFeeInResUi("fa_tc4_res", NEW_BANK, "1.00");
  }

  @Test(groups = {"litle"})
  public void faTc5Res() {
    verifyFapTransactionFeeInResUi("fa_tc5_res", NEW_DEBIT, "5.95");
  }

  @Test(groups = {"litle"})
  public void faTc6Res() {
    verifyFapTransactionFeeInResUi("fa_tc6_res", NEW_CREDIT, "19.95");
  }

  @Test
  public void faTc9Res() {
    verifyPopUpMessageDebitCredit(PaymentFlow.UI_RES, PaymentFlow.SCHEDULE_FIXED_AUTO, "fa_tc9_res",
        "transactionId");
  }

  @Test
  public void vaTc1Res() {
    verifyPaymentMethodFees(PaymentFlow.UI_RES, PaymentFlow.SCHEDULE_VARIABLE_AUTO, "va_tc1_res",
        "autopayId", NEW_BANK, "1.00");
  }

  @Test
  public void vaTc2Res() {
    verifyPaymentMethodFees(PaymentFlow.UI_RES, PaymentFlow.SCHEDULE_VARIABLE_AUTO, "va_tc2_res",
        "autopayId", NEW_DEBIT, "5.95");
  }

  @Test
  public void vaTc3Res() {
    verifyPaymentMethodFees(PaymentFlow.UI_RES, PaymentFlow.SCHEDULE_VARIABLE_AUTO, "va_tc3_res",
        "autopayId", NEW_CREDIT, "19.95");
  }

  @Test
  public void vaTc4Res() {
    verifyVapTransactionFeeInResUi("va_tc4_res", NEW_BANK, "1.00");
  }

  @Test(groups = {"litle"})
  public void vaTc5Res() {
    verifyVapTransactionFeeInResUi("va_tc5_res", NEW_DEBIT, "5.95");
  }

  @Test(groups = {"litle"})
  public void vaTc6Res() {
    verifyVapTransactionFeeInResUi("va_tc6_res", NEW_CREDIT, "19.95");
  }

  @Test
  public void vaTc7Res() {
    verifyPopUpMessageDebitCredit(PaymentFlow.UI_RES, PaymentFlow.SCHEDULE_VARIABLE_AUTO,
        "va_tc7_res", "autopayId");
  }

  @Test(groups = {"litle"})
  public void vaTc8Res() {
    verifyAutopayTemplatesTable(PaymentFlow.UI_RES, "va_tc8_res", false);
  }

  @Test(groups = {"litle"})
  public void vaTc9Res() {
    verifyAutopayTemplatesTable(PaymentFlow.UI_RES, "va_tc9_res", true);
  }

  //---------------------------------------------PM UI TESTS------------------------------------

  @Test
  public void otpTc1Pm() {
    verifyPaymentMethodFees(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_ONE_TIME, "otp_tc1_pm",
        "transactionId", NEW_BANK, "3.95");
  }

  @Test
  public void otpTc2Pm() {
    verifyPaymentMethodFees(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_ONE_TIME, "otp_tc2_pm",
        "transactionId", NEW_BANK, "19.95");
  }

  @Test
  public void otpTc3Pm() {
    verifyPaymentMethodFees(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_ONE_TIME, "otp_tc3_pm",
        "transactionId", NEW_DEBIT, "5.95");
  }

  @Test
  public void otpTc4Pm() {
    verifyPaymentMethodFees(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_ONE_TIME, "otp_tc4_pm",
        "transactionId", NEW_DEBIT, "19.95");
  }

  @Test
  public void otpTc5Pm() {
    verifyPaymentMethodFees(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_ONE_TIME, "otp_tc5_pm",
        "transactionId", NEW_CREDIT, "19.95");
  }

  @Test
  public void otpTc7Pm() {
    verifyFeesAppliedToCompletedPayment(PaymentFlow.UI_PM, "otp_tc7_pm", NEW_BANK, "99.99", "3.95");
  }

  @Test
  public void otpTc8Pm() {
    verifyFeesAppliedToCompletedPayment(PaymentFlow.UI_PM, "otp_tc8_pm", NEW_BANK, "100.01",
        "19.95");
  }

  @Test(groups = {"litle"})
  public void otpTc9Pm() {
    verifyFeesAppliedToCompletedPayment(PaymentFlow.UI_PM, "otp_tc9_pm", NEW_DEBIT, "99.99",
        "5.95");
  }

  @Test(groups = {"litle"})
  public void otpTc10Pm() {
    verifyFeesAppliedToCompletedPayment(PaymentFlow.UI_PM, "otp_tc10_pm", NEW_DEBIT, "100.01",
        "19.95");
  }

  @Test(groups = {"litle"})
  public void otpTc11Pm() {
    verifyFeesAppliedToCompletedPayment(PaymentFlow.UI_PM, "otp_tc11_pm", NEW_CREDIT, "100.01",
        "19.95");
  }

  @Test
  public void otpTc25Pm() {
    verifyPopUpMessageDebitCredit(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_ONE_TIME, "otp_tc25_pm",
        "transactionId");
  }

  @Test
  public void otpTc26Pm() {
    verifyPopUpMessageDebitCredit(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_ONE_TIME, "otp_tc26_pm",
        "transactionId");
  }


  @Test(groups = {"litle"})
  public void otpTc27Pm() {
    verifyCcTableOneTime(PaymentFlow.UI_PM, "otp_tc27_pm", false);
  }

  @Test(groups = {"litle"})
  public void otpTc28Pm() {
    verifyCcTableOneTime(PaymentFlow.UI_PM, "otp_tc28_pm", true);
  }

  @Test
  public void otpTc29PmBank() {
    verifyFeeWaiver(PaymentFlow.UI_PM, "otp_tc29_pm", NEW_BANK, false);
  }

  @Test(groups = {"litle"})
  public void otpTc29PmCc() {
    verifyFeeWaiver(PaymentFlow.UI_PM, "otp_tc29_pm", NEW_CREDIT, false);
  }

  @Test(groups = {"litle"})
  public void otpTc29PmDc() {
    verifyFeeWaiver(PaymentFlow.UI_PM, "otp_tc29_pm", NEW_DEBIT, false);
  }

  @Test
  public void otpTc30PmBank() {
    verifyFeeWaiver(PaymentFlow.UI_PM, "otp_tc30_pm", NEW_BANK, false);
  }

  @Test(groups = {"litle"})
  public void otpTc30PmCc() {
    verifyFeeWaiver(PaymentFlow.UI_PM, "otp_tc30_pm", NEW_CREDIT, false);
  }

  @Test(groups = {"litle"})
  public void otpTc30PmDc() {
    verifyFeeWaiver(PaymentFlow.UI_PM, "otp_tc30_pm", NEW_DEBIT, false);
  }

  @Test
  public void otpTc31PmBank() {
    verifyFeeWaiver(PaymentFlow.UI_PM, "otp_tc31_pm", NEW_BANK, false);
  }

  @Test(groups = {"litle"})
  public void otpTc31PmCc() {
    verifyFeeWaiver(PaymentFlow.UI_PM, "otp_tc31_pm", NEW_CREDIT, false);
  }

  @Test(groups = {"litle"})
  public void otpTc31PmDc() {
    verifyFeeWaiver(PaymentFlow.UI_PM, "otp_tc31_pm", NEW_DEBIT, false);
  }

  @Test
  public void otpTc32PmBank() {
    verifyFeeWaiver(PaymentFlow.UI_PM, "otp_tc32_pm", NEW_BANK, true);
  }

  @Test(groups = {"litle"})
  public void otpTc32PmCc() {
    verifyFeeWaiver(PaymentFlow.UI_PM, "otp_tc32_pm", NEW_CREDIT, true);
  }

  @Test(groups = {"litle"})
  public void otpTc32PmDc() {
    verifyFeeWaiver(PaymentFlow.UI_PM, "otp_tc32_pm", NEW_DEBIT, true);
  }

  @Test
  public void faTc1Pm() {
    verifyPaymentMethodFees(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_FIXED_AUTO, "fa_tc1_pm",
        "transactionId", NEW_BANK, "1.00");
  }

  @Test
  public void faTc2Pm() {
    verifyPaymentMethodFees(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_FIXED_AUTO, "fa_tc2_pm",
        "transactionId", NEW_DEBIT, "5.95");
  }

  @Test
  public void faTc3Pm() {
    verifyPaymentMethodFees(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_FIXED_AUTO, "fa_tc3_pm",
        "transactionId", NEW_CREDIT, "19.95");
  }

  @Test
  public void faTc4Pm() {
    verifyFapTransactionFeeInPmUi("fa_tc4_pm", NEW_BANK, "1.00");
  }

  @Test(groups = {"litle"})
  public void faTc5Pm() {
    verifyFapTransactionFeeInPmUi("fa_tc5_pm", NEW_DEBIT, "5.95");
  }

  @Test(groups = {"litle"})
  public void faTc6Pm() {
    verifyFapTransactionFeeInPmUi("fa_tc6_pm", NEW_CREDIT, "19.95");
  }

  @Test
  public void faTc9Pm() {
    verifyPopUpMessageDebitCredit(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_FIXED_AUTO, "fa_tc9_pm",
        "transactionId");
  }

  @Test
  public void vaTc1Pm() {
    verifyPaymentMethodFees(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_VARIABLE_AUTO, "va_tc1_pm",
        "autopayId", NEW_BANK, "1.00");
  }

  @Test
  public void vaTc2Pm() {
    verifyPaymentMethodFees(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_VARIABLE_AUTO, "va_tc2_pm",
        "autopayId", NEW_DEBIT, "5.95");
  }

  @Test
  public void vaTc3Pm() {
    verifyPaymentMethodFees(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_VARIABLE_AUTO, "va_tc3_pm",
        "autopayId", NEW_CREDIT, "19.95");
  }

  @Test
  public void vaTc4Pm() {
    verifyVapTransactionFeeInPmUi("va_tc4_pm", NEW_BANK, "1.00");
  }

  @Test(groups = {"litle"})
  public void vaTc5Pm() {
    verifyVapTransactionFeeInPmUi("va_tc5_pm", NEW_DEBIT, "5.95");
  }

  @Test(groups = {"litle"})
  public void vaTc6Pm() {
    verifyVapTransactionFeeInPmUi("va_tc6_pm", NEW_CREDIT, "19.95");
  }

  @Test
  public void vaTc7Pm() {
    verifyPopUpMessageDebitCredit(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_VARIABLE_AUTO,
        "va_tc7_pm", "autopayId");
  }

  @Test(groups = {"litle"})
  public void vaTc8Pm() {
    verifyAutopayTemplatesTable(PaymentFlow.UI_PM, "va_tc8_pm", false);
  }

  @Test(groups = {"litle"})
  public void vaTc9Pm() {
    verifyAutopayTemplatesTable(PaymentFlow.UI_PM, "va_tc9_pm", true);
  }

  //---------------------------------------------GUEST UI TESTS-----------------------------

  @Test
  public void otpTc1Guest() {
    verifyPaymentMethodFees(PaymentFlow.UI_GUEST, PaymentFlow.SCHEDULE_ONE_TIME, "otp_tc1_guest",
        "transactionId", NEW_BANK, "3.95");
  }

  @Test
  public void otpTc2Guest() {
    verifyPaymentMethodFees(PaymentFlow.UI_GUEST, PaymentFlow.SCHEDULE_ONE_TIME, "otp_tc2_guest",
        "transactionId", NEW_BANK, "19.95");
  }

  @Test
  public void otpTc3Guest() {
    verifyPaymentMethodFees(PaymentFlow.UI_GUEST, PaymentFlow.SCHEDULE_ONE_TIME, "otp_tc3_guest",
        "transactionId", NEW_DEBIT, "5.95");
  }

  @Test
  public void otpTc4Guest() {
    verifyPaymentMethodFees(PaymentFlow.UI_GUEST, PaymentFlow.SCHEDULE_ONE_TIME, "otp_tc4_guest",
        "transactionId", NEW_DEBIT, "19.95");
  }

  @Test
  public void otpTc5Guest() {
    verifyPaymentMethodFees(PaymentFlow.UI_GUEST, PaymentFlow.SCHEDULE_ONE_TIME, "otp_tc5_guest",
        "transactionId", NEW_CREDIT, "19.95");
  }

  @Test
  public void otpTc7Guest() {
    verifyFeesAppliedToCompletedPayment(PaymentFlow.UI_RES, "otp_tc7_guest", NEW_BANK, "99.99",
        "3.95");
  }

  @Test
  public void otpTc8Guest() {
    verifyFeesAppliedToCompletedPayment(PaymentFlow.UI_RES, "otp_tc8_guest", NEW_BANK, "100.01",
        "19.95");
  }

  @Test(groups = {"litle"})
  public void otpTc9Guest() {
    verifyFeesAppliedToCompletedPayment(PaymentFlow.UI_RES, "otp_tc9_guest", NEW_DEBIT, "99.99",
        "5.95");
  }

  @Test(groups = {"litle"})
  public void otpTc10Guest() {
    verifyFeesAppliedToCompletedPayment(PaymentFlow.UI_RES, "otp_tc10_guest", NEW_DEBIT, "100.01",
        "19.95");
  }

  @Test(groups = {"litle"})
  public void otpTc11Guest() {
    verifyFeesAppliedToCompletedPayment(PaymentFlow.UI_RES, "otp_tc11_guest", NEW_CREDIT, "100.01",
        "19.95");
  }

  @Test
  public void otpTc25Guest() {
    verifyPopUpMessageDebitCredit(PaymentFlow.UI_GUEST, PaymentFlow.SCHEDULE_ONE_TIME,
        "otp_tc25_guest", "transactionId");
  }

  @Test
  public void otpTc26Guest() {
    verifyPopUpMessageDebitCredit(PaymentFlow.UI_GUEST, PaymentFlow.SCHEDULE_ONE_TIME,
        "otp_tc26_guest", "transactionId");
  }

  //-----------------------------------------TEST IMPLEMENTATION METHODS----------------------------

  private void verifyPaymentMethodFees(String ui, String schedule, String testCase,
      String paymentIdKey, String method, String feeAmount) {
    Logger.info("Verify " + method + " fee for " + schedule + " in " + ui + " UI");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();
    final String transactionId = testSetupPage.getString(paymentIdKey, "Payment ID");

    assertPaymentMethodHasFee(ui, schedule, transactionId, method, feeAmount);
  }

  private void verifyPopUpMessageDebitCredit(String ui, String schedule, String testCase,
      String paymentIdKey) {
    Logger.info("Verify Pop-up message in " + ui + " UI");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();
    final String transactionId = testSetupPage.getString(paymentIdKey, "Payment ID");

    assertCreditFeeConfirmedInDebitForm(ui, schedule, transactionId);
  }

  private void verifyCcTableOneTime(String ui, String testCase, boolean useCredit) {
    Logger.info("Verify CC Table in " + ui + " UI");

    String paymentMethod;
    String cardType;

    if (useCredit) {
      paymentMethod = PaymentMethodPage.NEW_CREDIT;
      cardType = CardAccountDetailsPage.CARD_TYPE_CREDIT_VALID;
    } else {
      paymentMethod = PaymentMethodPage.NEW_DEBIT;
      cardType = CardAccountDetailsPage.CARD_TYPE_DEBIT_VALID;
    }

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();
    final String residentId = testSetupPage.getString("residentId");
    final String transactionId = testSetupPage.getString("transactionId");

    PaymentFlow paymentFlow = new PaymentFlow(ui, PaymentFlow.SCHEDULE_ONE_TIME, transactionId);

    paymentFlow.setPaymentMethod(paymentMethod);
    paymentFlow.setCardType(cardType);
    paymentFlow.openStep(PaymentFlow.STEP_METHOD);
    paymentFlow.advanceToStep(PaymentFlow.STEP_REVIEW);

    verifyCcSavedInDb(transactionId, residentId, useCredit);
  }

  private void verifyFeeWaiver(String ui, String testCase, String paymentMethod, boolean hasFee) {
    String feesWaived = hasFee ? "Fees Waived" : "Fees Applied";
    Logger.info("Verify " + feesWaived + " for " + paymentMethod + " in " + ui + " UI");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");

    assertFeeWaivedForDesiredFields(ui, transactionId, paymentMethod, hasFee);
  }

  private void verifyFeesAppliedToCompletedPayment(String ui, String testCase, String paymentMethod,
      String paymentAmount, String feeAmount) {
    Logger.info("Verify " + paymentMethod + " + fee for One time payment in " + ui + " UI");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");

    assertFeeAmountAfterPaymentComplete(ui, transactionId, paymentMethod, paymentAmount, feeAmount);
  }

  private void verifyAutopayTemplatesTable(String ui, String testCase, boolean useCredit) {
    Logger.info("Verify Autopay templates table in " + ui + " UI");

    String paymentMethod;
    String cardType;

    if (useCredit) {
      paymentMethod = PaymentMethodPage.NEW_CREDIT;
      cardType = CardAccountDetailsPage.CARD_TYPE_CREDIT_VALID;
    } else {
      paymentMethod = PaymentMethodPage.NEW_DEBIT;
      cardType = CardAccountDetailsPage.CARD_TYPE_DEBIT_VALID;
    }

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();
    final String autopayId = testSetupPage.getString("autopayId");

    PaymentFlow paymentFlow = new PaymentFlow(ui, PaymentFlow.SCHEDULE_VARIABLE_AUTO, autopayId);
    paymentFlow.setPaymentMethod(paymentMethod);
    paymentFlow.setCardType(cardType);
    paymentFlow.openStep(PaymentFlow.STEP_METHOD);
    paymentFlow.advanceToStep(PaymentFlow.STEP_REVIEW);

    verifyCcSavedInDbVapTemplate(autopayId, useCredit);
  }

  //---------------------------------------------RESIDENT UI METHODS----------------------------

  private void verifyFapTransactionFeeInResUi(String testCase, String paymentMethod, String fee) {
    Logger.info("Verify FAP Transaction fee in Resident UI");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();
    final String autopayId = testSetupPage.getString("transactionId", "AutoPay template ID");
    final String amount = testSetupPage.getString("amount");
    final String startDate = testSetupPage.getString("startDate");

    double paymentAmountNum = Double.parseDouble(amount);
    double feeAmountNum = Double.parseDouble(fee);
    double total = paymentAmountNum + feeAmountNum;
    DecimalFormat formatter = new DecimalFormat("#,##0.00");

    String totalAmount = formatter.format(total);

    String cardType =
        paymentMethod.equals(NEW_CREDIT) ? CardAccountDetailsPage.CARD_TYPE_CREDIT_VALID
            : CardAccountDetailsPage.CARD_TYPE_DEBIT_VALID;

    String newTransactionId = completeAndProcessAutopay(PaymentFlow.UI_RES,
        PaymentFlow.SCHEDULE_FIXED_AUTO, cardType, autopayId, paymentMethod);

    ResPaymentHistoryPage resPaymentHistoryPage = new ResPaymentHistoryPage();

    resPaymentHistoryPage.open();
    resPaymentHistoryPage.enterDateAndSubmit(startDate);

    ResPaymentHistoryReceiptPage residentPaymentHistoryReceiptPage = resPaymentHistoryPage
        .clickDetails(newTransactionId);

    Assert.assertTrue(residentPaymentHistoryReceiptPage.getProcessingFeeAmount().equals("$" + fee),
        "The fee should be " + fee + " in Receipt page");

    Assert.assertTrue(residentPaymentHistoryReceiptPage.getTotalAmount().equals("$" + totalAmount),
        "The " + totalAmount + " is incorrect in Receipt page");
  }

  private void verifyVapTransactionFeeInResUi(String testCase, String paymentMethod, String fee) {
    Logger.info("Verify VAP Transaction fee in Resident UI");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();
    final String autopayId = testSetupPage.getString("autopayId", "AutoPay template ID");
    final String amount = testSetupPage.getString("amount");

    double paymentA = Double.parseDouble(amount);
    double feeA = Double.parseDouble(fee);
    double total = paymentA + feeA;
    DecimalFormat formatter = new DecimalFormat("#,##0.00");

    String totalAmount = formatter.format(total);

    String cardType =
        paymentMethod.equals(NEW_CREDIT) ? CardAccountDetailsPage.CARD_TYPE_CREDIT_VALID
            : CardAccountDetailsPage.CARD_TYPE_DEBIT_VALID;

    String newTransactionId = completeAndProcessAutopay(PaymentFlow.UI_RES,
        PaymentFlow.SCHEDULE_VARIABLE_AUTO, cardType, autopayId, paymentMethod);

    ResPaymentHistoryPage resPaymentHistoryPage = new ResPaymentHistoryPage();

    resPaymentHistoryPage.open();

    ResPaymentHistoryReceiptPage residentPaymentHistoryReceiptPage = resPaymentHistoryPage
        .clickDetails(newTransactionId);

    Assert.assertTrue(residentPaymentHistoryReceiptPage.getProcessingFeeAmount().equals("$" + fee),
        "The fee should be " + fee + " in Receipt page");

    Assert.assertTrue(residentPaymentHistoryReceiptPage.getTotalAmount().equals("$" + totalAmount),
        "The " + totalAmount + " is incorrect in Receipt page");
  }

  //---------------------------------------------PM UI METHODS-----------------------------------

  private void verifyFapTransactionFeeInPmUi(String testCase, String paymentMethod, String fee) {
    Logger.info("Verify FAP Transaction fee in PM UI");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();
    final String autopayId = testSetupPage.getString("transactionId", "AutoPay template ID");
    final String amount = testSetupPage.getString("amount");
    final String startDate = testSetupPage.getString("startDate");

    double paymentA = Double.parseDouble(amount);
    double feeA = Double.parseDouble(fee);
    double total = paymentA + feeA;
    DecimalFormat formatter = new DecimalFormat("#,##0.00");

    String totalAmount = formatter.format(total);

    String cardType =
        paymentMethod.equals(NEW_CREDIT) ? CardAccountDetailsPage.CARD_TYPE_CREDIT_VALID
            : CardAccountDetailsPage.CARD_TYPE_DEBIT_VALID;

    String newTransactionId = completeAndProcessAutopay(PaymentFlow.UI_PM,
        PaymentFlow.SCHEDULE_FIXED_AUTO, cardType, autopayId, paymentMethod);

    PmPaymentHistoryPage pmPaymentHistoryPage = new PmPaymentHistoryPage();

    pmPaymentHistoryPage.open(true);
    pmPaymentHistoryPage.enterDateAndSubmit(startDate);

    PmPaymentHistoryReceiptPage pmPaymentHistoryReceiptPage = pmPaymentHistoryPage
        .clickTransactionNumber(newTransactionId);

    Assert.assertTrue(pmPaymentHistoryReceiptPage.getProcessingFeeAmount().equals("$" + fee),
        "The fee should be " + fee + " in Receipt page");

    Assert.assertTrue(pmPaymentHistoryReceiptPage.getTotalAmount().equals("$" + totalAmount),
        "The " + totalAmount + " is incorrect in Receipt page");
  }

  private void verifyVapTransactionFeeInPmUi(String testCase, String paymentMethod, String fee) {
    Logger.info("Verify VAP Transaction fee in PM UI");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();
    final String autopayId = testSetupPage.getString("autopayId", "AutoPay template Id");
    final String amount = testSetupPage.getString("amount");

    double paymentA = Double.parseDouble(amount);
    double feeA = Double.parseDouble(fee);
    double total = paymentA + feeA;
    DecimalFormat formatter = new DecimalFormat("#,##0.00");

    String totalAmount = formatter.format(total);

    String cardType =
        paymentMethod.equals(NEW_CREDIT) ? CardAccountDetailsPage.CARD_TYPE_CREDIT_VALID
            : CardAccountDetailsPage.CARD_TYPE_DEBIT_VALID;

    String newTransactionId = completeAndProcessAutopay(PaymentFlow.UI_PM,
        PaymentFlow.SCHEDULE_VARIABLE_AUTO, cardType, autopayId, paymentMethod);

    PmPaymentHistoryPage pmPaymentHistoryPage = new PmPaymentHistoryPage();

    pmPaymentHistoryPage.open(true);

    PmPaymentHistoryReceiptPage pmPaymentHistoryReceiptPage = pmPaymentHistoryPage
        .clickTransactionNumber(newTransactionId);

    Assert.assertTrue(pmPaymentHistoryReceiptPage.getProcessingFeeAmount().equals("$" + fee),
        "The fee should be " + fee + " in Receipt page");

    Assert.assertTrue(pmPaymentHistoryReceiptPage.getTotalAmount().equals("$" + totalAmount),
        "The " + totalAmount + " is incorrect in Receipt page");
  }

  //---------------------------------PAYMENT FLOW METHODS-------------------------------------------
  private void assertPaymentMethodHasFee(String ui, String schedule, String paymentId,
      String method, String feeAmount) {
    PaymentFlow paymentFlow = new PaymentFlow(ui, schedule, paymentId);

    paymentFlow.openStep(PaymentFlow.STEP_METHOD);

    PaymentMethodPage paymentMethodPage = new PaymentMethodPage();
    Assert.assertTrue(paymentMethodPage.getFeeAmount(method).contains(feeAmount),
        "The fee should be " + feeAmount);
  }

  private void assertCreditFeeConfirmedInDebitForm(String ui, String schedule, String paymentId) {
    PaymentFlow paymentFlow = new PaymentFlow(ui, schedule, paymentId);

    paymentFlow.openStep(PaymentFlow.STEP_NEW_DEBIT);

    CardAccountDetailsPage cardAccountDetailsPage = new CardAccountDetailsPage();
    cardAccountDetailsPage.prepCardType(CardAccountDetailsPage.CARD_TYPE_CREDIT_VALID);
    cardAccountDetailsPage.fillCardDetails();
    cardAccountDetailsPage.clickContinueButtonPopUp();

    Assert.assertTrue(cardAccountDetailsPage.getConfirmCardTypeText().contains("19.95"),
        "Pop should ask to confirm Credit Card Fee");
  }

  private void assertFeeAmountAfterPaymentComplete(String ui, String transactionId,
      String paymentMethod, String paymentAmount, String feeAmount) {
    String cardType =
        paymentMethod.equals(NEW_CREDIT) ? CardAccountDetailsPage.CARD_TYPE_CREDIT_VALID
            : CardAccountDetailsPage.CARD_TYPE_DEBIT_VALID;

    double paymentAmountNum = Double.parseDouble(paymentAmount);
    double feeAmountNum = Double.parseDouble(feeAmount);
    double total = paymentAmountNum + feeAmountNum;
    DecimalFormat formatter = new DecimalFormat("#,##0.00");

    String totalAmount = formatter.format(total);

    PaymentFlow paymentFlow = new PaymentFlow(ui, PaymentFlow.SCHEDULE_ONE_TIME,
        transactionId);
    paymentFlow.setPaymentMethod(paymentMethod);
    paymentFlow.setCardType(cardType);

    paymentFlow.openStep(PaymentFlow.STEP_METHOD);
    paymentFlow.advanceToStep(PaymentFlow.STEP_REVIEW);

    ReviewAndSubmitPage reviewAndSubmitPage = new ReviewAndSubmitPage();
    Assert.assertTrue(reviewAndSubmitPage.getFeeAmount().equals("$" + feeAmount),
        "The fee should " + feeAmount + " in Review and Submit page");

    Assert.assertTrue(reviewAndSubmitPage.getTotalAmount().equals("$" + totalAmount),
        "The " + totalAmount + " is incorrect in Review and Submit page");

    paymentFlow.advanceToStep(PaymentFlow.STEP_RECEIPT);
    ReceiptPage receiptPage = new ReceiptPage();

    Assert.assertTrue(receiptPage.getProcessingFeeAmount().equals("$" + feeAmount),
        "The fee should be " + feeAmount + " in Receipt page");

    Assert.assertTrue(receiptPage.getTotalAmount().equals("$" + totalAmount),
        "The " + totalAmount + " is incorrect in Receipt page");
  }

  private void assertFeeWaivedForDesiredFields(String ui, String transactionId,
      String paymentMethod, boolean hasFee) {
    String cardType =
        paymentMethod.equals(NEW_CREDIT) ? CardAccountDetailsPage.CARD_TYPE_CREDIT_VALID
            : CardAccountDetailsPage.CARD_TYPE_DEBIT_VALID;

    PaymentFlow paymentFlow = new PaymentFlow(ui, PaymentFlow.SCHEDULE_ONE_TIME,
        transactionId);
    paymentFlow.setPaymentMethod(paymentMethod);
    paymentFlow.setCardType(cardType);

    paymentFlow.openStep(PaymentFlow.STEP_METHOD);

    PaymentMethodPage paymentMethodPage = new PaymentMethodPage();
    Assert.assertEquals(!hasFee, paymentMethodPage.getFeeAmount(paymentMethod).contains("FREE"),
        "The fee should " + (hasFee ? "not " : "") + "be Free");

    paymentFlow.advanceToStep(PaymentFlow.STEP_REVIEW);

    ReviewAndSubmitPage reviewAndSubmitPage = new ReviewAndSubmitPage();
    Assert.assertEquals(hasFee, reviewAndSubmitPage.isFeeTextPresent(),
        "Fee text should " + (hasFee ? "" : "not ") + "be present in Review and Submit page");

    paymentFlow.advanceToStep(PaymentFlow.STEP_RECEIPT);

    ReceiptPage receiptPage = new ReceiptPage();
    Assert.assertEquals(hasFee, receiptPage.isProcessingFeeTextPresent(),
        "Fee text should " + (hasFee ? "" : "not ") + "be present in Receipt page");
  }

  private String completeAndProcessAutopay(String ui, String schedule, String cardType,
      String autopayId, String paymentMethod) {
    PaymentFlow paymentFlow = new PaymentFlow(ui, schedule, autopayId);
    paymentFlow.setPaymentMethod(paymentMethod);
    paymentFlow.setCardType(cardType);
    paymentFlow.openStep(PaymentFlow.STEP_METHOD);
    paymentFlow.advanceToStep(PaymentFlow.STEP_RECEIPT);

    AutoPaysPage autoPaysPage = new AutoPaysPage();

    autoPaysPage.open();

    String autopayType = schedule.equals(PaymentFlow.SCHEDULE_FIXED_AUTO) ? AutoPaysPage.FIXED
        : AutoPaysPage.VARIABLE;
    AutoPaysProcessingPage autoPaysProcessingPage = autoPaysPage
        .runAutoPay(autopayType, autopayId);

    return autoPaysProcessingPage.getTransactionId();
  }

  //-------------------------------------DATABASE CHECKS--------------------------------------------
  private void verifyCcSavedInDb(String transactionId, String residentId, boolean useCredit) {
    boolean isCardSaved = false;
    String sqlQuery = "SELECT cc.user_id, cc.is_debit_card "
        + "FROM cc "
        + "INNER JOIN transactions t ON t.payment_type_id = cc.cc_id "
        + "WHERE t.trans_id = " + transactionId;

    DataBaseConnector dataBaseConnector = new DataBaseConnector();
    dataBaseConnector.createConnection();

    ResultSet assertQuery = dataBaseConnector.executeSqlQuery(sqlQuery);

    try {
      while (assertQuery.next()) {
        isCardSaved = true;
        String expectedValue = useCredit ? "NO" : "YES";
        Assert.assertTrue(assertQuery.getString("user_id").equals(residentId));
        Assert.assertTrue(assertQuery.getString("is_debit_card").equals(expectedValue));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    Assert.assertTrue(isCardSaved, "Query returned as null");

    dataBaseConnector.closeConnection();
  }

  private void verifyCcSavedInDbVapTemplate(String autopayId, boolean useCredit) {
    boolean isAutopay = false;
    String sqlQuery = "SELECT is_debit_card "
        + "FROM autopay_templates "
        + "WHERE autopay_id = " + autopayId;

    DataBaseConnector dataBaseConnector = new DataBaseConnector();
    dataBaseConnector.createConnection();

    ResultSet assertQuery = dataBaseConnector.executeSqlQuery(sqlQuery);

    try {
      while (assertQuery.next()) {
        isAutopay = true;
        String expectedValue = useCredit ? "NO" : "YES";
        Assert.assertTrue(assertQuery.getString("is_debit_card").equals(expectedValue));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    Assert.assertTrue(isAutopay, "Query returned as null");

    dataBaseConnector.closeConnection();
  }
}