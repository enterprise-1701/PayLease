package com.paylease.app.qa.framework.api.aapi;

import com.paylease.app.qa.framework.api.Element;
import com.paylease.app.qa.framework.api.ParameterCollection;
import java.util.ArrayList;

public class SetResidentsTransaction extends AapiTransaction {

  private static final String ACTION_NAME = "SetResidents";

  public static final String RESIDENTS = "Residents";
  public static final String RESIDENT = "Resident";
  public static final String RESIDENT_ID = "ResidentID";
  public static final String SECONDARY_RESIDENT_ID = "SecondaryResidentID";
  public static final String PROPERTY_ID = "PropertyID";
  public static final String FIRST_NAME = "FirstName";
  public static final String LAST_NAME = "LastName";
  public static final String STREET_ADDRESS = "StreetAddress";
  public static final String CITY = "City";
  public static final String STATE = "State";
  public static final String POSTAL_CODE = "PostalCode";
  public static final String PHONE = "Phone";
  public static final String ALTERNATE_PHONE = "AlternatePhone";
  public static final String EMAIL = "Email";
  public static final String AMOUNT = "Amount";
  public static final String HOLD = "Hold";
  public static final String UNIT = "Unit";
  public static final String GENERATE_REGISTRATION_URL = "GenerateRegistrationUrl";
  public static final String CORPORATE_CLIENT = "CorporateClient";
  public static final String IS_CORPORATE_CLIENT = "IsCorporateClient";
  public static final String IS_CORPORATE_CLIENT_RESIDENT = "IsCorporateClientResident";
  public static final String CORPORATE_CLIENT_ID = "CorporateClientID";

  /**
   * Creates a SetResidents transaction with the given element values.
   *
   * @param parameters Values for transaction-specific elements
   */
  public SetResidentsTransaction(ParameterCollection parameters) {
    super(parameters);

    setRootActionAttribute(ACTION_NAME);
  }

  /**
   * Gets an array list of elements to be added.
   *
   */
  public ArrayList<Element> getElements() {

    ArrayList<Element> elements = super.getElements();

    ArrayList<String> residentElementNames = new ArrayList<>();
    residentElementNames.add(PROPERTY_ID);
    residentElementNames.add(RESIDENT_ID);
    residentElementNames.add(FIRST_NAME);
    residentElementNames.add(LAST_NAME);
    residentElementNames.add(STREET_ADDRESS);
    residentElementNames.add(CITY);
    residentElementNames.add(STATE);
    residentElementNames.add(POSTAL_CODE);
    residentElementNames.add(PHONE);
    residentElementNames.add(ALTERNATE_PHONE);
    residentElementNames.add(EMAIL);
    residentElementNames.add(AMOUNT);
    residentElementNames.add(HOLD);
    residentElementNames.add(UNIT);
    residentElementNames.add(SECONDARY_RESIDENT_ID);
    residentElementNames.add(GENERATE_REGISTRATION_URL);

    ArrayList<String> corpClientElementNames = new ArrayList<>();
    corpClientElementNames.add(IS_CORPORATE_CLIENT);
    corpClientElementNames.add(IS_CORPORATE_CLIENT_RESIDENT);
    corpClientElementNames.add(CORPORATE_CLIENT_ID);

    Element residentsElement = new Element(RESIDENTS);
    ParameterCollection residentsParameters = parameters.get(RESIDENTS).getParameters();

    for (int i = 0; i < residentsParameters.getParamCount(RESIDENT); i++) {
      Element residentElement = new Element(RESIDENT);

      ParameterCollection residentParameters = residentsParameters.get(RESIDENT, i).getParameters();

      setSubElements(residentElement, residentElementNames, residentParameters);

      Element corporateClientFieldElement = new Element(CORPORATE_CLIENT);

      ParameterCollection corpClientParameters = residentParameters.get(CORPORATE_CLIENT)
          .getParameters();
      setSubElements(corporateClientFieldElement, corpClientElementNames, corpClientParameters);

      if (!corporateClientFieldElement.getSubElements().isEmpty()) {
        residentElement.addSubElement(corporateClientFieldElement);
      }
      residentsElement.addSubElement(residentElement);
    }

    elements.add(residentsElement);

    return elements;
  }
}
