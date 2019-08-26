package com.paylease.app.qa.testbase.dataproviders;

import static com.paylease.app.qa.framework.newApi.Response.RESPONSE_CODE_10;
import static com.paylease.app.qa.framework.newApi.Response.RESPONSE_CODE_STATUS_MAP;

import com.paylease.app.qa.framework.DataHelper;
import org.testng.annotations.DataProvider;

public class IssueCashPermCardDataProvider {

  @DataProvider(name = "issueCashPermCardXmlInjection", parallel = true)
  public Object[][] issueCashPermCardData() {
    DataHelper dataHelper = new DataHelper();
    return new Object[][]{
        {"tc1", "395%253casdf%253e", "Test", "Lastname", dataHelper.getLuhnCardNumber(),
            "PayerReferenceId", "395%253casdf%253e", RESPONSE_CODE_10,
            RESPONSE_CODE_STATUS_MAP.get(RESPONSE_CODE_10)}
    };
  }
}
