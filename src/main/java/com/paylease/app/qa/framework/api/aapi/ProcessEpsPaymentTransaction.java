package com.paylease.app.qa.framework.api.aapi;

import java.util.HashMap;

public class ProcessEpsPaymentTransaction extends AapiTransaction {

  private static final String ACTION_NAME = "ProcessEPSPayment";

  public static final String VENDOR_ID = "VendorID";
  public static final String EXT_ACC_ID = "ExternalAcctID";
  public static final String UNIT_AMOUNT = "UnitAmount";
  public static final String EXT_TRANSACTION_ID = "ExtTransactionID";

  /**
   * Creates a ProcessEpsPayment transaction with the given element values.
   *
   * @param parameters Values for transaction-specific elements
   */
  public ProcessEpsPaymentTransaction(HashMap<String, String> parameters) {
    super(parameters);

    setRootActionAttribute(ACTION_NAME);

    addElementName(VENDOR_ID);
    addElementName(EXT_ACC_ID);
    addElementName(UNIT_AMOUNT);
    addElementName(EXT_TRANSACTION_ID);
  }
}
