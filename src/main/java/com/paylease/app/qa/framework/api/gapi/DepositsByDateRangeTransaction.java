package com.paylease.app.qa.framework.api.gapi;

import java.util.HashMap;

public class DepositsByDateRangeTransaction extends GapiTransaction {

  private static final String ACTION_NAME = "DepositsByDateRange";

  public static final String SEARCH_START_DATE = "SearchStartDate";
  public static final String SEARCH_END_DATE = "SearchEndDate";
  public static final String CURRENCY_CODE = "CurrencyCode";

  /**
   * Creates a DepositsByDateRange transaction with the given element values.
   *
   * @param parameters Values for transaction-specific elements
   */
  public DepositsByDateRangeTransaction(HashMap<String, String> parameters) {
    super(parameters);

    addElementName(SEARCH_START_DATE);
    addElementName(SEARCH_END_DATE);
    addElementName(CURRENCY_CODE);

    setRootActionName(ACTION_NAME);
  }
}
