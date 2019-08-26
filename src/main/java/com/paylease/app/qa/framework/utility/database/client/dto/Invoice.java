package com.paylease.app.qa.framework.utility.database.client.dto;

public class Invoice {

  private int invoiceId;
  private String createdOn;
  private String lastUpdate;
  private int invoiceConfigId;
  private String invoiceDate;
  private String invoicePaid;
  private double invoiceAmount;
  private double invoiceAmountPaid;
  private String currencyCode;
  private int invoiceStatus;
  private int directDebitOrMail;
  private int bankAccId;
  private int pmId;
  private String companyOrProperty;
  private int hasBeenEmailed;
  private int propId;
  private int groupId;
  private int flag;

  public int getInvoiceId() {
    return invoiceId;
  }

  public void setInvoiceId(int invoiceId) {
    this.invoiceId = invoiceId;
  }

  public String getCreatedOn() {
    return createdOn;
  }

  public void setCreatedOn(String createdOn) {
    this.createdOn = createdOn;
  }

  public String getLastUpdate() {
    return lastUpdate;
  }

  public void setLastUpdate(String lastUpdate) {
    this.lastUpdate = lastUpdate;
  }

  public int getInvoiceConfigId() {
    return invoiceConfigId;
  }

  public void setInvoiceConfigId(int invoiceConfigId) {
    this.invoiceConfigId = invoiceConfigId;
  }

  public String getInvoiceDate() {
    return invoiceDate;
  }

  public void setInvoiceDate(String invoiceDate) {
    this.invoiceDate = invoiceDate;
  }

  public String getInvoicePaid() {
    return invoicePaid;
  }

  public void setInvoicePaid(String invoicePaid) {
    this.invoicePaid = invoicePaid;
  }

  public double getInvoiceAmount() {
    return invoiceAmount;
  }

  public void setInvoiceAmount(double invoiceAmount) {
    this.invoiceAmount = invoiceAmount;
  }

  public double getInvoiceAmountPaid() {
    return invoiceAmountPaid;
  }

  public void setInvoiceAmountPaid(double invoiceAmountPaid) {
    this.invoiceAmountPaid = invoiceAmountPaid;
  }

  public String getCurrencyCode() {
    return currencyCode;
  }

  public void setCurrencyCode(String currencyCode) {
    this.currencyCode = currencyCode;
  }

  public int getInvoiceStatus() {
    return invoiceStatus;
  }

  public void setInvoiceStatus(int invoiceStatus) {
    this.invoiceStatus = invoiceStatus;
  }

  public int getDirectDebitOrMail() {
    return directDebitOrMail;
  }

  public void setDirectDebitOrMail(int directDebitOrMail) {
    this.directDebitOrMail = directDebitOrMail;
  }

  public int getBankAccId() {
    return bankAccId;
  }

  public void setBankAccId(int bankAccId) {
    this.bankAccId = bankAccId;
  }

  public int getPmId() {
    return pmId;
  }

  public void setPmId(int pmId) {
    this.pmId = pmId;
  }

  public String getCompanyOrProperty() {
    return companyOrProperty;
  }

  public void setCompanyOrProperty(String companyOrProperty) {
    this.companyOrProperty = companyOrProperty;
  }

  public int getHasBeenEmailed() {
    return hasBeenEmailed;
  }

  public void setHasBeenEmailed(int hasBeenEmailed) {
    this.hasBeenEmailed = hasBeenEmailed;
  }

  public int getPropId() {
    return propId;
  }

  public void setPropId(int propId) {
    this.propId = propId;
  }

  public int getGroupId() {
    return groupId;
  }

  public void setGroupId(int groupId) {
    this.groupId = groupId;
  }

  public int getFlag() {
    return flag;
  }

  public void setFlag(int flag) {
    this.flag = flag;
  }
}
