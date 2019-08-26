package com.paylease.app.qa.testbase.dataproviders;

import static com.paylease.app.qa.framework.newApi.Response.RESPONSE_CODE_333;
import static com.paylease.app.qa.framework.newApi.Response.RESPONSE_CODE_STATUS_MAP;

import org.testng.annotations.DataProvider;

public class AchPaymentDataProvider {

  @DataProvider(name = "achPaymentXmlInjection", parallel = true)
  public Object[][] achPaymentXml() {
    return new Object[][]{
        {"tc1", "395%253casdf%253e", "252525255", "dpfogkdfgp", "", "134029340", "300", "",
            "Checking", false, "", "", "No", "", "", "", "", false, false, false, "", "tester",
            "lastname", "tester lastname", "011000028", "PaymentReferenceId", "395%253casdf%253e",
            RESPONSE_CODE_333, RESPONSE_CODE_STATUS_MAP.get(RESPONSE_CODE_333)}
    };
  }
}
