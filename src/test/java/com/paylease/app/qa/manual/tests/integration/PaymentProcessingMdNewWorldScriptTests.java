package com.paylease.app.qa.manual.tests.integration;

import static com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage.NEW_BANK;
import static com.paylease.app.qa.testbase.dataproviders.PaymentProcessingDataProvider.BR_SP_PROFITSTARS_NEW_WORLD;

import com.paylease.app.qa.e2e.tests.resident.OneTimePaymentTest;
import com.paylease.app.qa.framework.AppConstant;
import com.paylease.app.qa.framework.DataBaseConnector;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.utility.database.client.dao.BatchItemDao;
import com.paylease.app.qa.framework.utility.database.client.dto.BatchItem;
import com.paylease.app.qa.framework.utility.kafka.KafkaConsumerClient;
import com.paylease.app.qa.framework.utility.sshtool.SshUtil;
import com.paylease.app.qa.testbase.DbBase;
import com.paylease.app.qa.testbase.DbBase.FindBy;
import com.paylease.app.qa.testbase.KafkaEventBase;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PaymentProcessingMdNewWorldScriptTests extends ScriptBase {

  private static final String REGION = "Integration";
  private static final String FEATURE = "ManagedDepositsForYavoIntegration";

  //----------------------------------------TESTS---------------------------------------------------

  @Test
  public void testNewWorldBrPtScripts() throws Exception {
    Logger.info("Payment Processing test for Managed deposit BR_SP_PROFITSTARS_NEW_WORLD script");

    TestSetupPage testSetupPage = new TestSetupPage(
        REGION, FEATURE, "createYavoPmWithManagedDepositsV2");

    testSetupPage.open();
    final String residentEmail = testSetupPage.getString("residentEmail");
    final String topicName = testSetupPage.getString("kafkaTopic");
    final String groupId = testSetupPage.getString("kafkaGroupId");

    OneTimePaymentTest oneTimePaymentTest = new OneTimePaymentTest();

    String transactionId = oneTimePaymentTest.residentOtPaymentActions(residentEmail, NEW_BANK,
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

    String expectedStatus = "1";
    int expectedTypeOfTransaction = 1;

    checkBatchItemsAndTransactionsDbTables(dbBase, connection, transactionId, expectedStatus,
        expectedTypeOfTransaction);

    SshUtil sshUtil = new SshUtil();

    //Start the Consumer
    KafkaConsumerClient kafkaConsumerClient = new KafkaConsumerClient(AppConstant.KAFKA_BROKER_URL,
        AppConstant.USERNAME, AppConstant.PASSWORD, topicName, groupId);
    kafkaConsumerClient.start();

    try {
      sshUtil.runIpnWorker();
    } catch (Exception e) {
      e.printStackTrace();
    }

    KafkaEventBase kafkaEventBase = new KafkaEventBase(kafkaConsumerClient);

    kafkaEventBase.waitForEvent(KafkaEventBase.ALL_DEPOSIT_ITEMS_CREATED, transactionId);

    List<BatchItem> depositItems = getDepositItemIdsFromBatchItemsTable(Long.valueOf(transactionId),
        connection);

    Assert.assertFalse(depositItems.isEmpty(), "Deposit items are empty");

    for (BatchItem depositItem : depositItems) {
      kafkaEventBase.waitForEvent(KafkaEventBase.DEPOSIT_ITEM_INTEGRATED,
          String.valueOf(depositItem.getProcId()));
    }

    //After IPN worker runs deposit_id != NULL & ready_to_process = 1
    checkDepositIdAndReadyToProcess(connection, transactionId);

    String[] command2 = new String[]{BR_SP_PROFITSTARS_NEW_WORLD};

    sshUtil.sshCommand(command2);

    //We should see the File ID for this transaction to be > 0 and a value for Ext ref Id.
    dbBase.checkBatchItemsDbTableForFileIdAndExtRefId(connection, transactionId, false);

    kafkaConsumerClient.finish();
    dataBaseConnector.closeConnection();
  }

  //-------------------------------------METHODS----------------------------------------------------

  private void checkBatchItemsAndTransactionsDbTables(DbBase dbBase, Connection connection,
      String transactionId, String expectedStatus, int expectedTypeOfTransaction) {

    dbBase.checkTransactionsDbTable(FindBy.TRANSACTION_ID, connection, transactionId,
        expectedStatus, expectedTypeOfTransaction);

    dbBase.checkBatchItemsDbTable(connection, transactionId, 0, null);
  }

  //This block from line 126 to 224 will need to be consolidated to DbBase after DEV-42685 and
  // DEV-42686
  private void checkDepositIdAndReadyToProcess(Connection connection, String transactionId) {
    BatchItemDao batchItemDao = new BatchItemDao();

    ArrayList<BatchItem> batchItemsList = batchItemDao.findById(connection,
        Long.parseLong(transactionId), 50);

    Assert.assertFalse(batchItemsList.isEmpty(), "Batch Items is empty");

    for (BatchItem batchItem : batchItemsList) {

      long depositId = batchItem.getDepositId();
      int readyToProcess = batchItem.getReadyToProcess();

      Logger.info("Deposit ID is: " + depositId);

      Assert.assertNotNull(depositId, "Deposit ID is NULL");
      Assert.assertEquals(readyToProcess, 1, "Ready to process should be set as 1");
    }
  }

  private List<BatchItem> getDepositItemIdsFromBatchItemsTable(Long transactionId,
      Connection connection) {
    BatchItemDao batchItemDao = new BatchItemDao();
    return batchItemDao.findById(connection, transactionId, 50);
  }
}

