package com.paylease.app.qa.framework.pages.registration;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ResRegistrationPage extends PageBase {

  @FindBy(id = "search_string")
  private WebElement propertySearchField;

  @FindBy(id = "submit")
  private WebElement searchButton;

  @FindBy(css = "a.change_prop_lnk")
  private WebElement thisIsMyPropertyLink;

  // ********************************************Action*********************************************

  /**
   * Search property in the search field.
   *
   * @param propertyName valid property name
   */
  public void searchPropertyName(String propertyName) {
    highlight(propertySearchField);

    propertySearchField.clear();
    propertySearchField.click();
    propertySearchField.sendKeys(propertyName);

    highlight(searchButton);

    clickAndWait(searchButton);
  }

  /**
   * Click on This is my property link.
   */
  public UserDetailsPage clickThisIsMyPropertyLink() {
    highlight(thisIsMyPropertyLink);

    clickAndWait(thisIsMyPropertyLink);

    Logger.trace("Clicked on this is my property link");

    return new UserDetailsPage();
  }

}
