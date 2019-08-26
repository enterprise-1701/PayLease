package com.paylease.app.qa.api.tests.aapi;

import com.paylease.app.qa.api.tests.TestCaseCollection;
import com.paylease.app.qa.api.tests.aapi.testcase.GetPaymentMethodsByResident;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.api.Credentials;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.testng.annotations.Test;


public class GetPaymentMethodsByResidentTest extends BaseTest {

  private static final String REGION = "aapi";
  private static final String FEATURE = "GetPaymentMethodsByResident";

  //-----------------------------------TESTS--------------------------------------------------------

  @Test(groups = {"aapi", "GetPaymentMethodsByResident"})
  public void getPaymentMethodsByResiden() {
    Logger.info("Get Payment Methods By Resident Test");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();
    final String userId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String pmId = testSetupPage.getString("pmId");
    final String residentIdBank = testSetupPage.getString("residentRefIdBank");
    final String residentIdCreditCard = testSetupPage.getString("residentRefIdCc");

    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    ArrayList<TestCaseCollection> testCases = new ArrayList<>();

    final String[] residentIdValues = {
        residentIdBank, residentIdCreditCard
    };

    for (String residentId : residentIdValues) {
      //GetPaymentMethodsByResident,1,1,313052174,wGNG2fzbkW,IeteoCiojao2Jahshahr,Test,oakwood,13052174,oakwoodFinalTest2,NULL
      //GetPaymentMethodsByResident,1,1,313052174,wGNG2fzbkW,IeteoCiojao2Jahshahr,Test,oakwood,13052174,oakwoodFinalTest3,NULL
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password, null, pmId),
              "Test",
              "oakwood"
          ).add(
              new GetPaymentMethodsByResident(
                  "Test case 1 - created since null",
                  getExpectedResponse(gatewayErrors, "1"))
                  .setResidentId(residentId)
          ));
    }

    //GetPaymentMethodsByResident,1,1,313052174,wGNG2fzbkW,IeteoCiojao2Jahshahr,Test,oakwood,13052174,RobOakWoodResID,2015-07-01
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password, null, pmId),
            "Test",
            "oakwood"
        ).add(
            new GetPaymentMethodsByResident(
                "Test case 1 - valid created since value",
                getExpectedResponse(gatewayErrors, "1"))
                .setResidentId(residentIdBank)
                .setCreatedSince("2015-07-01")
        ));

    executeTests(testCases);
  }
}

