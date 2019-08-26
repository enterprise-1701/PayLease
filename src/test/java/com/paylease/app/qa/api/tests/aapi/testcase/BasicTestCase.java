package com.paylease.app.qa.api.tests.aapi.testcase;

import com.paylease.app.qa.api.tests.ApiTestCase;
import com.paylease.app.qa.api.tests.ExpectedResponse;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.api.ParameterCollection;
import com.paylease.app.qa.framework.api.Response;
import com.paylease.app.qa.framework.api.aapi.AapiTransaction;
import java.util.HashMap;
import javax.xml.xpath.XPathExpressionException;

public abstract class BasicTestCase extends ApiTestCase {

  private String partnerPmId;

  protected BasicTestCase(
      String summary, ExpectedResponse expectedResponse
  ) {
    super(summary, expectedResponse);

    initValidValues();
  }

  public void setPartnerPmId(String pmId) {
    this.partnerPmId = pmId;
  }

  protected HashMap<String, String> getTransactionParameters() {
    HashMap<String, String> parameters = new HashMap<>();
    if (partnerPmId != null) {
      parameters.put(AapiTransaction.PM_ID, partnerPmId);
    }
    return parameters;
  }

  protected ParameterCollection getTransactionParameterCollection() {
    ParameterCollection parameters = super.getTransactionParameterCollection();
    if (partnerPmId != null) {
      parameters.put(AapiTransaction.PM_ID, partnerPmId);
    }
    return parameters;
  }

  /**
   * Perform test - compare code, status and message.
   *
   * @param response AapiResponse object to parse through to find interesting elements
   * @return true if everything matches
   */
  public boolean test(Response response) {
    try {
      String code = response.getResponseCode();
      String message = response.getResponseMessage();

      String expectedCode = expectedResponse.getCode();
      String expectedMessage = expectedResponse.getMessage();

      boolean resultCode = expectedCode.equals(code);
      boolean resultMessage = expectedMessage.equals(message);

      boolean result = resultCode && resultMessage;
      if (!result) {
        Logger.error(summary);
        if (!resultCode) {
          Logger.error("Expected code " + expectedCode + ", found " + code);
        }
        if (!resultMessage) {
          Logger.error("Expected message " + expectedMessage + ", found " + message);
        }
      }
      return result;
    } catch (XPathExpressionException e) {
      Logger.error("Invalid response: " + e);
      return false;
    }
  }
}

