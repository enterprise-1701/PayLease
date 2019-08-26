package com.paylease.app.qa.framework.pages.admin;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.pages.PageBase;
import com.paylease.app.qa.framework.pages.pm.ViewAutopays.Action;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AdminMenu extends PageBase {

  private static final String ADMIN_USER_FIRSTNAME = "quality";

  // ********************************************Action*********************************************

  public void clickLogoutButton() {
    WebElement adminWelcomeMessage = driver
        .findElement(By.linkText("Welcome " + ADMIN_USER_FIRSTNAME));
    Actions action = new Actions(driver);
    action.moveToElement(adminWelcomeMessage).build().perform();
    wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Logout")));
    clickAndWait(driver.findElement(By.linkText("Logout")));
  }
}

