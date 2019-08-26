package com.paylease.app.qa.manual.tests.integration;

import com.paylease.app.qa.api.tests.aapi.testcase.RdcTransactionCallback;
import com.paylease.app.qa.api.tests.gapi.testcase.AccountPayment;
import com.paylease.app.qa.api.tests.gapi.testcase.AchPayment;
import com.paylease.app.qa.api.tests.gapi.testcase.CcPayment;
import com.paylease.app.qa.framework.DataBaseConnector;
import com.paylease.app.qa.framework.DataHelper;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.api.Credentials;
import com.paylease.app.qa.framework.api.aapi.RdcRequest;
import com.paylease.app.qa.framework.api.gapi.GapiRequest;
import com.paylease.app.qa.framework.api.gapi.GapiResponse;
import com.paylease.app.qa.framework.components.Login;
import com.paylease.app.qa.framework.components.Login.UserType;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.utility.database.client.dao.TransactionDao;
import com.paylease.app.qa.framework.utility.database.client.dao.TransactionPaymentFieldDao;
import com.paylease.app.qa.framework.utility.database.client.dto.Transaction;
import com.paylease.app.qa.framework.utility.database.client.dto.TransactionPaymentField;
import com.paylease.app.qa.framework.utility.listeners.Retry;
import com.paylease.app.qa.framework.utility.sshtool.SshUtil;
import com.paylease.app.qa.testbase.PaymentBase;
import com.paylease.app.qa.testbase.dataproviders.IntegratedPmPaymentFieldsDataProvider;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import org.testng.Assert;
import org.testng.annotations.Test;

public class IntegratedPmPaymentFieldsTest extends ScriptBase {

  private static final String REGION = "Integration";
  private static final String FEATURE = "IntegratedPmPaymentFields";

  //--------------------------------ONE TIME PAYMENT FLOW TESTS-------------------------------------

  @Test(dataProvider = "otpDataIntegratedPm", dataProviderClass =
      IntegratedPmPaymentFieldsDataProvider.class, groups = {"manual"}, retryAnalyzer = Retry.class)
  public void otpPm(String testVariationNo, String testCase, String paymentType,
      boolean useResidentList, boolean expressPay, String integration) throws Exception {
    Logger.info("PM payment fields logging: " + testVariationNo + " with " + paymentType
        + " where resident list page being used is " + useResidentList
        + " and using express pay is " + expressPay + " and is integrated with " + integration);

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();
    final String pmEmail = testSetupPage.getString("pmEmail");
    final String residentId = testSetupPage.getString("residentId");

    ArrayList<String> paymentFieldList = new ArrayList<>();
    paymentFieldList.add(testSetupPage.getString("paymentField1Label"));
    paymentFieldList.add(testSetupPage.getString("paymentField2Label"));
    paymentFieldList.add(testSetupPage.getString("paymentField3Label"));
    Collections.sort(paymentFieldList);

    Login login = new Login();
    login.logInUser(pmEmail, UserType.PM);

    PaymentBase paymentBase = new PaymentBase();

    String transId = paymentBase.pmOtPaymentActions(paymentType, useResidentList, expressPay,
        residentId,
        paymentFieldList);
    TransactionPaymentFieldDao transactionPaymentFieldDao = new TransactionPaymentFieldDao();

    SshUtil sshUtil = new SshUtil();

    sshUtil.runIpnWorker();

    DataBaseConnector dataBaseConnector = new DataBaseConnector();

    dataBaseConnector.createConnection();
    Connection connection = dataBaseConnector.getConnection();

    ArrayList<TransactionPaymentField> transactionsList = transactionPaymentFieldDao
        .findByTransId(connection, Long.parseLong(transId), 50);
    dataBaseConnector.closeConnection();

    Assert.assertTrue(transactionsList.size() > 0, " Transaction list is empty");
    for (int i = 0; i < transactionsList.size(); i++) {
      String varName = transactionsList.get(i).getVarName();
      Assert.assertEquals(varName.replaceAll("_", " "), paymentFieldList.get(i).toLowerCase(),
          "Payment fields do not match");
    }

    dataBaseConnector.closeConnection();
  }

  @Test(dataProvider = "otpDataIntegratedPmGapi", dataProviderClass =
      IntegratedPmPaymentFieldsDataProvider.class, groups = {"manual"}, retryAnalyzer = Retry.class)
  public void gapiAchPaymentFieldsTransactionTest(String testVariationNo, String testCase,
      String transactionType) throws Exception {
    Logger.info("PM payment fields logging with GAPI: " + testVariationNo + "and Transaction Type"
        + transactionType);

    Long transId = null;

    switch (transactionType) {
      case "ACH":
      case "CC":
      case "AccountPayment":
        transId = Long.parseLong(createTransaction(testCase, transactionType));
        break;

      case "Check21":
        transId = Long.parseLong(createCheck21Transaction(testCase));
        break;

      case "Rdc":
        transId = Long.parseLong(createRdcTransaction(testCase));
        break;
    }

    TransactionPaymentFieldDao transactionPaymentFieldDao = new TransactionPaymentFieldDao();

    SshUtil sshUtil = new SshUtil();

    sshUtil.runIpnWorker();

    DataBaseConnector dataBaseConnector = new DataBaseConnector();

    dataBaseConnector.createConnection();
    Connection connection = dataBaseConnector.getConnection();

    ArrayList<TransactionPaymentField> transactionsList = transactionPaymentFieldDao
        .findByTransId(connection, transId, 50);
    dataBaseConnector.closeConnection();

    Assert.assertTrue(transactionsList.size() > 0, " Transaction list is empty");
    for (int i = 0; i < transactionsList.size(); i++) {
      Long transactionId = transactionsList.get(i).getTransactionId();
      Assert.assertEquals(transactionId, transId,
          "Transaction Id does not exist in the payment fields table");
    }

    dataBaseConnector.closeConnection();
  }

