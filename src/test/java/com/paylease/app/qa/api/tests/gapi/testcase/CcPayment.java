package com.paylease.app.qa.api.tests.gapi.testcase;

import com.github.javafaker.Faker;
import com.paylease.app.qa.api.tests.ExpectedResponse;
import com.paylease.app.qa.framework.DataHelper;
import com.paylease.app.qa.framework.api.Parameter;
import com.paylease.app.qa.framework.api.ParameterCollection;
import com.paylease.app.qa.framework.api.Request;
import com.paylease.app.qa.framework.api.gapi.CcPaymentTransaction;
import java.util.ArrayList;
import java.util.HashMap;


public class CcPayment extends BasicTestCase {

  private static final String VALID_FIELD_PAYER_REFERENCE_ID = "payerReferenceId";
  private static final String VALID_FIELD_PAYER_FIRST_NAME = "payerFirstName";
  private static final String VALID_FIELD_PAYER_LAST_NAME = "payerLastName";
  private static final String VALID_FIELD_CREDIT_CARD_TYPE = "CreditCardType";
  private static final String VALID_FIELD_CREDIT_CARD_NUMBER = "CreditCardNumber";
  private static final String VALID_FIELD_CREDIT_CARD_EXP_MONTH = "CreditCardExpMonth";
  private static final String VALID_FIELD_CREDIT_CARD_EXP_YEAR = "CreditCardExpYear";
  private static final String VALID_FIELD_CREDIT_CARD_CVV2 = "CreditCardCvv2";
  private static final String VALID_FIELD_BILLING_FIRST_NAME = "BillingFirstName";
  private static final String VALID_FIELD_BILLING_LAST_NAME = "BillingLastName";
  private static final String VALID_FIELD_BILLING_STREET_ADDRESS = "BillingStreetAddress";
  private static final String VALID_FIELD_BILLING_CITY = "BillingCity";
  private static final String VALID_FIELD_BILLING_STATE = "BillingState";
  private static final String VALID_FIELD_BILLING_COUNTRY = "BillingCountry";
  private static final String VALID_FIELD_BILLING_ZIP = "BillingZip";
  private static final String VALID_FIELD_TOTAL_AMOUNT = "totalAmount";
  private static final String VALID_FIELD_FEE_AMOUNT = "feeAmount";
  private static final String VALID_FIELD_INCUR_FEE = "incurFee";
  private static final String VALID_FIELD_SAVE_ACCOUNT = "saveAccount";

  protected String paymentReferenceId;
  protected String payerReferenceId;
  protected String payerSecondaryRefId;
  protected String payeeId;
  protected String payerFirstName;
  protected String payerLastName;
  protected String creditCardType;
  protected String creditCardNumber;
  protected String creditCardExpMonth;
  protected String creditCardExpYear;
  protected String creditCardCvv2;
  protected String billingFirstName;
  protected String billingLastName;
  protected String billingStreetAddress;
  protected String billingCity;
  protected String billingState;
  protected String billingCountry;
  protected String billingZip;
  protected String totalAmount;
  protected String feeAmount;
  protected String message;
  protected String incurFee;
  protected String saveAccount;
  protected String creditCardAction;
  protected String currencyCode;
  protected String amount;
  private ArrayList<HashMap<String, String>> depositItems;

  /**
   * CCPayment test case.
   *
   * @param paymentReferenceId PaymentReferenceId value
   * @param summary Summary of this test case
   * @param expectedResponse Expected response
   * @param payeeId Valid PayeeId given by test setup
   */
  public CcPayment(
      String paymentReferenceId, String summary, ExpectedResponse expectedResponse, String payeeId
  ) {
    this(paymentReferenceId, summary, expectedResponse, null,
        null, payeeId, null, null,
        null, null, null, null,
        null, null, null, null,
        null, null, null, null, null,
        null, null, null, null, null);
  }

  private CcPayment(
      String paymentReferenceId, String summary, ExpectedResponse expectedResponse,
      String payerReferenceId,
      String payerSecondaryRefId, String payeeId, String payerFirstName, String payerLastName,
      String creditCardType, String creditCardNumber, String creditCardExpMonth,
      String creditCardExpYear, String creditCardCvv2, String billingFirstName,
      String billingLastName, String billingStreetAddress, String billingCity, String billingState,
      String billingCountry, String billingZip, String totalAmount, String feeAmount,
      String message, String incurFee, String saveAccount, String creditCardAction
  ) {
    super(summary, expectedResponse);

    this.paymentReferenceId = paymentReferenceId;
    this.payeeId = payeeId;
    this.payerReferenceId = payerReferenceId;
    this.payerSecondaryRefId = payerSecondaryRefId;
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
    this.totalAmount = totalAmount;
    this.feeAmount = feeAmount;
    this.message = message;
    this.incurFee = incurFee;
    this.saveAccount = saveAccount;
    this.creditCardAction = creditCardAction;
    this.depositItems = new ArrayList<>();
  }

