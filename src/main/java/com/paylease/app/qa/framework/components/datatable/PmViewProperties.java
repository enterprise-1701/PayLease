package com.paylease.app.qa.framework.components.datatable;

import com.paylease.app.qa.framework.components.DataTable;
import org.openqa.selenium.WebElement;

public class PmViewProperties extends DataTable {
  public enum Columns {
    NAME("Name"),
    CITY("City"),
    STATE("State"),
    ZIP("Zip"),
    UNIT_COUNT("Unit Count"),
    REF_ID("Ref ID"),
    PAYMENT_FREQUENCY("Payment Frequency"),
    DATE_ADDED("Date Added");

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
  public PmViewProperties(WebElement table) {
    super(table, null);

    setColumnIndices();
  }
}