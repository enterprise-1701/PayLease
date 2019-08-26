package com.paylease.app.qa.api.tests.gapiNew;

import static com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest.TEST_MODE;
import static com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest.URL;
import static com.paylease.app.qa.framework.newApi.newGapi.CreateCreditCardPayerAccountTransaction.CREATE_CREDIT_CARD_PAYER_ACCOUNT_TRANSACTION;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.newApi.Credentials;
import com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest;
import com.paylease.app.qa.framework.newApi.Response;
import com.paylease.app.qa.framework.newApi.Transaction;
import com.paylease.app.qa.framework.newApi.Transactions;
import com.paylease.app.qa.framework.newApi.XmlHelper;
import com.paylease.app.qa.framework.newApi.newGapi.CreateCreditCardPayerAccountTransaction;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.utility.listeners.Retry;
import com.paylease.app.qa.framework.utility.restclienttool.RestClientUtil;
import com.paylease.app.qa.testbase.dataproviders.CreateCreditCardPayerAccountDataProvider;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.testng.annotations.Test;

public class NewCreateCreditCardPayerAccountTest extends ScriptBase {

  private static final String REGION = "gapi";
  private static final String FEATURE = "CreateCreditCardPayerAccount";

  @Test(dataProvider = "creditCardPayerAccountXmlInjection",
      dataProviderClass = CreateCreditCardPayerAccountDataProvider.class,
      retryAnalyzer = Retry.class)
  public void createCreditCardPayerAccountXmlInjection(String testVariationNum,
      String payerReferenceId, String payerFirstName,
      String payerLastName, String creditCardType, String creditCardNumber,
      String creditCardExpMonth, String creditCardExpYear, String creditCardCvv2,
      String billingFirstName, String billingLastName, String billingStreetAddress,
      String billingCity, String billingState, String billingCountry, String billingZip,
      String elementTagName, String expectedValue, String expectedCode, String expectedStatus) {
    Logger.info(testVariationNum
        + " Verify that CreditCardPayerAccount GAPI action is not vulnerable to XML Injection");

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
        payerReferenceId, payerFirstName, payerLastName, creditCardType, creditCardNumber,
        creditCardExpMonth, creditCardExpYear, creditCardCvv2, billingFirstName, billingLastName,
        billingStreetAddress, billingCity, billingState, billingCountry, billingZip);

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
      String payerReferenceId, String payerFirstName, String payerLastName, String creditCardType,
      String creditCardNumber, String creditCardExpMonth, String creditCardExpYear,
      String creditCardCvv2, String billingFirstName, String billingLastName,
      String billingStreetAddress, String billingCity, String billingState, String billingCountry,
      String billingZip) {

    PayLeaseGatewayRequest payLeaseGatewayRequest = new PayLeaseGatewayRequest();
    payLeaseGatewayRequest.setCredentials(credentials);
    payLeaseGatewayRequest.setMode(TEST_MODE);

    Transactions transactions = new Transactions();
    List<Transaction> transactionList = new ArrayList<>();

    CreateCreditCardPayerAccountTransaction createCreditCardPayerAccountTransaction = new CreateCreditCardPayerAccountTransaction();

    createCreditCardPayerAccountTransaction
        .setTransactionAction(CREATE_CREDIT_CARD_PAYER_ACCOUNT_TRANSACTION);
    createCreditCardPayerAccountTransaction.setPayerReferenceId(payerReferenceId);
    createCreditCardPayerAccountTransaction.setPayerFirstName(payerFirstName);
    createCreditCardPayerAccountTransaction.setPayerLastName(payerLastName);
    createCreditCardPayerAccountTransaction.setCreditCardType(creditCardType);
    createCreditCardPayerAccountTransaction.setCreditCardNumber(creditCardNumber);
    createCreditCardPayerAccountTransaction.setCreditCardExpMonth(creditCardExpMonth);
    createCreditCardPayerAccountTransaction.setCreditCardExpYear(creditCardExpYear);
    if (!creditCardCvv2.equals("")) {
      createCreditCardPayerAccountTransaction.setCreditCardCvv2(creditCardCvv2);
    }
    createCreditCardPayerAccountTransaction.setBillingFirstName(billingFirstName);
    createCreditCardPayerAccountTransaction.setBillingLastName(billingLastName);
    createCreditCardPayerAccountTransaction.setBillingStreetAddress(billingStreetAddress);
    createCreditCardPayerAccountTransaction.setBillingCity(billingCity);
    createCreditCardPayerAccountTransaction.setBillingState(billingState);
    createCreditCardPayerAccountTransaction.setBillingCountry(billingCountry);
    createCreditCardPayerAccountTransaction.setBillingZip(billingZip);

    transactionList.add(createCreditCardPayerAccountTransaction);
    transactions.setTransactions(transactionList);
    payLeaseGatewayRequest.setTransactions(transactions);

    return payLeaseGatewayRequest;
  }
}
