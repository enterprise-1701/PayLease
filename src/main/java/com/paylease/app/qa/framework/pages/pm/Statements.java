package com.paylease.app.qa.framework.pages.pm;

/**
 * Represents the Billing Statements Page in the PM UI.
 */
public class Statements extends Billing {

  /**
   * URL of the Statements Page in the PM UI.
   */
  private static final String URL = BASE_URL + "/pm/statements";

  /**
   * Get the page URL.
   *
   * @return String
   */
  public String getUrl() {
    return this.URL;
  }
}
