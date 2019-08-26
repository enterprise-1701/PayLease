package com.paylease.app.qa.framework.api.aapi;

import java.util.HashMap;

public class GetVariableAutoPaysTransaction extends AapiTransaction {
  private static final String ACTION_NAME = "GetVariableAutoPays";

  public static final String RESIDENT_REFERENCE_ID = "ResidentReferenceID";
  public static final String STATUS = "Status";

  public GetVariableAutoPaysTransaction(HashMap<String, String> parameters) {
    super(parameters);

    setRootActionAttribute(ACTION_NAME);

    addElementName(RESIDENT_REFERENCE_ID);
    addElementName(STATUS);
  }
}
