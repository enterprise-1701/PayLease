package com.paylease.app.qa.framework.pages.admin;

import com.paylease.app.qa.framework.components.datatable.AdminFlexibleFeeCustomSettings.Columns;
import com.paylease.app.qa.framework.pages.PageBase;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

/**
 * Flexible Fee Form for both Add and Edit.
 */
public abstract class FlexibleFeeAddAndEdit extends PageBase {
  public enum FormFields {
    PAYMENT_TYPE(Columns.PAYMENT_TYPE),
    ACCOUNT_TYPE(Columns.ACCOUNT_TYPE),
    FEE_TYPE(Columns.FEE_TYPE),
    FEE_AMOUNT(Columns.FEE_AMOUNT),
    INCURRED_BY(Columns.INCURRED_BY),
    TIER_AMOUNT(Columns.TIER_AMOUNT),
    PAYMENT_FIELD(Columns.PAYMENT_FIELD),
    PROPERTY_NAME(Columns.PROPERTY_NAME);

    private final Columns column;

    FormFields(Columns column) {
      this.column = column;
    }

    public Columns getColumn() {
      return column;
    }
  }

  protected String url;

  protected String pmId;

  @FindBy(id = "create_custom_setting")
  protected WebElement form;

  @FindBy(id = "payment_type")
  protected WebElement paymentType;

  @FindBy(id = "account_type")
  protected WebElement accountType;

  @FindBy(id = "fee_type")
  protected WebElement feeType;

  @FindBy(id = "fee_amt")
  protected WebElement feeAmt;

  @FindBy(id = "incurred_by")
  protected WebElement incurredBy;

  @FindBy(id = "tier_amt")
  protected WebElement tierAmt;

  @FindBy(id = "payment_field")
  protected WebElement paymentField;

  @FindBy(id = "save_custom_setting")
  protected WebElement saveBtn;

  @FindBy(linkText = "Cancel")
  protected WebElement cancelBtn;



  /**
   * Open the page.
   */
  public void open() {
    openAndWait(url);
  }

  abstract public boolean pageIsLoaded();

  public boolean isErrorMessagePresent() {
    return isElementDisplayed(By.id("error_list"));
  }

  /**
   * Get the payment type value.
   *
   * @return the currently selected value
   */
  public String getPaymentTypeValue() {
    return getSelectedValue(paymentType);
  }

  /**
   * Get the payment type display value.
   *
   * @return the currently selected display value
   */
  public String getPaymentTypeDisplayValue() {
    return getSelectedDisplayValue(paymentType);
  }

  /**
   * Set the payment type value.
   *
   * @param value value to set
   */
  public void setPaymentTypeValue(String value) {
    setSelectedValue(paymentType, value);
  }

  /**
   * Get the account type value.
   *
   * @return the currently selected value
   */
  public String getAccountTypeValue() {
    return getSelectedValue(accountType);
  }

  /**
   * Get the account type display value.
   *
   * @return the currently selected display value
   */
  public String getAccountTypeDisplayValue() {
    return getSelectedDisplayValue(accountType);
  }

  /**
   * Set the account type value.
   *
   * @param value value to set
   */
  public void setAccountTypeValue(String value) {
    setSelectedValue(accountType, value);
  }

  /**
   * Get the fee type value.
   *
   * @return the currently selected value
   */
  public String getFeeTypeValue() {
    return getSelectedValue(feeType);
  }

  /**
   * Get the fee type display value.
   *
   * @return the currently selected display value
   */
  public String getFeeTypeDisplayValue() {
    return getSelectedDisplayValue(feeType);
  }

  /**
   * Set the fee type value.
   *
   * @param value value to set
   */
  public void setFeeTypeValue(String value) {
    setSelectedValue(feeType, value);
  }

  /**
   * Get the fee amount value.
   *
   * @return the value from the input box
   */
  public String getFeeAmount() {
    return feeAmt.getAttribute("value");
  }

  /**
   * Set the fee amount value.
   *
   * @param value value to set
   */
  public void setFeeAmount(String value) {
    setKeys(feeAmt, value);
  }

  /**
   * Get the incurred by value.
   *
   * @return the currently selected value
   */
  public String getIncurredByValue() {
    return getSelectedValue(incurredBy);
  }

  /**
   * Get the incurred by display value.
   *
   * @return the currently selected display value
   */
  public String getIncurredByDisplayValue() {
    return getSelectedDisplayValue(incurredBy);
  }

  /**
   * Set the incurred by value.
   *
   * @param value value to set
   */
  public void setIncurredByValue(String value) {
    setSelectedValue(incurredBy, value);
  }

  /**
   * Get the payment field value.
   *
   * @return the currently selected value
   */
  public String getPaymentFieldValue() {
    return getSelectedValue(paymentField);
  }

  /**
   * Get the payment field display value.
   *
   * @return the currently selected display value
   */
  public String getPaymentFieldDisplayValue() {
    return getSelectedDisplayValue(paymentField);
  }

  /**
   * Set the payment field value.
   *
   * @param value value to set
   */
  public void setPaymentFieldValue(String value) {
    setSelectedValue(paymentField, value);
  }

  /**
   * Get the tier amount value.
   *
   * @return the value from the input box
   */
  public String getTierAmount() {
    return tierAmt.getAttribute("value");
  }

  /**
   * Set the tier amount value.
   *
   * @param value value to set
   */
  public void setTierAmount(String value) {
    setKeys(tierAmt, value);
  }

