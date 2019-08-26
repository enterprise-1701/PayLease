package com.paylease.app.qa.api.tests.aapi;

import com.paylease.app.qa.api.tests.TestCaseCollection;
import com.paylease.app.qa.api.tests.aapi.testcase.GetProperties;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.api.Credentials;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.testng.annotations.Test;

public class GetPropertiesTest extends BaseTest {

  private static final String REGION = "aapi";
  private static final String FEATURE = "GetProperties";

  //-----------------------------------TESTS--------------------------------------------------------

  @Test(groups = {"aapi", "GetProperties"})
  public void getProperties() {
    Logger.info("GetProperties Test");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();
    final String userId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String pmId = testSetupPage.getString("pmId");
    final String propertyRefId = testSetupPage.getString("propNumber");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    ArrayList<TestCaseCollection> testCases = new ArrayList<>();

    final String[] endpointValues = {
        "associa", "cinc", "collier", "fiserv", "onsite", "tops"
    };

    final String[] propRefValues = {
        "", null, propertyRefId,
    };

    final String[] attributeValues = {
        null, "YES",
    };

    for (String endpoint : endpointValues) {
      for (String propRefId : propRefValues) {
        for (String returnResidents : attributeValues) {
          for (String returnPaymentFields : attributeValues) {
            //GetProperties,1,1,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,17343184,,NULL,NULL,
            //GetProperties,1,2,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,17343184,,NULL,NULL,
            //GetProperties,1,3,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,17343184,,NULL,NULL,
            //GetProperties,1,4,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,17343184,,NULL,NULL,
            //GetProperties,1,5,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,17343184,,NULL,NULL,17343184
            //GetProperties,1,6,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,17343184,,NULL,NULL,17343184
            //GetProperties,1,7,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,17343184,NULL,NULL,NULL,
            //GetProperties,1,8,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,17343184,NULL,NULL,NULL,
            //GetProperties,1,9,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,17343184,NULL,NULL,NULL,
            //GetProperties,1,10,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,17343184,NULL,NULL,NULL,
            //GetProperties,1,11,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,17343184,NULL,NULL,NULL,17343184
            //GetProperties,1,12,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,17343184,NULL,NULL,NULL,17343184
            //GetProperties,1,13,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,17343184,123QAgapi,NULL,NULL,
            //GetProperties,1,14,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,17343184,123QAgapi,NULL,NULL,
            //GetProperties,1,15,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,17343184,123QAgapi,NULL,NULL,
            //GetProperties,1,16,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,17343184,123QAgapi,NULL,NULL,
            //GetProperties,1,17,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,17343184,123QAgapi,NULL,NULL,17343184
            //GetProperties,1,18,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,17343184,123QAgapi,NULL,NULL,17343184
            //GetProperties,1,19,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,17343184,,YES,NULL,
            //GetProperties,1,20,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,17343184,,YES,NULL,
            //GetProperties,1,21,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,17343184,,YES,NULL,
            //GetProperties,1,22,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,17343184,,YES,NULL,
            //GetProperties,1,23,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,17343184,,YES,NULL,17343184
            //GetProperties,1,24,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,17343184,,YES,NULL,17343184
            //GetProperties,1,25,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,17343184,NULL,YES,NULL,
            //GetProperties,1,26,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,17343184,NULL,YES,NULL,
            //GetProperties,1,27,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,17343184,NULL,YES,NULL,
            //GetProperties,1,28,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,17343184,NULL,YES,NULL,
            //GetProperties,1,29,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,17343184,NULL,YES,NULL,17343184
            //GetProperties,1,30,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,17343184,NULL,YES,NULL,17343184
            //GetProperties,1,31,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,17343184,123QAgapi,YES,NULL,
            //GetProperties,1,32,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,17343184,123QAgapi,YES,NULL,
            //GetProperties,1,33,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,17343184,123QAgapi,YES,NULL,
            //GetProperties,1,34,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,17343184,123QAgapi,YES,NULL,
            //GetProperties,1,35,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,17343184,123QAgapi,YES,NULL,17343184
            //GetProperties,1,36,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,17343184,123QAgapi,YES,NULL,17343184
            //GetProperties,1,37,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,17343184,,NULL,YES,
            //GetProperties,1,38,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,17343184,,NULL,YES,
            //GetProperties,1,39,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,17343184,,NULL,YES,
            //GetProperties,1,40,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,17343184,,NULL,YES,
            //GetProperties,1,41,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,17343184,,NULL,YES,17343184
            //GetProperties,1,42,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,17343184,,NULL,YES,17343184
            //GetProperties,1,43,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,17343184,NULL,NULL,YES,
            //GetProperties,1,44,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,17343184,NULL,NULL,YES,
            //GetProperties,1,45,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,17343184,NULL,NULL,YES,
            //GetProperties,1,46,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,17343184,NULL,NULL,YES,
            //GetProperties,1,47,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,17343184,NULL,NULL,YES,17343184
            //GetProperties,1,48,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,17343184,NULL,NULL,YES,17343184
            //GetProperties,1,49,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,17343184,123QAgapi,NULL,YES,
            //GetProperties,1,50,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,17343184,123QAgapi,NULL,YES,
            //GetProperties,1,51,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,17343184,123QAgapi,NULL,YES,
            //GetProperties,1,52,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,17343184,123QAgapi,NULL,YES,
            //GetProperties,1,53,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,17343184,123QAgapi,NULL,YES,17343184
            //GetProperties,1,54,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,17343184,123QAgapi,NULL,YES,17343184
            //GetProperties,1,55,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,17343184,,YES,YES,
            //GetProperties,1,56,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,17343184,,YES,YES,
            //GetProperties,1,57,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,17343184,,YES,YES,
            //GetProperties,1,58,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,17343184,,YES,YES,
            //GetProperties,1,59,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,17343184,,YES,YES,17343184
            //GetProperties,1,60,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,17343184,,YES,YES,17343184
            //GetProperties,1,61,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,17343184,NULL,YES,YES,
            //GetProperties,1,62,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,17343184,NULL,YES,YES,
            //GetProperties,1,63,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,17343184,NULL,YES,YES,
            //GetProperties,1,64,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,17343184,NULL,YES,YES,
            //GetProperties,1,65,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,17343184,NULL,YES,YES,17343184
            //GetProperties,1,66,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,17343184,NULL,YES,YES,17343184
            //GetProperties,1,67,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,17343184,123QAgapi,YES,YES,
            //GetProperties,1,68,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,17343184,123QAgapi,YES,YES,
            //GetProperties,1,69,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,17343184,123QAgapi,YES,YES,
            //GetProperties,1,70,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,17343184,123QAgapi,YES,YES,
            //GetProperties,1,71,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,17343184,123QAgapi,YES,YES,17343184
            //GetProperties,1,72,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,17343184,123QAgapi,YES,YES,17343184
            testCases.add(
                new AapiTestCaseCollection(
                    new Credentials(userId, username, password, null, pmId),
                    "Test",
                    endpoint
                ).add(
                    new GetProperties(
                        "Test case 1 to 72 - valid",
                        getExpectedResponse(gatewayErrors, "1"),
                        propRefId)
                        .setReturnResidents(returnResidents)
                        .setReturnPaymentFields(returnPaymentFields)
                ));
          }
        }
      }

      //GetProperties,2,73,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,17343184,badrefid,NULL,NULL,
      //GetProperties,2,74,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,17343184,badrefid,NULL,NULL,
      //GetProperties,2,75,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,17343184,badrefid,NULL,NULL,
      //GetProperties,2,76,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,17343184,badrefid,NULL,NULL,
      //GetProperties,2,77,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,17343184,badrefid,NULL,NULL,17343184
      //GetProperties,2,78,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,17343184,badrefid,NULL,NULL,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password, null, pmId),
              "Test",
              endpoint
          ).add(
              new GetProperties(
                  "Test case 73 to 78 - invalid property ref id",
                  getExpectedResponse(gatewayErrors, "2"),
                  "badrefid")
          ));

