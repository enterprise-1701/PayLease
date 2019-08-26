package com.paylease.app.qa.framework.components.datatable;

import com.paylease.app.qa.framework.components.DataTable;
import org.openqa.selenium.WebElement;

public class PmNonIntegratedTransactions extends DataTable {

  public enum Columns {
    TRANS_NUM("Transaction #"),
    TRANS_DATE("Transaction Date"),
    BANK_ACCOUNT("Bank Account"),
    RESIDENT("Resident"),
    ACCOUNT_ID("Account ID"),
    PROPERTY("Property"),
    AMOUNT("Amount");

    private String label;

    Columns(String label) {
      this.label = label.toLowerCase();
    }

    public String getLabel() {
      return label;
    }
  }

  /**
   * Create a DataTable and identify column indices.
   *
   * @param table table element on page
   */
  public PmNonIntegratedTransactions(WebElement table) {
    super(table, null);

    setColumnIndices();
  }

}
