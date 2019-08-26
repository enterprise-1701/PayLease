package com.paylease.app.qa.framework.utility.database.client.dto;

import java.sql.Date;


public class IntegrationLoggingDto {

  private long id;
  private int pmId;
  private int softwareIntegrationId;
  private String method;
  private String status;
  private int depositId;
  private int transId;
  private int procId;
  private String externalBatchId;
  private Date requestTime;
  private Date responseTime;
  private String requestRaw;
  private String responseRaw;
  private String host;

  //******************
  //Getter Methods
  //******************


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public int getPmId() {
    return pmId;
  }

  public void setPmId(int pmId) {
    this.pmId = pmId;
  }

  public int getSoftwareIntegrationId() {
    return softwareIntegrationId;
  }

  public void setSoftwareIntegrationId(int softwareIntegrationId) {
    this.softwareIntegrationId = softwareIntegrationId;
  }

  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public int getDepositId() {
    return depositId;
  }

  public void setDepositId(int depositId) {
    this.depositId = depositId;
  }

  public int getTransId() {
    return transId;
  }

  public void setTransId(int transId) {
    this.transId = transId;
  }

  //******************
  //Setter Methods
  //******************

  public int getProcId() {
    return procId;
  }

  public void setProcId(int procId) {
    this.procId = procId;
  }

  public String getExternalBatchId() {
    return externalBatchId;
  }

  public void setExternalBatchId(String externalBatchId) {
    this.externalBatchId = externalBatchId;
  }

  public Date getRequestTime() {
    return requestTime;
  }

  public void setRequestTime(Date requestTime) {
    this.requestTime = requestTime;
  }

  public Date getResponseTime() {
    return responseTime;
  }

  public void setResponseTime(Date responseTime) {
    this.responseTime = responseTime;
  }

  public String getRequestRaw() {
    return requestRaw;
  }

  public void setRequestRaw(String requestRaw) {
    this.requestRaw = requestRaw;
  }

  public String getResponseRaw() {
    return responseRaw;
  }

  public void setResponseRaw(String responseRaw) {
    this.responseRaw = responseRaw;
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

}