package com.paylease.app.qa.framework.pages.paymentflow;

import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ReceiptPage extends PageBase {

  @FindBy(id = "print_receipt_button")
  private WebElement printReceiptButton;

  @FindBy(id = "check_front")
  private WebElement frontCheckImage;

  @FindBy(id = "check_back")
  private WebElement backCheckImage;

  // ********************************************Action*********************************************

  /**
   * The page is loaded when the success message appears.
   *
   * @return true if the success message is present
   */
  public boolean pageIsLoaded() {
    return !driver.findElements(By.id("transaction_date")).isEmpty();
  }

  public String getTransactionId() {
    return getTextBySelector(By.id("trans_id"));
  }

  /**
   * Get the success message from the page.
   *
   * @return success message
   */
  public String getSuccessMessage() {
    return getTextBySelector(By.className("success_message"));
  }

  /**
   * Get the resident name from the page.
   *
   * @return resident name
   */
  public String getResidentName() {
    return getTextBySelector(By.id("resident_name"));
  }

  /**
   * Get the payment made by from the page.
   *
   * @return payment made by
   */
  public String getPaymentMadeBy() {
    return getTextBySelector(By.id("payment_made_by"));
  }

  /**
   * Get the guest payment amount from the page.
   *
   * @return guest payment amount
   */
  public String guestPaymentAmount() {
    return getTextBySelector(By.cssSelector("#line_item_guest_payment .amount"));
  }

  /**
   * Get the lease payment amount from the page.
   *
   * @return lease payment amount
   */
  public String getLeasePaymentAmount() {
    return getTextBySelector(By.cssSelector("#line_item_lease_payment .amount"));
  }

  /**
   * Get the processing fee amount from the page.
   *
   * @return guest payment amount
   */
  public String getProcessingFeeAmount() {
    return getTextBySelector(By.cssSelector("#line_item_processing_fee .amount"));
  }

  /**
   * Get the total amount from the page.
   *
   * @return total amount
   */
  public String getTotalAmount() {
    return getTextBySelector(By.id("total_amount"));
  }

  /**
   * Get payment method from the page.
   *
   * @return payment method
   */
  public String getPaymentMethod() {
    return getTextBySelector(By.id("payment_method"));
  }

  /**
   * Get pm name from the page.
   *
   * @return pm name
   */
  public String getPmName() {
    return getTextBySelector(By.id("pm_name"));
  }

  public String getPaymentAmount(String paymentField) {
    return getTextBySelector(By.cssSelector("#line_item_" + paymentField + " .amount"));
  }

  public String getPaymentAmountLabel(String paymentField) {
    return getTextBySelector(By.cssSelector("#line_item_" + paymentField + " .bill_type"));
  }

  /**
   * Get unit number from the page.
   *
   * @return unit number
   */
  public String getUnitNumber() {
    return getTextBySelector(By.id("unit"));
  }

  public String getTransactionDate() {
    return getTextBySelector(By.id("transaction_date"));
  }

  public String getTransactionStatus() {
    return getTextBySelector(By.id("transaction_status"));
  }

  public String getAccountNumber() {
    return getTextBySelector(By.id("account_number"));
  }

  public String getPropertyAddress() {
    return getTextBySelector(By.id("property_address_info"));
  }

  public boolean isWelcomeTextPresent(String residentName) {
    return isElementPresentBySelector(By.linkText("Welcome: " + residentName));
  }

  public boolean isLogoutButtonPresent() {
    return isElementPresentBySelector(By.linkText("Logout"));
  }

  public boolean isHomeLinkPresent() {
    return isElementPresentBySelector(By.linkText("Return Home"));
  }

  public boolean isProcessingFeeTextPresent() {
    return isElementPresentBySelector(By.id("line_item_processing_fee"));
  }


  public boolean isFeeShown() {
    return isElementDisplayed(By.cssSelector("#line_item_processing_fee .amount"));
  }

  /**
   * Click the Print receipt button.
   */
  public void clickPrintReceiptButton() {
    highlight(printReceiptButton);
    printReceiptButton.click();
  }

  /**
   * Front check image is present.
   *
   * @return true if image element present
   */
  public boolean isFrontCheckImagePresent() {
    return isImagePresentBySelector(By.id("check_front"));
  }

  /**
   * Back check image is present.
   *
   * @return true if image element present
   */
  public boolean isBackCheckImagePresent() {
    return isImagePresentBySelector(By.id("check_back"));
  }

  /**
   * Get the src attribute of the front check image element.
   *
   * @return src of the image
   */
  public String getFrontCheckImageSrc() {
    return frontCheckImage.getAttribute("src");
  }

  /**
   * Get the src attribute of the back check image element.
   *
   * @return src of the image
   */
  public String getBackCheckImageSrc() {
    return backCheckImage.getAttribute("src");
  }
}