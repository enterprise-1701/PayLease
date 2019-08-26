
package com.paylease.app.qa.api.tests.gapi;

import com.paylease.app.qa.api.tests.TestCaseCollection;
import com.paylease.app.qa.api.tests.gapi.testcase.IssueCashRentCard;
import com.paylease.app.qa.framework.DataHelper;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.api.Credentials;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import java.util.HashMap;
import java.util.List;
import org.testng.annotations.Test;


public class IssueCashRentCardTest extends BaseTest {

  private static final String REGION = "gapi";
  private static final String FEATURE = "issueCashRentCard";

  //-----------------------------------TESTS----------------------------------------------

  @Test(groups = {"gapi", "IssueCashRentCard"})
  public void requestValidation() {
    Logger.info("IssueCashRentCard basic request validation");

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

    //IssueCashRentCard,10,1,GAPItesterCashPay,Happy,Tester
    testCases.add(
        new IssueCashRentCard(
            dataHelper.getReferenceId(),
            "Test Case 1 - basic valid",
            getExpectedResponse(gatewayErrors, "10"))
            .setPayerFirstName("Happy")
            .setPayerLastName("Tester")
    );

    //IssueCashRentCard,10,2,GAPItesterCashPay,JeffersonBarwickBarrickNorthst,JeffersonBarwickBarrickNorthst
    testCases.add(
        new IssueCashRentCard(
            dataHelper.getReferenceId(),
            "Test Case 2 - valid - long names",
            getExpectedResponse(gatewayErrors, "10"))
            .setPayerFirstName("JeffersonBarwickBarrickNorthst")
            .setPayerLastName("JeffersonBarwickBarrickNorthst")
    );

    //IssueCashRentCard,10,3,GAPItesterCashPay,JeffersonBarwic-BarrickNorthst,JeffersonBarwic-BarrickNorthst
    testCases.add(
        new IssueCashRentCard(
            dataHelper.getReferenceId(),
            "Test Case 3 - valid - long names with punctuation",
            getExpectedResponse(gatewayErrors, "10"))
            .setPayerFirstName("JeffersonBarwic-BarrickNorthst")
            .setPayerLastName("JeffersonBarwic-BarrickNorthst")
    );

    //IssueCashRentCard,69,4,,Happy,Tester
    testCases.add(
        IssueCashRentCard.createValid(
            "",
            "Test Case 4 - empty payerReferenceId",
            getExpectedResponse(gatewayErrors, "69"))
    );

    //IssueCashRentCard,71,5,GAPItesterCashPay,,Tester
    testCases.add(
        IssueCashRentCard.createValid(
            dataHelper.getReferenceId(),
            "Test Case 5 - empty first name",
            getExpectedResponse(gatewayErrors, "71"))
            .setPayerFirstName("")
    );

    //IssueCashRentCard,72,6,GAPItesterCashPay,Happy,
    testCases.add(
        IssueCashRentCard.createValid(
            dataHelper.getReferenceId(),
            "Test Case 6 - empty last name",
            getExpectedResponse(gatewayErrors, "72"))
            .setPayerLastName("")
    );

    //IssueCashRentCard,112,7,GAPItesterCashPay,JeffersonBarwic-BarrickNorthstLONGNAME,JeffersonBarwic-BarrickNorthstLONGNAME
    testCases.add(
        IssueCashRentCard.createValid(
            dataHelper.getReferenceId(),
            "Test Case 7 - long names",
            getExpectedResponse(gatewayErrors, "112"))
            .setPayerFirstName("JeffersonBarwic-BarrickNorthstLONGNAME")
            .setPayerLastName("JeffersonBarwic-BarrickNorthstLONGNAME")
    );

    executeTests(testCases);
  }
}