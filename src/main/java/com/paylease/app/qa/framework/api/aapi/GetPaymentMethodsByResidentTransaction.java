package com.paylease.app.qa.framework.api.aapi;

import java.util.HashMap;

public class GetPaymentMethodsByResidentTransaction extends AapiTransaction {

  private static final String ACTION_NAME = "GetPaymentMethodsByResident";

  public static final String RESIDENT_ID = "ResidentID";
  public static final String CREATED_SINCE = "CreatedSince";

  /**
   * Creates a GetPaymentMethodsByResident transaction with the given element values.
   *
   * @param parameters Values for transaction-specific elements
   */
  public GetPaymentMethodsByResidentTransaction(HashMap<String, String> parameters) {
    super(parameters);

    setRootActionAttribute(ACTION_NAME);

    addElementName(RESIDENT_ID);
    addElementName(CREATED_SINCE);
  }
}
