package com.paylease.app.qa.framework.pages.resident;

import com.paylease.app.qa.framework.components.datatable.ResViewFap;
import com.paylease.app.qa.framework.components.datatable.ResViewVap.Columns;
import java.util.HashMap;

public class ResFapListPage extends ResViewAutopays {

  // ********************************************Action*********************************************

  /**
   * Find a row in the table matching the given data.
   *
   * @param status Status column value
   * @param startDate Start Date column value
   * @param endDate End Date column value
   * @param debitDay Debit Day column value
   * @param frequency Freq column value
   * @param accountNum Account # column value
   * @param amount Amount column value
   * @return True if a row matching all given data is found
   */
  public boolean hasRowMatchingData(String status, String startDate, String endDate,
      String debitDay, String frequency, String accountNum, String amount) {
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
    if (debitDay != null) {
      dataToFind.put(Columns.DEBIT_DAY.getLabel(), debitDay);
    }
    if (frequency != null) {
      dataToFind.put(Columns.FREQUENCY.getLabel(), frequency);
    }
    if (accountNum != null) {
      dataToFind.put(Columns.PAYMENT_METHOD.getLabel(), accountNum);
    }
    if (amount != null) {
      dataToFind.put(Columns.AMOUNT.getLabel(), amount);
    }

    dataTable = new ResViewFap(dataTableElement);
    
    return dataTable.getRowMatchingData(dataToFind) != null;
  }
}
