package com.paylease.app.qa.api.tests.gapi.testcase;

import com.paylease.app.qa.api.tests.ExpectedResponse;
import com.paylease.app.qa.framework.api.Request;
import com.paylease.app.qa.framework.api.gapi.GapiTransaction;
import com.paylease.app.qa.framework.api.gapi.TransactionSearchDateTransaction;
import java.util.HashMap;

public class TransactionSearchDate extends BasicTestCase {

  private static final String VALID_FIELD_START_DATE = "startDate";
  private static final String VALID_FIELD_END_DATE = "endDate";
  private static final String VALID_FIELD_NUM_ITEMS = "numItems";

  private String searchStartDate;
  private String searchStartTime;
  private String searchEndDate;
  private String searchEndTime;
  private String numberOfItems;

  private TransactionSearchDate(
      String summary, ExpectedResponse expectedResponse
  ) {
    this(summary, expectedResponse, null, null, null, null, null);
  }

  private TransactionSearchDate(
      String summary, ExpectedResponse expectedResponse, String searchStartDate,
      String searchStartTime, String searchEndDate, String searchEndTime, String numberOfItems
  ) {
    super(summary, expectedResponse);

    this.searchStartDate = searchStartDate;
    this.searchStartTime = searchStartTime;
    this.searchEndDate = searchEndDate;
    this.searchEndTime = searchEndTime;
    this.numberOfItems = numberOfItems;
  }

  /**
   * Create a valid test case for TransactionSearchDate.
   *
   * @param summary Message for this test case
   * @param expectedResponse Expected response returned by GAPI
   * @return Valid test case
   */
  public static TransactionSearchDate createValid(
      String summary, ExpectedResponse expectedResponse
  ) {
    TransactionSearchDate testCase = new TransactionSearchDate(summary, expectedResponse);

    testCase.searchStartDate = testCase.getValidValue(VALID_FIELD_START_DATE);
    testCase.searchEndDate = testCase.getValidValue(VALID_FIELD_END_DATE);
    testCase.numberOfItems = testCase.getValidValue(VALID_FIELD_NUM_ITEMS);

    return testCase;
  }

  protected void initValidValues() {
    super.initValidValues();
    validValues.put(VALID_FIELD_START_DATE, new String[]{
        "04/15/2014", "04/04/2014", "01/01/2013", "01/02/2013", "04/29/2014",
    });
    validValues.put(VALID_FIELD_END_DATE, new String[]{
        "04/15/2014", "04/04/2014", "01/01/2013", "01/02/2013", "04/29/2014",
    });
    validValues.put(VALID_FIELD_NUM_ITEMS, new String[]{
        "2",
    });
  }

  public String getSearchStartDate() {
    return searchStartDate;
  }

  public TransactionSearchDate setSearchStartDate(String searchStartDate) {
    this.searchStartDate = searchStartDate;
    return this;
  }

  public String getSearchEndDate() {
    return searchEndDate;
  }

  public TransactionSearchDate setSearchEndDate(String searchEndDate) {
    this.searchEndDate = searchEndDate;
    return this;
  }

  public TransactionSearchDate setNumberOfItems(String numberOfItems) {
    this.numberOfItems = numberOfItems;
    return this;
  }

  private String getSearchStartTime() {
    return searchStartTime;
  }

  public TransactionSearchDate setSearchStartTime(String searchStartTime) {
    this.searchStartTime = searchStartTime;
    return this;
  }

  private String getSearchEndTime() {
    return searchEndTime;
  }

  public TransactionSearchDate setSearchEndTime(String searchEndTime) {
    this.searchEndTime = searchEndTime;
    return this;
  }

  @Override
  public void addTransaction(Request apiRequest) {

    String searchStartDate = null;
    if (getSearchStartDate() != null) {
      searchStartDate = getSearchStartDate();
    }
    if (getSearchStartTime() != null) {
      if (searchStartDate == null) {
        searchStartDate = getSearchStartTime();
      } else {
        searchStartDate += " " + getSearchStartTime();
      }
    }

    String searchEndDate = null;
    if (getSearchEndDate() != null) {
      searchEndDate = getSearchEndDate();
    }
    if (getSearchEndTime() != null) {
      if (searchEndDate == null) {
        searchEndDate = getSearchEndTime();
      } else {
        searchEndDate += " " + getSearchEndTime();
      }
    }

    HashMap<String, String> parameters = new HashMap<>();

    parameters.put(TransactionSearchDateTransaction.SEARCH_START_DATE, searchStartDate);
    parameters.put(TransactionSearchDateTransaction.SEARCH_END_DATE, searchEndDate);
    parameters.put(TransactionSearchDateTransaction.NUMBER_OF_ITEMS, numberOfItems);

    GapiTransaction transaction = new TransactionSearchDateTransaction(parameters);
    apiRequest.addTransaction(transaction);
  }
}
