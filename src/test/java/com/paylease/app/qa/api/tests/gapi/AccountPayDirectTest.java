package com.paylease.app.qa.api.tests.gapi;

import com.paylease.app.qa.api.tests.TestCaseCollection;
import com.paylease.app.qa.api.tests.gapi.testcase.AccountPayDirect;
import com.paylease.app.qa.api.tests.gapi.testcase.ErrorTestCase;
import com.paylease.app.qa.framework.DataHelper;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.api.Credentials;
import com.paylease.app.qa.framework.api.gapi.GapiRequest;
import com.paylease.app.qa.framework.api.gapi.GapiResponse;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import java.util.HashMap;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;


public class AccountPayDirectTest extends BaseTest {

  private static final String REGION = "gapi";
  private static final String FEATURE = "AccountPayDirect";

  //-----------------------------------TESTS----------------------------------------------

  @Test(groups = {"gapi", "AccountPayDirect"})
  public void requestValidation() {

    Logger.info("Account Pay Direct request validation tests");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String payerId = testSetupPage.getString("payerId");
    final String gatewayPayeeId = testSetupPage.getString("gatewayPayeeId");
    final String payeeReferenceId = testSetupPage.getString("payeeReferenceId");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    DataHelper dataHelper = new DataHelper();
    TestCaseCollection testCases = new GapiTestCaseCollection(
        new Credentials(gatewayId, username, password)
    );

    final String[] validEmailAddresses = {
        "bobtester@paylease.com", "test@iana.org", "test@nominet.org.uk", "test@about.museum",
        "a@iana.org", "test.test@iana.org", "test@iana.a", "test.test@iana.org", "123@iana.org",
        "test@123.com", "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghiklm@iana.org",
        "test@mason-dixon.com", "test@c--n.com", "test@iana.co-uk", "",
    };

    for (String email : validEmailAddresses) {
      //AccountPayDirect,2,2,12035,GAPITester1235a,bobtester@paylease.com,32795,1500,,no
      //AccountPayDirect,2,3,12035,GAPITester1235a,test@iana.org,32795,5,,no
      //AccountPayDirect,2,4,12035,GAPITester1235a,test@nominet.org.uk,32795,5,,no
      //AccountPayDirect,2,5,12035,GAPITester1235a,test@about.museum,32795,5,,no
      //AccountPayDirect,2,6,12035,GAPITester1235a,a@iana.org,32795,5,,no
      //AccountPayDirect,2,9,12035,GAPITester1235a,test.test@iana.org,32795,5,,no
      //AccountPayDirect,2,8,12035,GAPITester1235a,test@iana.a,32795,5,,no
      //AccountPayDirect,2,9,12035,GAPITester1235a,test.test@iana.org,32795,5,,no
      //AccountPayDirect,2,10,12035,GAPITester1235a,123@iana.org,32795,5,,no
      //AccountPayDirect,2,11,12035,GAPITester1235a,test@123.com,32795,5,,no
      //AccountPayDirect,2,12,12035,GAPITester1235a,abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghiklm@iana.org,32795,5,,no
      //AccountPayDirect,2,13,12035,GAPITester1235a,test@mason-dixon.com,32795,5,,no
      //AccountPayDirect,2,14,12035,GAPITester1235a,test@c--n.com,32795,5,,no
      //AccountPayDirect,2,15,12035,GAPITester1235a,test@iana.co-uk,32795,5,,no
      //AccountPayDirect,2,16,12035,GAPITester1235a,,32795,5,,no
      testCases.add(
          new AccountPayDirect(
              dataHelper.getReferenceId(),
              "Test case 2-16: Basic amount and email - " + email,
              getExpectedResponse(gatewayErrors, "2"),
              payerId,
              payeeReferenceId,
              gatewayPayeeId)
              .setPayeeEmailAddress(email)
              .setTotalAmount("1500")
      );
    }

    //AccountPayDirect,2,17,12035,GAPITester1235a,NULL,32795,5,,no
    testCases.add(
        new AccountPayDirect(
            dataHelper.getReferenceId(),
            "Test case 17: Empty Email",
            getExpectedResponse(gatewayErrors, "2"),
            payerId,
            payeeReferenceId,
            gatewayPayeeId)
            .setTotalAmount("5")
    );

    //AccountPayDirect,85,18,12035,GAPITester1235a,,32795,,,no
    testCases.add(
        new AccountPayDirect(
            dataHelper.getReferenceId(),
            "Test case 18: Empty amount",
            getExpectedResponse(gatewayErrors, "85"),
            payerId,
            payeeReferenceId,
            gatewayPayeeId)
            .setTotalAmount("")
    );

    //AccountPayDirect,92,19,,GAPITester1235a,,32795,1500,,no
    testCases.add(
        AccountPayDirect.createValid(
            dataHelper.getReferenceId(),
            "Test case 19: Empty PayerId",
            getExpectedResponse(gatewayErrors, "92"),
            "",
            payeeReferenceId,
            gatewayPayeeId)
    );

    //AccountPayDirect,93,20,12035,,,32795,1500,,no
    testCases.add(
        AccountPayDirect.createValid(
            dataHelper.getReferenceId(),
            "Test case 20: Empty PayeeReferenceId",
            getExpectedResponse(gatewayErrors, "93"),
            payerId,
            "",
            gatewayPayeeId)
    );

    //AccountPayDirect,96,21,12035,GAPITester1235a,,,1500,,no
    testCases.add(
        AccountPayDirect.createValid(
            dataHelper.getReferenceId(),
            "Test case 21: Empty GatewayPayeeId",
            getExpectedResponse(gatewayErrors, "96"),
            payerId,
            payeeReferenceId,
            "")
    );

    //AccountPayDirect,125,22,12035,GAPITester1235a324tr23t34tw3tw3t335,,32795,1500,,no
    testCases.add(
        AccountPayDirect.createValid(
            dataHelper.getReferenceId(),
            "Test case 22: Long PayeeReferenceId",
            getExpectedResponse(gatewayErrors, "125"),
            payerId,
            "GAPITester1235a324tr23t34tw3tw3t335",
            gatewayPayeeId)
    );

    String[] invalidAmounts = {
        "2000.003", "2000 03", "2000. 3", "2000. 03", "2000 .3", "2000 .03", "-2000", "+53.36",
        "1.0a",
    };

    for (String amount : invalidAmounts) {
      //AccountPayDirect,133,23,12035,GAPITester1235a,,32795,2000.003,,no
      //AccountPayDirect,133,24,12035,GAPITester1235a,,32795,2000 03,,no
      //AccountPayDirect,133,25,12035,GAPITester1235a,,32795,2000. 3,,no
      //AccountPayDirect,133,26,12035,GAPITester1235a,,32795,2000. 03,,no
      //AccountPayDirect,133,27,12035,GAPITester1235a,,32795,2000 .3,,no
      //AccountPayDirect,133,28,12035,GAPITester1235a,,32795,2000 .03,,no
      //AccountPayDirect,133,29,12035,GAPITester1235a,,32795,-2000,,no
      //AccountPayDirect,133,30,12035,GAPITester1235a,,32795,+53.36,,no
      //AccountPayDirect,133,31,12035,GAPITester1235a,,32795,1.0a,,no
      testCases.add(
          AccountPayDirect.createValid(
              dataHelper.getReferenceId(),
              "Test case 23-31: invalid amount - " + amount,
              getExpectedResponse(gatewayErrors, "133"),
              payerId,
              payeeReferenceId,
              gatewayPayeeId)
              .setTotalAmount(amount)
      );
    }

    String[] invalidPayerIds = {
        "12035a", "120 35", "120-35", "120.35", "120!35",
    };
    for (String invalidPayerId : invalidPayerIds) {
      //AccountPayDirect,141,32,12035a,GAPITester1235a,,32795,1500,,no
      //AccountPayDirect,141,33,120 35,GAPITester1235a,,32795,1500,,no
      //AccountPayDirect,141,34,120-35,GAPITester1235a,,32795,1500,,no
      //AccountPayDirect,141,35,120.35,GAPITester1235a,,32795,1500,,no
      //AccountPayDirect,141,36,120!35,GAPITester1235a,,32795,1500,,no
      testCases.add(
          AccountPayDirect.createValid(
              dataHelper.getReferenceId(),
              "Test case 32-36: Invalid PayerId - " + invalidPayerId,
              getExpectedResponse(gatewayErrors, "141"),
              invalidPayerId,
              payeeReferenceId,
              gatewayPayeeId)
      );
    }

    //AccountPayDirect,142,37,12035,GAPITester1235a,,32790a,1500,,no
    testCases.add(
        AccountPayDirect.createValid(
            dataHelper.getReferenceId(),
            "Test case 37: Alphanumeric GatewayPayeeId",
            getExpectedResponse(gatewayErrors, "142"),
            payerId,
            payeeReferenceId,
            "32790a")
    );

    //AccountPayDirect,142,38,12035,GAPITester1235a,,32 790,1500,,no
    testCases.add(
        AccountPayDirect.createValid(
            dataHelper.getReferenceId(),
            "Test case 38: GatewayPayeeId with space between characters",
            getExpectedResponse(gatewayErrors, "142"),
            payerId,
            payeeReferenceId,
            "32 790")
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
      //AccountPayDirect,263,39,12035,GAPITester1235a,"test",32795,5,,no
      //AccountPayDirect,263,40,12035,GAPITester1235a,"@",32795,5,,no
      //AccountPayDirect,263,41,12035,GAPITester1235a,"test@",32795,5,,no
      //AccountPayDirect,263,42,12035,GAPITester1235a,"test@io",32795,5,,no
      //AccountPayDirect,263,43,12035,GAPITester1235a,"@io",32795,5,,no
      //AccountPayDirect,263,44,12035,GAPITester1235a,"@iana.org",32795,5,,no
      //AccountPayDirect,263,45,12035,GAPITester1235a,".test@iana.org",32795,5,,no
      //AccountPayDirect,263,46,12035,GAPITester1235a,"test.@iana.org",32795,5,,no
      //AccountPayDirect,263,47,12035,GAPITester1235a,"test..iana.org",32795,5,,no
      //AccountPayDirect,263,48,12035,GAPITester1235a,"test_exa-mple.com",32795,5,,no
      //AccountPayDirect,263,49,12035,GAPITester1235a,"!#$%&amp;`*+/=?^`{|}~@iana.org",32795,5,,no
      //AccountPayDirect,263,50,12035,GAPITester1235a,"test\@test@iana.org",32795,5,,no
      //AccountPayDirect,263,51,12035,GAPITester1235a,"test@iana.123",32795,5,,no
      //AccountPayDirect,263,52,12035,GAPITester1235a,"test@255.255.255.255",32795,5,,no
      //AccountPayDirect,263,53,12035,GAPITester1235a,"test@-iana.org",32795,5,,no
      //AccountPayDirect,263,54,12035,GAPITester1235a,"test@iana-.com",32795,5,,no
      //AccountPayDirect,263,55,12035,GAPITester1235a,"test@.iana.org",32795,5,,no
      //AccountPayDirect,263,56,12035,GAPITester1235a,"test@iana.org.",32795,5,,no
      //AccountPayDirect,263,57,12035,GAPITester1235a,"test@iana..com",32795,5,,no
      //AccountPayDirect,263,58,12035,GAPITester1235a,"abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghiklm@abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghikl.abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghikl.abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghij",32795,5,,no
      //AccountPayDirect,263,59,12035,GAPITester1235a,"a@abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghikl.abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghikl.abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghikl.abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefg.hijk",32795,5,,no
      //AccountPayDirect,263,60,12035,GAPITester1235a,"test"@iana.org",32795,5,,no
      //AccountPayDirect,263,61,12035,GAPITester1235a,""test@iana.org",32795,5,,no
      //AccountPayDirect,263,62,12035,GAPITester1235a,"(comment)test@iana.org",32795,5,,no
      //AccountPayDirect,263,63,12035,GAPITester1235a,"test@(comment)iana.org",32795,5,,no
      //AccountPayDirect,263,64,12035,GAPITester1235a,"test@iana.org-",32795,5,,no
      //AccountPayDirect,263,65,12035,GAPITester1235a,"(test@iana.org",32795,5,,no
      //AccountPayDirect,263,66,12035,GAPITester1235a,"test@(iana.org",32795,5,,no
      //AccountPayDirect,263,67,12035,GAPITester1235a,"test@[1.2.3.4",32795,5,,no
      testCases.add(
          AccountPayDirect.createValid(
              dataHelper.getReferenceId(),
              "Test case 39-67: Invalid email - " + email,
              getExpectedResponse(gatewayErrors, "263"),
              payerId,
              payeeReferenceId,
              gatewayPayeeId)
              .setPayeeEmailAddress(email)
      );
    }

    executeTests(testCases);
  }

