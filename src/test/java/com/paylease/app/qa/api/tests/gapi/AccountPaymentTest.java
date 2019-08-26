package com.paylease.app.qa.api.tests.gapi;

import com.paylease.app.qa.api.tests.TestCaseCollection;
import com.paylease.app.qa.api.tests.gapi.testcase.AccountPayment;
import com.paylease.app.qa.api.tests.gapi.testcase.CcPayment;
import com.paylease.app.qa.api.tests.gapi.testcase.ErrorTestCase;
import com.paylease.app.qa.framework.DataHelper;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.api.Credentials;
import com.paylease.app.qa.framework.api.gapi.GapiRequest;
import com.paylease.app.qa.framework.api.gapi.GapiResponse;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.automatedhelper.TransactionPage;
import com.paylease.app.qa.framework.pages.automatedhelper.TransactionProcessingPage;
import java.util.HashMap;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;


public class AccountPaymentTest extends BaseTest {

  private static final String REGION = "gapi";
  private static final String FEATURE = "AccountPayment";

  //-----------------------------------TESTS----------------------------------------------

  @Test(groups = {"gapi", "AccountPayment"})
  public void accountPayment1() {
    Logger.info("Account Payment Test");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String payeeId = testSetupPage.getString("payeeId");
    final String gatewayPayerId = testSetupPage.getString("gatewayPayerId");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    DataHelper dataHelper = new DataHelper();
    TestCaseCollection testCases = new GapiTestCaseCollection(
        new Credentials(gatewayId, username, password)
    );

    //AccountPayment,2,7,GAPITester1235a,12035,860119,1500.00,,,
    testCases.add(
        new AccountPayment(
            dataHelper.getReferenceId(),
            "Test case 7",
            getExpectedResponse(gatewayErrors, "2"),
            payeeId,
            gatewayPayerId)
            .setTotalAmount("1500.00")
            .setFeeAmount("")
            .setIncurFee("")
            .setCheckScanned("")
    );

    //AccountPayment,2,8,GAPITester1235a,12035,860119,100.00,,,
    testCases.add(
        new AccountPayment(
            dataHelper.getReferenceId(),
            "Test case 8",
            getExpectedResponse(gatewayErrors, "2"),
            payeeId,
            gatewayPayerId)
            .setTotalAmount("100.00")
            .setFeeAmount("")
            .setIncurFee("")
            .setCheckScanned("")
    );

    //AccountPayment,2,9,GAPITester1235a,12035,860119,100.00,0,No,
    testCases.add(
        new AccountPayment(
            dataHelper.getReferenceId(),
            "Test case 9",
            getExpectedResponse(gatewayErrors, "2"),
            payeeId,
            gatewayPayerId)
            .setTotalAmount("100.00")
            .setFeeAmount("0")
            .setIncurFee("No")
            .setCheckScanned("")
    );

    //AccountPayment,2,10,GAPITester1235a,12035,860119,100.00,6.45,,No
    testCases.add(
        new AccountPayment(
            dataHelper.getReferenceId(),
            "Test case 10",
            getExpectedResponse(gatewayErrors, "2"),
            payeeId,
            gatewayPayerId)
            .setTotalAmount("100.00")
            .setFeeAmount("6.45")
            .setIncurFee("")
            .setCheckScanned("No")
    );

    //AccountPayment,2,11,GAPITester1235a,12035,860119,5.95,2.95,No,No
    testCases.add(
        new AccountPayment(
            dataHelper.getReferenceId(),
            "Test case 11",
            getExpectedResponse(gatewayErrors, "2"),
            payeeId,
            gatewayPayerId)
            .setTotalAmount("5.95")
            .setFeeAmount("2.95")
            .setIncurFee("No")
            .setCheckScanned("No")
    );

    //AccountPayment,2,12,GAPITester1235a,12035,860119,35,2.95,Yes,
    testCases.add(
        new AccountPayment(
            dataHelper.getReferenceId(),
            "Test case 12",
            getExpectedResponse(gatewayErrors, "2"),
            payeeId,
            gatewayPayerId)
            .setTotalAmount("35")
            .setFeeAmount("2.95")
            .setIncurFee("Yes")
            .setCheckScanned("")
    );

    //AccountPayment,2,13,GAPITester1235a,12035,860119,590.95,2.95,,Yes
    testCases.add(
        new AccountPayment(
            dataHelper.getReferenceId(),
            "Test case 13",
            getExpectedResponse(gatewayErrors, "2"),
            payeeId,
            gatewayPayerId)
            .setTotalAmount("590.95")
            .setFeeAmount("2.95")
            .setIncurFee("")
            .setCheckScanned("Yes")
    );

    //AccountPayment,2,14,GAPITester1235a,12035,860119,1000,2.95,Yes,Yes
    testCases.add(
        new AccountPayment(
            dataHelper.getReferenceId(),
            "Test case 14",
            getExpectedResponse(gatewayErrors, "2"),
            payeeId,
            gatewayPayerId)
            .setTotalAmount("1000")
            .setFeeAmount("2.95")
            .setIncurFee("Yes")
            .setCheckScanned("Yes")
    );

    //AccountPayment,2,15,GAPITester1235a,12035,860119,1000.00,2.95,No,Yes
    testCases.add(
        new AccountPayment(
            dataHelper.getReferenceId(),
            "Test case 15",
            getExpectedResponse(gatewayErrors, "2"),
            payeeId,
            gatewayPayerId)
            .setTotalAmount("1000.00")
            .setFeeAmount("2.95")
            .setIncurFee("No")
            .setCheckScanned("Yes")
    );

    //AccountPayment,2,16,GAPITester1235a,12035,860119,1002.95,2.95,Yes,No
    testCases.add(
        new AccountPayment(
            dataHelper.getReferenceId(),
            "Test case 16",
            getExpectedResponse(gatewayErrors, "2"),
            payeeId,
            gatewayPayerId)
            .setTotalAmount("1002.95")
            .setFeeAmount("2.95")
            .setIncurFee("Yes")
            .setCheckScanned("No")
    );

    //AccountPayment,69,35,,12035,860124,5.95,,No,No
    testCases.add(
        AccountPayment.createValid(
            dataHelper.getReferenceId(),
            "Test case 35 - Empty PayerReferenceId",
            getExpectedResponse(gatewayErrors, "69"),
            payeeId,
            gatewayPayerId)
            .setPayerReferenceId("")
    );

    //AccountPayment,70,36,GAPITester1235a,,860124,5,0.00,,
    testCases.add(
        AccountPayment.createValid(
            dataHelper.getReferenceId(),
            "Test case 36 - Empty PayeeId",
            getExpectedResponse(gatewayErrors, "70"),
            "",
            gatewayPayerId)
    );

    String[] validIncurFee = {
        "", "No", "Yes",
    };

    String[] validCheckScanned = {
        "", "No", "Yes",
    };

    for (String incurFee : validIncurFee) {
      for (String checkScanned : validCheckScanned) {
        //AccountPayment,85,37,GAPITester1235a,12035,864249,,2.95,,
        //AccountPayment,85,38,GAPITester1235a,12035,864249,,2.95,No,
        //AccountPayment,85,39,GAPITester1235a,12035,864249,,2.95,,No
        //AccountPayment,85,40,GAPITester1235a,12035,864249,,2.95,No,No
        //AccountPayment,85,41,GAPITester1235a,12035,864249,,2.95,Yes,
        //AccountPayment,85,42,GAPITester1235a,12035,864249,,2.95,,Yes
        //AccountPayment,85,43,GAPITester1235a,12035,864249,,2.95,Yes,Yes
        //AccountPayment,85,44,GAPITester1235a,12035,864249,,2.95,No,Yes
        //AccountPayment,85,45,GAPITester1235a,12035,864249,,2.95,Yes,No
        testCases.add(
            AccountPayment.createValid(
                dataHelper.getReferenceId(),
                "Test case 37-45 - Empty Total Amount with IncurFee " + incurFee
                    + " and CheckScanned " + checkScanned,
                getExpectedResponse(gatewayErrors, "85"),
                payeeId,
                gatewayPayerId)
                .setTotalAmount("")
                .setIncurFee(incurFee)
                .setCheckScanned(checkScanned)
        );
      }
    }

    //AccountPayment,91,46,GAPITester1235a,12035,,,2.95,Yes,No
    testCases.add(
        AccountPayment.createValid(
            dataHelper.getReferenceId(),
            "Test case 46 - Empty GatewayPayerId",
            getExpectedResponse(gatewayErrors, "91"),
            payeeId,
            "")
    );

    //AccountPayment,111,47,a134oihqf9w7h2ufhqiusd872q45y92715yr7q8wgfasyfasf,12035,860124,1000.00,,No,Yes
    testCases.add(
        AccountPayment.createValid(
            dataHelper.getReferenceId(),
            "Test case 47 - Long PayerReferenceId",
            getExpectedResponse(gatewayErrors, "111"),
            payeeId,
            gatewayPayerId)
            .setPayerReferenceId("a134oihqf9w7h2ufhqiusd872q45y92715yr7q8wgfasyfasf")
    );

    String[] invalidPayeeIds = {
        "12035a", "120 35", "120-35", "120.35",
    };

    for (String invalidPayeeId : invalidPayeeIds) {
      //AccountPayment,130,48,GAPITester1235a,12035a,864249,5,2.95,,
      //AccountPayment,130,49,GAPITester1235a,120 35,864249,5.00,2.95,No,
      //AccountPayment,130,50,GAPITester1235a,120-35,864249,5.01,2.95,,No
      //AccountPayment,130,51,GAPITester1235a,120.35,864249,5.95,2.95,No,No
      testCases.add(
          AccountPayment.createValid(
              dataHelper.getReferenceId(),
              "Test case 48-51 - InvalidPayeeId",
              getExpectedResponse(gatewayErrors, "130"),
              invalidPayeeId,
              gatewayPayerId)
      );
    }

    String[] invalidTotalAmounts = {
        "2000. 003", "2000 3", "2000 03", "2000. 3", "2000. 03", "2000 .3", "2000 .03", "2000.",
        ".0", ".00", "1.0a",
    };

    for (String totalAmount : invalidTotalAmounts) {
      //AccountPayment,133,52,GAPITester1235a,12035,864249,2000. 003,2.95,,
      //AccountPayment,133,53,GAPITester1235a,12035,864249,2000 3,2.95,No,
      //AccountPayment,133,54,GAPITester1235a,12035,864249,2000 03,2.95,,No
      //AccountPayment,133,55,GAPITester1235a,12035,864249,2000. 3,2.95,No,No
      //AccountPayment,133,56,GAPITester1235a,12035,864249,2000. 03,2.95,Yes,
      //AccountPayment,133,57,GAPITester1235a,12035,864249,2000 .3,2.95,,Yes
      //AccountPayment,133,58,GAPITester1235a,12035,864249,2000 .03,2.95,Yes,Yes
      //AccountPayment,133,59,GAPITester1235a,12035,864249,2000.,2.95,No,Yes
      //AccountPayment,133,60,GAPITester1235a,12035,864249,.0,2.95,Yes,No
      //AccountPayment,133,61,GAPITester1235a,12035,864249,.00,0.00,,
      //AccountPayment,133,62,GAPITester1235a,12035,864249,1.0a,0.00,No,
      testCases.add(
          AccountPayment.createValid(
              dataHelper.getReferenceId(),
              "Test case 52-62 - Invalid Total Amount",
              getExpectedResponse(gatewayErrors, "133"),
              payeeId,
              gatewayPayerId)
              .setTotalAmount(totalAmount)
      );
    }

    String[] invalidPayerIds = {
        "864249a", "860 124", "860-124", "860.124",
    };

    for (String payerId : invalidPayerIds) {
      //AccountPayment,140,63,GAPITester1235a,12035,864249a,5,2.95,,
      //AccountPayment,140,64,GAPITester1235a,12035,860 124,5.00,2.95,No,
      //AccountPayment,140,65,GAPITester1235a,12035,860-124,5.01,2.95,,No
      //AccountPayment,140,66,GAPITester1235a,12035,860.124,5.95,2.95,No,No
      testCases.add(
          AccountPayment.createValid(
              dataHelper.getReferenceId(),
              "Test case 63-66 - Invalid GatewayPayerIds",
              getExpectedResponse(gatewayErrors, "140"),
              payeeId,
              payerId)
      );
    }

    //AccountPayment,149,67,GAPITester1235a,120356789,857530,1002.95,2.95,Yes,No
    testCases.add(
        AccountPayment.createValid(
            dataHelper.getReferenceId(),
            "Test case 67 - Incorrect PayeeId",
            getExpectedResponse(gatewayErrors, "149"),
            "120356789",
            gatewayPayerId)
    );

    //AccountPayment,152,68,GAPITester1235a,12035,123456789,5.95,2.95,No,No
    testCases.add(
        AccountPayment.createValid(
            dataHelper.getReferenceId(),
            "Test case 68 - Incorrect GatewayPayerId",
            getExpectedResponse(gatewayErrors, "152"),
            payeeId,
            "123456789")
    );

    executeTests(testCases);
  }

