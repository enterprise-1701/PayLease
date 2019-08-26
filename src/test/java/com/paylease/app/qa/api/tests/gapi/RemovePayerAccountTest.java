
package com.paylease.app.qa.api.tests.gapi;

import com.paylease.app.qa.api.tests.TestCaseCollection;
import com.paylease.app.qa.api.tests.gapi.testcase.RemovePayerAccount;
import com.paylease.app.qa.framework.DataHelper;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.api.Credentials;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import java.util.HashMap;
import java.util.List;
import org.testng.annotations.Test;


public class RemovePayerAccountTest extends BaseTest {

  private static final String REGION = "gapi";
  private static final String FEATURE = "removePayerAccount";

  //-----------------------------------TESTS----------------------------------------------

  @Test(groups = {"gapi", "RemovePayerAccount"})
  public void requestValidation() {
    Logger.info("RemovePayerAccount basic request validation");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String gatewayPayerId = testSetupPage.getString("gatewayPayerId");
    final String payerReferenceId = testSetupPage.getString("payerReferenceId");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    DataHelper dataHelper = new DataHelper();
    TestCaseCollection testCases = new GapiTestCaseCollection(
        new Credentials(gatewayId, username, password)
    );

    //RemovePayerAccount,11,1,GAPITesterACH,860542
    testCases.add(
        new RemovePayerAccount(
            payerReferenceId,
            "Test Case 1 - Basic valid",
            getExpectedResponse(gatewayErrors, "11"),
            gatewayPayerId)
    );

    //RemovePayerAccount,69,2,,860542
    testCases.add(
        new RemovePayerAccount(
            "",
            "Test Case 2 - empty PayerReferenceID",
            getExpectedResponse(gatewayErrors, "69"),
            gatewayPayerId)
    );

    //RemovePayerAccount,91,3,GAPITesterACH,
    testCases.add(
        new RemovePayerAccount(
            dataHelper.getReferenceId(),
            "Test Case 3 - empty gatewayPayerId",
            getExpectedResponse(gatewayErrors, "91"),
            "")
    );

    //RemovePayerAccount,111,4,a134oihqf9w7h2ufhqiusd872q45y92715yr7q8wgfasyfasf,860542
    testCases.add(
        new RemovePayerAccount(
            "a134oihqf9w7h2ufhqiusd872q45y92715yr7q8wgfasyfasf",
            "Test Case 4 - invalid payerReferenceId",
            getExpectedResponse(gatewayErrors, "111"),
            gatewayPayerId)
    );

    String[] invalidPayerIds = {
        "860542a", "860 542", "8605.42", "860!542", "300-020",
    };

    for (String invalidPayerId : invalidPayerIds) {
      //RemovePayerAccount,140,5,GAPITesterACH,860542a
      //RemovePayerAccount,140,6,GAPITesterACH,860 542
      //RemovePayerAccount,140,7,GAPITesterACH,8605.42
      //RemovePayerAccount,140,8,GAPITesterACH,860!542
      //RemovePayerAccount,140,9,GAPITesterACH,300-020
      testCases.add(
          new RemovePayerAccount(
              dataHelper.getReferenceId(),
              "Test Case 5-9 - invalid gatewayPayerId",
              getExpectedResponse(gatewayErrors, "140"),
              invalidPayerId)
      );
    }

    //RemovePayerAccount,152,10,GAPITesterACH,1212
    testCases.add(
        new RemovePayerAccount(
            dataHelper.getReferenceId(),
            "Test Case 10 - incorrect gatewayPayerId",
            getExpectedResponse(gatewayErrors, "152"),
            "1212")
    );

    executeTests(testCases);
  }
}