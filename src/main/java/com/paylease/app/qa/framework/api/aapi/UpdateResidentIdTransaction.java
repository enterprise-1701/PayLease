package com.paylease.app.qa.framework.api.aapi;

import com.paylease.app.qa.framework.api.Element;
import com.paylease.app.qa.framework.api.ParameterCollection;
import java.util.ArrayList;

public class UpdateResidentIdTransaction extends AapiTransaction {

  private static final String ACTION_NAME = "UpdateResidentID";

  public static final String SPECIFIC_PATH = "Result";
  public static final String RESPONSE_RESIDENT_ID = "ResidentID";
  public static final String RESPONSE_RESULT = "UpdatedResidentID";

  public static final String RESIDENTS = "Residents";
  public static final String RESIDENT = "Resident";
  public static final String CURRENT_RESIDENT_ID = "CurrentResidentID";
  public static final String NEW_RESIDENT_ID = "NewResidentID";

  /**
   * Creates an UpdateResidentId transaction with the given element values.
   *
   * @param parameters Values for transaction-specific elements
   */
  public UpdateResidentIdTransaction(ParameterCollection parameters) {
    super(parameters);
    setRootActionAttribute(ACTION_NAME);
  }

  @Override
  public ArrayList<Element> getElements() {
    ArrayList<Element> elements = super.getElements();

    ArrayList<String> residentElementNames = new ArrayList<>();
    residentElementNames.add(CURRENT_RESIDENT_ID);
    residentElementNames.add(NEW_RESIDENT_ID);

    Element residentsElement = new Element(RESIDENTS);
    ParameterCollection residentsParameters = parameters.get(RESIDENTS).getParameters();

    for (int i = 0; i < residentsParameters.getParamCount(RESIDENT); i++) {
      Element residentElement = new Element(RESIDENT);
      ParameterCollection residentParameters = residentsParameters.get(RESIDENT, i).getParameters();
      setSubElements(residentElement, residentElementNames, residentParameters);

      if (!residentElement.getSubElements().isEmpty()) {
        residentsElement.addSubElement(residentElement);
      }
    }

    elements.add(residentsElement);

    return elements;
  }
}
