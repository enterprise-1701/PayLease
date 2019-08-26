package com.paylease.app.qa.api.tests.gapi.testcase;

import com.paylease.app.qa.api.tests.ExpectedResponse;
import com.paylease.app.qa.framework.api.Request;
import com.paylease.app.qa.framework.api.gapi.GapiTransaction;
import com.paylease.app.qa.framework.api.gapi.TransactionVoidTransaction;
import java.util.HashMap;

public class TransactionVoid extends BasicTestCase {

  private String transactionId;

  /**
   * Create TransactionVoid test case.
   *
   * @param transactionId Key Value for this test case (TransactionId)
   * @param summary Summary for this test case
   * @param expectedResponse ExpectedResponse from GAPI
   */
  public TransactionVoid(
      String transactionId, String summary, ExpectedResponse expectedResponse
  ) {
    super(summary, expectedResponse);

    this.transactionId = transactionId;
  }

  @Override
  public void addTransaction(Request apiRequest) {
    HashMap<String, String> parameters = new HashMap<>();

    parameters.put(TransactionVoidTransaction.TRANSACTION_ID, transactionId);

    GapiTransaction transaction = new TransactionVoidTransaction(parameters);
    apiRequest.addTransaction(transaction);
  }
}
