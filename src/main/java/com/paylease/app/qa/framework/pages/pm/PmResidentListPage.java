package com.paylease.app.qa.framework.pages.pm;

import com.paylease.app.qa.framework.components.ElasticSearch;
import com.paylease.app.qa.framework.components.datatable.PmViewResidents;
import com.paylease.app.qa.framework.components.datatable.PmViewResidents.Columns;
import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PmResidentListPage extends PageBase {

  private static final String URL = BASE_URL + "pm/res_list";
  public static final String RESIDENTS_LIST = "Residents list";

  private PmViewResidents dataTable;

  @FindBy(id = "advanced_search")
  private WebElement advancedSearchLink;

  @FindBy(name = "filter_name")
  private WebElement basicSearchInputTextBox;

  @FindBy(id = "basic_search_btn_go")
  private WebElement basicSearchGoBtn;

  @FindBy(name = "search_res")
  private WebElement residentNameInputTextBox;

  @FindBy(name = "search_prop")
  private WebElement propertyNameInputTextBox;

  @FindBy(id = "submit_adv_search")
  private WebElement submitAdvSearchBtn;

  @FindBy(id = "res_table")
  private WebElement residentList;

  // ********************************************Action*********************************************

  public void open() {
    openAndWait(URL);
  }

  public void openAdvancedSearch() {
    advancedSearchLink.click();
    wait.until(ExpectedConditions.visibilityOf(submitAdvSearchBtn));
  }

  /** Click submit button. */
  public void submitAdvancedSearch() {
    clickAndWait(submitAdvSearchBtn);
    dataTable = new PmViewResidents(residentList);
  }

  /**
   * Enter search term for resident.
   *
   * @param searchTerm text to enter.
   */
  public void enterResidentNameSearch(String searchTerm) {
    enterText(residentNameInputTextBox, searchTerm);
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

  /** Click go button. */
  public void submitBasicSearch() {
    clickAndWait(basicSearchGoBtn);
    dataTable = new PmViewResidents(residentList);
  }

  /**
   * Determine if the given resident ref ID is in the table.
   *
   * @param refId resident ref ID to find
   * @return True if resident ref ID is found
   */
  public boolean isResidentPresent(String refId) {
    try {
      dataTable.getRowByCellText(refId, Columns.ACCOUNT_NUM.getLabel());
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  /** Click on Make Payment. */
  public void clickMakePayment(String resId, boolean refundTutorialOptOut) {
    if (refundTutorialOptOut) {
      setCookieOptOutRefundTutorial();
    }

    WebElement tableCell = driver.findElement(By.id("resId_" + resId));
    WebElement resListTableRow = tableCell.findElement(By.xpath(".."));
    WebElement makePaymentLink = resListTableRow.findElement(By.linkText("Make Payment"));

    highlight(makePaymentLink);
    clickAndWait(makePaymentLink);
  }

  /**
   * Click correct item in type ahead dropdown for basic or advanced search.
   *
   * @param searchTerm Term to search for in dropdown
   * @param searchResident bool either search resident or prop
   */
  public void clickDropDown(String searchTerm, boolean searchResident) {
    ElasticSearch elasticSearch = new ElasticSearch();
    elasticSearch.clickDropDown(searchTerm);
    if (searchResident) {
      submitAdvancedSearch();
    }
    dataTable = new PmViewResidents(residentList);
  }
}
