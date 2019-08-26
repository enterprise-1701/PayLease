package com.paylease.app.qa.framework.pages.registration;

import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.By;

public class RegistrationPage extends PageBase {

  /**
   * PayLease Registration page object.
   */
  public RegistrationPage() {
    super();
  }

  // ********************************************Action*********************************************

  /**
   * Check if navigation from old page has occurred and new page has loaded.
   *
   * @return true when  billing, renter, homeowner and pm create button are present
   */
  public boolean pageIsLoaded() {
    return isElementPresentBySelector(By.id("reg_link_billing"))
        && isElementPresentBySelector(By.id("reg_link_renter"))
        && isElementPresentBySelector(By.id("reg_link_homeowner"))
        && isElementPresentBySelector(By.id("reg_link_pm"));
  }
}
