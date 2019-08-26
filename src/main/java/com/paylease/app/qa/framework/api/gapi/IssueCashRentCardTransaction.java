package com.paylease.app.qa.framework.api.gapi;

import java.util.HashMap;

public class IssueCashRentCardTransaction extends GapiTransaction {

  private static final String ACTION_NAME = "IssueCashRentCard";

  public static final String PAYER_REFERENCE_ID = "PayerReferenceId";
  public static final String PAYER_FIRST_NAME = "PayerFirstName";
  public static final String PAYER_LAST_NAME = "PayerLastName";

  /**
   * Creates an IssueCashRentCard transaction with the given element values.
   *
   * @param parameters Values for transaction-specific elements
   */
  public IssueCashRentCardTransaction(HashMap<String, String> parameters) {
    super(parameters);

    addElementName(PAYER_REFERENCE_ID);
    addElementName(PAYER_FIRST_NAME);
    addElementName(PAYER_LAST_NAME);

    setRootActionName(ACTION_NAME);
  }
}
