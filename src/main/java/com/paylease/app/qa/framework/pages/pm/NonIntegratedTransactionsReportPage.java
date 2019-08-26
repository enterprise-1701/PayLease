package com.paylease.app.qa.framework.pages.pm;

import com.paylease.app.qa.framework.components.datatable.PmNonIntegratedTransactions.Columns;
import com.paylease.app.qa.framework.components.datatable.PmNonIntegratedTransactions;
import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NonIntegratedTransactionsReportPage extends PageBase {
  private static final String URL = BASE_URL + "pm/transactions/non_integrated_transaction_report";

  @FindBy(className = "error_message")
  private WebElement errorMessage;

  @FindBy(id = "trans_rpt_table")
  private WebElement transList;

  public void open() {
    openAndWait(URL);
  }

  public boolean hasNoNonIntegratedTransactions() {
    return errorMessage.getText().toString().equals("No transactions found");
  }

  public PmNonIntegratedTransactions getTransactions() {
    return new PmNonIntegratedTransactions(transList);
  }

  public boolean transactionAmountMatches(String transId, String amount) {
    WebElement row = this.getTransactions().getRowByCellText(transId, Columns.TRANS_NUM.getLabel());
    return row.findElement(By.className("trans_amount")).getText().toString().equals(amount);
  }

  public boolean transactionResponseIsVisible(String transId) {
    return transList.findElement(By.id("response" + transId)).isDisplayed();
  }

  public void toggleShowResponse(String transId) {
    WebElement row = this.getTransactions().getRowByCellText(transId, Columns.TRANS_NUM.getLabel());
    row.findElement(By.tagName("a")).click();
  }
}
