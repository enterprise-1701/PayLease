package com.paylease.app.qa.framework.api.aapi;

import com.paylease.app.qa.framework.api.Element;
import com.paylease.app.qa.framework.api.ParameterCollection;
import java.util.ArrayList;

public class AddPropertyTransaction extends AapiTransaction {

  private static final String ACTION_NAME = "AddProperty";

  public static final String PROPERTY_ID = "PropertyID";
  public static final String PROPERTY = "Property";
  public static final String PROPERTY_NAME = "PropertyName";
  public static final String STREET_ADDRESS = "StreetAddress";
  public static final String CITY = "City";
  public static final String STATE = "State";
  public static final String POSTAL_CODE = "PostalCode";
  public static final String PHONE = "Phone";
  public static final String FAX = "Fax";
  public static final String EMAIL = "Email";
  public static final String UNIT_COUNT = "UnitCount";
  public static final String FREQ_ID = "FreqID";
  public static final String LOGO_URL = "LogoUrl";
  public static final String PAYMENT_FIELDS = "PaymentFields";
  public static final String PAYMENT_FIELD = "PaymentField";
  public static final String FIELD_NAME = "FieldName";
  public static final String VAR_NAME = "VarName";
  public static final String BANK_NAME = "BankName";
  public static final String BANK_ACCOUNT_TYPE = "BankAccountType";
  public static final String BANK_ACCOUNT_ROUTING = "BankAccountRouting";
  public static final String BANK_ACCOUNT_NUMBER = "BankAccountNumber";

  /**
   * Creates a AddProperty transaction with the given element values.
   *
   * @param parameters Values for transaction-specific elements
   */
  public AddPropertyTransaction(ParameterCollection parameters) {
    super(parameters);
    setRootActionAttribute(ACTION_NAME);
  }

  /**
   * Gets an array list of elements to be added.
   *
   */
  public ArrayList<Element> getElements() {

    ArrayList<Element> elements = super.getElements();

    Element propertyElement = new Element(PROPERTY);

    ArrayList<String> propertyElementNames = new ArrayList<>();
    propertyElementNames.add(PROPERTY_ID);
    propertyElementNames.add(PROPERTY_NAME);
    propertyElementNames.add(STREET_ADDRESS);
    propertyElementNames.add(CITY);
    propertyElementNames.add(STATE);
    propertyElementNames.add(POSTAL_CODE);
    propertyElementNames.add(PHONE);
    propertyElementNames.add(FAX);
    propertyElementNames.add(EMAIL);
    propertyElementNames.add(UNIT_COUNT);
    propertyElementNames.add(FREQ_ID);
    propertyElementNames.add(LOGO_URL);

    ParameterCollection propertyParameters = parameters.get(PROPERTY).getParameters();
    setSubElements(propertyElement, propertyElementNames, propertyParameters);

    ArrayList<String> paymentFieldElementNames = new ArrayList<>();
    paymentFieldElementNames.add(FIELD_NAME);
    paymentFieldElementNames.add(VAR_NAME);
    paymentFieldElementNames.add(BANK_NAME);
    paymentFieldElementNames.add(BANK_ACCOUNT_TYPE);
    paymentFieldElementNames.add(BANK_ACCOUNT_ROUTING);
    paymentFieldElementNames.add(BANK_ACCOUNT_NUMBER);

    Element paymentFieldsElement = new Element(PAYMENT_FIELDS);

    ParameterCollection paymentFieldsParameters = propertyParameters.get(PAYMENT_FIELDS)
        .getParameters();
    for (int i = 0; i < paymentFieldsParameters.getParamCount(PAYMENT_FIELD); i++) {
      Element paymentFieldElement = new Element(PAYMENT_FIELD);
      ParameterCollection paymentFieldParameters = paymentFieldsParameters.get(PAYMENT_FIELD, i)
          .getParameters();

      setSubElements(paymentFieldElement, paymentFieldElementNames, paymentFieldParameters);
      paymentFieldsElement.addSubElement(paymentFieldElement);
    }

    propertyElement.addSubElement(paymentFieldsElement);

    elements.add(propertyElement);
    return elements;
  }
}
