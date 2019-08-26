package com.paylease.app.qa.api.tests.gapiNew;

import static com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest.TEST_MODE;
import static com.paylease.app.qa.framework.newApi.newGapi.CreditReportingOptOutTransaction.CREDIT_REPORTING_OPT_OUT_TRANSACTION;

import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.newApi.Credentials;
import com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest;
import com.paylease.app.qa.framework.newApi.Transaction;
import com.paylease.app.qa.framework.newApi.Transactions;
import com.paylease.app.qa.framework.newApi.newGapi.CreditReportingOptOutTransaction;
import java.util.ArrayList;
import java.util.List;

public class NewCreditReportingOptOut extends ScriptBase {

  private static final String REGION = "gapi";
  private static final String FEATURE = "creditReportingOptOut";

  private PayLeaseGatewayRequest buildRequest(Credentials credentials, String creditReportingId) {
    PayLeaseGatewayRequest payLeaseGatewayRequest = new PayLeaseGatewayRequest();
    payLeaseGatewayRequest.setCredentials(credentials);
    payLeaseGatewayRequest.setMode(TEST_MODE);

    Transactions transactions = new Transactions();
    List<Transaction> transactionList = new ArrayList<>();

    CreditReportingOptOutTransaction creditReportingOptOutTransaction = new CreditReportingOptOutTransaction();

    creditReportingOptOutTransaction.setTransactionAction(CREDIT_REPORTING_OPT_OUT_TRANSACTION);
    creditReportingOptOutTransaction.setCreditReportingId(creditReportingId);

    transactionList.add(creditReportingOptOutTransaction);
    transactions.setTransactions(transactionList);
    payLeaseGatewayRequest.setTransactions(transactions);

    return payLeaseGatewayRequest;
  }
}
