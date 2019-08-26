package com.paylease.app.qa.api.tests.aapi;

import com.paylease.app.qa.api.tests.TestCaseCollection;
import com.paylease.app.qa.api.tests.aapi.testcase.GetResidentTransactionHistory;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.api.Credentials;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.testng.annotations.Test;


public class GetResidentTransactionHistoryTest extends BaseTest {

  private static final String REGION = "aapi";
  private static final String FEATURE = "GetResidentTransactionHistory";

  //-----------------------------------TESTS--------------------------------------------------------

  @Test(groups = {"aapi", "GetResidentTransactionHistory"})
  public void getResidentsTranHistory() {
    Logger.info("Get Resident Transaction History Test");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();
    final String userId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String residentIdTrans = testSetupPage
        .getString("residentRefIdTrans");
    final String residentIdNoTrans = testSetupPage.getString("residentRefIdNoTrans");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    ArrayList<TestCaseCollection> testCases = new ArrayList<>();

    //GetResidentTransactionHistory,1,1,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,voidtranstester
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password),
            "Test",
            "fiserv"
        ).add(
            new GetResidentTransactionHistory(
                "Test case 1 - valid resident id",
                getExpectedResponse(gatewayErrors, "1"),
                residentIdTrans)
        ));

    //GetResidentTransactionHistory,7,2,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,1
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password),
            "Test",
            "fiserv"
        ).add(
            new GetResidentTransactionHistory(
                "Test case 2 - resident id with no transactions",
                getExpectedResponse(gatewayErrors, "7"),
                residentIdNoTrans)
        ));

    //GetResidentTransactionHistory,7,3,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,1a
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password),
            "Test",
            "fiserv"
        ).add(
            new GetResidentTransactionHistory(
                "Test case 3 - unrecognized resident id",
                getExpectedResponse(gatewayErrors, "7"),
                "1a")
        ));

    //GetResidentTransactionHistory,9,4,317343184,be297ejCEn,BADAPIKEY,Test,fiserv,17343188
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password, "BADAPIKEY"),
            "Test",
            "fiserv"
        ).add(
            new GetResidentTransactionHistory(
                "Test case 4 - Bad api key",
                getExpectedResponse(gatewayErrors, "9"),
                residentIdTrans)
        ));

    //GetResidentTransactionHistory,20,5,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,,fiserv,GAPITesterCC
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password),
            "",
            "fiserv"
        ).add(
            new GetResidentTransactionHistory(
                "Test case 5 - No mode",
                getExpectedResponse(gatewayErrors, "20"),
                residentIdTrans)
        ));

    //GetResidentTransactionHistory,38,6,317343184,BADPASS,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,GAPITesterCC
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, "BADPASS"),
            "Test",
            "fiserv"
        ).add(
            new GetResidentTransactionHistory(
                "Test case 6 - Bad password",
                getExpectedResponse(gatewayErrors, "38"),
                residentIdTrans)
        ));

    //GetResidentTransactionHistory,38,7,,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,GAPITesterCC
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials("", username, password),
            "Test",
            "fiserv"
        ).add(
            new GetResidentTransactionHistory(
                "Test case 7 - Empty userId",
                getExpectedResponse(gatewayErrors, "38"),
                residentIdTrans)
        ));

    //GetResidentTransactionHistory,57,8,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password),
            "Test",
            "fiserv"
        ).add(
            new GetResidentTransactionHistory(
                "Test case 8 - Empty resident Id",
                getExpectedResponse(gatewayErrors, "57"),
                "")
        ));

    executeTests(testCases);
  }
}

