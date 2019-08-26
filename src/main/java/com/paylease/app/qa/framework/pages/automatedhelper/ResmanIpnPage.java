package com.paylease.app.qa.framework.pages.automatedhelper;

import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ResmanIpnPage extends PageBase {

  private static final String URL = BASE_URL + "testing/automated_helper/resman_ipn";

  @FindBy(id = "transactionId")
  private WebElement transactionIdBox;

  @FindBy(id = "executeButton")
  private WebElement executeButton;

  /**
   * enter the given transaction id, submit and ipn is sent to Resman.
   *
   * @param transactionId Transaction that needs IPN to be sent
   */
  public void runResmanIpnForTrans(String transactionId) {
    // set the transaction id
    enterText(transactionIdBox, transactionId);

    clickAndWait(executeButton);
  }

  /** open the page. */
  public void open() {
    openAndWait(URL);
  }
}
