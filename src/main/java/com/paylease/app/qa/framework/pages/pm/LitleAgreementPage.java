package com.paylease.app.qa.framework.pages.pm;

import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class LitleAgreementPage extends PageBase {

  private static final String URL = BASE_URL + "pm/dashboard/litle_agree";

  // ********************************************Action*********************************************

  /**
   * Determine if page is loaded.
   *
   * @return true if the page title = "Merchant Services Agreement"
   */
  public boolean pageIsLoaded() {
    WebElement pageTitle = driver.findElement(By.className("page_title"));
    String titleText = pageTitle.getText();

    return titleText.equalsIgnoreCase("Merchant Services Agreement");
  }

  public void clickContinueToLitleForm() {
    WebElement btn = driver.findElement(By.id("continue_litle"));
    highlight(btn);
    btn.click();
  }

  public void fillOutDob() {
    setDateFieldValue("ltle_dob", "08/29/1990");
  }

  public void open() {
    openAndWait(URL);
  }

  public void submitLitleForm() {
    clickAndWait(driver.findElement(By.id("ltle_submit")));
  }
}
