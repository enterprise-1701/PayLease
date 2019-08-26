package com.paylease.app.qa.api.tests.aapi;

import com.paylease.app.qa.api.tests.TestCaseCollection;
import com.paylease.app.qa.api.tests.aapi.testcase.AddProperty;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.api.Credentials;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.testng.annotations.Test;


public class AddPropertyTest extends BaseTest {

  private static final String REGION = "aapi";
  private static final String FEATURE = "AddProperty";

  //-----------------------------------TESTS--------------------------------------------------------

  @Test(groups = {"aapi", "AddProperty"})
  public void addProperty() {
    Logger.info("Add Property Test");

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

    //AddProperty,1,1,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,associaAPI01,1 API lane,1 API lane,,,,0,3,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-JP Morgan Chase,Checking,490000018,8335939580,
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password, null, pmId),
            "Test",
            "associa"
        ).add(
            new AddProperty(
                "Test case 1 - associa",
                getExpectedResponse(gatewayErrors, "1"))
                .setPropertyId("associaAPI01")
                .setPropertyName("1 API Lane")
                .setStreetAddress("1 API Lane")
                .setCity("")
                .setState("")
                .setPostalCode("")
                .setUnitCount("0")
                .setFreqId("3")
                .setEmail("test@test.com")
                .setLogoUrl("https://paylease.com/image.png")
                .setPhone("760-711-1111")
                .setFieldName("Lease Payment")
                .setVarName("lease_payment")
                .setBankName("API-JP Morgan Chase")
                .setBankAccountType("Checking")
                .setBankAccountRouting("490000018")
                .setBankAccountNumber("8335939580")
        ));

    //AddProperty,1,2,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,cincAPI01,2 API lane,2 API lane,,,,0,4,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-USAA,Checking,490000018,1508579796,
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password, null, pmId),
            "Test",
            "cinc"
        ).add(
            new AddProperty(
                "Test case 2 - cinc",
                getExpectedResponse(gatewayErrors, "1"))
                .setPropertyId("cincAPI01")
                .setPropertyName("2 API Lane")
                .setStreetAddress("2 API Lane")
                .setCity("")
                .setState("")
                .setPostalCode("")
                .setUnitCount("0")
                .setFreqId("4")
                .setEmail("test@test.com")
                .setLogoUrl("https://paylease.com/image.png")
                .setPhone("760-711-1111")
                .setFieldName("Lease Payment")
                .setVarName("lease_payment")
                .setBankName("API-USAA")
                .setBankAccountType("Checking")
                .setBankAccountRouting("490000018")
                .setBankAccountNumber("1508579796")
        ));

    //AddProperty,1,3,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,collierAPI01,3 API lane,3 API lane,,,,0,1,NULL,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Bank Of America,Savings,490000018,3250332230,
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password, null, pmId),
            "Test",
            "collier"
        ).add(
            new AddProperty(
                "Test case 3 - collier",
                getExpectedResponse(gatewayErrors, "1"))
                .setPropertyId("associaAPI01")
                .setPropertyName("3 API Lane")
                .setStreetAddress("3 API Lane")
                .setCity("")
                .setState("")
                .setPostalCode("")
                .setUnitCount("0")
                .setFreqId("1")
                .setLogoUrl("https://paylease.com/image.png")
                .setPhone("760-711-1111")
                .setFieldName("Lease Payment")
                .setVarName("lease_payment")
                .setBankName("API-Bank Of America")
                .setBankAccountType("Savings")
                .setBankAccountRouting("490000018")
                .setBankAccountNumber("3250332230")
        ));

    //AddProperty,1,4,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,fiservAPII01,4 API lane,4 API lane,,,,0,2,test@test.com,NULL,760-711-1111,Lease Payment,lease_payment,API-San Diego County Credit Union,Savings,490000018,8242745794,
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password, null, pmId),
            "Test",
            "fiserv"
        ).add(
            new AddProperty(
                "Test case 4 - fiserv",
                getExpectedResponse(gatewayErrors, "1"))
                .setPropertyId("fiservAPI01")
                .setPropertyName("4 API Lane")
                .setStreetAddress("4 API Lane")
                .setCity("")
                .setState("")
                .setPostalCode("")
                .setUnitCount("0")
                .setFreqId("2")
                .setEmail("test@test.com")
                .setPhone("760-711-1111")
                .setFieldName("Lease Payment")
                .setVarName("lease_payment")
                .setBankName("API-San Diego County Credit Union")
                .setBankAccountType("Savings")
                .setBankAccountRouting("490000018")
                .setBankAccountNumber("8242745794")
        ));

    //AddProperty,1,5,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,onsiteAPI01,5 API lane,5 API lane,,,,0,3,test@test.com,https://paylease.com/image.png,NULL,Lease Payment,lease_payment,API-Navy Federal,Savings,490000018,8198956935,17343184
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password, null, pmId),
            "Test",
            "onsite"
        ).add(
            new AddProperty(
                "Test case 5 - onsite",
                getExpectedResponse(gatewayErrors, "1"))
                .setPropertyId("onsiteAPI01")
                .setPropertyName("5 API Lane")
                .setStreetAddress("5 API Lane")
                .setCity("")
                .setState("")
                .setPostalCode("")
                .setUnitCount("0")
                .setFreqId("3")
                .setEmail("test@test.com")
                .setLogoUrl("https://paylease.com/image.png")
                .setFieldName("Lease Payment")
                .setVarName("lease_payment")
                .setBankName("API-Navy Federal")
                .setBankAccountType("Savings")
                .setBankAccountRouting("490000018")
                .setBankAccountNumber("8198956935")
        ));

    //AddProperty,1,6,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,topsAPI01,6 API lane,6 API lane,,,,0,4,NULL,NULL,NULL,Lease Payment,lease_payment,API-Pacific Marine,Checking,490000018,5781725949,17343184
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password, null, pmId),
            "Test",
            "tops"
        ).add(
            new AddProperty(
                "Test case 6 - tops",
                getExpectedResponse(gatewayErrors, "1"))
                .setPropertyId("topsAPI01")
                .setPropertyName("6 API Lane")
                .setStreetAddress("6 API Lane")
                .setCity("")
                .setState("")
                .setPostalCode("")
                .setUnitCount("0")
                .setFreqId("4")
                .setFieldName("Lease Payment")
                .setVarName("lease_payment")
                .setBankName("API-Pacific Marine")
                .setBankAccountType("Checking")
                .setBankAccountRouting("490000018")
                .setBankAccountNumber("5781725949")
        ));

    for (String endpointValue : endpointValues) {
      final String[] unitCounts = {
          "1", "2", "10", "100", "1000", "10000", "16777215", "200000000",
      };

      for (String unitCount : unitCounts) {
        //AddProperty,1,7,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,associaAPI02,7 API lane,7 API lane,San Diego, IA,52081,1,1,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-JP Morgan Chase,Checking,490000018,8335939580,
        //AddProperty,1,8,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,cincAPI02,8 API lane,8 API lane,Cavetown, MT,59327,1,2,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-USAA,Checking,490000018,1508579796,
        //AddProperty,1,9,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,collierAPI02,9 API lane,9 API lane,Connoquenessing, TN,38275,1,3,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Bank Of America,Savings,490000018,3250332230,
        //AddProperty,1,10,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,fiservAPI02,10 API lane,10 API lane,Suquamish, MI,48666,1,4,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-San Diego County Credit Union,Savings,490000018,8242745794,
        //AddProperty,1,11,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,onsiteAPI02,11 API lane,11 API lane,Yellow Hammer, IL,62358,1,1,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Navy Federal,Savings,490000018,8198956935,17343184
        //AddProperty,1,12,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,topsAPI02,12 API lane,12 API lane,Mounds, GA,39801,1,2,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Pacific Marine,Checking,490000018,5781725949,17343184
        //AddProperty,1,13,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,associaAPI03,13 API lane,13 API lane,Pothook, PA,19023,2,3,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-JP Morgan Chase,Checking,490000018,8335939580,
        //AddProperty,1,14,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,cincAPI03,14 API lane,14 API lane,Bat Cave, TN,37519,2,4,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-USAA,Checking,490000018,1508579796,
        //AddProperty,1,15,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,collierAPI03,15 API lane,15 API lane,Friendsville, WY,82789,2,1,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Bank Of America,Savings,490000018,3250332230,
        //AddProperty,1,16,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,fiservAPI03,16 API lane,16 API lane,Maidstone, NC,28282,2,2,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-San Diego County Credit Union,Savings,490000018,8242745794,
        //AddProperty,1,17,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,onsiteAPI03,17 API lane,17 API lane,Welagamika, DE,19752,2,3,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Navy Federal,Savings,490000018,8198956935,17343184
        //AddProperty,1,18,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,topsAPI03,18 API lane,18 API lane,Winneboujou, NH,3659,2,4,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Pacific Marine,Checking,490000018,5781725949,17343184
        //AddProperty,1,19,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,associaAPI04,19 API lane,19 API lane,Una, FL,34480,10,,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-JP Morgan Chase,Checking,490000018,8335939580,
        //AddProperty,1,20,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,cincAPI04,20 API lane,20 API lane,Busthead, SC,29953,10,,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-USAA,Checking,490000018,1508579796,
        //AddProperty,1,21,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,collierAPI04,21 API lane,21 API lane,Third Cliff, NH,3047,10,,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Bank Of America,Savings,490000018,3250332230,
        //AddProperty,1,22,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,fiservAPI04,22 API lane,22 API lane,Ronkonkoma, NC,27315,10,,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-San Diego County Credit Union,Savings,490000018,8242745794,
        //AddProperty,1,23,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,onsiteAPI04,23 API lane,23 API lane,Gunsight, MI,49608,10,,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Navy Federal,Savings,490000018,8198956935,17343184
        //AddProperty,1,24,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,topsAPI04,24 API lane,24 API lane,Lipps, AR,71938,10,,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Pacific Marine,Checking,490000018,5781725949,17343184
        //AddProperty,1,25,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,associaAPI05,25 API lane,25 API lane,Coleville,NV,88956,100,1,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-JP Morgan Chase,Checking,490000018,8335939580,
        //AddProperty,1,26,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,cincAPI05,26 API lane,26 API lane,Shadow Gate,SC,29563,100,2,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-USAA,Checking,490000018,1508579796,
        //AddProperty,1,27,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,collierAPI05,27 API lane,27 API lane,Chickasawba,SC,29791,100,3,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Bank Of America,Savings,490000018,3250332230,
        //AddProperty,1,28,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,fiservAPI05,28 API lane,28 API lane,Good Thunder,CO,81493,100,4,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-San Diego County Credit Union,Savings,490000018,8242745794,
        //AddProperty,1,29,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,onsiteAPI05,29 API lane,29 API lane,Ohogamiut,ID,83954,100,1,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Navy Federal,Savings,490000018,8198956935,17343184
        //AddProperty,1,30,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,topsAPI05,30 API lane,30 API lane,Hardscrabble Corner,CO,81042,100,2,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Pacific Marine,Checking,490000018,5781725949,17343184
        //AddProperty,1,31,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,associaAPI0,31 API lane,31 API lane,Black Wolf,MI,49629,1000,3,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-JP Morgan Chase,Checking,490000018,8335939580,
        //AddProperty,1,32,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,cincAPI06,32 API lane,32 API lane,Stovepipe,HI,96857,1000,4,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-USAA,Checking,490000018,1508579796,
        //AddProperty,1,33,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,collierAPI06,33 API lane,33 API lane,Glasgow,AL,35950,1000,1,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Bank Of America,Savings,490000018,3250332230,
        //AddProperty,1,34,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,fiservAPI06,34 API lane,34 API lane,Enterprise,WA,98442,1000,2,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-San Diego County Credit Union,Savings,490000018,8242745794,
        //AddProperty,1,35,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,onsiteAPI06,35 API lane,35 API lane,Neilburg,WA,98727,1000,3,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Navy Federal,Savings,490000018,8198956935,17343184
        //AddProperty,1,36,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,topsAPI06,36 API lane,36 API lane,Shivwits,AL,36049,1000,4,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Pacific Marine,Checking,490000018,5781725949,17343184
        //AddProperty,1,37,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,associaAPI07,31 API lane,31 API lane,Black Wolf,MI,49629,10000,3,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-JP Morgan Chase,Checking,490000018,8335939580,
        //AddProperty,1,38,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,cincAPI07,32 API lane,32 API lane,Stovepipe,HI,96857,10000,4,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-USAA,Checking,490000018,1508579796,
        //AddProperty,1,39,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,collierAPI07,33 API lane,33 API lane,Glasgow,AL,35950,10000,1,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Bank Of America,Savings,490000018,3250332230,
        //AddProperty,1,40,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,fiservAPI07,34 API lane,34 API lane,Enterprise,WA,98442,10000,2,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-San Diego County Credit Union,Savings,490000018,8242745794,
        //AddProperty,1,41,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,onsiteAPI07,35 API lane,35 API lane,Neilburg,WA,98727,10000,3,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Navy Federal,Savings,490000018,8198956935,17343184
        //AddProperty,1,42,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,topsAPI07,36 API lane,36 API lane,Shivwits,AL,36049,10000,4,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Pacific Marine,Checking,490000018,5781725949,17343184
        //AddProperty,1,43,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,associaAPI08,37 API lane,37 API lane,Coleville,NV,88956,16777215,1,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-JP Morgan Chase,Checking,490000018,8335939580,
        //AddProperty,1,44,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,cincAPI08,38 API lane,38 API lane,Shadow Gate,SC,29563,16777215,2,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-USAA,Checking,490000018,1508579796,
        //AddProperty,1,45,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,collierAPI08,39 API lane,39 API lane,Chickasawba,SC,29791,16777215,3,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Bank Of America,Savings,490000018,3250332230,
        //AddProperty,1,46,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,fiservAPI08,40 API lane,40 API lane,Good Thunder,CO,81493,16777215,4,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-San Diego County Credit Union,Savings,490000018,8242745794,
        //AddProperty,1,47,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,onsiteAPI08,41 API lane,41 API lane,Ohogamiut,ID,83954,16777215,1,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Navy Federal,Savings,490000018,8198956935,17343184
        //AddProperty,1,48,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,topsAPI08,42 API lane,42 API lane,Hardscrabble Corner,CO,81042,16777215,2,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Pacific Marine,Checking,490000018,5781725949,17343184
        //AddProperty,1,49,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,associaAPI09,43 API lane,43 API lane,Black Wolf,MI,49629,200000000,3,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-JP Morgan Chase,Checking,490000018,8335939580,
        //AddProperty,1,50,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,cincAPI09,44 API lane,44 API lane,Stovepipe,HI,96857,200000000,4,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-USAA,Checking,490000018,1508579796,
        //AddProperty,1,51,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,collierAPI09,45 API lane,45 API lane,Glasgow,AL,35950,200000000,1,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Bank Of America,Savings,490000018,3250332230,
        //AddProperty,1,52,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,fiservAPI09,46 API lane,46 API lane,Enterprise,WA,98442,200000000,2,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-San Diego County Credit Union,Savings,490000018,8242745794,
        //AddProperty,1,53,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,onsiteAPI09,47 API lane,47 API lane,Neilburg,WA,98727,200000000,3,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Navy Federal,Savings,490000018,8198956935,17343184
        //AddProperty,1,54,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,topsAPI09,48 API lane,48 API lane,Shivwits,AL,36049,200000000,4,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Pacific Marine,Checking,490000018,5781725949,17343184
        AddProperty testCase = AddProperty.createValid(
            "Test case 7 to 54 - Unit count: " + unitCount,
            getExpectedResponse(gatewayErrors, "1"))
            .setUnitCount(unitCount);
        if (unitCount.equals("10")) {
          testCase.setFreqId("");
        }
        testCases.add(
            new AapiTestCaseCollection(
                new Credentials(userId, username, password, null, pmId),
                "Test",
                endpointValue
            ).add(testCase)
        );
      }

      //AddProperty,8,55,317343184,be297ejCEn,,Test,associa,associaID1,123 Bad Test Address,123 Bad Test Address,Tiptop,CA,94904,1,3,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-JP Morgan Chase,Checking,490000018,9741,
      //AddProperty,8,56,317343184,be297ejCEn,,Test,cinc,cincID1,123 Bad Test Address,123 Bad Test Address,Deputy,NV,89105,1,4,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-USAA,Checking,490000018,368108,
      //AddProperty,8,57,317343184,be297ejCEn,,Test,collier,collierID1,123 Bad Test Address,123 Bad Test Address,Hepburn,ID,83869,1,1,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Bank Of America,Savings,490000018,272541413958,
      //AddProperty,8,58,317343184,be297ejCEn,,Test,fiserv,fiservID1,123 Bad Test Address,123 Bad Test Address,Flag of Regina,DE,19951,1,2,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-San Diego County Credit Union,Savings,490000018,5491511362699,
      //AddProperty,8,59,317343184,be297ejCEn,,Test,onsite,onsiteID1,123 Bad Test Address,123 Bad Test Address,Deadman Crossing,AR,71857,1,3,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Navy Federal,Savings,490000018,094721976,17343184
      //AddProperty,8,60,317343184,be297ejCEn,,Test,tops,topsID1,123 Bad Test Address,123 Bad Test Address,Stovepipe,WY,82114,1,4,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Pacific Marine,Checking,490000018,274,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password, "", pmId),
              "Test",
              endpointValue
          ).add(AddProperty.createValid(
              "Test case 55 to 60 - Empty Api Key",
              getExpectedResponse(gatewayErrors, "8"))
          ));

      final String[] invalidApiKeys = {
          " ", "badapikey",
      };

      for (String invalidApiKey : invalidApiKeys) {
        //AddProperty,9,61,317343184,be297ejCEn, ,Test,associa,associaID1,123 Bad Test Address,123 Bad Test Address,Tiptop,CA,94904,1,3,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-JP Morgan Chase,Checking,490000018,9741,
        //AddProperty,9,62,317343184,be297ejCEn, ,Test,cinc,cincID1,123 Bad Test Address,123 Bad Test Address,Deputy,NV,89105,1,4,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-USAA,Checking,490000018,368108,
        //AddProperty,9,63,317343184,be297ejCEn, ,Test,collier,collierID1,123 Bad Test Address,123 Bad Test Address,Hepburn,ID,83869,1,1,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Bank Of America,Savings,490000018,272541413958,
        //AddProperty,9,64,317343184,be297ejCEn, ,Test,fiserv,fiservID1,123 Bad Test Address,123 Bad Test Address,Flag of Regina,DE,19951,1,2,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-San Diego County Credit Union,Savings,490000018,5491511362699,
        //AddProperty,9,65,317343184,be297ejCEn, ,Test,onsite,onsiteID1,123 Bad Test Address,123 Bad Test Address,Deadman Crossing,AR,71857,1,3,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Navy Federal,Savings,490000018,094721976,17343184
        //AddProperty,9,66,317343184,be297ejCEn, ,Test,tops,topsID1,123 Bad Test Address,123 Bad Test Address,Stovepipe,WY,82114,1,4,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Pacific Marine,Checking,490000018,274,17343184
        //AddProperty,9,67,317343184,be297ejCEn,badapikey,Test,associa,associaID1,123 Bad Test Address,123 Bad Test Address,Tiptop,CA,94904,1,3,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-JP Morgan Chase,Checking,490000018,9741,
        //AddProperty,9,68,317343184,be297ejCEn,badapikey,Test,cinc,cincID1,123 Bad Test Address,123 Bad Test Address,Deputy,NV,89105,1,4,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-USAA,Checking,490000018,368108,
        //AddProperty,9,69,317343184,be297ejCEn,badapikey,Test,collier,collierID1,123 Bad Test Address,123 Bad Test Address,Hepburn,ID,83869,1,1,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Bank Of America,Savings,490000018,272541413958,
        //AddProperty,9,70,317343184,be297ejCEn,badapikey,Test,fiserv,fiservID1,123 Bad Test Address,123 Bad Test Address,Flag of Regina,DE,19951,1,2,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-San Diego County Credit Union,Savings,490000018,5491511362699,
        //AddProperty,9,71,317343184,be297ejCEn,badapikey,Test,onsite,onsiteID1,123 Bad Test Address,123 Bad Test Address,Deadman Crossing,AR,71857,1,3,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Navy Federal,Savings,490000018,094721976,17343184
        //AddProperty,9,72,317343184,be297ejCEn,badapikey,Test,tops,topsID1,123 Bad Test Address,123 Bad Test Address,Stovepipe,WY,82114,1,4,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Pacific Marine,Checking,490000018,274,17343184
        testCases.add(
            new AapiTestCaseCollection(
                new Credentials(userId, username, password, invalidApiKey, pmId),
                "Test",
                endpointValue
            ).add(AddProperty.createValid(
                "Test case 61 to 72 - Invalid Api Key: '" + invalidApiKey + "'",
                getExpectedResponse(gatewayErrors, "9"))
            ));
      }

      //AddProperty,20,73,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,,associa,associaID1,123 Bad Test Address,123 Bad Test Address,Tiptop,CA,94904,1,3,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-JP Morgan Chase,Checking,490000018,9741,
      //AddProperty,20,74,317343184,be297ejCEn,hovee3uv0ooShoi1Such,,cinc,cincID1,123 Bad Test Address,123 Bad Test Address,Deputy,NV,89105,1,4,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-USAA,Checking,490000018,368108,
      //AddProperty,20,75,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,,collier,collierID1,123 Bad Test Address,123 Bad Test Address,Hepburn,ID,83869,1,1,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Bank Of America,Savings,490000018,272541413958,
      //AddProperty,20,76,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,,fiserv,fiservID1,123 Bad Test Address,123 Bad Test Address,Flag of Regina,DE,19951,1,2,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-San Diego County Credit Union,Savings,490000018,5491511362699,
      //AddProperty,20,77,317343184,be297ejCEn,qua1aiPhul2chohz5aer,,onsite,onsiteID1,123 Bad Test Address,123 Bad Test Address,Deadman Crossing,AR,71857,1,3,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Navy Federal,Savings,490000018,094721976,17343184
      //AddProperty,20,78,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,,tops,topsID1,123 Bad Test Address,123 Bad Test Address,Stovepipe,WY,82114,1,4,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Pacific Marine,Checking,490000018,274,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password, null, pmId),
              "",
              endpointValue
          ).add(
              AddProperty.createValid(
                  "Test case 73 to 78 - Empty Test mode",
                  getExpectedResponse(gatewayErrors, "20"))
          ));

      //AddProperty,21,79,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Production,associa,associaID1,123 Bad Test Address,123 Bad Test Address,Tiptop,CA,94904,1,3,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-JP Morgan Chase,Checking,490000018,9741,
      //AddProperty,21,80,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Production,cinc,cincID1,123 Bad Test Address,123 Bad Test Address,Deputy,NV,89105,1,4,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-USAA,Checking,490000018,368108,
      //AddProperty,21,81,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Production,collier,collierID1,123 Bad Test Address,123 Bad Test Address,Hepburn,ID,83869,1,1,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Bank Of America,Savings,490000018,272541413958,
      //AddProperty,21,82,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Production,fiserv,fiservID1,123 Bad Test Address,123 Bad Test Address,Flag of Regina,DE,19951,1,2,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-San Diego County Credit Union,Savings,490000018,5491511362699,
      //AddProperty,21,83,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Production,onsite,onsiteID1,123 Bad Test Address,123 Bad Test Address,Deadman Crossing,AR,71857,1,3,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Navy Federal,Savings,490000018,094721976,17343184
      //AddProperty,21,84,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Production,tops,topsID1,123 Bad Test Address,123 Bad Test Address,Stovepipe,WY,82114,1,4,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Pacific Marine,Checking,490000018,274,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password, null, pmId),
              "Production",
              endpointValue
          ).add(
              AddProperty.createValid(
                  "Test case 79 to 84 - Production Test mode",
                  getExpectedResponse(gatewayErrors, "21"))
          ));

      //AddProperty,38,85,317343184,badpass,iedahy2ohcie7nieWoo1,Test,associa,associaID1,123 Bad Test Address,123 Bad Test Address,Tiptop,CA,94904,1,3,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-JP Morgan Chase,Checking,490000018,9741,
      //AddProperty,38,86,317343184,badpass,hovee3uv0ooShoi1Such,Test,cinc,cincID1,123 Bad Test Address,123 Bad Test Address,Deputy,NV,89105,1,4,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-USAA,Checking,490000018,368108,
      //AddProperty,38,87,317343184,badpass,rohn3shaiquaesh1xoSu,Test,collier,collierID1,123 Bad Test Address,123 Bad Test Address,Hepburn,ID,83869,1,1,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Bank Of America,Savings,490000018,272541413958,
      //AddProperty,38,88,317343184,badpass,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,fiservID1,123 Bad Test Address,123 Bad Test Address,Flag of Regina,DE,19951,1,2,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-San Diego County Credit Union,Savings,490000018,5491511362699,
      //AddProperty,38,89,317343184,badpass,qua1aiPhul2chohz5aer,Test,onsite,onsiteID1,123 Bad Test Address,123 Bad Test Address,Deadman Crossing,AR,71857,1,3,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Navy Federal,Savings,490000018,094721976,17343184
      //AddProperty,38,90,317343184,badpass,EinaGh1oe4eihu1gohci,Test,tops,topsID1,123 Bad Test Address,123 Bad Test Address,Stovepipe,WY,82114,1,4,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Pacific Marine,Checking,490000018,274,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, "badpass", null, pmId),
              "Test",
              endpointValue
          ).add(
              AddProperty.createValid(
                  "Test case 85 to 90 - Incorrect password",
                  getExpectedResponse(gatewayErrors, "38"))
          ));

      //AddProperty,38,91,3173431,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,associaPropSAVEID01,123 Bad Test Address,123 Bad Test Address,,,,0,3,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-JP Morgan Chase,Checking,490000018,9741,
      //AddProperty,38,92,3173431,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,cincPropSAVEID01,123 Bad Test Address,123 Bad Test Address,,,,0,4,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-USAA,Checking,490000018,368108,
      //AddProperty,38,93,3173431,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,collierPropSAVEID01,123 Bad Test Address,123 Bad Test Address,,,,0,1,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Bank Of America,Savings,490000018,272541413958,
      //AddProperty,38,94,3173431,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,fiservPropSAVEID01,123 Bad Test Address,123 Bad Test Address,,,,0,2,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-San Diego County Credit Union,Savings,490000018,5491511362699,
      //AddProperty,38,95,3173431,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,onsitePropSAVEID01,123 Bad Test Address,123 Bad Test Address,,,,0,3,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Navy Federal,Savings,490000018,094721976,17343184
      //AddProperty,38,96,3173431,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,topsPropSAVEID01,123 Bad Test Address,123 Bad Test Address,,,,0,4,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Pacific Marine,Checking,490000018,274,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials("3173431", username, password, null, pmId),
              "Test",
              endpointValue
          ).add(
              AddProperty.createValid(
                  "Test case 91 to 96 - Invalid user ID",
                  getExpectedResponse(gatewayErrors, "38"))
          ));

      //AddProperty,38,97,317343184, ,iedahy2ohcie7nieWoo1,Test,associa,associaID1,123 Bad Test Address,123 Bad Test Address,Tiptop,CA,94904,1,3,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-JP Morgan Chase,Checking,490000018,9741,
      //AddProperty,38,98,317343184, ,hovee3uv0ooShoi1Such,Test,cinc,cincID1,123 Bad Test Address,123 Bad Test Address,Deputy,NV,89105,1,4,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-USAA,Checking,490000018,368108,
      //AddProperty,38,99,317343184, ,rohn3shaiquaesh1xoSu,Test,collier,collierID1,123 Bad Test Address,123 Bad Test Address,Hepburn,ID,83869,1,1,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Bank Of America,Savings,490000018,272541413958,
      //AddProperty,38,100,317343184, ,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,fiservID1,123 Bad Test Address,123 Bad Test Address,Flag of Regina,DE,19951,1,2,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-San Diego County Credit Union,Savings,490000018,5491511362699,
      //AddProperty,38,101,317343184, ,qua1aiPhul2chohz5aer,Test,onsite,onsiteID1,123 Bad Test Address,123 Bad Test Address,Deadman Crossing,AR,71857,1,3,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Navy Federal,Savings,490000018,094721976,17343184
      //AddProperty,38,102,317343184, ,EinaGh1oe4eihu1gohci,Test,tops,topsID1,123 Bad Test Address,123 Bad Test Address,Stovepipe,WY,82114,1,4,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Pacific Marine,Checking,490000018,274,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, " ", null, pmId),
              "Test",
              endpointValue
          ).add(
              AddProperty.createValid(
                  "Test case 97 to 102 - Blank space as password",
                  getExpectedResponse(gatewayErrors, "38"))
          ));

      //AddProperty,45,103,317343184,,iedahy2ohcie7nieWoo1,Test,associa,associaID1,123 Bad Test Address,123 Bad Test Address,Tiptop,CA,94904,1,3,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-JP Morgan Chase,Checking,490000018,9741,
      //AddProperty,45,104,317343184,,hovee3uv0ooShoi1Such,Test,cinc,cincID1,123 Bad Test Address,123 Bad Test Address,Deputy,NV,89105,1,4,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-USAA,Checking,490000018,368108,
      //AddProperty,45,105,317343184,,rohn3shaiquaesh1xoSu,Test,collier,collierID1,123 Bad Test Address,123 Bad Test Address,Hepburn,ID,83869,1,1,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Bank Of America,Savings,490000018,272541413958,
      //AddProperty,45,106,317343184,,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,fiservID1,123 Bad Test Address,123 Bad Test Address,Flag of Regina,DE,19951,1,2,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-San Diego County Credit Union,Savings,490000018,5491511362699,
      //AddProperty,45,107,317343184,,qua1aiPhul2chohz5aer,Test,onsite,onsiteID1,123 Bad Test Address,123 Bad Test Address,Deadman Crossing,AR,71857,1,3,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Navy Federal,Savings,490000018,094721976,17343184
      //AddProperty,45,108,317343184,,EinaGh1oe4eihu1gohci,Test,tops,topsID1,123 Bad Test Address,123 Bad Test Address,Stovepipe,WY,82114,1,4,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Pacific Marine,Checking,490000018,274,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, "", null, pmId),
              "Test",
              endpointValue
          ).add(
              AddProperty.createValid(
                  "Test case 103 to 108 - Empty password",
                  getExpectedResponse(gatewayErrors, "45"))
          ));

      //AddProperty,50,109,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,associaID01,123 Bad Test Address,123 Bad Test Address,Poysippi,KY,41210,,1,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-JP Morgan Chase,Checking,490000018,9002672278539,
      //AddProperty,50,110,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,cincID01,123 Bad Test Address,123 Bad Test Address,Codette,MA,02590,,2,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-USAA,Checking,490000018,947789084603572,
      //AddProperty,50,111,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,collierID01,123 Bad Test Address,123 Bad Test Address,Galilee,MD,36175,,3,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Bank Of America,Checking,490000018,513649,
      //AddProperty,50,112,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,fiservID01,123 Bad Test Address,123 Bad Test Address,Miami Beach,OH,45993,,4,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-San Diego County Credit Union,Checking,490000018,6215855,
      //AddProperty,50,113,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,onsiteID01,123 Bad Test Address,123 Bad Test Address,Clover,MA,01611,,1,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Navy Federal,Savings,490000018,4051852699625,17343184
      //AddProperty,50,114,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,topsID01,123 Bad Test Address,123 Bad Test Address,Black Wolf,ME,04485,,2,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Pacific Marine,Checking,490000018,4896160,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password, null, pmId),
              "Test",
              endpointValue
          ).add(
              AddProperty.createValid(
                  "Test case 109 to 114 - Empty unit count",
                  getExpectedResponse(gatewayErrors, "50"))
                  .setUnitCount("")
          ));

      //AddProperty,50,115,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,associaID01,123 Bad Test Address,123 Bad Test Address,Poysippi,KY,41210,-1,1,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-JP Morgan Chase,Checking,490000018,9002672278539,
      //AddProperty,50,116,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,cincID01,123 Bad Test Address,123 Bad Test Address,Codette,MA,02590,-1,2,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-USAA,Checking,490000018,947789084603572,
      //AddProperty,50,117,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,collierID01,123 Bad Test Address,123 Bad Test Address,Galilee,MD,36175,-1,3,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Bank Of America,Checking,490000018,513649,
      //AddProperty,50,118,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,fiservID01,123 Bad Test Address,123 Bad Test Address,Miami Beach,OH,45993,-1,4,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-San Diego County Credit Union,Checking,490000018,6215855,
      //AddProperty,50,119,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,onsiteID01,123 Bad Test Address,123 Bad Test Address,Clover,MA,01611,-1,1,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Navy Federal,Savings,490000018,4051852699625,17343184
      //AddProperty,50,120,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,topsID01,123 Bad Test Address,123 Bad Test Address,Black Wolf,ME,04485,-1,2,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Pacific Marine,Checking,490000018,4896160,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password, null, pmId),
              "Test",
              endpointValue
          ).add(
              AddProperty.createValid(
                  "Test case 115 to 120 - Negative unit count",
                  getExpectedResponse(gatewayErrors, "50"))
                  .setUnitCount("-1")
          ));

      String[] badRouting = {
          "", "1", "4900 00018",
      };
      for (String routing : badRouting) {
        //AddProperty,77,121,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,associaID0,123 Bad Test Address,123 Bad Test Address,Tiptop,CA,94904,0,3,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-JP Morgan Chase,Checking,,9741,
        //AddProperty,77,122,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,cincID0,123 Bad Test Address,123 Bad Test Address,Deputy,NV,89105,0,4,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-USAA,Checking,,368108,
        //AddProperty,77,123,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,collierID0,123 Bad Test Address,123 Bad Test Address,Hepburn,ID,83869,0,1,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Bank Of America,Savings,,272541413958,
        //AddProperty,77,124,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,fiservID0,123 Bad Test Address,123 Bad Test Address,Flag of Regina,DE,19951,0,2,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-San Diego County Credit Union,Savings,,5491511362699,
        //AddProperty,77,125,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,onsiteID0,123 Bad Test Address,123 Bad Test Address,Deadman Crossing,AR,71857,0,3,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Navy Federal,Savings,,094721976,17343184
        //AddProperty,77,126,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,topsID0,123 Bad Test Address,123 Bad Test Address,Stovepipe,WY,68208,0,4,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Pacific Marine,Checking,,274,17343184
        //AddProperty,77,127,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,associaID0,123 Bad Test Address,123 Bad Test Address,Tiptop,CA,94904,0,3,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-JP Morgan Chase,Checking,1,9741,
        //AddProperty,77,128,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,cincID0,123 Bad Test Address,123 Bad Test Address,Deputy,NV,89105,0,4,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-USAA,Checking,1,368108,
        //AddProperty,77,129,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,collierID0,123 Bad Test Address,123 Bad Test Address,Hepburn,ID,83869,0,1,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Bank Of America,Savings,1,272541413958,
        //AddProperty,77,130,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,fiservID0,123 Bad Test Address,123 Bad Test Address,Flag of Regina,DE,19951,0,2,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-San Diego County Credit Union,Savings,1,5491511362699,
        //AddProperty,77,131,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,onsiteID0,123 Bad Test Address,123 Bad Test Address,Deadman Crossing,AR,71857,0,3,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Navy Federal,Savings,1,094721976,17343184
        //AddProperty,77,132,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,topsID0,123 Bad Test Address,123 Bad Test Address,Stovepipe,WY,68208,0,4,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Pacific Marine,Checking,1,274,17343184
        //AddProperty,77,133,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,associaID0,123 Bad Test Address,123 Bad Test Address,Tiptop,CA,94904,0,3,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-JP Morgan Chase,Checking,4900 00018,9741,
        //AddProperty,77,134,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,cincID0,123 Bad Test Address,123 Bad Test Address,Deputy,NV,89105,0,4,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-USAA,Checking,4900 00018,368108,
        //AddProperty,77,135,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,collierID0,123 Bad Test Address,123 Bad Test Address,Hepburn,ID,83869,0,1,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Bank Of America,Savings,4900 00018,272541413958,
        //AddProperty,77,136,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,fiservID0,123 Bad Test Address,123 Bad Test Address,Flag of Regina,DE,19951,0,2,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-San Diego County Credit Union,Savings,4900 00018,5491511362699,
        //AddProperty,77,137,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,onsiteID0,123 Bad Test Address,123 Bad Test Address,Deadman Crossing,AR,71857,0,3,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Navy Federal,Savings,4900 00018,094721976,17343184
        //AddProperty,77,138,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,topsID0,123 Bad Test Address,123 Bad Test Address,Stovepipe,WY,68208,0,4,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Pacific Marine,Checking,4900 00018,274,17343184
        testCases.add(
            new AapiTestCaseCollection(
                new Credentials(userId, username, password, null, pmId),
                "Test",
                endpointValue
            ).add(
                AddProperty.createValid(
                    "Test case 121 to 126 - Bad routing number '" + routing + "'",
                    getExpectedResponse(gatewayErrors, "77"))
                    .setBankAccountRouting(routing)
            ));
      }

      //AddProperty,1,139,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,associaID0,123 Bad Test Address,123 Bad Test Address,Tiptop,CA,94904,0,3,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-JP Morgan Chase,Checking,490000018 ,9741,
      //AddProperty,1,140,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,cincID0,123 Bad Test Address,123 Bad Test Address,Deputy,NV,89105,0,4,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-USAA,Checking,490000018 ,368108,
      //AddProperty,1,141,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,collierID0,123 Bad Test Address,123 Bad Test Address,Hepburn,ID,83869,0,1,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Bank Of America,Savings,490000018 ,272541413958,
      //AddProperty,1,142,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,fiservID0,123 Bad Test Address,123 Bad Test Address,Flag of Regina,DE,19951,0,2,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-San Diego County Credit Union,Savings,490000018 ,5491511362699,
      //AddProperty,1,143,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,onsiteID0,123 Bad Test Address,123 Bad Test Address,Deadman Crossing,AR,71857,0,3,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Navy Federal,Savings,490000018 ,094721976,17343184
      //AddProperty,1,144,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,topsID0,123 Bad Test Address,123 Bad Test Address,Stovepipe,WY,68208,0,4,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Pacific Marine,Checking,490000018 ,274,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password, null, pmId),
              "Test",
              endpointValue
          ).add(
              AddProperty.createValid(
                  "Test case 139 to 144 - routing number with trailing space",
                  getExpectedResponse(gatewayErrors, "1"))
                  .setBankAccountRouting("490000018 ")
          ));

      //AddProperty,1,145,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,associaID0,123 Bad Test Address,123 Bad Test Address,Tiptop,CA,94904,0,3,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-JP Morgan Chase,Checking, 490000018 ,9741,
      //AddProperty,1,146,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,cincID0,123 Bad Test Address,123 Bad Test Address,Deputy,NV,89105,0,4,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-USAA,Checking, 490000018 ,368108,
      //AddProperty,1,147,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,collierID0,123 Bad Test Address,123 Bad Test Address,Hepburn,ID,83869,0,1,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Bank Of America,Savings, 490000018 ,272541413958,
      //AddProperty,1,148,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,fiservID0,123 Bad Test Address,123 Bad Test Address,Flag of Regina,DE,19951,0,2,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-San Diego County Credit Union,Savings, 490000018 ,5491511362699,
      //AddProperty,1,149,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,onsiteID0,123 Bad Test Address,123 Bad Test Address,Deadman Crossing,AR,71857,0,3,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Navy Federal,Savings, 490000018 ,094721976,17343184
      //AddProperty,1,150,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,topsID0,123 Bad Test Address,123 Bad Test Address,Stovepipe,WY,68208,0,4,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Pacific Marine,Checking, 490000018 ,274,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password, null, pmId),
              "Test",
              endpointValue
          ).add(
              AddProperty.createValid(
                  "Test case 145 to 150 - routing number with leading and trailing space",
                  getExpectedResponse(gatewayErrors, "1"))
                  .setBankAccountRouting(" 490000018 ")
          ));

      //AddProperty,78,157,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,associaID0,123 Bad Test Address,123 Bad Test Address,Tiptop,CA,94904,0,3,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-JP Morgan Chase,Checking,490000018, ,
      //AddProperty,78,158,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,cincID0,123 Bad Test Address,123 Bad Test Address,Deputy,NV,89105,0,4,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-USAA,Checking,490000018, ,
      //AddProperty,78,159,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,collierID0,123 Bad Test Address,123 Bad Test Address,Hepburn,ID,83869,0,1,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Bank Of America,Savings,490000018, ,
      //AddProperty,78,160,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,fiservID0,123 Bad Test Address,123 Bad Test Address,Flag of Regina,DE,19951,0,2,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-San Diego County Credit Union,Savings,490000018, ,
      //AddProperty,78,161,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,onsiteID0,123 Bad Test Address,123 Bad Test Address,Deadman Crossing,AR,71857,0,3,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Navy Federal,Savings,490000018, ,17343184
      //AddProperty,78,162,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,topsID0,123 Bad Test Address,123 Bad Test Address,Stovepipe,WY,68208,0,4,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Pacific Marine,Checking,490000018, ,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password, null, pmId),
              "Test",
              endpointValue
          ).add(
              AddProperty.createValid(
                  "Test case 157 to 162 - Blank space account number",
                  getExpectedResponse(gatewayErrors, "78"))
                  .setBankAccountNumber(" ")
          ));

      //AddProperty,78,163,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,associaID0,123 Bad Test Address,123 Bad Test Address,Tiptop,CA,94904,0,3,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-JP Morgan Chase,Checking,490000018,,
      //AddProperty,78,164,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,cincID0,123 Bad Test Address,123 Bad Test Address,Deputy,NV,89105,0,4,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-USAA,Checking,490000018,,
      //AddProperty,78,165,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,collierID0,123 Bad Test Address,123 Bad Test Address,Hepburn,ID,83869,0,1,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Bank Of America,Savings,490000018,,
      //AddProperty,78,166,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,fiservID0,123 Bad Test Address,123 Bad Test Address,Flag of Regina,DE,19951,0,2,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-San Diego County Credit Union,Savings,490000018,,
      //AddProperty,78,167,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,onsiteID0,123 Bad Test Address,123 Bad Test Address,Deadman Crossing,AR,71857,0,3,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Navy Federal,Savings,490000018,,17343184
      //AddProperty,78,168,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,topsID0,123 Bad Test Address,123 Bad Test Address,Stovepipe,WY,68208,0,4,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Pacific Marine,Checking,490000018,,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password, null, pmId),
              "Test",
              endpointValue
          ).add(
              AddProperty.createValid(
                  "Test case 163 to 168 - Empty account number",
                  getExpectedResponse(gatewayErrors, "78"))
                  .setBankAccountNumber("")
          ));
    }

    //AddProperty,78,151,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,associaID0,123 Bad Test Address,123 Bad Test Address,Tiptop,CA,94904,0,3,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-JP Morgan Chase,Checking,490000018,97 41,
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password, null, pmId),
            "Test",
            "associa"
        ).add(
            AddProperty.createValid(
                "Test case 151 - Split digit account number",
                getExpectedResponse(gatewayErrors, "78"))
                .setBankAccountNumber("97 41")
        ));

    //AddProperty,78,152,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,cincID0,123 Bad Test Address,123 Bad Test Address,Deputy,NV,89105,0,4,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-USAA,Checking,490000018,368 108,
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password, null, pmId),
            "Test",
            "cinc"
        ).add(
            AddProperty.createValid(
                "Test case 152 - Split digit account number",
                getExpectedResponse(gatewayErrors, "78"))
                .setBankAccountNumber("368 108")
        ));

    //AddProperty,78,153,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,collierID0,123 Bad Test Address,123 Bad Test Address,Hepburn,ID,83869,0,1,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Bank Of America,Savings,490000018,272541 413958,
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password, null, pmId),
            "Test",
            "collier"
        ).add(
            AddProperty.createValid(
                "Test case 153 - Split digit account number",
                getExpectedResponse(gatewayErrors, "78"))
                .setBankAccountNumber("272541 413958")
        ));

    //AddProperty,78,154,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,fiservID0,123 Bad Test Address,123 Bad Test Address,Flag of Regina,DE,19951,0,2,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-San Diego County Credit Union,Savings,490000018,54915 11362699,
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password, null, pmId),
            "Test",
            "fiserv"
        ).add(
            AddProperty.createValid(
                "Test case 154 - Split digit account number",
                getExpectedResponse(gatewayErrors, "78"))
                .setBankAccountNumber("54915 11362699")
        ));

    //AddProperty,78,155,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,onsiteID0,123 Bad Test Address,123 Bad Test Address,Deadman Crossing,AR,71857,0,3,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Navy Federal,Savings,490000018,0947 21976,17343184
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password, null, pmId),
            "Test",
            "onsite"
        ).add(
            AddProperty.createValid(
                "Test case 155 - Split digit account number",
                getExpectedResponse(gatewayErrors, "78"))
                .setBankAccountNumber("0947 21976")
        ));

    //AddProperty,78,156,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,topsID0,123 Bad Test Address,123 Bad Test Address,Stovepipe,WY,68208,0,4,test@test.com,https://paylease.com/image.png,760-711-1111,Lease Payment,lease_payment,API-Pacific Marine,Checking,490000018,27 4,17343184
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password, null, pmId),
            "Test",
            "tops"
        ).add(
            AddProperty.createValid(
                "Test case 156 - Split digit account number",
                getExpectedResponse(gatewayErrors, "78"))
                .setBankAccountNumber("27 4")
        ));

    executeTests(testCases);
  }
}

