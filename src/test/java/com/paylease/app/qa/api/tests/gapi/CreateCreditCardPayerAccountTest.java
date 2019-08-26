package com.paylease.app.qa.api.tests.gapi;

import com.paylease.app.qa.api.tests.TestCaseCollection;
import com.paylease.app.qa.api.tests.gapi.testcase.AccountPayment;
import com.paylease.app.qa.api.tests.gapi.testcase.CreateCreditCardPayerAccount;
import com.paylease.app.qa.framework.DataHelper;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.api.Credentials;
import com.paylease.app.qa.framework.api.Response;
import com.paylease.app.qa.framework.api.gapi.GapiRequest;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;


public class CreateCreditCardPayerAccountTest extends BaseTest {

  private static final String REGION = "gapi";
  private static final String FEATURE = "createCreditCardPayerAccount";

  //-----------------------------------TESTS----------------------------------------------

  @Test(groups = {"gapi", "CreateCreditCardPayerAccount", "litle"})
  public void userRecordWithPrimaryAccountOnly() throws Exception {
    userRecordWithNoGatewayPayerRecord("tc2");
  }

  private void userRecordWithNoGatewayPayerRecord(String testCase) throws Exception {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();

    // Credentials
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");

    // ResidentInfo
    final String createdResidentUserId = testSetupPage.getString("userId");
    final String payerRefId = testSetupPage.getString("payerRefId");
    final String firstName = testSetupPage.getString("firstName");
    final String lastName = testSetupPage.getString("lastName");
    final String creditCardType = testSetupPage.getString("creditCardType");
    final String creditCardNumber = testSetupPage.getString("creditCardNumber");
    final String creditCardCvv = testSetupPage.getString("creditCardCvv");
    final String creditCardExpMonth = testSetupPage.getString("creditCardExpMonth");
    final String creditCardExpYear = testSetupPage.getString("creditCardExpYear");
    final String billingFirstName = testSetupPage.getString("firstName");
    final String billingLastName = testSetupPage.getString("lastName");
    final String billingAddress = testSetupPage.getString("billingAddress");
    final String billingCity = testSetupPage.getString("billingCity");
    final String billingState = testSetupPage.getString("billingState");
    final String billingCountry = testSetupPage.getString("billingCountry");
    final String billingZip = testSetupPage.getString("billingZip");

    Credentials credentials = new Credentials(gatewayId, username, password);
    GapiRequest gapiRequest = new GapiRequest(credentials);
    DataHelper dataHelper = new DataHelper();

    CreateCreditCardPayerAccount createCreditCardPayerAccount = new CreateCreditCardPayerAccount(
        dataHelper.getReferenceId(),
        "Test Case - Resident Record With no Gateway Payer Record",
        null
    );

    createCreditCardPayerAccount
        .setPayerReferenceId(payerRefId)
        .setPayerFirstName(firstName)
        .setPayerLastName(lastName)
        .setCreditCardType(creditCardType)
        .setCreditCardNumber(creditCardNumber)
        .setCreditCardExpMonth(creditCardExpMonth)
        .setCreditCardExpYear(creditCardExpYear)
        .setCreditCardCvv2(creditCardCvv)
        .setBillingFirstName(billingFirstName)
        .setBillingLastName(billingLastName)
        .setBillingStreetAddress(billingAddress)
        .setBillingCity(billingCity)
        .setBillingState(billingState)
        .setBillingCountry(billingCountry)
        .setBillingZip(billingZip);

    createCreditCardPayerAccount.addTransaction(gapiRequest);

    Response response = gapiRequest.sendRequest();
    response.setIndex("1");

    String gatewayPayerId = response.getSpecificElementValue("GatewayPayerId");

    String userId = validateGapiResponse(gatewayPayerId);
    Assert.assertEquals(userId, createdResidentUserId,
        "Gateway Payer Record was not created using existing resident");
  }

