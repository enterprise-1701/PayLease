package com.paylease.app.qa.api.tests.gapi.testcase;

import com.paylease.app.qa.api.tests.ExpectedResponse;
import com.paylease.app.qa.framework.api.Request;
import com.paylease.app.qa.framework.api.gapi.GapiTransaction;
import com.paylease.app.qa.framework.api.gapi.RemovePayerAccountTransaction;
import java.util.HashMap;

public class RemovePayerAccount extends BasicTestCase {

  private String payerReferenceId;
  private String gatewayPayerId;

  /**
   * Create an empty test case for RemovePayerAccount.
   *
   * @param payerReferenceId Key for this test case (PayerReferenceId)
   * @param summary Message for this test case
   * @param expectedResponse Expected response to be returned by GAPI
   * @param gatewayPayerId GatewayPayerId given by test setup
   */
  public RemovePayerAccount(
      String payerReferenceId, String summary, ExpectedResponse expectedResponse,
      String gatewayPayerId
  ) {
    super(summary, expectedResponse);

    this.payerReferenceId = payerReferenceId;

    this.gatewayPayerId = gatewayPayerId;
  }

  @Override
  public void addTransaction(Request apiRequest) {
    HashMap<String, String> parameters = new HashMap<>();

    parameters.put(RemovePayerAccountTransaction.PAYER_REFERENCE_ID, payerReferenceId);
    parameters.put(RemovePayerAccountTransaction.GATEWAY_PAYER_ID, gatewayPayerId);

    GapiTransaction transaction = new RemovePayerAccountTransaction(parameters);
    apiRequest.addTransaction(transaction);
  }
}
