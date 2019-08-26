package com.paylease.app.qa.framework.pages.automatedhelper;

import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class GapiResponsePage extends PageBase {

  private static final String URL = BASE_URL + "testing/automated_helper/gapi_response";

  @FindBy(id = "gatewayPayerId")
  private WebElement gatewayPayerIdBox;

  @FindBy(name = "validateGatewayPayerRecord")
  private WebElement validateGatewayPayerRecordButton;

  // ********************************************Action*********************************************

  public void open() {
    openAndWait(URL);
  }

  public GapiValidationPage validateGatewayPayerRecord(String gatewayPayerId) {
    gatewayPayerIdBox.sendKeys(gatewayPayerId);

    clickAndWait(validateGatewayPayerRecordButton);

    return new GapiValidationPage();
  }
}
