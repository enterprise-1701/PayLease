package com.paylease.app.qa.api.tests.aapi.testcase;

import com.paylease.app.qa.api.tests.ExpectedResponse;
import com.paylease.app.qa.framework.api.Request;
import com.paylease.app.qa.framework.api.aapi.AapiTransaction;
import com.paylease.app.qa.framework.api.aapi.ValidateEpsAccountTransaction;
import java.util.HashMap;

public class ValidateEpsAccount extends BasicTestCase {

  private String vendorId;
  private String extAccId;

  /**
   * Create a basic ValidateEpsAccount test case with given values.
   *
   * @param summary Summary of this test case
   * @param expectedResponse response expected to be returned by GAPI
   * @param vendorId vendorId
   * @param extAccId extAccId
   */
  public ValidateEpsAccount(String summary, ExpectedResponse expectedResponse,
      String vendorId, String extAccId) {
    super(summary, expectedResponse);

    this.vendorId = vendorId;
    this.extAccId = extAccId;

  }

  @Override
  public void addTransaction(Request apiRequest) {
    HashMap<String, String> parameters = getTransactionParameters();

    parameters.put(ValidateEpsAccountTransaction.VENDOR_ID, vendorId);
    parameters.put(ValidateEpsAccountTransaction.EXT_ACC_ID, extAccId);

    AapiTransaction transaction = new ValidateEpsAccountTransaction(parameters);
    apiRequest.addTransaction(transaction);
  }
}
