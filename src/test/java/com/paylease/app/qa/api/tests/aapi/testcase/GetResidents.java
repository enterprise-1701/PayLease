package com.paylease.app.qa.api.tests.aapi.testcase;

import com.paylease.app.qa.api.tests.ExpectedResponse;
import com.paylease.app.qa.framework.api.Request;
import com.paylease.app.qa.framework.api.aapi.AapiTransaction;
import com.paylease.app.qa.framework.api.aapi.GetResidentsTransaction;
import java.util.HashMap;

public class GetResidents extends BasicTestCase {

  private String propertyReferenceId;
  private String residentStatus;
  private String residentId;
  private String secondaryResidentId;

  /**
   * Create a basic GetResidents test case with given values.
   *
   * @param summary Summary of this test case
   * @param expectedResponse response expected to be returned by AAPI
   * @param propertyReferenceId propertyReferenceId
   * @param residentId residentId
   * @param secondaryResidentId secondaryResidentId
   * @param residentStatus residentStatus
   */
  public GetResidents(String summary, ExpectedResponse expectedResponse,
      String propertyReferenceId, String residentId, String secondaryResidentId,
      String residentStatus) {
    super(summary, expectedResponse);

    this.propertyReferenceId = propertyReferenceId;
    this.residentId = residentId;
    this.secondaryResidentId = secondaryResidentId;
    this.residentStatus = residentStatus;
  }

  @Override
  public void addTransaction(Request apiRequest) {
    HashMap<String, String> parameters = getTransactionParameters();

    parameters.put(GetResidentsTransaction.PROPERTY_REFERENCE_ID, propertyReferenceId);
    parameters.put(GetResidentsTransaction.RESIDENT_STATUS, residentStatus);
    parameters.put(GetResidentsTransaction.RESIDENT_ID, residentId);
    parameters.put(GetResidentsTransaction.SECONDARY_RESIDENT_ID, secondaryResidentId);

    AapiTransaction transaction = new GetResidentsTransaction(parameters);
    apiRequest.addTransaction(transaction);
  }
}
