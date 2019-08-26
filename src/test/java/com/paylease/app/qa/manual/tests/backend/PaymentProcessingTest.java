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
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.utility.database.client.dao.TransactionDao;
import com.paylease.app.qa.framework.utility.database.client.dao.UserDao;
import com.paylease.app.qa.framework.utility.database.client.dto.Transaction;
import com.paylease.app.qa.framework.utility.database.client.dto.User;
import com.paylease.app.qa.framework.utility.sshtool.SshUtil;
import com.paylease.app.qa.testbase.DbBase;
import com.paylease.app.qa.testbase.dataproviders.PaymentProcessingDataProvider;
import java.sql.Connection;
import java.util.ArrayList;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PaymentProcessingTest extends DbBase {

  private static final String REGION = "resident";
  private static final String FEATURE = "otp";

  //----------------------------------------TESTS---------------------------------------------------

  @Test(dataProvider = "paymentProcessing", dataProviderClass = PaymentProcessingDataProvider.class,
      groups = {"manual"})
  public void testBrPtScripts(String testCaseVariationNo, String paymentMethod, String scriptName) {
    Logger.info("Payment processing test, where test variation: " + testCaseVariationNo + " with "
        + paymentMethod + ", script name: " + scriptName);

    TestSetupPage testSetupPage = null;

    String[] commands = null;
    String expectedStatus = null;
    int expectedTypeOfTransaction = 0;

    switch (scriptName) {
      case BR_PT_PROFIT_STARS:
        testSetupPage = new TestSetupPage(REGION, FEATURE, "testSetupForOtpFlow");
        commands = new String[]{BR_PT_PROFIT_STARS};
        expectedStatus = "1";
        expectedTypeOfTransaction = 1;
        break;

      case BR_PT_PROFIT_STARS_AND_BR_SP_PROFITSTARS:
        testSetupPage = new TestSetupPage(REGION, FEATURE, "testSetupForOtpFlow");
        commands = new String[]{BR_PT_PROFIT_STARS, BR_SP_PROFITSTARS};
        expectedStatus = "1";
        expectedTypeOfTransaction = 1;
        break;

      case BR_PT:
        testSetupPage = new TestSetupPage(REGION, FEATURE, "testSetupForOtpFlowFnbo");
        commands = new String[]{BR_PT};
        expectedStatus = "1";
        expectedTypeOfTransaction = 1;
        break;

      case BR_PROCESS_CC_AND_BR_PT:
        testSetupPage = new TestSetupPage(REGION, FEATURE, "testSetupForOtpFlow");
        commands = new String[]{BR_PROCESS_CC, BR_PT};
        expectedStatus = "2";
        expectedTypeOfTransaction = 1;
        break;

      case BR_PT_AND_BR_FN_SP:
        testSetupPage = new TestSetupPage(REGION, FEATURE, "testSetupForOtpFlowFnbo");
        commands = new String[]{BR_PT, BR_FN_SP};
        expectedStatus = "1";
        expectedTypeOfTransaction = 1;
        break;

      case BR_PROCESS_CC_AND_BR_PT_AND_BR_FN_SP:
        testSetupPage = new TestSetupPage(REGION, FEATURE, "testSetupForOtpFlow");
        commands = new String[]{BR_PROCESS_CC, BR_PT, BR_FN_SP};
        expectedStatus = "2";
        expectedTypeOfTransaction = 1;
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

    dbBase.checkTransactionsDbTable(FindBy.TRANSACTION_ID, connection, transactionId,
        expectedStatus, expectedTypeOfTransaction);

    dbBase.checkBatchItemsDbTable(connection, transactionId, 0, null);

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
      // It is expected that after br_process_cc script is run, a new payout transaction is created
      // and old transaction gets status assigned as 3 and type_of_transaction as 1. When
      // operation 1 is called transactionId is used for assertion. In this case we are asserting
      // the old transaction.
      dbBase.checkTransactionsDbTable(FindBy.TRANSACTION_ID, connection, transactionId, "3", 1);

      // It is expected that after br_process_cc script is run, a new payout transaction is created
      // and old transaction gets assigned as parent_transaction_id. When operation 2 is called
      // transactionId is used as parent_transaction_id for assertion. In this case we are asserting
      // the new payout transaction.
      dbBase.checkTransactionsDbTable(FindBy.PARENT_TRANSACTION_ID, connection, transactionId, "2", 2);
    } else {
      dbBase.checkTransactionsDbTable(FindBy.TRANSACTION_ID, connection, transactionId, "2", 1);
    }

    // Profit Stars has 2 rows in the BatchItems table and FNBO has 1 table. The check we do below
    // checks that.
    ArrayList<Double> amounts = new ArrayList<Double>();

    //Switch
    double total = 0;

    switch (scriptName) {
      case BR_PT_PROFIT_STARS:
      case BR_PT_PROFIT_STARS_AND_BR_SP_PROFITSTARS:
        amounts.add(Double.valueOf(paymentAmount));
        amounts.add(Double.valueOf(feeAmount));

        dbBase.checkBatchItemsDbTable(connection, transactionId, 2, amounts);
        break;

      case BR_PT:
      case BR_PT_AND_BR_FN_SP:
        total = Double.valueOf(paymentAmount) + Double.valueOf(feeAmount);
        amounts.add(total);

        dbBase.checkBatchItemsDbTable(connection, transactionId, 1, amounts);
        break;

      case BR_PROCESS_CC_AND_BR_PT:
      case BR_PROCESS_CC_AND_BR_PT_AND_BR_FN_SP:
        amounts.add(Double.valueOf(paymentAmount));

        dbBase.checkBatchItemsDbTable(connection, transactionId, 1, amounts);
        break;
    }

    switch (scriptName) {
      case BR_PT_AND_BR_FN_SP: {
        Long fileId = getFileId(connection, transactionId);

        //We send Account person name in case of ACH transaction.
        Assert.assertTrue(dbBase.checkIfNachaFileWasCreated(sshUtil, fileId, procId, total,
            accountPersonName),
            "NACHA file wasn't created and assertions failed");
        break;
      }
      case BR_PROCESS_CC_AND_BR_PT_AND_BR_FN_SP: {
        // We get the new transaction ID in case of CC transaction. It is because the new
        // transaction id is used by the batch file to make the payouts.
        Long newTransactionId = getTransactionId(connection, transactionId);

        Long fileId = getFileId(connection, String.valueOf(newTransactionId));

        String pmCompany = getPmCompany(connection, toId);

        //We send PM Company name in case of CC transaction.
        Assert.assertTrue(dbBase.checkIfNachaFileWasCreated(sshUtil, fileId, procId,
            Double.valueOf(paymentAmount),
            pmCompany), "NACHA file wasn't created and assertions failed");
        break;
      }
      case BR_PT_PROFIT_STARS_AND_BR_SP_PROFITSTARS: {
        //In this case we expect to see the file id>0 and external ref id != NULL
        dbBase.checkBatchItemsDbTableForFileIdAndExtRefId(connection, transactionId, false);

        break;
      }
    }

    dataBaseConnector.closeConnection();
  }

  //--------------------------------TEST METHODS----------------------------------------------------

  private Long getTransactionId(Connection connection, String parentTransactionId) {

    TransactionDao transactionDao = new TransactionDao();

    ArrayList<Transaction> transactionsList = transactionDao.findByParentId(connection,
        Long.parseLong(parentTransactionId), 50);

    Long transactionId = null;

    for (Transaction transaction : transactionsList) {
      transactionId = transaction.getTransactionId();
      this.toId = transaction.getToId();

      Logger.info("Transaction ID is: " + transactionId);
    }

    return transactionId;
  }

  private String getPmCompany(Connection conn, String pmId) {

    UserDao userDao = new UserDao();

    User user = userDao.findByPmId(conn, pmId);

    String pmCompany = user.getPmCompany();

    Logger.info("PM Company is: " + pmCompany);

    return pmCompany;
  }
}

