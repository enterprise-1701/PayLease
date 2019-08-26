package com.paylease.app.qa.framework.pages.admin;

import com.paylease.app.qa.framework.components.datatable.AdminAuditLogEntries;
import com.paylease.app.qa.framework.pages.PageBase;
import java.util.HashMap;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class LogsPage extends PageBase {

  private static final String URL = BASE_URL + "admin2.php?action=view_logs";

  @FindBy(id = "tid")
  private WebElement transIdFilterField;

  @FindBy(id = "apid")
  private WebElement autopayIdFilterField;

  @FindBy(id = "uuid")
  private WebElement updatingUserIdFilterField;

  @FindBy(id = "uid")
  private WebElement userIdFilterField;

  @FindBy(id = "pid")
  private WebElement propIdFilterField;

  @FindBy(id = "act")
  private WebElement eventFilterField;

  @FindBy(id = "submit_filter")
  private WebElement submitFilterBtn;

  @FindBy(id = "logs")
  private WebElement logTable;

  public void open() {
    openAndWait(URL);
  }

  /**
   * Enter filter values into form and submit.
   *
   * @param transId Trans ID to search for
   * @param autopayId Autopay ID to search for
   * @param updatingUserId Updating User ID to search for
   * @param userId UserID to search for
   * @param propId Property ID to search for
   * @param event Event label to search for
   */
  public void filter(
      String transId, String autopayId, String updatingUserId, String userId,
      String propId, String event
  ) {
    if (null != transId) {
      setKeys(transIdFilterField, transId);
    }
    if (null != autopayId) {
      setKeys(autopayIdFilterField, autopayId);
    }
    if (null != updatingUserId) {
      setKeys(updatingUserIdFilterField, updatingUserId);
    }
    if (null != userId) {
      setKeys(userIdFilterField, userId);
    }
    if (null != propId) {
      setKeys(propIdFilterField, propId);
    }
    if (null != event) {
      Select eventFilter = new Select(eventFilterField);
      eventFilter.selectByVisibleText(event);
    }
    clickAndWait(submitFilterBtn);
  }

  /**
   * Get the number of log entries visible in the table.
   *
   * @return number of visible log entries
   */
  public int getLogEntryCount() {
    AdminAuditLogEntries table = new AdminAuditLogEntries(logTable);

    WebElement firstRow = table.getRowByRowNum(0);
    if (firstRow.getText().equalsIgnoreCase("no log records.")) {
      return 0;
    }

    return table.getVisibleRowCount();
  }

  /**
   * Get a map of data for the nth row in the log entries table.
   *
   * @param rowNum 0-based index of row to find
   * @return map of data for given row
   */
  public HashMap<String, String> getRowData(int rowNum) {
    AdminAuditLogEntries table = new AdminAuditLogEntries(logTable);

    return table.getMapOfTableRow(table.getRowByRowNum(rowNum));
  }
}
