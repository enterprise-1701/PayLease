package com.paylease.app.qa.framework.api.aapi;

import java.util.HashMap;

public class GetTenantsTransaction extends AapiTransaction {

  private static final String ACTION_NAME = "GetTenants";

  public static final String LOCATION_ID = "LocationID";
  public static final String TIMESTAMP = "Timestamp";

  /**
   * Creates a GetTenants transaction with the given element values.
   *
   * @param parameters Values for transaction-specific elements
   */
  public GetTenantsTransaction(HashMap<String, String> parameters) {
    super(parameters);

    setRootActionAttribute(ACTION_NAME);

    addElementName(LOCATION_ID);
    addElementName(TIMESTAMP);
  }
}