  //--------------------------------TEST METHOD-----------------------------------------------------

  private String createTransaction(String testCaseName, String transactionType) throws Exception {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCaseName);
    testSetupPage.open();

    // Credentials
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String payeeId = testSetupPage.getString("payeeId");
    final String payeeId1 = testSetupPage.getString("payeeId1");
    final String gatewayPayerId = testSetupPage.getString("gatewayPayerId");
    final String payerReferenceId = "GAPITesterCC";

    DataHelper dataHelper = new DataHelper();
    GapiRequest gapiRequest = new GapiRequest(new Credentials(gatewayId, username, password),
        "Production");

    switch (transactionType) {

      case "ACH": {
        AchPayment testCase = AchPayment.createValidAchPayment(
            dataHelper.getReferenceId(),
            "ACH transaction: " + testCaseName,
            null,
            payeeId).setPayerReferenceId(payerReferenceId)
            .setFeeAmount("0.00")
            .addDepositItem(payeeId, "100.00")
            .addDepositItem(payeeId1, "100.00")
            .setTotalAmount("200.00");

        testCase.addTransaction(gapiRequest);
        break;
      }

      case "CC": {
        CcPayment testCase = CcPayment.createValid(
            dataHelper.getReferenceId(),
            "CC transaction: " + testCaseName,
            null,
            payeeId).setPayerReferenceId(payerReferenceId)
            .setFeeAmount("0.00")
            .addDepositItem(payeeId, "100.00")
            .addDepositItem(payeeId1, "100.00")
            .setTotalAmount("200.00");

        testCase.addTransaction(gapiRequest);
        break;
      }

      default:
        AccountPayment testCase = AccountPayment.createValid(
            dataHelper.getReferenceId(),
            "Account Payment transaction: " + testCaseName,
            null,
            payeeId, gatewayPayerId).setPayerReferenceId(payerReferenceId)
            .setFeeAmount("0.00")
            .addDepositItem(payeeId, "100.00")
            .addDepositItem(payeeId1, "100.00")
            .setTotalAmount("200.00");
        testCase.addTransaction(gapiRequest);
        break;
    }

    GapiResponse response = gapiRequest.sendRequest();
    response.setIndex("1");

    return response.getSpecificElementValue("TransactionId");
  }

  private String createCheck21Transaction(String testCase) throws Exception {
    DataHelper dataHelper = new DataHelper();

    final String checkNum = dataHelper.getCheckNum();

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();

    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String payeeId = testSetupPage.getString("payeeId");

    GapiRequest gapiRequest = new GapiRequest(new Credentials(gatewayId, username, password));

    AchPayment testCases = AchPayment.createValidCheck21(dataHelper.getReferenceId(),
        "Check21 transaction", null, payeeId).setCheckNum(checkNum)
        .setIncludeImages();

    testCases.addTransaction(gapiRequest);

    GapiResponse response = gapiRequest.sendRequest();
    response.setIndex("1");

    return response.getSpecificElementValue("TransactionId");
  }

  private String createRdcTransaction(String testCase) throws Exception {
    DataHelper dataHelper = new DataHelper();

    final String checkNum = dataHelper.getCheckNum();
    final String amount = "113.36";

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();

    final String pmId = testSetupPage.getString("pmId");
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String propId = testSetupPage.getString("propId");
    final String tenantId = testSetupPage.getString("tenantId"); // user->user_id
    final String residentId = testSetupPage.getString("residentId"); // user->integration_user_id
    final String bankAccountId = testSetupPage.getString("bankAccountId");
    final String locationId = testSetupPage.getString("locationId");

    RdcRequest request = new RdcRequest(
        new Credentials(gatewayId, username, password), "Test", "wfs"
    );

    RdcTransactionCallback testCases = new RdcTransactionCallback("RDC transaction", null);

    String batchSequence = testCases
        .createBatch(pmId, locationId, "P" + bankAccountId, amount, "1589635972", "11000028");

    testCases.addCheckToBatch(
        batchSequence, propId, tenantId, residentId, "Harry",
        "Dresden", amount, "267376382670", checkNum, "51000017"
    );

    testCases.addTransaction(request);
    request.sendRequest();

    DataBaseConnector dataBaseConnector = new DataBaseConnector();
    dataBaseConnector.createConnection();
    Connection connection = dataBaseConnector.getConnection();

    TransactionDao transactionDao = new TransactionDao();
    Transaction transaction = transactionDao
        .findLatestByResidentAndPm(connection, Integer.parseInt(tenantId), Integer.parseInt(pmId));

    Assert.assertNotNull(transaction, "There should be a transaction");

    return String.valueOf(transaction.getTransactionId());
  }
}
