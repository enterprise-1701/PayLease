package com.paylease.app.qa.framework.components.datatable;

import com.paylease.app.qa.framework.components.DataTable;
import org.openqa.selenium.WebElement;

public class PmPaymentHistory extends DataTable {

  public enum Columns {
    TRANS_NUM("Trans #"),
    STATUS("Status"),
    INITIATION_DATE("Initiation Date"),
    RESIDENT("Resident"),
    PROPERTY("Property"),
    UNIT("Unit"),
    BILL_TYPE("Bill Type"),
    AMOUNT("Amount"),
    TYPE("Type"),
    AUTOPAY("AutoPay");

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
  public PmPaymentHistory(WebElement table) {
    super(table, null);

    setColumnIndices(2);
  }
}