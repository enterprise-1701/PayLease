package com.paylease.app.qa.functional.tests.integration;

import static com.paylease.app.qa.framework.features.PaymentFlow.SCHEDULE_ONE_TIME;
import static com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage.NEW_BANK;
import static com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage.NEW_CREDIT;
import static com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage.NEW_DEBIT;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.components.Login;
import com.paylease.app.qa.framework.components.Login.UserType;
import com.paylease.app.qa.framework.pages.admin.TransactionDetailPage;
import com.paylease.app.qa.framework.pages.automatedhelper.ResmanIpnPage;
import com.paylease.app.qa.framework.pages.automatedhelper.ResmanSendNotificationPage;
import com.paylease.app.qa.framework.pages.automatedhelper.ResmanTransactionPage;
import com.paylease.app.qa.framework.pages.automatedhelper.ResmanTransactionProcessingPage;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.paymentflow.ReceiptPage;
import com.paylease.app.qa.framework.pages.pm.PmPaymentHistoryPage;
import com.paylease.app.qa.testbase.PaymentBase;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ResmanSplitTransactionTest extends ScriptBase {

  private static final String REGION = "integration";
  private static final String FEATURE = "resmanSplitTransaction";

  public static final String DEPOSIT_TRANS = "DEPOSIT";
  public static final String PAYMENT_TRANS = "PAYMENT";
  public static final String RETURN_DEPOSIT_TRANS = "RETURN_DEPOSIT";
  public static final String RETURN_PAYMENT_TRANS = "RETURN";
  public static final String REVERSAL_DEPOSIT_TRANS = "REVERSAL_DEPOSIT";
  public static final String REVERSAL_PAYMENT_TRANS = "REVERSAL";

  public static final String SUCCESSFUL_INTEGRATION_STATUS = "SUCCESS";

  //----------------------------------SPLIT TRANS---------------------------------------------------

  @Test
  public void tc2534() {
    Logger.info(
        "Verify that split transactions are split in the Resman Transactions table if the payment fields are pointing to different 'ledgers'");
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc2534");
    testSetupPage.open();
    final String transId = testSetupPage.getString("transId");

    String[] expectedNotificationType = {PAYMENT_TRANS, DEPOSIT_TRANS};

    checkResmanTransTable(transId, expectedNotificationType);
  }

  @Test
  public void tc2535Payment() {
    Logger.info(
        "Verify that a split transaction to the Payment notification type should appear as 1 trans in Resman Transactions Table");
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc2535Payment");
    testSetupPage.open();
    final String transId = testSetupPage.getString("transId");

    String[] expectedNotificationType = {PAYMENT_TRANS};

    checkResmanTransTable(transId, expectedNotificationType);
  }

  @Test
  public void tc2535Deposit() {
    Logger.info(
        "Verify that a split transaction to the Deposit notification type should appear as 1 trans in Resman Transactions Table");
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc2535Deposit");
    testSetupPage.open();
    final String transId = testSetupPage.getString("transId");

    String[] expectedNotificationType = {DEPOSIT_TRANS};

    checkResmanTransTable(transId, expectedNotificationType);
  }

  @Test
  public void tc2535TwoPaymentOneDeposit() {
    Logger.info(
        "Verify that a split transaction with 2 Payment and 1 Deposit notification type should appear as 2 trans in Resman Transactions Table");
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc2535TwoPaymentOneDeposit");
    testSetupPage.open();
    final String transId = testSetupPage.getString("transId");

    String[] expectedNotificationType = {PAYMENT_TRANS, DEPOSIT_TRANS};

    checkResmanTransTable(transId, expectedNotificationType);
  }

  @Test
  public void tc2535OnePaymentTwoDeposit() {
    Logger.info(
        "Verify that a split transaction with 1 Payment and 2 Deposit notification type should appear as 2 trans in Resman Transactions Table");
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc2535OnePaymentTwoDeposit");
    testSetupPage.open();
    final String transId = testSetupPage.getString("transId");

    String[] expectedNotificationType = {PAYMENT_TRANS, DEPOSIT_TRANS};

    checkResmanTransTable(transId, expectedNotificationType);
  }

  @Test
  public void tc2535TwoEach() {
    Logger.info(
        "Verify that a split transaction with 2 Deposit and 2 Payment notification type should appear as 2 trans in Resman Transactions Table");
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc2535TwoEach");
    testSetupPage.open();
    final String transId = testSetupPage.getString("transId");

    String[] expectedNotificationType = {PAYMENT_TRANS, DEPOSIT_TRANS};

    checkResmanTransTable(transId, expectedNotificationType);
  }

  //-------------------------------SPLIT TRANS FULL FLOW--------------------------------------------
  @Test
  public void fullFlowOnePaymentOneDepositDc() {
    String[] expectedResults = {PAYMENT_TRANS, DEPOSIT_TRANS};

    String[] otpInfo = pmOtp("fullFlowOnePaymentOneDeposit", NEW_DEBIT);
    checkResmanTransTable(otpInfo[0], expectedResults);
  }

  @Test
  public void fullFlowTwoPaymentDc() {
    String[] expectedResults = {PAYMENT_TRANS};

    String[] otpInfo = pmOtp("fullFlowTwoPayment", NEW_DEBIT);
    checkResmanTransTable(otpInfo[0], expectedResults);
  }

  @Test
  public void fullFlowTwoDepositDc() {
    String[] expectedResults = {DEPOSIT_TRANS};

    String[] otpInfo = pmOtp("fullFlowTwoDeposit", NEW_DEBIT);
    checkResmanTransTable(otpInfo[0], expectedResults);
  }

  @Test
  public void fullFlowTwoPaymentOneDepositDc() {
    String[] expectedResults = {PAYMENT_TRANS, DEPOSIT_TRANS};

    String[] otpInfo = pmOtp("fullFlowTwoPaymentOneDeposit", NEW_DEBIT);
    checkResmanTransTable(otpInfo[0], expectedResults);
  }

  @Test
  public void fullFlowOnePaymentTwoDepositDc() {
    String[] expectedResults = {PAYMENT_TRANS, DEPOSIT_TRANS};

    String[] otpInfo = pmOtp("fullFlowOnePaymentTwoDeposit", NEW_DEBIT);
    checkResmanTransTable(otpInfo[0], expectedResults);
  }

  @Test
  public void fullFlowTwoPaymentTwoDepositDc() {
    String[] expectedResults = {PAYMENT_TRANS, DEPOSIT_TRANS};

    String[] otpInfo = pmOtp("fullFlowTwoPaymentTwoDeposit", NEW_DEBIT);
    checkResmanTransTable(otpInfo[0], expectedResults);
  }

  @Test
  public void fullFlowOnePaymentOneDepositBank() {
    String[] expectedResults = {PAYMENT_TRANS, DEPOSIT_TRANS};

    String[] otpInfo = pmOtp("fullFlowOnePaymentOneDeposit", NEW_BANK);
    checkResmanTransTable(otpInfo[0], expectedResults);
  }

  @Test
  public void fullFlowTwoPaymentBank() {
    String[] expectedResults = {PAYMENT_TRANS};

    String[] otpInfo = pmOtp("fullFlowTwoPayment", NEW_BANK);
    checkResmanTransTable(otpInfo[0], expectedResults);
  }

  @Test
  public void fullFlowDepositNotificationTypeBank() {
    String[] expectedResults = {DEPOSIT_TRANS};

    String[] otpInfo = pmOtp("fullFlowTwoDeposit", NEW_BANK);
    checkResmanTransTable(otpInfo[0], expectedResults);
  }

  @Test
  public void fullFlowTwoPaymentOneDepositBank() {
    String[] expectedResults = {PAYMENT_TRANS, DEPOSIT_TRANS};

    String[] otpInfo = pmOtp("fullFlowTwoPaymentOneDeposit", NEW_BANK);
    checkResmanTransTable(otpInfo[0], expectedResults);
  }

  @Test
  public void fullFlowOnePaymentTwoDepositBank() {
    String[] expectedResults = {PAYMENT_TRANS, DEPOSIT_TRANS};

    String[] otpInfo = pmOtp("fullFlowOnePaymentTwoDeposit", NEW_BANK);
    checkResmanTransTable(otpInfo[0], expectedResults);
  }

  @Test
  public void fullFlowTwoPaymentTwoDepositBank() {
    String[] expectedResults = {PAYMENT_TRANS, DEPOSIT_TRANS};

    String[] otpInfo = pmOtp("fullFlowTwoPaymentTwoDeposit", NEW_DEBIT);
    checkResmanTransTable(otpInfo[0], expectedResults);
  }

  @Test
  public void fullFlowOnePaymentOneDepositCc() {
    String[] expectedResults = {PAYMENT_TRANS, DEPOSIT_TRANS};

    String[] otpInfo = pmOtp("fullFlowOnePaymentOneDeposit", NEW_CREDIT);
    checkResmanTransTable(otpInfo[0], expectedResults);
  }

  @Test
  public void fullFlowTwoPaymentCc() {
    String[] expectedResults = {PAYMENT_TRANS};

    String[] otpInfo = pmOtp("fullFlowTwoPayment", NEW_CREDIT);
    checkResmanTransTable(otpInfo[0], expectedResults);
  }

  @Test
  public void fullFlowDepositNotificationTypeCc() {

    String[] expectedResults = {DEPOSIT_TRANS};

    String[] otpInfo = pmOtp("fullFlowTwoDeposit", NEW_CREDIT);
    checkResmanTransTable(otpInfo[0], expectedResults);
  }

  @Test
  public void fullFlowTwoPaymentOneDepositCc() {
    String[] expectedResults = {PAYMENT_TRANS, DEPOSIT_TRANS};

    String[] otpInfo = pmOtp("fullFlowTwoPaymentOneDeposit", NEW_CREDIT);
    checkResmanTransTable(otpInfo[0], expectedResults);
  }

  @Test
  public void fullFlowOnePaymentTwoDepositCc() {
    String[] expectedResults = {PAYMENT_TRANS, DEPOSIT_TRANS};

    String[] otpInfo = pmOtp("fullFlowOnePaymentTwoDeposit", NEW_CREDIT);
    checkResmanTransTable(otpInfo[0], expectedResults);
  }

  @Test
  public void fullFlowTwoPaymentTwoDepositCc() {
    String[] expectedResults = {PAYMENT_TRANS, DEPOSIT_TRANS};

    String[] otpInfo = pmOtp("fullFlowTwoPaymentTwoDeposit", NEW_CREDIT);
    checkResmanTransTable(otpInfo[0], expectedResults);
  }

  //--------------------------------SPLIT REVERSAL--------------------------------------------------

  @Test
  public void tc4043() {
    Logger.info("Void split 2 payment 2 deposit transaction from PM UI");
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc4043");
    testSetupPage.open();
    final String transId = testSetupPage.getString("transId");

    voidPm(transId);

    sendNotification(transId);

    String[] expectedResults = {PAYMENT_TRANS, RETURN_PAYMENT_TRANS, DEPOSIT_TRANS,
        RETURN_DEPOSIT_TRANS};

    checkResmanTransTable(transId, expectedResults);
  }

  @Test
  public void tc4055Payment() {
    Logger.info("Void 2 Payment from PM UI");
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc4055Payment");
    testSetupPage.open();
    final String transId = testSetupPage.getString("transId");

    voidPm(transId);

    sendNotification(transId);

    String[] expectedResults = {PAYMENT_TRANS, RETURN_PAYMENT_TRANS};

    checkResmanTransTable(transId, expectedResults);
  }

  @Test
  public void tc4055Deposit() {
    Logger.info("Void 2 Deposit from PM UI");
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc4055Deposit");
    testSetupPage.open();
    final String transId = testSetupPage.getString("transId");

    voidPm(transId);

    sendNotification(transId);

    String[] expectedResults = {DEPOSIT_TRANS, RETURN_DEPOSIT_TRANS};

    checkResmanTransTable(transId, expectedResults);
  }

  @Test
  public void tc4044() {
    Logger.info("Void 2 deposit and 2 payment split");
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc4044");
    testSetupPage.open();
    final String transId = testSetupPage.getString("transId");

    voidAdmin(transId);

    sendNotification(transId);

    String[] expectedResults = {PAYMENT_TRANS, RETURN_PAYMENT_TRANS, DEPOSIT_TRANS,
        RETURN_DEPOSIT_TRANS};

    checkResmanTransTable(transId, expectedResults);
  }

  @Test
  public void tc3621() {
    Logger.info("2 deposit and 2 payment return split");
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc3621");
    testSetupPage.open();
    final String transId = testSetupPage.getString("transId");

    sendNotification(transId);

    String[] expectedResults = {PAYMENT_TRANS,
        RETURN_PAYMENT_TRANS, DEPOSIT_TRANS, RETURN_DEPOSIT_TRANS};

    checkResmanTransTable(transId, expectedResults);
  }

  @Test
  public void tc4054() {
    Logger.info("2 deposit and 2 payment refund split");
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc4054");
    testSetupPage.open();
    final String transId = testSetupPage.getString("transId");

    refundPm(transId);

    sendNotification(transId);

    String[] expectedResults = {PAYMENT_TRANS,
        REVERSAL_PAYMENT_TRANS, DEPOSIT_TRANS, REVERSAL_DEPOSIT_TRANS};

    checkResmanTransTable(transId, expectedResults);
  }

  @Test
  public void tc4161() {
    Logger.info("Send Notification sends out transactions that fail to integrate");
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc4161");
    testSetupPage.open();
    final String transId = testSetupPage.getString("transId");

    sendNotification(transId);

    String[] expectedNotificationTypes = {"PAYMENT", "DEPOSIT"};

    checkResmanTransTable(transId, expectedNotificationTypes);
  }

  //---------------------------------TEST METHODS---------------------------------------------------

  private String[] pmOtp(String testCase, String paymentType) {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();
    final String residentId = testSetupPage.getString("resId");
    final String pmEmail = testSetupPage.getString("pmEmail");
    final String resEmail = testSetupPage.getString("resEmail");

    Login login = new Login();

    login.logInUser(pmEmail, UserType.PM);

    PaymentBase paymentBase = new PaymentBase();
    String[] paymentFields = paymentBase.getPaymentFields(testCase);

    paymentBase.selectResidentFromResidentListAndBeginPayment(residentId, SCHEDULE_ONE_TIME);

    paymentBase.fillAndSubmitPaymentAmount(false, paymentFields);

    paymentBase.selectPaymentMethod(paymentType, false);
    paymentBase.reviewAndSubmit();

    ReceiptPage receiptPage = new ReceiptPage();
    String transId = receiptPage.getTransactionId();

    resmanIpn(transId);

    return new String[]{transId, resEmail, pmEmail};
  }

  private void resmanIpn(String transId) {
    ResmanIpnPage resmanIpnPage = new ResmanIpnPage();
    resmanIpnPage.open();
    resmanIpnPage.runResmanIpnForTrans(transId);
  }

  private void voidPm(String transId) {
    PmPaymentHistoryPage pmPaymentHistoryPage = new PmPaymentHistoryPage();
    pmPaymentHistoryPage.open(true);
    if (pmPaymentHistoryPage.hasVoidLink(transId)) {
      pmPaymentHistoryPage.clickVoidLink(transId);
      pmPaymentHistoryPage.acceptVoidAlert();
    }
  }

  private void refundPm(String transId) {
    PmPaymentHistoryPage pmPaymentHistoryPage = new PmPaymentHistoryPage();
    pmPaymentHistoryPage.open(true);
    pmPaymentHistoryPage.clickRefundLink(transId);
    pmPaymentHistoryPage.initiateRefund();
    pmPaymentHistoryPage.clickRefund();
  }

  private void voidAdmin(String transId) {
    TransactionDetailPage transactionDetailPage = new TransactionDetailPage(transId);
    transactionDetailPage.open();
    if (transactionDetailPage.hasVoidLink()) {
      transactionDetailPage.clickVoidLink();
      transactionDetailPage.acceptVoid();
    }
  }

  private void sendNotification(String transId) {
    ResmanSendNotificationPage resmanSendNotificationPage = new ResmanSendNotificationPage();
    resmanSendNotificationPage.open();
    resmanSendNotificationPage.runResmanSendNotificationForTrans(transId);
  }

  private void checkResmanTransTable(String transId, String[] expectedNotificationType) {
    ResmanTransactionPage resmanTransactionPage = new ResmanTransactionPage();
    resmanTransactionPage.open();

    ResmanTransactionProcessingPage resmanTransactionProcessingPage = resmanTransactionPage
        .getDataForTransactionId(transId);
    int i = 0;
    for (String expectedNotification : expectedNotificationType) {
      String notificationType = resmanTransactionProcessingPage
          .getNotificationType(transId + "_" + i);
      Assert.assertEquals(notificationType, expectedNotification,
          "Notification Type did not match expected");
      String status = resmanTransactionProcessingPage.getStatus(transId + "_" + i);
      Assert.assertEquals(SUCCESSFUL_INTEGRATION_STATUS, status, "Status should be SUCCESS");

      i++;
    }
    String numOfTrans = resmanTransactionProcessingPage.getResmanTransCount();
    String expectedNumOfTrans = Integer.toString(expectedNotificationType.length);

    Assert.assertEquals(numOfTrans, expectedNumOfTrans,
        "Num of trans should be " + expectedNumOfTrans);
  }
}
