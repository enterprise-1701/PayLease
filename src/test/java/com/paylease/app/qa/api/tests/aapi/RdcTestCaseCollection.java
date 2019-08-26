package com.paylease.app.qa.api.tests.aapi;

import com.paylease.app.qa.framework.api.Credentials;
import com.paylease.app.qa.framework.api.Request;
import com.paylease.app.qa.framework.api.aapi.RdcRequest;

public class RdcTestCaseCollection extends AapiTestCaseCollection {

  RdcTestCaseCollection(Credentials credentials, String mode, String endpoint) {
    super(credentials, mode, endpoint);
  }

  @Override
  public Request getRequest() throws Exception {
    return new RdcRequest(credentials, mode, endpoint);
  }
}
