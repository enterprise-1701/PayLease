package com.paylease.app.qa.testbase.dataproviders;

import org.testng.annotations.DataProvider;

public class PropertyDataSyncDataProvider {

  /**
   * Provides setup for Properties with no gapi pricing to sync.
   *
   * @return data
   */
  @DataProvider()
  public Object[][] provideNoGapi() {

    return new Object[][]{
        //Test case id, objective
        {"tc7a", "Verify that gapi pricing is not reported when a PM is not using gapi"},
        {"tc7b",
            "Verify that gapi pricing is not reported when a PM is using GAPI but property has no payment code"},
        {"tc7c",
            "Verify that gapi pricing is not reported when a PM is using GAPI but property has no active payment code"},
    };
  }

  /**
   * Provides setup for Properties with flexible fees.
   *
   * @return data
   */
  @DataProvider()
  public Object[][] provideFlexible() {

    return new Object[][]{
        //Test case id, objective
        {"tc10", "Verify that flexible fees are reported when tiered fee structure is enabled"},
        {"tc11", "Verify custom setting pricing are reported when custom setting fee is present"},
        {"tc12",
            "Verify custom setting pricing are reported when many custom settings fees are present"},
    };
  }
}
