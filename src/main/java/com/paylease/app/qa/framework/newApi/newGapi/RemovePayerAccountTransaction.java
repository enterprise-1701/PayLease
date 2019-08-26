package com.paylease.app.qa.framework.newApi.newGapi;

import com.paylease.app.qa.framework.newApi.Transaction;
import javax.xml.bind.annotation.XmlElement;

public class RemovePayerAccountTransaction extends Transaction {

  public static final String REMOVE_PAYER_ACCOUNT_TRANSACTION = "RemovePayerAccount";

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
