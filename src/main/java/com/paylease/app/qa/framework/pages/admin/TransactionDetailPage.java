package com.paylease.app.qa.framework.pages.admin;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.components.datatable.AdminLogEntriesList;
import com.paylease.app.qa.framework.components.datatable.AdminRefundsList;
import com.paylease.app.qa.framework.components.datatable.AdminRefundsList.Columns;
import com.paylease.app.qa.framework.components.datatable.AdminTransactionDetailPaysafeList;
import com.paylease.app.qa.framework.pages.PageBase;
import java.util.HashMap;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

/**
 * This class represents the PL Admin Transaction Detail page for the given transaction.
 */
public class TransactionDetailPage extends PageBase {

  public static final String REFUND_ENTITY_RESIDENT = "resident";
  public static final String REFUND_ENTITY_PM = "pm";
  public static final String ADMIN_REFUND_MAX = "Max Refund Amount";
  public static final String ADMIN_REFUND_PARTIAL = "Partial Refund Amount";
  public static final String ADMIN_REFUND_OVER_MAX = "Over Max Refund Limit Amount";
  private static final String URL = BASE_URL + "admin2.php?action=transaction_detail"
      + "&trans_id={transactionId}";
  private static final String VOID_LINK_ID = "transaction_detail_void_link";
  private AdminRefundsList adminRefundsList;
  private AdminLogEntriesList adminLogEntriesList;
  private AdminTransactionDetailPaysafeList adminTransactionDetailPaysafeList;
  private String url;

  @FindBy(linkText = "Void/Refund")
  private WebElement refundLink;

  @FindBy(css = "input[title='Refund to Resident']")
  private WebElement refundToResident;

  @FindBy(id = "transaction_detail_void_link")
  private WebElement voidButton;

  @FindBy(id = "void_transaction")
  private WebElement voidButtonPs;

  @FindBy(id = "confirmed")
  private WebElement confirmVoid;

  @FindBy(css = "input[title='Reverse from PM']")
  private WebElement reverseButton;

  /**
   * TransactionDetailPage constructor set the transaction id for the page.
   *
   * @param transactionId test transaction id
   */
  public TransactionDetailPage(String transactionId) {
    super();
    this.url = URL.replace("{transactionId}", transactionId);
  }

  /**
   * searches the page for the transaction void link.
   *
   * @return true if the void link is found or false otherwise
   */
  public boolean hasVoidLink() {
    skipTestIfBrScriptIsRunning();

    return isElementPresentBySelector(By.id(VOID_LINK_ID));
  }

  /**
   * Click Void Link.
   */
  public void clickVoidLink() {
    skipTestIfBrScriptIsRunning();
    scrollInToView(voidButton);
    voidButton.click();
  }

  /**
   * Click Void Link for ProfitStars.
   */
  public void clickVoidLinkProfitstars() {
    skipTestIfBrScriptIsRunning();
    scrollInToView(voidButtonPs);
    voidButtonPs.click();
  }

  /**
   * Confirm Void Link.
   */
  public void acceptVoid() {
    clickAndWait(confirmVoid);
  }

  /**
   * open the page.
   */
  public void open() {
    openAndWait(this.url);
  }

  /**
   * Click on "Void/Refund" link on a credit card transaction and return transaction refund page.
   */
  public TransactionRefundPage clickVoidRefund() {
    skipTestIfBrScriptIsRunning();
    scrollInToView(refundLink);
    clickAndWait(refundLink);
    return new TransactionRefundPage();
  }

  /**
   * Click on "Reverse/Refund" button on transaction detail page.
   */
  public void clickReverseRefund() {
    WebElement reverseRefundActionLink = driver.findElement(By.id("reverse_refund_admin_action"));
    scrollInToView(reverseRefundActionLink);
    reverseRefundActionLink.click();
  }