  @Test(groups = {"gapi", "CreateCreditCardPayerAccount", "litle"})
  public void requestValidation() {
    Logger.info("CreateCreditCardPayerAccount basic request validation");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    DataHelper dataHelper = new DataHelper();
    TestCaseCollection testCases = new GapiTestCaseCollection(
        new Credentials(gatewayId, username, password)
    );

    //CreateCreditCardPayerAccount,10,1,GAPITesterCC,Bonnie,Williams,Visa,4457010000000009,01,99,365,Bonnie,Williams,123 Fake Street,San Diego,CA,US,92121
    testCases.add(
        new CreateCreditCardPayerAccount(
            dataHelper.getReferenceId(),
            "Test Case 1 - Basic Valid - Visa",
            getExpectedResponse(gatewayErrors, "10"))
            .setPayerFirstName("Bonnie")
            .setPayerLastName("Williams")
            .setCreditCardType("Visa")
            .setCreditCardNumber("4457010000000009")
            .setCreditCardExpMonth("01")
            .setCreditCardExpYear("99")
            .setCreditCardCvv2("365")
            .setBillingFirstName("Bonnie")
            .setBillingLastName("Williams")
            .setBillingStreetAddress("123 Fake Street")
            .setBillingCity("San Diego")
            .setBillingState("CA")
            .setBillingCountry("US")
            .setBillingZip("92121")
    );

    //CreateCreditCardPayerAccount,10,2,GAPITesterCC,Bonnie,Williams,Amex,371449635398431,01,99,3654,Bonnie,Williams,123 Fake Street,San Diego,CA,US,92121
    testCases.add(
        new CreateCreditCardPayerAccount(
            dataHelper.getReferenceId(),
            "Test Case 2 - Basic Valid - Amex",
            getExpectedResponse(gatewayErrors, "10"))
            .setPayerFirstName("Bonnie")
            .setPayerLastName("Williams")
            .setCreditCardType("Amex")
            .setCreditCardNumber("375001000000005")
            .setCreditCardExpMonth("01")
            .setCreditCardExpYear("99")
            .setCreditCardCvv2("3654")
            .setBillingFirstName("Bonnie")
            .setBillingLastName("Williams")
            .setBillingStreetAddress("4 Main St.")
            .setBillingCity("Laurel")
            .setBillingState("CA")
            .setBillingCountry("US")
            .setBillingZip("92121")
    );

    //CreateCreditCardPayerAccount,10,3,GAPITesterCC,Bonnie,Williams,Discover,6011000995500000,01,99,365,Bonnie,Williams,123 Fake Street,San Diego,CA,US,92121
    testCases.add(
        new CreateCreditCardPayerAccount(
            dataHelper.getReferenceId(),
            "Test Case 3 - Basic Valid - Discover",
            getExpectedResponse(gatewayErrors, "10"))
            .setPayerFirstName("Bonnie")
            .setPayerLastName("Williams")
            .setCreditCardType("Discover")
            .setCreditCardNumber("6011000995500000")
            .setCreditCardExpMonth("01")
            .setCreditCardExpYear("99")
            .setCreditCardCvv2("365")
            .setBillingFirstName("Bonnie")
            .setBillingLastName("Williams")
            .setBillingStreetAddress("123 Fake Street")
            .setBillingCity("San Diego")
            .setBillingState("CA")
            .setBillingCountry("US")
            .setBillingZip("92121")
    );

    //CreateCreditCardPayerAccount,10,4,GAPITesterCC,Bonnie,Williams,MasterCard,5454545454545454,01,99,365,Bonnie,Williams,123 Fake Street,San Diego,CA,US,92121
    testCases.add(
        new CreateCreditCardPayerAccount(
            dataHelper.getReferenceId(),
            "Test Case 4 - Basic Valid - MasterCard",
            getExpectedResponse(gatewayErrors, "10"))
            .setPayerFirstName("Bonnie")
            .setPayerLastName("Williams")
            .setCreditCardType("MasterCard")
            .setCreditCardNumber("5112000100000003")
            .setCreditCardExpMonth("01")
            .setCreditCardExpYear("99")
            .setCreditCardCvv2("365")
            .setBillingFirstName("Bonnie")
            .setBillingLastName("Williams")
            .setBillingStreetAddress("123 Fake Street")
            .setBillingCity("San Diego")
            .setBillingState("CA")
            .setBillingCountry("US")
            .setBillingZip("92121")
    );

    //CreateCreditCardPayerAccount,10,5,GAPITesterCC,Bonnie,Williams,MasterCard,5454545454545454,1,99,365,Bonnie,Williams,123 Fake Street,San Diego,CA,US,92121
    testCases.add(
        new CreateCreditCardPayerAccount(
            dataHelper.getReferenceId(),
            "Test Case 5 - Basic Valid - Mastercard - single digit exp month",
            getExpectedResponse(gatewayErrors, "10"))
            .setPayerFirstName("Bonnie")
            .setPayerLastName("Williams")
            .setCreditCardType("MasterCard")
            .setCreditCardNumber("5112000100000003")
            .setCreditCardExpMonth("1")
            .setCreditCardExpYear("99")
            .setCreditCardCvv2("365")
            .setBillingFirstName("Bonnie")
            .setBillingLastName("Williams")
            .setBillingStreetAddress("123 Fake Street")
            .setBillingCity("San Diego")
            .setBillingState("CA")
            .setBillingCountry("US")
            .setBillingZip("92121")
    );

    //CreateCreditCardPayerAccount,10,6,GAPITesterCC,Bonnie,Williams,MasterCard,5454545454545454,1,99,,Bonnie,Williams,123 Fake Street,San Diego,CA,US,92121
    testCases.add(
        new CreateCreditCardPayerAccount(
            dataHelper.getReferenceId(),
            "Test Case 6 - Basic Valid - Mastercard - no cvv2",
            getExpectedResponse(gatewayErrors, "10"))
            .setPayerFirstName("Bonnie")
            .setPayerLastName("Williams")
            .setCreditCardType("MasterCard")
            .setCreditCardNumber("5112000100000003")
            .setCreditCardExpMonth("1")
            .setCreditCardExpYear("99")
            .setCreditCardCvv2("")
            .setBillingFirstName("Bonnie")
            .setBillingLastName("Williams")
            .setBillingStreetAddress("123 Fake Street")
            .setBillingCity("San Diego")
            .setBillingState("CA")
            .setBillingCountry("US")
            .setBillingZip("92121")
    );

    //CreateCreditCardPayerAccount,10,7,GAPITesterCC,K!a1@t#h$r4y^n&,B4*r(o4)wn,MasterCard,5454545454545454,1,99,,K!a1@t#h$r4y^n&,B4*r(o4)wn,123 Fake Street,San Diego,CA,US,92121
    testCases.add(
        new CreateCreditCardPayerAccount(
            dataHelper.getReferenceId(),
            "Test Case 7 - Basic Valid - Mastercard - weird names",
            getExpectedResponse(gatewayErrors, "10"))
            .setPayerFirstName("K!a1@t#h$r4y^n&")
            .setPayerLastName("B4*r(o4)wn")
            .setCreditCardType("MasterCard")
            .setCreditCardNumber("5112000100000003")
            .setCreditCardExpMonth("1")
            .setCreditCardExpYear("99")
            .setCreditCardCvv2("")
            .setBillingFirstName("K!a1@t#h$r4y^n&")
            .setBillingLastName("B4*r(o4)wn")
            .setBillingStreetAddress("123 Fake Street")
            .setBillingCity("San Diego")
            .setBillingState("CA")
            .setBillingCountry("US")
            .setBillingZip("92121")
    );

    //CreateCreditCardPayerAccount,10,8,GAPITesterCC,Bonnie,Williams,MasterCard,5454545454545454,1,99,,jeffersonBarwickBarrickNorthstAlistaireBenjaminaasJeffersonBarwickBarrickNorthstAlistaireBenjaminaas,jeffersonBarwickBarrickNorthstAlistaireBenjaminaasJeffersonBarwickBarrickNorthstAlistaireBenjaminaas,123 Fake Street,San Diego,CA,US,92121
    testCases.add(
        new CreateCreditCardPayerAccount(
            dataHelper.getReferenceId(),
            "Test Case 8 - Basic Valid - Mastercard - long names",
            getExpectedResponse(gatewayErrors, "10"))
            .setPayerFirstName("Bonnie")
            .setPayerLastName("Williams")
            .setCreditCardType("MasterCard")
            .setCreditCardNumber("5112000100000003")
            .setCreditCardExpMonth("1")
            .setCreditCardExpYear("99")
            .setCreditCardCvv2("")
            .setBillingFirstName(
                "jeffersonBarwickBarrickNorthstAlistaireBenjaminaasJeffersonBarwickBarrickNorthstAlistaireBenjaminaas")
            .setBillingLastName(
                "jeffersonBarwickBarrickNorthstAlistaireBenjaminaasJeffersonBarwickBarrickNorthstAlistaireBenjaminaas")
            .setBillingStreetAddress("123 Fake Street")
            .setBillingCity("San Diego")
            .setBillingState("CA")
            .setBillingCountry("US")
            .setBillingZip("92121")
    );

    //CreateCreditCardPayerAccount,10,9,GAPITesterCC,Bonnie,Williams,MasterCard,5454545454545454,11,99,365,Bonnie,Williams,123 ersonBarwickBarrickNorthstAlistaireBenjaminaasJeffersonBarwickBarrickNorthstAlistaireBenjaminaas JeffersonBarwickBarrickNorthstAlistaireBenjaminaasJeffersonBarwickBarrickNorthstAlistaireBenjam Ave,San Diego,CA,US,92121
    testCases.add(
        new CreateCreditCardPayerAccount(
            dataHelper.getReferenceId(),
            "Test Case 9 - Basic Valid - Mastercard - long address",
            getExpectedResponse(gatewayErrors, "10"))
            .setPayerFirstName("Bonnie")
            .setPayerLastName("Williams")
            .setCreditCardType("MasterCard")
            .setCreditCardNumber("5112000100000003")
            .setCreditCardExpMonth("11")
            .setCreditCardExpYear("99")
            .setCreditCardCvv2("365")
            .setBillingFirstName("Bonnie")
            .setBillingLastName("Williams")
            .setBillingStreetAddress(
                "123 ersonBarwickBarrickNorthstAlistaireBenjaminaasJeffersonBarwickBarrickNorthstAlistaireBenjaminaas JeffersonBarwickBarrickNorthstAlistaireBenjaminaasJeffersonBarwickBarrickNorthstAlistaireBenjam Ave")
            .setBillingCity("San Diego")
            .setBillingState("CA")
            .setBillingCountry("US")
            .setBillingZip("92121")
    );

    //CreateCreditCardPayerAccount,10,10,GAPITesterCC,Bonnie,Williams,Visa,4444424444444440,01,99,369,Bonnie,Williams,123 Fake Street,San Diego,CA,US,92121
    testCases.add(
        new CreateCreditCardPayerAccount(
            dataHelper.getReferenceId(),
            "Test Case 10 - Basic Valid - Visa - different card number",
            getExpectedResponse(gatewayErrors, "10"))
            .setPayerFirstName("Bonnie")
            .setPayerLastName("Williams")
            .setCreditCardType("Visa")
            .setCreditCardNumber("4444424444444440")
            .setCreditCardExpMonth("01")
            .setCreditCardExpYear("99")
            .setCreditCardCvv2("369")
            .setBillingFirstName("Bonnie")
            .setBillingLastName("Williams")
            .setBillingStreetAddress("123 Fake Street")
            .setBillingCity("San Diego")
            .setBillingState("CA")
            .setBillingCountry("US")
            .setBillingZip("92121")
    );

    //CreateCreditCardPayerAccount,10,11,GAPITesterCC,Bonnie,Williams,Visa,4444424444444440,1,99,369,Bonnie,Williams,123 Fake Street,San Diego,CA,US,92121
    testCases.add(
        new CreateCreditCardPayerAccount(
            dataHelper.getReferenceId(),
            "Test Case 11 - Basic Valid - Visa - single digit exp month",
            getExpectedResponse(gatewayErrors, "10"))
            .setPayerFirstName("Bonnie")
            .setPayerLastName("Williams")
            .setCreditCardType("Visa")
            .setCreditCardNumber("4444424444444440")
            .setCreditCardExpMonth("1")
            .setCreditCardExpYear("99")
            .setCreditCardCvv2("369")
            .setBillingFirstName("Bonnie")
            .setBillingLastName("Williams")
            .setBillingStreetAddress("123 Fake Street")
            .setBillingCity("San Diego")
            .setBillingState("CA")
            .setBillingCountry("US")
            .setBillingZip("92121")
    );

    //CreateCreditCardPayerAccount,10,12,GAPITesterCC,Bonnie,Williams,Visa,4444424444444440,1,99,,Bonnie,Williams,123 Fake Street,San Diego,CA,US,92121
    testCases.add(
        new CreateCreditCardPayerAccount(
            dataHelper.getReferenceId(),
            "Test Case 12 - Basic Valid - Visa - empty cvv2",
            getExpectedResponse(gatewayErrors, "10"))
            .setPayerFirstName("Bonnie")
            .setPayerLastName("Williams")
            .setCreditCardType("Visa")
            .setCreditCardNumber("4444424444444440")
            .setCreditCardExpMonth("1")
            .setCreditCardExpYear("99")
            .setCreditCardCvv2("")
            .setBillingFirstName("Bonnie")
            .setBillingLastName("Williams")
            .setBillingStreetAddress("123 Fake Street")
            .setBillingCity("San Diego")
            .setBillingState("CA")
            .setBillingCountry("US")
            .setBillingZip("92121")
    );

    //CreateCreditCardPayerAccount,10,13,GAPITesterCC,Bonnie,Williams,Discover,6011000995500000,01,99,136,Bonnie,Williams,123 Fake Street,San Diego,CA,US,92121
    testCases.add(
        new CreateCreditCardPayerAccount(
            dataHelper.getReferenceId(),
            "Test Case 13 - Basic Valid - Discover - different card number",
            getExpectedResponse(gatewayErrors, "10"))
            .setPayerFirstName("Bonnie")
            .setPayerLastName("Williams")
            .setCreditCardType("Discover")
            .setCreditCardNumber("6011000995500000")
            .setCreditCardExpMonth("01")
            .setCreditCardExpYear("99")
            .setCreditCardCvv2("136")
            .setBillingFirstName("Bonnie")
            .setBillingLastName("Williams")
            .setBillingStreetAddress("123 Fake Street")
            .setBillingCity("San Diego")
            .setBillingState("CA")
            .setBillingCountry("US")
            .setBillingZip("92121")
    );

    //CreateCreditCardPayerAccount,10,14,GAPITesterCC,Bonnie,Williams,Discover,6011000995500000,1,99,136,Bonnie,Williams,123 Fake Street,San Diego,CA,US,92121
    testCases.add(
        new CreateCreditCardPayerAccount(
            dataHelper.getReferenceId(),
            "Test Case 14 - Basic Valid - Discover - single digit exp month",
            getExpectedResponse(gatewayErrors, "10"))
            .setPayerFirstName("Bonnie")
            .setPayerLastName("Williams")
            .setCreditCardType("Discover")
            .setCreditCardNumber("6011000995500000")
            .setCreditCardExpMonth("1")
            .setCreditCardExpYear("99")
            .setCreditCardCvv2("136")
            .setBillingFirstName("Bonnie")
            .setBillingLastName("Williams")
            .setBillingStreetAddress("123 Fake Street")
            .setBillingCity("San Diego")
            .setBillingState("CA")
            .setBillingCountry("US")
            .setBillingZip("92121")
    );

    //CreateCreditCardPayerAccount,10,15,GAPITesterCC,Bonnie,Williams,Discover,6011000995500000,1,99,,Bonnie,Williams,123 Fake Street,San Diego,CA,US,92121
    testCases.add(
        new CreateCreditCardPayerAccount(
            dataHelper.getReferenceId(),
            "Test Case 15 - Basic Valid - Discover - empty cvv2",
            getExpectedResponse(gatewayErrors, "10"))
            .setPayerFirstName("Bonnie")
            .setPayerLastName("Williams")
            .setCreditCardType("Discover")
            .setCreditCardNumber("6011000995500000")
            .setCreditCardExpMonth("1")
            .setCreditCardExpYear("99")
            .setCreditCardCvv2("")
            .setBillingFirstName("Bonnie")
            .setBillingLastName("Williams")
            .setBillingStreetAddress("123 Fake Street")
            .setBillingCity("San Diego")
            .setBillingState("CA")
            .setBillingCountry("US")
            .setBillingZip("92121")
    );

    //CreateCreditCardPayerAccount,10,16,GAPITesterCC,Bonnie,Williams,MasterCard,5454545454545454,05,99,365,Bonnie,Williams,123 Fake Street,San Diego,CA,US,92121
    testCases.add(
        new CreateCreditCardPayerAccount(
            dataHelper.getReferenceId(),
            "Test Case 16 - Basic Valid - Mastercard - different exp month",
            getExpectedResponse(gatewayErrors, "10"))
            .setPayerFirstName("Bonnie")
            .setPayerLastName("Williams")
            .setCreditCardType("MasterCard")
            .setCreditCardNumber("5112000100000003")
            .setCreditCardExpMonth("05")
            .setCreditCardExpYear("99")
            .setCreditCardCvv2("365")
            .setBillingFirstName("Bonnie")
            .setBillingLastName("Williams")
            .setBillingStreetAddress("123 Fake Street")
            .setBillingCity("San Diego")
            .setBillingState("CA")
            .setBillingCountry("US")
            .setBillingZip("92121")
    );

    //CreateCreditCardPayerAccount,10,17,13434,Bonnie,Williams,MasterCard,5454545454545454,05,99,365,Bonnie,Williams,123 Fake Street,San Diego,CA,US,92121
    testCases.add(
        new CreateCreditCardPayerAccount(
            "13434",
            "Test Case 17 - Basic Valid - Mastercard - specific PayerReferenceId 1",
            getExpectedResponse(gatewayErrors, "10"))
            .setPayerFirstName("Bonnie")
            .setPayerLastName("Williams")
            .setCreditCardType("MasterCard")
            .setCreditCardNumber("5112000100000003")
            .setCreditCardExpMonth("05")
            .setCreditCardExpYear("99")
            .setCreditCardCvv2("365")
            .setBillingFirstName("Bonnie")
            .setBillingLastName("Williams")
            .setBillingStreetAddress("123 Fake Street")
            .setBillingCity("San Diego")
            .setBillingState("CA")
            .setBillingCountry("US")
            .setBillingZip("92121")
    );

    //CreateCreditCardPayerAccount,10,18,GAPITesterCC123,Bonnie,Williams,MasterCard,5454545454545454,05,99,365,Bonnie,Williams,123 Fake Street,San Diego,CA,US,92121
    testCases.add(
        new CreateCreditCardPayerAccount(
            "GAPITesterCC123",
            "Test Case 18 - Basic Valid - Mastercard - specific PayerReferenceId 2",
            getExpectedResponse(gatewayErrors, "10"))
            .setPayerFirstName("Bonnie")
            .setPayerLastName("Williams")
            .setCreditCardType("MasterCard")
            .setCreditCardNumber("5112000100000003")
            .setCreditCardExpMonth("05")
            .setCreditCardExpYear("99")
            .setCreditCardCvv2("365")
            .setBillingFirstName("Bonnie")
            .setBillingLastName("Williams")
            .setBillingStreetAddress("123 Fake Street")
            .setBillingCity("San Diego")
            .setBillingState("CA")
            .setBillingCountry("US")
            .setBillingZip("92121")
    );

    //CreateCreditCardPayerAccount,10,19,GAPITesterCC 123 !@#,Bonnie,Williams,MasterCard,5454545454545454,05,99,365,Bonnie,Williams,123 Fake Street,San Diego,CA,US,92121
    testCases.add(
        new CreateCreditCardPayerAccount(
            "GAPITesterCC 123 !@#",
            "Test Case 19 - Basic Valid - Mastercard - specific PayerReferenceId 3",
            getExpectedResponse(gatewayErrors, "10"))
            .setPayerFirstName("Bonnie")
            .setPayerLastName("Williams")
            .setCreditCardType("MasterCard")
            .setCreditCardNumber("5112000100000003")
            .setCreditCardExpMonth("05")
            .setCreditCardExpYear("99")
            .setCreditCardCvv2("365")
            .setBillingFirstName("Bonnie")
            .setBillingLastName("Williams")
            .setBillingStreetAddress("123 Fake Street")
            .setBillingCity("San Diego")
            .setBillingState("CA")
            .setBillingCountry("US")
            .setBillingZip("92121")
    );

    //CreateCreditCardPayerAccount,69,20,,Bonnie,Williams,MasterCard,5454545454545454,05,15,365,Bonnie,Williams,123 Fake Street,San Diego,CA,US,92121
    testCases.add(
        CreateCreditCardPayerAccount.createValid(
            "",
            "Test Case 20 - Empty PayerReferenceId",
            getExpectedResponse(gatewayErrors, "69"))
    );

    //CreateCreditCardPayerAccount,71,21,GAPITesterCC,,Williams,MasterCard,5454545454545454,05,15,365,Bonnie,Williams,123 Fake Street,San Diego,CA,US,92121
    testCases.add(
        CreateCreditCardPayerAccount.createValid(
            dataHelper.getReferenceId(),
            "Test Case 21 - Empty PayerFirstName",
            getExpectedResponse(gatewayErrors, "71"))
            .setPayerFirstName("")
    );

    //CreateCreditCardPayerAccount,72,22,GAPITesterCC,Bonnie,,MasterCard,5454545454545454,05,15,365,Bonnie,Williams,123 Fake Street,San Diego,CA,US,92121
    testCases.add(
        CreateCreditCardPayerAccount.createValid(
            dataHelper.getReferenceId(),
            "Test Case 22 - Empty PayerLastName",
            getExpectedResponse(gatewayErrors, "72"))
            .setPayerLastName("")
    );

    //CreateCreditCardPayerAccount,73,23,GAPITesterCC,Bonnie,Williams,,5454545454545454,05,15,365,Bonnie,Williams,123 Fake Street,San Diego,CA,US,92121
    testCases.add(
        CreateCreditCardPayerAccount.createValid(
            dataHelper.getReferenceId(),
            "Test Case 23 - Empty Card Type",
            getExpectedResponse(gatewayErrors, "73"))
            .setCreditCardType("")
    );

    //CreateCreditCardPayerAccount,75,24,GAPITesterCC,Bonnie,Williams,MasterCard,5454545454545454,0,15,365,Bonnie,Williams,123 Fake Street,San Diego,CA,US,92121
    testCases.add(
        CreateCreditCardPayerAccount.createValid(
            dataHelper.getReferenceId(),
            "Test Case 24 - Invalid Expiration month",
            getExpectedResponse(gatewayErrors, "75"))
            .setCreditCardExpMonth("0")
    );

    //CreateCreditCardPayerAccount,75,25,GAPITesterCC,Bonnie,Williams,MasterCard,5454545454545454,,15,365,Bonnie,Williams,123 Fake Street,San Diego,CA,US,92121
    testCases.add(
        CreateCreditCardPayerAccount.createValid(
            dataHelper.getReferenceId(),
            "Test Case 25 - Empty Exp Month",
            getExpectedResponse(gatewayErrors, "75"))
            .setCreditCardExpMonth("")
    );

    //CreateCreditCardPayerAccount,78,26,GAPITesterCC,Bonnie,Williams,MasterCard,5454545454545454,11,99,365,,Williams,123 Fake Street,San Diego,CA,US,92121
    testCases.add(
        CreateCreditCardPayerAccount.createValid(
            dataHelper.getReferenceId(),
            "Test Case 26 - Empty billing first name",
            getExpectedResponse(gatewayErrors, "78"))
            .setBillingFirstName("")
    );

    //CreateCreditCardPayerAccount,79,27,GAPITesterCC,Bonnie,Williams,MasterCard,5454545454545454,11,99,365,Bonnie,,123 Fake Street,San Diego,CA,US,92121
    testCases.add(
        CreateCreditCardPayerAccount.createValid(
            dataHelper.getReferenceId(),
            "Test Case 27 - Empty billing last name",
            getExpectedResponse(gatewayErrors, "79"))
            .setBillingLastName("")
    );

    //CreateCreditCardPayerAccount,80,28,GAPITesterCC,Bonnie,Williams,MasterCard,5454545454545454,11,99,365,Bonnie,Williams,,San Diego,CA,US,92121
    testCases.add(
        CreateCreditCardPayerAccount.createValid(
            dataHelper.getReferenceId(),
            "Test Case 28 - Empty billing address",
            getExpectedResponse(gatewayErrors, "80"))
            .setBillingStreetAddress("")
    );

    //CreateCreditCardPayerAccount,81,29,GAPITesterCC,Bonnie,Williams,MasterCard,5454545454545454,11,99,365,Bonnie,Williams,123 fake st,,CA,US,92121
    testCases.add(
        CreateCreditCardPayerAccount.createValid(
            dataHelper.getReferenceId(),
            "Test Case 29 - Empty billing city",
            getExpectedResponse(gatewayErrors, "81"))
            .setBillingCity("")
    );

    //CreateCreditCardPayerAccount,82,30,GAPITesterCC,Bonnie,Williams,MasterCard,5454545454545454,11,99,365,Bonnie,Williams,123 fake st,San Diego,,US,92121
    testCases.add(
        CreateCreditCardPayerAccount.createValid(
            dataHelper.getReferenceId(),
            "Test Case 30 - Empty billing state",
            getExpectedResponse(gatewayErrors, "82"))
            .setBillingState("")
    );

    //CreateCreditCardPayerAccount,83,31,GAPITesterCC,Bonnie,Williams,MasterCard,5454545454545454,11,99,365,Bonnie,Williams,123 fake st,San Diego,TX,,92121
    testCases.add(
        CreateCreditCardPayerAccount.createValid(
            dataHelper.getReferenceId(),
            "Test Case 31 - Empty billing country",
            getExpectedResponse(gatewayErrors, "83"))
            .setBillingCountry("")
    );

    //CreateCreditCardPayerAccount,84,32,GAPITesterCC,Bonnie,Williams,MasterCard,5454545454545454,11,99,365,Bonnie,Williams,123 fake st,San Diego,TX,US,
    testCases.add(
        CreateCreditCardPayerAccount.createValid(
            dataHelper.getReferenceId(),
            "Test Case 32 - Empty billing zip",
            getExpectedResponse(gatewayErrors, "84"))
            .setBillingZip("")
    );

    String[] invalidCardTypes = {
        "badcreditcardtype", "mastercard", "Mastercard",
    };
    for (String cardType : invalidCardTypes) {
      //CreateCreditCardPayerAccount,105,33,GAPITesterCC,Bonnie,Williams,badcreditcardtype,5454545454545454,05,15,365,Bonnie,Williams,123 Fake Street,San Diego,CA,US,92121
      //CreateCreditCardPayerAccount,105,34,GAPITesterCC,Bonnie,Williams,mastercard,5454545454545454,05,15,365,Bonnie,Williams,123 Fake Street,San Diego,CA,US,92121
      //CreateCreditCardPayerAccount,105,35,GAPITesterCC,Bonnie,Williams,Mastercard,5454545454545454,05,15,365,Bonnie,Williams,123 Fake Street,San Diego,CA,US,92121
      testCases.add(
          CreateCreditCardPayerAccount.createValid(
              dataHelper.getReferenceId(),
              "Test Case 33-35 - Invalid card types",
              getExpectedResponse(gatewayErrors, "105"))
              .setCreditCardType(cardType)
              .setCreditCardExpYear("15")
      );
    }

    //CreateCreditCardPayerAccount,106,36,GAPITesterCC,Bonnie,Williams,MasterCard,5454545454545454,05,01,365,Bonnie,Williams,123 Fake Street,San Diego,CA,US,92121
    testCases.add(
        CreateCreditCardPayerAccount.createValid(
            dataHelper.getReferenceId(),
            "Test Case 36 - Past exp year",
            getExpectedResponse(gatewayErrors, "106"))
            .setCreditCardExpYear("01")
    );

    String[] invalidCountryCodes = {
        "1", "a", "A",
    };
    for (String countryCode : invalidCountryCodes) {
      //CreateCreditCardPayerAccount,107,37,GAPITesterCC,Bonnie,Williams,MasterCard,5454545454545454,11,99,365,Bonnie,Williams,123 fake st,San Diego,TX,1,92121
      //CreateCreditCardPayerAccount,107,38,GAPITesterCC,Bonnie,Williams,MasterCard,5454545454545454,11,99,365,Bonnie,Williams,123 fake st,San Diego,na,a,92121
      //CreateCreditCardPayerAccount,107,39,GAPITesterCC,Bonnie,Williams,MasterCard,5454545454545454,11,99,365,Bonnie,Williams,123 fake st,San Diego,na,A,92121
      testCases.add(
          CreateCreditCardPayerAccount.createValid(
              dataHelper.getReferenceId(),
              "Test Case 37-39 - Invalid country code",
              getExpectedResponse(gatewayErrors, "107"))
              .setBillingCountry(countryCode)
      );
    }

    //CreateCreditCardPayerAccount,111,40,a134oihqf9w7h2ufhqiusd872q45y92715yr7q8wgfasyfasf,Bonnie,Williams,MasterCard,5454545454545454,05,15,365,Bonnie,Williams,123 Fake Street,San Diego,CA,US,92121
    testCases.add(
        CreateCreditCardPayerAccount.createValid(
            "a134oihqf9w7h2ufhqiusd872q45y92715yr7q8wgfasyfasf",
            "Test Case 40 - PayerReferenceId too long",
            getExpectedResponse(gatewayErrors, "111"))
    );

    //CreateCreditCardPayerAccount,112,41,GAPITesterCC,himylargenameislotsofcharacters,Williams,MasterCard,5454545454545454,05,15,365,Bonnie,Williams,123 Fake Street,San Diego,CA,US,92121
    testCases.add(
        CreateCreditCardPayerAccount.createValid(
            dataHelper.getReferenceId(),
            "Test Case 41 - PayerFirstName too long",
            getExpectedResponse(gatewayErrors, "112"))
            .setPayerFirstName("himylargenameislotsofcharacters")
    );

    //CreateCreditCardPayerAccount,113,42,GAPITesterCC,Bonnie,himylargenameislotsofcharacters,MasterCard,5454545454545454,05,15,365,Bonnie,Williams,123 Fake Street,San Diego,CA,US,92121
    testCases.add(
        CreateCreditCardPayerAccount.createValid(
            dataHelper.getReferenceId(),
            "Test Case 42 - PayerLastName too long",
            getExpectedResponse(gatewayErrors, "113"))
            .setPayerLastName("himylargenameislotsofcharacters")
    );

    String[] invalidCardNumbers = {
        "5454", "54545454545454545454",
    };
    for (String cardNumber : invalidCardNumbers) {
      //CreateCreditCardPayerAccount,116,43,GAPITesterCC,Bonnie,Williams,MasterCard,5454,05,15,365,Bonnie,Williams,123 Fake Street,San Diego,CA,US,92121
      //CreateCreditCardPayerAccount,116,44,GAPITesterCC,Bonnie,Williams,MasterCard,54545454545454545454,05,15,365,Bonnie,Williams,123 Fake Street,San Diego,CA,US,92121
      testCases.add(
          CreateCreditCardPayerAccount.createValid(
              dataHelper.getReferenceId(),
              "Test Case 43-44 - Invalid card number",
              getExpectedResponse(gatewayErrors, "116"))
              .setCreditCardNumber(cardNumber)
      );
    }

    //CreateCreditCardPayerAccount,117,45,GAPITesterCC,Bonnie,Williams,MasterCard,5454545454545454,111,15,365,Bonnie,Williams,123 Fake Street,San Diego,CA,US,92121
    testCases.add(
        CreateCreditCardPayerAccount.createValid(
            dataHelper.getReferenceId(),
            "Test Case 45 - Invalid exp month",
            getExpectedResponse(gatewayErrors, "117"))
            .setCreditCardExpMonth("111")
    );

    //CreateCreditCardPayerAccount,118,46,GAPITesterCC,Bonnie,Williams,MasterCard,5454545454545454,01,154,365,Bonnie,Williams,123 Fake Street,San Diego,CA,US,92121
    testCases.add(
        CreateCreditCardPayerAccount.createValid(
            dataHelper.getReferenceId(),
            "Test Case 46 - Invalid exp year",
            getExpectedResponse(gatewayErrors, "118"))
            .setCreditCardExpYear("154")
    );

    //CreateCreditCardPayerAccount,120,47,GAPITesterCC,Bonnie,Williams,MasterCard,5454545454545454,11,99,365,jeffersonBarwickBarrickNorthstAlistaireBenjaminaasJeffersonBarwickBarrickNorthstAlistaireBenjaminaasa,Williams,123 Fake Street,San Diego,CA,US,92121
    testCases.add(
        CreateCreditCardPayerAccount.createValid(
            dataHelper.getReferenceId(),
            "Test Case 47 - Billing First Name too long",
            getExpectedResponse(gatewayErrors, "120"))
            .setBillingFirstName(
                "jeffersonBarwickBarrickNorthstAlistaireBenjaminaasJeffersonBarwickBarrickNorthstAlistaireBenjaminaasa")
    );

    //CreateCreditCardPayerAccount,121,48,GAPITesterCC,Bonnie,Williams,MasterCard,5454545454545454,11,99,365,jeffersonBarwickBarrickNorthstAlistaireBenjaminaasJeffersonBarwickBarrickNorthstAlistaireBenjaminaas,jeffersonBarwickBarrickNorthstAlistaireBenjaminaasJeffersonBarwickBarrickNorthstAlistaireBenjaminaasa,123 Fake Street,San Diego,CA,US,92121
    testCases.add(
        CreateCreditCardPayerAccount.createValid(
            dataHelper.getReferenceId(),
            "Test Case 48 - Billing Last Name too long",
            getExpectedResponse(gatewayErrors, "121"))
            .setBillingLastName(
                "jeffersonBarwickBarrickNorthstAlistaireBenjaminaasJeffersonBarwickBarrickNorthstAlistaireBenjaminaasa")
    );

    //CreateCreditCardPayerAccount,122,49,GAPITesterCC,Bonnie,Williams,MasterCard,5454545454545454,11,99,365,Bonnie,Williams,1234 ersonBarwickBarrickNorthstAlistaireBenjaminaasJeffersonBarwickBarrickNorthstAlistaireBenjaminaas JeffersonBarwickBarrickNorthstAlistaireBenjaminaasJeffersonBarwickBarrickNorthstAlistaireBenjam Ave,San Diego,CA,US,92121
    testCases.add(
        CreateCreditCardPayerAccount.createValid(
            dataHelper.getReferenceId(),
            "Test Case 49 - Billing Address too long",
            getExpectedResponse(gatewayErrors, "122"))
            .setBillingStreetAddress(
                "1234 ersonBarwickBarrickNorthstAlistaireBenjaminaasJeffersonBarwickBarrickNorthstAlistaireBenjaminaas JeffersonBarwickBarrickNorthstAlistaireBenjaminaasJeffersonBarwickBarrickNorthstAlistaireBenjam Ave")
    );

    String[] longStates = {
        "tex", "texas",
    };

    for (String state : longStates) {
      //CreateCreditCardPayerAccount,123,50,GAPITesterCC,Bonnie,Williams,MasterCard,5454545454545454,11,99,365,Bonnie,Williams,123 fake st,San Diego,tex,US,92121
      //CreateCreditCardPayerAccount,123,51,GAPITesterCC,Bonnie,Williams,MasterCard,5454545454545454,11,99,365,Bonnie,Williams,123 fake st,San Diego,texas,US,92121
      testCases.add(
          CreateCreditCardPayerAccount.createValid(
              dataHelper.getReferenceId(),
              "Test Case 50-51 - Billing state too long",
              getExpectedResponse(gatewayErrors, "123"))
              .setBillingState(state)
      );
    }

    String[] badCardNumbers = {
        "jkjkjkjkjkjkjkjk", "jkjkjkjkjkjkjkjkjk",
    };

    for (String cardNum : badCardNumbers) {
      //CreateCreditCardPayerAccount,134,52,GAPITesterCC,Bonnie,Williams,MasterCard,jkjkjkjkjkjkjkjk,05,15,365,Bonnie,Williams,123 Fake Street,San Diego,CA,US,92121
      //CreateCreditCardPayerAccount,134,53,GAPITesterCC,Bonnie,Williams,MasterCard,jkjkjkjkjkjkjkjkjk,05,15,365,Bonnie,Williams,123 Fake Street,San Diego,CA,US,92121
      testCases.add(
          CreateCreditCardPayerAccount.createValid(
              dataHelper.getReferenceId(),
              "Test Case 52-53 - Non-numeric card number",
              getExpectedResponse(gatewayErrors, "134"))
              .setCreditCardNumber(cardNum)
      );
    }

    String[] badExpMonths = {
        "-1", "1a", "a1", "a",
    };

    for (String expMonth : badExpMonths) {
      //CreateCreditCardPayerAccount,135,54,GAPITesterCC,Bonnie,Williams,MasterCard,5454545454545454,-1,15,365,Bonnie,Williams,123 Fake Street,San Diego,CA,US,92121
      //CreateCreditCardPayerAccount,135,55,GAPITesterCC,Bonnie,Williams,MasterCard,5454545454545454,1a,15,365,Bonnie,Williams,123 Fake Street,San Diego,CA,US,92121
      //CreateCreditCardPayerAccount,135,56,GAPITesterCC,Bonnie,Williams,MasterCard,5454545454545454,a1,15,365,Bonnie,Williams,123 Fake Street,San Diego,CA,US,92121
      //CreateCreditCardPayerAccount,135,57,GAPITesterCC,Bonnie,Williams,MasterCard,5454545454545454,a,15,365,Bonnie,Williams,123 Fake Street,San Diego,CA,US,92121
      testCases.add(
          CreateCreditCardPayerAccount.createValid(
              dataHelper.getReferenceId(),
              "Test Case 54-57 - Non-numeric exp month",
              getExpectedResponse(gatewayErrors, "135"))
              .setCreditCardExpMonth(expMonth)
      );
    }

    String[] badExpYears = {
        "15a", "1a", "a1", "aa",
    };

    for (String expYear : badExpYears) {
      //CreateCreditCardPayerAccount,136,58,GAPITesterCC,Bonnie,Williams,MasterCard,5454545454545454,01,15a,365,Bonnie,Williams,123 Fake Street,San Diego,CA,US,92121
      //CreateCreditCardPayerAccount,136,59,GAPITesterCC,Bonnie,Williams,MasterCard,5454545454545454,01,1a,365,Bonnie,Williams,123 Fake Street,San Diego,CA,US,92121
      //CreateCreditCardPayerAccount,136,60,GAPITesterCC,Bonnie,Williams,MasterCard,5454545454545454,01,a1,365,Bonnie,Williams,123 Fake Street,San Diego,CA,US,92121
      //CreateCreditCardPayerAccount,136,61,GAPITesterCC,Bonnie,Williams,MasterCard,5454545454545454,01,aa,365,Bonnie,Williams,123 Fake Street,San Diego,CA,US,92121
      testCases.add(
          CreateCreditCardPayerAccount.createValid(
              dataHelper.getReferenceId(),
              "Test Case 58-61 - Non-numeric exp year",
              getExpectedResponse(gatewayErrors, "136"))
              .setCreditCardExpYear(expYear)
      );
    }

    String[] badStates = {
        "1", "1a", "a1",
    };

    for (String state : badStates) {
      //CreateCreditCardPayerAccount,138,62,GAPITesterCC,Bonnie,Williams,MasterCard,5454545454545454,11,99,365,Bonnie,Williams,123 fake st,San Diego,1,US,92121
      //CreateCreditCardPayerAccount,138,63,GAPITesterCC,Bonnie,Williams,MasterCard,5454545454545454,11,99,365,Bonnie,Williams,123 fake st,San Diego,1a,US,92121
      //CreateCreditCardPayerAccount,138,64,GAPITesterCC,Bonnie,Williams,MasterCard,5454545454545454,11,99,365,Bonnie,Williams,123 fake st,San Diego,a1,US,92121
      testCases.add(
          CreateCreditCardPayerAccount.createValid(
              dataHelper.getReferenceId(),
              "Test Case 62-64 - invalid billing state",
              getExpectedResponse(gatewayErrors, "138"))
              .setBillingState(state)
      );
    }

    String[] badZips = {
        "9212a", "123-123", "123-acs", "V9B 2W3", "V9B2W3",
    };

    for (String zip : badZips) {
      //CreateCreditCardPayerAccount,139,65,GAPITesterCC,Bonnie,Williams,MasterCard,5454545454545454,11,99,365,Bonnie,Williams,123 fake st,San Diego,TX,US,9212a
      //CreateCreditCardPayerAccount,139,66,GAPITesterCC,Bonnie,Williams,MasterCard,5454545454545454,11,99,365,Bonnie,Williams,123 fake st,San Diego,TX,US,123-123
      //CreateCreditCardPayerAccount,139,67,GAPITesterCC,Bonnie,Williams,MasterCard,5454545454545454,11,99,365,Bonnie,Williams,123 fake st,San Diego,TX,US,123-acs
      //CreateCreditCardPayerAccount,139,68,GAPITesterCC,Bonnie,Williams,MasterCard,5454545454545454,11,99,365,Bonnie,Williams,123 fake st,San Diego,TX,US,V9B 2W3
      //CreateCreditCardPayerAccount,139,69,GAPITesterCC,Bonnie,Williams,MasterCard,5454545454545454,11,99,365,Bonnie,Williams,123 fake st,San Diego,TX,US,V9B2W3
      testCases.add(
          CreateCreditCardPayerAccount.createValid(
              dataHelper.getReferenceId(),
              "Test Case 65-69 - invalid billing zip",
              getExpectedResponse(gatewayErrors, "139"))
              .setBillingZip(zip)
      );
    }

    //CreateCreditCardPayerAccount,242,70,GAPITesterCC,Bonnie,Williams,MasterCard,5454545454545454,11,99,365,Bonnie,Williams,123 fake st,jeffersonBarwickBarrickNorthstAlistaireBenjaminaasJeffersonBarwickBarrickNorthstAlistaireBenjaminaasa,CA,US,92121
    testCases.add(
        CreateCreditCardPayerAccount.createValid(
            dataHelper.getReferenceId(),
            "Test Case 70 - billing city too long",
            getExpectedResponse(gatewayErrors, "242"))
            .setBillingCity(
                "jeffersonBarwickBarrickNorthstAlistaireBenjaminaasJeffersonBarwickBarrickNorthstAlistaireBenjaminaasa")
    );

    executeTests(testCases);
  }

