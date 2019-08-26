package com.paylease.app.qa.api.tests.gapiNew;

import static com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest.TEST_MODE;
import static com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest.URL;
import static com.paylease.app.qa.framework.newApi.newGapi.RemovePayerAccountTransaction.REMOVE_PAYER_ACCOUNT_TRANSACTION;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.newApi.Credentials;
import com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest;
import com.paylease.app.qa.framework.newApi.Response;
import com.paylease.app.qa.framework.newApi.Transaction;
import com.paylease.app.qa.framework.newApi.Transactions;
import com.paylease.app.qa.framework.newApi.XmlHelper;
import com.paylease.app.qa.framework.newApi.newGapi.RemovePayerAccountTransaction;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.utility.restclienttool.RestClientUtil;
import com.paylease.app.qa.testbase.dataproviders.RemovePayerAccountDataProvider;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.testng.annotations.Test;

public class NewRemovePayerAccountTest extends ScriptBase {

  private static final String REGION = "gapi";
  private static final String FEATURE = "RemovePayerAccount";

  @Test(dataProvider = "removePayerAccountXmlInjection", dataProviderClass =
      RemovePayerAccountDataProvider.class)
  public void removePayerAccountXmlInjection(String testVariationNum, String payerReferenceId,
      String gatewayPayerId,
      String elementTagName, String expectedValue, String expectedCode, String expectedStatus,
      boolean doubleEncode) {
    Logger.info(testVariationNum
        + " Verify RemovePayerAccount GAPI action is not vulnerable to XML Injection");
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();

    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String validPayerReferenceId = testSetupPage.getString("payerReferenceId");
    final String validGatewayPayerId = testSetupPage.getString("gatewayPayerId");
    String payerReferenceIdValue = payerReferenceId;
    String gatewayPayerIdValue = gatewayPayerId;

    if (payerReferenceId.equals("")) {
      payerReferenceIdValue = validPayerReferenceId;
    }

    if (gatewayPayerId.equals("")) {
      gatewayPayerIdValue = validGatewayPayerId;
    }
    Credentials credentials = new Credentials();
    credentials.setGatewayId(gatewayId);
    credentials.setUsername(username);
    credentials.setPassword(password);

    PayLeaseGatewayRequest payLeaseGatewayRequest = buildRequestObject(credentials,
        payerReferenceIdValue, gatewayPayerIdValue);

    StringEntity payLeaseGatewayRequestEntity = XmlHelper
        .marshalRequestObjectToXmlString(payLeaseGatewayRequest);

    RestClientUtil restClientUtil = new RestClientUtil();
    HttpResponse httpResponse = XmlHelper
        .sendGapiRequest(restClientUtil, payLeaseGatewayRequestEntity, URL);
    Response response = new Response();

    response.validatePartOfGapiResponse(restClientUtil, httpResponse, elementTagName, expectedValue,
        expectedCode, expectedStatus, doubleEncode);
  }

  //------------------------------------------Test Methods------------------------------------------

  private PayLeaseGatewayRequest buildRequestObject(Credentials credentials,
      String payerReferenceId, String gatewayPayerId) {
    PayLeaseGatewayRequest payLeaseGatewayRequest = new PayLeaseGatewayRequest();
    payLeaseGatewayRequest.setCredentials(credentials);
    payLeaseGatewayRequest.setMode(TEST_MODE);

    Transactions transactions = new Transactions();
    List<Transaction> transactionList = new ArrayList<>();

    RemovePayerAccountTransaction removePayerAccountTransaction = new RemovePayerAccountTransaction();
    removePayerAccountTransaction.setTransactionAction(REMOVE_PAYER_ACCOUNT_TRANSACTION);
    removePayerAccountTransaction.setPayerReferenceId(payerReferenceId);
    removePayerAccountTransaction.setGatewayPayerId(gatewayPayerId);

    transactionList.add(removePayerAccountTransaction);
    transactions.setTransactions(transactionList);
    payLeaseGatewayRequest.setTransactions(transactions);

    return payLeaseGatewayRequest;
  }
}
