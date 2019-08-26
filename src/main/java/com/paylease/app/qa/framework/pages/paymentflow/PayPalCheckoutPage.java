package com.paylease.app.qa.framework.pages.paymentflow;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class PayPalCheckoutPage extends PageBase {

  private static final String PAYPAL_EMAIL = "paylease-buyer1@paylease.com";
  private static final String PAYPAL_PASSWORD = "paylease";

  // ********************************************Action*********************************************

  /** Fill and submit PayPal payment details. */
  public void fillAndSubmitPayPalPaymentDetails() {

    Object[] handles;

    handles = driver.getWindowHandles().toArray();

    if (handles.length == 2) {
      driver.switchTo().window(handles[1].toString());
    }

    setPayPalEmail();
    clickNextButton();
    setPayPalPassword();
    clickLoginButton();
    clickContinueButton();

    driver.switchTo().window(handles[0].toString());
  }

  private void setPayPalEmail() {
    WebElement payPalEmail = fluentWaitForElementToBeClickable(By.id("email"));

    highlight(payPalEmail);
    setKeys(payPalEmail, PAYPAL_EMAIL);

    Logger.trace("PayPal email set");
  }

  private void setPayPalPassword() {
    WebElement payPalPassword = fluentWaitForElementToBeClickable(By.id("password"));

    highlight(payPalPassword);
    setKeys(payPalPassword, PAYPAL_PASSWORD);

    Logger.trace("PayPal Password set");
  }

  private void clickLogin() {
    WebElement loginButton = waitUntilElementIsClickable(By.cssSelector("#loginSection a"));
    delayFor(5000);
    highlight(loginButton);
    clickAndWait(loginButton);

    Logger.trace("Clicked on PayPal Login button");
  }

  private void clickLoginButton() {
    WebElement loginButton = fluentWaitForElementToBeClickable(By.id("btnLogin"));
    delayFor(5000);

    highlight(loginButton);
    loginButton.click();
    delayFor(10000);

    Logger.trace("Clicked on PayPal Login button");
  }

  private void clickContinueButton() {
    WebElement continueButton = fluentWaitForElementToBeClickable(By.id("confirmButtonTop"));
    delayFor(5000);

    highlight(continueButton);
    continueButton.click();

    Logger.trace("Clicked on PayPal Continue button");
  }

  private void clickNextButton() {
    WebElement nextButton = fluentWaitForElementToBeClickable(By.id("btnNext"));

    highlight(nextButton);
    nextButton.click();

    Logger.trace("Clicked on next button");
  }
}
