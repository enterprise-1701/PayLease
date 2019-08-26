package com.paylease.app.qa.framework.utility.database.client.dto;

import java.math.BigDecimal;
import java.sql.Date;

public class ResmanTransactionsDto {

  private int id;
  private int transId;
  private String notificationType;
  private int pmId;
  private String requestMsg;
  private String responseMsg;
  private String integrationStatus;
  private byte attempts;
  private Date createdOn;
  private Date updatedOn;
  private String reviewed;
  private BigDecimal amount;
  private String pmBankLastFour;

  //******************
  //Getter Methods
  //******************

  public int getId() {
    return id;
  }

  public int getTransId() {
    return transId;
  }

  public String getNotificationType() {
    return notificationType;
  }

  public int getPmId() {
    return pmId;
  }

  public String getRequestMsg() {
    return requestMsg;
  }

  public String getResponseMsg() {
    return responseMsg;
  }

  public String getIntegrationStatus() {
    return integrationStatus;
  }

  public byte getAttempts() {
    return attempts;
  }

  public Date getCreatedOn() {
    return createdOn;
  }

  public Date getUpdatedOn() {
    return updatedOn;
  }

  public String getReviewed() {
    return reviewed;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public String getPmBankLastFour() {
    return pmBankLastFour;
  }

  //******************
  //Setter Methods
  //******************

  public void setId(int id) {
    this.id = id;
  }

  public void setTransId(int transId) {
    this.transId = transId;
  }

  public void setNotificationType(String notificationType) {
    this.notificationType = notificationType;
  }

  public void setPmId(int pmId) {
    this.pmId = pmId;
  }

  public void setRequestMsg(String requestMsg) {
    this.requestMsg = requestMsg;
  }

  public void setResponseMsg(String responseMsg) {
    this.responseMsg = responseMsg;
  }

  public void setIntegrationStatus(String integrationStatus) {
    this.integrationStatus = integrationStatus;
  }

  public void setAttempts(byte attempts) {
    this.attempts = attempts;
  }

  public void setCreatedOn(Date createdOn) {
    this.createdOn = createdOn;
  }

  public void setUpdatedOn(Date updatedOn) {
    this.updatedOn = updatedOn;
  }

  public void setReviewed(String reviewed) {
    this.reviewed = reviewed;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public void setPmBankLastFour(String pmBankLastFour) {
    this.pmBankLastFour = pmBankLastFour;
  }
}