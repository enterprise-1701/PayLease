package com.paylease.app.qa.framework.newApi.newGapi;


import com.paylease.app.qa.framework.newApi.PayDirectTypeTransaction;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"payeeFirstName", "payeeLastName", "payeeEmailAddress", "accountType",
    "accountFullName", "routingNumber", "accountNumber", "saveAccount"})

@XmlRootElement(name = "PayDirectTypeTransaction")
public class PayDirectTransaction extends PayDirectTypeTransaction {

  public final static String PAY_DIRECT_TRANSACTION = "PayDirect";

  private String payeeFirstName;
  private String payeeLastName;
  private String payeeEmailAddress;
  private String accountType;
  private String accountFullName;
  private String routingNumber;
  private String accountNumber;
  private String saveAccount;

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

  public String getPayeeEmailAddress() {
    return payeeEmailAddress;
  }

  @XmlElement(name = "PayeeEmailAddress")
  public void setPayeeEmailAddress(String payeeEmailAddress) {
    this.payeeEmailAddress = payeeEmailAddress;
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

  public String getSaveAccount() {
    return saveAccount;
  }

  @XmlElement(name = "SaveAccount")
  public void setSaveAccount(String saveAccount) {
    this.saveAccount = saveAccount;
  }
}
