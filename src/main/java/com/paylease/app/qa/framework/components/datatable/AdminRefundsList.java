package com.paylease.app.qa.framework.components.datatable;

import com.paylease.app.qa.framework.components.DataTable;
import org.openqa.selenium.WebElement;

public class AdminRefundsList extends DataTable {

  public enum Columns {
    ID("Id"),
    DATE("Date"),
    PAY_AMOUNT("Pay Amount"),
    FEE("Fee"),
    TOTAL("Total"),
    TYPE_OF_TRANSACTION("Type of Transaction"),
    BANK_TO("Bank To"),
    BANK_FROM("Bank From"),
    STATUS("Status"),
    FILE_ID("File Id"),
    ACTIONS("Actions");

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
  public AdminRefundsList(WebElement table) {
    super(table, null);

    setColumnIndices();
  }
}