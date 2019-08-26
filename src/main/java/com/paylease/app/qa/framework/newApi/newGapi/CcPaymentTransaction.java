package com.paylease.app.qa.framework.newApi.newGapi;

import com.paylease.app.qa.framework.newApi.PaymentTransaction;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"payerSecondaryRefId", "payerFirstName", "payerLastName", "creditCardAction",
    "creditCardType", "creditCardNumber", "creditCardExpMonth", "creditCardExpYear",
    "creditCardCvv2", "billingFirstName", "billingLastName", "billingStreetAddress", "billingCity",
    "billingState", "billingCountry", "billingZip", "message", "saveAccount"})

@XmlRootElement(name = "PaymentTransaction")
public class CcPaymentTransaction extends PaymentTransaction {

  public final static String CC_PAYMENT_TRANSACTION = "CCPayment";
  private String payerSecondaryRefId;
  private String payerFirstName;
  private String payerLastName;
  private String creditCardAction;
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
  private String message;
  private String saveAccount;

  public String getPayerSecondaryRefId() {
    return payerSecondaryRefId;
  }

  @XmlElement(name = "PayerSecondaryRefId")
  public void setPayerSecondaryRefId(String payerSecondaryRefId) {
    this.payerSecondaryRefId = payerSecondaryRefId;
  }

  public String getPayerFirstName() {
    return payerFirstName;
  }

  @XmlElement(name = "PayerFirstName")
  public void setPayerFirstName(String payerFirstName) {
    this.payerFirstName = payerFirstName;
  }

  public String getPayerLastName() {
    return payerLastName;
  }

  @XmlElement(name = "PayerLastName")
  public void setPayerLastName(String payerLastName) {
    this.payerLastName = payerLastName;
  }

  public String getCreditCardAction() {
    return creditCardAction;
  }

  @XmlElement(name = "CreditCardAction")
  public void setCreditCardAction(String creditCardAction) {
    this.creditCardAction = creditCardAction;
  }

  public String getCreditCardType() {
    return creditCardType;
  }

  @XmlElement(name = "CreditCardType")
  public void setCreditCardType(String creditCardType) {
    this.creditCardType = creditCardType;
  }

  public String getCreditCardNumber() {
    return creditCardNumber;
  }

  @XmlElement(name = "CreditCardNumber")
  public void setCreditCardNumber(String creditCardNumber) {
    this.creditCardNumber = creditCardNumber;
  }

  public String getCreditCardExpMonth() {
    return creditCardExpMonth;
  }

  @XmlElement(name = "CreditCardExpMonth")
  public void setCreditCardExpMonth(String creditCardExpMonth) {
    this.creditCardExpMonth = creditCardExpMonth;
  }

  public String getCreditCardExpYear() {
    return creditCardExpYear;
  }

  @XmlElement(name = "CreditCardExoYear")
  public void setCreditCardExpYear(String creditCardExpYear) {
    this.creditCardExpYear = creditCardExpYear;
  }

  public String getCreditCardCvv2() {
    return creditCardCvv2;
  }

  @XmlElement(name = "CreditCardCvv2")
  public void setCreditCardCvv2(String creditCardCvv2) {
    this.creditCardCvv2 = creditCardCvv2;
  }

  public String getBillingFirstName() {
    return billingFirstName;
  }

  @XmlElement(name = "BillingFirstName")
  public void setBillingFirstName(String billingFirstName) {
    this.billingFirstName = billingFirstName;
  }

  public String getBillingLastName() {
    return billingLastName;
  }

  @XmlElement(name = "BillingLastName")
  public void setBillingLastName(String billingLastName) {
    this.billingLastName = billingLastName;
  }

  public String getBillingStreetAddress() {
    return billingStreetAddress;
  }

  @XmlElement(name = "BillingStreetAddress")
  public void setBillingStreetAddress(String billingStreetAddress) {
    this.billingStreetAddress = billingStreetAddress;
  }

  public String getBillingCity() {
    return billingCity;
  }

  @XmlElement(name = "BillingCity")
  public void setBillingCity(String billingCity) {
    this.billingCity = billingCity;
  }

  public String getBillingState() {
    return billingState;
  }

  @XmlElement(name = "BillingState")
  public void setBillingState(String billingState) {
    this.billingState = billingState;
  }

  public String getBillingCountry() {
    return billingCountry;
  }

  @XmlElement(name = "BillingCountry")
  public void setBillingCountry(String billingCountry) {
    this.billingCountry = billingCountry;
  }

  public String getBillingZip() {
    return billingZip;
  }

  @XmlElement(name = "BillingZip")
  public void setBillingZip(String billingZip) {
    this.billingZip = billingZip;
  }

  public String getMessage() {
    return message;
  }

  @XmlElement(name = "Message")
  public void setMessage(String message) {
    this.message = message;
  }

  public String getSaveAccount() {
    return saveAccount;
  }

  @XmlElement(name = "SaveAccount")
  public void setSaveAccount(String saveAccount) {
    this.saveAccount = saveAccount;
  }
}
