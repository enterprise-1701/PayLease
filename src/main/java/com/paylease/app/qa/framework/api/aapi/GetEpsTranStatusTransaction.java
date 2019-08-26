package com.paylease.app.qa.framework.api.aapi;

import java.util.HashMap;

public class GetEpsTranStatusTransaction extends AapiTransaction {

  private static final String ACTION_NAME = "GetEPSTransactionStatus";

  public static final String VENDOR_ID = "VendorID";
  public static final String EXT_ACC_ID = "ExternalAcctID";
  public static final String EXT_TRANSACTION_ID = "ExtTransactionID";

  /**
   * Creates a GetEPSTransactionStatus transaction with the given element values.
   *
   * @param parameters Values for transaction-specific elements
   */
  public GetEpsTranStatusTransaction(HashMap<String, String> parameters) {
    super(parameters);

    setRootActionAttribute(ACTION_NAME);

    addElementName(VENDOR_ID);
    addElementName(EXT_ACC_ID);
    addElementName(EXT_TRANSACTION_ID);
  }
}
