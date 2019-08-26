package com.paylease.app.qa.framework.pages.admin;

import com.paylease.app.qa.framework.components.datatable.AdminFlexibleFeeCustomSettingsAdd;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class FlexibleFeeCustomSettingAddPage extends FlexibleFeeAddAndEdit {

  private static final String URL_TEMPLATE =
      BASE_URL + "admin/flexible_fee_structure/custom_setting/{pmId}";

  private static final String[] PROPERTIES_TABLE_COLUMN_NAMES = {
      "Property ID",
      "Property Name/Address",
      "City",
      "State",
      "Zip",
  };

  @FindBy(css = ".admin_container h3")
  private WebElement formTitle;

  @FindBy(id = "any_property")
  private WebElement anyPropertyCheckbox;

  @FindBy(id = "properties_wrapper")
  private WebElement propertiesWrapper;

  @FindBy(id = "properties")
  private WebElement propertiesTable;

  /**
   * The constructor.
   *
   * @param pmId the PM the fee belongs to
   */
  public FlexibleFeeCustomSettingAddPage(String pmId) {
    url = URL_TEMPLATE.replace("{pmId}", pmId);
    this.pmId = pmId;
  }

  /**
   * Open the page and wait for the custom settings table to load.
   */
  public void open() {
    super.open();

    waitUntilPropertiesTableIsLoaded();
  }

  /**
   * Wait until the loading label for the custom settings table is no longer present in the DOM.
   */
  private void waitUntilPropertiesTableIsLoaded() {
    List<WebElement> loadingLabel = propertiesTable.findElements(
        By.xpath(".//td[" + xPathMatchClassName("dataTables_empty")
            + " and starts-with(text(), 'Loading...')]"));

    if (loadingLabel.isEmpty()) {
      return;
    }

    wait.until(ExpectedConditions.stalenessOf(loadingLabel.get(0)));
  }

  /**
   * Page is loaded if the form contains a hidden pmId and "Add Custom Setting" title.
   *
   * @return true if the form contains a hidden pmId and "Add Custom Setting" title
   */
  public boolean pageIsLoaded() {
    try {
      WebElement hiddenPmId = form.findElement(By.name("pm_id"));

      return hiddenPmId.getAttribute("value").equals(pmId)
          && formTitle.getText().equals("Add Custom Setting");
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * Check the all properties check box if unchecked.
   */
  public void setAnyPropertyCheckBox() {
    if (!isAnyPropertyChecked()) {
      anyPropertyCheckbox.click();
    }
  }

  /**
   * Uncheck the all properties check box if checked.
   */
  public void clearAnyPropertyCheckBox() {
    if (isAnyPropertyChecked()) {
      anyPropertyCheckbox.click();
    }
  }

  /**
   * Checks if any property checkbox is checked.
   *
   * @return true if the checkbox is checked, false otherwise
   */
  public boolean isAnyPropertyChecked() {
    return anyPropertyCheckbox.isSelected();
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
   * Check to see if properties search is present on top of table.
   *
   * @return true if properties search is present on top of table
   */
  public boolean isPropertiesTableSearchPresent() {
    WebElement label = getElementBySelectorIfPresent(propertiesWrapper,
        By.cssSelector(".top #properties_filter label"));
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
   * Check to see if properties pagination is present on top and bottom of table.
   *
   * @return true if properties pagination is present on top and bottom of table
   */
  public boolean isPropertiesTablePaginationPresent() {
    return isElementWithTextPresent(propertiesWrapper,
        ".top .dataTables_paginate a.paginate_button.previous", "Previous")
        || isElementWithTextPresent(propertiesWrapper,
        ".top .dataTables_paginate a.paginate_button.next", "Next")
        || isElementWithTextPresent(propertiesWrapper,
        ".bottom .dataTables_paginate a.paginate_button.previous", "Previous")
        || isElementWithTextPresent(propertiesWrapper,
        ".bottom .dataTables_paginate a.paginate_button.next", "Next");
  }

  /**
   * Check to see if properties page size selector is present on top of table.
   *
   * @return true if properties page size selector is present on top of table
   */
  public boolean isPropertiesTablePageSizeSelectorPresent() {
    WebElement pageSizeElement = getElementBySelectorIfPresent(propertiesWrapper,
        By.cssSelector(".top #properties_length"));
    if (pageSizeElement == null) {
      return false;
    }

    return pageSizeElement.getText().equals("Show\n10\n25\n50\n100\nentries");
  }

  /**
   * Check to see if properties table info label is present on bottom of table.
   *
   * @return true if properties table info label is present on bottom of table
   */
  public boolean isPropertiesTableInfoLabelPresent() {
    WebElement tableInfo = getElementBySelectorIfPresent(propertiesWrapper,
        By.cssSelector(".bottom #properties_info"));
    if (tableInfo == null) {
      return false;
    }

    return tableInfo.getText().startsWith("Showing");
  }

  /**
   * Check to see if properties table header is present.
   *
   * @return true if properties table header is present
   */
  public boolean isPropertiesTableHeaderPresent() {
    String cssSelector = "thead tr th";
    List<WebElement> tableHeaderColumns = propertiesTable
        .findElements(By.cssSelector(cssSelector));
    if (tableHeaderColumns.size() == 0) {
      return false;
    }

    ArrayList<String> columns = new ArrayList<>(Arrays.asList(PROPERTIES_TABLE_COLUMN_NAMES));

    for (WebElement th : tableHeaderColumns) {
      String columnText = th.getText();

      if (columns.contains(columnText)) {
        columns.remove(columnText);
      }
    }

    return columns.isEmpty();
  }

  /**
   * Check to see if properties table header allows sorting.
   *
   * @return true if properties table header allows sorting
   */
  public boolean doesPropertiesTableHeaderAllowSorting() {
    return !propertiesTable.findElements(By.cssSelector("thead tr th[aria-controls]"))
        .isEmpty();
  }

  /**
   * Get the label of properties filter buttons.
   *
   * @return List of properties filter buttons labels
   */
  public List<String> getPropertiesSelectFilterButtons() {
    List<WebElement> buttonElements = propertiesWrapper
        .findElements(By.cssSelector(".top .ui-buttonset label"));

    List<String> buttonLabels = new ArrayList<>();

    for (WebElement element : buttonElements) {
      buttonLabels.add(element.getText());
    }

    return buttonLabels;
  }

  /**
   * Get a List of Maps of row data for the entire (visible) Properties Table.
   *
   * @return List of Maps of row data
   */
  public List<Map<String, String>> getPropertiesTableRowContent() {
    ArrayList<Map<String, String>> tableRowData = new ArrayList<>();

    AdminFlexibleFeeCustomSettingsAdd table = new AdminFlexibleFeeCustomSettingsAdd(
        propertiesTable
    );

    List<WebElement> tableRows = getCustomSettingsRows();

    for (WebElement tableRow : tableRows) {
      tableRowData.add(table.getMapOfTableRow(tableRow));
    }

    return tableRowData;
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
   * Returns the number of rows in the custom settings table.
   *
   * @return number of rows in the custom settings table
   */
  public int getPropertiesTableRowCount() {
    return getPropertiesRows().size();
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
   * Returns the total number of custom settings rows reported by the custom settings datatable.
   *
   * @return total number of custom settings rows
   */
  public int getTotalNumberOfProperties() {
    String labelText =
        propertiesWrapper.findElement(By.cssSelector(".dataTables_info")).getText();

    Pattern pattern = Pattern.compile("Showing 1 to \\d+ of (\\d+) entries");
    Matcher matcher = pattern.matcher(labelText);

    matcher.find();

    return Integer.parseInt(matcher.group(1));
  }

  @Override
  public void setField(FormFields fieldName, String value) {
    super.setField(fieldName, value);
    if (fieldName == FormFields.PROPERTY_NAME) {
      setProperty(value);
    }
  }

  /**
   * Select desired properties.
   *
   * @param value properties to set
   */
  public void setProperty(String value) {
    if (value.isEmpty()) {
      clearAnyPropertyCheckBox();
    }
  }

  /**
   * Select all rows visible in the table.
   */
  public void selectAllVisibleProperties() {
    List<WebElement> checkBoxes = propertiesWrapper
        .findElements(By.cssSelector("tr:not(.selected) .select-checkbox"));

    for (WebElement checkBox : checkBoxes) {
      checkBox.click();
    }
  }

  private List<WebElement> getPropertiesRows() {
    return propertiesTable.findElements(By.cssSelector("tbody tr[role='row']"));
  }

  private void clickFilter(String buttonName, int numberOfRows) {
    propertiesWrapper.findElement(By.cssSelector("label[for='filter_button_" + buttonName + "']"))
        .click();

    wait.until(ExpectedConditions
        .textMatches(By.cssSelector("#properties_wrapper .dataTables_info"),
            Pattern.compile("Showing 1 to \\d+ of " + numberOfRows + " entries")));
  }

  private List<WebElement> getCustomSettingsRows() {
    return propertiesTable.findElements(By.cssSelector("tbody tr[role='row']"));
  }

  private WebElement getRowByRowData(Map<String, String> rowData) {
    AdminFlexibleFeeCustomSettingsAdd table = new AdminFlexibleFeeCustomSettingsAdd(
        propertiesTable
    );

    return table.getRowMatchingData(rowData);
  }

  private boolean isElementWithTextPresent(WebElement context, String cssSelector, String text) {
    WebElement element = getElementBySelectorIfPresent(context, By.cssSelector(cssSelector));
    if (element == null) {
      return false;
    }

    return element.getText().equals(text);
  }
}
