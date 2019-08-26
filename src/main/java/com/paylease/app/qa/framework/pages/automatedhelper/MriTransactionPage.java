package com.paylease.app.qa.framework.pages.automatedhelper;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MriTransactionPage extends PageBase {

  private static final String URL = BASE_URL + "testing/automated_helper/mri_transaction";

  @FindBy(id = "transactionId")
  private WebElement transactionIdBox;

  @FindBy(id = "executeButton")
  private WebElement executeButton;

  /**
   * Get MRI transaction info.
   *
   * @param transactionId Trans ID to search for
   * @return results page
   */
  public MriTransactionProcessingPage getDataForTransactionId(String transactionId) {
    // set the transaction id
    enterText(transactionIdBox, transactionId);

    clickAndWait(executeButton);

    Logger.trace("Submit trans ID to search for in mri_transactions table");
    return new MriTransactionProcessingPage();
  }

  /**
   * open the page.
   */
  public void open() {
    openAndWait(URL);
  }
}
