package com.paylease.app.qa.api.tests.gapi;

import com.paylease.app.qa.api.tests.TestCaseCollection;
import com.paylease.app.qa.api.tests.gapi.testcase.CreditReportingOptIn;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.UtilityManager;
import com.paylease.app.qa.framework.api.Credentials;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.testng.annotations.Test;

public class CreditReportingOptinTest extends BaseTest {

  private static final String REGION = "gapi";
  private static final String FEATURE = "creditReportingOptIn";

  //-----------------------------------TESTS----------------------------------------------

  @Test(groups = {"gapi", "CreditReportingOptin"})
  public void optinUser() {
    Logger.info("CreditReportingOptin request validation with lease date parameter");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    UtilityManager um = new UtilityManager();
    Date temp = um.addDays(new Date(), 365);
    String futureDate = um.dateToString(temp, "MM/dd/yyyy");

    TestCaseCollection testCases = new GapiTestCaseCollection(
        new Credentials(gatewayId, username, password)
    );

    // Happy path
    testCases.add(
        new CreditReportingOptIn("Happy path",
            getExpectedResponse(gatewayErrors, "315"))
            .setFirstName("Jason")
            .setLastName("Holder")
            .setStreetAddress("Wonderful st")
            .setCity("San Diego")
            .setState("CA")
            .setZip("92121")
            .setLeaseEndDate(futureDate)
            .setSSN("789457896")
            .setBirthDate("03/03/1989")
    );

    // First name blank
    testCases.add(
        CreditReportingOptIn.createValidWithLeaseEndDate("First name blank",
            getExpectedResponse(gatewayErrors, "281"))
            .setFirstName("")
    );

    // First name exceeds 100 Characters
    testCases.add(
        CreditReportingOptIn.createValidWithLeaseEndDate(
            "First name exceeds 100 Characters",
            getExpectedResponse(gatewayErrors, "282"))
            .setFirstName("JasonJasonJasonJasonJasonJasonJasonJasonJasonJason"
                + "JasonJasonJasonJasonJasonJasonJasonJasonJasonJasonJason")
    );

    // Last name blank
    testCases.add(
        CreditReportingOptIn.createValidWithLeaseEndDate("Last name blank",
            getExpectedResponse(gatewayErrors, "284"))
            .setLastName("")
    );

    // Last name exceeds 100 characters
    testCases.add(
        CreditReportingOptIn.createValidWithLeaseEndDate(
            "Last name exceeds 100 characters",
            getExpectedResponse(gatewayErrors, "285"))
            .setLastName("RoachRoachRoachRoachRoachRoachRoachRoachRoachRoach"
                + "RoachRoachRoachRoachRoachRoachRoachRoachRoachRoachRoach")
    );

    // Street addreess blank
    testCases.add(
        CreditReportingOptIn.createValidWithLeaseEndDate("Street addreess blank",
            getExpectedResponse(gatewayErrors, "287"))
            .setStreetAddress("")
    );

    // City blank
    testCases.add(
        CreditReportingOptIn.createValidWithLeaseEndDate("City blank",
            getExpectedResponse(gatewayErrors, "289"))
            .setCity("")
    );

    // City value exceeds 250 characters
    testCases.add(
        CreditReportingOptIn.createValidWithLeaseEndDate(
            "City value exceeds 250 characters",
            getExpectedResponse(gatewayErrors, "290"))
            .setCity("San Diego San Diego San Diego San Diego San Diego San Diego "
                + "San Diego San Diego San Diego San Diego San Diego San Diego San Diego San Diego "
                + "San Diego San Diego San Diego San Diego San Diego San Diego San Diego San Diego "
                + "San Diego San Diego San Diego San Diego ")
    );

    // State blank
    testCases.add(
        CreditReportingOptIn.createValidWithLeaseEndDate("State blank",
            getExpectedResponse(gatewayErrors, "292"))
            .setState("")
    );
    // Invalid state - more than 2 characters
    testCases.add(
        CreditReportingOptIn.createValidWithLeaseEndDate(
            "Invalid state - more than 2 characters",
            getExpectedResponse(gatewayErrors, "293"))
            .setState("CAD")
    );

    // Invalid state - 2 character value which is not a state
    testCases.add(
        CreditReportingOptIn.createValidWithLeaseEndDate(
            "Invalid state - 2 character value which is not a state",
            getExpectedResponse(gatewayErrors, "294"))
            .setState("PP")
    );

    // Zip blank
    testCases.add(
        CreditReportingOptIn.createValidWithLeaseEndDate("Zip blank",
            getExpectedResponse(gatewayErrors, "296"))
            .setZip("")
    );

    // Invalid zip - less than 5 digits
    testCases.add(
        CreditReportingOptIn.createValidWithLeaseEndDate(
            "Invalid zip - less than 5 digits",
            getExpectedResponse(gatewayErrors, "297"))
            .setZip("2154")
    );

    // Invalid zip - more than 5 digits
    testCases.add(
        CreditReportingOptIn.createValidWithLeaseEndDate(
            "Invalid zip - more than 5 digits",
            getExpectedResponse(gatewayErrors, "297"))
            .setZip("215489")
    );

    // Invalid zip - 5 digit invalid zip
    testCases.add(
        CreditReportingOptIn.createValidWithLeaseEndDate(
            "Invalid zip - 5 digit invalid zip",
            getExpectedResponse(gatewayErrors, "298"))
            .setZip("45a87")
    );

    // Lease End date blank
    testCases.add(
        CreditReportingOptIn.createValidWithLeaseEndDate("Lease End date blank",
            getExpectedResponse(gatewayErrors, "300"))
            .setLeaseEndDate("")
    );

    // Lease End date invalid
    testCases.add(
        CreditReportingOptIn.createValidWithLeaseEndDate("Lease End date invalid",
            getExpectedResponse(gatewayErrors, "301"))
            .setLeaseEndDate("13/32/2020")
    );

    // Lease End date value in past
    testCases.add(
        CreditReportingOptIn.createValidWithLeaseEndDate(
            "Lease End date value in past",
            getExpectedResponse(gatewayErrors, "303"))
            .setLeaseEndDate("12/02/2015")
    );

    // SSN blank
    testCases.add(
        CreditReportingOptIn.createValidWithLeaseEndDate("SSN blank",
            getExpectedResponse(gatewayErrors, "305"))
            .setSSN("")
    );

    // Invalid SSN - less than 9 digits
    testCases.add(
        CreditReportingOptIn.createValidWithLeaseEndDate(
            "Invalid SSN - less than 9 digits",
            getExpectedResponse(gatewayErrors, "306"))
            .setSSN("12312312")
    );

    // Invalid SSN - more than 9 digits
    testCases.add(
        CreditReportingOptIn.createValidWithLeaseEndDate(
            "Invalid SSN - more than 9 digits",
            getExpectedResponse(gatewayErrors, "306"))
            .setSSN("1231231221")
    );

    // Birth date blank
    testCases.add(
        CreditReportingOptIn.createValidWithLeaseEndDate("Birth date blank",
            getExpectedResponse(gatewayErrors, "308"))
            .setBirthDate("")
    );

    // Birth date invalid
    testCases.add(
        CreditReportingOptIn.createValidWithLeaseEndDate("Birth date invalid",
            getExpectedResponse(gatewayErrors, "309"))
            .setBirthDate("21/02/1989")
    );

    // Birth date in future
    testCases.add(
        CreditReportingOptIn.createValidWithLeaseEndDate("Birth date in future",
            getExpectedResponse(gatewayErrors, "311"))
            .setBirthDate(futureDate)
    );

    // First Name less than 2 characters
    testCases.add(
        CreditReportingOptIn.createValidWithLeaseEndDate(
            "First Name less than 2 characters",
            getExpectedResponse(gatewayErrors, "323"))
            .setFirstName("A")
    );

    // Last Name less than 2 characters
    testCases.add(
        CreditReportingOptIn.createValidWithLeaseEndDate(
            "Last Name less than 2 characters",
            getExpectedResponse(gatewayErrors, "324"))
            .setLastName("A")
    );

    // Happy path with MonthToMonth flag set to yes
    testCases.add(
        CreditReportingOptIn.createValidMonthToMonth(
            "Happy path with MonthToMonth flag set to yes",
            getExpectedResponse(gatewayErrors, "315"))
    );

    // MonthToMonth blank
    testCases.add(
        CreditReportingOptIn.createValidMonthToMonth("MonthToMonth blank",
            getExpectedResponse(gatewayErrors, "335"))
            .setMonthToMonth("")
    );

    // Request with Lease end date and MonthToMonth flag set to yes
    testCases.add(
        CreditReportingOptIn.createValidMonthToMonth(
            "Request with Lease end date and MonthToMonth flag set to yes",
            getExpectedResponse(gatewayErrors, "337"))
            .setLeaseEndDate(futureDate)
    );

    // Request with no MonthToMonth flag and no lease end date
    testCases.add(
        CreditReportingOptIn.createPartial(
            "Request with no MonthToMonth flag and no lease end date",
            getExpectedResponse(gatewayErrors, "299"))
    );

    // Request with MonthToMonth flag set to no and valid lease end date
    testCases.add(
        CreditReportingOptIn.createValidWithLeaseEndDate(
            "Request with MonthToMonth flag set to no and valid lease end date",
            getExpectedResponse(gatewayErrors, "315"))
            .setMonthToMonth("No")
    );

    executeTests(testCases);

  }

