package com.paylease.app.qa.framework.utility.database.client.dto;

public class PropertyLockSchedule {

  private int propLockSchedId;
  private int pmId;
  private int propId;
  private String frequency;
  private String lockDate;
  private String lastLockDate;

  public int getPropLockSchedId() {
    return propLockSchedId;
  }

  public void setPropLockSchedId(int propLockSchedId) {
    this.propLockSchedId = propLockSchedId;
  }

  public int getPmId() {
    return pmId;
  }

  public void setPmId(int pmId) {
    this.pmId = pmId;
  }

  public int getPropId() {
    return propId;
  }

  public void setPropId(int propId) {
    this.propId = propId;
  }

  public String getFrequency() {
    return frequency;
  }

  public void setFrequency(String frequency) {
    this.frequency = frequency;
  }

  public String getLockDate() {
    return lockDate;
  }

  public void setLockDate(String lockDate) {
    this.lockDate = lockDate;
  }

  public String getLastLockDate() {
    return lastLockDate;
  }

  public void setLastLockDate(String lastLockDate) {
    this.lastLockDate = lastLockDate;
  }
}
