package com.paylease.app.qa.api.tests.gapi.testcase;

import com.paylease.app.qa.api.tests.ExpectedResponse;
import com.paylease.app.qa.framework.api.Request;
import com.paylease.app.qa.framework.api.gapi.GapiTransaction;
import com.paylease.app.qa.framework.api.gapi.TransactionDetailTransaction;
import java.util.HashMap;

public class TransactionDetail extends BasicTestCase {

  private String transactionId;
  private String paymentReferenceId;

  public TransactionDetail(
      String transactionId, String summary, ExpectedResponse expectedResponse
  ) {
    this(transactionId, summary, expectedResponse, null);
  }

  private TransactionDetail(
      String transactionId, String summary, ExpectedResponse expectedResponse,
      String paymentReferenceId
  ) {
    super(summary, expectedResponse);

    this.transactionId = transactionId;

    this.paymentReferenceId = paymentReferenceId;
  }

  public TransactionDetail setPaymentReferenceId(String paymentReferenceId) {
    this.paymentReferenceId = paymentReferenceId;
    return this;
  }

  @Override
  public void addTransaction(Request apiRequest) {
    HashMap<String, String> parameters = new HashMap<>();

    parameters.put(TransactionDetailTransaction.TRANSACTION_ID, transactionId);
    parameters.put(TransactionDetailTransaction.PAYMENT_REFERENCE_ID, paymentReferenceId);

    GapiTransaction transaction = new TransactionDetailTransaction(parameters);
    apiRequest.addTransaction(transaction);
  }
}
