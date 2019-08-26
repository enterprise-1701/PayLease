package com.paylease.app.qa.testbase.dataproviders;

import org.testng.annotations.DataProvider;

public class ProcessingRdcSendJsonFileDataProvider {

  /**
   * Provides data required to run various kinds of test combinations for different processors.
   *
   * @return data
   */
  @DataProvider()
  public Object[][] provideNoJobs() {

    return new Object[][]{
        //Test case id, objective
        {"tc6552", "Verify log message indicates no job for this batch file has been produced - batch file already sent"},
        {"tc6556", "Verify log message indicates no job for this batch file has been produced - json file does not exist"},
        {"tc6557", "Verify log message indicates no job for this batch file has been produced - json file invalid"},
        {"tc6558", "Verify log message indicates no job for this batch file has been produced - json file not valid X937"},
        {"tc6559", "Verify log message indicates no job for this batch file has been produced - x937 file does not exist"},
    };
  }
}
