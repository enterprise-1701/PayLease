package com.paylease.app.qa.api.tests.gapi.testcase;

import com.paylease.app.qa.api.tests.ExpectedResponse;
import com.paylease.app.qa.framework.api.Request;
import com.paylease.app.qa.framework.api.gapi.GapiTransaction;
import com.paylease.app.qa.framework.api.gapi.UpdateIntegrationIdByTransactionTransaction;
import java.util.HashMap;

public class UpdateIntegrationIdByTransaction extends BasicTestCase {

  private String transactionId;
  private String integrationId;

  public UpdateIntegrationIdByTransaction(
      String transactionId, String summary, ExpectedResponse expectedResponse
  ) {
    this(transactionId, summary, expectedResponse, null);
  }

  private UpdateIntegrationIdByTransaction(
      String transactionId, String summary, ExpectedResponse expectedResponse, String integrationId
  ) {
    super(summary, expectedResponse);

    this.transactionId = transactionId;
    this.integrationId = integrationId;
  }

  public UpdateIntegrationIdByTransaction setIntegrationId(String integrationId) {
    this.integrationId = integrationId;
    return this;
  }

  @Override
  public void addTransaction(Request apiRequest) {
    HashMap<String, String> parameters = new HashMap<>();

    parameters.put(UpdateIntegrationIdByTransactionTransaction.TRANSACTION_ID, transactionId);
    parameters.put(UpdateIntegrationIdByTransactionTransaction.INTEGRATION_ID, integrationId);

    GapiTransaction transaction = new UpdateIntegrationIdByTransactionTransaction(parameters);
    apiRequest.addTransaction(transaction);
  }
}
