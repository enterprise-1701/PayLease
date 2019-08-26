package com.paylease.app.qa.framework.components.datatable;

import com.paylease.app.qa.framework.components.DataTable;
import org.openqa.selenium.WebElement;

public class AdminUserSearchResultsList extends DataTable {

  public enum Columns {
    USER_ID("User Id"),
    FIRST_NAME("First Name"),
    LAST_NAME("Last Name"),
    EMAILS("Emails"),
    PHONE_NUMBERS("Phone Numbers"),
    ACC_NUM("ACC #"),
    SECONDARY_ACC_NUM("Secondary ACC #"),
    TYPE("Type"),
    MASTER("Master"),
    STATUS("Status"),
    PROPERTY("Property"),
    PM_COMPANY("PM Company"),
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
  public AdminUserSearchResultsList(WebElement table) {
    super(table, null);

    setColumnIndices();
  }
}