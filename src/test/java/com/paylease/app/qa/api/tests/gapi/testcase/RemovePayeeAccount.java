package com.paylease.app.qa.api.tests.gapi.testcase;

import com.paylease.app.qa.api.tests.ExpectedResponse;
import com.paylease.app.qa.framework.api.Request;
import com.paylease.app.qa.framework.api.gapi.GapiTransaction;
import com.paylease.app.qa.framework.api.gapi.RemovePayeeAccountTransaction;
import java.util.HashMap;

public class RemovePayeeAccount extends BasicTestCase {

  private String payeeReferenceId;
  private String gatewayPayeeId;

  /**
   * Create an empty test case for RemovePayeeAccount.
   *
   * @param payeeReferenceId Key for this test case (PayeeReferenceId)
   * @param summary Message for this test case
   * @param expectedResponse Expected respnose to be returned by GAPI
   * @param gatewayPayeeId GatewayPayeeId given by test setup
   */
  public RemovePayeeAccount(
      String payeeReferenceId, String summary, ExpectedResponse expectedResponse,
      String gatewayPayeeId
  ) {
    super(summary, expectedResponse);

    this.payeeReferenceId = payeeReferenceId;

    this.gatewayPayeeId = gatewayPayeeId;
  }

  @Override
  public void addTransaction(Request apiRequest) {
    HashMap<String, String> parameters = new HashMap<>();

    parameters.put(RemovePayeeAccountTransaction.PAYEE_REFERENCE_ID,payeeReferenceId);
    parameters.put(RemovePayeeAccountTransaction.GATEWAY_PAYEE_ID, gatewayPayeeId);

    GapiTransaction transaction = new RemovePayeeAccountTransaction(parameters);
    apiRequest.addTransaction(transaction);
  }
}
