package com.paylease.app.qa.api.tests.gapiNew;

import static com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest.TEST_MODE;
import static com.paylease.app.qa.framework.newApi.newGapi.PayDirectTransaction.PAY_DIRECT_TRANSACTION;

import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.newApi.Credentials;
import com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest;
import com.paylease.app.qa.framework.newApi.Transaction;
import com.paylease.app.qa.framework.newApi.Transactions;
import com.paylease.app.qa.framework.newApi.newGapi.PayDirectTransaction;
import java.util.ArrayList;
import java.util.List;

public class NewPayDirectTest extends ScriptBase {

  private static final String REGION = "gapi";
  private static final String FEATURE = "payDirect";

  private PayLeaseGatewayRequest buildRequest(Credentials credentials, String paymentReferenceId,
      String paymentTraceId, String payerId, String payeeReferenceId, String payeeFirstName,
      String payeeLastName, String payeeEmailAddres, String payeeState, String accountType,
      String accountFullName, String routingNumber, String accountNumber, String totalAmount,
      String saveAccount) {

    PayLeaseGatewayRequest payLeaseGatewayRequest = new PayLeaseGatewayRequest();
    payLeaseGatewayRequest.setCredentials(credentials);
    payLeaseGatewayRequest.setMode(TEST_MODE);

    Transactions transactions = new Transactions();
    List<Transaction> transactionList = new ArrayList<>();

    PayDirectTransaction payDirectTransaction = new PayDirectTransaction();
    payDirectTransaction.setTransactionAction(PAY_DIRECT_TRANSACTION);
    payDirectTransaction.setPaymentReferenceId(paymentReferenceId);
    payDirectTransaction.setPaymentTraceId(paymentTraceId);
    payDirectTransaction.setPayerId(payerId);
    payDirectTransaction.setPayeeReferenceId(payeeReferenceId);
    payDirectTransaction.setPayeeFirstName(payeeFirstName);
    payDirectTransaction.setPayeeLastName(payeeLastName);

    if (!payeeEmailAddres.equals("")) {
      payDirectTransaction.setPayeeEmailAddress(payeeEmailAddres);
    }

    payDirectTransaction.setPayeeState(payeeState);
    payDirectTransaction.setAccountType(accountType);
    payDirectTransaction.setAccountFullName(accountFullName);
    payDirectTransaction.setRoutingNumber(routingNumber);
    payDirectTransaction.setAccountNumber(accountNumber);
    payDirectTransaction.setTotalAmount(totalAmount);
    payDirectTransaction.setSaveAccount(saveAccount);

    transactionList.add(payDirectTransaction);
    transactions.setTransactions(transactionList);
    payLeaseGatewayRequest.setTransactions(transactions);

    return payLeaseGatewayRequest;
  }
}
