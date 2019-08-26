package com.paylease.app.qa.framework.pages.pm;

/**
 * PmUtilityChargeFiles represents the Utility Charge Files in the Pm UI.
 */
public class UtilityChargeFiles extends Billing {

  /**
   * URL of the Statements Page in the PM UI.
   */
  private static final String URL = BASE_URL + "pm/billing/chargefile";

  /**
   * Get the page URL.
   *
   * @return String
   */
  public String getUrl() {
    return this.URL;
  }
}
