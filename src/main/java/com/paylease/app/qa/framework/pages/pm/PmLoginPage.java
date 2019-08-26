package com.paylease.app.qa.framework.pages.pm;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created by Mahfuz Alam on 09/29/2017.
 */
public class PmLoginPage extends PageBase {

  private static final String URL = BASE_URL + "login";

  @FindBy(id = "email")
  private WebElement emailAdd;

  @FindBy(id = "password")
  private WebElement password;

  @FindBy(id = "login")
  private WebElement loginBtn;

  // ********************************************Action*********************************************

  private void enterEmail(String email) {
    highlight(emailAdd);
    emailAdd.click();
    emailAdd.sendKeys(email);

    Logger.trace("Email entered as: " + email);
  }

  private void enterPassword() {
    highlight(password);
    password.click();
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

  /**
   * Fill login info in the PM login page.
   */
  public void login(String email) {
    enterEmail(email);
    enterPassword();
    clickLoginButton(true);
  }

  /**
   * Check if the logout button is present on the page.
   *
   * @return true if there is a link with the title "Logout"
   */
  public boolean isLogoutButtonPresent() {
    return !driver.findElements(By.cssSelector("a[title='Logout']")).isEmpty();
  }

  public boolean pageIsLoaded() {
    return isElementPresentBySelector(By.id("login_form")) && isElementPresentBySelector(By.id("login"));
  }

}
