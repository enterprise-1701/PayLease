package com.paylease.app.qa.framework.pages.paymentflow;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.pages.PageBase;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PaymentMethodPage extends PageBase {

  public static final String NEW_BANK = "NewBank";
  public static final String NEW_CREDIT = "NewCredit";
  public static final String NEW_DEBIT = "NewDebit";
  public static final String PAYPAL = "PayPal";

  private String transactionId;

  @FindBy(name = "submit_exist_acct")
  private WebElement continueButton;

  /**
   * Payment Method Page object.
   */
  public PaymentMethodPage() {
    super();
    this.transactionId = parseTransactionId();
  }

  // ********************************************Action*********************************************

  private String parseTransactionId() {
    String pageUrl = driver.getCurrentUrl();

    return pageUrl.substring(pageUrl.lastIndexOf("/") + 1);
  }

  public String getTransactionId() {
    return transactionId;
  }

  /**
   * Select payment method from various ones.
   *
   * @param method Row to find
   */
  public void selectPaymentMethod(String method) {
    Logger.trace("Selected payment method is: " + method);

    String radioValue = getRadioValue(method);

    Logger.trace("Radio button value to select: " + radioValue);

    driver.findElement(By.cssSelector("input[value$='" + radioValue + "'][name='acct']")).click();
  }

  private String getRadioValue(String method) {
    String radioValue;
    switch (method) {
      case NEW_BANK:
        radioValue = "ach|new";
        break;
      case NEW_CREDIT:
        radioValue = "cc|new";
        break;
      case NEW_DEBIT:
        radioValue = "dc|new";
        break;
      case PAYPAL:
        radioValue = "paypal|new";
        break;
      default:
        radioValue = method;
    }
    return radioValue;
  }

  /**
   * Get the standard fee amount text for the given payment method row.
   *
   * @param method Row to find
   * @return Text of standard fee column
   */
  public String getFeeAmount(String method) {
    String idValue = getIdValue(method);

    Logger.trace("ID value is: " + idValue);
    return getTextBySelector(By.cssSelector("tr[id$='" + idValue + "'] .fee_amount_basic"));
  }

  private String getIdValue(String method) {
    String idValue;
    switch (method) {
      case NEW_BANK:
        idValue = "banew";
        break;
      case NEW_CREDIT:
        idValue = "ccnew";
        break;
      case NEW_DEBIT:
        idValue = "dcnew";
        break;
      default:
        idValue = method;
    }
    return idValue;
  }

  /**
   * Click on continue button and wait for next page to load.
   *
   */
  public void clickContinueButton() {
    highlight(continueButton);
    clickAndWait(continueButton);

    Logger.trace("Clicked Continue button on Payment Method page");
  }

  /**
   * Click on continue button.
   */
  public void clickContinueButtonNoWait() {
    highlight(continueButton);
    continueButton.click();

    Logger.trace("Clicked Continue button on Payment Method page");
  }

  /**
   * Determine if the page is loaded.
   *
   * @return true if the submit button is present
   */
  public boolean pageIsLoaded() {
    return isElementPresentBySelector(By.name("submit_exist_acct"));
  }

  /**
   * Check the express pay box based on given parameter.
   *
   * @param method payment method
   */
  public void setExpressPayCheckbox(String method) {
    WebElement checkbox;

    checkbox = driver.findElement(By
        .cssSelector("tr[id$='" + getIdValue(method)
            + "'] .fee_amount_additional input[name='express_pay']"));

    if (!checkbox.isSelected()) {
      checkbox.click();
      Logger.trace("Checked " + method + " box");
    } else {
      Logger.trace("Checkbox " + method + " already checked");
    }
  }

  /**
   * Check the PM Incur fee checkbox for the given method.
   *
   * @param method payment method
   */
  public void setPmIncursCheckbox(String method) {
    WebElement checkbox;

    checkbox = driver.findElement(By
        .cssSelector("tr[id$='" + getIdValue(method)
            + "'] input[name='incur_fee']"));

    if (!checkbox.isSelected()) {
      checkbox.click();
      Logger.trace("Checked " + method + " PM incurs box");
    } else {
      Logger.trace("Checkbox " + method + " PM incurs already checked");
    }
  }

  public String getPaymentMethodErrorMessage() {
    return getTextBySelector(By.cssSelector("label.error[for='acct']"));
  }

  public enum CardType {
    VISA("visa"), MASTERCARD("mc"), AMEX("amex"), DISCOVER("dc");

    private final String cardType;

    CardType(String cardType) {
      this.cardType = cardType;
    }

    String getValue() {
      return cardType;
    }
  }

  /**
   * Check if check image is present and displayed.
   *
   * @return boolean boolean value
   */
  public boolean isCheckImageDisplayed() {
    return isElementDisplayed(
        By.cssSelector("tr#banew img[src='/ci/assets/images/shared/ach_icon_sm.png']"));
  }

  /**
   * Check if the image of a particular cardType is present.
   *
   * @return boolean boolean value
   */
  public boolean isCcImageDisplayed(CardType cardType) {
    return isElementDisplayed(By.cssSelector(
        "#ccnew span.cc_icons > img[src='/ci/assets/images/shared/" + cardType.getValue()
            + "_icon_sm.png']"));
  }

  /**
   * To check if a method is present or not.
   *
   * @param method payment method to check
   * @return boolean value
   */
  public boolean isPaymentMethodPresent(String method) {
    String radioValue = getRadioValue(method);
    Boolean statement = false;

    List<WebElement> elements = driver.findElements(
        By.cssSelector("input[name='acct'][value='" + radioValue + "']"));

    if (!elements.isEmpty()) {
      statement = elements.get(0).isEnabled();
    }

    return statement;
  }

  public boolean isExpressPayPresent(String method) {
    return isElementPresentBySelector(
        By.cssSelector("#" + getIdValue(method) + " input#express_pay"));
  }

  /**
   * Is the PM Incur option present for the given payment method row.
   *
   * @param method method row to check
   * @return true if the checkbox is present
   */
  public boolean isPmIncurOptionPresent(String method) {
    return isElementPresentBySelector(
        By.cssSelector("#" + getIdValue(method) + " input[name='incur_fee']"));
  }

  public String getPaymentMethodLabel(String method) {
    return getTextBySelector(By.id(getIdValue(method) + "_text"));
  }

  /**
   * Get the error message displayed at the top of the page.
   *
   * @return Error message text
   */
  public String getErrorMessage() {
    return getTextBySelector(By.className("error_server"));
  }

  /**
   * Return ExpressPay text for given payment method.
   *
   * @param method payment method
   * @return expresspay text
   */
  public String getExpressPayText(String method) {
    String idValue = getIdValue(method);

    return getTextBySelector(
        By.cssSelector("tr[id$='" + idValue + "'] .fee_amount_additional"));
  }

  /**
   * Return text from expresspay column.
   *
   */
  public String getxpressPayColumnHeaderText() {
    return getTextBySelector(By.cssSelector("th.express"));
  }
}

