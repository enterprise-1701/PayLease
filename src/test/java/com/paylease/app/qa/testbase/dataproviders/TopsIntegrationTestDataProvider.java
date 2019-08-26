package com.paylease.app.qa.testbase.dataproviders;

import org.testng.annotations.DataProvider;

public class TopsIntegrationTestDataProvider {

  /**
   * Provides data required to run various kinds of test combinations for Resident.
   *
   * @return data
   */
  @DataProvider(name = "topsIntegrationData")
  public Object[][] dataTopIntegration() {
    return new Object[][]{
        //TC / Today is holiday / Yesteray was holiday / date was a holiday / use set sent date
        // set sent date / Command
        {"tc01", false, false, false, false, 0, "auto_export_tops.php"},
        {"tc02", true, false, false, false, 0, "auto_export_tops.php"},
        {"tc03", false, true, false, false, 0, "auto_export_tops.php"},
        {"tc04", false, true, false, true, -1, "auto_export_tops.php yesterday"},
        {"tc05", true, false, false, true, -1, "auto_export_tops.php yesterday"},
        {"tc06", false, false, false, true, -1, "auto_export_tops.php yesterday"},
        {"tc07", true, false, false, true, -1, "auto_export_tops.php now"},
        {"tc08", false, false, false, true, 0, "auto_export_tops.php now"},
        {"tc09", false, false, true, true, -1, "auto_export_tops.php"}
    };
  }
}
