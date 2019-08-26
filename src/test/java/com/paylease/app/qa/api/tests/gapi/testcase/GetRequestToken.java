package com.paylease.app.qa.api.tests.gapi.testcase;

import com.paylease.app.qa.api.tests.ExpectedResponse;
import com.paylease.app.qa.framework.api.Request;
import com.paylease.app.qa.framework.api.gapi.GapiTransaction;
import com.paylease.app.qa.framework.api.gapi.GetRequestTokenTransaction;
import java.util.HashMap;

public class GetRequestToken extends BasicTestCase {

  /**
   * Create test case for GetRequestToken.
   *
   * @param summary summary of this test case
   * @param expectedResponse response expected to be returned by GAPI
   */
  public GetRequestToken(
      String summary, ExpectedResponse expectedResponse
  ) {
    super(summary, expectedResponse);
  }

  @Override
  public void addTransaction(Request apiRequest) {
    HashMap<String, String> parameters = new HashMap<>();

    GapiTransaction transaction = new GetRequestTokenTransaction(parameters);
    apiRequest.addTransaction(transaction);
  }
}
