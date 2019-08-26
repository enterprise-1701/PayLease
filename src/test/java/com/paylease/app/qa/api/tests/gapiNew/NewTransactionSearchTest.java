package com.paylease.app.qa.api.tests.gapiNew;

import static com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest.TEST_MODE;
import static com.paylease.app.qa.framework.newApi.newGapi.TransactionSearchTransaction.TRANSACTION_SEARCH_TRANSACTION;

import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.newApi.Credentials;
import com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest;
import com.paylease.app.qa.framework.newApi.Transaction;
import com.paylease.app.qa.framework.newApi.Transactions;
import com.paylease.app.qa.framework.newApi.newGapi.TransactionSearchTransaction;
import java.util.ArrayList;
import java.util.List;

public class NewTransactionSearchTest extends ScriptBase {

  private static final String REGION = "gapi";
  private static final String FEATURE = "transactionSearch";

  private PayLeaseGatewayRequest buildRequest(Credentials credentials, String transactionType,
      String numberOfItems, String alwaysShowCurrency) {

    PayLeaseGatewayRequest payLeaseGatewayRequest = new PayLeaseGatewayRequest();
    payLeaseGatewayRequest.setCredentials(credentials);
    payLeaseGatewayRequest.setMode(TEST_MODE);

    Transactions transactions = new Transactions();
    List<Transaction> transactionList = new ArrayList<>();

    TransactionSearchTransaction transactionSearchTransaction = new TransactionSearchTransaction();
    transactionSearchTransaction.setTransactionAction(TRANSACTION_SEARCH_TRANSACTION);
    transactionSearchTransaction.setTransactionType(transactionType);
    transactionSearchTransaction.setNumberOfItems(numberOfItems);

    if (!alwaysShowCurrency.equals("")) {
      transactionSearchTransaction.setAlwaysShowCurrency(alwaysShowCurrency);
    }

    transactionList.add(transactionSearchTransaction);
    transactions.setTransactions(transactionList);
    payLeaseGatewayRequest.setTransactions(transactions);

    return payLeaseGatewayRequest;
  }
}
