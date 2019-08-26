package com.paylease.app.qa.framework.api.gapi;

import java.util.HashMap;

public class CreditReportingOptinTransaction extends GapiTransaction {

  private static final String ACTION_NAME = "CreditReportingOptIn";

  public static final String FIRST_NAME = "FirstName";
  public static final String LAST_NAME = "LastName";
  public static final String STREET_ADDRESS = "StreetAddress";
  public static final String CITY = "City";
  public static final String STATE = "State";
  public static final String ZIP = "Zip";
  public static final String LEASE_END_DATE = "LeaseEndDate";
  public static final String SSN = "SSN";
  public static final String BIRTH_DATE = "BirthDate";
  public static final String MONTH_TO_MONTH = "MonthToMonthCreditReporting";
  public static final String CREDIT_REPOTING_ID = "CreditReportingId";

  /**
   * Creates a CreditReportingOptinTransaction transaction with the given element values.
   *
   * @param parameters Values for transaction-specific elements
   */
  public CreditReportingOptinTransaction(HashMap<String, String> parameters) {
    super(parameters);

    addElementName(FIRST_NAME);
    addElementName(LAST_NAME);
    addElementName(CREDIT_REPOTING_ID);
    addElementName(STREET_ADDRESS);
    addElementName(CITY);
    addElementName(STATE);
    addElementName(ZIP);
    addElementName(LEASE_END_DATE);
    addElementName(SSN);
    addElementName(BIRTH_DATE);
    addElementName(MONTH_TO_MONTH);

    setRootActionName(ACTION_NAME);
  }

}
