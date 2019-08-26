package com.paylease.app.qa.framework.api.aapi;

import java.util.HashMap;

public class GetAutoPaysTransaction extends AapiTransaction {

  private static final String ACTION_NAME = "GetAutoPays";

  public static final String RESIDENT_REFERENCE_ID = "ResidentReferenceID";
  public static final String STATUS = "Status";

  /**
   * Creates a GetAutoPays transaction with the given element values.
   *
   * @param parameters Values for transaction-specific elements
   */
  public GetAutoPaysTransaction(HashMap<String, String> parameters) {
    super(parameters);

    setRootActionAttribute(ACTION_NAME);

    addElementName(RESIDENT_REFERENCE_ID);
    addElementName(STATUS);
  }
}
