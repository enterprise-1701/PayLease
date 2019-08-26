package com.paylease.app.qa.api.tests.aapi.testcase;

import com.paylease.app.qa.api.tests.BaseApiTest;
import com.paylease.app.qa.api.tests.ExpectedResponse;
import com.paylease.app.qa.framework.api.Request;
import com.paylease.app.qa.framework.api.aapi.AapiTransaction;
import com.paylease.app.qa.framework.api.aapi.GetVariableAutoPaysTransaction;
import java.util.HashMap;

public class GetVariableAutoPays extends BasicTestCase {

  protected String residentReferenceId;
  protected String status;

  public GetVariableAutoPays(String summary, ExpectedResponse expectedResponse, String residentReferenceId, String status) {
    super(summary, expectedResponse);

    this.residentReferenceId = residentReferenceId;
    this.status = status;
  }

  @Override
  public void addTransaction(Request apiRequest) {
    HashMap<String, String> parameters = getTransactionParameters();

    parameters.put(GetVariableAutoPaysTransaction.RESIDENT_REFERENCE_ID, residentReferenceId);
    parameters.put(GetVariableAutoPaysTransaction.STATUS, status);

    AapiTransaction transaction = new GetVariableAutoPaysTransaction(parameters);
    apiRequest.addTransaction(transaction);
  }
}
