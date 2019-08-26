package com.paylease.app.qa.framework.pages.paymentflow;

import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ReviewAndSubmitPage extends PageBase {

  @FindBy(name = "previous")
  private WebElement previousButton;

  @FindBy(id = "submit_amount")
  private WebElement submitPaymentButton;

  @FindBy(css = "#payment_amount_head a.edit_btn")
  private WebElement paymentAmountEditButton;

  @FindBy(css = "#payment_details_head a.edit_btn")
  private WebElement paymentDetailsEditButton;

  // ********************************************Action*********************************************

  /**
   * Determine if page is loaded.
   *
   * @return true if the payment agreement element is present
   */
  public boolean pageIsLoaded() {
    return isElementPresentBySelector(By.cssSelector("div.payment_agree"));
  }

  /**
   * Get the Payment Agreement Message from the page.
   *
   * @return payment agreement message
   */
  public String getPaymentAgreementMessage() {
    return getTextBySelector(By.cssSelector("div.payment_agree"));
  }

  public boolean isFeeTextPresent() {
    return isElementPresentBySelector(By.id("processing_fee"));
  }

  public boolean isPaymentAmountTablePresent() {
    return isElementPresentBySelector(By.id("payment_amount_head"));
  }

  public String getPaymentAmount() {
    return getTextBySelector(By.className("pay_amount"));
  }

  public String getFeeAmount() {
    return getTextBySelector(By.className("fee_amount"));
  }

  public String getTotalAmount() {
    return getTextBySelector(By.cssSelector("#total_row .amount"));
  }

  public String getBankName() {
    return getTextBySelector(By.id("bank_name"));
  }

  public String getAccountNumberLastFour() {
    return getTextBySelector(By.id("account_number"));
  }

  public String getRoutingNumber() {
    return getTextBySelector(By.id("routing_number"));
  }

  public String getNameOnAccount() {
    return getTextBySelector(By.id("name_on_account"));
  }

  public String getCardType() {
    return getTextBySelector(By.id("cc_type"));
  }

  public String getCardNumberLastFour() {
    return getTextBySelector(By.id("cc_number_last_four"));
  }

  public String getNameOnCard() {
    return getTextBySelector(By.id("name_on_card"));
  }

  public String getPropertyName() {
    return getTextBySelector(By.id("property_or_pm_name"));
  }

  public String getPropertyAddress() {
    return getTextBySelector(By.id("property_address"));
  }

  public String getPropertyCity() {
    return getTextBySelector(By.id("property_city"));
  }

  public String getPropertyState() {
    return getTextBySelector(By.id("property_state"));
  }

  public String getPropertyZip() {
    return getTextBySelector(By.id("property_zip"));
  }

  public String getChargebackAndNsfDisclosureMessage() {
    return getTextBySelector(By.className("addl_terms"));
  }

  /**
   * Click payment amount edit button.
   */
  public void clickPaymentAmountEditButton() {
    highlight(paymentAmountEditButton);
    clickAndWait(paymentAmountEditButton);
  }

  /**
   * Click payment details edit button.
   */
  public void clickPaymentDetailsEditButton() {
    highlight(paymentDetailsEditButton);
    clickAndWait(paymentDetailsEditButton);
  }

  /**
   * Click previous button.
   */
  public void clickPreviousButton() {
    highlight(previousButton);
    clickAndWait(previousButton);
  }

  /**
   * Click submit button.
   */
  public void clickSubmitButton() {
    highlight(submitPaymentButton);
    clickAndWait(submitPaymentButton);
  }

  public String getAutopayId() {
    String url = driver.getCurrentUrl();
    return url.substring(url.indexOf("payment_review_and_submit/") + "payment_review_and_submit/".length());
  }
}
