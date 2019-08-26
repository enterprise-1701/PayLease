package com.paylease.app.qa.framework.utility.database.client.dto;

public class BankAccount {

  private String routingNum;
  private String bankName;
  private String accountNum;
  private String name;
  private String userId;
  private String accountId;

  public String getRoutingNum() {
    return routingNum;
  }

  public void setRoutingNum(String routingNum) {
    this.routingNum = routingNum;
  }

  public String getBankName() {
    return bankName;
  }

  public void setBankName(String bankName) {
    this.bankName = bankName;
  }

  public String getAccountNum() {
    return accountNum;
  }

  public void setAccountNum(String accountNum) {
    this.accountNum = accountNum;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getAccountId() {
    return accountId;
  }

  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }
}