  @Test(groups = {"gapi", "AccountPayDirect"})
  public void largeBatchValidation() {
    Logger.info("Account Pay Direct Large Batch validation test");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String payerId = testSetupPage.getString("payerId");
    final String gatewayPayeeId = testSetupPage.getString("gatewayPayeeId");
    final String payeeReferenceId = testSetupPage.getString("payeeReferenceId");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    DataHelper dataHelper = new DataHelper();
    TestCaseCollection testCases = new GapiTestCaseCollection(
        new Credentials(gatewayId, username, password)
    );

    for (int i = 0; i < 100; i++) {

      //AccountPayDirect,2,1,12035,GAPITester1235a,NULL,32795,LARGE_BATCH,100,,no
      testCases.add(
          AccountPayDirect.createValid(
              dataHelper.getReferenceId(),
              "Test case 1: Valid Large Batch",
              getExpectedResponse(gatewayErrors, "2"),
              payerId,
              payeeReferenceId,
              gatewayPayeeId)
      );
    }

    executeTests(testCases);
  }

  @Test(groups = {"gapi", "AccountPayDirect"})
  public void invalidLargeBatch()
      throws Exception {
    Logger.info("Account Pay Direct Invalid Large Batch test");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String payerId = testSetupPage.getString("payerId");
    final String gatewayPayeeId = testSetupPage.getString("gatewayPayeeId");
    final String payeeReferenceId = testSetupPage.getString("payeeReferenceId");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    Credentials credentials = new Credentials(gatewayId, username, password);
    GapiRequest gapiRequest = new GapiRequest(credentials);

    DataHelper dataHelper = new DataHelper();

    for (int i = 0; i < 101; i++) {
      AccountPayDirect.createValid(
          dataHelper.getReferenceId(),
          "Unused",
          getExpectedResponse(gatewayErrors, ""),
          payerId,
          payeeReferenceId,
          gatewayPayeeId).addTransaction(gapiRequest);
    }

    TestCaseCollection testCases = new GapiTestCaseCollection(credentials);

    //AccountPayDirect,261,39,12035,GAPITester1235a,,32795,LARGE_BATCH,101,,no
    testCases.add(
        new ErrorTestCase(
            "Test case 39",
            getExpectedResponse(gatewayErrors, "261")
        )
    );

    GapiResponse response = gapiRequest.sendRequest();

    Assert.assertTrue(isCollectionValid(testCases, response), "Large batch failed");
  }

