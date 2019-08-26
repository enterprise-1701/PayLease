package com.paylease.app.qa.framework.pages.automatedhelper;

import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AutoPaysProcessingPage extends PageBase {

  @FindBy(id = "transId")
  private WebElement transactionId;

  // ********************************************Action*********************************************

  public String getTransactionId() {

    return transactionId.getText();
  }

}
