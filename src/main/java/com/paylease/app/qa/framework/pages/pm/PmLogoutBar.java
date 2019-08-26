package com.paylease.app.qa.framework.pages.pm;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class PmLogoutBar extends PageBase {

  /** Click on Logout button. */
  public void clickLogoutButton() {
    WebElement logout = waitUntilElementIsClickable(By.id("logout"));

    highlight(logout);
    clickAndWait(logout);

    Logger.trace("Logout button clicked");
  }
}

