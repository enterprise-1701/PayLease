package com.paylease.app.qa.api.tests.aapi;

import com.paylease.app.qa.api.tests.TestCaseCollection;
import com.paylease.app.qa.api.tests.aapi.testcase.PmSso;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.api.Credentials;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.testng.annotations.Test;


public class PmSsoTest extends BaseTest {

  private static final String REGION = "aapi";
  private static final String FEATURE = "Pmsso";

  //-----------------------------------TESTS--------------------------------------------------------

  @Test(groups = {"aapi", "PMSSO"})
  public void pmSso() {
    Logger.info("PMSSO Test");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();
    final String userId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String pmIdValid = testSetupPage.getString("pmIdValid");
    final String pmIdInvalid = testSetupPage.getString("pmIdInvalid");
    final String pmIdBlocked = testSetupPage.getString("pmIdBlocked");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    final String[] endpointValues = {
        "cinc", "resman"
    };

    ArrayList<TestCaseCollection> testCases = new ArrayList<>();

    for (String endpointValue : endpointValues) {

      //PMSSO,1,1,35081074,atN9MX7PKy,hovee3uv0ooShoi1Such,Test,cinc,,4252013
      //PMSSO,1,2,35911311,hj2NvBFU4V,yu0qqnkwq33FBEUBEDrA,Test,resman,17343184
      //PMSSO,1,13,35081074,atN9MX7PKy,hovee3uv0ooShoi1Such,Test,cinc,,4252013
      //PMSSO,1,21,35081074,atN9MX7PKy,hovee3uv0ooShoi1Such,Test,cinc,,4252013
      //PMSSO,1,23,35081074,atN9MX7PKy,hovee3uv0ooShoi1Such,Test,cinc,,4252013
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password, null, pmIdValid),
              "Test",
              endpointValue
          ).add(
              new PmSso(
                  "Test case 1/2/13/21/23 - Different endpoints",
                  getExpectedResponse(gatewayErrors, "1"))
                  .setPmId(pmIdValid)
          ));

      //PMSSO,8,3,35081074,atN9MX7PKy,,Test,cinc,,,
      //PMSSO,8,4,35911311,hj2NvBFU4V,,Test,resman,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password, "", pmIdValid),
              "Test",
              endpointValue
          ).add(
              new PmSso(
                  "Test case 3/4 - Empty API Key",
                  getExpectedResponse(gatewayErrors, "8"))
                  .setPmId("")
          ));

      //PMSSO,9,5,35081074,atN9MX7PKy,badapikey,Test,cinc,,,
      //PMSSO,9,6,35911311,hj2NvBFU4V,badapikey,Test,resman,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password, "badapikey", pmIdValid),
              "Test",
              endpointValue
          ).add(
              new PmSso(
                  "Test case 5/6 - Bad API Key",
                  getExpectedResponse(gatewayErrors, "9"))
                  .setPmId("")
          ));

      //PMSSO,9,7,35081074,atN9MX7PKy, ,Test,cinc,,,
      //PMSSO,9,8,35911311,hj2NvBFU4V, ,Test,resman,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password, " ", pmIdValid),
              "Test",
              endpointValue
          ).add(
              new PmSso(
                  "Test case 7/8 - Blank space as API Key",
                  getExpectedResponse(gatewayErrors, "9"))
                  .setPmId("")
          ));

      //PMSSO,20,9,35081074,atN9MX7PKy,hovee3uv0ooShoi1Such,,cinc,,,
      //PMSSO,20,10,35911311,hj2NvBFU4V,yu0qqnkwq33FBEUBEDrA,,resman,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password, null, pmIdValid),
              "",
              endpointValue
          ).add(
              new PmSso(
                  "Test case 9/10 - Empty mode",
                  getExpectedResponse(gatewayErrors, "20"))
                  .setPmId("")
          ));

      //PMSSO,21,11,35081074,atN9MX7PKy,hovee3uv0ooShoi1Such,Production,cinc,,,
      //PMSSO,21,12,35911311,hj2NvBFU4V,yu0qqnkwq33FBEUBEDrA,Production,resman,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password, null, pmIdValid),
              "Production",
              endpointValue
          ).add(
              new PmSso(
                  "Test case 11/12 - Invalid value for Mode",
                  getExpectedResponse(gatewayErrors, "21"))
                  .setPmId("")
          ));

      //PMSSO,38,15,35081074,badpass,hovee3uv0ooShoi1Such,Test,cinc,,,
      //PMSSO,38,16,35911311,badpass,yu0qqnkwq33FBEUBEDrA,Test,resman,17343184
      //PMSSO,38,17,35081074,badpass,hovee3uv0ooShoi1Such,Test,cinc,,,
      //PMSSO,38,18,35911311,badpass,yu0qqnkwq33FBEUBEDrA,Test,resman,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, "badpass", null, pmIdValid),
              "Test",
              endpointValue
          ).add(
              new PmSso(
                  "Test case 15/16/17/18 - Incorrect Password",
                  getExpectedResponse(gatewayErrors, "38"))
                  .setPmId("")
          ));

      //PMSSO,38,19,35081074, ,hovee3uv0ooShoi1Such,Test,cinc,,,
      //PMSSO,38,20,35911311, ,yu0qqnkwq33FBEUBEDrA,Test,resman,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, " ", null, pmIdValid),
              "Test",
              endpointValue
          ).add(
              new PmSso(
                  "Test case 19/20 - Incorrect Password",
                  getExpectedResponse(gatewayErrors, "38"))
                  .setPmId("")
          ));

      //PMSSO,45,25,35081074,,hovee3uv0ooShoi1Such,Test,cinc,,,
      //PMSSO,45,26,35911311,,yu0qqnkwq33FBEUBEDrA,Test,resman,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, "", null, pmIdValid),
              "Test",
              endpointValue
          ).add(
              new PmSso(
                  "Test case 25/26 - Empty Password",
                  getExpectedResponse(gatewayErrors, "45"))
                  .setPmId("")
          ));

    }

    //PMSSO,37,14,35911311,hj2NvBFU4V,yu0qqnkwq33FBEUBEDrA,Test,resman,162
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password, null, pmIdInvalid),
            "Test",
            "resman"
        ).add(
            new PmSso(
                "Test case 14 - Valid test",
                getExpectedResponse(gatewayErrors, "37"))
        ));

    //PMSSO,55,22,35911311,hj2NvBFU4V,yu0qqnkwq33FBEUBEDrA,Test,resman,1
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password, null, pmIdBlocked),
            "Test",
            "resman"
        ).add(
            new PmSso(
                "Test case 22 - Blocked user",
                getExpectedResponse(gatewayErrors, "55"))
        ));

    //PMSSO,53,24,35911311,hj2NvBFU4V,yu0qqnkwq33FBEUBEDrA,Test,resman,
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password, null, ""),
            "Test",
            "resman"
        ).add(
            new PmSso(
                "Test case 24 - Blank pmId",
                getExpectedResponse(gatewayErrors, "53"))
        ));

    executeTests(testCases);
  }
}

