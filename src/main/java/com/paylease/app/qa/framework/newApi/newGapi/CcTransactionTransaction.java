package com.paylease.app.qa.framework.newApi.newGapi;

import com.paylease.app.qa.framework.newApi.Transaction;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"transactionId", "gatewayPayerId", "creditCardAction"})

@XmlRootElement(name = "Transaction")
public class CcTransactionTransaction extends Transaction {

  public static final String CC_TRANSACTION_TRANSACTION = "CCTransaction";

  private String transactionId;
  private String gatewayPayerId;
  private String creditCardAction;

  public String getTransactionId() {
    return transactionId;
  }

  @XmlElement(name = "TransactionId")
  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }

  public String getGatewayPayerId() {
    return gatewayPayerId;
  }

  @XmlElement(name = "GatewayPayerId")
  public void setGatewayPayerId(String gatewayPayerId) {
    this.gatewayPayerId = gatewayPayerId;
  }

  public String getCreditCardAction() {
    return creditCardAction;
  }

  @XmlElement(name = "CreditCardAction")
  public void setCreditCardAction(String creditCardAction) {
    this.creditCardAction = creditCardAction;
  }
}
