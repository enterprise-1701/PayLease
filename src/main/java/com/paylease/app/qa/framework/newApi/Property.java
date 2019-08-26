package com.paylease.app.qa.framework.newApi;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"propertyId", "propertyName", "streetAddress", "city", "state", "postalCode",
    "phone", "fax", "email", "unitCount", "freqId", "logoUrl", "paymentFields", "customizations"})
@XmlRootElement(name = "Property")
public class Property {

  private String propertyId;
  private String propertyName;
  private String streetAddress;
  private String city;
  private String state;
  private String postalCode;
  private String phone;
  private String fax;
  private String email;
  private String unitCount;
  private String freqId;
  private String logoUrl;
  private PaymentFields paymentFields;

  public Customizations getCustomizations() {
    return customizations;
  }

  @XmlElement(name = "Customizations")
  public void setCustomizations(Customizations customizations) {
    this.customizations = customizations;
  }

  private Customizations customizations;


  @XmlElement(name = "PropertyID")
  public void setPropertyId(String propertyId) {
    this.propertyId = propertyId;
  }

  @XmlElement(name = "PropertyName")
  public void setPropertyName(String propertyName) {
    this.propertyName = propertyName;
  }

  @XmlElement(name = "StreetAddress")
  public void setStreetAddress(String streetAddress) {
    this.streetAddress = streetAddress;
  }

  @XmlElement(name = "City")
  public void setCity(String city) {
    this.city = city;
  }

  @XmlElement(name = "State")
  public void setState(String state) {
    this.state = state;
  }

  @XmlElement(name = "PostalCode")
  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  @XmlElement(name = "Phone")
  public void setPhone(String phone) {
    this.phone = phone;
  }

  @XmlElement(name = "Fax")
  public void setFax(String fax) {
    this.fax = fax;
  }

  @XmlElement(name = "Email")
  public void setEmail(String email) {
    this.email = email;
  }

  @XmlElement(name = "UnitCount")
  public void setUnitCount(String unitCount) {
    this.unitCount = unitCount;
  }

  @XmlElement(name = "FreqID")
  public void setFreqId(String freqId) {
    this.freqId = freqId;
  }

  @XmlElement(name = "LogoUrl")
  public void setLogoUrl(String logoUrl) {
    this.logoUrl = logoUrl;
  }

  @XmlElement(name = "PaymentFields")
  public void setPaymentFields(PaymentFields paymentFields) {
    this.paymentFields = paymentFields;
  }

  public String getPropertyId() {
    return propertyId;
  }

  public String getPropertyName() {
    return propertyName;
  }

  public String getStreetAddress() {
    return streetAddress;
  }

  public String getCity() {
    return city;
  }

  public String getState() {
    return state;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public String getPhone() {
    return phone;
  }

  public String getFax() {
    return fax;
  }

  public String getEmail() {
    return email;
  }

  public String getUnitCount() {
    return unitCount;
  }

  public String getFreqId() {
    return freqId;
  }

  public String getLogoUrl() {
    return logoUrl;
  }

  public PaymentFields getPaymentFields() {
    return paymentFields;
  }

}
