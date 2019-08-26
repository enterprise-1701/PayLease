package com.paylease.app.qa.framework.components.datatable;

import com.paylease.app.qa.framework.components.DataTable;
import org.openqa.selenium.WebElement;

public class AdminAuditLogEntries extends DataTable {

  public enum Columns {
    UPDATED_ON("Updated On"),
    UPDATED_BY("Updated By"),
    UPDATE_FROM("Update From"),
    TRANS_ID("Trans ID"),
    AUTOPAY_ID("Autopay ID"),
    USER_ID("User ID"),
    PROP_ID("Prop ID"),
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
  public AdminAuditLogEntries(WebElement table) {
    super(table, null);

    setColumnIndices();
  }
}
