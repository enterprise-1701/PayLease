package com.paylease.app.qa.api.tests.gapiNew;

import static com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest.TEST_MODE;
import static com.paylease.app.qa.framework.newApi.newGapi.CcTransactionTransaction.CC_TRANSACTION_TRANSACTION;

import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.newApi.Credentials;
import com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest;
import com.paylease.app.qa.framework.newApi.Transaction;
import com.paylease.app.qa.framework.newApi.Transactions;
import com.paylease.app.qa.framework.newApi.newGapi.CcTransactionTransaction;
import java.util.ArrayList;
import java.util.List;

public class NewCcTransactionTest extends ScriptBase {

  private static final String REGION = "gapi";
  private static final String FEATURE = "ccTransaction";

  private PayLeaseGatewayRequest buildRequest(Credentials credentials, String transactionId,
      String gatewayPayerId, String creditCardAction) {

    PayLeaseGatewayRequest payLeaseGatewayRequest = new PayLeaseGatewayRequest();
    Transactions transactions = new Transactions();
    List<Transaction> transactionList = new ArrayList<>();

    payLeaseGatewayRequest.setCredentials(credentials);
    payLeaseGatewayRequest.setMode(TEST_MODE);

    CcTransactionTransaction ccTransactionTransaction = new CcTransactionTransaction();
    ccTransactionTransaction.setTransactionAction(CC_TRANSACTION_TRANSACTION);
    ccTransactionTransaction.setTransactionId(transactionId);
    ccTransactionTransaction.setGatewayPayerId(gatewayPayerId);
    ccTransactionTransaction.setCreditCardAction(creditCardAction);

    transactionList.add(ccTransactionTransaction);
    transactions.setTransactions(transactionList);
    payLeaseGatewayRequest.setTransactions(transactions);

    return payLeaseGatewayRequest;
  }
}
