package com.paylease.app.qa.api.tests.gapiNew;

import static com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest.TEST_MODE;
import static com.paylease.app.qa.framework.newApi.newGapi.TransactionVoidTransaction.TRANSACTION_VOID_TRANSACTION;

import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.newApi.Credentials;
import com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest;
import com.paylease.app.qa.framework.newApi.Transaction;
import com.paylease.app.qa.framework.newApi.Transactions;
import com.paylease.app.qa.framework.newApi.newGapi.TransactionVoidTransaction;
import java.util.ArrayList;
import java.util.List;

public class NewTransactionVoidTest extends ScriptBase {

  private static final String REGION = "gapi";
  private static final String FEATURE = "transactionVoid";

  private PayLeaseGatewayRequest buildRequest(Credentials credentials, String transactionId) {

    PayLeaseGatewayRequest payLeaseGatewayRequest = new PayLeaseGatewayRequest();
    payLeaseGatewayRequest.setCredentials(credentials);
    payLeaseGatewayRequest.setMode(TEST_MODE);

    Transactions transactions = new Transactions();
    List<Transaction> transactionList = new ArrayList<>();

    TransactionVoidTransaction transactionVoidTransaction = new TransactionVoidTransaction();
    transactionVoidTransaction.setTransactionAction(TRANSACTION_VOID_TRANSACTION);
    transactionVoidTransaction.setTransactionId(transactionId);

    transactionList.add(transactionVoidTransaction);
    transactions.setTransactions(transactionList);
    payLeaseGatewayRequest.setTransactions(transactions);

    return payLeaseGatewayRequest;
  }

}
