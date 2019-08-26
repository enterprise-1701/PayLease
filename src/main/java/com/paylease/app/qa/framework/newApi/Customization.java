package com.paylease.app.qa.framework.newApi;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"name", "value", "success"})
@XmlRootElement(name = "Customization")
public class Customization {

  private String name;
  private String value;
  private String success;

  public String getSuccess() {
    return success;
  }

  @XmlElement(name = "Success")
  public void setSuccess(String success) {
    this.success = success;
  }

  public String getName() {
    return name;
  }

  @XmlElement(name = "Name")
  public void setName(String name) {
    this.name = name;
  }

  public String getValue() {
    return value;
  }

  @XmlElement(name = "Value")
  public void setValue(String value) {
    this.value = value;
  }
}
