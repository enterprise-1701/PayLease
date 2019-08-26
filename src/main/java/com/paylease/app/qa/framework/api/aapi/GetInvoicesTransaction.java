package com.paylease.app.qa.framework.api.aapi;

import java.util.HashMap;

public class GetInvoicesTransaction extends AapiTransaction {

  private static final String ACTION_NAME = "GetInvoices";

  public static final String START_DATE = "StartDate";
  public static final String END_DATE = "EndDate";

  /**
   * Creates a GetInvoices transaction with the given element values.
   *
   * @param parameters Values for transaction-specific elements
   */
  public GetInvoicesTransaction(HashMap<String, String> parameters) {
    super(parameters);

    setRootActionAttribute(ACTION_NAME);

    addElementName(START_DATE);
    addElementName(END_DATE);
  }
}