  @Test(groups = {"gapi", "AccountPayDirect"})
  public void profitStars1() {
    Logger.info("Account Pay Direct Profit Stars test 1");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc2");
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String payerId = testSetupPage.getString("payerId");
    final String gatewayPayeeId = testSetupPage.getString("gatewayPayeeId");
    final String payeeReferenceId = testSetupPage.getString("payeeReferenceId");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    DataHelper dataHelper = new DataHelper();
    TestCaseCollection testCases = new GapiTestCaseCollection(
        new Credentials(gatewayId, username, password)
    );

    //AccountPayDirect,268,,56092,1499459154,,33190,12,,yes
    testCases.add(
        AccountPayDirect.createValid(
            dataHelper.getReferenceId(),
            "Test case 1",
            getExpectedResponse(gatewayErrors, "268"),
            payerId,
            payeeReferenceId,
            gatewayPayeeId)
    );

    executeTests(testCases);
  }

  @Test(groups = {"gapi", "AccountPayDirect"})
  public void profitStars2() {
    Logger.info("Account Pay Direct Profit Stars test 2");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc3");
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String payerId = testSetupPage.getString("payerId");
    final String gatewayPayeeId = testSetupPage.getString("gatewayPayeeId");
    final String payeeReferenceId = testSetupPage.getString("payeeReferenceId");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    DataHelper dataHelper = new DataHelper();
    TestCaseCollection testCases = new GapiTestCaseCollection(
        new Credentials(gatewayId, username, password)
    );

    //AccountPayDirect,269,,56092,1499464523,,33190,20,,yes
    testCases.add(
        AccountPayDirect.createValid(
            dataHelper.getReferenceId(),
            "Test case 2",
            getExpectedResponse(gatewayErrors, "269"),
            payerId,
            payeeReferenceId,
            gatewayPayeeId)
    );

    //AccountPayDirect,266,,56092,1499464523,,33190,20,QQ,yes
    testCases.add(
        AccountPayDirect.createValid(
            dataHelper.getReferenceId(),
            "Test case 3",
            getExpectedResponse(gatewayErrors, "266"),
            payerId,
            payeeReferenceId,
            gatewayPayeeId)
            .setPayeeState("QQ")
    );

    //AccountPayDirect,265,,56092,1499464523,,33190,20, ,yes
    testCases.add(
        AccountPayDirect.createValid(
            dataHelper.getReferenceId(),
            "Test case 4",
            getExpectedResponse(gatewayErrors, "265"),
            payerId,
            payeeReferenceId,
            gatewayPayeeId)
            .setPayeeState(" ")
    );

    executeTests(testCases);
  }

