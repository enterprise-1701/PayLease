
package com.paylease.app.qa.api.tests.gapi;

import com.paylease.app.qa.api.tests.TestCaseCollection;
import com.paylease.app.qa.api.tests.gapi.testcase.UpdateIntegrationIdByTransaction;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.api.Credentials;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import java.util.HashMap;
import java.util.List;
import org.testng.annotations.Test;


public class UpdateIntegrationIdByTransactionTest extends BaseTest {

  private static final String REGION = "gapi";
  private static final String FEATURE = "updateIntegrationIdByTransaction";

  //-----------------------------------TESTS----------------------------------------------

  @Test(groups = {"gapi", "UpdateIntegrationIdByTransaction"})
  public void requestValidation() {
    Logger.info("UpdateIntegrationIdByTransaction request validation");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String transactionId = testSetupPage.getString("transactionId");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    TestCaseCollection testCases = new GapiTestCaseCollection(
        new Credentials(gatewayId, username, password)
    );

    //UpdateIntegrationIdByTransaction,215,1,60904082,QAGAPUIntegrationUpdate_01,NULL
    testCases.add(
        new UpdateIntegrationIdByTransaction(
            transactionId,
            "Test Case 1 - basic valid",
            getExpectedResponse(gatewayErrors, "215"))
            .setIntegrationId("QAGAPUIntegrationUpdate_01")
    );

    executeTests(testCases);
  }
}