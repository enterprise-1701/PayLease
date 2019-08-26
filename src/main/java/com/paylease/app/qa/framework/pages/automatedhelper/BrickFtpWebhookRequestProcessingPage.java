package com.paylease.app.qa.framework.pages.automatedhelper;

import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.By;

/**
 * this class interacts with the BrickFTP Webhook Request process result page.
 */
public class BrickFtpWebhookRequestProcessingPage extends PageBase {

  /**
   * get the id of the request.
   *
   * @return id
   */
  public String getId() {
    return getTextBySelector(By.id("id"));
  }

  /**
   * get the action of the request.
   *
   * @return action
   */
  public String getAction() {
    return getTextBySelector(By.id("action"));
  }

  /**
   * get the interface of the request.
   *
   * @return interface
   */
  public String getInterface() {
    return getTextBySelector(By.id("interface"));
  }

  /**
   * get the path of the request.
   *
   * @return path
   */
  public String getPath() {
    return getTextBySelector(By.id("path"));
  }

  /**
   * get the destination of the request.
   *
   * @return destination
   */
  public String getDestination() {
    return getTextBySelector(By.id("destination"));
  }

  /**
   * get the at of the request.
   *
   * @return at
   */
  public String getAt() {
    return getTextBySelector(By.id("at"));
  }

  /**
   * get the username of the request.
   *
   * @return username
   */
  public String getUsername() {
    return getTextBySelector(By.id("username"));
  }

  /**
   * get the type of the request.
   *
   * @return type
   */
  public String getType() {
    return getTextBySelector(By.id("type"));
  }

  /**
   * get the deleted_at of the request.
   *
   * @return deleted_at
   */
  public String getDeletedAt() {
    return getTextBySelector(By.id("deleted_at"));
  }
}
