package com.paylease.app.qa.functional.tests.pm;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.pm.NonIntegratedTransactionsReportPage;
import java.util.HashMap;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NonIntegratedTransactionsReport extends ScriptBase {
  private static final String REGION = "pm";
  private static final String FEATURE = "NonIntegratedTransactionsReport";

  private void verifyFailedTransactions(String testCase) {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();

    final String pmId = testSetupPage.getString("pmId");
    Logger.info("PM Id: " + pmId);

    final List<HashMap<String, Object>> failed_tops_transactions = testSetupPage.getTable("failed_tops_transactions");

    NonIntegratedTransactionsReportPage reportPage = new NonIntegratedTransactionsReportPage();
    reportPage.open();

    for (HashMap<String, Object> item : failed_tops_transactions) {
      String transId = (String) item.get("transaction_id");
      Logger.info("Transaction Id: " + transId);

      String topsTransId = (String) item.get("tops_trans_id");
      Logger.info("Tops Transaction Id: " + topsTransId);

      String amount = (String) item.get("amount");
      Logger.info("Amount: " + amount);

      String id = transId + "-" + topsTransId;

      Assert.assertTrue(reportPage.transactionAmountMatches(id, amount));
      Assert.assertFalse(reportPage.transactionResponseIsVisible(id));
      reportPage.toggleShowResponse(id);
      Assert.assertTrue(reportPage.transactionResponseIsVisible(id));
      reportPage.toggleShowResponse(id);
      Assert.assertFalse(reportPage.transactionResponseIsVisible(id));
    }
  }

  @Test(groups = {"tops"})
  public void verifySingleTopsTransactionAppearsWhenFailedTC850() {

    Logger.info("Verify that a single tops transaction with a status of 'OTHER' shows up on the report");

    verifyFailedTransactions("tc850");

  }

  @Test(groups = {"tops"})
  public void verifySingleTopsTransactionAppearsWhenFailedTC853() {

    Logger.info("Verify that a single failed transaction shows up on the report");

    verifyFailedTransactions("tc853");

  }

  @Test(groups = {"tops"})
  public void verifySplitTopsTransactionAppearsWhenFailedTC857() {

    Logger.info("Verify that multiple failed tops transaction shows up on the report for single transaction with different line items");

    verifyFailedTransactions("tc857");
  }

  @Test(groups = {"tops"})
  public void verifyNoFailedTransactionsTC1462() {
    Logger.info("Verify that there are no failed transactions on the Tops Non Integrated" +
        "Transactions Report after Successfully submitting a single transaction");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1462");
    testSetupPage.open();

    final String pmId = testSetupPage.getString("pmId");

    Logger.info("PM Id: " + pmId);

    NonIntegratedTransactionsReportPage reportPage = new NonIntegratedTransactionsReportPage();
    reportPage.open();

    Assert.assertTrue(reportPage.hasNoNonIntegratedTransactions());

  }
}
