package com.paylease.app.qa.framework.newApi.newGapi;

import com.paylease.app.qa.framework.newApi.Transaction;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"payeeReferenceId", "gatewayPayeeId"})
@XmlRootElement(name = "Transaction")
public class RemovePayeeAccountTransaction extends Transaction {

  public static final String REMOVE_PAYEE_ACCOUNT_TRANSACTION = "RemovePayeeAccount";

  private String payeeReferenceId;
  private String gatewayPayeeId;

  public String getPayeeReferenceId() {
    return payeeReferenceId;
  }

  @XmlElement(name = "PayeeReferenceId")
  public void setPayeeReferenceId(String payeeReferenceId) {
    this.payeeReferenceId = payeeReferenceId;
  }

  public String getGatewayPayeeId() {
    return gatewayPayeeId;
  }

  @XmlElement(name = "GatewayPayeeId")
  public void setGatewayPayeeId(String gatewayPayeeId) {
    this.gatewayPayeeId = gatewayPayeeId;
  }
}

