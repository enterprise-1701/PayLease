package com.paylease.app.qa.api.tests.gapiNew;

import static com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest.TEST_MODE;
import static com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest.URL;
import static com.paylease.app.qa.framework.newApi.newGapi.CreateBankPayeeAccountTransaction.CREATE_BANK_PAYEE_ACCOUNT_TRANSACTION;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.newApi.Credentials;
import com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest;
import com.paylease.app.qa.framework.newApi.Response;
import com.paylease.app.qa.framework.newApi.Transaction;
import com.paylease.app.qa.framework.newApi.Transactions;
import com.paylease.app.qa.framework.newApi.XmlHelper;
import com.paylease.app.qa.framework.newApi.newGapi.CreateBankPayeeAccountTransaction;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.utility.listeners.Retry;
import com.paylease.app.qa.framework.utility.restclienttool.RestClientUtil;
import com.paylease.app.qa.testbase.dataproviders.CreateBankPayeeAccountDataProvider;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.testng.annotations.Test;

public class NewCreateBankPayeeAccountTest extends ScriptBase {

  private static final String REGION = "gapi";
  private static final String FEATURE = "CreateBankPayeeAccount";

  @Test(dataProvider = "createBankPayeeAccountXmlInjection", dataProviderClass =
      CreateBankPayeeAccountDataProvider.class, retryAnalyzer = Retry.class)
  public void createBankPayeeAccountXmlInjection(String testVariationNum, String payeeReferenceId,
      String payeeFirstName, String payeeLastName, String payeeState, String accountType,
      String accountFullName, String routingNumber, String accountNumber, String elementTagName,
      String expectedValue, String expectedCode, String expectedStatus) {
    Logger.info(testVariationNum
        + " Verify that CreateBankPayeeAccount GAPI action is not vulnerable to XML injection");
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
        payeeReferenceId, payeeFirstName, payeeLastName, payeeState, accountType, accountFullName,
        routingNumber, accountNumber);

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
      String payeeReferenceId, String payeeFirstName, String payeeLastName, String payeeState,
      String accountType, String accountFullName, String routingnumber, String accountNumber) {

    PayLeaseGatewayRequest payLeaseGatewayRequest = new PayLeaseGatewayRequest();
    payLeaseGatewayRequest.setCredentials(credentials);
    payLeaseGatewayRequest.setMode(TEST_MODE);

    Transactions transactions = new Transactions();
    List<Transaction> transactionList = new ArrayList<>();

    CreateBankPayeeAccountTransaction createBankPayeeAccountTransaction = new CreateBankPayeeAccountTransaction();

    createBankPayeeAccountTransaction.setTransactionAction(CREATE_BANK_PAYEE_ACCOUNT_TRANSACTION);
    createBankPayeeAccountTransaction.setPayeeReferenceId(payeeReferenceId);
    createBankPayeeAccountTransaction.setPayeeFirstName(payeeFirstName);
    createBankPayeeAccountTransaction.setPayeeLastName(payeeLastName);
    createBankPayeeAccountTransaction.setPayeeState(payeeState);
    createBankPayeeAccountTransaction.setAccountType(accountType);
    createBankPayeeAccountTransaction.setAccountFullName(accountFullName);
    createBankPayeeAccountTransaction.setRoutingNumber(routingnumber);
    createBankPayeeAccountTransaction.setAccountNumber(accountNumber);

    transactionList.add(createBankPayeeAccountTransaction);
    transactions.setTransactions(transactionList);
    payLeaseGatewayRequest.setTransactions(transactions);

    return payLeaseGatewayRequest;
  }
}

