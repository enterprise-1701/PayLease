package com.paylease.app.qa.framework.components.datatable;

import com.paylease.app.qa.framework.components.DataTable;
import org.openqa.selenium.WebElement;

public class ResidentRecentPaymentHistory extends DataTable {

  public enum RecentPaymentHistory {
    TRANS_NUMBER("Trans #"),
    DATE("Date"),
    AMOUNT("Amount"),
    STATUS("Status");

    private String label;

    RecentPaymentHistory(String label) {
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
  public ResidentRecentPaymentHistory(WebElement table) {
    super(table, null);

    setColumnIndices();
  }
}
