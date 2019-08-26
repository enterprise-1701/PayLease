package com.paylease.app.qa.framework.components.datatable;

import com.paylease.app.qa.framework.components.DataTable;
import org.openqa.selenium.WebElement;

public class AdminPmList extends DataTable {

  public enum Columns {
    PM_ID("PM ID"),
    PM_COMPANY("PM Company"),
    MASTER_PM ("Master PM"),
    SALES_REP("Sales Rep"),
    CAR("CAR"),
    ACTIONS("Actions");

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
  public AdminPmList(WebElement table) {
    super(table, null);

    setColumnIndices();
  }
}