package com.paylease.app.qa.testbase.dataproviders;

import org.testng.annotations.DataProvider;

public class LinkedAccountsTestDataProvider {

  /**
   * Provides data required to run various kinds of test combinations for Resident.
   *
   * @return data
   */
  @DataProvider(name = "linkedAccountsDataResident", parallel = true)
  public Object[][] dataResident() {

    return new Object[][]{
        //TestCase, linkedAccountOrder

        {"tc01", 1},
        {"tc02", 2},
    };
  }
}