  @Test(groups = {"gapi", "CreateCreditCardPayerAccount", "paysafe"})
  public void paysafeCadTokenization() {
    Logger.info("CreateCreditCardPayerAccount tokenization with Paysafe and CAD currency");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc3595");
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    DataHelper dataHelper = new DataHelper();
    TestCaseCollection testCases = new GapiTestCaseCollection(
        new Credentials(gatewayId, username, password)
    );

    testCases.add(
        CreateCreditCardPayerAccount.createValid(
            dataHelper.getReferenceId(),
            "basic paysafe CAD tokenization",
            getExpectedResponse(gatewayErrors, "10"))
    );

    executeTests(testCases);
  }

  @Test(groups = {"gapi", "CreateCreditCardPayerAccount"})
  public void noProcessor() throws Exception {
    Logger.info("CreateCreditCardPayerAccount tokenization fails when no processor assigned");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc3597");
    testSetupPage.open();

    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");

    Credentials credentials = new Credentials(gatewayId, username, password);
    Response response = sendCreateCreditCardPayerRequest(credentials);
    response.setIndex("1");

    String gatewayPayerId = response.getSpecificElementValue("GatewayPayerId");
    String code = response.getSpecificElementValue("Code");

    Assert.assertEquals(gatewayPayerId, "", "No gateway payer Id returned");
    Assert.assertEquals(code, "1000", "Code 1000 returned in case of failure");
  }

