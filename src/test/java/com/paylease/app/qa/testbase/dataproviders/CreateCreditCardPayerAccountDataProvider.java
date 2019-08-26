package com.paylease.app.qa.testbase.dataproviders;

import static com.paylease.app.qa.framework.newApi.Response.RESPONSE_CODE_10;
import static com.paylease.app.qa.framework.newApi.Response.RESPONSE_CODE_STATUS_MAP;
import static com.paylease.app.qa.framework.pages.paymentflow.CardAccountDetailsPage.VALID_CREDIT;

import org.testng.annotations.DataProvider;

public class CreateCreditCardPayerAccountDataProvider {

  @DataProvider(name = "creditCardPayerAccountXmlInjection", parallel = true)
  public Object[][] creditCardPayerAccountData() {
    return new Object[][]{
        {"tc1", "45031%253casdf%253e", "Test", "Lastname", "Visa", VALID_CREDIT, "01", "20", "222",
            "Test", "Lastnamebilling", "testStree", "city", "CA", "US", "92929", "PayerReferenceId",
            "45031%253casdf%253e", RESPONSE_CODE_10, RESPONSE_CODE_STATUS_MAP.get(RESPONSE_CODE_10)}
    };
  }

}
