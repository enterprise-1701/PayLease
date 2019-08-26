package com.paylease.app.qa.framework.pages.automatedhelper;

import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * this class interacts with the transaction helper page.
 * <p/>
 * for a given transaction this page provides the ability to:
 * - get the from_id
 * - get the to_id
 */
public class TransactionPage extends PageBase {
  private static final String URL = BASE_URL + "testing/automated_helper/transaction";

  @FindBy(id = "transactionId")
  private WebElement transactionIdBox;

  @FindBy(id = "executeButton")
  private WebElement executeButton;

  /**
   * enter the given transaction id, submit the page, and return the transaction
   * information page.
   *
   * @param transactionId Transaction to search for
   * @return TransactionProcessingPage
   */
  public TransactionProcessingPage getDataForTransactionId(String transactionId) {
    // set the transaction id
    enterText(transactionIdBox, transactionId);

    clickAndWait(executeButton);

    return new TransactionProcessingPage();
  }

  /**
   * open the page.
   */
  public void open() {
    openAndWait(URL);
  }
}
