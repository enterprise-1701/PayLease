package com.paylease.app.qa.framework.pages.pm;

import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.By;

public class PropertyLockPage extends PageBase {

  private static final String URL = BASE_URL + "pm/transactions/property_lock";

  // ********************************************Action*********************************************

  /**
   * Opens the page.
   */
  public void open() {
    openAndWait(URL);
  }

  public void selectProperty(String propertyId) {
    click(By.id("selected_" + propertyId));
  }

  public void clickPropertyLockSubmit() {
    click(By.id("confirmation_continue_btn"));
  }

  public void lockAllProperties() {
    click(By.id("lock_all_props_btn"));
  }

  public void lockProperty(String propertyId){
    selectProperty(propertyId);
    clickPropertyLockSubmit();

  }

  public String getLockMessage() {
    return driver.findElement(By.id("success_message")).getText();
  }

}
