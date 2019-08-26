package com.paylease.app.qa.api.tests.gapi;

import com.paylease.app.qa.api.tests.TestCaseCollection;
import com.paylease.app.qa.api.tests.gapi.testcase.CreateBankPayeeAccount;
import com.paylease.app.qa.framework.DataHelper;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.api.Credentials;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import java.util.HashMap;
import java.util.List;
import org.testng.annotations.Test;


public class CreateBankPayeeAccountTest extends BaseTest {

  private static final String REGION = "gapi";
  private static final String FEATURE = "createBankPayeeAccount";

  //-----------------------------------TESTS----------------------------------------------

  @Test(groups = {"gapi", "CreateBankPayeeAccount"})
  public void payeeNonProfitstars() {
    Logger.info("CreateBankPayeeAccount with non profitstars paydirect");

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
      //CreateBankPayeeAccount,12,1,GAPITester1235a1,K!a1@t#h$r4%y^n&,B4*r(o4)wn,Checking,K!a1@t#h$r4%y^n& B4*r(o4)wn,490000018,614800811,,no
      //CreateBankPayeeAccount,12,8,GAPITester1235a8,K!a1@t#h$r4%y^n&,B4*r(o4)wn,Savings,K!a1@t#h$r4%y^n& B4*r(o4)wn,490000018,614800811,,no
      //CreateBankPayeeAccount,12,15,GAPITester1235a15,K!a1@t#h$r4%y^n&,B4*r(o4)wn,Business Checking,K!a1@t#h$r4%y^n& B4*r(o4)wn,490000018,614800811,,no
      testCases.add(
          new CreateBankPayeeAccount(
              dataHelper.getReferenceId(),
              "Test Case 1/8/15 - Valid - Weird names - accountType - " + accountType,
              getExpectedResponse(gatewayErrors, "12"))
              .setPayeeFirstName("K!a1@t#h$r4%y^n&")
              .setPayeeLastName("B4*r(o4)wn")
              .setAccountType(accountType)
              .setAccountFullName("K!a1@t#h$r4%y^n& B4*r(o4)wn")
              .setRoutingNumber("490000018")
              .setAccountNumber("614800811")
      );

      //CreateBankPayeeAccount,12,2,GAPITester1235a2,GAPI,TESTER,Checking,GAPI TESTER,490000018,614800811,,no
      //CreateBankPayeeAccount,12,4,GAPITester1235a4,GAPI,TESTER,Checking,GAPI TESTER,490000018,614800811,,no
      //CreateBankPayeeAccount,12,9,GAPITester1235a9,GAPI,TESTER,Savings,GAPI TESTER,490000018,614800811,,no
      //CreateBankPayeeAccount,12,11,GAPITester1235a11,GAPI,TESTER,Savings,GAPI TESTER,490000018,614800811,,no
      //CreateBankPayeeAccount,12,16,GAPITester1235a16,GAPI,TESTER,Business Checking,GAPI TESTER,490000018,614800811,,no
      //CreateBankPayeeAccount,12,18,GAPITester1235a18,GAPI,TESTER,Business Checking,GAPI TESTER,490000018,614800811,,no
      testCases.add(
          new CreateBankPayeeAccount(
              dataHelper.getReferenceId(),
              "Test Case 2/9/16 - Valid - Basic - accountType - " + accountType,
              getExpectedResponse(gatewayErrors, "12"))
              .setPayeeFirstName("GAPI")
              .setPayeeLastName("TESTER")
              .setAccountType(accountType)
              .setAccountFullName("GAPI TESTER")
              .setRoutingNumber("490000018")
              .setAccountNumber("614800811")
      );

      //CreateBankPayeeAccount,12,3,GAPITester1235a3,N_-+=eil,Wil_-+=son,Checking,N_-+=eil Wil_-+=son,490000018,614800811,,no
      //CreateBankPayeeAccount,12,10,GAPITester1235a10,N_-+=eil,Wil_-+=son,Savings,N_-+=eil Wil_-+=son,490000018,614800811,,no
      //CreateBankPayeeAccount,12,17,GAPITester1235a17,N_-+=eil,Wil_-+=son,Business Checking,N_-+=eil Wil_-+=son,490000018,614800811,,no
      testCases.add(
          new CreateBankPayeeAccount(
              dataHelper.getReferenceId(),
              "Test Case 3/10/17 - Valid - Weird names - accountType - " + accountType,
              getExpectedResponse(gatewayErrors, "12"))
              .setPayeeFirstName("N_-+=eil")
              .setPayeeLastName("Wil_-+=son")
              .setAccountType(accountType)
              .setAccountFullName("N_-+=eil Wil_-+=son")
              .setRoutingNumber("490000018")
              .setAccountNumber("614800811")
      );

      //CreateBankPayeeAccount,12,5,GAPITester1235a5,GAPI,TESTER,Checking,JeffersonBarwickBarrickNorthst AlistaireBenjaminLafayetteTrya,490000018,614800811,,no
      //CreateBankPayeeAccount,12,12,GAPITester1235a12,GAPI,TESTER,Savings,JeffersonBarwickBarrickNorthst AlistaireBenjaminLafayetteTrya,490000018,614800811,,no
      //CreateBankPayeeAccount,12,19,GAPITester1235a19,GAPI,TESTER,Business Checking,JeffersonBarwickBarrickNorthst AlistaireBenjaminLafayetteTrya,490000018,614800811,,no
      testCases.add(
          new CreateBankPayeeAccount(
              dataHelper.getReferenceId(),
              "Test Case 5/12/19 - Valid - Long account name - accountType - " + accountType,
              getExpectedResponse(gatewayErrors, "12"))
              .setPayeeFirstName("GAPI")
              .setPayeeLastName("TESTER")
              .setAccountType(accountType)
              .setAccountFullName("JeffersonBarwickBarrickNorthst AlistaireBenjaminLafayetteTrya")
              .setRoutingNumber("490000018")
              .setAccountNumber("614800811")
      );

      //CreateBankPayeeAccount,12,6,GAPITester1235a6,JeffersonBarwickBarrickNorthst,AlistaireBenjaminLafayetteTrya,Checking,JeffersonBarwickBarrickNorthst AlistaireBenjaminLafayetteTrya,490000018,614800811,,no
      //CreateBankPayeeAccount,12,13,GAPITester1235a13,JeffersonBarwickBarrickNorthst,AlistaireBenjaminLafayetteTrya,Savings,JeffersonBarwickBarrickNorthst AlistaireBenjaminLafayetteTrya,490000018,614800811,,no
      //CreateBankPayeeAccount,12,20,GAPITester1235a20,JeffersonBarwickBarrickNorthst,AlistaireBenjaminLafayetteTrya,Business Checking,JeffersonBarwickBarrickNorthst AlistaireBenjaminLafayetteTrya,490000018,614800811,,no
      testCases.add(
          new CreateBankPayeeAccount(
              dataHelper.getReferenceId(),
              "Test Case 6/13/20 - Valid - Long names - accountType - " + accountType,
              getExpectedResponse(gatewayErrors, "12"))
              .setPayeeFirstName("JeffersonBarwickBarrickNorthst")
              .setPayeeLastName("AlistaireBenjaminLafayetteTrya")
              .setAccountType(accountType)
              .setAccountFullName("JeffersonBarwickBarrickNorthst AlistaireBenjaminLafayetteTrya")
              .setRoutingNumber("490000018")
              .setAccountNumber("614800811")
      );

      //CreateBankPayeeAccount,12,7,GAPITester1235a7,JeffersonBarwickBarrickNorthst,AlistaireBenjaminLafayetteTrya,Checking,Jefferson Barwick,490000018,614800811,,no
      //CreateBankPayeeAccount,12,14,GAPITester1235a14,JeffersonBarwickBarrickNorthst,AlistaireBenjaminLafayetteTrya,Savings,Jefferson Barwick,490000018,614800811,,no
      //CreateBankPayeeAccount,12,21,GAPITester1235a21,JeffersonBarwickBarrickNorthst,AlistaireBenjaminLafayetteTrya,Business Checking,Jefferson Barwick,490000018,614800811,,no
      testCases.add(
          new CreateBankPayeeAccount(
              dataHelper.getReferenceId(),
              "Test Case 7/14/21 - Valid - Long names - accountType - " + accountType,
              getExpectedResponse(gatewayErrors, "12"))
              .setPayeeFirstName("JeffersonBarwickBarrickNorthst")
              .setPayeeLastName("AlistaireBenjaminLafayetteTrya")
              .setAccountType(accountType)
              .setAccountFullName("Jefferson Barwick")
              .setRoutingNumber("490000018")
              .setAccountNumber("614800811")
      );

      //CreateBankPayeeAccount,89,23,GAPITester1235a,Amanda,Phillips,Checking,Ronald Regan,,614800811,,no
      //CreateBankPayeeAccount,89,24,GAPITester1235a,Susan,Bell,Savings,Susan Bell,,614800811,,no
      //CreateBankPayeeAccount,89,25,GAPITester1235a,K!a1@t#h$r4%y^n&,B4*r(o4)wn,Business Checking,K!a1@t#h$r4%y^n& B4*r(o4)wn,,614800811,,no
      testCases.add(
          CreateBankPayeeAccount.createValid(
              dataHelper.getReferenceId(),
              "Test Case 23-25 - Empty routing number - accountType - " + accountType,
              getExpectedResponse(gatewayErrors, "89"))
              .setRoutingNumber("")
              .setAccountType(accountType)
      );

      //CreateBankPayeeAccount,90,26,GAPITester1235a,Amanda,Phillips,Checking,Ronald Regan,490000018,,,no
      //CreateBankPayeeAccount,90,27,GAPITester1235a,Susan,Bell,Savings,Susan Bell,490000018,,,no
      //CreateBankPayeeAccount,90,28,GAPITester1235a,K!a1@t#h$r4%y^n&,B4*r(o4)wn,Business Checking,K!a1@t#h$r4%y^n& B4*r(o4)wn,490000018,,,no
      testCases.add(
          CreateBankPayeeAccount.createValid(
              dataHelper.getReferenceId(),
              "Test Case 26-28 - Empty account number - accountType - " + accountType,
              getExpectedResponse(gatewayErrors, "90"))
              .setAccountNumber("")
              .setAccountType(accountType)
      );

      //CreateBankPayeeAccount,93,29,,Robert,Diaz,Checking,Robert Diaz,490000018,614800811,,no
      //CreateBankPayeeAccount,93,30,,K!a1@t#h$r4%y^n&,B4*r(o4)wn,Savings,K!a1@t#h$r4%y^n& B4*r(o4)wn,490000018,614800811,,no
      //CreateBankPayeeAccount,93,31,,Jonathan,Martin,Business Checking,Jonathan Martin,490000018,614800811,,no
      testCases.add(
          CreateBankPayeeAccount.createValid(
              "",
              "Test Case 29-31 - Empty payer reference id - accountType - " + accountType,
              getExpectedResponse(gatewayErrors, "93"))
              .setAccountType(accountType)
      );

      //CreateBankPayeeAccount,94,32,GAPITester1235a,,Baker,Checking,JeffersonBarwickBarrickNorthst AlistaireBenjaminLafayetteTrya,490000018,614800811,,no
      //CreateBankPayeeAccount,94,33,GAPITester1235a,,AlistaireBenjaminLafayetteTrya,Savings,JeffersonBarwickBarrickNorthst AlistaireBenjaminLafayetteTrya,490000018,614800811,,no
      //CreateBankPayeeAccount,94,34,GAPITester1235a,,Baker,Business Checking,JeffersonBarwickBarrickNorthst AlistaireBenjaminLafayetteTrya,490000018,614800811,,no
      testCases.add(
          CreateBankPayeeAccount.createValid(
              dataHelper.getReferenceId(),
              "Test Case 32-34 - Empty first name - accountType - " + accountType,
              getExpectedResponse(gatewayErrors, "94"))
              .setPayeeFirstName("")
              .setAccountType(accountType)
      );

      //CreateBankPayeeAccount,95,35,GAPITester1235a,JeffersonBarwickBarrickNorthst,,Checking,Jefferson Barwick,490000018,614800811,,no
      //CreateBankPayeeAccount,95,36,GAPITester1235a,K!a1@t#h$r4%y^n&,,Savings,K!a1@t#h$r4%y^n& B4*r(o4)wn,490000018,614800811,,no
      //CreateBankPayeeAccount,95,37,GAPITester1235a,Bryon,,Business Checking,Bryon Hendrix,490000018,614800811,,no
      testCases.add(
          CreateBankPayeeAccount.createValid(
              dataHelper.getReferenceId(),
              "Test Case 35-37 - Empty last name - accountType - " + accountType,
              getExpectedResponse(gatewayErrors, "95"))
              .setPayeeLastName("")
              .setAccountType(accountType)
      );

      //CreateBankPayeeAccount,114,39,GAPITester1235a,JeffersonBarwickBarrickNorthst,AlistaireBenjaminLafayetteTrya,Checking,JeffersonBarwickBarrickNorthstAlistaireBenjamina LafayetteTryaJeffersonBarwickBarrickNorthstAlistaire,490000018,614800811,,no
      //CreateBankPayeeAccount,114,40,GAPITester1235a,N_-+=eil,Wil_-+=son,Savings,JeffersonBarwickBarrickNorthstAlistaireBenjamina LafayetteTryaJeffersonBarwickBarrickNorthstAlistaire,490000018,614800811,,no
      //CreateBankPayeeAccount,114,41,GAPITester1235a,Albert,Bell,Business Checking,JeffersonBarwickBarrickNorthstAlistaireBenjamina LafayetteTryaJeffersonBarwickBarrickNorthstAlistaire,490000018,614800811,,no
      testCases.add(
          CreateBankPayeeAccount.createValid(
              dataHelper.getReferenceId(),
              "Test Case 39-41 - Long account name - accountType - " + accountType,
              getExpectedResponse(gatewayErrors, "114"))
              .setAccountFullName(
                  "JeffersonBarwickBarrickNorthstAlistaireBenjamina LafayetteTryaJeffersonBarwickBarrickNorthstAlistaire")
              .setAccountType(accountType)
      );

      //CreateBankPayeeAccount,115,42,GAPITester1235a,JeffersonBarwickBarrickNorthst,AlistaireBenjaminLafayetteTrya,Checking,JeffersonBarwickBarrickNorthst AlistaireBenjaminLafayetteTrya,4900000181,614800811,,no
      //CreateBankPayeeAccount,115,43,GAPITester1235a,Kaylee,Lanier,Savings,Kaylee Lanier,4900000181,614800811,,no
      //CreateBankPayeeAccount,115,44,GAPITester1235a,Jonathan,Martin,Business Checking,Jonathan Martin,4900000181,614800811,,no
      testCases.add(
          CreateBankPayeeAccount.createValid(
              dataHelper.getReferenceId(),
              "Test Case 42-44 - Long routing number - accountType - " + accountType,
              getExpectedResponse(gatewayErrors, "115"))
              .setRoutingNumber("4900000181")
              .setAccountType(accountType)
      );

      //CreateBankPayeeAccount,125,45,a134oihqf9w7h2ufhqiusd872q45y92715yr7q8wgfasyfasf,Amanda,Phillips,Business Checking,Ronald Regan,490000018,614800811,,no
      //CreateBankPayeeAccount,125,46,a134oihqf9w7h2ufhqiusd872q45y92715yr7q8wgfasyfasf,Kenneth,Evans,Savings,Abraham Lincoln,490000018,614800811,,no
      //CreateBankPayeeAccount,125,47,a134oihqf9w7h2ufhqiusd872q45y92715yr7q8wgfasyfasf,JeffersonBarwickBarrickNorthst,AlistaireBenjaminLafayetteTrya,Business Checking,JeffersonBarwickBarrickNorthst AlistaireBenjaminLafayetteTrya,490000018,614800811,,no
      testCases.add(
          CreateBankPayeeAccount.createValid(
              dataHelper.getReferenceId() + "a134oihqf9w7h2ufhqiusd872q45y92715yr7q8wgfasyfasf",
              "Test Case 45-47 - Long payee reference id - accountType - " + accountType,
              getExpectedResponse(gatewayErrors, "125"))
              .setAccountType(accountType)
      );

      //CreateBankPayeeAccount,126,48,GAPITester1235a,JeffersonBarwickBarrickNorthsta,Baker,Checking,JeffersonBarwickBarrickNorthst AlistaireBenjaminLafayetteTrya,490000018,614800811,,no
      //CreateBankPayeeAccount,126,49,GAPITester1235a,JeffersonBarwickBarrickNorthsta,Evans,Savings,Abraham Lincoln,490000018,614800811,,no
      //CreateBankPayeeAccount,126,50,GAPITester1235a,JeffersonBarwickBarrickNorthsta,AlistaireBenjaminLafayetteTrya,Business Checking,Jefferson Barwick,490000018,614800811,,no
      testCases.add(
          CreateBankPayeeAccount.createValid(
              dataHelper.getReferenceId(),
              "Test Case 48-50 - Long first name - accountType - " + accountType,
              getExpectedResponse(gatewayErrors, "126"))
              .setPayeeFirstName("JeffersonBarwickBarrickNorthsta")
              .setAccountType(accountType)
      );

      //CreateBankPayeeAccount,127,51,GAPITester1235a,JeffersonBarwickBarrickNorthst,AlistaireBenjaminLafayetteTryar,Checking,JeffersonBarwickBarrickNorthst AlistaireBenjaminLafayetteTrya,490000018,614800811,,no
      //CreateBankPayeeAccount,127,52,GAPITester1235a,JeffersonBarwickBarrickNorthst,AlistaireBenjaminLafayetteTryar,Savings,Abraham Lincoln,490000018,614800811,,no
      //CreateBankPayeeAccount,127,53,GAPITester1235a,JeffersonBarwickBarrickNorthst,AlistaireBenjaminLafayetteTryar,Business Checking,Jefferson Barwick,490000018,614800811,,no
      testCases.add(
          CreateBankPayeeAccount.createValid(
              dataHelper.getReferenceId(),
              "Test Case 51-53 - Long last name - accountType - " + accountType,
              getExpectedResponse(gatewayErrors, "127"))
              .setPayeeLastName("AlistaireBenjaminLafayetteTryar")
              .setAccountType(accountType)
      );

      String[] invalidRoutingNumbers = {
          "49000001a", "4900 0001",
      };

      for (String routingNumber : invalidRoutingNumbers) {
        //CreateBankPayeeAccount,131,54,GAPITester1235a,JeffersonBarwickBarrickNorthst,AlistaireBenjaminLafayetteTrya,Checking,JeffersonBarwickBarrickNorthst AlistaireBenjaminLafayetteTrya,49000001a,614800811,,no
        //CreateBankPayeeAccount,131,55,GAPITester1235a,JeffersonBarwickBarrickNorthst,AlistaireBenjaminLafayetteTrya,Checking,JeffersonBarwickBarrickNorthst AlistaireBenjaminLafayetteTrya,4900 0001,614800811,,no
        //CreateBankPayeeAccount,131,56,GAPITester1235a,Kaylee,Lanier,Savings,Kaylee Lanier,49000001a,614800811,,no
        //CreateBankPayeeAccount,131,57,GAPITester1235a,Kaylee,Lanier,Savings,Kaylee Lanier,4900 0001,614800811,,no
        //CreateBankPayeeAccount,131,58,GAPITester1235a,Jonathan,Martin,Business Checking,Jonathan Martin,49000001a,614800811,,no
        //CreateBankPayeeAccount,131,59,GAPITester1235a,Jonathan,Martin,Business Checking,Jonathan Martin,4900 0001,614800811,,no
        testCases.add(
            CreateBankPayeeAccount.createValid(
                dataHelper.getReferenceId(),
                "Test Case 54-59 - Invalid routing number - accountType - " + accountType,
                getExpectedResponse(gatewayErrors, "131"))
                .setRoutingNumber(routingNumber)
                .setAccountType(accountType)
        );
      }

      String[] invalidAccountNumbers = {
          "614800811dfwq", "6148 00811",
      };

      for (String accountNumber : invalidAccountNumbers) {
        //CreateBankPayeeAccount,132,60,GAPITester1235a,JeffersonBarwickBarrickNorthst,AlistaireBenjaminLafayetteTrya,Checking,JeffersonBarwickBarrickNorthst AlistaireBenjaminLafayetteTrya,490000018,614800811dfwq,,no
        //CreateBankPayeeAccount,132,61,GAPITester1235a,JeffersonBarwickBarrickNorthst,AlistaireBenjaminLafayetteTrya,Checking,JeffersonBarwickBarrickNorthst AlistaireBenjaminLafayetteTrya,490000018,6148 00811,,no
        //CreateBankPayeeAccount,132,62,GAPITester1235a,Kaylee,Lanier,Savings,Kaylee Lanier,490000018,614800811dfwq,,no
        //CreateBankPayeeAccount,132,63,GAPITester1235a,Kaylee,Lanier,Savings,Kaylee Lanier,490000018,6148 00811,,no
        //CreateBankPayeeAccount,132,64,GAPITester1235a,Jonathan,Martin,Business Checking,Jonathan Martin,490000018,614800811dfwq,,no
        //CreateBankPayeeAccount,132,65,GAPITester1235a,Jonathan,Martin,Business Checking,Jonathan Martin,490000018,6148 00811,,no
        testCases.add(
            CreateBankPayeeAccount.createValid(
                dataHelper.getReferenceId(),
                "Test Case 60-65 - Invalid account number - accountType - " + accountType,
                getExpectedResponse(gatewayErrors, "132"))
                .setAccountNumber(accountNumber)
                .setAccountType(accountType)
        );
      }
    }

