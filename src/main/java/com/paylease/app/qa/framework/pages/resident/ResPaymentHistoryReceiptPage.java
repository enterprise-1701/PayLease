package com.paylease.app.qa.framework.pages.resident;

import com.paylease.app.qa.framework.pages.paymentflow.ReceiptPage;

public class ResPaymentHistoryReceiptPage extends ReceiptPage {

  private static final String URL = BASE_URL + "resident/payment_history/receipt/{transactionId}";

  private String transactionId;

  public ResPaymentHistoryReceiptPage(String transactionId) {
    super();
    this.transactionId = transactionId;
  }

  // ********************************************Action*********************************************

  public String getUrl() {
    return URL.replace("{transactionId}", transactionId);
  }

  public void open() {
    openAndWait(getUrl());
  }

  public void openMobile() {
    openAndWait(getUrl() + "?vpw=300");
  }
}
