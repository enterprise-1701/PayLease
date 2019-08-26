package com.paylease.app.qa.framework.components.datatable;

import com.paylease.app.qa.framework.components.DataTable;
import org.openqa.selenium.WebElement;

public class ResManageCardAccounts extends DataTable {
  public enum Columns {
    LOGO(""),
    CARDHOLDER("Cardholder"),
    CARD_NUMBER("Card Number"),
    TYPE("Type"),
    DELETE("");

    private String label;

    Columns(String label) {
      this.label = label;
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
  public ResManageCardAccounts(WebElement table) {
    super(table, null);

    setColumnIndices();
  }

}
