package com.paylease.app.qa.framework.newApi.newGapi;

import com.paylease.app.qa.framework.newApi.Transaction;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"searchStartDate", "searchEndDate", "currencyCode"})

@XmlRootElement(name = "Transaction")
public class DepositsByDateRangeTransaction extends Transaction {

  public static final String DEPOSITS_BY_DATE_RANGE_TRANSACTION = "DepositsByDateRange";

  private String searchStartDate;
  private String searchEndDate;
  private String currencyCode;

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

  public String getCurrencyCode() {
    return currencyCode;
  }

  @XmlElement(name = "CurrencyCode")
  public void setCurrencyCode(String currencyCode) {
    this.currencyCode = currencyCode;
  }
}
