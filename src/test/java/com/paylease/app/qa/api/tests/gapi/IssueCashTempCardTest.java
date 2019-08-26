
package com.paylease.app.qa.api.tests.gapi;

import com.paylease.app.qa.api.tests.TestCaseCollection;
import com.paylease.app.qa.api.tests.gapi.testcase.IssueCashTempCard;
import com.paylease.app.qa.framework.DataHelper;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.api.Credentials;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import java.util.HashMap;
import java.util.List;
import org.testng.annotations.Test;


public class IssueCashTempCardTest extends BaseTest {

  private static final String REGION = "gapi";
  private static final String FEATURE = "issueCashTempCard";

  //-----------------------------------TESTS----------------------------------------------

  @Test(groups = {"gapi", "IssueCashTempCard"})
  public void requestValidation() {
    Logger.info("IssueCashTempCard basic request validation");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String apiKey = testSetupPage.getString("apiKey");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    DataHelper dataHelper = new DataHelper();
    TestCaseCollection testCases = new GapiTestCaseCollection(
        new Credentials(gatewayId, username, password, apiKey)
    );

    //IssueCashTempCard,10,1,GAPItesterCashPay,Happy,Tester
    testCases.add(
        new IssueCashTempCard(
            dataHelper.getReferenceId(),
            "Test Case 1 - basic valid",
            getExpectedResponse(gatewayErrors, "10"))
            .setPayerFirstName("Happy")
            .setPayerLastName("Tester")
    );

    //IssueCashTempCard,10,2,GAPItesterCashPay,JeffersonBarwickBarrickNorthst,JeffersonBarwickBarrickNorthst
    testCases.add(
        new IssueCashTempCard(
            dataHelper.getReferenceId(),
            "Test Case 2 - valid - long names",
            getExpectedResponse(gatewayErrors, "10"))
            .setPayerFirstName("JeffersonBarwickBarrickNorthst")
            .setPayerLastName("JeffersonBarwickBarrickNorthst")
    );

    //IssueCashTempCard,10,3,GAPItesterCashPay,JeffersonBarwic-BarrickNorthst,JeffersonBarwic-BarrickNorthst
    testCases.add(
        new IssueCashTempCard(
            dataHelper.getReferenceId(),
            "Test Case 3 - valid - long names with punctuation",
            getExpectedResponse(gatewayErrors, "10"))
            .setPayerFirstName("JeffersonBarwic-BarrickNorthst")
            .setPayerLastName("JeffersonBarwic-BarrickNorthst")
    );

    //IssueCashTempCard,69,4,,Happy,Tester
    testCases.add(
        IssueCashTempCard.createValid(
            "",
            "Test Case 4 - empty payerReferenceId",
            getExpectedResponse(gatewayErrors, "69"))
    );

    //IssueCashTempCard,71,5,GAPItesterCashPay,,Tester
    testCases.add(
        IssueCashTempCard.createValid(
            dataHelper.getReferenceId(),
            "Test Case 5 - empty first name",
            getExpectedResponse(gatewayErrors, "71"))
            .setPayerFirstName("")
    );

    //IssueCashTempCard,72,6,GAPItesterCashPay,Happy,
    testCases.add(
        IssueCashTempCard.createValid(
            dataHelper.getReferenceId(),
            "Test Case 6 - empty last name",
            getExpectedResponse(gatewayErrors, "72"))
            .setPayerLastName("")
    );

    //IssueCashTempCard,112,7,GAPItesterCashPay,JeffersonBarwic-BarrickNorthstLONGNAME,JeffersonBarwic-BarrickNorthstLONGNAME
    testCases.add(
        IssueCashTempCard.createValid(
            dataHelper.getReferenceId(),
            "Test Case 7 - long names",
            getExpectedResponse(gatewayErrors, "112"))
            .setPayerFirstName("JeffersonBarwic-BarrickNorthstLONGNAME")
            .setPayerLastName("JeffersonBarwic-BarrickNorthstLONGNAME")
    );

    executeTests(testCases);
  }
}