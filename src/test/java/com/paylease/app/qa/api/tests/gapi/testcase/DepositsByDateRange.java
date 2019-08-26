package com.paylease.app.qa.api.tests.gapi.testcase;

import com.paylease.app.qa.api.tests.ExpectedResponse;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.api.Request;
import com.paylease.app.qa.framework.api.Response;
import com.paylease.app.qa.framework.api.gapi.response.DepositsByDateRangeResponse;
import com.paylease.app.qa.framework.api.gapi.DepositsByDateRangeTransaction;
import com.paylease.app.qa.framework.api.gapi.GapiTransaction;
import java.util.ArrayList;
import java.util.HashMap;

public class DepositsByDateRange extends BasicTestCase {

  private static final String VALID_FIELD_START_DATE = "startDate";
  private static final String VALID_FIELD_END_DATE = "endDate";

  private String searchStartDate;
  private String searchEndDate;
  private String currencyCode;

  private String totalDeposits;
  private String totalDepositAmount;
  private String totalCurrencyCode;

  private ArrayList<Deposit> deposits;

  public DepositsByDateRange(String summary) {
    this(summary, null, null, null);
  }

  private DepositsByDateRange(
      String summary, ExpectedResponse expectedResponse
  ) {
    this(summary, expectedResponse, null, null);
  }

  private DepositsByDateRange(
      String summary, ExpectedResponse expectedResponse, String searchStartDate,
      String searchEndDate
  ) {
    super(summary, expectedResponse);

    this.searchStartDate = searchStartDate;
    this.searchEndDate = searchEndDate;

    this.deposits = new ArrayList<>();
  }

  /**
   * Factory method to create a valid request.
   *
   * @param summary Summary of this test case
   * @param expectedResponse response expected to be returned by GAPI
   * @return valid test case
   */
  public static DepositsByDateRange createValid(
      String summary, ExpectedResponse expectedResponse
  ) {
    DepositsByDateRange testCase = new DepositsByDateRange(summary, expectedResponse);

    testCase.searchStartDate = testCase.getValidValue(VALID_FIELD_START_DATE);
    testCase.searchEndDate = testCase.getValidValue(VALID_FIELD_END_DATE);

    return testCase;
  }

  protected void initValidValues() {
    super.initValidValues();
    validValues.put(VALID_FIELD_START_DATE, new String[]{
        "1/1/2015",
    });
    validValues.put(VALID_FIELD_END_DATE, new String[]{
        "1/1/2015",
    });
  }

  public DepositsByDateRange setSearchStartDate(String searchStartDate) {
    this.searchStartDate = searchStartDate;
    return this;
  }

  public DepositsByDateRange setSearchEndDate(String searchEndDate) {
    this.searchEndDate = searchEndDate;
    return this;
  }

  public DepositsByDateRange setCurrencyCode(String currencyCode) {
    this.currencyCode = currencyCode;
    return this;
  }

  public DepositsByDateRange setTotalDeposits(String totalDeposits) {
    this.totalDeposits = totalDeposits;
    return this;
  }

  public DepositsByDateRange setTotalDepositAmount(String totalDepositAmount) {
    this.totalDepositAmount = totalDepositAmount;
    return this;
  }

  public DepositsByDateRange setTotalCurrencyCode(String totalCurrencyCode) {
    this.totalCurrencyCode = totalCurrencyCode;
    return this;
  }

  public DepositsByDateRange addDeposit(Deposit deposit) {
    this.deposits.add(deposit);
    return this;
  }

  @Override
  public void addTransaction(Request apiRequest) {
    HashMap<String, String> parameters = new HashMap<>();
    parameters.put(DepositsByDateRangeTransaction.SEARCH_START_DATE, searchStartDate);
    parameters.put(DepositsByDateRangeTransaction.SEARCH_END_DATE, searchEndDate);
    parameters.put(DepositsByDateRangeTransaction.CURRENCY_CODE, currencyCode);

    GapiTransaction transaction = new DepositsByDateRangeTransaction(parameters);
    apiRequest.addTransaction(transaction);
  }


  @Override
  public boolean test(Response response) {
    if (null == totalDeposits) {
      return super.test(response);
    }

    response.setIndex(String.valueOf(getIndex()));
    boolean result;
    try {
      DepositsByDateRangeResponse specificResponse = new DepositsByDateRangeResponse(
          response.getSpecificResponseSnippet());
      String totalDepositsActual = specificResponse.getTotalDeposits();

      final boolean totalDepositResult = totalDepositsActual.equals(totalDeposits);
      if (!totalDepositResult) {
        Logger.error("Total Deposits mismatch: expected " + totalDeposits + ", found "
            + totalDepositsActual);
      }

      boolean totalDepositAmountResult = true;
      if (null != totalDepositAmount) {
        String totalDepositAmountActual = specificResponse.getTotalDepositAmount();

        totalDepositAmountResult = totalDepositAmountActual.equals(totalDepositAmount);
        if (!totalDepositAmountResult) {
          Logger.error("Total deposit amount mismatch: expected " + totalDepositAmount + ", found "
              + totalDepositAmountActual);
        }
      }

      boolean totalCurrencyCodeResult = true;
      if (null != totalCurrencyCode) {
        String totalCurrencyCodeActual = specificResponse.getTotalDepositCurrency();

        totalCurrencyCodeResult = totalCurrencyCode.equals(totalCurrencyCodeActual);
        if (!totalCurrencyCodeResult) {
          Logger.error(
              "Total deposit currency code mismatch: expected " + totalCurrencyCode + ", found "
                  + totalCurrencyCodeActual);
        }
      }

      boolean depositDetailsResult = true;
      if (!deposits.isEmpty()) {
        ArrayList<HashMap<String, Object>> depositsActual = specificResponse.getDeposits();
        Logger.debug(depositsActual.toString());

        for (Deposit deposit : deposits) {
          boolean specificDepositDetailsResult = isDepositPresent(deposit, depositsActual);
          if (!specificDepositDetailsResult) {
            Logger.error("Could not find deposit " + deposit.toString() + " in response");
          }
          depositDetailsResult = depositDetailsResult && specificDepositDetailsResult;
        }
      }

      result = totalDepositResult && totalDepositAmountResult && totalCurrencyCodeResult
          && depositDetailsResult;
    } catch (Exception e) {
      Logger.debug(e.getMessage());
      result = false;
    }

    return result;
  }

