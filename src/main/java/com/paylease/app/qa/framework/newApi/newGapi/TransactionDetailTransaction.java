package com.paylease.app.qa.framework.newApi.newGapi;

import com.paylease.app.qa.framework.newApi.Transaction;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"transactionId", "paymentReferenceId", "alwaysShowCurrency"})

@XmlRootElement(name = "Transaction")
public class TransactionDetailTransaction extends Transaction {

  public static final String TRANSACTION_DETAIL_TRANSACTION = "TransactionDetail";

  private String transactionId;
  private String paymentReferenceId;
  private String alwaysShowCurrency;

  public String getTransactionId() {
    return transactionId;
  }

  @XmlElement(name = "TransactionId")
  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }

  public String getPaymentReferenceId() {
    return paymentReferenceId;
  }

  @XmlElement(name = "PaymentReferenceId")
  public void setPaymentReferenceId(String paymentReferenceId) {
    this.paymentReferenceId = paymentReferenceId;
  }

  public String getAlwaysShowCurrency() {
    return alwaysShowCurrency;
  }

  @XmlElement(name = "AlwaysShowCurrency")
  public void setAlwaysShowCurrency(String alwaysShowCurrency) {
    this.alwaysShowCurrency = alwaysShowCurrency;
  }
}