  @Test(groups = {"gapi", "AccountPayment", "litle"})
  public void accountPayment2() {
    Logger.info("Account Payment Test - Credit Card");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc2");
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String payeeId = testSetupPage.getString("payeeId");
    final String gatewayPayerId = testSetupPage.getString("gatewayPayerId");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    DataHelper dataHelper = new DataHelper();
    TestCaseCollection testCases = new GapiTestCaseCollection(
        new Credentials(gatewayId, username, password)
    );

    //AccountPayment,8,17,GAPITester1235a,12035,864249,5,0.00,,
    testCases.add(
        new AccountPayment(
            dataHelper.getReferenceId(),
            "Test case 17",
            getExpectedResponse(gatewayErrors, "8"),
            payeeId,
            gatewayPayerId)
            .setTotalAmount("5")
            .setFeeAmount("0.00")
            .setIncurFee("")
            .setCheckScanned("")
    );

    //AccountPayment,8,18,GAPITester1235a,12035,864249,5.00,0.00,No,
    testCases.add(
        new AccountPayment(
            dataHelper.getReferenceId(),
            "Test case 18",
            getExpectedResponse(gatewayErrors, "8"),
            payeeId,
            gatewayPayerId)
            .setTotalAmount("5.00")
            .setFeeAmount("0.00")
            .setIncurFee("No")
            .setCheckScanned("")
    );

    //AccountPayment,8,19,GAPITester1235a,12035,864249,5.01,0.00,,No
    testCases.add(
        new AccountPayment(
            dataHelper.getReferenceId(),
            "Test case 19",
            getExpectedResponse(gatewayErrors, "8"),
            payeeId,
            gatewayPayerId)
            .setTotalAmount("5.01")
            .setFeeAmount("0.00")
            .setIncurFee("")
            .setCheckScanned("No")
    );

    //AccountPayment,8,20,GAPITester1235a,12035,864249,5.95,0.00,No,No
    testCases.add(
        new AccountPayment(
            dataHelper.getReferenceId(),
            "Test case 20",
            getExpectedResponse(gatewayErrors, "8"),
            payeeId,
            gatewayPayerId)
            .setTotalAmount("5.95")
            .setFeeAmount("0.00")
            .setIncurFee("No")
            .setCheckScanned("No")
    );

    //AccountPayment,8,21,GAPITester1235a,12035,864249,35,0.00,Yes,
    testCases.add(
        new AccountPayment(
            dataHelper.getReferenceId(),
            "Test case 21",
            getExpectedResponse(gatewayErrors, "8"),
            payeeId,
            gatewayPayerId)
            .setTotalAmount("35")
            .setFeeAmount("0.00")
            .setIncurFee("Yes")
            .setCheckScanned("")
    );

    //AccountPayment,8,22,GAPITester1235a,12035,864249,590.95,0.00,,Yes
    testCases.add(
        new AccountPayment(
            dataHelper.getReferenceId(),
            "Test case 22",
            getExpectedResponse(gatewayErrors, "8"),
            payeeId,
            gatewayPayerId)
            .setTotalAmount("590.95")
            .setFeeAmount("0.00")
            .setIncurFee("")
            .setCheckScanned("Yes")
    );

    //AccountPayment,8,23,GAPITester1235a,12035,864249,1000,0.00,Yes,Yes
    testCases.add(
        new AccountPayment(
            dataHelper.getReferenceId(),
            "Test case 23",
            getExpectedResponse(gatewayErrors, "8"),
            payeeId,
            gatewayPayerId)
            .setTotalAmount("1000")
            .setFeeAmount("0.00")
            .setIncurFee("Yes")
            .setCheckScanned("Yes")
    );

    //AccountPayment,8,24,GAPITester1235a,12035,864249,1000.00,0.00,No,Yes
    testCases.add(
        new AccountPayment(
            dataHelper.getReferenceId(),
            "Test case 24",
            getExpectedResponse(gatewayErrors, "8"),
            payeeId,
            gatewayPayerId)
            .setTotalAmount("1000.00")
            .setFeeAmount("0.00")
            .setIncurFee("No")
            .setCheckScanned("Yes")
    );

    //AccountPayment,8,25,GAPITester1235a,12035,864249,1002.95,0.00,Yes,No
    testCases.add(
        new AccountPayment(
            dataHelper.getReferenceId(),
            "Test case 25",
            getExpectedResponse(gatewayErrors, "8"),
            payeeId,
            gatewayPayerId)
            .setTotalAmount("1002.95")
            .setFeeAmount("0.00")
            .setIncurFee("Yes")
            .setCheckScanned("No")
    );

    //AccountPayment,8,26,GAPITester1235a,12035,864249,5,,,
    testCases.add(
        new AccountPayment(
            dataHelper.getReferenceId(),
            "Test case 26",
            getExpectedResponse(gatewayErrors, "8"),
            payeeId,
            gatewayPayerId)
            .setTotalAmount("5")
            .setFeeAmount("")
            .setIncurFee("")
            .setCheckScanned("")
    );

    //AccountPayment,8,27,GAPITester1235a,12035,864249,5.00,,No,
    testCases.add(
        new AccountPayment(
            dataHelper.getReferenceId(),
            "Test case 27",
            getExpectedResponse(gatewayErrors, "8"),
            payeeId,
            gatewayPayerId)
            .setTotalAmount("5.00")
            .setFeeAmount("")
            .setIncurFee("")
            .setCheckScanned("No")
    );

    //AccountPayment,8,28,GAPITester1235a,12035,864249,5.01,,,No
    testCases.add(
        new AccountPayment(
            dataHelper.getReferenceId(),
            "Test case 28",
            getExpectedResponse(gatewayErrors, "8"),
            payeeId,
            gatewayPayerId)
            .setTotalAmount("5.01")
            .setFeeAmount("")
            .setIncurFee("")
            .setCheckScanned("No")
    );

    //AccountPayment,8,29,GAPITester1235a,12035,864249,5.95,,No,No
    testCases.add(
        new AccountPayment(
            dataHelper.getReferenceId(),
            "Test case 29",
            getExpectedResponse(gatewayErrors, "8"),
            payeeId,
            gatewayPayerId)
            .setTotalAmount("5.95")
            .setFeeAmount("")
            .setIncurFee("No")
            .setCheckScanned("No")
    );

    //AccountPayment,8,30,GAPITester1235a,12035,864249,35,,Yes,
    testCases.add(
        new AccountPayment(
            dataHelper.getReferenceId(),
            "Test case 30",
            getExpectedResponse(gatewayErrors, "8"),
            payeeId,
            gatewayPayerId)
            .setTotalAmount("35")
            .setFeeAmount("")
            .setIncurFee("Yes")
            .setCheckScanned("")
    );

    //AccountPayment,8,31,GAPITester1235a,12035,864249,590.95,,,Yes
    testCases.add(
        new AccountPayment(
            dataHelper.getReferenceId(),
            "Test case 31",
            getExpectedResponse(gatewayErrors, "8"),
            payeeId,
            gatewayPayerId)
            .setTotalAmount("590.95")
            .setFeeAmount("")
            .setIncurFee("")
            .setCheckScanned("Yes")
    );

    //AccountPayment,8,32,GAPITester1235a,12035,864249,1000,,Yes,Yes
    testCases.add(
        new AccountPayment(
            dataHelper.getReferenceId(),
            "Test case 32",
            getExpectedResponse(gatewayErrors, "8"),
            payeeId,
            gatewayPayerId)
            .setTotalAmount("1000")
            .setFeeAmount("")
            .setIncurFee("Yes")
            .setCheckScanned("Yes")
    );

    //AccountPayment,8,33,GAPITester1235a,12035,864249,1000.00,,No,Yes
    testCases.add(
        new AccountPayment(
            dataHelper.getReferenceId(),
            "Test case 33",
            getExpectedResponse(gatewayErrors, "8"),
            payeeId,
            gatewayPayerId)
            .setTotalAmount("1000.00")
            .setFeeAmount("")
            .setIncurFee("No")
            .setCheckScanned("Yes")
    );

    // AccountPayment,8,34,GAPITester1235a,12035,864249,1002.95,,Yes,No
    testCases.add(
        new AccountPayment(
            dataHelper.getReferenceId(),
            "Test case 34",
            getExpectedResponse(gatewayErrors, "8"),
            payeeId,
            gatewayPayerId)
            .setTotalAmount("1002.95")
            .setFeeAmount("")
            .setIncurFee("Yes")
            .setCheckScanned("No")
    );

    executeTests(testCases);
  }

