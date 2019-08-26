package com.paylease.app.qa.api.tests.gapiNew;

import static com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest.TEST_MODE;
import static com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest.URL;
import static com.paylease.app.qa.framework.newApi.newGapi.AccountSearchTransaction.ACCOUNT_SEARCH_TRANSACTION;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.newApi.Credentials;
import com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest;
import com.paylease.app.qa.framework.newApi.Response;
import com.paylease.app.qa.framework.newApi.Transaction;
import com.paylease.app.qa.framework.newApi.Transactions;
import com.paylease.app.qa.framework.newApi.XmlHelper;
import com.paylease.app.qa.framework.newApi.newGapi.AccountSearchTransaction;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.utility.listeners.Retry;
import com.paylease.app.qa.framework.utility.restclienttool.RestClientUtil;
import com.paylease.app.qa.testbase.dataproviders.AccountSearchDataProvider;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.testng.annotations.Test;


public class NewAccountSearchTest extends ScriptBase {

  private static final String REGION = "gapi";
  private static final String FEATURE = "AccountPayment";

  @Test(dataProvider = "accountSearchXmlInjection", dataProviderClass =
      AccountSearchDataProvider.class, retryAnalyzer = Retry.class)
  public void accountSearchXmlInjection(String testVariationNum, String payerReferenceId,
      String gatewayPayerId, String elementToCheck, String expectedValue, String expectedCode,
      String expectedStatus) {
    Logger.info(testVariationNum
        + " Verify that AccountSearch GAPI action is not vulnerable to XML injection");
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();

    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String validPayerReferenceId = testSetupPage.getString("payeeId");
    final String validGatewayPayerId = testSetupPage.getString("gatewayPayerId");

    if (payerReferenceId.equals("")) {
      payerReferenceId = validPayerReferenceId;
    }

    if (gatewayPayerId.equals("")) {
      gatewayPayerId = validGatewayPayerId;
    }

    Credentials credentials = new Credentials();
    credentials.setGatewayId(gatewayId);
    credentials.setUsername(username);
    credentials.setPassword(password);

    PayLeaseGatewayRequest payLeaseGatewayRequest = buildRequestObject(credentials,
        payerReferenceId, gatewayPayerId);

    StringEntity payLeaseGatewayRequestEntity = XmlHelper
        .marshalRequestObjectToXmlString(payLeaseGatewayRequest);

    RestClientUtil restClientUtil = new RestClientUtil();
    HttpResponse httpResponse = XmlHelper
        .sendGapiRequest(restClientUtil, payLeaseGatewayRequestEntity, URL);

    Response response = new Response();

    response.validatePartOfGapiResponse(restClientUtil, httpResponse, elementToCheck, expectedValue,
        expectedCode, expectedStatus, true);
  }

  //------------------------------------------Test Methods------------------------------------------

  private PayLeaseGatewayRequest buildRequestObject(Credentials credentials,
      String payerReferenceId, String gatewayPayerId) {
    PayLeaseGatewayRequest payLeaseGatewayRequest = new PayLeaseGatewayRequest();
    payLeaseGatewayRequest.setCredentials(credentials);
    payLeaseGatewayRequest.setMode(TEST_MODE);

    Transactions transactions = new Transactions();
    List<Transaction> transactionList = new ArrayList<>();

    AccountSearchTransaction accountSearchTransaction = new AccountSearchTransaction();
    accountSearchTransaction.setTransactionAction(ACCOUNT_SEARCH_TRANSACTION);
    accountSearchTransaction.setPayerReferenceId(payerReferenceId);
    accountSearchTransaction.setGatewayPayerId(gatewayPayerId);

    transactionList.add(accountSearchTransaction);
    transactions.setTransactions(transactionList);
    payLeaseGatewayRequest.setTransactions(transactions);

    return payLeaseGatewayRequest;
  }

}
