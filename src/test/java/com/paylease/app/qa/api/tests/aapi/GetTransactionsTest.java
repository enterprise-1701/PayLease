package com.paylease.app.qa.api.tests.aapi;

import com.paylease.app.qa.api.tests.TestCaseCollection;
import com.paylease.app.qa.api.tests.aapi.testcase.GetTransactions;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.api.Credentials;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.testng.annotations.Test;


public class GetTransactionsTest extends BaseTest {

  private static final String REGION = "aapi";
  private static final String FEATURE = "GetTransactions";

  //-----------------------------------TESTS--------------------------------------------------------

  @Test(groups = {"aapi", "GetTransactions"})
  public void getTransactions() {
    Logger.info("Get Transactions Test");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();
    final String userId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String pmId = testSetupPage.getString("pmId");
    final String date = testSetupPage.getString("transDate_INITIATED");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    final String[] endpointValues = {
        "associa", "cinc", "collier", "fiserv", "onsite", "tops"
    };

    ArrayList<TestCaseCollection> testCases = new ArrayList<>();

    for (String endpointValue : endpointValues) {

      //GetTransactions,1,1,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,05/04/2015,INITIATED,
      //GetTransactions,1,2,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,05/04/2015,INITIATED,
      //GetTransactions,1,3,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,05/04/2015,INITIATED,
      //GetTransactions,1,4,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,05/04/2015,INITIATED,
      //GetTransactions,1,5,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,05/04/2015,INITIATED,17343184
      //GetTransactions,1,6,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,05/04/2015,INITIATED,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password, null, pmId),
              "Test",
              endpointValue
          ).add(
              new GetTransactions(
                  "Test case 1/2/3/4/5/6 - Different endpoints",
                  getExpectedResponse(gatewayErrors, "1"),
                  date,
                  "INITIATED")
          ));

