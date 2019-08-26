package com.paylease.app.qa.framework.api.gapi.response;

import com.jamesmurty.utils.XMLBuilder;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.api.SpecificResponse;
import java.util.ArrayList;
import java.util.HashMap;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

public class AchReturnsResponse extends SpecificResponse {
  public static final String RETURN_TRANSACTION_ID = "transactionId";
  public static final String RETURN_TRANSACTION_DATE = "transactionDate";
  public static final String RETURN_TRANSACTION_TIME = "transactionTime";
  public static final String RETURN_RETURN_STATUS = "returnStatus";
  public static final String RETURN_RETURN_CODE = "returnCode";
  public static final String RETURN_RETURN_DATE = "returnDate";
  public static final String RETURN_RETURN_TIME = "returnTime";

  private static final String XPATH_ALL_TRANSACTIONS_COUNT = "count(Transactions/Transaction)";
  private static final String XPATH_EACH_TRANSACTION = "Transactions/Transaction";
  private static final String XPATH_RETURN_TRANSACTION_ID = "TransactionId";
  private static final String XPATH_RETURN_TRANSACTION_DATE = "TransactionDate";
  private static final String XPATH_RETURN_TRANSACTION_TIME = "TransactionTime";
  private static final String XPATH_RETURN_RETURN_STATUS = "ReturnStatus";
  private static final String XPATH_RETURN_RETURN_CODE = "ReturnCode";
  private static final String XPATH_RETURN_RETURN_DATE = "ReturnDate";
  private static final String XPATH_RETURN_RETURN_TIME = "ReturnTime";

  public AchReturnsResponse(XMLBuilder response) {
    super(response);
  }

  /**
   * Get Return Transactions from response.
   *
   * @return List of maps representing return transaction content
   */
  public ArrayList<HashMap<String, Object>> getReturnTransactions() {
    ArrayList<HashMap<String, Object>> transactionList = new ArrayList<>();
    try {
      int returnCount = ((Number) response
          .xpathQuery(XPATH_ALL_TRANSACTIONS_COUNT, XPathConstants.NUMBER)).intValue();
      Logger.debug("Return Count: " + returnCount);
      for (int i = 1; i <= returnCount; i++) {
        XMLBuilder returnTrans = response.xpathFind(XPATH_EACH_TRANSACTION + "[" + i + "]");
        HashMap<String, Object> returnMap = new HashMap<>();
        returnMap.put(RETURN_TRANSACTION_ID, getElementText(returnTrans, XPATH_RETURN_TRANSACTION_ID));
        returnMap.put(RETURN_TRANSACTION_DATE, getElementText(returnTrans, XPATH_RETURN_TRANSACTION_DATE));
        returnMap.put(RETURN_TRANSACTION_TIME, getElementText(returnTrans, XPATH_RETURN_TRANSACTION_TIME));
        returnMap.put(RETURN_RETURN_STATUS, getElementText(returnTrans, XPATH_RETURN_RETURN_STATUS));
        returnMap.put(RETURN_RETURN_CODE, getElementText(returnTrans, XPATH_RETURN_RETURN_CODE));
        returnMap.put(RETURN_RETURN_DATE, getElementText(returnTrans, XPATH_RETURN_RETURN_DATE));
        returnMap.put(RETURN_RETURN_TIME, getElementText(returnTrans, XPATH_RETURN_RETURN_TIME));

        transactionList.add(returnMap);
      }

    } catch (XPathExpressionException e) {
      Logger.debug("Could not find Transaction element in response - " + e.getMessage());
    }
    return transactionList;
  }
}
