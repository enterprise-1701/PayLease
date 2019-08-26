package com.paylease.app.qa.functional.tests.resident;

import static com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage.NEW_BANK;
import static com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage.NEW_CREDIT;
import static com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage.NEW_DEBIT;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.features.PaymentFlow;
import com.paylease.app.qa.framework.pages.automatedhelper.AutoPaysPage;
import com.paylease.app.qa.framework.pages.automatedhelper.AutoPaysProcessingPage;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.automatedhelper.TransactionPage;
import com.paylease.app.qa.framework.pages.automatedhelper.TransactionProcessingPage;
import com.paylease.app.qa.framework.pages.paymentflow.CardAccountDetailsPage;
import com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage;
import com.paylease.app.qa.framework.pages.paymentflow.ReceiptPage;
import com.paylease.app.qa.framework.pages.paymentflow.ReviewAndSubmitPage;
import com.paylease.app.qa.framework.pages.resident.ResPaymentHistoryPage;
import com.paylease.app.qa.framework.pages.resident.ResPaymentHistoryReceiptPage;
import java.text.DecimalFormat;
import java.util.HashMap;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TieredFeeStructureTest extends ScriptBase {

  private static final String REGION = "resident";
  private static final String FEATURE = "TieredFeeStructure";

  //---------------------------------------------RESIDENT UI TESTS-----------------------------

  @Test
  public void otpTc33ResBank() {
    verifyFeeWaiver(PaymentFlow.UI_RES, "otp_tc33_res", NEW_BANK, false);
  }

  @Test(groups = {"litle"})
  public void otpTc33ResCc() {
    verifyFeeWaiver(PaymentFlow.UI_RES, "otp_tc33_res", NEW_CREDIT, false);
  }

  @Test(groups = {"litle"})
  public void otpTc33ResDc() {
    verifyFeeWaiver(PaymentFlow.UI_RES, "otp_tc33_res", NEW_DEBIT, false);
  }

  @Test(groups = {"litle"})
  public void otpTc34ResDc() {
    verifyFeeWaiverInDb(PaymentFlow.UI_RES, "otp_tc34_res", NEW_DEBIT, false);
  }

  @Test(groups = {"litle"})
  public void otpTc35ResDc() {
    verifyFeeWaiverInDb(PaymentFlow.UI_RES, "otp_tc35_res", NEW_DEBIT, false);
  }

  @Test(groups = {"litle"})
  public void otpTc36ResCc() {
    verifyFeeWaiverInDb(PaymentFlow.UI_RES, "otp_tc36_res", NEW_CREDIT, false);
  }

  @Test
  public void otpTc37ResBank() {
    verifyFeeWaiverInDb(PaymentFlow.UI_RES, "otp_tc37_res", NEW_BANK, false);
  }

  @Test
  public void otpTc38ResBank() {
    verifyFeeWaiverInDb(PaymentFlow.UI_RES, "otp_tc38_res", NEW_BANK, false);
  }

  @Test
  public void faTc10ResBank() {
    verifyPaymentMethodFees(PaymentFlow.UI_RES, PaymentFlow.SCHEDULE_FIXED_AUTO, "fa_tc10_res",
        "transactionId", NEW_BANK, "FREE");
  }

  @Test
  public void faTc10ResCc() {
    verifyPaymentMethodFees(PaymentFlow.UI_RES, PaymentFlow.SCHEDULE_FIXED_AUTO, "fa_tc10_res",
        "transactionId", NEW_CREDIT, "FREE");
  }

  @Test
  public void faTc10ResDc() {
    verifyPaymentMethodFees(PaymentFlow.UI_RES, PaymentFlow.SCHEDULE_FIXED_AUTO, "fa_tc10_res",
        "transactionId", NEW_DEBIT, "FREE");
  }

  @Test
  public void faTc10TransFeeResBank() {
    verifyFapTransactionFeeInResUi("fa_tc10_res", NEW_BANK, "0.00");
  }

  @Test(groups = {"litle"})
  public void faTc10TransFeeResCc() {
    verifyFapTransactionFeeInResUi("fa_tc10_res", NEW_CREDIT, "0.00");
  }

  @Test(groups = {"litle"})
  public void faTc10TransFeeResDc() {
    verifyFapTransactionFeeInResUi("fa_tc10_res", NEW_DEBIT, "0.00");
  }

  @Test
  public void vaTc10ResBank() {
    verifyPaymentMethodFees(PaymentFlow.UI_RES, PaymentFlow.SCHEDULE_VARIABLE_AUTO, "va_tc10_res",
        "autopayId", NEW_BANK, "FREE");
  }

  @Test
  public void vaTc10ResCc() {
    verifyPaymentMethodFees(PaymentFlow.UI_RES, PaymentFlow.SCHEDULE_VARIABLE_AUTO, "va_tc10_res",
        "autopayId", NEW_CREDIT, "FREE");
  }

  @Test
  public void vaTc10ResDc() {
    verifyPaymentMethodFees(PaymentFlow.UI_RES, PaymentFlow.SCHEDULE_VARIABLE_AUTO, "va_tc10_res",
        "autopayId", NEW_DEBIT, "FREE");
  }

  @Test
  public void vaTc10TransFeeResBank() {
    verifyVapTransactionFeeInResUi("va_tc10_res", NEW_BANK, "0.00");
  }

  @Test(groups = {"litle"})
  public void vaTc10TransFeeResDc() {
    verifyVapTransactionFeeInResUi("va_tc10_res", NEW_DEBIT, "0.00");
  }

  @Test(groups = {"litle"})
  public void vaTc10TransFeeResCc() {
    verifyVapTransactionFeeInResUi("va_tc10_res", NEW_CREDIT, "0.00");
  }

  //---------------------------------------------GUEST UI TESTS-----------------------------

  @Test(groups = {"litle"})
  public void otpTc34GuestDc() {
    verifyFeeWaiverInDb(PaymentFlow.UI_GUEST, "otp_tc34_guest", NEW_DEBIT, false);
  }

  @Test(groups = {"litle"})
  public void otpTc35GuestDc() {
    verifyFeeWaiverInDb(PaymentFlow.UI_GUEST, "otp_tc35_guest", NEW_DEBIT, false);
  }

  @Test(groups = {"litle"})
  public void otpTc36GuestCc() {
    verifyFeeWaiverInDb(PaymentFlow.UI_GUEST, "otp_tc36_guest", NEW_CREDIT, false);
  }

  @Test
  public void otpTc37GuestBank() {
    verifyFeeWaiverInDb(PaymentFlow.UI_GUEST, "otp_tc37_guest", NEW_BANK, false);
  }

  @Test(groups = {"litle"})
  public void otpTc38GuestBank() {
    verifyFeeWaiverInDb(PaymentFlow.UI_GUEST, "otp_tc38_guest", NEW_BANK, false);
  }

  @Test
  public void otpTc39GuestBank() {
    verifyPaymentMethodFees(PaymentFlow.UI_GUEST, PaymentFlow.SCHEDULE_ONE_TIME, "otp_tc39_guest",
        "transactionId", NEW_BANK, "FREE");
  }

  @Test
  public void otpTc39GuestCc() {
    verifyPaymentMethodFees(PaymentFlow.UI_GUEST, PaymentFlow.SCHEDULE_ONE_TIME, "otp_tc39_guest",
        "transactionId", NEW_CREDIT, "FREE");
  }

  @Test
  public void otpTc39GuestDc() {
    verifyPaymentMethodFees(PaymentFlow.UI_GUEST, PaymentFlow.SCHEDULE_ONE_TIME, "otp_tc39_guest",
        "transactionId", NEW_DEBIT, "FREE");
  }

  @Test
  public void otpTc39FeeAppGuestBank() {
    verifyFeesAppliedToCompletedPayment(PaymentFlow.UI_RES, "otp_tc39_guest", NEW_BANK, "99.99",
        "0.00");
  }

  @Test(groups = {"litle"})
  public void otpTc39FeeAppGuestCc() {
    verifyFeesAppliedToCompletedPayment(PaymentFlow.UI_RES, "otp_tc39_guest", NEW_CREDIT, "99.99",
        "0.00");
  }

  @Test(groups = {"litle"})
  public void otpTc39FeeAppGuestDc() {
    verifyFeesAppliedToCompletedPayment(PaymentFlow.UI_RES, "otp_tc39_guest", NEW_DEBIT, "99.99",
        "0.00");
  }

  //----------------------------------------------TEST METHODS-----------------------------------

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

    if (!fee.equals("0.00")) {
      Assert
          .assertTrue(residentPaymentHistoryReceiptPage.getProcessingFeeAmount().equals("$" + fee),
              "The fee should be " + fee + " in Receipt page");
    } else {
      Assert.assertTrue(!residentPaymentHistoryReceiptPage.isFeeShown(), "No Fee should be shown");
    }

    Assert.assertTrue(residentPaymentHistoryReceiptPage.getTotalAmount().equals("$" + totalAmount),
        "The " + totalAmount + " is incorrect in Receipt page");
  }

  private void verifyFeesAppliedToCompletedPayment(String ui, String testCase, String paymentMethod,
      String paymentAmount, String feeAmount) {
    Logger.info("Verify " + paymentMethod + " + fee for One time payment in " + ui + " UI");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");

    assertFeeAmountAfterPaymentComplete(ui, transactionId, paymentMethod, paymentAmount, feeAmount);
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

    if (!feeAmount.equals("0.00")) {
      Assert.assertTrue(reviewAndSubmitPage.getFeeAmount().equals("$" + feeAmount),
          "The fee should " + feeAmount + " in Review and Submit page");
    } else {
      Assert.assertTrue(!reviewAndSubmitPage.isFeeTextPresent(), "Fee should not be shown");
    }

    Assert.assertTrue(reviewAndSubmitPage.getTotalAmount().equals("$" + totalAmount),
        "The " + totalAmount + " is incorrect in Review and Submit page");

    paymentFlow.advanceToStep(PaymentFlow.STEP_RECEIPT);
    ReceiptPage receiptPage = new ReceiptPage();

    if (!feeAmount.equals("0.00")) {
      Assert.assertTrue(receiptPage.getProcessingFeeAmount().equals("$" + feeAmount),
          "The fee should be " + feeAmount + " in Receipt page");
    }
    Assert.assertTrue(receiptPage.getTotalAmount().equals("$" + totalAmount),
        "The " + totalAmount + " is incorrect in Receipt page");
  }

  private void assertPaymentMethodHasFee(String ui, String schedule, String paymentId,
      String method, String feeAmount) {
    PaymentFlow paymentFlow = new PaymentFlow(ui, schedule, paymentId);

    paymentFlow.openStep(PaymentFlow.STEP_METHOD);

    PaymentMethodPage paymentMethodPage = new PaymentMethodPage();
    Assert.assertTrue(paymentMethodPage.getFeeAmount(method).contains(feeAmount),
        "The fee should be " + feeAmount);
  }

  private void verifyFeeWaiver(String ui, String testCase, String paymentMethod, boolean hasFee) {
    String feesWaived = hasFee ? "Fees Waived" : "Fees Applied";
    Logger.info("Verify " + feesWaived + " for " + paymentMethod + " in " + ui + " UI");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");

    assertFeeWaivedForDesiredFields(ui, transactionId, paymentMethod, hasFee);
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

  private void verifyFeeWaiverInDb(String ui, String testCase, String paymentMethod,
      boolean hasFee) {
    String feesWaived = hasFee ? "Fees Waived" : "Fees Applied";
    Logger.info("Verify " + feesWaived + " for " + paymentMethod + " in " + ui + " UI in db");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");
    final String feeValue = testSetupPage.getString("feeValue");

    assertFeeWaivedInDB(ui, transactionId, paymentMethod, hasFee, feeValue);
  }

  private void assertFeeWaivedInDB(String ui, String transactionId,
      String paymentMethod, boolean hasFee, String feeValue) {
    String cardType =
        paymentMethod.equals(NEW_CREDIT) ? CardAccountDetailsPage.CARD_TYPE_CREDIT_VALID
            : CardAccountDetailsPage.CARD_TYPE_DEBIT_VALID;

    PaymentFlow paymentFlow = new PaymentFlow(ui, PaymentFlow.SCHEDULE_ONE_TIME,
        transactionId);
    paymentFlow.setPaymentMethod(paymentMethod);
    paymentFlow.setCardType(cardType);

    paymentFlow.openStep(PaymentFlow.STEP_METHOD);

    paymentFlow.advanceToStep(PaymentFlow.STEP_REVIEW);

    feeValue = feeValue.substring(0, feeValue.length() - 2) + "." + feeValue
        .substring(feeValue.length() - 2);

    HashMap<String, String> transactionFees = getFeesForTransaction(transactionId);

    HashMap<String, String> expectedResults = new HashMap<>();

    if (hasFee) {
      expectedResults.put("paylease_fee", feeValue);
      expectedResults.put("PM_pay_fee", "false");
      expectedResults.put("pm_fee_amount", "0.00");
    } else {
      expectedResults.put("paylease_fee", "0.00");
      expectedResults.put("PM_pay_fee", "true");
      expectedResults.put("pm_fee_amount", feeValue);
    }

    final boolean payleaseFee = transactionFees.get("paylease_fee")
        .equals(expectedResults.get("paylease_fee"));
    final boolean PM_pay_fee = transactionFees.get("PM_pay_fee")
        .equals(expectedResults.get("PM_pay_fee"));
    final boolean pm_fee_amount = transactionFees.get("pm_fee_amount")
        .equals(expectedResults.get("pm_fee_amount"));

    Assert.assertTrue((payleaseFee && PM_pay_fee && pm_fee_amount), "Fee is not right");
  }

  private void verifyPaymentMethodFees(String ui, String schedule, String testCase,
      String paymentIdKey, String method, String feeAmount) {
    Logger.info("Verify " + method + " fee for " + schedule + " in " + ui + " UI");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();
    String transactionId = testSetupPage.getString(paymentIdKey, "Payment ID");

    assertPaymentMethodHasFee(ui, schedule, transactionId, method, feeAmount);
  }

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

    if (!fee.equals("0.00")) {
      Assert
          .assertTrue(residentPaymentHistoryReceiptPage.getProcessingFeeAmount().equals("$" + fee),
              "The fee should be " + fee + " in Receipt page");
    } else {
      Assert.assertTrue(!residentPaymentHistoryReceiptPage.isFeeShown(), "No Fee should be shown");
    }

    Assert.assertTrue(residentPaymentHistoryReceiptPage.getTotalAmount().equals("$" + totalAmount),
        "The " + totalAmount + " is incorrect in Receipt page");
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

  private HashMap<String, String> getFeesForTransaction(String transactionId) {
    TransactionPage transactionPage = new TransactionPage();
    transactionPage.open();
    transactionPage.getDataForTransactionId(transactionId);

    TransactionProcessingPage transactionProcessingPage = new TransactionProcessingPage();
    HashMap<String, String> transactionFees = new HashMap<>();
    transactionFees.put("paylease_fee", transactionProcessingPage.getPayleaseFee());
    transactionFees.put("PM_pay_fee", transactionProcessingPage.getPmPayFee());
    transactionFees.put("pm_fee_amount", transactionProcessingPage.getPmFeeAmount());

    return transactionFees;
  }
}
