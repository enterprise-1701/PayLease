package com.paylease.app.qa.api.tests.gapi.testcase;

import com.paylease.app.qa.api.tests.ExpectedResponse;
import com.paylease.app.qa.framework.api.Request;
import com.paylease.app.qa.framework.api.gapi.GapiTransaction;
import com.paylease.app.qa.framework.api.gapi.IssueCashTempCardTransaction;
import java.util.HashMap;

public class IssueCashTempCard extends BasicTestCase {

  private static final String VALID_FIELD_FIRST_NAME = "firstName";
  private static final String VALID_FIELD_LAST_NAME = "lastName";

  private String payerReferenceId;
  private String payerFirstName;
  private String payerLastName;

  /**
   * Create test case for IssueCashTempCard.
   *
   * @param payerReferenceId key of this test case in the request (no unique key value)
   * @param summary summary of this test case
   * @param expectedResponse response expected to be returned by GAPI
   */
  public IssueCashTempCard(
      String payerReferenceId, String summary, ExpectedResponse expectedResponse
  ) {
    this(payerReferenceId, summary, expectedResponse, null, null);
  }

  private IssueCashTempCard(
      String payerReferenceId, String summary, ExpectedResponse expectedResponse,
      String payerFirstName,
      String payerLastName
  ) {
    super(summary, expectedResponse);

    this.payerReferenceId = payerReferenceId;

    this.payerFirstName = payerFirstName;
    this.payerLastName = payerLastName;
  }

  /**
   * Factory method to create a valid request.
   *
   * @param payerReferenceId Kev value of this test case in the request (PayerReferenceId)
   * @param summary Summary of this test case
   * @param expectedResponse AapiResponse expected to be returned by GAPI
   * @return valid test case
   */
  public static IssueCashTempCard createValid(
      String payerReferenceId, String summary, ExpectedResponse expectedResponse
  ) {
    IssueCashTempCard testCase = new IssueCashTempCard(payerReferenceId, summary, expectedResponse);

    testCase.payerFirstName = testCase.getValidValue(VALID_FIELD_FIRST_NAME);
    testCase.payerLastName = testCase.getValidValue(VALID_FIELD_LAST_NAME);

    return testCase;
  }

  protected void initValidValues() {
    super.initValidValues();
    validValues.put(VALID_FIELD_FIRST_NAME, new String[]{
        "Happy", "JeffersonBarwickBarrickNorthst",
    });
    validValues.put(VALID_FIELD_LAST_NAME, new String[]{
        "Tester", "JeffersonBarwickBarrickNorthst",
    });
  }

  public IssueCashTempCard setPayerFirstName(String payerFirstName) {
    this.payerFirstName = payerFirstName;
    return this;
  }

  public IssueCashTempCard setPayerLastName(String payerLastName) {
    this.payerLastName = payerLastName;
    return this;
  }

  @Override
  public void addTransaction(Request apiRequest) {
    HashMap<String, String> parameters = new HashMap<>();

    parameters.put(IssueCashTempCardTransaction.PAYER_REFERENCE_ID, payerReferenceId);
    parameters.put(IssueCashTempCardTransaction.PAYER_FIRST_NAME, payerFirstName);
    parameters.put(IssueCashTempCardTransaction.PAYER_LAST_NAME, payerLastName);

    GapiTransaction transaction = new IssueCashTempCardTransaction(parameters);
    apiRequest.addTransaction(transaction);
  }
}
