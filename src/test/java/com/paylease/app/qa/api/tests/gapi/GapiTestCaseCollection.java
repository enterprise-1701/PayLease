package com.paylease.app.qa.api.tests.gapi;

import com.paylease.app.qa.api.tests.TestCaseCollection;
import com.paylease.app.qa.framework.api.Credentials;
import com.paylease.app.qa.framework.api.Request;
import com.paylease.app.qa.framework.api.gapi.GapiRequest;

public class GapiTestCaseCollection extends TestCaseCollection {

  public GapiTestCaseCollection(Credentials credentials) {
    super(credentials, TestCaseCollection.REQUEST_TYPE_GAPI);
  }

  @Override
  public Request getRequest() throws Exception {
    return new GapiRequest(credentials, mode);
  }
}
