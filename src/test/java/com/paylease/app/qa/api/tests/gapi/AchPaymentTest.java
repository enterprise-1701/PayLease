package com.paylease.app.qa.api.tests.gapi;

import com.paylease.app.qa.api.tests.TestCaseCollection;
import com.paylease.app.qa.api.tests.gapi.testcase.AchPayment;
import com.paylease.app.qa.api.tests.gapi.testcase.ErrorTestCase;
import com.paylease.app.qa.framework.DataHelper;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.api.Credentials;
import com.paylease.app.qa.framework.api.Response;
import com.paylease.app.qa.framework.api.gapi.GapiRequest;
import com.paylease.app.qa.framework.api.gapi.GapiResponse;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import java.util.HashMap;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AchPaymentTest extends BaseTest {

  private static final String REGION = "gapi";
  private static final String FEATURE = "AchPayment";

  //-----------------------------------TEST----------------------------------------------


  @Test(groups = {"gapi", "ACHPayment"})
  public void userRecordWithPrimaryAccountOnly() throws Exception {
    userRecordWithNoGatewayPayerRecord("tc3");
  }

  @Test(groups = {"gapi", "ACHPayment"})
  public void userRecordWithPrimaryAndSecondaryAccount() throws Exception {
    userRecordWithNoGatewayPayerRecord("tc4");
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
    final String accountType = testSetupPage.getString("accountType");
    final String routingNumber = testSetupPage.getString("routingNumber");
    final String accountNumber = testSetupPage.getString("accountNumber");
    final String totalAmount = testSetupPage.getString("totalAmount");
    final String feeAmount = testSetupPage.getString("feeAmount");
    final String incurFee = testSetupPage.getString("incurFee");
    final String saveAccount = testSetupPage.getString("saveAccount");

    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    Credentials credentials = new Credentials(gatewayId, username, password);
    GapiRequest gapiRequest = new GapiRequest(credentials);
    DataHelper dataHelper = new DataHelper();

    AchPayment achPayment = new AchPayment(
        dataHelper.getReferenceId(),
        "Test Case - Resident Record With no Gateway Payer Record",
        getExpectedResponse(gatewayErrors, "2"),
        payeeId);

    achPayment
        .setPayerReferenceId(payerRefId)
        .setPayerFirstName(firstName)
        .setPayerLastName(lastName)
        .setAccountType(accountType.equals("1") ? "Checking" : "Savings")
        .setAccountFullName(firstName.concat(" ").concat(lastName))
        .setRoutingNumber(routingNumber)
        .setAccountNumber(accountNumber)
        .setTotalAmount(totalAmount)
        .setFeeAmount(feeAmount)
        .setIncurFee(incurFee)
        .setSaveAccount(saveAccount);

    if (!payerSecondaryRefId.isEmpty()) {
      achPayment.setPayerSecondaryReferenceId(payerSecondaryRefId);
    }

    achPayment.addTransaction(gapiRequest);

    Response response = gapiRequest.sendRequest();
    response.setIndex("1");
    String gatewayPayerId = response.getSpecificElementValue("GatewayPayerId");

    String userId = validateGapiResponse(gatewayPayerId);
    Assert.assertEquals(userId, createdResidentUserId,
        "Gateway Payer Record was not created using existing resident");
  }

  @Test(groups = {"gapi", "ACHPayment"})
  public void basicRequestValidation() {

    Logger.info("ACHPayment Basic GapiRequest Validation");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String payeeId = testSetupPage.getString("payeeId");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    final String[] incurFeeValues = {
        "No", "Yes",
    };

    DataHelper dataHelper = new DataHelper();
    TestCaseCollection testCases = new GapiTestCaseCollection(
        new Credentials(gatewayId, username, password)
    );

    //ACHPayment,2,2,GAPITesterACH1,NULL,12035,Foxy,Shuttle,Savings,Foxy Shuttle,490000018,9857881896,1752.36,2.95,No,No,NULL,NULL
    testCases.add(
        new AchPayment(
            dataHelper.getReferenceId(),
            "Test Case 2 - Basic Payment",
            getExpectedResponse(gatewayErrors, "2"),
            payeeId)
            .setPayerReferenceId("GAPITesterACH1")
            .setPayerFirstName("Foxy")
            .setPayerLastName("Shuttle")
            .setAccountType("Savings")
            .setAccountFullName("Foxy Shuttle")
            .setRoutingNumber("490000018")
            .setAccountNumber("9857881896")
            .setTotalAmount("1752.36")
            .setFeeAmount("2.95")
            .setIncurFee("No")
            .setSaveAccount("No")
    );

    //ACHPayment,2,3,GAPITesterACH,NULL,12035,Bryon,Hendrix,Savings,Bryon Hendrix,490000018,9857881896,342.35,2.95,No,No,NULL,NULL,
    testCases.add(
        new AchPayment(
            dataHelper.getReferenceId(),
            "Test Case 3 - Basic Payment2",
            getExpectedResponse(gatewayErrors, "2"),
            payeeId)
            .setPayerReferenceId("GAPITesterACH")
            .setPayerFirstName("Bryon")
            .setPayerLastName("Hendrix")
            .setAccountType("Savings")
            .setAccountFullName("Bryon Hendrix")
            .setRoutingNumber("490000018")
            .setAccountNumber("9857881896")
            .setTotalAmount("342.35")
            .setFeeAmount("2.95")
            .setSaveAccount("No")
            .setIncurFee("No")
    );

    //ACHPayment,2,4,GAPITesterACH,NULL,12035,K!a1@t#h$r4%y^n&,B4*r(o4)wn,Checking,Kathryn Brown,490000018,6643409289,1385.36,2.95,No,No,QA GAPI TEST SUITE,NULL,
    testCases.add(
        new AchPayment(
            dataHelper.getReferenceId(),
            "Test Case 4 - Basic Payment with weird names",
            getExpectedResponse(gatewayErrors, "2"),
            payeeId)
            .setPayerReferenceId("GAPITesterACH")
            .setPayerFirstName("K!a1@t#h$r4%y^n&")
            .setPayerLastName("B4*r(o4)wn")
            .setAccountType("Checking")
            .setAccountFullName("Kathryn Brown")
            .setRoutingNumber("490000018")
            .setAccountNumber("6643409289")
            .setTotalAmount("1385.36")
            .setFeeAmount("2.95")
            .setIncurFee("No")
            .setSaveAccount("No")
            .setMessage("QA GAPI TEST SUITE")
    );

    //ACHPayment,2,5,GAPITesterACH,NULL,12035,Kaylee,Lanier,Savings,K!a@y#l$e%e^ L&a*n(i)e_r+,490000018,8584537564,1748.36,,No,No,NULL,NULL,
    testCases.add(
        new AchPayment(
            dataHelper.getReferenceId(),
            "Test Case 5 - Basic Payment with weird account name",
            getExpectedResponse(gatewayErrors, "2"),
            payeeId)
            .setPayerReferenceId("GAPITesterACH")
            .setPayerFirstName("Kaylee")
            .setPayerLastName("Lanier")
            .setAccountType("Savings")
            .setAccountFullName("K!a@y#l$e%e^ L&a*n(i)e_r+")
            .setRoutingNumber("490000018")
            .setAccountNumber("8584537564")
            .setTotalAmount("1748.36")
            .setFeeAmount("")
            .setIncurFee("No")
            .setSaveAccount("No")
    );

    //ACHPayment,2,6,GAPITesterACH,NULL,12035,JeffersonBarwickBarrickNorthst,AlistaireBenjaminLafayetteTrya,Checking,JeffersonBarwickBarrickNorthstAlistaireBenjamin LafayetteTryaJeffersonBarwickBarrickNorthstAlistaire,490000018,0387466332,2000.11,0,No,No,QA GAPI TEST SUITE,NULL,
    testCases.add(
        new AchPayment(
            dataHelper.getReferenceId(),
            "Test Case 6 - Basic Payment with really long names",
            getExpectedResponse(gatewayErrors, "2"), payeeId)
            .setPayerReferenceId("GAPITesterACH")
            .setPayerFirstName("JeffersonBarwickBarrickNorthst")
            .setPayerLastName("AlistaireBenjaminLafayetteTrya")
            .setAccountType("Checking")
            .setAccountFullName(
                "JeffersonBarwickBarrickNorthstAlistaireBenjamin LafayetteTryaJeffersonBarwickBarrickNorthstAlistaire")
            .setRoutingNumber("490000018")
            .setAccountNumber("0387466332")
            .setTotalAmount("2000.11")
            .setFeeAmount("0")
            .setIncurFee("No")
            .setSaveAccount("No")
            .setMessage("QA GAPI TEST SUITE")
    );

    String[] invalidPayeeIds = {
        "120!35", "120 35", "120-35", "120.35",
    };

    for (String invalidPayeeId : invalidPayeeIds) {
      //ACHPayment,130,43,GAPITesterACH,NULL,120!35,Eugene,Carter,Checking,Eugene Carter,490000018,614800811,200.01,0,No,No,QA GAPI TEST SUITE,NULL,
      //ACHPayment,130,44,GAPITesterACH,NULL,120 35,Eugene,Carter,Checking,Eugene Carter,490000018,614800811,200.01,0,No,No,QA GAPI TEST SUITE,NULL,
      //ACHPayment,130,45,GAPITesterACH,NULL,120-35,Eugene,Carter,Checking,Eugene Carter,490000018,614800811,200.01,0,Yes,No,QA GAPI TEST SUITE,NULL,
      //ACHPayment,130,46,GAPITesterACH,NULL,120.35,Eugene,Carter,Checking,Eugene Carter,490000018,614800811,200.01,0,Yes,No,QA GAPI TEST SUITE,NULL,
      testCases.add(
          AchPayment.createValidAchPayment(
              dataHelper.getReferenceId(),
              "Test Case 43-46 - Invalid PayeeId",
              getExpectedResponse(gatewayErrors, "130"),
              invalidPayeeId)
      );
    }

    //ACHPayment,149,75,GAPITesterACH,NULL,120,Susan,Bell,Checking,Susan Bell,490000018,614800811,200.11,0,Yes,No,QA GAPI TEST SUITE,NULL,
    testCases.add(
        AchPayment.createValidAchPayment(
            dataHelper.getReferenceId(),
            "Test Case 75 - Incorrect Payee Id",
            getExpectedResponse(gatewayErrors, "149"),
            "120")
    );

    for (String incurFee : incurFeeValues) {
      //ACHPayment,69,7,,NULL,12035,Tammy,Murphy,Savings,Tammy Murphy,490000018,6asdwq14800811,200.1,0,No,No,QA GAPI TEST SUITE,NULL,
      //ACHPayment,69,8,,NULL,12035,Tammy,Murphy,Savings,Tammy Murphy,490000018,6asdwq14800811,200.1,0,Yes,No,QA GAPI TEST SUITE,NULL,
      testCases.add(
          AchPayment.createValidAchPayment(
              dataHelper.getReferenceId(),
              "Test Case 7/8 - Empty Payer Reference Id - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "69"),
              payeeId)
              .setPayerReferenceId("")
              .setIncurFee(incurFee)
      );

      //ACHPayment,70,9,GAPITesterACH,NULL,,Susan,Bell,Checking,Susan Bell,490000018,614800811,200.11,0,No,No,QA GAPI TEST SUITE,NULL,
      //ACHPayment,70,10,GAPITesterACH,NULL,,Susan,Bell,Checking,Susan Bell,490000018,614800811,200.11,0,Yes,No,QA GAPI TEST SUITE,NULL,
      testCases.add(
          AchPayment.createValidAchPayment(
              dataHelper.getReferenceId(),
              "Test Case 9/10 - Empty Payee Id - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "70"),
              "")
              .setIncurFee(incurFee)
      );

      //ACHPayment,71,11,GAPITesterACH,NULL,12035,,Baker,Checking,Brian Baker,490000018,614800811,20.1,0,No,No,QA GAPI TEST SUITE,NULL,
      //ACHPayment,71,12,GAPITesterACH,NULL,12035,,Baker,Checking,Brian Baker,490000018,614800811,20.1,0,Yes,No,QA GAPI TEST SUITE,NULL,
      testCases.add(
          AchPayment.createValidAchPayment(
              dataHelper.getReferenceId(),
              "Test Case 11/12 - Empty First Name - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "71"),
              payeeId)
              .setPayerFirstName("")
              .setIncurFee(incurFee)
      );

      //ACHPayment,72,13,GAPITesterACH,NULL,12035,Eugene,,Checking,Eugene Carter,490000018,614800811,200.01,0,No,No,QA GAPI TEST SUITE,NULL,
      //ACHPayment,72,14,GAPITesterACH,NULL,12035,Eugene,,Checking,Eugene Carter,490000018,614800811,200.01,0,Yes,No,QA GAPI TEST SUITE,NULL,
      testCases.add(
          AchPayment.createValidAchPayment(
              dataHelper.getReferenceId(),
              "Test Case 13/14 - Empty Last Name - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "72"),
              payeeId)
              .setPayerLastName("")
              .setIncurFee(incurFee)
      );

      //ACHPayment,85,15,GAPITesterACH,NULL,12035,Jose,Edwards,Checking,Jose Edwards,490000018,614800811,,0,No,No,QA GAPI TEST SUITE,NULL,
      //ACHPayment,85,16,GAPITesterACH,NULL,12035,Jose,Edwards,Checking,Jose Edwards,490000018,614800811,,0,Yes,No,QA GAPI TEST SUITE,NULL,
      testCases.add(
          AchPayment.createValidAchPayment(
              dataHelper.getReferenceId(),
              "Test Case 15/16 - Empty Total Amount - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "85"),
              payeeId)
              .setTotalAmount("")
              .setIncurFee(incurFee)
      );

      //ACHPayment,85,17,GAPITesterACH,NULL,12035,Jonathan,Martin,Savings,Jonathan Martin,490000018,614800811,0,0,No,No,QA GAPI TEST SUITE,NULL,
      //ACHPayment,85,18,GAPITesterACH,NULL,12035,Jonathan,Martin,Savings,Jonathan Martin,490000018,614800811,0,0,Yes,No,QA GAPI TEST SUITE,NULL,
      testCases.add(
          AchPayment.createValidAchPayment(
              dataHelper.getReferenceId(),
              "Test Case 17/18 - Total Amount 0 - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "85"),
              payeeId)
              .setTotalAmount("0")
              .setIncurFee(incurFee)
      );

      //ACHPayment,86,19,GAPITesterACH,NULL,12035,Albert,Bell,Checking,Albert Bell,490000018,614800811,2000.11,0,No,0,QA GAPI TEST SUITE,NULL,
      //ACHPayment,86,20,GAPITesterACH,NULL,12035,Albert,Bell,Checking,Albert Bell,490000018,614800811,2000.11,0,Yes,0,QA GAPI TEST SUITE,NULL,
      testCases.add(
          AchPayment.createValidAchPayment(
              dataHelper.getReferenceId(),
              "Test Case 19/20 - Invalid SaveAccount value - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "86"),
              payeeId)
              .setSaveAccount("0")
              .setIncurFee(incurFee)
      );

      //ACHPayment,87,21,GAPITesterACH,NULL,12035,Jonathan,Martin,,Jonathan Martin,490000018,614800811,,0,No,No,QA GAPI TEST SUITE,NULL,
      //ACHPayment,87,22,GAPITesterACH,NULL,12035,Jonathan,Martin,,Jonathan Martin,490000018,614800811,,0,Yes,No,QA GAPI TEST SUITE,NULL,
      testCases.add(
          AchPayment.createValidAchPayment(
              dataHelper.getReferenceId(),
              "Test Case 21/22 - Empty AccountType, Total Amount - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "87"),
              payeeId)
              .setAccountType("")
              .setTotalAmount("")
              .setIncurFee(incurFee)
      );

      //ACHPayment,88,23,GAPITesterACH,NULL,12035,Tammy,Murphy,Savings,,490000018,614800811,200.1,0,No,No,QA GAPI TEST SUITE,NULL,
      //ACHPayment,88,24,GAPITesterACH,NULL,12035,Tammy,Murphy,Savings,,490000018,614800811,200.1,0,Yes,No,QA GAPI TEST SUITE,NULL,
      testCases.add(
          AchPayment.createValidAchPayment(
              dataHelper.getReferenceId(),
              "Test Case 23/24 - Empty Account Name - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "88"),
              payeeId)
              .setAccountFullName("")
              .setIncurFee(incurFee)
      );

      //ACHPayment,89,25,GAPITesterACH,NULL,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,,61480081,20,11.11,No,No,QA GAPI TEST SUITE,NULL,
      //ACHPayment,89,26,GAPITesterACH,NULL,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,,61480081,20,11.11,Yes,No,QA GAPI TEST SUITE,NULL,
      testCases.add(
          AchPayment.createValidAchPayment(
              dataHelper.getReferenceId(),
              "Test Case 25/26 - Empty Routing Number - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "89"),
              payeeId)
              .setRoutingNumber("")
              .setIncurFee(incurFee)
      );

      //ACHPayment,90,27,GAPITesterACH,NULL,12035,Jonathan,Martin,Savings,Jonathan Martin,490000018,,2000,0,No,No,QA GAPI TEST SUITE,NULL,
      //ACHPayment,90,28,GAPITesterACH,NULL,12035,Jonathan,Martin,Savings,Jonathan Martin,490000018,,2000,0,Yes,No,QA GAPI TEST SUITE,NULL,
      testCases.add(
          AchPayment.createValidAchPayment(
              dataHelper.getReferenceId(),
              "Test Case 27/28 - Empty Account Number - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "90"),
              payeeId)
              .setAccountNumber("")
              .setIncurFee(incurFee)
      );

      //ACHPayment,103,29,GAPITesterACH,NULL,12035,Eugene,Carter,checking,Eugene Carter,490000018,614800811,200.01,0,No,No,QA GAPI TEST SUITE,NULL,
      //ACHPayment,103,30,GAPITesterACH,NULL,12035,Eugene,Carter,checking,Eugene Carter,490000018,614800811,200.01,0,Yes,No,QA GAPI TEST SUITE,NULL,
      testCases.add(
          AchPayment.createValidAchPayment(
              dataHelper.getReferenceId(),
              "Test Case 29/30 - Invalid AccountType - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "103"),
              payeeId)
              .setAccountType("checking")
              .setIncurFee(incurFee)
      );

      //ACHPayment,104,31,GAPITesterACH,NULL,12035,Albert,Bell,Checking,Albert Bell,490000018,614800811,2000.11,0,No,N,QA GAPI TEST SUITE,NULL,
      //ACHPayment,104,32,GAPITesterACH,NULL,12035,Albert,Bell,Checking,Albert Bell,490000018,614800811,2000.11,0,Yes,N,QA GAPI TEST SUITE,NULL,
      testCases.add(
          AchPayment.createValidAchPayment(
              dataHelper.getReferenceId(),
              "Test Case 31/32 - Invalid SaveAccount - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "104"),
              payeeId)
              .setSaveAccount("N")
              .setIncurFee(incurFee)
      );

      //ACHPayment,111,33,a134oihqf9w7h2ufhqiusd872q45y92715yr7q8wgfasyfasf,NULL,12035,Kenneth,Evans,Savings,Kenneth Evans,490000018,614800811,20.01,11.11,No,No,QA GAPI TEST SUITE,NULL,
      //ACHPayment,111,34,a134oihqf9w7h2ufhqiusd872q45y92715yr7q8wgfasyfasf,NULL,12035,Kenneth,Evans,Savings,Kenneth Evans,490000018,614800811,20.01,11.11,Yes,No,QA GAPI TEST SUITE,NULL,
      testCases.add(
          AchPayment.createValidAchPayment(
              dataHelper.getReferenceId(),
              "Test Case 33/34 - Payer Reference Id too long - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "111"),
              payeeId)
              .setPayerReferenceId("a134oihqf9w7h2ufhqiusd872q45y92715yr7q8wgfasyfasf")
              .setIncurFee(incurFee)
      );

      //ACHPayment,112,35,GAPITesterACH,NULL,12035,JeffersonBarwickBarrickNorthsta,AlistaireBenjaminLafayetteTrya,Checking,JeffersonBarwickBarrickNorthst AlistaireBenjaminLafayetteTrya,490000018,614800811,2000.11,0,No,No,QA GAPI TEST SUITE,NULL,
      //ACHPayment,112,36,GAPITesterACH,NULL,12035,JeffersonBarwickBarrickNorthsta,AlistaireBenjaminLafayetteTrya,Checking,JeffersonBarwickBarrickNorthst AlistaireBenjaminLafayetteTrya,490000018,614800811,2000.11,0,Yes,No,QA GAPI TEST SUITE,NULL,
      testCases.add(
          AchPayment.createValidAchPayment(
              dataHelper.getReferenceId(),
              "Test Case 35/36 - PayerFirstName too long - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "112"),
              payeeId)
              .setPayerFirstName("JeffersonBarwickBarrickNorthsta")
              .setIncurFee(incurFee)
      );

      //ACHPayment,113,37,GAPITesterACH,NULL,12035,JeffersonBarwickBarrickNorthst,AlistaireBenjaminLafayetteTryar,Checking,JeffersonBarwickBarrickNorthst AlistaireBenjaminLafayetteTrya,490000018,614800811,2000.11,0,No,No,QA GAPI TEST SUITE,NULL,
      //ACHPayment,113,38,GAPITesterACH,NULL,12035,JeffersonBarwickBarrickNorthst,AlistaireBenjaminLafayetteTryar,Checking,JeffersonBarwickBarrickNorthst AlistaireBenjaminLafayetteTrya,490000018,614800811,2000.11,0,Yes,No,QA GAPI TEST SUITE,NULL,
      testCases.add(
          AchPayment.createValidAchPayment(
              dataHelper.getReferenceId(),
              "Test Case 37/38 - PayerLastName too long - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "113"),
              payeeId)
              .setPayerLastName("AlistaireBenjaminLafayetteTryar")
              .setIncurFee(incurFee)
      );

      //ACHPayment,114,39,GAPITesterACH,NULL,12035,JeffersonBarwickBarrickNorthst,AlistaireBenjaminLafayetteTrya,Checking,JeffersonBarwickBarrickNorthstAlistaireBenjamina LafayetteTryaJeffersonBarwickBarrickNorthstAlistaire,490000018,614800811,2000.11,0,No,No,QA GAPI TEST SUITE,NULL,
      //ACHPayment,114,40,GAPITesterACH,NULL,12035,JeffersonBarwickBarrickNorthst,AlistaireBenjaminLafayetteTrya,Checking,JeffersonBarwickBarrickNorthstAlistaireBenjamina LafayetteTryaJeffersonBarwickBarrickNorthstAlistaire,490000018,614800811,2000.11,0,Yes,No,QA GAPI TEST SUITE,NULL,
      testCases.add(
          AchPayment.createValidAchPayment(
              dataHelper.getReferenceId(),
              "Test Case 39/40 - AccountFullName too long - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "114"),
              payeeId)
              .setAccountFullName(
                  "JeffersonBarwickBarrickNorthstAlistaireBenjamina LafayetteTryaJeffersonBarwickBarrickNorthstAlistaire")
              .setIncurFee(incurFee)
      );

      //ACHPayment,115,41,GAPITesterACH,NULL,12035,Jonathan,Martin,Savings,Jonathan Martin,4900000181,614800811,2000,0,No,No,QA GAPI TEST SUITE,NULL,
      //ACHPayment,115,42,GAPITesterACH,NULL,12035,Jonathan,Martin,Savings,Jonathan Martin,4900000181,614800811,2000,0,Yes,No,QA GAPI TEST SUITE,NULL,
      testCases.add(
          AchPayment.createValidAchPayment(
              dataHelper.getReferenceId(),
              "Test Case 41/42 - RoutingNumber too long - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "115"),
              payeeId)
              .setRoutingNumber("4900000181")
              .setIncurFee(incurFee)
      );

      //ACHPayment,131,47,GAPITesterACH,NULL,12035,Mary,Powell,Savings,Mary Powell,49000001a,614800811,200,0,No,No,QA GAPI TEST SUITE,NULL,
      //ACHPayment,131,49,GAPITesterACH,NULL,12035,Mary,Powell,Savings,Mary Powell,49000001a,614800811,200,0,Yes,No,QA GAPI TEST SUITE,NULL,
      testCases.add(
          AchPayment.createValidAchPayment(
              dataHelper.getReferenceId(),
              "Test Case 47/49 - Invalid RoutingNumber1 - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "131"),
              payeeId)
              .setRoutingNumber("49000001a")
              .setIncurFee(incurFee)
      );

      //ACHPayment,131,48,GAPITesterACH,NULL,12035,Mary,Powell,Savings,Mary Powell,4900 0001,614800811,200,0,No,No,QA GAPI TEST SUITE,NULL,
      //ACHPayment,131,50,GAPITesterACH,NULL,12035,Mary,Powell,Savings,Mary Powell,4900 0001,614800811,200,0,Yes,No,QA GAPI TEST SUITE,NULL,
      testCases.add(
          AchPayment.createValidAchPayment(
              dataHelper.getReferenceId(),
              "Test Case 48/50 - Invalid RoutingNumber2 - IncurFee" + incurFee,
              getExpectedResponse(gatewayErrors, "131"),
              payeeId)
              .setRoutingNumber("4900 0001")
              .setIncurFee(incurFee)
      );

      //ACHPayment,132,51,GAPITesterACH,NULL,12035,Jose,Edwards,Checking,Jose Edwards,490000018,614800811dfwq,2000.01,0,No,No,QA GAPI TEST SUITE,NULL,
      //ACHPayment,132,53,GAPITesterACH,NULL,12035,Jose,Edwards,Checking,Jose Edwards,490000018,614800811dfwq,2000.01,0,Yes,No,QA GAPI TEST SUITE,NULL,
      testCases.add(
          AchPayment.createValidAchPayment(
              dataHelper.getReferenceId(),
              "Test Case 51/53 - Invalid AccountNumber1 - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "132"),
              payeeId)
              .setAccountNumber("614800811dfwq")
              .setIncurFee(incurFee)
      );

      //ACHPayment,132,52,GAPITesterACH,NULL,12035,Jose,Edwards,Checking,Jose Edwards,490000018,6148 00811,2000.01,0,No,No,QA GAPI TEST SUITE,NULL,
      //ACHPayment,132,54,GAPITesterACH,NULL,12035,Jose,Edwards,Checking,Jose Edwards,490000018,6148 00811,2000.01,0,Yes,No,QA GAPI TEST SUITE,NULL,
      testCases.add(
          AchPayment.createValidAchPayment(
              dataHelper.getReferenceId(),
              "Test Case 52/54 - Invalid AccountNumber2 - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "132"),
              payeeId)
              .setAccountNumber("6148 00811")
              .setIncurFee(incurFee)
      );

      //ACHPayment,133,55,GAPITesterACH,NULL,12035,Jose,Edwards,Checking,Jose Edwards,490000018,614800811,2000.003,0,No,No,QA GAPI TEST SUITE,NULL,
      //ACHPayment,133,64,GAPITesterACH,NULL,12035,Jose,Edwards,Checking,Jose Edwards,490000018,614800811,2000.003,0,Yes,No,QA GAPI TEST SUITE,NULL,
      testCases.add(
          AchPayment.createValidAchPayment(
              dataHelper.getReferenceId(),
              "Test Case 55/64 - Invalid TotalAmount1 - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "133"),
              payeeId)
              .setTotalAmount("2000.003")
              .setIncurFee(incurFee)
      );

      //ACHPayment,133,56,GAPITesterACH,NULL,12035,Jose,Edwards,Checking,Jose Edwards,490000018,614800811,2000 03,0,No,No,QA GAPI TEST SUITE,NULL,
      //ACHPayment,133,65,GAPITesterACH,NULL,12035,Jose,Edwards,Checking,Jose Edwards,490000018,614800811,2000 03,0,Yes,No,QA GAPI TEST SUITE,NULL,
      testCases.add(
          AchPayment.createValidAchPayment(
              dataHelper.getReferenceId(),
              "Test Case 56/65 - Invalid TotalAmount2 - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "133"),
              payeeId)
              .setTotalAmount("2000 03")
              .setIncurFee(incurFee)
      );

      //ACHPayment,133,57,GAPITesterACH,NULL,12035,Jose,Edwards,Checking,Jose Edwards,490000018,614800811,2000 3,0,No,No,QA GAPI TEST SUITE,NULL,
      //ACHPayment,133,66,GAPITesterACH,NULL,12035,Jose,Edwards,Checking,Jose Edwards,490000018,614800811,2000 3,0,Yes,No,QA GAPI TEST SUITE,NULL,
      testCases.add(
          AchPayment.createValidAchPayment(
              dataHelper.getReferenceId(),
              "Test Case 57/66 - Invalid TotalAmount3 - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "133"),
              payeeId)
              .setTotalAmount("2000 3")
              .setIncurFee(incurFee)
      );

      //ACHPayment,133,58,GAPITesterACH,NULL,12035,Jose,Edwards,Checking,Jose Edwards,490000018,614800811,2000. 3,0,No,No,QA GAPI TEST SUITE,NULL,
      //ACHPayment,133,67,GAPITesterACH,NULL,12035,Jose,Edwards,Checking,Jose Edwards,490000018,614800811,2000. 3,0,Yes,No,QA GAPI TEST SUITE,NULL,
      testCases.add(
          AchPayment.createValidAchPayment(
              dataHelper.getReferenceId(),
              "Test Case 58/67 - Invalid TotalAmount4 - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "133"),
              payeeId)
              .setTotalAmount("2000. 3")
              .setIncurFee(incurFee)
      );

      //ACHPayment,133,59,GAPITesterACH,NULL,12035,Jose,Edwards,Checking,Jose Edwards,490000018,614800811,2000. 03,0,No,No,QA GAPI TEST SUITE,NULL,
      //ACHPayment,133,68,GAPITesterACH,NULL,12035,Jose,Edwards,Checking,Jose Edwards,490000018,614800811,2000. 03,0,Yes,No,QA GAPI TEST SUITE,NULL,
      testCases.add(
          AchPayment.createValidAchPayment(
              dataHelper.getReferenceId(),
              "Test Case 59/68 - Invalid TotalAmount5 - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "133"),
              payeeId)
              .setTotalAmount("2000. 03")
              .setIncurFee(incurFee)
      );

      //ACHPayment,133,60,GAPITesterACH,NULL,12035,Jose,Edwards,Checking,Jose Edwards,490000018,614800811,2000 .3,0,No,No,QA GAPI TEST SUITE,NULL,
      //ACHPayment,133,69,GAPITesterACH,NULL,12035,Jose,Edwards,Checking,Jose Edwards,490000018,614800811,2000 .3,0,Yes,No,QA GAPI TEST SUITE,NULL,
      testCases.add(
          AchPayment.createValidAchPayment(
              dataHelper.getReferenceId(),
              "Test Case 60/69 - Invalid TotalAmount6 - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "133"),
              payeeId)
              .setTotalAmount("2000 .3")
              .setIncurFee(incurFee)
      );

      //ACHPayment,133,61,GAPITesterACH,NULL,12035,Jose,Edwards,Checking,Jose Edwards,490000018,614800811,2000 .03,0,No,No,QA GAPI TEST SUITE,NULL,
      //ACHPayment,133,70,GAPITesterACH,NULL,12035,Jose,Edwards,Checking,Jose Edwards,490000018,614800811,2000 .3,0,Yes,No,QA GAPI TEST SUITE,NULL,
      testCases.add(
          AchPayment.createValidAchPayment(
              dataHelper.getReferenceId(),
              "Test Case 61/70 - Invalid TotalAmount7 - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "133"),
              payeeId)
              .setTotalAmount("2000 .03")
              .setIncurFee(incurFee)
      );

      //ACHPayment,133,62,GAPITesterACH,NULL,12035,Jose,Edwards,Checking,Jose Edwards,490000018,614800811,2000.,0,No,No,QA GAPI TEST SUITE,NULL,
      //ACHPayment,133,71,GAPITesterACH,NULL,12035,Jose,Edwards,Checking,Jose Edwards,490000018,614800811,2000.,0,Yes,No,QA GAPI TEST SUITE,NULL,
      testCases.add(
          AchPayment.createValidAchPayment(
              dataHelper.getReferenceId(),
              "Test Case 62/71 - Invalid TotalAmount8 - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "133"),
              payeeId)
              .setTotalAmount("2000.")
              .setIncurFee(incurFee)
      );

      //ACHPayment,133,63,GAPITesterACH,NULL,12035,Jonathan,Martin,Savings,Jonathan Martin,490000018,614800811,1.0a,0,No,No,QA GAPI TEST SUITE,NULL,
      //ACHPayment,133,72,GAPITesterACH,NULL,12035,Jonathan,Martin,Savings,Jonathan Martin,490000018,614800811,1.0a,0,Yes,No,QA GAPI TEST SUITE,NULL,
      testCases.add(
          AchPayment.createValidAchPayment(
              dataHelper.getReferenceId(),
              "Test Case 63/72 - Invalid TotalAmount9 - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "133"),
              payeeId)
              .setTotalAmount("1.0a")
              .setIncurFee(incurFee)
      );

      //ACHPayment,133,73,GAPITesterACH,NULL,12035,Jonathan,Martin,Savings,Jonathan Martin,490000018,614800811,.0,0,No,No,QA GAPI TEST SUITE,NULL,
      //ACHPayment,133,74,GAPITesterACH,NULL,12035,Jonathan,Martin,Savings,Jonathan Martin,490000018,614800811,.0,0,No,No,QA GAPI TEST SUITE,NULL,
      testCases.add(
          AchPayment.createValidAchPayment(
              dataHelper.getReferenceId(),
              "Test Case 73/74 - Invalid TotalAmount10 - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "133"),
              payeeId)
              .setTotalAmount(".0")
              .setIncurFee(incurFee)
      );

      //ACHPayment,151,76,GAPITesterACH,NULL,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,49000001,61480081,20,11.11,No,No,QA GAPI TEST SUITE,NULL,
      //ACHPayment,151,77,GAPITesterACH,NULL,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,49000001,61480081,20,11.11,Yes,No,QA GAPI TEST SUITE,NULL,
      testCases.add(
          AchPayment.createValidAchPayment(
              dataHelper.getReferenceId(),
              "Test Case 76/77 - Invalid Routing Number - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "151"),
              payeeId)
              .setRoutingNumber("49000001")
              .setIncurFee(incurFee)
      );

      //ACHPayment,185,78,GAPITesterACH,NULL,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2,11.11,No,No,QA GAPI TEST SUITE,NULL,
      //ACHPayment,185,79,GAPITesterACH,NULL,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2,11.11,Yes,No,QA GAPI TEST SUITE,NULL,
      testCases.add(
          AchPayment.createValidAchPayment(
              dataHelper.getReferenceId(),
              "Test Case 78/79 - Fee greater than total - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "185"),
              payeeId)
              .setTotalAmount("2")
              .setFeeAmount("11.11")
              .setIncurFee(incurFee)
      );
    }

    executeTests(testCases);
  }


  @Test(groups = {"gapi", "ACHPayment"})
  public void largeBatchValidation() {
    Logger.info("ACHPayment Large Batch validation test");

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

      //ACHPayment,2,1,GAPITesterACH1,NULL,12035,LARGE_BATCH,2
      testCases.add(
          AchPayment.createValidAchPayment(
              dataHelper.getReferenceId(),
              "Test Case 1 - Large Batch",
              getExpectedResponse(gatewayErrors, "2"),
              payeeId)
              .setIncurFee("No")
      );
    }

    executeTests(testCases);
  }

  @Test(groups = {"gapi", "ACHPayment"})
  public void invalidLargeBatch()
      throws Exception {
    Logger.info("ACHPayment Invalid Large Batch test");

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
      AchPayment.createValidAchPayment(
          dataHelper.getReferenceId(),
          "Unused",
          getExpectedResponse(gatewayErrors, ""),
          payeeId).addTransaction(gapiRequest);
    }

    TestCaseCollection testCases = new GapiTestCaseCollection(credentials);

    //ACHPayment,261,80,GAPITesterACH1,NULL,12035,LARGE_BATCH,101
    testCases.add(
        new ErrorTestCase(
            "",
            getExpectedResponse(gatewayErrors, "261")
        )
    );

    GapiResponse response = gapiRequest.sendRequest();

    Assert.assertTrue(isCollectionValid(testCases, response), "Large batch failed");
  }


  @Test(groups = {"gapi", "ACHPayment", "Check21"})
  public void basicRequestValidationCheck21() {

    Logger.info("ACHPayment Basic GapiRequest Validation for Check21 requests");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc2");
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

    //Check21,2,1,GAPITesterACH,12035,Bryon,Hendrix,Savings,Bryon Hendrix,490000018,614800811,100.00,NULL,Yes,Yes,Yes,Yes,01/01/2020,AuxOnUs,RAND,RAND
    testCases.add(
        new AchPayment(
            dataHelper.getReferenceId(),
            "Test Case 1 - Basic Payment",
            getExpectedResponse(gatewayErrors, "2"),
            payeeId)
            .setPayerReferenceId("GAPITesterACH")
            .setPayerFirstName("Bryon")
            .setPayerLastName("Hendrix")
            .setAccountType("Savings")
            .setAccountFullName("Bryon Hendrix")
            .setRoutingNumber("490000018")
            .setAccountNumber("614800811")
            .setTotalAmount("100.00")
            .setIncurFee("Yes")
            .setSaveAccount("Yes")
            .setCheckScanned("Yes")
            .setCheck21("Yes")
            .setCheckDate("01/01/2020")
            .setAuxOnUs(dataHelper.getAuxOnUs())
            .setIncludeImages()
    );

    //Check21,2,2,GAPITesterACH,12035,K!a1@t#h$r4%y^n&,B4*r(o4)wn,Checking,K!a@y#l$e%e^ L&a*n(i)e_r+,490000018,614800811,100.15,NULL,No,Yes,Yes,Yes,01/01/2020,CheckNum,RAND,NULL,NULL,
    testCases.add(
        new AchPayment(
            dataHelper.getReferenceId(),
            "Test Case 2 - Basic Payment - Weird Names",
            getExpectedResponse(gatewayErrors, "2"),
            payeeId)
            .setPayerReferenceId("GAPITesterACH")
            .setPayerFirstName("K!a1@t#h$r4%y^n&")
            .setPayerLastName("B4*r(o4)wn")
            .setAccountType("Checking")
            .setAccountFullName("K!a@y#l$e%e^ L&a*n(i)e_r+")
            .setRoutingNumber("490000018")
            .setAccountNumber("614800811")
            .setTotalAmount("100.15")
            .setIncurFee("No")
            .setSaveAccount("Yes")
            .setCheckScanned("Yes")
            .setCheck21("Yes")
            .setCheckDate("01/01/2020")
            .setCheckNum(dataHelper.getCheckNum())
            .setIncludeImages()
    );

    //Check21,2,3,GAPITesterACH,12035,Kaylee,Lanier,Savings,Kaylee Lanier,490000018,614800811,1423.36,NULL,No,No,Yes,Yes,01/01/2020,Both,RAND,RAND,NULL,
    testCases.add(
        new AchPayment(
            dataHelper.getReferenceId(),
            "Test Case 3 - Basic Payment - CheckNum and AuxOnUs",
            getExpectedResponse(gatewayErrors, "2"),
            payeeId)
            .setPayerReferenceId("GAPITesterACH")
            .setPayerFirstName("Kaylee")
            .setPayerLastName("Lanier")
            .setAccountType("Savings")
            .setAccountFullName("Kaylee Lanier")
            .setRoutingNumber("490000018")
            .setAccountNumber("614800811")
            .setTotalAmount("1423.36")
            .setIncurFee("No")
            .setSaveAccount("No")
            .setCheckScanned("Yes")
            .setCheck21("Yes")
            .setCheckDate("01/01/2020")
            .setCheckNum(dataHelper.getCheckNum())
            .setAuxOnUs(dataHelper.getAuxOnUs())
            .setIncludeImages()
    );

    //Check21,2,4,GAPITesterACH,12035,Kurt,Myers,Savings,Kur_-+=t A. Myers,490000018,614800811,2400.3,NULL,No,No,No,Yes,01/01/2020,NULL,RAND,RAND,NULL,
    testCases.add(
        new AchPayment(
            dataHelper.getReferenceId(),
            "Test Case 4 - Basic Payment - No CheckScanned, No CheckNum or AuxOnUs",
            getExpectedResponse(gatewayErrors, "2"),
            payeeId)
            .setPayerReferenceId("GAPITesterACH")
            .setPayerFirstName("Kurt")
            .setPayerLastName("Myers")
            .setAccountType("Savings")
            .setAccountFullName("Kur_-+=t A. Myers")
            .setRoutingNumber("490000018")
            .setAccountNumber("614800811")
            .setTotalAmount("2400.3")
            .setIncurFee("No")
            .setSaveAccount("No")
            .setCheckScanned("No")
            .setCheck21("Yes")
            .setCheckDate("01/01/2020")
            .setIncludeImages()
    );

    //Check21,2,5,GAPITesterACH,12035,JeffersonBarwickBarrickNorthst,AlistaireBenjaminLafayetteTrya,Checking,JeffersonBarwickBarrickNorthstAlistaireBenjamin LafayetteTryaJeffersonBarwickBarrickNorthstAlistaire,490000018,614800811,1875.36,0,No,No,No,No,01/01/2020,AuxOnUs,NULL,RAND,NULL,
    testCases.add(
        new AchPayment(
            dataHelper.getReferenceId(),
            "Test Case 5 - Basic Payment - Very Long Names",
            getExpectedResponse(gatewayErrors, "2"),
            payeeId)
            .setPayerReferenceId("GAPITesterACH")
            .setPayerFirstName("JeffersonBarwickBarrickNorthst")
            .setPayerLastName("AlistaireBenjaminLafayetteTrya")
            .setAccountType("Checking")
            .setAccountFullName(
                "JeffersonBarwickBarrickNorthstAlistaireBenjamin LafayetteTryaJeffersonBarwickBarrickNorthstAlistaire")
            .setRoutingNumber("490000018")
            .setAccountNumber("614800811")
            .setTotalAmount("1875.36")
            .setIncurFee("No")
            .setSaveAccount("No")
            .setCheckScanned("No")
            .setCheck21("No")
            .setCheckDate("01/01/2020")
            .setAuxOnUs("NULL")
            .setIncludeImages()
    );

    //Check21,2,6,GAPITesterACH,12035,Kurt,Myers,Checking,Kathryn Brown,490000018,614800811,1900.00,0,Yes,No,No,No,01/01/2020,CheckNum,NULL,RAND,NULL,
    testCases.add(
        new AchPayment(
            dataHelper.getReferenceId(),
            "Test Case 6 - No CheckScanned",
            getExpectedResponse(gatewayErrors, "2"),
            payeeId)
            .setPayerReferenceId("GAPITesterACH")
            .setPayerFirstName("Kurt")
            .setPayerLastName("Myers")
            .setAccountType("Checking")
            .setAccountFullName("Kathryn Brown")
            .setRoutingNumber("490000018")
            .setAccountNumber("614800811")
            .setTotalAmount("1900.00")
            .setFeeAmount("0")
            .setIncurFee("Yes")
            .setSaveAccount("No")
            .setCheckScanned("No")
            .setCheck21("No")
            .setCheckDate("01/01/2020")
            .setCheckNum("NULL")
            .setIncludeImages()
    );

    //Check21,2,7,GAPITesterACH,12035,Kurt,Myers,Checking,Kathryn Brown,490000018,614800811,745.36,0,Yes,Yes,No,No,01/01/2020,Both,NULL,RAND,NULL,
    testCases.add(
        new AchPayment(
            dataHelper.getReferenceId(),
            "Test Case 7 - No CheckScanned",
            getExpectedResponse(gatewayErrors, "2"),
            payeeId)
            .setPayerReferenceId("GAPITesterACH")
            .setPayerFirstName("Kurt")
            .setPayerLastName("Myers")
            .setAccountType("Checking")
            .setAccountFullName("Kathryn Brown")
            .setRoutingNumber("490000018")
            .setAccountNumber("614800811")
            .setTotalAmount("745.36")
            .setFeeAmount("0")
            .setIncurFee("Yes")
            .setSaveAccount("Yes")
            .setCheckScanned("No")
            .setCheck21("No")
            .setCheckDate("01/01/2020")
            .setCheckNum("NULL")
            .setAuxOnUs("NULL")
            .setIncludeImages()
    );

    //Check21,2,8,GAPITesterACH,12035,Kurt,Myers,Checking,Kathryn Brown,490000018,614800811,1313.13,0,Yes,Yes,Yes,No,01/01/2020,NULL,NULL,RAND,NULL,
    testCases.add(
        new AchPayment(
            dataHelper.getReferenceId(),
            "Test Case 8 - CheckScanned but no Check21",
            getExpectedResponse(gatewayErrors, "2"),
            payeeId)
            .setPayerReferenceId("GAPITesterACH")
            .setPayerFirstName("Kurt")
            .setPayerLastName("Myers")
            .setAccountType("Checking")
            .setAccountFullName("Kathryn Brown")
            .setRoutingNumber("490000018")
            .setAccountNumber("614800811")
            .setTotalAmount("1313.13")
            .setFeeAmount("0")
            .setIncurFee("Yes")
            .setSaveAccount("Yes")
            .setCheckScanned("Yes")
            .setCheck21("No")
            .setCheckDate("01/01/2020")
            .setIncludeImages()
    );

    //Check21,104,42,GAPITesterACH,12035,Jonathan,Martin,Savings,Jonathan Martin,490000018,614800811,2000,0,No,N,Yes,Yes,01/01/2024,NULL,3,RAND,NULL,
    testCases.add(
        AchPayment.createValidCheck21(
            dataHelper.getReferenceId(),
            "Test Case 42 - Invalid SaveAccount - IncurFee No",
            getExpectedResponse(gatewayErrors, "104"),
            payeeId)
            .setSaveAccount("N")
            .setIncurFee("No")
    );

    //Check21,104,43,GAPITesterACH,12035,Jonathan,Martin,Savings,Jonathan Martin,490000018,614800811,2000,0,Yes,NO,Yes,Yes,01/01/2024,NULL,3,RAND,NULL,
    testCases.add(
        AchPayment.createValidCheck21(
            dataHelper.getReferenceId(),
            "Test Case 43 - Invalid SaveAccount - IncurFee Yes",
            getExpectedResponse(gatewayErrors, "104"),
            payeeId)
            .setSaveAccount("NO")
            .setIncurFee("No")
    );

    //Check21,104,44,GAPITesterACH,12035,Jonathan,Martin,Savings,Jonathan Martin,490000018,614800811,2000,0,,Y,Yes,Yes,01/01/2024,NULL,3,RAND,NULL,
    testCases.add(
        AchPayment.createValidCheck21(
            dataHelper.getReferenceId(),
            "Test Case 44 - Invalid SaveAccount - IncurFee empty",
            getExpectedResponse(gatewayErrors, "104"),
            payeeId)
            .setSaveAccount("Y")
            .setIncurFee("")
    );

    String[] accountTypeValues = {
        "Checking", "Savings",
    };

    for (String accountType : accountTypeValues) {
      //Check21,130,60,GAPITesterACH,120!35,Eugene,Carter,Checking,Eugene Carter,490000018,614800811,200.01,0,No,No,Yes,Yes,01/01/2009,NULL,dt,RAND,NULL,
      //Check21,130,64,GAPITesterACH,120!35,Eugene,Carter,Savings,Eugene Carter,490000018,614800811,200.01,0,No,No,Yes,Yes,01/01/2009,NULL,dt,RAND,NULL,
      testCases.add(
          AchPayment.createValidCheck21(
              dataHelper.getReferenceId(),
              "Test Case 60/64 - Invalid PayeeId1 - " + accountType,
              getExpectedResponse(gatewayErrors, "130"),
              "120!35")
              .setAccountType(accountType)
      );

      //Check21,130,61,GAPITesterACH,12 035,Eugene,Carter,Checking,Eugene Carter,490000018,614800811,200.01,0,No,No,Yes,Yes,01/01/2010,NULL,dt,RAND,NULL,
      //Check21,130,65,GAPITesterACH,12 035,Eugene,Carter,Savings,Eugene Carter,490000018,614800811,200.01,0,No,No,Yes,Yes,01/01/2010,NULL,dt,RAND,NULL,
      testCases.add(
          AchPayment.createValidCheck21(
              dataHelper.getReferenceId(),
              "Test Case 61/65 - Invalid PayeeId2 - " + accountType,
              getExpectedResponse(gatewayErrors, "130"),
              "12 035")
              .setAccountType(accountType)
      );

      //Check21,130,62,GAPITesterACH,120.35,Eugene,Carter,Checking,Eugene Carter,490000018,614800811,200.01,0,Yes,No,Yes,Yes,01/01/2009,NULL,dt,RAND,NULL,
      //Check21,130,66,GAPITesterACH,120.35,Eugene,Carter,Savings,Eugene Carter,490000018,614800811,200.01,0,Yes,No,Yes,Yes,01/01/2009,NULL,dt,RAND,NULL,
      testCases.add(
          AchPayment.createValidCheck21(
              dataHelper.getReferenceId(),
              "Test Case 62/66 - Invalid PayeeId3 - " + accountType,
              getExpectedResponse(gatewayErrors, "130"),
              "120.35")
              .setAccountType(accountType)
      );

      //Check21,130,63,GAPITesterACH,1203-5,Eugene,Carter,Checking,Eugene Carter,490000018,614800811,200.01,0,Yes,No,Yes,Yes,01/01/2010,NULL,dt,RAND,NULL,
      //Check21,130,67,GAPITesterACH,1203-5,Eugene,Carter,Savings,Eugene Carter,490000018,614800811,200.01,0,Yes,No,Yes,Yes,01/01/2010,NULL,dt,RAND,NULL,
      testCases.add(
          AchPayment.createValidCheck21(
              dataHelper.getReferenceId(),
              "Test Case 63/67 - Invalid PayeeId4 - " + accountType,
              getExpectedResponse(gatewayErrors, "130"),
              "1203-5")
              .setAccountType(accountType)
      );
    }

    final String[] incurFeeValues = {
        "No", "Yes", "",
    };

    for (String incurFee : incurFeeValues) {
      //Check21,69,9,,12035,Tammy,Murphy,Savings,Tammy Murphy,490000018,6asdwq14800811,200.1,,No,No,Yes,Yes,01/01/2012,NULL,233155udd65d4a5,RAND,NULL,
      //Check21,69,10,,12035,Tammy,Murphy,Savings,Tammy Murphy,490000018,6asdwq14800811,200.1,0,Yes,No,Yes,Yes,01/01/2012,NULL,233155udd65d4a5,RAND,NULL,
      //Check21,69,11,,12035,Tammy,Murphy,Savings,Tammy Murphy,490000018,6asdwq14800811,200.1,0,,No,Yes,Yes,01/01/2012,NULL,233155udd65d4a5,RAND,NULL,
      testCases.add(
          AchPayment.createValidCheck21(
              dataHelper.getReferenceId(),
              "Test Case 9-11 - Empty PayerReferenceId - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "69"),
              payeeId)
              .setPayerReferenceId("")
              .setIncurFee(incurFee)
      );

      //Check21,70,12,GAPITesterACH,,Eugene,Carter,Checking,Eugene Carter,490000018,614800811,200.01,0,No,No,Yes,Yes,01/01/2013,NULL,73237d8uo05d,RAND,NULL,
      //Check21,70,13,GAPITesterACH,,Eugene,Carter,Checking,Eugene Carter,490000018,614800811,200.01,0,Yes,No,Yes,Yes,01/01/2013,NULL,73237d8uo05d,RAND,NULL,
      //Check21,70,14,GAPITesterACH,,Eugene,Carter,Checking,Eugene Carter,490000018,614800811,200.01,0,,No,Yes,Yes,01/01/2013,NULL,73237d8uo05d,RAND,NULL,
      testCases.add(
          AchPayment.createValidCheck21(
              dataHelper.getReferenceId(),
              "Test Case 12-14 - Empty PayeeId - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "70"),
              "")
              .setIncurFee(incurFee)
      );

      //Check21,71,15,GAPITesterACH,12035,,Baker,Checking,Brian Baker,490000018,614800811,20.1,0,No,No,Yes,Yes,01/01/2014,NULL,34114t1uuudd9,RAND,NULL,
      //Check21,71,16,GAPITesterACH,12035,,Baker,Checking,Brian Baker,490000018,614800811,20.1,0,Yes,No,Yes,Yes,01/01/2014,NULL,34114t1uuudd9,RAND,NULL,
      //Check21,71,17,GAPITesterACH,12035,,Baker,Checking,Brian Baker,490000018,614800811,20.1,0,,No,Yes,Yes,01/01/2014,NULL,34114t1uuudd9,RAND,NULL,
      testCases.add(
          AchPayment.createValidCheck21(
              dataHelper.getReferenceId(),
              "Test Case 15-17 - Empty First Name - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "71"),
              payeeId)
              .setPayerFirstName("")
              .setIncurFee(incurFee)
      );

      //Check21,72,18,GAPITesterACH,12035,Brian,,Checking,,490000018,614800811,20.1,0,No,No,Yes,Yes,01/01/2015,NULL,34114t1uuudd9,RAND,NULL,
      //Check21,72,19,GAPITesterACH,12035,Brian,,Checking,,490000018,614800811,20.1,0,Yes,No,Yes,Yes,01/01/2015,NULL,34114t1uuudd9,RAND,NULL,
      //Check21,72,20,GAPITesterACH,12035,Brian,,Checking,,490000018,614800811,20.1,0,,No,Yes,Yes,01/01/2015,NULL,34114t1uuudd9,RAND,NULL,
      testCases.add(
          AchPayment.createValidCheck21(
              dataHelper.getReferenceId(),
              "Test Case 18-20 - Empty Last Name - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "72"),
              payeeId)
              .setPayerLastName("")
              .setIncurFee(incurFee)
      );

      //Check21,85,21,GAPITesterACH,12035,Jose,Edwards,Checking,Jose Edwards,490000018,614800811,,0,No,No,Yes,Yes,01/01/2016,NULL,27o55todt8u584,RAND,NULL,
      //Check21,85,22,GAPITesterACH,12035,Jose,Edwards,Checking,Jose Edwards,490000018,614800811,,0,Yes,No,Yes,Yes,01/01/2016,NULL,27o55todt8u584,RAND,NULL,
      //Check21,85,23,GAPITesterACH,12035,Jose,Edwards,Checking,Jose Edwards,490000018,614800811,,0,,No,Yes,Yes,01/01/2016,NULL,27o55todt8u584,RAND,NULL,
      testCases.add(
          AchPayment.createValidCheck21(
              dataHelper.getReferenceId(),
              "Test Case 21-23 - Empty Total Amount - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "85"),
              payeeId)
              .setTotalAmount("")
              .setIncurFee(incurFee)
      );

      //Check21,86,24,GAPITesterACH,12035,Jonathan,Martin,Savings,Jonathan Martin,490000018,614800811,2000,0,No,,Yes,Yes,01/01/2018,NULL,tdad78o9dut1,RAND,NULL,
      //Check21,86,25,GAPITesterACH,12035,Jonathan,Martin,Savings,Jonathan Martin,490000018,614800811,2000,0,Yes,,Yes,Yes,01/01/2018,NULL,tdad78o9dut1,RANDc,NULL,
      //Check21,86,26,GAPITesterACH,12035,Jonathan,Martin,Savings,Jonathan Martin,490000018,614800811,2000,0,,,Yes,Yes,01/01/2018,NULL,tdad78o9dut1,RAND,NULL,
      testCases.add(
          AchPayment.createValidCheck21(
              dataHelper.getReferenceId(),
              "Test Case 24-26 - Empty SaveAccount - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "86"),
              payeeId)
              .setSaveAccount("")
              .setIncurFee(incurFee)
      );

      //Check21,87,27,GAPITesterACH,12035,Mary,Powell,,Mary Powell,490000018,614800811,200,0,No,No,Yes,Yes,01/01/2019,NULL,3a88d5uu,RAND,NULL,
      //Check21,87,28,GAPITesterACH,12035,Mary,Powell,,Mary Powell,490000018,614800811,200,0,Yes,No,Yes,Yes,01/01/2019,NULL,3a88d5uu,RAND,NULL,
      //Check21,87,29,GAPITesterACH,12035,Mary,Powell,,Mary Powell,490000018,614800811,200,0,,No,Yes,Yes,01/01/2019,NULL,3a88d5uu,RAND,NULL,
      testCases.add(
          AchPayment.createValidCheck21(
              dataHelper.getReferenceId(),
              "Test Case 27-29 - Empty AccountType - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "87"),
              payeeId)
              .setAccountType("")
              .setIncurFee(incurFee)
      );

      //Check21,88,30,GAPITesterACH,12035,Brian,Baker,Checking,,490000018,614800811,20.1,0,No,No,Yes,Yes,01/01/2020,NULL,34114t1uuudd9,RAND,NULL,
      //Check21,88,31,GAPITesterACH,12035,Brian,Baker,Checking,,490000018,614800811,20.1,0,Yes,No,Yes,Yes,01/01/2020,NULL,34114t1uuudd9,RAND,NULL,
      //Check21,88,32,GAPITesterACH,12035,Brian,Baker,Checking,,490000018,614800811,20.1,0,,No,Yes,Yes,01/01/2020,NULL,34114t1uuudd9,RAND,NULL,
      testCases.add(
          AchPayment.createValidCheck21(
              dataHelper.getReferenceId(),
              "Test Case 30-32 - Empty AccountFullName - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "88"),
              payeeId)
              .setAccountFullName("")
              .setIncurFee(incurFee)
      );

      //Check21,89,33,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,,61480081,20,0,No,No,Yes,Yes,01/01/2021,NULL,1313,RAND,NULL,
      //Check21,89,34,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,,61480081,20,0,Yes,No,Yes,Yes,01/01/2021,NULL,1313,RAND,NULL,
      //Check21,89,35,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,,61480081,20,0,,No,Yes,Yes,01/01/2021,NULL,1313,RAND,NULL,
      testCases.add(
          AchPayment.createValidCheck21(
              dataHelper.getReferenceId(),
              "Test Case 33-35 - Empty RoutingNumber - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "89"),
              payeeId)
              .setRoutingNumber("")
              .setIncurFee(incurFee)
      );

      //Check21,90,36,GAPITesterACH,12035,Mary,Powell,Savings,Mary Powell,490000018,,200,0,No,No,Yes,Yes,01/01/2022,NULL,3a88d5uu,RAND,NULL,
      //Check21,90,37,GAPITesterACH,12035,Mary,Powell,Savings,Mary Powell,490000018,,200,0,Yes,No,Yes,Yes,01/01/2022,NULL,3a88d5uu,RAND,NULL,
      //Check21,90,38,GAPITesterACH,12035,Mary,Powell,Savings,Mary Powell,490000018,,200,0,,No,Yes,Yes,01/01/2022,NULL,3a88d5uu,RAND,NULL,
      testCases.add(
          AchPayment.createValidCheck21(
              dataHelper.getReferenceId(),
              "Test Case 36-38 - Empty AccountNumber - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "90"),
              payeeId)
              .setAccountNumber("")
              .setIncurFee(incurFee)
      );

      //Check21,103,39,GAPITesterACH,12035,Eugene,Carter,checking,Eugene Carter,490000018,614800811,200.01,0,No,No,Yes,Yes,01/01/2023,NULL,dt,RAND,NULL,
      //Check21,103,40,GAPITesterACH,12035,Eugene,Carter,checking,Eugene Carter,490000018,614800811,200.01,0,Yes,No,Yes,Yes,01/01/2023,NULL,dt,RAND,NULL,
      //Check21,103,41,GAPITesterACH,12035,Eugene,Carter,checking,Eugene Carter,490000018,614800811,200.01,0,,No,Yes,Yes,01/01/2023,NULL,dt,RAND,NULL,
      testCases.add(
          AchPayment.createValidCheck21(
              dataHelper.getReferenceId(),
              "Test Case 39-41 - Invalid AccountType - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "103"),
              payeeId)
              .setAccountType("checking")
              .setIncurFee(incurFee)
      );

      //Check21,111,45,a134oihqf9w7h2ufhqiusd872q45y92715yr7q8wgfasyfasf,12035,Eugene,Carter,Checking,JeffersonBarwickBarrickNorthst AlistaireBenjaminLafayetteTrya,490000018,614800811,200.01,0,No,No,Yes,Yes,01/01/2025,NULL,73237d8uo05d,RAND,NULL,
      //Check21,111,46,a134oihqf9w7h2ufhqiusd872q45y92715yr7q8wgfasyfasf,12035,Eugene,Carter,Checking,JeffersonBarwickBarrickNorthst AlistaireBenjaminLafayetteTrya,490000018,614800811,200.01,0,Yes,No,Yes,Yes,01/01/2025,NULL,73237d8uo05d,RAND,NULL,
      //Check21,111,47,a134oihqf9w7h2ufhqiusd872q45y92715yr7q8wgfasyfasf,12035,Eugene,Carter,Checking,JeffersonBarwickBarrickNorthst AlistaireBenjaminLafayetteTrya,490000018,614800811,200.01,0,,No,Yes,Yes,01/01/2025,NULL,73237d8uo05d,RAND,NULL,
      testCases.add(
          AchPayment.createValidCheck21(
              dataHelper.getReferenceId(),
              "Test Case 45-47 - PayerReferenceId too long - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "111"),
              payeeId)
              .setPayerReferenceId("a134oihqf9w7h2ufhqiusd872q45y92715yr7q8wgfasyfasf")
              .setIncurFee(incurFee)
      );

      //Check21,112,48,GAPITesterACH,12035,JeffersonBarwickBarrickNorthsta,AlistaireBenjaminLafayetteTrya,Checking,JeffersonBarwickBarrickNorthst AlistaireBenjaminLafayetteTrya,490000018,614800811,2000.11,0,No,No,Yes,Yes,01/01/2026,NULL,699tod,RAND,NULL,
      //Check21,112,49,GAPITesterACH,12035,JeffersonBarwickBarrickNorthsta,AlistaireBenjaminLafayetteTrya,Checking,JeffersonBarwickBarrickNorthst AlistaireBenjaminLafayetteTrya,490000018,614800811,2000.11,0,Yes,No,Yes,Yes,01/01/2026,NULL,699tod,RAND,NULL,
      //Check21,112,50,GAPITesterACH,12035,JeffersonBarwickBarrickNorthsta,AlistaireBenjaminLafayetteTrya,Checking,JeffersonBarwickBarrickNorthst AlistaireBenjaminLafayetteTrya,490000018,614800811,2000.11,0,,No,Yes,Yes,01/01/2026,NULL,699tod,RAND,NULL,
      testCases.add(
          AchPayment.createValidCheck21(
              dataHelper.getReferenceId(),
              "Test Case 48-50 - PayerFirstName too long - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "112"),
              payeeId)
              .setPayerFirstName("JeffersonBarwickBarrickNorthsta")
              .setIncurFee(incurFee)
      );

      //Check21,113,51,GAPITesterACH,12035,JeffersonBarwickBarrickNorthst,AlistaireBenjaminLafayetteTryar,Checking,JeffersonBarwickBarrickNorthst AlistaireBenjaminLafayetteTrya,490000018,614800811,2000.11,0,No,No,Yes,Yes,01/01/2027,NULL,699tod,RAND,NULL,
      //Check21,113,52,GAPITesterACH,12035,JeffersonBarwickBarrickNorthst,AlistaireBenjaminLafayetteTryar,Checking,JeffersonBarwickBarrickNorthst AlistaireBenjaminLafayetteTrya,490000018,614800811,2000.11,0,Yes,No,Yes,Yes,01/01/2027,NULL,699tod,RAND,NULL,
      //Check21,113,53,GAPITesterACH,12035,JeffersonBarwickBarrickNorthst,AlistaireBenjaminLafayetteTryar,Checking,JeffersonBarwickBarrickNorthst AlistaireBenjaminLafayetteTrya,490000018,614800811,2000.11,0,,No,Yes,Yes,01/01/2027,NULL,699tod,RAND,NULL,
      testCases.add(
          AchPayment.createValidCheck21(
              dataHelper.getReferenceId(),
              "Test Case 51-53 - PayerLastName too long - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "113"),
              payeeId)
              .setPayerLastName("AlistaireBenjaminLafayetteTryar")
              .setIncurFee(incurFee)
      );

      //Check21,114,54,GAPITesterACH,12035,JeffersonBarwickBarrickNorthst,AlistaireBenjaminLafayetteTrya,Checking,JeffersonBarwickBarrickNorthstAlistaireBenjamina LafayetteTryaJeffersonBarwickBarrickNorthstAlistaire,490000018,614800811,2000.11,0,No,No,Yes,Yes,01/01/2028,NULL,699tod,RAND,NULL,
      //Check21,114,55,GAPITesterACH,12035,JeffersonBarwickBarrickNorthst,AlistaireBenjaminLafayetteTrya,Checking,JeffersonBarwickBarrickNorthstAlistaireBenjamina LafayetteTryaJeffersonBarwickBarrickNorthstAlistaire,490000018,614800811,2000.11,0,Yes,No,Yes,Yes,01/01/2028,NULL,699tod,RAND,NULL,
      //Check21,114,56,GAPITesterACH,12035,JeffersonBarwickBarrickNorthst,AlistaireBenjaminLafayetteTrya,Checking,JeffersonBarwickBarrickNorthstAlistaireBenjamina LafayetteTryaJeffersonBarwickBarrickNorthstAlistaire,490000018,614800811,2000.11,0,,No,Yes,Yes,01/01/2028,NULL,699tod,RAND,NULL,
      testCases.add(
          AchPayment.createValidCheck21(
              dataHelper.getReferenceId(),
              "Test Case 54-56 - AccountFullName too long - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "114"),
              payeeId)
              .setAccountFullName(
                  "JeffersonBarwickBarrickNorthstAlistaireBenjamina LafayetteTryaJeffersonBarwickBarrickNorthstAlistaire")
              .setIncurFee(incurFee)
      );

      //Check21,115,57,GAPITesterACH,12035,Kurt,Myers,Savings,Kur_-+=t A. Myers,4900000181,614800811,20.11,0,No,No,No,Yes,01/01/15,NULL,,RAND,NULL,
      //Check21,115,58,GAPITesterACH,12035,Kurt,Myers,Savings,Kur_-+=t A. Myers,4900000181,614800811,20.11,0,Yes,No,No,Yes,01/01/15,NULL,,RAND,NULL,
      //Check21,115,59,GAPITesterACH,12035,Kurt,Myers,Savings,Kur_-+=t A. Myers,4900000181,614800811,20.11,0,,No,No,Yes,01/01/15,NULL,,RAND,NULL,
      testCases.add(
          AchPayment.createValidCheck21(
              dataHelper.getReferenceId(),
              "Test Case 57-59 - RoutingNumber too long - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "115"),
              payeeId)
              .setRoutingNumber("4900000181")
              .setIncurFee(incurFee)
      );

      //Check21,131,68,GAPITesterACH,12035,Mary,Powell,Savings,Mary Powell,49000001a,614800811,200,0,No,No,Yes,Yes,01/01/2011,NULL,0odoo8uao,RAND,NULL,
      //Check21,131,70,GAPITesterACH,12035,Mary,Powell,Savings,Mary Powell,49000001a,614800811,200,0,Yes,No,Yes,Yes,01/01/2011,NULL,0odoo8uao,RAND,NULL,
      //Check21,131,72,GAPITesterACH,12035,Mary,Powell,Savings,Mary Powell,49000001a,614800811,200,0,,No,Yes,Yes,01/01/2011,NULL,0odoo8uao,RAND,NULL,
      testCases.add(
          AchPayment.createValidCheck21(
              dataHelper.getReferenceId(),
              "Test Case 68/70/72 - Invalid RoutingNumber1 - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "131"),
              payeeId)
              .setRoutingNumber("49000001a")
              .setIncurFee(incurFee)
      );

      //Check21,131,69,GAPITesterACH,12035,Mary,Powell,Savings,Mary Powell,4900 0001,614800811,200,0,No,No,Yes,Yes,01/01/2012,NULL,0odoo8uao,RAND,NULL,
      //Check21,131,71,GAPITesterACH,12035,Mary,Powell,Savings,Mary Powell,4900 0001,614800811,200,0,Yes,No,Yes,Yes,01/01/2012,NULL,0odoo8uao,RAND,NULL,
      //Check21,131,73,GAPITesterACH,12035,Mary,Powell,Savings,Mary Powell,4900 0001,614800811,200,0,,No,Yes,Yes,01/01/2012,NULL,0odoo8uao,RAND,NULL,
      testCases.add(
          AchPayment.createValidCheck21(
              dataHelper.getReferenceId(),
              "Test Case 69/71/73 - Invalid RoutingNumber2 - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "131"),
              payeeId)
              .setRoutingNumber("4900 0001")
              .setIncurFee(incurFee)
      );

      //Check21,132,74,GAPITesterACH,12035,Tammy,Murphy,Savings,Tammy Murphy,490000018,614800811dfwq,200.1,0,No,No,Yes,Yes,01/01/2013,NULL,233155udd65d4a5,RAND,NULL,
      //Check21,132,76,GAPITesterACH,12035,Tammy,Murphy,Savings,Tammy Murphy,490000018,614800811dfwq,200.1,0,Yes,No,Yes,Yes,01/01/2013,NULL,233155udd65d4a5,RAND,NULL,
      //Check21,132,78,GAPITesterACH,12035,Tammy,Murphy,Savings,Tammy Murphy,490000018,614800811dfwq,200.1,0,,No,Yes,Yes,01/01/2013,NULL,233155udd65d4a5,RAND,NULL,
      testCases.add(
          AchPayment.createValidCheck21(
              dataHelper.getReferenceId(),
              "Test Case 74/76/78 - Invalid AccountNumber1 - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "132"),
              payeeId)
              .setAccountNumber("614800811dfwq")
              .setIncurFee(incurFee)
      );

      //Check21,132,75,GAPITesterACH,12035,Tammy,Murphy,Savings,Tammy Murphy,490000018,6148 00811,200.1,0,No,No,Yes,Yes,01/01/2014,NULL,233155udd65d4a5,RAND,NULL,
      //Check21,132,77,GAPITesterACH,12035,Tammy,Murphy,Savings,Tammy Murphy,490000018,6148 00811,200.1,0,Yes,No,Yes,Yes,01/01/2014,NULL,233155udd65d4a5,RAND,NULL,
      //Check21,132,79,GAPITesterACH,12035,Tammy,Murphy,Savings,Tammy Murphy,490000018,6148 00811,200.1,0,,No,Yes,Yes,01/01/2014,NULL,233155udd65d4a5,RAND,NULL,
      testCases.add(
          AchPayment.createValidCheck21(
              dataHelper.getReferenceId(),
              "Test Case 75/77/79 - Invalid AccountNumber2 - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "132"),
              payeeId)
              .setAccountNumber("6148 00811")
              .setIncurFee(incurFee)
      );

      final String[] invalidTotalAmounts = {
          "2000.003", "2000 03", "2000. 3", "2000. 03", "2000 .3", "2000 .03", "2000.", ".0",
          "1.0a",
      };

      for (String totalAmount : invalidTotalAmounts) {
        //Check21,133,80,GAPITesterACH,12035,Jose,Edwards,Checking,Jose Edwards,490000018,614800811,2000.003,0,No,No,No,Yes,01/01/15,NULL,27o55todt8u584,RAND,NULL,
        //Check21,133,81,GAPITesterACH,12035,Jose,Edwards,Checking,Jose Edwards,490000018,614800811,2000 03,0,No,No,Yes,Yes,01/01/2016,NULL,27o55todt8u584,RAND,NULL,
        //Check21,133,82,GAPITesterACH,12035,Jose,Edwards,Checking,Jose Edwards,490000018,614800811,2000. 3,0,No,No,Yes,Yes,01/01/2017,NULL,27o55todt8u584,RAND,NULL,
        //Check21,133,83,GAPITesterACH,12035,Jose,Edwards,Checking,Jose Edwards,490000018,614800811,2000. 03,0,No,No,Yes,Yes,01/01/2014,NULL,27o55todt8u584,RAND,NULL,
        //Check21,133,84,GAPITesterACH,12035,Jose,Edwards,Checking,Jose Edwards,490000018,614800811,2000 .3,0,No,No,Yes,Yes,01/01/2015,NULL,27o55todt8u584,RAND,NULL,
        //Check21,133,85,GAPITesterACH,12035,Jose,Edwards,Checking,Jose Edwards,490000018,614800811,2000 .03,0,No,No,Yes,Yes,01/01/2016,NULL,27o55todt8u584,RAND,NULL,
        //Check21,133,86,GAPITesterACH,12035,Jose,Edwards,Checking,Jose Edwards,490000018,614800811,2000.,0,No,No,Yes,Yes,01/01/2017,NULL,27o55todt8u584,RAND,NULL,
        //Check21,133,87,GAPITesterACH,12035,Jonathan,Martin,Savings,Jonathan Martin,490000018,614800811,.0,0,No,No,Yes,Yes,01/01/2014,NULL,tdad78o9dut1,RAND,NULL,
        //Check21,133,88,GAPITesterACH,12035,Jonathan,Martin,Savings,Jonathan Martin,490000018,614800811,1.0a,0,No,No,Yes,Yes,01/01/2015,NULL,tdad78o9dut1,RAND,NULL,
        //Check21,133,89,GAPITesterACH,12035,Jose,Edwards,Checking,Jose Edwards,490000018,614800811,2000.003,0,Yes,No,No,Yes,01/01/15,NULL,27o55todt8u584,RAND,NULL,
        //Check21,133,90,GAPITesterACH,12035,Jose,Edwards,Checking,Jose Edwards,490000018,614800811,2000 03,0,Yes,No,Yes,Yes,01/01/2016,NULL,27o55todt8u584,RAND,NULL,
        //Check21,133,91,GAPITesterACH,12035,Jose,Edwards,Checking,Jose Edwards,490000018,614800811,2000. 3,0,Yes,No,Yes,Yes,01/01/2017,NULL,27o55todt8u584,RAND,NULL,
        //Check21,133,92,GAPITesterACH,12035,Jose,Edwards,Checking,Jose Edwards,490000018,614800811,2000. 03,0,Yes,No,Yes,Yes,01/01/2014,NULL,27o55todt8u584,RAND,NULL,
        //Check21,133,93,GAPITesterACH,12035,Jose,Edwards,Checking,Jose Edwards,490000018,614800811,2000 .3,0,Yes,No,Yes,Yes,01/01/2015,NULL,27o55todt8u584,RAND,NULL,
        //Check21,133,94,GAPITesterACH,12035,Jose,Edwards,Checking,Jose Edwards,490000018,614800811,2000 .03,0,Yes,No,Yes,Yes,01/01/2016,NULL,27o55todt8u584,RAND,NULL,
        //Check21,133,95,GAPITesterACH,12035,Jose,Edwards,Checking,Jose Edwards,490000018,614800811,2000.,0,Yes,No,Yes,Yes,01/01/2017,NULL,27o55todt8u584,RAND,NULL,
        //Check21,133,96,GAPITesterACH,12035,Jonathan,Martin,Savings,Jonathan Martin,490000018,614800811,.0,0,Yes,No,Yes,Yes,01/01/2014,NULL,tdad78o9dut1,RAND,NULL,
        //Check21,133,97,GAPITesterACH,12035,Jonathan,Martin,Savings,Jonathan Martin,490000018,614800811,1.0a,0,Yes,No,Yes,Yes,01/01/2015,NULL,tdad78o9dut1,RAND,NULL,
        //Check21,133,98,GAPITesterACH,12035,Jose,Edwards,Checking,Jose Edwards,490000018,614800811,2000.003,0,,No,No,Yes,01/01/15,NULL,27o55todt8u584,RAND,NULL,
        //Check21,133,99,GAPITesterACH,12035,Jose,Edwards,Checking,Jose Edwards,490000018,614800811,2000 03,0,,No,Yes,Yes,01/01/2016,NULL,27o55todt8u584,RAND,NULL,
        //Check21,133,100,GAPITesterACH,12035,Jose,Edwards,Checking,Jose Edwards,490000018,614800811,2000. 3,0,,No,Yes,Yes,01/01/2017,NULL,27o55todt8u584,RAND,NULL,
        //Check21,133,101,GAPITesterACH,12035,Jose,Edwards,Checking,Jose Edwards,490000018,614800811,2000. 03,0,,No,Yes,Yes,01/01/2014,NULL,27o55todt8u584,RAND,NULL,
        //Check21,133,102,GAPITesterACH,12035,Jose,Edwards,Checking,Jose Edwards,490000018,614800811,2000 .3,0,,No,Yes,Yes,01/01/2015,NULL,27o55todt8u584,RAND,NULL,
        //Check21,133,103,GAPITesterACH,12035,Jose,Edwards,Checking,Jose Edwards,490000018,614800811,2000 .03,0,,No,Yes,Yes,01/01/2016,NULL,27o55todt8u584,RAND,NULL,
        //Check21,133,104,GAPITesterACH,12035,Jose,Edwards,Checking,Jose Edwards,490000018,614800811,2000.,0,,No,Yes,Yes,01/01/2017,NULL,27o55todt8u584,RAND,NULL,
        //Check21,133,105,GAPITesterACH,12035,Jonathan,Martin,Savings,Jonathan Martin,490000018,614800811,.0,0,,No,Yes,Yes,01/01/2014,NULL,tdad78o9dut1,RAND,NULL,
        //Check21,133,106,GAPITesterACH,12035,Jonathan,Martin,Savings,Jonathan Martin,490000018,614800811,1.0a,0,,No,Yes,Yes,01/01/2015,NULL,tdad78o9dut1,RAND,NULL,
        testCases.add(
            AchPayment.createValidCheck21(
                dataHelper.getReferenceId(),
                "Test Case 80-106 - Invalid TotalAmount " + totalAmount + " - IncurFee " + incurFee,
                getExpectedResponse(gatewayErrors, "133"),
                payeeId)
                .setTotalAmount(totalAmount)
                .setIncurFee(incurFee)
        );
      }

      //Check21,149,107,GAPITesterACH,34,Eugene,Carter,Checking,Eugene Carter,490000018,614800811,200.01,0,No,No,Yes,Yes,01/01/2016,NULL,73237d8uo05d,RAND,NULL,
      //Check21,149,108,GAPITesterACH,34,Eugene,Carter,Checking,Eugene Carter,490000018,614800811,200.01,0,Yes,No,Yes,Yes,01/01/2016,NULL,73237d8uo05d,RAND,NULL,
      //Check21,149,109,GAPITesterACH,34,Eugene,Carter,Checking,Eugene Carter,490000018,614800811,200.01,0,,No,Yes,Yes,01/01/2016,NULL,73237d8uo05d,RAND,NULL,
      testCases.add(
          AchPayment.createValidCheck21(
              dataHelper.getReferenceId(),
              "Test Case 107-109 - Incorrect PayeeId - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "149"),
              "34")
              .setIncurFee(incurFee)
              .setIncludeImages()
      );

      //Check21,151,110,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,49000001,61480081,20,0,No,No,Yes,Yes,01/01/2017,NULL,1313,RAND,NULL,
      //Check21,151,111,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,49000001,61480081,20,0,Yes,No,Yes,Yes,01/01/2017,NULL,1313,RAND,NULL,
      //Check21,151,112,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,49000001,61480081,20,0,,No,Yes,Yes,01/01/2017,NULL,1313,RAND,NULL,
      testCases.add(
          AchPayment.createValidCheck21(
              dataHelper.getReferenceId(),
              "Test Case 110-112 - Incorrect RoutingNumber - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "151"),
              payeeId)
              .setRoutingNumber("49000001")
              .setIncurFee(incurFee)
              .setIncludeImages()
      );

      //Check21,184,113,GAPITesterACH,12035,Jonathan,Martin,Savings,Jonathan Martin,490000018,614800811,2,1,No,No,es,Yes,01/01/2015,NULL,tdad78o9dut1,RAND,NULL,
      //Check21,184,115,GAPITesterACH,12035,Jonathan,Martin,Savings,Jonathan Martin,490000018,614800811,2,1,Yes,No,es,Yes,01/01/2015,NULL,tdad78o9dut1,RAND,NULL,
      //Check21,184,117,GAPITesterACH,12035,Jonathan,Martin,Savings,Jonathan Martin,490000018,614800811,2,1,,No,es,Yes,01/01/2015,NULL,tdad78o9dut1,RAND,NULL,
      testCases.add(
          AchPayment.createValidCheck21(
              dataHelper.getReferenceId(),
              "Test Case 113/115/117 - Invalid CheckScanned1 - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "184"),
              payeeId)
              .setCheckScanned("es")
              .setIncurFee(incurFee)
      );

      //Check21,184,114,GAPITesterACH,12035,Jonathan,Martin,Savings,Jonathan Martin,490000018,614800811,2,1,No,No,,Yes,01/01/2015,NULL,tdad78o9dut1,RAND,NULL,
      //Check21,184,116,GAPITesterACH,12035,Jonathan,Martin,Savings,Jonathan Martin,490000018,614800811,2,1,Yes,No,,Yes,01/01/2015,NULL,tdad78o9dut1,RAND,NULL,
      //Check21,184,118,GAPITesterACH,12035,Jonathan,Martin,Savings,Jonathan Martin,490000018,614800811,2,1,,No,,Yes,01/01/2015,NULL,tdad78o9dut1,RAND,NULL,
      testCases.add(
          AchPayment.createValidCheck21(
              dataHelper.getReferenceId(),
              "Test Case 114/116/118 - Invalid CheckScanned2 - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "184"),
              payeeId)
              .setCheckScanned("")
              .setIncurFee(incurFee)
      );

      //Check21,185,119,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2,11.11,No,No,Yes,Yes,01/01/2015,NULL,1236,RAND,NULL,
      //Check21,185,120,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2,11.11,Yes,No,Yes,Yes,01/01/2015,NULL,1236,RAND,NULL,
      //Check21,185,121,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2,11.11,,No,Yes,Yes,01/01/2015,NULL,1236,RAND,NULL,
      testCases.add(
          AchPayment.createValidCheck21(
              dataHelper.getReferenceId(),
              "Test Case 119-121 - FeeAmount greater than total - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "185"),
              payeeId)
              .setTotalAmount("2")
              .setFeeAmount("11.11")
              .setIncurFee(incurFee)
      );

      //Check21,200,122,GAPITesterACH,12035,N_-+=eil,Wil_-+=son,Checking,Neil Wilson,490000018,614800811,2.1,0,No,No,Yes,Yes,,NULL,,RAND,NULL,
      //Check21,200,123,GAPITesterACH,12035,N_-+=eil,Wil_-+=son,Checking,Neil Wilson,490000018,614800811,2.1,0,Yes,No,Yes,Yes,,NULL,,RAND,NULL,
      //Check21,200,124,GAPITesterACH,12035,N_-+=eil,Wil_-+=son,Checking,Neil Wilson,490000018,614800811,2.1,0,,No,Yes,Yes,,NULL,,RAND,NULL,
      testCases.add(
          AchPayment.createValidCheck21(
              dataHelper.getReferenceId(),
              "Test Case 122-124 - empty CheckDate - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "200"),
              payeeId)
              .setCheckDate("")
              .setIncurFee(incurFee)
      );

      //Check21,203,125,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.11,0,No,No,Yes,Yes,01/02/2017,CheckNum,12563985697458256,RAND,NULL,
      //Check21,203,126,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.11,0,Yes,No,Yes,Yes,01/02/2017,CheckNum,12563985697458256,RAND,NULL,
      //Check21,203,127,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.11,0,,No,Yes,Yes,01/02/2017,CheckNum,12563985697458256,RAND,NULL,
      testCases.add(
          AchPayment.createValidCheck21(
              dataHelper.getReferenceId(),
              "Test Case 125-127 - CheckNum too long - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "203"),
              payeeId)
              .setCheckNum("12563985697458256")
              .setIncurFee(incurFee)
      );

      //Check21,207,238,GAPITesterACH,12035,Jonathan,Martin,Savings,Jonathan Martin,490000018,614800811,2,1,No,No,Yes,es,01/01/2008,NULL,tdad78o9dut1,RAND,NULL,
      //Check21,207,239,GAPITesterACH,12035,Jonathan,Martin,Savings,Jonathan Martin,490000018,614800811,2,1,Yes,No,Yes,es,01/01/2008,NULL,tdad78o9dut1,RAND,NULL,
      //Check21,207,240,GAPITesterACH,12035,Jonathan,Martin,Savings,Jonathan Martin,490000018,614800811,2,1,,No,Yes,es,01/01/2008,NULL,tdad78o9dut1,RAND,NULL,
      testCases.add(
          AchPayment.createValidCheck21(
              dataHelper.getReferenceId(),
              "Test Case 238-240 - Invalid Check21 1 - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "207"),
              payeeId)
              .setCheck21("es")
              .setIncurFee(incurFee)
      );

      //Check21,207,241,GAPITesterACH,12035,Jonathan,Martin,Savings,Jonathan Martin,490000018,614800811,3,1,No,No,Yes,,01/01/2009,NULL,tdad78o9dut1,RAND,NULL,
      //Check21,207,242,GAPITesterACH,12035,Jonathan,Martin,Savings,Jonathan Martin,490000018,614800811,3,1,Yes,No,Yes,,01/01/2009,NULL,tdad78o9dut1,RAND,NULL,
      //Check21,207,243,GAPITesterACH,12035,Jonathan,Martin,Savings,Jonathan Martin,490000018,614800811,3,1,,No,Yes,,01/01/2009,NULL,tdad78o9dut1,RAND,NULL,
      testCases.add(
          AchPayment.createValidCheck21(
              dataHelper.getReferenceId(),
              "Test Case 241-243 - Invalid Check21 2 - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "207"),
              payeeId)
              .setCheck21("")
              .setIncurFee(incurFee)
      );

      //Check21,208,244,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,40,11.11,No,No,Yes,Yes,01/01/2010,NULL,1236,RAND,NULL,
      //Check21,208,245,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,40,11.11,Yes,No,Yes,Yes,01/01/2010,NULL,1236,RAND,NULL,
      //Check21,208,246,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,40,11.11,,No,Yes,Yes,01/01/2010,NULL,1236,RAND,NULL,
      testCases.add(
          AchPayment.createValidCheck21(
              dataHelper.getReferenceId(),
              "Test Case 244-246 - Fee greater than 0 for check - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "208"),
              payeeId)
              .setFeeAmount("11.11")
              .setCheck21("Yes")
              .setIncurFee(incurFee)
      );

      //Check21,217,247,GAPITesterACH,12035,N_-+=eil,Wil_-+=son,Checking,Neil Wilson,490000018,54564654654949800000000000000000000000000,2.1,0,No,No,Yes,Yes,01/01/2011,NULL,,RAND,NULL,
      //Check21,217,248,GAPITesterACH,12035,N_-+=eil,Wil_-+=son,Checking,Neil Wilson,490000018,54564654654949800000000000000000000000000,2.1,0,Yes,No,Yes,Yes,01/01/2011,NULL,,RAND,NULL,
      //Check21,217,249,GAPITesterACH,12035,N_-+=eil,Wil_-+=son,Checking,Neil Wilson,490000018,54564654654949800000000000000000000000000,2.1,0,,No,Yes,Yes,01/01/2011,NULL,,RAND,NULL,
      testCases.add(
          AchPayment.createValidCheck21(
              dataHelper.getReferenceId(),
              "Test Case 247-249 - Invalid AccountNumber - IncurFee " + incurFee,
              getExpectedResponse(gatewayErrors, "217"),
              payeeId)
              .setAccountNumber("54564654654949800000000000000000000000000")
              .setIncurFee(incurFee)
              .setIncludeImages()
      );
    }

    //Check21,219,250,GAPITesterACH,12035,Bryon,Hendrix,Savings,Bryon Hendrix,490000018,614800811,100.00,NULL,Yes,Yes,Yes,Yes,01/01/2020,CheckNum,1234?,RAND
    testCases.add(
        AchPayment.createValidCheck21(
            dataHelper.getReferenceId(),
            "Test Case 250 - Invalid CheckNumber",
            getExpectedResponse(gatewayErrors, "219"),
            payeeId)
            .setCheckNum("1234?")
            .setIncludeImages()
    );

    //Check21,258,251,GAPITesterACH,12035,Bryon,Hendrix,Savings,Bryon Hendrix,490000018,614800811,100.00,NULL,Yes,Yes,Yes,Yes,01/01/2020,AuxOnUs,1000000000000000000000000,NULL,NULL,
    testCases.add(
        AchPayment.createValidCheck21(
            dataHelper.getReferenceId(),
            "Test Case 251 - AuxOnUs too long",
            getExpectedResponse(gatewayErrors, "258"),
            payeeId)
            .setAuxOnUs("1000000000000000000000000")
            .setIncludeImages()
    );

    //Check21,259,252,GAPITesterACH,12035,Bryon,Hendrix,Savings,Bryon Hendrix,490000018,614800811,100.00,NULL,Yes,Yes,Yes,Yes,01/01/2020,AuxOnUs,badnum,NULL,NULL,
    testCases.add(
        AchPayment.createValidCheck21(
            dataHelper.getReferenceId(),
            "Test Case 252 - Invalid AuxOnUs",
            getExpectedResponse(gatewayErrors, "259"),
            payeeId)
            .setAuxOnUs("badnum")
    );

    String[] validIncurFee = {
        "Yes", "No", "",
    };

    String[] invalidCheckDates = {
        "02//2014", "02//14", "/10/2014", "/10/14", "1/1/", "02/10/", "02/10/14", "02/31/2014",
        "02/31/14", "02a/10/2014", "02a/10/14", "02/10a/2014", "02/10a/14", "02/10/2014a",
        "02/10/14a", "02!/10/2014", "02!/10/14", "02/10!/2014", "02/10!/14", "02/10/2014!",
        "02/10/14!", "40/15/2014", "40/15/14", "4/40/2014", "4/40/14", "4/150/2014", "4/150/14",
        "4/015/2014", "4/015/14", "4/15/214", "4/15/20014", "01-01-2015", "01-01-15", "01 01 2015",
        "01 01 15", "01.01.2015", "01.01.15",
    };

    //Check21,204,128,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.13,0,No,No,Yes,Yes,02//2014,NULL,0,RAND,NULL,
    //Check21,204,165,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.13,0,Yes,No,Yes,Yes,02//2014,NULL,0,RAND,NULL,
    //Check21,204,202,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.13,0,,No,Yes,Yes,02//2014,NULL,0,RAND,NULL,
    //Check21,204,129,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.14,0,No,No,Yes,Yes,02//14,NULL,0,RAND,NULL,
    //Check21,204,166,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.14,0,Yes,No,Yes,Yes,02//14,NULL,0,RAND,NULL,
    //Check21,204,203,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.14,0,,No,Yes,Yes,02//14,NULL,0,RAND,NULL,
    //Check21,204,130,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.15,0,No,No,Yes,Yes,/10/2014,NULL,0,RAND,NULL,
    //Check21,204,167,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.15,0,Yes,No,Yes,Yes,/10/2014,NULL,0,RAND,NULL,
    //Check21,204,204,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.15,0,,No,Yes,Yes,/10/2014,NULL,0,RAND,NULL,
    //Check21,204,131,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.16,0,No,No,Yes,Yes,/10/14,NULL,0,RAND,NULL,
    //Check21,204,168,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.16,0,Yes,No,Yes,Yes,/10/14,NULL,0,RAND,NULL,
    //Check21,204,205,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.16,0,,No,Yes,Yes,/10/14,NULL,0,RAND,NULL,
    //Check21,204,132,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.17,0,No,No,Yes,Yes,1/1/,NULL,0,RAND,NULL,
    //Check21,204,169,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.17,0,Yes,No,Yes,Yes,1/1/,NULL,0,RAND,NULL,
    //Check21,204,206,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.17,0,,No,Yes,Yes,1/1/,NULL,0,RAND,NULL,
    //Check21,204,133,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.18,0,No,No,Yes,Yes,02/10/,NULL,0,RAND,NULL,
    //Check21,204,170,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.18,0,Yes,No,Yes,Yes,02/10/,NULL,0,RAND,NULL,
    //Check21,204,207,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.18,0,,No,Yes,Yes,02/10/,NULL,0,RAND,NULL,
    //Check21,204,134,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.19,0,No,No,Yes,Yes,02/10/14,NULL,0,RAND,NULL,
    //Check21,204,171,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.19,0,Yes,No,Yes,Yes,02/10/14,NULL,0,RAND,NULL,
    //Check21,204,208,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.19,0,,No,Yes,Yes,02/10/14,NULL,0,RAND,NULL,
    //Check21,204,135,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.2,0,No,No,Yes,Yes,02/31/2014,NULL,0,RAND,NULL,
    //Check21,204,172,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.2,0,Yes,No,Yes,Yes,02/31/2014,NULL,0,RAND,NULL,
    //Check21,204,209,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.2,0,,No,Yes,Yes,02/31/2014,NULL,0,RAND,NULL,
    //Check21,204,136,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.21,0,No,No,Yes,Yes,02/31/14,NULL,0,RAND,NULL,
    //Check21,204,173,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.21,0,Yes,No,Yes,Yes,02/31/14,NULL,0,RAND,NULL,
    //Check21,204,210,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.21,0,,No,Yes,Yes,02/31/14,NULL,0,RAND,NULL,
    //Check21,204,137,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.22,0,No,No,Yes,Yes,02a/10/2014,NULL,0,RAND,NULL,
    //Check21,204,174,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.22,0,Yes,No,Yes,Yes,02a/10/2014,NULL,0,RAND,NULL,
    //Check21,204,211,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.22,0,,No,Yes,Yes,02a/10/2014,NULL,0,RAND,NULL,
    //Check21,204,138,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.23,0,No,No,Yes,Yes,02a/10/14,NULL,0,RAND,NULL,
    //Check21,204,175,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.23,0,Yes,No,Yes,Yes,02a/10/14,NULL,0,RAND,NULL,
    //Check21,204,212,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.23,0,,No,Yes,Yes,02a/10/14,NULL,0,RAND,NULL,
    //Check21,204,139,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.24,0,No,No,Yes,Yes,02/10a/2014,NULL,0,RAND,NULL,
    //Check21,204,176,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.24,0,Yes,No,Yes,Yes,02/10a/2014,NULL,0,RAND,NULL,
    /// should have been another one here for 02/10a/2014 with the empty incurFee value, but it was missing from the original csv
    //Check21,204,140,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.25,0,No,No,Yes,Yes,02/10a/14,NULL,0,RAND,NULL,
    //Check21,204,177,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.25,0,Yes,No,Yes,Yes,02/10a/14,NULL,0,RAND,NULL,
    //Check21,204,213,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.25,0,,No,Yes,Yes,02/10a/14,NULL,0,RAND,NULL,
    //Check21,204,141,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.26,0,No,No,Yes,Yes,02/10/2014a,NULL,0,RAND,NULL,
    //Check21,204,178,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.26,0,Yes,No,Yes,Yes,02/10/2014a,NULL,0,RAND,NULL,
    //Check21,204,214,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.26,0,,No,Yes,Yes,02/10/2014a,NULL,0,RAND,NULL,
    //Check21,204,142,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.27,0,No,No,Yes,Yes,02/10/14a,NULL,0,RAND,NULL,
    //Check21,204,179,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.27,0,Yes,No,Yes,Yes,02/10/14a,NULL,0,RAND,NULL,
    //Check21,204,215,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.27,0,,No,Yes,Yes,02/10/14a,NULL,0,RAND,NULL,
    //Check21,204,143,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.28,0,No,No,Yes,Yes,02!/10/2014,NULL,0,RAND,NULL,
    //Check21,204,180,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.28,0,Yes,No,Yes,Yes,02!/10/2014,NULL,0,RAND,NULL,
    //Check21,204,216,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.28,0,,No,Yes,Yes,02!/10/2014,NULL,0,RAND,NULL,
    //Check21,204,144,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.29,0,No,No,Yes,Yes,02!/10/14,NULL,0,RAND,NULL,
    //Check21,204,181,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.29,0,Yes,No,Yes,Yes,02!/10/14,NULL,0,RAND,NULL,
    //Check21,204,217,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.29,0,,No,Yes,Yes,02!/10/14,NULL,0,RAND,NULL,
    //Check21,204,145,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.3,0,No,No,Yes,Yes,02/10!/2014,NULL,0,RAND,NULL,
    //Check21,204,182,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.3,0,Yes,No,Yes,Yes,02/10!/2014,NULL,0,RAND,NULL,
    //Check21,204,218,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.3,0,,No,Yes,Yes,02/10!/2014,NULL,0,RAND,NULL,
    //Check21,204,146,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.31,0,No,No,Yes,Yes,02/10!/14,NULL,0,RAND,NULL,
    //Check21,204,183,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.31,0,Yes,No,Yes,Yes,02/10!/14,NULL,0,RAND,NULL,
    //Check21,204,219,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.31,0,,No,Yes,Yes,02/10!/14,NULL,0,RAND,NULL,
    //Check21,204,147,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.32,0,No,No,Yes,Yes,02/10/2014!,NULL,0,RAND,NULL,
    //Check21,204,184,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.32,0,Yes,No,Yes,Yes,02/10/2014!,NULL,0,RAND,NULL,
    //Check21,204,220,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.32,0,,No,Yes,Yes,02/10/2014!,NULL,0,RAND,NULL,
    //Check21,204,148,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.33,0,No,No,Yes,Yes,02/10/14!,NULL,0,RAND,NULL,
    //Check21,204,185,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.33,0,Yes,No,Yes,Yes,02/10/14!,NULL,0,RAND,NULL,
    //Check21,204,221,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.33,0,,No,Yes,Yes,02/10/14!,NULL,0,RAND,NULL,
    //Check21,204,149,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.34,0,No,No,Yes,Yes,40/15/2014,NULL,0,RAND,NULL,
    //Check21,204,186,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.34,0,Yes,No,Yes,Yes,40/15/2014,NULL,0,RAND,NULL,
    //Check21,204,222,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.34,0,,No,Yes,Yes,40/15/2014,NULL,0,RAND,NULL,
    //Check21,204,150,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.35,0,No,No,Yes,Yes,40/15/14,NULL,0,RAND,NULL,
    //Check21,204,187,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.35,0,Yes,No,Yes,Yes,40/15/14,NULL,0,RAND,NULL,
    //Check21,204,223,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.35,0,,No,Yes,Yes,40/15/14,NULL,0,RAND,NULL,
    //Check21,204,151,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.36,0,No,No,Yes,Yes,4/40/2014,NULL,0,RAND,NULL,
    //Check21,204,188,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.36,0,Yes,No,Yes,Yes,4/40/2014,NULL,0,RAND,NULL,
    //Check21,204,224,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.36,0,,No,Yes,Yes,4/40/2014,NULL,0,RAND,NULL,
    //Check21,204,152,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.37,0,No,No,Yes,Yes,4/40/14,NULL,0,RAND,NULL,
    //Check21,204,189,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.37,0,Yes,No,Yes,Yes,4/40/14,NULL,0,RAND,NULL,
    //Check21,204,225,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.37,0,,No,Yes,Yes,4/40/14,NULL,0,RAND,NULL,
    //Check21,204,153,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.38,0,No,No,Yes,Yes,4/150/2014,NULL,0,RAND,NULL,
    //Check21,204,190,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.38,0,Yes,No,Yes,Yes,4/150/2014,NULL,0,RAND,NULL,
    //Check21,204,226,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.38,0,,No,Yes,Yes,4/150/2014,NULL,0,RAND,NULL,
    //Check21,204,154,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.39,0,No,No,Yes,Yes,4/150/14,NULL,0,RAND,NULL,
    //Check21,204,191,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.39,0,Yes,No,Yes,Yes,4/150/14,NULL,0,RAND,NULL,
    //Check21,204,227,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.39,0,,No,Yes,Yes,4/150/14,NULL,0,RAND,NULL,
    //Check21,204,155,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.4,0,No,No,Yes,Yes,4/015/2014,NULL,0,RAND,NULL,
    //Check21,204,192,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.4,0,Yes,No,Yes,Yes,4/015/2014,NULL,0,RAND,NULL,
    //Check21,204,228,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.4,0,,No,Yes,Yes,4/015/2014,NULL,0,RAND,NULL,
    //Check21,204,156,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.41,0,No,No,Yes,Yes,4/015/14,NULL,0,RAND,NULL,
    //Check21,204,193,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.41,0,Yes,No,Yes,Yes,4/015/14,NULL,0,RAND,NULL,
    //Check21,204,229,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.41,0,,No,Yes,Yes,4/015/14,NULL,0,RAND,NULL,
    //Check21,204,157,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.42,0,No,No,Yes,Yes,4/15/214,NULL,0,RAND,NULL,
    //Check21,204,194,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.42,0,Yes,No,Yes,Yes,4/15/214,NULL,0,RAND,NULL,
    //Check21,204,230,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.42,0,,No,Yes,Yes,4/15/214,NULL,0,RAND,NULL,
    //Check21,204,158,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.43,0,No,No,Yes,Yes,4/15/20014,NULL,0,RAND,NULL,
    //Check21,204,195,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.43,0,Yes,No,Yes,Yes,4/15/20014,NULL,0,RAND,NULL,
    //Check21,204,231,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.43,0,,No,Yes,Yes,4/15/20014,NULL,0,RAND,NULL,
    //Check21,204,159,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.44,0,No,No,Yes,Yes,01-01-2015,NULL,0,RAND,NULL,
    //Check21,204,196,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.44,0,Yes,No,Yes,Yes,01-01-2015,NULL,0,RAND,NULL,
    //Check21,204,232,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.44,0,,No,Yes,Yes,01-01-2015,NULL,0,RAND,NULL,
    //Check21,204,160,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.45,0,No,No,Yes,Yes,01-01-15,NULL,0,RAND,NULL,
    //Check21,204,197,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.45,0,Yes,No,Yes,Yes,01-01-15,NULL,0,RAND,NULL,
    //Check21,204,233,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.45,0,,No,Yes,Yes,01-01-15,NULL,0,RAND,NULL,
    //Check21,204,161,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.46,0,No,No,Yes,Yes,01 01 2015,NULL,0,RAND,NULL,
    //Check21,204,198,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.46,0,Yes,No,Yes,Yes,01 01 2015,NULL,0,RAND,NULL,
    //Check21,204,234,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.46,0,,No,Yes,Yes,01 01 2015,NULL,0,RAND,NULL,
    //Check21,204,162,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.47,0,No,No,Yes,Yes,01 01 15,NULL,0,RAND,NULL,
    //Check21,204,199,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.47,0,Yes,No,Yes,Yes,01 01 15,NULL,0,RAND,NULL,
    //Check21,204,235,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.47,0,,No,Yes,Yes,01 01 15,NULL,0,RAND,NULL,
    //Check21,204,163,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.48,0,No,No,Yes,Yes,01.01.2015,NULL,0,RAND,NULL,
    //Check21,204,200,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.48,0,Yes,No,Yes,Yes,01.01.2015,NULL,0,RAND,NULL,
    //Check21,204,236,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.48,0,,No,Yes,Yes,01.01.2015,NULL,0,RAND,NULL,
    //Check21,204,164,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.49,0,No,No,Yes,Yes,01.01.15,NULL,0,RAND,NULL,
    //Check21,204,201,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.49,0,Yes,No,Yes,Yes,01.01.15,NULL,0,RAND,NULL,
    //Check21,204,237,GAPITesterACH,12035,K!a@t#h$r%y^n&,B*r(o)wn,Checking,Kathryn Brown,490000018,614800811,2.49,0,,No,Yes,Yes,01.01.15,NULL,0,RAND,NULL,
    for (String incurFee : validIncurFee) {
      for (String checkDate : invalidCheckDates) {
        testCases.add(
            AchPayment.createValidCheck21(
                dataHelper.getReferenceId(),
                "Test Case 128 : 237 - Invalid CheckDate " + checkDate + " - IncurFee " + incurFee,
                getExpectedResponse(gatewayErrors, "204"),
                payeeId)
                .setCheckDate(checkDate)
                .setIncurFee(incurFee)
        );
      }
    }

    executeTests(testCases);
  }

  @Test(groups = {"gapi", "ACHPayment"})
  public void achPaymentCad() {
    Logger.info("ACHPayment Basic GapiRequest Validation");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc5");
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

    //ACHPayment,2,2,GAPITesterACH1,NULL,12035,Foxy,Shuttle,Savings,Foxy Shuttle,490000018,9857881896,1752.36,2.95,No,No,NULL,NULL
    testCases.add(
        new AchPayment(
            dataHelper.getReferenceId(),
            "Test Case 2 - Basic Payment",
            getExpectedResponse(gatewayErrors, "2"),
            payeeId)
            .setPayerReferenceId("GAPITesterACH1")
            .setPayerFirstName("Foxy")
            .setPayerLastName("Shuttle")
            .setAccountType("Savings")
            .setAccountFullName("Foxy Shuttle")
            .setRoutingNumber("011000015")
            .setAccountNumber("9857881896")
            .setTotalAmount("1752.36")
            .setFeeAmount("2.95")
            .setIncurFee("No")
            .setSaveAccount("No")
            .setCurrencyCode("CAD")
    );
    executeTests(testCases);
  }

  @Test(groups = {"gapi", "ACHPayment"})
  public void achPaymentCAD2() {
    Logger.info("ACH Payment Test for CAD - GapiResponse code 2");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc5");
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String payeeId = testSetupPage.getString("payeeId");
    final String amount = setAmount();
    final String amount1 = setAmount();
    final String amount2 = setAmount();
    final String amount3 = setAmount();
    final String feeAmount = setFeeAmount();
    String[] allAmounts = new String[]{amount, amount1, amount2, amount3};
    final String totalAmount = totalAmount(allAmounts);
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    DataHelper dataHelper = new DataHelper();

    TestCaseCollection testCases = new GapiTestCaseCollection(
        new Credentials(gatewayId, username, password)
    );

    //ACHPayment,2,2,GAPITesterACH,NULL,12035,Foxy,Shuttle,Savings,Foxy Shuttle,490000018,9857881896,160.00,NULL,No,No,NULL,NULL
    testCases
        .add(
            AchPayment.createValidAchPayment(
                dataHelper.getReferenceId(),
                "Test Case 1 - Empty fee amount and incur fee NO",
                getExpectedResponse(gatewayErrors, "2"),
                payeeId)
                .setRoutingNumber("011000015")
                .setTotalAmount(totalAmount)
                .setFeeAmount("")
                .setIncurFee("No")
                .setCurrencyCode("CAD")
        );

    //ACHPayment,2,2,GAPITesterACH,NULL,12035,Foxy,Shuttle,Savings,Foxy Shuttle,490000018,9857881896,160.00,NULL,No,No,NULL,NULL
    testCases
        .add(
            AchPayment.createValidAchPayment(
                dataHelper.getReferenceId(),
                "Test Case 2 - Empty fee amount, Incur fee yes",
                getExpectedResponse(gatewayErrors, "2"),
                payeeId)
                .setRoutingNumber("011000015")
                .setTotalAmount(totalAmount)
                .setFeeAmount("")
                .setIncurFee("Yes")
                .setCurrencyCode("CAD")
        );

    //ACHPayment,2,2,GAPITesterACH,NULL,12035,Foxy,Shuttle,Savings,Foxy Shuttle,490000018,9857881896,160.00,20.00,No,No,NULL,NULL
    testCases
        .add(
            AchPayment.createValidAchPayment(
                dataHelper.getReferenceId(),
                "Test Case 3 -  Fee amount set, Incur fee NO",
                getExpectedResponse(gatewayErrors, "2"),
                payeeId)
                .setRoutingNumber("011000015")
                .setFeeAmount("0.00")
                .setTotalAmount(
                    Integer.toString(Integer.parseInt(totalAmount) + Integer.parseInt("0")))
                .setIncurFee("No")
                .setCurrencyCode("CAD")
        );

    //ACHPayment,2,2,GAPITesterACH,NULL,12035,Foxy,Shuttle,Savings,Foxy Shuttle,490000018,9857881896,160.00,NULL,No,No,NULL,NULL
    testCases
        .add(
            AchPayment.createValidAchPayment(
                dataHelper.getReferenceId(),
                "Test Case 4 - Fee amount set and Incur fee yes",
                getExpectedResponse(gatewayErrors, "2"),
                payeeId)
                .setRoutingNumber("011000015")
                .setFeeAmount("0.00")
                .setTotalAmount(totalAmount)
                .setMessage("Test Message")
                .setIncurFee("Yes")
                .setCurrencyCode("CAD")
        );

    //ACHPayment,2,2,GAPITesterACH,NULL,12035,Foxy,Shuttle,Savings,Foxy Shuttle,490000018,9857881896,160.00,20.00,No,No,NULL,NULL
    testCases
        .add(
            AchPayment.createValidAchPayment(
                dataHelper.getReferenceId(),
                "Test Case 5 - Fee amount set ,Incur fee empty",
                getExpectedResponse(gatewayErrors, "2"),
                payeeId)
                .setRoutingNumber("011000015")
                .setFeeAmount(feeAmount)
                .setTotalAmount(
                    Integer.toString(Integer.parseInt(totalAmount) + Integer.parseInt(feeAmount)))
                .setMessage("Test Message")
                .setIncurFee("")
                .setCurrencyCode("CAD")
        );

//ACHPayment,2,2,GAPITesterACH,NULL,12035,Foxy,Shuttle,Savings,Foxy Shuttle,490000018,9857881896,160.00,NULL,No,No,NULL,NULL
    testCases
        .add(
            AchPayment.createValidAchPayment(
                dataHelper.getReferenceId(),
                "Test Case 6 - Incur fee empty and Fee amount empty",
                getExpectedResponse(gatewayErrors, "2"),
                payeeId)
                .setRoutingNumber("011000015")
                .setTotalAmount(totalAmount)
                .setFeeAmount("")
                .setIncurFee("")
                .setCurrencyCode("CAD")
        );
    executeTests(testCases);
  }


  @Test(groups = {"gapi", "ACHPayment"})
  public void achSplitPaymentDifferentPropertyUsd() {
    Logger.info("ACH Payment Test for split deposits going to different properties");

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

    testCases.add(AchPayment.createValidAchPayment(
        dataHelper.getReferenceId(),
        "Test Case - ACH payment",
        getExpectedResponse(gatewayErrors, "2"),
        payeeId)
        .setRoutingNumber("011000015")
        .setFeeAmount("0.00")
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

  @Test(groups = {"gapi", "ACHPayment"})
  public void achSplitPaymentSamePropertyUsd() {
    Logger.info("ACH Payment Test for split deposits going to same property");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc7");
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

    testCases.add(AchPayment.createValidAchPayment(
        dataHelper.getReferenceId(),
        "Test Case - ACH payment",
        getExpectedResponse(gatewayErrors, "2"),
        payeeId)
        .setFeeAmount("0.00")
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

  @Test(groups = {"gapi", "ACHPayment"})
  public void achPaymentSplitDeposit2() {
    Logger.info("ACH Payment Test for split deposit fees - GapiResponse code 2");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc7");
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String payeeId = testSetupPage.getString("payeeId");
    final String payeeId1 = testSetupPage.getString("payeeId1");
    final String payeeId2 = testSetupPage.getString("payeeId2");
    final String payeeId3 = testSetupPage.getString("payeeId3");
    final String amount = setAmount();
    final String amount1 = setAmount();
    final String amount2 = setAmount();
    final String amount3 = setAmount();
    final String feeAmount = setFeeAmount();
    String[] allAmounts = new String[]{amount, amount1, amount2, amount3};
    final String totalAmount = totalAmount(allAmounts);
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    DataHelper dataHelper = new DataHelper();

    TestCaseCollection testCases = new GapiTestCaseCollection(
        new Credentials(gatewayId, username, password)
    );

    //ACHPayment,2,2,GAPITesterACH,NULL,12035,Foxy,Shuttle,Savings,Foxy Shuttle,490000018,9857881896,160.00,NULL,No,No,NULL,NULL
    testCases
        .add(
            AchPayment.createValidAchPayment(
                dataHelper.getReferenceId(),
                "Test Case 1 - Empty fee amount and incur fee NO",
                getExpectedResponse(gatewayErrors, "2"),
                payeeId)
                .addDepositItem(payeeId,
                    amount)
                .addDepositItem(payeeId1,
                    amount1)
                .addDepositItem(payeeId2,
                    amount2)
                .addDepositItem(payeeId3,
                    amount3)
                .setTotalAmount(totalAmount)
                .setFeeAmount("")
                .setIncurFee("No")
                .setCurrencyCode("USD")
        );

    //ACHPayment,2,2,GAPITesterACH,NULL,12035,Foxy,Shuttle,Savings,Foxy Shuttle,490000018,9857881896,160.00,NULL,No,No,NULL,NULL
    testCases
        .add(
            AchPayment.createValidAchPayment(
                dataHelper.getReferenceId(),
                "Test Case 2 - Empty fee amount, Incur fee yes",
                getExpectedResponse(gatewayErrors, "2"),
                payeeId)
                .addDepositItem(payeeId,
                    amount)
                .addDepositItem(payeeId1,
                    amount1)
                .addDepositItem(payeeId2,
                    amount2)
                .addDepositItem(payeeId3,
                    amount3)
                .setTotalAmount(totalAmount)
                .setFeeAmount("")
                .setIncurFee("Yes")
                .setCurrencyCode("USD")
        );

    //ACHPayment,2,2,GAPITesterACH,NULL,12035,Foxy,Shuttle,Savings,Foxy Shuttle,490000018,9857881896,160.00,20.00,No,No,NULL,NULL
    testCases
        .add(
            AchPayment.createValidAchPayment(
                dataHelper.getReferenceId(),
                "Test Case 3 -  Fee amount set, Incur fee NO",
                getExpectedResponse(gatewayErrors, "2"),
                payeeId)
                .addDepositItem(payeeId,
                    amount)
                .addDepositItem(payeeId1,
                    amount1)
                .addDepositItem(payeeId2,
                    amount2)
                .addDepositItem(payeeId3,
                    amount3)
                .setFeeAmount("0.00")
                .setTotalAmount(
                    Integer.toString(Integer.parseInt(totalAmount) + Integer.parseInt("0")))
                .setIncurFee("No")
                .setCurrencyCode("USD")
        );

    //ACHPayment,2,2,GAPITesterACH,NULL,12035,Foxy,Shuttle,Savings,Foxy Shuttle,490000018,9857881896,160.00,NULL,No,No,NULL,NULL
    testCases
        .add(
            AchPayment.createValidAchPayment(
                dataHelper.getReferenceId(),
                "Test Case 4 - Fee amount set and Incur fee yes",
                getExpectedResponse(gatewayErrors, "2"),
                payeeId)
                .addDepositItem(payeeId,
                    amount)
                .addDepositItem(payeeId1,
                    amount1)
                .addDepositItem(payeeId2,
                    amount2)
                .addDepositItem(payeeId3,
                    amount3)
                .setFeeAmount("0.00")
                .setTotalAmount(totalAmount)
                .setMessage("Test Message")
                .setIncurFee("Yes")
                .setCurrencyCode("USD")
        );

    //ACHPayment,2,2,GAPITesterACH,NULL,12035,Foxy,Shuttle,Savings,Foxy Shuttle,490000018,9857881896,160.00,20.00,No,No,NULL,NULL
    testCases
        .add(
            AchPayment.createValidAchPayment(
                dataHelper.getReferenceId(),
                "Test Case 5 - Fee amount set ,Incur fee empty",
                getExpectedResponse(gatewayErrors, "2"),
                payeeId)
                .addDepositItem(payeeId,
                    amount)
                .addDepositItem(payeeId1,
                    amount1)
                .addDepositItem(payeeId2,
                    amount2)
                .addDepositItem(payeeId3,
                    amount3)
                .setFeeAmount(feeAmount)
                .setTotalAmount(
                    Integer.toString(Integer.parseInt(totalAmount) + Integer.parseInt(feeAmount)))
                .setMessage("Test Message")
                .setIncurFee("")
                .setCurrencyCode("USD")
        );

//ACHPayment,2,2,GAPITesterACH,NULL,12035,Foxy,Shuttle,Savings,Foxy Shuttle,490000018,9857881896,160.00,NULL,No,No,NULL,NULL
    testCases
        .add(
            AchPayment.createValidAchPayment(
                dataHelper.getReferenceId(),
                "Test Case 6 - Incur fee empty and Fee amount empty",
                getExpectedResponse(gatewayErrors, "2"),
                payeeId)
                .addDepositItem(payeeId,
                    amount)
                .addDepositItem(payeeId1,
                    amount1)
                .addDepositItem(payeeId2,
                    amount2)
                .addDepositItem(payeeId3,
                    amount3)
                .setTotalAmount(totalAmount)
                .setFeeAmount("")
                .setIncurFee("")
                .setCurrencyCode("USD")
        );
    executeTests(testCases);
  }
}