package com.paylease.app.qa.framework.pages.admin;

import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.By;

public class ComplianceKycPage extends PageBase {

  private static final String URL =
      BASE_URL + "/admin/reports/compliance_kyc";

  /**
   * Check if Compliance KYC Report loads
   *
   * @return true when Get KYC Info button is found
   */
  public boolean pageIsLoaded() {
    return isElementPresentBySelector(By.cssSelector("#submit"))
        && isElementPresentBySelector(By.cssSelector("input[value='Get KYC Info']"));
  }

  /** open the page */
  public void open() {
    openAndWait(URL);
  }
}
