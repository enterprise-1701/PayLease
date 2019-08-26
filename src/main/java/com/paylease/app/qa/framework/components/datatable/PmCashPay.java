package com.paylease.app.qa.framework.components.datatable;

import com.paylease.app.qa.framework.components.DataTable;
import org.openqa.selenium.WebElement;

public class PmCashPay extends DataTable {

  public enum Columns {
    RESIDENT("Resident"),
    PROPERTY("Property"),
    UNIT("Unit"),
    ACCOUNT_ID("Account ID"),
    CASH_PAY_NUMBER("Cash Pay Number"),
    CARD_TYPE("Card Type"),
    DEPOSIT_ACCOUNT("Deposit Account");

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
  public PmCashPay(WebElement table) {
    super(table, null);

    setColumnIndices();
  }
}
