package com.paylease.app.qa.framework.pages.automatedhelper;

import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.By;

/**
 * this class interacts with the transaction process result page.
 */
public class MriTransactionProcessingPage extends PageBase {

  /**
   * get the response of the mri transaction.
   *
   * @return response
   */
  public String getResponse() {
    return getTextBySelector(By.id("response"));
  }

  /**
   * Get status from mri transaction table.
   *
   * @return status
   */
  public String getStatus() {
    return getTextBySelector(By.id("status"));
  }
}
