package com.paylease.app.qa.api.tests.gapi;

import com.paylease.app.qa.api.tests.TestCaseCollection;
import com.paylease.app.qa.api.tests.gapi.testcase.CreditReportingOptout;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.api.Credentials;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import java.util.HashMap;
import java.util.List;
import org.testng.annotations.Test;

public class CreditReportingOptoutTest extends BaseTest {

  private static final String REGION = "gapi";
  private static final String FEATURE = "creditReportingOptOut";

  //-----------------------------------TESTS----------------------------------------------

  @Test(groups = {"gapi", "CreditReportingOptout"})
  public void optoutUser() {
    Logger.info("CreditReportingOptout basic request validation");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String creditReportingId = testSetupPage.getString("creditReportingOptin");
    final String creditRepIdAnotherPm =
        testSetupPage.getString("creditReportingOptinForAnotherPm");

    final List<HashMap<String, Object>> gatewayErrors =
        testSetupPage.getTable("gatewayErrors");

    TestCaseCollection testCases = new GapiTestCaseCollection(
        new Credentials(gatewayId, username, password)
    );

    // Happy path
    testCases.add(
        new CreditReportingOptout("Happy path",
            getExpectedResponse(gatewayErrors, "330"))
            .setCreditReportingId(creditReportingId));

    // Blank CreditReportingId
    testCases.add(
        new CreditReportingOptout("Blank CreditReportingId",
            getExpectedResponse(gatewayErrors, "312"))
            .setCreditReportingId(""));

    // Invalid CreditReportingId
    testCases.add(
        new CreditReportingOptout("Invalid CreditReportingId",
            getExpectedResponse(gatewayErrors, "313"))
            .setCreditReportingId("asd12"));

    // Invalid CreditReportingId with '-' sign
    testCases.add(
        new CreditReportingOptout("Invalid CreditReportingId with '-' sign",
            getExpectedResponse(gatewayErrors, "313"))
            .setCreditReportingId("-12345"));

    // Credit reporting id not belonging to the pm
    testCases.add(
        new CreditReportingOptout("Credit reporting id not belonging to the pm",
            getExpectedResponse(gatewayErrors, "313"))
            .setCreditReportingId(creditRepIdAnotherPm));
  }
}
