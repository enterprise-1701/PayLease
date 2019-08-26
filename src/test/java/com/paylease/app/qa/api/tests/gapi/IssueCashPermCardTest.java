
package com.paylease.app.qa.api.tests.gapi;

import com.paylease.app.qa.api.tests.TestCaseCollection;
import com.paylease.app.qa.api.tests.gapi.testcase.IssueCashPermCard;
import com.paylease.app.qa.framework.DataHelper;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.api.Credentials;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import java.util.HashMap;
import java.util.List;
import org.testng.annotations.Test;


public class IssueCashPermCardTest extends BaseTest {

  private static final String REGION = "gapi";
  private static final String FEATURE = "issueCashPermCard";

  //-----------------------------------TESTS----------------------------------------------

  @Test(groups = {"gapi", "IssueCashPermCard"})
  public void requestValidation() {
    Logger.info("IssueCashPermCard basic request validation");

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

    //IssueCashPermCard,10,1,GAPItesterCashPay,Happy,Tester,RAND
    testCases.add(
        new IssueCashPermCard(
            dataHelper.getReferenceId(),
            "Test Case 1 - basic valid",
            getExpectedResponse(gatewayErrors, "10"))
            .setPayerFirstName("Happy")
            .setPayerLastName("Tester")
            .setCardNumber(dataHelper.getLuhnCardNumber())
    );

    //IssueCashPermCard,10,2,GAPItesterCashPay,JeffersonBarwickBarrickNorthst,JeffersonBarwickBarrickNorthst,RAND
    testCases.add(
        new IssueCashPermCard(
            dataHelper.getReferenceId(),
            "Test Case 2 - valid - long names",
            getExpectedResponse(gatewayErrors, "10"))
            .setPayerFirstName("JeffersonBarwickBarrickNorthst")
            .setPayerLastName("JeffersonBarwickBarrickNorthst")
            .setCardNumber(dataHelper.getLuhnCardNumber())
    );

    //IssueCashPermCard,10,3,GAPItesterCashPay,JeffersonBarwic-BarrickNorthst,JeffersonBarwic-BarrickNorthst,RAND
    testCases.add(
        new IssueCashPermCard(
            dataHelper.getReferenceId(),
            "Test Case 3 - valid - long names with punctuation",
            getExpectedResponse(gatewayErrors, "10"))
            .setPayerFirstName("JeffersonBarwic-BarrickNorthst")
            .setPayerLastName("JeffersonBarwic-BarrickNorthst")
            .setCardNumber(dataHelper.getLuhnCardNumber())
    );

    //IssueCashPermCard,69,4,,Happy,Tester,RAND
    testCases.add(
        IssueCashPermCard.createValid(
            "",
            "Test Case 4 - empty payerReferenceId",
            getExpectedResponse(gatewayErrors, "69"))
            .setCardNumber(dataHelper.getLuhnCardNumber())
    );

    //IssueCashPermCard,71,5,GAPItesterCashPay,,Tester,RAND
    testCases.add(
        IssueCashPermCard.createValid(
            dataHelper.getReferenceId(),
            "Test Case 5 - empty first name",
            getExpectedResponse(gatewayErrors, "71"))
            .setPayerFirstName("")
            .setCardNumber(dataHelper.getLuhnCardNumber())
    );

    //IssueCashPermCard,72,6,GAPItesterCashPay,Happy,,RAND
    testCases.add(
        IssueCashPermCard.createValid(
            dataHelper.getReferenceId(),
            "Test Case 6 - empty last name",
            getExpectedResponse(gatewayErrors, "72"))
            .setPayerLastName("")
            .setCardNumber(dataHelper.getLuhnCardNumber())
    );

    //IssueCashPermCard,112,7,GAPItesterCashPay,JeffersonBarwickBarrickNorthstLONGNAME,JeffersonBarwickBarrickNorthstLONGNAME,RAND
    testCases.add(
        IssueCashPermCard.createValid(
            dataHelper.getReferenceId(),
            "Test Case 7 - long names",
            getExpectedResponse(gatewayErrors, "112"))
            .setPayerFirstName("JeffersonBarwic-BarrickNorthstLONGNAME")
            .setPayerLastName("JeffersonBarwic-BarrickNorthstLONGNAME")
            .setCardNumber(dataHelper.getLuhnCardNumber())
    );

    //IssueCashPermCard,236,8,GAPItesterCashPay,Happy,Tester,
    testCases.add(
        IssueCashPermCard.createValid(
            dataHelper.getReferenceId(),
            "Test Case 8 - empty card number",
            getExpectedResponse(gatewayErrors, "236"))
            .setPayerFirstName("Happy")
            .setPayerLastName("Tester")
            .setCardNumber("")
    );

    String[] invalidCardNumbers = {
        "8911668149", "pvm6u6b7n4",
    };

    for (String invalidCardNumber : invalidCardNumbers) {
      //IssueCashPermCard,237,9,GAPItesterCashPay,Happy,Tester,8911668149
      //IssueCashPermCard,237,10,GAPItesterCashPay,Happy,Tester,pvm6u6b7n4
      testCases.add(
          IssueCashPermCard.createValid(
              dataHelper.getReferenceId(),
              "Test Case 9/10 - invalid card number",
              getExpectedResponse(gatewayErrors, "237"))
              .setPayerFirstName("Happy")
              .setPayerLastName("Tester")
              .setCardNumber(invalidCardNumber)
      );
    }

    executeTests(testCases);
  }
}