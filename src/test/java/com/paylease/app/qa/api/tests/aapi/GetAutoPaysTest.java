package com.paylease.app.qa.api.tests.aapi;

import com.paylease.app.qa.api.tests.TestCaseCollection;
import com.paylease.app.qa.api.tests.aapi.testcase.GetAutoPays;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.api.Credentials;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.testng.annotations.Test;


public class GetAutoPaysTest extends BaseTest {

  private static final String REGION = "aapi";
  private static final String FEATURE = "GetAutoPays";

  //-----------------------------------TESTS--------------------------------------------------------

  @Test(groups = {"aapi", "GetAutoPays"})
  public void getAutoPays() {
    Logger.info("Get AutoPays Test");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();
    final String userId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String pmId = testSetupPage.getString("pmId");
    final String residentReferenceId = testSetupPage.getString("residentReferenceId");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    final String[] endpointValues = {
        "associa", "cinc", "collier", "fiserv", "onsite", "tops"
    };

    final String[] statuses = {
        "ACTIVE", "CANCELLED", "EXPIRED", ""
    };

    ArrayList<TestCaseCollection> testCases = new ArrayList<>();

    for (String endpointValue : endpointValues) {
      for (String status : statuses) {

        //GetAutoPays,1,1,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,GAPITesterAutoPays,ACTIVE,
        //GetAutoPays,1,2,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,GAPITesterAutoPays,ACTIVE,
        //GetAutoPays,1,3,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,GAPITesterAutoPays,ACTIVE,
        //GetAutoPays,1,4,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,GAPITesterAutoPays,ACTIVE,
        //GetAutoPays,1,5,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,GAPITesterAutoPays,ACTIVE,17343184
        //GetAutoPays,1,6,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,GAPITesterAutoPays,ACTIVE,17343184

        //GetAutoPays,1,7,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,GAPITesterAutoPays,CANCELLED,
        //GetAutoPays,1,8,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,GAPITesterAutoPays,CANCELLED,
        //GetAutoPays,1,9,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,GAPITesterAutoPays,CANCELLED,
        //GetAutoPays,1,10,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,GAPITesterAutoPays,CANCELLED,
        //GetAutoPays,1,11,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,GAPITesterAutoPays,CANCELLED,17343184
        //GetAutoPays,1,12,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,GAPITesterAutoPays,CANCELLED,17343184

        //GetAutoPays,1,13,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,GAPITesterAutoPays,EXPIRED,
        //GetAutoPays,1,14,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,GAPITesterAutoPays,EXPIRED,
        //GetAutoPays,1,15,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,GAPITesterAutoPays,EXPIRED,
        //GetAutoPays,1,16,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,GAPITesterAutoPays,EXPIRED,
        //GetAutoPays,1,17,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,GAPITesterAutoPays,EXPIRED,17343184
        //GetAutoPays,1,18,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,GAPITesterAutoPays,EXPIRED,17343184

        //GetAutoPays,1,19,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,GAPITesterAutoPays,,
        //GetAutoPays,1,20,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,GAPITesterAutoPays,,
        //GetAutoPays,1,21,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,GAPITesterAutoPays,,
        //GetAutoPays,1,22,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,GAPITesterAutoPays,,
        //GetAutoPays,1,23,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,GAPITesterAutoPays,,17343184
        //GetAutoPays,1,24,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,GAPITesterAutoPays,,17343184
        testCases.add(
            new AapiTestCaseCollection(
                new Credentials(userId, username, password, null, pmId),
                "Test",
                endpointValue
            ).add(
                new GetAutoPays(
                    "Test case 1 to 24 - Different status and endpoints",
                    getExpectedResponse(gatewayErrors, "1"),
                    residentReferenceId,
                    status)
            ));

        //GetAutoPays,1,25,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,,ACTIVE,
        //GetAutoPays,1,26,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,,ACTIVE,
        //GetAutoPays,1,27,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,,ACTIVE,
        //GetAutoPays,1,28,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,,ACTIVE,
        //GetAutoPays,1,29,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,,ACTIVE,17343184
        //GetAutoPays,1,30,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,,ACTIVE,17343184

        //GetAutoPays,1,31,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,,CANCELLED,
        //GetAutoPays,1,32,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,,CANCELLED,
        //GetAutoPays,1,33,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,,CANCELLED,
        //GetAutoPays,1,34,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,,CANCELLED,
        //GetAutoPays,1,35,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,,CANCELLED,17343184
        //GetAutoPays,1,36,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,,CANCELLED,17343184

        //GetAutoPays,1,37,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,,EXPIRED,
        //GetAutoPays,1,38,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,,EXPIRED,
        //GetAutoPays,1,39,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,,EXPIRED,
        //GetAutoPays,1,40,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,,EXPIRED,
        //GetAutoPays,1,41,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,,EXPIRED,17343184
        //GetAutoPays,1,42,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,,EXPIRED,17343184

        //GetAutoPays,1,43,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,,,
        //GetAutoPays,1,44,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,,,
        //GetAutoPays,1,45,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,,,
        //GetAutoPays,1,46,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,,,
        //GetAutoPays,1,47,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,,,17343184
        //GetAutoPays,1,48,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,,,17343184
        testCases.add(
            new AapiTestCaseCollection(
                new Credentials(userId, username, password, null, pmId),
                "Test",
                endpointValue
            ).add(
                new GetAutoPays(
                    "Test case 25 to 48 - Empty resident ref id, different statuses",
                    getExpectedResponse(gatewayErrors, "1"),
                    "",
                    status)
            ));

        //GetAutoPays,6,49,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,badrefid,ACTIVE,
        //GetAutoPays,6,50,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,badrefid,ACTIVE,
        //GetAutoPays,6,51,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,badrefid,ACTIVE,
        //GetAutoPays,6,52,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,badrefid,ACTIVE,
        //GetAutoPays,6,53,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,badrefid,ACTIVE,17343184
        //GetAutoPays,6,54,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,badrefid,ACTIVE,17343184

        //GetAutoPays,6,55,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,badrefid,CANCELLED,
        //GetAutoPays,6,56,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,badrefid,CANCELLED,
        //GetAutoPays,6,57,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,badrefid,CANCELLED,
        //GetAutoPays,6,58,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,badrefid,CANCELLED,
        //GetAutoPays,6,59,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,badrefid,CANCELLED,17343184
        //GetAutoPays,6,60,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,badrefid,CANCELLED,17343184

        //GetAutoPays,6,61,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,badrefid,EXPIRED,
        //GetAutoPays,6,62,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,badrefid,EXPIRED,
        //GetAutoPays,6,63,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,badrefid,EXPIRED,
        //GetAutoPays,6,64,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,badrefid,EXPIRED,
        //GetAutoPays,6,65,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,badrefid,EXPIRED,17343184
        //GetAutoPays,6,66,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,badrefid,EXPIRED,17343184

        //GetAutoPays,6,67,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,badrefid,,
        //GetAutoPays,6,68,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,badrefid,,
        //GetAutoPays,6,69,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,badrefid,,
        //GetAutoPays,6,70,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,badrefid,,
        //GetAutoPays,6,71,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,badrefid,,17343184
        //GetAutoPays,6,72,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,badrefid,,17343184
        testCases.add(
            new AapiTestCaseCollection(
                new Credentials(userId, username, password, null, pmId),
                "Test",
                endpointValue
            ).add(
                new GetAutoPays(
                    "Test case 49 to 72 - Bad resident ref id, status active",
                    getExpectedResponse(gatewayErrors, "6"),
                    "badrefid",
                    status)
            ));

        //GetAutoPays,8,73,317343184,be297ejCEn,,Test,associa,GAPITesterAutoPays,ACTIVE,
        //GetAutoPays,8,74,317343184,be297ejCEn,,Test,cinc,GAPITesterAutoPays,ACTIVE,
        //GetAutoPays,8,75,317343184,be297ejCEn,,Test,collier,GAPITesterAutoPays,ACTIVE,
        //GetAutoPays,8,76,317343184,be297ejCEn,,Test,fiserv,GAPITesterAutoPays,ACTIVE,
        //GetAutoPays,8,77,317343184,be297ejCEn,,Test,onsite,GAPITesterAutoPays,ACTIVE,17343184
        //GetAutoPays,8,78,317343184,be297ejCEn,,Test,tops,GAPITesterAutoPays,ACTIVE,17343184

        //GetAutoPays,8,79,317343184,be297ejCEn,,Test,associa,GAPITesterAutoPays,CANCELLED,
        //GetAutoPays,8,80,317343184,be297ejCEn,,Test,cinc,GAPITesterAutoPays,CANCELLED,
        //GetAutoPays,8,81,317343184,be297ejCEn,,Test,collier,GAPITesterAutoPays,CANCELLED,
        //GetAutoPays,8,82,317343184,be297ejCEn,,Test,fiserv,GAPITesterAutoPays,CANCELLED,
        //GetAutoPays,8,83,317343184,be297ejCEn,,Test,onsite,GAPITesterAutoPays,CANCELLED,17343184
        //GetAutoPays,8,84,317343184,be297ejCEn,,Test,tops,GAPITesterAutoPays,CANCELLED,17343184

        //GetAutoPays,8,85,317343184,be297ejCEn,,Test,associa,GAPITesterAutoPays,EXPIRED,
        //GetAutoPays,8,86,317343184,be297ejCEn,,Test,cinc,GAPITesterAutoPays,EXPIRED,
        //GetAutoPays,8,87,317343184,be297ejCEn,,Test,collier,GAPITesterAutoPays,EXPIRED,
        //GetAutoPays,8,88,317343184,be297ejCEn,,Test,fiserv,GAPITesterAutoPays,EXPIRED,
        //GetAutoPays,8,89,317343184,be297ejCEn,,Test,onsite,GAPITesterAutoPays,EXPIRED,17343184
        //GetAutoPays,8,90,317343184,be297ejCEn,,Test,tops,GAPITesterAutoPays,EXPIRED,17343184

        //GetAutoPays,8,91,317343184,be297ejCEn,,Test,associa,GAPITesterAutoPays,,
        //GetAutoPays,8,92,317343184,be297ejCEn,,Test,cinc,GAPITesterAutoPays,,
        //GetAutoPays,8,93,317343184,be297ejCEn,,Test,collier,GAPITesterAutoPays,,
        //GetAutoPays,8,94,317343184,be297ejCEn,,Test,fiserv,GAPITesterAutoPays,,
        //GetAutoPays,8,95,317343184,be297ejCEn,,Test,onsite,GAPITesterAutoPays,,17343184
        //GetAutoPays,8,96,317343184,be297ejCEn,,Test,tops,GAPITesterAutoPays,,17343184
        testCases.add(
            new AapiTestCaseCollection(
                new Credentials(userId, username, password, "", pmId),
                "Test",
                endpointValue
            ).add(
                new GetAutoPays(
                    "Test case 73 to 96 - Empty api key, status active",
                    getExpectedResponse(gatewayErrors, "8"),
                    residentReferenceId,
                    status)
            ));

        //GetAutoPays,9,97,317343184,be297ejCEn,badapikey,Test,associa,GAPITesterAutoPays,ACTIVE,
        //GetAutoPays,9,98,317343184,be297ejCEn,badapikey,Test,cinc,GAPITesterAutoPays,ACTIVE,
        //GetAutoPays,9,99,317343184,be297ejCEn,badapikey,Test,collier,GAPITesterAutoPays,ACTIVE,
        //GetAutoPays,9,100,317343184,be297ejCEn,badapikey,Test,fiserv,GAPITesterAutoPays,ACTIVE,
        //GetAutoPays,9,101,317343184,be297ejCEn,badapikey,Test,onsite,GAPITesterAutoPays,ACTIVE,17343184
        //GetAutoPays,9,102,317343184,be297ejCEn,badapikey,Test,tops,GAPITesterAutoPays,ACTIVE,17343184

        //GetAutoPays,9,103,317343184,be297ejCEn,badapikey,Test,associa,GAPITesterAutoPays,CANCELLED,
        //GetAutoPays,9,104,317343184,be297ejCEn,badapikey,Test,cinc,GAPITesterAutoPays,CANCELLED,
        //GetAutoPays,9,105,317343184,be297ejCEn,badapikey,Test,collier,GAPITesterAutoPays,CANCELLED,
        //GetAutoPays,9,106,317343184,be297ejCEn,badapikey,Test,fiserv,GAPITesterAutoPays,CANCELLED,
        //GetAutoPays,9,107,317343184,be297ejCEn,badapikey,Test,onsite,GAPITesterAutoPays,CANCELLED,17343184
        //GetAutoPays,9,108,317343184,be297ejCEn,badapikey,Test,tops,GAPITesterAutoPays,CANCELLED,17343184

        //GetAutoPays,9,109,317343184,be297ejCEn,badapikey,Test,associa,GAPITesterAutoPays,EXPIRED,
        //GetAutoPays,9,110,317343184,be297ejCEn,badapikey,Test,cinc,GAPITesterAutoPays,EXPIRED,
        //GetAutoPays,9,111,317343184,be297ejCEn,badapikey,Test,collier,GAPITesterAutoPays,EXPIRED,
        //GetAutoPays,9,112,317343184,be297ejCEn,badapikey,Test,fiserv,GAPITesterAutoPays,EXPIRED,
        //GetAutoPays,9,113,317343184,be297ejCEn,badapikey,Test,onsite,GAPITesterAutoPays,EXPIRED,17343184
        //GetAutoPays,9,114,317343184,be297ejCEn,badapikey,Test,tops,GAPITesterAutoPays,EXPIRED,17343184

        //GetAutoPays,9,115,317343184,be297ejCEn,badapikey,Test,associa,GAPITesterAutoPays,,
        //GetAutoPays,9,116,317343184,be297ejCEn,badapikey,Test,cinc,GAPITesterAutoPays,,
        //GetAutoPays,9,117,317343184,be297ejCEn,badapikey,Test,collier,GAPITesterAutoPays,,
        //GetAutoPays,9,118,317343184,be297ejCEn,badapikey,Test,fiserv,GAPITesterAutoPays,,
        //GetAutoPays,9,119,317343184,be297ejCEn,badapikey,Test,onsite,GAPITesterAutoPays,,17343184
        //GetAutoPays,9,120,317343184,be297ejCEn,badapikey,Test,tops,GAPITesterAutoPays,,17343184
        testCases.add(
            new AapiTestCaseCollection(
                new Credentials(userId, username, password, "badapikey", pmId),
                "Test",
                endpointValue
            ).add(
                new GetAutoPays(
                    "Test case 97 to 120 - Bad api key, different statuses",
                    getExpectedResponse(gatewayErrors, "9"),
                    residentReferenceId,
                    status)
            ));

        //GetAutoPays,9,121,317343184,be297ejCEn, ,Test,associa,GAPITesterAutoPays,ACTIVE,
        //GetAutoPays,9,122,317343184,be297ejCEn, ,Test,cinc,GAPITesterAutoPays,ACTIVE,
        //GetAutoPays,9,123,317343184,be297ejCEn, ,Test,collier,GAPITesterAutoPays,ACTIVE,
        //GetAutoPays,9,124,317343184,be297ejCEn, ,Test,fiserv,GAPITesterAutoPays,ACTIVE,
        //GetAutoPays,9,125,317343184,be297ejCEn, ,Test,onsite,GAPITesterAutoPays,ACTIVE,17343184
        //GetAutoPays,9,126,317343184,be297ejCEn, ,Test,tops,GAPITesterAutoPays,ACTIVE,17343184

        //GetAutoPays,9,127,317343184,be297ejCEn, ,Test,associa,GAPITesterAutoPays,CANCELLED,
        //GetAutoPays,9,128,317343184,be297ejCEn, ,Test,cinc,GAPITesterAutoPays,CANCELLED,
        //GetAutoPays,9,129,317343184,be297ejCEn, ,Test,collier,GAPITesterAutoPays,CANCELLED,
        //GetAutoPays,9,130,317343184,be297ejCEn, ,Test,fiserv,GAPITesterAutoPays,CANCELLED,
        //GetAutoPays,9,131,317343184,be297ejCEn, ,Test,onsite,GAPITesterAutoPays,CANCELLED,17343184
        //GetAutoPays,9,132,317343184,be297ejCEn, ,Test,tops,GAPITesterAutoPays,CANCELLED,17343184

        //GetAutoPays,9,133,317343184,be297ejCEn, ,Test,associa,GAPITesterAutoPays,EXPIRED,
        //GetAutoPays,9,134,317343184,be297ejCEn, ,Test,cinc,GAPITesterAutoPays,EXPIRED,
        //GetAutoPays,9,135,317343184,be297ejCEn, ,Test,collier,GAPITesterAutoPays,EXPIRED,
        //GetAutoPays,9,136,317343184,be297ejCEn, ,Test,fiserv,GAPITesterAutoPays,EXPIRED,
        //GetAutoPays,9,137,317343184,be297ejCEn, ,Test,onsite,GAPITesterAutoPays,EXPIRED,17343184
        //GetAutoPays,9,138,317343184,be297ejCEn, ,Test,tops,GAPITesterAutoPays,EXPIRED,17343184

        //GetAutoPays,9,139,317343184,be297ejCEn, ,Test,associa,GAPITesterAutoPays,,
        //GetAutoPays,9,140,317343184,be297ejCEn, ,Test,cinc,GAPITesterAutoPays,,
        //GetAutoPays,9,141,317343184,be297ejCEn, ,Test,collier,GAPITesterAutoPays,,
        //GetAutoPays,9,142,317343184,be297ejCEn, ,Test,fiserv,GAPITesterAutoPays,,
        //GetAutoPays,9,143,317343184,be297ejCEn, ,Test,onsite,GAPITesterAutoPays,,17343184
        //GetAutoPays,9,144,317343184,be297ejCEn, ,Test,tops,GAPITesterAutoPays,,17343184
        testCases.add(
            new AapiTestCaseCollection(
                new Credentials(userId, username, password, " ", pmId),
                "Test",
                endpointValue
            ).add(
                new GetAutoPays(
                    "Test case 121 to 144 - Blank space as api key, different statuses",
                    getExpectedResponse(gatewayErrors, "9"),
                    residentReferenceId,
                    status)
            ));

        //GetAutoPays,20,145,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,,associa,GAPITesterAutoPays,ACTIVE,
        //GetAutoPays,20,146,317343184,be297ejCEn,hovee3uv0ooShoi1Such,,cinc,GAPITesterAutoPays,ACTIVE,
        //GetAutoPays,20,147,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,,collier,GAPITesterAutoPays,ACTIVE,
        //GetAutoPays,20,148,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,,fiserv,GAPITesterAutoPays,ACTIVE,
        //GetAutoPays,20,149,317343184,be297ejCEn,qua1aiPhul2chohz5aer,,onsite,GAPITesterAutoPays,ACTIVE,17343184
        //GetAutoPays,20,150,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,,tops,GAPITesterAutoPays,ACTIVE,17343184

        //GetAutoPays,20,151,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,,associa,GAPITesterAutoPays,CANCELLED,
        //GetAutoPays,20,152,317343184,be297ejCEn,hovee3uv0ooShoi1Such,,cinc,GAPITesterAutoPays,CANCELLED,
        //GetAutoPays,20,153,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,,collier,GAPITesterAutoPays,CANCELLED,
        //GetAutoPays,20,154,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,,fiserv,GAPITesterAutoPays,CANCELLED,
        //GetAutoPays,20,155,317343184,be297ejCEn,qua1aiPhul2chohz5aer,,onsite,GAPITesterAutoPays,CANCELLED,17343184
        //GetAutoPays,20,156,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,,tops,GAPITesterAutoPays,CANCELLED,17343184

        //GetAutoPays,20,157,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,,associa,GAPITesterAutoPays,EXPIRED,
        //GetAutoPays,20,158,317343184,be297ejCEn,hovee3uv0ooShoi1Such,,cinc,GAPITesterAutoPays,EXPIRED,
        //GetAutoPays,20,159,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,,collier,GAPITesterAutoPays,EXPIRED,
        //GetAutoPays,20,160,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,,fiserv,GAPITesterAutoPays,EXPIRED,
        //GetAutoPays,20,161,317343184,be297ejCEn,qua1aiPhul2chohz5aer,,onsite,GAPITesterAutoPays,EXPIRED,17343184
        //GetAutoPays,20,162,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,,tops,GAPITesterAutoPays,EXPIRED,17343184

        //GetAutoPays,20,163,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,,associa,GAPITesterAutoPays,,
        //GetAutoPays,20,164,317343184,be297ejCEn,hovee3uv0ooShoi1Such,,cinc,GAPITesterAutoPays,,
        //GetAutoPays,20,165,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,,collier,GAPITesterAutoPays,,
        //GetAutoPays,20,166,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,,fiserv,GAPITesterAutoPays,,
        //GetAutoPays,20,167,317343184,be297ejCEn,qua1aiPhul2chohz5aer,,onsite,GAPITesterAutoPays,,17343184
        //GetAutoPays,20,168,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,,tops,GAPITesterAutoPays,,17343184
        testCases.add(
            new AapiTestCaseCollection(
                new Credentials(userId, username, password, null, pmId),
                "",
                endpointValue
            ).add(
                new GetAutoPays(
                    "Test case 145 to 168 - Empty mode, different statuses",
                    getExpectedResponse(gatewayErrors, "20"),
                    residentReferenceId,
                    status)
            ));

        //GetAutoPays,21,169,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Production,associa,GAPITesterAutoPays,ACTIVE,
        //GetAutoPays,21,170,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Production,cinc,GAPITesterAutoPays,ACTIVE,
        //GetAutoPays,21,171,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Production,collier,GAPITesterAutoPays,ACTIVE,
        //GetAutoPays,21,172,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Production,fiserv,GAPITesterAutoPays,ACTIVE,
        //GetAutoPays,21,173,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Production,onsite,GAPITesterAutoPays,ACTIVE,17343184
        //GetAutoPays,21,174,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Production,tops,GAPITesterAutoPays,ACTIVE,17343184

        //GetAutoPays,21,175,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Production,associa,GAPITesterAutoPays,CANCELLED,
        //GetAutoPays,21,176,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Production,cinc,GAPITesterAutoPays,CANCELLED,
        //GetAutoPays,21,177,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Production,collier,GAPITesterAutoPays,CANCELLED,
        //GetAutoPays,21,178,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Production,fiserv,GAPITesterAutoPays,CANCELLED,
        //GetAutoPays,21,179,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Production,onsite,GAPITesterAutoPays,CANCELLED,17343184
        //GetAutoPays,21,180,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Production,tops,GAPITesterAutoPays,CANCELLED,17343184

        //GetAutoPays,21,181,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Production,associa,GAPITesterAutoPays,EXPIRED,
        //GetAutoPays,21,182,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Production,cinc,GAPITesterAutoPays,EXPIRED,
        //GetAutoPays,21,183,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Production,collier,GAPITesterAutoPays,EXPIRED,
        //GetAutoPays,21,184,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Production,fiserv,GAPITesterAutoPays,EXPIRED,
        //GetAutoPays,21,185,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Production,onsite,GAPITesterAutoPays,EXPIRED,17343184
        //GetAutoPays,21,186,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Production,tops,GAPITesterAutoPays,EXPIRED,17343184

        //GetAutoPays,21,187,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Production,associa,GAPITesterAutoPays,,
        //GetAutoPays,21,188,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Production,cinc,GAPITesterAutoPays,,
        //GetAutoPays,21,189,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Production,collier,GAPITesterAutoPays,,
        //GetAutoPays,21,190,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Production,fiserv,GAPITesterAutoPays,,
        //GetAutoPays,21,191,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Production,onsite,GAPITesterAutoPays,,17343184
        //GetAutoPays,21,192,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Production,tops,GAPITesterAutoPays,,17343184
        testCases.add(
            new AapiTestCaseCollection(
                new Credentials(userId, username, password, null, pmId),
                "Production",
                endpointValue
            ).add(
                new GetAutoPays(
                    "Test case 169 to 192 - Production mode, different statuses",
                    getExpectedResponse(gatewayErrors, "21"),
                    residentReferenceId,
                    status)
            ));

        //GetAutoPays,38,193,317343184, ,iedahy2ohcie7nieWoo1,Test,associa,GAPITesterAutoPays,ACTIVE,
        //GetAutoPays,38,194,317343184, ,hovee3uv0ooShoi1Such,Test,cinc,GAPITesterAutoPays,ACTIVE,
        //GetAutoPays,38,195,317343184, ,rohn3shaiquaesh1xoSu,Test,collier,GAPITesterAutoPays,ACTIVE,
        //GetAutoPays,38,196,317343184, ,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,GAPITesterAutoPays,ACTIVE,
        //GetAutoPays,38,197,317343184, ,qua1aiPhul2chohz5aer,Test,onsite,GAPITesterAutoPays,ACTIVE,17343184
        //GetAutoPays,38,198,317343184, ,EinaGh1oe4eihu1gohci,Test,tops,GAPITesterAutoPays,ACTIVE,17343184

        //GetAutoPays,38,199,317343184, ,iedahy2ohcie7nieWoo1,Test,associa,GAPITesterAutoPays,CANCELLED,
        //GetAutoPays,38,200,317343184, ,hovee3uv0ooShoi1Such,Test,cinc,GAPITesterAutoPays,CANCELLED,
        //GetAutoPays,38,201,317343184, ,rohn3shaiquaesh1xoSu,Test,collier,GAPITesterAutoPays,CANCELLED,
        //GetAutoPays,38,202,317343184, ,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,GAPITesterAutoPays,CANCELLED,
        //GetAutoPays,38,203,317343184, ,qua1aiPhul2chohz5aer,Test,onsite,GAPITesterAutoPays,CANCELLED,17343184
        //GetAutoPays,38,204,317343184, ,EinaGh1oe4eihu1gohci,Test,tops,GAPITesterAutoPays,CANCELLED,17343184

        //GetAutoPays,38,205,317343184, ,iedahy2ohcie7nieWoo1,Test,associa,GAPITesterAutoPays,EXPIRED,
        //GetAutoPays,38,206,317343184, ,hovee3uv0ooShoi1Such,Test,cinc,GAPITesterAutoPays,EXPIRED,
        //GetAutoPays,38,207,317343184, ,rohn3shaiquaesh1xoSu,Test,collier,GAPITesterAutoPays,EXPIRED,
        //GetAutoPays,38,208,317343184, ,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,GAPITesterAutoPays,EXPIRED,
        //GetAutoPays,38,209,317343184, ,qua1aiPhul2chohz5aer,Test,onsite,GAPITesterAutoPays,EXPIRED,17343184
        //GetAutoPays,38,210,317343184, ,EinaGh1oe4eihu1gohci,Test,tops,GAPITesterAutoPays,EXPIRED,17343184

        //GetAutoPays,38,211,317343184, ,iedahy2ohcie7nieWoo1,Test,associa,GAPITesterAutoPays,,
        //GetAutoPays,38,212,317343184, ,hovee3uv0ooShoi1Such,Test,cinc,GAPITesterAutoPays,,
        //GetAutoPays,38,213,317343184, ,rohn3shaiquaesh1xoSu,Test,collier,GAPITesterAutoPays,,
        //GetAutoPays,38,214,317343184, ,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,GAPITesterAutoPays,,
        //GetAutoPays,38,215,317343184, ,qua1aiPhul2chohz5aer,Test,onsite,GAPITesterAutoPays,,17343184
        //GetAutoPays,38,216,317343184, ,EinaGh1oe4eihu1gohci,Test,tops,GAPITesterAutoPays,,17343184
        testCases.add(
            new AapiTestCaseCollection(
                new Credentials(userId, username, " ", null, pmId),
                "Test",
                endpointValue
            ).add(
                new GetAutoPays(
                    "Test case 193 to 216 - Blank space as password, different statuses",
                    getExpectedResponse(gatewayErrors, "38"),
                    residentReferenceId,
                    status)
            ));

        //GetAutoPays,38,217,31734,badpass,iedahy2ohcie7nieWoo1,Test,associa,GAPITesterAutoPays,ACTIVE,
        //GetAutoPays,38,218,31734,badpass,hovee3uv0ooShoi1Such,Test,cinc,GAPITesterAutoPays,ACTIVE,
        //GetAutoPays,38,219,31734,badpass,rohn3shaiquaesh1xoSu,Test,collier,GAPITesterAutoPays,ACTIVE,
        //GetAutoPays,38,220,31734,badpass,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,GAPITesterAutoPays,ACTIVE,
        //GetAutoPays,38,221,31734,badpass,qua1aiPhul2chohz5aer,Test,onsite,GAPITesterAutoPays,ACTIVE,17343184
        //GetAutoPays,38,222,31734,badpass,EinaGh1oe4eihu1gohci,Test,tops,GAPITesterAutoPays,ACTIVE,17343184

        //GetAutoPays,38,223,31734,badpass,iedahy2ohcie7nieWoo1,Test,associa,GAPITesterAutoPays,CANCELLED,
        //GetAutoPays,38,224,31734,badpass,hovee3uv0ooShoi1Such,Test,cinc,GAPITesterAutoPays,CANCELLED,
        //GetAutoPays,38,225,31734,badpass,rohn3shaiquaesh1xoSu,Test,collier,GAPITesterAutoPays,CANCELLED,
        //GetAutoPays,38,226,31734,badpass,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,GAPITesterAutoPays,CANCELLED,
        //GetAutoPays,38,227,31734,badpass,qua1aiPhul2chohz5aer,Test,onsite,GAPITesterAutoPays,CANCELLED,17343184
        //GetAutoPays,38,228,31734,badpass,EinaGh1oe4eihu1gohci,Test,tops,GAPITesterAutoPays,CANCELLED,17343184

        //GetAutoPays,38,229,31734,badpass,iedahy2ohcie7nieWoo1,Test,associa,GAPITesterAutoPays,EXPIRED,
        //GetAutoPays,38,230,31734,badpass,hovee3uv0ooShoi1Such,Test,cinc,GAPITesterAutoPays,EXPIRED,
        //GetAutoPays,38,231,31734,badpass,rohn3shaiquaesh1xoSu,Test,collier,GAPITesterAutoPays,EXPIRED,
        //GetAutoPays,38,232,31734,badpass,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,GAPITesterAutoPays,EXPIRED,
        //GetAutoPays,38,233,31734,badpass,qua1aiPhul2chohz5aer,Test,onsite,GAPITesterAutoPays,EXPIRED,17343184
        //GetAutoPays,38,234,31734,badpass,EinaGh1oe4eihu1gohci,Test,tops,GAPITesterAutoPays,EXPIRED,17343184

        //GetAutoPays,38,235,31734,badpass,iedahy2ohcie7nieWoo1,Test,associa,GAPITesterAutoPays,,
        //GetAutoPays,38,236,31734,badpass,hovee3uv0ooShoi1Such,Test,cinc,GAPITesterAutoPays,,
        //GetAutoPays,38,237,31734,badpass,rohn3shaiquaesh1xoSu,Test,collier,GAPITesterAutoPays,,
        //GetAutoPays,38,238,31734,badpass,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,GAPITesterAutoPays,,
        //GetAutoPays,38,239,31734,badpass,qua1aiPhul2chohz5aer,Test,onsite,GAPITesterAutoPays,,17343184
        //GetAutoPays,38,240,31734,badpass,EinaGh1oe4eihu1gohci,Test,tops,GAPITesterAutoPays,,17343184
        testCases.add(
            new AapiTestCaseCollection(
                new Credentials("31734", username, "badpass", null, pmId),
                "Test",
                endpointValue
            ).add(
                new GetAutoPays(
                    "Test case 217 to 240 - Bad password and Bad userId, different statuses",
                    getExpectedResponse(gatewayErrors, "38"),
                    residentReferenceId,
                    status)
            ));

        //GetAutoPays,38,241,317343184,badpass,iedahy2ohcie7nieWoo1,Test,associa,GAPITesterAutoPays,ACTIVE,
        //GetAutoPays,38,242,317343184,badpass,hovee3uv0ooShoi1Such,Test,cinc,GAPITesterAutoPays,ACTIVE,
        //GetAutoPays,38,243,317343184,badpass,rohn3shaiquaesh1xoSu,Test,collier,GAPITesterAutoPays,ACTIVE,
        //GetAutoPays,38,244,317343184,badpass,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,GAPITesterAutoPays,ACTIVE,
        //GetAutoPays,38,245,317343184,badpass,qua1aiPhul2chohz5aer,Test,onsite,GAPITesterAutoPays,ACTIVE,17343184
        //GetAutoPays,38,246,317343184,badpass,EinaGh1oe4eihu1gohci,Test,tops,GAPITesterAutoPays,ACTIVE,17343184

        //GetAutoPays,38,247,317343184,badpass,iedahy2ohcie7nieWoo1,Test,associa,GAPITesterAutoPays,CANCELLED,
        //GetAutoPays,38,248,317343184,badpass,hovee3uv0ooShoi1Such,Test,cinc,GAPITesterAutoPays,CANCELLED,
        //GetAutoPays,38,249,317343184,badpass,rohn3shaiquaesh1xoSu,Test,collier,GAPITesterAutoPays,CANCELLED,
        //GetAutoPays,38,250,317343184,badpass,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,GAPITesterAutoPays,CANCELLED,
        //GetAutoPays,38,251,317343184,badpass,qua1aiPhul2chohz5aer,Test,onsite,GAPITesterAutoPays,CANCELLED,17343184
        //GetAutoPays,38,252,317343184,badpass,EinaGh1oe4eihu1gohci,Test,tops,GAPITesterAutoPays,CANCELLED,17343184

        //GetAutoPays,38,253,317343184,badpass,iedahy2ohcie7nieWoo1,Test,associa,GAPITesterAutoPays,EXPIRED,
        //GetAutoPays,38,254,317343184,badpass,hovee3uv0ooShoi1Such,Test,cinc,GAPITesterAutoPays,EXPIRED,
        //GetAutoPays,38,255,317343184,badpass,rohn3shaiquaesh1xoSu,Test,collier,GAPITesterAutoPays,EXPIRED,
        //GetAutoPays,38,256,317343184,badpass,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,GAPITesterAutoPays,EXPIRED,
        //GetAutoPays,38,257,317343184,badpass,qua1aiPhul2chohz5aer,Test,onsite,GAPITesterAutoPays,EXPIRED,17343184
        //GetAutoPays,38,258,317343184,badpass,EinaGh1oe4eihu1gohci,Test,tops,GAPITesterAutoPays,EXPIRED,17343184

        //GetAutoPays,38,259,317343184,badpass,iedahy2ohcie7nieWoo1,Test,associa,GAPITesterAutoPays,,
        //GetAutoPays,38,260,317343184,badpass,hovee3uv0ooShoi1Such,Test,cinc,GAPITesterAutoPays,,
        //GetAutoPays,38,261,317343184,badpass,rohn3shaiquaesh1xoSu,Test,collier,GAPITesterAutoPays,,
        //GetAutoPays,38,262,317343184,badpass,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,GAPITesterAutoPays,,
        //GetAutoPays,38,263,317343184,badpass,qua1aiPhul2chohz5aer,Test,onsite,GAPITesterAutoPays,,17343184
        //GetAutoPays,38,264,317343184,badpass,EinaGh1oe4eihu1gohci,Test,tops,GAPITesterAutoPays,,17343184
        testCases.add(
            new AapiTestCaseCollection(
                new Credentials(userId, username, "badpass", null, pmId),
                "Test",
                endpointValue
            ).add(
                new GetAutoPays(
                    "Test case 241 to 264 - Bad password, different statuses",
                    getExpectedResponse(gatewayErrors, "38"),
                    residentReferenceId,
                    status)
            ));

        //GetAutoPays,45,271,317343184,,iedahy2ohcie7nieWoo1,Test,associa,GAPITesterAutoPays,ACTIVE,
        //GetAutoPays,45,272,317343184,,hovee3uv0ooShoi1Such,Test,cinc,GAPITesterAutoPays,ACTIVE,
        //GetAutoPays,45,273,317343184,,rohn3shaiquaesh1xoSu,Test,collier,GAPITesterAutoPays,ACTIVE,
        //GetAutoPays,45,274,317343184,,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,GAPITesterAutoPays,ACTIVE,
        //GetAutoPays,45,275,317343184,,qua1aiPhul2chohz5aer,Test,onsite,GAPITesterAutoPays,ACTIVE,17343184
        //GetAutoPays,45,276,317343184,,EinaGh1oe4eihu1gohci,Test,tops,GAPITesterAutoPays,ACTIVE,17343184

        //GetAutoPays,45,277,317343184,,iedahy2ohcie7nieWoo1,Test,associa,GAPITesterAutoPays,CANCELLED,
        //GetAutoPays,45,278,317343184,,hovee3uv0ooShoi1Such,Test,cinc,GAPITesterAutoPays,CANCELLED,
        //GetAutoPays,45,279,317343184,,rohn3shaiquaesh1xoSu,Test,collier,GAPITesterAutoPays,CANCELLED,
        //GetAutoPays,45,280,317343184,,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,GAPITesterAutoPays,CANCELLED,
        //GetAutoPays,45,281,317343184,,qua1aiPhul2chohz5aer,Test,onsite,GAPITesterAutoPays,CANCELLED,17343184
        //GetAutoPays,45,282,317343184,,EinaGh1oe4eihu1gohci,Test,tops,GAPITesterAutoPays,CANCELLED,17343184

        //GetAutoPays,45,283,317343184,,iedahy2ohcie7nieWoo1,Test,associa,GAPITesterAutoPays,EXPIRED,
        //GetAutoPays,45,284,317343184,,hovee3uv0ooShoi1Such,Test,cinc,GAPITesterAutoPays,EXPIRED,
        //GetAutoPays,45,285,317343184,,rohn3shaiquaesh1xoSu,Test,collier,GAPITesterAutoPays,EXPIRED,
        //GetAutoPays,45,286,317343184,,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,GAPITesterAutoPays,EXPIRED,
        //GetAutoPays,45,287,317343184,,qua1aiPhul2chohz5aer,Test,onsite,GAPITesterAutoPays,EXPIRED,17343184
        //GetAutoPays,45,288,317343184,,EinaGh1oe4eihu1gohci,Test,tops,GAPITesterAutoPays,EXPIRED,17343184

        //GetAutoPays,45,289,317343184,,iedahy2ohcie7nieWoo1,Test,associa,GAPITesterAutoPays,,
        //GetAutoPays,45,290,317343184,,hovee3uv0ooShoi1Such,Test,cinc,GAPITesterAutoPays,,
        //GetAutoPays,45,291,317343184,,rohn3shaiquaesh1xoSu,Test,collier,GAPITesterAutoPays,,
        //GetAutoPays,45,292,317343184,,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,GAPITesterAutoPays,,
        //GetAutoPays,45,293,317343184,,qua1aiPhul2chohz5aer,Test,onsite,GAPITesterAutoPays,,17343184
        //GetAutoPays,45,294,317343184,,EinaGh1oe4eihu1gohci,Test,tops,GAPITesterAutoPays,,17343184
        testCases.add(
            new AapiTestCaseCollection(
                new Credentials(userId, username, "", null, pmId),
                "Test",
                endpointValue
            ).add(
                new GetAutoPays(
                    "Test case 271 to 294 - Empty password, status empty",
                    getExpectedResponse(gatewayErrors, "45"),
                    residentReferenceId,
                    status)
            ));
      }

      //GetAutoPays,39,265,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,GAPITesterAutoPays,badstatus,
      //GetAutoPays,39,266,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,GAPITesterAutoPays,badstatus,
      //GetAutoPays,39,267,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,GAPITesterAutoPays,badstatus,
      //GetAutoPays,39,268,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,GAPITesterAutoPays,badstatus,
      //GetAutoPays,39,269,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,GAPITesterAutoPays,badstatus,17343184
      //GetAutoPays,39,270,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,GAPITesterAutoPays,badstatus,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password, null, pmId),
              "Test",
              endpointValue
          ).add(
              new GetAutoPays(
                  "Test case 265 to 270 - Bad status",
                  getExpectedResponse(gatewayErrors, "39"),
                  residentReferenceId,
                  "badstatus")
          ));
    }

    executeTests(testCases);
  }
}

