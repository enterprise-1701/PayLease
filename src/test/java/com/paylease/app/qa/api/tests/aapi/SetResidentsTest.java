package com.paylease.app.qa.api.tests.aapi;

import com.paylease.app.qa.api.tests.TestCaseCollection;
import com.paylease.app.qa.api.tests.aapi.testcase.SetResidents;
import com.paylease.app.qa.framework.DataHelper;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.api.Credentials;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.testng.annotations.Test;


public class SetResidentsTest extends BaseTest {

  private static final String REGION = "aapi";
  private static final String FEATURE = "SetResidents";

  //-----------------------------------TESTS--------------------------------------------------------

  @Test(groups = {"aapi", "SetResidents"})
  public void setResidents() {
    Logger.info(" Set ResidentsTest");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();
    final String userId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String pmId = testSetupPage.getString("pmId");
    final String propertyId = testSetupPage.getString("propNumber");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    ArrayList<TestCaseCollection> testCases = new ArrayList<>();

    //SetResidents,1,3,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,BlahTester01,NULL,123QAgapi,Blah,Tester,123 GAPI Lane,NULL,Testland,CA,36958,NULL,NULL,NULL,0,FALSE,TRUE,,,,
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password, null, pmId),
            "Test",
            "associa"
        ).add(
            new SetResidents(
                "Test case 3 - associa - null phone, email",
                getExpectedResponse(gatewayErrors, "1"))
                .setResidentId("BlahTester01")
                .setPropertyId(propertyId)
                .setFirstName("Blah")
                .setLastName("Tester")
                .setStreetAddress("123 GAPI Lane")
                .setCity("TestLand")
                .setState("CA")
                .setPostalCode("36958")
                .setAmount("0")
                .setHold("FALSE")
                .setGenerateRegistrationUrl("TRUE")
        ));

    //SetResidents,1,4,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,BlahTester03,NULL,123QAgapi,Blah,Tester,123 GAPI Lane,NULL,Testland,CA,36958,2222222222,3333333333,test@test.com,0,FALSE,TRUE,,,,
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password, null, pmId),
            "Test",
            "associa"
        ).add(
            new SetResidents(
                "Test case 4 - associa - phone, email",
                getExpectedResponse(gatewayErrors, "1"))
                .setResidentId("BlahTester03")
                .setPropertyId(propertyId)
                .setFirstName("Blah")
                .setLastName("Tester")
                .setStreetAddress("123 GAPI Lane")
                .setCity("TestLand")
                .setState("CA")
                .setPostalCode("36958")
                .setPhone("2222222222")
                .setAlternatePhone("3333333333")
                .setEmail("test@paylease.com")
                .setAmount("0")
                .setHold("FALSE")
                .setGenerateRegistrationUrl("TRUE")
        ));

    final String[] endpointValues = {
        "associa", "cinc", "collier", "fiserv",
    };

    for (String endpointValue : endpointValues) {
      //SetResidents,1,5,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,,,123QAgapi,,,,,,,,,,,,,,,,,
      //SetResidents,1,8,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,,,123QAgapi,,,,,,,,,,,,,,,,,
      //SetResidents,1,11,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,,,123QAgapi,,,,,,,,,,,,,,,,,
      //SetResidents,1,14,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,,,123QAgapi,,,,,,,,,,,,,,,,,
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password, null, pmId),
              "Test",
              endpointValue
          ).add(
              SetResidents.createAllEmpty(
                  "Test case 5/8/11/14  - property Id only",
                  getExpectedResponse(gatewayErrors, "1"))
                  .setPropertyId(propertyId)
          ));
    }

    final String[] endpoints = {
        "cinc", "collier", "fiserv",
    };

    final String[] holdValues = {
        "TRUE", "FALSE",
    };

    for (String endpoint : endpoints) {
      for (String holdValue : holdValues) {
        String amount = "";
        if (endpoint.equals("cinc")) {
          amount = "0.0";
        } else if (endpoint.equals("collier")) {
          amount = "0.00";
        } else if (endpoint.equals("fiserv")) {
          amount = "1";
        }
        //SetResidents,1,6,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,apiQAtestRes2HOLD,,123QAgapi,SetResidents,Two,123 GAPI Lane,,Testland,CA,36958,,,,0.0,TRUE,,,,,
        //SetResidents,1,7,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,apiQAtestRes2,,123QAgapi,SetResidents,Two,123 GAPI Lane,,Testland,CA,36958,,,,0.0,FALSE,,,,,

        //SetResidents,1,9,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,apiQAtestRes3HOLD,,123QAgapi,SetResidents,Three,123 GAPI Lane,,Testland,CA,36958,,,,0.00,TRUE,,,,,
        //SetResidents,1,10,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,apiQAtestRes3,,123QAgapi,SetResidents,Three,123 GAPI Lane,,Testland,CA,36958,,,,0.00,FALSE,,,,,

        //SetResidents,1,12,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,apiQAtestRes4HOLD,,123QAgapi,SetResidents,Four,123 GAPI Lane,,Testland,CA,36958,,,,.1,TRUE,,,,,
        //SetResidents,1,13,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,apiQAtestRes4,,123QAgapi,SetResidents,Four,123 GAPI Lane,,Testland,CA,36958,,,,.1,FALSE,,,,,
        testCases.add(
            new AapiTestCaseCollection(
                new Credentials(userId, username, password, null, pmId),
                "Test",
                endpoint
            ).add(
                new SetResidents(
                    "Test case 6/7/9/10/12/13  - different hold values",
                    getExpectedResponse(gatewayErrors, "1"))
                    .setResidentId("apiQAtestRes2HOLD")
                    .setSecondaryResidentId("")
                    .setPropertyId(propertyId)
                    .setFirstName("SetResidents")
                    .setLastName("Two")
                    .setStreetAddress("123 GAPI Lane")
                    .setUnit("")
                    .setCity("Testland")
                    .setState("CA")
                    .setPostalCode("36958")
                    .setPhone("")
                    .setAlternatePhone("")
                    .setEmail("")
                    .setAmount(amount)
                    .setHold(holdValue)
                    .setGenerateRegistrationUrl("")
            ));
      }
    }

    final String[] twoEndpoints = {
        "onsite", "tops",
    };

    for (String endpoint : twoEndpoints) {
      for (String holdValue : holdValues) {
        //SetResidents,55,15,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,apiQAtestRes5HOLD,,123QAgapi,SetResidents,Five,123 GAPI Lane,,Testland,CA,36958,,,,.11,TRUE,,317343184,,,1
        //SetResidents,55,16,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,apiQAtestRes5,,123QAgapi,SetResidents,Five,123 GAPI Lane,,Testland,CA,36958,,,,.11,FALSE,,317343184,,,1

        //SetResidents,55,18,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,apiQAtestRes6HOLD,,123QAgapi,SetResidents,Williams,123 GAPI Lane,,Testland,CA,36958,,,,0.11,FALSE,,317343184,,,1
        //SetResidents,55,19,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,apiQAtestRes6,,123QAgapi,SetResidents,Williams,123 GAPI Lane,,Testland,CA,36958,,,,0.11,TRUE,,317343184,,,1
        testCases.add(
            new AapiTestCaseCollection(
                new Credentials(userId, username, password, null, "317343184"),
                "Test",
                endpoint
            ).add(
                SetResidents.createValid(
                    "Test case 15/16/18/19 - invalid partner pm id - with resident data",
                    getExpectedResponse(gatewayErrors, "55"))
                    .setSecondaryResidentId("")
                    .setPropertyId(propertyId)
                    .setHold(holdValue)
                    .setGenerateRegistrationUrl("")
            ));
      }

      //SetResidents,55,17,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,,,123QAgapi,,,,,,,,,,,,,,317343184,,,1
      //SetResidents,55,20,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,,,123QAgapi,,,,,,,,,,,,,,317343184,,,1
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password, null, "317343184"),
              "Test",
              endpoint
          ).add(
              SetResidents.createAllEmpty(
                  "Test case 17/20 - invalid partner pm id - no resident data",
                  getExpectedResponse(gatewayErrors, "55"))
                  .setPropertyId(propertyId)
          ));
    }

    final String[] allEndpointValues = {
        "associa", "cinc", "collier", "fiserv", "onsite", "tops"
    };

    for (String endpoint : allEndpointValues) {
      for (String holdValue : holdValues) {
        //SetResidents,8,21,317343184,be297ejCEn,,Test,associa,apiTestAssocia,,associaPropSAVEID01,Kenneth,Gonzalez,3514 Tawny Mews,,Shiawasseetown,AR,72089,,,,0,FALSE,,,,,
        //SetResidents,8,23,317343184,be297ejCEn,,Test,cinc,apiTestCinc,,cincPropSAVEID01,Aaron,Sanders,4658 Iron Pond Vista,,Monumental,IA,52897,,,,0.0,FALSE,,,,,
        //SetResidents,8,25,317343184,be297ejCEn,,Test,collier,apiTestCollier,,collierPropSAVEID01,Cheryl,Russell,4580 Velvet Horse Edge,,Do Stop,AZ,86473,,,,0.00,FALSE,,,,,
        //SetResidents,8,27,317343184,be297ejCEn,,Test,fiserv,apiTestFiserv,,fiservPropSAVEID01,Paula,Rivera,8504 Harvest Apple Round,,Turnip Hole,AR,72228,,,,.1,FALSE,,,,,
        //SetResidents,8,29,317343184,be297ejCEn,,Test,onsite,apiTestOnsite,,onsitePropSAVEID01,Joshua,Bryant,3751 Emerald Impasse,,Nenahnezad,NM,88408,,,,.11,FALSE,,317343184,,,17343184
        //SetResidents,8,31,317343184,be297ejCEn,,Test,tops,apiTestTops,,topsPropSAVEID01,Bonnie,Williams,8701 Sunny Rise Boulevard,,Windthorst,VT,5103,,,,0.11,FALSE,,317343184,,,17343184

        //SetResidents,8,33,317343184,be297ejCEn,,Test,associa,apiTestHOLDAssocia,,associaPropSAVEID01,Kenneth,Gonzalez,3514 Tawny Mews,,Shiawasseetown,AR,72089,,,,0,TRUE,,,,,
        //SetResidents,8,34,317343184,be297ejCEn,,Test,cinc,apiTestHOLDCinc,,cincPropSAVEID01,Aaron,Sanders,4658 Iron Pond Vista,,Monumental,IA,52897,,,,0.0,TRUE,,,,,
        //SetResidents,8,35,317343184,be297ejCEn,,Test,collier,apiTestHOLDCollier,,collierPropSAVEID01,Cheryl,Russell,4580 Velvet Horse Edge,,Do Stop,AZ,86473,,,,0.00,TRUE,,,,,
        //SetResidents,8,36,317343184,be297ejCEn,,Test,fiserv,apiTestHOLDFiserv,,fiservPropSAVEID01,Paula,Rivera,8504 Harvest Apple Round,,Turnip Hole,AR,72228,,,,.1,TRUE,,,,,
        //SetResidents,8,37,317343184,be297ejCEn,,Test,onsite,apiTestHOLDOnsite,,onsitePropSAVEID01,Joshua,Bryant,3751 Emerald Impasse,,Nenahnezad,NM,88408,,,,.11,TRUE,,317343184,,,17343184
        //SetResidents,8,38,317343184,be297ejCEn,,Test,tops,apiTestHOLDTops,,topsPropSAVEID01,Bonnie,Williams,8701 Sunny Rise Boulevard,,Windthorst,VT,5103,,,,0.11,TRUE,,317343184,,,17343184
        testCases.add(
            new AapiTestCaseCollection(
                new Credentials(userId, username, password, "", pmId),
                "Test",
                endpoint
            ).add(
                SetResidents.createValid(
                    "Test case 21/23/25/27/29/31/33 to 38  - empty api key",
                    getExpectedResponse(gatewayErrors, "8"))
                    .setPropertyId(propertyId)
                    .setHold(holdValue)
                    .setGenerateRegistrationUrl("")
            ));

        //SetResidents,9,39,317343184,be297ejCEn,badapi,Test,associa,apiTestAssocia,,associaPropSAVEID01,Kenneth,Gonzalez,3514 Tawny Mews,,Shiawasseetown,AR,72089,,,,0,FALSE,,,,,
        //SetResidents,9,41,317343184,be297ejCEn,badapi,Test,cinc,apiTestCinc,,cincPropSAVEID01,Aaron,Sanders,4658 Iron Pond Vista,,Monumental,IA,52897,,,,0.0,FALSE,,,,,
        //SetResidents,9,43,317343184,be297ejCEn,badapi,Test,collier,apiTestCollier,,collierPropSAVEID01,Cheryl,Russell,4580 Velvet Horse Edge,,Do Stop,AZ,86473,,,,0.00,FALSE,,,,,
        //SetResidents,9,45,317343184,be297ejCEn,badapi,Test,fiserv,apiTestFiserv,,fiservPropSAVEID01,Paula,Rivera,8504 Harvest Apple Round,,Turnip Hole,AR,72228,,,,.1,FALSE,,,,,
        //SetResidents,9,47,317343184,be297ejCEn,badapi,Test,onsite,apiTestOnsite,,onsitePropSAVEID01,Joshua,Bryant,3751 Emerald Impasse,,Nenahnezad,NM,88408,,,,.11,FALSE,,317343184,,,17343184
        //SetResidents,9,49,317343184,be297ejCEn,badapi,Test,tops,apiTestTops,,topsPropSAVEID01,Bonnie,Williams,8701 Sunny Rise Boulevard,,Windthorst,VT,5103,,,,0.11,FALSE,,317343184,,,17343184

        //SetResidents,9,51,317343184,be297ejCEn,badapi,Test,associa,apiTestHOLDAssocia,,associaPropSAVEID01,Kenneth,Gonzalez,3514 Tawny Mews,,Shiawasseetown,AR,72089,,,,0,TRUE,,,,,
        //SetResidents,9,52,317343184,be297ejCEn,badapi,Test,cinc,apiTestHOLDCinc,,cincPropSAVEID01,Aaron,Sanders,4658 Iron Pond Vista,,Monumental,IA,52897,,,,0.0,TRUE,,,,,
        //SetResidents,9,53,317343184,be297ejCEn,badapi,Test,collier,apiTestHOLDCollier,,collierPropSAVEID01,Cheryl,Russell,4580 Velvet Horse Edge,,Do Stop,AZ,86473,,,,0.00,TRUE,,,,,
        //SetResidents,9,54,317343184,be297ejCEn,badapi,Test,fiserv,apiTestHOLDFiserv,,fiservPropSAVEID01,Paula,Rivera,8504 Harvest Apple Round,,Turnip Hole,AR,72228,,,,.1,TRUE,,,,,
        //SetResidents,9,55,317343184,be297ejCEn,badapi,Test,onsite,apiTestHOLDOnsite,,onsitePropSAVEID01,Joshua,Bryant,3751 Emerald Impasse,,Nenahnezad,NM,88408,,,,.11,TRUE,,317343184,,,17343184
        //SetResidents,9,56,317343184,be297ejCEn,badapi,Test,tops,apiTestHOLDTops,,topsPropSAVEID01,Bonnie,Williams,8701 Sunny Rise Boulevard,,Windthorst,VT,5103,,,,0.11,TRUE,,317343184,,,17343184
        testCases.add(
            new AapiTestCaseCollection(
                new Credentials(userId, username, password, "badapi", pmId),
                "Test",
                endpoint
            ).add(
                SetResidents.createValid(
                    "Test case 39/41/43/45/47/49/51 to 56  - bad api key",
                    getExpectedResponse(gatewayErrors, "9"))
                    .setPropertyId(propertyId)
                    .setHold(holdValue)
                    .setGenerateRegistrationUrl("")
            ));

        //SetResidents,20,57,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,,associa,apiTestAssocia,,associaPropSAVEID01,Kenneth,Gonzalez,3514 Tawny Mews,,Shiawasseetown,AR,72089,,,,0,FALSE,,,,,
        //SetResidents,20,59,317343184,be297ejCEn,hovee3uv0ooShoi1Such,,cinc,apiTestCinc,,cincPropSAVEID01,Aaron,Sanders,4658 Iron Pond Vista,,Monumental,IA,52897,,,,0.0,FALSE,,,,,
        //SetResidents,20,61,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,,collier,apiTestCollier,,collierPropSAVEID01,Cheryl,Russell,4580 Velvet Horse Edge,,Do Stop,AZ,86473,,,,0.00,FALSE,,,,,
        //SetResidents,20,63,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,,fiserv,apiTestFiserv,,fiservPropSAVEID01,Paula,Rivera,8504 Harvest Apple Round,,Turnip Hole,AR,72228,,,,.1,FALSE,,,,,
        //SetResidents,20,65,317343184,be297ejCEn,qua1aiPhul2chohz5aer,,onsite,apiTestOnsite,,onsitePropSAVEID01,Joshua,Bryant,3751 Emerald Impasse,,Nenahnezad,NM,88408,,,,.11,FALSE,,317343184,,,17343184
        //SetResidents,20,67,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,,tops,apiTestTops,,topsPropSAVEID01,Bonnie,Williams,8701 Sunny Rise Boulevard,,Windthorst,VT,5103,,,,0.11,FALSE,,317343184,,,17343184

        //SetResidents,20,69,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,,associa,apiTestHOLDAssocia,,associaPropSAVEID01,Kenneth,Gonzalez,3514 Tawny Mews,,Shiawasseetown,AR,72089,,,,0,TRUE,,,,,
        //SetResidents,20,70,317343184,be297ejCEn,hovee3uv0ooShoi1Such,,cinc,apiTestHOLDCinc,,cincPropSAVEID01,Aaron,Sanders,4658 Iron Pond Vista,,Monumental,IA,52897,,,,0.0,TRUE,,,,,
        //SetResidents,20,71,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,,collier,apiTestHOLDCollier,,collierPropSAVEID01,Cheryl,Russell,4580 Velvet Horse Edge,,Do Stop,AZ,86473,,,,0.00,TRUE,,,,,
        //SetResidents,20,72,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,,fiserv,apiTestHOLDFiserv,,fiservPropSAVEID01,Paula,Rivera,8504 Harvest Apple Round,,Turnip Hole,AR,72228,,,,.1,TRUE,,,,,
        //SetResidents,20,73,317343184,be297ejCEn,qua1aiPhul2chohz5aer,,onsite,apiTestHOLDOnsite,,onsitePropSAVEID01,Joshua,Bryant,3751 Emerald Impasse,,Nenahnezad,NM,88408,,,,.11,TRUE,,317343184,,,17343184
        //SetResidents,20,74,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,,tops,apiTestHOLDTops,,topsPropSAVEID01,Bonnie,Williams,8701 Sunny Rise Boulevard,,Windthorst,VT,5103,,,,0.11,TRUE,,317343184,,,17343184
        testCases.add(
            new AapiTestCaseCollection(
                new Credentials(userId, username, password, null, pmId),
                "",
                endpoint
            ).add(
                SetResidents.createValid(
                    "Test case 57/59/61/63/65/67/69 to 74  - empty mode",
                    getExpectedResponse(gatewayErrors, "20"))
                    .setPropertyId(propertyId)
                    .setHold(holdValue)
                    .setGenerateRegistrationUrl("")
            ));

        //SetResidents,38,75,317343184,badpass,iedahy2ohcie7nieWoo1,Test,associa,apiTestAssocia,,associaPropSAVEID01,Kenneth,Gonzalez,3514 Tawny Mews,,Shiawasseetown,AR,72089,,,,0,FALSE,,,,,
        //SetResidents,38,77,317343184,badpass,hovee3uv0ooShoi1Such,Test,cinc,apiTestCinc,,cincPropSAVEID01,Aaron,Sanders,4658 Iron Pond Vista,,Monumental,IA,52897,,,,0.0,FALSE,,,,,
        //SetResidents,38,79,317343184,badpass,rohn3shaiquaesh1xoSu,Test,collier,apiTestCollier,,collierPropSAVEID01,Cheryl,Russell,4580 Velvet Horse Edge,,Do Stop,AZ,86473,,,,0.00,FALSE,,,,,
        //SetResidents,38,81,317343184,badpass,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,apiTestFiserv,,fiservPropSAVEID01,Paula,Rivera,8504 Harvest Apple Round,,Turnip Hole,AR,72228,,,,.1,FALSE,,,,,
        //SetResidents,38,83,317343184,badpass,qua1aiPhul2chohz5aer,Test,onsite,apiTestOnsite,,onsitePropSAVEID01,Joshua,Bryant,3751 Emerald Impasse,,Nenahnezad,NM,88408,,,,.11,FALSE,,317343184,,,17343184
        //SetResidents,38,85,317343184,badpass,EinaGh1oe4eihu1gohci,Test,tops,apiTestTops,,topsPropSAVEID01,Bonnie,Williams,8701 Sunny Rise Boulevard,,Windthorst,VT,5103,,,,0.11,FALSE,,317343184,,,17343184

        //SetResidents,38,87,317343184,badpass,iedahy2ohcie7nieWoo1,Test,associa,apiTestHOLDAssocia,,associaPropSAVEID01,Kenneth,Gonzalez,3514 Tawny Mews,,Shiawasseetown,AR,72089,,,,0,TRUE,,,,,
        //SetResidents,38,88,317343184,badpass,hovee3uv0ooShoi1Such,Test,cinc,apiTestHOLDCinc,,cincPropSAVEID01,Aaron,Sanders,4658 Iron Pond Vista,,Monumental,IA,52897,,,,0.0,TRUE,,,,,
        //SetResidents,38,89,317343184,badpass,rohn3shaiquaesh1xoSu,Test,collier,apiTestHOLDCollier,,collierPropSAVEID01,Cheryl,Russell,4580 Velvet Horse Edge,,Do Stop,AZ,86473,,,,0.00,TRUE,,,,,
        //SetResidents,38,90,317343184,badpass,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,apiTestHOLDFiserv,,fiservPropSAVEID01,Paula,Rivera,8504 Harvest Apple Round,,Turnip Hole,AR,72228,,,,.1,TRUE,,,,,
        //SetResidents,38,91,317343184,badpass,qua1aiPhul2chohz5aer,Test,onsite,apiTestHOLDOnsite,,onsitePropSAVEID01,Joshua,Bryant,3751 Emerald Impasse,,Nenahnezad,NM,88408,,,,.11,TRUE,,317343184,,,17343184
        //SetResidents,38,92,317343184,badpass,EinaGh1oe4eihu1gohci,Test,tops,apiTestHOLDTops,,topsPropSAVEID01,Bonnie,Williams,8701 Sunny Rise Boulevard,,Windthorst,VT,5103,,,,0.11,TRUE,,317343184,,,17343184
        testCases.add(
            new AapiTestCaseCollection(
                new Credentials(userId, username, "badpass", null, pmId),
                "Test",
                endpoint
            ).add(
                SetResidents.createValid(
                    "Test case 75/77/79/81/83/85/87 to 92  - bad password",
                    getExpectedResponse(gatewayErrors, "38"))
                    .setPropertyId(propertyId)
                    .setHold(holdValue)
                    .setGenerateRegistrationUrl("")
            ));

        //SetResidents,38,93,31734,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,apiTestAssocia,,associaPropSAVEID01,Kenneth,Gonzalez,3514 Tawny Mews,,Shiawasseetown,AR,72089,,,,0,FALSE,,,,,
        //SetResidents,38,95,31734,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,apiTestCinc,,cincPropSAVEID01,Aaron,Sanders,4658 Iron Pond Vista,,Monumental,IA,52897,,,,0.0,FALSE,,,,,
        //SetResidents,38,97,31734,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,apiTestCollier,,collierPropSAVEID01,Cheryl,Russell,4580 Velvet Horse Edge,,Do Stop,AZ,86473,,,,0.00,FALSE,,,,,
        //SetResidents,38,99,31734,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,apiTestFiserv,,fiservPropSAVEID01,Paula,Rivera,8504 Harvest Apple Round,,Turnip Hole,AR,72228,,,,.1,FALSE,,,,,
        //SetResidents,38,101,31734,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,apiTestOnsite,,onsitePropSAVEID01,Joshua,Bryant,3751 Emerald Impasse,,Nenahnezad,NM,88408,,,,.11,FALSE,,317343184,,,17343184
        //SetResidents,38,103,31734,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,apiTestTops,,topsPropSAVEID01,Bonnie,Williams,8701 Sunny Rise Boulevard,,Windthorst,VT,5103,,,,0.11,FALSE,,317343184,,,17343184

        //SetResidents,38,105,31734,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,apiTestHOLDAssocia,,associaPropSAVEID01,Kenneth,Gonzalez,3514 Tawny Mews,,Shiawasseetown,AR,72089,,,,0,TRUE,,,,,
        //SetResidents,38,106,31734,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,apiTestHOLDCinc,,cincPropSAVEID01,Aaron,Sanders,4658 Iron Pond Vista,,Monumental,IA,52897,,,,0.0,TRUE,,,,,
        //SetResidents,38,107,31734,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,apiTestHOLDCollier,,collierPropSAVEID01,Cheryl,Russell,4580 Velvet Horse Edge,,Do Stop,AZ,86473,,,,0.00,TRUE,,,,,
        //SetResidents,38,108,31734,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,apiTestHOLDFiserv,,fiservPropSAVEID01,Paula,Rivera,8504 Harvest Apple Round,,Turnip Hole,AR,72228,,,,.1,TRUE,,,,,
        //SetResidents,38,109,31734,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,apiTestHOLDOnsite,,onsitePropSAVEID01,Joshua,Bryant,3751 Emerald Impasse,,Nenahnezad,NM,88408,,,,.11,TRUE,,317343184,,,17343184
        //SetResidents,38,110,31734,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,apiTestHOLDTops,,topsPropSAVEID01,Bonnie,Williams,8701 Sunny Rise Boulevard,,Windthorst,VT,5103,,,,0.11,TRUE,,317343184,,,17343184
        testCases.add(
            new AapiTestCaseCollection(
                new Credentials("31734", username, password, null, pmId),
                "Test",
                endpoint
            ).add(
                SetResidents.createValid(
                    "Test case 93/95/97/99/101/103/105 to 110  - bad userId",
                    getExpectedResponse(gatewayErrors, "38"))
                    .setPropertyId(propertyId)
                    .setHold(holdValue)
                    .setGenerateRegistrationUrl("")
            ));

        //SetResidents,38,111,,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,apiTestAssocia,,associaPropSAVEID01,Kenneth,Gonzalez,3514 Tawny Mews,,Shiawasseetown,AR,72089,,,,0,FALSE,,,,,
        //SetResidents,38,113,,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,apiTestCinc,,cincPropSAVEID01,Aaron,Sanders,4658 Iron Pond Vista,,Monumental,IA,52897,,,,0.0,FALSE,,,,,
        //SetResidents,38,115,,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,apiTestCollier,,collierPropSAVEID01,Cheryl,Russell,4580 Velvet Horse Edge,,Do Stop,AZ,86473,,,,0.00,FALSE,,,,,
        //SetResidents,38,117,,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,apiTestFiserv,,fiservPropSAVEID01,Paula,Rivera,8504 Harvest Apple Round,,Turnip Hole,AR,72228,,,,.1,FALSE,,,,,
        //SetResidents,38,119,,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,apiTestOnsite,,onsitePropSAVEID01,Joshua,Bryant,3751 Emerald Impasse,,Nenahnezad,NM,88408,,,,.11,FALSE,,317343184,,,17343184
        //SetResidents,38,121,,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,apiTestTops,,topsPropSAVEID01,Bonnie,Williams,8701 Sunny Rise Boulevard,,Windthorst,VT,5103,,,,0.11,FALSE,,317343184,,,17343184

        //SetResidents,38,123,,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,apiTestHOLDAssocia,,associaPropSAVEID01,Kenneth,Gonzalez,3514 Tawny Mews,,Shiawasseetown,AR,72089,,,,0,TRUE,,,,,
        //SetResidents,38,124,,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,apiTestHOLDCinc,,cincPropSAVEID01,Aaron,Sanders,4658 Iron Pond Vista,,Monumental,IA,52897,,,,0.0,TRUE,,,,,
        //SetResidents,38,125,,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,apiTestHOLDCollier,,collierPropSAVEID01,Cheryl,Russell,4580 Velvet Horse Edge,,Do Stop,AZ,86473,,,,0.00,TRUE,,,,,
        //SetResidents,38,126,,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,apiTestHOLDFiserv,,fiservPropSAVEID01,Paula,Rivera,8504 Harvest Apple Round,,Turnip Hole,AR,72228,,,,.1,TRUE,,,,,
        //SetResidents,38,127,,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,apiTestHOLDOnsite,,onsitePropSAVEID01,Joshua,Bryant,3751 Emerald Impasse,,Nenahnezad,NM,88408,,,,.11,TRUE,,317343184,,,17343184
        //SetResidents,38,128,,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,apiTestHOLDTops,,topsPropSAVEID01,Bonnie,Williams,8701 Sunny Rise Boulevard,,Windthorst,VT,5103,,,,0.11,TRUE,,317343184,,,17343184
        testCases.add(
            new AapiTestCaseCollection(
                new Credentials("", username, password, null, pmId),
                "Test",
                endpoint
            ).add(
                SetResidents.createValid(
                    "Test case 111/113/115/117/119/121/123 to 128  - empty userId",
                    getExpectedResponse(gatewayErrors, "38"))
                    .setPropertyId(propertyId)
                    .setHold(holdValue)
                    .setGenerateRegistrationUrl("")
            ));
      }

      //SetResidents,8,22,317343184,be297ejCEn,,Test,associa,,,associaPropSAVEID01,,,,,,,,,,,,,,,,,
      //SetResidents,8,24,317343184,be297ejCEn,,Test,cinc,,,cincPropSAVEID01,,,,,,,,,,,,,,,,,
      //SetResidents,8,26,317343184,be297ejCEn,,Test,collier,,,collierPropSAVEID01,,,,,,,,,,,,,,,,,
      //SetResidents,8,28,317343184,be297ejCEn,,Test,fiserv,,,fiservPropSAVEID01,,,,,,,,,,,,,,,,,
      //SetResidents,8,30,317343184,be297ejCEn,,Test,onsite,,,onsitePropSAVEID01,,,,,,,,,,,,,,317343184,,,17343184
      //SetResidents,8,32,317343184,be297ejCEn,,Test,tops,,,topsPropSAVEID01,,,,,,,,,,,,,,317343184,,,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password, "", pmId),
              "Test",
              endpoint
          ).add(
              SetResidents.createAllEmpty(
                  "Test case 22/24/26/28/30/32 - empty api key",
                  getExpectedResponse(gatewayErrors, "8"))
                  .setPropertyId(propertyId)
          ));

      //SetResidents,9,40,317343184,be297ejCEn,badapi,Test,associa,,,associaPropSAVEID01,,,,,,,,,,,,,,,,,
      //SetResidents,9,42,317343184,be297ejCEn,badapi,Test,cinc,,,cincPropSAVEID01,,,,,,,,,,,,,,,,,
      //SetResidents,9,44,317343184,be297ejCEn,badapi,Test,collier,,,collierPropSAVEID01,,,,,,,,,,,,,,,,,
      //SetResidents,9,46,317343184,be297ejCEn,badapi,Test,fiserv,,,fiservPropSAVEID01,,,,,,,,,,,,,,,,,
      //SetResidents,9,48,317343184,be297ejCEn,badapi,Test,onsite,,,onsitePropSAVEID01,,,,,,,,,,,,,,317343184,,,17343184
      //SetResidents,9,50,317343184,be297ejCEn,badapi,Test,tops,,,topsPropSAVEID01,,,,,,,,,,,,,,317343184,,,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password, "badapi", pmId),
              "Test",
              endpoint
          ).add(
              SetResidents.createAllEmpty(
                  "Test case 40/42/44/46/48/50 - bad api key",
                  getExpectedResponse(gatewayErrors, "9"))
                  .setPropertyId(propertyId)
          ));

      //SetResidents,20,58,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,,associa,,,associaPropSAVEID01,,,,,,,,,,,,,,,,,
      //SetResidents,20,60,317343184,be297ejCEn,hovee3uv0ooShoi1Such,,cinc,,,cincPropSAVEID01,,,,,,,,,,,,,,,,,
      //SetResidents,20,62,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,,collier,,,collierPropSAVEID01,,,,,,,,,,,,,,,,,
      //SetResidents,20,64,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,,fiserv,,,fiservPropSAVEID01,,,,,,,,,,,,,,,,,
      //SetResidents,20,66,317343184,be297ejCEn,qua1aiPhul2chohz5aer,,onsite,,,onsitePropSAVEID01,,,,,,,,,,,,,,317343184,,,17343184
      //SetResidents,20,68,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,,tops,,,topsPropSAVEID01,,,,,,,,,,,,,,317343184,,,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password, null, pmId),
              "",
              endpoint
          ).add(
              SetResidents.createAllEmpty(
                  "Test case 58/60/62/64/66/68 - empty mode",
                  getExpectedResponse(gatewayErrors, "20"))
                  .setPropertyId(propertyId)
          ));

      //SetResidents,38,76,317343184,badpass,iedahy2ohcie7nieWoo1,Test,associa,,,associaPropSAVEID01,,,,,,,,,,,,,,,,,
      //SetResidents,38,78,317343184,badpass,hovee3uv0ooShoi1Such,Test,cinc,,,cincPropSAVEID01,,,,,,,,,,,,,,,,,
      //SetResidents,38,80,317343184,badpass,rohn3shaiquaesh1xoSu,Test,collier,,,collierPropSAVEID01,,,,,,,,,,,,,,,,,
      //SetResidents,38,82,317343184,badpass,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,,,fiservPropSAVEID01,,,,,,,,,,,,,,,,,
      //SetResidents,38,84,317343184,badpass,qua1aiPhul2chohz5aer,Test,onsite,,,onsitePropSAVEID01,,,,,,,,,,,,,,317343184,,,17343184
      //SetResidents,38,86,317343184,badpass,EinaGh1oe4eihu1gohci,Test,tops,,,topsPropSAVEID01,,,,,,,,,,,,,,317343184,,,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, "badpass", null, pmId),
              "Test",
              endpoint
          ).add(
              SetResidents.createAllEmpty(
                  "Test case 76/78/80/82/84/86 - bad password",
                  getExpectedResponse(gatewayErrors, "38"))
                  .setPropertyId(propertyId)
          ));

      //SetResidents,38,94,31734,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,,,associaPropSAVEID01,,,,,,,,,,,,,,,,,
      //SetResidents,38,96,31734,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,,,cincPropSAVEID01,,,,,,,,,,,,,,,,,
      //SetResidents,38,98,31734,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,,,collierPropSAVEID01,,,,,,,,,,,,,,,,,
      //SetResidents,38,100,31734,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,,,fiservPropSAVEID01,,,,,,,,,,,,,,,,,
      //SetResidents,38,102,31734,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,,,onsitePropSAVEID01,,,,,,,,,,,,,,317343184,,,17343184
      //SetResidents,38,104,31734,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,,,topsPropSAVEID01,,,,,,,,,,,,,,317343184,,,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials("31734", username, password, null, pmId),
              "Test",
              endpoint
          ).add(
              SetResidents.createAllEmpty(
                  "Test case 94/96/98/100/102/104 - bad userId",
                  getExpectedResponse(gatewayErrors, "38"))
                  .setPropertyId(propertyId)
          ));

      //SetResidents,38,112,,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,,,associaPropSAVEID01,,,,,,,,,,,,,,,,,
      //SetResidents,38,114,,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,,,cincPropSAVEID01,,,,,,,,,,,,,,,,,
      //SetResidents,38,116,,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,,,collierPropSAVEID01,,,,,,,,,,,,,,,,,
      //SetResidents,38,118,,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,,,fiservPropSAVEID01,,,,,,,,,,,,,,,,,
      //SetResidents,38,120,,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,,,onsitePropSAVEID01,,,,,,,,,,,,,,317343184,,,17343184
      //SetResidents,38,122,,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,,,topsPropSAVEID01,,,,,,,,,,,,,,317343184,,,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials("", username, password, null, pmId),
              "Test",
              endpoint
          ).add(
              SetResidents.createAllEmpty(
                  "Test case  112/114/116/118/120/122 - empty userId",
                  getExpectedResponse(gatewayErrors, "38"))
                  .setPropertyId(propertyId)
          ));
    }

    executeTests(testCases);
  }

  @Test(groups = {"aapi", "SetResidents"})
  public void setResidentsCorporate() {
    Logger.info("SetResidents for Oakwood corporate clients Test");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc2");
    testSetupPage.open();
    final String userId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String pmId = testSetupPage.getString("pmId");
    final String propertyId = testSetupPage.getString("propNumber");
    final String corpClientId = testSetupPage.getString("cpayClientId");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    DataHelper dataHelper = new DataHelper();
    ArrayList<TestCaseCollection> testCases = new ArrayList<>();

    //SetResidents,1,1,324751344,Y3vgNMyPJE,IeteoCiojao2Jahshahr,Test,oakwood,cpay_idRAND,NULL,robsynergy1,CorpClient,Tester,123 Synergy Lane,NULL,San Diego,CA,92121,2222222222,3333333333,test@paylease.com,1300.33,FALSE,TRUE,,CORP,client,
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password, null, pmId),
            "Test",
            "oakwood"
        ).add(
            new SetResidents(
                "Test case 1  - oakwood - client",
                getExpectedResponse(gatewayErrors, "1"))
                .setResidentId(dataHelper.getReferenceId())
                .setPropertyId(propertyId)
                .setFirstName("CorpClient")
                .setLastName("Tester")
                .setStreetAddress("123 Synergy Lane")
                .setCity("San Diego")
                .setState("CA")
                .setPostalCode("92121")
                .setPhone("2222222222")
                .setAlternatePhone("3333333333")
                .setEmail("test@paylease.com")
                .setAmount("1300.33")
                .setHold("FALSE")
                .setGenerateRegistrationUrl("TRUE")
                .setIsCorporateClient("TRUE")
        ));

    //SetResidents,1,2,324751344,Y3vgNMyPJE,IeteoCiojao2Jahshahr,Test,oakwood,cpay_idRAND,NULL,robsynergy1,CorpResident,Tester,123 Synergy Lane,NULL,San Diego,CA,92121,2222222222,3333333333,test@paylease.com,1300.33,FALSE,FALSE,,CORP,resident,find_cpay_client_id
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password, null, pmId),
            "Test",
            "oakwood"
        ).add(
            new SetResidents(
                "Test case 2  - oakwood - resident",
                getExpectedResponse(gatewayErrors, "1"))
                .setResidentId(dataHelper.getReferenceId())
                .setPropertyId(propertyId)
                .setFirstName("CorpResident")
                .setLastName("Tester")
                .setStreetAddress("123 Synergy Lane")
                .setCity("San Diego")
                .setState("CA")
                .setPostalCode("92121")
                .setPhone("2222222222")
                .setAlternatePhone("3333333333")
                .setEmail("test@paylease.com")
                .setAmount("1300.33")
                .setHold("FALSE")
                .setGenerateRegistrationUrl("FALSE")
                .setIsCorporateClientResident("TRUE")
                .setCorporateClientId(corpClientId)
        ));

    executeTests(testCases);
  }
}