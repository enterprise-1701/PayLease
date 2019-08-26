package com.paylease.app.qa.manual.tests.backend;

import static com.paylease.app.qa.testbase.dataproviders.PaymentProcessingDataProvider.BR_FN_SP;
import static com.paylease.app.qa.testbase.dataproviders.PaymentProcessingDataProvider.BR_PROCESS_CC;
import static com.paylease.app.qa.testbase.dataproviders.PaymentProcessingDataProvider.BR_PROCESS_CC_AND_BR_PT;
import static com.paylease.app.qa.testbase.dataproviders.PaymentProcessingDataProvider.BR_PROCESS_CC_AND_BR_PT_AND_BR_FN_SP;
import static com.paylease.app.qa.testbase.dataproviders.PaymentProcessingDataProvider.BR_PT;
import static com.paylease.app.qa.testbase.dataproviders.PaymentProcessingDataProvider.BR_PT_AND_BR_FN_SP;
import static com.paylease.app.qa.testbase.dataproviders.PaymentProcessingDataProvider.BR_PT_PROFIT_STARS;
import static com.paylease.app.qa.testbase.dataproviders.PaymentProcessingDataProvider.BR_PT_PROFIT_STARS_AND_BR_SP_PROFITSTARS;
import static com.paylease.app.qa.testbase.dataproviders.PaymentProcessingDataProvider.BR_SP_PROFITSTARS;

import com.paylease.app.qa.e2e.tests.resident.OneTimePaymentTest;
import com.paylease.app.qa.framework.DataBaseConnector;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.utility.sshtool.SshUtil;
import com.paylease.app.qa.testbase.DbBase;
import com.paylease.app.qa.testbase.dataproviders.PaymentProcessingDataProvider;
import java.sql.Connection;
import org.testng.annotations.Test;

public class PaymentProcessingTestManagedDeposits extends ScriptBase {

  private static final String REGION = "Integration";
  private static final String FEATURE = "ManagedDepositsForYavoIntegration";

  //----------------------------------------TESTS---------------------------------------------------

  @Test(dataProvider = "paymentProcessing", dataProviderClass = PaymentProcessingDataProvider.class,
      groups = {"manual"})
  public void testBrPtScript(String testCaseVariationNo, String paymentMethod, String scriptName) {

    Logger.info("Payment processing test, where test variation: " + testCaseVariationNo + " with "
        + paymentMethod + ", script name: " + scriptName);

    TestSetupPage testSetupPage = null;

    String[] commands = null;
    String expectedStatus = null;
    int expectedTypeOfTransaction = 1;

    switch (scriptName) {
      case BR_PT_PROFIT_STARS:
        testSetupPage = new TestSetupPage(REGION, FEATURE, "createYavoPmWithManagedDepositsV2");
        commands = new String[]{BR_PT_PROFIT_STARS};
        expectedStatus = "1";
        break;

      case BR_PT_PROFIT_STARS_AND_BR_SP_PROFITSTARS:
        testSetupPage = new TestSetupPage(REGION, FEATURE, "createYavoPmWithManagedDepositsV2");
        commands = new String[]{BR_PT_PROFIT_STARS, BR_SP_PROFITSTARS};
        expectedStatus = "1";
        break;

      case BR_PT:
        testSetupPage = new TestSetupPage(REGION, FEATURE,
            "createYavoPmforFnboProcessingWithManagedDepositsV2");
        commands = new String[]{BR_PT};
        expectedStatus = "1";
        break;

      case BR_PROCESS_CC_AND_BR_PT:
        testSetupPage = new TestSetupPage(REGION, FEATURE, "createYavoPmWithManagedDepositsV2");
        commands = new String[]{BR_PROCESS_CC, BR_PT};
        expectedStatus = "2";
        break;

      case BR_PT_AND_BR_FN_SP:
        testSetupPage = new TestSetupPage(REGION, FEATURE,
            "createYavoPmforFnboProcessingWithManagedDepositsV2");
        commands = new String[]{BR_PT, BR_FN_SP};
        expectedStatus = "1";
        break;

      case BR_PROCESS_CC_AND_BR_PT_AND_BR_FN_SP:
        testSetupPage = new TestSetupPage(REGION, FEATURE, "createYavoPmWithManagedDepositsV2");
        commands = new String[]{BR_PROCESS_CC, BR_PT, BR_FN_SP};
        expectedStatus = "2";
        break;
    }

    testSetupPage.open();
    final String residentEmail = testSetupPage.getString("residentEmail");

    OneTimePaymentTest oneTimePaymentTest = new OneTimePaymentTest();

    String transactionId = oneTimePaymentTest.residentOtPaymentActions(residentEmail, paymentMethod,
        false, false, null);

    Logger.info("Transaction ID is: " + transactionId);

    String paymentAmount = oneTimePaymentTest.getPaymentAmount();
    paymentAmount = paymentAmount.replace("$", "").replace(",", "");

    Logger.info("Payment amount is: " + paymentAmount);

    String feeAmount = oneTimePaymentTest.getFeeAmount();
    feeAmount = feeAmount.replace("$", "").replace(",", "");

    Logger.info("Fee amount is: " + feeAmount);

    DataBaseConnector dataBaseConnector = new DataBaseConnector();

    dataBaseConnector.createConnection();
    Connection connection = dataBaseConnector.getConnection();

    DbBase dbBase = new DbBase();

    dbBase.checkBatchItemsAndTransactionsDbTableForNewWorldChanges(dbBase, connection,
        transactionId, expectedStatus, expectedTypeOfTransaction);

    //In case of Credit card transaction it has to be 2 business days old, in order for it to be
    // picked up by the br_process_cc script. That's why, before we run the script we update
    // the transaction date to be 5 days old. That makes sure that the transaction we created
    // gets picked up by the script.
    if ((scriptName.equals(BR_PROCESS_CC_AND_BR_PT) || (scriptName
        .equals(BR_PROCESS_CC_AND_BR_PT_AND_BR_FN_SP)))) {
      dbBase.updateTransactionsDbTable(connection, transactionId, DbBase.DAYS_FROM_TODAY);
    }

    SshUtil sshUtil = new SshUtil();

    sshUtil.sshCommand(commands);

    if ((scriptName.equals(BR_PROCESS_CC_AND_BR_PT) || (scriptName
        .equals(BR_PROCESS_CC_AND_BR_PT_AND_BR_FN_SP)))) {

      // Ensure that there is no payout transaction created for new world credit card transaction
      dbBase.checkTransactionsDbTableForParent(connection, transactionId);
    }

    //Since this Transaction is a Managed Deposits transaction so after the scripts are run we
    // expect the status and type of transaction to remain the same.
    dbBase.checkBatchItemsAndTransactionsDbTableForNewWorldChanges(dbBase, connection,
        transactionId, expectedStatus, expectedTypeOfTransaction);

    dataBaseConnector.closeConnection();
  }
}