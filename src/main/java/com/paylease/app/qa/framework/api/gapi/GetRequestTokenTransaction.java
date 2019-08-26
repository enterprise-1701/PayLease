package com.paylease.app.qa.framework.api.gapi;

import java.util.HashMap;

public class GetRequestTokenTransaction extends GapiTransaction {

  private static final String ACTION_NAME = "GetRequestToken";

  /**
   * Creates a GetRequestToken transaction with the given element values.
   *
   * @param parameters Values for transaction-specific elements
   */
  public GetRequestTokenTransaction(HashMap<String, String> parameters) {
    super(parameters);
    setRootActionName(ACTION_NAME);
  }
}
