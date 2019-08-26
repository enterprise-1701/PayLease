package com.paylease.app.qa.framework.newApi;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"paymentReferenceId", "paymentTraceId", "payerId", "payeeReferenceId",
    "payeeState", "totalAmount"})

@XmlRootElement(name = "Transaction")
public class PayDirectTypeTransaction extends Transaction {

  private String paymentReferenceId;
  private String paymentTraceId;
  private String payerId;
  private String payeeReferenceId;
  private String payeeState;
  private String totalAmount;

  public String getPaymentReferenceId() {
    return paymentReferenceId;
  }

  @XmlElement(name = "PaymentReferenceId")
  public void setPaymentReferenceId(String paymentReferenceId) {
    this.paymentReferenceId = paymentReferenceId;
  }

  public String getPaymentTraceId() {
    return paymentTraceId;
  }

  @XmlElement(name = "PaymentTraceId")
  public void setPaymentTraceId(String paymentTraceId) {
    this.paymentTraceId = paymentTraceId;
  }

  public String getPayerId() {
    return payerId;
  }

  @XmlElement(name = "PayerId")
  public void setPayerId(String payerId) {
    this.payerId = payerId;
  }

  public String getPayeeReferenceId() {
    return payeeReferenceId;
  }

  @XmlElement(name = "PayeeReferenceId")
  public void setPayeeReferenceId(String payeeReferenceId) {
    this.payeeReferenceId = payeeReferenceId;
  }

  public String getPayeeState() {
    return payeeState;
  }

  @XmlElement(name = "PayeeState")
  public void setPayeeState(String payeeState) {
    this.payeeState = payeeState;
  }

  public String getTotalAmount() {
    return totalAmount;
  }

  @XmlElement(name = "TotalAmount")
  public void setTotalAmount(String totalAmount) {
    this.totalAmount = totalAmount;
  }
}
