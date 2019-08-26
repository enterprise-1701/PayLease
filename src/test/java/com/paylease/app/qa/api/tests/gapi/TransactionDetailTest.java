
package com.paylease.app.qa.api.tests.gapi;

import com.paylease.app.qa.api.tests.TestCaseCollection;
import com.paylease.app.qa.api.tests.gapi.testcase.TransactionDetail;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.api.Credentials;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import java.util.HashMap;
import java.util.List;
import org.testng.annotations.Test;


public class TransactionDetailTest extends BaseTest {

  private static final String REGION = "gapi";
  private static final String FEATURE = "transactionDetail";

  //-----------------------------------TESTS----------------------------------------------

  @Test(groups = {"gapi", "TransactionDetail"})
  public void requestForReturned() {
    Logger.info("TransactionDetail request for transaction that has returned");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String transactionId = testSetupPage.getString("transactionId");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    TestCaseCollection testCases = new GapiTestCaseCollection(
        new Credentials(gatewayId, username, password)
    );

    //TransactionDetail,6,1,41839708,NULL
    testCases.add(
        new TransactionDetail(
            transactionId,
            "Test Case 1 - returned transaction",
            getExpectedResponse(gatewayErrors, "6"))
    );

    executeTests(testCases);
  }

  @Test(groups = {"gapi", "TransactionDetail"})
  public void requestValidation() {
    Logger.info("TransactionDetail request validation");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc2");
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String transactionId = testSetupPage.getString("transactionId");
    final String paymentReferenceId = testSetupPage.getString("paymentReferenceId");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    TestCaseCollection testCases = new GapiTestCaseCollection(
        new Credentials(gatewayId, username, password)
    );

    //TransactionDetail,3,2,71192889,1362821310
    testCases.add(
        new TransactionDetail(
            transactionId,
            "Test Case 2 - completed transaction by trans id",
            getExpectedResponse(gatewayErrors, "3"))
            .setPaymentReferenceId(paymentReferenceId)
    );

    //TransactionDetail,3,3,NULL,1362821310
    testCases.add(
        new TransactionDetail(
            null,
            "Test Case 3 - completed transaction by ref id",
            getExpectedResponse(gatewayErrors, "3"))
            .setPaymentReferenceId(paymentReferenceId)
    );

    //TransactionDetail,97,4,,
    testCases.add(
        new TransactionDetail(
            "",
            "Test Case 4 - empty trans id and ref id",
            getExpectedResponse(gatewayErrors, "97"))
            .setPaymentReferenceId("")
    );

    String[] invalidTransIds = {
        "a", "!", "1a", "1!", "a1", "!1", "1a1", "1!1", "1312 34241",
    };

    for (String transId : invalidTransIds) {
      //TransactionDetail,143,5,a,
      //TransactionDetail,143,6,!,
      //TransactionDetail,143,7,1a,
      //TransactionDetail,143,8,1!,
      //TransactionDetail,143,9,a1,
      //TransactionDetail,143,10,!1,
      //TransactionDetail,143,11,1a1,
      //TransactionDetail,143,12,1!1,
      //TransactionDetail,143,13,1312 34241,
      testCases.add(
          new TransactionDetail(
              transId,
              "Test Case 4-13 - invalid trans id",
              getExpectedResponse(gatewayErrors, "143"))
              .setPaymentReferenceId("")
      );
    }

    //TransactionDetail,154,14,13,
    testCases.add(
        new TransactionDetail(
            "14",
            "Test Case 14 - incorrect trans id",
            getExpectedResponse(gatewayErrors, "154"))
            .setPaymentReferenceId("13")
    );

    executeTests(testCases);
  }
}