  /**
   * Get a map of payment field options.
   *
   * @return map of payment field options
   */
  public HashMap<String, String> getPaymentFieldOptions() {
    HashMap<String, String> map = new HashMap<>();

    Select select = new Select(paymentField);

    for (WebElement option : select.getOptions()) {
      map.put(option.getAttribute("value"), option.getText());
    }

    return map;
  }

  /**
   * Get the form field value.
   *
   * @param fieldName the field name
   * @return the value
   */
  public String getField(FormFields fieldName) {
    switch (fieldName) {
      case PAYMENT_TYPE:
        return getPaymentTypeValue();
      case ACCOUNT_TYPE:
        return getAccountTypeValue();
      case FEE_TYPE:
        return getFeeTypeValue();
      case FEE_AMOUNT:
        return getFeeAmount();
      case INCURRED_BY:
        return getIncurredByValue();
      case TIER_AMOUNT:
        return getTierAmount();
      case PAYMENT_FIELD:
        return getPaymentFieldValue();
      default:
        return "";
    }
  }

  /**
   * Get the displayed value of the given form field.
   *
   * @param fieldName field name to get
   * @return displayed value
   */
  public String getFieldDisplay(FormFields fieldName) {
    switch (fieldName) {
      case PAYMENT_TYPE:
        return getPaymentTypeDisplayValue();
      case ACCOUNT_TYPE:
        return getAccountTypeDisplayValue();
      case FEE_TYPE:
        return getFeeTypeDisplayValue();
      case FEE_AMOUNT:
        return getFeeAmount();
      case INCURRED_BY:
        return getIncurredByDisplayValue();
      case TIER_AMOUNT:
        return getTierAmount();
      case PAYMENT_FIELD:
        return getPaymentFieldDisplayValue();
      default:
        return "";
    }
  }

  /**
   * Set the form field value.
   *
   * @param fieldName the field name
   * @param value the value
   */
  public void setField(FormFields fieldName, String value) {
    switch (fieldName) {
      case PAYMENT_TYPE:
        setPaymentTypeValue(value);
        break;
      case ACCOUNT_TYPE:
        setAccountTypeValue(value);
        break;
      case FEE_TYPE:
        setFeeTypeValue(value);
        break;
      case FEE_AMOUNT:
        setFeeAmount(value);
        break;
      case INCURRED_BY:
        setIncurredByValue(value);
        break;
      case TIER_AMOUNT:
        setTierAmount(value);
        break;
      case PAYMENT_FIELD:
        setPaymentFieldValue(value);
        break;
      default:
        // Do nothing
    }
  }

  /**
   * Is the properties table present on the page.
   *
   * @return true if the properties table is present
   */
  public boolean isPropertiesTablePresent() {
    return isElementPresentBySelector(By.id("properties"));
  }

  public void clickSaveNoWait() {
    saveBtn.click();
  }

  public void clickCancel() {
    clickAndWait(cancelBtn);
  }

  /**
   * Check to see if alert confirmation is present on the page.
   *
   * @return true if present
   */
  public boolean isAlertPresent() {
    try {
      wait.until(ExpectedConditions.alertIsPresent());

      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public void clickCancelOnConfirm() {
    Alert alert = wait.until(ExpectedConditions.alertIsPresent());
    alert.dismiss();
  }

  /**
   * Click OK on the confirmation and wait for the form to submit and new page to load.
   */
  public void clickSaveAndConfirm() {
    WebElement oldPage = driver.findElement(By.tagName("html"));

    saveBtn.click();

    Alert alert = wait.until(ExpectedConditions.alertIsPresent());
    alert.accept();

    waitForPageToLoad(oldPage);
  }

  /**
   * Get a map of all current form values as displayed.
   * The map is sorted by how the fields are presented on the form (left to right, top to bottom).
   *
   * @return map of current form display values
   */
  public SortedMap<String, String> getFormDisplayValues() {
    SortedMap<String, String> formValues = new TreeMap<>((o1, o2) -> {
      Map<String, Integer> sortedFormFields = new HashMap<>();
      sortedFormFields.put(FormFields.PAYMENT_TYPE.column.getLabel(), 1);
      sortedFormFields.put(FormFields.ACCOUNT_TYPE.column.getLabel(), 2);
      sortedFormFields.put(FormFields.FEE_TYPE.column.getLabel(), 3);
      sortedFormFields.put(FormFields.FEE_AMOUNT.column.getLabel(), 4);
      sortedFormFields.put(FormFields.INCURRED_BY.column.getLabel(), 5);
      sortedFormFields.put(FormFields.TIER_AMOUNT.column.getLabel(), 6);
      sortedFormFields.put(FormFields.PAYMENT_FIELD.column.getLabel(), 7);

      return sortedFormFields.get(o1).compareTo(sortedFormFields.get(o2));
    });

    for (FormFields field : FormFields.values()) {
      if (field == FormFields.PROPERTY_NAME) {
        continue;
      }

      formValues.put(field.column.getLabel(), getFieldDisplay(field));
    }

    return formValues;
  }

  private String getSelectedValue(WebElement selectElement) {
    Select select = new Select(selectElement);

    return select.getFirstSelectedOption().getAttribute("value");
  }

  private String getSelectedDisplayValue(WebElement selectElement) {
    Select select = new Select(selectElement);

    return select.getFirstSelectedOption().getText();
  }

  private void setSelectedValue(WebElement selectElement, String value) {
    Select select = new Select(selectElement);

    select.selectByValue(value);
  }
}