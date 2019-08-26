package com.paylease.app.qa.api.tests.aapi.testcase;

import com.paylease.app.qa.api.tests.ExpectedResponse;
import com.paylease.app.qa.framework.api.Request;
import com.paylease.app.qa.framework.api.aapi.AapiTransaction;
import com.paylease.app.qa.framework.api.aapi.ProcessEpsPaymentTransaction;
import java.util.HashMap;

public class ProcessEpsPayment extends BasicTestCase {

  private String vendorId;
  private String extAccId;
  private String extTransactionId;
  private String unitAmount;

  /**
   * Create a valid ProcessEpsPayment test case with random valid values for required fields.
   *
   * @param summary Summary of this test case
   * @param expectedResponse response expected to be returned by AAPI
   * @param vendorId vendorId
   * @param extAccId extAccId
   * @param extTransactionId extTransactionId
   * @param unitAmount unitAmount
   */
  public ProcessEpsPayment(String summary, ExpectedResponse expectedResponse,
      String vendorId, String extAccId, String extTransactionId, String unitAmount) {
    super(summary, expectedResponse);

    this.vendorId = vendorId;
    this.extAccId = extAccId;
    this.extTransactionId = extTransactionId;
    this.unitAmount = unitAmount;

  }

  @Override
  public void addTransaction(Request apiRequest) {
    HashMap<String, String> parameters = getTransactionParameters();

    parameters.put(ProcessEpsPaymentTransaction.VENDOR_ID, vendorId);
    parameters.put(ProcessEpsPaymentTransaction.EXT_ACC_ID, extAccId);
    parameters.put(ProcessEpsPaymentTransaction.EXT_TRANSACTION_ID, extTransactionId);
    parameters.put(ProcessEpsPaymentTransaction.UNIT_AMOUNT, unitAmount);

    AapiTransaction transaction = new ProcessEpsPaymentTransaction(parameters);
    apiRequest.addTransaction(transaction);
  }
}
