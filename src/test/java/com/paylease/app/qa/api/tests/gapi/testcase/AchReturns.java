package com.paylease.app.qa.api.tests.gapi.testcase;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.api.Request;
import com.paylease.app.qa.framework.api.Response;
import com.paylease.app.qa.framework.api.gapi.AchReturnsTransaction;
import com.paylease.app.qa.framework.api.gapi.GapiTransaction;
import com.paylease.app.qa.framework.api.gapi.response.AchReturnsResponse;
import java.util.ArrayList;
import java.util.HashMap;

public class AchReturns extends BasicTestCase {

  private String searchStartDate;
  private String searchEndDate;
  private String timeFrame;

  ArrayList<ReturnTransaction> expectedReturns;

  public AchReturns(String summary) {
    super(summary, null);
  }

  public AchReturns setSearchStartDate(String searchStartDate) {
    this.searchStartDate = searchStartDate;
    return this;
  }

  public AchReturns setSearchEndDate(String searchEndDate) {
    this.searchEndDate = searchEndDate;
    return this;
  }

  public AchReturns setTimeFrame(String timeFrame) {
    this.timeFrame = timeFrame;
    return this;
  }

  public AchReturns setReturnTransactions(ArrayList<ReturnTransaction> expectedReturns) {
    this.expectedReturns = expectedReturns;
    return this;
  }

  @Override
  public void addTransaction(Request apiRequest) {
    HashMap<String, String> parameters = new HashMap<>();
    parameters.put(AchReturnsTransaction.SEARCH_START_DATE, searchStartDate);
    parameters.put(AchReturnsTransaction.SEARCH_END_DATE, searchEndDate);
    parameters.put(AchReturnsTransaction.TIME_FRAME, timeFrame);

    GapiTransaction transaction = new AchReturnsTransaction(parameters);
    apiRequest.addTransaction(transaction);
  }

  @Override
  public boolean test(Response response) {
    response.setIndex(String.valueOf(getIndex()));
    boolean result;
    try {
      AchReturnsResponse specificResponse = new AchReturnsResponse(
          response.getSpecificResponseSnippet());

      boolean returnsDetailsResult = true;
      if (!expectedReturns.isEmpty()) {
        ArrayList<HashMap<String, Object>> returnsActual = specificResponse.getReturnTransactions();
        Logger.debug(returnsActual.toString());

        for (ReturnTransaction expectedReturn : expectedReturns) {
          boolean specificReturnDetailsResult = isReturnPresent(expectedReturn, returnsActual);
          if (!specificReturnDetailsResult) {
            Logger.error("Could not find return " + expectedReturn.toString() + " in response");
          }
          returnsDetailsResult = returnsDetailsResult && specificReturnDetailsResult;
        }
      }

      result = returnsDetailsResult;
    } catch (Exception e) {
      Logger.debug(e.getMessage());
      result = false;
    }

    return result;
  }

  private boolean isReturnPresent(ReturnTransaction expectedReturn,
      ArrayList<HashMap<String, Object>> returnsActual) {
    boolean returnFound = false;
    for (HashMap<String, Object> returnActual : returnsActual) {
      if (null != expectedReturn.getTransactionId() && !expectedReturn.getTransactionId()
          .equals(returnActual.get(AchReturnsResponse.RETURN_TRANSACTION_ID))) {
        continue;
      }
      if (null != expectedReturn.getTransactionDate() && !expectedReturn.getTransactionDate()
          .equals(returnActual.get(AchReturnsResponse.RETURN_TRANSACTION_DATE))) {
        continue;
      }
      if (null != expectedReturn.getTransactionTime() && !expectedReturn.getTransactionTime()
          .equals(returnActual.get(AchReturnsResponse.RETURN_TRANSACTION_TIME))) {
        continue;
      }
      if (null != expectedReturn.getReturnStatus() && !expectedReturn.getReturnStatus()
          .equals(returnActual.get(AchReturnsResponse.RETURN_RETURN_STATUS))) {
        continue;
      }
      if (null != expectedReturn.getReturnCode() && !expectedReturn.getReturnCode()
          .equals(returnActual.get(AchReturnsResponse.RETURN_RETURN_CODE))) {
        continue;
      }
      if (null != expectedReturn.getReturnDate() && !expectedReturn.getReturnDate()
          .equals(returnActual.get(AchReturnsResponse.RETURN_RETURN_DATE))) {
        continue;
      }
      if (null != expectedReturn.getReturnTime() && !expectedReturn.getReturnTime()
          .equals(returnActual.get(AchReturnsResponse.RETURN_RETURN_TIME))) {
        continue;
      }

      returnFound = true;
    }
    return returnFound;
  }
}