      //GetProperties,8,79,317343184,be297ejCEn,,Test,associa,17343184,,NULL,NULL,
      //GetProperties,8,80,317343184,be297ejCEn,,Test,cinc,17343184,,NULL,NULL,
      //GetProperties,8,81,317343184,be297ejCEn,,Test,collier,17343184,,NULL,NULL,
      //GetProperties,8,82,317343184,be297ejCEn,,Test,fiserv,17343184,,NULL,NULL,
      //GetProperties,8,83,317343184,be297ejCEn,,Test,onsite,17343184,,NULL,NULL,17343184
      //GetProperties,8,84,317343184,be297ejCEn,,Test,tops,17343184,,NULL,NULL,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password, "", pmId),
              "Test",
              endpoint
          ).add(
              new GetProperties(
                  "Test case 79 to 84 - empty api key",
                  getExpectedResponse(gatewayErrors, "8"),
                  "")
          ));

      // FYI these tests use an api key that ALMOST matches, but doesn't quite
      //GetProperties,9,85,317343184,be297ejCEn,iedahy2ohcie7nieWoo,Test,associa,17343184,,NULL,NULL,
      //GetProperties,9,86,317343184,be297ejCEn,hovee3uv0ooShoi1Suc,Test,cinc,17343184,,NULL,NULL,
      //GetProperties,9,87,317343184,be297ejCEn,rohn3shaiquaesh1xoS,Test,collier,17343184,,NULL,NULL,
      //GetProperties,9,88,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Z,Test,fiserv,17343184,,NULL,NULL,
      //GetProperties,9,89,317343184,be297ejCEn,qua1aiPhul2chohz5ae,Test,onsite,17343184,,NULL,NULL,17343184
      //GetProperties,9,90,317343184,be297ejCEn,EinaGh1oe4eihu1gohc,Test,tops,17343184,,NULL,NULL,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password, "badapikey", pmId),
              "Test",
              endpoint
          ).add(
              new GetProperties(
                  "Test case 85 to 90 - invalid api key",
                  getExpectedResponse(gatewayErrors, "9"),
                  "")
          ));

      //GetProperties,20,91,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,,associa,17343184,,NULL,NULL,
      //GetProperties,20,92,317343184,be297ejCEn,hovee3uv0ooShoi1Such,,cinc,17343184,,NULL,NULL,
      //GetProperties,20,93,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,,collier,17343184,,NULL,NULL,
      //GetProperties,20,94,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,,fiserv,17343184,,NULL,NULL,
      //GetProperties,20,95,317343184,be297ejCEn,qua1aiPhul2chohz5aer,,onsite,17343184,,NULL,NULL,17343184
      //GetProperties,20,96,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,,tops,17343184,,NULL,NULL,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password, null, pmId),
              "",
              endpoint
          ).add(
              new GetProperties(
                  "Test case 91 to 96 - empty mode",
                  getExpectedResponse(gatewayErrors, "20"),
                  "")
          ));

      //GetProperties,38,97,badid,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,17343184,,NULL,NULL,
      //GetProperties,38,98,badid,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,17343184,,NULL,NULL,
      //GetProperties,38,99,badid,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,17343184,,NULL,NULL,
      //GetProperties,38,100,badid,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,17343184,,NULL,NULL,
      //GetProperties,38,101,badid,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,17343184,,NULL,NULL,17343184
      //GetProperties,38,102,badid,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,17343184,,NULL,NULL,17343184
      //GetProperties,38,103,badid,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,17343184,,NULL,NULL,
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials("badid", username, password, null, pmId),
              "Test",
              endpoint
          ).add(
              new GetProperties(
                  "Test case 97 to 103 - invalid user id",
                  getExpectedResponse(gatewayErrors, "38"),
                  "")
          ));

      //GetProperties,38,104,,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,17343184,,NULL,NULL,
      //GetProperties,38,105,,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,17343184,,NULL,NULL,
      //GetProperties,38,106,,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,17343184,,NULL,NULL,
      //GetProperties,38,107,,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,17343184,,NULL,NULL,17343184
      //GetProperties,38,108,,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,17343184,,NULL,NULL,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials("", username, password, null, pmId),
              "Test",
              endpoint
          ).add(
              new GetProperties(
                  "Test case 104 to 108 - empty user id",
                  getExpectedResponse(gatewayErrors, "38"),
                  "")
          ));

      //GetProperties,38,109,317343184,badpass,iedahy2ohcie7nieWoo1,Test,associa,17343184,,NULL,NULL,
      //GetProperties,38,110,317343184,badpass,hovee3uv0ooShoi1Such,Test,cinc,17343184,,NULL,NULL,
      //GetProperties,38,111,317343184,badpass,rohn3shaiquaesh1xoSu,Test,collier,17343184,,NULL,NULL,
      //GetProperties,38,112,317343184,badpass,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,17343184,,NULL,NULL,
      //GetProperties,38,113,317343184,badpass,qua1aiPhul2chohz5aer,Test,onsite,17343184,,NULL,NULL,17343184
      //GetProperties,38,114,317343184,badpass,EinaGh1oe4eihu1gohci,Test,tops,17343184,,NULL,NULL,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, "badpass", null, pmId),
              "Test",
              endpoint
          ).add(
              new GetProperties(
                  "Test case 109 to 114 - invalid password",
                  getExpectedResponse(gatewayErrors, "38"),
                  "")
          ));

      //GetProperties,45,115,317343184,,iedahy2ohcie7nieWoo1,Test,associa,17343184,,NULL,NULL,
      //GetProperties,45,116,317343184,,hovee3uv0ooShoi1Such,Test,cinc,17343184,,NULL,NULL,
      //GetProperties,45,117,317343184,,rohn3shaiquaesh1xoSu,Test,collier,17343184,,NULL,NULL,
      //GetProperties,45,118,317343184,,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,17343184,,NULL,NULL,
      //GetProperties,45,119,317343184,,qua1aiPhul2chohz5aer,Test,onsite,17343184,,NULL,NULL,17343184
      //GetProperties,45,120,317343184,,EinaGh1oe4eihu1gohci,Test,tops,17343184i,,NULL,NULL,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, "", null, pmId),
              "Test",
              endpoint
          ).add(
              new GetProperties(
                  "Test case 115 to 120 - empty password",
                  getExpectedResponse(gatewayErrors, "45"),
                  "")
          ));
    }

    executeTests(testCases);
  }
}
