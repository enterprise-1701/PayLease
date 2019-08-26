package com.paylease.app.qa.framework.utility.database.client.dto;

import java.sql.Date;

public class YavoTransactionsDto {

  private int transId;
  private int pmId;
  private int batchId;
  private String requestMsg;
  private String responseMsg;
  private String status;
  private String processingStatus;
  private short attempts;
  private Date timestamp;
  private Date createdOn;
  private String reviewed;

  //******************
  //Getter Methods
  //******************

  public int getTransId() {
    return transId;
  }

  public int getPmId() {
    return pmId;
  }

  public int getBatchId() {
    return batchId;
  }

  public String getRequestMsg() {
    return requestMsg;
  }

  public String getResponseMsg() {
    return responseMsg;
  }

  public String getStatus() {
    return status;
  }

  public String getProcessingStatus() {
    return processingStatus;
  }

  public short getAttempts() {
    return attempts;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public Date getCreatedOn() {
    return createdOn;
  }

  public String getReviewed() {
    return reviewed;
  }

  //******************
  //Setter Methods
  //******************

  public void setTransId(int transId) {
    this.transId = transId;
  }

  public void setPmId(int pmId) {
    this.pmId = pmId;
  }

  public void setBatchId(int batchId) {
    this.batchId = batchId;
  }

  public void setRequestMsg(String requestMsg) {
    this.requestMsg = requestMsg;
  }

  public void setResponseMsg(String responseMsg) {
    this.responseMsg = responseMsg;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public void setProcessingStatus(String processingStatus) {
    this.processingStatus = processingStatus;
  }

  public void setAttempts(short attempts) {
    this.attempts = attempts;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public void setCreatedOn(Date createdOn) {
    this.createdOn = createdOn;
  }

  public void setReviewed(String reviewed) {
    this.reviewed = reviewed;
  }
}