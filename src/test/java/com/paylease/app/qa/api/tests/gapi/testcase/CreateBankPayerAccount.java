package com.paylease.app.qa.api.tests.gapi.testcase;

import com.paylease.app.qa.api.tests.ExpectedResponse;
import com.paylease.app.qa.framework.api.Request;
import com.paylease.app.qa.framework.api.gapi.CreateBankPayerAccountTransaction;
import com.paylease.app.qa.framework.api.gapi.GapiTransaction;
import java.util.HashMap;

public class CreateBankPayerAccount extends BasicTestCase {

  private static final String VALID_FIELD_PAYER_REFERENCE_ID = "payerReferenceId";
  private static final String VALID_FIELD_PAYER_FIRST_NAME = "payerFirstName";
  private static final String VALID_FIELD_PAYER_LAST_NAME = "payerLastName";
  private static final String VALID_FIELD_ACCOUNT_TYPE = "accountType";
  private static final String VALID_FIELD_ACCOUNT_FULL_NAME = "accountFullName";
  private static final String VALID_FIELD_ROUTING_NUMBER = "routingNumber";
  private static final String VALID_FIELD_ACCOUNT_NUMBER = "accountNumber";

  private String payerReferenceId;
  private String payerFirstName;
  private String payerLastName;
  private String accountType;
  private String accountFullName;
  private String routingNumber;
  private String accountNumber;

  /**
   * Create a basic CreateBankPayerAccount test case. Set remaining values to null.
   *
   * @param summary Summary for this test case
   * @param expectedResponse Expected response to be returned by GAPI
   */
  public CreateBankPayerAccount(
      String payerReferenceId, String summary, ExpectedResponse expectedResponse
  ) {
    this(payerReferenceId, summary, expectedResponse, null, null, null,
        null, null, null);
  }

  private CreateBankPayerAccount(
      String payerReferenceId, String summary, ExpectedResponse expectedResponse,
      String payerFirstName,
      String payerLastName, String accountType, String accountFullName,
      String routingNumber, String accountNumber
  ) {
    super(summary, expectedResponse);

    this.payerReferenceId = payerReferenceId;

    this.payerFirstName = payerFirstName;
    this.payerLastName = payerLastName;
    this.accountType = accountType;
    this.accountFullName = accountFullName;
    this.routingNumber = routingNumber;
    this.accountNumber = accountNumber;
  }

  /**
   * Create an createValid test case and valid values for all fields typically used
   * CreateBankPayerAccount request.
   *
   * @param summary Summary for this test case
   * @param expectedResponse response expected to be returned by GAPI
   * @return createValid test case that can be submitted as valid
   */
  public static CreateBankPayerAccount createValid(
      String payerReferenceId, String summary, ExpectedResponse expectedResponse
  ) {
    CreateBankPayerAccount testCase = new CreateBankPayerAccount(
        payerReferenceId, summary, expectedResponse);

    testCase.payerFirstName = testCase.getValidValue(VALID_FIELD_PAYER_FIRST_NAME);
    testCase.payerLastName = testCase.getValidValue(VALID_FIELD_PAYER_LAST_NAME);
    testCase.accountType = testCase.getValidValue(VALID_FIELD_ACCOUNT_TYPE);
    testCase.accountFullName = testCase.getValidValue(VALID_FIELD_ACCOUNT_FULL_NAME);
    testCase.routingNumber = testCase.getValidValue(VALID_FIELD_ROUTING_NUMBER);
    testCase.accountNumber = testCase.getValidValue(VALID_FIELD_ACCOUNT_NUMBER);

    return testCase;
  }

  protected void initValidValues() {
    super.initValidValues();
    validValues.put(VALID_FIELD_PAYER_REFERENCE_ID, new String[]{
        "GAPITester1235a",
    });
    validValues.put(VALID_FIELD_PAYER_FIRST_NAME, new String[]{
        "Foxy", "Bryon", "K!a1@t#h$r4%y^n&", "Kaylee", "JeffersonBarwickBarrickNorthst",
        "Tammy", "Susan", "Brian", "Eugene", "Jose", "Jonathan", "Albert", "Mary", "Kurt",
        "N_-+=eil", "Kenneth",
    });
    validValues.put(VALID_FIELD_PAYER_LAST_NAME, new String[]{
        "Shuttle", "Hendrix", "B4*r(o4)wn", "Lanier", "AlistaireBenjaminLafayetteTrya",
        "Murphy", "Bell", "Baker", "Carter", "Edwards", "Martin", "Powell", "Myers",
        "Wil_-+=son", "Evans",
    });
    validValues.put(VALID_FIELD_ACCOUNT_TYPE, new String[]{
        "Checking", "Savings", "Business Checking",
    });
    validValues.put(VALID_FIELD_ACCOUNT_FULL_NAME, new String[]{
        "Foxy Shuttle", "Bryon Hendrix", "Kathryn Brown", "K!a@y#l$e%e^ L&a*n(i)e_r+",
        "JeffersonBarwickBarrickNorthstAlistaireBenjamin LafayetteTryaJeffersonBarwickBarrickNorthstAlistaire",
        "Tammy Murphy", "Susan Bell", "Brian Baker", "Eugene Carter", "Jose Edwards",
        "Jonathan Martin", "Albert Bell", "Kenneth Evans",
        "JeffersonBarwickBarrickNorthst AlistaireBenjaminLafayetteTrya", "Mary Powell",
        "Kur_-+=t A. Myers", "Neil Wilson",
    });
    validValues.put(VALID_FIELD_ROUTING_NUMBER, new String[]{
        "490000018",
    });
    validValues.put(VALID_FIELD_ACCOUNT_NUMBER, new String[]{
        "614800811", "61480081",
    });
  }

  public CreateBankPayerAccount setPayerFirstName(String payerFirstName) {
    this.payerFirstName = payerFirstName;
    return this;
  }

  public CreateBankPayerAccount setPayerLastName(String payerLastName) {
    this.payerLastName = payerLastName;
    return this;
  }

  public CreateBankPayerAccount setPayerReferenceId(String payerReferenceId) {
    this.payerReferenceId = payerReferenceId;
    return this;
  }

  public CreateBankPayerAccount setAccountType(String accountType) {
    this.accountType = accountType;
    return this;
  }

  public CreateBankPayerAccount setAccountFullName(String accountFullName) {
    this.accountFullName = accountFullName;
    return this;
  }

  public CreateBankPayerAccount setRoutingNumber(String routingNumber) {
    this.routingNumber = routingNumber;
    return this;
  }

  public CreateBankPayerAccount setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
    return this;
  }

  @Override
  public void addTransaction(Request apiRequest) {
    HashMap<String, String> parameters = new HashMap<>();

    parameters.put(CreateBankPayerAccountTransaction.PAYER_REFERENCE_ID, payerReferenceId);
    parameters.put(CreateBankPayerAccountTransaction.PAYER_FIRST_NAME, payerFirstName);
    parameters.put(CreateBankPayerAccountTransaction.PAYER_LAST_NAME, payerLastName);
    parameters.put(CreateBankPayerAccountTransaction.ACCOUNT_TYPE, accountType);
    parameters.put(CreateBankPayerAccountTransaction.ACCOUNT_FULL_NAME, accountFullName);
    parameters.put(CreateBankPayerAccountTransaction.ROUTING_NUMBER, routingNumber);
    parameters.put(CreateBankPayerAccountTransaction.ACCOUNT_NUMBER, accountNumber);

    GapiTransaction transaction = new CreateBankPayerAccountTransaction(parameters);
    apiRequest.addTransaction(transaction);
  }
}
