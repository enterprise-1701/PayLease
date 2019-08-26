package com.paylease.app.qa.framework.api.aapi;

import java.util.HashMap;

public class RemovePropertyTransaction extends AapiTransaction {
  private static final String ACTION_NAME = "RemoveProperty";

  public static final String PROPERTY_ID = "PropertyID";

  /**
   * Creates a RemoveProperty transaction with the given element values.
   *
   * @param parameters Values for transaction-specific elements
   */
  public RemovePropertyTransaction(HashMap<String, String> parameters) {
    super(parameters);

    setRootActionAttribute(ACTION_NAME);

    addElementName(PROPERTY_ID);
  }
}
