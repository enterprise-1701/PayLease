package com.paylease.app.qa.api.tests.gapi.testcase;

import com.paylease.app.qa.api.tests.ExpectedResponse;
import com.paylease.app.qa.framework.api.Request;
import com.paylease.app.qa.framework.api.gapi.CreditReportingOptinTransaction;
import com.paylease.app.qa.framework.api.gapi.CreditReportingOptoutTransaction;
import com.paylease.app.qa.framework.api.gapi.GapiTransaction;
import java.util.HashMap;

public class CreditReportingOptout extends BasicTestCase {

  private String creditReportingId;

  public CreditReportingOptout(String summary, ExpectedResponse expectedResponse) {
    this(summary, expectedResponse, null);
  }

  private CreditReportingOptout(String summary,
      ExpectedResponse expectedResponse, String creditReportingId) {
    super(summary, expectedResponse);

    this.creditReportingId = creditReportingId;
  }

  public CreditReportingOptout setCreditReportingId(String creditReportingId) {
    this.creditReportingId = creditReportingId;
    return this;
  }

  @Override
  public void addTransaction(Request apiRequest) {
    HashMap<String, String> parameters = new HashMap<>();
    parameters.put(CreditReportingOptoutTransaction.CREDIT_REPORTING_ID, creditReportingId);

    GapiTransaction transaction = new CreditReportingOptinTransaction(parameters);
    apiRequest.addTransaction(transaction);
  }
}
