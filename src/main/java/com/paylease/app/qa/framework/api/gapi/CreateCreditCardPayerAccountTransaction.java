package com.paylease.app.qa.framework.api.gapi;

import java.util.HashMap;

public class CreateCreditCardPayerAccountTransaction extends GapiTransaction {

  private static final String ACTION_NAME = "CreateCreditCardPayerAccount";

  public static final String PAYER_REFERENCE_ID = "PayerReferenceId";
  public static final String PAYER_FIRST_NAME = "PayerFirstName";
  public static final String PAYER_LAST_NAME = "PayerLastName";
  public static final String CREDIT_CARD_TYPE = "CreditCardType";
  public static final String CREDIT_CARD_NUMBER = "CreditCardNumber";
  public static final String CREDIT_CARD_EXP_MONTH = "CreditCardExpMonth";
  public static final String CREDIT_CARD_EXP_YEAR = "CreditCardExpYear";
  public static final String CREDIT_CARD_CVV2 = "CreditCardCvv2";
  public static final String BILLING_FIRST_NAME = "BillingFirstName";
  public static final String BILLING_LAST_NAME = "BillingLastName";
  public static final String BILLING_STREET_ADDRESS = "BillingStreetAddress";
  public static final String BILLING_CITY = "BillingCity";
  public static final String BILLING_STATE = "BillingState";
  public static final String BILLING_COUNTRY = "BillingCountry";
  public static final String BILLING_ZIP = "BillingZip";

  /**
   * Creates a CreateCreditCardPayerAccount transaction with the given element values.
   *
   * @param parameters Values for transaction-specific elements
   */
  public CreateCreditCardPayerAccountTransaction(HashMap<String, String> parameters) {
    super(parameters);

    addElementName(PAYER_REFERENCE_ID);
    addElementName(PAYER_FIRST_NAME);
    addElementName(PAYER_LAST_NAME);
    addElementName(CREDIT_CARD_TYPE);
    addElementName(CREDIT_CARD_NUMBER);
    addElementName(CREDIT_CARD_EXP_MONTH);
    addElementName(CREDIT_CARD_EXP_YEAR);
    addElementName(CREDIT_CARD_CVV2);
    addElementName(BILLING_FIRST_NAME);
    addElementName(BILLING_LAST_NAME);
    addElementName(BILLING_STREET_ADDRESS);
    addElementName(BILLING_CITY);
    addElementName(BILLING_STATE);
    addElementName(BILLING_COUNTRY);
    addElementName(BILLING_ZIP);

    setRootActionName(ACTION_NAME);
  }
}
