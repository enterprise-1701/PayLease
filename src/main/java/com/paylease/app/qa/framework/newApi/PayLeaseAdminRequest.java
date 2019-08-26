package com.paylease.app.qa.framework.newApi;

import com.paylease.app.qa.framework.pages.PageBase;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"credentials", "mode", "action"})
@XmlRootElement(name = "PayLeaseRequest")
public class PayLeaseAdminRequest extends PageBase {

  public static final String TEST_MODE = "Test";

  public static final String URL = BASE_URL + "api/request.php";

  private Credentials credentials;
  private String mode;
  private Action action;

  public static String createUrl(String endPoint) {
    return BASE_URL + "api/" + endPoint + ".php";
  }

  @XmlElement(name = "Credentials")
  public void setCredentials(Credentials credentials) {
    this.credentials = credentials;
  }

  @XmlElement(name = "Mode")
  public void setMode(String mode) {
    this.mode = mode;
  }

  @XmlElement(name = "Action")
  public void setAction(Action action) {
    this.action = action;
  }

  public String getMode() {
    return mode;
  }

  public Credentials getCredentials() {
    return credentials;
  }

  public Action getAction() {
    return action;
  }
}
