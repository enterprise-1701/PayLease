package com.paylease.app.qa.framework.api.gapi;

import java.util.HashMap;

public class UpdateIntegrationIdByTransactionTransaction extends GapiTransaction {

  private static final String ACTION_NAME = "UpdateIntegrationIdByTransaction";

  public static final String TRANSACTION_ID = "TransactionId";
  public static final String INTEGRATION_ID = "IntegrationId";
  public static final String SECONDARY_INTEGRATION_ID = "SecondaryIntegrationId";

  /**
   * Creates an UpdateIntegrationIdByTransaction transaction with the given element values.
   *
   * @param parameters Values for transaction-specific elements
   */
  public UpdateIntegrationIdByTransactionTransaction(HashMap<String, String> parameters) {
    super(parameters);

    addElementName(TRANSACTION_ID);
    addElementName(INTEGRATION_ID);
    addElementName(SECONDARY_INTEGRATION_ID);

    setRootActionName(ACTION_NAME);
  }
}