  /**
   * CCPayment valid test case.
   *
   * @param paymentReferenceId PaymentReferenceId value
   * @param summary Summary of this test case
   * @param expectedResponse Expected response
   * @param payeeId Valid PayeeId given by test setup
   * @return CcPayment test case that can be submitted as valid
   */
  public static CcPayment createValid(
      String paymentReferenceId, String summary, ExpectedResponse expectedResponse, String payeeId
  ) {
    CcPayment testCase = new CcPayment(paymentReferenceId, summary, expectedResponse, payeeId);

    testCase.payerReferenceId = testCase.getValidValue(VALID_FIELD_PAYER_REFERENCE_ID);
    testCase.payerFirstName = testCase.getValidValue(VALID_FIELD_PAYER_FIRST_NAME);
    testCase.payerLastName = testCase.getValidValue(VALID_FIELD_PAYER_LAST_NAME);
    testCase.creditCardType = testCase.getValidValue(VALID_FIELD_CREDIT_CARD_TYPE);
    testCase.creditCardNumber = testCase.getValidValue(VALID_FIELD_CREDIT_CARD_NUMBER);
    testCase.creditCardExpMonth = testCase.getValidValue(VALID_FIELD_CREDIT_CARD_EXP_MONTH);
    testCase.creditCardExpYear = testCase.getValidValue(VALID_FIELD_CREDIT_CARD_EXP_YEAR);
    testCase.creditCardCvv2 = testCase.getValidValue(VALID_FIELD_CREDIT_CARD_CVV2);
    testCase.billingFirstName = testCase.getValidValue(VALID_FIELD_BILLING_FIRST_NAME);
    testCase.billingLastName = testCase.getValidValue(VALID_FIELD_BILLING_LAST_NAME);
    testCase.billingStreetAddress = testCase.getValidValue(VALID_FIELD_BILLING_STREET_ADDRESS);
    testCase.billingCity = testCase.getValidValue(VALID_FIELD_BILLING_CITY);
    testCase.billingState = testCase.getValidValue(VALID_FIELD_BILLING_STATE);
    testCase.billingCountry = testCase.getValidValue(VALID_FIELD_BILLING_COUNTRY);
    testCase.billingZip = testCase.getValidValue(VALID_FIELD_BILLING_ZIP);
    testCase.totalAmount = testCase.getValidValue(VALID_FIELD_TOTAL_AMOUNT);
    testCase.feeAmount = testCase.getValidValue(VALID_FIELD_FEE_AMOUNT);
    testCase.incurFee = testCase.getValidValue(VALID_FIELD_INCUR_FEE);
    testCase.saveAccount = testCase.getValidValue(VALID_FIELD_SAVE_ACCOUNT);

    return testCase;
  }