  @Test(groups = {"gapi", "CreateCreditCardPayerAccount", "AccountPayment", "paysafe"})
  public void createTransactionCad() throws Exception {
    testCreateTransaction("tc3605", "CAD");
  }

  @Test(groups = {"gapi", "CreateCreditCardPayerAccount", "AccountPayment", "litle"})
  public void createTransactionUsd() throws Exception {
    testCreateTransaction("tc3606", "USD");
  }

  private Response sendCreateCreditCardPayerRequest(Credentials credentials) throws Exception {
    GapiRequest gapiRequest = new GapiRequest(credentials);

    DataHelper dataHelper = new DataHelper();
    Calendar calendar = Calendar.getInstance();

    String year = Integer.toString(calendar.get(Calendar.YEAR) + 2).substring(2);
    CreateCreditCardPayerAccount createCreditCardPayerAccount = CreateCreditCardPayerAccount
        .createValid(dataHelper.getReferenceId(), "valid request", null).setCreditCardExpYear(year);

    createCreditCardPayerAccount.addTransaction(gapiRequest);

    return gapiRequest.sendRequest();
  }

  private void testCreateTransaction(String testCase, String currencyCode) throws Exception {
    Logger.info("Create transaction with credit card payer (" + currencyCode + ")");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();

    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String payeeId = testSetupPage.getString("payeeId");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    Credentials credentials = new Credentials(gatewayId, username, password);

    Response response = sendCreateCreditCardPayerRequest(credentials);
    response.setIndex("1");

    String gatewayPayerId = response.getSpecificElementValue("GatewayPayerId");

    DataHelper dataHelper = new DataHelper();
    TestCaseCollection testCases = new GapiTestCaseCollection(credentials);

    testCases.add(
        AccountPayment.createValid(
            dataHelper.getReferenceId(),
            "gateway payer id should have been valid",
            getExpectedResponse(gatewayErrors, "8"),
            payeeId,
            gatewayPayerId).setCurrencyCode(currencyCode));
    executeTests(testCases);
  }
}