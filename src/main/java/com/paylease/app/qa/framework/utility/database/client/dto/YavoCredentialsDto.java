package com.paylease.app.qa.framework.utility.database.client.dto;

import java.sql.Date;

public class YavoCredentialsDto {

  private int pmId;
  private String url;
  private String username;
  private String password;
  private String servername;
  private String clientDb;
  private String platform;
  private String interfaceentityField;
  private int isAltBatDesc;
  private String alertRecipients;
  private String interfacesVersionField;
  private String billingPaymentsVersion;
  private Date versionsLastChange;
  private String isSsl;
  private int residentUpdateIntMinutes;
  private int residentUpdateSleepIntMinutes;
  private String validCustomerTypes;
  private int socketTimeout;

  //******************
  //Getter Methods
  //******************

  public int getPmId() {
    return pmId;
  }

  public String getUrl() {
    return url;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getServername() {
    return servername;
  }

  public String getClientDb() {
    return clientDb;
  }

  public String getPlatform() {
    return platform;
  }

  public String getInterfaceentityField() {
    return interfaceentityField;
  }

  public int getIsAltBatDesc() {
    return isAltBatDesc;
  }

  public String getAlertRecipients() {
    return alertRecipients;
  }

  public String getInterfacesVersionField() {
    return interfacesVersionField;
  }

  public String getBillingPaymentsVersion() {
    return billingPaymentsVersion;
  }

  public Date getVersionsLastChange() {
    return versionsLastChange;
  }

  public String getIsSsl() {
    return isSsl;
  }

  public int getResidentUpdateIntMinutes() {
    return residentUpdateIntMinutes;
  }

  public int getResidentUpdateSleepIntMinutes() {
    return residentUpdateSleepIntMinutes;
  }

  public String getValidCustomerTypes() {
    return validCustomerTypes;
  }

  public int getSocketTimeout() {
    return socketTimeout;
  }

  //******************
  //Setter Methods
  //******************

  public void setPmId(int pmId) {
    this.pmId = pmId;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setServername(String servername) {
    this.servername = servername;
  }

  public void setClientDb(String clientDb) {
    this.clientDb = clientDb;
  }

  public void setPlatform(String platform) {
    this.platform = platform;
  }

  public void setInterfaceentityField(String interfaceentityField) {
    this.interfaceentityField = interfaceentityField;
  }

  public void setIsAltBatDesc(int isAltBatDesc) {
    this.isAltBatDesc = isAltBatDesc;
  }

  public void setAlertRecipients(String alertRecipients) {
    this.alertRecipients = alertRecipients;
  }

  public void setInterfacesVersionField(String interfacesVersionField) {
    this.interfacesVersionField = interfacesVersionField;
  }

  public void setBillingPaymentsVersion(String billingPaymentsVersion) {
    this.billingPaymentsVersion = billingPaymentsVersion;
  }

  public void setVersionsLastChange(Date versionsLastChange) {
    this.versionsLastChange = versionsLastChange;
  }

  public void setIsSsl(String isSsl) {
    this.isSsl = isSsl;
  }

  public void setResidentUpdateIntMinutes(int residentUpdateIntMinutes) {
    this.residentUpdateIntMinutes = residentUpdateIntMinutes;
  }

  public void setResidentUpdateSleepIntMinutes(int residentUpdateSleepIntMinutes) {
    this.residentUpdateSleepIntMinutes = residentUpdateSleepIntMinutes;
  }

  public void setValidCustomerTypes(String validCustomerTypes) {
    this.validCustomerTypes = validCustomerTypes;
  }

  public void setSocketTimeout(int socketTimeout) {
    this.socketTimeout = socketTimeout;
  }

}
