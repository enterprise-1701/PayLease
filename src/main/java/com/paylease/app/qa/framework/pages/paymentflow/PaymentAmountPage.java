package com.paylease.app.qa.framework.pages.paymentflow;

import com.github.javafaker.Faker;
import com.paylease.app.qa.framework.DataHelper;
import com.paylease.app.qa.framework.components.PaymentAmount;
import com.paylease.app.qa.framework.pages.PageBase;
import com.paylease.app.qa.framework.pages.resident.ResHomePage;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PaymentAmountPage extends PageBase {

  private PaymentAmount paymentAmount = new PaymentAmount();
  private DataHelper dataHelper = new DataHelper();

  @FindBy(name = "submit_amount")
  private WebElement continueButton;

  // ********************************************Action*********************************************

  private WebElement getPaymentFieldElement(String paymentField) {
    return paymentAmount.getPaymentFieldElement(paymentField);
  }

  public String getPaymentFieldAmount(String paymentField) {
    return paymentAmount.getPaymentFieldValue(paymentField);
  }

  public void setPaymentFieldAmount(String paymentField, String amount) {
    paymentAmount.setPaymentFieldAmount(paymentField, amount);
  }

  /**
   * Get the Updated Date for the given payment field.
   *
   * @param paymentField Payment Field we want
   * @return Date string that follows "As of: " or an empty string if no date present
   */
  public String getPaymentFieldUpdated(String paymentField) {
    WebElement paymentFieldRow = getPaymentFieldElement(paymentField);
    List<WebElement> paymentAmountUpdatedElements = paymentFieldRow.findElements(
        By.className("payfield_update_date"));
    String updatedDate = "";
    if (paymentAmountUpdatedElements.isEmpty()) {
      return updatedDate;
    }

    WebElement paymentAmountUpdated = paymentAmountUpdatedElements.get(0);
    return paymentAmountUpdated.getText().replace("As of: ", "");
  }

  /**
   * Click on continue button and wait for next page to load.
   */
  public void clickContinueButton() {
    highlight(continueButton);
    clickAndWait(continueButton);
  }

  /**
   * Determine if page is loaded.
   *
   * @return true if the submit amount button is present
   */
  public boolean pageIsLoaded() {
    return !driver.findElements(By.name("submit_amount")).isEmpty()
        || !driver.findElements(By.className("in_page_err")).isEmpty();
  }

  /**
   * Check if the previous button is present on the page.
   *
   * @return true if there is a Button with the title "Previous"
   */
  public boolean isPreviousButtonPresent() {
    return !driver.findElements(By.linkText("PREVIOUS")).isEmpty();
  }

  /**
   * Fill in Payment Field with random amount.
   *
   * @param paymentFields Array of payment fields
   */
  public void fillAndSubmit(ArrayList<String> paymentFields) {
    for (String paymentField : paymentFields) {
      Faker faker = new Faker();
      int amount = faker.number().numberBetween(100, 1000);
      String amountString = Integer.toString(amount);
      if (!paymentField.equals("")) {
        setPaymentFieldAmount(paymentField, amountString);
      }
    }

    clickContinueButton();
  }

  /**
   * Fill and submit randomly generated payment amount.
   */
  public void fillAndSubmit() {
    setPaymentFieldAmount("Lease Payment", dataHelper.getAmount());
    clickContinueButton();
  }

  /**
   * Set payment amount and click on make payment button in Resident home page.
   */
  public void fillAndSubmitResHomePage() {
    setPaymentFieldAmount("Lease Payment", dataHelper.getAmount());

    ResHomePage resHomePage = new ResHomePage();
    resHomePage.clickMakeOneTimePayment(true);
  }

  /**
   * Set payment amounts on given payment fields and click on make payment button in Resident home
   * page.
   */
  public void fillAndSubmitResHomePage(ArrayList<String> paymentFields) {
    for (String paymentField : paymentFields) {
      Faker faker = new Faker();
      int amount = faker.number().numberBetween(100, 1000);
      String amountString = Integer.toString(amount);
      if (!paymentField.equals("")) {
        setPaymentFieldAmount(paymentField, amountString);
      }
    }

    ResHomePage resHomePage = new ResHomePage();
    resHomePage.clickMakeOneTimePayment(true);
  }

  /**
   * Get the error message displayed at the top of the page.
   *
   * @return Error message text
   */
  public String getErrorMessage() {
    WebElement errorMessage = driver.findElement(By.className("error_server"));
    return errorMessage.getText();
  }

  /**
   * Get the first warning message.
   *
   * @return warning messages when there is an existing autopay or a recently processed transaction.
   */
  public String getFirstWarningMessage() {
    return getTextBySelector(By.cssSelector(".warning_box_p"));
  }

  /**
   * Check for the presence of a warning message about payment not being accepted.
   *
   * @return true if the page content begins with this message
   */
  public boolean hasBlockedPaymentsWarning() {
    WebElement bodyContent = driver.findElement(By.className("bodycontent"));
    return bodyContent.getText().trim().startsWith("Payments are not being accepted at this time.");
  }

  /**
   * Find the Amount Owed payment amount info label.
   *
   * @return true if the amount info line is present and labeled Amount Owed
   */
  public boolean isAmountOwedPresent() {
    List<WebElement> amountInfo = driver.findElements(By.id("payment_amount_info_label"));
    return !amountInfo.isEmpty() && amountInfo.get(0).getText().trim().contains("Amount Owed");
  }

  /**
   * Get the Amount given in the Amount Info section.
   *
   * @return String text of the element, if present
   */
  public String getAmountInfoAmount() {
    List<WebElement> amountInfoAmount = driver.findElements(By.id("payment_amount_info_amount"));
    String amount = "";
    return amountInfoAmount.isEmpty() ? amount : amountInfoAmount.get(0).getText();
  }

  /**
   * Get the text from body.
   *
   * @return payment not accepted message
   */
  public String getPaymentNotAcceptedText() {
    return getTextBySelector(By.id("main_content_100"));
  }
}
