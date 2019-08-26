package com.paylease.app.qa.api.tests.aapi.testcase;

import com.paylease.app.qa.api.tests.ExpectedResponse;
import com.paylease.app.qa.framework.api.ParameterCollection;
import com.paylease.app.qa.framework.api.Request;
import com.paylease.app.qa.framework.api.aapi.AapiTransaction;
import com.paylease.app.qa.framework.api.aapi.GetPropertiesTransaction;

public class GetProperties extends BasicTestCase {

  private String propertyReferenceId;
  private String returnResidents;
  private String returnPaymentFields;

  /**
   * Create a basic GetProperties test case with given values.
   *
   * @param summary Summary of this test case
   * @param expectedResponse response expected to be returned by AAPI
   * @param propertyReferenceId propertyReferenceId
   */
  public GetProperties(String summary, ExpectedResponse expectedResponse,
      String propertyReferenceId) {
    super(summary, expectedResponse);

    this.propertyReferenceId = propertyReferenceId;
  }

  public GetProperties setReturnResidents(String returnResidents) {
    this.returnResidents = returnResidents;
    return this;
  }

  public GetProperties setReturnPaymentFields(String returnPaymentFields) {
    this.returnPaymentFields = returnPaymentFields;
    return this;
  }

  @Override
  public void addTransaction(Request apiRequest) {
    ParameterCollection parameters = getTransactionParameterCollection();

    parameters.put(GetPropertiesTransaction.PROPERTY_REFERENCE_ID, propertyReferenceId);

    AapiTransaction transaction = new GetPropertiesTransaction(parameters);
    if (returnResidents != null) {
      transaction.addActionAttribute(GetPropertiesTransaction.ATTR_RETURN_RESIDENTS, returnResidents);
    }
    if (returnPaymentFields != null) {
      transaction.addActionAttribute(GetPropertiesTransaction.ATTR_RETURN_PAYMENT_FIELDS, returnPaymentFields);
    }
    apiRequest.addTransaction(transaction);
  }
}
