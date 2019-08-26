package com.paylease.app.qa.api.tests.gapiNew;

import static com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest.TEST_MODE;
import static com.paylease.app.qa.framework.newApi.newGapi.TransactionRefundTransaction.TRANSACTION_REFUND_TRANSACTION;

import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.newApi.Credentials;
import com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest;
import com.paylease.app.qa.framework.newApi.Transaction;
import com.paylease.app.qa.framework.newApi.Transactions;
import com.paylease.app.qa.framework.newApi.newGapi.TransactionRefundTransaction;
import java.util.ArrayList;
import java.util.List;

public class NewTransactionRefundTest extends ScriptBase {

  private static final String REGION = "gapi";
  private static final String FEATURE = "transactionRefund";

  private PayLeaseGatewayRequest buildrequest(Credentials credentials, String transactionId,
      String refundAmount) {

    PayLeaseGatewayRequest payLeaseGatewayRequest = new PayLeaseGatewayRequest();
    payLeaseGatewayRequest.setCredentials(credentials);
    payLeaseGatewayRequest.setMode(TEST_MODE);

    Transactions transactions = new Transactions();
    List<Transaction> transactionList = new ArrayList<>();

    TransactionRefundTransaction transactionRefundTransaction = new TransactionRefundTransaction();
    transactionRefundTransaction.setTransactionAction(TRANSACTION_REFUND_TRANSACTION);
    transactionRefundTransaction.setTransactionId(transactionId);
    transactionRefundTransaction.setRefundAmount(refundAmount);

    transactionList.add(transactionRefundTransaction);
    transactions.setTransactions(transactionList);
    payLeaseGatewayRequest.setTransactions(transactions);

    return payLeaseGatewayRequest;
  }
}
