package com.paylease.app.qa.api.tests.gapi.testcase;

import com.paylease.app.qa.api.tests.ExpectedResponse;
import com.paylease.app.qa.framework.api.Request;
import com.paylease.app.qa.framework.api.gapi.GapiTransaction;
import com.paylease.app.qa.framework.api.gapi.TransactionSearchTransaction;
import java.util.HashMap;

public class TransactionSearch extends BasicTestCase {

  private static final String VALID_FIELD_TRANSACTION_TYPE = "transactionType";
  private static final String VALID_FIELD_NUM_ITEMS = "numItems";

  private String transactionType;
  private String numberOfItems;

  private TransactionSearch(
      String summary, ExpectedResponse expectedResponse
  ) {
    this(summary, expectedResponse, null, null);
  }

  private TransactionSearch(
      String summary, ExpectedResponse expectedResponse, String transactionType,
      String numberOfItems
  ) {
    super(summary, expectedResponse);

    this.transactionType = transactionType;
    this.numberOfItems = numberOfItems;
  }

  /**
   * Create a valid test case for TransactionSearch.
   *
   * @param summary Message for this test case
   * @param expectedResponse Expected response returned by GAPI
   * @return Valid test case
   */
  public static TransactionSearch createValid(String summary, ExpectedResponse expectedResponse) {
    TransactionSearch testCase = new TransactionSearch(
        summary, expectedResponse);

    testCase.transactionType = testCase.getValidValue(VALID_FIELD_TRANSACTION_TYPE);
    testCase.numberOfItems = testCase.getValidValue(VALID_FIELD_NUM_ITEMS);

    return testCase;
  }

  protected void initValidValues() {
    super.initValidValues();
    validValues.put(VALID_FIELD_TRANSACTION_TYPE, new String[]{
        "ACH", "CreditCard",
    });
    validValues.put(VALID_FIELD_NUM_ITEMS, new String[]{
        "5",
    });
  }

  public TransactionSearch setTransactionType(String transactionType) {
    this.transactionType = transactionType;
    return this;
  }

  public TransactionSearch setNumberOfItems(String numberOfItems) {
    this.numberOfItems = numberOfItems;
    return this;
  }

  @Override
  public void addTransaction(Request apiRequest) {
    HashMap<String, String> parameters = new HashMap<>();

    parameters.put(TransactionSearchTransaction.TRANSACTION_TYPE, transactionType);
    parameters.put(TransactionSearchTransaction.NUMBER_OF_ITEMS, numberOfItems);

    GapiTransaction transaction = new TransactionSearchTransaction(parameters);
    apiRequest.addTransaction(transaction);
  }
}
