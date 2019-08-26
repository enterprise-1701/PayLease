package com.paylease.app.qa.api.tests;

import com.paylease.app.qa.framework.api.ParameterCollection;
import com.paylease.app.qa.framework.api.Request;
import com.paylease.app.qa.framework.api.Response;
import java.util.HashMap;
import java.util.Random;

public class ApiTestCase {

  protected String summary;

  protected ExpectedResponse expectedResponse;

  protected int index;

  protected HashMap<String, String[]> validValues;

  protected ApiTestCase(
      String summary, ExpectedResponse expectedResponse
  ) {
    this.summary = summary;
    this.expectedResponse = expectedResponse;

    initValidValues();
  }

  protected void initValidValues() {
    validValues = new HashMap<>();
  }

  void setIndex(int index) {
    this.index = index;
  }

  protected int getIndex() {
    return index;
  }

  protected String getValidValue(String field) {
    String[] choices = validValues.get(field);
    int rnd = new Random().nextInt(choices.length);
    return choices[rnd];
  }

  /**
   * Perform test - fails by default - should be overridden by child classes.
   *
   * @param response AapiResponse object to parse through to find interesting elements
   * @return false in all cases
   */
  public boolean test(Response response) {
    return false;
  }

  public void addTransaction(Request apiRequest) {
    // this should be implemented by child classes
  }

  protected ParameterCollection getTransactionParameterCollection() {
    return new ParameterCollection();
  }
}
