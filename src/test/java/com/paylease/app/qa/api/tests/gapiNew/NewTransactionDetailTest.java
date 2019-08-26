package com.paylease.app.qa.api.tests.gapiNew;

import static com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest.TEST_MODE;
import static com.paylease.app.qa.framework.newApi.newGapi.TransactionDetailTransaction.TRANSACTION_DETAIL_TRANSACTION;

import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.newApi.Credentials;
import com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest;
import com.paylease.app.qa.framework.newApi.Transaction;
import com.paylease.app.qa.framework.newApi.Transactions;
import com.paylease.app.qa.framework.newApi.newGapi.TransactionDetailTransaction;
import java.util.ArrayList;
import java.util.List;

public class NewTransactionDetailTest extends ScriptBase {

  private static final String REGION = "gapi";
  private static final String FEATURE = "transactionDetail";

  private PayLeaseGatewayRequest buildRequest(Credentials credentials, String transactionId,
      String paymentReferenceId, String alwaysShowCurrency) {

    PayLeaseGatewayRequest payLeaseGatewayRequest = new PayLeaseGatewayRequest();
    payLeaseGatewayRequest.setCredentials(credentials);
    payLeaseGatewayRequest.setMode(TEST_MODE);

    Transactions transactions = new Transactions();
    List<Transaction> transactionList = new ArrayList<>();

    TransactionDetailTransaction transactionDetailTransaction = new TransactionDetailTransaction();
    transactionDetailTransaction.setTransactionAction(TRANSACTION_DETAIL_TRANSACTION);
    transactionDetailTransaction.setTransactionId(transactionId);
    transactionDetailTransaction.setPaymentReferenceId(paymentReferenceId);

    if (!alwaysShowCurrency.equals("")) {
      transactionDetailTransaction.setAlwaysShowCurrency(alwaysShowCurrency);
    }

    transactionList.add(transactionDetailTransaction);
    transactions.setTransactions(transactionList);
    payLeaseGatewayRequest.setTransactions(transactions);

    return payLeaseGatewayRequest;
  }

}
