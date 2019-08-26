package com.paylease.app.qa.framework.pages.pm;

import com.paylease.app.qa.framework.components.datatable.PmDeposits;
import com.paylease.app.qa.framework.components.datatable.PmDeposits.Columns;
import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DepositsPage extends PageBase {

  private static final String URL = BASE_URL + "pm/deposits";
  private PmDeposits dataTable;

  @FindBy(name = "search_prop")
  private WebElement propertyInputTextBox;

  @FindBy(id = "submit_button")
  private WebElement submitButton;

  @FindBy(id = "deposits")
  private WebElement depositList;

  // ********************************************Action*********************************************

  /**
   * Opens the page.
   */
  public void open() {
    openAndWait(URL);
  }

  /**
   * Enter search term for property.
   *
   * @param searchTerm text to enter.
   */
  public void enterPropertySearch(String searchTerm) {
    enterText(propertyInputTextBox, searchTerm);
  }

  /**
   * Click submit button.
   */
  public void submitSearch() {
    clickAndWait(submitButton);
    dataTable = new PmDeposits(depositList);
  }

  /**
   * Determine if the given transaction ID is in the table.
   *
   * @param transId Property ref ID to find
   * @return True if property ref ID is found
   */
  public boolean isTransactionPresent(String transId) {
    try {
      dataTable.getRowByCellText(transId, Columns.TRANS_NUM.getLabel());
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }
}
