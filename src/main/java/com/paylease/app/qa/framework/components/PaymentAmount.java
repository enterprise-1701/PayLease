package com.paylease.app.qa.framework.components;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.pages.PageBase;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class PaymentAmount extends PageBase {

  private HashMap<String, String> paymentFieldsAndValues;

  public PaymentAmount() {
    getPaymentFieldsAndValuesAsMap();
  }

  // ********************************************Action*********************************************

  /**
   * Get the field webelement using given value.
   *
   * @param paymentField field we are looking for
   * @return webelement
   */
  public WebElement getPaymentFieldElement(String paymentField) {
    // match any element with a class name "pay_container", which has a child element with class
    // name "field_name", whose text begins with the payment field we're looking for
    String xPath =
        "//*[" + xPathMatchClassName("pay_container") + "]//*[" + xPathMatchClassName(
            "field_name") + " and starts-with(text(), '" + paymentField + "')]";
    WebElement payFieldLabel = driver.findElement(By.xpath(xPath));
    return payFieldLabel.findElement(By.xpath("./ancestor::*[" + xPathMatchClassName("pay_container") + "]"));
  }

  /**
   * Set the amount for a given payment field.
   *
   * @param paymentField Payment Field label to set
   * @param amount amount to set
   * @return true if setting clearing the payment amount field and setting a value was successful
   */
  public boolean setPaymentFieldAmount(String paymentField, String amount) {
    WebElement paymentFieldRow = getPaymentFieldElement(paymentField);
    highlight(paymentFieldRow);
    WebElement paymentFieldAmount = paymentFieldRow.findElement(By.tagName("input"));
    highlight(paymentFieldAmount);

    try {
      paymentFieldAmount.clear();
    } catch (Exception e) {
      Logger.error(
          "Unable to clear amount for payment field " + paymentField + ": " + e.getMessage());
      return false;
    }
    paymentFieldAmount.sendKeys(amount);

    getPaymentFieldsAndValuesAsMap();
    return true;
  }

  /**
   * Check if a payment field is present.
   *
   * @param paymentFieldLabel field label string
   * @return true or false
   */
  public boolean isPaymentFieldPresent(String paymentFieldLabel) {

    return paymentFieldsAndValues.containsKey(paymentFieldLabel);
  }

  /**
   * Get the value of the paymentfield.
   *
   * @param paymentFieldLabel field label string
   * @return String payment field value
   */
  public String getPaymentFieldValue(String paymentFieldLabel) {

    return paymentFieldsAndValues.get(paymentFieldLabel);
  }

  private void getPaymentFieldsAndValuesAsMap() {
    HashMap<String, String> paymentFieldsAndValues = new HashMap<>();
    List<WebElement> paymentFields = driver.findElements(By.className("field_name"));
    List<WebElement> paymentValues = driver.findElements(By.className("money_input"));

    for (int i = 0; i < paymentFields.size(); i++) {
      paymentFieldsAndValues.put(
          paymentFields.get(i).getText().replace(":", ""),
          paymentValues.get(i).getAttribute("value"));
    }
    this.paymentFieldsAndValues = paymentFieldsAndValues;
  }
}
