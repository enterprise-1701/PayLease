package com.paylease.app.qa.framework.api.gapi;

import java.util.HashMap;

public class TransactionSearchDateTransaction extends GapiTransaction {

  private static final String ACTION_NAME = "TransactionSearchDate";

  public static final String SEARCH_START_DATE = "SearchStartDate";
  public static final String SEARCH_END_DATE = "SearchEndDate";
  public static final String NUMBER_OF_ITEMS = "NumberOfItems";
  public static final String ALWAYS_SHOW_CURRENCY = "AlwaysShowCurrency";

  /**
   * Creates a TransactionSearchDate transaction with the given element values.
   *
   * @param parameters Values for transaction-specific elements
   */
  public TransactionSearchDateTransaction(HashMap<String, String> parameters) {
    super(parameters);

    addElementName(SEARCH_START_DATE);
    addElementName(SEARCH_END_DATE);
    addElementName(NUMBER_OF_ITEMS);
    addElementName(ALWAYS_SHOW_CURRENCY);

    setRootActionName(ACTION_NAME);
  }
}
