package com.paylease.app.qa.api.tests.aapi;

import com.paylease.app.qa.api.tests.TestCaseCollection;
import com.paylease.app.qa.api.tests.aapi.testcase.CheckCredentials;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.api.Credentials;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.testng.annotations.Test;


public class CheckCredentialsTest extends BaseTest {

  private static final String REGION = "aapi";
  private static final String FEATURE = "CheckCredentials";

  //-----------------------------------TESTS--------------------------------------------------------

  @Test(groups = {"aapi", "CheckCredentials"})
  public void checkCredentials() {
    Logger.info("Check Credentials Test");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();
    final String userId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String pmId = testSetupPage.getString("pmId");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    final String[] endpointValues = {
        "associa", "cinc", "collier", "fiserv", "onsite", "tops"
    };

    ArrayList<TestCaseCollection> testCases = new ArrayList<>();

    for (String endpointValue : endpointValues) {

      //CheckCredentials,1,1,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,
      //CheckCredentials,1,2,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,
      //CheckCredentials,1,3,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,
      //CheckCredentials,1,4,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,
      //CheckCredentials,1,5,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,17343184
      //CheckCredentials,1,6,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password, null, pmId),
              "Test",
              endpointValue
          ).add(
              new CheckCredentials(
                  "Test case 1/2/3/4/5/6 - Different endpoints",
                  getExpectedResponse(gatewayErrors, "1"))
          ));

