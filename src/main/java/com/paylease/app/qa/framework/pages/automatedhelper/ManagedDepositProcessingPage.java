package com.paylease.app.qa.framework.pages.automatedhelper;

import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

/**
 * this class interacts with the managed deposit process result page
 */
public class ManagedDepositProcessingPage extends PageBase {

  @FindBy(id = "depositId")
  private WebElement depositId;

  @FindBy(id = "externalStatus")
  private WebElement externalStatus;

  @FindBy(id = "internalStatus")
  private WebElement internalStatus;

  /**
   * get the id of the managed deposit that was created or updated
   *
   * @return managed deposit id
   */
  public String getDepositId() {
    return depositId.getText();
  }

  /**
   * get the external status of the managed deposit that was created or updated
   *
   * @return either CLOSED or OPEN
   */
  public String getExternalStatus() {
    return externalStatus.getText();
  }

  /**
   * get the internal status of the managed deposit that was created or updated
   *
   * @return either CLOSED or OPEN
   */
  public String getInternalStatus() {
    return internalStatus.getText();
  }
}
