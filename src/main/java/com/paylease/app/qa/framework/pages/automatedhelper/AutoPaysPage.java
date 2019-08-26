package com.paylease.app.qa.framework.pages.automatedhelper;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AutoPaysPage extends PageBase {

  private static final String URL = BASE_URL + "testing/automated_helper/autopays";

  public static final String FIXED = "fixed";
  public static final String VARIABLE = "variable";

  @FindBy(id = "templateId")
  private WebElement templateIdBox;

  @FindBy(name = "runAutopays")
  private WebElement runAutopaysButton;

  // ********************************************Action*********************************************

  public void open() {
    openAndWait(URL);
  }

  /**
   * Run the autopay.
   *
   * @param type Autopay template type to process
   * @param templateId templateId to search and process the correct autopay
   * @return AutoPaysProcessingPage
   */
  public AutoPaysProcessingPage runAutoPay(String type, String templateId) {
    String idValue = getIdValue(type);

    Logger.trace("ID value is: " + idValue);

    WebElement radioButton = driver.findElement(By.id(idValue));
    radioButton.click();

    templateIdBox.click();
    templateIdBox.clear();
    templateIdBox.sendKeys(templateId);

    clickAndWait(runAutopaysButton);

    return new AutoPaysProcessingPage();
  }

  private String getIdValue(String type) {
    String idValue;
    switch (type) {
      case FIXED:
        idValue = "templateTypeFixed";
        break;
      case VARIABLE:
        idValue = "templateTypeVariable";
        break;
      default:
        throw new IllegalArgumentException("Invalid value");
    }
    return idValue;
  }

}
