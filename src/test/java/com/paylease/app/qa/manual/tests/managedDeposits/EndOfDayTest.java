package com.paylease.app.qa.manual.tests.managedDeposits;

import com.paylease.app.qa.framework.AppConstant;
import com.paylease.app.qa.framework.DataBaseConnector;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.utility.database.client.dao.BatchItemDao;
import com.paylease.app.qa.framework.utility.database.client.dao.ExternalTransactionsDao;
import com.paylease.app.qa.framework.utility.database.client.dao.IntegrationLoggingDao;
import com.paylease.app.qa.framework.utility.database.client.dto.BatchItem;
import com.paylease.app.qa.framework.utility.kafka.KafkaConsumerClient;
import com.paylease.app.qa.framework.utility.sshtool.SshUtil;
import com.paylease.app.qa.testbase.DbBase;
import com.paylease.app.qa.testbase.KafkaEventBase;
import com.paylease.app.qa.testbase.PaymentBase;
import com.paylease.app.qa.testbase.dataproviders.ManagedDepositsOneTimePaymentDataProvider;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EndOfDayTest extends ScriptBase {

  private static final String REGION = "Integration";
  private static final String FEATURE = "ManagedDepositsForYavoIntegration";
  public static final String EXCEPTION_DEPOSIT = "exception";
  public static final String STATUS_CLOSED = "closed";

  //--------------------------------DEPOSIT SERVICE FLOW TESTS-------------------------------------

  @Test(dataProvider = "cancelEmptyBatches", dataProviderClass =
      ManagedDepositsOneTimePaymentDataProvider.class, groups = {"manual"})
  public void cancelEmptyBatches(String testVariationNo, String testSetup, String processor,
      boolean emptyBatch, boolean emptyExternalTransactions, boolean deleteError)
      throws Exception {
    Logger.info(
        "Validate that deposit and batches are deleted successfully for" + testVariationNo + "and"
            + testSetup);

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE,
        testSetup);
    testSetupPage.open();
    final String residentEmail = testSetupPage.getString("residentEmail");
    final String topicName = testSetupPage.getString("kafkaTopic");
    final String groupId = testSetupPage.getString("kafkaGroupId");

    ArrayList<String> paymentFieldList = new ArrayList<>();
    paymentFieldList.add(testSetupPage.getString("paymentField1Label"));
    paymentFieldList.add(testSetupPage.getString("paymentField2Label"));
    paymentFieldList.add(testSetupPage.getString("paymentField3Label"));
    Collections.sort(paymentFieldList);

    //Start the Consumer
    KafkaConsumerClient kafkaConsumerClient = new KafkaConsumerClient(AppConstant.KAFKA_BROKER_URL,
        AppConstant.USERNAME, AppConstant.PASSWORD, topicName, groupId);

    kafkaConsumerClient.start();

    DataBaseConnector dataBaseConnector = new DataBaseConnector();

    dataBaseConnector.createConnection();
    Connection connection = dataBaseConnector.getConnection();

    //Trigger a OTP for a resident with incorrect yardi Account number
    PaymentBase paymentBase = new PaymentBase();
    String transIdString = paymentBase
        .residentOtPaymentActions(residentEmail, "NewBank", paymentFieldList);
    long transactionId = Long.parseLong(transIdString);

    //Run Ipn Worker
    SshUtil sshUtil = new SshUtil();
    DbBase dbBase = new DbBase();

    sshUtil.runIpnWorker();

    //get the batch item created for the transaction
    KafkaEventBase kafkaEventBase = new KafkaEventBase(kafkaConsumerClient);
    kafkaEventBase.waitForEvent(KafkaEventBase.ALL_DEPOSIT_ITEMS_CREATED,
        transIdString);

    //Get all the transactions for the parent transaction Id(FNBO) and add it to the transactionlist
    List<Long> transactionIdsList = new ArrayList<>();
    transactionIdsList = dbBase.getTransactionIdList(processor, connection, transactionId, dbBase,
        transactionIdsList,false );

    //Get deposit Ids for all the transactions recieved from the parent trans Id
    for (long expectedTransactionId : transactionIdsList) {
      kafkaEventBase.waitForEvent(KafkaEventBase.ALL_DEPOSIT_ITEMS_CREATED,
          String.valueOf(expectedTransactionId));
    }
    List<Long> depositIds = dbBase.getDepositIdList(connection,
        transactionIdsList);

    //Get all the deposit items for the transactions in the transactionIdsList
    dbBase.getBatchItems(transactionIdsList, connection);

    //delete all the deposit items
    BatchItemDao batchItemDao = new BatchItemDao();
    for (long transaction : transactionIdsList) {
      Thread.sleep(10000);
      kafkaEventBase.waitForEventToProcess(kafkaEventBase.DEPOSIT_ITEM_CREATED,
          String.valueOf(transaction));
      batchItemDao.removeByTransId(connection, (int) transaction);
    }

    if (emptyExternalTransactions) {
      ExternalTransactionsDao externalTransactionsDao = new ExternalTransactionsDao();
      externalTransactionsDao.removeByTransId(connection, (int) transactionId);
    }

    //trigger end of day
    for (long deposit : depositIds) {

      closeDepositOnLocal(String.valueOf(deposit));

      if (emptyBatch) {
        //Wait for batch deleted event.
        kafkaEventBase.waitForEvent(kafkaEventBase.EXTERNAL_BATCH_DELETED, String.valueOf(deposit));
        kafkaEventBase
            .waitForEventToProcess(kafkaEventBase.EXTERNAL_BATCH_DELETED, String.valueOf(deposit));

        //validate that the deposit is closed
        String depositStatus = dbBase.getDeposit(deposit, connection).getStatus();
        Assert.assertEquals(depositStatus, STATUS_CLOSED, "The deposit is not finalized");

        //validate that ReviewReceiptBatch is called.
        IntegrationLoggingDao integrationLoggingDao = new IntegrationLoggingDao();
        Assert.assertFalse(integrationLoggingDao.findByDepositIdAndMethod(connection, deposit, 1,
            IntegrationLoggingDao.METHOD_REVIEW_BATCH).isEmpty(),
            "Expected to find a call to review the external batch");

        //validate that CancelReceiptBatch is called.
        Assert.assertFalse(integrationLoggingDao.findByDepositIdAndMethod(connection, deposit, 1,
            IntegrationLoggingDao.METHOD_DELETE_BATCH).isEmpty(),
            "Expected to find a call to cancel the external batch");

        //validate that all empty deposits are deleted.
        String deletedAt = dbBase.getDeposit(deposit, connection).getDeletedAt().toString();
        Assert.assertFalse(deletedAt.isEmpty(), "The deposit was not deleted when empty");

        //validate that all empty batches are deleted if the cancelreciept request was successful.
        if (deleteError) {
          Assert.assertNull(dbBase.getExternalBatch(deposit, connection).getDeletedAt(),
              "The external batch was deleted");
          Assert.assertEquals(dbBase.getExternalBatch(deposit, connection).getStatus(),
              "deleted_error", "The status is not deleted with error");
        } else {
          String batchDeletedAt = dbBase.getExternalBatch(deposit, connection).getDeletedAt()
              .toString();
          Assert.assertFalse(batchDeletedAt.isEmpty(), "The batch was not deleted when empty");
          Assert.assertEquals(dbBase.getExternalBatch(deposit, connection).getStatus(), "deleted",
              "The status is not deleted with error");

        }
      } else {
        kafkaEventBase.waitForEvent(kafkaEventBase.DEPOSIT_CLOSED, String.valueOf(deposit));

        //validate that the deposit is closed
        String depositStatus = dbBase.getDeposit(deposit, connection).getStatus();
        Assert.assertEquals(depositStatus, STATUS_CLOSED, "The deposit is not finalized");

        //validate that ReviewReceiptBatch is called only when external_transactions is empty.
        IntegrationLoggingDao integrationLoggingDao = new IntegrationLoggingDao();
        if (!emptyExternalTransactions) {
          Assert.assertTrue(integrationLoggingDao.findByDepositIdAndMethod(connection, deposit, 1,
              IntegrationLoggingDao.METHOD_REVIEW_BATCH).isEmpty(),
              "Expected to find a call to review the external batch");
        } else {
          Assert.assertFalse(integrationLoggingDao.findByDepositIdAndMethod(connection, deposit, 1,
              IntegrationLoggingDao.METHOD_REVIEW_BATCH).isEmpty(),
              "Expected to find a call to review the external batch");
        }
        //validate that CancelReceiptBatch is not called.
        Assert.assertTrue(integrationLoggingDao.findByDepositIdAndMethod(connection, deposit, 1,
            IntegrationLoggingDao.METHOD_DELETE_BATCH).isEmpty(),
            "Expected to find a call to cancel the external batch");

        //validate that all empty deposits are not deleted.
        Assert.assertNull(dbBase.getDeposit(deposit, connection).getDeletedAt(),
            "The deposit was not deleted when empty");

        //validate that non empty deposits are not deleted.
        Assert.assertNull(dbBase.getExternalBatch(deposit, connection).getDeletedAt(),
            "The batch was not deleted when empty");
      }

    }
    kafkaConsumerClient.finish();
    dataBaseConnector.closeConnection();
  }

  @Test(dataProvider = "addNonIntegratedTransactionsToExceptionDeposit", dataProviderClass =
      ManagedDepositsOneTimePaymentDataProvider.class, groups = {"manual"})
  public void addNonIntegratedTransactionToExceptionDeposit(String testVariationNo,
      String testSetup, String processor)
      throws Exception {
    Logger.info(
        "Validate that the non integrated deposit items are added to an excpetion deposit for "
            + testVariationNo + "and"
            + testSetup);

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE,
        testSetup);
    testSetupPage.open();
    final String residentEmail = testSetupPage.getString("residentEmail");
    final String residentEmail2 = testSetupPage.getString("residentEmail2");
    final String topicName = testSetupPage.getString("kafkaTopic");
    final String groupId = testSetupPage.getString("kafkaGroupId");

    ArrayList<String> paymentFieldList = new ArrayList<>();
    paymentFieldList.add(testSetupPage.getString("paymentField1Label"));
    paymentFieldList.add(testSetupPage.getString("paymentField2Label"));
    paymentFieldList.add(testSetupPage.getString("paymentField3Label"));
    Collections.sort(paymentFieldList);

    //Start the Consumer
    KafkaConsumerClient kafkaConsumerClient = new KafkaConsumerClient(AppConstant.KAFKA_BROKER_URL,
        AppConstant.USERNAME, AppConstant.PASSWORD, topicName, groupId);

    kafkaConsumerClient.start();

    DataBaseConnector dataBaseConnector = new DataBaseConnector();

    dataBaseConnector.createConnection();
    Connection connection = dataBaseConnector.getConnection();

    //Trigger a OTP for a resident with incorrect yardi Account number
    PaymentBase paymentBase = new PaymentBase();
    String transIdString = paymentBase
        .residentOtPaymentActions(residentEmail, "NewBank", paymentFieldList);
    long transactionId = Long.parseLong(transIdString);

    //Trigger another payment for the second resident that would integrate successfully.
    String transIdTwoString = paymentBase
        .residentOtPaymentActions(residentEmail2, "NewBank", paymentFieldList);
    long transactionId2 = Long.parseLong(transIdTwoString);

    //Run Ipn Worker
    SshUtil sshUtil = new SshUtil();
    DbBase dbBase = new DbBase();

    sshUtil.runIpnWorker();

    //get the batch item created for the transaction
    KafkaEventBase kafkaEventBase = new KafkaEventBase(kafkaConsumerClient);
    kafkaEventBase.waitForEvent(KafkaEventBase.ALL_DEPOSIT_ITEMS_CREATED,
        String.valueOf(transactionId));

    //Get all the transactions for the parent transaction Id(FNBO) and add it to the transactionlist
    List<Long> transactionIdsList = new ArrayList<>();
    List<Long> transactionIdsList2 = new ArrayList<>();
    transactionIdsList = dbBase.getTransactionIdList(processor, connection, transactionId, dbBase,
        transactionIdsList,false );
    transactionIdsList2 = dbBase.getTransactionIdList(processor, connection, transactionId2, dbBase,
        transactionIdsList2,false );

    //Get deposit Ids for all the transactions recieved from the parent trans Id
    List<Long> depositIds = dbBase
        .getDepositIdList(connection,
            transactionIdsList);
    List<Long> depositIds2 = dbBase
        .getDepositIdList(connection,
            transactionIdsList2);

    Assert.assertEquals(depositIds, depositIds2,
        "The transactions are going to different deposits even though they are going to the same bank accounts ");

    //wait for deposit_item_created for all non-integrated batch items
    List<BatchItem> depositItems = dbBase
        .getBatchItems(transactionIdsList, connection);
    for (BatchItem batchItem : depositItems) {
      kafkaEventBase.waitForEventToProcess(KafkaEventBase.DEPOSIT_ITEM_CREATED,
          String.valueOf(batchItem.getProcId()));
      //validate that the ready to process flag is 0 for non integrated transactions
      int readyToProcess = batchItem.getReadyToProcess();
      Long depositId = batchItem.getDepositId();
      if(depositId != 0) {
        Assert.assertEquals(readyToProcess, 0,
            "The transaction did not get integrated but has ready to process as non 0");
      }
    }
    //Wait for deposit_item_integrated for all successfully integrated batch items
    List<BatchItem> depositItems2 = dbBase
        .getBatchItems(transactionIdsList2, connection);
    for (BatchItem batchItem : depositItems2) {
      kafkaEventBase.waitForEventToProcess(KafkaEventBase.DEPOSIT_ITEM_INTEGRATED,
          String.valueOf(batchItem.getProcId()));
    }

    //trigger end of day for each deposit
    for (long deposit : depositIds) {
      closeDepositOnLocal(String.valueOf(deposit));

      //validate that the batch is closed for the deposit that has successfully integrated transactions
      kafkaEventBase.waitForEventToProcess(KafkaEventBase.DEPOSIT_CLOSED,String.valueOf(deposit));
      String batchStatus = dbBase.getExternalBatch(deposit, connection).getStatus();
      Assert.assertEquals(batchStatus, STATUS_CLOSED,"The batch did not get closed successfully");

      //validate that the PostReceiptBatch is called for the deposit that has successfully integrated transactions
      IntegrationLoggingDao integrationLoggingDao = new IntegrationLoggingDao();
      Assert.assertFalse(integrationLoggingDao.findByDepositIdAndMethod(connection, deposit, 1,
          IntegrationLoggingDao.METHOD_CLOSE_BATCH).isEmpty(),
          "Expected to find a call to close the external batch");
    }

    List<Long> depositIds3 = dbBase.getDepositIdList(connection, transactionIdsList);
    List<Long> depositIds4 = dbBase.getDepositIdList(connection, transactionIdsList2);

    Assert.assertEquals(depositIds2, depositIds4,
        "The transactions integrated successfully got added to a new deposit");
    Assert.assertNotEquals(depositIds, depositIds3,
        "The transaction that failed integration did not get moved to a new deposit");




    //validate that non-integrated transactions are added to an exception deposit.
    for (long exceptionDeposit : depositIds3) {
      String depositType = dbBase.getDeposit(exceptionDeposit, connection).getDepositType();
      String depositStatus = dbBase.getDeposit(exceptionDeposit, connection).getStatus();
      Assert.assertEquals(depositType, EXCEPTION_DEPOSIT,
          "The deposit created for non integrated transactions is not an exception deposit");
      Assert.assertEquals(depositStatus, STATUS_CLOSED,
          "The exception deposit does not have a status of closed");

      //validate that the ready to process flag is updated to 1 when moved to an exception deposit
      List<BatchItem> depositItems1 = dbBase
          .getBatchItems(transactionIdsList, connection);
      for (BatchItem batchItem : depositItems1) {
        int readyToProcess = batchItem.getReadyToProcess();
        Assert.assertEquals(readyToProcess,1,"The transaction moved to exception deposit was not updated with ready to process as 1");
      }
    }

    kafkaConsumerClient.finish();
    dataBaseConnector.closeConnection();
  }

}