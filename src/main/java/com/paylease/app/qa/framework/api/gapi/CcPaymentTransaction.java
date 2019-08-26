package com.paylease.app.qa.framework.api.gapi;

import com.paylease.app.qa.framework.api.Element;
import com.paylease.app.qa.framework.api.ParameterCollection;
import java.util.ArrayList;

public class CcPaymentTransaction extends GapiTransaction {

  private static final String ACTION_NAME = "CCPayment";
  public static final String PAYMENT_REFERENCE_ID = "PaymentReferenceId";
  public static final String PAYMENT_TRACE_ID = "PaymentTraceId";
  public static final String PAYER_REFERENCE_ID = "PayerReferenceId";
  public static final String PAYER_SECONDARY_REFERENCE_ID = "PayerSecondaryReferenceId";
  public static final String PAYEE_ID = "PayeeId";
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
  public static final String TOTAL_AMOUNT = "TotalAmount";
  public static final String FEE_AMOUNT = "FeeAmount";
  public static final String MESSAGE = "Message";
  public static final String INCUR_FEE = "IncurFee";
  public static final String SAVE_ACCOUNT = "SaveAccount";
  public static final String CREDIT_CARD_ACTION = "CreditCardAction";
  public static final String CURRENCY_CODE = "CurrencyCode";
  public static final String SPLIT_DEPOSIT = "SplitDeposit";
  public static final String DEPOSIT = "Deposit";
  public static final String AMOUNT = "Amount";

  /**
   * CC Payment GapiTransaction.
   *
   * @param parameters Bunch of parameters from ParameterCollection
   */
  public CcPaymentTransaction(ParameterCollection parameters)
  {
    super(parameters);
    setRootActionName(ACTION_NAME);
    addElementName(PAYMENT_REFERENCE_ID);
    addElementName(PAYMENT_TRACE_ID);
    addElementName(PAYEE_ID);
    addElementName(PAYER_REFERENCE_ID);
    addElementName(PAYER_SECONDARY_REFERENCE_ID);
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
    addElementName(TOTAL_AMOUNT);
    addElementName(FEE_AMOUNT);
    addElementName(INCUR_FEE);
    addElementName(SAVE_ACCOUNT);
    addElementName(MESSAGE);
    addElementName(CREDIT_CARD_ACTION);
    addElementName(CURRENCY_CODE);
    addElementName(SPLIT_DEPOSIT);
  }

  @Override
  public ArrayList<Element> getElements() {

    ArrayList<Element> elements = super.getElements();

    ArrayList<String> splitDepositElementNames = new ArrayList<>();
    splitDepositElementNames.add(DEPOSIT);
    Element splitDepositElement = new Element(SPLIT_DEPOSIT);

    ArrayList<String> depositElementNames = new ArrayList<>();
    depositElementNames.add(PAYEE_ID);
    depositElementNames.add(AMOUNT);

    ParameterCollection splitDepositParameters = parameters.get(SPLIT_DEPOSIT).getParameters();

    int depositCount = splitDepositParameters.getParamCount(DEPOSIT);
    if (depositCount > 0) {
      for (int i = 0; i < depositCount; i++) {
        Element depositElement = new Element(DEPOSIT);
        ParameterCollection depositParameters = splitDepositParameters.get(DEPOSIT, i)
            .getParameters();

        setSubElements(depositElement, depositElementNames, depositParameters);
        splitDepositElement.addSubElement(depositElement);
      }

      elements.add(splitDepositElement);
    }
    return elements;
  }

}
