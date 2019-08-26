package com.paylease.app.qa.framework.api.gapi;

import java.util.HashMap;

public class CreateBankPayerAccountTransaction extends GapiTransaction {

  private static final String ACTION_NAME = "CreateBankPayerAccount";

  public static final String PAYER_REFERENCE_ID = "PayerReferenceId";
  public static final String PAYER_FIRST_NAME = "PayerFirstName";
  public static final String PAYER_LAST_NAME = "PayerLastName";
  public static final String ACCOUNT_TYPE = "AccountType";
  public static final String ACCOUNT_FULL_NAME = "AccountFullName";
  public static final String ROUTING_NUMBER = "RoutingNumber";
  public static final String ACCOUNT_NUMBER = "AccountNumber";

  /**
   * Creates a CreateBankPayerAccount transaction with the given element values.
   *
   * @param parameters Values for transaction-specific elements
   */
  public CreateBankPayerAccountTransaction(HashMap<String, String> parameters) {
    super(parameters);

    addElementName(PAYER_REFERENCE_ID);
    addElementName(PAYER_FIRST_NAME);
    addElementName(PAYER_LAST_NAME);
    addElementName(ACCOUNT_TYPE);
    addElementName(ACCOUNT_FULL_NAME);
    addElementName(ROUTING_NUMBER);
    addElementName(ACCOUNT_NUMBER);

    setRootActionName(ACTION_NAME);
  }
}
