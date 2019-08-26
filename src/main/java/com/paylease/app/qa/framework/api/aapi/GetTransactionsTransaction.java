package com.paylease.app.qa.framework.api.aapi;

import java.util.HashMap;

public class GetTransactionsTransaction extends AapiTransaction {

  private static final String ACTION_NAME = "GetTransactions";

  public static final String DATE = "Date";
  public static final String STATUS = "Status";

  /**
   * Creates a GetTransactions transaction with the given element values.
   *
   * @param parameters Values for transaction-specific elements
   */
  public GetTransactionsTransaction(HashMap<String, String> parameters) {
    super(parameters);

    setRootActionAttribute(ACTION_NAME);

    addElementName(DATE);
    addElementName(STATUS);
  }
}
