package com.paylease.app.qa.framework.api.gapi.response;

import com.jamesmurty.utils.XMLBuilder;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.api.SpecificResponse;
import java.util.ArrayList;
import java.util.HashMap;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

public class DepositsByDateRangeResponse extends SpecificResponse {

  public static final String DEPOSIT_PAYEE_ID = "payeeId";
  public static final String DEPOSIT_ACCOUNT_NUMBER = "accountNumber";
  public static final String DEPOSIT_DATE = "date";
  public static final String DEPOSIT_AMOUNT = "amount";
  public static final String DEPOSIT_CURRENCY = "currency";
  public static final String DEPOSIT_TOTAL_TRANSACTIONS = "totalTransactions";
  public static final String DEPOSIT_TRANSACTIONS = "transactions";
  public static final String DEPOSIT_TRANSACTION_ID = "id";
  public static final String DEPOSIT_TRANSACTION_INITIATED_DATE = "initiatedDate";
  public static final String DEPOSIT_TRANSACTION_PAYOUT_DATE = "payoutDate";
  public static final String DEPOSIT_TRANSACTION_PAYMENT_TYPE = "paymentType";
  public static final String DEPOSIT_TRANSACTION_BILL_TYPE = "billType";
  public static final String DEPOSIT_TRANSACTION_REF_ID = "refId";
  public static final String DEPOSIT_TRANSACTION_AMOUNT = "amount";
  public static final String DEPOSIT_TRANSACTION_CURRENCY = "currency";

  private static final String XPATH_TOTAL_DEPOSITS = "Deposits/TotalDeposits";
  private static final String XPATH_TOTAL_DEPOSIT_AMOUNT = "Deposits/TotalDepositAmount";
  private static final String XPATH_TOTAL_DEPOSIT_CURRENCY = "Deposits/TotalDepositCurrencyCode";
  private static final String XPATH_ALL_DEPOSITS_COUNT = "count(Deposits/Deposit)";
  private static final String XPATH_EACH_DEPOSIT = "Deposits/Deposit";
  private static final String XPATH_DEPOSIT_PAYEE_ID = "PayeeId";
  private static final String XPATH_DEPOSIT_ACCOUNT_NUMBER = "AccountNumber";
  private static final String XPATH_DEPOSIT_DEPOSIT_DATE = "DepositDate";
  private static final String XPATH_DEPOSIT_DEPOSIT_AMOUNT = "DepositAmount";
  private static final String XPATH_DEPOSIT_DEPOSIT_CURRENCY = "DepositCurrencyCode";
  private static final String XPATH_DEPOSIT_TOTAL_TRANSACTIONS = "TotalTransactions";
  private static final String XPATH_DEPOSIT_TRANSACTIONS_COUNT = "count(TransactionsDetail/TransactionDetail)";
  private static final String XPATH_DEPOSIT_EACH_TRANSACTION = "TransactionsDetail/TransactionDetail";
  private static final String XPATH_DEPOSIT_TRANSACTION_TRANSACTION_ID = "TransactionId";
  private static final String XPATH_DEPOSIT_TRANSACTION_INITIATED_DATE = "InitiatedDate";
  private static final String XPATH_DEPOSIT_TRANSACTION_PAYOUT_DATE = "PayoutDate";
  private static final String XPATH_DEPOSIT_TRANSACTION_PAYMENT_TYPE = "PaymentType";
  private static final String XPATH_DEPOSIT_TRANSACTION_BILL_TYPE = "BillType";
  private static final String XPATH_DEPOSIT_TRANSACTION_PAYMENT_REFERENCE_ID = "PaymentReferenceId";
  private static final String XPATH_DEPOSIT_TRANSACTION_AMOUNT = "Amount";
  private static final String XPATH_DEPOSIT_TRANSACTION_CURRENCY = "CurrencyCode";

  public DepositsByDateRangeResponse(XMLBuilder response) {
    super(response);
  }

  public String getTotalDeposits() {
    return getElementText(XPATH_TOTAL_DEPOSITS);
  }

  public String getTotalDepositAmount() {
    return getElementText(XPATH_TOTAL_DEPOSIT_AMOUNT);
  }

