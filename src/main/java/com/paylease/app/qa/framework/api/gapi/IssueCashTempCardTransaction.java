package com.paylease.app.qa.framework.api.gapi;

import java.util.HashMap;

public class IssueCashTempCardTransaction extends GapiTransaction {

  private static final String ACTION_NAME = "IssueCashTempCard";

  public static final String PAYER_REFERENCE_ID = "PayerReferenceId";
  public static final String PAYER_FIRST_NAME = "PayerFirstName";
  public static final String PAYER_LAST_NAME = "PayerLastName";

  /**
   * Creates an IssueCashTempCard transaction with the given element values.
   *
   * @param parameters Values for transaction-specific elements
   */
  public IssueCashTempCardTransaction(HashMap<String, String> parameters) {
    super(parameters);

    addElementName(PAYER_REFERENCE_ID);
    addElementName(PAYER_FIRST_NAME);
    addElementName(PAYER_LAST_NAME);

    setRootActionName(ACTION_NAME);
  }
}
