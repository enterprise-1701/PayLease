package com.paylease.app.qa.api.tests.gapi.testcase;

public class DepositTransaction {
  private String transactionId;
  private String initiatedDate;
  private String payoutDate;
  private String paymentType;
  private String billType;
  private String paymentReferenceId;
  private String amount;
  private String currencyCode;

  public String getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }

  public String getInitiatedDate() {
    return initiatedDate;
  }

  public void setInitiatedDate(String initiatedDate) {
    this.initiatedDate = initiatedDate;
  }

  public String getPayoutDate() {
    return payoutDate;
  }

  public void setPayoutDate(String payoutDate) {
    this.payoutDate = payoutDate;
  }

  public String getPaymentType() {
    return paymentType;
  }

  public void setPaymentType(String paymentType) {
    this.paymentType = paymentType;
  }

  public String getBillType() {
    return billType;
  }

  public void setBillType(String billType) {
    this.billType = billType;
  }

  public String getPaymentReferenceId() {
    return paymentReferenceId;
  }

  public void setPaymentReferenceId(String paymentReferenceId) {
    this.paymentReferenceId = paymentReferenceId;
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

  @Override
  public String toString() {
    String strRep = "";

    if (null != transactionId) {
      strRep += "Transaction Id: " + transactionId + ", ";
    }
    if (null != initiatedDate) {
      strRep += "Initiated Date: " + initiatedDate + ", ";
    }
    if (null != payoutDate) {
      strRep += "Payout Date: " + payoutDate + ", ";
    }
    if (null != paymentType) {
      strRep += "Payment Type: " + paymentType + ", ";
    }
    if (null != billType) {
      strRep += "Bill Type: " + billType + ", ";
    }
    if (null != paymentReferenceId) {
      strRep += "Payment Reference Id: " + paymentReferenceId + ", ";
    }
    if (null != amount) {
      strRep += "Amount: " + amount + ", ";
    }
    if (null != currencyCode) {
      strRep += "Currency Code: " + currencyCode;
    }
    return strRep;
  }
}
