package com.paylease.app.qa.framework.pages.admin;

import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

class ResetPasswordPage extends PageBase {

  private static final String URL = BASE_URL + "admin2.php?action=change_password";

  @FindBy(id = "old_password")
  private WebElement oldPassword;

  @FindBy(id = "new_password")
  private WebElement newPassword;

  @FindBy(id = "new_password2")
  private WebElement confirmPassword;

  @FindBy(css = "input[value='Update']")
  private WebElement updateBtn;

  void cycle(String password) {
    String prevPassword = password;
    for (int i = 0; i < 4; i++) {
      String tmpPassword = prevPassword + i;
      oldPassword.sendKeys(prevPassword);
      newPassword.sendKeys(tmpPassword);
      confirmPassword.sendKeys(tmpPassword);
      prevPassword = tmpPassword;
      clickAndWait(updateBtn);
    }
    oldPassword.sendKeys(prevPassword);
    newPassword.sendKeys(password);
    confirmPassword.sendKeys(password);
    clickAndWait(updateBtn);
  }
}
