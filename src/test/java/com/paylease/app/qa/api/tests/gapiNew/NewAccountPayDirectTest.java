package com.paylease.app.qa.api.tests.gapiNew;

import static com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest.TEST_MODE;
import static com.paylease.app.qa.framework.newApi.newGapi.AccountPayDirectTransaction.ACCOUNT_PAY_DIRECT_TRANSACTION;

import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.newApi.Credentials;
import com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest;
import com.paylease.app.qa.framework.newApi.Transaction;
import com.paylease.app.qa.framework.newApi.Transactions;
import com.paylease.app.qa.framework.newApi.newGapi.AccountPayDirectTransaction;
import java.util.ArrayList;
import java.util.List;

public class NewAccountPayDirectTest extends ScriptBase {

  private static final String REGION = "gapi";
  private static final String FEATURE = "accountPayDirect";

//------------------------------------------Test Methods------------------------------------------

  private PayLeaseGatewayRequest buildRequestObject(Credentials credentials,
      String paymentReferenceId, String paymentTraceId, String payerId, String payeeReferenceId,
      String payeeState, String gatewayPayeeId, String totalAmount) {
    PayLeaseGatewayRequest payLeaseGatewayRequest = new PayLeaseGatewayRequest();
    payLeaseGatewayRequest.setCredentials(credentials);
    payLeaseGatewayRequest.setMode(TEST_MODE);

    Transactions transactions = new Transactions();
    List<Transaction> transactionList = new ArrayList<>();

    AccountPayDirectTransaction accountPayDirectTransaction = new AccountPayDirectTransaction();
    accountPayDirectTransaction.setTransactionAction(ACCOUNT_PAY_DIRECT_TRANSACTION);
    accountPayDirectTransaction.setPaymentReferenceId(paymentReferenceId);
    accountPayDirectTransaction.setPaymentTraceId(paymentTraceId);
    accountPayDirectTransaction.setPayerId(payerId);
    accountPayDirectTransaction.setPayeeReferenceId(payeeReferenceId);
    accountPayDirectTransaction.setPayeeState(payeeState);
    accountPayDirectTransaction.setGatewayPayeeId(gatewayPayeeId);
    accountPayDirectTransaction.setTotalAmount(totalAmount);

    transactionList.add(accountPayDirectTransaction);
    transactions.setTransactions(transactionList);
    payLeaseGatewayRequest.setTransactions(transactions);

    return payLeaseGatewayRequest;
  }

}
