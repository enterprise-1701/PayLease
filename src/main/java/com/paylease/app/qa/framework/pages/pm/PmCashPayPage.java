package com.paylease.app.qa.framework.pages.pm;

import com.paylease.app.qa.framework.components.ElasticSearch;
import com.paylease.app.qa.framework.components.datatable.PmCashPay;
import com.paylease.app.qa.framework.components.datatable.PmCashPay.Columns;
import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PmCashPayPage extends PageBase {

  private static final String URL = BASE_URL + "pm/cash_pay";
  private PmCashPay dataTable;

  @FindBy(id = "submit_btn")
  private WebElement submitButton;

  @FindBy(name = "search_prop")
  private WebElement searchPropTextInputBox;

  @FindBy(name = "search_res")
  private WebElement searchResTextInputBox;

  @FindBy(id = "cashpay_list")
  private WebElement cashpayList;

  // ********************************************Action*********************************************
  public void open() {
    openAndWait(URL);
  }

  /**
   * Enter search term for resident.
   *
   * @param searchTerm text to enter.
   */
  public void enterResidentSearch(String searchTerm) {
    enterText(searchResTextInputBox, searchTerm);
  }

  /**
   * Enter search term for property.
   *
   * @param searchTerm text to enter.
   */
  public void enterPropertySearch(String searchTerm) {
    enterText(searchPropTextInputBox, searchTerm);
  }

  /**
   * Click go button.
   */
  public void submitSearch() {
    clickAndWait(submitButton);
    dataTable = new PmCashPay(cashpayList);
  }

  /**
   * Click correct item in type ahead dropdown.
   *
   * @param searchTerm term to look for in dropdown list
   */
  public void clickDropDown(String searchTerm) {
    ElasticSearch elasticSearch = new ElasticSearch();
    elasticSearch.clickDropDown(searchTerm);

    dataTable = new PmCashPay(cashpayList);
  }

  /**
   * Determine if the given cash pay number is in the table.
   *
   * @param epsAccountId eps account ID to find
   * @return True if eps account ID is found
   */
  public boolean isCardPresent(String epsAccountId) {
    try {
      dataTable.getRowByCellText(epsAccountId, Columns.CASH_PAY_NUMBER.getLabel());
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }
}
