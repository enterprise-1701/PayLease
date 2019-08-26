package com.paylease.app.qa.framework.pages.admin;

import com.paylease.app.qa.framework.pages.PageBase;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class InvoicePaymentSearch extends PageBase {

  private static final String URL = BASE_URL
      + "admin2.php?action=advanced_search_payments&subaction=invoice_search&paid_on_invoice={invoiceId}&payment_type={paymentType}";

  @FindBy(id = "thead")
  private WebElement tableHeaders;

  @FindBy(css = "div.x-box-mc table")
  private WebElement invoicePaymentSearchTable;

  private String url;

  /**
   * Replace url with invoice ID for specified invoice.
   *
   * @param invoiceId invoice id from test helper
   * @param paymentType Payment type filter
   * @param ccType CC type(VISA, MC etc) filter
   */
  public InvoicePaymentSearch(String invoiceId, String paymentType, String ccType) {
    super();
    this.url = URL.replace("{invoiceId}", invoiceId);

    this.url = this.url.replace("{paymentType}", paymentType);

    if (!ccType.isEmpty()) {
      this.url = this.url + "&cc_type=" + ccType;
    }
  }

  // ********************************************Action*********************************************

  public void open() {
    openAndWait(url);
  }

  /**
   * Get table headers.
   *
   * @return Array of table headers
   */
  public String[] getTableHeaders() {
    List<WebElement> columnNameElements = tableHeaders.findElements(By.tagName("th"));
    String[] columnNames = new String[columnNameElements.size()];
    for (int i = 0; i < columnNames.length; i++) {
      columnNames[i] = columnNameElements.get(i).getText();
    }
    return columnNames;
  }

  /**
   * Get array of transactions.
   *
   * @return Array of transactions
   */
  public String[][] getTransactions() {
    List<WebElement> transactionList = invoicePaymentSearchTable.findElements(By.tagName("tr"));
    final int columnCount = tableHeaders.findElements(By.tagName("th")).size();
    final int firstTransRow = transactionList.indexOf(tableHeaders) + 1;
    final int transactionListRowCount = transactionList.size();
    String[][] transactions = new String[transactionListRowCount - firstTransRow - 1][columnCount];
    for (int i = firstTransRow; i < transactionListRowCount - 1; i++) {
      List<WebElement> transaction = transactionList.get(i).findElements(By.tagName("td"));
      for (int j = 0; j < columnCount; j++) {
        transactions[i - 3][j] = transaction.get(j).getText();
      }
    }
    return transactions;
  }
}
