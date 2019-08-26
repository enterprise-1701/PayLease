package com.paylease.app.qa.api.tests.aapi;

import com.paylease.app.qa.api.tests.TestCaseCollection;
import com.paylease.app.qa.api.tests.aapi.testcase.RemoveProperty;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.api.Credentials;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.testng.annotations.Test;


public class RemovePropertyTest extends BaseTest {

  private static final String REGION = "aapi";
  private static final String FEATURE = "RemoveProperty";

  //-----------------------------------TESTS--------------------------------------------------------

  @Test(groups = {"aapi", "RemoveProperty"})
  public void removeProperty() {
    Logger.info("Remove Property Test");

    final String[] endpointValues = {
        "associa", "cinc", "collier", "fiserv", "onsite", "tops"
    };

    ArrayList<TestCaseCollection> testCases = new ArrayList<>();

    for (String endpointValue : endpointValues) {

      TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
      testSetupPage.open();
      final String userId = testSetupPage.getString("gatewayId");
      final String username = testSetupPage.getString("username");
      final String password = testSetupPage.getString("password");
      final String pmId = testSetupPage.getString("pmId");
      final String propertyId = testSetupPage.getString("propNumber");
      final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

      //RemoveProperty,4,1,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,associaAPI01,
      //RemoveProperty,4,2,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,cincAPI01,
      //RemoveProperty,4,3,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,collierAPI01,
      //RemoveProperty,4,4,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,fiservAPII01,
      //RemoveProperty,4,5,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,onsiteAPI01,17343184
      //RemoveProperty,4,6,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,topsAPI01,17343184

      //RemoveProperty,4,7,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,associaAPI02,
      //RemoveProperty,4,8,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,cincAPI02,
      //RemoveProperty,4,9,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,collierAPI02,
      //RemoveProperty,4,10,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,fiservAPI02,
      //RemoveProperty,4,11,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,onsiteAPI02,17343184
      //RemoveProperty,4,12,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,topsAPI02,17343184

      //RemoveProperty,4,13,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,associaAPI03,
      //RemoveProperty,4,14,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,cincAPI03,
      //RemoveProperty,4,15,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,collierAPI03,
      //RemoveProperty,4,16,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,fiservAPI03,
      //RemoveProperty,4,17,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,onsiteAPI03,17343184
      //RemoveProperty,4,18,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,topsAPI03,17343184

      //RemoveProperty,4,19,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,associaAPI04,
      //RemoveProperty,4,20,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,cincAPI04,
      //RemoveProperty,4,21,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,collierAPI04,
      //RemoveProperty,4,22,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,fiservAPI04,
      //RemoveProperty,4,23,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,onsiteAPI04,17343184
      //RemoveProperty,4,24,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,topsAPI04,17343184

      //RemoveProperty,4,25,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,associaAPI05,
      //RemoveProperty,4,26,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,cincAPI05,
      //RemoveProperty,4,27,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,collierAPI05,
      //RemoveProperty,4,28,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,fiservAPI05,
      //RemoveProperty,4,29,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,onsiteAPI05,17343184
      //RemoveProperty,4,30,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,topsAPI05,17343184

      //RemoveProperty,4,31,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,associaAPI0,
      //RemoveProperty,4,32,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,cincAPI06,
      //RemoveProperty,4,33,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,collierAPI06,
      //RemoveProperty,4,34,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,fiservAPI06,
      //RemoveProperty,4,35,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,onsiteAPI06,17343184
      //RemoveProperty,4,36,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,topsAPI06,17343184

      //RemoveProperty,4,37,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,associaAPI07,
      //RemoveProperty,4,38,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,cincAPI07,
      //RemoveProperty,4,39,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,collierAPI07,
      //RemoveProperty,4,40,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,fiservAPI07,
      //RemoveProperty,4,41,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,onsiteAPI07,17343184
      //RemoveProperty,4,42,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,topsAPI07,17343184

      //RemoveProperty,4,43,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,associaAPI08,
      //RemoveProperty,4,44,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,cincAPI08,
      //RemoveProperty,4,45,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,collierAPI08,
      //RemoveProperty,4,46,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,fiservAPI08,
      //RemoveProperty,4,47,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,onsiteAPI08,17343184
      //RemoveProperty,4,48,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,topsAPI08,17343184

      //RemoveProperty,4,49,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,associaAPI09,
      //RemoveProperty,4,50,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,cincAPI09,
      //RemoveProperty,4,51,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,collierAPI09,
      //RemoveProperty,4,52,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,fiservAPI09,
      //RemoveProperty,4,53,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,onsiteAPI09,17343184
      //RemoveProperty,4,54,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,topsAPI09,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password, null, pmId),
              "Test",
              endpointValue
          ).add(
          new RemoveProperty(
              "Test case 1 to 54 - Different properties",
              getExpectedResponse(gatewayErrors, "4"),
              propertyId)
      ));
    }

    executeTests(testCases);
  }
}

