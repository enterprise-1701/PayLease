package com.paylease.app.qa.framework.pages.pm;

/**
 * Prebill Page represents the PreBilling page in the Pm UI.
 */
public class PrebillPage extends Billing {

  /**
   * URL of the Statements Page in the PM UI.
   */
  private static final String URL = BASE_URL + "pm/billing/prebill";

  /**
   * Get the page URL.
   *
   * @return String
   */
  public String getUrl() {
    return this.URL;
  }
}
