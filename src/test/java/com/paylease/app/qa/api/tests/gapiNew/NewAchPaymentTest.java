package com.paylease.app.qa.api.tests.gapiNew;

import static com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest.TEST_MODE;
import static com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest.URL;
import static com.paylease.app.qa.framework.newApi.newGapi.AchPaymentTransaction.ACH_PAYMENT_TRANSACTION;

import com.paylease.app.qa.framework.DataHelper;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.newApi.Credentials;
import com.paylease.app.qa.framework.newApi.Deposit;
import com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest;
import com.paylease.app.qa.framework.newApi.Response;
import com.paylease.app.qa.framework.newApi.SplitDeposit;
import com.paylease.app.qa.framework.newApi.Transaction;
import com.paylease.app.qa.framework.newApi.Transactions;
import com.paylease.app.qa.framework.newApi.XmlHelper;
import com.paylease.app.qa.framework.newApi.newGapi.AchPaymentTransaction;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.utility.listeners.Retry;
import com.paylease.app.qa.framework.utility.restclienttool.RestClientUtil;
import com.paylease.app.qa.testbase.dataproviders.AchPaymentDataProvider;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.testng.annotations.Test;

public class NewAchPaymentTest extends ScriptBase {

  private static final String REGION = "gapi";
  private static final String FEATURE = "AchPayment";


  @Test(dataProvider = "achPaymentXmlInjection", dataProviderClass = AchPaymentDataProvider.class,
      retryAnalyzer = Retry.class)
  public void achPaymentXmlInjection(String testVariationNum, String paymentReferenceId,
      String payerReferenceID,
      String paymentTraceID, String payerSecondaryRefId, String accountNumber,
      String creditReportingId, String currencyCode, String accountType, boolean splitDeposit,
      String feeAmount, String incurFee, String saveAccount, String message, String checkScanned,
      String check21, String checkNum, boolean checkDate, boolean imageFront, boolean imageBack,
      String auxOnUs, String payerFirstName, String payerLastName, String accountFullName,
      String routingNumber, String elementToCheck, String expectedValue, String expectedCode,
      String expectedStatus) {

    Logger.info(testVariationNum
        + " Verify that AchPayment GAPI action is not vulnerable to XML Injection");
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();

    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String payeeId = testSetupPage.getString("payeeId");

    Credentials credentials = new Credentials();
    credentials.setGatewayId(gatewayId);
    credentials.setUsername(username);
    credentials.setPassword(password);

    PayLeaseGatewayRequest payleaseGatewayRequest = buildRequestObject(paymentReferenceId,
        payerReferenceID, paymentTraceID, payerSecondaryRefId, accountNumber, creditReportingId,
        currencyCode, accountType, splitDeposit, feeAmount, incurFee, saveAccount, message,
        checkScanned, check21, checkNum, checkDate, imageFront, imageBack, auxOnUs, credentials,
        payeeId, payerFirstName, payerLastName, accountFullName, routingNumber);

    StringEntity payleaseGatewayRequestEntity = XmlHelper
        .marshalRequestObjectToXmlString(payleaseGatewayRequest);

    RestClientUtil restClientUtil = new RestClientUtil();
    HttpResponse httpResponse = XmlHelper
        .sendGapiRequest(restClientUtil, payleaseGatewayRequestEntity, URL);

    Response response = new Response();
    response
        .validatePartOfGapiResponse(restClientUtil, httpResponse, elementToCheck, expectedValue,
            expectedCode, expectedStatus, false);
  }

  //------------------------------------------Test Methods------------------------------------------

