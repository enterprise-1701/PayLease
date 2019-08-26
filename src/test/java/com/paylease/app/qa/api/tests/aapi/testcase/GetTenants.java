package com.paylease.app.qa.api.tests.aapi.testcase;

import com.paylease.app.qa.api.tests.ExpectedResponse;
import com.paylease.app.qa.framework.api.Request;
import com.paylease.app.qa.framework.api.aapi.AapiTransaction;
import com.paylease.app.qa.framework.api.aapi.GetTenantsTransaction;
import java.util.HashMap;

public class GetTenants extends BasicTestCase {

  protected String locationId;
  protected String timestamp;

  /**
   * Create a valid GetTenants test case with random valid values for required fields.
   *
   * @param summary Summary of this test case
   * @param expectedResponse response expected to be returned by AAPI
   * @param locationId locationId
   */
  public GetTenants(String summary, ExpectedResponse expectedResponse, String locationId) {
    super(summary, expectedResponse);

    this.locationId = locationId;
    this.timestamp = String.valueOf(System.currentTimeMillis() / 1000L);
  }

  @Override
  public void addTransaction(Request apiRequest) {
    HashMap<String, String> parameters = getTransactionParameters();

    parameters.put(GetTenantsTransaction.LOCATION_ID, locationId);
    parameters.put(GetTenantsTransaction.TIMESTAMP, timestamp);

    AapiTransaction transaction = new GetTenantsTransaction(parameters);
    apiRequest.addTransaction(transaction);
  }
}
