package com.paylease.app.qa.functional.tests.pm;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.pm.DepositsAndDebitsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DepositsAndDebitsTest extends ScriptBase {

  private static final String REGION = "Pm";
  private static final String FEATURE = "depositsDebits";

  //--------------------------------YAVO BATCH ID TESTS-----------------------------------------

  @Test
  public void verifyBatchIdColumn() {
    Logger.info("Verify column name");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc7");
    testSetupPage.open();

    DepositsAndDebitsPage depositsAndDebits = new DepositsAndDebitsPage();
    depositsAndDebits.open();

    String columnLeftToAmount = depositsAndDebits.getColumnLeftToAmount();
    Assert.assertEquals("Batch ID", columnLeftToAmount,
        "Verifying Column left to the Amount column id Batch Id");
  }

  @Test
  public void verifyBatchId() {
    Logger.info("Verify batch Id");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc8");

    testSetupPage.open();
    String transId = testSetupPage.getString("transactionId");
    String batchId = testSetupPage.getString("batchId");

    DepositsAndDebitsPage depositsAndDebits = new DepositsAndDebitsPage();
    depositsAndDebits.open();

    depositsAndDebits.searchTransaction(transId);
    Assert.assertEquals(batchId, depositsAndDebits.getBatchId(),
        "Verifying the batch id on the page matches the batch id in the database");
  }

  @Test
  public void verifyBillTypeForTransaction() {
    Logger.info("View new PM UI deposits report with gateway transactions (GAPI) to see bill type");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc23");
    testSetupPage.open();

    final String transId = testSetupPage.getString("transId");
    final String paymentFieldName = testSetupPage.getString("paymentFieldName");

    DepositsAndDebitsPage depositsAndDebitsPage = new DepositsAndDebitsPage();
    depositsAndDebitsPage.open();
    depositsAndDebitsPage.clickExpandAll();

    String actualBillType = depositsAndDebitsPage.getBillType(transId);

    Assert.assertTrue(actualBillType.equalsIgnoreCase(paymentFieldName), "Bill type translated from payee id");
  }
}
