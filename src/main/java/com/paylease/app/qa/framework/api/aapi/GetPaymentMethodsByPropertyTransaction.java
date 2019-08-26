package com.paylease.app.qa.framework.api.aapi;

import java.util.HashMap;

public class GetPaymentMethodsByPropertyTransaction extends AapiTransaction {

  private static final String ACTION_NAME = "GetPaymentMethodsByProperty";

  public static final String PROPERTY_REFERENCE_ID = "PropertyReferenceID";
  public static final String ACTIVE_PAYMENT_METHOD_RESIDENTS = "ActivePaymentMethodResidents";
  public static final String CREATED_SINCE = "CreatedSince";

  /**
   * Creates a GetPaymentMethodsByProperty transaction with the given element values.
   *
   * @param parameters Values for transaction-specific elements
   */
  public GetPaymentMethodsByPropertyTransaction(HashMap<String, String> parameters) {
    super(parameters);

    setRootActionAttribute(ACTION_NAME);

    addElementName(PROPERTY_REFERENCE_ID);
    addElementName(ACTIVE_PAYMENT_METHOD_RESIDENTS);
    addElementName(CREATED_SINCE);
  }
}
