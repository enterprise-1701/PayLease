package com.paylease.app.qa.framework.pages.automatedhelper;

import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.By;

public class ResmanTransactionProcessingPage extends PageBase {

  /**
   * Get Notification type.
   *
   * @param transId Trans ID
   * @return Notification Type
   */
  public String getNotificationType(String transId) {
    return getTextBySelector(By.id(transId));
  }

  /**
   * Get count of Resman Trans.
   *
   * @return number of trans
   */
  public String getResmanTransCount() {
    return getTextBySelector(By.id("numOfTrans"));
  }

  /**
   * Get Integration Status.
   *
   * @param transId Trans ID
   * @return Integration Status
   */
  public String getStatus(String transId) {
    return getTextBySelector(By.id(transId + "Status"));
  }
}
