package com.paylease.app.qa.api.tests.aapi.testcase;

import com.paylease.app.qa.api.tests.ExpectedResponse;
import com.paylease.app.qa.framework.api.Request;
import com.paylease.app.qa.framework.api.aapi.AapiTransaction;
import com.paylease.app.qa.framework.api.aapi.GetTransactionsTransaction;
import java.util.HashMap;

public class GetTransactions extends BasicTestCase {

  protected String date;
  protected String status;

  /**
   * Create a valid GetTransactions test case with random valid values for required fields.
   *
   * @param summary Summary of this test case
   * @param expectedResponse response expected to be returned by AAPI
   * @param date date
   * @param status status
   */
  public GetTransactions(String summary, ExpectedResponse expectedResponse, String date, String status) {
    super(summary, expectedResponse);

    this.date = date;
    this.status = status;
  }

  @Override
  public void addTransaction(Request apiRequest) {
    HashMap<String, String> parameters = getTransactionParameters();

    parameters.put(GetTransactionsTransaction.DATE, date);
    parameters.put(GetTransactionsTransaction.STATUS, status);

    AapiTransaction transaction = new GetTransactionsTransaction(parameters);
    apiRequest.addTransaction(transaction);
  }
}
