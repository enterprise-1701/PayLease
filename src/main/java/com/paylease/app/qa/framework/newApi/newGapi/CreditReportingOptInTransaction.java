package com.paylease.app.qa.framework.newApi.newGapi;

import com.paylease.app.qa.framework.newApi.Transaction;
import javax.xml.bind.annotation.XmlElement;

public class CreditReportingOptInTransaction extends Transaction {

  public static final String CREDIT_REPORTING_OPT_IN_TRANSACTION = "CreditReportingOptIn";

  private String creditReportingId;
  private String firstName;
  private String lastName;
  private String streetAddress;
  private String city;
  private String state;
  private String zip;
  private String monthToMonthCreditReporting;
  private String leaseEndDate;
  private String ssn;
  private String birthDate;

  public String getCreditReportingId() {
    return creditReportingId;
  }

  @XmlElement(name = "CreditReportingId")
  public void setCreditReportingId(String creditReportingId) {
    this.creditReportingId = creditReportingId;
  }

  public String getFirstName() {
    return firstName;
  }

  @XmlElement(name = "FirstName")
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  @XmlElement(name = "LastName")
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getStreetAddress() {
    return streetAddress;
  }

  @XmlElement(name = "StreetAddress")
  public void setStreetAddress(String streetAddress) {
    this.streetAddress = streetAddress;
  }

  public String getCity() {
    return city;
  }

  @XmlElement(name = "City")
  public void setCity(String city) {
    this.city = city;
  }

  public String getState() {
    return state;
  }

  @XmlElement(name = "State")
  public void setState(String state) {
    this.state = state;
  }

  public String getZip() {
    return zip;
  }

  @XmlElement(name = "Zip")
  public void setZip(String zip) {
    this.zip = zip;
  }

  public String getMonthToMonthCreditReporting() {
    return monthToMonthCreditReporting;
  }

  @XmlElement(name = "MonthToMonthCreditReporting")
  public void setMonthToMonthCreditReporting(String monthToMonthCreditReporting) {
    this.monthToMonthCreditReporting = monthToMonthCreditReporting;
  }

  public String getLeaseEndDate() {
    return leaseEndDate;
  }

  @XmlElement(name = "LeaseEndDate")
  public void setLeaseEndDate(String leaseEndDate) {
    this.leaseEndDate = leaseEndDate;
  }

  public String getSsn() {
    return ssn;
  }

  @XmlElement(name = "Ssn")
  public void setSsn(String ssn) {
    this.ssn = ssn;
  }

  public String getBirthDate() {
    return birthDate;
  }

  @XmlElement(name = "BirthDate")
  public void setBirthDate(String birthDate) {
    this.birthDate = birthDate;
  }
}
