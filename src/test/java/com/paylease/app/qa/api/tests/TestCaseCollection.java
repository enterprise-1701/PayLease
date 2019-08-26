package com.paylease.app.qa.api.tests;

import com.paylease.app.qa.framework.api.Credentials;
import com.paylease.app.qa.framework.api.Request;
import com.paylease.app.qa.framework.api.aapi.AapiRequest;
import java.util.ArrayList;

public class TestCaseCollection {

  public static final String REQUEST_TYPE_GAPI = "gapi";
  public static final String REQUEST_TYPE_AAPI = "aapi";

  protected ArrayList<ApiTestCase> testCases;
  protected Credentials credentials;
  protected String mode;
  protected String requestType;
  protected String endpoint;

  /**
   * Get a collection of test cases.
   *
   * @param credentials credentials
   * @param mode mode
   * @param endpoint endpoint
   * @param requestType requestType
   */
  public TestCaseCollection(Credentials credentials, String mode, String endpoint,
      String requestType) {
    this.credentials = credentials;
    this.mode = mode;
    this.requestType = requestType;
    setEndpoint(endpoint);
    testCases = new ArrayList<>();
  }

  public TestCaseCollection(Credentials credentials, String mode, String requestType) {
    this(credentials, mode, null, requestType);
  }

  public TestCaseCollection(Credentials credentials, String requestType) {
    this(credentials, "Test", requestType);
  }

  public TestCaseCollection(TestCaseCollection original) {
    this(original.credentials, original.mode, original.endpoint, original.requestType);
  }

  public ArrayList<ApiTestCase> getTestCases() {
    return testCases;
  }

  public TestCaseCollection add(ApiTestCase testCase) {
    addTestCase(testCase);
    return this;
  }

  protected void addTestCase(ApiTestCase testCase) {
    testCases.add(testCase);
  }

  public Credentials getCredentials() {
    return credentials;
  }

  public String getMode() {
    return mode;
  }

  private void setEndpoint(String endpoint) {
    this.endpoint = endpoint;
    if (credentials.getApiKey() == null && endpoint != null) {
      credentials.setApiKey(AapiRequest.getApiKey(endpoint));
    }
  }

  public Request getRequest() throws Exception {
    throw new Exception();
  }
}
