package com.paylease.app.qa.framework.pages.registration;

import com.paylease.app.qa.framework.DataHelper;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class PayPortalChooseUserPage extends PageBase {

  private static final String URL = BASE_URL + "registration/choose_user/{pmId}/ACC";

  @FindBy(id = "propertyname")
  private WebElement nameSelect;

  @FindBy(id = "user_name")
  private WebElement emailField;

  @FindBy(id = "password")
  private WebElement passwordField;

  @FindBy(id = "password_confirm")
  private WebElement confirmPasswordField;

  @FindBy(name = "referrer")
  private WebElement referrerSelect;

  @FindBy(id = "terms")
  private WebElement termsCheckbox;

  @FindBy(name = "complete_btn")
  private WebElement createAccountButton;

  @FindBy(css = "label.error")
  private WebElement errorMessage;

  // ********************************************Action*********************************************

  /**
   * Fill and submit registration info.
   */
  public void fillAndSubmit(String residentId) {
    selectName(residentId);
    enterEmail();
    enterPassword();
    enterConfirmPassword();
    selectReferrer();
    clickTermsCheckbox();

    clickCreateAccountButton();
  }

  private void selectName(String residentId) {
    highlight(nameSelect);

    Select selectNames = new Select(nameSelect);

    selectNames.selectByValue(residentId);

    Logger.trace("Name is selected");
  }

  private void enterEmail() {
    DataHelper dataHelper = new DataHelper();

    String email = dataHelper.getUniqueEmail();

    highlight(emailField);
    emailField.click();

    emailField.sendKeys(email);

    Logger.trace("Email entered as: " + email);
  }

  private void enterPassword() {
    highlight(passwordField);
    passwordField.click();

    passwordField.sendKeys(STANDARD_PASS);

    Logger.trace("Standard password entered");
  }

  private void enterConfirmPassword() {
    highlight(confirmPasswordField);
    confirmPasswordField.click();
    confirmPasswordField.sendKeys(STANDARD_PASS);

    Logger.trace("Standard password confirmed");
  }

  private void selectReferrer() {
    highlight(referrerSelect);

    Select selectReferrer = new Select(referrerSelect);

    selectReferrer.selectByValue("2");

    Logger.trace("Referrer is selected");
  }

  private void clickTermsCheckbox() {
    if (!termsCheckbox.isSelected()) {
      termsCheckbox.click();
      Logger.trace("Checked " + termsCheckbox + " box");
    } else {
      Logger.trace("Checkbox " + termsCheckbox + " already checked");
    }
  }

  private void clickCreateAccountButton() {
    highlight(createAccountButton);

    createAccountButton.click();
    Logger.trace("Clicked on create account button");
  }

  /**
   * Get error message.
   *
   * @return error message text
   */
  public String getErrorMessage() {
    highlight(errorMessage);

    return errorMessage.getText();
  }

}
