package com.paylease.app.qa.api.tests.gapi.testcase;

import com.paylease.app.qa.api.tests.ExpectedResponse;
import com.paylease.app.qa.framework.api.Request;
import com.paylease.app.qa.framework.api.gapi.CreateCreditCardPayerAccountTransaction;
import com.paylease.app.qa.framework.api.gapi.GapiTransaction;
import java.util.HashMap;

public class CreateCreditCardPayerAccount extends BasicTestCase {

  private static final String VALID_FIELD_FIRST_NAME = "firstName";
  private static final String VALID_FIELD_LAST_NAME = "lastName";
  private static final String VALID_FIELD_CARD_TYPE = "cardType";
  private static final String VALID_FIELD_CARD_NUM = "cardNum";
  private static final String VALID_FIELD_EXP_MONTH = "expMonth";
  private static final String VALID_FIELD_EXP_YEAR = "expYear";
  private static final String VALID_FIELD_CVV2 = "cvv2";
  private static final String VALID_FIELD_ADDRESS = "address";
  private static final String VALID_FIELD_CITY = "city";
  private static final String VALID_FIELD_STATE = "state";
  private static final String VALID_FIELD_COUNTRY = "country";
  private static final String VALID_FIELD_ZIP = "zip";

  private String payerReferenceId;
  private String payerFirstName;
  private String payerLastName;
  private String creditCardType;
  private String creditCardNumber;
  private String creditCardExpMonth;
  private String creditCardExpYear;
  private String creditCardCvv2;
  private String billingFirstName;
  private String billingLastName;
  private String billingStreetAddress;
  private String billingCity;
  private String billingState;
  private String billingCountry;
  private String billingZip;

  /**
   * Create a CreateCreditCardPayerAccount test case.
   *
   * @param payerReferenceId PayerReferenceId value
   * @param summary Summary of this test case
   * @param expectedResponse response expected to be returned by GAPI
   */
  public CreateCreditCardPayerAccount(
      String payerReferenceId, String summary, ExpectedResponse expectedResponse
  ) {
    this(payerReferenceId, summary, expectedResponse, null, null, null,
        null, null, null, null, null, null, null,
        null, null, null, null);
  }

  private CreateCreditCardPayerAccount(
      String payerReferenceId, String summary, ExpectedResponse expectedResponse,
      String payerFirstName,
      String payerLastName, String creditCardType, String creditCardNumber,
      String creditCardExpMonth, String creditCardExpYear, String creditCardCvv2,
      String billingFirstName, String billingLastName, String billingStreetAddress,
      String billingCity, String billingState, String billingCountry, String billingZip
  ) {
    super(summary, expectedResponse);

    this.payerReferenceId = payerReferenceId;

    this.payerFirstName = payerFirstName;
    this.payerLastName = payerLastName;
    this.creditCardType = creditCardType;
    this.creditCardNumber = creditCardNumber;
    this.creditCardExpMonth = creditCardExpMonth;
    this.creditCardExpYear = creditCardExpYear;
    this.creditCardCvv2 = creditCardCvv2;
    this.billingFirstName = billingFirstName;
    this.billingLastName = billingLastName;
    this.billingStreetAddress = billingStreetAddress;
    this.billingCity = billingCity;
    this.billingState = billingState;
    this.billingCountry = billingCountry;
    this.billingZip = billingZip;
  }

  /**
   * Factory method to create a valid request.
   *
   * @param payerReferenceId PayerReferenceId value
   * @param summary Summary of this test case
   * @param expectedResponse response expected to be returned by GAPI
   * @return valid test case
   */
  public static CreateCreditCardPayerAccount createValid(
      String payerReferenceId, String summary, ExpectedResponse expectedResponse
  ) {
    CreateCreditCardPayerAccount testCase = new CreateCreditCardPayerAccount(
        payerReferenceId, summary, expectedResponse
    );

    testCase.payerFirstName = testCase.getValidValue(VALID_FIELD_FIRST_NAME);
    testCase.payerLastName = testCase.getValidValue(VALID_FIELD_LAST_NAME);
    testCase.creditCardType = testCase.getValidValue(VALID_FIELD_CARD_TYPE);
    testCase.creditCardNumber = testCase.getValidValue(VALID_FIELD_CARD_NUM);
    testCase.creditCardExpMonth = testCase.getValidValue(VALID_FIELD_EXP_MONTH);
    testCase.creditCardExpYear = testCase.getValidValue(VALID_FIELD_EXP_YEAR);
    testCase.creditCardCvv2 = testCase.getValidValue(VALID_FIELD_CVV2);
    testCase.billingFirstName = testCase.getValidValue(VALID_FIELD_FIRST_NAME);
    testCase.billingLastName = testCase.getValidValue(VALID_FIELD_LAST_NAME);
    testCase.billingStreetAddress = testCase.getValidValue(VALID_FIELD_ADDRESS);
    testCase.billingCity = testCase.getValidValue(VALID_FIELD_CITY);
    testCase.billingState = testCase.getValidValue(VALID_FIELD_STATE);
    testCase.billingCountry = testCase.getValidValue(VALID_FIELD_COUNTRY);
    testCase.billingZip = testCase.getValidValue(VALID_FIELD_ZIP);
    return testCase;
  }

