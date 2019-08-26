package com.paylease.app.qa.api.tests.aapi.testcase;

import com.paylease.app.qa.api.tests.ExpectedResponse;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.api.Parameter;
import com.paylease.app.qa.framework.api.ParameterCollection;
import com.paylease.app.qa.framework.api.Request;
import com.paylease.app.qa.framework.api.Response;
import com.paylease.app.qa.framework.api.aapi.AapiTransaction;
import com.paylease.app.qa.framework.api.aapi.UpdateResidentIdTransaction;
import javax.xml.xpath.XPathExpressionException;

public class UpdateResidentId extends BasicTestCase {

  private static final String SUCCESS_CODE = "1";

  private String currentResidentId;
  private String newResidentId;
  private String updateSuccess;

  /**
   * Create a basic UpdateResidentId test case with given values.
   *
   * @param summary Summary of this test case
   * @param expectedResponse response expected to be returned by GAPI
   */
  public UpdateResidentId(String summary, ExpectedResponse expectedResponse) {
    super(summary, expectedResponse);
  }

  public UpdateResidentId setCurrentResidentId(String currentResidentId) {
    this.currentResidentId = currentResidentId;
    return this;
  }

  public UpdateResidentId setNewResidentId(String newResidentId) {
    this.newResidentId = newResidentId;
    return this;
  }

  public UpdateResidentId setUpdateSuccess(String updateSuccess) {
    this.updateSuccess = updateSuccess;
    return this;
  }

  @Override
  public boolean test(Response response) {
    boolean initialResult = super.test(response);

    response.setIndex(String.valueOf(getIndex()));
    response.setSpecificPath(UpdateResidentIdTransaction.SPECIFIC_PATH);

    try {
      boolean result;
      String logMessage = "";

      if (expectedResponse.getCode().equals(SUCCESS_CODE)) {
        String responseResidentId = response
            .getSpecificElementValue(UpdateResidentIdTransaction.RESPONSE_RESIDENT_ID);
        result = responseResidentId.equals(newResidentId);
        if (!result) {
          logMessage =
              "Response ResidentID Node '" + responseResidentId + "' not equal to expected '"
                  + newResidentId + "'";
        } else {
          String resultResponse = response
              .getSpecificElementValue(UpdateResidentIdTransaction.RESPONSE_RESULT);

          result = resultResponse.equals(updateSuccess);
          if (!result) {
            logMessage = "Update Success mismatch: Expected '" + updateSuccess + "', found '"
                + resultResponse + "'";
          }
        }
      } else {
        result = !response.isElementPresent("//" + UpdateResidentIdTransaction.SPECIFIC_PATH + "/"
            + UpdateResidentIdTransaction.RESPONSE_RESIDENT_ID);
        if (!result) {
          logMessage = "ResidentID node should not be present for with response error code '"
              + expectedResponse.getCode() + "'";
        } else {
          result = !response.isElementPresent("//" + UpdateResidentIdTransaction.SPECIFIC_PATH + "/"
              + UpdateResidentIdTransaction.RESPONSE_RESIDENT_ID);
          if (!result) {
            logMessage =
                "UpdatedResidentID node should not be present for with response error code '"
                    + expectedResponse.getCode() + "'";
          }
        }
      }

      if (!result) {
        if (initialResult) {
          Logger.error(summary);
        }
        Logger.error(logMessage);
      }
      return result && initialResult;

    } catch (XPathExpressionException e) {
      Logger.error("Unable to find item " + index + " in response");
      return false;
    }
  }

  @Override
  public void addTransaction(Request apiRequest) {
    ParameterCollection parameters = getTransactionParameterCollection();

    Parameter residents = new Parameter();

    Parameter resident = new Parameter();
    resident.addParameter(UpdateResidentIdTransaction.CURRENT_RESIDENT_ID, currentResidentId);
    resident.addParameter(UpdateResidentIdTransaction.NEW_RESIDENT_ID, newResidentId);

    residents.addParameter(UpdateResidentIdTransaction.RESIDENT, resident);

    parameters.put(UpdateResidentIdTransaction.RESIDENTS, residents);

    AapiTransaction transaction = new UpdateResidentIdTransaction(parameters);
    apiRequest.addTransaction(transaction);
  }
}
