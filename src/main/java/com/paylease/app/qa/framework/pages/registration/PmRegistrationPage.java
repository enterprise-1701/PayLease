package com.paylease.app.qa.framework.pages.registration;

import com.github.javafaker.Faker;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

/**
 * Created by Mahfuz Alam on 09/26/2017.
 */

public class PmRegistrationPage extends PageBase {

  private static final String URL = BASE_URL + "registration/property_manager";

  private Faker faker;

  @FindBy(id = "company_name")
  private WebElement companyNameField;

  @FindBy(id = "company_type")
  private WebElement companyTypeSelect;

  @FindBy(id = "number_units")
  private WebElement noOfUnitsField;

  @FindBy(id = "fname")
  private WebElement firstNameField;

  @FindBy(id = "lname")
  private WebElement lastNameField;

  @FindBy(id = "phone")
  private WebElement phoneNumberField;

  @FindBy(id = "address")
  private WebElement streetAddressField;

  @FindBy(id = "city")
  private WebElement cityField;

  @FindBy(id = "zipcode")
  private WebElement zipField;

  @FindBy(id = "email")
  private WebElement emailField;

  @FindBy(id = "password")
  private WebElement passwordField;

  @FindBy(id = "confpass")
  private WebElement confirmPasswordField;

  @FindBy(id = "state")
  private WebElement chooseStateSelect;

  @FindBy(id = "submitBtn")
  private WebElement createAccountBtn;

  @FindBy(css = "#message h2")
  private WebElement registrationResponseMessage;

  /**
   * PM Registration Page object.
   */
  public PmRegistrationPage() {
    super();
    faker = new Faker();
  }
  // ********************************************Action*********************************************

  public void open() {
    openAndWait(URL);
  }

  /**
   * Fill the registration form and submit.
   *
   * @param email Email address to use as username
   */
  public void fillAndSubmit(String email) {
    enterEmail(email);
    enterCompanyName();
    enterFirstName();
    enterLastName();
    chooseCompanyType("Multi-Family");
    enterPhoneNumber("951-369-8358");
    enterNumberOfUnits("550");
    enterPassword();
    enterConfirmPassword();
    enterStreetAddress();
    enterCity();
    enterZip("98556");
    chooseState("CA");
    clickCreateAccount(true);

    verifySuccessMessage();
  }

  private void enterEmail(String email) {
    highlight(emailField);
    emailField.click();
    emailField.sendKeys(email);

    Logger.trace("Email entered as: " + email);
  }

  private void enterFirstName() {
    String firstName = faker.name().firstName();

    highlight(firstNameField);
    firstNameField.click();

    firstNameField.sendKeys(firstName);

    Logger.trace("First Name entered as: " + firstName);
  }

  private void enterLastName() {
    String lastName = faker.name().lastName();

    highlight(lastNameField);
    lastNameField.click();

    lastNameField.sendKeys(lastName);

    Logger.trace("Last Name entered as: " + lastName);
  }

  private void enterCompanyName() {
    String companyName = faker.company().name();

    highlight(companyNameField);
    companyNameField.click();

    companyNameField.sendKeys(companyName);

    Logger.trace("Company name entered as: " + companyName);
  }

  private void enterPhoneNumber(String phoneNumber) {
    highlight(phoneNumberField);
    phoneNumberField.click();
    phoneNumberField.sendKeys(phoneNumber);

    Logger.trace("Phone Number entered as: " + phoneNumber);
  }

  private void chooseCompanyType(String companyType) {
    highlight(companyTypeSelect);
    Select companyTypeSelectObj = new Select(companyTypeSelect);
    companyTypeSelectObj.selectByVisibleText(companyType);

    Logger.trace("Company Type chosen as: " + companyType);
  }

  private void enterNumberOfUnits(String numUnits) {
    highlight(noOfUnitsField);
    noOfUnitsField.click();
    noOfUnitsField.sendKeys(numUnits);

    Logger.trace("No. of units entered as: " + numUnits);
  }

  private void enterStreetAddress() {
    String streetAddress = faker.address().streetAddress();

    highlight(streetAddressField);
    streetAddressField.click();

    streetAddressField.sendKeys(streetAddress);

    Logger.trace("Street Address entered as: " + streetAddress);
  }

  private void enterPassword() {
    highlight(passwordField);
    passwordField.click();

    passwordField.sendKeys(STANDARD_PASS);

    Logger.trace("Standard password entered");
  }

  private void enterConfirmPassword() {
    highlight(confirmPasswordField);
    confirmPasswordField.click();
    confirmPasswordField.sendKeys(STANDARD_PASS);

    Logger.trace("Standard password confirmed");
  }

  private void enterCity() {
    String cityIn = faker.address().city();

    highlight(cityField);
    cityField.click();

    cityField.sendKeys(cityIn);

    Logger.trace("City entered as: " + cityIn);
  }

  private void chooseState(String stateCode) {
    highlight(chooseStateSelect);
    Select chooseStateSelectObj = new Select(chooseStateSelect);
    chooseStateSelectObj.selectByVisibleText(stateCode);

    Logger.trace("State chosen as: " + stateCode);
  }

  private void enterZip(String zipCode) {
    highlight(zipField);
    zipField.click();

    zipField.sendKeys(zipCode);

    Logger.trace("Zip Code entered as: " + zipCode);
  }

  private void clickCreateAccount(boolean wait) {
    highlight(createAccountBtn);
    if (wait) {
      clickAndWait(createAccountBtn);
    } else {
      createAccountBtn.click();
    }
    Logger.trace("Create Account button is clicked");
  }

  private void verifySuccessMessage() {
    highlight(registrationResponseMessage);
    String text = registrationResponseMessage.getText();
    Assert.assertEquals(text, "Registration Successful",
        "Registration of new PM should have succeeded");

    Logger.trace("Registration Successful message displayed");
  }
}
