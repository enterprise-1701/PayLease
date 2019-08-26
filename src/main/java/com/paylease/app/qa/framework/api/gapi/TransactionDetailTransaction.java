package com.paylease.app.qa.framework.api.gapi;

import java.util.HashMap;

public class TransactionDetailTransaction extends GapiTransaction {

  private static final String ACTION_NAME = "TransactionDetail";

  public static final String TRANSACTION_ID = "TransactionId";
  public static final String PAYMENT_REFERENCE_ID = "PaymentReferenceId";
  public static final String ALWAYS_SHOW_CURRENCY = "AlwaysShowCurrency";

  /**
   * Creates a TransactionDetail transaction with the given element values.
   *
   * @param parameters Values for transaction-specific elements
   */
  public TransactionDetailTransaction(HashMap<String, String> parameters) {
    super(parameters);

    addElementName(TRANSACTION_ID);
    addElementName(PAYMENT_REFERENCE_ID);
    addElementName(ALWAYS_SHOW_CURRENCY);

    setRootActionName(ACTION_NAME);
  }
}
