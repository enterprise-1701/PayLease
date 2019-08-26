package com.paylease.app.qa.framework.pages.pm;

import com.paylease.app.qa.framework.pages.PageBase;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public abstract class BillingAndUem extends PageBase {

  private static final String ALL_PROPS_CLASS_NAME = "ui-menu-item";
  /**
   * Search box that allows you to search for properties.
   */
  @FindBy(id = "property_id")
  private WebElement propSearch;
  /**
   * Drop down of suggested properties after entering a search term.
   */
  @FindBy(className = "ui-autocomplete")
  private WebElement suggestedProps;

  /**
   * Get the page url.
   *
   * @return String
   */
  public abstract String getUrl();

  // ********************************************Action*********************************************

  /**
   * Opens the page.
   */
  public void open() {
    openAndWaitForAjax(this.getUrl());
  }

  /**
   * Enter search term in the search bar.
   */
  private void enterPropertySearch(String searchName) {
    this.propSearch.click();
    WebElement searchResult;
    searchResult = wait.until(ExpectedConditions.presenceOfElementLocated(
        By.cssSelector("ul.ui-autocomplete li")));

    this.propSearch.sendKeys(searchName);
    wait.until(ExpectedConditions.stalenessOf(searchResult));
  }

  /**
   * Find a property with a search term.
   *
   * @return String
   */
  public String findProp(String searchName) {
    enterPropertySearch(searchName);
    WebElement suggestedProp = this.getElement();
    suggestedProp.click();
    return this.propSearch.getAttribute("value");
  }

  /**
   * Find multiple properties with a search term.
   *
   * @return List of strings
   */
  public ArrayList<String> findProps(String searchName) {
    enterPropertySearch(searchName);
    ArrayList<String> properties = new ArrayList<>();
    String location = this.getLocation();
    List<WebElement> props = driver.findElements(By.className(location));
    for (WebElement temp : props) {
      properties.add(temp.getText());
    }
    return properties;
  }

  /**
   * Returns a web element representing the search dropdown.
   *
   * @return WebElement
   */
  public WebElement getElement() {
    return suggestedProps;
  }

  /**
   * Returns the class name of the search box.
   *
   * @return String
   */
  private String getLocation() {
    return ALL_PROPS_CLASS_NAME;
  }
}
