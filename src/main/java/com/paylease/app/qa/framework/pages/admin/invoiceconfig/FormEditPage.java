package com.paylease.app.qa.framework.pages.admin.invoiceconfig;

import com.paylease.app.qa.framework.Logger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class FormEditPage extends FormPage {

  public static final String ITEM_ROW_NAME = "itemName";
  public static final String ITEM_ROW_AMOUNT = "amount";

  private static final String URL = BASE_URL
      + "admin2.php?action=invoices_config_addnew&user_id={userId}&invoices_config_id={configId}";

  private String configId;

  @FindBy(id = "saved_property")
  private WebElement savedProperty;

  @FindBy(id = "saved_mode")
  private WebElement savedPaymentMode;

  @FindBy(id = "saved_debitDay")
  private WebElement savedDebitDay;

  @FindBy(id = "saved_createdOn")
  private WebElement savedCreatedOn;

  @FindBy(id = "lastRunDate")
  private WebElement lastRunDate;

  @FindBy(name = "invoicetype")
  private WebElement invoiceType;

  @FindBy(name = "amount")
  private WebElement amount;

  @FindBy(id = "addInvoiceItem")
  private WebElement addButton;

  /**
   * Create a Form Page for editing an invoice config.
   *
   * @param pmId PM ID for the given invoice config
   * @param configId ID for the given invoice config
   */
  public FormEditPage(String pmId, String configId) {
    super(pmId);

    this.configId = configId;
  }

  public String getConfigId() {
    return configId;
  }

  public void open() {
    String url = URL.replace("{userId}", pmId).replace("{configId}", configId);
    openAndWait(url);
    initSelectElements();
  }

  /**
   * Add a random invoice item with a specified amount.
   *
   * @param amount amount to enter
   * @return item name selected
   */
  public String addInvoiceItem(String amount) {
    String itemName = setInvoiceType();
    setAmount(amount);
    clickAddButton();
    return itemName;
  }

  /**
   * Select a random invoice item to be added to the line items.
   *
   * @return Name of the item
   */
  private String setInvoiceType() {
    List<WebElement> validOptions = invoiceType
        .findElements(By.cssSelector("option:not([value=''])"));
    WebElement selectedOption = validOptions.get(new Random().nextInt(validOptions.size()));
    return setInvoiceType(selectedOption.getAttribute("value"));
  }

  /**
   * Select an invoice item to be added to the line items.
   *
   * @return Selected line item name
   */
  private String setInvoiceType(String itemTypeId) {
    Select selectInvoiceType = new Select(invoiceType);
    selectInvoiceType.selectByValue(itemTypeId);

    WebElement selectedInvoiceType = selectInvoiceType.getFirstSelectedOption();
    Logger.trace("Selected Invoice Type: " + selectedInvoiceType.getText());
    return selectedInvoiceType.getText();
  }

  /**
   * Enter an amount for a new invoice line item.
   *
   * @param value Amount to enter
   */
  private void setAmount(String value) {
    highlight(amount);
    amount.clear();
    amount.click();
    amount.sendKeys(value);
  }

  /**
   * Click the add button to add a new line item.
   */
  private void clickAddButton() {
    highlight(addButton);
    clickAndWait(addButton);
  }


  public String getSavedProperty() {
    return savedProperty.getText();
  }

  public String getSavedPaymentMode() {
    return savedPaymentMode.getText();
  }

  public String getSavedDebitDay() {
    return savedDebitDay.getText();
  }

  public String getSavedCreatedOn() {
    return savedCreatedOn.getText();
  }

  public String getLastRunDate() {
    return lastRunDate.getText();
  }

  public int getInvoiceItemCount() {
    return driver.findElements(By.className("invoiceItemRow")).size();
  }

  /**
   * Get a map of the values for a given invoice item row, by id.
   *
   * @param id ID of the invoice item row
   * @return Map of column values from table
   */
  public HashMap<String, String> getInvoiceItemRow(String id) {
    WebElement itemRow = driver.findElement(By.id("itemId_" + id));
    HashMap<String, String> rowValues = new HashMap<>();
    rowValues.put(ITEM_ROW_NAME, itemRow.findElement(By.className("itemName")).getText());
    rowValues.put(ITEM_ROW_AMOUNT, itemRow.findElement(By.className("amount")).getText());
    return rowValues;
  }

  /**
   * Get a set of invoice item row Ids from the table.
   *
   * @return Set of item Ids
   */
  public Set<String> getInvoiceItemRowIds() {
    Set<String> itemIds = new HashSet<>();
    List<WebElement> allRows = driver.findElements(By.className("invoiceItemRow"));
    for (WebElement row : allRows) {
      itemIds.add(row.getAttribute("id").replace("itemId_", ""));
    }
    return itemIds;
  }

  /**
   * Delete an invoice item row by Id.
   *
   * @param id ID of row to delete
   */
  public void deleteInvoiceItem(String id) {
    WebElement itemRow = driver.findElement(By.id("itemId_" + id));
    WebElement deleteLink = itemRow.findElement(By.linkText("Delete Item"));
    clickAndWait(deleteLink);
  }
}
