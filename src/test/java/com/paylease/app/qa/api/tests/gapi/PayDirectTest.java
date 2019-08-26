package com.paylease.app.qa.api.tests.gapi;

import com.paylease.app.qa.api.tests.TestCaseCollection;
import com.paylease.app.qa.api.tests.gapi.testcase.PayDirect;
import com.paylease.app.qa.framework.DataHelper;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.api.Credentials;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import java.util.HashMap;
import java.util.List;
import org.testng.annotations.Test;


public class PayDirectTest extends BaseTest {

  private static final String REGION = "gapi";
  private static final String FEATURE = "payDirect";

  //-----------------------------------TESTS----------------------------------------------

  @Test(groups = {"gapi", "PayDirect"})
  public void basicLargeBatch() {
    Logger.info("PayDirect large batch success");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String payerId = testSetupPage.getString("payerId");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    DataHelper dataHelper = new DataHelper();
    TestCaseCollection testCases = new GapiTestCaseCollection(
        new Credentials(gatewayId, username, password)
    );
    for (int i = 0; i < 100; i++) {

      //PayDirect,2,1,12035,GAPITesterACH,LARGE_BATCH,100,,,,,,,,,no
      testCases.add(
          PayDirect.createValid(
              dataHelper.getReferenceId(),
              "Test Case 1 - Large Batch",
              getExpectedResponse(gatewayErrors, "2"),
              payerId)
      );
    }

    executeTests(testCases);
  }

