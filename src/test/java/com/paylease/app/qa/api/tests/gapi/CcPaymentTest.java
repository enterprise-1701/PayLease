package com.paylease.app.qa.api.tests.gapi;

import com.paylease.app.qa.api.tests.TestCaseCollection;
import com.paylease.app.qa.api.tests.gapi.testcase.CcPayment;
import com.paylease.app.qa.api.tests.gapi.testcase.ErrorTestCase;
import com.paylease.app.qa.framework.DataHelper;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.api.Credentials;
import com.paylease.app.qa.framework.api.Response;
import com.paylease.app.qa.framework.api.gapi.GapiRequest;
import com.paylease.app.qa.framework.api.gapi.GapiResponse;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.automatedhelper.TransactionPage;
import com.paylease.app.qa.framework.pages.automatedhelper.TransactionProcessingPage;
import java.util.HashMap;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CcPaymentTest extends BaseTest {

  private static final String REGION = "gapi";
  private static final String FEATURE = "CcPayment";

  //-----------------------------------TESTS----------------------------------------------

  @Test(groups = {"gapi", "CCPayment", "litle"})
  public void userRecordWithPrimaryAccountOnly() throws Exception {
    userRecordWithNoGatewayPayerRecord("tc2");
  }

  @Test(groups = {"gapi", "CCPayment", "litle"})
  public void userRecordWithPrimaryAndSecondaryAccount() throws Exception {
    userRecordWithNoGatewayPayerRecord("tc3");
  }

  private void userRecordWithNoGatewayPayerRecord(String testCase) throws Exception {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();

    // Credentials
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String payeeId = testSetupPage.getString("payeeId");

    // ResidentInfo
    final String createdResidentUserId = testSetupPage.getString("userId");
    final String payerRefId = testSetupPage.getString("payerRefId");
    final String payerSecondaryRefId = testSetupPage.getString("payerSecondaryRefId");
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
    final String totalAmount = testSetupPage.getString("totalAmount");
    final String feeAmount = testSetupPage.getString("feeAmount");
    final String incurFee = testSetupPage.getString("incurFee");
    final String saveAccount = testSetupPage.getString("saveAccount");

    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    Credentials credentials = new Credentials(gatewayId, username, password);
    GapiRequest gapiRequest = new GapiRequest(credentials);
    DataHelper dataHelper = new DataHelper();

    CcPayment ccPayment = new CcPayment(
        dataHelper.getReferenceId(),
        "Test Case - Empty fee amount, Visa",
        getExpectedResponse(gatewayErrors, "8"),
        payeeId)
        .setPayerReferenceId(payerRefId)
        .setPayerFirstName(firstName)
        .setPayerLastName(lastName)
        .setCreditCardType(creditCardType)
        .setCreditCardNumber(creditCardNumber)
        .setCreditExpMonth(creditCardExpMonth)
        .setCreditExpYear(creditCardExpYear)
        .setCreditCardCvv2(creditCardCvv)
        .setBillingFirstName(billingFirstName)
        .setBillingLastName(billingLastName)
        .setBillingStreetAddress(billingAddress)
        .setBillingCity(billingCity)
        .setBillingState(billingState)
        .setBillingCountry(billingCountry)
        .setBillingZip(billingZip)
        .setTotalAmount(totalAmount)
        .setFeeAmount(feeAmount)
        .setIncurFee(incurFee)
        .setSaveAccount(saveAccount);

    if (!payerSecondaryRefId.isEmpty()) {
      ccPayment.setPayerSecondaryRefId(payerSecondaryRefId);
    }

    ccPayment.addTransaction(gapiRequest);

    Response response = gapiRequest.sendRequest();
    response.setIndex("1");
    String gatewayPayerId = response.getSpecificElementValue("GatewayPayerId");

    String userId = validateGapiResponse(gatewayPayerId);
    Assert.assertEquals(userId, createdResidentUserId,
        "Gateway Payer Record was not created using existing resident");
  }

  @Test(groups = {"gapi", "CCPayment", "litle"})
  public void ccPayment1() {
    Logger.info("CC Payment Test - AapiResponse code 8");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String payeeId = testSetupPage.getString("payeeId");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    DataHelper dataHelper = new DataHelper();
    TestCaseCollection testCases = new GapiTestCaseCollection(
        new Credentials(gatewayId, username, password)
    );

    //CCPayment,8,2,GAPITesterCC,qagapisecondrefid,12035,Bryon,Hendrix,Visa,4457010000000009,5,25,369,Bryon,Hendrix,369 Atbash Court,Fallbrook,CA,US,92028,100.00,,No,Yes,Test Message,NULL,,20
    testCases.add(
        new CcPayment(
            dataHelper.getReferenceId(),
            "Test Case 2 - Empty fee amount, Visa",
            getExpectedResponse(gatewayErrors, "8"),
            payeeId)
            .setPayerReferenceId("GAPITesterCC")
            .setPayerSecondaryRefId("qagapisecondrefid")
            .setPayerFirstName("Bryon")
            .setPayerLastName("Hendrix")
            .setCreditCardType("Visa")
            .setCreditCardNumber("4457010000000009")
            .setCreditExpMonth("5")
            .setCreditExpYear("25")
            .setCreditCardCvv2("369")
            .setBillingFirstName("Bryon")
            .setBillingLastName("Hendrix")
            .setBillingStreetAddress("369 Atbash Court")
            .setBillingCity("Fallbrook")
            .setBillingState("CA")
            .setBillingCountry("US")
            .setBillingZip("92028")
            .setTotalAmount("100.00")
            .setFeeAmount("")
            .setMessage("Test Message")
            .setIncurFee("No")
            .setSaveAccount("Yes")
    );

    //CCPayment,8,3,GAPITesterCC,qagapisecondrefid,12035,Bryon,Hendrix,Amex,371449635398431,5,25,1369,Bryon,Hendrix,369 Atbash Court,Fallbrook,CA,US,92028,100.00,,No,Yes,Test Message,NULL,,20
    testCases.add(
        new CcPayment(
            dataHelper.getReferenceId(),
            "Test Case 3 - Empty fee amount, Amex ",
            getExpectedResponse(gatewayErrors, "8"),
            payeeId)
            .setPayerReferenceId("GAPITesterCC")
            .setPayerSecondaryRefId("qagapisecondrefid")
            .setPayerFirstName("Bryon")
            .setPayerLastName("Hendrix")
            .setCreditCardType("Amex")
            .setCreditCardNumber("375001000000005")
            .setCreditExpMonth("04")
            .setCreditExpYear("21")
            .setCreditCardCvv2("0421")
            .setBillingFirstName("Bryon")
            .setBillingLastName("Hendrix")
            .setBillingStreetAddress("4 Main St.")
            .setBillingCity("Laurel")
            .setBillingState("MD")
            .setBillingCountry("US")
            .setBillingZip("92028")
            .setTotalAmount("100.00")
            .setFeeAmount("")
            .setMessage("Test Message")
            .setIncurFee("No")
            .setSaveAccount("Yes")
    );

    //CCPayment,8,4,GAPITesterCC,qagapisecondrefid,12035,Bryon,Hendrix,Discover,6011000995500000,5,25,369,Bryon,Hendrix,369 Atbash Court,Fallbrook,CA,US,92028,100.00,,No,Yes,Test Message,NULL,,20
    testCases.add(
        new CcPayment(
            dataHelper.getReferenceId(),
            "Test Case 4 - Empty fee amount, Discover ",
            getExpectedResponse(gatewayErrors, "8"),
            payeeId)
            .setPayerReferenceId("GAPITesterCC")
            .setPayerSecondaryRefId("qagapisecondrefid")
            .setPayerFirstName("Bryon")
            .setPayerLastName("Hendrix")
            .setCreditCardType("Discover")
            .setCreditCardNumber("6011000995500000")
            .setCreditExpMonth("5")
            .setCreditExpYear("25")
            .setCreditCardCvv2("369")
            .setBillingFirstName("Bryon")
            .setBillingLastName("Hendrix")
            .setBillingStreetAddress("369 Atbash Court")
            .setBillingCity("Fallbrook")
            .setBillingState("CA")
            .setBillingCountry("US")
            .setBillingZip("92028")
            .setTotalAmount("100.00")
            .setFeeAmount("")
            .setMessage("Test Message")
            .setIncurFee("No")
            .setSaveAccount("Yes")
    );

    //CCPayment,8,5,GAPITesterCC,qagapisecondrefid,12035,Bryon,Hendrix,MasterCard,5454545454545454,5,25,369,Bryon,Hendrix,369 Atbash Court,Fallbrook,CA,US,92028,100.00,,No,Yes,Test Message,NULL,,20
    testCases.add(
        new CcPayment(
            dataHelper.getReferenceId(),
            "Test Case 5 - Empty fee amount, MasterCard",
            getExpectedResponse(gatewayErrors, "8"),
            payeeId)
            .setPayerReferenceId("GAPITesterCC")
            .setPayerSecondaryRefId("qagapisecondrefid")
            .setPayerFirstName("Bryon")
            .setPayerLastName("Hendrix")
            .setCreditCardType("MasterCard")
            .setCreditCardNumber("5112000100000003")
            .setCreditExpMonth("5")
            .setCreditExpYear("25")
            .setCreditCardCvv2("369")
            .setBillingFirstName("Bryon")
            .setBillingLastName("Hendrix")
            .setBillingStreetAddress("369 Atbash Court")
            .setBillingCity("Fallbrook")
            .setBillingState("CA")
            .setBillingCountry("US")
            .setBillingZip("92028")
            .setTotalAmount("100.00")
            .setFeeAmount("")
            .setMessage("Test Message")
            .setIncurFee("No")
            .setSaveAccount("Yes")
    );

    //CCPayment,8,6,GAPITesterCC,NULL,12035,K!a1@t#h$r4y^n&,B4*r(o4)wn,MasterCard,5454545454545454,5,25,369,K!a1@t#h$r4y^n&,B4*r(o4)wn,369 Atbash Court,Fallbrook,CA,US,92028,1300.00,,Yes,No,NULL,AUTH,,
    testCases.add(
        new CcPayment(
            dataHelper.getReferenceId(),
            "Test Case 6 - Empty fee amount, Incur fee yes, Save account no",
            getExpectedResponse(gatewayErrors, "8"),
            payeeId)
            .setPayerReferenceId("GAPITesterCC")
            .setPayerFirstName("K!a1@t#h$r4y^n&")
            .setPayerLastName("B4*r(o4)wn")
            .setCreditCardType("MasterCard")
            .setCreditCardNumber("5112000100000003")
            .setCreditExpMonth("5")
            .setCreditExpYear("25")
            .setCreditCardCvv2("369")
            .setBillingFirstName("K!a1@t#h$r4y^n&")
            .setBillingLastName("B4*r(o4)wn")
            .setBillingStreetAddress("369 Atbash Court")
            .setBillingCity("Fallbrook")
            .setBillingState("CA")
            .setBillingCountry("US")
            .setBillingZip("92028")
            .setTotalAmount("100.00")
            .setFeeAmount("")
            .setIncurFee("Yes")
            .setSaveAccount("No")
            .setCreditCardAction("AUTH")
    );

    //CCPayment,8,7,GAPITesterCC,NULL,12035,Kaylee,Lanier,MasterCard,5112000100000003,5,25,369,Kaylee,Lanier,369 Atbash Court,Fallbrook,CA,US,92028,2750.36,45.00,No,No,NULL,CAPTURE,LARGE,2
    testCases.add(
        new CcPayment(
            dataHelper.getReferenceId(),
            "Test Case 7 - Incur fee no, Save account no",
            getExpectedResponse(gatewayErrors, "8"),
            payeeId)
            .setPayerReferenceId("GAPITesterCC")
            .setPayerFirstName("Kaylee")
            .setPayerLastName("Lanier")
            .setCreditCardType("MasterCard")
            .setCreditCardNumber("5112000100000003")
            .setCreditExpMonth("5")
            .setCreditExpYear("25")
            .setCreditCardCvv2("369")
            .setBillingFirstName("Kaylee")
            .setBillingLastName("Lanier")
            .setBillingStreetAddress("369 Atbash Court")
            .setBillingCity("Fallbrook")
            .setBillingState("CA")
            .setBillingCountry("US")
            .setBillingZip("92028")
            .setTotalAmount("2750.36")
            .setFeeAmount("45.00")
            .setIncurFee("No")
            .setSaveAccount("No")
            .setCreditCardAction("CAPTURE")
    );

    //CCPayment,8,8,GAPITesterCC,NULL,12035,Brian,Baker,MasterCard,5454545454545454,5,25,369,Baker,Brian,123 ersonBarwickBarrickNorthstAlistaireBenjaminaasJeffersonBarwickBarrickNorthstAlistaireBenjaminaas JeffersonBarwickBarrickNorthstAlistaireBenjaminaasJeffersonBarwickBarrickNorthstAlistaireBenjam Ave,Mooselookmeguntic,CA,US,92028,325.25,45.00,Yes,No,NULL,NULL,NULL,
    testCases.add(
        new CcPayment(
            dataHelper.getReferenceId(),
            "Test Case 8 - Incur fee yes, Save account no",
            getExpectedResponse(gatewayErrors, "8"),
            payeeId)
            .setPayerReferenceId("GAPITesterCC")
            .setPayerFirstName("Brian")
            .setPayerLastName("Baker")
            .setCreditCardType("MasterCard")
            .setCreditCardNumber("5112000100000003")
            .setCreditExpMonth("5")
            .setCreditExpYear("25")
            .setCreditCardCvv2("369")
            .setBillingFirstName("Brian")
            .setBillingLastName("Baker")
            .setBillingStreetAddress(
                "123 ersonBarwickBarrickNorthstAlistaireBenjaminaasJeffersonBarwickBarrickNorthstAlistaireBenjaminaas JeffersonBarwickBarrickNorthstAlistaireBenjaminaasJeffersonBarwickBarrickNorthstAlistaireBenjam Ave")
            .setBillingCity("Mooselookmeguntic")
            .setBillingState("CA")
            .setBillingCountry("US")
            .setBillingZip("92028")
            .setTotalAmount("325.25")
            .setFeeAmount("45.00")
            .setIncurFee("Yes")
            .setSaveAccount("No")
    );

    //CCPayment,8,9,GAPITesterCC,NULL,12035,Eugene,Carter,MasterCard,5454545454545454,5,25,369,Eugene,Carter,369 Atbash Court,Fallbrook,CA,US,92028,3200.00,100.00,,No,NULL,NULL,NULL,
    testCases.add(
        new CcPayment(
            dataHelper.getReferenceId(),
            "Test Case 9 - Incur fee empty, Save account no",
            getExpectedResponse(gatewayErrors, "8"),
            payeeId)
            .setPayerReferenceId("GAPITesterCC")
            .setPayerFirstName("Eugene")
            .setPayerLastName("Carter")
            .setCreditCardType("MasterCard")
            .setCreditCardNumber("5112000100000003")
            .setCreditExpMonth("5")
            .setCreditExpYear("25")
            .setCreditCardCvv2("369")
            .setBillingFirstName("Eugene")
            .setBillingLastName("Carter")
            .setBillingStreetAddress("369 Atbash Court")
            .setBillingCity("Fallbrook")
            .setBillingState("CA")
            .setBillingCountry("US")
            .setBillingZip("92028")
            .setTotalAmount("3200.00")
            .setFeeAmount("100.00")
            .setIncurFee("")
            .setSaveAccount("No")
    );

    //CCPayment,8,10,GAPITesterCC,NULL,12035,Bryon,Hendrix,MasterCard,5454545454545454,5,25,369,Bryon,Hendrix,369 Atbash Court,Fallbrook,CA,US,92028,3200.00,100.00,,No,NULL,NULL,NULL,
    testCases.add(
        new CcPayment(
            dataHelper.getReferenceId(),
            "Test Case 10 - Incur fee empty, Save account no",
            getExpectedResponse(gatewayErrors, "8"),
            payeeId)
            .setPayerReferenceId("GAPITesterCC")
            .setPayerFirstName("Bryon")
            .setPayerLastName("Hendrix")
            .setCreditCardType("MasterCard")
            .setCreditCardNumber("5112000100000003")
            .setCreditExpMonth("5")
            .setCreditExpYear("25")
            .setCreditCardCvv2("369")
            .setBillingFirstName("Bryon")
            .setBillingLastName("Hendrix")
            .setBillingStreetAddress("369 Atbash Court")
            .setBillingCity("Fallbrook")
            .setBillingState("CA")
            .setBillingCountry("US")
            .setBillingZip("92028")
            .setTotalAmount("3200.00")
            .setFeeAmount("100.00")
            .setIncurFee("")
            .setSaveAccount("No")
    );

    executeTests(testCases);
  }

  @Test(groups = {"gapi", "CCPayment"})
  public void ccPayment2() {
    Logger.info("CC Payment Test - AapiResponse codes invalid");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String payeeId = testSetupPage.getString("payeeId");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    DataHelper dataHelper = new DataHelper();
    TestCaseCollection testCases = new GapiTestCaseCollection(
        new Credentials(gatewayId, username, password)
    );

    String[] incurFeeValues = {
        "No", "Yes", "",
    };

    for (String incurFee : incurFeeValues) {
      //CCPayment,69,11,,NULL,12035,Robert,Diaz,MasterCard,5454545454545454,5,25,369,Robert,Diaz,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,No,No,NULL,NULL,NULL,
      //CCPayment,69,12,,NULL,12035,Robert,Diaz,MasterCard,5454545454545454,5,25,369,Robert,Diaz,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,Yes,No,NULL,NULL,NULL,
      //CCPayment,69,13,,NULL,12035,Robert,Diaz,MasterCard,5454545454545454,5,25,369,Robert,Diaz,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,,No,NULL,NULL,NULL,
      testCases.add(
          CcPayment.createValid(
              dataHelper.getReferenceId(),
              "Test Case 11/12/13 - Empty Payer Ref Id, where incur fee is: " + incurFee,
              getExpectedResponse(gatewayErrors, "69"),
              payeeId)
              .setPayerReferenceId("")
              .setIncurFee(incurFee)
      );

      //CCPayment,70,14,GAPITesterCC,NULL,,Eugene,Carter,Visa,4444424444444440,5,25,369,Eugene,Carter,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,No,No,NULL,NULL,NULL,
      //CCPayment,70,15,GAPITesterCC,NULL,,Eugene,Carter,Visa,4444424444444440,5,25,369,Eugene,Carter,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,Yes,No,NULL,NULL,NULL,
      //CCPayment,70,16,GAPITesterCC,NULL,,Eugene,Carter,Visa,4444424444444440,5,25,369,Eugene,Carter,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,,No,NULL,NULL,NULL,
      testCases.add(
          CcPayment.createValid(
              dataHelper.getReferenceId(),
              "Test Case 14/15/16 - Empty Payee Id, where incur fee is: " + incurFee,
              getExpectedResponse(gatewayErrors, "70"),
              "")
              .setIncurFee(incurFee)
      );

      //CCPayment,72,17,GAPITesterCC,NULL,12035,Robert,,Discover,6011000995500000,5,25,136,Robert,Diaz,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,No,No,NULL,NULL,NULL,
      //CCPayment,72,18,GAPITesterCC,NULL,12035,Robert,,Discover,6011000995500000,5,25,136,Robert,Diaz,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,Yes,No,NULL,NULL,NULL,
      //CCPayment,72,19,GAPITesterCC,NULL,12035,Robert,,Discover,6011000995500000,5,25,136,Robert,Diaz,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,,No,NULL,NULL,NULL,
      testCases.add(
          CcPayment.createValid(
              dataHelper.getReferenceId(),
              "Test Case 17/18/19 - Empty Payer Last Name, where incur fee is: " + incurFee,
              getExpectedResponse(gatewayErrors, "72"),
              payeeId)
              .setPayerLastName("")
              .setIncurFee(incurFee)
      );

      //CCPayment,73,20,GAPITesterCC,NULL,12035,Kenneth,Evans,,5454545454545454,5,25,369,Abraham,Lincoln,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,No,No,NULL,NULL,NULL,
      //CCPayment,73,21,GAPITesterCC,NULL,12035,Amanda,Phillips,,4444424444444440,5,25,369,Ronald,Regan,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,Yes,No,NULL,NULL,NULL,
      //CCPayment,73,22,GAPITesterCC,NULL,12035,Amanda,Phillips,,6011000995500000,5,25,136,Ronald,Regan,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,,No,NULL,NULL,NULL,
      testCases.add(
          CcPayment.createValid(
              dataHelper.getReferenceId(),
              "Test Case 20/21/22 - Empty Card Type, where incur fee is: " + incurFee,
              getExpectedResponse(gatewayErrors, "73"),
              payeeId)
              .setCreditCardType("")
              .setIncurFee(incurFee)
      );

      //CCPayment,74,23,GAPITesterCC,NULL,12035,Brian,Baker,Discover,,5,25,136,Baker,Brian,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,No,No,NULL,NULL,NULL,
      //CCPayment,74,24,GAPITesterCC,NULL,12035,Mary,Powell,MasterCard,,5,25,369,Mary,Powell,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,Yes,No,NULL,NULL,NULL,
      if (!incurFee.equals("")) {
        testCases.add(
            CcPayment.createValid(
                dataHelper.getReferenceId(),
                "Test Case 23/24 - Empty Card Number, where incur fee is: " + incurFee,
                getExpectedResponse(gatewayErrors, "74"),
                payeeId)
                .setCreditCardNumber("")
                .setIncurFee(incurFee)
        );
      }

      //CCPayment,75,25,GAPITesterCC,NULL,12035,Mary,Powell,MasterCard,5454545454545454,,25,369,Mary,Powell,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,No,No,NULL,NULL,NULL,
      //CCPayment,75,26,GAPITesterCC,NULL,12035,Mary,Powell,MasterCard,5454545454545454,,25,369,Mary,Powell,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,Yes,No,NULL,NULL,NULL,
      //CCPayment,75,27,GAPITesterCC,NULL,12035,Mary,Powell,MasterCard,5454545454545454,,25,369,Mary,Powell,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,,No,NULL,NULL,NULL,
      testCases.add(
          CcPayment.createValid(
              dataHelper.getReferenceId(),
              "Test Case 25/26/27 - Empty Card Exp Month, where incur fee is: " + incurFee,
              getExpectedResponse(gatewayErrors, "75"),
              payeeId)
              .setCreditExpMonth("")
              .setIncurFee(incurFee)
      );

      //CCPayment,76,28,GAPITesterCC,NULL,12035,Tammy,Murphy,Visa,4444424444444440,5,,369,Tammy,Murphy,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,No,No,NULL,NULL,NULL,
      //CCPayment,76,29,GAPITesterCC,NULL,12035,Tammy,Murphy,Visa,4444424444444440,5,,369,Tammy,Murphy,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,Yes,No,NULL,NULL,NULL,
      //CCPayment,76,30,GAPITesterCC,NULL,12035,Tammy,Murphy,Visa,4444424444444440,5,,369,Tammy,Murphy,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,,No,NULL,NULL,NULL,
      testCases.add(
          CcPayment.createValid(
              dataHelper.getReferenceId(),
              "Test Case 28/29/30 - Empty Card Exp Year, where incur fee is: " + incurFee,
              getExpectedResponse(gatewayErrors, "76"),
              payeeId)
              .setCreditExpYear("")
              .setIncurFee(incurFee)
      );

      //CCPayment,78,31,GAPITesterCC,NULL,12035,Jose,Edwards,Discover,6011000995500000,5,25,136,,Edwards,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,Yes,No,NULL,NULL,NULL,
      //CCPayment,78,32,GAPITesterCC,NULL,12035,Kurt,Myers,Visa,4444424444444440,5,25,369,,Myers,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,,No,NULL,NULL,NULL,
      if (!incurFee.equals("No")) {
        testCases.add(
            CcPayment.createValid(
                dataHelper.getReferenceId(),
                "Test Case 31/32 - Empty Billing First Name, where incur fee is: " + incurFee,
                getExpectedResponse(gatewayErrors, "78"),
                payeeId)
                .setBillingFirstName("")
                .setIncurFee(incurFee)
        );
      }

      //CCPayment,79,33,GAPITesterCC,NULL,12035,Jose,Edwards,Discover,6011000995500000,5,25,136,Jose,,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,No,No,NULL,NULL,NULL,
      //CCPayment,79,34,GAPITesterCC,NULL,12035,Jose,Edwards,Discover,6011000995500000,5,25,136,Jose,,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,Yes,No,NULL,NULL,NULL,
      //CCPayment,79,35,GAPITesterCC,NULL,12035,Jose,Edwards,Discover,6011000995500000,5,25,136,Jose,,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,,No,NULL,NULL,NULL,
      testCases.add(
          CcPayment.createValid(
              dataHelper.getReferenceId(),
              "Test Case 33/34/35 - Empty Billing Last Name, where incur fee is: " + incurFee,
              getExpectedResponse(gatewayErrors, "79"),
              payeeId)
              .setBillingLastName("")
              .setIncurFee(incurFee)
      );

      //CCPayment,81,37,GAPITesterCC,NULL,12035,Robert,Diaz,Visa,4444424444444440,5,25,369,Robert,Diaz,369 Atbash Court,,CA,US,92028,325.25,45.32,No,No,NULL,NULL,NULL,
      //CCPayment,81,38,GAPITesterCC,NULL,12035,Robert,Diaz,Visa,4444424444444440,5,25,369,Robert,Diaz,369 Atbash Court,,CA,US,92028,325.25,45.32,Yes,No,NULL,NULL,NULL,
      //CCPayment,81,39,GAPITesterCC,NULL,12035,Robert,Diaz,Visa,4444424444444440,5,25,369,Robert,Diaz,369 Atbash Court,,CA,US,92028,325.25,45.32,,No,NULL,NULL,NULL,
      testCases.add(
          CcPayment.createValid(
              dataHelper.getReferenceId(),
              "Test Case 37/38/39 - Empty Billing City, where incur fee is: " + incurFee,
              getExpectedResponse(gatewayErrors, "81"),
              payeeId)
              .setBillingCity("")
              .setIncurFee(incurFee)
      );

      //CCPayment,82,40,GAPITesterCC,NULL,12035,Eugene,Carter,MasterCard,5454545454545454,5,25,369,Eugene,Carter,369 Atbash Court,Fallbrook,,US,92028,325.25,45.32,No,No,NULL,NULL,NULL,
      //CCPayment,82,41,GAPITesterCC,NULL,12035,Eugene,Carter,MasterCard,5454545454545454,5,25,369,Eugene,Carter,369 Atbash Court,Fallbrook,,US,92028,325.25,45.32,Yes,No,NULL,NULL,NULL,
      //CCPayment,82,42,GAPITesterCC,NULL,12035,Eugene,Carter,MasterCard,5454545454545454,5,25,369,Eugene,Carter,369 Atbash Court,Fallbrook,,US,92028,325.25,45.32,,No,NULL,NULL,NULL,
      testCases.add(
          CcPayment.createValid(
              dataHelper.getReferenceId(),
              "Test Case 40/41/42 - Empty Billing State, where incur fee is: " + incurFee,
              getExpectedResponse(gatewayErrors, "82"),
              payeeId)
              .setBillingState("")
              .setIncurFee(incurFee)
      );

      //CCPayment,83,43,GAPITesterCC,NULL,12035,Kaylee,Lanier,MasterCard,5454545454545454,5,25,369,Kaylee,Lanier,369 Atbash Court,Fallbrook,CA,,92028,325.25,45.32,No,No,NULL,NULL,NULL,
      //CCPayment,83,44,GAPITesterCC,NULL,12035,Kaylee,Lanier,MasterCard,5454545454545454,5,25,369,Kaylee,Lanier,369 Atbash Court,Fallbrook,CA,,92028,325.25,45.32,Yes,No,NULL,NULL,NULL,
      //CCPayment,83,45,GAPITesterCC,NULL,12035,Kaylee,Lanier,MasterCard,5454545454545454,5,25,369,Kaylee,Lanier,369 Atbash Court,Fallbrook,CA,,92028,325.25,45.32,,No,NULL,NULL,NULL,
      testCases.add(
          CcPayment.createValid(
              dataHelper.getReferenceId(),
              "Test Case 43/44/45 - Empty Billing Country, where incur fee is: " + incurFee,
              getExpectedResponse(gatewayErrors, "83"),
              payeeId)
              .setBillingCountry("")
              .setIncurFee(incurFee)
      );

      //CCPayment,84,46,GAPITesterCC,NULL,12035,Lee,Frazier,MasterCard,5454545454545454,5,25,369,Lee,Frazier,369 Atbash Court,Fallbrook,CA,US,,325.25,45.32,No,No,NULL,NULL,NULL,
      //CCPayment,84,47,GAPITesterCC,NULL,12035,Lee,Frazier,MasterCard,5454545454545454,5,25,369,Lee,Frazier,369 Atbash Court,Fallbrook,CA,US,,325.25,45.32,Yes,No,NULL,NULL,NULL,
      //CCPayment,84,48,GAPITesterCC,NULL,12035,Lee,Frazier,MasterCard,5454545454545454,5,25,369,Lee,Frazier,369 Atbash Court,Fallbrook,CA,US,,325.25,45.32,,No,NULL,NULL,NULL,
      testCases.add(
          CcPayment.createValid(
              dataHelper.getReferenceId(),
              "Test Case 46/47/48 - Empty Billing Zip, where incur fee is: " + incurFee,
              getExpectedResponse(gatewayErrors, "84"),
              payeeId)
              .setBillingZip("")
              .setIncurFee(incurFee)
      );

      //CCPayment,85,52,GAPITesterCC,NULL,12035,Susan,Bell,MasterCard,5454545454545454,5,25,369,Susan,Bell,369 Atbash Court,Fallbrook,CA,US,92028,,45.32,No,No,NULL,NULL,NULL,
      //CCPayment,85,53,GAPITesterCC,NULL,12035,Susan,Bell,MasterCard,5454545454545454,5,25,369,Susan,Bell,369 Atbash Court,Fallbrook,CA,US,92028,,45.32,Yes,No,NULL,NULL,NULL,
      //CCPayment,85,54,GAPITesterCC,NULL,12035,Susan,Bell,MasterCard,5454545454545454,5,25,369,Susan,Bell,369 Atbash Court,Fallbrook,CA,US,92028,,45.32,,No,NULL,NULL,NULL,
      testCases.add(
          CcPayment.createValid(
              dataHelper.getReferenceId(),
              "Test Case 52/53/54 - Empty Total Amount, where incur fee is: " + incurFee,
              getExpectedResponse(gatewayErrors, "85"),
              payeeId)
              .setTotalAmount("")
              .setIncurFee(incurFee)
      );

      //CCPayment,86,55,GAPITesterCC,NULL,12035,Kenneth,Evans,MasterCard,5454545454545454,5,25,369,Abraham,Lincoln,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,No,,NULL,NULL,NULL,
      //CCPayment,86,56,GAPITesterCC,NULL,12035,Kenneth,Evans,MasterCard,5454545454545454,5,25,369,Abraham,Lincoln,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,Yes,,NULL,NULL,NULL,
      //CCPayment,86,57,GAPITesterCC,NULL,12035,Kenneth,Evans,MasterCard,5454545454545454,5,25,369,Abraham,Lincoln,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,,,NULL,NULL,NULL,
      testCases.add(
          CcPayment.createValid(
              dataHelper.getReferenceId(),
              "Test Case 55/56/57 - Empty Save Account, where incur fee is: " + incurFee,
              getExpectedResponse(gatewayErrors, "86"),
              payeeId)
              .setSaveAccount("")
              .setIncurFee(incurFee)
      );

      //CCPayment,104,58,GAPITesterCC,NULL,12035,Tammy,Murphy,Visa,4444424444444440,5,25,369,Tammy,Murphy,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,No,N,NULL,NULL,NULL,
      //CCPayment,104,59,GAPITesterCC,NULL,12035,Tammy,Murphy,Visa,4444424444444440,5,25,369,Tammy,Murphy,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,Yes,N,NULL,NULL,NULL,
      //CCPayment,104,60,GAPITesterCC,NULL,12035,Tammy,Murphy,Visa,4444424444444440,5,25,369,Tammy,Murphy,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,,N,NULL,NULL,NULL,
      testCases.add(
          CcPayment.createValid(
              dataHelper.getReferenceId(),
              "Test Case 58/59/60 - Invalid Save Account value, where incur fee is: " + incurFee,
              getExpectedResponse(gatewayErrors, "104"),
              payeeId)
              .setSaveAccount("N")
              .setIncurFee(incurFee)
      );

      //CCPayment,105,61,GAPITesterCC,NULL,12035,Kaylee,Lanier,dMasterCard,5454545454545454,5,25,369,Kaylee,Lanier,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,No,No,NULL,NULL,NULL,
      //CCPayment,105,62,GAPITesterCC,NULL,12035,Kaylee,Lanier,dMasterCard,5454545454545454,5,25,369,Kaylee,Lanier,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,Yes,No,NULL,NULL,NULL,
      //CCPayment,105,63,GAPITesterCC,NULL,12035,Kaylee,Lanier,dMasterCard,5454545454545454,5,25,369,Kaylee,Lanier,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,,No,NULL,NULL,NULL,
      testCases.add(
          CcPayment.createValid(
              dataHelper.getReferenceId(),
              "Test Case 61/62/63 - Invalid Credit Card Type Value, where incur fee is: "
                  + incurFee,
              getExpectedResponse(gatewayErrors, "105"),
              payeeId)
              .setCreditCardType("dMasterCard")
              .setIncurFee(incurFee)
      );

      //CCPayment,106,64,GAPITesterCC,NULL,12035,Jose,Edwards,MasterCard,5454545454545454,5,10,369,Jose,Edwards,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,No,No,NULL,NULL,NULL,
      //CCPayment,106,65,GAPITesterCC,NULL,12035,Jose,Edwards,MasterCard,5454545454545454,5,10,369,Jose,Edwards,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,Yes,No,NULL,NULL,NULL,
      //CCPayment,106,66,GAPITesterCC,NULL,12035,Jose,Edwards,MasterCard,5454545454545454,5,10,369,Jose,Edwards,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,,No,NULL,NULL,NULL,
      testCases.add(
          CcPayment.createValid(
              dataHelper.getReferenceId(),
              "Test Case 64/65/66 - Invalid Credit Card Year, where incur fee is: " + incurFee,
              getExpectedResponse(gatewayErrors, "106"),
              payeeId)
              .setCreditExpYear("10")
              .setIncurFee(incurFee)
      );

      //CCPayment,111,67,a134oihqf9w7h2ufhqiusd872q45y92715yr7q8wgfasyfasf,NULL,12035,Tammy,Murphy,Visa,4444424444444440,5,25,369,Tammy,Murphy,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,No,No,NULL,NULL,NULL,
      //CCPayment,111,68,a134oihqf9w7h2ufhqiusd872q45y92715yr7q8wgfasyfasf,NULL,12035,Tammy,Murphy,Visa,4444424444444440,5,25,369,Tammy,Murphy,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,Yes,No,NULL,NULL,NULL,
      //CCPayment,111,69,a134oihqf9w7h2ufhqiusd872q45y92715yr7q8wgfasyfasf,NULL,12035,Tammy,Murphy,Visa,4444424444444440,5,25,369,Tammy,Murphy,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,,No,NULL,NULL,NULL,
      testCases.add(
          CcPayment.createValid(
              dataHelper.getReferenceId(),
              "Test Case 67/68/69 - Payer Ref Id exceeds 30 char, where incur fee is: " + incurFee,
              getExpectedResponse(gatewayErrors, "111"),
              payeeId)
              .setPayerReferenceId("a134oihqf9w7h2ufhqiusd872q45y92715yr7q8wgfasyfasf")
              .setIncurFee(incurFee)
      );

      //CCPayment,112,70,GAPITesterCC,NULL,12035,JeffersonBarwickBarrickNorthsta,AlistaireBenjaminLafayetteTrya,Visa,4444424444444440,5,25,369,Jonathan,Martin,369 Atbash Court,Fallbrook,CA,CA,92028,325.25,45.32,No,No,NULL,NULL,NULL,
      //CCPayment,112,71,GAPITesterCC,NULL,12035,JeffersonBarwickBarrickNorthsta,AlistaireBenjaminLafayetteTrya,Visa,4444424444444440,5,25,369,Jonathan,Martin,369 Atbash Court,Fallbrook,CA,CA,92028,325.25,45.32,Yes,No,NULL,NULL,NULL,
      //CCPayment,112,72,GAPITesterCC,NULL,12035,JeffersonBarwickBarrickNorthsta,AlistaireBenjaminLafayetteTrya,Visa,4444424444444440,5,25,369,Jonathan,Martin,369 Atbash Court,Fallbrook,CA,CA,92028,325.25,45.32,,No,NULL,NULL,NULL,
      testCases.add(
          CcPayment.createValid(
              dataHelper.getReferenceId(),
              "Test Case 70/71/72 - Payer First Name exceeds 30 char, where incur fee is: "
                  + incurFee,
              getExpectedResponse(gatewayErrors, "112"),
              payeeId)
              .setPayerFirstName("JeffersonBarwickBarrickNorthsta")
              .setIncurFee(incurFee)
      );

      //CCPayment,117,73,GAPITesterCC,NULL,12035,Brian,Baker,Discover,6011000995500000,15,25,136,Baker,Brian,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,No,No,NULL,NULL,NULL,
      //CCPayment,117,74,GAPITesterCC,NULL,12035,Brian,Baker,Discover,6011000995500000,15,25,136,Baker,Brian,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,Yes,No,NULL,NULL,NULL,
      //CCPayment,117,75,GAPITesterCC,NULL,12035,Brian,Baker,Discover,6011000995500000,15,25,136,Baker,Brian,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,,No,NULL,NULL,NULL,
      testCases.add(
          CcPayment.createValid(
              dataHelper.getReferenceId(),
              "Test Case 73/74/75 - Credit card exp month exceeds max value 12, where incur fee is: "
                  + incurFee,
              getExpectedResponse(gatewayErrors, "117"),
              payeeId)
              .setCreditExpMonth("15")
              .setIncurFee(incurFee)
      );

      //CCPayment,118,76,GAPITesterCC,NULL,12035,Mary,Powell,Discover,6011000995500000,5,251,136,Mary,Powell,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,No,No,NULL,NULL,NULL,
      //CCPayment,118,77,GAPITesterCC,NULL,12035,Mary,Powell,Discover,6011000995500000,5,251,136,Mary,Powell,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,Yes,No,NULL,NULL,NULL,
      //CCPayment,118,78,GAPITesterCC,NULL,12035,Mary,Powell,Discover,6011000995500000,5,251,136,Mary,Powell,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,,No,NULL,NULL,NULL,
      testCases.add(
          CcPayment.createValid(
              dataHelper.getReferenceId(),
              "Test Case 76/77/78 - Credit card exp year exceeds max length 2, where incur fee is: "
                  + incurFee,
              getExpectedResponse(gatewayErrors, "118"),
              payeeId)
              .setCreditExpYear("251")
              .setIncurFee(incurFee)
      );

      //CCPayment,119,79,GAPITesterCC,NULL,12035,Brian,Baker,MasterCard,5454545454545454,5,25,3,Baker,Brian,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,No,No,NULL,NULL,NULL,
      //CCPayment,119,80,GAPITesterCC,NULL,12035,Brian,Baker,MasterCard,5454545454545454,5,25,3,Baker,Brian,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,Yes,No,NULL,NULL,NULL,
      if (!incurFee.equals("")) {
        testCases.add(
            CcPayment.createValid(
                dataHelper.getReferenceId(),
                "Test Case 79/80 - Credit card cvv2 incorrect length, where incur fee is: "
                    + incurFee,
                getExpectedResponse(gatewayErrors, "119"),
                payeeId)
                .setCreditCardCvv2("3")
                .setIncurFee(incurFee)
        );
      }

      //CCPayment,122,82,GAPITesterCC,NULL,12035,Susan,Bell,MasterCard,5454545454545454,5,25,369,Susan,Bell,1234 ersonBarwickBarrickNorthstAlistaireBenjaminaasJeffersonBarwickBarrickNorthstAlistaireBenjaminaas JeffersonBarwickBarrickNorthstAlistaireBenjaminaasJeffersonBarwickBarrickNorthstAlistaireBenjam Ave,Fallbrook,CA,US,92028,325.25,45.32,No,No,NULL,NULL,NULL,
      //CCPayment,122,83,GAPITesterCC,NULL,12035,Susan,Bell,MasterCard,5454545454545454,5,25,369,Susan,Bell,1234 ersonBarwickBarrickNorthstAlistaireBenjaminaasJeffersonBarwickBarrickNorthstAlistaireBenjaminaas JeffersonBarwickBarrickNorthstAlistaireBenjaminaasJeffersonBarwickBarrickNorthstAlistaireBenjam Ave,Fallbrook,CA,US,92028,325.25,45.32,Yes,No,NULL,NULL,NULL,
      //CCPayment,122,84,GAPITesterCC,NULL,12035,Susan,Bell,MasterCard,5454545454545454,5,25,369,Susan,Bell,1234 ersonBarwickBarrickNorthstAlistaireBenjaminaasJeffersonBarwickBarrickNorthstAlistaireBenjaminaas JeffersonBarwickBarrickNorthstAlistaireBenjaminaasJeffersonBarwickBarrickNorthstAlistaireBenjam Ave,Fallbrook,CA,US,92028,325.25,45.32,,No,NULL,NULL,NULL,
      testCases.add(
          CcPayment.createValid(
              dataHelper.getReferenceId(),
              "Test Case 82/83/84 - Billing Street Address exceeds 200 char, where incur fee is: "
                  + incurFee,
              getExpectedResponse(gatewayErrors, "122"),
              payeeId)
              .setBillingStreetAddress(
                  "1234 ersonBarwickBarrickNorthstAlistaireBenjaminaasJeffersonBarwickBarrickNorthstAlistaireBenjaminaas JeffersonBarwickBarrickNorthstAlistaireBenjaminaasJeffersonBarwickBarrickNorthstAlistaireBenjam Ave")
              .setIncurFee(incurFee)
      );

      //CCPayment,123,85,GAPITesterCC,NULL,12035,Lee,Frazier,MasterCard,5454545454545454,5,25,369,Lee,Frazier,369 Atbash Court,Fallbrook,CAL,US,92028,325.25,45.32,No,No,NULL,NULL,NULL,
      //CCPayment,123,86,GAPITesterCC,NULL,12035,Lee,Frazier,MasterCard,5454545454545454,5,25,369,Lee,Frazier,369 Atbash Court,Fallbrook,CAL,US,92028,325.25,45.32,Yes,No,NULL,NULL,NULL,
      //CCPayment,123,87,GAPITesterCC,NULL,12035,Lee,Frazier,MasterCard,5454545454545454,5,25,369,Lee,Frazier,369 Atbash Court,Fallbrook,CAL,US,92028,325.25,45.32,,No,NULL,NULL,NULL,
      testCases.add(
          CcPayment.createValid(
              dataHelper.getReferenceId(),
              "Test Case 85/86/87 - Billing State exceeds max length, where incur fee is: "
                  + incurFee,
              getExpectedResponse(gatewayErrors, "123"),
              payeeId)
              .setBillingState("CAL")
              .setIncurFee(incurFee)
      );

    }

    //CCPayment,80,36,GAPITesterCC,NULL,12035,Tammy,Murphy,MasterCard,5454545454545454,5,25,369,Tammy,Murphy,,Fallbrook,CA,US,92028,325.25,45.32,No,No,NULL,NULL,NULL,
    testCases.add(
        CcPayment.createValid(
            dataHelper.getReferenceId(),
            "Test Case 36 - Empty Billing Street Address",
            getExpectedResponse(gatewayErrors, "80"),
            payeeId)
            .setBillingStreetAddress("")
            .setIncurFee("No")
    );

    //CCPayment,84,49,GAPITesterCC,NULL,12035,Kurt,Myers,MasterCard,5454545454545454,5,25,369,Kurt,Myers,369 Atbash Court,Fallbrook,XX,US,0,325.25,45.32,No,No,NULL,NULL,NULL,
    testCases.add(
        CcPayment.createValid(
            dataHelper.getReferenceId(),
            "Test Case 49 - Empty Billing Zip",
            getExpectedResponse(gatewayErrors, "84"),
            payeeId)
            .setBillingZip("")
            .setIncurFee("No")
            .setBillingState("XX")
            .setBillingCountry("US")
    );

    //CCPayment,84,50,GAPITesterCC,NULL,12035,Kurt,Myers,MasterCard,5454545454545454,5,25,369,Kurt,Myers,369 Atbash Court,Fallbrook,XX,GB,0,325.25,45.32,Yes,No,NULL,NULL,NULL,
    testCases.add(
        CcPayment.createValid(
            dataHelper.getReferenceId(),
            "Test Case 50 - Empty Billing Zip",
            getExpectedResponse(gatewayErrors, "84"),
            payeeId)
            .setBillingZip("")
            .setIncurFee("No")
            .setBillingState("XX")
            .setBillingCountry("GB")
    );

    //CCPayment,84,51,GAPITesterCC,NULL,12035,Kurt,Myers,MasterCard,5454545454545454,5,25,369,Kurt,Myers,369 Atbash Court,Fallbrook,XX,MX,0,325.25,45.32,,No,NULL,NULL,NULL,
    testCases.add(
        CcPayment.createValid(
            dataHelper.getReferenceId(),
            "Test Case 51 - Empty Billing Zip",
            getExpectedResponse(gatewayErrors, "84"),
            payeeId)
            .setBillingZip("")
            .setIncurFee("No")
            .setBillingState("XX")
            .setBillingCountry("MX")
    );

    //CCPayment,119,81,GAPITesterCC,NULL,12035,Amanda,Phillips,Visa,4444424444444440,5,25,36229,Ronald,Regan,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,,No,NULL,NULL,NULL,
    testCases.add(
        CcPayment.createValid(
            dataHelper.getReferenceId(),
            "Test Case 81 - Credit card cvv2 incorrect length",
            getExpectedResponse(gatewayErrors, "119"),
            payeeId)
            .setCreditCardCvv2("36229")
            .setIncurFee("")
    );

    String[] payeeIdValues = {
        "120 35", "120!35", "120.35", "120-35",
    };

    for (String payeeIdValue : payeeIdValues) {
      //CCPayment,130,88,GAPITesterCC,NULL,120 35,GAPI CC,Tester,MasterCard,5454545454545454,5,25,369,GAPI CC,Tester,369 Atbash Court,Fallbrook,CA,US,92028,1500.00,,,Yes,NULL,NULL,NULL,
      //CCPayment,130,89,GAPITesterCC,NULL,120!35,GAPI CC,Tester,MasterCard,5454545454545454,5,25,369,GAPI CC,Tester,369 Atbash Court,Fallbrook,CA,US,92028,1500.00,,,Yes,NULL,NULL,NULL
      //CCPayment,130,90,GAPITesterCC,NULL,120.35,GAPI CC,Tester,MasterCard,5454545454545454,5,25,369,GAPI CC,Tester,369 Atbash Court,Fallbrook,CA,US,92028,1500.00,,,Yes,NULL,NULL,NULL,
      //CCPayment,130,91,GAPITesterCC,NULL,120-35,GAPI CC,Tester,MasterCard,5454545454545454,5,25,369,GAPI CC,Tester,369 Atbash Court,Fallbrook,CA,US,92028,1500.00,,,Yes,NULL,NULL,NULL,
      testCases.add(
          CcPayment.createValid(
              dataHelper.getReferenceId(),
              "Test Case 88 to 91 - Payee ID value in invalid format: " + payeeIdValue,
              getExpectedResponse(gatewayErrors, "130"),
              payeeIdValue)
              .setFeeAmount("")
              .setIncurFee("")
              .setSaveAccount("Yes")
      );
    }

    String[] totalAmountInvalidValues = {
        "2000.003", "2000 03", "2000. 03", "2000. 3", "2000 .03", "2000 .3", "2000 .03", "2000.",
        ".0", "1.0a",
    };

    for (String totalAmount : totalAmountInvalidValues) {
      //CCPayment,133,92,GAPITesterCC145,NULL,12035,Brian,Baker,MasterCard,5454545454545454,5,25,369,Baker,Brian,123 ersonBarwickBarrickNorthstAlistaireBenjaminaasJeffersonBarwickBarrickNorthstAlistaireBenjaminaas JeffersonBarwickBarrickNorthstAlistaireBenjaminaasJeffersonBarwickBarrickNorthstAlistaireBenjam Ave,Mooselookmeguntic,CA,US,92028,2000.003,45.32,No,No,NULL,NULL,NULL,
      //CCPayment,133,93,GAPITesterCC145,NULL,12035,Brian,Baker,MasterCard,5454545454545454,5,25,369,Baker,Brian,123 ersonBarwickBarrickNorthstAlistaireBenjaminaasJeffersonBarwickBarrickNorthstAlistaireBenjaminaas JeffersonBarwickBarrickNorthstAlistaireBenjaminaasJeffersonBarwickBarrickNorthstAlistaireBenjam Ave,Mooselookmeguntic,CA,US,92028,2000 03,45.32,No,No,NULL,NULL,NULL,
      //CCPayment,133,94,GAPITesterCC145,NULL,12035,Brian,Baker,MasterCard,5454545454545454,5,25,369,Baker,Brian,123 ersonBarwickBarrickNorthstAlistaireBenjaminaasJeffersonBarwickBarrickNorthstAlistaireBenjaminaas JeffersonBarwickBarrickNorthstAlistaireBenjaminaasJeffersonBarwickBarrickNorthstAlistaireBenjam Ave,Mooselookmeguntic,CA,US,92028,2000. 03,45.32,No,No,NULL,NULL,NULL,
      //CCPayment,133,95,GAPITesterCC145,NULL,12035,Brian,Baker,MasterCard,5454545454545454,5,25,369,Baker,Brian,123 ersonBarwickBarrickNorthstAlistaireBenjaminaasJeffersonBarwickBarrickNorthstAlistaireBenjaminaas JeffersonBarwickBarrickNorthstAlistaireBenjaminaasJeffersonBarwickBarrickNorthstAlistaireBenjam Ave,Mooselookmeguntic,CA,US,92028,2000. 3,45.32,No,No,NULL,NULL,NULL,
      //CCPayment,133,96,GAPITesterCC145,NULL,12035,Brian,Baker,MasterCard,5454545454545454,5,25,369,Baker,Brian,123 ersonBarwickBarrickNorthstAlistaireBenjaminaasJeffersonBarwickBarrickNorthstAlistaireBenjaminaas JeffersonBarwickBarrickNorthstAlistaireBenjaminaasJeffersonBarwickBarrickNorthstAlistaireBenjam Ave,Mooselookmeguntic,CA,US,92028,2000 .03,45.32,No,No,NULL,NULL,NULL,
      //CCPayment,133,97,GAPITesterCC145,NULL,12035,Brian,Baker,MasterCard,5454545454545454,5,25,369,Baker,Brian,123 ersonBarwickBarrickNorthstAlistaireBenjaminaasJeffersonBarwickBarrickNorthstAlistaireBenjaminaas JeffersonBarwickBarrickNorthstAlistaireBenjaminaasJeffersonBarwickBarrickNorthstAlistaireBenjam Ave,Mooselookmeguntic,CA,US,92028,2000 .3,45.32,No,No,NULL,NULL,NULL,
      //CCPayment,133,98,GAPITesterCC145,NULL,12035,Brian,Baker,MasterCard,5454545454545454,5,25,369,Baker,Brian,123 ersonBarwickBarrickNorthstAlistaireBenjaminaasJeffersonBarwickBarrickNorthstAlistaireBenjaminaas JeffersonBarwickBarrickNorthstAlistaireBenjaminaasJeffersonBarwickBarrickNorthstAlistaireBenjam Ave,Mooselookmeguntic,CA,US,92028,2000.,45.32,No,No,NULL,NULL,NULL,
      //CCPayment,133,99,GAPITesterCC145,NULL,12035,Brian,Baker,MasterCard,5454545454545454,5,25,369,Baker,Brian,123 ersonBarwickBarrickNorthstAlistaireBenjaminaasJeffersonBarwickBarrickNorthstAlistaireBenjaminaas JeffersonBarwickBarrickNorthstAlistaireBenjaminaasJeffersonBarwickBarrickNorthstAlistaireBenjam Ave,Mooselookmeguntic,CA,US,92028,.0,45.32,No,No,NULL,NULL,NULL,
      //CCPayment,133,100,GAPITesterCC145,NULL,12035,Brian,Baker,MasterCard,5454545454545454,5,25,369,Baker,Brian,123 ersonBarwickBarrickNorthstAlistaireBenjaminaasJeffersonBarwickBarrickNorthstAlistaireBenjaminaas JeffersonBarwickBarrickNorthstAlistaireBenjaminaasJeffersonBarwickBarrickNorthstAlistaireBenjam Ave,Mooselookmeguntic,CA,US,92028,1.0a,45.32,No,No,NULL,NULL,NULL,
      testCases.add(
          CcPayment.createValid(
              dataHelper.getReferenceId(),
              "Test Cases 92 to 100 - Total Amount value is in invalid format: " + totalAmount,
              getExpectedResponse(gatewayErrors, "133"),
              payeeId)
              .setTotalAmount(totalAmount)
              .setIncurFee("No")
      );
    }

    for (String totalAmount : totalAmountInvalidValues) {
      //CCPayment,133,101,GAPITesterCC145,NULL,12035,N_-+=eil,Wil_-+=son,Visa,4444424444444440,5,25,369,N_-+=eil,Wil_-+=son,369 Atbash Court,Mooselookmeguntic,CA,US,92028,2000.003,45.32,Yes,No,NULL,NULL,NULL,
      //CCPayment,133,102,GAPITesterCC145,NULL,12035,N_-+=eil,Wil_-+=son,Visa,4444424444444440,5,25,369,N_-+=eil,Wil_-+=son,369 Atbash Court,Mooselookmeguntic,CA,US,92028,2000 03,45.32,Yes,No,NULL,NULL,NULL,
      //CCPayment,133,103,GAPITesterCC145,NULL,12035,N_-+=eil,Wil_-+=son,Visa,4444424444444440,5,25,369,N_-+=eil,Wil_-+=son,369 Atbash Court,Mooselookmeguntic,CA,US,92028,2000. 3,45.32,Yes,No,NULL,NULL,NULL,
      //CCPayment,133,104,GAPITesterCC145,NULL,12035,N_-+=eil,Wil_-+=son,Visa,4444424444444440,5,25,369,N_-+=eil,Wil_-+=son,369 Atbash Court,Mooselookmeguntic,CA,US,92028,2000. 03,45.32,Yes,No,NULL,NULL,NULL,
      //CCPayment,133,105,GAPITesterCC145,NULL,12035,N_-+=eil,Wil_-+=son,Visa,4444424444444440,5,25,369,N_-+=eil,Wil_-+=son,369 Atbash Court,Mooselookmeguntic,CA,US,92028,2000 .3,45.32,Yes,No,NULL,NULL,NULL,
      //CCPayment,133,106,GAPITesterCC145,NULL,12035,N_-+=eil,Wil_-+=son,Visa,4444424444444440,5,25,369,N_-+=eil,Wil_-+=son,369 Atbash Court,Mooselookmeguntic,CA,US,92028,2000 .03,45.32,Yes,No,NULL,NULL,NULL,
      //CCPayment,133,107,GAPITesterCC145,NULL,12035,N_-+=eil,Wil_-+=son,Visa,4444424444444440,5,25,369,N_-+=eil,Wil_-+=son,369 Atbash Court,Mooselookmeguntic,CA,US,92028,2000.,45.32,Yes,No,NULL,NULL,NULL,
      //CCPayment,133,108,GAPITesterCC145,NULL,12035,N_-+=eil,Wil_-+=son,Visa,4444424444444440,5,25,369,N_-+=eil,Wil_-+=son,369 Atbash Court,Mooselookmeguntic,CA,US,92028,.0,45.32,Yes,No,NULL,NULL,NULL,
      //CCPayment,133,109,GAPITesterCC145,NULL,12035,N_-+=eil,Wil_-+=son,Visa,4444424444444440,5,25,369,N_-+=eil,Wil_-+=son,369 Atbash Court,Mooselookmeguntic,CA,US,92028,1.0a,45.32,Yes,No,NULL,NULL,NULL,
      testCases.add(
          CcPayment.createValid(
              dataHelper.getReferenceId(),
              "Test Cases 101 to 109 - Total Amount value is in invalid format: " + totalAmount,
              getExpectedResponse(gatewayErrors, "133"),
              payeeId)
              .setTotalAmount(totalAmount)
              .setIncurFee("Yes")
      );
    }

    for (String incurFee : incurFeeValues) {
      //CCPayment,134,110,GAPITesterCC,NULL,12035,Lee,Frazier,MasterCard,df5454545,5,25,369,Lee,Frazier,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,No,No,NULL,NULL,NULL,
      //CCPayment,134,116,GAPITesterCC,NULL,12035,Lee,Frazier,MasterCard,df5454545,5,25,369,Lee,Frazier,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,Yes,No,NULL,NULL,NULL,
      //CCPayment,134,122,GAPITesterCC,NULL,12035,Lee,Frazier,MasterCard,df5454545,5,25,369,Lee,Frazier,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,,No,NULL,NULL,NULL,
      testCases.add(
          CcPayment.createValid(
              dataHelper.getReferenceId(),
              "Test Cases 110/116/122 - Invalid CC value, where incur fee value is: " + incurFee,
              getExpectedResponse(gatewayErrors, "134"),
              payeeId)
              .setCreditCardType("MasterCard")
              .setCreditCardNumber("df5454545")
              .setIncurFee(incurFee)
      );
    }

    for (String incurFee : incurFeeValues) {
      //CCPayment,134,111,GAPITesterCC,NULL,12035,Lee,Frazier,MasterCard,5454545 54545454,5,25,369,Lee,Frazier,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,No,No,NULL,NULL,NULL,
      //CCPayment,134,117,GAPITesterCC,NULL,12035,Lee,Frazier,MasterCard,5454545 54545454,5,25,369,Lee,Frazier,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,Yes,No,NULL,NULL,NULL,
      //CCPayment,134,123,GAPITesterCC,NULL,12035,Lee,Frazier,MasterCard,5454545 54545454,5,25,369,Lee,Frazier,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,,No,NULL,NULL,NULL,
      testCases.add(
          CcPayment.createValid(
              dataHelper.getReferenceId(),
              "Test Cases 111/117/123 - Invalid CC value, where incur fee value is: " + incurFee,
              getExpectedResponse(gatewayErrors, "134"),
              payeeId)
              .setCreditCardType("MasterCard")
              .setCreditCardNumber("5454545 54545454")
              .setIncurFee(incurFee)
      );
    }

    for (String incurFee : incurFeeValues) {
      //CCPayment,134,112,GAPITesterCC,NULL,12035,Amanda,Phillips,Visa,df444444441,5,25,369,Ronald,Regan,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,No,No,NULL,NULL,NULL,
      //CCPayment,134,118,GAPITesterCC,NULL,12035,Amanda,Phillips,Visa,df444444441,5,25,369,Ronald,Regan,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,Yes,No,NULL,NULL,NULL,
      //CCPayment,134,124,GAPITesterCC,NULL,12035,Amanda,Phillips,Visa,df444444441,5,25,369,Ronald,Regan,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,,No,NULL,NULL,NULL,
      testCases.add(
          CcPayment.createValid(
              dataHelper.getReferenceId(),
              "Test Cases 112/118/124 - Invalid CC value, where incur fee value is: " + incurFee,
              getExpectedResponse(gatewayErrors, "134"),
              payeeId)
              .setCreditCardType("Visa")
              .setCreditCardNumber("df444444441")
              .setIncurFee(incurFee)
      );
    }

    for (String incurFee : incurFeeValues) {
      //CCPayment,134,113,GAPITesterCC,NULL,12035,Amanda,Phillips,Visa,4444414 44444441,5,25,369,Ronald,Regan,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,No,No,NULL,NULL,NULL,
      //CCPayment,134,119,GAPITesterCC,NULL,12035,Amanda,Phillips,Visa,4444414 44444441,5,25,369,Ronald,Regan,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,Yes,No,NULL,NULL,NULL,
      //CCPayment,134,125,GAPITesterCC,NULL,12035,Amanda,Phillips,Visa,4444414 44444441,5,25,369,Ronald,Regan,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,,No,NULL,NULL,NULL,
      testCases.add(
          CcPayment.createValid(
              dataHelper.getReferenceId(),
              "Test Cases 113/119/125 - Invalid CC value, where incur fee value is: " + incurFee,
              getExpectedResponse(gatewayErrors, "134"),
              payeeId)
              .setCreditCardType("Visa")
              .setCreditCardNumber("4444414 44444441")
              .setIncurFee(incurFee)
      );
    }

    for (String incurFee : incurFeeValues) {
      //CCPayment,134,114,GAPITesterCC,NULL,12035,Kurt,Myers,Discover,saf60110500000,5,25,136,Kurt,Myers,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,No,No,NULL,NULL,NULL,
      //CCPayment,134,120,GAPITesterCC,NULL,12035,Kurt,Myers,Discover,saf60110500000,5,25,136,Kurt,Myers,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,Yes,No,NULL,NULL,NULL,
      //CCPayment,134,126,GAPITesterCC,NULL,12035,Kurt,Myers,Discover,saf60110500000,5,25,136,Kurt,Myers,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,,No,NULL,NULL,NULL,
      testCases.add(
          CcPayment.createValid(
              dataHelper.getReferenceId(),
              "Test Cases 114/120/126 - Invalid CC value, where incur fee value is: " + incurFee,
              getExpectedResponse(gatewayErrors, "134"),
              payeeId)
              .setCreditCardType("Discover")
              .setCreditCardNumber("saf60110500000")
              .setIncurFee(incurFee)
      );
    }

    for (String incurFee : incurFeeValues) {
      //CCPayment,134,115,GAPITesterCC,NULL,12035,Kurt,Myers,Discover,6011000 95500000,5,25,136,Kurt,Myers,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,No,No,NULL,NULL,NULL,
      //CCPayment,134,121,GAPITesterCC,NULL,12035,Kurt,Myers,Discover,6011000 95500000,5,25,136,Kurt,Myers,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,Yes,No,NULL,NULL,NULL,
      //CCPayment,134,127,GAPITesterCC,NULL,12035,Kurt,Myers,Discover,6011000 95500000,5,25,136,Kurt,Myers,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,,No,NULL,NULL,NULL,
      testCases.add(
          CcPayment.createValid(
              dataHelper.getReferenceId(),
              "Test Cases 114/120/126 - Invalid CC value, where incur fee value is: " + incurFee,
              getExpectedResponse(gatewayErrors, "134"),
              payeeId)
              .setCreditCardType("Discover")
              .setCreditCardNumber("6011000 95500000")
              .setIncurFee(incurFee)
      );

    }

    String[] creditCardExpMonthInvalidValues = {
        "a", "a1", "1a", "1a1", "2 41",
    };

    for (String creditCardExpMonth : creditCardExpMonthInvalidValues) {
      //CCPayment,135,128,GAPITesterCC,NULL,12035,Robert,Diaz,Visa,4444424444444440,a,25,369,Robert,Diaz,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,No,No,NULL,NULL,NULL,
      //CCPayment,135,129,GAPITesterCC,NULL,12035,Robert,Diaz,Visa,4444424444444440,1a,25,369,Robert,Diaz,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,No,No,NULL,NULL,NULL,
      //CCPayment,135,130,GAPITesterCC,NULL,12035,Robert,Diaz,Visa,4444424444444440,a1,25,369,Robert,Diaz,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,No,No,NULL,NULL,NULL,
      //CCPayment,135,131,GAPITesterCC,NULL,12035,Robert,Diaz,Visa,4444424444444440,1a1,25,369,Robert,Diaz,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,No,No,NULL,NULL,NULL,
      //CCPayment,135,132,GAPITesterCC,NULL,12035,Robert,Diaz,Visa,4444424444444440,2 41,25,369,Robert,Diaz,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,No,No,NULL,NULL,NULL,
      testCases.add(
          CcPayment.createValid(
              dataHelper.getReferenceId(),
              "Test Cases 128 to 132 - Invalid CC exp month: " + creditCardExpMonth,
              getExpectedResponse(gatewayErrors, "135"),
              payeeId)
              .setCreditCardType("Visa")
              .setCreditCardNumber("4444424444444440")
              .setCreditExpMonth(creditCardExpMonth)
              .setIncurFee("No")
      );
    }

    for (String creditCardExpMonth : creditCardExpMonthInvalidValues) {
      //CCPayment,135,133,GAPITesterCC,NULL,12035,Mary,Powell,MasterCard,5454545454545454,a,25,369,Mary,Powell,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,,No,NULL,NULL,NULL,
      //CCPayment,135,134,GAPITesterCC,NULL,12035,Mary,Powell,MasterCard,5454545454545454,1a,25,369,Mary,Powell,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,,No,NULL,NULL,NULL,
      //CCPayment,135,135,GAPITesterCC,NULL,12035,Mary,Powell,MasterCard,5454545454545454,a1,25,369,Mary,Powell,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,,No,NULL,NULL,NULL,
      //CCPayment,135,136,GAPITesterCC,NULL,12035,Mary,Powell,MasterCard,5454545454545454,1a1,25,369,Mary,Powell,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,,No,NULL,NULL,NULL,
      if (!creditCardExpMonth.equals("2 41")) {
        testCases.add(
            CcPayment.createValid(
                dataHelper.getReferenceId(),
                "Test Cases 133 to 136 - Invalid CC exp month: " + creditCardExpMonth,
                getExpectedResponse(gatewayErrors, "135"),
                payeeId)
                .setCreditCardType("MasterCard")
                .setCreditCardNumber("5112000100000003")
                .setCreditExpMonth(creditCardExpMonth)
                .setIncurFee("")
        );
      }
    }

    String[] creditCardExpYearInvalidValues = {
        "a", "a1", "1a", "1a1", "1 8",
    };

    for (String creditCardExpYear : creditCardExpYearInvalidValues) {
      //CCPayment,136,137,GAPITesterCC,NULL,12035,Jose,Edwards,MasterCard,5454545454545454,5,a,369,Jose,Edwards,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,No,No,NULL,NULL,NULL,
      //CCPayment,136,138,GAPITesterCC,NULL,12035,Jose,Edwards,MasterCard,5454545454545454,5,1a,369,Jose,Edwards,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,No,No,NULL,NULL,NULL,
      //CCPayment,136,139,GAPITesterCC,NULL,12035,Jose,Edwards,MasterCard,5454545454545454,5,a1,369,Jose,Edwards,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,No,No,NULL,NULL,NULL,
      //CCPayment,136,140,GAPITesterCC,NULL,12035,Jose,Edwards,MasterCard,5454545454545454,5,1a1,369,Jose,Edwards,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,No,No,NULL,NULL,NULL,
      //CCPayment,136,141,GAPITesterCC,NULL,12035,Jose,Edwards,MasterCard,5454545454545454,5,1 8,369,Jose,Edwards,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,No,No,NULL,NULL,NULL,
      testCases.add(
          CcPayment.createValid(
              dataHelper.getReferenceId(),
              "Test Cases 137 to 141 - Invalid CC exp year: " + creditCardExpYear,
              getExpectedResponse(gatewayErrors, "136"),
              payeeId)
              .setCreditCardType("MasterCard")
              .setCreditCardNumber("5112000100000003")
              .setCreditExpYear(creditCardExpYear)
              .setIncurFee("No")
      );
    }

    for (String creditCardExpYear : creditCardExpYearInvalidValues) {
      //CCPayment,136,142,GAPITesterCC,NULL,12035,Lee,Frazier,Visa,4444424444444440,5,a,369,Lee,Frazier,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,Yes,No,NULL,NULL,NULL,
      //CCPayment,136,143,GAPITesterCC,NULL,12035,Lee,Frazier,Visa,4444424444444440,5,1a,369,Lee,Frazier,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,Yes,No,NULL,NULL,NULL,
      //CCPayment,136,144,GAPITesterCC,NULL,12035,Lee,Frazier,Visa,4444424444444440,5,a1,369,Lee,Frazier,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,Yes,No,NULL,NULL,NULL,
      //CCPayment,136,145,GAPITesterCC,NULL,12035,Lee,Frazier,Visa,4444424444444440,5,1a1,369,Lee,Frazier,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,Yes,No,NULL,NULL,NULL,
      //CCPayment,136,146,GAPITesterCC,NULL,12035,Lee,Frazier,Visa,4444424444444440,5,1 8,369,Lee,Frazier,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,Yes,No,NULL,NULL,NULL,
      testCases.add(
          CcPayment.createValid(
              dataHelper.getReferenceId(),
              "Test Cases 142 to 146 - Invalid CC exp year: " + creditCardExpYear,
              getExpectedResponse(gatewayErrors, "136"),
              payeeId)
              .setCreditCardType("Visa")
              .setCreditCardNumber("4444424444444440")
              .setCreditExpYear(creditCardExpYear)
              .setIncurFee("Yes")
      );
    }

    for (String creditCardExpYear : creditCardExpYearInvalidValues) {
      //CCPayment,136,147,GAPITesterCC,NULL,12035,Tammy,Murphy,Discover,6011000995500000,5,a,136,Tammy,Murphy,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,,No,NULL,NULL,NULL,
      //CCPayment,136,148,GAPITesterCC,NULL,12035,Tammy,Murphy,Discover,6011000995500000,5,a1,136,Tammy,Murphy,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,,No,NULL,NULL,NULL,
      //CCPayment,136,149,GAPITesterCC,NULL,12035,Tammy,Murphy,Discover,6011000995500000,5,1a,136,Tammy,Murphy,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,,No,NULL,NULL,NULL,
      //CCPayment,136,150,GAPITesterCC,NULL,12035,Tammy,Murphy,Discover,6011000995500000,5,1a1,136,Tammy,Murphy,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,,No,NULL,NULL,NULL,
      //CCPayment,136,151,GAPITesterCC,NULL,12035,Tammy,Murphy,Discover,6011000995500000,5,1 8,136,Tammy,Murphy,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,,No,NULL,NULL,NULL,
      testCases.add(
          CcPayment.createValid(
              dataHelper.getReferenceId(),
              "Test Cases 147 to 151 - Invalid CC exp year: " + creditCardExpYear,
              getExpectedResponse(gatewayErrors, "136"),
              payeeId)
              .setCreditCardType("Discover")
              .setCreditCardNumber("6011000995500000")
              .setCreditExpYear(creditCardExpYear)
              .setIncurFee("")
      );
    }

    String[] creditCardCvv2InvalidValues = {
        "a", "a1", "1a", "1a1", "36 9",
    };

    for (String creditCardCvv2 : creditCardCvv2InvalidValues) {
      //CCPayment,137,152,GAPITesterCC,NULL,12035,Lee,Frazier,Visa,4444424444444440,5,25,a,Lee,Frazier,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,No,No,NULL,NULL,NULL,
      //CCPayment,137,153,GAPITesterCC,NULL,12035,Lee,Frazier,Visa,4444424444444440,5,25,1a,Lee,Frazier,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,No,No,NULL,NULL,NULL,
      //CCPayment,137,154,GAPITesterCC,NULL,12035,Lee,Frazier,Visa,4444424444444440,5,25,a1,Lee,Frazier,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,No,No,NULL,NULL,NULL,
      //CCPayment,137,155,GAPITesterCC,NULL,12035,Lee,Frazier,Visa,4444424444444440,5,25,1a1,Lee,Frazier,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,No,No,NULL,NULL,NULL,
      //CCPayment,137,156,GAPITesterCC,NULL,12035,Lee,Frazier,Visa,4444424444444440,5,25,36 9,Lee,Frazier,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,No,No,NULL,NULL,NULL,
      testCases.add(
          CcPayment.createValid(
              dataHelper.getReferenceId(),
              "Test Cases 152 to 156 - Invalid CVV2: " + creditCardCvv2,
              getExpectedResponse(gatewayErrors, "137"),
              payeeId)
              .setCreditCardType("Visa")
              .setCreditCardNumber("4444424444444440")
              .setCreditCardCvv2(creditCardCvv2)
              .setIncurFee("No")
      );

      //CCPayment,137,157,GAPITesterCC,NULL,12035,Jose,Edwards,MasterCard,5454545454545454,5,25,a,Jose,Edwards,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,Yes,No,NULL,NULL,NULL,
      //CCPayment,137,158,GAPITesterCC,NULL,12035,Jose,Edwards,MasterCard,5454545454545454,5,25,1a,Jose,Edwards,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,Yes,No,NULL,NULL,NULL,
      //CCPayment,137,159,GAPITesterCC,NULL,12035,Jose,Edwards,MasterCard,5454545454545454,5,25,a1,Jose,Edwards,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,Yes,No,NULL,NULL,NULL,
      //CCPayment,137,160,GAPITesterCC,NULL,12035,Jose,Edwards,MasterCard,5454545454545454,5,25,1a1,Jose,Edwards,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,Yes,No,NULL,NULL,NULL,
      //CCPayment,137,161,GAPITesterCC,NULL,12035,Jose,Edwards,MasterCard,5454545454545454,5,25,36 9,Jose,Edwards,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,Yes,No,NULL,NULL,NULL,
      testCases.add(
          CcPayment.createValid(
              dataHelper.getReferenceId(),
              "Test Cases 157 to 161 - Invalid CVV2: " + creditCardCvv2,
              getExpectedResponse(gatewayErrors, "137"),
              payeeId)
              .setCreditCardType("MasterCard")
              .setCreditCardNumber("5112000100000003")
              .setCreditCardCvv2(creditCardCvv2)
              .setIncurFee("Yes")
      );
    }

    String[] invalidZipCodeValues = {
        "a1234578901234569871", "12345789012345698 1",
    };

    for (String zipCode : invalidZipCodeValues) {
      //CCPayment,139,162,GAPITesterCC,NULL,12035,JeffersonBarwickBarrickNorthst,AlistaireBenjaminLafayetteTrya,MasterCard,5454545454545454,5,25,369,Eugene,Carter,123 ersonBarwickBarrickNorthstAlistaireBenjaminaasJeffersonBarwickBarrickNorthstAlistaireBenjaminaas JeffersonBarwickBarrickNorthstAlistaireBenjaminaasJeffersonBarwickBarrickNorthstAlistaireBenjam Ave,Fallbrook,CA,US,a1234578901234569871,325.25,45.32,No,No,NULL,NULL,NULL,
      //CCPayment,139,163,GAPITesterCC,NULL,12035,JeffersonBarwickBarrickNorthst,AlistaireBenjaminLafayetteTrya,MasterCard,5454545454545454,5,25,369,Eugene,Carter,123 ersonBarwickBarrickNorthstAlistaireBenjaminaasJeffersonBarwickBarrickNorthstAlistaireBenjaminaas JeffersonBarwickBarrickNorthstAlistaireBenjaminaasJeffersonBarwickBarrickNorthstAlistaireBenjam Ave,Fallbrook,CA,US,12345789012345698 1,325.25,45.32,No,No,NULL,NULL,NULL,

      testCases.add(
          CcPayment.createValid(
              dataHelper.getReferenceId(),
              "Test Cases 162/163 - Invalid ZipCode: " + zipCode,
              getExpectedResponse(gatewayErrors, "139"),
              payeeId)
              .setCreditCardType("MasterCard")
              .setCreditCardNumber("5112000100000003")
              .setBillingZip(zipCode)
              .setIncurFee("No")
      );

      //CCPayment,139,164,GAPITesterCC,NULL,12035,Amanda,Phillips,Discover,6011000995500000,5,25,136,Ronald,Regan,369 Atbash Court,Fallbrook,CA,US,a1234578901234569871,325.25,45.32,,No,NULL,NULL,NULL,
      //CCPayment,139,165,GAPITesterCC,NULL,12035,Amanda,Phillips,Discover,6011000995500000,5,25,136,Ronald,Regan,369 Atbash Court,Fallbrook,CA,US,12345789012345698 1,325.25,45.32,,No,NULL,NULL,NULL,
      testCases.add(
          CcPayment.createValid(
              dataHelper.getReferenceId(),
              "Test Cases 164/165 - Invalid ZipCode: " + zipCode,
              getExpectedResponse(gatewayErrors, "139"),
              payeeId)
              .setCreditCardType("Discover")
              .setCreditCardNumber("6011000995500000")
              .setBillingZip(zipCode)
              .setIncurFee("")
      );
    }

    //CCPayment,159,166,GAPITesterCC,NULL,12035,Amanda,Phillips,Visa,5454545454545454,5,25,369,Ronald,Regan,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,No,No,NULL,NULL,NULL,
    testCases.add(
        CcPayment.createValid(
            dataHelper.getReferenceId(),
            "Test Cases 166 - Credit Card Number mismatch",
            getExpectedResponse(gatewayErrors, "159"),
            payeeId)
            .setCreditCardType("Visa")
            .setCreditCardNumber("5112000100000003")
            .setIncurFee("No")
    );
    //CCPayment,159,167,GAPITesterCC,NULL,12035,Mary,Powell,MasterCard,4444424444444440,5,25,369,Mary,Powell,369 Atbash Court,Fallbrook,CA,US,12345678901234600000,325.25,45.32,Yes,No,NULL,NULL,NULL,
    testCases.add(
        CcPayment.createValid(
            dataHelper.getReferenceId(),
            "Test Cases 167 - Credit Card Number mismatch",
            getExpectedResponse(gatewayErrors, "159"),
            payeeId)
            .setCreditCardType("MasterCard")
            .setCreditCardNumber("4444424444444440")
            .setIncurFee("Yes")
    );

    //CCPayment,159,168,GAPITesterCC,NULL,12035,Amanda,Phillips,Discover,5454545454545454,5,25,369,Ronald,Regan,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,,No,NULL,NULL,NULL,
    testCases.add(
        CcPayment.createValid(
            dataHelper.getReferenceId(),
            "Test Cases 168 - Credit Card Number mismatch",
            getExpectedResponse(gatewayErrors, "159"),
            payeeId)
            .setCreditCardType("Discover")
            .setCreditCardNumber("5112000100000003")
            .setIncurFee("")
    );

    //CCPayment,186,169,GAPITesterCC,NULL,12035,N_-+=eil,Wil_-+=son,MasterCard,5454545454545454,5,25,369,N_-+=eil,Wil_-+=son,369 Atbash Court,Fallbrook,CA,US,92028,325.25,45.32,No,No,NULL,VOID,NULL,
    testCases.add(
        CcPayment.createValid(
            dataHelper.getReferenceId(),
            "Test Case 169 - Invalid Credit Card Action Type",
            getExpectedResponse(gatewayErrors, "186"),
            payeeId)
            .setCreditCardAction("VOID")
    );

    executeTests(testCases);
  }


  @Test(groups = {"gapi", "CCPayment", "litle"})
  public void largeBatchValid() {
    Logger.info("CC Payment Test - Large Batch");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String payeeId = testSetupPage.getString("payeeId");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    DataHelper dataHelper = new DataHelper();
    TestCaseCollection testCases = new GapiTestCaseCollection(
        new Credentials(gatewayId, username, password)
    );
    for (int i = 0; i < 2; i++) {

      //CCPayment,8,1,GAPITesterCC,qagapisecondrefid,12035,LARGE_BATCH,2,,,,,,,,,,,,,,,,,,,,
      testCases.add(
          CcPayment.createValid(
              dataHelper.getReferenceId(),
              "Test case 1",
              getExpectedResponse(gatewayErrors, "8"),
              payeeId)
      );
    }

    executeTests(testCases);
  }

  @Test(groups = {"gapi", "CCPayment", "litle"})
  public void invalidLargeBatchCreditCard() throws Exception {
    Logger.info("Cc Payment Test Invalid Large Batch");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String payeeId = testSetupPage.getString("payeeId");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    Credentials credentials = new Credentials(gatewayId, username, password);
    GapiRequest gapiRequest = new GapiRequest(credentials);

    DataHelper dataHelper = new DataHelper();

    for (int i = 0; i < 101; i++) {
      CcPayment.createValid(
          dataHelper.getReferenceId(),
          "Unused",
          getExpectedResponse(gatewayErrors, ""),
          payeeId)
          .setPayerReferenceId("GAPITesterCC")
          .setPayerSecondaryRefId("qagapisecondrefid").addTransaction(gapiRequest);
    }

    TestCaseCollection testCases = new GapiTestCaseCollection(credentials);

    //CCPayment,261,204,GAPITesterCC,qagapisecondrefid,12035,LARGE_BATCH,101,,,,,,,,,,,,,,,,,,,,
    testCases.add(
        new ErrorTestCase(
            "Test case 204: Invalid Large Batch Test",
            getExpectedResponse(gatewayErrors, "261")
        )
    );

    GapiResponse response = gapiRequest.sendRequest();

    Assert.assertTrue(isCollectionValid(testCases, response), "Large batch failed");
  }

  @Test(groups = {"gapi", "CCPayment", "paysafe"})
  public void ccPaymentCad() throws Exception {
    Logger.info("CC Payment Test - Paysafe");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc4");
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String payeeId = testSetupPage.getString("payeeId");
    final String bankAccountId = testSetupPage.getString("bankAccountId");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    DataHelper dataHelper = new DataHelper();
    GapiRequest gapiRequest = new GapiRequest(new Credentials(gatewayId, username, password));

    CcPayment testCase = CcPayment.createValid(
        dataHelper.getReferenceId(),
        "Test Case - CC payment with Paysafe",
        getExpectedResponse(gatewayErrors, "8"),
        payeeId)
        .setBillingStreetAddress("369 Atbash Court")
        .setBillingState("ON")
        .setBillingCountry("CA")
        .setBillingZip("M3C0C1")
        .setTotalAmount("100.00")
        .setFeeAmount("")
        .setIncurFee("No")
        .setCurrencyCode("CAD");

    testCase.addTransaction(gapiRequest);

    GapiResponse response = gapiRequest.sendRequest();
    response.setIndex("1");

    String responseCode = response.getSpecificElementValue("Code");
    Assert.assertEquals(responseCode, "8", "Transaction should be processed successfully");

    String transId = response.getSpecificElementValue("TransactionId");

    TransactionPage transactionPage = new TransactionPage();
    transactionPage.open();
    TransactionProcessingPage transactionProcessingPage = transactionPage
        .getDataForTransactionId(transId);

    Assert.assertTrue(transactionProcessingPage.isPayoutPresent(bankAccountId, "100.00"),
        "BankAccount payout should be present");
  }

  @Test(groups = {"gapi", "CCPayment", "paysafe"})
  public void ccPaymentCad2() {
    Logger.info("CC Payment Test for Paysafe fees - AapiResponse code 8");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc4");
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String payeeId = testSetupPage.getString("payeeId");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    DataHelper dataHelper = new DataHelper();
    TestCaseCollection testCases = new GapiTestCaseCollection(
        new Credentials(gatewayId, username, password)
    );

    //CCPayment,8,2,GAPITesterCC,qagapisecondrefid,12035,Bryon,Hendrix,Visa,4003440000000007,5,25,369,Bryon,Hendrix,369 Atbash Court,Fallbrook,ON,CA,M3C0C1,100.00,,No,Yes,Test Message,NULL,,20
    testCases.add(
        new CcPayment(
            dataHelper.getReferenceId(),
            "Test Case 1 - Empty fee amount and incur fee NO, Visa Paysafe",
            getExpectedResponse(gatewayErrors, "8"),
            payeeId)
            .setPayerReferenceId("GAPITesterCC")
            .setPayerSecondaryRefId("qagapisecondrefid")
            .setPayerFirstName("Bryon")
            .setPayerLastName("Hendrix")
            .setCreditCardType("Visa")
            .setCreditCardNumber("4003440000000007")
            .setCreditExpMonth("5")
            .setCreditExpYear("25")
            .setCreditCardCvv2("369")
            .setBillingFirstName("Bryon")
            .setBillingLastName("Hendrix")
            .setBillingStreetAddress("369 Atbash Court")
            .setBillingCity("Fallbrook")
            .setBillingState("ON")
            .setBillingCountry("CA")
            .setBillingZip("M3C0C1")
            .setTotalAmount("100.00")
            .setFeeAmount("")
            .setMessage("Test Message")
            .setIncurFee("No")
            .setSaveAccount("Yes")
            .setCurrencyCode("CAD")
    );

    //CCPayment,8,6,GAPITesterCC,NULL,12035,Bryon,Hendrix,MasterCard,5454545454545454,5,25,369,Bryon,Hendrix,369 Atbash Court,Fallbrook,ON,CA,M3C0C1,1300.00,,Yes,No,NULL,AUTH,,
    testCases.add(
        new CcPayment(
            dataHelper.getReferenceId(),
            "Test Case 2 - Empty fee amount, Incur fee yes",
            getExpectedResponse(gatewayErrors, "8"),
            payeeId)
            .setPayerReferenceId("GAPITesterCC")
            .setPayerFirstName("Bryon")
            .setPayerLastName("Hendrix")
            .setCreditCardType("MasterCard")
            .setCreditCardNumber("5112000100000003")
            .setCreditExpMonth("5")
            .setCreditExpYear("25")
            .setCreditCardCvv2("369")
            .setBillingFirstName("Bryon")
            .setBillingLastName("Hendrix")
            .setBillingStreetAddress("369 Atbash Court")
            .setBillingCity("Fallbrook")
            .setBillingState("ON")
            .setBillingCountry("CA")
            .setBillingZip("M3C0C1")
            .setTotalAmount("100.00")
            .setFeeAmount("")
            .setIncurFee("Yes")
            .setSaveAccount("No")
            .setCreditCardAction("AUTH")
            .setCurrencyCode("CAD")
    );

    //CCPayment,8,7,GAPITesterCC,NULL,12035,Kaylee,Lanier,MasterCard,5454545454545454,5,25,369,Kaylee,Lanier,369 Atbash Court,Fallbrook,ON,CA,M3C0C1,2750.36,45.00,No,No,NULL,CAPTURE,LARGE,2
    testCases.add(
        new CcPayment(
            dataHelper.getReferenceId(),
            "Test Case 3 -  Fee amount set, Incur fee NO for Paysafe",
            getExpectedResponse(gatewayErrors, "8"),
            payeeId)
            .setPayerReferenceId("GAPITesterCC")
            .setPayerFirstName("Kaylee")
            .setPayerLastName("Lanier")
            .setCreditCardType("MasterCard")
            .setCreditCardNumber("5112000100000003")
            .setCreditExpMonth("5")
            .setCreditExpYear("25")
            .setCreditCardCvv2("369")
            .setBillingFirstName("Kaylee")
            .setBillingLastName("Lanier")
            .setBillingStreetAddress("369 Atbash Court")
            .setBillingCity("Fallbrook")
            .setBillingState("ON")
            .setBillingCountry("CA")
            .setBillingZip("M3C0C1")
            .setTotalAmount("2750.36")
            .setFeeAmount("45.00")
            .setIncurFee("No")
            .setSaveAccount("No")
            .setCreditCardAction("CAPTURE")
            .setCurrencyCode("CAD")
    );

    //CCPayment,8,8,GAPITesterCC,NULL,12035,Brian,Baker,MasterCard,5454545454545454,5,25,369,Baker,Brian,369 Atbash Cour,Mooselookmeguntic,ON,CA,M3C0C1,325.25,45.00,Yes,No,NULL,NULL,NULL,
    testCases.add(
        new CcPayment(
            dataHelper.getReferenceId(),
            "Test Case 4 - Fee amount set and Incur fee yes for Paysafe",
            getExpectedResponse(gatewayErrors, "8"),
            payeeId)
            .setPayerReferenceId("GAPITesterCC")
            .setPayerFirstName("Brian")
            .setPayerLastName("Baker")
            .setCreditCardType("MasterCard")
            .setCreditCardNumber("5112000100000003")
            .setCreditExpMonth("5")
            .setCreditExpYear("25")
            .setCreditCardCvv2("369")
            .setBillingFirstName("Brian")
            .setBillingLastName("Baker")
            .setBillingStreetAddress(
                "369 Atbash Cour")
            .setBillingCity("Mooselookmeguntic")
            .setBillingState("ON")
            .setBillingCountry("CA")
            .setBillingZip("M3C0C1")
            .setTotalAmount("325.25")
            .setFeeAmount("45.00")
            .setIncurFee("Yes")
            .setSaveAccount("No")
            .setCurrencyCode("CAD")
    );

    //CCPayment,8,9,GAPITesterCC,NULL,12035,Eugene,Carter,MasterCard,5454545454545454,5,25,369,Eugene,Carter,369 Atbash Court,Fallbrook,ON,CA,M3C0C1,3200.00,100.00,,No,NULL,NULL,NULL,
    testCases.add(
        new CcPayment(
            dataHelper.getReferenceId(),
            "Test Case 5 - Fee amount set ,Incur fee empty for Paysafe",
            getExpectedResponse(gatewayErrors, "8"),
            payeeId)
            .setPayerReferenceId("GAPITesterCC")
            .setPayerFirstName("Eugene")
            .setPayerLastName("Carter")
            .setCreditCardType("MasterCard")
            .setCreditCardNumber("5112000100000003")
            .setCreditExpMonth("5")
            .setCreditExpYear("25")
            .setCreditCardCvv2("369")
            .setBillingFirstName("Eugene")
            .setBillingLastName("Carter")
            .setBillingStreetAddress("369 Atbash Court")
            .setBillingCity("Fallbrook")
            .setBillingState("ON")
            .setBillingCountry("CA")
            .setBillingZip("M3C0C1")
            .setTotalAmount("3200.00")
            .setFeeAmount("100.00")
            .setIncurFee("")
            .setSaveAccount("No")
            .setCurrencyCode("CAD")
    );

    //CCPayment,8,10,GAPITesterCC,NULL,12035,Bryon,Hendrix,MasterCard,5454545454545454,5,25,369,Bryon,Hendrix,369 Atbash Court,Fallbrook,ON,CA,M3C0C1,3200.00,NULL,,No,NULL,NULL,NULL,
    testCases.add(
        new CcPayment(
            dataHelper.getReferenceId(),
            "Test Case 6 - Incur fee empty and Fee amount empty for paysafe",
            getExpectedResponse(gatewayErrors, "8"),
            payeeId)
            .setPayerReferenceId("GAPITesterCC")
            .setPayerFirstName("Bryon")
            .setPayerLastName("Hendrix")
            .setCreditCardType("MasterCard")
            .setCreditCardNumber("5112000100000003")
            .setCreditExpMonth("5")
            .setCreditExpYear("25")
            .setCreditCardCvv2("369")
            .setBillingFirstName("Bryon")
            .setBillingLastName("Hendrix")
            .setBillingStreetAddress("369 Atbash Court")
            .setBillingCity("Fallbrook")
            .setBillingState("ON")
            .setBillingCountry("CA")
            .setBillingZip("M3C0C1")
            .setTotalAmount("3200.00")
            .setFeeAmount("")
            .setIncurFee("")
            .setSaveAccount("No")
            .setCurrencyCode("CAD")
    );

    executeTests(testCases);
  }

  @Test(groups = {"gapi", "CCPayment", "litle"})
  public void ccPaymentUsd(){
    Logger.info("CC Payment Test - Litle");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String payeeId = testSetupPage.getString("payeeId");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    DataHelper dataHelper = new DataHelper();
    TestCaseCollection testCases = new GapiTestCaseCollection(
        new Credentials(gatewayId, username, password)
    );
    testCases.add(CcPayment.createValid(
        dataHelper.getReferenceId(),
        "Test Case - CC payment with litle",
        getExpectedResponse(gatewayErrors, "8"),
        payeeId)
        .setTotalAmount("100.00")
        .setFeeAmount("")
        .setMessage("Test Message")
        .setIncurFee("No")
        .setCurrencyCode("USD")
    );
    executeTests(testCases);
  }

  @Test(groups = {"gapi", "CCPayment", "litle"})
  public void ccSplitPaymentDifferentPropertyUsd() {
    Logger.info("CC Payment Test - Litle");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc5");
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String payeeId = testSetupPage.getString("payeeId");
    final String payeeId1 = testSetupPage.getString("payeeId1");
    final String payeeId2 = testSetupPage.getString("payeeId2");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    DataHelper dataHelper = new DataHelper();
    TestCaseCollection testCases = new GapiTestCaseCollection(
        new Credentials(gatewayId, username, password)
    );

    testCases.add(CcPayment.createValid(
        dataHelper.getReferenceId(),
        "Test Case - CC payment with litle",
        getExpectedResponse(gatewayErrors, "8"),
        payeeId)
        .addDepositItem(payeeId,
            "100.00")
        .addDepositItem(payeeId1,
            "15.00")
        .addDepositItem(payeeId2,
            "20.00")
        .setIncurFee("Yes")
        .setTotalAmount("135.00")
    );

    executeTests(testCases);
  }

  @Test(groups = {"gapi", "CCPayment", "litle"})
  public void ccSplitPaymentSamePropertyUsd() {
    Logger.info("CC Payment Test - Litle");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6");
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String payeeId = testSetupPage.getString("payeeId");
    final String payeeId1 = testSetupPage.getString("payeeId1");
    final String payeeId2 = testSetupPage.getString("payeeId2");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    DataHelper dataHelper = new DataHelper();
    TestCaseCollection testCases = new GapiTestCaseCollection(
        new Credentials(gatewayId, username, password)
    );

    testCases.add(CcPayment.createValid(
        dataHelper.getReferenceId(),
        "Test Case - CC payment with litle",
        getExpectedResponse(gatewayErrors, "8"),
        payeeId)
        .addDepositItem(payeeId,
            "100.00")
        .addDepositItem(payeeId1,
            "15.00")
        .addDepositItem(payeeId2,
            "20.00")
        .setIncurFee("Yes")
        .setTotalAmount("135.00")
    );

    executeTests(testCases);
  }

  @Test(groups = {"gapi", "CCPayment", "Litle"})
  public void ccPaymentSplitDeposit2() {
    Logger.info("CC Payment Test for split deposit fees - GapiResponse code 8");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc5");
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String payeeId = testSetupPage.getString("payeeId");
    final String payeeId1 = testSetupPage.getString("payeeId1");
    final String payeeId2 = testSetupPage.getString("payeeId2");
    final String payeeId3 = testSetupPage.getString("payeeId3");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    DataHelper dataHelper = new DataHelper();

    TestCaseCollection testCases = new GapiTestCaseCollection(
        new Credentials(gatewayId, username, password)
    );

    //CCPayment,8,2,GAPITesterCC,qagapisecondrefid,12035,Bryon,Hendrix,Visa,4003440000000007,5,25,369,Bryon,Hendrix,369 Atbash Court,Fallbrook,ON,CA,M3C0C1,100.00,,No,Yes,Test Message,NULL,,20
    testCases
        .add(
            new CcPayment(
                dataHelper.getReferenceId(),
                "Test Case 1 - Empty fee amount and incur fee NO",
                getExpectedResponse(gatewayErrors, "8"),
                payeeId)
                .setPayerReferenceId("GAPITesterCC")
                .setPayerSecondaryRefId("qagapisecondrefid")
                .setPayerFirstName("Bryon")
                .setPayerLastName("Hendrix")
                .addDepositItem(payeeId,
                    "100.00")
                .addDepositItem(payeeId1,
                    "15.00")
                .addDepositItem(payeeId2,
                    "20.00")
                .addDepositItem(payeeId3,
                    "25.00")
                .setCreditCardType("Visa")
                .setCreditCardNumber("4444444444444448")
                .setCreditExpMonth("5")
                .setCreditExpYear("25")
                .setCreditCardCvv2("369")
                .setBillingFirstName("Bryon")
                .setBillingLastName("Hendrix")
                .setBillingStreetAddress("369 Atbash Court")
                .setBillingCity("Fallbrook")
                .setBillingState("CA")
                .setBillingCountry("US")
                .setBillingZip("92122")
                .setTotalAmount("160.00")
                .setFeeAmount("")
                .setMessage("Test Message")
                .setIncurFee("No")
                .setSaveAccount("Yes")
                .setCurrencyCode("USD")
        );

    //CCPayment,8,6,GAPITesterCC,NULL,12035,Bryon,Hendrix,MasterCard,5454545454545454,5,25,369,Bryon,Hendrix,369 Atbash Court,Fallbrook,ON,CA,M3C0C1,1300.00,,Yes,No,NULL,AUTH,,
    testCases
        .add(
            new CcPayment(
                dataHelper.getReferenceId(),
                "Test Case 2 - Empty fee amount, Incur fee yes",
                getExpectedResponse(gatewayErrors, "8"),
                payeeId)
                .setPayerReferenceId("GAPITesterCC")
                .setPayerSecondaryRefId("qagapisecondrefid")
                .setPayerFirstName("Bryon")
                .setPayerLastName("Hendrix")
                .addDepositItem(payeeId,
                    "100.00")
                .addDepositItem(payeeId1,
                    "15.00")
                .addDepositItem(payeeId2,
                    "20.00")
                .addDepositItem(payeeId3,
                    "25.00")
                .setCreditCardType("Visa")
                .setCreditCardNumber("4444444444444448")
                .setCreditExpMonth("5")
                .setCreditExpYear("25")
                .setCreditCardCvv2("369")
                .setBillingFirstName("Bryon")
                .setBillingLastName("Hendrix")
                .setBillingStreetAddress("369 Atbash Court")
                .setBillingCity("Fallbrook")
                .setBillingState("CA")
                .setBillingCountry("US")
                .setBillingZip("92122")
                .setTotalAmount("160.00")
                .setFeeAmount("")
                .setMessage("Test Message")
                .setIncurFee("Yes")
                .setSaveAccount("Yes")
                .setCurrencyCode("USD")
        );

    //CCPayment,8,7,GAPITesterCC,NULL,12035,Kaylee,Lanier,MasterCard,5454545454545454,5,25,369,Kaylee,Lanier,369 Atbash Court,Fallbrook,ON,CA,M3C0C1,2750.36,45.00,No,No,NULL,CAPTURE,LARGE,2
    testCases
        .add(
            new CcPayment(
                dataHelper.getReferenceId(),
                "Test Case 3 -  Fee amount set, Incur fee NO",
                getExpectedResponse(gatewayErrors, "8"),
                payeeId)
                .setPayerReferenceId("GAPITesterCC")
                .setPayerSecondaryRefId("qagapisecondrefid")
                .setPayerFirstName("Bryon")
                .setPayerLastName("Hendrix")
                .addDepositItem(payeeId,
                    "100.00")
                .addDepositItem(payeeId1,
                    "15.00")
                .addDepositItem(payeeId2,
                    "20.00")
                .addDepositItem(payeeId3,
                    "25.00")
                .setCreditCardType("Visa")
                .setCreditCardNumber("4444444444444448")
                .setCreditExpMonth("5")
                .setCreditExpYear("25")
                .setCreditCardCvv2("369")
                .setBillingFirstName("Bryon")
                .setBillingLastName("Hendrix")
                .setBillingStreetAddress("369 Atbash Court")
                .setBillingCity("Fallbrook")
                .setBillingState("CA")
                .setBillingCountry("US")
                .setBillingZip("92122")
                .setTotalAmount("180.00")
                .setFeeAmount("20.00")
                .setMessage("Test Message")
                .setIncurFee("No")
                .setSaveAccount("Yes")
                .setCurrencyCode("USD")
        );

    //CCPayment,8,8,GAPITesterCC,NULL,12035,Brian,Baker,MasterCard,5454545454545454,5,25,369,Baker,Brian,369 Atbash Cour,Mooselookmeguntic,ON,CA,M3C0C1,325.25,45.00,Yes,No,NULL,NULL,NULL,
    testCases
        .add(
            new CcPayment(
                dataHelper.getReferenceId(),
                "Test Case 4 - Fee amount set and Incur fee yes",
                getExpectedResponse(gatewayErrors, "8"),
                payeeId)
                .setPayerReferenceId("GAPITesterCC")
                .setPayerSecondaryRefId("qagapisecondrefid")
                .setPayerFirstName("Bryon")
                .setPayerLastName("Hendrix")
                .addDepositItem(payeeId,
                    "100.00")
                .addDepositItem(payeeId1,
                    "15.00")
                .addDepositItem(payeeId2,
                    "20.00")
                .addDepositItem(payeeId3,
                    "25.00")
                .setCreditCardType("Visa")
                .setCreditCardNumber("4444444444444448")
                .setCreditExpMonth("5")
                .setCreditExpYear("25")
                .setCreditCardCvv2("369")
                .setBillingFirstName("Bryon")
                .setBillingLastName("Hendrix")
                .setBillingStreetAddress("369 Atbash Court")
                .setBillingCity("Fallbrook")
                .setBillingState("CA")
                .setBillingCountry("US")
                .setBillingZip("92122")
                .setTotalAmount("160.00")
                .setFeeAmount("")
                .setMessage("Test Message")
                .setIncurFee("yes")
                .setSaveAccount("Yes")
                .setCurrencyCode("USD")
        );

    //CCPayment,8,9,GAPITesterCC,NULL,12035,Eugene,Carter,MasterCard,5454545454545454,5,25,369,Eugene,Carter,369 Atbash Court,Fallbrook,ON,CA,M3C0C1,3200.00,100.00,,No,NULL,NULL,NULL,
    testCases
        .add(
            new CcPayment(
                dataHelper.getReferenceId(),
                "Test Case 5 - Fee amount set ,Incur fee empty",
                getExpectedResponse(gatewayErrors, "8"),
                payeeId)
                .setPayerReferenceId("GAPITesterCC")
                .setPayerSecondaryRefId("qagapisecondrefid")
                .setPayerFirstName("Bryon")
                .setPayerLastName("Hendrix")
                .addDepositItem(payeeId,
                    "100.00")
                .addDepositItem(payeeId1,
                    "15.00")
                .addDepositItem(payeeId2,
                    "20.00")
                .addDepositItem(payeeId3,
                    "25.00")
                .setCreditCardType("Visa")
                .setCreditCardNumber("4444444444444448")
                .setCreditExpMonth("5")
                .setCreditExpYear("25")
                .setCreditCardCvv2("369")
                .setBillingFirstName("Bryon")
                .setBillingLastName("Hendrix")
                .setBillingStreetAddress("369 Atbash Court")
                .setBillingCity("Fallbrook")
                .setBillingState("CA")
                .setBillingCountry("US")
                .setBillingZip("92122")
                .setTotalAmount("180.00")
                .setFeeAmount("20.00")
                .setMessage("Test Message")
                .setIncurFee("")
                .setSaveAccount("Yes")
                .setCurrencyCode("USD")
        );

    //CCPayment,8,10,GAPITesterCC,NULL,12035,Bryon,Hendrix,MasterCard,5454545454545454,5,25,369,Bryon,Hendrix,369 Atbash Court,Fallbrook,ON,CA,M3C0C1,3200.00,NULL,,No,NULL,NULL,NULL,
    testCases
        .add(
            new CcPayment(
                dataHelper.getReferenceId(),
                "Test Case 6 - Incur fee empty and Fee amount empty",
                getExpectedResponse(gatewayErrors, "8"),
                payeeId)
                .setPayerReferenceId("GAPITesterCC")
                .setPayerSecondaryRefId("qagapisecondrefid")
                .setPayerFirstName("Bryon")
                .setPayerLastName("Hendrix")
                .addDepositItem(payeeId,
                    "100.00")
                .addDepositItem(payeeId1,
                    "15.00")
                .addDepositItem(payeeId2,
                    "20.00")
                .addDepositItem(payeeId3,
                    "25.00")
                .setCreditCardType("Visa")
                .setCreditCardNumber("4444444444444448")
                .setCreditExpMonth("5")
                .setCreditExpYear("25")
                .setCreditCardCvv2("369")
                .setBillingFirstName("Bryon")
                .setBillingLastName("Hendrix")
                .setBillingStreetAddress("369 Atbash Court")
                .setBillingCity("Fallbrook")
                .setBillingState("CA")
                .setBillingCountry("US")
                .setBillingZip("92122")
                .setTotalAmount("160.00")
                .setFeeAmount("")
                .setMessage("Test Message")
                .setIncurFee("")
                .setSaveAccount("Yes")
                .setCurrencyCode("USD")
        );
    executeTests(testCases);
  }

  @Test(groups = {"gapi", "CCPayment", "paysafe"})
  public void ccSplitPaymentDifferentPropertyCad() throws Exception {
    Logger.info("CC Split Payment Test for different properties - Paysafe");

    testCcSplitPaymentCad("tc3610");
  }

  @Test(groups = {"gapi", "CCPayment", "paysafe"})
  public void ccSplitPaymentSamePropertyCad() throws Exception {
    Logger.info("CC Split Payment Test for same properties - Paysafe");

    testCcSplitPaymentCad("tc3611");
  }

  private void testCcSplitPaymentCad(String testCaseName)
      throws Exception {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCaseName);
    testSetupPage.open();

    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String payeeId1 = testSetupPage.getString("payeeId1");
    final String payeeId2 = testSetupPage.getString("payeeId2");
    final String bankAccountId1 = testSetupPage.getString("bankAccountId1");
    final String bankAccountId2 = testSetupPage.getString("bankAccountId2");
    final String amount1 = "0.01";
    final String amount2 = "0.02";
    final String totalAmount = "0.03";

    DataHelper dataHelper = new DataHelper();
    GapiRequest gapiRequest = new GapiRequest(new Credentials(gatewayId, username, password));

    CcPayment testCase = CcPayment.createValid(
        dataHelper.getReferenceId(),
        "Test Case - CC Split Payment Test - Paysafe",
        null,
        payeeId1)
        .addDepositItem(payeeId1, amount1)
        .addDepositItem(payeeId2, amount2)
        .setIncurFee("Yes")
        .setFeeAmount("")
        .setCurrencyCode("CAD")
        .setTotalAmount(totalAmount);

    testCase.addTransaction(gapiRequest);

    GapiResponse response = gapiRequest.sendRequest();
    response.setIndex("1");

    String responseCode = response.getSpecificElementValue("Code");
    Assert.assertEquals(responseCode, "8", "Transaction should be processed successfully");

    String transId = response.getSpecificElementValue("TransactionId");

    TransactionPage transactionPage = new TransactionPage();
    transactionPage.open();
    TransactionProcessingPage transactionProcessingPage = transactionPage
        .getDataForTransactionId(transId);

    Assert.assertTrue(transactionProcessingPage.isPayoutPresent(bankAccountId1, amount1),
        "BankAccount1 payout should be present");
    Assert.assertTrue(transactionProcessingPage.isPayoutPresent(bankAccountId2, amount2),
        "BankAccount2 payout should be present");
  }
}
