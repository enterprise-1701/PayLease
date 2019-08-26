package com.paylease.app.qa.api.tests;

import java.util.HashMap;
import java.util.List;

public class ExpectedResponse {
  private String code;
  private String status;
  private String message;

  private ExpectedResponse(String code, String status, String message) {
    this.code = code;
    this.status = status;
    this.message = message;
  }

  /**
   * Create an ExpectedResponse object from the table of API messages given in test setup.
   *
   * @param gatewayErrors Table of API message from test setup
   * @param errorId Code to find
   * @return ExpectedResponse
   */
  static ExpectedResponse createFromSetupTable(
      List<HashMap<String, Object>> gatewayErrors, String errorId
  ) {
    String code = null;
    String status = null;
    String message = null;

    for (HashMap<String, Object> item : gatewayErrors) {
      if (item.get("code").equals(errorId)) {
        code = (String) item.get("code");
        status = (String) item.get("status");
        message = (String) item.get("message");
        break;
      }
    }
    return new ExpectedResponse(code, status, message);
  }

  public String getCode() {
    return code;
  }

  public String getStatus() {
    return status;
  }

  public String getMessage() {
    return message;
  }

  public ExpectedResponse appendMessage(String extraMessage) {
    this.message += extraMessage;
    return this;
  }
}
