package com.paylease.app.qa.api.tests.gapiNew;

import static com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest.TEST_MODE;
import static com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest.URL;
import static com.paylease.app.qa.framework.newApi.newGapi.CreateBankPayerAccountTransaction.CREATE_BANK_PAYER_ACCOUNT_TRANSACTION;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.newApi.Credentials;
import com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest;
import com.paylease.app.qa.framework.newApi.Response;
import com.paylease.app.qa.framework.newApi.Transaction;
import com.paylease.app.qa.framework.newApi.Transactions;
import com.paylease.app.qa.framework.newApi.XmlHelper;
import com.paylease.app.qa.framework.newApi.newGapi.CreateBankPayerAccountTransaction;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.utility.listeners.Retry;
import com.paylease.app.qa.framework.utility.restclienttool.RestClientUtil;
import com.paylease.app.qa.testbase.dataproviders.CreateBankPayerAccountDataProvider;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.testng.annotations.Test;

public class NewCreateBankPayerAccountTest extends ScriptBase {

  private static final String REGION = "gapi";
  private static final String FEATURE = "CreateBankPayerAccount";

  @Test(dataProvider = "createBankPayerAccountXmlInjection", dataProviderClass =
      CreateBankPayerAccountDataProvider.class, retryAnalyzer = Retry.class)
  public void createBankPayerAccountXmlInjection(String testVariationNum, String payerReferenceId,
      String payerFirstName,
      String payerLastName, String accountType, String accountFullName, String routingNumber,
      String accountNumber, String currencyCode, String elementTagName, String expectedValue,
      String expectedCode, String expectedStatus) {
    Logger.info(testVariationNum
        + " Verify that CreateBankPayerAccount is not vulnerable to XML Injection");

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
        payerReferenceId, payerFirstName, payerLastName, accountType, accountFullName,
        routingNumber, accountNumber, currencyCode);

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
      String payerReferenceId, String payerFirstName, String payerLastName, String accountType,
      String accountFullName, String routingNumber, String accountNumber, String currencyCode) {

    PayLeaseGatewayRequest payLeaseGatewayRequest = new PayLeaseGatewayRequest();
    payLeaseGatewayRequest.setCredentials(credentials);
    payLeaseGatewayRequest.setMode(TEST_MODE);

    Transactions transactions = new Transactions();
    List<Transaction> transactionList = new ArrayList<>();

    CreateBankPayerAccountTransaction createBankPayerAccountTransaction = new CreateBankPayerAccountTransaction();

    createBankPayerAccountTransaction.setTransactionAction(CREATE_BANK_PAYER_ACCOUNT_TRANSACTION);
    createBankPayerAccountTransaction.setPayerReferenceId(payerReferenceId);
    createBankPayerAccountTransaction.setPayerFirstName(payerFirstName);
    createBankPayerAccountTransaction.setPayerLastName(payerLastName);
    createBankPayerAccountTransaction.setAccountType(accountType);
    createBankPayerAccountTransaction.setAccountFullName(accountFullName);
    createBankPayerAccountTransaction.setRoutingNumber(routingNumber);
    createBankPayerAccountTransaction.setAccountNumber(accountNumber);

    if (!currencyCode.equals("")) {
      createBankPayerAccountTransaction.setCurrencyCode(currencyCode);
    }

    transactionList.add(createBankPayerAccountTransaction);
    transactions.setTransactions(transactionList);
    payLeaseGatewayRequest.setTransactions(transactions);

    return payLeaseGatewayRequest;
  }
}