  @Test(groups = {"gapi", "PayDirect"})
  public void requestValidation() {
    Logger.info("PayDirect basic request validation");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String payerId = testSetupPage.getString("payerId");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    DataHelper dataHelper = new DataHelper();
    TestCaseCollection testCases = new GapiTestCaseCollection(
        new Credentials(gatewayId, username, password)
    );

    //PayDirect,2,2,12035,GAPITesterACH,GPAI ACH,TESTER,,Savings,GAPI ACH Tester,490000018,614800811,1,No,,no
    testCases.add(
        new PayDirect(
            dataHelper.getReferenceId(),
            "Test Case 2 - basic valid",
            getExpectedResponse(gatewayErrors, "2"),
            payerId)
            .setPayeeReferenceId("GAPITesterACH")
            .setPayeeFirstName("GPAI ACH")
            .setPayeeLastName("TESTER")
            .setPayeeEmailAddress("")
            .setAccountType("Savings")
            .setAccountFullName("GAPI ACH Tester")
            .setRoutingNumber("490000018")
            .setAccountNumber("614800811")
            .setTotalAmount("1")
            .setSaveAccount("No")
    );

    //PayDirect,2,3,12035,GAPITesterACH,Bryon,Hendrix,,Savings,Bryon Hendrix,490000018,614800811,1,No,,no
    testCases.add(
        new PayDirect(
            dataHelper.getReferenceId(),
            "Test Case 3 - basic valid - different names",
            getExpectedResponse(gatewayErrors, "2"),
            payerId)
            .setPayeeReferenceId("GAPITesterACH")
            .setPayeeFirstName("Bryon")
            .setPayeeLastName("Hendrix")
            .setPayeeEmailAddress("")
            .setAccountType("Savings")
            .setAccountFullName("Bryon Hendrix")
            .setRoutingNumber("490000018")
            .setAccountNumber("614800811")
            .setTotalAmount("1")
            .setSaveAccount("No")
    );

    //PayDirect,2,4,12035,GAPITesterACH,K!a1@t#h$r4%y^n&,B4*r(o4)wn,bobtester@paylease.com,Checking,Kathryn Brown,490000018,614800811,2,No,,no
    testCases.add(
        new PayDirect(
            dataHelper.getReferenceId(),
            "Test Case 4 - basic valid - weird names",
            getExpectedResponse(gatewayErrors, "2"),
            payerId)
            .setPayeeReferenceId("GAPITesterACH")
            .setPayeeFirstName("K!a1@t#h$r4%y^n&")
            .setPayeeLastName("B4*r(o4)wn")
            .setPayeeEmailAddress("bobtester@paylease.com")
            .setAccountType("Checking")
            .setAccountFullName("Kathryn Brown")
            .setRoutingNumber("490000018")
            .setAccountNumber("614800811")
            .setTotalAmount("2")
            .setSaveAccount("No")
    );

    //PayDirect,2,5,12035,GAPITesterACH,Kaylee,Lanier,test@iana.org,Savings,K!a@y#l$e%e^ L&a*n(i)e_r+,490000018,614800811,2.01,No,,no
    testCases.add(
        new PayDirect(
            dataHelper.getReferenceId(),
            "Test Case 5 - basic valid - weird full name",
            getExpectedResponse(gatewayErrors, "2"),
            payerId)
            .setPayeeReferenceId("GAPITesterACH")
            .setPayeeFirstName("Kaylee")
            .setPayeeLastName("Lanier")
            .setPayeeEmailAddress("test@iana.org")
            .setAccountType("Savings")
            .setAccountFullName("K!a@y#l$e%e^ L&a*n(i)e_r+")
            .setRoutingNumber("490000018")
            .setAccountNumber("614800811")
            .setTotalAmount("2.01")
            .setSaveAccount("No")
    );

    //PayDirect,2,6,12035,GAPITesterACH,N_-+=eil,Wil_-+=son,test@nominet.org.uk,Checking,Neil Wilson,490000018,614800811,2.1,No,,no
    testCases.add(
        new PayDirect(
            dataHelper.getReferenceId(),
            "Test Case 6 - basic valid - weird names, different email",
            getExpectedResponse(gatewayErrors, "2"),
            payerId)
            .setPayeeReferenceId("GAPITesterACH")
            .setPayeeFirstName("N_-+=eil")
            .setPayeeLastName("Wil_-+=son")
            .setPayeeEmailAddress("test@nominet.org.uk")
            .setAccountType("Checking")
            .setAccountFullName("Neil Wilson")
            .setRoutingNumber("490000018")
            .setAccountNumber("614800811")
            .setTotalAmount("2.1")
            .setSaveAccount("No")
    );

    //PayDirect,2,7,12035,GAPITesterACH,Kurt,Myers,test@about.museum,Savings,Kur_-+=t A. Myers,490000018,614800811,20.11,No,,no
    testCases.add(
        new PayDirect(
            dataHelper.getReferenceId(),
            "Test Case 7 - basic valid - weird full name, different email",
            getExpectedResponse(gatewayErrors, "2"),
            payerId)
            .setPayeeReferenceId("GAPITesterACH")
            .setPayeeFirstName("Kurt")
            .setPayeeLastName("Myers")
            .setPayeeEmailAddress("test@about.museum")
            .setAccountType("Savings")
            .setAccountFullName("Kur_-+=t A. Myers")
            .setRoutingNumber("490000018")
            .setAccountNumber("614800811")
            .setTotalAmount("20.11")
            .setSaveAccount("No")
    );

    //PayDirect,2,8,12035,GAPITesterACH,Lee,Frazier,a@iana.org,Checking,Frazier Lee,490000018,614800811,20,No,,no
    testCases.add(
        new PayDirect(
            dataHelper.getReferenceId(),
            "Test Case 8 - basic valid - different email",
            getExpectedResponse(gatewayErrors, "2"),
            payerId)
            .setPayeeReferenceId("GAPITesterACH")
            .setPayeeFirstName("Lee")
            .setPayeeLastName("Frazier")
            .setPayeeEmailAddress("a@iana.org")
            .setAccountType("Checking")
            .setAccountFullName("Frazier Lee")
            .setRoutingNumber("490000018")
            .setAccountNumber("614800811")
            .setTotalAmount("20")
            .setSaveAccount("No")
    );

    //PayDirect,2,9,12035,GAPITesterACH,Kenneth,Evans,test@e.com,Savings,Kenneth Evans,490000018,614800811,20.01,No,,no
    testCases.add(
        new PayDirect(
            dataHelper.getReferenceId(),
            "Test Case 9 - basic valid - different email",
            getExpectedResponse(gatewayErrors, "2"),
            payerId)
            .setPayeeReferenceId("GAPITesterACH")
            .setPayeeFirstName("Kenneth")
            .setPayeeLastName("Evans")
            .setPayeeEmailAddress("test@e.com")
            .setAccountType("Savings")
            .setAccountFullName("Kenneth Evans")
            .setRoutingNumber("490000018")
            .setAccountNumber("614800811")
            .setTotalAmount("20.01")
            .setSaveAccount("No")
    );

    //PayDirect,2,10,12035,GAPITesterACH,Brian,Baker,test@iana.a,Checking,Brian Baker,490000018,614800811,20.1,No,,no
    testCases.add(
        new PayDirect(
            dataHelper.getReferenceId(),
            "Test Case 10 - basic valid - different email",
            getExpectedResponse(gatewayErrors, "2"),
            payerId)
            .setPayeeReferenceId("GAPITesterACH")
            .setPayeeFirstName("Brian")
            .setPayeeLastName("Baker")
            .setPayeeEmailAddress("test@iana.a")
            .setAccountType("Checking")
            .setAccountFullName("Brian Baker")
            .setRoutingNumber("490000018")
            .setAccountNumber("614800811")
            .setTotalAmount("20.1")
            .setSaveAccount("No")
    );

    //PayDirect,2,11,12035,GAPITesterACH,Amanda,Phillips,test.test@iana.org,Checking,Amanda Phillips,490000018,614800811,20.11,No,,no
    testCases.add(
        new PayDirect(
            dataHelper.getReferenceId(),
            "Test Case 11 - basic valid - different email",
            getExpectedResponse(gatewayErrors, "2"),
            payerId)
            .setPayeeReferenceId("GAPITesterACH")
            .setPayeeFirstName("Amanda")
            .setPayeeLastName("Phillips")
            .setPayeeEmailAddress("test.test@iana.org")
            .setAccountType("Checking")
            .setAccountFullName("Amanda Phillips")
            .setRoutingNumber("490000018")
            .setAccountNumber("614800811")
            .setTotalAmount("20.11")
            .setSaveAccount("No")
    );

    //PayDirect,2,12,12035,GAPITesterACH,Mary,Powell,123@iana.org,Savings,Mary Powell,490000018,614800811,200,No,,no
    testCases.add(
        new PayDirect(
            dataHelper.getReferenceId(),
            "Test Case 12 - basic valid - different email",
            getExpectedResponse(gatewayErrors, "2"),
            payerId)
            .setPayeeReferenceId("GAPITesterACH")
            .setPayeeFirstName("Mary")
            .setPayeeLastName("Powell")
            .setPayeeEmailAddress("123@iana.org")
            .setAccountType("Savings")
            .setAccountFullName("Mary Powell")
            .setRoutingNumber("490000018")
            .setAccountNumber("614800811")
            .setTotalAmount("200")
            .setSaveAccount("No")
    );

    //PayDirect,2,13,12035,GAPITesterACH,JeffersonBarwickBarrickNorthst,AlistaireBenjaminLafayetteTrya,test@123.com,Checking,JeffersonBarwickBarrickNorthst AlistaireBenjaminLafayetteTrya,490000018,614800811,200.01,No,,no
    testCases.add(
        new PayDirect(
            dataHelper.getReferenceId(),
            "Test Case 13 - basic valid - long names",
            getExpectedResponse(gatewayErrors, "2"),
            payerId)
            .setPayeeReferenceId("GAPITesterACH")
            .setPayeeFirstName("JeffersonBarwickBarrickNorthst")
            .setPayeeLastName("AlistaireBenjaminLafayetteTrya")
            .setPayeeEmailAddress("test@123.com")
            .setAccountType("Checking")
            .setAccountFullName("JeffersonBarwickBarrickNorthst AlistaireBenjaminLafayetteTrya")
            .setRoutingNumber("490000018")
            .setAccountNumber("614800811")
            .setTotalAmount("200.01")
            .setSaveAccount("No")
    );

    //PayDirect,2,14,12035,GAPITesterACH,Tammy,Murphy,abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghiklm@iana.org,Savings,Tammy Murphy,490000018,614800811,200.1,No,,no
    testCases.add(
        new PayDirect(
            dataHelper.getReferenceId(),
            "Test Case 14 - basic valid - very long email",
            getExpectedResponse(gatewayErrors, "2"),
            payerId)
            .setPayeeReferenceId("GAPITesterACH")
            .setPayeeFirstName("Tammy")
            .setPayeeLastName("Murphy")
            .setPayeeEmailAddress(
                "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghiklm@iana.org")
            .setAccountType("Savings")
            .setAccountFullName("Tammy Murphy")
            .setRoutingNumber("490000018")
            .setAccountNumber("614800811")
            .setTotalAmount("200.1")
            .setSaveAccount("No")
    );

    //PayDirect,2,15,12035,GAPITesterACH,Susan,Bell,test@mason-dixon.com,Checking,Susan Bell,490000018,614800811,200.11,No,,no
    testCases.add(
        new PayDirect(
            dataHelper.getReferenceId(),
            "Test Case 15 - basic valid - email with punctuation",
            getExpectedResponse(gatewayErrors, "2"),
            payerId)
            .setPayeeReferenceId("GAPITesterACH")
            .setPayeeFirstName("Susan")
            .setPayeeLastName("Bell")
            .setPayeeEmailAddress("test@mason-dixon.com")
            .setAccountType("Checking")
            .setAccountFullName("Susan Bell")
            .setRoutingNumber("490000018")
            .setAccountNumber("614800811")
            .setTotalAmount("200.11")
            .setSaveAccount("No")
    );

    //PayDirect,2,16,12035,GAPITesterACH,Jonathan,Martin,test@c--n.com,Savings,Jonathan Martin,490000018,614800811,2000,No,,no
    testCases.add(
        new PayDirect(
            dataHelper.getReferenceId(),
            "Test Case 16 - basic valid - email with punctuation",
            getExpectedResponse(gatewayErrors, "2"),
            payerId)
            .setPayeeReferenceId("GAPITesterACH")
            .setPayeeFirstName("Jonathan")
            .setPayeeLastName("Martin")
            .setPayeeEmailAddress("test@c--n.com")
            .setAccountType("Savings")
            .setAccountFullName("Jonathan Martin")
            .setRoutingNumber("490000018")
            .setAccountNumber("614800811")
            .setTotalAmount("2000")
            .setSaveAccount("No")
    );

    //PayDirect,2,17,12035,GAPITesterACH,Jose,Edwards,test@iana.co-uk,Checking,Jose Edwards,490000018,614800811,2000.01,No,,no
    testCases.add(
        new PayDirect(
            dataHelper.getReferenceId(),
            "Test Case 17 - basic valid - email with punctuation",
            getExpectedResponse(gatewayErrors, "2"),
            payerId)
            .setPayeeReferenceId("GAPITesterACH")
            .setPayeeFirstName("Jose")
            .setPayeeLastName("Edwards")
            .setPayeeEmailAddress("test@iana.co-uk")
            .setAccountType("Checking")
            .setAccountFullName("Jose Edwards")
            .setRoutingNumber("490000018")
            .setAccountNumber("614800811")
            .setTotalAmount("2000.01")
            .setSaveAccount("No")
    );

    //PayDirect,2,18,12035,GAPITesterACH,Robert,Diaz,NULL,Savings,Robert Diaz,490000018,614800811,2000.1,No,,no
    testCases.add(
        new PayDirect(
            dataHelper.getReferenceId(),
            "Test Case 18 - basic valid - no email",
            getExpectedResponse(gatewayErrors, "2"),
            payerId)
            .setPayeeReferenceId("GAPITesterACH")
            .setPayeeFirstName("Robert")
            .setPayeeLastName("Diaz")
            .setAccountType("Savings")
            .setAccountFullName("Robert Diaz")
            .setRoutingNumber("490000018")
            .setAccountNumber("614800811")
            .setTotalAmount("2000.1")
            .setSaveAccount("No")
    );

    //PayDirect,2,19,12035,GAPITesterACH,Albert,Bell,NULL,Checking,Albert Bell,490000018,614800811,2000.11,No,,no
    testCases.add(
        new PayDirect(
            dataHelper.getReferenceId(),
            "Test Case 19 - basic valid - no email",
            getExpectedResponse(gatewayErrors, "2"),
            payerId)
            .setPayeeReferenceId("GAPITesterACH")
            .setPayeeFirstName("Albert")
            .setPayeeLastName("Bell")
            .setAccountType("Checking")
            .setAccountFullName("Albert Bell")
            .setRoutingNumber("490000018")
            .setAccountNumber("614800811")
            .setTotalAmount("2000.11")
            .setSaveAccount("No")
    );

    //PayDirect,2,20,12035,GAPITesterACH,Jonathan,Martin,NULL,Savings,Jonathan Martin,490000018,614800811,1,No,,no
    testCases.add(
        new PayDirect(
            dataHelper.getReferenceId(),
            "Test Case 20 - basic valid - no email",
            getExpectedResponse(gatewayErrors, "2"),
            payerId)
            .setPayeeReferenceId("GAPITesterACH")
            .setPayeeFirstName("Jonathan")
            .setPayeeLastName("Martin")
            .setAccountType("Savings")
            .setAccountFullName("Jonathan Martin")
            .setRoutingNumber("490000018")
            .setAccountNumber("614800811")
            .setTotalAmount("1")
            .setSaveAccount("No")
    );

    //PayDirect,2,21,12035,GAPITesterACH,JeffersonBarwickBarrickNorthst,AlistaireBenjaminLafayetteTrya,NULL,Savings,JeffersonBarwickBarrickNorthstAlistaireBenjamin LafayetteTryaJeffersonBarwickBarrickNorthstAlistaire,490000018,614800811,1,No,,no
    testCases.add(
        new PayDirect(
            dataHelper.getReferenceId(),
            "Test Case 21 - basic valid - long names",
            getExpectedResponse(gatewayErrors, "2"),
            payerId)
            .setPayeeReferenceId("GAPITesterACH")
            .setPayeeFirstName("JeffersonBarwickBarrickNorthst")
            .setPayeeLastName("AlistaireBenjaminLafayetteTrya")
            .setAccountType("Savings")
            .setAccountFullName(
                "JeffersonBarwickBarrickNorthstAlistaireBenjamin LafayetteTryaJeffersonBarwickBarrickNorthstAlistaire")
            .setRoutingNumber("490000018")
            .setAccountNumber("614800811")
            .setTotalAmount("1")
            .setSaveAccount("No")
    );

    //PayDirect,85,22,12035,GAPITesterACH,Jonathan,Martin,,Savings,Jonathan Martin,490000018,614800811,,No,,no
    testCases.add(
        PayDirect.createValid(
            dataHelper.getReferenceId(),
            "Test Case 22 - empty total amount",
            getExpectedResponse(gatewayErrors, "85"),
            payerId)
            .setTotalAmount("")
    );

    //PayDirect,86,23,12035,GAPITesterACH,Albert,Bell,,Checking,Albert Bell,490000018,614800811,2000.11,,,no
    testCases.add(
        PayDirect.createValid(
            dataHelper.getReferenceId(),
            "Test Case 23 - empty saveAccount",
            getExpectedResponse(gatewayErrors, "86"),
            payerId)
            .setSaveAccount("")
    );

    //PayDirect,87,24,12035,GAPITesterACH,Jose,Edwards,,,Jose Edwards,490000018,614800811,2000.01,No,,no
    testCases.add(
        PayDirect.createValid(
            dataHelper.getReferenceId(),
            "Test Case 24 - empty account type",
            getExpectedResponse(gatewayErrors, "87"),
            payerId)
            .setAccountType("")
    );

    //PayDirect,88,25,12035,GAPITesterACH,Susan,Bell,,Checking,,490000018,614800811,200.11,No,,no
    testCases.add(
        PayDirect.createValid(
            dataHelper.getReferenceId(),
            "Test Case 25 - empty accout name",
            getExpectedResponse(gatewayErrors, "88"),
            payerId)
            .setAccountFullName("")
    );

    //PayDirect,89,26,12035,GAPITesterACH,Albert,Bell,,Checking,Albert Bell,,61480081,2000.11,No,,no
    testCases.add(
        PayDirect.createValid(
            dataHelper.getReferenceId(),
            "Test Case 26 - empty routing number",
            getExpectedResponse(gatewayErrors, "89"),
            payerId)
            .setRoutingNumber("")
    );

    //PayDirect,90,27,12035,GAPITesterACH,Jose,Edwards,,Checking,Jose Edwards,490000018,,2000.01,No,,no
    testCases.add(
        PayDirect.createValid(
            dataHelper.getReferenceId(),
            "Test Case 27 - empty account number",
            getExpectedResponse(gatewayErrors, "90"),
            payerId)
            .setAccountNumber("")
    );

    //PayDirect,92,28,,GAPITesterACH,Susan,Bell,,Checking,Susan Bell,490000018,614800811,200.11,No,,no
    testCases.add(
        PayDirect.createValid(
            dataHelper.getReferenceId(),
            "Test Case 28 - empty payerId",
            getExpectedResponse(gatewayErrors, "92"),
            "")
    );

    //PayDirect,93,29,12035,,Albert,Bell,,Checking,Albert Bell,490000018,614800811,2000.11,No,,no
    testCases.add(
        PayDirect.createValid(
            dataHelper.getReferenceId(),
            "Test Case 29 - empty referenceId",
            getExpectedResponse(gatewayErrors, "93"),
            payerId)
            .setPayeeReferenceId("")
    );

    //PayDirect,94,30,12035,GAPITesterACH,,Bell,,Checking,Susan Bell,490000018,614800811,200.11,No,,no
    testCases.add(
        PayDirect.createValid(
            dataHelper.getReferenceId(),
            "Test Case 30 - empty first name",
            getExpectedResponse(gatewayErrors, "94"),
            payerId)
            .setPayeeFirstName("")
    );

    //PayDirect,95,31,12035,GAPITesterACH,Susan,,,Checking,Susan Bell,490000018,614800811,200.11,No,,no
    testCases.add(
        PayDirect.createValid(
            dataHelper.getReferenceId(),
            "Test Case 31 - empty last name",
            getExpectedResponse(gatewayErrors, "95"),
            payerId)
            .setPayeeLastName("")
    );

    //PayDirect,103,32,12035,GAPITesterACH,Robert,Diaz,,savings,Robert Diaz,490000018,614800811,2000.1,No,,no
    testCases.add(
        PayDirect.createValid(
            dataHelper.getReferenceId(),
            "Test Case 32 - invalid account type",
            getExpectedResponse(gatewayErrors, "103"),
            payerId)
            .setAccountType("savings")
    );

    //PayDirect,104,33,12035,GAPITesterACH,Albert,Bell,,Checking,Albert Bell,490000018,614800811,2000.11,N,,no
    testCases.add(
        PayDirect.createValid(
            dataHelper.getReferenceId(),
            "Test Case 33 - invalid SaveAccount",
            getExpectedResponse(gatewayErrors, "104"),
            payerId)
            .setSaveAccount("N")
    );

    //PayDirect,114,34,12035,GAPITesterACH,JeffersonBarwickBarrickNorthst,AlistaireBenjaminLafayetteTrya,,Savings,JeffersonBarwickBarrickNorthstAlistaireBenjamina LafayetteTryaJeffersonBarwickBarrickNorthstAlistaire,490000018,614800811,1,No,,no
    testCases.add(
        PayDirect.createValid(
            dataHelper.getReferenceId(),
            "Test Case 34 - full name too long",
            getExpectedResponse(gatewayErrors, "114"),
            payerId)
            .setAccountFullName(
                "JeffersonBarwickBarrickNorthstAlistaireBenjamina LafayetteTryaJeffersonBarwickBarrickNorthstAlistaire")
    );

    //PayDirect,115,35,12035,GAPITesterACH,Robert,Diaz,,Savings,Robert Diaz,4900000181,614800811,2000.1,No,,no
    testCases.add(
        PayDirect.createValid(
            dataHelper.getReferenceId(),
            "Test Case 35 - routing number too long",
            getExpectedResponse(gatewayErrors, "115"),
            payerId)
            .setRoutingNumber("4900000181")
    );

    //PayDirect,125,36,12035,a134oihqf9w7h2ufhqiusd872q45y92715yr7q8wgfasyfasf,JeffersonBarwickBarrickNorthst,AlistaireBenjaminLafayetteTrya,,Savings,JeffersonBarwickBarrickNorthstAlistaireBenjamin LafayetteTryaJeffersonBarwickBarrickNorthstAlistaire,490000018,614800811,1,No,,no
    testCases.add(
        PayDirect.createValid(
            dataHelper.getReferenceId(),
            "Test Case 36 - referenceId too long",
            getExpectedResponse(gatewayErrors, "125"),
            payerId)
            .setPayeeReferenceId("a134oihqf9w7h2ufhqiusd872q45y92715yr7q8wgfasyfasf")
    );

    //PayDirect,126,37,12035,GAPITesterACH,JeffersonBarwickBarrickNorthsta,AlistaireBenjaminLafayetteTrya,,Savings,JeffersonBarwickBarrickNorthst AlistaireBenjaminLafayetteTrya,490000018,614800811,1,No,,no
    testCases.add(
        PayDirect.createValid(
            dataHelper.getReferenceId(),
            "Test Case 37 - first name too long",
            getExpectedResponse(gatewayErrors, "126"),
            payerId)
            .setPayeeFirstName("JeffersonBarwickBarrickNorthsta")
    );

    //PayDirect,127,38,12035,GAPITesterACH,JeffersonBarwickBarrickNorthst,AlistaireBenjaminLafayetteTryar,,Savings,JeffersonBarwickBarrickNorthst AlistaireBenjaminLafayetteTrya,490000018,614800811,1,No,,no
    testCases.add(
        PayDirect.createValid(
            dataHelper.getReferenceId(),
            "Test Case 38 - last name too long",
            getExpectedResponse(gatewayErrors, "127"),
            payerId)
            .setPayeeLastName("AlistaireBenjaminLafayetteTryar")
    );

    String[] invalidRoutingNumbers = {
        "490000018a", "4900 00018",
    };

    for (String routingNumber : invalidRoutingNumbers) {
      //PayDirect,131,39,12035,GAPITesterACH,Jonathan,Martin,,Savings,Jonathan Martin,490000018a,614800811,2000,No,,no
      //PayDirect,131,40,12035,GAPITesterACH,Jonathan,Martin,,Savings,Jonathan Martin,4900 00018,614800811,2000,No,,no
      testCases.add(
          PayDirect.createValid(
              dataHelper.getReferenceId(),
              "Test Case 39/40 - invalid routing number",
              getExpectedResponse(gatewayErrors, "131"),
              payerId)
              .setRoutingNumber(routingNumber)
      );
    }

    String[] invalidAccountNumbers = {
        "614800811dfwq", "6148 00811",
    };

    for (String accountNumber : invalidAccountNumbers) {
      //PayDirect,132,41,12035,GAPITesterACH,Albert,Bell,,Checking,Albert Bell,490000018,614800811dfwq,2000.11,No,,no
      //PayDirect,132,42,12035,GAPITesterACH,Albert,Bell,,Checking,Albert Bell,490000018,6148 00811,2000.11,No,,no
      testCases.add(
          PayDirect.createValid(
              dataHelper.getReferenceId(),
              "Test Case 41/42 - invalid account number",
              getExpectedResponse(gatewayErrors, "132"),
              payerId)
              .setAccountNumber(accountNumber)
      );
    }

    String[] invalidTotalAmounts = {
        "2000.003", "2000 03", "2000 .3", "2000 .03", "2000. 3", "2000. 03", "-200", "+150.36",
        "1.00a",
    };

    for (String totalAmount : invalidTotalAmounts) {
      //PayDirect,133,43,12035,GAPITesterACH,Jonathan,Martin,,Savings,Jonathan Martin,490000018,614800811,2000.003,No,,no
      //PayDirect,133,44,12035,GAPITesterACH,Jonathan,Martin,,Savings,Jonathan Martin,490000018,614800811,2000 03,No,,no
      //PayDirect,133,45,12035,GAPITesterACH,Jonathan,Martin,,Savings,Jonathan Martin,490000018,614800811,2000 .3,No,,no
      //PayDirect,133,46,12035,GAPITesterACH,Jonathan,Martin,,Savings,Jonathan Martin,490000018,614800811,2000 .03,No,,no
      //PayDirect,133,47,12035,GAPITesterACH,Jonathan,Martin,,Savings,Jonathan Martin,490000018,614800811,2000. 3,No,,no
      //PayDirect,133,48,12035,GAPITesterACH,Jonathan,Martin,,Savings,Jonathan Martin,490000018,614800811,2000. 03,No,,no
      //PayDirect,133,49,12035,GAPITesterACH,Jonathan,Martin,,Savings,Jonathan Martin,490000018,614800811,-200,No,,no
      //PayDirect,133,50,12035,GAPITesterACH,Jonathan,Martin,,Savings,Jonathan Martin,490000018,614800811,+150.36,No,,no
      //PayDirect,133,51,12035,GAPITesterACH,Jonathan,Martin,,Savings,Jonathan Martin,490000018,614800811,1.00a,No,,no
      testCases.add(
          PayDirect.createValid(
              dataHelper.getReferenceId(),
              "Test Case 43-51 - invalid total amount",
              getExpectedResponse(gatewayErrors, "133"),
              payerId)
              .setTotalAmount(totalAmount)
      );
    }

    String[] invalidPayerIds = {
        "d12035", "12 035", "12!035", "12-035", "12.035",
    };

    for (String invalidPayerId : invalidPayerIds) {
      //PayDirect,141,52,d12035,GAPITesterACH,Susan,Bell,,Checking,Susan Bell,490000018,614800811,200.11,No,,no
      //PayDirect,141,53,12 035,GAPITesterACH,Susan,Bell,,Checking,Susan Bell,490000018,614800811,200.11,No,,no
      //PayDirect,141,54,12!035,GAPITesterACH,Susan,Bell,,Checking,Susan Bell,490000018,614800811,200.11,No,,no
      //PayDirect,141,55,12-035,GAPITesterACH,Susan,Bell,,Checking,Susan Bell,490000018,614800811,200.11,No,,no
      //PayDirect,141,56,12.035,GAPITesterACH,Susan,Bell,,Checking,Susan Bell,490000018,614800811,200.11,No,,no
      testCases.add(
          PayDirect.createValid(
              dataHelper.getReferenceId(),
              "Test Case 52-56 - invalid payerId",
              getExpectedResponse(gatewayErrors, "141"),
              invalidPayerId)
      );
    }

    //PayDirect,151,57,12035,GAPITesterACH,Albert,Bell,,Checking,Albert Bell,49000001,614800811,2000.11,No,,no
    testCases.add(
        PayDirect.createValid(
            dataHelper.getReferenceId(),
            "Test Case 57 - routing number too short",
            getExpectedResponse(gatewayErrors, "151"),
            payerId)
            .setRoutingNumber("49000001")
    );

    //PayDirect,153,58,312035,GAPITesterACH,Susan,Bell,,Checking,Susan Bell,490000018,614800811,200.11,No,,no
    testCases.add(
        PayDirect.createValid(
            dataHelper.getReferenceId(),
            "Test Case 58 - incorrect payerId",
            getExpectedResponse(gatewayErrors, "153"),
            "312035")
    );

    //PayDirect,194,59,12035,GAPITesterACH,Jonathan,Martin,,Savings,Jonathan Martin,490000018,614800811,2000000.3,No,,no
    testCases.add(
        PayDirect.createValid(
            dataHelper.getReferenceId(),
            "Test Case 59 - amount too large",
            getExpectedResponse(gatewayErrors, "194"),
            payerId)
            .setTotalAmount("2000000.3")
    );

    String[] invalidEmails = {
        "test", "@", "test@", "test@io", "@io", "@iana.org", ".test@iana.org", "test.@iana.org",
        "test..iana.org", "test_exa-mple.com", "!#$%&amp;`*+/=?^`{|}~@iana.org",
        "test\\@test@iana.org", "test@iana.123", "test@255.255.255.255", "test@-iana.org",
        "test@iana-.com", "test@.iana.org", "test@iana.org.", "test@iana..com",
        "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghiklm@abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghikl.abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghikl.abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghij",
        "a@abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghikl.abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghikl.abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghikl.abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefg.hijk",
        "test\"@iana.org", "\"test@iana.org", "(comment)test@iana.org", "test@(comment)iana.org",
        "test@iana.org-", "(test@iana.org", "test@(iana.org", "test@[1.2.3.4",
    };

    for (String email : invalidEmails) {
      //PayDirect,263,60,12035,GAPITesterACH,GPAI ACH,TESTER,"test",Savings,GAPI ACH Tester,490000018,614800811,1,No,,no
      //PayDirect,263,61,12035,GAPITesterACH,GPAI ACH,TESTER,"@",Savings,GAPI ACH Tester,490000018,614800811,1,No,,no
      //PayDirect,263,62,12035,GAPITesterACH,GPAI ACH,TESTER,"test@",Savings,GAPI ACH Tester,490000018,614800811,1,No,,no
      //PayDirect,263,63,12035,GAPITesterACH,GPAI ACH,TESTER,"test@io",Savings,GAPI ACH Tester,490000018,614800811,1,No,,no
      //PayDirect,263,64,12035,GAPITesterACH,GPAI ACH,TESTER,"@io",Savings,GAPI ACH Tester,490000018,614800811,1,No,,no
      //PayDirect,263,65,12035,GAPITesterACH,GPAI ACH,TESTER,"@iana.org",Savings,GAPI ACH Tester,490000018,614800811,1,No,,no
      //PayDirect,263,66,12035,GAPITesterACH,GPAI ACH,TESTER,".test@iana.org",Savings,GAPI ACH Tester,490000018,614800811,1,No,,no
      //PayDirect,263,67,12035,GAPITesterACH,GPAI ACH,TESTER,"test.@iana.org",Savings,GAPI ACH Tester,490000018,614800811,1,No,,no
      //PayDirect,263,68,12035,GAPITesterACH,GPAI ACH,TESTER,"test..iana.org",Savings,GAPI ACH Tester,490000018,614800811,1,No,,no
      //PayDirect,263,69,12035,GAPITesterACH,GPAI ACH,TESTER,"test_exa-mple.com",Savings,GAPI ACH Tester,490000018,614800811,1,No,,no
      //PayDirect,263,70,12035,GAPITesterACH,GPAI ACH,TESTER,"!#$%&amp;`*+/=?^`{|}~@iana.org",Savings,GAPI ACH Tester,490000018,614800811,1,No,,no
      //PayDirect,263,71,12035,GAPITesterACH,GPAI ACH,TESTER,"test\@test@iana.org",Savings,GAPI ACH Tester,490000018,614800811,1,No,,no
      //PayDirect,263,72,12035,GAPITesterACH,GPAI ACH,TESTER,"test@iana.123",Savings,GAPI ACH Tester,490000018,614800811,1,No,,no
      //PayDirect,263,73,12035,GAPITesterACH,GPAI ACH,TESTER,"test@255.255.255.255",Savings,GAPI ACH Tester,490000018,614800811,1,No,,no
      //PayDirect,263,74,12035,GAPITesterACH,GPAI ACH,TESTER,"test@-iana.org",Savings,GAPI ACH Tester,490000018,614800811,1,No,,no
      //PayDirect,263,75,12035,GAPITesterACH,GPAI ACH,TESTER,"test@iana-.com",Savings,GAPI ACH Tester,490000018,614800811,1,No,,no
      //PayDirect,263,76,12035,GAPITesterACH,GPAI ACH,TESTER,"test@.iana.org",Savings,GAPI ACH Tester,490000018,614800811,1,No,,no
      //PayDirect,263,77,12035,GAPITesterACH,GPAI ACH,TESTER,"test@iana.org.",Savings,GAPI ACH Tester,490000018,614800811,1,No,,no
      //PayDirect,263,78,12035,GAPITesterACH,GPAI ACH,TESTER,"test@iana..com",Savings,GAPI ACH Tester,490000018,614800811,1,No,,no
      //PayDirect,263,79,12035,GAPITesterACH,GPAI ACH,TESTER,"abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghiklm@abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghikl.abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghikl.abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghij",Savings,GAPI ACH Tester,490000018,614800811,1,No,,no
      //PayDirect,263,80,12035,GAPITesterACH,GPAI ACH,TESTER,"a@abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghikl.abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghikl.abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghikl.abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefg.hijk",Savings,GAPI ACH Tester,490000018,614800811,1,No,,no
      //PayDirect,263,81,12035,GAPITesterACH,GPAI ACH,TESTER,"test"@iana.org",Savings,GAPI ACH Tester,490000018,614800811,1,No,,no
      //PayDirect,263,82,12035,GAPITesterACH,GPAI ACH,TESTER,""test@iana.org",Savings,GAPI ACH Tester,490000018,614800811,1,No,,no
      //PayDirect,263,83,12035,GAPITesterACH,GPAI ACH,TESTER,"(comment)test@iana.org",Savings,GAPI ACH Tester,490000018,614800811,1,No,,no
      //PayDirect,263,84,12035,GAPITesterACH,GPAI ACH,TESTER,"test@(comment)iana.org",Savings,GAPI ACH Tester,490000018,614800811,1,No,,no
      //PayDirect,263,85,12035,GAPITesterACH,GPAI ACH,TESTER,"test@iana.org-",Savings,GAPI ACH Tester,490000018,614800811,1,No,,no
      //PayDirect,263,86,12035,GAPITesterACH,GPAI ACH,TESTER,"(test@iana.org",Savings,GAPI ACH Tester,490000018,614800811,1,No,,no
      //PayDirect,263,87,12035,GAPITesterACH,GPAI ACH,TESTER,"test@(iana.org",Savings,GAPI ACH Tester,490000018,614800811,1,No,,no
      //PayDirect,263,88,12035,GAPITesterACH,GPAI ACH,TESTER,"test@[1.2.3.4",Savings,GAPI ACH Tester,490000018,614800811,1,No,,no
      testCases.add(
          PayDirect.createValid(
              dataHelper.getReferenceId(),
              "Test Case 60-88 - invalid email",
              getExpectedResponse(gatewayErrors, "263"),
              payerId)
              .setPayeeEmailAddress(email)
      );
    }

    executeTests(testCases);
  }


