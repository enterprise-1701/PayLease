package com.paylease.app.qa.framework.pages.pm;

public class VacantCostRecovery extends ExpenseManagement {

  /**
   * URL of the Statements Page in the PM UI.
   */
  private static final String URL = BASE_URL + "pm/UEM/vacant_recovery";

  /**
   * Get the page URL.
   *
   * @return String
   */
  public String getUrl() {
    return this.URL;
  }
}
