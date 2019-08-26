package com.paylease.app.qa.framework.newApi;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Transaction Entity
 *
 * @author Jeffrey Walker
 */
@XmlType(propOrder = {"transactionAction"})
@XmlRootElement(name = "Transaction")
public class Transaction {

  private String transactionAction;

  @XmlElement(name = "TransactionAction")
  public void setTransactionAction(String transactionAction) {
    this.transactionAction = transactionAction;
  }

  public String getTransactionAction() {
    return transactionAction;
  }
}

