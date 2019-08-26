package com.paylease.app.qa.framework.pages.admin;

import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TransactionRefundPage extends PageBase {

  @FindBy(id = "refund-submit-btn")
  private WebElement submitBtn;

  public void submitRefund() {
    clickAndWait(submitBtn);
  }

  /** Click submit button. */
  public void submitRefundToResident() {
    clickAndWait(driver.findElement(By.xpath("//input[@value='Submit']")));
  }

  /** Click submit button. */
  public void submitRefundToResidentForFnbo() {
    clickAndWait(driver.findElement(By.id("dupes-submit")));
  }

  /** Click "Void This Payment" button. */
  public void submitVoid() {
    clickAndWait(driver.findElement(By.xpath("//input[@value='VOID THIS PAYMENT']")));
  }

  public void inputPartialAmountToRefund() {
    WebElement refundTextBox = driver.findElement(By.id("amount_field"));
    setKeys(refundTextBox, "1.00");
  }

  public void inputPartialAmountToRefundForFnbo() {
    WebElement refundTextBox = driver
        .findElement(By.id("refund_amount"));
    setKeys(refundTextBox, "1.00");
  }
}
