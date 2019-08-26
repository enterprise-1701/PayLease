package com.paylease.app.qa.framework.newApi;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Credentials Entity
 *
 * @author Jeffrey Walker
 */
@XmlType(propOrder = {"gatewayId", "username", "password", "apiKey", "pmId"})
@XmlRootElement(name = "Credentials")
public class Credentials {

  private String gatewayId;
  private String username;
  private String password;
  private String apiKey;
  private String pmId;

  //Setter Methods
  @XmlElement(name = "GatewayId")
  public void setGatewayId(String gatewayId) {
    this.gatewayId = gatewayId;
  }

  @XmlElement(name = "Username")
  public void setUsername(String username) {
    this.username = username;
  }

  @XmlElement(name = "Password")
  public void setPassword(String password) {
    this.password = password;
  }

  @XmlElement(name = "ApiKey")
  public void setApiKey(String apiKey) {
    this.apiKey = apiKey;
  }

  @XmlElement(name = "PmId")
  public void setPmId(String pmId) {
    this.pmId = pmId;
  }

  //Getter Methods
  public String getGatewayId() {
    return gatewayId;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getApiKey() {
    return apiKey;
  }

  public String getPmId() {
    return pmId;
  }
}

