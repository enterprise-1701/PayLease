package com.paylease.app.qa.framework.components.datatable;

import com.paylease.app.qa.framework.components.DataTable;
import org.openqa.selenium.WebElement;

public class TransHelperPayouts extends DataTable {

  public enum Columns {
    ACCOUNT_ID("Account ID"),
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
  public TransHelperPayouts(WebElement table) {
    super(table, null);

    setColumnIndices();
  }
}
