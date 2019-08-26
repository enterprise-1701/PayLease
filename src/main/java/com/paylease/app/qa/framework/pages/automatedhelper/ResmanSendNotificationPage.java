package com.paylease.app.qa.framework.pages.automatedhelper;

import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ResmanSendNotificationPage extends PageBase {

  private static final String URL = BASE_URL + "testing/automated_helper/resman_send_notification";

  @FindBy(id = "transactionId")
  private WebElement transactionIdBox;

  @FindBy(id = "executeButton")
  private WebElement executeButton;

  /**
   * enter the given transaction id, submit and Send Notification is run for the PM of the transaction
   *
   * @param transactionId Transaction that belongs to PM that needs Resman Send Notification
   */
  public void runResmanSendNotificationForTrans(String transactionId) {
    // set the transaction id
    enterText(transactionIdBox, transactionId);

    clickAndWait(executeButton);
  }

  /** open the page. */
  public void open() {
    openAndWait(URL);
  }
}
