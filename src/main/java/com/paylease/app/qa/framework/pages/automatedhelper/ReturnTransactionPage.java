package com.paylease.app.qa.framework.pages.automatedhelper;

import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ReturnTransactionPage extends PageBase {
  private static final String URL = BASE_URL + "testing/automated_helper/return_transaction";

  @FindBy(id = "transactionId")
  private WebElement transactionIdBox;

  @FindBy(id = "executeButton")
  private WebElement executeButton;

  @FindBy(id = "returnOne")
  private WebElement returnOneRadio;

  /**
   * enter the given transaction id, submit and transaction is returned.
   *
   * @param transactionId Transaction that needs to be returned
   */
  public void returnTransaction(String transactionId) {
    // set the transaction id
    enterText(transactionIdBox, transactionId);

    clickAndWait(executeButton);
  }

  /** Partial return(not all batch items are returned). */
  public void partialReturn() {
    returnOneRadio.click();
  }

  /** open the page. */
  public void open() {
    openAndWait(URL);
  }
}
