package com.paylease.app.qa.api.tests.gapiNew;

import static com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest.TEST_MODE;
import static com.paylease.app.qa.framework.newApi.newGapi.ServerPingTransaction.SERVER_PING_TRANSACTION;

import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.newApi.Credentials;
import com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest;
import com.paylease.app.qa.framework.newApi.Transaction;
import com.paylease.app.qa.framework.newApi.Transactions;
import com.paylease.app.qa.framework.newApi.newGapi.ServerPingTransaction;
import java.util.ArrayList;
import java.util.List;

public class NewServerPingTest extends ScriptBase {

  private static final String REGION = "gapi";
  private static final String FEATURE = "serverPing";

  private PayLeaseGatewayRequest buildRequest(Credentials credentials) {

    PayLeaseGatewayRequest payLeaseGatewayRequest = new PayLeaseGatewayRequest();
    payLeaseGatewayRequest.setCredentials(credentials);
    payLeaseGatewayRequest.setMode(TEST_MODE);

    Transactions transactions = new Transactions();
    List<Transaction> transactionList = new ArrayList<>();

    ServerPingTransaction serverPingTransaction = new ServerPingTransaction();
    serverPingTransaction.setTransactionAction(SERVER_PING_TRANSACTION);

    transactionList.add(serverPingTransaction);
    transactions.setTransactions(transactionList);
    payLeaseGatewayRequest.setTransactions(transactions);

    return payLeaseGatewayRequest;
  }
}
