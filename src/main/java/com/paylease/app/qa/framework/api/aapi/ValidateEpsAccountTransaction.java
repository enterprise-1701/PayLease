package com.paylease.app.qa.framework.api.aapi;

import java.util.HashMap;

public class ValidateEpsAccountTransaction extends AapiTransaction {

  private static final String ACTION_NAME = "ValidateEPSAccount";

  public static final String VENDOR_ID = "VendorID";
  public static final String EXT_ACC_ID = "ExternalAcctID";

  /**
   * Creates a ValidateEpsAccount transaction with the given element values.
   *
   * @param parameters Values for transaction-specific elements
   */
  public ValidateEpsAccountTransaction(HashMap<String, String> parameters) {
    super(parameters);

    setRootActionAttribute(ACTION_NAME);

    addElementName(VENDOR_ID);
    addElementName(EXT_ACC_ID);
  }
}
