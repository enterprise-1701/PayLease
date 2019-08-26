package com.paylease.app.qa.api.tests.aapi;

import com.paylease.app.qa.api.tests.ApiTestCase;
import com.paylease.app.qa.api.tests.TestCaseCollection;
import com.paylease.app.qa.api.tests.aapi.testcase.BasicTestCase;
import com.paylease.app.qa.framework.api.Credentials;
import com.paylease.app.qa.framework.api.Request;
import com.paylease.app.qa.framework.api.aapi.AapiRequest;

public class AapiTestCaseCollection extends TestCaseCollection {

  public AapiTestCaseCollection(Credentials credentials, String mode, String endpoint) {
    super(credentials, mode, endpoint, TestCaseCollection.REQUEST_TYPE_AAPI);
  }

  @Override
  protected void addTestCase(ApiTestCase testCase) {
    if (isPartner()) {
      BasicTestCase aapiTestCase = (BasicTestCase) testCase;
      aapiTestCase.setPartnerPmId(credentials.getPmId());
    }

    super.addTestCase(testCase);
  }

  @Override
  public Request getRequest() throws Exception {
    return new AapiRequest(credentials, mode, endpoint);
  }

  private boolean isPartner() {
    switch (endpoint) {
      case AapiRequest.ONSITE:
      case AapiRequest.TOPS:
      case AapiRequest.RESMAN:
        return true;
      default:
        return false;
    }
  }
}
