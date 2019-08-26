package com.paylease.app.qa.framework.pages.registration;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PayPortalShortAccountRegPage extends PageBase {

  private static final String URL = BASE_URL + "registration/pay_portal/{pmId}/ACC";

  private String url;

  @FindBy(id = "account_number")
  private WebElement accountNumberField;

  @FindBy(name = "continue_btn")
  private WebElement proceedButton;

  /**
   * Pay Portal registration Page object.
   *
   * @param pmId pmId
   */
  public PayPortalShortAccountRegPage(String pmId) {
    super();
    this.url = URL.replace("{pmId}", pmId);
  }

  // ********************************************Action*********************************************

  public void open() {
    openAndWait(url);
  }

  /**
   * Enter account number in account field.
   */
  public void enterAccountNumber(String accountNumber) {
    highlight(accountNumberField);

    accountNumberField.click();
    accountNumberField.sendKeys(accountNumber);

    Logger.trace("Entered account number");
  }

  /**
   * Click on Proceed button.
   *
   * @return PayPortalChooseUserPage
   */
  public PayPortalChooseUserPage clickOnProceedButton() {
    highlight(proceedButton);

    clickAndWait(proceedButton);
    Logger.trace("Clicked proceed with registration button");

    return new PayPortalChooseUserPage();
  }

}