  @Test(groups = {"gapi", "CreditReportingOptin"})
  public void optinExistingUser() {
    Logger.info("CreditReportingOptin request validation with MonthToMonth flag");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc2");
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

    // Opting in user with CreditReportingId
    testCases.add(
        CreditReportingOptIn.createValidWithLeaseEndDate(
            "Opting in user with CreditReportingId",
            getExpectedResponse(gatewayErrors, "321"))
            .setOptinId(creditReportingId)
    );

    // CreditReportingId blank
    testCases.add(
        CreditReportingOptIn.createValidWithLeaseEndDate("CreditReportingId blank",
            getExpectedResponse(gatewayErrors, "312"))
            .setOptinId("")
    );

    // Invalid CreditReportingId
    testCases.add(
        CreditReportingOptIn.createValidWithLeaseEndDate("Invalid CreditReportingId",
            getExpectedResponse(gatewayErrors, "313"))
            .setOptinId("asd123")
    );

    // CreditReportingId not belonging to the pm
    testCases.add(
        CreditReportingOptIn.createValidWithLeaseEndDate(
            "CreditReportingId not belonging to the pm",
            getExpectedResponse(gatewayErrors, "314"))
            .setOptinId(creditRepIdAnotherPm)
    );

    executeTests(testCases);
  }
}
