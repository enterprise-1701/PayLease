package com.paylease.app.qa.framework.pages.automatedhelper;

import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * this class interacts with the Brick FTP Webhook Request helper page.
 *
 * for a given request this page provides the ability to:
 * - get the data from the table
 */
public class BrickFtpWebhookRequestHelperPage extends PageBase {
  private static final String URL = BASE_URL + "testing/automated_helper/brick_ftp_webhook_request";

  @FindBy(id = "username")
  private WebElement usernameInput;

  @FindBy(id = "path")
  private WebElement pathInput;

  @FindBy(id = "executeButton")
  private WebElement executeButton;

  /**
   * enter the given transaction id, submit the page, and return the transaction
   * information page.
   *
   * @param username username of the request to find
   * @param path path of the request to find
   * @return BrickFtpWebhookRequestProcessingPage
   */
  public BrickFtpWebhookRequestProcessingPage getDataForRequest(String username, String path) {
    // set the username
    usernameInput.click();
    usernameInput.clear();
    usernameInput.sendKeys(username);

    // set the path
    pathInput.click();
    pathInput.clear();
    pathInput.sendKeys(path);

    clickAndWait(executeButton);

    return new BrickFtpWebhookRequestProcessingPage();
  }

  /**
   * open the page.
   */
  public void open() {
    openAndWait(URL);
  }
}
