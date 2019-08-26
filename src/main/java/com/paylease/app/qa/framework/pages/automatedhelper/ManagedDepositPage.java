package com.paylease.app.qa.framework.pages.automatedhelper;

import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * this class interacts with the managed deposit helper page
 *
 * this page provides the ability to:
 * - close a related managed deposit for a transaction internally and externally
 * - close a related managed deposit for a transaction externally only
 * - close a related managed deposit for a transaction internally only
 * - create a managed deposit for a transaction
 */
public class ManagedDepositPage extends PageBase {
  private static final String URL = BASE_URL + "testing/automated_helper/managed_deposit";

  // processing actions
  public static final String CLOSE = "close";
  public static final String CLOSE_EXTERNALLY = "close_externally";
  public static final String CLOSE_INTERNALLY = "close_internally";
  public static final String CREATE = "create";

  @FindBy(id = "transactionId")
  private WebElement transactionIdBox;

  @FindBy(id = "executeButton")
  private WebElement executeButton;

  /**
   * process the given action for the managed deposit related to the given transaction id
   *
   * @param action valid values: close, close_externally, close_internally, create
   * @param transactionId test transaction id
   * @return a managed deposit process result page
   */
  public ManagedDepositProcessingPage processManagedDepositForTransactionId(
      String action,
      String transactionId
  ) {
    // set the transaction id
    transactionIdBox.click();
    transactionIdBox.clear();
    transactionIdBox.sendKeys(transactionId);

    // set the action type
    String idValue = getIdValue(action);
    WebElement radioButton = driver.findElement(By.id(idValue));
    radioButton.click();

    clickAndWait(executeButton);

    return new ManagedDepositProcessingPage();
  }

  /**
   * get the element id for the given action
   *
   * @param action valid values: close, close_externally, close_internally, create
   * @return an element id
   * @throws IllegalArgumentException when given an invalid action
   */
  private String getIdValue(String action) {
    String idValue;

    switch (action) {
      case CLOSE:
        idValue = "actionClose";
        break;
      case CLOSE_EXTERNALLY:
        idValue = "actionCloseExternally";
        break;
      case CLOSE_INTERNALLY:
        idValue = "actionCloseInternally";
        break;
      case CREATE:
        idValue = "actionCreate";
        break;
      default:
        throw new IllegalArgumentException("Invalid value");
    }

    return idValue;
  }

  /**
   * open the page
   */
  public void open() {
    openAndWait(URL);
  }
}