  protected void initValidValues() {
    super.initValidValues();
    validValues.put(VALID_FIELD_PAYER_REFERENCE_ID, new String[]{
        "GAPITesterACH", "GAPITesterACH1",
    });
    validValues.put(VALID_FIELD_PAYER_FIRST_NAME, new String[]{
        "Foxy", "Bryon", "Sean", "Kaylee", "JeffersonBarwickBarrickNorthst",
        "Tammy", "Susan", "Brian", "Eugene", "Jose", "Jonathan", "Albert", "Mary", "Kurt",
        "N_-+=eil", "Kenneth",
    });
    validValues.put(VALID_FIELD_PAYER_LAST_NAME, new String[]{
        "Shuttle", "Hendrix", "Brown", "Lanier", "AlistaireBenjaminLafayetteTrya",
        "Murphy", "Bell", "Baker", "Carter", "Edwards", "Martin", "Powell", "Myers",
        "Wil_-+=son", "Evans",
    });
    validValues.put(VALID_FIELD_CREDIT_CARD_TYPE, new String[]{
        "MasterCard",
    });
    validValues.put(VALID_FIELD_CREDIT_CARD_NUMBER, new String[]{
        "5112000100000003",
    });
    validValues.put(VALID_FIELD_CREDIT_CARD_EXP_MONTH, new String[]{
        "5",
    });
    validValues.put(VALID_FIELD_CREDIT_CARD_EXP_YEAR, new String[]{
        "25",
    });
    validValues.put(VALID_FIELD_CREDIT_CARD_CVV2, new String[]{
        "369", "136",
    });
    validValues.put(VALID_FIELD_BILLING_FIRST_NAME, new String[]{
        "Foxy", "Bryon", "Kathy", "Kaylee", "JeffersonBarwickBarrickNorthst",
        "Tammy", "Susan", "Brian", "Eugene", "Jose", "Jonathan", "Albert", "Mary", "Kurt",
        "N_-+=eil", "Kenneth",
    });
    validValues.put(VALID_FIELD_BILLING_LAST_NAME, new String[]{
        "Shuttle", "Hendrix", "Brown", "Lanier", "AlistaireBenjaminLafayetteTrya",
        "Murphy", "Bell", "Baker", "Carter", "Edwards", "Martin", "Powell", "Myers",
        "Wil_-+=son", "Evans",
    });
    validValues.put(VALID_FIELD_BILLING_STREET_ADDRESS, new String[]{
        "369 Atbash Court"
    });
    validValues.put(VALID_FIELD_BILLING_CITY, new String[]{
        "Fallbrook", "Mooselookmeguntic",
    });
    validValues.put(VALID_FIELD_BILLING_STATE, new String[]{
        "CA",
    });
    validValues.put(VALID_FIELD_BILLING_COUNTRY, new String[]{
        "US",
    });
    validValues.put(VALID_FIELD_BILLING_ZIP, new String[]{
        "92028",
    });

    Faker faker = new Faker();

    String[] totals = new String[10];
    for (int i = 0; i < 10; i++) {
      totals[i] = faker.commerce().price(100, 500);
    }
    String[] fees = new String[10];
    for (int i = 0; i < 10; i++) {
      fees[i] = faker.commerce().price(0, 50);
    }
    validValues.put(VALID_FIELD_TOTAL_AMOUNT, totals);
    validValues.put(VALID_FIELD_FEE_AMOUNT, fees);

    validValues.put(VALID_FIELD_INCUR_FEE, new String[]{
        "Yes", "No",
    });
    validValues.put(VALID_FIELD_SAVE_ACCOUNT, new String[]{
        "Yes", "No",
    });
  }

  public CcPayment setPayerReferenceId(String payerReferenceId) {
    this.payerReferenceId = payerReferenceId;
    return this;
  }

  public CcPayment setPayerSecondaryRefId(String payerSecondaryRefId) {
    this.payerSecondaryRefId = payerSecondaryRefId;
    return this;
  }

  public CcPayment setPayerFirstName(String payerFirstName) {
    this.payerFirstName = payerFirstName;
    return this;
  }

  public CcPayment setPayerLastName(String payerLastName) {
    this.payerLastName = payerLastName;
    return this;
  }

  public CcPayment setCreditCardType(String creditCardType) {
    this.creditCardType = creditCardType;
    return this;
  }

  public CcPayment setCreditCardNumber(String creditCardNumber) {
    this.creditCardNumber = creditCardNumber;
    return this;
  }

  public CcPayment setCreditExpMonth(String creditCardExpMonth) {
    this.creditCardExpMonth = creditCardExpMonth;
    return this;
  }

  public CcPayment setCreditExpYear(String creditCardExpYear) {
    this.creditCardExpYear = creditCardExpYear;
    return this;
  }

  public CcPayment setCreditCardCvv2(String creditCardCvv2) {
    this.creditCardCvv2 = creditCardCvv2;
    return this;
  }

  public CcPayment setBillingFirstName(String billingFirstName) {
    this.billingFirstName = billingFirstName;
    return this;
  }

  public CcPayment setBillingLastName(String billingLastName) {
    this.billingLastName = billingLastName;
    return this;
  }

  public CcPayment setBillingStreetAddress(String billingStreetAddress) {
    this.billingStreetAddress = billingStreetAddress;
    return this;
  }

  public CcPayment setBillingCity(String billingCity) {
    this.billingCity = billingCity;
    return this;
  }

  public CcPayment setBillingState(String billingState) {
    this.billingState = billingState;
    return this;
  }

  public CcPayment setBillingCountry(String billingCountry) {
    this.billingCountry = billingCountry;
    return this;
  }

  public CcPayment setBillingZip(String billingZip) {
    this.billingZip = billingZip;
    return this;
  }

  public CcPayment setMessage(String message) {
    this.message = message;
    return this;
  }

  public CcPayment setTotalAmount(String totalAmount) {
    this.totalAmount = totalAmount;
    return this;
  }

