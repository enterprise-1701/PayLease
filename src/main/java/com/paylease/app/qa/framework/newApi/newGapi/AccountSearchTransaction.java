package com.paylease.app.qa.framework.newApi.newGapi;

import com.paylease.app.qa.framework.newApi.Transaction;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"payerReferenceId", "gatewayPayerId"})
@XmlRootElement(name = "Transaction")
public class AccountSearchTransaction extends Transaction {

  public static final String ACCOUNT_SEARCH_TRANSACTION = "AccountSearch";

  private String payerReferenceId;
  private String gatewayPayerId;

  public String getPayerReferenceId() {
    return payerReferenceId;
  }

  @XmlElement(name = "PayerReferenceId")
  public void setPayerReferenceId(String payerReferenceId) {
    this.payerReferenceId = payerReferenceId;
  }

  public String getGatewayPayerId() {
    return gatewayPayerId;
  }

  @XmlElement(name = "GatewayPayerId")
  public void setGatewayPayerId(String gatewayPayerId) {
    this.gatewayPayerId = gatewayPayerId;
  }

}
