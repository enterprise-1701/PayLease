package com.paylease.app.qa.framework.components.datatable;

import com.paylease.app.qa.framework.components.DataTable;
import org.openqa.selenium.WebElement;

public class PmDepositsDebits extends DataTable {

  public enum Columns {
    BANK_ACCOUNT("Bank Account"),
    DATE("Date"),
    TOTAL("Total"),
    TRANS_NUM("Trans #"),
    RESIDENT("Resident"),
    PROPERTY("Property"),
    UNIT("Unit"),
    INIT_DATE("Init Date"),
    TYPE("Type"),
    BILL_TYPE("Bill Type"),
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
  public PmDepositsDebits(WebElement table) {
    super(table, null);

    setColumnIndices();
  }
}