  private PayLeaseGatewayRequest buildRequestObject(String paymentReferenceId,
      String payerReferenceId, String paymentTraceID, String payerSecondaryRefId,
      String accountNumber, String creditReportingId, String currencyCode, String accountType,
      boolean splitDeposit, String feeAmount, String incurFee, String saveAccount, String message,
      String checkScanned, String check21,
      String checkNum, boolean checkDate, boolean imageFront, boolean imageBack, String auxOnUs,
      Credentials credentials, String payeeId, String payerFirstName, String payerLastName,
      String accountFullName, String routingNumber) {

    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    Date date = new Date();
    DataHelper dataHelper = new DataHelper();

    PayLeaseGatewayRequest payLeaseGatewayRequest = new PayLeaseGatewayRequest();
    payLeaseGatewayRequest.setCredentials(credentials);
    payLeaseGatewayRequest.setMode(TEST_MODE);

    Transactions transactions = new Transactions();
    List<Transaction> transactionList = new ArrayList<>();

    AchPaymentTransaction achPaymentTransaction = new AchPaymentTransaction();

    achPaymentTransaction.setTransactionAction(ACH_PAYMENT_TRANSACTION);
    achPaymentTransaction.setPaymentReferenceId(paymentReferenceId);
    achPaymentTransaction.setPaymentTraceId(paymentTraceID);
    achPaymentTransaction.setPayeeId(payeeId);
    achPaymentTransaction.setPayerReferenceId(payerReferenceId);
    achPaymentTransaction.setPayerFirstName(payerFirstName);
    achPaymentTransaction.setPayerLastName(payerLastName);
    achPaymentTransaction.setAccountFullName(accountFullName);
    achPaymentTransaction.setRoutingNumber(routingNumber);
    achPaymentTransaction.setAccountNumber(accountNumber);

    if (!creditReportingId.equals("")) {
      achPaymentTransaction.setCreditReportingId(creditReportingId);
    }

    if (!payerSecondaryRefId.equals("")) {
      achPaymentTransaction.setPayerSecondaryRefId(payerSecondaryRefId);
    }

    achPaymentTransaction.setAccountType(accountType);

    if (!currencyCode.equals("")) {
      achPaymentTransaction.setCurrencyCode(currencyCode);
    }

    double feeAmountVal = 0.0;
    if (!feeAmount.equals("")) {
      feeAmountVal = Double.parseDouble(feeAmount);
      achPaymentTransaction.setFeeAmount(feeAmountVal);
    }

    if (splitDeposit) {
      DecimalFormat decim = new DecimalFormat("0.00");

      SplitDeposit splitDeposit1 = new SplitDeposit();
      Deposit deposit1 = new Deposit();
      deposit1.setPayeeId(payeeId);
      double amount1 = Double.parseDouble(
          "2" + RandomStringUtils.random(1, false, true) + "." + RandomStringUtils
              .random(2, false, true));
      deposit1.setAmount(amount1);

      Deposit deposit2 = new Deposit();
      deposit2.setPayeeId(payeeId);
      double amount2 = Double.parseDouble(
          "2" + RandomStringUtils.random(1, false, true) + "." + RandomStringUtils
              .random(2, false, true));
      deposit2.setAmount(amount2);

      List<Deposit> depositList = new ArrayList<>();
      depositList.add(deposit1);
      depositList.add(deposit2);
      splitDeposit1.setDepositList(depositList);

      achPaymentTransaction.setSplitDeposit(splitDeposit1);
      achPaymentTransaction
          .setTotalAmount(decim.format(feeAmountVal + deposit1.getAmount() + deposit2.getAmount()));
    } else {
      String totalAmount = "2" + RandomStringUtils.random(2, false, true) + "." + RandomStringUtils
          .random(2, false, true);
      achPaymentTransaction.setTotalAmount(totalAmount);
    }

    if (!incurFee.equals("")) {
      achPaymentTransaction.setIncurFee(incurFee);
    }

    achPaymentTransaction.setSaveAccount(saveAccount);

    if (!message.equals("")) {
      achPaymentTransaction.setMessage(message);
    }

    if (!checkScanned.equals("")) {
      achPaymentTransaction.setCheckScanned(checkScanned);
    }

    if (!check21.equals("")) {
      achPaymentTransaction.setCheck21(check21);
    }

    if (!checkNum.equals("")) {
      achPaymentTransaction.setCheckNum(checkNum);
    }

    if (checkDate) {
      achPaymentTransaction.setCheckDate(dateFormat.format(date));
    }

    if (!auxOnUs.equals("")) {
      achPaymentTransaction.setAuxOnUs(auxOnUs);
    }

    if (imageFront) {
      achPaymentTransaction.setImageFront(dataHelper.generateAlphanumericString(10));
    }

    if (imageBack) {
      achPaymentTransaction.setImageBack(dataHelper.generateAlphanumericString(10));
    }

    transactionList.add(achPaymentTransaction);
    transactions.setTransactions(transactionList);
    payLeaseGatewayRequest.setTransactions(transactions);

    return payLeaseGatewayRequest;
  }

}
