package com.paylease.app.qa.framework.pages.admin;

import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PropertyUploadResultPage extends PageBase {

  private static final String URL = BASE_URL + "admin2.php?action=upload_properties_proc";


  @FindBy(tagName = "body")
  private WebElement propertyUploadResultHTML;


  /**
   * PropertyUploadPage constructor
   */
  public PropertyUploadResultPage() {
    super();
  }

  public String gatherResultPageHTML() {
    return propertyUploadResultHTML.getText();
  }
}
