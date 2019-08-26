package com.paylease.app.qa.framework.components.datatable;

import com.paylease.app.qa.framework.components.DataTable;
import org.openqa.selenium.WebElement;

public class AdminLogEntriesList extends DataTable {

  public enum Columns {
    TRANS("Trans"),
    DATE("Date"),
    EVENT("Event"),
    USER("User"),
    IP("IP"),
    MESSAGE("Message");

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
  public AdminLogEntriesList(WebElement table) {
    super(table, null);

    setColumnIndices();
  }
}