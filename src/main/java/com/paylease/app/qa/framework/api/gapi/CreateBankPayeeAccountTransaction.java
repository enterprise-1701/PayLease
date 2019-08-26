package com.paylease.app.qa.framework.api.gapi;

import java.util.HashMap;

public class CreateBankPayeeAccountTransaction extends GapiTransaction {

  private static final String ACTION_NAME = "CreateBankPayeeAccount";

  public static final String PAYEE_REFERENCE_ID = "PayeeReferenceId";
  public static final String PAYEE_FIRST_NAME = "PayeeFirstName";
  public static final String PAYEE_LAST_NAME = "PayeeLastName";
  public static final String PAYEE_STATE = "PayeeState";
  public static final String ACCOUNT_TYPE = "AccountType";
  public static final String ACCOUNT_FULL_NAME = "AccountFullName";
  public static final String ROUTING_NUMBER = "RoutingNumber";
  public static final String ACCOUNT_NUMBER = "AccountNumber";

  /**
   * Creates a CreateBankPayeeAccount transaction with the given element values.
   *
   * @param parameters Values for transaction-specific elements
   */
  public CreateBankPayeeAccountTransaction(HashMap<String, String> parameters) {
    super(parameters);

    addElementName(PAYEE_REFERENCE_ID);
    addElementName(PAYEE_FIRST_NAME);
    addElementName(PAYEE_LAST_NAME);
    addElementName(PAYEE_STATE);
    addElementName(ACCOUNT_TYPE);
    addElementName(ACCOUNT_FULL_NAME);
    addElementName(ROUTING_NUMBER);
    addElementName(ACCOUNT_NUMBER);

    setRootActionName(ACTION_NAME);
  }
}
