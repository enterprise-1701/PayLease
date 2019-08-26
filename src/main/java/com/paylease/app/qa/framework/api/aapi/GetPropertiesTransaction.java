package com.paylease.app.qa.framework.api.aapi;

import com.paylease.app.qa.framework.api.ParameterCollection;

public class GetPropertiesTransaction extends AapiTransaction {

  private static final String ACTION_NAME = "GetProperties";

  public static final String PROPERTY_REFERENCE_ID = "PropertyReferenceID";
  public static final String ATTR_RETURN_RESIDENTS = "return_residents";
  public static final String ATTR_RETURN_PAYMENT_FIELDS = "return_payment_fields";

  /**
   * Creates a GetProperties transaction with the given element values.
   *
   * @param parameters Values for transaction-specific elements
   */
  public GetPropertiesTransaction(ParameterCollection parameters) {
    super(parameters);

    setRootActionAttribute(ACTION_NAME);

    addElementName(PROPERTY_REFERENCE_ID);
  }
}
