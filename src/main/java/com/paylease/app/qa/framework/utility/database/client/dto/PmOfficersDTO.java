package com.paylease.app.qa.framework.utility.database.client.dto;

import java.sql.Date;


public class PmOfficersDTO {

  private int id;
  private int pmId;
  private String officerType;
  private String firstName;
  private String lastName;
  private String title;
  private String role;
  private String email;
  private int percentStake;
  private String dateOfBirth;
  private String ssn;
  private String address;
  private String city;
  private String state;
  private String zip;
  private String licenseNumber;
  private String licenseState;
  private Date verificationLastSubmittedDate;
  private Date verificationApprovalDate;
  private int verificationAttempts;

  //******************
  //Getter Methods
  //******************


  public int getId() {
    return id;
  }

  public int getPmId() {
    return pmId;
  }

  public String getOfficerType() {
    return officerType;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getTitle() {
    return title;
  }

  public String getRole() {
    return role;
  }

  public String getEmail() {
    return email;
  }

  public int getPercentStake() {
    return percentStake;
  }

  public String getDateOfBirth() {
    return dateOfBirth;
  }

  public String getSsn() {
    return ssn;
  }

  public String getAddress() {
    return address;
  }

  public String getCity() {
    return city;
  }

  public String getState() {
    return state;
  }

  public String getZip() {
    return zip;
  }

  public String getLicenseNumber() {
    return licenseNumber;
  }

  public String getLicenseState() {
    return licenseState;
  }

  public Date getVerificationLastSubmittedDate() {
    return verificationLastSubmittedDate;
  }

  public Date getVerificationApprovalDate() {
    return verificationApprovalDate;
  }

  public int getVerificationAttempts() {
    return verificationAttempts;
  }

  //******************
  //Setter Methods
  //******************


  public void setId(int id) {
    this.id = id;
  }

  public void setPmId(int pmId) {
    this.pmId = pmId;
  }

  public void setOfficerType(String officerType) {
    this.officerType = officerType;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPercentStake(int percentStake) {
    this.percentStake = percentStake;
  }

  public void setDateOfBirth(String dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public void setSsn(String ssn) {
    this.ssn = ssn;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public void setState(String state) {
    this.state = state;
  }

  public void setZip(String zip) {
    this.zip = zip;
  }

  public void setLicenseNumber(String licenseNumber) {
    this.licenseNumber = licenseNumber;
  }

  public void setLicenseState(String licenseState) {
    this.licenseState = licenseState;
  }

  public void setVerificationLastSubmittedDate(Date verificationLastSubmittedDate) {
    this.verificationLastSubmittedDate = verificationLastSubmittedDate;
  }

  public void setVerificationApprovalDate(Date verificationApprovalDate) {
    this.verificationApprovalDate = verificationApprovalDate;
  }

  public void setVerificationAttempts(int verificationAttempts) {
    this.verificationAttempts = verificationAttempts;
  }

}