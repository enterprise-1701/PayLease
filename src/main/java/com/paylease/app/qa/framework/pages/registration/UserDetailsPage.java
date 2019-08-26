package com.paylease.app.qa.framework.pages.registration;

import com.github.javafaker.Faker;
import com.paylease.app.qa.framework.DataHelper;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class UserDetailsPage extends PageBase {

  private Faker faker;

  @FindBy(id = "fname")
  private WebElement firstNameField;

  @FindBy(id = "lname")
  private WebElement lastNameField;

  @FindBy(id = "email")
  private WebElement emailField;

  @FindBy(id = "phone")
  private WebElement phoneNumberField;

  @FindBy(id = "unit_number")
  private WebElement unitNumberField;

  @FindBy(id = "password")
  private WebElement passwordField;

  @FindBy(id = "confpass")
  private WebElement confirmPasswordField;

  @FindBy(name = "referrer")
  private WebElement referrerSelect;

  @FindBy(id = "terms")
  private WebElement termsCheckbox;

  @FindBy(css = "input.wid_auto")
  private WebElement createAccountButton;

  /**
   * User Details Page object.
   */
  UserDetailsPage() {
    super();
    faker = new Faker();
  }

  // ********************************************Action*********************************************

  /**
   * Fill the user details form.
   */
  public void fillForm() {
    enterFirstName();
    enterLastName();
    enterEmail();
    enterPhoneNumber();
    enterUnitNumber();
    enterPassword();
    enterConfirmPassword();
    selectReferrer();
    clickTermsCheckbox();
  }

  private void enterEmail() {
    DataHelper dataHelper = new DataHelper();

    String email = dataHelper.getUniqueEmail();

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

  private void enterPhoneNumber() {
    highlight(phoneNumberField);
    phoneNumberField.click();
    phoneNumberField.sendKeys("2035629767");

    Logger.trace("Phone Number entered as: 2035629767");
  }

  private void enterUnitNumber() {
    int unitNumber = faker.number().numberBetween(100, 999);

    highlight(unitNumberField);
    unitNumberField.click();
    unitNumberField.sendKeys(String.valueOf(unitNumber));

    Logger.trace("My unit number entered as: " + unitNumber);
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

  private void selectReferrer() {
    highlight(referrerSelect);

    Select selectReferrer = new Select(referrerSelect);

    selectReferrer.selectByValue("2");

    Logger.trace("Referrer is selected");
  }

  private void clickTermsCheckbox() {
    if (!termsCheckbox.isSelected()) {
      termsCheckbox.click();
      Logger.trace("Checked " + termsCheckbox + " box");
    } else {
      Logger.trace("Checkbox " + termsCheckbox + " already checked");
    }
  }

  /**
   * Click create account button.
   */
  public void clickCreateAccount() {
    highlight(createAccountButton);
    clickAndWait(createAccountButton);

    Logger.trace("Create Account button is clicked");
  }

  /**
   * Get first and last name from user details form
   *
   * @return fullName
   */
  public String getFullName() {

    String firstName = firstNameField.getAttribute("value");

    String lastName = lastNameField.getAttribute("value");

    return firstName + " " + lastName;
  }

}
