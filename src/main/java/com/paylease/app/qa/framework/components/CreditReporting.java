package com.paylease.app.qa.framework.components;

import com.github.javafaker.Faker;
import com.paylease.app.qa.framework.DataHelper;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.pages.PageBase;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class CreditReporting extends PageBase {

  private Faker faker;
  private static final String STATE = "CA";

  @FindBy(id = "first_name")
  private WebElement firstNameField;

  @FindBy(id = "last_name")
  private WebElement lastNameField;

  @FindBy(id = "ssn")
  private WebElement ssnField;

  @FindBy(id = "ssn_confirm")
  private WebElement ssnConfirmField;

  @FindBy(id = "address")
  private WebElement payerAddressField;

  @FindBy(id = "city")
  private WebElement payerCityField;

  @FindBy(id = "state")
  private WebElement payerState;

  @FindBy(id = "zip")
  private WebElement payerZipField;

  @FindBy(id = "birth_date")
  private WebElement dobBox;

  @FindBy(id = "lease_end_date")
  private WebElement leaseEndDate;

  /**
   * Credit Reporting Page object.
   */
  public CreditReporting() {
    super();
    faker = new Faker();
  }

  // ********************************************Action*********************************************

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

  private void setSsn(String ssn) {
    Logger.trace("Set SSN as " + ssn);

    highlight(ssnField);
    ssnField.clear();
    ssnField.sendKeys(ssn);
  }

  private void setConfirmSsn(String ssn) {
    Logger.trace("Set confirm SSN as " + ssn);

    highlight(ssnConfirmField);
    ssnConfirmField.clear();
    ssnConfirmField.sendKeys(ssn);
  }

  private void setAndConfirmSsn(String ssn) {
    setSsn(ssn);

    setConfirmSsn(ssn);
  }

  private void enterStreetAddress() {
    String streetAddress = faker.address().streetAddress();

    highlight(payerAddressField);
    payerAddressField.click();

    payerAddressField.sendKeys(streetAddress);

    Logger.trace("Billing Address entered as: " + streetAddress);
  }

  private void enterCity() {
    String cityIn = faker.address().city();

    highlight(payerCityField);
    payerCityField.click();

    payerCityField.sendKeys(cityIn);

    Logger.trace("City entered as: " + cityIn);
  }

  private void selectState(String state) {
    highlight(payerState);
    Select chooseStateSelectObj = new Select(payerState);
    chooseStateSelectObj.selectByVisibleText(state);

    Logger.trace("State selected: " + state);
  }

  private void setZipCode() {
    String zipCode = faker.address().zipCode().substring(0,5);

    highlight(payerZipField);
    payerZipField.click();
    payerZipField.clear();
    payerZipField.sendKeys(zipCode);

    Logger.trace("Zip Code entered as: " + zipCode);
  }

  private String generateSsn() {
    DataHelper dataHelper = new DataHelper();

    return Integer.toString(dataHelper.getAccountNumber());
  }

  private void enterDob() throws ParseException {
    SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

    Date date1 = format.parse("01/01/1952");
    Date date2 = format.parse("01/01/1995");

    String dateToSet = format.format(faker.date().between(date1, date2));

    highlight(dobBox);
    dobBox.click();

    setDateFieldValue("birth_date", dateToSet);
  }

  private void enterLeaseEndDate() {
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.DATE, 15);
    SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

    String dateToSet = format.format(cal.getTime());

    highlight(dobBox);
    dobBox.click();

    setDateFieldValue("lease_end_date", dateToSet);
  }

  /**
   * Fill credit reporting form.
   *
   * @throws ParseException when date exception occurs.
   */
  public void fillCreditReportingForm() throws ParseException {
    enterFirstName();
    enterLastName();
    setAndConfirmSsn(generateSsn());
    enterDob();
    enterStreetAddress();
    enterCity();
    selectState(STATE);
    setZipCode();
    enterLeaseEndDate();
  }

  /**
   * Get learn more text.
   */
  public String getLearnMoreText() {
    return getTextBySelector(By.className("opt_in_action_tbl"));
  }

  /**
   * Check if credit reporting checkbox is selected and click.
   */
  public void setCreditReportingCheckbox() {
    WebElement crCheckbox = driver.findElement(By.id("cr_opt_in"));

    if (!crCheckbox.isSelected()) {
      crCheckbox.click();
      Logger.trace("Checked " + crCheckbox + " box");
    } else {
      Logger.trace("Checkbox " + crCheckbox + " already checked");
    }
  }

  /**
   * Check if Optin container is present or not.
   *
   * @return true when optin container is present.
   */
  public boolean isOptinContainerPresent() {
    return isElementPresentBySelector(By.id("credit_reporting_optin_container"));
  }
}
