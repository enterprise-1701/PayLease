package com.paylease.app.qa.framework.api.gapi;

import java.util.HashMap;

public class RemovePayeeAccountTransaction extends GapiTransaction {

  private static final String ACTION_NAME = "RemovePayeeAccount";

  public static final String PAYEE_REFERENCE_ID = "PayeeReferenceId";
  public static final String GATEWAY_PAYEE_ID = "GatewayPayeeId";

  /**
   * Creates a RemovePayeeAccount transaction with the given element values.
   *
   * @param parameters Values for transaction-specific elements
   */
  public RemovePayeeAccountTransaction(HashMap<String, String> parameters) {
    super(parameters);

    addElementName(PAYEE_REFERENCE_ID);
    addElementName(GATEWAY_PAYEE_ID);

    setRootActionName(ACTION_NAME);
  }
}
