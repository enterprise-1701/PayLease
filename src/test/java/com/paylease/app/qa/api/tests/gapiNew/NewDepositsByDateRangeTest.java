package com.paylease.app.qa.api.tests.gapiNew;

import static com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest.TEST_MODE;
import static com.paylease.app.qa.framework.newApi.newGapi.DepositsByDateRangeTransaction.DEPOSITS_BY_DATE_RANGE_TRANSACTION;

import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.newApi.Credentials;
import com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest;
import com.paylease.app.qa.framework.newApi.Transaction;
import com.paylease.app.qa.framework.newApi.Transactions;
import com.paylease.app.qa.framework.newApi.newGapi.DepositsByDateRangeTransaction;
import java.util.ArrayList;
import java.util.List;

public class NewDepositsByDateRangeTest extends ScriptBase {

  private static final String REGION = "gapi";
  private static final String FEATURE = "depositByDateRange";

  private PayLeaseGatewayRequest buildRequest(Credentials credentials, String searchStartDate,
      String searchEndDate, String currencyCode) {

    PayLeaseGatewayRequest payLeaseGatewayRequest = new PayLeaseGatewayRequest();
    payLeaseGatewayRequest.setCredentials(credentials);
    payLeaseGatewayRequest.setMode(TEST_MODE);

    Transactions transactions = new Transactions();
    List<Transaction> transactionList = new ArrayList<>();

    DepositsByDateRangeTransaction depositsByDateRangeTransaction = new DepositsByDateRangeTransaction();
    depositsByDateRangeTransaction.setTransactionAction(DEPOSITS_BY_DATE_RANGE_TRANSACTION);
    depositsByDateRangeTransaction.setSearchStartDate(searchStartDate);
    depositsByDateRangeTransaction.setSearchEndDate(searchEndDate);

    if (!currencyCode.equals("")) {
      depositsByDateRangeTransaction.setCurrencyCode(currencyCode);
    }

    transactionList.add(depositsByDateRangeTransaction);
    transactions.setTransactions(transactionList);
    payLeaseGatewayRequest.setTransactions(transactions);

    return payLeaseGatewayRequest;
  }

}
