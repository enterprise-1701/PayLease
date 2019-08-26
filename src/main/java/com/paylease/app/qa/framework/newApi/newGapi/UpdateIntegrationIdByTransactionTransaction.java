package com.paylease.app.qa.framework.newApi.newGapi;

import com.paylease.app.qa.framework.newApi.Transaction;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"transactionId", "integrationId", "secondaryIntegrationId"})

@XmlRootElement(name = "Transaction")
public class UpdateIntegrationIdByTransactionTransaction extends Transaction {

  public static final String UPDATE_INTEGRATION_ID_BY_TRANSACTION_TRANSACTION = "UpdateIntegrationIdByTransaction";

  private String transactionId;
  private String integrationId;
  private String secondaryIntegrationId;

  public String getTransactionId() {
    return transactionId;
  }

  @XmlElement(name = "TransactionId")
  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }

  public String getIntegrationId() {
    return integrationId;
  }

  @XmlElement(name = "IntegrationId")
  public void setIntegrationId(String integrationId) {
    this.integrationId = integrationId;
  }

  public String getSecondaryIntegrationId() {
    return secondaryIntegrationId;
  }

  @XmlElement(name = "SecondaryIntegrationId")
  public void setSecondaryIntegrationId(String secondaryIntegrationId) {
    this.secondaryIntegrationId = secondaryIntegrationId;
  }
}