  /**
   * Reverse ACH transaction.
   *
   * @param partial Will only reverse 1 batch item instead of all batch items
   */
  public void reverseTransBank(boolean partial) {
    adminTransactionDetailPaysafeList = new AdminTransactionDetailPaysafeList(
        driver.findElement(By.id("admin_ps_tbl")));
    int tableRowCount =
        adminTransactionDetailPaysafeList.getTableRows().size() - 1; //Not including header row
    if (partial) {
      scrollInToView(driver.findElement(By.id("admin_ps_tbl")));
      WebElement reverseFromPmButton = adminTransactionDetailPaysafeList.getRowByRowNum(0)
          .findElement(By.cssSelector("input[title='Reverse from PM']"));
      clickAndWait(reverseFromPmButton);
      TransactionRefundPage transactionRefundPage = new TransactionRefundPage();
      transactionRefundPage.submitRefundToResident();
    } else {
      for (int i = 0; i < tableRowCount; i++) {
        scrollInToView(driver.findElement(By.id("admin_ps_tbl")));
        adminTransactionDetailPaysafeList = new AdminTransactionDetailPaysafeList(
            driver.findElement(By.id("admin_ps_tbl")));
        WebElement row = adminTransactionDetailPaysafeList.getRowByRowNum(i);
        if (!row.findElements(By.cssSelector("input[title='Reverse from PM']"))
            .isEmpty()) { //Check if row has Reverse from PM button
          clickAndWait(row.findElement(By.cssSelector("input[title='Reverse from PM']")));
          TransactionRefundPage transactionRefundPage = new TransactionRefundPage();
          transactionRefundPage.submitRefundToResident();
        }
      }
    }
  }

  /**
   * Reverse ACH transaction.
   *
   * @param partial Will only reverse 1 batch item instead of all batch items
   */
  public void refundTransBank(boolean partial) {
    adminTransactionDetailPaysafeList = new AdminTransactionDetailPaysafeList(
        driver.findElement(By.id("admin_ps_tbl")));
    int tableRowCount =
        adminTransactionDetailPaysafeList.getTableRows().size() - 1; //Not including header row
    if (partial) {
      scrollInToView(driver.findElement(By.id("admin_ps_tbl")));
      WebElement refundFromResident = adminTransactionDetailPaysafeList.getRowByRowNum(0)
          .findElement(By.cssSelector("input[title='Refund to Resident']"));
      clickAndWait(refundFromResident);
      TransactionRefundPage transactionRefundPage = new TransactionRefundPage();
      transactionRefundPage.inputPartialAmountToRefund();
      transactionRefundPage.submitRefundToResident();
    } else {
      for (int i = 0; i < tableRowCount; i++) {
        scrollInToView(driver.findElement(By.id("admin_ps_tbl")));
        adminTransactionDetailPaysafeList = new AdminTransactionDetailPaysafeList(
            driver.findElement(By.id("admin_ps_tbl")));
        WebElement row = adminTransactionDetailPaysafeList.getRowByRowNum(i);
        if (!row.findElements(By.cssSelector("input[title='Refund to Resident']"))
            .isEmpty()) { //Check if row has refund to Resident button
          clickAndWait(row.findElement(By.cssSelector("input[title='Refund to Resident']")));
          TransactionRefundPage transactionRefundPage = new TransactionRefundPage();
          transactionRefundPage.submitRefundToResident();
        }
      }
    }
  }

  /**
   * Refund ACH transaction.
   *
   * @param partial Will only refund 1 batch item instead of all batch items
   */
  public void refundTransBankFnbo(boolean partial) {
    adminTransactionDetailPaysafeList = new AdminTransactionDetailPaysafeList(
        driver.findElement(By.id("admin_fnbo_tbl")));
    scrollInToView(driver.findElement(By.id("admin_fnbo_tbl")));
    WebElement fullOrPartialRefund = adminTransactionDetailPaysafeList.getRowByRowNum(0)
        .findElement(By.linkText("Full / Partial Refund"));
    scrollInToView(fullOrPartialRefund);
    clickAndWait(fullOrPartialRefund);
    TransactionRefundPage transactionRefundPage = new TransactionRefundPage();
    if (partial) {
      transactionRefundPage.inputPartialAmountToRefundForFnbo();
      transactionRefundPage.submitRefundToResidentForFnbo();
    } else {
      transactionRefundPage.submitRefundToResidentForFnbo();
    }
  }

