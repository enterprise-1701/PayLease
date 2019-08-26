package com.paylease.app.qa.framework.pages.registration;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TokenRegistrationPage extends PageBase {

  private static final String URL = BASE_URL + "registration/token/{token}";

  private String url;

  @FindBy(id = "email")
  private WebElement emailAddressField;

  @FindBy(id = "register")
  private WebElement continueButton;

  @FindBy(id = "password")
  private WebElement passwordField;

  @FindBy(id = "password_confirm")
  private WebElement confirmPasswordField;

  @FindBy(id = "terms")
  private WebElement termsCheckbox;

  /**
   * Token registration Page object.
   *
   * @param token token
   */
  public TokenRegistrationPage(String token) {
    super();
    this.url = URL.replace("{token}", token);
  }

  // ********************************************Action*********************************************

  public void open() {
    openAndWait(url);
  }

  /**
   * Enter email address in email field.
   */
  public void enterEmailAddress(String email) {
    highlight(emailAddressField);

    emailAddressField.click();
    emailAddressField.clear();
    emailAddressField.sendKeys(email);

    Logger.trace("Email address was entered as: " + email);
  }

  /**
   * Click on continue button and wait for next page to load.
   */
  public void clickContinueButton() {
    highlight(continueButton);
    clickAndWait(continueButton);

    Logger.trace("Clicked Continue button");
  }

  /**
   * Enter password in the password field.
   */
  public void enterPassword() {
    highlight(passwordField);
    passwordField.click();
    passwordField.sendKeys(STANDARD_PASS);

    Logger.trace("Password was entered");
  }

  /**
   * Enter password in the confirm password field.
   */
  public void enterConfirmPassword() {
    highlight(confirmPasswordField);
    confirmPasswordField.click();
    confirmPasswordField.sendKeys(STANDARD_PASS);

    Logger.trace("Password was confirmed");
  }

  /**
   * Check if terms checkbox is selected and click.
   */
  public void setTermsCheckbox() {
    if (!termsCheckbox.isSelected()) {
      termsCheckbox.click();
      Logger.trace("Checked " + termsCheckbox + " box");
    } else {
      Logger.trace("Checkbox " + termsCheckbox + " already checked");
    }
  }
}
