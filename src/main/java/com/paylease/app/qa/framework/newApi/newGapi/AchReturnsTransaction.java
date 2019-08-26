package com.paylease.app.qa.framework.newApi.newGapi;

import com.paylease.app.qa.framework.newApi.Transaction;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"searchStartDate", "searchEndDate", "timeFrame"})

@XmlRootElement(name = "Transaction")
public class AchReturnsTransaction extends Transaction {

  public static final String ACH_RETURN_TRANSACTION = "ACHReturns";

  private String searchStartDate;
  private String searchEndDate;
  private String timeFrame;

  public String getSearchStartDate() {
    return searchStartDate;
  }

  @XmlElement(name = "SearchStartDate")
  public void setSearchStartDate(String searchStartDate) {
    this.searchStartDate = searchStartDate;
  }

  public String getSearchEndDate() {
    return searchEndDate;
  }

  @XmlElement(name = "SearchEndDate")
  public void setSearchEndDate(String searchEndDate) {
    this.searchEndDate = searchEndDate;
  }

  public String getTimeFrame() {
    return timeFrame;
  }

  @XmlElement(name = "TimeFrame")
  public void setTimeFrame(String timeFrame) {
    this.timeFrame = timeFrame;
  }
}
