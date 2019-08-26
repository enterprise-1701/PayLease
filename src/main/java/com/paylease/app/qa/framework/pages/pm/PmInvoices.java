package com.paylease.app.qa.framework.pages.pm;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class PmInvoices extends PageBase {

  private static final String URL = BASE_URL + "pm/invoices";

  @FindBy(name = "search_field")
  private WebElement accountOrInvoice;

  @FindBy(name = "search_value")
  private WebElement searchByField;

  @FindBy(name = "submit_filters")
  private WebElement submitButton;

  @FindBy(css = ".status")
  private WebElement status;

  /**
   * PM Invoices page object.
   */
  public PmInvoices() {
    super();
  }

  // ********************************************Action*********************************************

  public void open() {
    openAndWait(URL);
  }

  /**
   * Enter the search value in the search by field.
   *
   * @param value search value to be entered
   */
  private void enterSearchByValue(String value) {
    highlight(searchByField);
    searchByField.click();
    searchByField.clear();
    searchByField.sendKeys(value);

    Logger.trace("Entered search by value as: " + value);
  }

  /**
   * Click on continue button.
   */
  private void clickSubmitButton() {
    highlight(submitButton);
    clickAndWait(submitButton);
    Logger.trace("Clicked submit button on Invoices page");
  }

  /**
   * Search for an Invoice.
   *
   * @param invoiceId id of the invoice to be searched
   */
  public void searchByInvoiceId(String invoiceId) {
    new Select(accountOrInvoice).selectByVisibleText("Invoice ID");
    enterSearchByValue(invoiceId);
    clickSubmitButton();
  }

  public void mouseHoverOnStatus() {
    mouseHover(status);
  }

  public String getStatusText() {
    return getTextBySelector(By.cssSelector(".status"));
  }

  public String getStatusQtipText() {
    return status.getAttribute("oldtitle");
  }

  public boolean isPayNowLinkPresent() {
    return isElementPresentBySelector(By.cssSelector("a.pay_now"));
  }
}
