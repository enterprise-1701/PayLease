package com.paylease.app.qa.api.tests.aapi.testcase;

import com.paylease.app.qa.api.tests.ExpectedResponse;
import com.paylease.app.qa.framework.api.Request;
import com.paylease.app.qa.framework.api.aapi.AapiTransaction;
import com.paylease.app.qa.framework.api.aapi.GetAutoPaysTransaction;
import java.util.HashMap;

public class GetAutoPays extends BasicTestCase {

  protected String residentReferenceId;
  protected String status;

  /**
   * Create a valid GetAutoPays test case with random valid values for required fields.
   *
   * @param summary Summary of this test case
   * @param expectedResponse response expected to be returned by AAPI
   * @param residentReferenceId residentReferenceId
   * @param status status
   */
  public GetAutoPays(String summary, ExpectedResponse expectedResponse, String residentReferenceId,
      String status) {
    super(summary, expectedResponse);

    this.residentReferenceId = residentReferenceId;
    this.status = status;
  }

  @Override
  public void addTransaction(Request apiRequest) {
    HashMap<String, String> parameters = getTransactionParameters();

    parameters.put(GetAutoPaysTransaction.RESIDENT_REFERENCE_ID, residentReferenceId);
    parameters.put(GetAutoPaysTransaction.STATUS, status);

    AapiTransaction transaction = new GetAutoPaysTransaction(parameters);
    apiRequest.addTransaction(transaction);
  }
}
