package com.paylease.app.qa.testbase.dataproviders;

import static com.paylease.app.qa.framework.newApi.Response.RESPONSE_CODE_12;
import static com.paylease.app.qa.framework.newApi.Response.RESPONSE_CODE_STATUS_MAP;

import org.testng.annotations.DataProvider;

public class CreateBankPayeeAccountDataProvider {

  @DataProvider(name = "createBankPayeeAccountXmlInjection", parallel = true)
  public Object[][] createBankPayeeAccountXmlInjectionData() {
    return new Object[][]{
        {"tc1", "45031%253casdf%253e", "Test", "Lastname", "CA", "Checking", "Test Lastname",
            "011000028", "890234890342980", "PayeeReferenceId", "45031%253casdf%253e",
            RESPONSE_CODE_12, RESPONSE_CODE_STATUS_MAP.get(RESPONSE_CODE_12)}
    };
  }
}
