package com.paylease.app.qa.framework.pages.resident;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.pages.paymentflow.BankAccountDetailsPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ResidentAddBankAccountPage extends BankAccountDetailsPage {

  @FindBy(name = "add_account")
  private WebElement continueButton;

  /**
   * Resident Add Bank Account page object.
   */
  public ResidentAddBankAccountPage() {
    super();
  }

  // ********************************************Action*********************************************

  @Override
  protected void clickContinueButton() {
    highlight(continueButton);
    clickAndWait(continueButton);

    Logger.trace("Clicked Continue button on Add Bank account page");
  }
}
