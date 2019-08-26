package com.paylease.app.qa.framework.components;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class ElasticSearch extends PageBase {

  /**
   * Wait for autocomplete dropdown & select correct item in autocomplete dropdown.
   *
   * @param searchTerm Term that is searched for in the dropdown
   */
  public void clickDropDown(String searchTerm) {
    WebElement element = waitUntilElementIsClickable((
        By.xpath("//a[.//span[contains(text(), '" + searchTerm + "' )]]")));

    Actions actions = new Actions(driver);
    actions.moveToElement(element);
    actions.click();
    actions.sendKeys(searchTerm);
    actions.build().perform();

    Logger.trace("Clicked on dropdown");
  }
}
