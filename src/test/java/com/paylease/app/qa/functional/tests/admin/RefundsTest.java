package com.paylease.app.qa.functional.tests.admin;

import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.admin.TransactionDetailPage;
import com.paylease.app.qa.framework.pages.admin.TransactionRefundPage;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RefundsTest extends ScriptBase {

  public static final String REGION = "admin";
  public static final String FEATURE = "refunds";

  @Test(groups = "litle")
  public void refundGatewayCcTransaction() {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1493");
    testSetupPage.open();

    final String transId = testSetupPage.getString("transId");

    TransactionDetailPage transactionDetailPage = new TransactionDetailPage(transId);
    transactionDetailPage.open();

    TransactionRefundPage transactionRefundPage = transactionDetailPage.clickVoidRefund();
    transactionRefundPage.submitRefund();

    Assert.assertNotEquals("Pending Refund", transactionDetailPage.getStatus(),
        "Status should not be 'Pending Refund'");
  }
}
