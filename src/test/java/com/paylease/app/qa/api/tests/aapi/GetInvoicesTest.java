package com.paylease.app.qa.api.tests.aapi;

import com.paylease.app.qa.api.tests.TestCaseCollection;
import com.paylease.app.qa.api.tests.aapi.testcase.GetInvoices;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.UtilityManager;
import com.paylease.app.qa.framework.api.Credentials;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.testng.annotations.Test;


public class GetInvoicesTest extends BaseTest {

  private static final String REGION = "aapi";
  private static final String FEATURE = "GetInvoices";

  //-----------------------------------TESTS--------------------------------------------------------

  @Test(groups = {"aapi", "GetInvoices"})
  public void getInvoices() throws ParseException {
    Logger.info("Get Invoices Test");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();
    final String userId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String invoiceDateString = testSetupPage.getString("invoiceDate");

    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    ArrayList<TestCaseCollection> testCases = new ArrayList<>();

    UtilityManager utilityManager = new UtilityManager();

    Date invoiceDate = utilityManager.stringToDate(invoiceDateString, DATE_FORMAT);

    //GetInvoices,1,2,313052174,wGNG2fzbkW,IeteoCiojao2Jahshahr,Test,oakwood,10/10/2015,10/11/2015
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password, null, null),
            "Test",
            "oakwood"
        ).add(
            new GetInvoices(
                "Test case 1 - oakwood",
                getExpectedResponse(gatewayErrors, "1"))
                .setStartDate(invoiceDateString)
                .setEndDate(addDays(invoiceDate, 1))
        ));

    executeTests(testCases);
  }
}

