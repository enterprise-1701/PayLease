package com.paylease.app.qa.api.tests.aapi;

import com.paylease.app.qa.api.tests.TestCaseCollection;
import com.paylease.app.qa.api.tests.aapi.testcase.CancelEpsTransaction;
import com.paylease.app.qa.framework.DataHelper;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.api.Credentials;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.testng.annotations.Test;


public class CancelEpsTransactionTest extends BaseTest {

  private static final String REGION = "aapi";
  private static final String FEATURE = "CancelEpsTransaction";

  //-----------------------------------TESTS--------------------------------------------------------

  @Test(groups = {"aapi", "CancelEPSTransaction"})
  public void cancelEpsTransaction() {
    Logger.info("CancelEPSTransaction Test");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();
    final String userId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String vendorId = testSetupPage.getString("vendorId");
    final String extAcctId = testSetupPage.getString("extAcctId");
    final String extTransactionId = testSetupPage.getString("gatewayTransId");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    ArrayList<TestCaseCollection> testCases = new ArrayList<>();

    //CancelEPSTransaction,1,1,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,1,8124194997,
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password),
            "Test",
            "fiserv"
        ).add(
            new CancelEpsTransaction(
                "Test case 1 - valid test",
                getExpectedResponse(gatewayErrors, "1"),
                vendorId,
                extAcctId,
                extTransactionId)
        ));

    //CancelEPSTransaction,8,2,317343184,be297ejCEn,,Test,fiserv,1,9914632345,
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password, ""),
            "Test",
            "fiserv"
        ).add(
            new CancelEpsTransaction(
                "Test case 2 - empty api key",
                getExpectedResponse(gatewayErrors, "8"),
                vendorId,
                extAcctId,
                extTransactionId)
        ));

    //CancelEPSTransaction,9,3,317343184,be297ejCEn,badapikey,Test,fiserv,1,9914632345,
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password, "badapikey"),
            "Test",
            "fiserv"
        ).add(
            new CancelEpsTransaction(
                "Test case 3 - bad api key",
                getExpectedResponse(gatewayErrors, "9"),
                vendorId,
                extAcctId,
                extTransactionId)
        ));

    //CancelEPSTransaction,20,4,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,,fiserv,1,9914632345,
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password),
            "",
            "fiserv"
        ).add(
            new CancelEpsTransaction(
                "Test case 4 - empty mode",
                getExpectedResponse(gatewayErrors, "20"),
                vendorId,
                extAcctId,
                extTransactionId)
        ));

    //CancelEPSTransaction,38,5,31734318,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,1,9914632345,
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials("31734318", username, password),
            "Test",
            "fiserv"
        ).add(
            new CancelEpsTransaction(
                "Test case 5 - bad username",
                getExpectedResponse(gatewayErrors, "38"),
                vendorId,
                extAcctId,
                extTransactionId)
        ));

    //CancelEPSTransaction,38,6, ,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,1,9914632345,
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(" ", username, password),
            "Test",
            "fiserv"
        ).add(
            new CancelEpsTransaction(
                "Test case 6 - blank user id",
                getExpectedResponse(gatewayErrors, "38"),
                vendorId,
                extAcctId,
                extTransactionId)
        ));

    //CancelEPSTransaction,38,7,317343184,badpass,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,1,9914632345,
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, "badpass"),
            "Test",
            "fiserv"
        ).add(
            new CancelEpsTransaction(
                "Test case 7 - password error",
                getExpectedResponse(gatewayErrors, "38"),
                vendorId,
                extAcctId,
                extTransactionId)
        ));

    //CancelEPSTransaction,45,8,317343184,,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,1,9914632345,
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, ""),
            "Test",
            "fiserv"
        ).add(
            new CancelEpsTransaction(
                "Test case 8 - password error",
                getExpectedResponse(gatewayErrors, "45"),
                vendorId,
                extAcctId,
                extTransactionId)
        ));

    //CancelEPSTransaction,59,9,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,,9914632345,
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password),
            "Test",
            "fiserv"
        ).add(
            new CancelEpsTransaction(
                "Test case 9 - empty vendor id",
                getExpectedResponse(gatewayErrors, "59"),
                "",
                extAcctId,
                extTransactionId)
        ));

    //CancelEPSTransaction,60,10,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,1,,
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password),
            "Test",
            "fiserv"
        ).add(
            new CancelEpsTransaction(
                "Test case 10 - empty extAccId",
                getExpectedResponse(gatewayErrors, "60"),
                vendorId,
                "",
                extTransactionId)
        ));

    //CancelEPSTransaction,61,11,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,1,8124194997,NULL
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password),
            "Test",
            "fiserv"
        ).add(
            new CancelEpsTransaction(
                "Test case 11 - empty extTransactionId",
                getExpectedResponse(gatewayErrors, "61"),
                vendorId,
                extAcctId,
                null)
        ));

    //CancelEPSTransaction,66,12,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,1,8124194997,25432
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password),
            "Test",
            "fiserv"
        ).add(
            new CancelEpsTransaction(
                "Test case 12 - incorrect extTransactionId",
                getExpectedResponse(gatewayErrors, "66"),
                vendorId,
                extAcctId,
                "25432")
        ));

    final String[] vendorIdValues = {
        "1a", "2345",
    };

    for (String vendorIdValue : vendorIdValues) {
      //CancelEPSTransaction,71,13,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,1a,9914632345,
      //CancelEPSTransaction,71,14,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,2345,9914632345,
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password),
              "Test",
              "fiserv"
          ).add(
              new CancelEpsTransaction(
                  "Test case 13/14 - invalid ext acc ids",
                  getExpectedResponse(gatewayErrors, "71"),
                  vendorIdValue,
                  extAcctId,
                  extTransactionId)
          ));
    }

    final String[] extAccIdValues = {
        "9914632", "9914632a",
    };

    for (String extAccIdValue : extAccIdValues) {
      //CancelEPSTransaction,75,15,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,1,99146323,
      //CancelEPSTransaction,75,16,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,1,99146323a,
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password),
              "Test",
              "fiserv"
          ).add(
              new CancelEpsTransaction(
                  "Test case 15/16 - invalid ext acc ids",
                  getExpectedResponse(gatewayErrors, "75"),
                  vendorId,
                  extAccIdValue,
                  extTransactionId)
          ));
    }

    executeTests(testCases);
  }
}

