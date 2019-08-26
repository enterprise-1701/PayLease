package com.paylease.app.qa.testbase.dataproviders;

import static com.paylease.app.qa.framework.newApi.Response.RESPONSE_CODE_10;
import static com.paylease.app.qa.framework.newApi.Response.RESPONSE_CODE_STATUS_MAP;

import org.testng.annotations.DataProvider;

public class IssueCashRentCardDataProvider {

  @DataProvider(name = "issueCashRentCardXmlInjection", parallel = true)
  public Object[][] issueCashRentCardData() {
    return new Object[][]{
        {"tc1", "45031%253casdf%253e", "Test", "Lastname", "PayerReferenceId",
            "45031%253casdf%253e", RESPONSE_CODE_10, RESPONSE_CODE_STATUS_MAP.get(
            RESPONSE_CODE_10)}
    };
  }
}
