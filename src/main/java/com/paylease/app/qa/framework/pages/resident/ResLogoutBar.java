package com.paylease.app.qa.framework.pages.resident;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ResLogoutBar extends PageBase {

  /**
   * Click on Logout button.
   *
   */
  public void clickLogoutButton() {
    WebElement logout = wait
        .until(ExpectedConditions.elementToBeClickable(By.id("logout")));

    highlight(logout);
    clickAndWait(logout);

    Logger.trace("Logout button clicked");
  }
}

