package com.paylease.app.qa.api.tests.gapiNew;

import static com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest.TEST_MODE;
import static com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest.URL;
import static com.paylease.app.qa.framework.newApi.newGapi.IssueCashPermCardTransaction.ISSUE_CASH_PERM_CARD_TRANSACTION;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.newApi.Credentials;
import com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest;
import com.paylease.app.qa.framework.newApi.Response;
import com.paylease.app.qa.framework.newApi.Transaction;
import com.paylease.app.qa.framework.newApi.Transactions;
import com.paylease.app.qa.framework.newApi.XmlHelper;
import com.paylease.app.qa.framework.newApi.newGapi.IssueCashPermCardTransaction;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.utility.listeners.Retry;
import com.paylease.app.qa.framework.utility.restclienttool.RestClientUtil;
import com.paylease.app.qa.testbase.dataproviders.IssueCashPermCardDataProvider;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.testng.annotations.Test;

public class NewIssueCashPermCardTest extends ScriptBase {

  private static final String REGION = "gapi";
  private static final String FEATURE = "IssueCashPermCard";

  @Test(dataProvider = "issueCashPermCardXmlInjection", dataProviderClass =
      IssueCashPermCardDataProvider.class, retryAnalyzer = Retry.class)
  public void issueCashPermCardXmlInjection(String testVariationNum, String payerReferenceId,
      String payerFirstName, String payerLastName, String cardNumber, String elementToCheck,
      String expectedValue,
      String expectedCode, String expectedStatus) {
    Logger.info(testVariationNum
        + " Verify that IssueCashPermCard GAPI action is not vulnerable to XML Injection");
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();

    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String apiKey = testSetupPage.getString("apiKey");

    Credentials credentials = new Credentials();
    credentials.setGatewayId(gatewayId);
    credentials.setUsername(username);
    credentials.setPassword(password);
    credentials.setApiKey(apiKey);

    PayLeaseGatewayRequest payLeaseGatewayRequest = buildRequestObject(credentials,
        payerReferenceId, payerFirstName, payerLastName, cardNumber);

    StringEntity payLeaseGatewayRequestEntity = XmlHelper
        .marshalRequestObjectToXmlString(payLeaseGatewayRequest);
    RestClientUtil restClientUtil = new RestClientUtil();
    Response response = new Response();

    HttpResponse httpResponse = XmlHelper
        .sendGapiRequest(restClientUtil, payLeaseGatewayRequestEntity, URL);

    response.validatePartOfGapiResponse(restClientUtil, httpResponse, elementToCheck, expectedValue,
        expectedCode, expectedStatus, true);
  }

  //------------------------------------------Test Methods------------------------------------------

  private PayLeaseGatewayRequest buildRequestObject(Credentials credentials,
      String payerReferenceId, String payerFirstName, String payerLastName, String cardNumber) {

    PayLeaseGatewayRequest payLeaseGatewayRequest = new PayLeaseGatewayRequest();
    payLeaseGatewayRequest.setCredentials(credentials);
    payLeaseGatewayRequest.setMode(TEST_MODE);

    Transactions transactions = new Transactions();
    List<Transaction> transactionList = new ArrayList<>();

    IssueCashPermCardTransaction issueCashPermCardTransaction = new IssueCashPermCardTransaction();

    issueCashPermCardTransaction.setTransactionAction(ISSUE_CASH_PERM_CARD_TRANSACTION);
    issueCashPermCardTransaction.setPayerReferenceId(payerReferenceId);
    issueCashPermCardTransaction.setPayerFirstName(payerFirstName);
    issueCashPermCardTransaction.setPayerLastName(payerLastName);
    issueCashPermCardTransaction.setCardNumber(cardNumber);

    transactionList.add(issueCashPermCardTransaction);
    transactions.setTransactions(transactionList);
    payLeaseGatewayRequest.setTransactions(transactions);

    return payLeaseGatewayRequest;
  }
}
