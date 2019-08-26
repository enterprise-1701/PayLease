package com.paylease.app.qa.framework.newApi.newGapi;

import com.paylease.app.qa.framework.newApi.Transaction;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"searchStartDate", "searchEndDate", "numberOfItems", "alwaysShowCurrency"})

@XmlRootElement(name = "Transaction")
public class TransactionSearchDateTransaction extends Transaction {

  public static final String TRANSACTION_SEARCH_DATE_TRANSACTION = "TransactionSearchDate";

  private String searchStartDate;
  private String searchEndDate;
  private String numberOfItems;
  private String alwaysShowCurrency;

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

  public String getNumberOfItems() {
    return numberOfItems;
  }

  @XmlElement(name = "NumberOfItems")
  public void setNumberOfItems(String numberOfItems) {
    this.numberOfItems = numberOfItems;
  }

  public String getAlwaysShowCurrency() {
    return alwaysShowCurrency;
  }

  @XmlElement(name = "AlwaysShowCurrency")
  public void setAlwaysShowCurrency(String alwaysShowCurrency) {
    this.alwaysShowCurrency = alwaysShowCurrency;
  }
}
