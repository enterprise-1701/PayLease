package com.paylease.app.qa.api.tests.aapi.testcase;

import com.paylease.app.qa.api.tests.ExpectedResponse;
import com.paylease.app.qa.framework.api.Request;
import com.paylease.app.qa.framework.api.aapi.AapiTransaction;
import com.paylease.app.qa.framework.api.aapi.GetPaymentMethodsByPropertyTransaction;
import java.util.HashMap;

public class GetPaymentMethodsByProperty extends BasicTestCase {

  private String propertyReferenceId;
  private String activePaymentMethodResidents;
  private String createdSince;

  /**
   * Create a basic GetPaymentMethodsByProperty test case with given values, and nulls for other
   * fields. By itself, this will not create a valid request.
   *
   * @param summary Summary of this test case
   * @param expectedResponse response expected to be returned by AAPI
   */
  public GetPaymentMethodsByProperty(
      String summary, ExpectedResponse expectedResponse
  ) {
    this(summary, expectedResponse, null, null, null);
  }

  private GetPaymentMethodsByProperty(String summary, ExpectedResponse expectedResponse,
      String propertyReferenceId, String activePaymentMethodResidents, String createdSince) {
    super(summary, expectedResponse);

    this.propertyReferenceId = propertyReferenceId;
    this.activePaymentMethodResidents = activePaymentMethodResidents;
    this.createdSince = createdSince;
  }

  /**
   * PropertyReferenceId setter.
   *
   * @param propertyReferenceId propertyReferenceId
   * @return propertyReferenceId instance
   */
  public GetPaymentMethodsByProperty setPropertyReferenceId(String propertyReferenceId) {
    this.propertyReferenceId = propertyReferenceId;

    return this;
  }

  /**
   * ActivePaymentMethodResidents setter.
   *
   * @param activePaymentMethodResidents activePaymentMethodResidents
   * @return activePaymentMethodResidents instance
   */
  public GetPaymentMethodsByProperty setActivePaymentMethodResidents(
      String activePaymentMethodResidents) {
    this.activePaymentMethodResidents = activePaymentMethodResidents;

    return this;
  }

  /**
   * CreatedSince setter.
   *
   * @param createdSince createdSince
   * @return createdSince instance
   */
  public GetPaymentMethodsByProperty setCreatedSince(String createdSince) {
    this.createdSince = createdSince;

    return this;
  }

  @Override
  public void addTransaction(Request apiRequest) {
    HashMap<String, String> parameters = getTransactionParameters();

    parameters.put(GetPaymentMethodsByPropertyTransaction.PROPERTY_REFERENCE_ID,
        propertyReferenceId);
    parameters.put(GetPaymentMethodsByPropertyTransaction.ACTIVE_PAYMENT_METHOD_RESIDENTS,
        activePaymentMethodResidents);
    parameters.put(GetPaymentMethodsByPropertyTransaction.CREATED_SINCE, createdSince);

    AapiTransaction transaction = new GetPaymentMethodsByPropertyTransaction(parameters);
    apiRequest.addTransaction(transaction);
  }
}
