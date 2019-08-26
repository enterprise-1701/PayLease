package com.paylease.app.qa.framework.pages.resident;

import com.github.javafaker.Faker;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;


public class ResMyProfileNotificationPage extends PageBase {

  private static final String URL = BASE_URL + "resident/profile/edit/notification";

  private Faker faker;

  @FindBy(id = "pay_by_txt_pin")
  private WebElement payByTextPin;

  @FindBy(id = "text_check")
  private WebElement textMessageCheckbox;

  @FindBy(id = "pay_by_txt")
  private WebElement payByTextCheckbox;

  @FindBy(id = "pr_phone")
  private WebElement mobileNumber;

  @FindBy(id = "pay_by_txt_account")
  private WebElement paymentAccountDropdown;

  @FindBy(id = "pay_by_txt_field")
  private WebElement paymentFieldDropdown;

  @FindBy(id = "pay_by_txt_amount")
  private WebElement paymentAmountField;

  @FindBy(id = "edit_details_save_btn")
  private WebElement saveBtn;

  @FindBy(id = "pay_rem_suc")
  private WebElement successMessage;

  @FindBy(id = "pbt_terms_link")
  private WebElement termsAndConditionsLink;

  /**
   * Resident my profile notification page object.
   */
  public ResMyProfileNotificationPage() {
    super();
    faker = new Faker();
  }

  // ********************************************Action*********************************************

  public void open() {
    openAndWait(URL);
  }

  public boolean isPayByTextCheckboxPresent() {
    return !driver.findElements(By.id("pay_by_txt")).isEmpty();
  }

  public String getPinErrorMessage() {
    forceLastElementBlur();
    WebElement pinErrorMessage = driver.findElement(By.cssSelector("label[for='pay_by_txt_pin']"));
    return pinErrorMessage.getText();
  }

  public void enableTextMessage() {
    if (!textMessageCheckbox.isSelected()) {
      textMessageCheckbox.click();
    }
  }

  public void enablePayByText() {
    if (!payByTextCheckbox.isSelected()) {
      payByTextCheckbox.click();
    }
  }

  public void setPin(String pin) {
    highlight(payByTextPin);
    payByTextPin.clear();
    payByTextPin.sendKeys(pin);
    Logger.trace("Pin entered as: " + pin);
  }

  public void setMobileNumber(String mobileNumber) {
    highlight(this.mobileNumber);
    this.mobileNumber.click();
    this.mobileNumber.clear();
    this.mobileNumber.sendKeys(mobileNumber);
    Logger.trace("Mobile number entered as: " + mobileNumber);
  }

  /**
   * Select bank account from Payment Account dropdown.
   */
  public void selectPaymentAccountBank(String accountId) {
    highlight(paymentAccountDropdown);
    new Select(paymentAccountDropdown).selectByValue("1|" + accountId);
    Logger.trace("Selected Payment Account Bank: " + accountId);
  }

  /**
   * Select credit card from Payment Account dropdown.
   */
  public void selectPaymentAccountCc(String accountId) {
    highlight(paymentAccountDropdown);
    new Select(paymentAccountDropdown).selectByValue("2|" + accountId);
    Logger.trace("Selected Payment Account CC: " + accountId);
  }

  /**
   * Select the Payment Field dropdown from given id.
   */
  public void selectPaymentField(String paymentFieldVarName) {
    highlight(paymentFieldDropdown);
    new Select(paymentFieldDropdown).selectByValue(paymentFieldVarName);
    Logger.trace("Selected Payment Field: " + paymentFieldVarName);
  }

  public void setPaymentAmount(String amount) {
    highlight(paymentAmountField);
    paymentAmountField.clear();
    paymentAmountField.sendKeys(amount);
    Logger.trace("Payment Amount entered as: " + amount);
  }

  public void save() {
    save(true);
  }

  public void save(boolean wait) {
    highlight(saveBtn);
    if (wait) {
      clickAndWait(saveBtn);
    } else {
      saveBtn.click();
    }
    Logger.trace("Clicked on the save button");
  }

  public String getSuccessMessage() {
    return successMessage.getText();
  }

  public void disableTextMessages() {
    if (textMessageCheckbox.isSelected()) {
      textMessageCheckbox.click();
    }
  }

  public boolean isPayByTextCheckboxChecked() {
    return payByTextCheckbox.isSelected();
  }

  public String getMobileNumberErrorMessage() {
    forceLastElementBlur();
    WebElement mobileNumberLabel = driver.findElement(By.cssSelector("label[for='pr_phone']"));
    return mobileNumberLabel.getText();
  }

  public boolean isPaymentAccountDropdownPresent() {
    return !driver.findElements(By.id("pay_by_txt_account")).isEmpty();
  }

  public boolean isPaymentFieldDropdownPresent() {
    return !driver.findElements(By.id("pay_by_txt_field")).isEmpty();
  }

  public boolean isPaymentAmountFieldPresent() {
    return !driver.findElements(By.id("pay_by_txt_amount")).isEmpty();
  }

  public boolean isSelectablePaymentAccountBank(String accountId) {
    try {
      selectPaymentAccountBank(accountId);
    } catch (NoSuchElementException e) {
      return false;
    }

    return true;
  }

  public boolean isSelectablePaymentAccountCc(String accountId) {
    try {
      selectPaymentAccountCc(accountId);
    } catch (NoSuchElementException e) {
      return false;
    }

    return true;
  }

  public boolean isTermsAndConditionAvailable() {
    termsAndConditionsLink.click();
    driver.switchTo().activeElement();
    return !driver.findElements(By.id("dialog")).isEmpty();
  }

  public String getAmountErrorMessage() {
    save(false);
    WebElement amountErrorMessage = driver
        .findElement(By.cssSelector("label[for='pay_by_txt_amount']"));
    return amountErrorMessage.getText();
  }
}
