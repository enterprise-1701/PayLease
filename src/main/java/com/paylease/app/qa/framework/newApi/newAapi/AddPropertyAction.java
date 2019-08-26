package com.paylease.app.qa.framework.newApi.newAapi;

import com.paylease.app.qa.framework.newApi.Action;
import com.paylease.app.qa.framework.newApi.Property;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"pmId", "property"})
@XmlRootElement(name = "Action")
public class AddPropertyAction extends Action {

  public static final String ACTION_NAME = "AddProperty";

  private String pmId;
  private Property property;

  public String getPmId() {
    return pmId;
  }

  @XmlElement(name = "PmID")
  public void setPmId(String pmId) {
    this.pmId = pmId;
  }

  public Property getProperty() {
    return property;
  }

  @XmlElement(name = "Property")
  public void setProperty(Property property) {
    this.property = property;
  }
}
