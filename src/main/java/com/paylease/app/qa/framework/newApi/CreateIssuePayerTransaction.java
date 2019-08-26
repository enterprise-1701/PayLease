package com.paylease.app.qa.framework.newApi;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"payerReferenceId", "payerFirstName", "payerLastName"})
@XmlRootElement(name = "Transaction")
public class CreateIssuePayerTransaction extends Transaction {

  private String payerReferenceId;
  private String payerFirstName;
  private String payerLastName;

  public String getPayerReferenceId() {
    return payerReferenceId;
  }

  @XmlElement(name = "PayerReferenceId")
  public void setPayerReferenceId(String payerReferenceId) {
    this.payerReferenceId = payerReferenceId;
  }

  public String getPayerFirstName() {
    return payerFirstName;
  }

  @XmlElement(name = "PayerFirstName")
  public void setPayerFirstName(String payerFirstName) {
    this.payerFirstName = payerFirstName;
  }

  public String getPayerLastName() {
    return payerLastName;
  }

  @XmlElement(name = "PayerLastName")
  public void setPayerLastName(String payerLastName) {
    this.payerLastName = payerLastName;
  }
}
