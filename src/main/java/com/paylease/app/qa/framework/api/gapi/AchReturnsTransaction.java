package com.paylease.app.qa.framework.api.gapi;

import java.util.HashMap;

public class AchReturnsTransaction extends GapiTransaction {

  private static final String ACTION_NAME = "ACHReturns";

  public static final String SEARCH_START_DATE = "SearchStartDate";
  public static final String SEARCH_END_DATE = "SearchEndDate";
  public static final String TIME_FRAME = "TimeFrame";

  /**
   * Creates a DepositsByDateRange transaction with the given element values.
   *
   * @param parameters Values for transaction-specific elements
   */
  public AchReturnsTransaction(HashMap<String, String> parameters) {
    super(parameters);

    addElementName(SEARCH_START_DATE);
    addElementName(SEARCH_END_DATE);
    addElementName(TIME_FRAME);

    setRootActionName(ACTION_NAME);
  }

}
