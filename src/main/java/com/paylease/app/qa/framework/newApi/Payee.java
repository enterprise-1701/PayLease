package com.paylease.app.qa.framework.newApi;

public class Payee {
  private String payeeIdFieldName;
  private String payeeIdVarName;
  private String payeeIdLedgerType;
  private String payeeIdChargeCode;
  private String bankAccountNumber;

  public String getPayeeIdFieldName() {
    return payeeIdFieldName;
  }

  public void setPayeeIdFieldName(String payeeIdFieldName) {
    this.payeeIdFieldName = payeeIdFieldName;
  }

  public String getPayeeIdVarName() {
    return payeeIdVarName;
  }

  public void setPayeeIdVarName(String payeeIdVarName) {
    this.payeeIdVarName = payeeIdVarName;
  }

  public String getPayeeIdLedgerType() {
    return payeeIdLedgerType;
  }

  public void setPayeeIdLedgerType(String payeeIdLedgerType) {
    this.payeeIdLedgerType = payeeIdLedgerType;
  }

  public String getPayeeIdChargeCode() {
    return payeeIdChargeCode;
  }

  public void setPayeeIdChargeCode(String payeeIdChargeCode) {
    this.payeeIdChargeCode = payeeIdChargeCode;
  }

  public String getBankAccountNumber() {
    return bankAccountNumber;
  }

  public void setBankAccountNumber(String bankAccountNumber) {
    this.bankAccountNumber = bankAccountNumber;
  }
}