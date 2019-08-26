package com.paylease.app.qa.framework.api.aapi;

import java.util.HashMap;

public class CheckCredentialsTransaction extends AapiTransaction {

  private static final String ACTION_NAME = "CheckCredentials";

  /**
   * Creates a CheckCredentials transaction with the given element values.
   */
  public CheckCredentialsTransaction(HashMap<String, String> parameters) {
    super(parameters);

    setRootActionAttribute(ACTION_NAME);
  }
}
