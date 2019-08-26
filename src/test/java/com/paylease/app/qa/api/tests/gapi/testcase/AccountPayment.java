package com.paylease.app.qa.api.tests.gapi.testcase;

import com.github.javafaker.Faker;
import com.paylease.app.qa.api.tests.ExpectedResponse;
import com.paylease.app.qa.framework.DataHelper;
import com.paylease.app.qa.framework.api.Parameter;
import com.paylease.app.qa.framework.api.ParameterCollection;
import com.paylease.app.qa.framework.api.Request;
import com.paylease.app.qa.framework.api.gapi.AccountPaymentTransaction;
import com.paylease.app.qa.framework.api.gapi.CcPaymentTransaction;
import com.paylease.app.qa.framework.api.gapi.GapiTransaction;
import java.util.ArrayList;
import java.util.HashMap;

public class AccountPayment extends BasicTestCase {

  private static final String VALID_FIELD_TOTAL_AMOUNT = "totalAmount";
  private static final String VALID_FIELD_PAYER_REFERENCE_ID = "payerReferenceId";

  private String paymentReferenceId;
  private String payeeId;
  private String payerReferenceId;
  private String gatewayPayerId;
  private String totalAmount;
  private String feeAmount;
  private String incurFee;
  private String checkScanned;
  private String currencyCode;
  private ArrayList<HashMap<String, String>> depositItems;

  /**
   * AccountPayment test case.
   *
   * @param paymentReferenceId PaymentReferenceId value
   * @param summary Summary of this test case
   * @param expectedResponse Expected response
   * @param payeeId Valid PayeeId given by test setup
   * @param gatewayPayerId Valid GatewayPayerId given by test setup
   */
  public AccountPayment(
      String paymentReferenceId, String summary, ExpectedResponse expectedResponse, String payeeId,
      String gatewayPayerId
  ) {
    this(paymentReferenceId, summary, expectedResponse, null, payeeId, null,
        gatewayPayerId, null, null, null, null);
    DataHelper dataHelper = new DataHelper();
    this.payerReferenceId = dataHelper.getPayerReferenceId();
  }

  private AccountPayment(
      String paymentReferenceId, String summary, ExpectedResponse expectedResponse,
      String totalAmount,
      String payeeId, String payerReferenceId, String gatewayPayerId, String feeAmount,
      String incurFee, String checkScanned, String currencyCode
  ) {
    super(summary, expectedResponse);

    this.paymentReferenceId = paymentReferenceId;
    this.payeeId = payeeId;
    this.payerReferenceId = payerReferenceId;
    this.gatewayPayerId = gatewayPayerId;
    this.totalAmount = totalAmount;
    this.feeAmount = feeAmount;
    this.incurFee = incurFee;
    this.checkScanned = checkScanned;
    this.currencyCode = currencyCode;
    this.depositItems = new ArrayList<>();
  }

  /**
   * Create Valid AccountPayment test case.
   *
   * @param paymentReferenceId Key value for this test case (PaymentReferenceId)
   * @param summary Summary for this test case
   * @param expectedResponse response expected to be returned by GAPI
   * @param payeeId Valid PayeeId given by test setup
   * @param gatewayPayerId Valid GatewayPayerId given by test setup
   * @return AccountPayment test case that can be submitted as valid
   */
  public static AccountPayment createValid(
      String paymentReferenceId, String summary, ExpectedResponse expectedResponse, String payeeId,
      String gatewayPayerId
  ) {
    AccountPayment testCase = new AccountPayment(
        paymentReferenceId, summary, expectedResponse, payeeId, gatewayPayerId);

    testCase.payerReferenceId = testCase.getValidValue(VALID_FIELD_PAYER_REFERENCE_ID);
    testCase.totalAmount = testCase.getValidValue(VALID_FIELD_TOTAL_AMOUNT);

    return testCase;
  }

  protected void initValidValues() {
    super.initValidValues();
    Faker faker = new Faker();
    DataHelper dataHelper = new DataHelper();

    String[] totals = new String[10];
    for (int i = 0; i < 10; i++) {
      totals[i] = faker.commerce().price(100, 500);
    }
    validValues.put(VALID_FIELD_TOTAL_AMOUNT, totals);

    String[] referenceIds = new String[10];
    for (int i = 0; i < 10; i++) {
      referenceIds[i] = dataHelper.getPayerReferenceId();
    }
    validValues.put(VALID_FIELD_PAYER_REFERENCE_ID, referenceIds);
  }

  public AccountPayment setPayerReferenceId(String payerReferenceId) {
    this.payerReferenceId = payerReferenceId;
    return this;
  }

  public AccountPayment setTotalAmount(String totalAmount) {
    this.totalAmount = totalAmount;
    return this;
  }

  public AccountPayment setFeeAmount(String feeAmount) {
    this.feeAmount = feeAmount;
    return this;
  }

  public AccountPayment setIncurFee(String incurFee) {
    this.incurFee = incurFee;
    return this;
  }

  public AccountPayment setCheckScanned(String checkScanned) {
    this.checkScanned = checkScanned;
    return this;
  }

  public AccountPayment setCurrencyCode(String currencyCode) {
    this.currencyCode = currencyCode;
    return this;
  }

  @Override
  public void addTransaction(Request apiRequest) {
    DataHelper dataHelper = new DataHelper();

    String paymentTraceId = dataHelper.getPaymentTraceId();
    ParameterCollection parameters = getTransactionParameterCollection();
    parameters.put(AccountPaymentTransaction.PAYMENT_REFERENCE_ID, paymentReferenceId);
    parameters.put(AccountPaymentTransaction.PAYMENT_TRACE_ID, paymentTraceId);
    parameters.put(AccountPaymentTransaction.PAYER_REFERENCE_ID, payerReferenceId);
    parameters.put(AccountPaymentTransaction.PAYEE_ID, payeeId);
    parameters.put(AccountPaymentTransaction.GATEWAY_PAYER_ID, gatewayPayerId);
    parameters.put(AccountPaymentTransaction.TOTAL_AMOUNT, totalAmount);
    parameters.put(AccountPaymentTransaction.FEE_AMOUNT, feeAmount);
    parameters.put(AccountPaymentTransaction.INCUR_FEE, incurFee);
    parameters.put(AccountPaymentTransaction.CHECK_SCANNED, checkScanned);
    parameters.put(AccountPaymentTransaction.CURRENCY_CODE, currencyCode);

    Parameter splitDeposit = new Parameter();
    for (HashMap<String, String> item : depositItems) {
      Parameter deposit = new Parameter();
      deposit.addParameter(AccountPaymentTransaction.PAYEE_ID, item.get("payeeId"));
      deposit.addParameter(AccountPaymentTransaction.AMOUNT, item.get("amount"));
      splitDeposit.addParameter(AccountPaymentTransaction.DEPOSIT,deposit);
    }

    parameters.put(AccountPaymentTransaction.SPLIT_DEPOSIT, splitDeposit);

    GapiTransaction transaction = new AccountPaymentTransaction(parameters);
    apiRequest.addTransaction(transaction);
  }

  /**
   * Function to add Line item elements to ListItems.
   *
   * @param payeeId chargeDays
   * @param amount amount
   * @return a Hashmap of list items
   */
  public AccountPayment addDepositItem(String payeeId, String amount) {

    HashMap<String, String> depositItemValues = new HashMap<>();
    depositItemValues.put("payeeId", payeeId);
    depositItemValues.put("amount", amount);

    depositItems.add(depositItemValues);

    return this;
  }
}
