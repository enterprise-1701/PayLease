package com.paylease.app.qa.framework.api.aapi;

import java.util.HashMap;

public class PmSsoTransaction extends AapiTransaction {
  private static final String ACTION_NAME = "PMSSO";

  public static final String PM_ID = "PmID";

  /**
   * Creates a PMSSO transaction with the given element values.
   *
   * @param parameters Values for transaction-specific elements
   */
  public PmSsoTransaction(HashMap<String, String> parameters) {
    super(parameters);

    setRootActionAttribute(ACTION_NAME);
  }
}
