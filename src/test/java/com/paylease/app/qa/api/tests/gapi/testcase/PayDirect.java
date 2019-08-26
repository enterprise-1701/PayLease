package com.paylease.app.qa.api.tests.gapi.testcase;

import com.github.javafaker.Faker;
import com.paylease.app.qa.api.tests.ExpectedResponse;
import com.paylease.app.qa.framework.DataHelper;
import com.paylease.app.qa.framework.api.Request;
import com.paylease.app.qa.framework.api.gapi.GapiTransaction;
import com.paylease.app.qa.framework.api.gapi.PayDirectTransaction;
import java.util.HashMap;

public class PayDirect extends BasicTestCase {

  private static final String VALID_FIELD_PAYEE_REFERENCE_ID = "payeeReferenceId";
  private static final String VALID_FIELD_PAYEE_FIRST_NAME = "payeeFirstName";
  private static final String VALID_FIELD_PAYEE_LAST_NAME = "payeeLastName";
  private static final String VALID_FIELD_PAYEE_EMAIL_ADDRESS = "payeeEmailAddress";
  private static final String VALID_FIELD_ACCOUNT_TYPE = "accountType";
  private static final String VALID_FIELD_ACCOUNT_FULL_NAME = "accountFullName";
  private static final String VALID_FIELD_ROUTING_NUMBER = "routingNumber";
  private static final String VALID_FIELD_ACCOUNT_NUMBER = "accountNumber";
  private static final String VALID_FIELD_TOTAL_AMOUNT = "totalAmount";
  private static final String VALID_FIELD_SAVE_ACCOUNT = "saveAccount";

  private String paymentReferenceId;
  private String payerId;
  private String payeeReferenceId;
  private String payeeFirstName;
  private String payeeLastName;
  private String payeeEmailAddress;
  private String accountType;
  private String accountFullName;
  private String routingNumber;
  private String accountNumber;
  private String totalAmount;
  private String saveAccount;
  private String payeeState;

  public PayDirect(String paymentReferenceId, String summary, ExpectedResponse expectedResponse,
      String payerId) {
    this(paymentReferenceId, summary, expectedResponse, payerId, null, null, null,
        null, null, null, null, null, null, null, null);
  }

  private PayDirect(
      String paymentReferenceId, String summary, ExpectedResponse expectedResponse, String payerId,
      String payeeReferenceId, String payeeFirstName, String payeeLastName,
      String payeeEmailAddress, String accountType, String accountFullName, String routingNumber,
      String accountNumber, String totalAmount, String saveAccount, String payeeState
  ) {
    super(summary, expectedResponse);

    this.paymentReferenceId = paymentReferenceId;

    this.payerId = payerId;
    this.payeeReferenceId = payeeReferenceId;
    this.payeeFirstName = payeeFirstName;
    this.payeeLastName = payeeLastName;
    this.payeeEmailAddress = payeeEmailAddress;
    this.accountType = accountType;
    this.accountFullName = accountFullName;
    this.routingNumber = routingNumber;
    this.accountNumber = accountNumber;
    this.totalAmount = totalAmount;
    this.saveAccount = saveAccount;
    this.payeeState = payeeState;
  }

  /**
   * Create a valid test case for PayDirect.
   *
   * @param paymentReferenceId Key value for this test case (PaymentReferenceId)
   * @param summary Summary for this test case
   * @param expectedResponse Expected response to be returned by GAPI
   * @param payerId valid PayerId given by test setup
   * @return Valid test case
   */
  public static PayDirect createValid(
      String paymentReferenceId, String summary, ExpectedResponse expectedResponse,
      String payerId) {
    PayDirect testCase = new PayDirect(paymentReferenceId, summary, expectedResponse, payerId);

    testCase.payeeReferenceId = testCase.getValidValue(VALID_FIELD_PAYEE_REFERENCE_ID);
    testCase.payeeFirstName = testCase.getValidValue(VALID_FIELD_PAYEE_FIRST_NAME);
    testCase.payeeLastName = testCase.getValidValue(VALID_FIELD_PAYEE_LAST_NAME);
    testCase.accountType = testCase.getValidValue(VALID_FIELD_ACCOUNT_TYPE);
    testCase.accountFullName = testCase.getValidValue(VALID_FIELD_ACCOUNT_FULL_NAME);
    testCase.routingNumber = testCase.getValidValue(VALID_FIELD_ROUTING_NUMBER);
    testCase.accountNumber = testCase.getValidValue(VALID_FIELD_ACCOUNT_NUMBER);
    testCase.totalAmount = testCase.getValidValue(VALID_FIELD_TOTAL_AMOUNT);
    testCase.saveAccount = testCase.getValidValue(VALID_FIELD_SAVE_ACCOUNT);

    return testCase;
  }

