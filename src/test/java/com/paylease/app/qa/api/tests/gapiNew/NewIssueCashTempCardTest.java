package com.paylease.app.qa.api.tests.gapiNew;

import static com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest.TEST_MODE;
import static com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest.URL;
import static com.paylease.app.qa.framework.newApi.newGapi.IssueCashTempCardTransaction.ISSUE_CASH_TEMP_CARD_TRANSACTION;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.newApi.Credentials;
import com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest;
import com.paylease.app.qa.framework.newApi.Response;
import com.paylease.app.qa.framework.newApi.Transaction;
import com.paylease.app.qa.framework.newApi.Transactions;
import com.paylease.app.qa.framework.newApi.XmlHelper;
import com.paylease.app.qa.framework.newApi.newGapi.IssueCashTempCardTransaction;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.utility.restclienttool.RestClientUtil;
import com.paylease.app.qa.testbase.dataproviders.IssueCashTempCardDataProvider;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.testng.annotations.Test;

public class NewIssueCashTempCardTest extends ScriptBase {

  private static final String REGION = "gapi";
  private static final String FEATURE = "IssueCashTempCard";

  @Test(dataProvider = "issueCashTempCardXmlInjection", dataProviderClass =
      IssueCashTempCardDataProvider.class)
  public void issueCashTempCardXmlInjection(String testVariationNum, String payerReferenceId,
      String payerFirstName,
      String payerLastName, String elementTagName, String expectedValue, String expectedCode,
      String expectedStatus) {
    Logger.info(testVariationNum
        + " Verify that IssueCashTempCard GAPI action is not vulnerable to XML Injection");
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
        payerReferenceId, payerFirstName, payerLastName);

    StringEntity payLeaseGatewayRequestEntity = XmlHelper
        .marshalRequestObjectToXmlString(payLeaseGatewayRequest);

    RestClientUtil restClientUtil = new RestClientUtil();
    HttpResponse httpResponse = XmlHelper
        .sendGapiRequest(restClientUtil, payLeaseGatewayRequestEntity, URL);
    Response response = new Response();

    response.validatePartOfGapiResponse(restClientUtil, httpResponse, elementTagName, expectedValue,
        expectedCode, expectedStatus, true);
  }

  //------------------------------------------Test Methods------------------------------------------

  private PayLeaseGatewayRequest buildRequestObject(Credentials credentials,
      String payerReferenceId, String payerFirstName, String payerLastName) {

    PayLeaseGatewayRequest payLeaseGatewayRequest = new PayLeaseGatewayRequest();
    payLeaseGatewayRequest.setCredentials(credentials);
    payLeaseGatewayRequest.setMode(TEST_MODE);

    Transactions transactions = new Transactions();
    List<Transaction> transactionList = new ArrayList<>();

    IssueCashTempCardTransaction issueCashTempCardTransaction = new IssueCashTempCardTransaction();

    issueCashTempCardTransaction.setTransactionAction(ISSUE_CASH_TEMP_CARD_TRANSACTION);
    issueCashTempCardTransaction.setPayerReferenceId(payerReferenceId);
    issueCashTempCardTransaction.setPayerFirstName(payerFirstName);
    issueCashTempCardTransaction.setPayerLastName(payerLastName);

    transactionList.add(issueCashTempCardTransaction);
    transactions.setTransactions(transactionList);
    payLeaseGatewayRequest.setTransactions(transactions);

    return payLeaseGatewayRequest;
  }
}
