package com.paylease.app.qa.framework.newApi;

import com.paylease.app.qa.framework.newApi.newAapi.AddPropertyAction;
import com.paylease.app.qa.framework.newApi.newAapi.SetPropertyCustomizations;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement(name = "Action")
@XmlSeeAlso({AddPropertyAction.class, SetPropertyCustomizations.class})
public class Action {

  private String type;

  @XmlAttribute(name = "Type")
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
