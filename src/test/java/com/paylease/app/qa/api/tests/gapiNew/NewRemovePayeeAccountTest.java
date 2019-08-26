package com.paylease.app.qa.api.tests.gapiNew;

import static com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest.TEST_MODE;
import static com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest.URL;
import static com.paylease.app.qa.framework.newApi.newGapi.RemovePayeeAccountTransaction.REMOVE_PAYEE_ACCOUNT_TRANSACTION;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.newApi.Credentials;
import com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest;
import com.paylease.app.qa.framework.newApi.Response;
import com.paylease.app.qa.framework.newApi.Transaction;
import com.paylease.app.qa.framework.newApi.Transactions;
import com.paylease.app.qa.framework.newApi.XmlHelper;
import com.paylease.app.qa.framework.newApi.newGapi.RemovePayeeAccountTransaction;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.utility.restclienttool.RestClientUtil;
import com.paylease.app.qa.testbase.dataproviders.RemovePayeeAccountDataProvider;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.testng.annotations.Test;

public class NewRemovePayeeAccountTest extends ScriptBase {

  private static final String REGION = "gapi";
  private static final String FEATURE = "removePayeeAccount";

  @Test(dataProvider = "removePayeeAccountXmlInjection", dataProviderClass =
      RemovePayeeAccountDataProvider.class)
  public void removePayeeAccountXmlInjection(String testVariationNum, String payeeReferenceId,
      String gatewayPayeeId,
      String elementTagName, String expectedValue, String expectedCode, String expectedStatus) {
    Logger.info(testVariationNum
        + " Verify that RemovePayeeAcount GAPI action is not vulnerable to XML Injection");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();

    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");

    Credentials credentials = new Credentials();
    credentials.setGatewayId(gatewayId);
    credentials.setUsername(username);
    credentials.setPassword(password);

    PayLeaseGatewayRequest payLeaseGatewayRequest = buildRequestObject(credentials,
        payeeReferenceId, gatewayPayeeId);

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

  private PayLeaseGatewayRequest buildRequestObject(Credentials credentials, String payeeRefeneceId,
      String gatewayPayeeId) {

    PayLeaseGatewayRequest payLeaseGatewayRequest = new PayLeaseGatewayRequest();
    payLeaseGatewayRequest.setCredentials(credentials);
    payLeaseGatewayRequest.setMode(TEST_MODE);

    Transactions transactions = new Transactions();
    List<Transaction> transactionList = new ArrayList<>();

    RemovePayeeAccountTransaction removePayeeAccountTransaction = new RemovePayeeAccountTransaction();
    removePayeeAccountTransaction.setTransactionAction(REMOVE_PAYEE_ACCOUNT_TRANSACTION);
    removePayeeAccountTransaction.setPayeeReferenceId(payeeRefeneceId);
    removePayeeAccountTransaction.setGatewayPayeeId(gatewayPayeeId);

    transactionList.add(removePayeeAccountTransaction);
    transactions.setTransactions(transactionList);
    payLeaseGatewayRequest.setTransactions(transactions);

    return payLeaseGatewayRequest;
  }
}

