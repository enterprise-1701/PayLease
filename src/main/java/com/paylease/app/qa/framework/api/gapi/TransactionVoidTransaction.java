package com.paylease.app.qa.framework.api.gapi;

import java.util.HashMap;

public class TransactionVoidTransaction extends GapiTransaction {

  private static final String ACTION_NAME = "TransactionVoid";

  public static final String TRANSACTION_ID = "TransactionId";

  /**
   * Creates a TransactionVoid transaction with the given element values.
   *
   * @param parameters Values for transaction-specific elements
   */
  public TransactionVoidTransaction(HashMap<String, String> parameters) {
    super(parameters);

    addElementName(TRANSACTION_ID);

    setRootActionName(ACTION_NAME);
  }
}
