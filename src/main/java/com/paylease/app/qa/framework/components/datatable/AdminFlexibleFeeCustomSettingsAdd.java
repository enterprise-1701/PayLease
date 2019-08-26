package com.paylease.app.qa.framework.components.datatable;

import com.paylease.app.qa.framework.components.DataTable;
import org.openqa.selenium.WebElement;

public class AdminFlexibleFeeCustomSettingsAdd extends DataTable {

  public enum Columns {
    PROP_ID("Property Id"),
    PROPERTY_NAME_ADDRESS("Property Name/Address"),
    CITY("City"),
    STATE("State"),
    ZIP("Zip");

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
  public AdminFlexibleFeeCustomSettingsAdd(WebElement table) {
    super(table, null);

    setColumnIndices();
  }
}