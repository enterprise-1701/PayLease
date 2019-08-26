package com.paylease.app.qa.testbase.dataproviders;

import static com.paylease.app.qa.framework.newApi.Response.RESPONSE_CODE_10;
import static com.paylease.app.qa.framework.newApi.Response.RESPONSE_CODE_STATUS_MAP;

import org.testng.annotations.DataProvider;

public class CreateBankPayerAccountDataProvider {

  @DataProvider(name = "createBankPayerAccountXmlInjection", parallel = true)
  public Object[][] createBankPayerAccountData() {
    return new Object[][]{
        {"tc1", "45031%253casdf%253e", "first", "last", "Checking", "first last", "011000028",
            "002002002", "", "PayerReferenceId", "45031%253casdf%253e", RESPONSE_CODE_10,
            RESPONSE_CODE_STATUS_MAP.get(RESPONSE_CODE_10)}
    };
  }
}
