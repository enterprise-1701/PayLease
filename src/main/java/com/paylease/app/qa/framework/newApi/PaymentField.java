package com.paylease.app.qa.framework.newApi;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"fieldName", "varName", "ledger", "bankName", "bankAccountType", "bankAccountRouting", "bankAccountNumber"})
@XmlRootElement(name = "PaymentField")
public class PaymentField {

  private String fieldName;
  private String varName;
  private Ledger ledger;
  private String bankName;
  private String bankAccountType;
  private String bankAccountRouting;
  private String bankAccountNumber;

  public String getFieldName() {
    return fieldName;
  }

  public String getVarName() {
    return varName;
  }

  public Ledger getLedger() {
    return ledger;
  }

  public String getBankName() {
    return bankName;
  }

  public String getBankAccountType() {
    return bankAccountType;
  }

  public String getBankAccountRouting() {
    return bankAccountRouting;
  }

  public String getBankAccountNumber() {
    return bankAccountNumber;
  }

  @XmlElement(name = "FieldName")
  public void setFieldName(String fieldName) {
    this.fieldName = fieldName;
  }

  @XmlElement(name = "VarName")
  public void setVarName(String varName) {
    this.varName = varName;
  }

  @XmlElement(name = "Ledger")
  public void setLedger(Ledger ledger) {
    this.ledger = ledger;
  }

  @XmlElement(name = "BankName")
  public void setBankName(String bankName) {
    this.bankName = bankName;
  }

  @XmlElement(name = "BankAccountType")
  public void setBankAccountType(String bankAccountType) {
    this.bankAccountType = bankAccountType;
  }

  @XmlElement(name = "BankAccountRouting")
  public void setBankAccountRouting(String bankAccountRouting) {
    this.bankAccountRouting = bankAccountRouting;
  }

  @XmlElement(name = "BankAccountNumber")
  public void setBankAccountNumber(String bankAccountNumber) {
    this.bankAccountNumber = bankAccountNumber;
  }
}
