package com.paylease.app.qa.framework.utility.database.client.dto;

public class TransactionsPropertyLock {

  private int transId;
  private int propId;

  public int getTransId() {
    return transId;
  }

  public void setTransId(int transId) {
    this.transId = transId;
  }

  public int getPropId() {
    return propId;
  }

  public void setPropId(int propId) {
    this.propId = propId;
  }
}
