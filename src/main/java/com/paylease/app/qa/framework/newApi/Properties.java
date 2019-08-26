package com.paylease.app.qa.framework.newApi;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Properties")
public class Properties {

  private List<Property> propertyList;

  public List<Property> getPropertyList() {
    return propertyList;
  }

  @XmlElement(name = "Property")
  public void setPropertyList(List<Property> propertyList) {
    this.propertyList = propertyList;
  }
}
