package com.paylease.app.qa.framework.api.gapi;

import java.util.HashMap;

public class RemovePayerAccountTransaction extends GapiTransaction {

  private static final String ACTION_NAME = "RemovePayerAccount";

  public static final String PAYER_REFERENCE_ID = "PayerReferenceId";
  public static final String GATEWAY_PAYER_ID = "GatewayPayerId";

  /**
   * Creates a RemovePayerAccount transaction with the given element values.
   *
   * @param parameters Values for transaction-specific elements
   */
  public RemovePayerAccountTransaction(HashMap<String, String> parameters) {
    super(parameters);

    addElementName(PAYER_REFERENCE_ID);
    addElementName(GATEWAY_PAYER_ID);

    setRootActionName(ACTION_NAME);
  }
}
