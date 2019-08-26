package com.paylease.app.qa.framework.pages.pm;

import com.paylease.app.qa.framework.pages.paymentflow.ReceiptPage;

public class PmPaymentHistoryReceiptPage extends ReceiptPage {

  private static final String URL = BASE_URL + "pm/pm_payment_history/receipt/{transactionId}";

  private String transactionId;

  /**
   * PM Payment History Receipt Page object.
   *
   * @param transactionId transactionId
   */
  public PmPaymentHistoryReceiptPage(String transactionId) {
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
}
