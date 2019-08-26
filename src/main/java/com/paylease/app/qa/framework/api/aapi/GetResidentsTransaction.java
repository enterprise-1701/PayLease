package com.paylease.app.qa.framework.api.aapi;

import java.util.HashMap;

public class GetResidentsTransaction extends AapiTransaction {

  private static final String ACTION_NAME = "GetResidents";

  public static final String PROPERTY_REFERENCE_ID = "PropertyReferenceID";
  public static final String RESIDENT_STATUS = "ResidentStatus";
  public static final String RESIDENT_ID = "ResidentID";
  public static final String SECONDARY_RESIDENT_ID = "SecondaryResidentID";

  /**
   * Creates a GetResidents transaction with the given element values.
   *
   * @param parameters Values for transaction-specific elements
   */
  public GetResidentsTransaction(HashMap<String, String> parameters) {
    super(parameters);

    setRootActionAttribute(ACTION_NAME);

    addElementName(PROPERTY_REFERENCE_ID);
    addElementName(RESIDENT_STATUS);
    addElementName(RESIDENT_ID);
    addElementName(SECONDARY_RESIDENT_ID);
  }
}
