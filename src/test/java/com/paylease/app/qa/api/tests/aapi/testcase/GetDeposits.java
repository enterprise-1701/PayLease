package com.paylease.app.qa.api.tests.aapi.testcase;

import com.paylease.app.qa.api.tests.ExpectedResponse;
import com.paylease.app.qa.framework.api.Request;
import com.paylease.app.qa.framework.api.aapi.AapiTransaction;
import com.paylease.app.qa.framework.api.aapi.GetDepositsTransaction;
import java.util.HashMap;

public class GetDeposits extends BasicTestCase {

  private String propertyReferenceId;
  private String startDate;
  private String endDate;

  /**
   * Create a basic GetDeposits test case with given values, and nulls for other fields. By
   * itself, this will not create a valid request.
   *
   * @param summary Summary of this test case
   * @param expectedResponse response expected to be returned by AAPI
   */
  public GetDeposits(
      String summary, ExpectedResponse expectedResponse
  ) {
    this(summary, expectedResponse, null, null, null);
  }

  private GetDeposits(String summary, ExpectedResponse expectedResponse,
      String propertyReferenceId, String startDate, String endDate) {
    super(summary, expectedResponse);

    this.propertyReferenceId = propertyReferenceId;
    this.startDate = startDate;
    this.endDate = endDate;
  }

  /**
   * PropertyReferenceId setter.
   *
   * @param propertyReferenceId propertyReferenceId
   * @return propertyReferenceId instance
   */
  public GetDeposits setPropertyReferenceId(String propertyReferenceId) {
    this.propertyReferenceId = propertyReferenceId;

    return this;
  }

  /**
   * StartDate setter.
   *
   * @param startDate startDate
   * @return startDate instance
   */
  public GetDeposits setStartDate(String startDate) {
    this.startDate = startDate;

    return this;
  }

  /**
   * EndDate setter.
   *
   * @param endDate endDate
   * @return endDate instance
   */
  public GetDeposits setEndDate(String endDate) {
    this.endDate = endDate;

    return this;
  }

  @Override
  public void addTransaction(Request apiRequest) {
    HashMap<String, String> parameters = getTransactionParameters();

    parameters.put(GetDepositsTransaction.PROPERTY_REFERENCE_ID, propertyReferenceId);
    parameters.put(GetDepositsTransaction.START_DATE, startDate);
    parameters.put(GetDepositsTransaction.END_DATE, endDate);

    AapiTransaction transaction = new GetDepositsTransaction(parameters);
    apiRequest.addTransaction(transaction);
  }
}
