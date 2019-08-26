package com.paylease.app.qa.framework.api.gapi;

import java.util.HashMap;

public class CreditReportingOptoutTransaction extends GapiTransaction {

  private static final String ACTION_NAME = "CreditReportingOptOut";

  public static final String CREDIT_REPORTING_ID = "CreditReportingId";


  /**
   * Creates a CreditReportingOptoutTransaction transaction with the given element values.
   *
   * @param parameters Values for transaction-specific elements
   */
  public CreditReportingOptoutTransaction(HashMap<String, String> parameters) {
    super(parameters);

    addElementName(CREDIT_REPORTING_ID);

    setRootActionName(ACTION_NAME);
  }

}
