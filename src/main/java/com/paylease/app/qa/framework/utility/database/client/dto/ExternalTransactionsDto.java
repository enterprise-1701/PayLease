package com.paylease.app.qa.framework.utility.database.client.dto;

import java.sql.Date;


public class ExternalTransactionsDto {

  private long id;
  private int pmId;
  private String type;
  private String status;
  private int transId;
  private int procId;
  private String externalId;
  private String externalBatchId;
  private Date createdAt;
  private Date updatedAt;
  private Date voidedAt;

  //******************
  //Getter Methods
  //******************


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public int getPmId() {
    return pmId;
  }

  public void setPmId(int pmId) {
    this.pmId = pmId;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public int getTransId() {
    return transId;
  }

  public void setTransId(int transId) {
    this.transId = transId;
  }

  public int getProcId() {
    return procId;
  }

  //******************
  //Setter Methods
  //******************

  public void setProcId(int procId) {
    this.procId = procId;
  }

  public String getExternalId() {
    return externalId;
  }

  public void setExternalId(String externalId) {
    this.externalId = externalId;
  }

  public String getExternalBatchId() {
    return externalBatchId;
  }

  public void setExternalBatchId(String externalBatchId) {
    this.externalBatchId = externalBatchId;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public Date getVoidedAt() {
    return voidedAt;
  }

  public void setVoidedAt(Date voidedAt) {
    this.voidedAt = voidedAt;
  }

}