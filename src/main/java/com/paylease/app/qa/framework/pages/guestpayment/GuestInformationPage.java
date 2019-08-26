package com.paylease.app.qa.framework.pages.guestpayment;

import com.github.javafaker.Faker;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class GuestInformationPage extends PageBase {

  protected String firstName;
  protected String lastName;
  protected String pmId;

  private Faker faker;

  @FindBy(id = "guest_payment_header_message")
  private WebElement importantMessage;

  @FindBy(id = "guest_first_name")
  private WebElement firstNameBox;

  @FindBy(css = "label.error[for='guest_first_name']")
  private WebElement firstNameBoxErrorMessage;

  @FindBy(id = "guest_last_name")
  private WebElement lastNameBox;

  @FindBy(css = "label.error[for='guest_last_name']")
  private WebElement lastNameBoxErrorMessage;

  @FindBy(id = "guest_email")
  private WebElement guestEmailBox;

  @FindBy(css = "label.error[for='guest_email']")
  private WebElement guestEmailBoxErrorMessage;

  @FindBy(id = "submit")
  private WebElement continueButton;

  /** Guest Information Page object. */
  public GuestInformationPage() {
    this(null);
  }

  GuestInformationPage(String pmId) {
    super();
    this.pmId = pmId;
    faker = new Faker();
  }

  // ********************************************Action*********************************************

  /**
   * Check if navigation from old page has occurred and new page has loaded.
   *
   * @return true when Guest Information form and uest email box is present.
   */
  public boolean pageIsLoaded() {
    return isElementPresentBySelector(By.id("guest_payment"))
        && isElementPresentBySelector(By.id("guest_email"));
  }

  /**
   * Get important message from the page.
   *
   * @return important message
   */
  public String getImportantMessageInGuest() {
    highlight(importantMessage);

    return importantMessage.getText();
  }

  /**
   * Enter guest first name in the First name field.
   *
   * @return firstName
   */
  public String enterGuestFirstName() {
    highlight(firstNameBox);
    firstName = faker.name().firstName();

    firstNameBox.click();
    firstNameBox.clear();
    firstNameBox.sendKeys(firstName);

    Logger.trace("Guest First Name entered as: " + firstName);

    return firstName;
  }

  /**
   * Returns the first name value set by enterGuestFirstName.
   *
   * @return firstName
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * Enter guest last name in the last name field.
   *
   * @return lastName
   */
  public String enterGuestLastName() {
    highlight(lastNameBox);
    lastName = faker.name().lastName();

    lastNameBox.click();
    lastNameBox.clear();
    lastNameBox.sendKeys(lastName);

    Logger.trace("Guest Last Name entered as: " + lastName);

    return lastName;
  }

  /**
   * Returns the last name value set by enterGuestLastName.
   *
   * @return lastName
   */
  public String getLastName() {
    return lastName;
  }

  /** Enter guest email in the email field. */
  public void enterGuestEmail() {
    highlight(guestEmailBox);
    String emailAddress = faker.internet().emailAddress();

    guestEmailBox.click();
    guestEmailBox.clear();
    guestEmailBox.sendKeys(emailAddress);

    Logger.trace("Guest Email entered as: " + emailAddress);
  }

  /** Enter invalid email in the guest email field. */
  public void enterInvalidEmail() {
    highlight(guestEmailBox);
    String emailAddress = "abc@thjk";

    guestEmailBox.click();
    guestEmailBox.clear();
    guestEmailBox.sendKeys(emailAddress);

    Logger.trace("Guest Email entered as: " + emailAddress);
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
   * Get error message from email box field.
   *
   * @return emailBoxErrorMessage
   */
  public String getErrorEmailBox() {
    highlight(guestEmailBoxErrorMessage);

    return guestEmailBoxErrorMessage.getText();
  }

  /** Click on continue button and wait for next page to load. */
  public void clickContinueButton() {
    highlight(continueButton);
    clickAndWait(continueButton);

    Logger.trace("Clicked Continue button on Guest Information page");
  }

  /** Click on continue button to assert error messages. */
  public void clickContinueButtonToVerify() {
    highlight(continueButton);
    continueButton.click();

    Logger.trace("Clicked Continue button on Guest Information page");
  }

}