  public CcPayment setFeeAmount(String feeAmount) {
    this.feeAmount = feeAmount;
    return this;
  }

  public CcPayment setIncurFee(String incurFee) {
    this.incurFee = incurFee;
    return this;
  }

  public CcPayment setSaveAccount(String saveAccount) {
    this.saveAccount = saveAccount;
    return this;
  }

  public CcPayment setCreditCardAction(String creditCardAction) {
    this.creditCardAction = creditCardAction;
    return this;
  }

  public CcPayment setCurrencyCode(String currencyCode){
    this.currencyCode = currencyCode;
    return this;
  }

  @Override
  public void addTransaction(Request apiRequest) {
    DataHelper dataHelper = new DataHelper();
    ParameterCollection parameters = getTransactionParameterCollection();

    String paymentTraceId = dataHelper.getPaymentTraceId();
    parameters.put(CcPaymentTransaction.PAYMENT_REFERENCE_ID, paymentReferenceId);
    parameters.put(CcPaymentTransaction.PAYMENT_TRACE_ID, paymentTraceId);
    parameters.put(CcPaymentTransaction.PAYER_REFERENCE_ID, payerReferenceId);
    parameters.put(CcPaymentTransaction.PAYER_SECONDARY_REFERENCE_ID, payerSecondaryRefId);
    parameters.put(CcPaymentTransaction.PAYEE_ID, payeeId);
    parameters.put(CcPaymentTransaction.PAYER_FIRST_NAME, payerFirstName);
    parameters.put(CcPaymentTransaction.PAYER_LAST_NAME, payerLastName);

    Parameter splitDeposit = new Parameter();
    for (HashMap<String, String> item : depositItems) {
      Parameter deposit = new Parameter();
      deposit.addParameter(CcPaymentTransaction.PAYEE_ID, item.get("payeeId"));
      deposit.addParameter(CcPaymentTransaction.AMOUNT, item.get("amount"));
      splitDeposit.addParameter(CcPaymentTransaction.DEPOSIT,deposit);
    }

    parameters.put(CcPaymentTransaction.SPLIT_DEPOSIT, splitDeposit);

    parameters.put(CcPaymentTransaction.CREDIT_CARD_TYPE, creditCardType);
    parameters.put(CcPaymentTransaction.CREDIT_CARD_NUMBER, creditCardNumber);
    parameters.put(CcPaymentTransaction.CREDIT_CARD_EXP_MONTH, creditCardExpMonth);
    parameters.put(CcPaymentTransaction.CREDIT_CARD_EXP_YEAR, creditCardExpYear);
    parameters.put(CcPaymentTransaction.CREDIT_CARD_CVV2, creditCardCvv2);
    parameters.put(CcPaymentTransaction.BILLING_FIRST_NAME, billingFirstName);
    parameters.put(CcPaymentTransaction.BILLING_LAST_NAME, billingLastName);
    parameters.put(CcPaymentTransaction.BILLING_STREET_ADDRESS, billingStreetAddress);
    parameters.put(CcPaymentTransaction.BILLING_CITY, billingCity);
    parameters.put(CcPaymentTransaction.BILLING_STATE, billingState);
    parameters.put(CcPaymentTransaction.BILLING_COUNTRY, billingCountry);
    parameters.put(CcPaymentTransaction.BILLING_ZIP, billingZip);
    parameters.put(CcPaymentTransaction.TOTAL_AMOUNT, totalAmount);
    parameters.put(CcPaymentTransaction.FEE_AMOUNT, feeAmount);
    parameters.put(CcPaymentTransaction.MESSAGE, message);
    parameters.put(CcPaymentTransaction.INCUR_FEE, incurFee);
    parameters.put(CcPaymentTransaction.SAVE_ACCOUNT, saveAccount);
    parameters.put(CcPaymentTransaction.CREDIT_CARD_ACTION, creditCardAction);
    parameters.put(CcPaymentTransaction.CURRENCY_CODE, currencyCode);

    CcPaymentTransaction gapiTransaction = new CcPaymentTransaction(parameters);
    apiRequest.addTransaction(gapiTransaction);
  }

  /**
   * Function to add Line item elements to ListItems.
   *
   * @param payeeId chargeDays
   * @param amount amount
   * @return a Hashmap of list items
   */
  public CcPayment addDepositItem(String payeeId, String amount) {

    HashMap<String, String> depositItemValues = new HashMap<>();
    depositItemValues.put("payeeId", payeeId);
    depositItemValues.put("amount", amount);

    depositItems.add(depositItemValues);

    return this;
  }
}
