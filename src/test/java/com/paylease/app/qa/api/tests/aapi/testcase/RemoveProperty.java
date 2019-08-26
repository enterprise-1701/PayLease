package com.paylease.app.qa.api.tests.aapi.testcase;

import com.paylease.app.qa.api.tests.ExpectedResponse;
import com.paylease.app.qa.framework.api.Request;
import com.paylease.app.qa.framework.api.aapi.AapiTransaction;
import com.paylease.app.qa.framework.api.aapi.RemovePropertyTransaction;
import java.util.HashMap;

public class RemoveProperty extends BasicTestCase {

  protected String propertyId;

  /**
   * Create a valid RemoveProperty test case with random valid values for required fields.
   *
   * @param summary Summary of this test case
   * @param expectedResponse response expected to be returned by AAPI
   * @param propertyId propertyId
   */
  public RemoveProperty(String summary, ExpectedResponse expectedResponse, String propertyId) {
    super(summary, expectedResponse);

    this.propertyId = propertyId;
  }

  @Override
  public void addTransaction(Request apiRequest) {
    HashMap<String, String> parameters = getTransactionParameters();

    parameters.put(RemovePropertyTransaction.PROPERTY_ID, propertyId);

    AapiTransaction transaction = new RemovePropertyTransaction(parameters);
    apiRequest.addTransaction(transaction);
  }
}
