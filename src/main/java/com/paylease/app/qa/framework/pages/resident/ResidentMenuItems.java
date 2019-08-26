package com.paylease.app.qa.framework.pages.resident;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ResidentMenuItems extends PageBase {

  public static final String AUTOPAY_TAB = "Autopay tab";

  // ********************************************Action*********************************************

  /** Click on invoices tab. */
  public void clickInvoicesTab() {
    WebElement invoicesTab = driver.findElement(By.id("invoice_tab"));

    highlight(invoicesTab);
    clickAndWait(invoicesTab);

    Logger.trace("Clicked on invoices tab");
  }

  /** Click on manage accounts tab. */
  public void clickManageAccountsTab() {
    WebElement manageAccountsTab = driver.findElement(By.id("manage_accounts_tab"));

    highlight(manageAccountsTab);
    clickAndWait(manageAccountsTab);

    Logger.trace("Clicked on manage accounts tab");
  }

  /** Click on my profile tab. */
  public void clickMyProfile() {
    WebElement myProfileTab = driver.findElement(By.id("profile_tab"));

    highlight(myProfileTab);
    clickAndWait(myProfileTab);

    Logger.trace("Clicked on my profile tab");
  }

  /**
   * Checks if profile tab exists.
   *
   * @return true if profile tab exist
   */
  public boolean isMyProfileTabPresent() {
    return isElementPresentBySelector(By.id("profile_tab"));
  }

  /** Click on billing tab. */
  public void clickBilling() {
    WebElement billingTab = driver.findElement(By.id("billing_tab"));

    highlight(billingTab);
    clickAndWait(billingTab);

    Logger.trace("Clicked on billing tab");
  }

  /** Click Autopay tab. */
  public void clickAutopayTab() {
    WebElement autopayTab = driver.findElement(By.id("autopay_tab"));

    highlight(autopayTab);
    clickAndWait(autopayTab);

    Logger.trace("Clicked autopay tab");
  }

  /** Click Make Payment tab. */
  public void clickMakePaymentTab() {
    WebElement makePaymentTab = driver.findElement(By.id("make_payment_tab"));

    highlight(makePaymentTab);
    clickAndWait(makePaymentTab);

    Logger.trace("Clicked make payment tab");
  }

  /** Click Payment History tab. */
  public void clickPaymentHistoryTab() {
    WebElement paymentHistoryTab = driver.findElement(By.id("payment_history_tab"));

    highlight(paymentHistoryTab);
    clickAndWait(paymentHistoryTab);

    Logger.trace("Clicked payment history tab");
  }

  /** Click Payment Methods tab. */
  public void clickPaymentMethodsTab() {
    WebElement paymentMethodsTab = driver.findElement(By.id("payment_methods_tab"));

    highlight(paymentMethodsTab);
    clickAndWait(paymentMethodsTab);

    Logger.trace("Clicked payment methods tab");
  }
}
