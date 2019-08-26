package com.paylease.app.qa.framework.api;

public class Credentials {
  private String gatewayId;
  private String username;
  private String password;
  private String apiKey;
  private String pmId;

  public Credentials(String gatewayId, String username, String password) {
    this(gatewayId, username, password, null, null);
  }

  public Credentials(String gatewayId, String username, String password, String apiKey) {
    this(gatewayId, username, password, apiKey, null);
  }

  /**
   * Constructor for Credentials object.
   *
   * @param gatewayId gatewayId
   * @param username username
   * @param password password
   * @param apiKey apiKey
   * @param pmId pmId
   */
  public Credentials(
      String gatewayId, String username, String password, String apiKey, String pmId
  ) {
    this.gatewayId = gatewayId;
    this.username = username;
    this.password = password;
    this.apiKey = apiKey;
    this.pmId = pmId;
  }

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

  public void setApiKey(String apiKey) {
    this.apiKey = apiKey;
  }

  public String getPmId() {
    return pmId;
  }
}
