package com.paylease.app.qa.api.tests.gapiNew;

import static com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest.TEST_MODE;
import static com.paylease.app.qa.framework.newApi.newGapi.FeeStructureTransaction.FEE_STRUCTURE_TRANSACTION;

import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.newApi.Credentials;
import com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest;
import com.paylease.app.qa.framework.newApi.Transaction;
import com.paylease.app.qa.framework.newApi.Transactions;
import com.paylease.app.qa.framework.newApi.newGapi.FeeStructureTransaction;
import java.util.ArrayList;
import java.util.List;

public class NewFeeStructureTest extends ScriptBase {

  private static final String REGION = "gapi";
  private static final String FEATURE = "feeStructure";

  private PayLeaseGatewayRequest buildRequest(Credentials credentials, String showIncurredFees) {

    PayLeaseGatewayRequest payLeaseGatewayRequest = new PayLeaseGatewayRequest();
    payLeaseGatewayRequest.setCredentials(credentials);
    payLeaseGatewayRequest.setMode(TEST_MODE);

    Transactions transactions = new Transactions();
    List<Transaction> transactionList = new ArrayList<>();

    FeeStructureTransaction feeStructureTransaction = new FeeStructureTransaction();
    feeStructureTransaction.setTransactionAction(FEE_STRUCTURE_TRANSACTION);

    if (!showIncurredFees.equals("")) {
      feeStructureTransaction.setShowIncurredFees(showIncurredFees);
    }

    transactionList.add(feeStructureTransaction);
    transactions.setTransactions(transactionList);
    payLeaseGatewayRequest.setTransactions(transactions);

    return payLeaseGatewayRequest;
  }

}
