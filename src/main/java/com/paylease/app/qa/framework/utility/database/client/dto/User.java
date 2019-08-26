package com.paylease.app.qa.framework.utility.database.client.dto;

public class User {

  private String userId;
  private String pmCompany;
  private String propId;
  private String firstName;
  private String lastName;
  private String user;
  private String rentAmount;
  private String rentAmountLastUpdated;
  private String lateAmount;
  private String lateAmountLastUpdated;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getPropId() {
    return propId;
  }

  public void setPropId(String propId) {
    this.propId = propId;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getPmCompany() {
    return pmCompany;
  }

  public void setPmCompany(String pmCompany) {
    this.pmCompany = pmCompany;
  }

  public String getRentAmount() {
    return rentAmount;
  }

  public void setRentAmount(String rentAmount) {
    this.rentAmount = rentAmount;
  }

  public String getRentAmountLastUpdated() {
    return rentAmountLastUpdated;
  }

  public void setRentAmountLastUpdated(String rentAmountLastUpdated) {
    this.rentAmountLastUpdated = rentAmountLastUpdated;
  }

  public String getLateAmount() {
    return lateAmount;
  }

  public void setLateAmount(String lateAmount) {
    this.lateAmount = lateAmount;
  }

  public String getLateAmountLastUpdated() {
    return lateAmountLastUpdated;
  }

  public void setLateAmountLastUpdated(String lateAmountLastUpdated) {
    this.lateAmountLastUpdated = lateAmountLastUpdated;
  }
}
