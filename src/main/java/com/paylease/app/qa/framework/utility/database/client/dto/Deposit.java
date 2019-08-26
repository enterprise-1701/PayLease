package com.paylease.app.qa.framework.utility.database.client.dto;

import java.sql.Date;

public class Deposit {

  private String status;
  private int depositId;
  private String accountNumber;
  private Date processingTime;
  private Date depositDate;
  private String creditOrDebit;
  private int propId;
  private String paymentType;
  private Date deletedAt;
  private String depositType;


  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public long getDepositId() {
    return depositId;
  }

  public void setDepositId(int depositId) {
    this.depositId = depositId;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  public Date getProcessingTime() {
    return processingTime;
  }

  public void setProcessingTime(Date processingTime) {
    this.processingTime = processingTime;
  }

  public Date getDepositDate() { return depositDate; }

  public void setDepositDate(Date depositDate) { this.depositDate = depositDate; }

  public String getCreditOrDebit() {
    return creditOrDebit;
  }

  public void setCreditOrDebit(String creditOrDebit) {
    this.creditOrDebit = creditOrDebit;
  }

  public int getPropId() { return propId; }

  public void setPropId(int propId) { this.propId = propId; }

  public String getPaymentType() {
    return paymentType;
  }

  public void setPaymentType(String paymentType) {
    this.paymentType = paymentType;
  }


  public Date getDeletedAt() { return deletedAt; }

  public void setDeletedAt(Date deletedAt) { this.deletedAt = deletedAt; }


  public String getDepositType() {
    return depositType;
  }

  public void setDepositType(String depositType) {
    this.depositType = depositType;
  }


}