  public String getTotalDepositCurrency() {
    return getElementText(XPATH_TOTAL_DEPOSIT_CURRENCY);
  }

  public ArrayList<HashMap<String, Object>> getDeposits() {
    ArrayList<HashMap<String, Object>> depositList = new ArrayList<>();
    try {
      int depositCount = ((Number) response
          .xpathQuery(XPATH_ALL_DEPOSITS_COUNT, XPathConstants.NUMBER)).intValue();
      Logger.debug("Deposit Count: " + depositCount);
      for (int i = 1; i <= depositCount; i++) {
        XMLBuilder deposit = response.xpathFind(XPATH_EACH_DEPOSIT + "[" + i + "]");
        HashMap<String, Object> depositMap = new HashMap<>();
        depositMap.put(DEPOSIT_PAYEE_ID, getElementText(deposit, XPATH_DEPOSIT_PAYEE_ID));
        depositMap
            .put(DEPOSIT_ACCOUNT_NUMBER, getElementText(deposit, XPATH_DEPOSIT_ACCOUNT_NUMBER));
        depositMap.put(DEPOSIT_DATE, getElementText(deposit, XPATH_DEPOSIT_DEPOSIT_DATE));
        depositMap.put(DEPOSIT_AMOUNT, getElementText(deposit, XPATH_DEPOSIT_DEPOSIT_AMOUNT));
        depositMap.put(DEPOSIT_CURRENCY, getElementText(deposit, XPATH_DEPOSIT_DEPOSIT_CURRENCY));
        depositMap.put(DEPOSIT_TOTAL_TRANSACTIONS,
            getElementText(deposit, XPATH_DEPOSIT_TOTAL_TRANSACTIONS));

        ArrayList<HashMap<String, String>> transactions = new ArrayList<>();
        int depositTransactionCount = ((Number) deposit
            .xpathQuery(XPATH_DEPOSIT_TRANSACTIONS_COUNT, XPathConstants.NUMBER)).intValue();
        Logger.debug("Deposit Transaction Count: " + depositTransactionCount);
        for (int j = 1; j <= depositTransactionCount; j++) {
          XMLBuilder depositTransaction = deposit
              .xpathFind(XPATH_DEPOSIT_EACH_TRANSACTION + "[" + j + "]");
          HashMap<String, String> transactionMap = new HashMap<>();
          transactionMap.put(DEPOSIT_TRANSACTION_ID,
              getElementText(depositTransaction, XPATH_DEPOSIT_TRANSACTION_TRANSACTION_ID));
          transactionMap.put(DEPOSIT_TRANSACTION_INITIATED_DATE,
              getElementText(depositTransaction, XPATH_DEPOSIT_TRANSACTION_INITIATED_DATE));
          transactionMap.put(DEPOSIT_TRANSACTION_PAYOUT_DATE,
              getElementText(depositTransaction, XPATH_DEPOSIT_TRANSACTION_PAYOUT_DATE));
          transactionMap.put(DEPOSIT_TRANSACTION_PAYMENT_TYPE,
              getElementText(depositTransaction, XPATH_DEPOSIT_TRANSACTION_PAYMENT_TYPE));
          transactionMap.put(DEPOSIT_TRANSACTION_BILL_TYPE,
              getElementText(depositTransaction, XPATH_DEPOSIT_TRANSACTION_BILL_TYPE));
          transactionMap.put(DEPOSIT_TRANSACTION_REF_ID,
              getElementText(depositTransaction, XPATH_DEPOSIT_TRANSACTION_PAYMENT_REFERENCE_ID));
          transactionMap.put(DEPOSIT_TRANSACTION_AMOUNT,
              getElementText(depositTransaction, XPATH_DEPOSIT_TRANSACTION_AMOUNT));
          transactionMap.put(DEPOSIT_TRANSACTION_CURRENCY,
              getElementText(depositTransaction, XPATH_DEPOSIT_TRANSACTION_CURRENCY));
          transactions.add(transactionMap);
        }
        depositMap.put(DEPOSIT_TRANSACTIONS, transactions);
        depositList.add(depositMap);
      }

    } catch (XPathExpressionException e) {
      Logger.debug("Could not find Deposit element in response - " + e.getMessage());
    }
    return depositList;
  }
}
