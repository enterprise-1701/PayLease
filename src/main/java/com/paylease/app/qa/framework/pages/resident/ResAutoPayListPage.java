package com.paylease.app.qa.framework.pages.resident;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ResAutoPayListPage extends PageBase {

  private static final String URL = BASE_URL + "resident/autopay_list";

  /**
   * Resident autopay page object.
   */
  public ResAutoPayListPage() {
    super();
  }

  // ********************************************Action*********************************************

  public void open() {
    openAndWait(URL);
  }

  public boolean isCreateNewAutoPayButtonPresent() {
    return isElementPresentBySelector(By.name("button_add_autopay"));
  }

  /** Click create new autopay button. */
  public void clickCreateNewAutoPayButton() {
    WebElement createAutoPayButton = waitUntilElementIsClickable(By.name("button_add_autopay"));

    highlight(createAutoPayButton);
    clickAndWait(createAutoPayButton);

    Logger.trace("Create New AutoPay Button clicked");
  }

  public boolean isSuccessMessagePresent() {
    return isElementPresentBySelector(By.className("success_message"));
  }

  public boolean pageIsLoaded() {
    return getTitle().equals("AUTOPAY");
  }
}
