package com.paylease.app.qa.framework.pages.resident;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.pages.paymentflow.CardAccountDetailsPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ResidentAddCardAccountPage extends CardAccountDetailsPage {

  public static String URL = BASE_URL + "resident/accounts/add_credit_card";
  @FindBy(name = "add_account")
  private WebElement continueButton;

  /**
   * Resident Add card Account page object.
   */
  public ResidentAddCardAccountPage() {
    super();
  }

  public void open() {
    openAndWait(URL);
  }

  // ********************************************Action*********************************************

  @Override
  protected void clickContinueButton() {
    highlight(continueButton);
    clickAndWait(continueButton);

    Logger.trace("Clicked Continue button on Add Card account page");
  }
}
