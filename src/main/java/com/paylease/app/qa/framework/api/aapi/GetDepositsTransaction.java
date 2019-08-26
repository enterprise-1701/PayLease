package com.paylease.app.qa.framework.api.aapi;

import java.util.HashMap;

public class GetDepositsTransaction extends AapiTransaction {

  private static final String ACTION_NAME = "GetDeposits";

  public static final String PROPERTY_REFERENCE_ID = "PropertyReferenceId";
  public static final String START_DATE = "StartDate";
  public static final String END_DATE = "EndDate";

  /**
   * Creates a GetDeposits transaction with the given element values.
   *
   * @param parameters Values for transaction-specific elements
   */
  public GetDepositsTransaction(HashMap<String, String> parameters) {
    super(parameters);

    setRootActionAttribute(ACTION_NAME);

    addElementName(PROPERTY_REFERENCE_ID);
    addElementName(START_DATE);
    addElementName(END_DATE);
  }
}
