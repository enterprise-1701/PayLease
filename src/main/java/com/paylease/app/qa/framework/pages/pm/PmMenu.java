package com.paylease.app.qa.framework.pages.pm;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PmMenu extends PageBase {

  @FindBy(id = "nav_payments")
  private WebElement paymentsTab;

  @FindBy(id = "subnav_dashboard")
  private WebElement accountOverview;

  public static final String PAYMENTS_DROPDOWN = "Payments dropdown";

  // ********************************************Action*********************************************

  private void clickPaymentsTab(boolean refundTutorialOptOut) {
    if (refundTutorialOptOut) {
      setCookieOptOutRefundTutorial();
    }
    highlight(paymentsTab);
    paymentsTab.click();

    Logger.trace("Payments tab clicked");
  }

  /** Click on One Time Payment Tab. */
  public void clickOneTimePaymentTab() {
    clickPaymentsTab(true);

    WebElement oneTimePayment = wait
        .until(ExpectedConditions.elementToBeClickable(By.id("subnav_one_time_payment")));

    highlight(oneTimePayment);
    clickAndWait(oneTimePayment);

    Logger.trace("One Time Payment tab clicked");
  }

  /** Click on Create Fixed AutoPay Tab. */
  public void clickCreateFixedAutoPayTab() {
    clickPaymentsTab(true);

    WebElement createFixedAutoPay = wait
        .until(ExpectedConditions.elementToBeClickable(By.id("subnav_fixed_autopay")));

    highlight(createFixedAutoPay);
    clickAndWait(createFixedAutoPay);

    Logger.trace("Create Fixed AutoPay tab clicked");
  }

  /** Click on Create Variable AutoPay Tab. */
  public void clickCreateVariableAutoPayTab() {
    clickPaymentsTab(true);

    WebElement createVariableAutoPay = wait
        .until(ExpectedConditions.elementToBeClickable(By.id("subnav_variable_autopay")));
    clickAndWait(createVariableAutoPay);

    Logger.trace("Create Variable AutoPay tab clicked");
  }

  public boolean isBillingTabDisplayed() {
    return isElementDisplayed(By.id("nav_billing"));
  }

  /**
   * Click Account Overview
   **/
  public void clickAccountOverview() {
    highlight(accountOverview);
    accountOverview.click();
    Logger.trace("account overview clicked");
  }

}

