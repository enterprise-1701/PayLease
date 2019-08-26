package com.paylease.app.qa.framework.api.gapi;

import java.util.HashMap;

public class PayDirectTransaction extends GapiTransaction {

  private static final String ACTION_NAME = "PayDirect";

  public static final String PAYMENT_REFERENCE_ID = "PaymentReferenceId";
  public static final String PAYMENT_TRACE_ID = "PaymentTraceId";
  public static final String PAYER_ID = "PayerId";
  public static final String PAYEE_REFERENCE_ID = "PayeeReferenceId";
  public static final String PAYEE_FIRST_NAME = "PayeeFirstName";
  public static final String PAYEE_LAST_NAME = "PayeeLastName";
  public static final String PAYEE_EMAIL_ADDRESS = "PayeeEmailAddress";
  public static final String PAYEE_STATE = "PayeeState";
  public static final String ACCOUNT_TYPE = "AccountType";
  public static final String ACCOUNT_FULL_NAME = "AccountFullName";
  public static final String ROUTING_NUMBER = "RoutingNumber";
  public static final String ACCOUNT_NUMBER = "AccountNumber";
  public static final String TOTAL_AMOUNT = "TotalAmount";
  public static final String SAVE_ACCOUNT = "SaveAccount";

  /**
   * Creates a PayDirect transaction with the given element values.
   *
   * @param parameters Values for transaction-specific elements
   */
  public PayDirectTransaction(HashMap<String, String> parameters) {
    super(parameters);

    addElementName(PAYMENT_REFERENCE_ID);
    addElementName(PAYMENT_TRACE_ID);
    addElementName(PAYER_ID);
    addElementName(PAYEE_REFERENCE_ID);
    addElementName(PAYEE_FIRST_NAME);
    addElementName(PAYEE_LAST_NAME);
    addElementName(PAYEE_EMAIL_ADDRESS);
    addElementName(PAYEE_STATE);
    addElementName(ACCOUNT_TYPE);
    addElementName(ACCOUNT_FULL_NAME);
    addElementName(ROUTING_NUMBER);
    addElementName(ACCOUNT_NUMBER);
    addElementName(TOTAL_AMOUNT);
    addElementName(SAVE_ACCOUNT);

    setRootActionName(ACTION_NAME);
  }
}
