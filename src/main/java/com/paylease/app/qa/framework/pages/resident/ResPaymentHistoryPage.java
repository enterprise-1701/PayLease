package com.paylease.app.qa.framework.pages.resident;

import com.paylease.app.qa.framework.components.datatable.ResPaymentHistory;
import com.paylease.app.qa.framework.components.datatable.ResPaymentHistory.Columns;
import com.paylease.app.qa.framework.pages.PageBase;
import java.util.HashMap;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ResPaymentHistoryPage extends PageBase {

  private static final String URL = BASE_URL + "resident/payment_history";
  private static final String VOID_ERROR_MESSAGE = "A payment has already been initiated.";
  private static final String VOID_LINK_ID = "resident_transaction_void_link_";
  ResPaymentHistory resPaymentHistoryTable;

  @FindBy(id = "end_date")
  private WebElement endDateBox;

  @FindBy(id = "submit_search")
  private WebElement submitButton;

  @FindBy(id = "trans_tbl")
  private WebElement transactionTable;

  // ********************************************Action*********************************************

  /**
   * clicks the Void link for the given transaction id.
   *
   * @param transactionId test transaction id
   */
  public void clickVoidLink(String transactionId) {
    WebElement voidLink = driver.findElement(By.id(VOID_LINK_ID + transactionId));
    voidLink.click();
  }

  /**
   * searches the page for the void link for the given transaction id.
   *
   * @param transactionId test transaction id
   * @return true if the void link is found or false otherwise
   */
  public boolean hasVoidLink(String transactionId) {
    skipTestIfBrScriptIsRunning();

    return isElementPresentBySelector(By.id(VOID_LINK_ID + transactionId));
  }

  /**
   * searches the page for the void error message.
   *
   * @return true if the void error message is found or false otherwise
   */
  public boolean isVoidErrorDisplayed() {
    List<WebElement> errorMessages = driver.findElements(By.className("error_server"));
    for (WebElement element : errorMessages) {
      if (element.getText().contains(VOID_ERROR_MESSAGE)) {
        return true;
      }
    }

    return false;
  }

  public void open() {
    openAndWait(URL);
  }

  /**
   * Click details opens new window and then switch to new window.
   *
   * @param transactionId transactionId
   * @return ResPaymentHistoryReceiptPage
   */
  public ResPaymentHistoryReceiptPage clickDetails(String transactionId) {

    WebElement tableCell = driver
        .findElement(By.xpath("//td[contains(text(),'" + transactionId + "')]"));
    WebElement paymentHistoryTableRow = tableCell.findElement(By.xpath(".."));
    WebElement paymentHistoryDetailsLink = paymentHistoryTableRow
        .findElement(By.linkText("Details"));

    paymentHistoryDetailsLink.click();

    ResPaymentHistoryReceiptPage residentPaymentHistoryReceiptPage = new ResPaymentHistoryReceiptPage(
        transactionId);

    for (String winHandle : driver.getWindowHandles()) {
      driver.switchTo().window(winHandle);

      if (driver.getCurrentUrl().equals(residentPaymentHistoryReceiptPage.getUrl())) {
        break;
      }
    }

    return residentPaymentHistoryReceiptPage;

  }

  /**
   * Enter Date and Submit.
   */
  public void enterDateAndSubmit(String date) {

    highlight(endDateBox);
    endDateBox.click();

    setDateFieldValue("end_date", date);

    highlight(submitButton);
    submitButton.click();
  }

  public boolean pageIsLoaded() {
    return getTitle().equals("PAYMENT HISTORY")
        && isElementPresentBySelector(By.id("trans_tbl"));
  }

  /**
   *  Get transaction status.
   *
   * @param transactionId transaction id
   * @param billType bill type ex. lease payment, total payment, application fee
   * @return String status/"No matching row found." if no match found
   */
  public String getStatusByIdAndBillType(String transactionId, String billType) {
    resPaymentHistoryTable = new ResPaymentHistory(transactionTable);

    List<WebElement> transactionRows = resPaymentHistoryTable
        .getRowsByCellText(transactionId, Columns.TRANS_NUM.getLabel());

    for (WebElement row : transactionRows) {
      HashMap<String, String> tableRowValues = resPaymentHistoryTable.getMapOfTableRow(row);
      if (tableRowValues.get(Columns.BILL_TYPE.getLabel().toLowerCase())
          .equalsIgnoreCase(billType)) {
        return tableRowValues.get(Columns.STATUS.getLabel());
      }
    }
    return "No matching row found.";
  }
}
