package com.paylease.app.qa.framework.utility.database.client.dto;


public class TransactionPaymentField {

  private long id;
  private long transactionId;
  private String varName;
  private String extRefId;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(long transactionId) {
    this.transactionId = transactionId;
  }

  public String getVarName() {
    return varName;
  }

  public void setVarName(String varName) {
    this.varName = varName;
  }

  public String getExtRefId() {
    return extRefId;
  }

  public void setExtRefId(String extRefId) {
    this.extRefId = extRefId;
  }
}
