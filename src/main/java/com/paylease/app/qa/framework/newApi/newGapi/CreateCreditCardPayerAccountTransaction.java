package com.paylease.app.qa.framework.newApi.newGapi;

import com.paylease.app.qa.framework.newApi.CreateIssuePayerTransaction;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"creditCardType",
    "creditCardNumber", "creditCardExpMonth", "creditCardExpYear", "creditCardCvv2",
    "billingFirstName", "billingLastName", "billingStreetAddress", "billingCity", "billingState",
    "billingCountry", "billingZip"})
@XmlRootElement(name = "CreateIssuePayerTransaction")
public class CreateCreditCardPayerAccountTransaction extends CreateIssuePayerTransaction {

  public static final String CREATE_CREDIT_CARD_PAYER_ACCOUNT_TRANSACTION = "CreateCreditCardPayerAccount";

  private String creditCardType;
  private String creditCardNumber;
  private String creditCardExpMonth;
  private String creditCardExpYear;
  private String creditCardCvv2;
  private String billingFirstName;
  private String billingLastName;
  private String billingStreetAddress;
  private String billingCity;
  private String billingState;
  private String billingCountry;
  private String billingZip;

  @XmlElement(name = "CreditCardType")
  public void setCreditCardType(String creditCardType) {
    this.creditCardType = creditCardType;
  }

  @XmlElement(name = "CreditCardNumber")
  public void setCreditCardNumber(String creditCardNumber) {
    this.creditCardNumber = creditCardNumber;
  }

  @XmlElement(name = "CreditCardExpMonth")
  public void setCreditCardExpMonth(String creditCardExpMonth) {
    this.creditCardExpMonth = creditCardExpMonth;
  }

  @XmlElement(name = "CreditCardExpYear")
  public void setCreditCardExpYear(String creditCardExpYear) {
    this.creditCardExpYear = creditCardExpYear;
  }

  @XmlElement(name = "CreditCardCvv2")
  public void setCreditCardCvv2(String creditCardCvv2) {
    this.creditCardCvv2 = creditCardCvv2;
  }

  @XmlElement(name = "BillingFirstName")
  public void setBillingFirstName(String billingFirstName) {
    this.billingFirstName = billingFirstName;
  }

  @XmlElement(name = "BillingLastName")
  public void setBillingLastName(String billingLastName) {
    this.billingLastName = billingLastName;
  }

  @XmlElement(name = "BillingStreetAddress")
  public void setBillingStreetAddress(String billingStreetAddress) {
    this.billingStreetAddress = billingStreetAddress;
  }

  @XmlElement(name = "BillingCity")
  public void setBillingCity(String billingCity) {
    this.billingCity = billingCity;
  }

  @XmlElement(name = "BillingState")
  public void setBillingState(String billingState) {
    this.billingState = billingState;
  }

  @XmlElement(name = "BillingCountry")
  public void setBillingCountry(String billingCountry) {
    this.billingCountry = billingCountry;
  }

  @XmlElement(name = "BillingZip")
  public void setBillingZip(String billingZip) {
    this.billingZip = billingZip;
  }

  public String getCreditCardType() {
    return creditCardType;
  }

  public String getCreditCardNumber() {
    return creditCardNumber;
  }

  public String getCreditCardExpMonth() {
    return creditCardExpMonth;
  }

  public String getCreditCardExpYear() {
    return creditCardExpYear;
  }

  public String getCreditCardCvv2() {
    return creditCardCvv2;
  }

  public String getBillingFirstName() {
    return billingFirstName;
  }

  public String getBillingLastName() {
    return billingLastName;
  }

  public String getBillingStreetAddress() {
    return billingStreetAddress;
  }

  public String getBillingCity() {
    return billingCity;
  }

  public String getBillingState() {
    return billingState;
  }

  public String getBillingCountry() {
    return billingCountry;
  }

  public String getBillingZip() {
    return billingZip;
  }
}
