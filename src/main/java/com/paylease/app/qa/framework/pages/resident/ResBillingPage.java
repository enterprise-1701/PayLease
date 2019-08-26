package com.paylease.app.qa.framework.pages.resident;

import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.By;

public class ResBillingPage extends PageBase {

  private static final String URL = BASE_URL + "resident/billing";

  /**
   * Resident billing page object.
   */
  public ResBillingPage() {
    super();
  }

  // ********************************************Action*********************************************

  public void open() {
    openAndWait(URL);
  }

  /**
   * Check if navigation from old page has occurred and new page has loaded.
   *
   * @return true when page title is correct and table is present
   */
  public boolean pageIsLoaded() {
    return getTitle().equals("BILLING")
        && isElementPresentBySelector(By.id("billing_statements"));
  }
}
