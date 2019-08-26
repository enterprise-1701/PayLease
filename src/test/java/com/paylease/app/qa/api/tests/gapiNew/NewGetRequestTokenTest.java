package com.paylease.app.qa.api.tests.gapiNew;

import static com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest.TEST_MODE;
import static com.paylease.app.qa.framework.newApi.newGapi.GetRequestTokenTransaction.GET_REQUEST_TOKEN_TRANSACTION;

import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.newApi.Credentials;
import com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest;
import com.paylease.app.qa.framework.newApi.Transaction;
import com.paylease.app.qa.framework.newApi.Transactions;
import com.paylease.app.qa.framework.newApi.newGapi.GetRequestTokenTransaction;
import java.util.ArrayList;
import java.util.List;

public class NewGetRequestTokenTest extends ScriptBase {

  private static final String REGION = "gapi";
  private static final String FEATURE = "getRequestToken";

  private PayLeaseGatewayRequest buildRequest(Credentials credentials) {

    PayLeaseGatewayRequest payLeaseGatewayRequest = new PayLeaseGatewayRequest();
    payLeaseGatewayRequest.setCredentials(credentials);
    payLeaseGatewayRequest.setMode(TEST_MODE);

    Transactions transactions = new Transactions();
    List<Transaction> transactionList = new ArrayList<>();

    GetRequestTokenTransaction getRequestTokenTransaction = new GetRequestTokenTransaction();
    getRequestTokenTransaction.setTransactionAction(GET_REQUEST_TOKEN_TRANSACTION);

    transactionList.add(getRequestTokenTransaction);
    transactions.setTransactions(transactionList);
    payLeaseGatewayRequest.setTransactions(transactions);

    return payLeaseGatewayRequest;
  }
}
