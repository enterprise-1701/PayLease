package com.paylease.app.qa.manual.tests.backend;

import static com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage.NEW_BANK;
import static com.paylease.app.qa.testbase.dataproviders.PaymentProcessingDataProvider.BR_FN_PROCESS_RETURNS;
import static com.paylease.app.qa.testbase.dataproviders.PaymentProcessingDataProvider.BR_PT;

import com.jcraft.jsch.Session;
import com.paylease.app.qa.e2e.tests.resident.OneTimePaymentTest;
import com.paylease.app.qa.framework.AppConstant;
import com.paylease.app.qa.framework.DataBaseConnector;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ResourceFactory;
import com.paylease.app.qa.framework.UtilityManager;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.utility.database.client.dao.BatchItemDao;
import com.paylease.app.qa.framework.utility.database.client.dao.DepositDao;
import com.paylease.app.qa.framework.utility.database.client.dao.ExternalTransactionsDao;
import com.paylease.app.qa.framework.utility.database.client.dao.IntegrationLoggingDao;
import com.paylease.app.qa.framework.utility.database.client.dao.ReturnFilesDao;
import com.paylease.app.qa.framework.utility.database.client.dao.TransactionDao;
import com.paylease.app.qa.framework.utility.database.client.dto.BatchItem;
import com.paylease.app.qa.framework.utility.database.client.dto.Deposit;
import com.paylease.app.qa.framework.utility.database.client.dto.ExternalTransactionsDto;
import com.paylease.app.qa.framework.utility.database.client.dto.IntegrationLoggingDto;
import com.paylease.app.qa.framework.utility.database.client.dto.Transaction;
import com.paylease.app.qa.framework.utility.kafka.KafkaConsumerClient;
import com.paylease.app.qa.framework.utility.sshtool.SshUtil;
import com.paylease.app.qa.testbase.DbBase;
import com.paylease.app.qa.testbase.KafkaEventBase;
import com.paylease.app.qa.testbase.dataproviders.PaymentProcessingDataProvider;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PaymentReturnsProcessingTestFnbo extends DbBase {

  private String returnFileName;

  //----------------------------------------TESTS---------------------------------------------------

  @Test(dataProvider = "paymentReturns", dataProviderClass = PaymentProcessingDataProvider.class,
      groups = {"manual"})
  public void testReturnedPayInBatchItemFnbo(String testCaseVariationNumber, boolean isNewWorld)
      throws Exception {
    Logger.info("Payment process returns test for FNBO: " + testCaseVariationNumber);

    TestSetupPage testSetupPage;
    String residentEmail;
    String topicName = "";
    String groupId = "";

    if (isNewWorld) {
      testSetupPage = new TestSetupPage("Integration", "ManagedDepositsForYavoIntegration",
          "createYavoPmforFnboProcessingWithManagedDepositsV2");
      testSetupPage.open();

      residentEmail = testSetupPage.getString("residentEmail");
      topicName = testSetupPage.getString("kafkaTopic");
      groupId = testSetupPage.getString("kafkaGroupId");
    } else {
      testSetupPage = new TestSetupPage("resident", "otp", "testSetupForOtpFlowFnbo");
      testSetupPage.open();

      residentEmail = testSetupPage.getString("residentEmail");
    }

    ArrayList<String> paymentFieldList = new ArrayList<>();

    //Assemble the list of payment fields and get the size of the list.
    paymentFieldList.add(testSetupPage.getString("paymentField1Label"));
    paymentFieldList.add(testSetupPage.getString("paymentField2Label"));
    OneTimePaymentTest oneTimePaymentTest = new OneTimePaymentTest();

    String transactionId = oneTimePaymentTest.residentOtPaymentActions(residentEmail, NEW_BANK,
        false, false, paymentFieldList);

    Logger.info("Transaction ID is: " + transactionId);

    String paymentAmount = oneTimePaymentTest.getPaymentAmount();
    paymentAmount = paymentAmount.replace("$", "").replace(",", "");

    Logger.info("Payment amount is: " + paymentAmount);

    String feeAmount = oneTimePaymentTest.getFeeAmount();
    feeAmount = feeAmount.replace("$", "").replace(",", "");

    Logger.info("Fee amount is: " + feeAmount);

    ArrayList<Double> amounts = new ArrayList<>();
    double total = Double.valueOf(paymentAmount) + Double.valueOf(feeAmount);
    amounts.add(total);

    DataBaseConnector dataBaseConnector = new DataBaseConnector();

    dataBaseConnector.createConnection();
    Connection connection = dataBaseConnector.getConnection();

    DbBase dbBase = new DbBase();

    //Start the Consumer
    KafkaConsumerClient kafkaConsumerClient = new KafkaConsumerClient(
        AppConstant.KAFKA_BROKER_URL, AppConstant.USERNAME, AppConstant.PASSWORD, topicName,
        groupId);

    if (isNewWorld) {

      dbBase.checkBatchItemsAndTransactionsDbTableForNewWorldChanges(dbBase, connection,
          transactionId, "1", 1);

      kafkaConsumerClient.start();

      SshUtil sshUtil = new SshUtil();

      try {
        sshUtil.runIpnWorker();
      } catch (Exception e) {
        e.printStackTrace();
      }

      KafkaEventBase kafkaEventBase = new KafkaEventBase(kafkaConsumerClient);

      kafkaEventBase.waitForEventToProcess(KafkaEventBase.ALL_DEPOSIT_ITEMS_CREATED, transactionId);

      fileOperation(connection, transactionId, total, dbBase, sshUtil);

      kafkaEventBase.waitForEvent(KafkaEventBase.BATCH_ITEM_RETURNED, String.valueOf(procId));
      kafkaEventBase.waitForEvent(KafkaEventBase.DEPOSIT_ITEM_RETURNED, String.valueOf(procId));

      TransactionDao transactionDao = new TransactionDao();

      ArrayList<Transaction> payoutTransactions = transactionDao
          .findByParentId(connection, Long.valueOf(transactionId), 50);

      List<Integer> payoutProcIds = new ArrayList<>();

      for (Transaction payoutTransaction : payoutTransactions) {
        kafkaEventBase.waitForEventToProcess(KafkaEventBase.ALL_DEPOSIT_ITEMS_CREATED,
            String.valueOf(payoutTransaction.getTransactionId()));
        payoutProcIds.add(dbBase.getProcIdFromBatchItemsTable(connection,
            String.valueOf(payoutTransaction.getTransactionId())));
      }

      DepositDao depositDao = new DepositDao();
      BatchItemDao batchItemDao = new BatchItemDao();

      for (int procId : payoutProcIds) {
        kafkaEventBase.waitForEvent(KafkaEventBase.BATCH_ITEM_VOIDED, String.valueOf(procId));
        kafkaEventBase.waitForEvent(KafkaEventBase.DEPOSIT_ITEM_RETURNED, String.valueOf(procId));
        kafkaEventBase
            .waitForEventToProcess(KafkaEventBase.DEPOSIT_ITEM_RETURNED, String.valueOf(procId));
        BatchItem payoutBatchItem = batchItemDao.findById(connection, procId);
        Assert.assertTrue(payoutBatchItem.getReturnDepositId() > 0,
            "Return Deposit ID should be greater than 0. Batch item " + procId);
        Deposit returnDeposit = depositDao
            .findById(connection, payoutBatchItem.getReturnDepositId());
        Assert.assertEquals("debit", returnDeposit.getCreditOrDebit(),
            "Return Deposit should be a debit deposit. Batch item " + procId);
      }

      ExternalTransactionsDao externalTransactionsDAO = new ExternalTransactionsDao();
      IntegrationLoggingDao integrationLoggingDao = new IntegrationLoggingDao();
      ArrayList<ExternalTransactionsDto> externalTransactions = externalTransactionsDAO
          .findByTransIdAndType(connection, Integer.parseInt(transactionId),
              ExternalTransactionsDao.TYPE_RETURN);
      Assert.assertEquals(paymentFieldList.size(), externalTransactions.size(),
          "We expect to see returns integrated for  all of the payout batch items");

      ArrayList<IntegrationLoggingDto> integrationLogs = integrationLoggingDao
          .findByTransIdAndMethod(connection, Integer.parseInt(transactionId), 50,
              IntegrationLoggingDao.METHOD_REVERSAL);
      Assert.assertEquals(paymentFieldList.size(), integrationLogs.size(),
          "We expect to reach out and integrate returns for each of the payout batch items");

      for (int i = 0; i < payoutProcIds.size(); i++) {
        BatchItem payoutBatchItem = batchItemDao.findById(connection, payoutProcIds.get(i));
        Assert.assertEquals((Integer) externalTransactions.get(i).getProcId(), payoutProcIds.get(i),
            "Expect external transaction row for each payout batch item");
        Assert.assertEquals((Integer) integrationLogs.get(i).getProcId(), payoutProcIds.get(i),
            "Expect integration logging row for each payout batch item");
        Assert.assertTrue(integrationLogs.get(i).getRequestRaw()
                .contains(Double.toString(payoutBatchItem.getAmount())),
            "Expect the integration request is for the correct amount");
      }

      for (IntegrationLoggingDto integrationLog : integrationLogs) {
        Assert.assertTrue(integrationLog.getRequestRaw().contains("NSF"),
            "Expect the integration request is of type NSF");
        // TODO: Add assertion for external batch id and check it to be Return
      }

    } else {

      dbBase.checkBatchItemsDbTable(connection, transactionId, 0, null);

      SshUtil sshUtil = new SshUtil();
      sshUtil.sshCommand(new String[]{BR_PT});

      dbBase.checkTransactionsDbTable(FindBy.TRANSACTION_ID, connection, transactionId, "2", 1);

      fileOperation(connection, transactionId, total, dbBase, sshUtil);
    }

    BatchItemDao batchItemDao = new BatchItemDao();

    ArrayList<BatchItem> batchItemsList = batchItemDao.findById(connection, procId, 50);

    Assert.assertFalse(batchItemsList.isEmpty(), "Batch Items is empty");

    for (BatchItem batchItem : batchItemsList) {

      String returnCode = batchItem.getReturnCode();
      int returnFileId = batchItem.getReturnFileId();

      Assert.assertTrue(returnFileId > 0, "Return file id should be set");
      Assert.assertTrue(returnCode.equalsIgnoreCase("R01"), "Code doesn't match");
    }

    if (isNewWorld) {
      kafkaConsumerClient.finish();
    }

    dataBaseConnector.closeConnection();
  }

  @Test(dataProvider = "paymentReturns", dataProviderClass = PaymentProcessingDataProvider.class,
      groups = {"manual"})
  public void testReturnedPayInBatchItemFnboPaidOut(String testCaseVariationNumber,
      boolean isNewWorld) throws Exception {
    Logger.info("Payment process returns test for FNBO: " + testCaseVariationNumber);

    TestSetupPage testSetupPage;
    String residentEmail;
    String topicName = "";
    String groupId = "";

    if (isNewWorld) {
      testSetupPage = new TestSetupPage("Integration", "ManagedDepositsForYavoIntegration",
          "createYavoPmforFnboProcessingWithManagedDepositsV2");
      testSetupPage.open();

      residentEmail = testSetupPage.getString("residentEmail");
      topicName = testSetupPage.getString("kafkaTopic");
      groupId = testSetupPage.getString("kafkaGroupId");
    } else {
      testSetupPage = new TestSetupPage("resident", "otp", "testSetupForOtpFlowFnbo");
      testSetupPage.open();

      residentEmail = testSetupPage.getString("residentEmail");
    }

    ArrayList<String> paymentFieldList = new ArrayList<>();

    //Assemble the list of payment fields and get the size of the list.
    paymentFieldList.add(testSetupPage.getString("paymentField1Label"));
    paymentFieldList.add(testSetupPage.getString("paymentField2Label"));

    OneTimePaymentTest oneTimePaymentTest = new OneTimePaymentTest();

    String transactionId = oneTimePaymentTest.residentOtPaymentActions(residentEmail, NEW_BANK,
        false, false, paymentFieldList);

    Logger.info("Transaction ID is: " + transactionId);

    String paymentAmount = oneTimePaymentTest.getPaymentAmount();
    paymentAmount = paymentAmount.replace("$", "").replace(",", "");

    Logger.info("Payment amount is: " + paymentAmount);

    String feeAmount = oneTimePaymentTest.getFeeAmount();
    feeAmount = feeAmount.replace("$", "").replace(",", "");

    Logger.info("Fee amount is: " + feeAmount);

    ArrayList<Double> amounts = new ArrayList<>();
    double total = Double.valueOf(paymentAmount) + Double.valueOf(feeAmount);
    amounts.add(total);

    DataBaseConnector dataBaseConnector = new DataBaseConnector();

    dataBaseConnector.createConnection();
    Connection connection = dataBaseConnector.getConnection();

    DbBase dbBase = new DbBase();

    //Start the Consumer
    KafkaConsumerClient kafkaConsumerClient = new KafkaConsumerClient(
        AppConstant.KAFKA_BROKER_URL, AppConstant.USERNAME, AppConstant.PASSWORD, topicName,
        groupId);

    if (isNewWorld) {
      dbBase.checkBatchItemsAndTransactionsDbTableForNewWorldChanges(dbBase, connection,
          transactionId, "1", 1);

      kafkaConsumerClient.start();

      SshUtil sshUtil = new SshUtil();

      try {
        sshUtil.runIpnWorker();
      } catch (Exception e) {
        e.printStackTrace();
      }

      KafkaEventBase kafkaEventBase = new KafkaEventBase(kafkaConsumerClient);

      kafkaEventBase.waitForEventToProcess(KafkaEventBase.ALL_DEPOSIT_ITEMS_CREATED, transactionId);

      TransactionDao transactionDao = new TransactionDao();

      ArrayList<Transaction> payoutTransactions = transactionDao
          .findByParentId(connection, Long.valueOf(transactionId), 50);

      BatchItemDao batchItemDao = new BatchItemDao();

      for (Transaction payoutTransaction : payoutTransactions) {
        kafkaEventBase.waitForEventToProcess(KafkaEventBase.ALL_DEPOSIT_ITEMS_CREATED,
            String.valueOf(payoutTransaction.getTransactionId()));
        int payoutProcId = dbBase.getProcIdFromBatchItemsTable(connection,
            String.valueOf(payoutTransaction.getTransactionId()));
        BatchItem payoutBatchItem = batchItemDao.findById(connection, payoutProcId);
        payoutBatchItem.setFileId(123);
        batchItemDao.update(connection, payoutBatchItem);
      }

      fileOperation(connection, transactionId, total, dbBase, sshUtil);

      kafkaEventBase.waitForEvent(KafkaEventBase.BATCH_ITEM_RETURNED, String.valueOf(procId));
      kafkaEventBase.waitForEvent(KafkaEventBase.DEPOSIT_ITEM_RETURNED, String.valueOf(procId));

      for (Transaction transaction : payoutTransactions) {
        long transId = transaction.getTransactionId();
        ArrayList<Transaction> reversedTransactions = transactionDao
            .findByParentId(connection, transId, 50);
        for (Transaction reversedTransaction : reversedTransactions) {
          kafkaEventBase.waitForEvent(KafkaEventBase.TRANSACTION_CREATED,
              String.valueOf(reversedTransaction.getTransactionId()));
          kafkaEventBase.waitForEvent(KafkaEventBase.DEPOSIT_ITEM_CREATED,
              String.valueOf(reversedTransaction.getTransactionId()));
        }
      }

      List<BatchItem> reversalBatchItems;

      //get depositItemId/proc_id for reversals from the batch items table.
      List<Transaction> transactionIdsList = dbBase.getReversalTransactions(connection,
          String.valueOf(transactionId));
      List<Long> reversalTransactionIds = new ArrayList<>();
      for (Transaction trans : transactionIdsList) {
        Long reversalTransId = trans.getTransactionId();
        reversalTransactionIds.add(reversalTransId);
      }

      for (Long reversalTransactionId : reversalTransactionIds) {
        kafkaEventBase.waitForEventToProcess(KafkaEventBase.DEPOSIT_ITEM_CREATED,
            String.valueOf(reversalTransactionId));
      }

      reversalBatchItems = dbBase.getBatchItems(reversalTransactionIds,
          connection);

      for (BatchItem reversalBatchItem : reversalBatchItems) {
        kafkaEventBase.waitForEventToProcess(KafkaEventBase.DEPOSIT_ITEM_CREATED,
            String.valueOf(reversalBatchItem.getProcId()));
      }

      ExternalTransactionsDao externalTransactionsDAO = new ExternalTransactionsDao();
      IntegrationLoggingDao integrationLoggingDao = new IntegrationLoggingDao();

      ArrayList<ExternalTransactionsDto> externalTransactions = externalTransactionsDAO
          .findByTransIdAndType(connection, Integer.parseInt(transactionId),
              ExternalTransactionsDao.TYPE_REVERSAL);
      Assert.assertEquals(paymentFieldList.size(), externalTransactions.size(),
          "We expect to see reversals integrated for all of the reversal batch items");
      ArrayList<IntegrationLoggingDto> integrationLogs = integrationLoggingDao
          .findByTransIdAndMethod(connection, Integer.parseInt(transactionId), 50,
              IntegrationLoggingDao.METHOD_REVERSAL);
      Assert.assertEquals(paymentFieldList.size(), integrationLogs.size(),
          "We expect to integrate the reversal for each of the reversal batch items");

      for (IntegrationLoggingDto integrationLog : integrationLogs) {
        Assert.assertEquals(integrationLog.getExternalBatchId(),
            ExternalTransactionsDao.TYPE_REVERSAL,
            "We expect to integrate reversals for each of the reversal batch items");
      }

      for (int i = 0; i < reversalBatchItems.size(); i++) {
        Assert.assertEquals(externalTransactions.get(i).getProcId(),
            reversalBatchItems.get(i).getProcId(),
            "Expect a row in external transactions for each reversal batch item");
        Assert
            .assertEquals(integrationLogs.get(i).getProcId(), reversalBatchItems.get(i).getProcId(),
                "Expect a row in integration logging for each reversal batch item");
        Assert.assertTrue(integrationLogs.get(i).getRequestRaw()
                .contains(Double.toString(reversalBatchItems.get(i).getAmount())),
            "Expect the integration request is for the right amount");

        // TODO: Add assertion for external batch id and check it to be Reversal
      }

    } else {

      dbBase.checkBatchItemsDbTable(connection, transactionId, 0, null);

      SshUtil sshUtil = new SshUtil();
      sshUtil.sshCommand(new String[]{BR_PT});

      dbBase.checkTransactionsDbTable(FindBy.TRANSACTION_ID, connection, transactionId, "2", 1);

      fileOperation(connection, transactionId, total, dbBase, sshUtil);
    }

    BatchItemDao batchItemDao = new BatchItemDao();

    ArrayList<BatchItem> batchItemsList = batchItemDao.findById(connection, procId, 50);

    Assert.assertFalse(batchItemsList.isEmpty(), "Batch Items is empty");

    for (BatchItem batchItem : batchItemsList) {

      String returnCode = batchItem.getReturnCode();
      int returnFileId = batchItem.getReturnFileId();

      Assert.assertTrue(returnFileId > 0, "Expect returns script to set the return file id");
      Assert.assertTrue(returnCode.equalsIgnoreCase("R01"), "Code doesn't match");
    }

    if (isNewWorld) {
      kafkaConsumerClient.finish();
    }

    dataBaseConnector.closeConnection();
  }

  //--------------------------------TEST METHODS----------------------------------------------------

  private void writeAndUploadReturnsFile() throws Exception {

    this.returnFileName = "fnreturns" + UtilityManager.getCurrentDate("yyyyMMddHHmmss");

    String localFilePath = UPLOAD_DIR_PATH + File.separator + returnFileName;

    String effectiveDate = UtilityManager.getCurrentDate("yyMMdd");

    String newAmount = String.valueOf(amount).replace(".", "");

    Logger.info("New amount is: " + newAmount);

    Logger.info(String.format("%010d", Integer.parseInt(newAmount)));

    FileWriter writer;
    try {
      writer = new FileWriter(new File(localFilePath));
      //1st line to remain untouched
      writer.write(
          "101 104000016 1040000161505271524H094101PAYLEASE INC           FIRST NAT BK OMAHA");
      writer.write(System.lineSeparator());
      writer.write(
          "5200PAYLEASE.COM                        0000000000WEBRESIDENT        " + effectiveDate
              + "0001086300010000001");
      writer.write(System.lineSeparator());
      writer.write("626" + fromRoutingNum + fromAcc + "        " + String.format("%010d",
          Integer.parseInt(newAmount)) + " " + procId
          + "     Anthony  Laing        S 1086300019537074");
      writer.write(System.lineSeparator());
      writer.write(
          "799R01104000016043895      32527202                                            325272022994263");
      writer.write(System.lineSeparator());
      writer.write(
          "820000000200325272020000000308950000000000002273639005                         325272020000001");
      writer.write(System.lineSeparator());
      writer.write("9000146000060000003002348759608000005782273000001194060");
      writer.write(System.lineSeparator());
      writer.write(
          "9999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999");
      writer.write(System.lineSeparator());
      writer.write(
          "9999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999");
      writer.write(System.lineSeparator());
      writer.write(
          "9999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999");
      writer.write(System.lineSeparator());
      writer.write(
          "9999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999");
      writer.write(System.lineSeparator());
      writer.write(
          "9999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999");
      writer.write(System.lineSeparator());
      writer.write(
          "9999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999");
      writer.write(System.lineSeparator());
      writer.write("FNBO END 0000600");
      writer.write(System.lineSeparator());
      writer.flush();
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    SshUtil sshUtil = new SshUtil();

    String remoteFilePath = ResourceFactory.getInstance()
        .getProperty(ResourceFactory.WEB_APP_ROOT_DIR_KEY) + "batches/files/fn/returns/";

    Session ftpSession = sshUtil.ftpFileToRemoteServer(remoteFilePath, localFilePath);

    Logger.info("Uploaded returns file");

    ftpSession.disconnect();
  }

  private void createReturnFile(Connection connection) {

    ReturnFilesDao returnFilesDao = new ReturnFilesDao();

    String repoPath = ResourceFactory.getInstance().getProperty(ResourceFactory
        .WEB_APP_ROOT_DIR_KEY) + "batches/files/fn/returns/" + returnFileName;

    returnFilesDao.create(connection, returnFileName, repoPath, 1);
  }

  private void fileOperation(Connection connection, String transactionId, double total,
      DbBase dbBase, SshUtil sshUtil) throws Exception {
    Long fileId = getFileId(connection, transactionId);

    Assert.assertTrue(dbBase.checkIfNachaFileWasCreated(sshUtil, fileId, procId, total,
        accountPersonName), "NACHA file wasn't created and assertions failed");

    writeAndUploadReturnsFile();

    createReturnFile(connection);

    sshUtil.sshCommand(new String[]{BR_FN_PROCESS_RETURNS});
  }
}