  @Test(groups = {"gapi", "PayDirect"})
  public void requestValidationProfitstars() {
    Logger.info("PayDirect basic request validation with Profitstars configuration");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc2");
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String payerId = testSetupPage.getString("payerId");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    DataHelper dataHelper = new DataHelper();
    TestCaseCollection testCases = new GapiTestCaseCollection(
        new Credentials(gatewayId, username, password)
    );

    //PayDirect,2,2,56092,234235,GPAI ACH,TESTER,,Savings,GAPI ACH Tester,490000018,614800811,1,No,CA,yes
    testCases.add(
        PayDirect.createValid(
            dataHelper.getReferenceId(),
            "Test Case X - valid profitstars",
            getExpectedResponse(gatewayErrors, "2"),
            payerId)
            .setPayeeState("CA")
    );

    //PayDirect,265,2,56092,432564,GPAI ACH,TESTER,,Savings,GAPI ACH Tester,490000018,614800811,1,No,,yes
    testCases.add(
        PayDirect.createValid(
            dataHelper.getReferenceId(),
            "Test Case X - empty state",
            getExpectedResponse(gatewayErrors, "265"),
            payerId)
            .setPayeeState("")
    );

    //PayDirect,266,2,56092,235236,GPAI ACH,TESTER,,Savings,GAPI ACH Tester,490000018,614800811,1,No,QQ,yes
    testCases.add(
        PayDirect.createValid(
            dataHelper.getReferenceId(),
            "Test Case X - invalid state",
            getExpectedResponse(gatewayErrors, "266"),
            payerId)
            .setPayeeState("QQ")
    );

    executeTests(testCases);
  }

