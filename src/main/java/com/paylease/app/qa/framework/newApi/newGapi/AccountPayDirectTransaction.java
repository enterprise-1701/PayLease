package com.paylease.app.qa.framework.newApi.newGapi;

import com.paylease.app.qa.framework.newApi.PayDirectTypeTransaction;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"gatewayPayeeId"})

@XmlRootElement(name = "PayDirectTypeTransaction")
public class AccountPayDirectTransaction extends PayDirectTypeTransaction {

  public final static String ACCOUNT_PAY_DIRECT_TRANSACTION = "AccountPayDirect";

  private String gatewayPayeeId;

  public String getGatewayPayeeId() {
    return gatewayPayeeId;
  }

  @XmlElement(name = "GatewayPayeeId")
  public void setGatewayPayeeId(String gatewayPayeeId) {
    this.gatewayPayeeId = gatewayPayeeId;
  }
}
