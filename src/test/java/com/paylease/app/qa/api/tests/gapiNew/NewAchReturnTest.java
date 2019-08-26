package com.paylease.app.qa.api.tests.gapiNew;

import static com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest.TEST_MODE;
import static com.paylease.app.qa.framework.newApi.newGapi.AchReturnsTransaction.ACH_RETURN_TRANSACTION;

import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.newApi.Credentials;
import com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest;
import com.paylease.app.qa.framework.newApi.Transaction;
import com.paylease.app.qa.framework.newApi.Transactions;
import com.paylease.app.qa.framework.newApi.newGapi.AchReturnsTransaction;
import java.util.ArrayList;
import java.util.List;

public class NewAchReturnTest extends ScriptBase {

  private static final String REGION = "gapi";
  private static final String FEATURE = "achReturn";

  private PayLeaseGatewayRequest buildRequest(Credentials credentials, String searchStartDate,
      String searchEndDate, String timeFrame) {
    PayLeaseGatewayRequest payLeaseGatewayRequest = new PayLeaseGatewayRequest();
    payLeaseGatewayRequest.setCredentials(credentials);
    payLeaseGatewayRequest.setMode(TEST_MODE);

    Transactions transactions = new Transactions();
    List<Transaction> transactionList = new ArrayList<>();

    AchReturnsTransaction achReturnsTransaction = new AchReturnsTransaction();
    achReturnsTransaction.setTransactionAction(ACH_RETURN_TRANSACTION);

    if (!searchStartDate.equals("")) {
      achReturnsTransaction.setSearchStartDate(searchStartDate);
    }

    if (!searchEndDate.equals("")) {
      achReturnsTransaction.setSearchEndDate(searchEndDate);
    }

    if (!timeFrame.equals("")) {
      achReturnsTransaction.setTimeFrame(timeFrame);
    }

    transactionList.add(achReturnsTransaction);
    transactions.setTransactions(transactionList);
    payLeaseGatewayRequest.setTransactions(transactions);

    return payLeaseGatewayRequest;
  }

}