  /**
   * Reverse ACH transaction.
   *
   * @param partial Will only reverse 1 batch item instead of all batch items
   */
  public void reverseTransBankFnbo(boolean partial) {
    adminTransactionDetailPaysafeList = new AdminTransactionDetailPaysafeList(
        driver.findElement(By.id("admin_fnbo_payout_tbl")));
    int tableRowCount =
        adminTransactionDetailPaysafeList.getTableRows().size() - 1; //Not including header row
    if (partial) {
      scrollInToView(driver.findElement(By.id("admin_fnbo_payout_tbl")));
      WebElement fullOrPartialReversal = adminTransactionDetailPaysafeList.getRowByRowNum(0)
          .findElement(By.linkText("Full / Partial Reverse"));
      clickAndWait(fullOrPartialReversal);
      TransactionRefundPage transactionRefundPage = new TransactionRefundPage();
      transactionRefundPage.inputPartialAmountToRefundForFnbo();
      transactionRefundPage.submitRefundToResidentForFnbo();
    } else {
      for (int i = 0; i < tableRowCount; i++) {
        scrollInToView(driver.findElement(By.id("admin_fnbo_tbl")));
        WebElement fnboPayoutTable = driver.findElement(By.id("admin_fnbo_payout_tbl"));
        scrollInToView(fnboPayoutTable);
        adminTransactionDetailPaysafeList = new AdminTransactionDetailPaysafeList(
            fnboPayoutTable);
        WebElement row = adminTransactionDetailPaysafeList.getRowByRowNum(i);
        if (!row.findElements(By.linkText("Full / Partial Reverse"))
            .isEmpty()) { //Check if row has refund to Resident button
          clickAndWait(row.findElement(By.linkText("Full / Partial Reverse")));
          TransactionRefundPage transactionRefundPage = new TransactionRefundPage();
          transactionRefundPage.submitRefundToResident();
        }
      }
    }
  }

  /**
   * Reverse Credit Card Transaction.
   *
   * @param partial Will only reverse 1 batch item instead of all batch items
   */
  public void reverseTransCc(boolean partial) {
    List<WebElement> reverseLinks = driver.findElements(By.linkText("Reversal"));
    TransactionRefundPage transactionRefundPage = new TransactionRefundPage();
    if (partial) {
      scrollInToView(reverseLinks.get(0));
      clickAndWait(reverseLinks.get(0));
      transactionRefundPage.submitRefundToResident();
    } else {
      for (int i = 0; i < reverseLinks.size(); i++) {
        List<WebElement> reverseLink = driver.findElements(By.linkText("Reversal"));
        scrollInToView(reverseLink.get(i));
        clickAndWait(reverseLink.get(i));
        transactionRefundPage.submitRefundToResident();
      }
    }
  }

  /**
   * Refund Credit Card Transaction.
   *
   * @param partial Will only refund a partial cc transaction
   */
  public void refundTransCc(boolean partial) {
    WebElement refundLink = driver.findElement(By.linkText("Void/Refund"));
    TransactionRefundPage transactionRefundPage = new TransactionRefundPage();
    scrollInToView(refundLink);
    clickAndWait(refundLink);
    if (partial) {
      transactionRefundPage.inputPartialAmountToRefundForFnbo();
      transactionRefundPage.submitRefundToResident();
    } else {
      transactionRefundPage.submitRefundToResident();
    }
  }

  /**
   * Charge back the transaction.
   */
  public void chargebackTrans() {
    WebElement chargebackDropdown = driver.findElement(By.cssSelector("option[value='7']"));
    WebElement submit = driver.findElement(By.name("Submit"));
    chargebackDropdown.click();

    clickAndWait(submit);
  }

  /**
   * NOTE: this returns the status of the original payment transaction for CC transactions only.
   *
   * @return status of the original CC transaction
   */
  public String getStatus() {
    return getTextBySelector(By.id("transaction_status"));
  }

