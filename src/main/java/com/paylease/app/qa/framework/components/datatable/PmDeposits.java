package com.paylease.app.qa.framework.components.datatable;

import com.paylease.app.qa.framework.components.DataTable;
import org.openqa.selenium.WebElement;

public class PmDeposits extends DataTable {

  public enum Columns {
    ACCOUNT("Account"),
    DEPOSIT_TOTAL("Deposit Total"),
    DEPOSIT_DATE("Deposit Date"),
    PAYOUT_DATE("Payout Date"),
    TRANS_NUM("Trans #"),
    RESIDENT_NAME("Resident Name"),
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
  public PmDeposits(WebElement table) {
    super(table, null);

    setColumnIndices();
  }
}