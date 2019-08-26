package com.paylease.app.qa.framework.pages.resident;

import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.By;

public class SsoErrorPage extends PageBase {

  /**
   * Get the text from body.
   *
   * @return error text
   */
  public String getErrorText() {
    return getTextBySelector(By.cssSelector("body"));
  }

}
