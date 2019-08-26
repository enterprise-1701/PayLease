package com.paylease.app.qa.framework.newApi;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;

public class Customizations {

  private List<Customization> customizationList;

  public List<Customization> getCustomizationList() {
    return customizationList;
  }

  @XmlElement(name = "Customization")
  public void setCustomizationList(List<Customization> customizationList) {
    this.customizationList = customizationList;
  }
}
