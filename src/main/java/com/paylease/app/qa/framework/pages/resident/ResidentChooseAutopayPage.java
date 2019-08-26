package com.paylease.app.qa.framework.pages.resident;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ResidentChooseAutopayPage extends PageBase {

  /** Click on Create Variable AutoPay Button.*/
  public void clickCreateVariableAutoPayButton() {
    clickCreateAutoPayButton("Variable");
  }

  /** Click on Create Fixed AutoPay Button.*/
  public void clickCreateFixedAutoPayButton() {
    clickCreateAutoPayButton("Fixed");
  }

  private void clickCreateAutoPayButton(String button) {
    WebElement createAutoPayButton = waitUntilElementIsClickable(
        By.xpath("//a[contains(text(),'Create " + button + " AutoPay')]"));

    highlight(createAutoPayButton);
    clickAndWait(createAutoPayButton);

    Logger.trace("Create " + button + "AutoPay Button clicked");
  }
}
