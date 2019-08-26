package com.paylease.app.qa.framework.newApi;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"code", "message"})
@XmlRootElement(name = "Error")
public class CodeMessage {

  private String code;
  private String message;

  public String getCode() {
    return code;
  }

  @XmlElement(name = "Code")
  public void setCode(String code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  @XmlElement(name = "Message")
  public void setMessage(String message) {
    this.message = message;
  }
}
