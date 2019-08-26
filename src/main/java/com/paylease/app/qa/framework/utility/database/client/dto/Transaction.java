package com.paylease.app.qa.framework.utility.database.client.dto;

public class Transaction {

  private long transactionId;
  private String transactionDate;
  private int typeOfTransaction;
  private String fromId;
  private String toId;
  private String paymentTypeId;
  private String totalAmount;
  private String description;
  private String unitAmount;
  private String status;
  private String parentTransactionId;
  private String payleaseFee;
  private String pmFeeAmount;
  private String autoPayStartDate;
  private String autoPayDebitDay;
  private String autopayId;
  private boolean isExpressPay;

  public long getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(long transactionId) {
    this.transactionId = transactionId;
  }

  public String getTransactionDate() {
    return transactionDate;
  }

  public void setTransactionDate(String transactionDate) {
    this.transactionDate = transactionDate;
  }

  public int getTypeOfTransaction() {
    return typeOfTransaction;
  }

  public void setTypeOfTransaction(int typeOfTransaction) {
    this.typeOfTransaction = typeOfTransaction;
  }

  public String getFromId() {
    return fromId;
  }

  public void setFromId(String fromId) {
    this.fromId = fromId;
  }

  public String getToId() {
    return toId;
  }

  public void setToId(String toId) {
    this.toId = toId;
  }

  public String getPaymentTypeId() {
    return paymentTypeId;
  }

  public void setPaymentTypeId(String paymentTypeId) {
    this.paymentTypeId = paymentTypeId;
  }

  public String getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(String totalAmount) {
    this.totalAmount = totalAmount;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getUnitAmount() {
    return unitAmount;
  }

  public void setUnitAmount(String unitAmount) {
    this.unitAmount = unitAmount;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getParentTransactionId() {
    return parentTransactionId;
  }

  public void setParentTransactionId(String parentTransactionId) {
    this.parentTransactionId = parentTransactionId;
  }

  public String getPayleaseFee() {
    return payleaseFee;
  }

  public void setPayleaseFee(String payleaseFee) {
    this.payleaseFee = payleaseFee;
  }

  public String getPmFeeAmount() {
    return pmFeeAmount;
  }

  public void setPmFeeAmount(String pmFeeAmount) {
    this.pmFeeAmount = pmFeeAmount;
  }

  public String getAutoPayStartDate() {
    return autoPayStartDate;
  }

  public void setAutoPayStartDate(String autoPayStartDate) {
    this.autoPayStartDate = autoPayStartDate;
  }

  public String getAutoPayDebitDay() {
    return autoPayDebitDay;
  }

  public void setAutoPayDebitDay(String autoPayDebitDay) {
    this.autoPayDebitDay = autoPayDebitDay;
  }

  public String getAutopayId() {
    return autopayId;
  }

  public void setAutopayId(String autopayId) {
    this.autopayId = autopayId;
  }

  public boolean isExpressPay() {
    return isExpressPay;
  }

  public void setExpressPay(boolean expressPay) {
    isExpressPay = expressPay;
  }
}
