package com.paylease.app.qa.api.tests.gapi;

import com.paylease.app.qa.api.tests.TestCaseCollection;
import com.paylease.app.qa.api.tests.gapi.testcase.AchReturns;
import com.paylease.app.qa.api.tests.gapi.testcase.ReturnTransaction;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.api.Credentials;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import java.util.ArrayList;
import org.testng.annotations.Test;

public class AchReturnsTest extends BaseTest {

  private static final String REGION = "gapi";
  private static final String FEATURE = "AchReturns";

  @Test(groups = {"gapi", "AchReturns"})
  public void canadaEftRejection() {
    Logger.info("AchReturns - EFT rejected by paysafe");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "content_tc1");
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String transId = testSetupPage.getString("transId");

    TestCaseCollection testCases = new GapiTestCaseCollection(
        new Credentials(gatewayId, username, password)
    );

    ArrayList<ReturnTransaction> expectedReturns = new ArrayList<>();

    ReturnTransaction expectedReturn = new ReturnTransaction();
    expectedReturn.setTransactionId(transId);
    expectedReturn.setReturnCode("RJ");
    expectedReturn.setReturnStatus("Returned");
    expectedReturns.add(expectedReturn);
    testCases.add(
        new AchReturns(
            "EFT rejected by paysafe")
        .setTimeFrame("ALL")
        .setReturnTransactions(expectedReturns)
    );

    executeTests(testCases);
  }

  @Test(groups = {"gapi", "AchReturns"})
  public void canadaEftNsf() {
    Logger.info("AchReturns - EFT returns as NSF by paysafe");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "content_tc2");
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String transId = testSetupPage.getString("transId");
    final String returnCode = testSetupPage.getString("returnCode");

    TestCaseCollection testCases = new GapiTestCaseCollection(
        new Credentials(gatewayId, username, password)
    );

    ArrayList<ReturnTransaction> expectedReturns = new ArrayList<>();

    ReturnTransaction expectedReturn = new ReturnTransaction();
    expectedReturn.setTransactionId(transId);
    expectedReturn.setReturnCode(returnCode);
    expectedReturn.setReturnStatus("NSF");
    expectedReturns.add(expectedReturn);
    testCases.add(
        new AchReturns(
            "EFT returns NSF by paysafe")
            .setTimeFrame("ALL")
            .setReturnTransactions(expectedReturns)
    );

    executeTests(testCases);
  }

  @Test(groups = {"gapi", "AchReturns"})
  public void usAchNsf() {
    Logger.info("AchReturns - US ACH returns as NSF by Profitstars");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "content_tc3");
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String transId = testSetupPage.getString("transId");
    final String returnCode = testSetupPage.getString("returnCode");

    TestCaseCollection testCases = new GapiTestCaseCollection(
        new Credentials(gatewayId, username, password)
    );

    ArrayList<ReturnTransaction> expectedReturns = new ArrayList<>();

    ReturnTransaction expectedReturn = new ReturnTransaction();
    expectedReturn.setTransactionId(transId);
    expectedReturn.setReturnCode(returnCode);
    expectedReturn.setReturnStatus("NSF");
    expectedReturns.add(expectedReturn);
    testCases.add(
        new AchReturns(
            "ACH returns NSF by profitstars")
            .setTimeFrame("ALL")
            .setReturnTransactions(expectedReturns)
    );

    executeTests(testCases);
  }
}
