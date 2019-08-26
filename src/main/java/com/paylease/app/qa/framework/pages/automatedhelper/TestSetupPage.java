package com.paylease.app.qa.framework.pages.automatedhelper;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.pages.PageBase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class TestSetupPage extends PageBase {

  private static final String URL =
      BASE_URL + "testing/automated_helper/setup/{region}/{feature}/{testCase}";

  private String url;

  /**
   * Create a TestSetupPage for the give region, feature and testCase.
   *
   * @param region Region - area of code/UI for this test
   * @param feature Feature - feature for this test
   * @param testCase TestCase specific test case
   */
  public TestSetupPage(String region, String feature, String testCase) {
    super();
    this.url = URL.replace("{region}", region).replace("{feature}", feature)
        .replace("{testCase}", testCase);
  }

  // ********************************************Action*********************************************

  public void open() {
    openAndWait(url);
  }

  /**
   * Retrieve a String data value - default logger entry.
   *
   * @param key Key of the data element on the page
   * @return String value
   */
  public String getString(String key) {
    return getString(key, "");
  }

  /**
   * Retrieve a String data value with logger entry.
   *
   * @param key Key of the data element on the page
   * @param label Prefix for Logger entry
   * @return String value
   */
  public String getString(String key, String label) {
    WebElement testDataEl = driver
        .findElement(By.cssSelector("span.testData[data-type='String'][data-key='" + key + "']"));
    String data = testDataEl.getText();

    if (label.isEmpty()) {
      label = key;
    }
    Logger.trace(label + ": " + data);
    return data;
  }

  /**
   * Retrieve a boolean data value - default logger entry.
   *
   * @param key Key of the data element on the page
   * @return boolean value
   */
  public boolean getFlag(String key) {
    return getFlag(key, "");
  }

  /**
   * Retrieve a boolean data value with logger entry.
   *
   * @param key Key of the data element on the page
   * @param label Prefix for Logger entry
   * @return boolean value
   */
  public boolean getFlag(String key, String label) {
    WebElement testDataEl = driver
        .findElement(By.cssSelector("span.testData[data-type='Flag'][data-key='" + key + "']"));
    boolean data = testDataEl.getText().equals("ON");

    if (label.isEmpty()) {
      label = key;
    }
    Logger.trace(label + ": " + data);
    return data;
  }

  /**
   * Retrieve a table of data with default log entry.
   *
   * @param key Key of the data element on the page
   * @return List representing table
   */
  public List<HashMap<String, Object>> getTable(String key) {
    return getTable(key, "");
  }

  /**
   * Retrieve a table of data with specific log entry.
   *
   * @param key Key of the data element on the page
   * @param label Prefix for Logger entry
   * @return List representing table
   */
  public List<HashMap<String, Object>> getTable(String key, String label) {
    WebElement testDataEl = driver.findElement(By.cssSelector("#" + key + ".testDataTable"));
    List<HashMap<String, Object>> testDataTable = new ArrayList<>();
    List<WebElement> tableRows = testDataEl.findElements(By.className("testDataTableRow"));
    for (WebElement tableRow : tableRows) {
      HashMap<String, Object> rowValues = new HashMap<>();
      List<WebElement> tableValues = tableRow.findElements(By.className("testDataRowValue"));
      for (WebElement tableValue : tableValues) {
        if (tableValue.getAttribute("class").contains("onOffFlag")) {
          rowValues
              .put(tableValue.getAttribute("data-fieldName"), tableValue.getText().equals("ON"));
        } else {
          rowValues.put(tableValue.getAttribute("data-fieldName"), tableValue.getText());
        }
      }
      testDataTable.add(rowValues);
    }

    if (label.isEmpty()) {
      label = key;
    }
    Logger.trace(label + ": " + testDataTable);

    return testDataTable;
  }
}
