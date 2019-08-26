package com.paylease.app.qa.framework.pages.registration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomeOwnerRegistrationPage extends ResRegistrationPage {

  private static final String URL = BASE_URL + "registration/homeowner";

  @FindBy(id = "filter_strings")
  private WebElement searchFilter;

  @FindBy(css = "span.prop_address")
  private WebElement propertyNameAndAddress;

  @FindBy(className = "company_name")
  private WebElement pmName;

  // ********************************************Action*********************************************

  public void open() {
    openAndWait(URL);
  }

  /**
   * Search PM in the search field.
   *
   * @param pmName pmName
   */
  public void filterSearchPm(String pmName) {
    highlight(searchFilter);

    searchFilter.clear();
    searchFilter.click();
    searchFilter.sendKeys(pmName);
  }

  /**
   * Get property name and address.
   *
   * @return propertyNameAndAddress
   */
  public String getPropertyNameAndAddress() {
    highlight(propertyNameAndAddress);

    return propertyNameAndAddress.getText();
  }

  /**
   * Get PM name.
   *
   * @return pmName
   */
  public String getPmName() {
    highlight(pmName);

    return pmName.getText();
  }

}
