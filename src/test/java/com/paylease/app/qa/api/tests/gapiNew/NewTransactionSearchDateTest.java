package com.paylease.app.qa.api.tests.gapiNew;

import static com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest.TEST_MODE;
import static com.paylease.app.qa.framework.newApi.newGapi.TransactionSearchDateTransaction.TRANSACTION_SEARCH_DATE_TRANSACTION;

import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.newApi.Credentials;
import com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest;
import com.paylease.app.qa.framework.newApi.Transaction;
import com.paylease.app.qa.framework.newApi.Transactions;
import com.paylease.app.qa.framework.newApi.newGapi.TransactionSearchDateTransaction;
import java.util.ArrayList;
import java.util.List;

public class NewTransactionSearchDateTest extends ScriptBase {

  private static final String REGION = "gapi";
  private static final String FEATURE = "transactionSearchDate";

  private PayLeaseGatewayRequest buildRequest(Credentials credentials, String searchStartDate,
      String searchEndDate, String numberOfItems, String alwaysShowCurrency) {

    PayLeaseGatewayRequest payLeaseGatewayRequest = new PayLeaseGatewayRequest();
    payLeaseGatewayRequest.setCredentials(credentials);
    payLeaseGatewayRequest.setMode(TEST_MODE);

    Transactions transactions = new Transactions();
    List<Transaction> transactionList = new ArrayList<>();

    TransactionSearchDateTransaction transactionSearchDateTransaction = new TransactionSearchDateTransaction();
    transactionSearchDateTransaction.setTransactionAction(TRANSACTION_SEARCH_DATE_TRANSACTION);
    transactionSearchDateTransaction.setSearchStartDate(searchStartDate);
    transactionSearchDateTransaction.setSearchEndDate(searchEndDate);
    transactionSearchDateTransaction.setNumberOfItems(numberOfItems);

    if (!alwaysShowCurrency.equals("")) {
      transactionSearchDateTransaction.setAlwaysShowCurrency(alwaysShowCurrency);
    }

    transactionList.add(transactionSearchDateTransaction);
    transactions.setTransactions(transactionList);
    payLeaseGatewayRequest.setTransactions(transactions);

    return payLeaseGatewayRequest;
  }
}
