package com.paylease.app.qa.framework.api.gapi;

import java.util.HashMap;

public class TransactionSearchTransaction extends GapiTransaction {

  private static final String ACTION_NAME = "TransactionSearch";

  public static final String TRANSACTION_TYPE = "TransactionType";
  public static final String NUMBER_OF_ITEMS = "NumberOfItems";
  public static final String ALWAYS_SHOW_CURRENCY = "AlwaysShowCurrency";

  /**
   * Creates a TransactionSearch transaction with the given element values.
   *
   * @param parameters Values for transaction-specific elements
   */
  public TransactionSearchTransaction(HashMap<String, String> parameters) {
    super(parameters);

    addElementName(TRANSACTION_TYPE);
    addElementName(NUMBER_OF_ITEMS);
    addElementName(ALWAYS_SHOW_CURRENCY);

    setRootActionName(ACTION_NAME);
  }
}
