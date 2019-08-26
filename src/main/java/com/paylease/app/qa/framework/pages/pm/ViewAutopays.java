package com.paylease.app.qa.framework.pages.pm;

import com.paylease.app.qa.framework.components.DataTable;
import com.paylease.app.qa.framework.components.ElasticSearch;
import com.paylease.app.qa.framework.components.datatable.PmViewFap;
import com.paylease.app.qa.framework.pages.PageBase;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class ViewAutopays extends PageBase {

  public enum Action {
    CANCEL("Cancel"), SKIP("Skip"), CANCEL_SKIP("Cancel Skip"), EDIT("Edit");

    private String linkText;

    Action(String linkText) {
      this.linkText = linkText;
    }

    public String getLinkText() {
      return linkText;
    }
  }

  private static final String EXPORT_FILE_NAME = "autopays.xls";

  @FindBy(id = "createAutopay")
  private WebElement createAutoPayButton;

  @FindBy(className = "page_title")
  WebElement pageTitle;

  @FindBy(css = "table.full_tbl")
  WebElement dataTableElement;

  @FindBy(name = "submit_export")
  private WebElement exportLink;

  @FindBy(name = "search_prop")
  private WebElement searchPropTextInputBox;

  @FindBy(name = "search_res")
  private WebElement searchResTextInputBox;

  @FindBy(name = "submit_ap_search")
  private WebElement submitButton;

  @FindBy(name = "ap_status")
  private WebElement statusDropDown;

  @FindBy(className = "refId")
  private WebElement refId;

  DataTable dataTable;

  /**
   * Click on Create AutoPay button.
   */
  public void clickCreateAutoPayButton() {
    highlight(createAutoPayButton);
    clickAndWait(createAutoPayButton);
  }

  /**
   * Click on the Excel export link. Wait for the file to be downloaded.
   *
   * @param downloadPath Download path set up on browser driver
   */
  public void clickExport(String downloadPath) {
    exportLink.click();
    waitForFileToDownload(downloadPath, EXPORT_FILE_NAME);
  }

  public String getSuccessMessage() {
    return getTextBySelector(By.className("success_message"));
  }

  /**
   * Check if an alert is present.
   *
   * @return true if the alert is present
   */
  public boolean isConfirmCancelDialogDisplayed() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  public String getConfirmCancelMessage() {
    return driver.switchTo().alert().getText();
  }

  public void dismissAlert() {
    driver.switchTo().alert().dismiss();
  }

  public void acceptAlert() {
    driver.switchTo().alert().accept();
  }

  /**
   * Click on the given action link in the nth row of the table.
   *
   * @param index Row number of the row in the table to act on
   * @param rowAction Action to perform
   */
  public void clickRowActionByRowNum(int index, Action rowAction) {
    WebElement row = dataTable.getRowByRowNum(index);

    boolean needWait = true;
    if (rowAction == Action.CANCEL) {
      needWait = false;
    }
    clickRowAction(row, rowAction, needWait);
  }

  /**
   * Determine if the given row, by index, has the desired action available.
   *
   * @param index Row number of the row in the table to check
   * @param rowAction Action to check is available
   * @return True if the given row has a link to perform the given action
   */
  public boolean rowHasActionByRowNum(int index, Action rowAction) {
    WebElement row = dataTable.getRowByRowNum(index);

    return rowHasAction(row, rowAction);
  }

  private boolean rowHasAction(WebElement row, Action action) {
    List<WebElement> actionLinks = row.findElements(By.linkText(action.getLinkText()));

    return !actionLinks.isEmpty();
  }

  private void clickRowAction(WebElement row, Action action, boolean wait) {
    WebElement actionLink = row.findElement(By.linkText(action.getLinkText()));
    if (wait) {
      clickAndWait(actionLink);
    } else {
      actionLink.click();
    }
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
   * Click correct item in type ahead dropdown.
   *
   * @param searchTerm Term to search for in dropdown
   */
  public void clickDropDown(String searchTerm) {
    ElasticSearch elasticSearch = new ElasticSearch();
    elasticSearch.clickDropDown(searchTerm);

    dataTable = new PmViewFap(dataTableElement);
  }

  /**
   * Click go button.
   */
  public void submitSearch() {
    clickAndWait(submitButton);
    dataTable = new PmViewFap(dataTableElement);
  }

  /** Get refID value. */
  public String getRefIdText() {
    return refId.getText();
  }

  /**
   * Select Autopay status.
   *
   * @param option to select
   */
  public void selectAutopayStatusOption(String option) {
    Select select = new Select(
        driver.findElement(By.name("ap_status")));
    select.selectByValue(option);
  }
}
