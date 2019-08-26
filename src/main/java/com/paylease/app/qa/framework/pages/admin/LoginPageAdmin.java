package com.paylease.app.qa.framework.pages.admin;

import com.paylease.app.qa.framework.AppConstant;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Mahfuz Alam on 09/26/2017.
 */

public class LoginPageAdmin extends PageBase {

  private static final String URL = BASE_URL + "admin_login.php";

  @FindBy(name = "user")
  private WebElement signInEmail;

  @FindBy(name = "password")
  private WebElement signInPass;

  @FindBy(name = "submit")
  private WebElement loginButton;

  // ********************************************Action*********************************************

  private void sendSignInEmail(String email) {
    highlight(signInEmail);
    signInEmail.click();
    signInEmail.sendKeys(email);

    Logger.trace("Sign in email entered as: " + email);
  }

  private void sendSignInPass(String pass) {
    highlight(signInPass);
    signInPass.clear();
    signInPass.click();
    signInPass.sendKeys(pass);

    Logger.trace("Sign in password entered");
  }

  private void clickLoginButton(boolean wait) {
    highlight(loginButton);
    if (wait) {
      clickAndWait(loginButton);
    } else {
      loginButton.click();
    }
    Logger.trace("Sign in is successful");
  }

  /**
   * Log in to Admin as standard QA Admin user.
   */
  public void login() {
    openAndWait(URL);
    sendSignInEmail(AppConstant.QA_ADMIN_USER);
    sendSignInPass(AppConstant.QA_ADMIN_PASSWORD);
    clickLoginButton(true);

    resetPasswordIfNecessary();
  }

  /**
   * This isn't going to work if two tests hit at the same time.
   */
  private void resetPasswordIfNecessary() {
    if (driver.getCurrentUrl().contains("action=change_password")) {
      ResetPasswordPage resetPasswordPage = new ResetPasswordPage();
      resetPasswordPage.cycle(AppConstant.QA_ADMIN_PASSWORD);
    }
  }
}
