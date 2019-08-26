package com.paylease.app.qa.framework.pages.admin;

import com.paylease.app.qa.framework.components.datatable.AdminFlexibleFeeCustomSettings;
import com.paylease.app.qa.framework.components.datatable.AdminFlexibleFeeCustomSettings.Columns;
import com.paylease.app.qa.framework.pages.PageBase;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class FlexibleFeeStructure extends PageBase {

  private static final String ATTRIBUTE_AMOUNT = "amount";
  private static final String ATTRIBUTE_INCUR = "incur";
  private static final String ATTRIBUTE_TYPE = "type";

  private static final String[] CUSTOM_SETTINGS_TABLE_COLUMN_NAMES = {
      "Property Name",
      "Payment Field",
      "Payment Type",
      "Account Type",
      "Fee Type",
      "Fee Amount",
      "Incurred By",
      "Tier Amount",
  };

  public enum PaymentType {
    ONE_TIME("otp"),
    FIXED_AUTOPAY("fap"),
    VARIABLE_AUTOPAY("vap");

    private final String paymentType;

    PaymentType(String paymentType) {
      this.paymentType = paymentType;
    }

    String getValue() {
      return paymentType;
    }
  }

  public enum PaymentMethod {
    ACH("ach"),
    CREDIT("cc"),
    DEBIT("dc");

    private final String paymentMethod;

    PaymentMethod(String paymentMethod) {
      this.paymentMethod = paymentMethod;
    }

    String getValue() {
      return paymentMethod;
    }
  }

  private static final String URL_TEMPLATE =
      BASE_URL + "admin/flexible_fee_structure/setup/{pmId}";
  private String url;

  private String pmId;

  @FindBy(id = "page_title")
  private WebElement pageTitle;

  @FindBy(id = "cc_ach_fee")
  private WebElement ccAchFeeCheckbox;

  @FindBy(id = "rounding")
  private WebElement roundingCheckbox;

  @FindBy(id = "pm_one_time_incur")
  private WebElement pmOneTimeFeeIncurCheckbox;

  @FindBy(id = "phone_fee")
  private WebElement phoneFeeCheckbox;

  @FindBy(id = "phone_fee_amt")
  private WebElement phoneFeeInput;

  @FindBy(id = "express_pay")
  private WebElement expressPayCheckbox;

  @FindBy(id = "express_pay_amt")
  private WebElement expressFeeInput;

  @FindBy(id = "save_settings")
  private WebElement saveSettingsBtn;

  @FindBy(id = "save_defaults")
  private WebElement saveDefaultsBtn;

  @FindBy(id = "custom_prop_settings_wrapper")
  private WebElement customSettingsWrapper;

  @FindBy(id = "custom_prop_settings")
  private WebElement customSettingsTable;

  @FindBy(className = "delete_selected_custom_settings")
  private WebElement customSettingsDeleteBtn;

  @FindBy(id = "create_prop_setting")
  private WebElement createPropSetting;

  // ********************************************Action*********************************************

  /**
   * Constructor.
   *
   * @param pmId the pmId
   */
  public FlexibleFeeStructure(int pmId) {
    url = URL_TEMPLATE.replace("{pmId}", String.valueOf(pmId));
    this.pmId = String.valueOf(pmId);
  }

  /**
   * Open the page and wait for the custom settings table to load.
   */
  public void open() {
    openAndWait(url);

    waitUntilCustomSettingsTableIsLoaded();
  }

  public boolean pageIsLoaded() {
    return pageTitle.getText().equals("Set Up Fees (Flexible Fee Structure)")
        && driver.findElement(By.id("user_id")).getText().equals(pmId);
  }

  public void clickSaveSettingsBtnAndWait() {
    clickAndWait(saveSettingsBtn);
  }

  public void clickSaveSettingsBtnNoWait() {
    saveSettingsBtn.click();
  }

  public void clickSaveDefaultsBtnAndWait() {
    clickAndWait(saveDefaultsBtn);
  }

  public void clickSaveDefaultsBtnNoWait() {
    saveDefaultsBtn.click();
  }

  public void clickOkayOnAlert() {
    Alert alert = wait.until(ExpectedConditions.alertIsPresent());
    alert.accept();
  }

  public void clickCancelOnConfirm() {
    Alert alert = wait.until(ExpectedConditions.alertIsPresent());
    alert.dismiss();
  }

  /**
   * Click OK on the confirmation and wait for the form to submit and new page to load.
   */
  public void clickDeleteAndOkayOnConfirm() {
    WebElement oldPage = driver.findElement(By.tagName("html"));

    clickDelete();

    clickOkayOnAlert();

    waitForPageToLoad(oldPage);
  }

  public void clickCreateCustomSetting() {
    clickAndWait(createPropSetting);
  }

  public boolean isErrorShown() {
    return driver.findElement(By.id("error_list")).isDisplayed();
  }

  public String getSuccessMessage() {
    return getTextBySelector(By.className("success_message"));
  }

  public String getErrorMessage() {
    return getTextBySelector(By.cssSelector("#error_list .error_item"));
  }

  /**
   * Change the input element for express fee from number to text.
   */
  public void disableValidationExpressFeeInput() {
    disableValidationInput("express_pay_amt");
  }

  /**
   * Change the input element for phone fee from number to  text.
   */
  public void disableValidationPhoneFeeInput() {
    disableValidationInput("phone_fee_amt");
  }

  /**
   * Determine if the checkbox for CC + ACH Fee is checked.
   *
   * @return true if the checkbox is checked
   */
  public boolean isCcAchFeeEnabled() {
    return ccAchFeeCheckbox.isSelected();
  }

  /**
   * Determine if the checkbox for Round to $0.95 is checked.
   *
   * @return true if the checkbox is checked
   */
  public boolean isRoundingEnabled() {
    return roundingCheckbox.isSelected();
  }

  /**
   * Determine if the checkbox for PM One-Time Fee Incur is checked.
   *
   * @return true if the checkbox is checked
   */
  public boolean isPmOneTimeFeeIncurEnabled() {
    return pmOneTimeFeeIncurCheckbox.isSelected();
  }

  /**
   * Determine if the checkbox for Phone Fee is checked.
   *
   * @return true if the checkbox is checked
   */
  public boolean isPhoneFeeEnabled() {
    return phoneFeeCheckbox.isSelected();
  }

  /**
   * Get the Phone Fee.
   *
   * @return the Phone Fee
   */
  public String getPhoneFee() {
    return phoneFeeInput.getAttribute("value");
  }

  /**
   * Determine if the checkbox for Express Pay is checked.
   *
   * @return true if the checkbox is checked
   */
  public boolean isExpressPayEnabled() {
    return expressPayCheckbox.isSelected();
  }

  /**
   * Get the Express Pay Fee.
   *
   * @return the Express Pay Fee
   */
  public String getExpressPayFee() {
    return expressFeeInput.getAttribute("value");
  }

  /**
   * Retrieve the amount for the given fee.
   *
   * @param paymentType payment type
   * @param paymentMethod payment method
   * @return amount
   */
  public String getAmount(PaymentType paymentType, PaymentMethod paymentMethod) throws Exception {

    return getFormField(paymentType, paymentMethod, ATTRIBUTE_AMOUNT).getAttribute("value");
  }

  /**
   * Retrieve the selected incurring party for the given fee.
   *
   * @param paymentType payment type
   * @param paymentMethod payment method
   * @return user type
   */
  public String getIncur(PaymentType paymentType, PaymentMethod paymentMethod) throws Exception {
    Select select = new Select(getFormField(paymentType, paymentMethod, ATTRIBUTE_INCUR));

    return select.getFirstSelectedOption().getText();
  }

  /**
   * Retrieve the selected fee type for the given fee.
   *
   * @param paymentType payment type
   * @param paymentMethod payment method
   * @return fee type
   */
  public String getFeeType(PaymentType paymentType, PaymentMethod paymentMethod) throws Exception {
    Select select = new Select(getFormField(paymentType, paymentMethod, ATTRIBUTE_TYPE));

    return select.getFirstSelectedOption().getText();
  }

  /**
   * Disable validation on the given default amount field.
   *
   * @param paymentType payment type
   * @param paymentMethod payment method
   * @throws Exception if element not present
   */
  public void disableValidationDefaultAmount(PaymentType paymentType, PaymentMethod paymentMethod)
      throws Exception {
    disableValidationInput(
        getFormField(paymentType, paymentMethod, ATTRIBUTE_AMOUNT).getAttribute("id"));
  }

  /**
   * Set the amount for the given fee.
   *
   * @param paymentType payment type
   * @param paymentMethod payment method
   * @param amount amount
   */
  public void setAmount(PaymentType paymentType, PaymentMethod paymentMethod, String amount)
      throws Exception {
    setKeys(getFormField(paymentType, paymentMethod, ATTRIBUTE_AMOUNT), amount);
  }

  /**
   * Set the incurring party for the given fee.
   *
   * @param paymentType payment type
   * @param paymentMethod payment method
   * @param incur option text to select
   */
  public void setIncur(PaymentType paymentType, PaymentMethod paymentMethod, String incur)
      throws Exception {
    Select select = new Select(getFormField(paymentType, paymentMethod, ATTRIBUTE_INCUR));

    select.selectByVisibleText(incur);
  }

  /**
   * Set the fee type for the given fee.
   *
   * @param paymentType payment type
   * @param paymentMethod payment method
   * @param feeType option text to select
   */
  public void setFeeType(PaymentType paymentType, PaymentMethod paymentMethod, String feeType)
      throws Exception {
    Select select = new Select(getFormField(paymentType, paymentMethod, ATTRIBUTE_TYPE));

    select.selectByVisibleText(feeType);
  }

  public void clickCcAchFeeCheckbox() {
    ccAchFeeCheckbox.click();
  }

  public void clickRoundingCheckbox() {
    roundingCheckbox.click();
  }

  public void clickPmOneTimeFeeIncurCheckbox() {
    pmOneTimeFeeIncurCheckbox.click();
  }

  public void clickPhoneFeeCheckbox() {
    phoneFeeCheckbox.click();
  }

  public void clickExpressPayCheckbox() {
    expressPayCheckbox.click();
  }

  public void setPhoneFee(String amount) {
    setKeys(phoneFeeInput, amount);
  }

  public void setExpressFee(String amount) {
    setKeys(expressFeeInput, amount);
  }

  /**
   * Check to see if custom settings search is present on top of table.
   *
   * @return true if custom settings search is present on top of table
   */
  public boolean isCustomSettingsTableSearchPresent() {
    WebElement label = getElementBySelectorIfPresent(customSettingsWrapper,
        By.cssSelector(".top #custom_prop_settings_filter label"));
    if (label == null) {
      return false;
    }
    boolean searchLabelExist = label.getText().equals("Search:");

    if (getElementBySelectorIfPresent(label, By.cssSelector("input[type='search']")) == null) {
      return false;
    }

    return searchLabelExist;
  }

  /**
   * Check to see if custom settings pagination is present on top and bottom of table.
   *
   * @return true if custom settings pagination is present on top and bottom of table
   */
  public boolean isCustomSettingsTablePaginationPresent() {
    return isElementWithTextPresent(customSettingsWrapper,
        ".top .dataTables_paginate a.paginate_button.previous", "Previous")
        || isElementWithTextPresent(customSettingsWrapper,
        ".top .dataTables_paginate a.paginate_button.next", "Next")
        || isElementWithTextPresent(customSettingsWrapper,
        ".bottom .dataTables_paginate a.paginate_button.previous", "Previous")
        || isElementWithTextPresent(customSettingsWrapper,
        ".bottom .dataTables_paginate a.paginate_button.next", "Next");
  }

  /**
   * Check to see if custom settings page size selector is present on top of table.
   *
   * @return true if custom settings page size selector is present on top of table
   */
  public boolean isCustomSettingsTablePageSizeSelectorPresent() {
    WebElement pageSizeElement = getElementBySelectorIfPresent(customSettingsWrapper,
        By.cssSelector(".top #custom_prop_settings_length"));
    if (pageSizeElement == null) {
      return false;
    }

    return pageSizeElement.getText().equals("Show\n10\n25\n50\n100\nentries");
  }

  /**
   * Check to see if custom settings table info label is present on bottom of table.
   *
   * @return true if custom settings table info label is present on bottom of table
   */
  public boolean isCustomSettingsTableInfoLabelPresent() {
    WebElement tableInfo = getElementBySelectorIfPresent(customSettingsWrapper,
        By.cssSelector(".bottom #custom_prop_settings_info"));
    if (tableInfo == null) {
      return false;
    }

    return tableInfo.getText().startsWith("Showing");
  }

  /**
   * Check to see if custom settings table header is present.
   *
   * @return true if custom settings table header is present
   */
  public boolean isCustomSettingsTableHeaderPresent() {
    String cssSelector = "thead tr th";
    List<WebElement> tableHeaderColumns = customSettingsTable
        .findElements(By.cssSelector(cssSelector));
    if (tableHeaderColumns.size() == 0) {
      return false;
    }

    ArrayList<String> columns = new ArrayList<>(Arrays.asList(CUSTOM_SETTINGS_TABLE_COLUMN_NAMES));

    for (WebElement th : tableHeaderColumns) {
      String columnText = th.getText();

      if (columns.contains(columnText)) {
        columns.remove(columnText);
      }
    }

    return columns.isEmpty();
  }

  /**
   * Check to see if custom settings table header allows sorting.
   *
   * @return true if custom settings table header allows sorting
   */
  public boolean doesCustomSettingsTableHeaderAllowSorting() {
    return !customSettingsTable.findElements(By.cssSelector("thead tr th[aria-controls]"))
        .isEmpty();
  }

  /**
   * Check to see if custom settings table has a delete button at the top of the table.
   *
   * @return if custom settings table has a delete button
   */
  public boolean isCustomSettingsDeleteButtonPresent() {
    return isElementPresentBySelector(By.className("delete_selected_custom_settings"));
  }

  /**
   * Returns the total number of custom settings rows reported by the custom settings datatable.
   *
   * @return total number of custom settings rows
   */
  public int getTotalNumberOfCustomSettings() {
    String labelText =
        customSettingsWrapper.findElement(By.cssSelector(".dataTables_info")).getText();

    Pattern pattern = Pattern.compile("Showing 1 to \\d+ of (\\d+) entries");
    Matcher matcher = pattern.matcher(labelText);

    matcher.find();

    return Integer.parseInt(matcher.group(1));
  }

  /**
   * Get the label of Custom Settings filter buttons.
   *
   * @return List of Custom Settings filter buttons labels
   */
  public List<String> getCustomSettingsSelectFilterButtons() {
    List<WebElement> buttonElements = customSettingsWrapper
        .findElements(By.cssSelector(".top .ui-buttonset label"));

    List<String> buttonLabels = new ArrayList<>();

    for (WebElement element : buttonElements) {
      buttonLabels.add(element.getText());
    }

    return buttonLabels;
  }

  /**
   * Check to see if custom settings table is showing the 'No Data Available' message.
   *
   * @return true if custom settings table is showing the 'No Data Available' message
   */
  public boolean isCustomSettingsTableNoDataAvailableMessagePresent() {
    WebElement tableBody = getElementBySelectorIfPresent(customSettingsTable,
        By.cssSelector("tbody tr td.dataTables_empty"));
    if (tableBody == null) {
      return false;
    }

    return tableBody.getText().equals("No data available in table");
  }

  /**
   * Check to see if custom settings table info label is showing zero entries.
   *
   * @return true if custom settings table info label is showing zero entries
   */
  public boolean doesCustomSettingsTableInfoLabelShowZeroEntries() {
    WebElement tableInfo = getElementBySelectorIfPresent(customSettingsWrapper,
        By.cssSelector(".bottom #custom_prop_settings_info"));
    if (tableInfo == null) {
      return false;
    }

    return tableInfo.getText().equals("Showing 0 to 0 of 0 entries");
  }

  /**
   * Enter a search term into the search box on the custom settings table.
   *
   * @param searchTerm search term to enter
   */
  public void customSettingsTableEnterSearchTerm(String searchTerm) {
    WebElement searchBox = customSettingsWrapper.findElement(
        By.cssSelector(
            "#custom_prop_settings_filter input[type='search']"
        )
    );

    int rowCount = getCustomSettingsTableRowCount();

    enterText(searchBox, searchTerm);

    if (rowCount == 0) {
      return;
    }

    wait.until(ExpectedConditions.numberOfElementsToBeLessThan(
        By.cssSelector("#custom_prop_settings tbody tr[role='row']"), rowCount));
  }

  /**
   * Returns the number of rows in the custom settings table.
   *
   * @return number of rows in the custom settings table
   */
  public int getCustomSettingsTableRowCount() {
    return getCustomSettingsRows().size();
  }

  /**
   * Determine if the given row in the custom settings table has the given value in the given
   * column.
   *
   * @param rowNum Row number to test
   * @param column Column to test
   * @param value Value to find
   * @return True if cell matches value
   */
  public boolean customSettingsTableRowHasValueInColumn(int rowNum, Columns column, String value) {
    return customSettingsTableRowHasValueInColumn(rowNum, column.getLabel(), value);
  }

  /**
   * Determine if the given row in the custom settings table has the given value in the given
   * column.
   *
   * @param rowNum Row number to test
   * @param columnLabel Column to test
   * @param value Value to find
   * @return True if cell matches value
   */
  public boolean customSettingsTableRowHasValueInColumn(
      int rowNum, String columnLabel, String value
  ) {
    AdminFlexibleFeeCustomSettings table = new AdminFlexibleFeeCustomSettings(customSettingsTable);

    WebElement row = table.getRowByRowNum(rowNum);
    HashMap<String, String> rowData = table.getMapOfTableRow(row);

    return rowData.get(columnLabel.toLowerCase()).equals(value);
  }

  /**
   * Get a List of Maps of row data for the entire (visible) Custom Settings Table.
   *
   * @return List of Maps of row data
   */
  public List<Map<String, String>> getCustomSettingsTableRowContent() {
    ArrayList<Map<String, String>> tableRowData = new ArrayList<>();

    AdminFlexibleFeeCustomSettings table = new AdminFlexibleFeeCustomSettings(customSettingsTable);

    List<WebElement> tableRows = getCustomSettingsRows();

    for (WebElement tableRow : tableRows) {
      tableRowData.add(table.getMapOfTableRow(tableRow));
    }

    return tableRowData;
  }

  /**
   * Click the "Edit" link for the row matching the given map of data.
   *
   * @param rowData map of data for finding a row
   * @throws Exception If unable to find row or edit link
   */
  public void clickEditLinkByRowData(HashMap<String, String> rowData) throws Exception {
    AdminFlexibleFeeCustomSettings table = new AdminFlexibleFeeCustomSettings(customSettingsTable);

    WebElement row = table.getRowMatchingData(rowData);

    if (row == null) {
      throw new Exception("Unable to find row");
    }

    row.findElement(By.linkText("Edit")).click();
  }

  /**
   * Determine if row matching given data is present.
   *
   * @param rowData map of data for finding a row
   * @return true if row is found, false otherwise
   */
  public boolean isRowByRowDataPresent(Map<String, String> rowData) {
    return getRowByRowData(rowData) != null;
  }

  /**
   * Toggle selection of row matching given data.
   *
   * @param rowData map of data for finding a row
   */
  public void toggleRowByRowData(Map<String, String> rowData) throws Exception {
    WebElement row = getRowByRowData(rowData);

    if (row == null) {
      throw new Exception("Unable to find row");
    }

    row.findElement(By.className("select-checkbox")).click();
  }

  /**
   * Return if the row matching given data has been selected or not.
   *
   * @return if the row has been selected or not
   * @throws Exception if row has not been found
   */
  public boolean isRowSelected(Map<String, String> rowData) throws Exception {
    WebElement row = getRowByRowData(rowData);

    if (row == null) {
      throw new Exception("Unable to find row");
    }

    List<String> classNames = Arrays.asList(row.getAttribute("class").split(" "));

    return classNames.contains("selected");
  }

  public void clickDelete() {
    customSettingsDeleteBtn.click();
  }

  /**
   * Click the next page button.
   */
  public void clickNext() {
    WebElement currentPageNumber = customSettingsWrapper.findElement(
        By.cssSelector(".paginate_button.current"));

    String pageNumber = currentPageNumber.getText();

    customSettingsWrapper.findElement(By.linkText("Next")).click();

    wait.until(
        ExpectedConditions.not(
            ExpectedConditions.textToBe(
                By.cssSelector("#custom_prop_settings_wrapper .paginate_button.current"), pageNumber
            )
        )
    );
  }

  private void clickFilter(String buttonName, int numberOfRows) {
    customSettingsWrapper
        .findElement(By.cssSelector("label[for='filter_button_" + buttonName + "']")).click();

    wait.until(ExpectedConditions
        .textMatches(By.cssSelector("#custom_prop_settings_wrapper .dataTables_info"),
            Pattern.compile("Showing 1 to \\d+ of " + numberOfRows + " entries")));
  }

  /**
   * Click the all filter button.
   */
  public void clickAllFilter(int numberOfRows) {
    clickFilter("all", numberOfRows);
  }

  /**
   * Click the selected filter button.
   *
   * @param numSelected Number of rows selected
   */
  public void clickSelectedFilter(int numSelected) {
    clickFilter("selected", numSelected);
  }

  /**
   * Click the not selected filter button.
   *
   * @param numNotSelected Number of rows not selected
   */
  public void clickNotSelectedFilter(int numNotSelected) {
    clickFilter("not_selected", numNotSelected);
  }

  /**
   * Check to see if alert confirmation is present on the page.
   *
   * @return true if present
   */
  public boolean isAlertPresent() {
    try {
      wait.until(ExpectedConditions.alertIsPresent());

      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public String getAlertText() {
    Alert alert = wait.until(ExpectedConditions.alertIsPresent());

    return alert.getText();
  }

  /**
   * Wait until the loading label for the custom settings table is no longer present in the DOM.
   */
  private void waitUntilCustomSettingsTableIsLoaded() {
    List<WebElement> loadingLabel = customSettingsTable.findElements(
        By.xpath(".//td[" + xPathMatchClassName("dataTables_empty")
            + " and starts-with(text(), 'Loading...')]"));

    if (loadingLabel.isEmpty()) {
      return;
    }

    wait.until(ExpectedConditions.stalenessOf(loadingLabel.get(0)));
  }

  private List<WebElement> getCustomSettingsRows() {
    return customSettingsTable.findElements(By.cssSelector("tbody tr[role='row']"));
  }

  private boolean isElementWithTextPresent(WebElement context, String cssSelector, String text) {
    WebElement element = getElementBySelectorIfPresent(context, By.cssSelector(cssSelector));
    if (element == null) {
      return false;
    }

    return element.getText().equals(text);
  }

  private WebElement getFormField(PaymentType paymentType, PaymentMethod paymentMethod,
      String attributeName) throws Exception {
    String id = paymentType.getValue() + "_" + paymentMethod.getValue() + "_" + attributeName;
    List<WebElement> elements = driver.findElements(By.id(id));
    if (elements.isEmpty()) {

      throw new Exception("Element not found");
    }

    return elements.get(0);
  }

  /**
   * Change the input element from number to text and remove required attribute.
   */
  private void disableValidationInput(String id) {
    JavascriptExecutor js = (JavascriptExecutor) driver;

    String script = "document.getElementById('" + id + "').setAttribute('type', 'text');";
    js.executeScript(script);

    script = "document.getElementById('" + id + "').removeAttribute('required');";
    js.executeScript(script);
  }

  private WebElement getRowByRowData(Map<String, String> rowData) {
    AdminFlexibleFeeCustomSettings table = new AdminFlexibleFeeCustomSettings(customSettingsTable);

    return table.getRowMatchingData(rowData);
  }
}