  protected void initValidValues() {
    super.initValidValues();
    validValues.put(VALID_FIELD_FIRST_NAME, new String[]{
        "Bonnie",
    });
    validValues.put(VALID_FIELD_LAST_NAME, new String[]{
        "Williams",
    });
    validValues.put(VALID_FIELD_CARD_TYPE, new String[]{
        "MasterCard",
    });
    validValues.put(VALID_FIELD_CARD_NUM, new String[]{
        "5112000100000003",
    });
    validValues.put(VALID_FIELD_EXP_MONTH, new String[]{
        "01", "05", "11",
    });
    validValues.put(VALID_FIELD_EXP_YEAR, new String[]{
        "99",
    });
    validValues.put(VALID_FIELD_CVV2, new String[]{
        "365",
    });
    validValues.put(VALID_FIELD_ADDRESS, new String[]{
        "123 Fake Street", "123 fake st",
    });
    validValues.put(VALID_FIELD_CITY, new String[]{
        "San Diego",
    });
    validValues.put(VALID_FIELD_STATE, new String[]{
        "CA", "TX",
    });
    validValues.put(VALID_FIELD_COUNTRY, new String[]{
        "US",
    });
    validValues.put(VALID_FIELD_ZIP, new String[]{
        "92121",
    });
  }

  public CreateCreditCardPayerAccount setPayerFirstName(String payerFirstName) {
    this.payerFirstName = payerFirstName;
    return this;
  }

  public CreateCreditCardPayerAccount setPayerLastName(String payerLastName) {
    this.payerLastName = payerLastName;
    return this;
  }

  public CreateCreditCardPayerAccount setPayerReferenceId(String payerReferenceId) {
    this.payerReferenceId = payerReferenceId;
    return this;
  }

  public CreateCreditCardPayerAccount setCreditCardType(String creditCardType) {
    this.creditCardType = creditCardType;
    return this;
  }

  public CreateCreditCardPayerAccount setCreditCardNumber(String creditCardNumber) {
    this.creditCardNumber = creditCardNumber;
    return this;
  }

  public CreateCreditCardPayerAccount setCreditCardExpMonth(String creditCardExpMonth) {
    this.creditCardExpMonth = creditCardExpMonth;
    return this;
  }

  public CreateCreditCardPayerAccount setCreditCardExpYear(String creditCardExpYear) {
    this.creditCardExpYear = creditCardExpYear;
    return this;
  }

  public CreateCreditCardPayerAccount setCreditCardCvv2(String creditCardCvv2) {
    this.creditCardCvv2 = creditCardCvv2;
    return this;
  }

  public CreateCreditCardPayerAccount setBillingFirstName(String billingFirstName) {
    this.billingFirstName = billingFirstName;
    return this;
  }

  public CreateCreditCardPayerAccount setBillingLastName(String billingLastName) {
    this.billingLastName = billingLastName;
    return this;
  }

  public CreateCreditCardPayerAccount setBillingStreetAddress(String billingStreetAddress) {
    this.billingStreetAddress = billingStreetAddress;
    return this;
  }

  public CreateCreditCardPayerAccount setBillingCity(String billingCity) {
    this.billingCity = billingCity;
    return this;
  }

  public CreateCreditCardPayerAccount setBillingState(String billingState) {
    this.billingState = billingState;
    return this;
  }

  public CreateCreditCardPayerAccount setBillingCountry(String billingCountry) {
    this.billingCountry = billingCountry;
    return this;
  }

  public CreateCreditCardPayerAccount setBillingZip(String billingZip) {
    this.billingZip = billingZip;
    return this;
  }

  @Override
  public void addTransaction(Request apiRequest) {
    HashMap<String, String> parameters = new HashMap<>();
    parameters.put(CreateCreditCardPayerAccountTransaction.PAYER_REFERENCE_ID, payerReferenceId);
    parameters.put(CreateCreditCardPayerAccountTransaction.PAYER_FIRST_NAME, payerFirstName);
    parameters.put(CreateCreditCardPayerAccountTransaction.PAYER_LAST_NAME, payerLastName);
    parameters.put(CreateCreditCardPayerAccountTransaction.CREDIT_CARD_TYPE, creditCardType);
    parameters.put(CreateCreditCardPayerAccountTransaction.CREDIT_CARD_NUMBER, creditCardNumber);
    parameters
        .put(CreateCreditCardPayerAccountTransaction.CREDIT_CARD_EXP_MONTH, creditCardExpMonth);
    parameters.put(CreateCreditCardPayerAccountTransaction.CREDIT_CARD_EXP_YEAR, creditCardExpYear);
    parameters.put(CreateCreditCardPayerAccountTransaction.CREDIT_CARD_CVV2, creditCardCvv2);
    parameters.put(CreateCreditCardPayerAccountTransaction.BILLING_FIRST_NAME, billingFirstName);
    parameters.put(CreateCreditCardPayerAccountTransaction.BILLING_LAST_NAME, billingLastName);
    parameters
        .put(CreateCreditCardPayerAccountTransaction.BILLING_STREET_ADDRESS, billingStreetAddress);
    parameters.put(CreateCreditCardPayerAccountTransaction.BILLING_CITY, billingCity);
    parameters.put(CreateCreditCardPayerAccountTransaction.BILLING_STATE, billingState);
    parameters.put(CreateCreditCardPayerAccountTransaction.BILLING_COUNTRY, billingCountry);
    parameters.put(CreateCreditCardPayerAccountTransaction.BILLING_ZIP, billingZip);

    GapiTransaction transaction = new CreateCreditCardPayerAccountTransaction(parameters);
    apiRequest.addTransaction(transaction);
  }
}