  @Test(groups = {"gapi", "AccountPayment"})
  public void largeBatchValidation() {
    Logger.info("Account Payment Test - Large Batch");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String payeeId = testSetupPage.getString("payeeId");
    final String gatewayPayerId = testSetupPage.getString("gatewayPayerId");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    DataHelper dataHelper = new DataHelper();
    TestCaseCollection testCases = new GapiTestCaseCollection(
        new Credentials(gatewayId, username, password)
    );
    for (int i = 0; i < 5; i++) {

      //AccountPayment,2,1,GAPITester1235a,12035,860119,LARGE_BATCH,5,,
      testCases.add(
          AccountPayment.createValid(
              dataHelper.getReferenceId(),
              "Test case 1",
              getExpectedResponse(gatewayErrors, "2"),
              payeeId,
              gatewayPayerId)
      );
    }

    executeTests(testCases);
  }

  @Test(groups = {"gapi", "AccountPayment", "litle"})
  public void largeBatchValidationMasterCard() {
    Logger.info("Account Payment Test - Large Batch Master Card");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc2");
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String payeeId = testSetupPage.getString("payeeId");
    final String gatewayPayerId = testSetupPage.getString("gatewayPayerId");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    DataHelper dataHelper = new DataHelper();
    TestCaseCollection testCases = new GapiTestCaseCollection(
        new Credentials(gatewayId, username, password)
    );
    for (int i = 0; i < 5; i++) {

      //AccountPayment,8,2,GAPITesterCC,12035,2314856,LARGE_BATCH,5,,
      testCases.add(
          AccountPayment.createValid(
              dataHelper.getReferenceId(),
              "Test case 2",
              getExpectedResponse(gatewayErrors, "8"),
              payeeId,
              gatewayPayerId)
      );
    }

    executeTests(testCases);
  }

