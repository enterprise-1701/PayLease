package com.paylease.app.qa.framework.utility.database.client.dto;

public class BatchFile {

  private long fileId;
  private int hasBeenSent;
  private String sentDate;
  private int hasBeenProcessed;

  public long getFileId() {
    return fileId;
  }

  public void setFileId(long fileId) {
    this.fileId = fileId;
  }

  public int getHasBeenSent() {
    return hasBeenSent;
  }

  public void setHasBeenSent(int hasBeenSent) {
    this.hasBeenSent = hasBeenSent;
  }

  public String getSentDate() {
    return sentDate;
  }

  public void setSentDate(String sentDate) {
    this.sentDate = sentDate;
  }

  public int getHasBeenProcessed() {
    return hasBeenProcessed;
  }

  public void setHasBeenProcessed(int hasBeenProcessed) {
    this.hasBeenProcessed = hasBeenProcessed;
  }
}