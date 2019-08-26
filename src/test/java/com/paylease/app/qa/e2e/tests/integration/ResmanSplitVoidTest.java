package com.paylease.app.qa.e2e.tests.integration;

import static com.paylease.app.qa.framework.features.PaymentFlow.SCHEDULE_ONE_TIME;
import static com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage.NEW_BANK;
import static com.paylease.app.qa.functional.tests.integration.ResmanSplitTransactionTest.SUCCESSFUL_INTEGRATION_STATUS;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.components.Login;
import com.paylease.app.qa.framework.components.Login.UserType;
import com.paylease.app.qa.framework.pages.admin.TransactionDetailPage;
import com.paylease.app.qa.framework.pages.admin.TransactionRefundPage;
import com.paylease.app.qa.framework.pages.automatedhelper.ResmanIpnPage;
import com.paylease.app.qa.framework.pages.automatedhelper.ResmanSendNotificationPage;
import com.paylease.app.qa.framework.pages.automatedhelper.ResmanTransactionPage;
import com.paylease.app.qa.framework.pages.automatedhelper.ResmanTransactionProcessingPage;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.paymentflow.ReceiptPage;
import com.paylease.app.qa.framework.pages.pm.PmPaymentHistoryPage;
import com.paylease.app.qa.framework.pages.resident.ResPaymentHistoryPage;
import com.paylease.app.qa.framework.pages.resident.ResidentMenuItems;
import com.paylease.app.qa.framework.utility.listeners.Retry;
import com.paylease.app.qa.testbase.PaymentBase;
import com.paylease.app.qa.testbase.dataproviders.ResmanSplitVoidDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ResmanSplitVoidTest extends ScriptBase {

  private static final String REGION = "integration";
  private static final String FEATURE = "resmanSplitTransaction";

  @Test(dataProvider = "resmanSplitVoid", dataProviderClass = ResmanSplitVoidDataProvider.class, groups = {
      "e2e"}, retryAnalyzer = Retry.class)
  public void resmanSplitVoidFlow(String testNum, String testCaseinfo, String testCase,
      String paymentType, String paymentUi, String voidLocation, String[] expectedResults) {
    Logger.info(testNum + " " + testCaseinfo);
    String[] otpInfo = new String[3];

    switch (paymentUi) {
      case "pm":
        otpInfo = pmOtp(testCase, paymentType);
        break;
      case "res":
        otpInfo = resOtp(testCase, paymentType);
        break;
      default:
        break;
    }

    Login login = new Login();
    switch (voidLocation) {
      case "pm":
        login.logInUser(otpInfo[2], UserType.PM);
        voidPm(otpInfo[0]);
        break;
      case "res":
        login.logInUser(otpInfo[1], UserType.RESIDENT);
        voidRes(otpInfo[0]);
        break;
      case "admin":
        login.logInAdmin();
        voidAdmin(otpInfo[0], paymentType);
        break;
      default:
        break;
    }

    sendNotification(otpInfo[0]);

    checkResmanTransTable(otpInfo[0], expectedResults);
  }

  private String[] pmOtp(String testCase, String paymentType) {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();
    String residentId = testSetupPage.getString("resId");
    String pmEmail = testSetupPage.getString("pmEmail");
    String resEmail = testSetupPage.getString("resEmail");
    Login login = new Login();
    login.logInUser(pmEmail, UserType.PM);

    PaymentBase paymentBase = new PaymentBase();

    paymentBase.selectResidentFromResidentListAndBeginPayment(residentId, SCHEDULE_ONE_TIME);

    String[] paymentFields = paymentBase.getPaymentFields(testCase);

    paymentBase.fillAndSubmitPaymentAmount(false, paymentFields);

    paymentBase.selectPaymentMethod(paymentType, false);
    paymentBase.reviewAndSubmit();

    ReceiptPage receiptPage = new ReceiptPage();
    String transId = receiptPage.getTransactionId();

    resmanIpn(transId);

    return new String[]{transId, resEmail, pmEmail};
  }

  private String[] resOtp(String testCase, String paymentType) {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();
    String pmEmail = testSetupPage.getString("pmEmail");
    String resEmail = testSetupPage.getString("resEmail");

    Login login = new Login();
    login.logInUser(resEmail, UserType.RESIDENT);

    PaymentBase paymentBase = new PaymentBase();
    ResidentMenuItems residentMenuItems = new ResidentMenuItems();

    String[] paymentFields = paymentBase.getPaymentFields(testCase);

    residentMenuItems.clickMakePaymentTab();
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

  private void sendNotification(String transId) {
    ResmanSendNotificationPage resmanSendNotificationPage = new ResmanSendNotificationPage();
    resmanSendNotificationPage.open();
    resmanSendNotificationPage.runResmanSendNotificationForTrans(transId);
  }

  private void voidPm(String transId) {
    PmPaymentHistoryPage pmPaymentHistoryPage = new PmPaymentHistoryPage();
    pmPaymentHistoryPage.open(true);
    if (pmPaymentHistoryPage.hasVoidLink(transId)) {
      pmPaymentHistoryPage.clickVoidLink(transId);
      pmPaymentHistoryPage.acceptVoidAlert();
    }
  }

  private void voidRes(String transId) {
    ResPaymentHistoryPage resPaymentHistoryPage = new ResPaymentHistoryPage();
    resPaymentHistoryPage.open();
    if (resPaymentHistoryPage.hasVoidLink(transId)) {
      resPaymentHistoryPage.clickVoidLink(transId);
      resPaymentHistoryPage.acceptVoidAlert();
    }
  }

  private void voidAdmin(String transId, String paymentType) {
    TransactionDetailPage transactionDetailPage = new TransactionDetailPage(transId);
    transactionDetailPage.open();
    if (NEW_BANK.equals(paymentType)) {
      transactionDetailPage.clickVoidLink();
      transactionDetailPage.acceptVoid();
    } else {
      TransactionRefundPage transactionRefundPage = transactionDetailPage.clickVoidRefund();
      transactionRefundPage.submitVoid();
    }
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