  /**
   * Click on void link.
   */
  public void voidTransaction() {
    skipTestIfBrScriptIsRunning();
    clickVoidLink();
    acceptVoid();
  }


  /**
   * On Reverse/Refund dialog, selects "Refund" radio button, selects Resident as entity type and
   * enters the given amount.
   *
   * @param amount amount to be refunded
   */
  public void fillRefundDetailsForResident(String amount) {
    driver.findElement(By.cssSelector("input#revref_type[name='revref_type'][value='refund']"))
        .click();
    driver.findElement(By.cssSelector("input#revref_res")).click();
    driver.findElement(By.id("revref_amount")).sendKeys(amount);
  }

  /**
   * On Reverse/Refund dialog, selects "Refund" radio button, selects Pm as entity type, selects the
   * given bank and enters the given amount.
   *
   * @param amount amount to be refunded
   */
  public void fillRefundDetailsForPm(String pmBankId, String amount) {
    driver.findElement(By.cssSelector("input#revref_type[name='revref_type'][value='refund']"))
        .click();
    driver.findElement(By.cssSelector("input#revref_pm")).click();

    Select select = new Select(driver.findElement(By.name("revref_account_pm")));
    if (null == pmBankId) {
      select.selectByIndex(1);
    } else {
      select.selectByValue(pmBankId);
    }

    driver.findElement(By.id("revref_amount")).clear();
    driver.findElement(By.id("revref_amount")).sendKeys(amount);
  }

  /* Returns the error message on the Reverse/Refund dialog. */
  public String getRefundReversalError() {
    return driver.findElement(By.id("revref_error")).getText();
  }

  /* Clicks on the Ok button on the Reverse/Refund dialog. */
  public void clickOkReverseRefund() {
    driver.findElement(By.id("rrt_ok")).click();
  }

  /* Clicks on the Cancel button on the Reverse/Refund dialog. */
  public void clickCancelReverseRefund() {
    driver.findElement(By.id("rrt_cancel")).click();
  }

  public boolean isRefundTransactionPresent(String refundTransactionId) {
    adminRefundsList = new AdminRefundsList(driver.findElement(By.id("refund_tbl")));

    return (adminRefundsList.getRowsByCellText(refundTransactionId, Columns.ID.getLabel()).size()
        == 1);
  }

  public boolean isRefundTransactionPresent(String refundTransactionId, String processor) {
    if (processor.equals("FNBO")) {
      adminRefundsList = new AdminRefundsList(driver.findElement(By.id("refund_tbl")));
    } else {
      adminRefundsList = new AdminRefundsList(driver.findElement(By.id("admin_ps_tbl_refunds")));
    }

    return (adminRefundsList.getRowsByCellText(refundTransactionId, Columns.ID.getLabel()).size()
        == 1);
  }

  public boolean isAdminRefundLogged(String parentTransactionId, String refundTransactionId,
      String adminUserFirstName, String adminUserLastName, String adminUserEmail) {
    adminLogEntriesList = new AdminLogEntriesList(
        driver.findElement(By.className("adminLogEntriesTblBlk")));

    HashMap<String, String> dataToFind = new HashMap<>();

    dataToFind.put(AdminLogEntriesList.Columns.TRANS.getLabel(), parentTransactionId);
    dataToFind.put(AdminLogEntriesList.Columns.EVENT.getLabel(), "Transaction refund");
    dataToFind.put(AdminLogEntriesList.Columns.MESSAGE.getLabel(),
        "Refund transaction - new trans " + refundTransactionId + " from original trans "
            + parentTransactionId + " - [action performed by admin user " + adminUserFirstName + " "
            + adminUserLastName + " - " + adminUserEmail + "]");

    for (String key : dataToFind.keySet()) {
      Logger.info(key + ": " + dataToFind.get(key));
    }
    WebElement matchingRow = adminLogEntriesList.getRowMatchingData(dataToFind);
    return (matchingRow != null);
  }

}
