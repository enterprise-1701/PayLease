package com.paylease.app.qa.framework.newApi.newGapi;

import com.paylease.app.qa.framework.newApi.Transaction;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"transactionId"})

@XmlRootElement(name = "Transaction")
public class TransactionVoidTransaction extends Transaction {

  public static final String TRANSACTION_VOID_TRANSACTION = "TransactionVoid";

  private String transactionId;

  public String getTransactionId() {
    return transactionId;
  }

  @XmlElement(name = "TransactionId")
  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }
}
