package com.paylease.app.qa.api.tests.aapi;

import com.paylease.app.qa.api.tests.TestCaseCollection;
import com.paylease.app.qa.api.tests.aapi.testcase.GetEpsTransactionStatus;
import com.paylease.app.qa.framework.DataHelper;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.api.Credentials;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.testng.annotations.Test;


public class GetEpsTransactionStatusTest extends BaseTest {

  private static final String REGION = "aapi";
  private static final String FEATURE = "GetEpsTransactionStatus";

  //-----------------------------------TESTS--------------------------------------------------------

  @Test(groups = {"aapi", "GetEPSTransactionStatus"})
  public void getEpsTransaction() {
    Logger.info("GetEpsTransactionStatus Test");

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

    //GetEPSTransactionStatus,1,1,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,1,8124194997,
    // second test is the same - python code put in the extTransactionId automatically if it was missing
    //GetEPSTransactionStatus,1,2,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,1,8124194997,1368634245
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password),
            "Test",
            "fiserv"
        ).add(
            new GetEpsTransactionStatus(
                "Test case 1 - valid test",
                getExpectedResponse(gatewayErrors, "1"),
                vendorId,
                extAcctId,
                extTransactionId)
        ));

    //GetEPSTransactionStatus,8,3,317343184,be297ejCEn,,Test,fiserv,1,9914632345,
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password, ""),
            "Test",
            "fiserv"
        ).add(
            new GetEpsTransactionStatus(
                "Test case 3 - empty api key",
                getExpectedResponse(gatewayErrors, "8"),
                vendorId,
                extAcctId,
                extTransactionId)
        ));

    //GetEPSTransactionStatus,9,4,317343184,be297ejCEn,badapikey,Test,fiserv,1,9914632345,
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password, "badapikey"),
            "Test",
            "fiserv"
        ).add(
            new GetEpsTransactionStatus(
                "Test case 4 - bad api key",
                getExpectedResponse(gatewayErrors, "9"),
                vendorId,
                extAcctId,
                extTransactionId)
        ));

    //GetEPSTransactionStatus,20,5,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,,fiserv,1,9914632345,
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password),
            "",
            "fiserv"
        ).add(
            new GetEpsTransactionStatus(
                "Test case 5 - empty mode",
                getExpectedResponse(gatewayErrors, "20"),
                vendorId,
                extAcctId,
                extTransactionId)
        ));

    //GetEPSTransactionStatus,38,6,317343,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,1,9914632345,
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials("317343", username, password),
            "Test",
            "fiserv"
        ).add(
            new GetEpsTransactionStatus(
                "Test case 6 - bad userId",
                getExpectedResponse(gatewayErrors, "38"),
                vendorId,
                extAcctId,
                extTransactionId)
        ));

    //GetEPSTransactionStatus,38,7,,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,1,9914632345,
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials("", username, password),
            "Test",
            "fiserv"
        ).add(
            new GetEpsTransactionStatus(
                "Test case 7 - empty userId",
                getExpectedResponse(gatewayErrors, "38"),
                vendorId,
                extAcctId,
                extTransactionId)
        ));

    //GetEPSTransactionStatus,38,8,317343184,badpass,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,1,9914632345,
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, "badpass"),
            "Test",
            "fiserv"
        ).add(
            new GetEpsTransactionStatus(
                "Test case 8 - bad password",
                getExpectedResponse(gatewayErrors, "38"),
                vendorId,
                extAcctId,
                extTransactionId)
        ));

    //GetEPSTransactionStatus,38,9,3173431,badpass,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,1,9914632345,
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials("3173431", username, "badpass"),
            "Test",
            "fiserv"
        ).add(
            new GetEpsTransactionStatus(
                "Test case 9 - bad userId and bad password",
                getExpectedResponse(gatewayErrors, "38"),
                vendorId,
                extAcctId,
                extTransactionId)
        ));

    //GetEPSTransactionStatus,45,10,317343184,,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,1,9914632345,
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, ""),
            "Test",
            "fiserv"
        ).add(
            new GetEpsTransactionStatus(
                "Test case 10 - empty password",
                getExpectedResponse(gatewayErrors, "45"),
                vendorId,
                extAcctId,
                extTransactionId)
        ));

    //GetEPSTransactionStatus,59,11,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,,9914632345,
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password),
            "Test",
            "fiserv"
        ).add(
            new GetEpsTransactionStatus(
                "Test case 11 - empty vendor ID",
                getExpectedResponse(gatewayErrors, "59"),
                "",
                extAcctId,
                extTransactionId)
        ));

    //GetEPSTransactionStatus,61,12,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,1,8124194997,NULL
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password),
            "Test",
            "fiserv"
        ).add(
            new GetEpsTransactionStatus(
                "Test case 12 - empty ext acc ID",
                getExpectedResponse(gatewayErrors, "61"),
                vendorId,
                extAcctId,
                null)
        ));

    final String[] extTransactionIdValues = {
        "25432", "25432a",
    };

    for (String extTransactionIdValue : extTransactionIdValues) {
      //GetEPSTransactionStatus,66,13,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,1,8124194997,25432
      //GetEPSTransactionStatus,66,14,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,1,8124194997,25432a
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password),
              "Test",
              "fiserv"
          ).add(
              new GetEpsTransactionStatus(
                  "Test case 13/14 - incorrect extTransactionId",
                  getExpectedResponse(gatewayErrors, "66"),
                  vendorId,
                  extAcctId,
                  extTransactionIdValue)
          ));
    }

    final String[] vendorIdValues = {
        "1a", "2345",
    };

    for (String vendorIdValue : vendorIdValues) {
      //GetEPSTransactionStatus,71,15,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,1a,9914632345,
      //GetEPSTransactionStatus,71,16,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,2345,9914632345,
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password),
              "Test",
              "fiserv"
          ).add(
              new GetEpsTransactionStatus(
                  "Test case 15/16 - invalid vendor ids",
                  getExpectedResponse(gatewayErrors, "71"),
                  vendorIdValue,
                  extAcctId,
                  extTransactionId)
          ));
    }

    final String[] extAccIdValues = {
        "99146323a", "88200558",
    };

    for (String extAccIdValue : extAccIdValues) {
      //GetEPSTransactionStatus,75,17,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,1,99146323a,
      //GetEPSTransactionStatus,75,18,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,1,88200558,
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password),
              "Test",
              "fiserv"
          ).add(
              new GetEpsTransactionStatus(
                  "Test case 17/18 - invalid ext acc ids",
                  getExpectedResponse(gatewayErrors, "75"),
                  vendorId,
                  extAccIdValue,
                  extTransactionId)
          ));
    }

    executeTests(testCases);
  }
}

