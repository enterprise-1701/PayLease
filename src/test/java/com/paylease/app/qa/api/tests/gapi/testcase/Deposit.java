package com.paylease.app.qa.api.tests.gapi.testcase;

import java.util.ArrayList;

public class Deposit {
  private String payeeId;
  private String accountNumber;
  private String depositDate;
  private String amount;
  private String currencyCode;
  private ArrayList<DepositTransaction> depositTransactions;

  public Deposit() {
    depositTransactions = new ArrayList<>();
  }

  public String getPayeeId() {
    return payeeId;
  }

  public void setPayeeId(String payeeId) {
    this.payeeId = payeeId;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  public String getDepositDate() {
    return depositDate;
  }

  public void setDepositDate(String depositDate) {
    this.depositDate = depositDate;
  }

  public String getAmount() {
    return amount;
  }

  public void setAmount(String amount) {
    this.amount = amount;
  }

  public String getCurrencyCode() {
    return currencyCode;
  }

  public void setCurrencyCode(String currencyCode) {
    this.currencyCode = currencyCode;
  }

  public void addTransaction(DepositTransaction transaction) {
    depositTransactions.add(transaction);
  }

  public ArrayList<DepositTransaction> getDepositTransactions() {
    return depositTransactions;
  }

  @Override
  public String toString() {
    String strRep = "";
    if (null != payeeId) {
      strRep += "Payee Id: " + payeeId + ", ";
    }
    if (null != accountNumber) {
      strRep += "Account Number: " + accountNumber + ", ";
    }
    if (null != depositDate) {
      strRep += "Deposit Date: " + depositDate + ", ";
    }
    if (null != amount) {
      strRep += "Amount: " + amount + ", ";
    }
    if (null != currencyCode) {
      strRep += "Currency Code: " + currencyCode + ", ";
    }
    if (!depositTransactions.isEmpty()) {
      strRep += "Transactions: " + depositTransactions.toString();
    }
    return strRep;
  }
}
