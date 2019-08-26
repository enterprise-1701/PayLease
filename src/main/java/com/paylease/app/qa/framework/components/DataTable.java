package com.paylease.app.qa.framework.components;

import com.paylease.app.qa.framework.pages.PageBase;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class DataTable extends PageBase {

  private WebElement table;
  private String rowIdPrefix = "";
  private HashMap<String, String> tableColumnMap;

  private HashMap<String, Integer> columnIndices;

  /**
   * Create a data table from a table web element and the prefix for identifying rows by id.
   *
   * @param table table WebElement
   * @param rowIdPrefix ids are appended to this string to get the unique ID in the document
   */
  public DataTable(WebElement table, String rowIdPrefix) {
    super();
    this.table = table;
    this.rowIdPrefix = rowIdPrefix;
    tableColumnMap = new HashMap<>();
    columnIndices = new HashMap<>();
  }

  public void addTableColumn(String key, String className) {
    tableColumnMap.put(key, className);
  }

  public WebElement getRowById(String id) {
    return table.findElement(By.id(rowIdPrefix + id));
  }

  /**
   * Get the nth row in the table.
   *
   * @param index Row number to retrieve
   * @return WebElement representing the nth row in the table
   */
  public WebElement getRowByRowNum(int index) {
    List<WebElement> tableRows = getTableRows();
    tableRows.remove(0); // skip the first/header row

    return tableRows.get(index);
  }

  /**
   * NOTE: Deprecated. Please use getRowsByCellText which returns a list, instead of this.
   * Find a row that contains the given text in the given column.
   *
   * @param text text to find
   * @param columnName name of the column to search
   * @return WebElement for the matching row
   */
  public WebElement getRowByCellText(String text, String columnName) {
    List<WebElement> matchingRows = getMultipleRowsByCellText(text, columnName);
    return matchingRows.get(0);
  }

  /**
   * Find rows that contain the given text in the given column.
   *
   * @param text text to find
   * @param columnName name of the column to search
   * @return WebElement for the matching row
   */
  public List<WebElement> getRowsByCellText(String text, String columnName) {
    return getMultipleRowsByCellText(text, columnName);
  }

  /**
   * Find a string in the given column.
   *
   * @param text text to find
   * @param columnName name of the column to search
   * @return Boolean if string is found
   */
  public Boolean isStringPresent(String text, String columnName) {
    List<WebElement> matchingRows = getMultipleRowsByCellText(text, columnName);
    return matchingRows.size() != 0;
  }

  /**
   * Find all rows that contain the given text in the given column.
   *
   * @param text text to find
   * @param columnName name of column to search
   * @return WebElement array for all of the matching rows
   */
  public List<WebElement> getMultipleRowsByCellText(String text, String columnName) {
    String className = tableColumnMap.get(columnName);
    if (className == null) {
      columnName = columnName.trim().toLowerCase();
      return table.findElements(By.xpath(
          ".//td[" + columnIndices.get(columnName) + "]/descendant-or-self::*[contains(text(), \""
              + text + "\")]/ancestor::tr"));
    } else {
      return table.findElements(By.xpath(
          ".//td[contains(@class, '" + className + "') and contains(text(), "
              + "\"" + text + "\")]/ancestor::tr"));
    }
  }

  /**
   * Get a map of values from the table for a given row.
   *
   * @param row Row to get from table
   * @return HashMap of values from the row
   */
  public HashMap<String, String> getMapOfTableRow(WebElement row) {
    HashMap<String, String> rowValues = new HashMap<>();
    if (tableColumnMap.isEmpty()) {
      for (Map.Entry<String, Integer> entry : columnIndices.entrySet()) {
        rowValues.put(entry.getKey(),
            row.findElement(By.xpath(".//td[" + entry.getValue() + "]")).getText());
      }
    } else {
      for (Map.Entry<String, String> entry : tableColumnMap.entrySet()) {
        rowValues.put(entry.getKey(), row.findElement(By.className(entry.getValue())).getText());
      }
    }
    return rowValues;
  }

  /**
   * Get the row that matches the given data.
   *
   * @param dataToFind Map of column values to find
   * @return WebElement for first matching row
   */
  public WebElement getRowMatchingData(Map<String, String> dataToFind) {
    List<WebElement> tableRows = getTableRows();
    tableRows.remove(0); // skip the first/header row

    for (WebElement row : tableRows) {
      HashMap<String, String> rowData = getRowData(row);

      boolean rowMatches = true;
      for (Map.Entry<String, String> dataEntry : dataToFind.entrySet()) {
        boolean containsKey = rowData.containsKey(dataEntry.getKey());
        boolean matchesVal = rowData.get(dataEntry.getKey()).equals(dataEntry.getValue());
        if (!rowData.containsKey(dataEntry.getKey()) || !rowData.get(dataEntry.getKey())
            .equals(dataEntry.getValue())) {
          rowMatches = false;
          break;
        }
      }
      if (rowMatches) {
        return row;
      }
    }
    return null;
  }

  protected void setColumnIndices(int headerRowNum) {
    WebElement headerRow = table
        .findElement(By.xpath(".//tr[" + String.valueOf(headerRowNum) + "]"));

    int i = 0;
    for (WebElement column : headerRow.findElements(By.xpath("./child::*"))) {
      i++;
      String columnHeading = column.getText().trim().toLowerCase();
      if (!columnHeading.isEmpty()) {
        columnIndices.put(columnHeading, i);
      }
    }
  }

  protected void setColumnIndices() {
    setColumnIndices(1);
  }

  public List<WebElement> getTableRows() {
    return table.findElements(By.tagName("tr"));
  }

  /**
   * Get the number of rows visible in the table, excluding the header row.
   *
   * @return count of rows
   */
  public int getVisibleRowCount() {
    return table.findElements(By.tagName("tr")).size() - 1; // exclude header row
  }

  private HashMap<String, String> getRowData(WebElement row) {
    HashMap<String, String> rowData = new HashMap<>();

    highlight(row);
    for (Map.Entry<String, Integer> columnEntry : columnIndices.entrySet()) {
      rowData.put(columnEntry.getKey(),
          row.findElement(By.xpath("./td[" + columnEntry.getValue().toString() + "]")).getText()
              .trim());
    }

    return rowData;
  }
}
