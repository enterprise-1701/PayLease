package com.paylease.app.qa.framework.components.datatable;

import com.paylease.app.qa.framework.components.DataTable;
import org.openqa.selenium.WebElement;

public class ResPaymentHistory extends DataTable {

  public enum Columns {
    TRANS_NUM("Trans #"),
    STATUS("Status"),
    DATE("Date"),
    BILL_TYPE("Bill Type"),
    AMOUNT("Amount"),
    METHOD("Method");

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
  public ResPaymentHistory(WebElement table) {
    super(table, null);

    setColumnIndices(1);
  }
}