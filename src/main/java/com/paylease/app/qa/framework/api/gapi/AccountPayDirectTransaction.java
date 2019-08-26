package com.paylease.app.qa.framework.api.gapi;

import java.util.HashMap;

public class AccountPayDirectTransaction extends GapiTransaction {

  private static final String ACTION_NAME = "AccountPayDirect";

  public static final String PAYMENT_REFERENCE_ID = "PaymentReferenceId";
  public static final String PAYMENT_TRACE_ID = "PaymentTraceId";
  public static final String PAYER_ID = "PayerId";
  public static final String PAYEE_REFERENCE_ID = "PayeeReferenceId";
  public static final String PAYEE_STATE = "PayeeState";
  public static final String GATEWAY_PAYEE_ID = "GatewayPayeeId";
  public static final String TOTAL_AMOUNT = "TotalAmount";
  public static final String PAYEE_EMAIL_ADDRESS = "PayeeEmailAddress";


  /**
   * Creates an AccountPayDirect transaction with the given element values.
   *
   * @param parameters Values for transaction-specific elements
   */
  public AccountPayDirectTransaction(HashMap<String, String> parameters) {
    super(parameters);

    addElementName(PAYMENT_REFERENCE_ID);
    addElementName(PAYMENT_TRACE_ID);
    addElementName(PAYER_ID);
    addElementName(PAYEE_REFERENCE_ID);
    addElementName(PAYEE_STATE);
    addElementName(GATEWAY_PAYEE_ID);
    addElementName(TOTAL_AMOUNT);
    addElementName(PAYEE_EMAIL_ADDRESS);

    setRootActionName(ACTION_NAME);
  }
}
