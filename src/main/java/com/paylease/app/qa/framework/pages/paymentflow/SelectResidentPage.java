package com.paylease.app.qa.framework.pages.paymentflow;

import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SelectResidentPage extends PageBase {

  @FindBy(className = "error_server")
  private WebElement invalidItemError;

  // ********************************************Action*********************************************

  /**
   * Determine if the page is loaded.
   *
   * @return true if resident table is present
   */
  public boolean pageIsLoaded() {
    return !driver.findElements(By.id("res_table")).isEmpty();
  }

  /**
   * Click on Make Payment.
   */
  public void clickToBeginPayment(String resId) {

    WebElement tableCell = driver.findElement(By.id("resId_" + resId));
    WebElement resListTableRow = tableCell.findElement(By.xpath(".."));
    WebElement paymentLink = resListTableRow.findElement(By.name("mk_pmt_submit_0"));

    highlight(paymentLink);
    clickAndWait(paymentLink);
  }

  /**
   * Grab the Invalid item error.
   *
   * @return Invalid Item specified error text.
   */
  public String getInvalidItemErrorMessage() {
    highlight(invalidItemError);

    return invalidItemError.getText();
  }

}
