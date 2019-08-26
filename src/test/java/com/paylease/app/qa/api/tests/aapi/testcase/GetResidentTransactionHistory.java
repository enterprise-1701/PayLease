package com.paylease.app.qa.api.tests.aapi.testcase;

import com.paylease.app.qa.api.tests.ExpectedResponse;
import com.paylease.app.qa.framework.api.Request;
import com.paylease.app.qa.framework.api.aapi.AapiTransaction;
import com.paylease.app.qa.framework.api.aapi.GetResidentTransHistoryTransaction;
import java.util.HashMap;

public class GetResidentTransactionHistory extends BasicTestCase {

  private String residentId;

  /**
   * Create a basic GetResidentTransactionHistory test case with given values.
   *
   * @param summary Summary of this test case
   * @param expectedResponse response expected to be returned by AAPI
   * @param residentId residentId
   */
  public GetResidentTransactionHistory(String summary, ExpectedResponse expectedResponse,
      String residentId) {
    super(summary, expectedResponse);

    this.residentId = residentId;

  }

  @Override
  public void addTransaction(Request apiRequest) {
    HashMap<String, String> parameters = getTransactionParameters();

    parameters.put(GetResidentTransHistoryTransaction.RESIDENT_ID, residentId);

    AapiTransaction transaction = new GetResidentTransHistoryTransaction(parameters);
    apiRequest.addTransaction(transaction);
  }
}
