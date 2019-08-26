package com.paylease.app.qa.framework.utility.database.client.dto;

public class BatchItem {

  private long fileId;
  private long transId;
  private long depositId;
  private long returnDepositId;
  private long transactionPaymentFieldId;
  private int creditOrDebit;
  private String fromRoutingNum;
  private String fromAcctNum;
  private String toRoutingNum;
  private String toAcctNum;
  private String fromBankAcctId;
  private String toBankAcctId;
  private String accountPersonName;
  private String createdDate;
  private int achProcessor;
  private int pmId;
  private int propid;
  private int collectingFee;
  private double amount;
  private int procId;
  private String extRefId;
  private int readyToProcess;
  private int batchId;
  private String returnCode;
  private int returnFileId;

  public String getExtRefId() {
    return extRefId;
  }

  public void setExtRefId(String extRefId) {
    this.extRefId = extRefId;
  }

  public long getFileId() {
    return fileId;
  }

  public void setFileId(long fileId) {
    this.fileId = fileId;
  }

  public long getTransId() {
    return transId;
  }

  public void setTransId(long transId) {
    this.transId = transId;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public int getProcId() {
    return procId;
  }

  public void setProcId(int procId) {
    this.procId = procId;
  }

  public int getCreditOrDebit() {
    return creditOrDebit;
  }

  public void setCreditOrDebit(int creditOrDebit) {
    this.creditOrDebit = creditOrDebit;
  }

  public String getFromRoutingNum() {
    return fromRoutingNum;
  }

  public void setFromRoutingNum(String fromRoutingNum) {
    this.fromRoutingNum = fromRoutingNum;
  }

  public String getFromAcctNum() {
    return fromAcctNum;
  }

  public void setFromAcctNum(String fromAcctNum) {
    this.fromAcctNum = fromAcctNum;
  }

  public String getToRoutingNum() {
    return toRoutingNum;
  }

  public void setToRoutingNum(String toRoutingNum) {
    this.toRoutingNum = toRoutingNum;
  }

  public String getToAcctNum() {
    return toAcctNum;
  }

  public void setToAcctNum(String toAcctNum) {
    this.toAcctNum = toAcctNum;
  }

  public String getFromBankAcctId() {
    return fromBankAcctId;
  }

  public void setFromBankAcctId(String fromBankAcctId) {
    this.fromBankAcctId = fromBankAcctId;
  }

  public String getToBankAcctId() {
    return toBankAcctId;
  }

  public void setToBankAcctId(String toBankAcctId) {
    this.toBankAcctId = toBankAcctId;
  }

  public String getAccountPersonName() {
    return accountPersonName;
  }

  public void setAccountPersonName(String accountPersonName) {
    this.accountPersonName = accountPersonName;
  }

  public String getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(String createdDate) {
    this.createdDate = createdDate;
  }

  public int getAchProcessor() {
    return achProcessor;
  }

  public void setAchProcessor(int achProcessor) {
    this.achProcessor = achProcessor;
  }

  public int getPmId() {
    return pmId;
  }

  public void setPmId(int pmId) {
    this.pmId = pmId;
  }

  public int getPropid() {
    return propid;
  }

  public void setPropid(int propid) {
    this.propid = propid;
  }

  public int getCollectingFee() {
    return collectingFee;
  }

  public void setCollectingFee(int collectingFee) {
    this.collectingFee = collectingFee;
  }

  public long getDepositId() {
    return depositId;
  }

  public void setDepositId(long depositId) {
    this.depositId = depositId;
  }

  public int getReadyToProcess() {
    return readyToProcess;
  }

  public void setReadyToProcess(int readyToProcess) {
    this.readyToProcess = readyToProcess;
  }

  public int getBatchId() {
    return batchId;
  }

  public void setBatchId(int batchId) {
    this.batchId = batchId;
  }


  public String getReturnCode() {
    return returnCode;
  }

  public void setReturnCode(String returnCode) {
    this.returnCode = returnCode;
  }

  public long getReturnDepositId() {
    return returnDepositId;
  }

  public void setReturnDepositId(long returnDepositId) {
    this.returnDepositId = returnDepositId;
  }

  public long getTransactionPaymentFieldId() {
    return transactionPaymentFieldId;
  }

  public void setTransactionPaymentFieldId(long transactionPaymentFieldId) {
    this.transactionPaymentFieldId = transactionPaymentFieldId;
  }

  public int getReturnFileId() {
    return returnFileId;
  }

  public void setReturnFileId(int returnFileId) {
    this.returnFileId = returnFileId;
  }
}