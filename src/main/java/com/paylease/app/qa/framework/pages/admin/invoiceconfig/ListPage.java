package com.paylease.app.qa.framework.pages.admin.invoiceconfig;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.components.DataTable;
import com.paylease.app.qa.framework.pages.PageBase;
import java.util.HashMap;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Mahfuz Alam on 10/18/2017.
 */

public class ListPage extends PageBase {

  private static final String COLUMN_PROPERTY = "property";
  private static final String COLUMN_BANK_ACCOUNT = "bankAccount";
  private static final String COLUMN_MODE = "mode";
  private static final String COLUMN_DEBIT_DAY = "debitDay";
  private static final String COLUMN_CREATED_ON = "createdOn";
  private static final String COLUMN_LAST_RUN = "lastRun";

  private static final String URL = BASE_URL + "admin2.php?action=admin_pm_invoices&user_id=";

  private String pmId;

  @FindBy(id = "createNewInvoice")
  private WebElement createNewInvoice;

  @FindBy(id = "invoiceList")
  private WebElement invoiceList;

  private DataTable invoices;

  /**
   * List of Invoice Configs for given PM.
   *
   * @param pmId PM ID
   */
  public ListPage(String pmId) {
    super();
    this.pmId = pmId;

    initTable();
  }

  // ********************************************Action*********************************************

  /**
   * Open the Invoice Config List page for the given PM.
   */
  public void open() {
    openAndWait(URL + pmId);
    Logger.trace("Navigated to Invoice page, where PM is: " + pmId);
  }

  /**
   * Click the Create New Invoice link and wait for form page to load.
   *
   * @return Create Form Page, once it has loaded
   */
  public FormCreatePage clickCreateNewInvoice() {
    highlight(createNewInvoice);
    clickAndWait(createNewInvoice);
    Logger.trace("Clicked on create invoice config link");

    FormCreatePage formCreatePage = new FormCreatePage(pmId);
    formCreatePage.initSelectElements();
    return formCreatePage;
  }

  /**
   * Get a Map of the values from a given row in the list.
   *
   * @param configId ID of the Invoice Config to find
   * @return Map of values from the row
   */
  public HashMap<String, String> getTableRowMap(String configId) {
    WebElement row = invoices.getRowById(configId);
    return invoices.getMapOfTableRow(row);
  }

  /**
   * Click the link to edit a given Invoice Config.
   *
   * @param configId ID of the Invoice Config to edit
   * @return Form Edit Page for this invoice config once it has loaded
   */
  public FormEditPage clickOnEditAction(String configId) {
    WebElement rowId = invoices.getRowById(configId);
    WebElement rowValue = rowId.findElement(By.className("action"));
    WebElement editLink = rowValue.findElement(By.tagName("a"));
    clickAndWait(editLink);

    FormEditPage formEditPage = new FormEditPage(pmId, configId);
    formEditPage.initSelectElements();
    return formEditPage;
  }

  private void initTable() {
    invoices = new DataTable(invoiceList, "configId_");
    invoices.addTableColumn(COLUMN_PROPERTY, "property");
    invoices.addTableColumn(COLUMN_BANK_ACCOUNT, "bankAccount");
    invoices.addTableColumn(COLUMN_MODE, "mode");
    invoices.addTableColumn(COLUMN_DEBIT_DAY, "debitDay");
    invoices.addTableColumn(COLUMN_CREATED_ON, "createdOn");
    invoices.addTableColumn(COLUMN_LAST_RUN, "lastRun");

  }
}
