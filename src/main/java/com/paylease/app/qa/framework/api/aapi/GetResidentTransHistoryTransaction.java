package com.paylease.app.qa.framework.api.aapi;

import java.util.HashMap;

public class GetResidentTransHistoryTransaction extends AapiTransaction {

  private static final String ACTION_NAME = "GetResidentTransactionHistory";

  public static final String RESIDENT_ID = "ResidentID";

  /**
   * Creates a GetResidentsTransactionHistory transaction with the given element values.
   *
   * @param parameters Values for transaction-specific elements
   */
  public GetResidentTransHistoryTransaction(HashMap<String, String> parameters) {
    super(parameters);

    setRootActionAttribute(ACTION_NAME);

    addElementName(RESIDENT_ID);
  }
}