    //CreateBankPayeeAccount,87,22,GAPITester1235a,Albert,Bell,,Albert Bell,490000018,614800811,,no
    testCases.add(
        CreateBankPayeeAccount.createValid(
            dataHelper.getReferenceId(),
            "Test Case 22 - Empty account type",
            getExpectedResponse(gatewayErrors, "87"))
            .setAccountType("")
    );

    //CreateBankPayeeAccount,103,38,GAPITester1235a,Albert,Bell,Checdking,Albert Bell,490000018,614800811,,no
    testCases.add(
        CreateBankPayeeAccount.createValid(
            dataHelper.getReferenceId(),
            "Test Case 38 - Invalid Empty account type",
            getExpectedResponse(gatewayErrors, "103"))
            .setAccountType("Checdking")
    );

    executeTests(testCases);
  }

  @Test(groups = {"gapi", "CreateBankPayeeAccount"})
  public void payeeProfitstars() {
    Logger.info("CreateBankPayeeAccount with profitstars paydirect");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc2");
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    DataHelper dataHelper = new DataHelper();
    TestCaseCollection testCases = new GapiTestCaseCollection(
        new Credentials(gatewayId, username, password)
    );

    //CreateBankPayeeAccount,12,65,132132132,Jonathan,Martin,Business Checking,Jonathan Martin,490000018,614800811,CA,yes
    testCases.add(
        CreateBankPayeeAccount.createValid(
            dataHelper.getReferenceId(),
            "Test Case 65.1 - Valid with Valid State",
            getExpectedResponse(gatewayErrors, "12"))
            .setPayeeState("CA")
    );

    //CreateBankPayeeAccount,12,65,132132132,Jonathan,Martin,Business Checking,Jonathan Martin,490000018,614800811,,yes
    testCases.add(
        CreateBankPayeeAccount.createValid(
            dataHelper.getReferenceId(),
            "Test Case 65.2 - Valid with No State",
            getExpectedResponse(gatewayErrors, "12"))
            .setPayeeState(null)
    );

    //CreateBankPayeeAccount,266,65,132132132,Jonathan,Martin,Business Checking,Jonathan Martin,490000018,614800811,QQ,yes
    testCases.add(
        CreateBankPayeeAccount.createValid(
            dataHelper.getReferenceId(),
            "Test Case 65.3 - Invalid State",
            getExpectedResponse(gatewayErrors, "266"))
            .setPayeeState("QQ")
    );

    //CreateBankPayeeAccount,265,65,132132132,Jonathan,Martin,Business Checking,Jonathan Martin,490000018,614800811, ,yes
    testCases.add(
        CreateBankPayeeAccount.createValid(
            dataHelper.getReferenceId(),
            "Test Case 65.4 - Invalid State (empty)",
            getExpectedResponse(gatewayErrors, "265"))
            .setPayeeState(" ")
    );

    executeTests(testCases);
  }
}

