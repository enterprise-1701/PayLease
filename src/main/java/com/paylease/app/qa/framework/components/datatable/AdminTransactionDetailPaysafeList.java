package com.paylease.app.qa.framework.components.datatable;

import com.paylease.app.qa.framework.components.DataTable;
import org.openqa.selenium.WebElement;

public class AdminTransactionDetailPaysafeList extends DataTable {

  public enum Columns {
    PL_ID("PL ID"),
    PS_TRANSACTION_NUMBER("PS Transaction Number"),
    PS_REFERENCE_NUMBER("PS Reference Number"),
    DATE("Date"),
    AMOUNT("Amount"),
    TO("To"),
    TOTAL("Total"),
    STATUS("Status"),
    BANK_FROM("Bank From"),
    BANK_TO("Bank To"),
    SENT_ON("Sent On"),
    FILE_ID("File Id"),
    RETURN_DATE("Return Date"),
    RETURN_CODE("Return Code:"),
    PM_LID("PM LID"),
    NO_OF_NSF_FEE_ATTEMPTS("# of NSF Fee Attempts"),
    STOP_NSF_CHARGES("Stop NSF Charges"),
    CHG_BACK("Chg Back"),
    CHANGE_CHG_BACK("Change Chg Back"),
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
  public AdminTransactionDetailPaysafeList(WebElement table) {
    super(table, null);

    setColumnIndices();
  }
}