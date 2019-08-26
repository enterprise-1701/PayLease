package com.paylease.app.qa.framework.pages.pm;

import com.paylease.app.qa.framework.components.datatable.PmViewVap;
import com.paylease.app.qa.framework.components.datatable.PmViewVap.Columns;
import java.util.HashMap;
import org.openqa.selenium.By;

public class PmVapListPage extends ViewAutopays {

  private static final String URL = BASE_URL + "pm/variable_autopay_list";

  // ********************************************Action*********************************************

  public void open() {
    openAndWait(URL);
    dataTable = new PmViewVap(dataTableElement);
  }

  /**
   * Determine if page is loaded.
   *
   * @return true if the search autopays element is present
   */
  public boolean pageIsLoaded() {
    String titleText = pageTitle.getText();

    return (titleText.equalsIgnoreCase("Variable AutoPays") && !driver.findElements(
        By.id("search_autopays")).isEmpty());
  }

  /**
   * Find a row in the table matching the given data.
   *
   * @param status Status column value
   * @param startDate Start Date column value
   * @param endDate End Date column value
   * @param creationDate Creation Date column value
   * @param lastUpdated Last Updated column value
   * @param refId Ref ID column value
   * @param residentName Resident Name column value
   * @param property Property column value
   * @param debitDay Debit Day column value
   * @param freq Freq column value
   * @param accountNum Account # column value
   * @param maxLimit Max Limit column value
   * @return True if a row matching all given data is found
   */
  public boolean hasRowMatchingData(String status, String startDate, String endDate,
      String creationDate, String lastUpdated, String refId, String residentName, String property,
      String debitDay, String freq, String accountNum, String maxLimit) {
    HashMap<String, String> dataToFind = new HashMap<>();
    if (status != null) {
      dataToFind.put(Columns.STATUS.getLabel(), status);
    }
    if (startDate != null) {
      dataToFind.put(Columns.START_DATE.getLabel(), startDate);
    }
    if (endDate != null) {
      dataToFind.put(Columns.END_DATE.getLabel(), endDate);
    }
    if (creationDate != null) {
      dataToFind.put(Columns.CREATION_DATE.getLabel(), creationDate);
    }
    if (lastUpdated != null) {
      dataToFind.put(Columns.LAST_UPDATED.getLabel(), lastUpdated);
    }
    if (refId != null) {
      dataToFind.put(Columns.REF_ID.getLabel(), refId);
    }
    if (residentName != null) {
      dataToFind.put(Columns.RESIDENT_NAME.getLabel(), residentName);
    }
    if (property != null) {
      dataToFind.put(Columns.PROPERTY.getLabel(), property);
    }
    if (debitDay != null) {
      dataToFind.put(Columns.DEBIT_DAY.getLabel(), debitDay);
    }
    if (freq != null) {
      dataToFind.put(Columns.FREQUENCY.getLabel(), freq);
    }
    if (accountNum != null) {
      dataToFind.put(Columns.PAYMENT_METHOD.getLabel(), accountNum);
    }
    if (maxLimit != null) {
      dataToFind.put(Columns.MAX_LIMIT.getLabel(), maxLimit);
    }
    return dataTable.getRowMatchingData(dataToFind) != null;
  }
}
