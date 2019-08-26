package com.paylease.app.qa.framework.newApi.newAapi;

import com.paylease.app.qa.framework.newApi.Action;
import com.paylease.app.qa.framework.newApi.Properties;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"pmId", "properties"})
@XmlRootElement(name = "Action")
public class SetPropertyCustomizations extends Action {

  public static final String ACTION_NAME = "SetPropertyCustomizations";

  private String pmId;
  private Properties properties;

  public String getPmId() {
    return pmId;
  }

  @XmlElement(name = "PmID")
  public void setPmId(String pmId) {
    this.pmId = pmId;
  }

  public Properties getProperties() {
    return properties;
  }

  @XmlElement(name = "Properties")
  public void setProperties(Properties properties) {
    this.properties = properties;
  }
}