  @Test(groups = {"gapi", "AccountPayment", "litle"})
  public void largeBatchValidationVisa() {
    Logger.info("Account Payment Test - Large Batch Visa");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc3");
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String payeeId = testSetupPage.getString("payeeId");
    final String gatewayPayerId = testSetupPage.getString("gatewayPayerId");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    DataHelper dataHelper = new DataHelper();
    TestCaseCollection testCases = new GapiTestCaseCollection(
        new Credentials(gatewayId, username, password)
    );
    for (int i = 0; i < 5; i++) {

      //AccountPayment,8,3,GAPITesterCC,12035,1817297,LARGE_BATCH,5,,
      testCases.add(
          AccountPayment.createValid(
              dataHelper.getReferenceId(),
              "Test case 2",
              getExpectedResponse(gatewayErrors, "8"),
              payeeId,
              gatewayPayerId)
      );
    }

    executeTests(testCases);
  }

  @Test(groups = {"gapi", "AccountPayment", "litle"})
  public void largeBatchValidationAmex() {
    Logger.info("Account Payment Test - Large Batch AMEX");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc4");
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String payeeId = testSetupPage.getString("payeeId");
    final String gatewayPayerId = testSetupPage.getString("gatewayPayerId");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    DataHelper dataHelper = new DataHelper();
    TestCaseCollection testCases = new GapiTestCaseCollection(
        new Credentials(gatewayId, username, password)
    );
    for (int i = 0; i < 5; i++) {

      //AccountPayment,8,4,GAPITesterCC,12035,2321471,LARGE_BATCH,5,,
      testCases.add(
          AccountPayment.createValid(
              dataHelper.getReferenceId(),
              "Test case 2",
              getExpectedResponse(gatewayErrors, "8"),
              payeeId,
              gatewayPayerId)
      );
    }

    executeTests(testCases);
  }

