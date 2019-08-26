package com.paylease.app.qa.framework.pages.guestpayment;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created by Mahfuz Alam on 11/08/2017.
 */
public class ResidentInformationPage extends PageBase {

  private static final String URL = BASE_URL + "guest_payment/pm/{pmId}";

  protected String firstName;
  protected String lastName;
  protected String pmId;

  @FindBy(id = "guest_payment_header_message")
  private WebElement importantMessage;

  @FindBy(id = "select2-prop_id-container")
  private WebElement propertyNameBox;

  @FindBy(css = "label.error[for='prop_id']")
  private WebElement propertyNameBoxErrorMessage;

  @FindBy(id = "res_first_name")
  private WebElement firstNameBox;

  @FindBy(css = "label.error[for='res_first_name']")
  private WebElement firstNameBoxErrorMessage;

  @FindBy(id = "res_last_name")
  private WebElement lastNameBox;

  @FindBy(css = "label.error[for='res_last_name']")
  private WebElement lastNameBoxErrorMessage;

  @FindBy(id = "res_dob")
  private WebElement residentDobBox;

  @FindBy(css = "label.error[for='res_dob']")
  private WebElement dobBoxErrorMessage;

  @FindBy(id = "submit")
  private WebElement continueButton;

  @FindBy(className = "error_server")
  private WebElement errorMessage;

  /**
   * Resident Information Page object.
   *
   * @param pmId pmId
   */
  public ResidentInformationPage(String pmId) {
    super();
    this.pmId = pmId;
  }

  // ********************************************Action*********************************************

  public void open() {
    String url = URL.replace("{pmId}", pmId);
    openAndWait(url);
  }

  /**
   * Get important message from the page.
   *
   * @return important message
   */
  public String importantMessageInResident() {
    highlight(importantMessage);

    return importantMessage.getText();
  }

  private void performSearch(String value) {
    highlight(propertyNameBox);

    Actions actions = new Actions(driver);
    actions.moveToElement(propertyNameBox);
    actions.click();
    actions.sendKeys(value);
    actions.build().perform();
    waitForAjax();

    Logger.trace("Property search letters entered as: " + value);
  }

  private WebElement propertySearch(String value) {
    performSearch(value);

    return driver.findElement(
        By.xpath("id('select2-prop_id-results')//span[contains(text(),\"" + value + "\")]"));
  }

  /**
   * Enter property name in the property search field.
   */
  public void enterPropertyName(String pName) {
    propertySearch(pName).click();

    Logger.trace("Property Name entered as: " + pName);
  }

  public String searchProperty(String search) {

    return propertySearch(search).getText();
  }

  /**
   * Enter property name in the property search field.
   *
   * @return boolean
   */
  public boolean isPropertyPresent(String propertyName, String propIdNo) {
    performSearch(propertyName);

    return !driver.findElements(By.id("propId_" + propIdNo)).isEmpty();
  }

  /**
   * Enter resident first name in the first name field.
   */
  public void enterResidentFirstName(String fName) {
    firstName = fName;

    highlight(firstNameBox);
    firstNameBox.click();
    firstNameBox.clear();
    firstNameBox.sendKeys(fName);

    Logger.trace("Resident First Name entered as: " + fName);
  }

  /**
   * returns the first name value set by enterResidentFirstName
   *
   * @return firstName
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * Enter resident last name in the last name field.
   */
  public void enterResidentLastName(String lName) {
    lastName = lName;

    highlight(lastNameBox);
    lastNameBox.click();
    lastNameBox.clear();
    lastNameBox.sendKeys(lName);

    Logger.trace("Resident Last Name entered as: " + lName);
  }

  /**
   * returns the first name value set by enterResidentLastName
   *
   * @return lastName
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * Enter resident dob in the dob field.
   */
  public void enterResidentDob(String year, String month, String date) {
    String dateToSet = month + "/" + date + "/" + year;

    highlight(residentDobBox);
    residentDobBox.click();

    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("document.getElementById('res_dob').value = '" + dateToSet + "'");
  }

  /**
   * Get error message from property box field.
   *
   * @return propertyNameBoxErrorMessage
   */
  public String getErrorPropertyBox() {
    highlight(propertyNameBoxErrorMessage);

    return propertyNameBoxErrorMessage.getText();
  }

  /**
   * Get error message from First Name box field.
   *
   * @return firstNameBoxErrorMessage
   */
  public String getErrorFirstNameBox() {
    highlight(firstNameBoxErrorMessage);

    return firstNameBoxErrorMessage.getText();
  }

  /**
   * Get error message from Last Name box field.
   *
   * @return lastNameBoxErrorMessage
   */
  public String getErrorLastNameBox() {
    highlight(lastNameBoxErrorMessage);

    return lastNameBoxErrorMessage.getText();
  }

  /**
   * Get error message from dob box field.
   *
   * @return dobBoxErrorMessage
   */
  public String getErrorDobBox() {
    highlight(dobBoxErrorMessage);

    return dobBoxErrorMessage.getText();
  }

  /**
   * Click on continue button and wait ajax process to complete.
   *
   * @return GuestInformationPage
   */
  public GuestInformationPage clickContinueButton() {
    highlight(continueButton);
    clickAndWaitForAjax(continueButton);

    Logger.trace("Clicked Continue button on Resident Information page");

    return new GuestInformationPage(pmId);
  }

  /**
   * Click on continue button to assert error messages.
   */
  public void clickContinueButtonToVerify() {
    highlight(continueButton);
    continueButton.click();

    Logger.trace("Clicked Continue button on Resident Information page");
  }

  /**
   * Get error message.
   *
   * @return errorMessage
   */
  public String getErrorMessage() {
    highlight(errorMessage);

    wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("error_server")));

    String errorMsg = errorMessage.getText();

    Logger.trace("Error message displayed is: " + errorMsg);

    return errorMsg;
  }

}
