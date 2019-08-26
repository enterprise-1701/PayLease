package com.paylease.app.qa.testbase.dataproviders;

import static com.paylease.app.qa.framework.newApi.Response.RESPONSE_CODE_152;
import static com.paylease.app.qa.framework.newApi.Response.RESPONSE_CODE_STATUS_MAP;

import org.testng.annotations.DataProvider;

public class AccountSearchDataProvider {

  @DataProvider(name = "accountSearchXmlInjection", parallel = true)
  public Object[][] accountSearchData() {
    return new Object[][]{
        {"tc1", "395%253casdf%253e", "", "PayerReferenceId", "395%253casdf%253e", RESPONSE_CODE_152,
            RESPONSE_CODE_STATUS_MAP.get(RESPONSE_CODE_152)}
    };
  }
}