  @Test(groups = {"gapi", "AccountPayment", "litle"})
  public void largeBatchValidationDiscover() {
    Logger.info("Account Payment Test - Large Batch Discover");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc5");
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String payeeId = testSetupPage.getString("payeeId");
    final String gatewayPayerId = testSetupPage.getString("gatewayPayerId");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    DataHelper dataHelper = new DataHelper();
    TestCaseCollection testCases = new GapiTestCaseCollection(
        new Credentials(gatewayId, username, password)
    );
    for (int i = 0; i < 5; i++) {

      //AccountPayment,8,5,GAPITesterCC,12035,2321472,LARGE_BATCH,5,,
      testCases.add(
          AccountPayment.createValid(
              dataHelper.getReferenceId(),
              "Test case 2",
              getExpectedResponse(gatewayErrors, "8"),
              payeeId,
              gatewayPayerId)
      );
    }

    executeTests(testCases);
  }

  @Test(groups = {"gapi", "AccountPayment"})
  public void invalidLargeBatchCreditCard() throws Exception {
    Logger.info("Account Payment Test Invalid Large Batch");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc2");
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String payeeId = testSetupPage.getString("payeeId");
    final String gatewayPayerId = testSetupPage.getString("gatewayPayerId");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    Credentials credentials = new Credentials(gatewayId, username, password);
    GapiRequest gapiRequest = new GapiRequest(credentials);

    DataHelper dataHelper = new DataHelper();

    for (int i = 0; i < 101; i++) {
      AccountPayment.createValid(
          dataHelper.getReferenceId(),
          "Unused",
          getExpectedResponse(gatewayErrors, ""),
          payeeId,
          gatewayPayerId).addTransaction(gapiRequest);
    }

    TestCaseCollection testCases = new GapiTestCaseCollection(credentials);

    //AccountPayment,261,70,GAPITesterCC,12035,2314856,LARGE_BATCH,101,,
    testCases.add(
        new ErrorTestCase(
            "Test case 70",
            getExpectedResponse(gatewayErrors, "261")
        )
    );

    GapiResponse response = gapiRequest.sendRequest();

    Assert.assertTrue(isCollectionValid(testCases, response), "Large batch failed");
  }

