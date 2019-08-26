
package com.paylease.app.qa.api.tests.gapi;

import com.paylease.app.qa.api.tests.TestCaseCollection;
import com.paylease.app.qa.api.tests.gapi.testcase.RemovePayeeAccount;
import com.paylease.app.qa.framework.DataHelper;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.api.Credentials;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import java.util.HashMap;
import java.util.List;
import org.testng.annotations.Test;


public class RemovePayeeAccountTest extends BaseTest {

  private static final String REGION = "gapi";
  private static final String FEATURE = "removePayeeAccount";

  //-----------------------------------TESTS----------------------------------------------

  @Test(groups = {"gapi", "RemovePayeeAccount"})
  public void requestValidation() {
    Logger.info("RemovePayeeAccount basic request validation");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String gatewayPayeeId = testSetupPage.getString("gatewayPayeeId");
    final String payeeReferenceId = testSetupPage.getString("payeeReferenceId");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    DataHelper dataHelper = new DataHelper();
    TestCaseCollection testCases = new GapiTestCaseCollection(
        new Credentials(gatewayId, username, password)
    );

    //RemovePayeeAccount,13,1,GAPITester1235a,32795
    testCases.add(
        new RemovePayeeAccount(
            payeeReferenceId,
            "Test Case 1 - Basic valid",
            getExpectedResponse(gatewayErrors, "13"),
            gatewayPayeeId)
    );

    //RemovePayeeAccount,93,2,,32787
    testCases.add(
        new RemovePayeeAccount(
            "",
            "Test Case 2 - empty payeeReferenceId",
            getExpectedResponse(gatewayErrors, "93"),
            gatewayPayeeId)
    );

    //RemovePayeeAccount,96,3,GAPITester1235a,
    testCases.add(
        new RemovePayeeAccount(
            dataHelper.getReferenceId(),
            "Test Case 3 - empty gateway id",
            getExpectedResponse(gatewayErrors, "96"),
            "")
    );

    //RemovePayeeAccount,125,4,refidasfiojf29jqoj3o4jush2uhq9ufhwusfdas,32795
    testCases.add(
        new RemovePayeeAccount(
            "refidasfiojf29jqoj3o4jush2uhq9ufhwusfdas",
            "Test Case 4 - invalid payeeReferenceId",
            getExpectedResponse(gatewayErrors, "125"),
            gatewayPayeeId)
    );

    String[] invalidPayeeIds = {
        "32795a", "32 795", "32!795", "32-795", "32.795",
    };

    for (String invalidPayeeId : invalidPayeeIds) {
      //RemovePayeeAccount,142,5,GAPITester1235a,32795a
      //RemovePayeeAccount,142,6,GAPITester1235a,32 795
      //RemovePayeeAccount,142,8,GAPITester1235a,32 795
      //RemovePayeeAccount,142,7,GAPITester1235a,32!795
      //RemovePayeeAccount,142,9,GAPITester1235a,32-795
      //RemovePayeeAccount,142,10,GAPITester1235a,32.795
      testCases.add(
          new RemovePayeeAccount(
              dataHelper.getReferenceId(),
              "Test Case 5-10 - invalid gatewayPayeeId",
              getExpectedResponse(gatewayErrors, "142"),
              invalidPayeeId)
      );
    }

    //RemovePayeeAccount,193,11,GAPITester1235a,32787
    testCases.add(
        new RemovePayeeAccount(
            dataHelper.getReferenceId(),
            "Test Case 11 - incorrect gatewayPayeeId",
            getExpectedResponse(gatewayErrors, "193"),
            "32787")
    );

    executeTests(testCases);
  }
}