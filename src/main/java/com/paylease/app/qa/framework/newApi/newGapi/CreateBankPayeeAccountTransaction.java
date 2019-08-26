package com.paylease.app.qa.framework.newApi.newGapi;

import com.paylease.app.qa.framework.newApi.Transaction;
import javax.xml.bind.annotation.XmlElement;

public class CreateBankPayeeAccountTransaction extends Transaction {

  public static final String CREATE_BANK_PAYEE_ACCOUNT_TRANSACTION = "CreateBankPayeeAccount";

  private String payeeReferenceId;
  private String payeeFirstName;
  private String payeeLastName;
  private String payeeState;
  private String accountType;
  private String accountFullName;
  private String routingNumber;
  private String accountNumber;

  public String getPayeeReferenceId() {
    return payeeReferenceId;
  }

  @XmlElement(name = "PayeeReferenceId")
  public void setPayeeReferenceId(String payeeReferenceId) {
    this.payeeReferenceId = payeeReferenceId;
  }

  public String getPayeeFirstName() {
    return payeeFirstName;
  }

  @XmlElement(name = "PayeeFirstName")
  public void setPayeeFirstName(String payeeFirstName) {
    this.payeeFirstName = payeeFirstName;
  }

  public String getPayeeLastName() {
    return payeeLastName;
  }

  @XmlElement(name = "PayeeLastName")
  public void setPayeeLastName(String payeeLastName) {
    this.payeeLastName = payeeLastName;
  }

  public String getPayeeState() {
    return payeeState;
  }

  @XmlElement(name = "PayeeState")
  public void setPayeeState(String payeeState) {
    this.payeeState = payeeState;
  }

  public String getAccountType() {
    return accountType;
  }

  @XmlElement(name = "AccountType")
  public void setAccountType(String accountType) {
    this.accountType = accountType;
  }

  public String getAccountFullName() {
    return accountFullName;
  }

  @XmlElement(name = "AccountFullName")
  public void setAccountFullName(String accountFullName) {
    this.accountFullName = accountFullName;
  }

  public String getRoutingNumber() {
    return routingNumber;
  }

  @XmlElement(name = "RoutingNumber")
  public void setRoutingNumber(String routingNumber) {
    this.routingNumber = routingNumber;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  @XmlElement(name = "AccountNumber")
  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }
}

