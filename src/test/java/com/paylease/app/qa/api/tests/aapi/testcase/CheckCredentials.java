package com.paylease.app.qa.api.tests.aapi.testcase;

import com.paylease.app.qa.api.tests.ExpectedResponse;
import com.paylease.app.qa.framework.api.Request;
import com.paylease.app.qa.framework.api.aapi.AapiTransaction;
import com.paylease.app.qa.framework.api.aapi.CheckCredentialsTransaction;
import java.util.HashMap;

public class CheckCredentials extends BasicTestCase {

  /**
   * Create a valid test case with random valid values for required fields.
   *
   * @param summary Summary of this test case
   * @param expectedResponse response expected to be returned by AAPI
   */
  public CheckCredentials(String summary, ExpectedResponse expectedResponse) {
    super(summary, expectedResponse);
  }

  @Override
  public void addTransaction(Request apiRequest) {
    HashMap<String, String> parameters = getTransactionParameters();
    AapiTransaction transaction = new CheckCredentialsTransaction(parameters);
    apiRequest.addTransaction(transaction);
  }
}
