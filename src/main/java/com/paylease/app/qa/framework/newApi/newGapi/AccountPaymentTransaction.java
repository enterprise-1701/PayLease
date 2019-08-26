package com.paylease.app.qa.framework.newApi.newGapi;

import com.paylease.app.qa.framework.newApi.PaymentTransaction;
import javax.xml.bind.annotation.XmlElement;

public class AccountPaymentTransaction extends PaymentTransaction {

  public static final String ACCOUNT_PAYMENT_TRANSACTION = "AccountPayment";

  private String gatewayPayerId;
  private String checkScanned;

  public String getGatewayPayerId() {
    return gatewayPayerId;
  }

  @XmlElement(name = "GatewayPayerId")
  public void setGatewayPayerId(String gatewayPayerId) {
    this.gatewayPayerId = gatewayPayerId;
  }

  public String getCheckScanned() {
    return checkScanned;
  }

  @XmlElement(name = "CheckScanned")
  public void setCheckScanned(String checkScanned) {
    this.checkScanned = checkScanned;
  }
}
