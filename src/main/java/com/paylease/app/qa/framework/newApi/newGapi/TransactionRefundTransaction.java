package com.paylease.app.qa.framework.newApi.newGapi;

import com.paylease.app.qa.framework.newApi.Transaction;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"transactionId", "refundAmount"})

@XmlRootElement(name = "Transaction")
public class TransactionRefundTransaction extends Transaction {

  public static final String TRANSACTION_REFUND_TRANSACTION = "TransactionRefund";

  private String transactionId;
  private String refundAmount;

  public String getTransactionId() {
    return transactionId;
  }

  @XmlElement(name = "TransactionId")
  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }

  public String getRefundAmount() {
    return refundAmount;
  }

  @XmlElement(name = "RefundAmount")
  public void setRefundAmount(String refundAmount) {
    this.refundAmount = refundAmount;
  }
}
