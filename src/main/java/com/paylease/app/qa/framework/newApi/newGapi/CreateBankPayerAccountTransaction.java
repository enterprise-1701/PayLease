package com.paylease.app.qa.framework.newApi.newGapi;

import com.paylease.app.qa.framework.newApi.CreateIssuePayerTransaction;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"accountType", "accountFullName", "routingNumber", "accountNumber",
    "currencyCode"})
@XmlRootElement(name = "CreateIssuePayerTransaction")
public class CreateBankPayerAccountTransaction extends CreateIssuePayerTransaction {

  public final static String CREATE_BANK_PAYER_ACCOUNT_TRANSACTION = "CreateBankPayerAccount";

  private String accountType;
  private String accountFullName;
  private String routingNumber;
  private String accountNumber;
  private String currencyCode;

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

  public String getCurrencyCode() {
    return currencyCode;
  }

  @XmlElement(name = "CurrencyCode")
  public void setCurrencyCode(String currencyCode) {
    this.currencyCode = currencyCode;
  }
}
