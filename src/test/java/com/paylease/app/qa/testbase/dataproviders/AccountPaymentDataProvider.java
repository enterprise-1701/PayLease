package com.paylease.app.qa.testbase.dataproviders;

import static com.paylease.app.qa.framework.newApi.Response.RESPONSE_CODE_152;
import static com.paylease.app.qa.framework.newApi.Response.RESPONSE_CODE_STATUS_MAP;

import org.testng.annotations.DataProvider;

public class AccountPaymentDataProvider {

  @DataProvider(name = "accountPaymentXmlInjection", parallel = true)
  public Object[][] accountPaymentXml() {
    return new Object[][]{
        {"tc1", "395%253casdf%253e", "1234567800000", "589059045890", "3454890345890", "8459980489",
            false, "USD", "", "", "", "PaymentTraceId", "395%253casdf%253e", RESPONSE_CODE_152,
            RESPONSE_CODE_STATUS_MAP.get(RESPONSE_CODE_152)}
    };
  }
}
