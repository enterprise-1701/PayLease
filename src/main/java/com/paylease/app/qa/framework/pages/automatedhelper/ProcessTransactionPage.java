package com.paylease.app.qa.framework.pages.automatedhelper;

import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProcessTransactionPage extends PageBase {
  private static final String URL = BASE_URL + "testing/automated_helper/process_transaction";

  @FindBy(id = "transactionId")
  private WebElement transactionIdBox;

  @FindBy(id = "executeButton")
  private WebElement executeButton;

  /**
   * Enter the given transaction id, submit and transaction is processed.
   *
   * @param transactionId Transaction that needs to be returned
   */
  public void processTransaction(String transactionId) {
    // set the transaction id
    enterText(transactionIdBox, transactionId);

    clickAndWait(executeButton);
  }

  /** Open the page. */
  public void open() {
    openAndWait(URL);
  }
}