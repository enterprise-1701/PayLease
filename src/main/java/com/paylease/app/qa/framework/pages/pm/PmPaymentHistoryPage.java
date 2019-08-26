package com.paylease.app.qa.framework.pages.pm;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.components.ElasticSearch;
import com.paylease.app.qa.framework.components.datatable.PmPaymentHistory;
import com.paylease.app.qa.framework.components.datatable.PmPaymentHistory.Columns;
import com.paylease.app.qa.framework.pages.PageBase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PmPaymentHistoryPage extends PageBase {

  private static final String URL = BASE_URL + "pm/pm_payment_history";
  private static final String VOID_ERROR_MESSAGE = "Failed to void payment";
  private static final String VOID_LINK_ID = "pm_transaction_void_link_";
  private static final String REFUND_LINK_ID = "refund_link_";
  private PmPaymentHistory dataTable;

  public static final String REFUND_TYPE_FULL = "Full Refund";
  public static final String REFUND_TYPE_PARTIAL_MAX = "Max amount for partial refund";
  public static final String REFUND_TYPE_PARTIAL_PARTIAL = "Partial amount of partial refund";
  public static final String REFUND_TYPE_PARTIAL_SINGLE_FIELD = "Partial amount of a single payment field";
  public static final String REFUND_TYPE_PARTIAL_OVER_LIMIT = "Over the limit amount for partial "
      + "refund";

  @FindBy(id = "end_date")
  private WebElement endDateBox;

  @FindBy(id = "start_date")
  private WebElement startDateBox;

  @FindBy(name = "submit_filters")
  private WebElement submitButton;

  @FindBy(css = "#CREATE_VARIABLE_AUTOPAY .payment_link")
  private WebElement createVariableAutoPayButton;

  @FindBy(css = "#CREATE_FIXED_AUTOPAY .payment_link")
  private WebElement createFixedAutoPayButton;

  @FindBy(css = "#ONE_TIME_PAYMENT_PM .payment_link")
  private WebElement createOneTimePaymentButton;

  @FindBy(name = "resident_id")
  private WebElement residentId;

  @FindBy(name = "search_prop")
  private WebElement searchPropTextInputBox;

  @FindBy(name = "search_res")
  private WebElement searchResTextInputBox;

  @FindBy(id = "trans_list")
  private WebElement transactionList;

  // ********************************************Action*********************************************

  /**
   * Clicks the VOID link for the given transaction id.
   *
   * @param transactionId test transaction id
   */
  public void clickVoidLink(String transactionId) {
    WebElement voidLink = driver.findElement(By.id(VOID_LINK_ID + transactionId));
    voidLink.click();
  }

  /**
   * Searches the page for the void link for the given transaction id.
   *
   * @param transactionId test transaction id
   * @return true if the void link is found or false otherwise
   */
  public boolean hasVoidLink(String transactionId) {
    skipTestIfBrScriptIsRunning();

    return isElementPresentBySelector(By.id(VOID_LINK_ID + transactionId));
  }

  /**
   * Searches the page for the void error message.
   *
   * @return true if the void error message is found or false otherwise
   */
  public boolean isVoidErrorDisplayed() {
    List<WebElement> errorMessages = driver.findElements(By.className("error_server"));
    for (WebElement element : errorMessages) {
      if (element.getText().equals(VOID_ERROR_MESSAGE)) {
        return true;
      }
    }

    return false;
  }

  /**
   * Click refund link.
   *
   * @param transactionId transaction to refund
   */
  public void clickRefundLink(String transactionId) {
    WebElement transactionRow = dataTable
        .getRowByCellText(transactionId, Columns.TRANS_NUM.getLabel());
    highlight(transactionRow);
    transactionRow.findElement(By.cssSelector(".refund-btn")).click();
    wait.until(
        ExpectedConditions.presenceOfElementLocated(By.cssSelector("#refund-modal .modal-header"))
    );
  }

  /**
   * Click continue button on modal refund dialog.
   */
  public void confirmRefund() {
    clickAndWait(driver.findElement(By.cssSelector("#confirmation-notice .refund")));

    Logger.info("Confirm refund button clicked");
  }

  /**
   * Click initiate refund button on modal refund dialog.
   */
  public void initiateRefund() {
    WebElement initiateRefund = driver.findElement(By.cssSelector("#refund-modal .confirm"));

    initiateRefund.click();

    Logger.info("Initiate refund button clicked");
  }

  /**
   * Open payment history page.
   */
  public void open(boolean refundTutorialOptOut) {
    if (refundTutorialOptOut) {
      setCookieOptOutRefundTutorial();
    }
    openAndWait(URL);
    dataTable = new PmPaymentHistory(transactionList);
  }

  /**
   * Open and wait PM payment history page.
   *
   * @param residentId residentID
   * @param refundTutorialOptOut opt out if true
   */
  public void openWithResidentId(String residentId, boolean refundTutorialOptOut) {
    if (refundTutorialOptOut) {
      setCookieOptOutRefundTutorial();
    }

    openAndWait(URL + "?source=res_list&referrer=res_list&res_id=" + residentId);
  }

  /**
   * Get the value of the resident id from page.
   *
   * @return Text value
   */
  public String getResidentId() {
    highlight(residentId);
    return residentId.getAttribute("value");
  }

  /**
   * The page is loaded when the table appears.
   *
   * @return true if the table is present
   */
  public boolean pageIsLoaded() {
    return !driver.findElements(By.id("trans_list")).isEmpty();
  }

  /**
   * Click on Create Fixed AutoPay Tab.
   */
  public void clickCreateFixedAutoPayButton() {
    highlight(createFixedAutoPayButton);
    clickAndWait(createFixedAutoPayButton);

    Logger.trace("Create Fixed AutoPay Button clicked");
  }

  /**
   * Click on Create Variable AutoPay Tab.
   */
  public void clickCreateVariableAutoPayButton() {
    highlight(createVariableAutoPayButton);
    clickAndWait(createVariableAutoPayButton);

    Logger.trace("Create Variable AutoPay Button clicked");
  }

  /**
   * Click on Create One time payment button.
   */
  public void clickOnetimePaymentButton() {
    highlight(createOneTimePaymentButton);
    clickAndWait(createOneTimePaymentButton);

    Logger.trace("Create OTP Button clicked");
  }

  /**
   * Click the Transaction number.
   *
   * @param transactionNo transactionNo
   * @return PmPaymentHistoryReceiptPage
   */
  public PmPaymentHistoryReceiptPage clickTransactionNumber(String transactionNo) {

    WebElement paymentHistoryTransactionsLink = driver.findElement(By.linkText(transactionNo));
    paymentHistoryTransactionsLink.click();

    PmPaymentHistoryReceiptPage pmReceiptPage = new PmPaymentHistoryReceiptPage(transactionNo);

    for (String winHandle : driver.getWindowHandles()) {
      driver.switchTo().window(winHandle);

      if (driver.getCurrentUrl().equals(pmReceiptPage.getUrl())) {
        break;
      }
    }

    return pmReceiptPage;
  }

  /**
   * Enter Date and Submit.
   */
  public void enterDateAndSubmit(String date) {

    highlight(endDateBox);
    endDateBox.click();

    setDateFieldValue("end_date", date);

    highlight(submitButton);
    clickAndWait(submitButton);
  }

  /**
   * Enter Start Date and Submit.
   */
  public void enterStartDateAndSubmit(String date) {

    highlight(startDateBox);
    startDateBox.click();

    setDateFieldValue("start_date", date);

    highlight(submitButton);
    clickAndWait(submitButton);
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
    dataTable = new PmPaymentHistory(transactionList);
  }

  /**
   * Click correct item in type ahead drop down.
   *
   * @param searchTerm term to search for in dropdown
   */
  public void clickDropDown(String searchTerm) {
    ElasticSearch elasticSearch = new ElasticSearch();
    elasticSearch.clickDropDown(searchTerm);

    dataTable = new PmPaymentHistory(transactionList);
  }

  /**
   * Determine if the given Transaction ID is in the table.
   *
   * @param transId Trans ID to find
   * @return True if Trans ID is found
   */
  public boolean isTransactionPresent(String transId) {
    try {
      dataTable.getRowByCellText(transId, Columns.TRANS_NUM.getLabel());
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  /**
   * Return list of bill types for a given transaction.
   *
   * @param transId transaction to find
   * @return list of bill types
   */
  public ArrayList<String> getBillTypes(String transId) {
    List<WebElement> allTransRows = dataTable
        .getMultipleRowsByCellText(transId, Columns.TRANS_NUM.getLabel());

    ArrayList<String> billTypes = new ArrayList<>();

    for (WebElement row : allTransRows) {
      HashMap<String, String> rowData = dataTable.getMapOfTableRow(row);
      billTypes.add(rowData.get(Columns.BILL_TYPE.getLabel()));
    }

    return billTypes;
  }

  /**
   * Searches the page for the refund link for the given transaction id.
   *
   * @param transactionId test transaction id
   * @return true if the refund link is found or false otherwise
   */
  public boolean hasRefundLink(String transactionId, String paymentField) {
    return isElementPresentBySelector(By.id(REFUND_LINK_ID + transactionId + "_" + paymentField));
  }

  /**
   * Finds the refund link of the given transaction id and returns true if the transaction is
   * eligible for refund.
   *
   * @param transactionId id of transaction
   * @param paymentField payment field name ex. "lease_payment"
   * @return true if the transaction is eligible for refund
   */
  public boolean isRefundEligible(String transactionId, String paymentField) {
    WebElement refundLink = driver
        .findElement(By.id(REFUND_LINK_ID + transactionId + "_" + paymentField));
    return !refundLink.getAttribute("class").contains("refund-ineligible");
  }

  /**
   * Get the tooltip text that displays when hovering over the refund link of ineligible
   * transactions.
   *
   * @param transactionId id of transaction
   * @param paymentField payment field name ex. "lease_payment"
   * @return String tooltip message
   */
  public String getRefundLinkIneligibleMessage(String transactionId, String paymentField) {
    WebElement refundLink = driver
        .findElement(By.id(
            REFUND_LINK_ID + transactionId + "_" + paymentField.toLowerCase().replace(" ", "_")));
    Actions action = new Actions(driver);
    String qtipNumber = refundLink.getAttribute("data-hasqtip");
    action.moveToElement(refundLink).build().perform();
    wait.until(ExpectedConditions.presenceOfElementLocated(By.id("qtip-"+qtipNumber)));
    return driver.findElement(By.id("qtip-"+qtipNumber)).getText();
  }

  /**
   * Selects the full refund radio option on refund modal.
   */
  public void clickFullRefundOption() {
    wait.until(ExpectedConditions.elementToBeClickable(By.id("refund-total")));
    driver.findElement(By.id("refund-total")).click();
  }

  /**
   * Selects the partial refund radio option on refund modal.
   */
  public void clickPartialRefundOption() {
    driver.findElement(By.id("refund-partial")).click();
  }


  /**
   * Click the Initiate Refund button on Initiate Refund stage of refund modal.
   */
  public void clickInitiateRefund() {
    WebElement initiateRefundButton = driver.findElement(By.id("initiate_refund"));
    initiateRefundButton.click();
  }

  /**
   * Click the Continue button on Continue Refund stage of refund modal.
   */
  public void clickContinueRefund() {
    WebElement continueRefundButton = driver.findElement(By.id("continue_refund"));
    continueRefundButton.click();
  }

  /**
   * Accept a map of bank accounts to refund amounts and enters these amounts to the corresponding
   * input field.
   *
   * @param refundDetailsList List of HashMap of refund details. Each HashMap should have the keys
   * "account", "refundAmount" and "paymentAmount"
   */
  public void enterPartialRefundAmount(ArrayList<HashMap<String, String>> refundDetailsList) {
    WebElement refundInputElement;
    List<WebElement> partialRefundFormElements = driver
        .findElements(By.cssSelector("div.partial-form"));
    String partialRefundMessage;

    for (WebElement partialRefundForm : partialRefundFormElements) {
      partialRefundMessage = partialRefundForm.findElement(By.cssSelector("p.footnote")).getText();

      for (HashMap<String, String> refundDetails : refundDetailsList) {

        if (partialRefundMessage.contains(refundDetails.get("account")) && partialRefundMessage
            .contains(refundDetails.get("paymentAmount"))) {
          refundInputElement = partialRefundForm.findElement(By.cssSelector(
              "input[name='partial[" + partialRefundFormElements.indexOf(partialRefundForm)
                  + "]']"));
          refundInputElement.sendKeys(refundDetails.get("refundAmount"));
          break;
        }
      }
    }
  }

  /**
   * Return an arraylist of hashmaps, of refund confirmation details.
   */
  public ArrayList<HashMap<String, String>> getRefundConfirmationDetails() {
    ArrayList<HashMap<String, String>> refundConfirmationDetails = new ArrayList<>();

    List<WebElement> refundConfirmationTables = driver.findElements(By.className("refund-details"));

    for (WebElement table : refundConfirmationTables) {
      HashMap<String, String> detailsMap = new HashMap<>();
      detailsMap
          .put("paymentAmount",
              table.findElement(By.className("payment-amount")).getText().replace("$", ""));
      detailsMap.put("refundAmount",
          table.findElement(By.className("refund-amount")).getText().replace("$", ""));
      detailsMap
          .put("account", table.findElement(By.className("account")).getText().replace("#", ""));

      refundConfirmationDetails.add(detailsMap);
    }
    return refundConfirmationDetails;
  }

  /**
   * Click Cancel button on initiate refund stage of refund modal.
   */
  public void clickCancelInitiateRefund() {
    driver.findElement(By.id("cancel_initiate_refund")).click();
    Logger.info("Clicked 'Cancel' on Initiate Refund.");
  }

  /**
   * Click Cancel button on continue refund stage of refund modal.
   */
  public void clickCancelContinueRefund() {
    driver.findElement(By.id("cancel_continue_refund")).click();
    Logger.info("Clicked 'Cancel' on Continue Refund.");
  }

  /**
   * Get the status of a transaction given the transaction ID and bill type.
   *
   * @param transactionId id of transaction
   * @param billType usually the payment field name
   * @return String status
   */
  public String getStatusByIdAndBillType(String transactionId, String billType) {
    dataTable = new PmPaymentHistory(transactionList);
    List<WebElement> transactionRows = dataTable
        .getRowsByCellText(transactionId, Columns.TRANS_NUM.getLabel());

    for (WebElement row : transactionRows) {
      HashMap<String, String> tableRowValues = dataTable.getMapOfTableRow(row);
      if (tableRowValues.get(Columns.BILL_TYPE.getLabel().toLowerCase())
          .equalsIgnoreCase(billType)) {
        return tableRowValues.get(Columns.STATUS.getLabel());
      }
    }
    return "No matching row found.";
  }

  /**
   * Return a list of error messages from the partial refund inputs.
   *
   * @return ArrayList<> error messages
   */
  public ArrayList<String> getpartialRefundInputErrorMessages() {
    ArrayList<String> errorMessages = new ArrayList<>();

    List<WebElement> errorMessageElements = driver
        .findElements(By.cssSelector("label.error[for^='partial']"));
    for (WebElement e : errorMessageElements) {
      errorMessages.add(e.getText());
    }

    return errorMessages;
  }

  /**
   * Click Refund Button.
   */
  public void clickRefund() {
    driver.findElement(By.cssSelector("button.sm_gray_btn.refund")).click();
  }

}