  @Test(groups = {"gapi", "AccountPayment", "paysafe"})
  public void ccSplitPaymentDifferentPropertyCad() throws Exception {
    Logger.info("AccountPayment - CC Split Payment Test for different properties - Paysafe");

    testCcSplitPaymentCad("tc3607");
  }

  @Test(groups = {"gapi", "AccountPayment", "paysafe"})
  public void ccSplitPaymentSamePropertyCad() throws Exception {
    Logger.info("AccountPayment - CC Split Payment Test for same properties - Paysafe");

    testCcSplitPaymentCad("tc3608");
  }

  @Test(groups = {"gapi", "AccountPayment", "paysafe"})
  public void testCcCad() throws Exception {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc3609");
    testSetupPage.open();

    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String payeeId = testSetupPage.getString("payeeId");
    final String gatewayPayerId = testSetupPage.getString("gatewayPayerId");
    final String bankAccountId = testSetupPage.getString("bankAccountId");
    final String totalAmount = "0.03";

    DataHelper dataHelper = new DataHelper();
    GapiRequest gapiRequest = new GapiRequest(new Credentials(gatewayId, username, password));

    AccountPayment testCase = AccountPayment.createValid(
        dataHelper.getReferenceId(),
        "Test Case - CC Split Payment Test - Paysafe",
        null,
        payeeId,
        gatewayPayerId)
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
    TransactionProcessingPage processingPage = transactionPage.getDataForTransactionId(transId);

    Assert.assertTrue(processingPage.isPayoutPresent(bankAccountId, totalAmount),
        "BankAccount payout should be present");
  }

