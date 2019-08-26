package com.paylease.app.qa.api.tests.aapi.testcase;

import com.paylease.app.qa.api.tests.ExpectedResponse;
import com.paylease.app.qa.framework.api.Request;
import com.paylease.app.qa.framework.api.aapi.AapiTransaction;
import com.paylease.app.qa.framework.api.aapi.PmSsoTransaction;
import java.util.HashMap;

public class PmSso extends BasicTestCase {

  private String pmId;

  /**
   * Create a valid PmSso test case with random valid values for required fields.
   *
   * @param summary Summary of this test case
   * @param expectedResponse response expected to be returned by AAPI
   */
  public PmSso(String summary, ExpectedResponse expectedResponse) {
    super(summary, expectedResponse);
  }

  public PmSso setPmId(String pmId) {
    this.pmId = pmId;
    return this;
  }

  @Override
  public void addTransaction(Request apiRequest) {
    HashMap<String, String> parameters = getTransactionParameters();

    parameters.putIfAbsent(PmSsoTransaction.PM_ID, pmId);

    AapiTransaction transaction = new PmSsoTransaction(parameters);
    apiRequest.addTransaction(transaction);
  }
}
