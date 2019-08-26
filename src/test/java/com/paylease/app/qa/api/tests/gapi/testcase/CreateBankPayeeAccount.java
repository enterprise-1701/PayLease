package com.paylease.app.qa.api.tests.gapi.testcase;

import com.paylease.app.qa.api.tests.ExpectedResponse;
import com.paylease.app.qa.framework.api.Request;
import com.paylease.app.qa.framework.api.gapi.CreateBankPayeeAccountTransaction;
import com.paylease.app.qa.framework.api.gapi.GapiTransaction;
import java.util.HashMap;

public class CreateBankPayeeAccount extends BasicTestCase {

  private static final String VALID_FIELD_PAYEE_FIRST_NAME = "payeeFirstName";
  private static final String VALID_FIELD_PAYEE_LAST_NAME = "payeeLastName";
  private static final String VALID_FIELD_ACCOUNT_TYPE = "accountType";
  private static final String VALID_FIELD_ACCOUNT_FULL_NAME = "accountFullName";
  private static final String VALID_FIELD_ROUTING_NUMBER = "routingNumber";
  private static final String VALID_FIELD_ACCOUNT_NUMBER = "accountNumber";

  private String payeeReferenceId;
  private String payeeFirstName;
  private String payeeLastName;
  private String payeeState;
  private String accountType;
  private String accountFullName;
  private String routingNumber;
  private String accountNumber;

  public CreateBankPayeeAccount(
      String payeeReferenceId, String summary, ExpectedResponse expectedResponse
  ) {
    this(payeeReferenceId, summary, expectedResponse, null, null, null,
        null, null, null, null);
  }

  private CreateBankPayeeAccount(
      String payeeReferenceId, String summary, ExpectedResponse expectedResponse,
      String payeeFirstName,
      String payeeLastName, String payeeState, String accountType, String accountFullName,
      String routingNumber, String accountNumber
  ) {
    super(summary, expectedResponse);

    this.payeeReferenceId = payeeReferenceId;
    this.payeeFirstName = payeeFirstName;
    this.payeeLastName = payeeLastName;
    this.payeeState = payeeState;
    this.accountType = accountType;
    this.accountFullName = accountFullName;
    this.routingNumber = routingNumber;
    this.accountNumber = accountNumber;
  }

  /**
   * Factory method to create a valid request.
   *
   * @param payeeReferenceId PayeeReferenceId value
   * @param summary Summary of this test case
   * @param expectedResponse response expected to be returned by GAPI
   * @return valid test case
   */
  public static CreateBankPayeeAccount createValid(
      String payeeReferenceId, String summary, ExpectedResponse expectedResponse
  ) {
    CreateBankPayeeAccount testCase = new CreateBankPayeeAccount(
        payeeReferenceId, summary, expectedResponse);

    testCase.payeeFirstName = testCase.getValidValue(VALID_FIELD_PAYEE_FIRST_NAME);
    testCase.payeeLastName = testCase.getValidValue(VALID_FIELD_PAYEE_LAST_NAME);
    testCase.accountFullName = testCase.getValidValue(VALID_FIELD_ACCOUNT_FULL_NAME);
    testCase.accountType = testCase.getValidValue(VALID_FIELD_ACCOUNT_TYPE);
    testCase.routingNumber = testCase.getValidValue(VALID_FIELD_ROUTING_NUMBER);
    testCase.accountNumber = testCase.getValidValue(VALID_FIELD_ACCOUNT_NUMBER);

    return testCase;
  }

  protected void initValidValues() {
    super.initValidValues();
    validValues.put(VALID_FIELD_PAYEE_FIRST_NAME, new String[]{
        "Foxy", "Bryon", "K!a1@t#h$r4%y^n&", "Kaylee", "JeffersonBarwickBarrickNorthst",
        "Tammy", "Susan", "Brian", "Eugene", "Jose", "Jonathan", "Albert", "Mary", "Kurt",
        "N_-+=eil", "Kenneth",
    });
    validValues.put(VALID_FIELD_PAYEE_LAST_NAME, new String[]{
        "Shuttle", "Hendrix", "B4*r(o4)wn", "Lanier", "AlistaireBenjaminLafayetteTrya",
        "Murphy", "Bell", "Baker", "Carter", "Edwards", "Martin", "Powell", "Myers",
        "Wil_-+=son", "Evans",
    });
    validValues.put(VALID_FIELD_ACCOUNT_FULL_NAME, new String[]{
        "Foxy Shuttle", "Bryon Hendrix", "Kathryn Brown", "K!a@y#l$e%e^ L&a*n(i)e_r+",
        "JeffersonBarwickBarrickNorthstAlistaireBenjamin LafayetteTryaJeffersonBarwickBarrickNorthstAlistaire",
        "Tammy Murphy", "Susan Bell", "Brian Baker", "Eugene Carter", "Jose Edwards",
        "Jonathan Martin", "Albert Bell", "Kenneth Evans",
        "JeffersonBarwickBarrickNorthst AlistaireBenjaminLafayetteTrya", "Mary Powell",
        "Kur_-+=t A. Myers", "Neil Wilson",
    });
    validValues.put(VALID_FIELD_ACCOUNT_TYPE, new String[]{
        "Checking", "Savings", "Business Checking",
    });
    validValues.put(VALID_FIELD_ROUTING_NUMBER, new String[]{
        "490000018",
    });
    validValues.put(VALID_FIELD_ACCOUNT_NUMBER, new String[]{
        "614800811", "61480081",
    });
  }

  public CreateBankPayeeAccount setPayeeFirstName(String payeeFirstName) {
    this.payeeFirstName = payeeFirstName;
    return this;
  }

  public CreateBankPayeeAccount setPayeeLastName(String payeeLastName) {
    this.payeeLastName = payeeLastName;
    return this;
  }

  public CreateBankPayeeAccount setPayeeState(String payeeState) {
    this.payeeState = payeeState;
    return this;
  }

  public CreateBankPayeeAccount setAccountType(String accountType) {
    this.accountType = accountType;
    return this;
  }

  public CreateBankPayeeAccount setAccountFullName(String accountFullName) {
    this.accountFullName = accountFullName;
    return this;
  }

  public CreateBankPayeeAccount setRoutingNumber(String routingNumber) {
    this.routingNumber = routingNumber;
    return this;
  }

  public CreateBankPayeeAccount setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
    return this;
  }

  @Override
  public void addTransaction(Request apiRequest) {
    HashMap<String, String> parameters = new HashMap<>();
    parameters.put(CreateBankPayeeAccountTransaction.PAYEE_REFERENCE_ID, payeeReferenceId);
    parameters.put(CreateBankPayeeAccountTransaction.PAYEE_FIRST_NAME, payeeFirstName);
    parameters.put(CreateBankPayeeAccountTransaction.PAYEE_LAST_NAME, payeeLastName);
    parameters.put(CreateBankPayeeAccountTransaction.PAYEE_STATE, payeeState);
    parameters.put(CreateBankPayeeAccountTransaction.ACCOUNT_TYPE, accountType);
    parameters.put(CreateBankPayeeAccountTransaction.ACCOUNT_FULL_NAME, accountFullName);
    parameters.put(CreateBankPayeeAccountTransaction.ROUTING_NUMBER, routingNumber);
    parameters.put(CreateBankPayeeAccountTransaction.ACCOUNT_NUMBER, accountNumber);

    GapiTransaction transaction = new CreateBankPayeeAccountTransaction(parameters);
    apiRequest.addTransaction(transaction);
  }
}
