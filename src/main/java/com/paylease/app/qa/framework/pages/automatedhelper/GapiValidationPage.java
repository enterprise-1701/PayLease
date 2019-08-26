package com.paylease.app.qa.framework.pages.automatedhelper;

import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class GapiValidationPage extends PageBase {

  @FindBy(id = "userId")
  private WebElement userId;

  // ********************************************Action*********************************************

  public String getUserId() {

    return userId.getText();
  }

}
