package com.paylease.app.qa.api.tests.aapi;

import com.paylease.app.qa.api.tests.TestCaseCollection;
import com.paylease.app.qa.api.tests.aapi.testcase.ValidateEpsAccount;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.api.Credentials;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.testng.annotations.Test;


public class ValidateEpsAccountTest extends BaseTest {

  private static final String REGION = "aapi";
  private static final String FEATURE = "ValidateEpsAccount";

  //-----------------------------------TESTS--------------------------------------------------------

  @Test(groups = {"aapi", "ValidateEPSAccount"})
  public void validateEpsAccount() {
    Logger.info("Validate EPS Account Test");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();
    final String userId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String vendorId = testSetupPage.getString("vendorId");
    final String extAcctId = testSetupPage.getString("extAcctId");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    ArrayList<TestCaseCollection> testCases = new ArrayList<>();

    //ValidateEPSAccount,1,2,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,1,8901860646
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password),
            "Test",
            "fiserv"
        ).add(
            new ValidateEpsAccount(
                "Test case 2 - valid resident id",
                getExpectedResponse(gatewayErrors, "1"),
                vendorId,
                extAcctId)
        ));

    //ValidateEPSAccount,8,2,317343184,be297ejCEn,,Test,fiserv,1,9914632345
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password, ""),
            "Test",
            "fiserv"
        ).add(
            new ValidateEpsAccount(
                "Test case 2 - empty api key",
                getExpectedResponse(gatewayErrors, "8"),
                vendorId,
                extAcctId)
        ));

    //ValidateEPSAccount,9,3,317343184,be297ejCEn,badapikey,Test,fiserv,1,9914632345
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password, "badapikey"),
            "Test",
            "fiserv"
        ).add(
            new ValidateEpsAccount(
                "Test case 3 - bad api key",
                getExpectedResponse(gatewayErrors, "9"),
                vendorId,
                extAcctId)
        ));

    //ValidateEPSAccount,20,4,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,,fiserv,1,9914632345
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password),
            "",
            "fiserv"
        ).add(
            new ValidateEpsAccount(
                "Test case 4 - no mode",
                getExpectedResponse(gatewayErrors, "20"),
                vendorId,
                extAcctId)
        ));

    //ValidateEPSAccount,38,5,,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,1,9914632345
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials("", username, password),
            "Test",
            "fiserv"
        ).add(
            new ValidateEpsAccount(
                "Test case 5 - no user",
                getExpectedResponse(gatewayErrors, "38"),
                vendorId,
                extAcctId)
        ));

    //ValidateEPSAccount,38,6,3173431,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,1,9914632345
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials("3173431", username, password),
            "Test",
            "fiserv"
        ).add(
            new ValidateEpsAccount(
                "Test case 6 - wrong user",
                getExpectedResponse(gatewayErrors, "38"),
                vendorId,
                extAcctId)
        ));

    //ValidateEPSAccount,38,7,317343184,badpass,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,1,9914632345
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, "badpass"),
            "Test",
            "fiserv"
        ).add(
            new ValidateEpsAccount(
                "Test case 7 - bad password",
                getExpectedResponse(gatewayErrors, "38"),
                vendorId,
                extAcctId)
        ));

    //ValidateEPSAccount,45,8,317343184,,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,1,9914632345
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, ""),
            "Test",
            "fiserv"
        ).add(
            new ValidateEpsAccount(
                "Test case 8 - empty password",
                getExpectedResponse(gatewayErrors, "45"),
                vendorId,
                extAcctId)
        ));

    //ValidateEPSAccount,59,9,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,,9914632345
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password),
            "Test",
            "fiserv"
        ).add(
            new ValidateEpsAccount(
                "Test case 9 - empty vendor",
                getExpectedResponse(gatewayErrors, "59"),
                "",
                extAcctId)
        ));

    //ValidateEPSAccount,60,10,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,1,
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password),
            "Test",
            "fiserv"
        ).add(
            new ValidateEpsAccount(
                "Test case 10 - empty ext acc id",
                getExpectedResponse(gatewayErrors, "60"),
                vendorId,
                "")
        ));

    final String[] vendorIdValues = {
        "1a", "2345",
    };

    for (String vendorIdValue : vendorIdValues) {
      //ValidateEPSAccount,71,11,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,1a,9914632345
      //ValidateEPSAccount,71,12,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,2345,9914632345
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password),
              "Test",
              "fiserv"
          ).add(
              new ValidateEpsAccount(
                  "Test case 11/12 - invalid vendor ids",
                  getExpectedResponse(gatewayErrors, "71"),
                  vendorIdValue,
                  extAcctId)
          ));
    }

    final String[] extAccValues = {
        "9914632", "9914632a",
    };

    for (String extAccValue : extAccValues) {
      //ValidateEPSAccount,75,13,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,1,9914632
      //ValidateEPSAccount,75,14,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,1,9914632a
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password),
              "Test",
              "fiserv"
          ).add(
              new ValidateEpsAccount(
                  "Test case 13/14 - invalid ext acc ids",
                  getExpectedResponse(gatewayErrors, "75"),
                  vendorId,
                  extAccValue)
          ));
    }

    executeTests(testCases);
  }
}

