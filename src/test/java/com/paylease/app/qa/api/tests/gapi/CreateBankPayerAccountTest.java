package com.paylease.app.qa.api.tests.gapi;

import com.paylease.app.qa.api.tests.TestCaseCollection;
import com.paylease.app.qa.api.tests.gapi.testcase.CreateBankPayerAccount;
import com.paylease.app.qa.framework.DataHelper;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.api.Credentials;
import com.paylease.app.qa.framework.api.Response;
import com.paylease.app.qa.framework.api.gapi.GapiRequest;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import java.util.HashMap;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CreateBankPayerAccountTest extends BaseTest {

  private static final String REGION = "gapi";
  private static final String FEATURE = "CreateBankPayerAccount";

  //-----------------------------------TEST----------------------------------------------

  @Test(groups = {"gapi", "CreateBankPayerAccount"})
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
    final String accountType = testSetupPage.getString("accountType");
    final String routingNumber = testSetupPage.getString("routingNumber");
    final String accountNumber = testSetupPage.getString("accountNumber");

    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    Credentials credentials = new Credentials(gatewayId, username, password);
    GapiRequest gapiRequest = new GapiRequest(credentials);
    DataHelper dataHelper = new DataHelper();

    CreateBankPayerAccount createBankPayerAccount = new CreateBankPayerAccount(
        dataHelper.getReferenceId(),
        "Test Case - Resident Record With no Gateway Payer Record",
        getExpectedResponse(gatewayErrors, "2")
    );

   createBankPayerAccount
        .setPayerFirstName(firstName)
        .setPayerLastName(lastName)
        .setPayerReferenceId(payerRefId)
        .setAccountType(accountType.equals("1") ? "Checking" : "Savings")
        .setAccountFullName(firstName.concat(" ").concat(lastName))
        .setRoutingNumber(routingNumber)
        .setAccountNumber(accountNumber);

    createBankPayerAccount.addTransaction(gapiRequest);

    Response response = gapiRequest.sendRequest();
    response.setIndex("1");

    String gatewayPayerId = response.getSpecificElementValue("GatewayPayerId");

    String userId = validateGapiResponse(gatewayPayerId);
    Assert.assertEquals(userId, createdResidentUserId, "Gateway Payer Record was not created using existing resident");
  }

  @Test(groups = {"gapi", "CreateBankPayerAccount"})
  public void basicRequestValidation() {

    Logger.info("CreateBankPayerAccount Basic GapiRequest Validation");

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

    String[] validAccountTypes = {
        "Checking", "Savings", "Business Checking",
    };

    for (String accountType : validAccountTypes) {
      //CreateBankPayerAccount,10,1,GAPITester1235a,GAPI,TESTER,Checking,GAPI TESTER,490000018,614800811
      //CreateBankPayerAccount,10,20,GAPITester1235a,GAPI,TESTER,Savings,GAPI TESTER,490000018,614800811
      //CreateBankPayerAccount,10,26,GAPITester1235a,GAPI,TESTER,Business Checking,GAPI TESTER,490000018,614800811
      testCases.add(
          new CreateBankPayerAccount(
              dataHelper.getReferenceId(),
              "Test Case 1/20/26 - Name in upper case",
              getExpectedResponse(gatewayErrors, "10"))
              .setPayerFirstName("GAPI")
              .setPayerLastName("TESTER")
              .setAccountType(accountType)
              .setAccountFullName("GAPI TESTER")
              .setRoutingNumber("490000018")
              .setAccountNumber("614800811")
      );

      //CreateBankPayerAccount,10,2,GAPITester1235a,K!a1@t#h$r4%y^n&,B4*r(o4)wn,Checking,K!a1@t#h$r4%y^n& B4*r(o4)wn,490000018,614800811
      //CreateBankPayerAccount,10,21,GAPITester1235a,K!a1@t#h$r4%y^n&,B4*r(o4)wn,Savings,K!a1@t#h$r4%y^n& B4*r(o4)wn,490000018,614800811
      //CreateBankPayerAccount,10,27,GAPITester1235a,K!a1@t#h$r4%y^n&,B4*r(o4)wn,Business Checking,K!a1@t#h$r4%y^n& B4*r(o4)wn,490000018,614800811
      testCases.add(
          new CreateBankPayerAccount(
              dataHelper.getReferenceId(),
              "Test Case 2/21/27 - Weird name",
              getExpectedResponse(gatewayErrors, "10"))
              .setPayerFirstName("K!a1@t#h$r4%y^n&")
              .setPayerLastName("B4*r(o4)wn")
              .setAccountType(accountType)
              .setAccountFullName("K!a1@t#h$r4%y^n& B4*r(o4)wn")
              .setRoutingNumber("490000018")
              .setAccountNumber("614800811")
      );

      //CreateBankPayerAccount,10,4,GAPITester1235a,N_-+=eil,Wil_-+=son,Checking,N_-+=eil Wil_-+=son,490000018,614800811
      //CreateBankPayerAccount,10,22,GAPITester1235a,N_-+=eil,Wil_-+=son,Savings,N_-+=eil Wil_-+=son,490000018,614800811
      //CreateBankPayerAccount,10,28,GAPITester1235a,N_-+=eil,Wil_-+=son,Business Checking,N_-+=eil Wil_-+=son,490000018,614800811
      testCases.add(
          new CreateBankPayerAccount(
              dataHelper.getReferenceId(),
              "Test Case 4/22/28 - Weird name",
              getExpectedResponse(gatewayErrors, "10"))
              .setPayerFirstName("N_-+=eil")
              .setPayerLastName("Wil_-+=son")
              .setAccountType(accountType)
              .setAccountFullName("N_-+=eil Wil_-+=son")
              .setRoutingNumber("490000018")
              .setAccountNumber("614800811")
      );

      //CreateBankPayerAccount,10,8,GAPITester1235a,Brian,Baker,Checking,JeffersonBarwickBarrickNorthst AlistaireBenjaminLafayetteTrya,490000018,614800811
      //CreateBankPayerAccount,10,23,GAPITester1235a,Brian,Baker,Savings,JeffersonBarwickBarrickNorthst AlistaireBenjaminLafayetteTrya,490000018,614800811
      //CreateBankPayerAccount,10,29,GAPITester1235a,Brian,Baker,Business Checking,JeffersonBarwickBarrickNorthst AlistaireBenjaminLafayetteTrya,490000018,614800811
      testCases.add(
          new CreateBankPayerAccount(
              dataHelper.getReferenceId(),
              "Test Case 8/23/29 - Different names",
              getExpectedResponse(gatewayErrors, "10"))
              .setPayerFirstName("Brian")
              .setPayerLastName("Baker")
              .setAccountType(accountType)
              .setAccountFullName("JeffersonBarwickBarrickNorthst AlistaireBenjaminLafayetteTrya")
              .setRoutingNumber("490000018")
              .setAccountNumber("614800811")
      );

      //CreateBankPayerAccount,10,11,GAPITester1235a,JeffersonBarwickBarrickNorthst,AlistaireBenjaminLafayetteTrya,Checking,JeffersonBarwickBarrickNorthst AlistaireBenjaminLafayetteTrya,490000018,614800811
      //CreateBankPayerAccount,10,24,GAPITester1235a,JeffersonBarwickBarrickNorthst,AlistaireBenjaminLafayetteTrya,Savings,JeffersonBarwickBarrickNorthst AlistaireBenjaminLafayetteTrya,490000018,614800811
      //CreateBankPayerAccount,10,30,GAPITester1235a,JeffersonBarwickBarrickNorthst,AlistaireBenjaminLafayetteTrya,Business Checking,JeffersonBarwickBarrickNorthst AlistaireBenjaminLafayetteTrya,490000018,614800811
      testCases.add(
          new CreateBankPayerAccount(
              dataHelper.getReferenceId(),
              "Test Case 11/24/30 - Weird name",
              getExpectedResponse(gatewayErrors, "10"))
              .setPayerFirstName("JeffersonBarwickBarrickNorthst")
              .setPayerLastName("AlistaireBenjaminLafayetteTrya")
              .setAccountType(accountType)
              .setAccountFullName("JeffersonBarwickBarrickNorthst AlistaireBenjaminLafayetteTrya")
              .setRoutingNumber("490000018")
              .setAccountNumber("614800811")
      );

      //CreateBankPayerAccount,10,19,GAPITester1235a,JeffersonBarwickBarrickNorthst,AlistaireBenjaminLafayetteTrya,Checking,Jefferson Barwick,490000018,614800811
      //CreateBankPayerAccount,10,25,GAPITester1235a,JeffersonBarwickBarrickNorthst,AlistaireBenjaminLafayetteTrya,Savings,Jefferson Barwick,490000018,614800811
      //CreateBankPayerAccount,10,31,GAPITester1235a,JeffersonBarwickBarrickNorthst,AlistaireBenjaminLafayetteTrya,Business Checking,Jefferson Barwick,490000018,614800811
      testCases.add(
          new CreateBankPayerAccount(
              dataHelper.getReferenceId(),
              "Test Case 19/25/31 - Weird name",
              getExpectedResponse(gatewayErrors, "10"))
              .setPayerFirstName("JeffersonBarwickBarrickNorthst")
              .setPayerLastName("AlistaireBenjaminLafayetteTrya")
              .setAccountType(accountType)
              .setAccountFullName("Jefferson Barwick")
              .setRoutingNumber("490000018")
              .setAccountNumber("614800811")
      );

      //CreateBankPayerAccount,69,58,,Jose,Edwards,Checking,Jose Edwards,490000018,1234
      //CreateBankPayerAccount,69,59,,JeffersonBarwickBarrickNorthst,AlistaireBenjaminLafayetteTrya,Savings,JeffersonBarwickBarrickNorthst AlistaireBenjaminLafayetteTrya,490000018,1234
      //CreateBankPayerAccount,69,60,,Lee,Frazier,Business Checking,Lee Frazier,490000018,1234
      testCases.add(
          CreateBankPayerAccount.createValid(
              "",
              "Test Case 58/59/60 - Payer Ref ID empty",
              getExpectedResponse(gatewayErrors, "69"))
              .setAccountType(accountType)
      );

      //CreateBankPayerAccount,71,61,GAPITester1235a,,Baker,Checking,Baker Brian,490000018,1234
      //CreateBankPayerAccount,71,62,GAPITester1235a,,Wil_-+=son,Savings,N_-+=eil Wil_-+=son,490000018,1234
      //CreateBankPayerAccount,71,63,GAPITester1235a,,Diaz,Business Checking,Robert Diaz,490000018,1234
      testCases.add(
          CreateBankPayerAccount.createValid(
              dataHelper.getReferenceId(),
              "Test Case 61/62/63 - Payer First Name empty",
              getExpectedResponse(gatewayErrors, "71"))
              .setPayerFirstName("")
              .setAccountType(accountType)
      );

      //CreateBankPayerAccount,72,64,GAPITester1235a,Robert,,Checking,Robert Diaz,490000018,1234
      //CreateBankPayerAccount,72,65,GAPITester1235a,JeffersonBarwickBarrickNorthst,,Savings,JeffersonBarwickBarrickNorthst AlistaireBenjaminLafayetteTrya,490000018,1234
      //CreateBankPayerAccount,72,66,GAPITester1235a,Bryon,,Business Checking,Bryon Hendrix,490000018,1234
      testCases.add(
          CreateBankPayerAccount.createValid(
              dataHelper.getReferenceId(),
              "Test Case 64/65/66 - Payer Last Name empty",
              getExpectedResponse(gatewayErrors, "72"))
              .setPayerLastName("")
              .setAccountType(accountType)
      );

      //CreateBankPayerAccount,88,68,GAPITester1235a,Mary,Powell,Checking,,490000018,1234
      //CreateBankPayerAccount,88,69,GAPITester1235a,Kurt,Myers,Savings,,490000018,1234
      //CreateBankPayerAccount,88,70,GAPITester1235a,JeffersonBarwickBarrickNorthst,AlistaireBenjaminLafayetteTrya,Business Checking,,490000018,1234
      testCases.add(
          CreateBankPayerAccount.createValid(
              dataHelper.getReferenceId(),
              "Test Case 68/69/70 - Empty account full name",
              getExpectedResponse(gatewayErrors, "88"))
              .setAccountFullName("")
              .setAccountType(accountType)
      );

      //CreateBankPayerAccount,89,71,GAPITester1235a,Jose,Edwards,Checking,Jose Edwards,,1234
      //CreateBankPayerAccount,89,72,GAPITester1235a,Amanda,Phillips,Savings,Ronald Regan,,1234
      //CreateBankPayerAccount,89,73,GAPITester1235a,K!a1@t#h$r4%y^n&,B4*r(o4)wn,Business Checking,K!a1@t#h$r4%y^n& B4*r(o4)wn,,1234
      testCases.add(
          CreateBankPayerAccount.createValid(
              dataHelper.getReferenceId(),
              "Test Case 71/72/73 - Empty routing number",
              getExpectedResponse(gatewayErrors, "89"))
              .setAccountType(accountType)
              .setRoutingNumber("")
      );

      //CreateBankPayerAccount,90,74,GAPITester1235a,Susan,Bell,Checking,Susan Bell,490000018,
      //CreateBankPayerAccount,90,75,GAPITester1235a,Tammy,Murphy,Savings,Tammy Murphy,490000018,
      //CreateBankPayerAccount,90,76,GAPITester1235a,JeffersonBarwickBarrickNorthst,AlistaireBenjaminLafayetteTrya,Business Checking,JeffersonBarwickBarrickNorthst AlistaireBenjaminLafayetteTrya,490000018,
      testCases.add(
          CreateBankPayerAccount.createValid(
              dataHelper.getReferenceId(),
              "Test Case 74/75/76 - Empty account number",
              getExpectedResponse(gatewayErrors, "90"))
              .setAccountType(accountType)
              .setAccountNumber("")
      );

      //CreateBankPayerAccount,111,78,a134oihqf9w7h2ufhqiusd872q45y92715yr7q8wgfasyfasf,Brian,Baker,Business Checking,Baker Brian,490000018,1234
      //CreateBankPayerAccount,111,79,a134oihqf9w7h2ufhqiusd872q45y92715yr7q8wgfasyfasf,Susan,Bell,Savings,Susan Bell,490000018,1234
      //CreateBankPayerAccount,111,80,a134oihqf9w7h2ufhqiusd872q45y92715yr7q8wgfasyfasf,Brian,Baker,Business Checking,Baker Brian,490000018,1234
      testCases.add(
          CreateBankPayerAccount.createValid(
              dataHelper.getReferenceId() + "a134oihqf9w7h2ufhqiusd872q45y92715yr7q8wgfasyfasf",
              "Test Case 78/79/80 - Payer ref id exceeds maximum length",
              getExpectedResponse(gatewayErrors, "111"))
              .setAccountType(accountType)
      );

      //CreateBankPayerAccount,112,81,GAPITester1235a,JeffersonBarwickBarrickNorthsta,Martin,Checking,Jonathan Martin,490000018,1234
      //CreateBankPayerAccount,112,82,GAPITester1235a,JeffersonBarwickBarrickNorthsta,Powell,Savings,Mary Powell,490000018,1234
      //CreateBankPayerAccount,112,83,GAPITester1235a,JeffersonBarwickBarrickNorthsta,AlistaireBenjaminLafayetteTrya,Business Checking,JeffersonBarwickBarrickNorthst AlistaireBenjaminLafayetteTrya,490000018,1234
      testCases.add(
          CreateBankPayerAccount.createValid(
              dataHelper.getReferenceId(),
              "Test Case 81/82/83 - Payer first name exceeds maximum length",
              getExpectedResponse(gatewayErrors, "112"))
              .setPayerFirstName("JeffersonBarwickBarrickNorthsta")
              .setAccountType(accountType)
      );

      //CreateBankPayerAccount,113,84,GAPITester1235a,JeffersonBarwickBarrickNorthst,AlistaireBenjaminLafayetteTryar,Checking,Jonathan Martin,490000018,1234
      //CreateBankPayerAccount,113,85,GAPITester1235a,JeffersonBarwickBarrickNorthst,AlistaireBenjaminLafayetteTryar,Savings,Mary Powell,490000018,1234
      //CreateBankPayerAccount,113,86,GAPITester1235a,JeffersonBarwickBarrickNorthst,AlistaireBenjaminLafayetteTryar,Business Checking,JeffersonBarwickBarrickNorthst AlistaireBenjaminLafayetteTrya,490000018,1234
      testCases.add(
          CreateBankPayerAccount.createValid(
              dataHelper.getReferenceId(),
              "Test Case 84/85/86 - Payer last name exceeds maximum length",
              getExpectedResponse(gatewayErrors, "113"))
              .setPayerLastName("AlistaireBenjaminLafayetteTryar")
              .setAccountType(accountType)
      );

      //CreateBankPayerAccount,114,87,GAPITester1235a,JeffersonBarwickBarrickNorthst,AlistaireBenjaminLafayetteTrya,Checking,JeffersonBarwickBarrickNorthstAlistaireBenjamina LafayetteTryaJeffersonBarwickBarrickNorthstAlistaire,490000018,1234
      //CreateBankPayerAccount,114,88,GAPITester1235a,JeffersonBarwickBarrickNorthst,AlistaireBenjaminLafayetteTrya,Savings,JeffersonBarwickBarrickNorthstAlistaireBenjamina LafayetteTryaJeffersonBarwickBarrickNorthstAlistaire,490000018,1234
      //CreateBankPayerAccount,114,89,GAPITester1235a,JeffersonBarwickBarrickNorthst,AlistaireBenjaminLafayetteTrya,Business Checking,JeffersonBarwickBarrickNorthstAlistaireBenjamina LafayetteTryaJeffersonBarwickBarrickNorthstAlistaire,490000018,1234
      testCases.add(
          CreateBankPayerAccount.createValid(
              dataHelper.getReferenceId(),
              "Test Case 87/88/89 - Account full name exceeds maximum length",
              getExpectedResponse(gatewayErrors, "114"))
              .setAccountFullName(
                  "JeffersonBarwickBarrickNorthstAlistaireBenjamina LafayetteTryaJeffersonBarwickBarrickNorthstAlistaire")
              .setAccountType(accountType)
      );

      //CreateBankPayerAccount,115,90,GAPITester1235a,Jose,Edwards,Checking,Jose Edwards,4900000181,614800811
      //CreateBankPayerAccount,115,91,GAPITester1235a,Amanda,Phillips,Savings,Ronald Regan,4900000181,614800811
      //CreateBankPayerAccount,115,92,GAPITester1235a,K!a1@t#h$r4%y^n&,B4*r(o4)wn,Business Checking,K!a1@t#h$r4%y^n& B4*r(o4)wn,4900000181,614800811
      testCases.add(
          CreateBankPayerAccount.createValid(
              dataHelper.getReferenceId(),
              "Test Case 90/91/92 - Routing number exceeds maximum length",
              getExpectedResponse(gatewayErrors, "115"))
              .setAccountType(accountType)
              .setRoutingNumber("4900000181")
      );

      //CreateBankPayerAccount,131,93,GAPITester1235a,Jose,Edwards,Checking,Jose Edwards,49000001a,614800811
      //CreateBankPayerAccount,131,95,GAPITester1235a,Amanda,Phillips,Savings,Ronald Regan,49000001a,614800811
      //CreateBankPayerAccount,131,97,GAPITester1235a,K!a1@t#h$r4%y^n&,B4*r(o4)wn,Business Checking,K!a1@t#h$r4%y^n& B4*r(o4)wn,49000001a,1234
      testCases.add(
          CreateBankPayerAccount.createValid(
              dataHelper.getReferenceId(),
              "Test Case 93/95/97 - Routing number is in invalid format",
              getExpectedResponse(gatewayErrors, "131"))
              .setAccountType(accountType)
              .setRoutingNumber("49000001a")
      );

      //CreateBankPayerAccount,131,94,GAPITester1235a,Jose,Edwards,Checking,Jose Edwards,4900 0001,614800811
      //CreateBankPayerAccount,131,96,GAPITester1235a,Amanda,Phillips,Savings,Ronald Regan,4900 0001,614800811
      //CreateBankPayerAccount,131,98,GAPITester1235a,K!a1@t#h$r4%y^n&,B4*r(o4)wn,Business Checking,K!a1@t#h$r4%y^n& B4*r(o4)wn,4900 0001,1234
      testCases.add(
          CreateBankPayerAccount.createValid(
              dataHelper.getReferenceId(),
              "Test Case 94/96/98 - Routing number is in invalid format",
              getExpectedResponse(gatewayErrors, "131"))
              .setAccountType(accountType)
              .setRoutingNumber("4900 0001")
      );

      //CreateBankPayerAccount,132,99,GAPITester1235a,Susan,Bell,Checking,Susan Bell,490000018,614800811dfwq
      //CreateBankPayerAccount,132,101,GAPITester1235a,Tammy,Murphy,Savings,Tammy Murphy,490000018,614800811dfwq
      //CreateBankPayerAccount,132,103,GAPITester1235a,JeffersonBarwickBarrickNorthst,AlistaireBenjaminLafayetteTrya,Business Checking,JeffersonBarwickBarrickNorthst AlistaireBenjaminLafayetteTrya,490000018,614800811dfwq
      testCases.add(
          CreateBankPayerAccount.createValid(
              dataHelper.getReferenceId(),
              "Test Case 99/101/103 - Account number is in invalid format",
              getExpectedResponse(gatewayErrors, "132"))
              .setAccountType(accountType)
              .setAccountNumber("614800811dfwq")
      );

      //CreateBankPayerAccount,132,100,GAPITester1235a,Susan,Bell,Checking,Susan Bell,490000018,6148 00811
      //CreateBankPayerAccount,132,102,GAPITester1235a,Tammy,Murphy,Savings,Tammy Murphy,490000018,6148 00811
      //CreateBankPayerAccount,132,104,GAPITester1235a,JeffersonBarwickBarrickNorthst,AlistaireBenjaminLafayetteTrya,Business Checking,JeffersonBarwickBarrickNorthst AlistaireBenjaminLafayetteTrya,490000018,6148 00811
      testCases.add(
          CreateBankPayerAccount.createValid(
              dataHelper.getReferenceId(),
              "Test Case 100/102/104 - Account number is in invalid format",
              getExpectedResponse(gatewayErrors, "132"))
              .setAccountType(accountType)
              .setAccountNumber("6148 00811")
      );

      //CreateBankPayerAccount,151,105,GAPITester1235a,Jose,Edwards,Checking,Jose Edwards,49000001,614800811
      //CreateBankPayerAccount,151,106,GAPITester1235a,Amanda,Phillips,Savings,Ronald Regan,49000001,614800811
      //CreateBankPayerAccount,151,107,GAPITester1235a,K!a1@t#h$r4%y^n&,B4*r(o4)wn,Business Checking,K!a1@t#h$r4%y^n& B4*r(o4)wn,49000001,614800811
      testCases.add(
          CreateBankPayerAccount.createValid(
              dataHelper.getReferenceId(),
              "Test Case 105/106/107 - Routing number not found",
              getExpectedResponse(gatewayErrors, "151"))
              .setAccountType(accountType)
              .setRoutingNumber("49000001")
      );
    }

    //CreateBankPayerAccount,87,67,GAPITester1235a,Brian,Baker,,Baker Brian,490000018,1234
    testCases.add(
        CreateBankPayerAccount.createValid(
            dataHelper.getReferenceId(),
            "Test Case 67 - Empty account type",
            getExpectedResponse(gatewayErrors, "87"))
            .setAccountType("")
    );

    //CreateBankPayerAccount,103,77,GAPITester1235a,TestFirstName,TestLastName,TestAccountType,TestFirstName TestLastName,490000018,1234
    testCases.add(
        CreateBankPayerAccount.createValid(
            dataHelper.getReferenceId(),
            "Test Case 77 - Invalid account type",
            getExpectedResponse(gatewayErrors, "103"))
            .setAccountType("TestAccountType")
    );

    executeTests(testCases);
  }
}