package com.paylease.app.qa.framework.components.datatable;

import com.paylease.app.qa.framework.components.DataTable;
import org.openqa.selenium.WebElement;

public class ResViewFap extends DataTable {
  public enum Columns {
    STATUS("Status"),
    START_DATE("Start Date"),
    END_DATE("End Date"),
    DEBIT_DAY("Debit Day"),
    FREQUENCY("Frequency"),
    PAYMENT_METHOD("Account #"),
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
  public ResViewFap(WebElement table) {
    super(table, null);

    setColumnIndices();
  }
}
