package com.paylease.app.qa.framework.components.datatable;

import com.paylease.app.qa.framework.components.DataTable;
import org.openqa.selenium.WebElement;

public class AdminFlexibleFeeCustomSettings extends DataTable {

  public enum Columns {
    PROPERTY_NAME("Property Name"),
    PAYMENT_FIELD("Payment Field"),
    PAYMENT_TYPE("Payment Type"),
    ACCOUNT_TYPE("Account Type"),
    FEE_TYPE("Fee Type"),
    FEE_AMOUNT("Fee Amount"),
    INCURRED_BY("Incurred By"),
    TIER_AMOUNT("Tier Amount");

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
  public AdminFlexibleFeeCustomSettings(WebElement table) {
    super(table, null);

    setColumnIndices();
  }
}