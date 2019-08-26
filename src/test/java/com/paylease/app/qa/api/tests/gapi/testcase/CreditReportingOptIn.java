package com.paylease.app.qa.api.tests.gapi.testcase;

import com.paylease.app.qa.api.tests.ExpectedResponse;
import com.paylease.app.qa.framework.UtilityManager;
import com.paylease.app.qa.framework.api.Request;
import com.paylease.app.qa.framework.api.gapi.CreditReportingOptinTransaction;
import com.paylease.app.qa.framework.api.gapi.GapiTransaction;
import java.util.Date;
import java.util.HashMap;

public class CreditReportingOptIn extends BasicTestCase {

  private static final String FIRST_NAME = "TestFirst";
  private static final String LAST_NAME = "TestLast";
  private static final String STREET_ADDRESS = "wonderful street";
  private static final String CITY = "San Diego";
  private static final String STATE = "CA";
  private static final String ZIP = "92121";
  private static final String SSN = "165487489";
  private static final String BIRTHDATE = "09/09/1989";
  private static final String MONTH_TO_MONTH = "Yes";
  private String firstName;
  private String lastName;
  private String streetAddress;
  private String city;
  private String state;
  private String zip;
  private String leaseEndDate;
  private String ssn;
  private String birthdate;
  private String monthToMonth;
  private String optinId;

  public CreditReportingOptIn(
      String summary, ExpectedResponse expectedResponse
  ) {
    this(summary, expectedResponse, null, null, null, null, null,
        null, null, null, null, null, null);
  }

  private CreditReportingOptIn(
      String summary, ExpectedResponse expectedResponse,
      String firstName, String lastName, String optinId, String streetAddress, String city,
      String state,
      String zip, String leaseEndDate, String monthToMonth, String ssn, String birthdate) {
    super(summary, expectedResponse);

    this.firstName = firstName;
    this.lastName = lastName;
    this.optinId = optinId;
    this.streetAddress = streetAddress;
    this.city = city;
    this.state = state;
    this.zip = zip;
    this.ssn = ssn;
    this.birthdate = birthdate;
    this.monthToMonth = monthToMonth;
    this.leaseEndDate = leaseEndDate;

  }

  public static CreditReportingOptIn createPartial(String summary,
      ExpectedResponse expectedResponse) {
    CreditReportingOptIn testCase = new CreditReportingOptIn(summary, expectedResponse);
    testCase.firstName = FIRST_NAME;
    testCase.lastName = LAST_NAME;
    testCase.streetAddress = STREET_ADDRESS;
    testCase.city = CITY;
    testCase.state = STATE;
    testCase.zip = ZIP;
    testCase.ssn = SSN;
    testCase.birthdate = BIRTHDATE;

    return testCase;
  }

  public static CreditReportingOptIn createValidWithLeaseEndDate(String summary,
      ExpectedResponse expectedResponse) {

    UtilityManager um = new UtilityManager();
    Date temp = um.addDays(new Date(), 365);
    String futureDate = um.dateToString(temp, "MM/dd/yyyy");

    CreditReportingOptIn testCase = createPartial(summary, expectedResponse);
    testCase.leaseEndDate = futureDate;
    return testCase;
  }

  public static CreditReportingOptIn createValidMonthToMonth(String summary,
      ExpectedResponse expectedResponse) {
    CreditReportingOptIn testCase = createPartial(summary, expectedResponse);
    testCase.monthToMonth = MONTH_TO_MONTH;
    return testCase;
  }

  public CreditReportingOptIn setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public CreditReportingOptIn setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public CreditReportingOptIn setStreetAddress(String streetAddress) {
    this.streetAddress = streetAddress;
    return this;
  }

  public CreditReportingOptIn setCity(String city) {
    this.city = city;
    return this;
  }

  public CreditReportingOptIn setState(String state) {
    this.state = state;
    return this;
  }

  public CreditReportingOptIn setZip(String zip) {
    this.zip = zip;
    return this;
  }

  public CreditReportingOptIn setLeaseEndDate(String leaseEndDate) {
    this.leaseEndDate = leaseEndDate;
    return this;
  }

  public CreditReportingOptIn setSSN(String ssn) {
    this.ssn = ssn;
    return this;
  }

  public CreditReportingOptIn setBirthDate(String birthDate) {
    this.birthdate = birthDate;
    return this;
  }

  public CreditReportingOptIn setMonthToMonth(String monthToMonth) {
    this.monthToMonth = monthToMonth;
    return this;
  }

  public CreditReportingOptIn setOptinId(String optinId) {
    this.optinId = optinId;
    return this;
  }

  @Override
  public void addTransaction(Request apiRequest) {
    HashMap<String, String> parameters = new HashMap<>();
    parameters.put(CreditReportingOptinTransaction.FIRST_NAME, firstName);
    parameters.put(CreditReportingOptinTransaction.LAST_NAME, lastName);
    parameters.put(CreditReportingOptinTransaction.CREDIT_REPOTING_ID, optinId);
    parameters.put(CreditReportingOptinTransaction.STREET_ADDRESS, streetAddress);
    parameters.put(CreditReportingOptinTransaction.CITY, city);
    parameters.put(CreditReportingOptinTransaction.STATE, state);
    parameters.put(CreditReportingOptinTransaction.ZIP, zip);
    parameters.put(CreditReportingOptinTransaction.LEASE_END_DATE, leaseEndDate);
    parameters.put(CreditReportingOptinTransaction.MONTH_TO_MONTH, monthToMonth);
    parameters.put(CreditReportingOptinTransaction.SSN, ssn);
    parameters.put(CreditReportingOptinTransaction.BIRTH_DATE, birthdate);

    GapiTransaction transaction = new CreditReportingOptinTransaction(parameters);
    apiRequest.addTransaction(transaction);
  }

}
