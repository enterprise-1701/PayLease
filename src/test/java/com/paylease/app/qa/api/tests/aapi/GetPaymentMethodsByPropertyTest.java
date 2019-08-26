package com.paylease.app.qa.api.tests.aapi;

import com.paylease.app.qa.api.tests.TestCaseCollection;
import com.paylease.app.qa.api.tests.aapi.testcase.GetPaymentMethodsByProperty;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.api.Credentials;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.testng.annotations.Test;


public class GetPaymentMethodsByPropertyTest extends BaseTest {

  private static final String REGION = "aapi";
  private static final String FEATURE = "GetPaymentMethodsByProperty";

  //-----------------------------------TESTS--------------------------------------------------------

  @Test(groups = {"aapi", "GetPaymentMethodsByProperty"})
  public void getPaymentMethodsByProperty() {
    Logger.info("Get Payment Methods By Property Test");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();
    final String userId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String pmId = testSetupPage.getString("pmId");
    final String propertyReferenceId = testSetupPage.getString("propNumber");
    final String propertyNoResidents = testSetupPage.getString("propNumberNoRes");

    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    ArrayList<TestCaseCollection> testCases = new ArrayList<>();

    //GetPaymentMethodsByProperty,1,1,313052174,wGNG2fzbkW,IeteoCiojao2Jahshahr,Test,oakwood,13052174,RobOakWoodPropRefID,NULL,NULL
    //GetPaymentMethodsByProperty,1,4,313052174,wGNG2fzbkW,IeteoCiojao2Jahshahr,Test,oakwood,13052174,RobOakWoodPropRefID,NULL,NULL
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password, null, pmId),
            "Test",
            "oakwood"
        ).add(
            new GetPaymentMethodsByProperty(
                "Test case 1/4 - activePaymentMethod and created since null",
                getExpectedResponse(gatewayErrors, "1"))
                .setPropertyReferenceId(propertyReferenceId)
        ));

    final String[] booleanValues = {
        "TRUE", "FALSE"
    };

    final String[] createdSinceValues = {
        "2015-07-21", "07/18/14", "7/21/2015",
    };

    for (String activePaymentMethod : booleanValues) {
      //GetPaymentMethodsByProperty,1,2,313052174,wGNG2fzbkW,IeteoCiojao2Jahshahr,Test,oakwood,13052174,RobOakWoodPropRefID,TRUE,NULL
      //GetPaymentMethodsByProperty,1,3,313052174,wGNG2fzbkW,IeteoCiojao2Jahshahr,Test,oakwood,13052174,RobOakWoodPropRefID,FALSE,NULL
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password, null, pmId),
              "Test",
              "oakwood"
          ).add(
              new GetPaymentMethodsByProperty(
                  "Test case 2/3 - Active Payment Method Residents",
                  getExpectedResponse(gatewayErrors, "1"))
                  .setPropertyReferenceId(propertyReferenceId)
                  .setActivePaymentMethodResidents(activePaymentMethod)
          ));

      for (String createdSinceValue : createdSinceValues) {
        //GetPaymentMethodsByProperty,1,5,313052174,wGNG2fzbkW,IeteoCiojao2Jahshahr,Test,oakwood,13052174,RobOakWoodPropRefID,TRUE,2015-07-21
        //GetPaymentMethodsByProperty,1,6,313052174,wGNG2fzbkW,IeteoCiojao2Jahshahr,Test,oakwood,13052174,RobOakWoodPropRefID,TRUE,07/21/15
        //GetPaymentMethodsByProperty,1,7,313052174,wGNG2fzbkW,IeteoCiojao2Jahshahr,Test,oakwood,13052174,RobOakWoodPropRefID,TRUE,7/21/2015
        //GetPaymentMethodsByProperty,1,8,313052174,wGNG2fzbkW,IeteoCiojao2Jahshahr,Test,oakwood,13052174,RobOakWoodPropRefID,TRUE,2014-07-18
        //GetPaymentMethodsByProperty,1,9,313052174,wGNG2fzbkW,IeteoCiojao2Jahshahr,Test,oakwood,13052174,RobOakWoodPropRefID,TRUE,07/18/14
        //GetPaymentMethodsByProperty,1,10,313052174,wGNG2fzbkW,IeteoCiojao2Jahshahr,Test,oakwood,13052174,RobOakWoodPropRefID,TRUE,7/18/2014
        //GetPaymentMethodsByProperty,1,11,313052174,wGNG2fzbkW,IeteoCiojao2Jahshahr,Test,oakwood,13052174,RobOakWoodPropRefID,FALSE,2014-07-18
        //GetPaymentMethodsByProperty,1,12,313052174,wGNG2fzbkW,IeteoCiojao2Jahshahr,Test,oakwood,13052174,RobOakWoodPropRefID,FALSE,07/18/14
        //GetPaymentMethodsByProperty,1,13,313052174,wGNG2fzbkW,IeteoCiojao2Jahshahr,Test,oakwood,13052174,RobOakWoodPropRefID,FALSE,7/18/2014
        testCases.add(
            new AapiTestCaseCollection(
                new Credentials(userId, username, password, null, pmId),
                "Test",
                "oakwood"
            ).add(
                new GetPaymentMethodsByProperty(
                    "Test case 5 to 13 - different format of valid created since values - active filter",
                    getExpectedResponse(gatewayErrors, "1"))
                    .setPropertyReferenceId(propertyReferenceId)
                    .setActivePaymentMethodResidents(activePaymentMethod)
                    .setCreatedSince(createdSinceValue)
            ));
      }
    }

    for (String createdSinceValue : createdSinceValues) {
      //GetPaymentMethodsByProperty,1,14,313052174,wGNG2fzbkW,IeteoCiojao2Jahshahr,Test,oakwood,13052174,RobOakWoodPropRefID,NULL,2014-07-18
      //GetPaymentMethodsByProperty,1,15,313052174,wGNG2fzbkW,IeteoCiojao2Jahshahr,Test,oakwood,13052174,RobOakWoodPropRefID,NULL,07/18/14
      //GetPaymentMethodsByProperty,1,16,313052174,wGNG2fzbkW,IeteoCiojao2Jahshahr,Test,oakwood,13052174,RobOakWoodPropRefID,NULL,7/18/2014
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password, null, pmId),
              "Test",
              "oakwood"
          ).add(
              new GetPaymentMethodsByProperty(
                  "Test case 14 to 16 - different format of valid created since values - no active filter",
                  getExpectedResponse(gatewayErrors, "1"))
                  .setPropertyReferenceId(propertyReferenceId)
                  .setCreatedSince(createdSinceValue)
          ));
    }

    final String[] futureDateValues = {
        "2020-07-18", "07/18/20", "7/18/2020",
    };

    for (String createdSinceValue : futureDateValues) {
      //GetPaymentMethodsByProperty,6,17,313052174,wGNG2fzbkW,IeteoCiojao2Jahshahr,Test,oakwood,13052174,RobOakWoodPropRefID,TRUE,2020-07-18
      //GetPaymentMethodsByProperty,6,18,313052174,wGNG2fzbkW,IeteoCiojao2Jahshahr,Test,oakwood,13052174,RobOakWoodPropRefID,TRUE,07/18/20
      //GetPaymentMethodsByProperty,6,19,313052174,wGNG2fzbkW,IeteoCiojao2Jahshahr,Test,oakwood,13052174,RobOakWoodPropRefID,TRUE,7/18/2020
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password, null, pmId),
              "Test",
              "oakwood"
          ).add(
              new GetPaymentMethodsByProperty(
                  "Test case 17 to 19 - different format of created since values",
                  getExpectedResponse(gatewayErrors, "6"))
                  .setPropertyReferenceId(propertyReferenceId)
                  .setActivePaymentMethodResidents("TRUE")
                  .setCreatedSince(createdSinceValue)
          ));
    }

    for (String createdSinceValue : futureDateValues) {
      //GetPaymentMethodsByProperty,6,23,313052174,wGNG2fzbkW,IeteoCiojao2Jahshahr,Test,oakwood,13052174,RobOakWoodPropRefID,NULL,2020-07-18
      //GetPaymentMethodsByProperty,6,24,313052174,wGNG2fzbkW,IeteoCiojao2Jahshahr,Test,oakwood,13052174,RobOakWoodPropRefID,NULL,07/18/20
      //GetPaymentMethodsByProperty,6,25,313052174,wGNG2fzbkW,IeteoCiojao2Jahshahr,Test,oakwood,13052174,RobOakWoodPropRefID,NULL,7/18/2020
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password, null, pmId),
              "Test",
              "oakwood"
          ).add(
              new GetPaymentMethodsByProperty(
                  "Test case 23/24/25 - different format of created since values",
                  getExpectedResponse(gatewayErrors, "6"))
                  .setPropertyReferenceId(propertyReferenceId)
                  .setCreatedSince(createdSinceValue)
          ));
    }

    for (String createdSinceValue : futureDateValues) {
      //GetPaymentMethodsByProperty,6,20,313052174,wGNG2fzbkW,IeteoCiojao2Jahshahr,Test,oakwood,13052174,NORESIDENTS,FALSE,2020-07-18
      //GetPaymentMethodsByProperty,6,21,313052174,wGNG2fzbkW,IeteoCiojao2Jahshahr,Test,oakwood,13052174,NORESIDENTS,FALSE,07/18/20
      //GetPaymentMethodsByProperty,6,22,313052174,wGNG2fzbkW,IeteoCiojao2Jahshahr,Test,oakwood,13052174,NORESIDENTS,FALSE,7/18/2020
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password, null, pmId),
              "Test",
              "oakwood"
          ).add(
              new GetPaymentMethodsByProperty(
                  "Test case 20/21/22 - different format of created since values",
                  getExpectedResponse(gatewayErrors, "6"))
                  .setPropertyReferenceId(propertyNoResidents)
                  .setActivePaymentMethodResidents("FALSE")
                  .setCreatedSince(createdSinceValue)
          ));
    }

    //GetPaymentMethodsByProperty,46,26,313052174,wGNG2fzbkW,IeteoCiojao2Jahshahr,Test,oakwood,13052174,badpropid,TRUE,NULL
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password, null, pmId),
            "Test",
            "oakwood"
        ).add(
            new GetPaymentMethodsByProperty(
                "Test case 26 - different format of created since values",
                getExpectedResponse(gatewayErrors, "46"))
                .setPropertyReferenceId("badpropid")
                .setActivePaymentMethodResidents("TRUE")
        ));

    executeTests(testCases);
  }
}

