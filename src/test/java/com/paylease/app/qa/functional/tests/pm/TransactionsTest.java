package com.paylease.app.qa.functional.tests.pm;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.UtilityManager;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.automatedhelper.TransactionPage;
import com.paylease.app.qa.framework.pages.automatedhelper.TransactionProcessingPage;
import com.paylease.app.qa.framework.pages.pm.PmPaymentHistoryPage;
import java.util.ArrayList;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TransactionsTest extends ScriptBase {

  public static final String REGION = "pm";
  public static final String FEATURE = "transactions";

  @Test
  public void validateGapiSplitPaymentFields() {
    Logger.info(
        "To validate the payment history page shows the split transaction payment fields and not the PayeeIds.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1255");
    testSetupPage.open();

    final String transId = testSetupPage.getString("transId");
    final String payment1VarName = testSetupPage.getString("payment1VarName");
    final String payment2VarName = testSetupPage.getString("payment2VarName");

    UtilityManager utilityManager = new UtilityManager();
    String expectedPayment1Label = utilityManager.unslugify(payment1VarName);
    String expectedPayment2Label = utilityManager.unslugify(payment2VarName);

    ArrayList<String> expectedPaymentLabels = new ArrayList<>();
    expectedPaymentLabels.add(expectedPayment1Label);
    expectedPaymentLabels.add(expectedPayment2Label);

    PmPaymentHistoryPage paymentHistoryPage = new PmPaymentHistoryPage();
    paymentHistoryPage.open(true);

    ArrayList<String> actualBillTypes = paymentHistoryPage.getBillTypes(transId);

    Assert.assertTrue(actualBillTypes.containsAll(expectedPaymentLabels),
        "Expected payment fields translated on history page.");
  }

  @Test
  public void validateGapiPaymentField() {
    Logger.info(
        "To validate the payment history page shows the transaction payment field and not the PayeeId.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1257");
    testSetupPage.open();

    final String transId = testSetupPage.getString("transId");
    final String paymentVarName = testSetupPage.getString("paymentVarName");

    UtilityManager utilityManager = new UtilityManager();
    String expectedPaymentLabel = utilityManager.unslugify(paymentVarName);

    PmPaymentHistoryPage paymentHistoryPage = new PmPaymentHistoryPage();
    paymentHistoryPage.open(true);

    ArrayList<String> actualBillTypes = paymentHistoryPage.getBillTypes(transId);

    Assert.assertTrue(actualBillTypes.contains(expectedPaymentLabel),
        "Expected payment field translated on history page.");
  }

  @Test
  public void refundEftGatewayTransTest() {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "refunds_tc1180");
    testSetupPage.open();

    final String transId = testSetupPage.getString("transId");

    PmPaymentHistoryPage pmPaymentHistoryPage = new PmPaymentHistoryPage();
    pmPaymentHistoryPage.open(true);
    pmPaymentHistoryPage.clickRefundLink(transId);
    pmPaymentHistoryPage.initiateRefund();
    pmPaymentHistoryPage.confirmRefund();

    TransactionPage transactionPage = new TransactionPage();
    transactionPage.open();
    TransactionProcessingPage transactionProcessingPage = transactionPage
        .getDataForTransactionId(transId);

    Assert.assertEquals("0", transactionProcessingPage.getGatewayPendingStatus(),
        "Pending Status should be reset to 0 after refund");
  }
}