  protected void initValidValues() {
    super.initValidValues();
    validValues.put(VALID_FIELD_PAYEE_FIRST_NAME, new String[]{
        "GPAI ACH", "Bryon", "K!a1@t#h$r4%y^n&", "Kaylee", "N_-+=eil", "Kurt", "Lee", "Kenneth",
        "Brian", "Amanda", "Mary", "JeffersonBarwickBarrickNorthst", "Tammy", "Susan", "Jonathan",
        "Jose", "Robert", "Albert",
    });
    validValues.put(VALID_FIELD_PAYEE_LAST_NAME, new String[]{
        "TESTER", "Hendrix", "B4*r(o4)wn", "Lanier", "Wil_-+=son", "Myers", "Frazier", "Evans",
        "Baker", "Phillips", "Powell", "AlistaireBenjaminLafayetteTrya", "Murphy", "Bell", "Martin",
        "Edwards", "Diaz",
    });
    validValues.put(VALID_FIELD_PAYEE_REFERENCE_ID, new String[]{
        "GAPITesterACH",
    });
    validValues.put(VALID_FIELD_PAYEE_EMAIL_ADDRESS, new String[]{
        "", "bobtester@paylease.com", "test@iana.org", "test@nominet.org.uk", "test@about.museum",
        "a@iana.org", "test@e.com", "test@iana.a", "test.test@iana.org", "123@iana.org",
        "test@123.com", "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghiklm@iana.org",
        "test@mason-dixon.com", "test@c--n.com", "test@iana.co-uk",
    });
    validValues.put(VALID_FIELD_ACCOUNT_TYPE, new String[]{
        "Savings", "Checking",
    });
    validValues.put(VALID_FIELD_ACCOUNT_FULL_NAME, new String[]{
        "GAPI ACH Tester", "Bryon Hendrix", "Kathryn Brown", "K!a@y#l$e%e^ L&a*n(i)e_r+",
        "Neil Wilson", "Kur_-+=t A. Myers", "Frazier Lee", "Kenneth Evans", "Brian Baker",
        "Amanda Phillips", "Mary Powell",
        "JeffersonBarwickBarrickNorthst AlistaireBenjaminLafayetteTrya",
        "Tammy Murphy", "Susan Bell", "Jonathan Martin", "Jose Edwards", "Robert Diaz",
        "Albert Bell",
    });
    validValues.put(VALID_FIELD_ROUTING_NUMBER, new String[]{
        "490000018",
    });
    validValues.put(VALID_FIELD_ACCOUNT_NUMBER, new String[]{
        "614800811",
    });

    Faker faker = new Faker();

    String[] totals = new String[10];
    for (int i = 0; i < 10; i++) {
      totals[i] = faker.commerce().price(100, 500);
    }
    validValues.put(VALID_FIELD_TOTAL_AMOUNT, totals);

    validValues.put(VALID_FIELD_SAVE_ACCOUNT, new String[]{
        "No",
    });
  }

  public PayDirect setPayeeReferenceId(String payeeReferenceId) {
    this.payeeReferenceId = payeeReferenceId;
    return this;
  }

  public PayDirect setPayeeFirstName(String payeeFirstName) {
    this.payeeFirstName = payeeFirstName;
    return this;
  }

  public PayDirect setPayeeLastName(String payeeLastName) {
    this.payeeLastName = payeeLastName;
    return this;
  }

  public PayDirect setPayeeEmailAddress(String payeeEmailAddress) {
    this.payeeEmailAddress = payeeEmailAddress;
    return this;
  }

  public PayDirect setAccountType(String accountType) {
    this.accountType = accountType;
    return this;
  }

  public PayDirect setAccountFullName(String accountFullName) {
    this.accountFullName = accountFullName;
    return this;
  }

  public PayDirect setRoutingNumber(String routingNumber) {
    this.routingNumber = routingNumber;
    return this;
  }

  public PayDirect setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
    return this;
  }

  public PayDirect setTotalAmount(String totalAmount) {
    this.totalAmount = totalAmount;
    return this;
  }

  public PayDirect setSaveAccount(String saveAccount) {
    this.saveAccount = saveAccount;
    return this;
  }

  public PayDirect setPayeeState(String payeeState) {
    this.payeeState = payeeState;
    return this;
  }

  @Override
  public void addTransaction(Request apiRequest) {
    DataHelper dataHelper = new DataHelper();

    HashMap<String, String> parameters = new HashMap<>();
    parameters.put(PayDirectTransaction.PAYMENT_REFERENCE_ID, paymentReferenceId);
    parameters.put(PayDirectTransaction.PAYMENT_TRACE_ID, dataHelper.getReferenceId());
    parameters.put(PayDirectTransaction.PAYER_ID, payerId);
    parameters.put(PayDirectTransaction.PAYEE_REFERENCE_ID, payeeReferenceId);
    parameters.put(PayDirectTransaction.PAYEE_FIRST_NAME, payeeFirstName);
    parameters.put(PayDirectTransaction.PAYEE_LAST_NAME, payeeLastName);
    parameters.put(PayDirectTransaction.PAYEE_EMAIL_ADDRESS, payeeEmailAddress);
    parameters.put(PayDirectTransaction.PAYEE_STATE, payeeState);
    parameters.put(PayDirectTransaction.ACCOUNT_TYPE, accountType);
    parameters.put(PayDirectTransaction.ACCOUNT_FULL_NAME, accountFullName);
    parameters.put(PayDirectTransaction.ROUTING_NUMBER, routingNumber);
    parameters.put(PayDirectTransaction.ACCOUNT_NUMBER, accountNumber);
    parameters.put(PayDirectTransaction.TOTAL_AMOUNT, totalAmount);
    parameters.put(PayDirectTransaction.SAVE_ACCOUNT, saveAccount);

    GapiTransaction transaction = new PayDirectTransaction(parameters);
    apiRequest.addTransaction(transaction);
  }
}
