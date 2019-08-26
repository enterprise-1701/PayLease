package com.paylease.app.qa.framework.pages.admin.bankaccount;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.components.DataTable;
import com.paylease.app.qa.framework.pages.PageBase;
import java.util.HashMap;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ListPage extends PageBase {

  public static final String COLUMN_ACCOUNT_ID = "accountId";
  public static final String COLUMN_BANK_NAME = "bankName";
  public static final String COLUMN_ROUTING_NUMBER = "routingNumber";
  public static final String COLUMN_ACCOUNT_NUMBER = "accountNumber";
  public static final String COLUMN_ACCOUNT_TYPE = "accountType";

  private static final String URL = BASE_URL + "admin2.php?action=pm_bank_accounts&user_id=";

  private String pmId;

  @FindBy(id = "add_bank_account_btn")
  private WebElement addBankAccountBtn;

  @FindBy(id = "bank_acc_table")
  private WebElement bankAccountTable;

  private DataTable bankAccounts;

  /**
   * List of Bank Accounts for given PM.
   *
   * @param pmId PM ID
   */
  public ListPage(String pmId) {
    super();
    this.pmId = pmId;

    initTable();
  }

  /**
   * Open the Bank Account List page for the given PM.
   */
  public void open() {
    openAndWait(URL + pmId);
    Logger.trace("Navigated to Bank Account page, where PM is: " + pmId);
  }

  /**
   * Click the Add New Bank Account button and wait for form page to load.
   *
   * @return Form Page, once it has loaded
   */
  public FormCreatePage clickAddNewBankAccountButton() {
    highlight(addBankAccountBtn);
    clickAndWait(addBankAccountBtn);
    Logger.trace("Clicked on add new bank account button");

    return new FormCreatePage(pmId);
  }

  /**
   * Click to edit the bank account with the given account id.
   *
   * @param accountId Account ID of the bank account to edit
   * @return Newly loaded edit form page
   */
  public FormEditPage clickEditLink(String accountId) {
    WebElement row = bankAccounts.getRowById(accountId);
    WebElement editLink = row.findElement(By.linkText("Edit"));
    clickAndWait(editLink);

    return new FormEditPage(pmId, accountId);
  }

  /**
   * Get a map of row values from the table for the (first) row with the matching bank name.
   *
   * @param bankName Bank Name to search for
   * @return Map of values from the row
   */
  public HashMap<String, String> getTableRowByBankName(String bankName) {
    WebElement row = bankAccounts.getRowByCellText(bankName, COLUMN_BANK_NAME);
    return bankAccounts.getMapOfTableRow(row);
  }

  /**
   * Get a map of row values from the table for the row matching the given account id.
   *
   * @param accountId Account ID to find in table
   * @return Map of values from the row
   */
  public HashMap<String, String> getTableRowById(String accountId) {
    WebElement row = bankAccounts.getRowById(accountId);
    return bankAccounts.getMapOfTableRow(row);
  }

  private void initTable() {
    bankAccounts = new DataTable(bankAccountTable, "accountId_");
    bankAccounts.addTableColumn(COLUMN_ACCOUNT_ID, "account_id");
    bankAccounts.addTableColumn(COLUMN_BANK_NAME, "bank_name");
    bankAccounts.addTableColumn(COLUMN_ROUTING_NUMBER, "routing_number");
    bankAccounts.addTableColumn(COLUMN_ACCOUNT_NUMBER, "account_number");
    bankAccounts.addTableColumn(COLUMN_ACCOUNT_TYPE, "account_type");
  }
}
