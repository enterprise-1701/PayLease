
package com.paylease.app.qa.api.tests.gapi;

import com.paylease.app.qa.api.tests.TestCaseCollection;
import com.paylease.app.qa.api.tests.gapi.testcase.GetRequestToken;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.api.Credentials;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import java.util.HashMap;
import java.util.List;
import org.testng.annotations.Test;


public class GetRequestTokenTest extends BaseTest {

  private static final String REGION = "gapi";
  private static final String FEATURE = "getRequestToken";

  //-----------------------------------TESTS----------------------------------------------

  @Test(groups = {"gapi", "GetRequestToken"})
  public void requestValidation() {
    Logger.info("GetRequestToken basic request validation");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    TestCaseCollection testCases = new GapiTestCaseCollection(
        new Credentials(gatewayId, username, password)
    );

    //GetRequestToken,2,1
    testCases.add(
        new GetRequestToken(
            "Test Case 1 - valid request",
            getExpectedResponse(gatewayErrors, "2"))
    );

    executeTests(testCases);
  }
}

