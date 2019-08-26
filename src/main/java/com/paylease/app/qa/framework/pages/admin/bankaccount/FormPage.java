package com.paylease.app.qa.framework.pages.admin.bankaccount;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.pages.PageBase;
import java.util.Random;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public abstract class FormPage extends PageBase {

  protected String pmId;

  @FindBy(id = "name")
  private WebElement name;

  @FindBy(id = "bank_name")
  private WebElement bankName;

  @FindBy(id = "account_type")
  private WebElement accountType;

  @FindBy(id = "routing_number")
  private WebElement routingNumber;

  @FindBy(id = "account_number")
  private WebElement accountNumber;

  @FindBy(id = "account_number_confirm")
  private WebElement accountNumberConfirm;

  @FindBy(id = "save_btn")
  private WebElement saveBtn;

  private Select selectAccountType;

  /**
   * Create a Form Page.
   *
   * @param pmId PM ID for this page
   */
  FormPage(String pmId) {
    super();
    this.pmId = pmId;

    initSelectElements();
  }

  private void initSelectElements() {
    selectAccountType = new Select(accountType);
  }

  /**
   * Enter a value for the Name on Account field.
   *
   * @param text Text to enter
   */
  public void setName(String text) {
    highlight(name);
    name.clear();
    name.sendKeys(text);

    Logger.trace("Enter name: " + text);
  }

  /**
   * Get the value of the Bank Name field.
   *
   * @return Text value
   */
  public String getBankName() {
    highlight(bankName);
    return bankName.getAttribute("value");
  }

  /**
   * Enter a value for the Bank Name field.
   *
   * @param text Text to enter
   */
  public void setBankName(String text) {
    highlight(bankName);
    bankName.clear();
    bankName.sendKeys(text);

    Logger.trace("Enter bank name: " + text);
  }

  /**
   * Choose a random option for the Account Type and return the selected text.
   *
   * @return Text of option selected
   */
  public String setAccountType() {
    highlight(accountType);
    selectAccountType.selectByIndex(new Random().nextInt(selectAccountType.getOptions().size()));

    String selectedAccountType = getSelectedAccountType();
    Logger.trace("Selected Account Type: " + selectedAccountType);

    return selectedAccountType;
  }

  private String getSelectedAccountType() {
    return selectAccountType.getFirstSelectedOption().getText();
  }

  public String getAccountType() {
    highlight(accountType);
    return getSelectedAccountType();
  }

  /**
   * Choose a given account type.
   *
   * @param value Text of option to choose
   */
  public void setAccountType(String value) {
    highlight(accountType);
    selectAccountType.selectByVisibleText(value);
    Logger.trace("Selected Account Type: " + value);
  }

  /**
   * Get the value of the Routing Number field.
   *
   * @return Text value
   */
  public String getRoutingNumber() {
    highlight(routingNumber);
    return routingNumber.getAttribute("value");
  }

  /**
   * Enter a value for the Routing Number field.
   *
   * @param text Text to enter
   */
  public void setRoutingNumber(String text) {
    highlight(routingNumber);
    routingNumber.clear();
    routingNumber.sendKeys(text);

    Logger.trace("Enter routing number: " + text);
  }

  /**
   * Get the value of the Account Number field.
   *
   * @return Text value
   */
  public String getAccountNumber() {
    highlight(accountNumber);
    return accountNumber.getAttribute("value");
  }

  /**
   * Enter a value for the Account Number field.
   *
   * @param text Text to enter
   */
  public void setAccountNumber(String text) {
    highlight(accountNumber);
    accountNumber.clear();
    accountNumber.sendKeys(text);

    Logger.trace("Enter account number: " + text);
  }

  /**
   * Get the value of the Account Number confirm field.
   *
   * @return Text value
   */
  public String getAccountNumberConfirm() {
    highlight(accountNumberConfirm);
    return accountNumberConfirm.getAttribute("value");
  }

  /**
   * Enter a value for the Account Number Confirmation field.
   *
   * @param text Text to enter
   */
  public void setAccountNumberConfirm(String text) {
    highlight(accountNumberConfirm);
    accountNumberConfirm.clear();
    accountNumberConfirm.sendKeys(text);

    Logger.trace("Confirm account number: " + text);
  }

  /**
   * Click the Save button and wait for the List page to load.
   *
   * @return ListPage after the new account has been saved
   */
  public ListPage clickSave() {
    clickAndWait(saveBtn);

    return new ListPage(pmId);
  }
}
