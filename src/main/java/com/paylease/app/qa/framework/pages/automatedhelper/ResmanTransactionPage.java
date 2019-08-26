package com.paylease.app.qa.framework.pages.automatedhelper;

import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ResmanTransactionPage extends PageBase {
  private static final String URL = BASE_URL + "testing/automated_helper/resman_transaction";

  @FindBy(id = "transactionId")
  private WebElement transactionIdBox;

  @FindBy(id = "executeButton")
  private WebElement executeButton;

  /**
   * enter the given transaction id, submit the page, and return the resman transaction
   * information page.
   *
   * @param transactionId transaction ID to look for in Resman transactions
   * @return TransactionProcessingPage
   */
  public ResmanTransactionProcessingPage getDataForTransactionId(String transactionId) {
    // set the transaction id
    enterText(transactionIdBox, transactionId);

    clickAndWait(executeButton);

    return new ResmanTransactionProcessingPage();
  }

  /** open the page. */
  public void open() {
    openAndWait(URL);
  }
}