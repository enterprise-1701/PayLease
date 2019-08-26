package com.paylease.app.qa.framework.pages.pm;

import com.paylease.app.qa.framework.components.ElasticSearch;
import com.paylease.app.qa.framework.components.datatable.PmViewSubAccounts;
import com.paylease.app.qa.framework.components.datatable.PmViewSubAccounts.Columns;
import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ViewSubAccountsPage extends PageBase {

  private static final String URL = BASE_URL + "pm/sub_accounts";

  @FindBy(name = "search_sub")
  private WebElement subSearch;

  @FindBy(name = "search_prop")
  private WebElement propertySearch;

  @FindBy(name = "submit_filters")
  private WebElement submitSearchBtn;

  @FindBy(css = ".full_tbl")
  private WebElement subAccountsTable;

  private PmViewSubAccounts dataTable;

  /**
   * Return message after saving a sub-account.
   */
  public String getMessage() {
    return getTextBySelector(By.className("success_message"));
  }

  /**
   * Open url.
   */
  public void open() {
    openAndWait(URL);
    dataTable = new PmViewSubAccounts(subAccountsTable);
  }

  /**
   * Enter search term for property.
   *
   * @param searchTerm text to enter.
   */
  public void enterPropertySearch(String searchTerm) {
    enterText(propertySearch, searchTerm);
  }

  /**
   * Enter search term for sub-account.
   *
   * @param searchTerm text to enter
   */
  public void enterSubSearch(String searchTerm) {
    enterText(subSearch, searchTerm);
  }

  /**
   * Click submit button.
   */
  public void submitSearch() {
    clickAndWait(submitSearchBtn);
    dataTable = new PmViewSubAccounts(subAccountsTable);
  }

  /**
   * Select prop or sub account from type ahead dropdown.
   *
   * @param searchTerm Select correct dropdown item
   * @param propSearch either prop search or sub account search
   */
  public void clickDropDown(String searchTerm, boolean propSearch) {
    ElasticSearch elasticSearch = new ElasticSearch();
    elasticSearch.clickDropDown(searchTerm);
    if (propSearch) {
      submitSearch();
    }
    dataTable = new PmViewSubAccounts(subAccountsTable);
  }

  /**
   * Determine if the given sub account ID is in the table.
   *
   * @param subAccountId Sub Account ID to find
   * @return True if sub account ID is found
   */
  public boolean isSubAccountPresent(String subAccountId) {
    try {
      dataTable.getRowByCellText(subAccountId, Columns.ID_NUMBER.getLabel());
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }
}
