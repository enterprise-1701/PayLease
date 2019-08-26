package com.paylease.app.qa.framework.utility.database.client.dto;

import java.math.BigDecimal;
import java.sql.Date;

public class ReturnFilesDto {

  private int returnFileId;
  private String fileName;
  private String fullName;
  private int achProcessor;
  private int hasBeenProcessed;
  private Date createdOn;
  private Date processedOn;
  private byte mailSent;
  private String gpgName;
  private BigDecimal creditAmount;
  private BigDecimal debitAmount;
  private boolean checked;
  private Date checkedOn;
  private boolean hasBeenAudit;
  private String isError;

  //******************
  //Getter Methods
  //******************

  public int getReturnFileId() {
    return returnFileId;
  }

  public String getFileName() {
    return fileName;
  }

  public String getFullName() {
    return fullName;
  }

  public int getAchProcessor() {
    return achProcessor;
  }

  public int getHasBeenProcessed() {
    return hasBeenProcessed;
  }

  public Date getCreatedOn() {
    return createdOn;
  }

  public Date getProcessedOn() {
    return processedOn;
  }

  public byte getMailSent() {
    return mailSent;
  }

  public String getGpgName() {
    return gpgName;
  }

  public BigDecimal getCreditAmount() {
    return creditAmount;
  }

  public BigDecimal getDebitAmount() {
    return debitAmount;
  }

  public boolean getChecked() {
    return checked;
  }

  public Date getCheckedOn() {
    return checkedOn;
  }

  public boolean getHasBeenAudit() {
    return hasBeenAudit;
  }

  public String getIsError() {
    return isError;
  }

  //******************
  //Setter Methods
  //******************

  public void setReturnFileId(int returnFileId) {
    this.returnFileId = returnFileId;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public void setAchProcessor(int achProcessor) {
    this.achProcessor = achProcessor;
  }

  public void setHasBeenProcessed(int hasBeenProcessed) {
    this.hasBeenProcessed = hasBeenProcessed;
  }

  public void setCreatedOn(Date createdOn) {
    this.createdOn = createdOn;
  }

  public void setProcessedOn(Date processedOn) {
    this.processedOn = processedOn;
  }

  public void setMailSent(byte mailSent) {
    this.mailSent = mailSent;
  }

  public void setGpgName(String gpgName) {
    this.gpgName = gpgName;
  }

  public void setCreditAmount(BigDecimal creditAmount) {
    this.creditAmount = creditAmount;
  }

  public void setDebitAmount(BigDecimal debitAmount) {
    this.debitAmount = debitAmount;
  }

  public void setChecked(boolean checked) {
    this.checked = checked;
  }

  public void setCheckedOn(Date checkedOn) {
    this.checkedOn = checkedOn;
  }

  public void setHasBeenAudit(boolean hasBeenAudit) {
    this.hasBeenAudit = hasBeenAudit;
  }

  public void setIsError(String isError) {
    this.isError = isError;
  }

}
