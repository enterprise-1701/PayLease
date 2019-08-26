package com.paylease.app.qa.framework.pages.pm;

import com.paylease.app.qa.framework.components.ElasticSearch;
import com.paylease.app.qa.framework.components.datatable.PmViewProperties;
import com.paylease.app.qa.framework.components.datatable.PmViewProperties.Columns;
import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ViewPropertiesPage extends PageBase {

  private static final String URL = BASE_URL + "pm/prop_list";
  private PmViewProperties dataTable;

  @FindBy(id = "advanced_search")
  private WebElement advancedSearchLink;

  @FindBy(id = "basic_prop_search")
  private WebElement basicSearchInputTextBox;

  @FindBy(id = "basic_search_btn_go")
  private WebElement basicSearchGoBtn;

  @FindBy(name = "search_prop")
  private WebElement propertyNameInputTextBox;

  @FindBy(id = "submit_adv_search")
  private WebElement submitAdvSearchBtn;

  @FindBy(css = ".full_tbl")
  private WebElement propertyList;

  public void open() {
    openAndWait(URL);
  }

  public void openAdvancedSearch() {
    advancedSearchLink.click();
    wait.until(ExpectedConditions.visibilityOf(submitAdvSearchBtn));
  }

  /**
   * Enter search term for property.
   *
   * @param searchTerm text to enter.
   */
  public void enterPropertyNameSearch(String searchTerm) {
    enterText(propertyNameInputTextBox, searchTerm);
  }

  /**
   * Enter search term for basic search.
   *
   * @param searchTerm text to enter.
   */
  public void enterBasicSearch(String searchTerm) {
    enterText(basicSearchInputTextBox, searchTerm);
  }

  /**
   * Click submit button.
   */
  public void submitAdvancedSearch() {
    clickAndWait(submitAdvSearchBtn);
    dataTable = new PmViewProperties(propertyList);
  }

  /**
   * Click go button.
   */
  public void submitBasicSearch() {
    clickAndWait(basicSearchGoBtn);
    dataTable = new PmViewProperties(propertyList);
  }

  /**
   * Determine if the given property ref ID is in the table.
   *
   * @param refId Property ref ID to find
   * @return True if property ref ID is found
   */
  public boolean isPropertyPresent(String refId) {
    try {
      dataTable.getRowByCellText(refId, Columns.REF_ID.getLabel());
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  /**
   * Click correct item in type ahead dropdown.
   *
   * @param searchTerm Term to search for in dropdown
   */
  public void clickDropDown(String searchTerm) {
    ElasticSearch elasticSearch = new ElasticSearch();
    elasticSearch.clickDropDown(searchTerm);

    dataTable = new PmViewProperties(propertyList);
  }
}
