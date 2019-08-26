package com.paylease.app.qa.api.tests.gapi.testcase;

import com.paylease.app.qa.api.tests.ExpectedResponse;
import com.paylease.app.qa.framework.DataHelper;
import com.paylease.app.qa.framework.api.Request;
import com.paylease.app.qa.framework.api.gapi.AccountPayDirectTransaction;
import com.paylease.app.qa.framework.api.gapi.GapiTransaction;
import java.util.HashMap;

public class AccountPayDirect extends BasicTestCase {

  private static final String VALID_FIELD_TOTAL_AMOUNT = "totalAmount";

  private String paymentReferenceId;
  private String payeeEmailAddress;
  private String totalAmount;
  private String payeeState;
  private String payerId;
  private String gatewayPayeeId;
  private String payeeReferenceId;

  /**
   * Create a basic AccountPayDirect test case with given values, and nulls for other fields. By
   * itself, this will not create a valid request
   *
   * @param paymentReferenceId Key value for this test case (PaymentReferenceId)
   * @param summary Summary of this test case
   * @param expectedResponse response expected to be returned by GAPI
   * @param payerId Valid PayerId given by test setup
   * @param payeeReferenceId Valid PayeeReferenceId given by test setup
   * @param gatewayPayeeId Valid GatewayPayeeId given by test setup
   */
  public AccountPayDirect(
      String paymentReferenceId, String summary, ExpectedResponse expectedResponse, String payerId,
      String payeeReferenceId, String gatewayPayeeId
  ) {
    this(paymentReferenceId, summary, expectedResponse, null, null, null,
        payerId, payeeReferenceId, gatewayPayeeId);
  }

  private AccountPayDirect(
      String paymentReferenceId, String summary, ExpectedResponse expectedResponse,
      String payeeEmailAddress,
      String totalAmount, String payeeState, String payerId, String payeeReferenceId,
      String gatewayPayeeId
  ) {
    super(summary, expectedResponse);

    this.paymentReferenceId = paymentReferenceId;
    this.payeeEmailAddress = payeeEmailAddress;
    this.totalAmount = totalAmount;
    this.payeeState = payeeState;
    this.payerId = payerId;
    this.payeeReferenceId = payeeReferenceId;
    this.gatewayPayeeId = gatewayPayeeId;
  }

  /**
   * Create a valid test case with random valid values for required fields.
   *
   * @param paymentReferenceId Key value for this test case (PaymentReferenceId)
   * @param summary Summary of this test case
   * @param expectedResponse response expected to be returned by GAPI
   * @param payerId Valid PayerId given by test setup
   * @param payeeReferenceId Valid PayeeReferenceId given by test setup
   * @param gatewayPayeeId Valid GatewayPayeeId given by test setup
   * @return AccountPayDirect test case that can be submitted as valid
   */
  public static AccountPayDirect createValid(
      String paymentReferenceId, String summary, ExpectedResponse expectedResponse, String payerId,
      String payeeReferenceId, String gatewayPayeeId
  ) {
    AccountPayDirect validTestCase = new AccountPayDirect(
        paymentReferenceId, summary, expectedResponse, payerId, payeeReferenceId, gatewayPayeeId);

    validTestCase.totalAmount = validTestCase.getValidValue(VALID_FIELD_TOTAL_AMOUNT);

    return validTestCase;
  }

  protected void initValidValues() {
    super.initValidValues();
    validValues.put(VALID_FIELD_TOTAL_AMOUNT, new String[]{
        "5", "1500",
    });
  }

  public AccountPayDirect setPayeeEmailAddress(String payeeEmailAddress) {
    this.payeeEmailAddress = payeeEmailAddress;
    return this;
  }

  public AccountPayDirect setTotalAmount(String totalAmount) {
    this.totalAmount = totalAmount;
    return this;
  }

  public AccountPayDirect setPayeeState(String payeeState) {
    this.payeeState = payeeState;
    return this;
  }

  @Override
  public void addTransaction(Request apiRequest) {
    DataHelper dataHelper = new DataHelper();
    String paymentTraceId = dataHelper.getPaymentTraceId();
    HashMap<String, String> parameters = new HashMap<>();
    parameters.put(AccountPayDirectTransaction.PAYMENT_REFERENCE_ID, paymentReferenceId);
    parameters.put(AccountPayDirectTransaction.PAYMENT_TRACE_ID, paymentTraceId);
    parameters.put(AccountPayDirectTransaction.PAYER_ID, payerId);
    parameters.put(AccountPayDirectTransaction.PAYEE_REFERENCE_ID, payeeReferenceId);
    parameters.put(AccountPayDirectTransaction.PAYEE_EMAIL_ADDRESS, payeeEmailAddress);
    parameters.put(AccountPayDirectTransaction.GATEWAY_PAYEE_ID, gatewayPayeeId);
    parameters.put(AccountPayDirectTransaction.TOTAL_AMOUNT, totalAmount);
    parameters.put(AccountPayDirectTransaction.PAYEE_STATE, payeeState);

    GapiTransaction transaction = new AccountPayDirectTransaction(parameters);
    apiRequest.addTransaction(transaction);
  }
}
