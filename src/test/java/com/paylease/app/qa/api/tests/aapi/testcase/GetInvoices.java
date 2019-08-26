package com.paylease.app.qa.api.tests.aapi.testcase;

import com.paylease.app.qa.api.tests.ExpectedResponse;
import com.paylease.app.qa.framework.api.Request;
import com.paylease.app.qa.framework.api.aapi.AapiTransaction;
import com.paylease.app.qa.framework.api.aapi.GetInvoicesTransaction;
import java.util.HashMap;

public class GetInvoices extends BasicTestCase {

  private String startDate;
  private String endDate;

  /**
   * Create a basic GetInvoices test case with given values, and nulls for other fields. By
   * itself, this will not create a valid request.
   *
   * @param summary Summary of this test case
   * @param expectedResponse response expected to be returned by AAPI
   */
  public GetInvoices(
      String summary, ExpectedResponse expectedResponse
  ) {
    this(summary, expectedResponse, null, null);
  }

  private GetInvoices(String summary, ExpectedResponse expectedResponse,
      String startDate, String endDate) {
    super(summary, expectedResponse);

    this.startDate = startDate;
    this.endDate = endDate;
  }

  /**
   * StartDate setter.
   *
   * @param startDate startDate
   * @return startDate instance
   */
  public GetInvoices setStartDate(String startDate) {
    this.startDate = startDate;

    return this;
  }

  /**
   * EndDate setter.
   *
   * @param endDate endDate
   * @return endDate instance
   */
  public GetInvoices setEndDate(String endDate) {
    this.endDate = endDate;

    return this;
  }

  @Override
  public void addTransaction(Request apiRequest) {
    HashMap<String, String> parameters = getTransactionParameters();

    parameters.put(GetInvoicesTransaction.START_DATE, startDate);
    parameters.put(GetInvoicesTransaction.END_DATE, endDate);

    AapiTransaction transaction = new GetInvoicesTransaction(parameters);
    apiRequest.addTransaction(transaction);
  }
}
