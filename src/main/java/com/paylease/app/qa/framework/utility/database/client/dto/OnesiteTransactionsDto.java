package com.paylease.app.qa.framework.utility.database.client.dto;

import java.sql.Date;

public class OnesiteTransactionsDto {

  private int transId;
  private int pmId;
  private String externalBatchId;
  private String externalTransId;
  private String request;
  private String response;
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

  public String getExternalBatchId() {
    return externalBatchId;
  }

  public String getExternalTransId() {
    return externalTransId;
  }

  public String getRequest() {
    return request;
  }

  public String getResponse() {
    return response;
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

  public void setExternalBatchId(String externalBatchId) {
    this.externalBatchId = externalBatchId;
  }

  public void setExternalTransId(String externalTransId) {
    this.externalTransId = externalTransId;
  }

  public void setRequest(String request) {
    this.request = request;
  }

  public void setResponse(String response) {
    this.response = response;
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