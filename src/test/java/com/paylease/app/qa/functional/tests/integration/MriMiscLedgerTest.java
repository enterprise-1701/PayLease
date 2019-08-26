package com.paylease.app.qa.functional.tests.integration;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.automatedhelper.MriTransactionPage;
import com.paylease.app.qa.framework.pages.automatedhelper.MriTransactionProcessingPage;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MriMiscLedgerTest extends ScriptBase {

  private static final String REGION = "integration";
  private static final String FEATURE = "MriMiscLedger";

  @Test
  public void tc3283() {
    Logger.info("Check Misc Ledger for Check payment");
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc3283");
    testSetupPage.open();

    String transId = testSetupPage.getString("transId");

    MriTransactionPage mriTransactionPage = new MriTransactionPage();
    mriTransactionPage.open();
    MriTransactionProcessingPage mriTransactionProcessingPage = mriTransactionPage
        .getDataForTransactionId(transId);

    String response = mriTransactionProcessingPage.getResponse();
    String status = mriTransactionProcessingPage.getStatus();
    Assert.assertTrue(response
            .contains("\"AccountNumber\":\"MR11010000\",\"Description\":\"Vending Machine 2nd Floor\""),
        "Should contain correct account num and description");
    Assert.assertTrue(response.contains("MiscReceiptsAdjustments"), "Should go to misc ledger");
    Assert.assertEquals(status, "SUCCESS", "Should integrate successfully");
  }

  @Test
  public void tc3286Cc() {
    Logger.info("Check Misc Ledger for CC payment");
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc3286Cc");
    testSetupPage.open();

    String transId = testSetupPage.getString("transId");

    MriTransactionPage mriTransactionPage = new MriTransactionPage();
    mriTransactionPage.open();
    MriTransactionProcessingPage mriTransactionProcessingPage = mriTransactionPage
        .getDataForTransactionId(transId);

    String response = mriTransactionProcessingPage.getResponse();
    String status = mriTransactionProcessingPage.getStatus();
    Assert.assertTrue(response.contains("MiscReceiptsAdjustments"), "Should go to misc ledger");
    Assert.assertEquals(status, "SUCCESS", "Should integrate successfully");

  }

  @Test
  public void tc3286Ach() {
    Logger.info("Check Misc Ledger for ACH payment");
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc3286Ach");
    testSetupPage.open();

    String transId = testSetupPage.getString("transId");

    MriTransactionPage mriTransactionPage = new MriTransactionPage();
    mriTransactionPage.open();
    MriTransactionProcessingPage mriTransactionProcessingPage = mriTransactionPage
        .getDataForTransactionId(transId);

    String response = mriTransactionProcessingPage.getResponse();
    String status = mriTransactionProcessingPage.getStatus();
    Assert.assertTrue(response.contains("MiscReceiptsAdjustments"), "Should go to misc ledger");
    Assert.assertEquals(status, "SUCCESS", "Should integrate successfully");
  }
}
