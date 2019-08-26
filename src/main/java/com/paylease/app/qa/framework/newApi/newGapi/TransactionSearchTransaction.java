package com.paylease.app.qa.framework.newApi.newGapi;

import com.paylease.app.qa.framework.newApi.Transaction;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"transactionType", "numberOfItems", "alwaysShowCurrency"})

@XmlRootElement(name = "Transaction")
public class TransactionSearchTransaction extends Transaction {

  public static final String TRANSACTION_SEARCH_TRANSACTION = "TransactionSearch";

  private String transactionType;
  private String numberOfItems;
  private String alwaysShowCurrency;

  public String getTransactionType() {
    return transactionType;
  }

  @XmlElement(name = "TransactionType")
  public void setTransactionType(String transactionType) {
    this.transactionType = transactionType;
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
