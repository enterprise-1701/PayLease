package com.paylease.app.qa.api.tests.aapi;

import com.paylease.app.qa.api.tests.TestCaseCollection;
import com.paylease.app.qa.api.tests.aapi.testcase.GetDeposits;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.UtilityManager;
import com.paylease.app.qa.framework.api.Credentials;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.testng.annotations.Test;

public class GetDepositsTest extends BaseTest {

  private static final String REGION = "aapi";
  private static final String FEATURE = "GetDeposits";

  private static final String NEW_FORMAT = "MM-dd-yyyy";

  //-----------------------------------TESTS--------------------------------------------------------

  @Test(groups = {"aapi", "GetDeposits"})
  public void getDeposits() throws ParseException {
    Logger.info("Get Deposits Test");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.skipTestIfItsWeekend();
    testSetupPage.open();
    final String userId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String pmId = testSetupPage.getString("pmId");
    final String depositDateString = testSetupPage.getString("depositDate");
    final String propertyReferenceId = testSetupPage.getString("propNumber");

    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    ArrayList<TestCaseCollection> testCases = new ArrayList<>();

    UtilityManager utilityManager = new UtilityManager();

    Date depositDate = utilityManager.stringToDate(depositDateString, DATE_FORMAT);

    //GetDeposits,1,1,312189211,8dXGX4NnEw,IeteoCiojao2Jahshahr,Test,oakwood,08/28/2016,08/31/2016,NULL
    //GetDeposits,1,3,322437014,qtCZYvXjc9,IeteoCiojao2Jahshahr,Test,oakwood,08/28/2016,08/31/2016,NULL
    //GetDeposits,1,4,312189211,8dXGX4NnEw,IeteoCiojao2Jahshahr,Test,oakwood,08/12/2016,08/15/2016,NULL
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password, null, pmId),
            "Test",
            "oakwood"
        ).add(
            new GetDeposits(
                "Test case 1/3/4 - propertyRefId null & 3 day search",
                getExpectedResponse(gatewayErrors, "1"))
                .setStartDate(depositDateString)
                .setEndDate(addDays(depositDate, 3))
        ));

    //GetDeposits,1,2,312189211,8dXGX4NnEw,IeteoCiojao2Jahshahr,Test,oakwood,7/1/2016,7/1/2016,NULL
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password),
            "Test",
            "oakwood"
        ).add(
            new GetDeposits(
                "Test case 2 - propertyRefId null & 1 day search",
                getExpectedResponse(gatewayErrors, "1"))
                .setStartDate(depositDateString)
                .setEndDate(addDays(depositDate, 0))
        ));

    //GetDeposits,1,5,312189211,8dXGX4NnEw,IeteoCiojao2Jahshahr,Test,oakwood,08/12/2016,08/15/2016,
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password),
            "Test",
            "oakwood"
        ).add(
            new GetDeposits(
                "Test case 5 - propertyRefId empty & 3 day search",
                getExpectedResponse(gatewayErrors, "1"))
                .setPropertyReferenceId("")
                .setStartDate(depositDateString)
                .setEndDate(addDays(depositDate, 3))
        ));

    //GetDeposits,114,6,312189211,8dXGX4NnEw,IeteoCiojao2Jahshahr,Test,oakwood,08-12-2016,08/15/2016,1306
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password),
            "Test",
            "oakwood"
        ).add(
            new GetDeposits(
                "Test case 6 - wrong start date format",
                getExpectedResponse(gatewayErrors, "114"))
                .setPropertyReferenceId(propertyReferenceId)
                .setStartDate(changeDateFormat(depositDateString))
                .setEndDate(addDays(depositDate, 3))
        ));

    //GetDeposits,115,7,312189211,8dXGX4NnEw,IeteoCiojao2Jahshahr,Test,oakwood,08/10/2016,08-15-2016,1306
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password),
            "Test",
            "oakwood"
        ).add(
            new GetDeposits(
                "Test case 7 - wrong end date format",
                getExpectedResponse(gatewayErrors, "115"))
                .setPropertyReferenceId(propertyReferenceId)
                .setStartDate(depositDateString)
                .setEndDate(changeDateFormat(depositDateString))
        ));

    //GetDeposits,116,8,312189211,8dXGX4NnEw,IeteoCiojao2Jahshahr,Test,oakwood,08/12/2016,08/15/2016,lksadhfouhasoufn2ljhasuon2jfnwkf2afsohasfuiasfdlksadhfouhasoufn2ljhasuon2jfnwkf2afsohasfuiasfd4321dfj
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password),
            "Test",
            "oakwood"
        ).add(
            new GetDeposits(
                "Test case 8 - Large property reference id",
                getExpectedResponse(gatewayErrors, "116"))
                .setPropertyReferenceId(
                    "lksadhfouhasoufn2ljhasuon2jfnwkf2afsohasfuiasfdlksadhfouhasoufn2ljhasuon2jfnwkf2afsohasfuiasfd4321dfj")
                .setStartDate(depositDateString)
                .setEndDate(addDays(depositDate, 3))
        ));

    //GetDeposits,117,9,312189211,8dXGX4NnEw,IeteoCiojao2Jahshahr,Test,oakwood,08/12/2010,08/15/2010,1306
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password),
            "Test",
            "oakwood"
        ).add(
            new GetDeposits(
                "Test case 9 - no deposits",
                getExpectedResponse(gatewayErrors, "117"))
                .setPropertyReferenceId(propertyReferenceId)
                .setStartDate("08/12/2010")
                .setEndDate("08/15/2010")
        ));

    //GetDeposits,118,10,312189211,8dXGX4NnEw,IeteoCiojao2Jahshahr,Test,oakwood,08/10/2016,08/15/2016,1306
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password),
            "Test",
            "oakwood"
        ).add(
            new GetDeposits(
                "Test case 10 - date range too big ",
                getExpectedResponse(gatewayErrors, "118"))
                .setPropertyReferenceId(propertyReferenceId)
                .setStartDate(depositDateString)
                .setEndDate(addDays(depositDate,  5))
        ));

    executeTests(testCases);
  }

  //------------------------------------Test Method-------------------------------------------------

  private String changeDateFormat(String depositDate) throws ParseException {

    SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
    Date date = dateFormat.parse(depositDate);

    dateFormat.applyPattern(NEW_FORMAT);

    return dateFormat.format(date);
  }

}

