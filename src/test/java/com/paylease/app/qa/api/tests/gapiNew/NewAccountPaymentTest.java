package com.paylease.app.qa.api.tests.gapiNew;

import static com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest.TEST_MODE;
import static com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest.URL;
import static com.paylease.app.qa.framework.newApi.newGapi.AccountPaymentTransaction.ACCOUNT_PAYMENT_TRANSACTION;

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
import com.paylease.app.qa.framework.newApi.newGapi.AccountPaymentTransaction;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.utility.restclienttool.RestClientUtil;
import com.paylease.app.qa.testbase.dataproviders.AccountPaymentDataProvider;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.testng.annotations.Test;

public class NewAccountPaymentTest extends ScriptBase {

  private static final String REGION = "gapi";
  private static final String FEATURE = "accountPayment";

  @Test(dataProvider = "accountPaymentXmlInjection", dataProviderClass = AccountPaymentDataProvider.class)
  public void tcXXXX(String testVariationNum, String paymentTraceId, String paymentReferenceId,
      String payerReferenceId, String gatewayPayerId, String creditReportingId, boolean splitDepoit,
      String currencyCode, String feeAmount, String incurFee, String checkScanned,
      String elementToCheck, String expectedValue, String expectedCode, String expectedStatus) {
    Logger.info(testVariationNum + "");
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

    PayLeaseGatewayRequest payLeaseGatewayRequest = buildRequestObject(credentials,
        paymentReferenceId, paymentTraceId, payeeId, payerReferenceId, gatewayPayerId,
        creditReportingId, splitDepoit, currencyCode, feeAmount, incurFee, checkScanned);

    StringEntity payLeaseGatewayRequestEntity = XmlHelper
        .marshalRequestObjectToXmlString(payLeaseGatewayRequest);

    RestClientUtil restClientUtil = new RestClientUtil();
    Response response = new Response();
    HttpResponse httpResponse = XmlHelper
        .sendGapiRequest(restClientUtil, payLeaseGatewayRequestEntity, URL);

    response
        .validatePartOfGapiResponse(restClientUtil, httpResponse, elementToCheck, expectedValue,
            expectedCode,
            expectedStatus, false);

  }

  //------------------------------------------Test Methods------------------------------------------

  private PayLeaseGatewayRequest buildRequestObject(Credentials credentials,
      String paymentReferenceId, String paymentTraceId, String payeeId, String payerReferenceId,
      String gatewayPayerId, String creditReportingId, boolean splitDeposit, String currencyCode,
      String feeAmount, String incurFee, String checkScanned) {
    PayLeaseGatewayRequest payLeaseGatewayRequest = new PayLeaseGatewayRequest();
    payLeaseGatewayRequest.setCredentials(credentials);
    payLeaseGatewayRequest.setMode(TEST_MODE);

    Transactions transactions = new Transactions();
    List<Transaction> transactionList = new ArrayList<>();

    AccountPaymentTransaction accountPaymentTransaction = new AccountPaymentTransaction();
    accountPaymentTransaction.setTransactionAction(ACCOUNT_PAYMENT_TRANSACTION);
    accountPaymentTransaction.setPaymentReferenceId(paymentReferenceId);
    accountPaymentTransaction.setPaymentTraceId(paymentTraceId);
    accountPaymentTransaction.setPayeeId(payeeId);
    accountPaymentTransaction.setPayerReferenceId(payerReferenceId);
    accountPaymentTransaction.setGatewayPayerId(gatewayPayerId);

    if (!creditReportingId.equals("")) {
      accountPaymentTransaction.setCreditReportingId(creditReportingId);
    }

    double feeAmountVal = 0.0;
    if (!feeAmount.equals("")) {
      feeAmountVal = Double.parseDouble(feeAmount);
      accountPaymentTransaction.setFeeAmount(feeAmountVal);
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

      accountPaymentTransaction.setSplitDeposit(splitDeposit1);
      accountPaymentTransaction
          .setTotalAmount(decim.format(feeAmountVal + deposit1.getAmount() + deposit2.getAmount()));
    } else {
      String totalAmount = "2" + RandomStringUtils.random(2, false, true) + "." + RandomStringUtils
          .random(2, false, true);
      accountPaymentTransaction.setTotalAmount(totalAmount);
    }

    if (!currencyCode.equals("")) {
      accountPaymentTransaction.setCurrencyCode(currencyCode);
    }

    if (!incurFee.equals("")) {
      accountPaymentTransaction.setIncurFee(incurFee);
    }

    if (!checkScanned.equals("")) {
      accountPaymentTransaction.setCheckScanned(checkScanned);
    }

    transactionList.add(accountPaymentTransaction);
    transactions.setTransactions(transactionList);
    payLeaseGatewayRequest.setTransactions(transactions);

    return payLeaseGatewayRequest;
  }


}
