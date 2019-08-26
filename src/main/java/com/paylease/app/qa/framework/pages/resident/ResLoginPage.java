package com.paylease.app.qa.framework.pages.resident;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.pages.PageBase;
import com.paylease.app.qa.framework.pages.pm.PmHomePage;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Mahfuz Alam on 11/15/2017.
 */
public class ResLoginPage extends PageBase {

  private static final String URL = BASE_URL + "login/resident";

  private static final String RESIDENT_URL = BASE_URL + "resident";

  @FindBy(id = "email")
  private WebElement emailAdd;

  @FindBy(id = "password")
  private WebElement password;

  @FindBy(id = "login")
  private WebElement loginBtn;

  @FindBy(id = "paylease_logo")
  private WebElement payleaseLogo;

  @FindBy(id = "faq")
  private WebElement faqLink;

  @FindBy(css = ".forgot_link")
  private WebElement forgotPasswordLink;

  @FindBy(css = ".login_lnk")
  private WebElement createAccountLink;

  // ********************************************Action*********************************************

  private void enterEmail(String email) {
    highlight(emailAdd);
    emailAdd.click();
    emailAdd.sendKeys(email);

    Logger.trace("Email entered as: " + email);
  }

  private void enterPassword(String passwordValue) {
    highlight(password);
    password.click();

    if (passwordValue != null) {
      password.sendKeys(passwordValue);
    }

    password.sendKeys(STANDARD_PASS);

    Logger.trace("Password keyed in");
  }

  private void clickLoginButton(boolean wait) {
    highlight(loginBtn);
    if (wait) {
      clickAndWait(loginBtn);
    } else {
      loginBtn.click();
    }

    Logger.trace("Login Button clicked");
  }

  public void open() {
    openAndWait(URL);
  }

  public void openResidentPage() {
    openAndWait(RESIDENT_URL);
  }

  /**
   * Fill login info in the Res login page.
   */
  public ResHomePage login(String email, String password) {
    enterEmail(email);

    enterPassword(password);
    clickLoginButton(true);

    return new ResHomePage();
  }

  /**
   * Fill login info in the Res login page with the standard password.
   *
   * @param email email to login with
   * @return ResHomePage
   */
  public ResHomePage login(String email) {
    return login(email, null);
  }

  /**
   * Click on paylease logo.
   */
  public void clickPayLeaseLogo() {
    highlight(payleaseLogo);
    clickAndWait(payleaseLogo);

    Logger.trace("Clicked on PayLease logo");
  }

  /**
   * Click on faq link.
   */
  public void clickFaqLink() {
    highlight(faqLink);
    clickAndWait(faqLink);

    Logger.trace("Clicked on Faq link");
  }

  /**
   * Check if phone number is present and displayed.
   *
   * @return boolean boolean value
   */
  public boolean isPhoneNumberDisplayed() {
    return isElementDisplayed(
        By.id("phone_number"));
  }

  /**
   * Click on forgot password link.
   */
  public void clickForgotPasswordLink() {
    highlight(forgotPasswordLink);
    clickAndWait(forgotPasswordLink);

    Logger.trace("Clicked on forgot password link");
  }

  /**
   * Click on create account link.
   */
  public void clickCreateAccountLink() {
    highlight(createAccountLink);
    clickAndWait(createAccountLink);

    Logger.trace("Clicked on create account link");
  }

  /**
   * Fill pm login info in the Res login page.
   */
  public PmHomePage loginPm(String email) {
    enterEmail(email);
    enterPassword(null);
    clickLoginButton(true);

    return new PmHomePage();
  }

  /**
   * Get a list of error messages.
   *
   * @return error messages as a list
   */
  public List<String> getErrorMessages() {
    List<WebElement> errors = driver.findElements(By.cssSelector("label.error_server"));
    List<String> errorMessages = new ArrayList<>();

    for (WebElement error : errors) {
      errorMessages.add(error.getText());
    }

    return errorMessages;
  }

  /**
   * Verify User is on login page.
   */
  public boolean pageIsLoaded() {
    return isElementDisplayed(By.id("login"));
  }
}
