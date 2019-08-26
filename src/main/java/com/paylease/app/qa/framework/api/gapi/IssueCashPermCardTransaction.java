package com.paylease.app.qa.framework.api.gapi;

import java.util.HashMap;

public class IssueCashPermCardTransaction extends GapiTransaction {

  private static final String ACTION_NAME = "IssueCashPermCard";

  public static final String PAYER_REFERENCE_ID = "PayerReferenceId";
  public static final String PAYER_FIRST_NAME = "PayerFirstName";
  public static final String PAYER_LAST_NAME = "PayerLastName";
  public static final String CARD_NUMBER = "CardNumber";

  /**
   * Creates an IssueCashPermCard transaction with the given element values.
   *
   * @param parameters Values for transaction-specific elements
   */
  public IssueCashPermCardTransaction(HashMap<String, String> parameters) {
    super(parameters);

    addElementName(PAYER_REFERENCE_ID);
    addElementName(PAYER_FIRST_NAME);
    addElementName(PAYER_LAST_NAME);
    addElementName(CARD_NUMBER);

    setRootActionName(ACTION_NAME);
  }
}
