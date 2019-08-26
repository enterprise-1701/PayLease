package com.paylease.app.qa.framework.pages.resident;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ResidentLogoutBar extends PageBase {

  /** Click on Logout button. */
  public void clickLogoutButton() {
    WebElement logout = waitUntilElementIsClickable(By.className("logout_btn"));

    highlight(logout);
    clickAndWait(logout);

    Logger.trace("Logout button clicked");
  }
}

