package com.paylease.app.qa.framework.newApi;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"paymentReferenceId", "paymentTraceId", "payeeId", "payerReferenceId",
    "creditReportingId", "splitDeposit", "currencyCode", "totalAmount", "feeAmount", "incurFee"})
@XmlRootElement(name = "Transaction")
public class PaymentTransaction extends Transaction {

  private String paymentReferenceId;
  private String paymentTraceId;
  private String payeeId;
  private String payerReferenceId;
  private String creditReportingId;
  private SplitDeposit splitDeposit;
  private String currencyCode;
  private String totalAmount;
  private Double feeAmount;
  private String incurFee;

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

  public String getPayeeId() {
    return payeeId;
  }

  @XmlElement(name = "PayeeId")
  public void setPayeeId(String payeeId) {
    this.payeeId = payeeId;
  }

  public String getPayerReferenceId() {
    return payerReferenceId;
  }

  @XmlElement(name = "PayerReferenceId")
  public void setPayerReferenceId(String payerReferenceId) {
    this.payerReferenceId = payerReferenceId;
  }

  public String getCreditReportingId() {
    return creditReportingId;
  }

  @XmlElement(name = "CreditReportingId")
  public void setCreditReportingId(String creditReportingId) {
    this.creditReportingId = creditReportingId;
  }

  public SplitDeposit getSplitDeposit() {
    return splitDeposit;
  }

  @XmlElement(name = "SplitDeposit")
  public void setSplitDeposit(SplitDeposit splitDeposit) {
    this.splitDeposit = splitDeposit;
  }

  public String getCurrencyCode() {
    return currencyCode;
  }

  @XmlElement(name = "CurrencyCode")
  public void setCurrencyCode(String currencyCode) {
    this.currencyCode = currencyCode;
  }

  public String getTotalAmount() {
    return totalAmount;
  }

  @XmlElement(name = "TotalAmount")
  public void setTotalAmount(String totalAmount) {
    this.totalAmount = totalAmount;
  }

  public Double getFeeAmount() {
    return feeAmount;
  }

  @XmlElement(name = "FeeAmount")
  public void setFeeAmount(Double feeAmount) {
    this.feeAmount = feeAmount;
  }

  public String getIncurFee() {
    return incurFee;
  }

  @XmlElement(name = "IncurFee")
  public void setIncurFee(String incurFee) {
    this.incurFee = incurFee;
  }
}
