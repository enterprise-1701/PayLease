package com.paylease.app.qa.framework.api.aapi;

import com.paylease.app.qa.framework.api.ParameterCollection;
import com.paylease.app.qa.framework.api.Transaction;
import java.util.HashMap;

public abstract class AapiTransaction extends Transaction {

  private static final String ACTION_ATTRIBUTE = "Type";

  public static final String PM_ID = "PmID";

  /**
   * Creates an AAPI transaction with the given element values.
   *
   * @param parameters Values for transaction-specific elements
   */
  public AapiTransaction(ParameterCollection parameters) {
    super(parameters);

    addElementName(PM_ID);
  }

  /**
   * Creates an AAPI transaction with the given element values.
   *
   * @param parameters Values for transaction-specific elements
   */
  public AapiTransaction(HashMap<String, String> parameters) {
    super(parameters);

    addElementName(PM_ID);
  }

  protected void setRootActionAttribute(String actionName) {
    addActionAttribute(ACTION_ATTRIBUTE, actionName);
  }
}