      //CheckCredentials,8,7,317343184,be297ejCEn,,Test,associa,
      //CheckCredentials,8,8,317343184,be297ejCEn,,Test,cinc,
      //CheckCredentials,8,9,317343184,be297ejCEn,,Test,collier,
      //CheckCredentials,8,10,317343184,be297ejCEn,,Test,fiserv,
      //CheckCredentials,8,11,317343184,be297ejCEn,,Test,onsite,17343184
      //CheckCredentials,8,12,317343184,be297ejCEn,,Test,tops,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password, "", pmId),
              "Test",
              endpointValue
          ).add(
              new CheckCredentials(
                  "Test case 7/8/9/10/11/12 - Empty API key",
                  getExpectedResponse(gatewayErrors, "8"))
          ));

      //CheckCredentials,9,13,317343184,be297ejCEn,badapikey,Test,associa,
      //CheckCredentials,9,14,317343184,be297ejCEn,badapikey,Test,cinc,
      //CheckCredentials,9,15,317343184,be297ejCEn,badapikey,Test,collier,
      //CheckCredentials,9,16,317343184,be297ejCEn,badapikey,Test,fiserv,
      //CheckCredentials,9,17,317343184,be297ejCEn,badapikey,Test,onsite,17343184
      //CheckCredentials,9,18,317343184,be297ejCEn,badapikey,Test,tops,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password, "badapikey", pmId),
              "Test",
              endpointValue
          ).add(
              new CheckCredentials(
                  "Test case 13/14/15/16/17/18 - Bad API key",
                  getExpectedResponse(gatewayErrors, "9"))
          ));

      //CheckCredentials,9,19,317343184,be297ejCEn, ,Test,associa,
      //CheckCredentials,9,20,317343184,be297ejCEn, ,Test,cinc,
      //CheckCredentials,9,21,317343184,be297ejCEn, ,Test,collier,
      //CheckCredentials,9,22,317343184,be297ejCEn, ,Test,fiserv,
      //CheckCredentials,9,23,317343184,be297ejCEn, ,Test,onsite,17343184
      //CheckCredentials,9,24,317343184,be297ejCEn, ,Test,tops,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password, " ", pmId),
              "Test",
              endpointValue
          ).add(
              new CheckCredentials(
                  "Test case 19/20/21/22/23/24 - Empty space API key",
                  getExpectedResponse(gatewayErrors, "9"))
          ));

      //CheckCredentials,20,25,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,,associa,
      //CheckCredentials,20,26,317343184,be297ejCEn,hovee3uv0ooShoi1Such,,cinc,
      //CheckCredentials,20,27,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,,collier,
      //CheckCredentials,20,28,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,,fiserv,
      //CheckCredentials,20,29,317343184,be297ejCEn,qua1aiPhul2chohz5aer,,onsite,17343184
      //CheckCredentials,20,30,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,,tops,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password, null, pmId),
              "",
              endpointValue
          ).add(
              new CheckCredentials(
                  "Test case 25/26/27/28/29/30 - Empty mode",
                  getExpectedResponse(gatewayErrors, "20"))
          ));

      //CheckCredentials,21,31,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Production,associa,
      //CheckCredentials,21,32,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Production,cinc,
      //CheckCredentials,21,33,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Production,collier,
      //CheckCredentials,21,34,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Production,fiserv,
      //CheckCredentials,21,35,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Production,onsite,17343184
      //CheckCredentials,21,36,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Production,tops,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password, null, pmId),
              "Production",
              endpointValue
          ).add(
              new CheckCredentials(
                  "Test case 31/32/33/34/35/36 - Production mode",
                  getExpectedResponse(gatewayErrors, "21"))
          ));

      //CheckCredentials,38,37,31734318,badpass,iedahy2ohcie7nieWoo1,Test,associa,
      //CheckCredentials,38,38,31734318,badpass,hovee3uv0ooShoi1Such,Test,cinc,
      //CheckCredentials,38,39,31734318,badpass,rohn3shaiquaesh1xoSu,Test,collier,
      //CheckCredentials,38,40,31734318,badpass,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,
      //CheckCredentials,38,41,31734318,badpass,qua1aiPhul2chohz5aer,Test,onsite,17343184
      //CheckCredentials,38,42,31734318,badpass,EinaGh1oe4eihu1gohci,Test,tops,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials("31734318", username, "badpass", null, pmId),
              "Test",
              endpointValue
          ).add(
              new CheckCredentials(
                  "Test case 37/38/39/40/41/42 - Bad userId",
                  getExpectedResponse(gatewayErrors, "38"))
          ));

      //CheckCredentials,38,43,317343184,badpass,iedahy2ohcie7nieWoo1,Test,associa,
      //CheckCredentials,38,44,317343184,badpass,hovee3uv0ooShoi1Such,Test,cinc,
      //CheckCredentials,38,45,317343184,badpass,rohn3shaiquaesh1xoSu,Test,collier,
      //CheckCredentials,38,46,317343184,badpass,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,
      //CheckCredentials,38,47,317343184,badpass,qua1aiPhul2chohz5aer,Test,onsite,17343184
      //CheckCredentials,38,48,317343184,badpass,EinaGh1oe4eihu1gohci,Test,tops,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, "badpass", null, pmId),
              "Test",
              endpointValue
          ).add(
              new CheckCredentials(
                  "Test case 43/44/45/46/47/48 - Bad password",
                  getExpectedResponse(gatewayErrors, "38"))
          ));

      //CheckCredentials,38,49,317343184, ,iedahy2ohcie7nieWoo1,Test,associa,
      //CheckCredentials,38,50,317343184, ,hovee3uv0ooShoi1Such,Test,cinc,
      //CheckCredentials,38,51,317343184, ,rohn3shaiquaesh1xoSu,Test,collier,
      //CheckCredentials,38,52,317343184, ,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,
      //CheckCredentials,38,53,317343184, ,qua1aiPhul2chohz5aer,Test,onsite,17343184
      //CheckCredentials,38,54,317343184, ,EinaGh1oe4eihu1gohci,Test,tops,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, " ", null, pmId),
              "Test",
              endpointValue
          ).add(
              new CheckCredentials(
                  "Test case 49/50/51/52/53/54 - Blank space as password",
                  getExpectedResponse(gatewayErrors, "38"))
          ));

      //CheckCredentials,45,55,317343184,,iedahy2ohcie7nieWoo1,Test,associa,
      //CheckCredentials,45,56,317343184,,hovee3uv0ooShoi1Such,Test,cinc,
      //CheckCredentials,45,57,317343184,,rohn3shaiquaesh1xoSu,Test,collier,
      //CheckCredentials,45,58,317343184,,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,
      //CheckCredentials,45,59,317343184,,qua1aiPhul2chohz5aer,Test,onsite,17343184
      //CheckCredentials,45,60,317343184,,EinaGh1oe4eihu1gohci,Test,tops,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, "", null, pmId),
              "Test",
              endpointValue
          ).add(
              new CheckCredentials(
                  "Test case 55/56/57/58/59/60 - Empty password",
                  getExpectedResponse(gatewayErrors, "45"))
          ));
    }

    executeTests(testCases);
  }
}

