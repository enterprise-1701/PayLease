package com.paylease.app.qa.testbase.dataproviders;

import static com.paylease.app.qa.framework.newApi.Response.RESPONSE_CODE_140;
import static com.paylease.app.qa.framework.newApi.Response.RESPONSE_CODE_152;
import static com.paylease.app.qa.framework.newApi.Response.RESPONSE_CODE_STATUS_MAP;

import org.testng.annotations.DataProvider;

public class RemovePayerAccountDataProvider {

  @DataProvider(name = "removePayerAccountXmlInjection", parallel = true)
  public Object[][] removePayerAccountData() {
    return new Object[][]{
        {"tc1", "45031%253casdf%253e", "", "PayerReferenceId", "45031%253casdf%253e",
            RESPONSE_CODE_152, RESPONSE_CODE_STATUS_MAP.get(RESPONSE_CODE_152), true},
        {"tc2", "", "45031%253casdf%253e", "GatewayPayerId", "45031%253casdf%253e",
            RESPONSE_CODE_140, RESPONSE_CODE_STATUS_MAP.get(RESPONSE_CODE_140), false},
    };
  }

}
