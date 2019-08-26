package com.paylease.app.qa.testbase.dataproviders;

import static com.paylease.app.qa.framework.newApi.Response.RESPONSE_CODE_142;
import static com.paylease.app.qa.framework.newApi.Response.RESPONSE_CODE_193;
import static com.paylease.app.qa.framework.newApi.Response.RESPONSE_CODE_STATUS_MAP;

import com.paylease.app.qa.framework.newApi.Response;
import org.testng.annotations.DataProvider;

public class RemovePayeeAccountDataProvider {

  @DataProvider(name = "removePayeeAccountXmlInjection", parallel = true)
  public Object[][] removePayeeAccountData() {
    return new Object[][]{
        {"tc1", "45031%253casdf%253e", "494949494949", "PayeeReferenceId", "45031%253casdf%253e",
            Response.RESPONSE_CODE_193, RESPONSE_CODE_STATUS_MAP.get(RESPONSE_CODE_193)},
        {"tc2", "304930492323049234409", "45031%253casdf%253e", "GatewayPayeeId",
            "45031%253casdf%253e", RESPONSE_CODE_142,
            RESPONSE_CODE_STATUS_MAP.get(RESPONSE_CODE_142)}
    };
  }

}