  @Test(groups = {"gapi", "AccountPayDirect"})
  public void profitStars3() {
    Logger.info("Account Pay Direct Profit Stars test 3");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc4");
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String payerId = testSetupPage.getString("payerId");
    final String gatewayPayeeId = testSetupPage.getString("gatewayPayeeId");
    final String payeeReferenceId = testSetupPage.getString("payeeReferenceId");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    DataHelper dataHelper = new DataHelper();
    TestCaseCollection testCases = new GapiTestCaseCollection(
        new Credentials(gatewayId, username, password)
    );

    //AccountPayDirect,2,,56092,1499466786,,33190,20,,yes
    testCases.add(
        AccountPayDirect.createValid(
            dataHelper.getReferenceId(),
            "Test case 5",
            getExpectedResponse(gatewayErrors, "2"),
            payerId,
            payeeReferenceId,
            gatewayPayeeId)
    );

    //AccountPayDirect,2,,56092,1499466786,,33190,20,LA,yes
    testCases.add(
        AccountPayDirect.createValid(
            dataHelper.getReferenceId(),
            "Test case 6",
            getExpectedResponse(gatewayErrors, "2"),
            payerId,
            payeeReferenceId,
            gatewayPayeeId)
            .setPayeeState("LA")
    );

    executeTests(testCases);
  }
}
