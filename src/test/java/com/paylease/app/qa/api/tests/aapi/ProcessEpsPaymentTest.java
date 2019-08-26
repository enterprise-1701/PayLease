package com.paylease.app.qa.api.tests.aapi;

import com.paylease.app.qa.api.tests.TestCaseCollection;
import com.paylease.app.qa.api.tests.aapi.testcase.ProcessEpsPayment;
import com.paylease.app.qa.framework.DataHelper;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.api.Credentials;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.testng.annotations.Test;


public class ProcessEpsPaymentTest extends BaseTest {

  private static final String REGION = "aapi";
  private static final String FEATURE = "ProcessEpsPayment";

  //-----------------------------------TESTS--------------------------------------------------------

  @Test(groups = {"aapi", "ProcessEPSPayment"})
  public void processEpsPayment() {
    Logger.info("Process EPS Payment Test");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();
    final String userId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String vendorId = testSetupPage.getString("vendorId");
    final String extAcctId = testSetupPage.getString("extAcctId");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    ArrayList<TestCaseCollection> testCases = new ArrayList<>();

    DataHelper dataHelper = new DataHelper();

    final String[] unitAmountValues = {
        "7500.00", "100.00", "100.0", "100.", "100", ".01",
    };

    for (String unitAmount : unitAmountValues) {
      //ProcessEPSPayment,1,1,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,1,8124194997,7500.00
      //ProcessEPSPayment,1,2,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,1,8124194997,100.00
      //ProcessEPSPayment,1,3,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,1,8124194997,100.0
      //ProcessEPSPayment,1,4,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,1,8124194997,100.
      //ProcessEPSPayment,1,5,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,1,8124194997,100
      //ProcessEPSPayment,1,6,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,1,8124194997,.01
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password),
              "Test",
              "fiserv"
          ).add(
              new ProcessEpsPayment(
                  "Test case 1 to 6 - different unit amounts",
                  getExpectedResponse(gatewayErrors, "1"),
                  vendorId,
                  extAcctId,
                  dataHelper.getExtTransactionId(),
                  unitAmount)
          ));
    }

    //ProcessEPSPayment,8,7,317343184,be297ejCEn,,Test,fiserv,1,9914632345,100.00
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password, ""),
            "Test",
            "fiserv"
        ).add(
            new ProcessEpsPayment(
                "Test case 7 - empty api key",
                getExpectedResponse(gatewayErrors, "8"),
                vendorId,
                extAcctId,
                dataHelper.getExtTransactionId(),
                "100.00")
        ));

    //ProcessEPSPayment,9,8,317343184,be297ejCEn,badapikey,Test,fiserv,1,9914632345,100.00
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password, "badapikey"),
            "Test",
            "fiserv"
        ).add(
            new ProcessEpsPayment(
                "Test case 8 - bad api key",
                getExpectedResponse(gatewayErrors, "9"),
                vendorId,
                extAcctId,
                dataHelper.getExtTransactionId(),
                "100.00")
        ));

    //ProcessEPSPayment,20,9,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,,fiserv,1,9914632345,100.00
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password),
            "",
            "fiserv"
        ).add(
            new ProcessEpsPayment(
                "Test case 9 - empty mode",
                getExpectedResponse(gatewayErrors, "20"),
                vendorId,
                extAcctId,
                dataHelper.getExtTransactionId(),
                "100.00")
        ));

    //ProcessEPSPayment,38,10,317343,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,1,9914632345,100.00
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials("317343", username, password),
            "Test",
            "fiserv"
        ).add(
            new ProcessEpsPayment(
                "Test case 10 - bad userId",
                getExpectedResponse(gatewayErrors, "38"),
                vendorId,
                extAcctId,
                dataHelper.getExtTransactionId(),
                "100.00")
        ));

    //ProcessEPSPayment,38,11,,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,1,9914632345,100.00
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials("", username, password),
            "Test",
            "fiserv"
        ).add(
            new ProcessEpsPayment(
                "Test case 11 - empty userId",
                getExpectedResponse(gatewayErrors, "38"),
                vendorId,
                extAcctId,
                dataHelper.getExtTransactionId(),
                "100.00")
        ));

    //ProcessEPSPayment,38,12,317343184,badpass,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,1,9914632345,100.00
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, "badpass"),
            "Test",
            "fiserv"
        ).add(
            new ProcessEpsPayment(
                "Test case 12 - incorrect password",
                getExpectedResponse(gatewayErrors, "38"),
                vendorId,
                extAcctId,
                dataHelper.getExtTransactionId(),
                "100.00")
        ));

    //ProcessEPSPayment,45,13,317343184,,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,1,9914632345,100.00
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, ""),
            "Test",
            "fiserv"
        ).add(
            new ProcessEpsPayment(
                "Test case 13 - empty password",
                getExpectedResponse(gatewayErrors, "45"),
                vendorId,
                extAcctId,
                dataHelper.getExtTransactionId(),
                "100.00")
        ));

    //ProcessEPSPayment,59,14,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,,9914632345,100.00
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password),
            "Test",
            "fiserv"
        ).add(
            new ProcessEpsPayment(
                "Test case 14 - empty vendor ID",
                getExpectedResponse(gatewayErrors, "59"),
                "",
                extAcctId,
                dataHelper.getExtTransactionId(),
                "100.00")
        ));

    //ProcessEPSPayment,60,15,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,1,,100.00
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password),
            "Test",
            "fiserv"
        ).add(
            new ProcessEpsPayment(
                "Test case 15 - empty ext acc ID",
                getExpectedResponse(gatewayErrors, "60"),
                vendorId,
                "",
                dataHelper.getExtTransactionId(),
                "100.00")
        ));

    //ProcessEPSPayment,62,16,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,1,8124194997,100.00a
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password),
            "Test",
            "fiserv"
        ).add(
            new ProcessEpsPayment(
                "Test case 16 - invalid unit amount",
                getExpectedResponse(gatewayErrors, "62"),
                vendorId,
                extAcctId,
                dataHelper.getExtTransactionId(),
                "100.00a")
        ));

    final String[] invalidUnitAmountValues = {
        ".00", "0", "-10",
    };

    for (String invalidUnitAmount : invalidUnitAmountValues) {
      //ProcessEPSPayment,65,17,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,1,8124194997,.00
      //ProcessEPSPayment,65,18,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,1,8124194997,0
      //ProcessEPSPayment,65,19,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,1,8124194997,-10
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password),
              "Test",
              "fiserv"
          ).add(
              new ProcessEpsPayment(
                  "Test case 17 to 19 - invalid unit amount values",
                  getExpectedResponse(gatewayErrors, "65"),
                  vendorId,
                  extAcctId,
                  dataHelper.getExtTransactionId(),
                  invalidUnitAmount)
          ));
    }

    final String[] vendorIdValues = {
        "1a", "2345",
    };

    for (String vendorIdValue : vendorIdValues) {
      //ProcessEPSPayment,71,20,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,1a,9914632345,100.00
      //ProcessEPSPayment,71,20,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,2354,9914632345,100.00
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password),
              "Test",
              "fiserv"
          ).add(
              new ProcessEpsPayment(
                  "Test case 20 - invalid vendor ID values",
                  getExpectedResponse(gatewayErrors, "71"),
                  vendorIdValue,
                  extAcctId,
                  dataHelper.getExtTransactionId(),
                  "100.00")
          ));
    }

    final String[] extAccIdValues = {
        "9914632", "9914632a",
    };

    for (String extAccIdValue : extAccIdValues) {
      //ProcessEPSPayment,75,21,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,1,99146323,100.00
      //ProcessEPSPayment,75,22,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,1,991463234a,100.00
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password),
              "Test",
              "fiserv"
          ).add(
              new ProcessEpsPayment(
                  "Test case 21/22 - invalid ext acc ID values",
                  getExpectedResponse(gatewayErrors, "75"),
                  vendorId,
                  extAccIdValue,
                  dataHelper.getExtTransactionId(),
                  "100.00")
          ));
    }

    //ProcessEPSPayment,76,23,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,1,8124194997,7500.01
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password),
            "Test",
            "fiserv"
        ).add(
            new ProcessEpsPayment(
                "Test case 23 - large unit amount value",
                getExpectedResponse(gatewayErrors, "76"),
                vendorId,
                extAcctId,
                dataHelper.getExtTransactionId(),
                "7500.01")
        ));

    executeTests(testCases);
  }
}

