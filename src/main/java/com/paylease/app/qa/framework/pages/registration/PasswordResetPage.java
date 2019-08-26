package com.paylease.app.qa.framework.pages.registration;

import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.By;

public class PasswordResetPage extends PageBase {

  /**
   * PayLease Password Reset page object.
   */
  public PasswordResetPage() {
    super();
  }

  // ********************************************Action*********************************************

  /**
   * Check if navigation from old page has occurred and new page has loaded.
   *
   * @return true when email field and request password reset button is present
   */
  public boolean pageIsLoaded() {
    return isElementPresentBySelector(By.id("email"))
        && isElementPresentBySelector(By.id("get_pass"));
  }
}
