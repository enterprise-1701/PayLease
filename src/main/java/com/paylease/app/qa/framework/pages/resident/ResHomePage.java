package com.paylease.app.qa.framework.pages.resident;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.components.datatable.ResidentRecentPaymentHistory;
import com.paylease.app.qa.framework.components.datatable.ResidentRecentPaymentHistory.RecentPaymentHistory;
import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Mahfuz Alam on 11/15/2017.
 */
public class ResHomePage extends PageBase {

  private static final String URL = BASE_URL + "resident/dashboard";

  public static final String CREATE_AUTOPAY_LINK = "Create autopay link";
  public static final String GET_STARTED_AUTOPAY_LINK = "Get started autopay link";

  @FindBy(name = "submit_amount_home")
  private WebElement makeOneTimePayment;

  @FindBy(name = "lease_payment")
  private WebElement leasePayment;

  @FindBy(className = "ap_lnk")
  private WebElement getStarted;

  @FindBy(id = "recent_payment_history")
  private WebElement recentPaymentHistory;

  @FindBy(linkText = "Click here to set up a new AutoPay")
  private WebElement setUpNewAutopayLink;

  @FindBy(className = "no_popup")
  private WebElement helpTab;

  @FindBy(className = "logout_btn")
  private WebElement logOutButton;

  // ********************************************Action*********************************************

  /** Get property name.*/
  public String getPropertyName() {
    return getTextBySelector(By.id("header_property_name"));
  }

  /** Get property management name. */
  public String getPropertyManagementName() {
    return getTextBySelector(By.id("header_pm_company"));
  }

  /** Get welcome message text. */
  public String getWelcomeMessage() {
    return getTextBySelector(By.className("welcome_mess"));
  }

  /** Get success message text. */
  public String getSuccessMessage() {
    return getTextBySelector(By.className("success_message"));
  }

  public void open() {
    openAndWait(URL);
  }

  /**
   * Click on make one time payment button.
   *
   * @param wait for next page to load
   */
  public void clickMakeOneTimePayment(boolean wait) {
    highlight(makeOneTimePayment);
    if (wait) {
      clickAndWait(makeOneTimePayment);
    } else {
      makeOneTimePayment.click();
    }

    Logger.trace("Clicked make one time payment button on home page");
  }

  /**
   * Check if navigation from old page has occurred and new page has loaded.
   *
   * @return true when page title is correct and table is present
   */
  public boolean pageIsLoaded() {
    return getTitle().equals("HOME")
        && isElementPresentBySelector(By.id("make_payment"));
  }

  /**
   * Click Get started button for automatic payment setup.
   */
  public void clickGetStartedAutoPayButton() {
    clickAndWait(getStarted);

    Logger.trace("Clicked get started button on home page for autopay");
  }

  /**
   * Check if amount exist in Active Autopays table.
   *
   * @param amount to search for
   * @return true if amount exists in table
   */
  public boolean isAmountPresent(String amount) {
    return isElementPresentBySelector(By.xpath(
        "//*[@id='active_autopays']//*[contains(text(),\"" + amount + "\")]"));
  }

  /**
   * Click Setup New Autopay link.
   */
  public void clickSetUpNewAutopay() {
    clickAndWait(setUpNewAutopayLink);
  }

  /**
   * Click Logout button.
   */
  public void clickLogoutButton() {
    clickAndWait(logOutButton);
  }

  /**
   * Get error message from payment label.
   *
   * @return error message for payment field label
   */
  public String getPaymentFieldErrorMessage() {
    return getTextBySelector(By.cssSelector("label.error"));
  }

  /**
   * Get the payment disabled error message on Make A Payment box.
   *
   * @return String
   */
  public String getPaymentsDisabledErrorMessage() {
    return getTextBySelector(By.id("error_payments_disabled"));
  }

  /**
   * Get error message from Make Payment Now message box.
   *
   * @return message for Make Payment Now Messsage Box
   */
  public String getMakePaymentNowMessage() {
    return getTextBySelector(By.id("make_payment"));
  }

  /**
   * Check if transaction is present.
   *
   * @param transaction id to search for
   * @return True or False
   */
  public Boolean isTransactionPresent(String transaction) {
    ResidentRecentPaymentHistory recentPayments;

    recentPayments = new ResidentRecentPaymentHistory(recentPaymentHistory);

    return recentPayments.isStringPresent(transaction, RecentPaymentHistory.TRANS_NUMBER.getLabel());
  }

  /**
   * Click on Manage Accounts link.
   *
   */
  public void clickManageAccountsLink() {
    clickAndWait(driver.findElement(By.id("res_manage_accounts_link")));
  }

  public boolean isManageAccountsTablePresent() {
    return isElementDisplayed(By.id("manage_accounts_list_tbl"));
  }

  public void clickLogAsLinkAccount(String linkId) {
    clickAndWait(driver.findElement(By.id("logas_"+linkId)));
  }

}