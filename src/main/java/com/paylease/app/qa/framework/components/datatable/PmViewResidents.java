package com.paylease.app.qa.framework.components.datatable;

import com.paylease.app.qa.framework.components.DataTable;
import org.openqa.selenium.WebElement;

public class PmViewResidents extends DataTable {

  public enum Columns {
    REGISTERED("Registered"),
    RESIDENT_NAME("Resident Name"),
    PROPERTY_NAME("Property Name"),
    UNIT("Unit"),
    STATUS("Status"),
    ACCOUNT_NUM("Account #"),
    ACH("ACH"),
    CC("CC");

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
  public PmViewResidents(WebElement table) {
    super(table, null);

    setColumnIndices();
  }
}