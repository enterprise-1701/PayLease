package com.paylease.app.qa.framework.components.datatable;

import com.paylease.app.qa.framework.components.DataTable;
import org.openqa.selenium.WebElement;

public class PmViewSubAccounts extends DataTable {
  public enum Columns {
    ID_NUMBER("ID Number"),
    ACCOUNT_NAME("Account Name"),
    EMAIL("Email"),
    CONTACT_PHONE_NUMBER("Contact Phone Number"),
    ALTERNATE_CONTACT_NUMBER("Alternate Contact Number");

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
  public PmViewSubAccounts(WebElement table) {
    super(table, null);

    setColumnIndices();
  }
}