  private void testCcSplitPaymentCad(String testCaseName) throws Exception {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCaseName);
    testSetupPage.open();

    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String payeeId1 = testSetupPage.getString("payeeId1");
    final String payeeId2 = testSetupPage.getString("payeeId2");
    final String gatewayPayerId = testSetupPage.getString("gatewayPayerId");
    final String bankAccountId1 = testSetupPage.getString("bankAccountId1");
    final String bankAccountId2 = testSetupPage.getString("bankAccountId2");
    final String amount1 = "0.01";
    final String amount2 = "0.02";
    final String totalAmount = "0.03";

    DataHelper dataHelper = new DataHelper();
    GapiRequest gapiRequest = new GapiRequest(new Credentials(gatewayId, username, password));

    AccountPayment testCase = AccountPayment.createValid(
        dataHelper.getReferenceId(),
        "Test Case - CC Split Payment Test - Paysafe",
        null,
        payeeId1,
        gatewayPayerId)
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
    TransactionProcessingPage processingPage = transactionPage.getDataForTransactionId(transId);

    Assert.assertTrue(processingPage.isPayoutPresent(bankAccountId1, amount1),
        "BankAccount1 payout should be present");
    Assert.assertTrue(processingPage.isPayoutPresent(bankAccountId2, amount2),
        "BankAccount2 payout should be present");
  }

  @Test(groups = {"gapi", "AccountPayment", "litle"})
  public void ccSplitPaymentDifferentPropertyUsd() {
    Logger.info("AccountPayment - CC Payment Test - Litle");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc3613");
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String gatewayPayerId = testSetupPage.getString("gatewayPayerId");
    final String payeeId = testSetupPage.getString("payeeId");
    final String payeeId1 = testSetupPage.getString("payeeId1");
    final String payeeId2 = testSetupPage.getString("payeeId2");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    DataHelper dataHelper = new DataHelper();
    TestCaseCollection testCases = new GapiTestCaseCollection(
        new Credentials(gatewayId, username, password)
    );

    testCases.add(AccountPayment.createValid(
        dataHelper.getReferenceId(),
        "Test Case - CC payment with litle",
        getExpectedResponse(gatewayErrors, "8"),
        payeeId,
        gatewayPayerId)
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

  @Test(groups = {"gapi", "AccountPayment", "litle"})
  public void ccSplitPaymentSamePropertyUsd() {
    Logger.info("AccountPayment - CC Payment Test - Litle");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc3614");
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String gatewayPayerId = testSetupPage.getString("gatewayPayerId");
    final String payeeId = testSetupPage.getString("payeeId");
    final String payeeId1 = testSetupPage.getString("payeeId1");
    final String payeeId2 = testSetupPage.getString("payeeId2");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    DataHelper dataHelper = new DataHelper();
    TestCaseCollection testCases = new GapiTestCaseCollection(
        new Credentials(gatewayId, username, password)
    );

    testCases.add(AccountPayment.createValid(
        dataHelper.getReferenceId(),
        "Test Case - CC payment with litle",
        getExpectedResponse(gatewayErrors, "8"),
        payeeId,
        gatewayPayerId)
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
}
