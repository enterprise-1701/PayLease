package com.paylease.app.qa.framework.pages.resident;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.components.datatable.ResManageBankAccounts;
import com.paylease.app.qa.framework.components.datatable.ResManageCardAccounts;
import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ResidentAccountsPage extends PageBase {

  private static final String URL = BASE_URL + "resident/accounts";

  public enum PaymentTypes {
    PAYMENT_TYPE_ACH,
    PAYMENT_TYPE_CC
  }

  @FindBy(id = "add_bank_account_button")
  private WebElement addBankAccountButton;

  @FindBy(id = "add_card_account_button")
  private WebElement addCardAccountButton;

  @FindBy(id = "bank_accounts")
  private WebElement bankAccountsTable;

  @FindBy(id = "card_accounts")
  private WebElement cardAccountsTable;

  // ********************************************Action*********************************************

  public void open() {
    openAndWait(URL);
  }

  /**
   * Get info in bank accounts table.
   *
   * @return table contents
   */
  public String getBankAccountsTable() {
    return getTextBySelector(By.id("bank_accounts"));
  }

  /**
   * Get info in card accounts table.
   *
   * @return table contents
   */
  public String getCardAccountsTable() {
    return getTextBySelector(By.id("card_accounts"));
  }

  /**
   * Check if navigation from old page has occurred and new page has loaded.
   *
   * @return true when bank account table is present and page title matches
   */
  public boolean pageIsLoaded() {
    return getTitle().equals("My Payment Accounts")
        && isElementPresentBySelector(By.id("bank_accounts"));
  }

  /**
   * Click on add bank button.
   *
   * @param wait wait after clicking if true
   */
  public void clickAddBankAccountButton(boolean wait) {
    highlight(addBankAccountButton);

    if (wait) {
      clickAndWait(addBankAccountButton);
    } else {
      addBankAccountButton.click();
    }

    Logger.trace("Clicked on add bank account button");
  }

  /**
   * Click on add card button.
   *
   * @param wait after clicking if true
   */
  public void clickAddCardAccountButton(boolean wait) {
    highlight(addCardAccountButton);

    if (wait) {
      clickAndWait(addCardAccountButton);
    } else {
      addCardAccountButton.click();
    }

    Logger.trace("Clicked on add card account button");
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
   * Get the text from alert.
   *
   * @return alert text
   */
  public String getAlertText() {
    wait.until(ExpectedConditions.alertIsPresent());
    Alert alert = driver.switchTo().alert();
    return alert.getText();
  }

  /**
   * Get the error message from the page.
   *
   * @return error message
   */
  public String getErrorMessage() {
    return getTextBySelector(By.className("error_mess"));
  }

  /**
   * Click on delete button of payment method, given the account holder name.
   *
   * @param wait wait after clicking if true
   * @param paymentMethod ACH or CC
   * @param accountHolderName name on payment method
   */
  public void deletePaymentMethod(boolean wait, PaymentTypes paymentMethod,
      String accountHolderName) {
    WebElement deleteLink = null;

    if (paymentMethod == PaymentTypes.PAYMENT_TYPE_CC) {
      ResManageCardAccounts manageCardAcctsTable = new ResManageCardAccounts(cardAccountsTable);
      deleteLink = manageCardAcctsTable
          .getRowByCellText(accountHolderName, ResManageCardAccounts.Columns.CARDHOLDER.getLabel())
          .findElement(By.className("delete_lnk"));
    } else if (paymentMethod == PaymentTypes.PAYMENT_TYPE_ACH) {
      ResManageBankAccounts manageBankAcctsTable = new ResManageBankAccounts(bankAccountsTable);
      deleteLink = manageBankAcctsTable.getRowByCellText(accountHolderName,
          ResManageBankAccounts.Columns.NAME_ON_ACCOUNT.getLabel())
          .findElement(By.className("delete_lnk"));
    }

    if (deleteLink != null) {
      highlight(deleteLink);
      if (wait) {
        clickAndWait(deleteLink);
      } else {
        deleteLink.click();
      }
    }

    Logger.trace("Clicked on delete button.");
  }

  /**
   * Click ok on alert.
   *
   */
  public void acceptAlert() {
    wait.until(ExpectedConditions.alertIsPresent());
    Alert alert = driver.switchTo().alert();
    alert.accept();

    Logger.trace("Clicked on OK button of alert.");
  }

  /**
   * Get card logo src.
   *
   * @param accountHolderName name on payment method
   */
  public String getCardLogoSrc(String accountHolderName) {
    ResManageCardAccounts manageCardAcctsTable = new ResManageCardAccounts(cardAccountsTable);

    WebElement cardLogo = manageCardAcctsTable
        .getRowByCellText(accountHolderName, ResManageCardAccounts.Columns.CARDHOLDER.getLabel())
        .findElement(By.tagName("img"));
    highlight(cardLogo);
    return cardLogo.getAttribute("src");
  }
}
