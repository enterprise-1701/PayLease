package com.paylease.app.qa.framework.utility.database.client.dto;

public class UserDashboardConfig {

  private String id;
  private String userId;
  private String reportTypeId;
  private String customReportId;
  private String displayOrder;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getReportTypeId() {
    return reportTypeId;
  }

  public void setReportTypeId(String reportTypeId) {
    this.reportTypeId = reportTypeId;
  }

  public String getCustomReportId() {
    return customReportId;
  }

  public void setCustomReportId(String customReportId) {
    this.customReportId = customReportId;
  }

  public String getDisplayOrder() {
    return displayOrder;
  }

  public void setDisplayOrder(String displayOrder) {
    this.displayOrder = displayOrder;
  }
}