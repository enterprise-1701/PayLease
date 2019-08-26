package com.paylease.app.qa.testbase.dataproviders;

import org.testng.annotations.DataProvider;

public class BossPmDataSyncDataProvider {
  @DataProvider()
  public Object[][] provideNonActiveMasterPms() {
    return new Object[][]{
        {"tc2", "To validate non-existent PM ID is not sent to Boomi"},
        {"tc2b", "To validate non master PM ID is not sent to Boomi"},
        {"tc2c", "To validate inactive PM ID is not sent to Boomi"},
    };
  }

}
