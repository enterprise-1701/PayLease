package com.paylease.app.qa.framework.pages.pm;

/**
 * MoveOutCalculator represents the Move Out Calculator in the Pm UI.
 */
public class MoveOutCalculator extends Billing {

  /**
   * URL of the Statements Page in the PM UI.
   */
  private static final String URL = BASE_URL + "pm/move_out";

  /**
   * Get the page URL.
   *
   * @return String
   */
  public String getUrl() {
    return this.URL;
  }
}
