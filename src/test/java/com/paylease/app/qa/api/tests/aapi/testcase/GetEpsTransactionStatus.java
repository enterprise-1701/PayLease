package com.paylease.app.qa.api.tests.aapi.testcase;

import com.paylease.app.qa.api.tests.ExpectedResponse;
import com.paylease.app.qa.framework.api.Request;
import com.paylease.app.qa.framework.api.aapi.AapiTransaction;
import com.paylease.app.qa.framework.api.aapi.GetEpsTranStatusTransaction;
import java.util.HashMap;

public class GetEpsTransactionStatus extends BasicTestCase {

  private String vendorId;
  private String extAccId;
  private String extTransactionId;

  /**
   * Create a basic GetEpsTransactionStatus test case with given values, and nulls for other
   * fields. By itself, this will not create a valid request.
   *
   * @param summary Summary of this test case
   * @param expectedResponse response expected to be returned by AAPI
   */
  public GetEpsTransactionStatus(String summary, ExpectedResponse expectedResponse,
      String vendorId, String extAccId, String extTransactionId) {
    super(summary, expectedResponse);

    this.vendorId = vendorId;
    this.extAccId = extAccId;
    this.extTransactionId = extTransactionId;

  }

  @Override
  public void addTransaction(Request apiRequest) {
    HashMap<String, String> parameters = getTransactionParameters();

    parameters.put(GetEpsTranStatusTransaction.VENDOR_ID, vendorId);
    parameters.put(GetEpsTranStatusTransaction.EXT_ACC_ID, extAccId);
    parameters.put(GetEpsTranStatusTransaction.EXT_TRANSACTION_ID, extTransactionId);

    AapiTransaction transaction = new GetEpsTranStatusTransaction(parameters);
    apiRequest.addTransaction(transaction);
  }
}
