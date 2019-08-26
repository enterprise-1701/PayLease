package com.paylease.app.qa.framework.components.datatable;

import com.paylease.app.qa.framework.components.DataTable;
import org.openqa.selenium.WebElement;

public class PmViewFap extends DataTable {
  public enum Columns {
    STATUS("Status"),
    START_DATE("Start Date"),
    END_DATE("End Date"),
    CREATION_DATE("Creation Date"),
    LAST_UPDATED("Last Updated"),
    REF_ID("Ref ID"),
    RESIDENT_NAME("Resident Name"),
    PROPERTY("Property"),
    DEBIT_DAY("Debit Day"),
    FREQUENCY("Freq"),
    PAYMENT_METHOD("Account"),
    AMOUNT("Amount");

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
  public PmViewFap(WebElement table) {
    super(table, null);

    setColumnIndices();
  }

}
