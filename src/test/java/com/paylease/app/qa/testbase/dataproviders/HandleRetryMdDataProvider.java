package com.paylease.app.qa.testbase.dataproviders;

import org.testng.annotations.DataProvider;

public class HandleRetryMdDataProvider {

  /**
   * Provides data required to run various retry mechanisms for Handling retry in MD.
   *
   * @return data
   */
  @DataProvider(name = "retry")
  public Object[][] data() {

    return new Object[][]{
        //TestCase, non-retryable, max retry
        {"tc01", false, false},
        {"tc02", true, false},
        {"tc03", false, true}
    };
  }
}