      //GetTransactions,8,7,317343184,be297ejCEn,,Test,associa,01/14/2015,INITIATED,
      //GetTransactions,8,8,317343184,be297ejCEn,,Test,cinc,01/14/2015,INITIATED,
      //GetTransactions,8,9,317343184,be297ejCEn,,Test,collier,01/14/2015,INITIATED,
      //GetTransactions,8,10,317343184,be297ejCEn,,Test,fiserv,01/14/2015,INITIATED,
      //GetTransactions,8,11,317343184,be297ejCEn,,Test,onsite,01/14/2015,INITIATED,17343184
      //GetTransactions,8,12,317343184,be297ejCEn,,Test,tops,01/14/2015,INITIATED,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password, "", pmId),
              "Test",
              endpointValue
          ).add(
              new GetTransactions(
                  "Test case 7/8/9/10/11/12 - No apikey",
                  getExpectedResponse(gatewayErrors, "8"),
                  date,
                  "INITIATED")
          ));

      //GetTransactions,9,13,317343184,be297ejCEn,badapikey,Test,associa,01/14/2015,INITIATED,
      //GetTransactions,9,14,317343184,be297ejCEn,badapikey,Test,cinc,01/14/2015,INITIATED,
      //GetTransactions,9,15,317343184,be297ejCEn,badapikey,Test,collier,01/14/2015,INITIATED,
      //GetTransactions,9,16,317343184,be297ejCEn,badapikey,Test,fiserv,01/14/2015,INITIATED,
      //GetTransactions,9,17,317343184,be297ejCEn,badapikey,Test,onsite,01/14/2015,INITIATED,17343184
      //GetTransactions,9,18,317343184,be297ejCEn,badapikey,Test,tops,01/14/2015,INITIATED,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password, "badapikey", pmId),
              "Test",
              endpointValue
          ).add(
              new GetTransactions(
                  "Test case 13/14/15/16/17/18 - Bad apikey",
                  getExpectedResponse(gatewayErrors, "9"),
                  date,
                  "INITIATED")
          ));

      //GetTransactions,9,19,317343184,be297ejCEn, ,Test,associa,01/14/2015,INITIATED,
      //GetTransactions,9,20,317343184,be297ejCEn, ,Test,cinc,01/14/2015,INITIATED,
      //GetTransactions,9,21,317343184,be297ejCEn, ,Test,collier,01/14/2015,INITIATED,
      //GetTransactions,9,22,317343184,be297ejCEn, ,Test,fiserv,01/14/2015,INITIATED,
      //GetTransactions,9,23,317343184,be297ejCEn, ,Test,onsite,01/14/2015,INITIATED,17343184
      //GetTransactions,9,24,317343184,be297ejCEn, ,Test,tops,01/14/2015,INITIATED,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password, " ", pmId),
              "Test",
              endpointValue
          ).add(
              new GetTransactions(
                  "Test case 19/20/21/22/23/24 - Blank space as api key",
                  getExpectedResponse(gatewayErrors, "9"),
                  date,
                  "INITIATED")
          ));

      //GetTransactions,20,25,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,,associa,01/14/2015,INITIATED,
      //GetTransactions,20,26,317343184,be297ejCEn,hovee3uv0ooShoi1Such,,cinc,01/14/2015,INITIATED,
      //GetTransactions,20,27,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,,collier,01/14/2015,INITIATED,
      //GetTransactions,20,28,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,,fiserv,01/14/2015,INITIATED,
      //GetTransactions,20,29,317343184,be297ejCEn,qua1aiPhul2chohz5aer,,onsite,01/14/2015,INITIATED,17343184
      //GetTransactions,20,30,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,,tops,01/14/2015,INITIATED,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password, null, pmId),
              "",
              endpointValue
          ).add(
              new GetTransactions(
                  "Test case 25/26/27/28/29/30 - No mode",
                  getExpectedResponse(gatewayErrors, "20"),
                  date,
                  "INITIATED")
          ));

      //GetTransactions,21,31,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Production,associa,01/14/2015,INITIATED,
      //GetTransactions,21,32,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Production,cinc,01/14/2015,INITIATED,
      //GetTransactions,21,33,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Production,collier,01/14/2015,INITIATED,
      //GetTransactions,21,34,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Production,fiserv,01/14/2015,INITIATED,
      //GetTransactions,21,35,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Production,onsite,01/14/2015,INITIATED,17343184
      //GetTransactions,21,36,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Production,tops,01/14/2015,INITIATED,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password, null, pmId),
              "Production",
              endpointValue
          ).add(
              new GetTransactions(
                  "Test case 31/32/33/34/35/36 - Production mode",
                  getExpectedResponse(gatewayErrors, "21"),
                  date,
                  "INITIATED")
          ));

      //GetTransactions,38,37,317343184,badpass,iedahy2ohcie7nieWoo1,Test,associa,01/14/2015,INITIATED,
      //GetTransactions,38,38,317343184,badpass,hovee3uv0ooShoi1Such,Test,cinc,01/14/2015,INITIATED,
      //GetTransactions,38,39,317343184,badpass,rohn3shaiquaesh1xoSu,Test,collier,01/14/2015,INITIATED,
      //GetTransactions,38,40,317343184,badpass,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,01/14/2015,INITIATED,
      //GetTransactions,38,41,317343184,badpass,qua1aiPhul2chohz5aer,Test,onsite,01/14/2015,INITIATED,17343184
      //GetTransactions,38,42,317343184,badpass,EinaGh1oe4eihu1gohci,Test,tops,01/14/2015,INITIATED,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, "badpass", null, pmId),
              "Test",
              endpointValue
          ).add(
              new GetTransactions(
                  "Test case 37/38/39/40/41/42 - Bad password",
                  getExpectedResponse(gatewayErrors, "38"),
                  date,
                  "INITIATED")
          ));

      //GetTransactions,38,43,317343184, ,iedahy2ohcie7nieWoo1,Test,associa,01/14/2015,INITIATED,
      //GetTransactions,38,44,317343184, ,hovee3uv0ooShoi1Such,Test,cinc,01/14/2015,INITIATED,
      //GetTransactions,38,45,317343184, ,rohn3shaiquaesh1xoSu,Test,collier,01/14/2015,INITIATED,
      //GetTransactions,38,46,317343184, ,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,01/14/2015,INITIATED,
      //GetTransactions,38,47,317343184, ,qua1aiPhul2chohz5aer,Test,onsite,01/14/2015,INITIATED,17343184
      //GetTransactions,38,48,317343184, ,EinaGh1oe4eihu1gohci,Test,tops,01/14/2015,INITIATED,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, " ", null, pmId),
              "Test",
              endpointValue
          ).add(
              new GetTransactions(
                  "Test case 43/44/45/46/47/48 - Blank space as password",
                  getExpectedResponse(gatewayErrors, "38"),
                  date,
                  "INITIATED")
          ));

      //GetTransactions,39,49,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,05/14/15,badstatus,
      //GetTransactions,39,50,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,05/14/15,badstatus,
      //GetTransactions,39,51,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,05/14/15,badstatus,
      //GetTransactions,39,52,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,05/14/15,badstatus,
      //GetTransactions,39,53,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,05/14/15,badstatus,17343184
      //GetTransactions,39,54,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,05/14/15,badstatus,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password, null, pmId),
              "Test",
              endpointValue
          ).add(
              new GetTransactions(
                  "Test case 49/50/51/52/53/54 - Bad status",
                  getExpectedResponse(gatewayErrors, "39"),
                  date,
                  "badstatus")
          ));

      //GetTransactions,39,55,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,05/14/15,,
      //GetTransactions,39,56,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,05/14/15,,
      //GetTransactions,39,57,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,05/14/15,,
      //GetTransactions,39,58,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,05/14/15,,
      //GetTransactions,39,59,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,05/14/15,,17343184
      //GetTransactions,39,60,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,05/14/15,,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password, null, pmId),
              "Test",
              endpointValue
          ).add(
              new GetTransactions(
                  "Test case 55/56/57/58/59/60 - Empty status",
                  getExpectedResponse(gatewayErrors, "39"),
                  date,
                  "")
          ));

      //GetTransactions,45,61,317343184,,iedahy2ohcie7nieWoo1,Test,associa,01/14/2015,INITIATED,
      //GetTransactions,45,62,317343184,,hovee3uv0ooShoi1Such,Test,cinc,01/14/2015,INITIATED,
      //GetTransactions,45,63,317343184,,rohn3shaiquaesh1xoSu,Test,collier,01/14/2015,INITIATED,
      //GetTransactions,45,64,317343184,,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,01/14/2015,INITIATED,
      //GetTransactions,45,65,317343184,,qua1aiPhul2chohz5aer,Test,onsite,01/14/2015,INITIATED,17343184
      //GetTransactions,45,66,317343184,,EinaGh1oe4eihu1gohci,Test,tops,01/14/2015,INITIATED,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, "", null, pmId),
              "Test",
              endpointValue
          ).add(
              new GetTransactions(
                  "Test case 61/62/63/64/65/66 - Empty password",
                  getExpectedResponse(gatewayErrors, "45"),
                  date,
                  "INITIATED")
          ));
    }

    executeTests(testCases);
  }

  @Test(groups = {"aapi", "GetTransactions"})
  public void getTransactionsOakwood() {
    Logger.info("Get Transactions Test - Oakwood");

    TestSetupPage testSetupPageOak = new TestSetupPage(REGION, FEATURE, "tc2");
    testSetupPageOak.open();
    final String userIdOak = testSetupPageOak.getString("gatewayId");
    final String usernameOak = testSetupPageOak.getString("username");
    final String passwordOak = testSetupPageOak.getString("password");
    final String dateInitiated = testSetupPageOak.getString("transDate_INITIATED");
    final String dateCancelled = testSetupPageOak.getString("transDate_CANCELLED");
    final String dateDeposited = testSetupPageOak.getString("transDate_DEPOSITED");
    final String dateRefunded = testSetupPageOak.getString("transDate_REFUNDED");
    final List<HashMap<String, Object>> gatewayErrorsOak = testSetupPageOak
        .getTable("gatewayErrors");

    ArrayList<TestCaseCollection> testCases = new ArrayList<>();

    final String[] statuses = {
        "REFUNDED", "CANCELLED", "DEPOSITED", "INITIATED"
    };

    for (String status : statuses) {
      String date = "";

      switch (status) {
        case "REFUNDED":
          date = dateRefunded;
          break;
        case "CANCELLED":
          date = dateCancelled;
          break;
        case "DEPOSITED":
          date = dateDeposited;
          break;
        case "INITIATED":
          date = dateInitiated;
          break;
        default:
          // not supported
      }

      //GetTransactions,1,67,313052174,wGNG2fzbkW,IeteoCiojao2Jahshahr,Test,oakwood,03/16/2016,REFUNDED,
      //GetTransactions,1,68,313052174,wGNG2fzbkW,IeteoCiojao2Jahshahr,Test,oakwood,05/18/2016,DEPOSITED,
      //GetTransactions,1,69,313052174,wGNG2fzbkW,IeteoCiojao2Jahshahr,Test,oakwood,06/06/2016,INITIATED,
      //GetTransactions,1,70,313052174,wGNG2fzbkW,IeteoCiojao2Jahshahr,Test,oakwood,06/21/2016,CANCELLED,
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userIdOak, usernameOak, passwordOak),
              "Test",
              "oakwood"
          ).add(
              new GetTransactions(
                  "Test case 67/68/69/70 - Different Statuses",
                  getExpectedResponse(gatewayErrorsOak, "1"),
                  date,
                  status)
          ));
    }

    executeTests(testCases);
  }
}