  private boolean isDepositPresent(Deposit expectedDeposit,
      ArrayList<HashMap<String, Object>> depositsActual) {
    boolean depositFound = false;
    for (HashMap<String, Object> depositActual : depositsActual) {
      if (null != expectedDeposit.getPayeeId() && !expectedDeposit.getPayeeId()
          .equals(depositActual.get(DepositsByDateRangeResponse.DEPOSIT_PAYEE_ID))) {
        continue;
      }
      if (null != expectedDeposit.getAccountNumber() && !expectedDeposit.getAccountNumber()
          .equals(depositActual.get(DepositsByDateRangeResponse.DEPOSIT_ACCOUNT_NUMBER))) {
        continue;
      }
      if (null != expectedDeposit.getDepositDate() && !expectedDeposit.getDepositDate()
          .equals(depositActual.get(DepositsByDateRangeResponse.DEPOSIT_DATE))) {
        continue;
      }
      if (null != expectedDeposit.getAmount() && !expectedDeposit.getAmount()
          .equals(depositActual.get(DepositsByDateRangeResponse.DEPOSIT_AMOUNT))) {
        continue;
      }
      if (null != expectedDeposit.getCurrencyCode() && !expectedDeposit.getCurrencyCode()
          .equals(depositActual.get(DepositsByDateRangeResponse.DEPOSIT_CURRENCY))) {
        continue;
      }
      boolean depositTransactionsFound = true;
      for (DepositTransaction depositTransaction : expectedDeposit.getDepositTransactions()) {
        boolean depositTransactionFound = false;
        ArrayList<HashMap<String, String>> depositTransactionsActual = (ArrayList) depositActual
            .get(DepositsByDateRangeResponse.DEPOSIT_TRANSACTIONS);
        for (HashMap<String, String> depositTransactionActual : depositTransactionsActual) {
          if (null != depositTransaction.getTransactionId() && !depositTransaction
              .getTransactionId().equals(depositTransactionActual
                  .get(DepositsByDateRangeResponse.DEPOSIT_TRANSACTION_ID))) {
            continue;
          }
          if (null != depositTransaction.getInitiatedDate() && !depositTransaction
              .getInitiatedDate().equals(depositTransactionActual
                  .get(DepositsByDateRangeResponse.DEPOSIT_TRANSACTION_INITIATED_DATE))) {
            continue;
          }
          if (null != depositTransaction.getPayoutDate() && !depositTransaction.getPayoutDate()
              .equals(depositTransactionActual
                  .get(DepositsByDateRangeResponse.DEPOSIT_TRANSACTION_PAYOUT_DATE))) {
            continue;
          }
          if (null != depositTransaction.getPaymentType() && !depositTransaction.getPaymentType()
              .equals(depositTransactionActual
                  .get(DepositsByDateRangeResponse.DEPOSIT_TRANSACTION_PAYMENT_TYPE))) {
            continue;
          }
          if (null != depositTransaction.getBillType() && !depositTransaction.getBillType().equals(
              depositTransactionActual
                  .get(DepositsByDateRangeResponse.DEPOSIT_TRANSACTION_BILL_TYPE))) {
            continue;
          }
          if (null != depositTransaction.getPaymentReferenceId() && !depositTransaction
              .getPaymentReferenceId().equals(depositTransactionActual
                  .get(DepositsByDateRangeResponse.DEPOSIT_TRANSACTION_REF_ID))) {
            continue;
          }
          if (null != depositTransaction.getAmount() && !depositTransaction.getAmount().equals(
              depositTransactionActual
                  .get(DepositsByDateRangeResponse.DEPOSIT_TRANSACTION_AMOUNT))) {
            continue;
          }
          if (null != depositTransaction.getCurrencyCode() && !depositTransaction.getCurrencyCode()
              .equals(depositTransactionActual
                  .get(DepositsByDateRangeResponse.DEPOSIT_TRANSACTION_CURRENCY))) {
            continue;
          }
          depositTransactionFound = true;
          break;
        }
        depositTransactionsFound = depositTransactionsFound && depositTransactionFound;
      }
      if (!depositTransactionsFound) {
        continue;
      }
      depositFound = true;
    }
    return depositFound;
  }
}