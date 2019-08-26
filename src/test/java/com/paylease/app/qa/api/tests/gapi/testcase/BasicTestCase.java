package com.paylease.app.qa.api.tests.gapi.testcase;

import com.paylease.app.qa.api.tests.ApiTestCase;
import com.paylease.app.qa.api.tests.ExpectedResponse;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.api.Response;
import javax.xml.xpath.XPathExpressionException;

public abstract class BasicTestCase extends ApiTestCase {

  BasicTestCase(
      String summary, ExpectedResponse expectedResponse
  ) {
    super(summary, expectedResponse);
  }

  /**
   * Perform test - compare code, status and message.
   *
   * @param response AapiResponse object to parse through to find interesting elements
   * @return true if everything matches
   */
  @Override
  public boolean test(Response response) {
    response.setIndex(String.valueOf(getIndex()));

    try {
      String code = response.getSpecificCode();
      String status = response.getSpecificStatus();
      String message = response.getSpecificMessage();

      String expectedCode = expectedResponse.getCode();
      String expectedStatus = expectedResponse.getStatus();
      String expectedMessage = expectedResponse.getMessage();

      boolean resultCode = expectedCode.equals(code);
      boolean resultStatus = expectedStatus.equals(status);
      boolean resultMessage = expectedMessage.equals(message);

      boolean result = resultCode && resultStatus && resultMessage;
      if (!result) {
        Logger.error(summary);
        if (!resultCode) {
          Logger.error("Expected code " + expectedCode + ", found " + code);
        }
        if (!resultStatus) {
          Logger.error("Expected status " + expectedStatus + ", found " + status);
        }
        if (!resultMessage) {
          Logger.error("Expected message " + expectedMessage + ", found " + message);
        }
      }
      return result;
    } catch (XPathExpressionException e) {
      Logger.error("Unable to find item " + index + " in response");
      return false;
    }
  }
}
