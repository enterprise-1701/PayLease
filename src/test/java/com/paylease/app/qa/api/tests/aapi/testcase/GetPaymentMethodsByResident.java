package com.paylease.app.qa.api.tests.aapi.testcase;

import com.paylease.app.qa.api.tests.ExpectedResponse;
import com.paylease.app.qa.framework.api.Request;
import com.paylease.app.qa.framework.api.aapi.AapiTransaction;
import com.paylease.app.qa.framework.api.aapi.GetPaymentMethodsByResidentTransaction;
import java.util.HashMap;

public class GetPaymentMethodsByResident extends BasicTestCase {

  private String residentId;
  private String createdSince;

  /**
   * Create a basic GetInvoices test case with given values, and nulls for other fields. By
   * itself, this will not create a valid request.
   *
   * @param summary Summary of this test case
   * @param expectedResponse response expected to be returned by AAPI
   */
  public GetPaymentMethodsByResident(
      String summary, ExpectedResponse expectedResponse
  ) {
    this(summary, expectedResponse, null, null);
  }

  private GetPaymentMethodsByResident(String summary, ExpectedResponse expectedResponse,
      String residentId, String createdSince) {
    super(summary, expectedResponse);

    this.residentId = residentId;
    this.createdSince = createdSince;
  }

  /**
   * ResidentId setter.
   *
   * @param residentId residentId
   * @return residentId instance
   */
  public GetPaymentMethodsByResident setResidentId(String residentId) {
    this.residentId = residentId;

    return this;
  }

  /**
   * CreatedSince setter.
   *
   * @param createdSince createdSince
   * @return createdSince instance
   */
  public GetPaymentMethodsByResident setCreatedSince(String createdSince) {
    this.createdSince = createdSince;

    return this;
  }

  @Override
  public void addTransaction(Request apiRequest) {
    HashMap<String, String> parameters = getTransactionParameters();

    parameters.put(GetPaymentMethodsByResidentTransaction.RESIDENT_ID, residentId);
    parameters.put(GetPaymentMethodsByResidentTransaction.CREATED_SINCE, createdSince);

    AapiTransaction transaction = new GetPaymentMethodsByResidentTransaction(parameters);
    apiRequest.addTransaction(transaction);
  }
}