  @Test(groups = {"gapi", "PayDirect"})
  public void requestValidationProfitstarsNoState() {
    Logger.info("PayDirect basic request validation with Profitstars configuration - no state");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc3");
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String payerId = testSetupPage.getString("payerId");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    DataHelper dataHelper = new DataHelper();
    TestCaseCollection testCases = new GapiTestCaseCollection(
        new Credentials(gatewayId, username, password)
    );

    //PayDirect,264,2,56094,235236,GPAI ACH,TESTER,,Savings,GAPI ACH Tester,490000018,614800811,1,No,LA,yes,noentityid
    testCases.add(
        PayDirect.createValid(
            dataHelper.getReferenceId(),
            "Test Case X - pm has no entity configured",
            getExpectedResponse(gatewayErrors, "264"),
            payerId)
            .setPayeeState("LA")
    );

    executeTests(testCases);
  }

  @Test(groups = {"gapi", "PayDirect"})
  public void requestValidationProfitstarsNoBankEntity() {
    Logger.info("PayDirect request validation Profitstars configuration - bank entity missing");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc4");
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String payerId = testSetupPage.getString("payerId");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    DataHelper dataHelper = new DataHelper();
    TestCaseCollection testCases = new GapiTestCaseCollection(
        new Credentials(gatewayId, username, password)
    );

    //PayDirect,267,2,56093,235236,GPAI ACH,TESTER,,Savings,GAPI ACH Tester,490000018,614800811,1,No,LA,yes
    testCases.add(
        PayDirect.createValid(
            dataHelper.getReferenceId(),
            "Test Case X - pm bank account has no entity configured",
            getExpectedResponse(gatewayErrors, "267"),
            payerId)
            .setPayeeState("LA")
    );

    executeTests(testCases);
  }
}