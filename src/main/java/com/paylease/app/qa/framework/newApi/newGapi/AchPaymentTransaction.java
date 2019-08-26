package com.paylease.app.qa.framework.newApi.newGapi;

import com.paylease.app.qa.framework.newApi.PaymentTransaction;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"payerSecondaryRefId", "payerFirstName", "payerLastName", "accountType",
    "accountFullName", "routingNumber", "accountNumber", "saveAccount", "message", "checkScanned",
    "check21", "checkNum", "checkDate", "imageFront", "imageBack", "auxOnUs"})
@XmlRootElement(name = "PaymentTransaction")
public class AchPaymentTransaction extends PaymentTransaction {

  public final static String ACH_PAYMENT_TRANSACTION = "ACHPayment";
  private String payerSecondaryRefId; //generated id (max length = 30 chars)
  private String payerFirstName;
  private String payerLastName;
  private String accountType;//Use the AccountType Enum***
  private String accountFullName;
  private String routingNumber;//9 digits always
  private String accountNumber;
  private String saveAccount;//Use the SaveAccount Enum***
  private String message;// (max length = 100 chars)
  private String checkScanned;//Use the CheckScanned Enum***
  private String check21;//Use the Check21 Enum***
  private String checkNum;
  private String checkDate; //Check date. The format of the date is "m/d/Y", where "m" is a 1 or 2 digit month, "d" is a 1 or 2 digit day, and "Y" is a 4 digit year.
  private String imageFront; //A Base64 encoded text field containing a TIFF image at 200dpi, 1bit color. Base64 encoded string should be URL Encoded to prevent problems caused by special characters (eg. +).
  private String imageBack; //A Base64 encoded text field containing a TIFF image at 200dpi, 1bit color. Base64 encoded string should be URL Encoded to prevent problems caused by special characters (eg. +).
  private String auxOnUs;

  @XmlElement(name = "PayerSecondaryRefId")
  public void setPayerSecondaryRefId(String payerSecondaryRefId) {
    this.payerSecondaryRefId = payerSecondaryRefId;
  }

  @XmlElement(name = "PayerFirstName")
  public void setPayerFirstName(String payerFirstName) {
    this.payerFirstName = payerFirstName;
  }

  @XmlElement(name = "PayerLastName")
  public void setPayerLastName(String payerLastName) {
    this.payerLastName = payerLastName;
  }

  @XmlElement(name = "AccountType")
  public void setAccountType(String accountType) {
    this.accountType = accountType;
  }

  @XmlElement(name = "AccountFullName")
  public void setAccountFullName(String accountFullName) {
    this.accountFullName = accountFullName;
  }

  @XmlElement(name = "RoutingNumber")
  public void setRoutingNumber(String routingNumber) {
    this.routingNumber = routingNumber;
  }

  @XmlElement(name = "AccountNumber")
  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  @XmlElement(name = "SaveAccount")
  public void setSaveAccount(String saveAccount) {
    this.saveAccount = saveAccount;
  }

  @XmlElement(name = "Message")
  public void setMessage(String message) {
    this.message = message;
  }

  @XmlElement(name = "CheckScanned")
  public void setCheckScanned(String checkScanned) {
    this.checkScanned = checkScanned;
  }

  @XmlElement(name = "Check21")
  public void setCheck21(String check21) {
    this.check21 = check21;
  }

  @XmlElement(name = "CheckNum")
  public void setCheckNum(String checkNum) {
    this.checkNum = checkNum;
  }

  @XmlElement(name = "CheckDate")
  public void setCheckDate(String checkDate) {
    this.checkDate = checkDate;
  }

  @XmlElement(name = "ImageFront")
  public void setImageFront(String imageFront) {
    this.imageFront = imageFront;
  }

  @XmlElement(name = "ImageBack")
  public void setImageBack(String imageBack) {
    this.imageBack = imageBack;
  }

  @XmlElement(name = "AuxOnUs")
  public void setAuxOnUs(String auxOnUs) {
    this.auxOnUs = auxOnUs;
  }

  public String getPayerSecondaryRefId() {
    return payerSecondaryRefId;
  }

  public String getPayerFirstName() {
    return payerFirstName;
  }

  public String getPayerLastName() {
    return payerLastName;
  }

  public String getAccountType() {
    return accountType;
  }

  public String getAccountFullName() {
    return accountFullName;
  }

  public String getRoutingNumber() {
    return routingNumber;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public String getSaveAccount() {
    return saveAccount;
  }

  public String getMessage() {
    return message;
  }

  public String getCheckScanned() {
    return checkScanned;
  }

  public String getCheck21() {
    return check21;
  }

  public String getCheckNum() {
    return checkNum;
  }

  public String getCheckDate() {
    return checkDate;
  }

  public String getImageFront() {
    return imageFront;
  }

  public String getImageBack() {
    return imageBack;
  }

  public String getAuxOnUs() {
    return auxOnUs;
  }
}
