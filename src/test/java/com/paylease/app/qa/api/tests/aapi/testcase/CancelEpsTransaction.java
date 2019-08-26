package com.paylease.app.qa.api.tests.aapi.testcase;

import com.paylease.app.qa.api.tests.ExpectedResponse;
import com.paylease.app.qa.framework.api.Request;
import com.paylease.app.qa.framework.api.aapi.AapiTransaction;
import com.paylease.app.qa.framework.api.aapi.CancelEpsTranTransaction;
import java.util.HashMap;

public class CancelEpsTransaction extends BasicTestCase {

  private String vendorId;
  private String extAccId;
  private String extTransactionId;

  /**
   * Create a basic CancelEpsTransaction test case with given values.
   *
   * @param summary Summary of this test case
   * @param expectedResponse response expected to be returned by AAPI
   * @param vendorId vendorId
   * @param extAccId extAccId
   * @param extTransactionId extTransactionId
   */
  public CancelEpsTransaction(String summary, ExpectedResponse expectedResponse,
      String vendorId, String extAccId, String extTransactionId) {
    super(summary, expectedResponse);

    this.vendorId = vendorId;
    this.extAccId = extAccId;
    this.extTransactionId = extTransactionId;

  }

  @Override
  public void addTransaction(Request apiRequest) {
    HashMap<String, String> parameters = getTransactionParameters();

    parameters.put(CancelEpsTranTransaction.VENDOR_ID, vendorId);
    parameters.put(CancelEpsTranTransaction.EXT_ACC_ID, extAccId);
    parameters.put(CancelEpsTranTransaction.EXT_TRANSACTION_ID, extTransactionId);

    AapiTransaction transaction = new CancelEpsTranTransaction(parameters);
    apiRequest.addTransaction(transaction);
  }
}
