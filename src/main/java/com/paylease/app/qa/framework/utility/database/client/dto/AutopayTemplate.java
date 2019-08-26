package com.paylease.app.qa.framework.utility.database.client.dto;

public class AutopayTemplate {

  private String feeFixed;
  private String pmFeeFixed;
  private String startDate;
  private String debitDay;
  private String lastExecutedDate;
  private String autopayId;
  private boolean isExpressPay;

  public String getFeeFixed() {
    return feeFixed;
  }

  public void setFeeFixed(String feeFixed) {
    this.feeFixed = feeFixed;
  }

  public String getPmFeeFixed() {
    return pmFeeFixed;
  }

  public void setPmFeeFixed(String pmFeeFixed) {
    this.pmFeeFixed = pmFeeFixed;
  }

  public boolean isExpressPay() {
    return isExpressPay;
  }

  public void setExpressPay(boolean expressPay) {
    isExpressPay = expressPay;
  }

  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public String getDebitDay() {
    return debitDay;
  }

  public void setDebitDay(String debitDay) {
    this.debitDay = debitDay;
  }

  public String getLastExecutedDate() {
    return lastExecutedDate;
  }

  public void setLastExecutedDate(String lastExecutedDate) {
    this.lastExecutedDate = lastExecutedDate;
  }

  public String getAutopayId() {
    return autopayId;
  }

  public void setAutopayId(String autopayId) {
    this.autopayId = autopayId;
  }
}
