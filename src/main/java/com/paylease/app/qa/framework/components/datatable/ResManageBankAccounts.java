package com.paylease.app.qa.framework.components.datatable;

import com.paylease.app.qa.framework.components.DataTable;
import org.openqa.selenium.WebElement;

public class ResManageBankAccounts extends DataTable {
  public enum Columns {
    BANK_NAME("Bank Name"),
    ACCOUNT_NUM("Account #"),
    ROUTING_NUM("Routing #"),
    TYPE("Type"),
    NAME_ON_ACCOUNT("Name on Account"),
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
  public ResManageBankAccounts(WebElement table) {
    super(table, null);

    setColumnIndices();
  }

}
