package com.paylease.app.qa.manual.tests.managedDeposits;

import static com.paylease.app.qa.framework.pages.admin.TransactionDetailPage.ADMIN_REFUND_MAX;
import static com.paylease.app.qa.framework.pages.admin.TransactionDetailPage.ADMIN_REFUND_PARTIAL;
import static com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage.NEW_BANK;
import static com.paylease.app.qa.framework.pages.pm.PmPaymentHistoryPage.REFUND_TYPE_FULL;
import static com.paylease.app.qa.framework.pages.pm.PmPaymentHistoryPage.REFUND_TYPE_PARTIAL_MAX;
import static com.paylease.app.qa.framework.pages.pm.PmPaymentHistoryPage.REFUND_TYPE_PARTIAL_PARTIAL;
import static com.paylease.app.qa.framework.pages.pm.PmPaymentHistoryPage.REFUND_TYPE_PARTIAL_SINGLE_FIELD;

import com.paylease.app.qa.framework.AppConstant;
import com.paylease.app.qa.framework.DataBaseConnector;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.components.Login;
import com.paylease.app.qa.framework.pages.admin.TransactionDetailPage;
import com.paylease.app.qa.framework.pages.automatedhelper.ReturnTransactionPage;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.pm.PmLoginPage;
import com.paylease.app.qa.framework.pages.pm.PmLogoutBar;
import com.paylease.app.qa.framework.pages.pm.PmPaymentHistoryPage;
import com.paylease.app.qa.framework.utility.database.client.dao.BatchItemDao;
import com.paylease.app.qa.framework.utility.database.client.dao.ExternalBatchesDao;
import com.paylease.app.qa.framework.utility.database.client.dao.ExternalTransactionsDao;
import com.paylease.app.qa.framework.utility.database.client.dao.IntegrationLoggingDao;
import com.paylease.app.qa.framework.utility.database.client.dao.TransactionDao;
import com.paylease.app.qa.framework.utility.database.client.dao.TransactionPaymentFieldDao;
import com.paylease.app.qa.framework.utility.database.client.dto.BatchItem;
import com.paylease.app.qa.framework.utility.database.client.dto.ExternalBatchesDto;
import com.paylease.app.qa.framework.utility.database.client.dto.ExternalTransactionsDto;
import com.paylease.app.qa.framework.utility.database.client.dto.IntegrationLoggingDto;
import com.paylease.app.qa.framework.utility.database.client.dto.Transaction;
import com.paylease.app.qa.framework.utility.database.client.dto.TransactionPaymentField;
import com.paylease.app.qa.framework.utility.kafka.KafkaConsumerClient;
import com.paylease.app.qa.framework.utility.kafka.KafkaProducerClient;
import com.paylease.app.qa.framework.utility.sshtool.SshUtil;
import com.paylease.app.qa.testbase.DbBase;
import com.paylease.app.qa.testbase.KafkaEventBase;
import com.paylease.app.qa.testbase.PaymentBase;
import com.paylease.app.qa.testbase.dataproviders.ManagedDepositsOneTimePaymentDataProvider;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.testng.Assert;
import org.testng.annotations.Test;

public class IntegrationServiceTest extends ScriptBase {

  private static final String REGION = "Integration";
  private static final String FEATURE = "ManagedDepositsForYavoIntegration";
  private ArrayList<String> paymentFieldList = new ArrayList<>();

  //--------------------------------DEPOSIT SERVICE FLOW TESTS-------------------------------------

  @Test(dataProvider = "otpDataIntegrationService", dataProviderClass =
      ManagedDepositsOneTimePaymentDataProvider.class, groups = {"manual"})
  public void integrateTransactionTest(String testVariationNo, String testSetup, String paymentType,
      boolean useResidentList, boolean expressPay, String processor) throws Exception {
    Logger.info(
        "PM otp, where test variation: " + testVariationNo + " with " + paymentType
            + " where resident list page being used is " + useResidentList
            + " and using express pay is " + expressPay);

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testSetup);
    testSetupPage.open();
    final String pmEmail = testSetupPage.getString("pmEmail");
    final String residentId = testSetupPage.getString("residentId");
    final String topicName = testSetupPage.getString("kafkaTopic");
    final String groupId = testSetupPage.getString("kafkaGroupId");

    //ArrayList<String> paymentFieldList = new ArrayList<>();
    paymentFieldList.add(testSetupPage.getString("paymentField1Label"));
    paymentFieldList.add(testSetupPage.getString("paymentField2Label"));
    paymentFieldList.add(testSetupPage.getString("paymentField3Label"));
    Collections.sort(paymentFieldList);

    PmLoginPage pmLoginPage = new PmLoginPage();

    pmLoginPage.open();
    pmLoginPage.login(pmEmail);

    PaymentBase paymentBase = new PaymentBase();

    String transIdString = paymentBase.pmOtPaymentActions(paymentType, useResidentList,
        expressPay, residentId, paymentFieldList);

    Long transactionId = Long.parseLong(transIdString);

    Logger.info(transIdString);

    DataBaseConnector dataBaseConnector = new DataBaseConnector();

    dataBaseConnector.createConnection();
    Connection connection = dataBaseConnector.getConnection();

    //Start the Consumer
    KafkaConsumerClient kafkaConsumerClient = new KafkaConsumerClient(AppConstant.KAFKA_BROKER_URL,
        AppConstant.USERNAME, AppConstant.PASSWORD, topicName, groupId);

    kafkaConsumerClient.start();

    DbBase dbBase = new DbBase();

    SshUtil sshUtil = new SshUtil();
    sshUtil.runIpnWorker();


    //get depositItemId/proc_id from batch_items table.
    List<BatchItem> depositItems = isTransactionIntegrated(processor, transactionId, connection, dbBase, kafkaConsumerClient);

    List<String> externalBatchIdsList = dbBase.getExternalBatchIdfromExternalTransactionsTable(connection,
        transactionId.intValue());

    ExternalBatchesDao externalBatchesDAO = new ExternalBatchesDao();

    ArrayList<Integer> depositId = new ArrayList<>();
    ArrayList<String> externalId = new ArrayList<>();

    //validate that the external batch is open
    for (String externalBatchId : externalBatchIdsList) {
      ExternalBatchesDto externalBatchRow = externalBatchesDAO
          .findById(connection, Long.parseLong(externalBatchId));

      Assert.assertEquals(externalBatchRow.getStatus(), "open",
          "The status of the depositId is not open ");
      Assert.assertFalse(externalBatchRow.getExternalId().isEmpty(), "The external Id is null");
      depositId.add(externalBatchRow.getDepositId());
      externalId.add(externalBatchRow.getExternalId());
    }

    // Validate that if batch items are going to different bank accounts then they are added to different external batches
    int firstDepositIdInDepositId = depositId.get(0);
    int secondDepositIdInDepositId = depositId.get(1);
    int thirdDepositIdInDepositId = depositId.get(2);

    String firstexternalId = externalId.get(0);
    String secondexternalId = externalId.get(1);
    String thirdexternalId = externalId.get(2);

    if (firstDepositIdInDepositId == secondDepositIdInDepositId) {
      Assert.assertEquals(firstexternalId, secondexternalId,
          "The transactions are added to different deposit even though they are going to same bank accounts");
    } else {
      Assert.assertNotEquals(firstexternalId, secondexternalId,
          "The transactions are added to same deposit even though they are going to different bank accounts");
    }
    if (secondDepositIdInDepositId == thirdDepositIdInDepositId) {
      Assert.assertEquals(secondexternalId, thirdexternalId,
          "The transactions are added to different deposit even though they are going to same bank accounts");
    } else {
      Assert.assertNotEquals(secondexternalId, thirdexternalId,
          "The transactions are added to same deposit even though they are going to different bank accounts");
    }
    if (thirdDepositIdInDepositId == firstDepositIdInDepositId) {
      Assert.assertEquals(thirdexternalId, firstexternalId,
          "The transactions are added to different deposit even though they are going to same bank accounts");
    } else {
      Assert.assertNotEquals(thirdexternalId, firstexternalId,
          "The transactions are added to same deposit even though they are going to different bank accounts");
    }

    //Validate that there is an logging for each integration request except when it is fee item.
    List<IntegrationLoggingDto> integrationLogRecords = getIntegrationLogRecords(connection,
        transactionId.intValue());

    //Validate that there is always a external batch ID for the integration logging record.
    for (IntegrationLoggingDto integrationLogging:integrationLogRecords) {
      String externalBatchId = integrationLogging.getExternalBatchId();
      Assert.assertFalse(externalBatchId.isEmpty(),"The External batch Id is NULL");
    }

    //validate that the deposit ID is updated for all the integration logging records.
    //validate that the there is no integration logging record for fee items.
    //validate that the method is "AddReceiptsToBatch" when a transaction is integrated and added to an external deposit.
    for (BatchItem depositItem : depositItems) {
      int depositItemId = depositItem.getProcId();
      long deposit = depositItem.getDepositId();
      boolean isCollectingFee = 1 == depositItem.getCollectingFee();
      boolean hasDepositItemRecord = logHasDepositItemRecord(integrationLogRecords, depositItemId, deposit,
          "AddReceiptsToBatch");
      if (isCollectingFee) {
        Assert.assertFalse(hasDepositItemRecord,
            "Deposit Item for fee has an integration_logging record for deposit item: "
                + depositItemId);
      } else {
        Assert.assertTrue(hasDepositItemRecord,
            "Does not have integration_logging record for deposit item: " + depositItemId);
      }
    }
    dataBaseConnector.closeConnection();
  }

  @Test(dataProvider = "refundTransIntegrationService", dataProviderClass = ManagedDepositsOneTimePaymentDataProvider.class,
      groups = {"manual"})
  public void refundTransactionPm(String testVariationNo, String testCaseSetup,
      String refundType, String paymentType, String processor)
      throws Exception {
    Logger.info("Pm, where test variation no and testcase: " + testVariationNo + "," + testCaseSetup
        + ". Verify refund transaction when "
        + " and isusingPaymenttype: " + paymentType
        + " and refundType: " + refundType
        + " and testCaseSetup: " + testCaseSetup);

    TestSetupPage testSetupPage;

    testSetupPage = new TestSetupPage(REGION, FEATURE, testCaseSetup);
    testSetupPage.open();

    ArrayList<String> paymentFieldList = new ArrayList<>();

    //Assemble the list of payment fields and get the size of the list.
    paymentFieldList.add(testSetupPage.getString("paymentField1Label"));
    paymentFieldList.add(testSetupPage.getString("paymentField2Label"));

    //Get test data strings
    String residentEmail = testSetupPage.getString("residentEmail");
    String pmEmail = testSetupPage.getString("pmEmail");
    final String topicName = testSetupPage.getString("kafkaTopic");
    final String groupId = testSetupPage.getString("kafkaGroupId");

    DataBaseConnector dataBaseConnector = new DataBaseConnector();
    dataBaseConnector.createConnection();
    Connection connection = dataBaseConnector.getConnection();

    //Create the transaction
    Transaction transaction = createTransaction(residentEmail, paymentFieldList, paymentType,
        connection);
    String transactionId = String.valueOf(transaction.getTransactionId());
    Logger.info(transactionId);

    DbBase dbBase = new DbBase();

    //Make the transaction paid out

    ArrayList<Transaction> payoutTransactionsList = dbBase
        .makeTransactionPaidOut(transactionId, connection);

    //Fetch the batch items for the transaction.
    getBatchItemsAndTriggerDepositItemCreatedEvent(processor, topicName, groupId, connection,
        transactionId);
    //Make the transaction refundable
    dbBase.makeTransactionRefundable(payoutTransactionsList, connection);

    //Login as the pm and click the refund link on the transaction history page
    PmLoginPage pmLoginPage = new PmLoginPage();
    pmLoginPage.open();
    pmLoginPage.login(pmEmail);

    PmPaymentHistoryPage pmPaymentHistoryPage = new PmPaymentHistoryPage();
    pmPaymentHistoryPage.open(true);

    //Start the Consumer to listen to the refund triggered event.
    KafkaConsumerClient kafkaConsumerClient = new KafkaConsumerClient(AppConstant.KAFKA_BROKER_URL,
        AppConstant.USERNAME, AppConstant.PASSWORD, topicName, groupId);

    kafkaConsumerClient.start();

    pmPaymentHistoryPage.clickRefundLink(transactionId);

    //Select the refund type
    //Prepare data for refund, will use for filling out inputs (in the case of partial refunds)
    // and for assertions at the end of the test
    HashMap<Transaction, String> refundsToPayoutsList = new HashMap<>();
    ArrayList<HashMap<String, String>> refundsDetailsList;

    if (refundType.equals(REFUND_TYPE_FULL)) {
      pmPaymentHistoryPage.clickFullRefundOption();

      for (Transaction payoutTransaction : payoutTransactionsList) {
        refundsToPayoutsList
            .put(payoutTransaction, String.valueOf(payoutTransaction.getUnitAmount()));
      }
    } else {
      pmPaymentHistoryPage.clickPartialRefundOption();
      //If partial refund, amount will depend on whether it is partial, max or over the limit
      switch (refundType) {
        case REFUND_TYPE_PARTIAL_PARTIAL:
          for (Transaction payoutTransaction : payoutTransactionsList) {
            refundsToPayoutsList.put(payoutTransaction, "1.00");
          }
          break;

        case REFUND_TYPE_PARTIAL_MAX:
          for (Transaction payoutTransaction : payoutTransactionsList) {
            refundsToPayoutsList
                .put(payoutTransaction, String.valueOf(payoutTransaction.getUnitAmount()));
          }
          break;

        case REFUND_TYPE_PARTIAL_SINGLE_FIELD:
          refundsToPayoutsList.put(payoutTransactionsList.get(1), "1.00");
          break;

        default:
          break;
      }

      //assemble list of refunds to bank accounts and payment fields
      refundsDetailsList = dbBase.createRefundDetails(refundsToPayoutsList, connection);
      //enter the amounts on the page (not needed for full refund)
      pmPaymentHistoryPage.enterPartialRefundAmount(refundsDetailsList);
    }

    //Click 'Initiate'
    pmPaymentHistoryPage.clickInitiateRefund();

    // click 'Continue' to refund the transaction.
    pmPaymentHistoryPage.clickContinueRefund();

    PmLogoutBar pmLogoutBar = new PmLogoutBar();
    pmLogoutBar.clickLogoutButton();

    List<BatchItem> depositItemsAfterRefund;

    //get depositItemId/proc_id for reversals from the batch items table.
    List<Transaction> transactionIdsList = dbBase.getReversalTransactions(connection,
        String.valueOf(transactionId));

    List<Long> reversalTransactionIds = new ArrayList<>();

    for (Transaction trans : transactionIdsList) {
      Long reversalDepositId = trans.getTransactionId();
      reversalTransactionIds.add(reversalDepositId);
    }

    depositItemsAfterRefund = dbBase.getBatchItems(reversalTransactionIds,
        connection);

    //Validate that there is an logging for each integration request except when it is fee item.
    List<IntegrationLoggingDto> integrationLogRecords = getIntegrationLogRecords(connection,
        Integer.parseInt(transactionId));

    //Validate that there is always a external batch ID for the integration logging record.
    for (IntegrationLoggingDto integrationLogging:integrationLogRecords) {
      String externalBatchId = integrationLogging.getExternalBatchId();
      Assert.assertFalse(externalBatchId.isEmpty(),"The External batch Id is empty");
    }

    //validate that for reversals , ImportResidentTransactions_Login is the method used
    for (BatchItem depositItem : depositItemsAfterRefund) {
      int depositItemId = depositItem.getProcId();
      long deposit = depositItem.getDepositId();
      Thread.sleep(1000);

      boolean isCollectingFee = 1 == depositItem.getCollectingFee();
      boolean hasDepositItemRecord = logHasDepositItemRecord(integrationLogRecords, depositItemId,
          deposit, "ImportResidentTransactions_Login");

      if (isCollectingFee) {
        Assert.assertFalse(hasDepositItemRecord,
            "Deposit Item for fee has an integration_logging record for deposit item: "
                + depositItemId);
      } else {
        Assert.assertEquals(getMethodFromIntegrationLogging(connection, depositItemId),
            "ImportResidentTransactions_Login",
            "The method is not 'ImportResidentTransactions_Login' " + depositItemId);
      }
      //Validate the externalBatchId for the integegrated Transaction.
      Assert.assertEquals(getExternalBatchIdFromIntegrationLogging(connection, depositItemId),
          "REVERSAL", " The external batch Id is not REVERSAL");

      //Validate the amount that has been integrated.
      switch (refundType) {
        case REFUND_TYPE_PARTIAL_PARTIAL:
          Assert.assertTrue(
              getRawRequestFromIntegrationLogging(connection, depositItemId).contains("1.00"),
              "The reversal amount is not equal to the amount refunded");
          break;

        case REFUND_TYPE_PARTIAL_MAX:
        case REFUND_TYPE_FULL:
          String unitAmount = String
              .valueOf(dbBase.getAmountFromBatchItemsTable(depositItemId, connection));
          Assert.assertTrue(
              getRawRequestFromIntegrationLogging(connection, depositItemId).contains(unitAmount));
          break;

        case REFUND_TYPE_PARTIAL_SINGLE_FIELD:
          Assert.assertTrue(
              getRawRequestFromIntegrationLogging(connection, depositItemId).contains("1.00"),
              "The reversal amount is not equal to the amount refunded");
          break;

        default:
          break;
      }

    }
    dataBaseConnector.closeConnection();
  }

  @Test(dataProvider = "reversalTransIntegrationDataAdmin", dataProviderClass = ManagedDepositsOneTimePaymentDataProvider.class,
      groups = {"e2e"})
  public void reverseBatchItemAdmin(String testVariationNo, String testCaseSetup,
      String reversalType, String paymentType, String processor) throws InterruptedException {
    Logger.info(
        "Admin, where test variation no and testcase: " + testVariationNo + "," + testCaseSetup
            + ". Verify refund transaction when "
            + " and reversalType: " + reversalType
            + " and paymentType: " + paymentType
            + " and testCaseSetup: " + testCaseSetup);

    DataBaseConnector dataBaseConnector = new DataBaseConnector();

    dataBaseConnector.createConnection();
    Connection connection = dataBaseConnector.getConnection();

    TestSetupPage testSetupPage;

    testSetupPage = new TestSetupPage(REGION, FEATURE, testCaseSetup);
    testSetupPage.open();

    ArrayList<String> paymentFieldList = new ArrayList<>();

    //Assemble the list of payment fields
    paymentFieldList.add(testSetupPage.getString("paymentField1Label"));
    paymentFieldList.add(testSetupPage.getString("paymentField2Label"));

    //Get test data strings
    String residentEmail = testSetupPage.getString("residentEmail");
    final String topicName = testSetupPage.getString("kafkaTopic");
    final String groupId = testSetupPage.getString("kafkaGroupId");

    //Create the transaction
    Transaction transaction = createTransaction(residentEmail, paymentFieldList, paymentType,
        connection);
    String transactionId = String.valueOf(transaction.getTransactionId());

    DbBase dbBase = new DbBase();

    dbBase.makeTransactionPaidOut(transactionId, connection);

    getBatchItemsAndTriggerDepositItemCreatedEvent(processor, topicName, groupId, connection,
        transactionId);

    //Start the Consumer to listen to the refund triggered event.
    KafkaConsumerClient kafkaConsumerClient = new KafkaConsumerClient(AppConstant.KAFKA_BROKER_URL,
        AppConstant.USERNAME, AppConstant.PASSWORD, topicName, groupId);
    kafkaConsumerClient.start();

    //login to admin UI and refund the transaction.
    Login login = new Login();
    login.logInAdmin();

    TransactionDetailPage transactionDetailPage = new TransactionDetailPage(transactionId);
    transactionDetailPage.open();

    int expectedReversalCount = 0;

    expectedReversalCount = dbBase.triggerReversalsAndGetExpectedRefundsCount(reversalType,
        transactionDetailPage, expectedReversalCount, processor);

    ArrayList<Transaction> reversalTransactions = dbBase.getReversalTransactions(connection,
        transactionId);

    //assert refund appears in database
    if (reversalType.equals("Max Refund Amount")) {
      Assert.assertEquals(reversalTransactions.size(), 2,
          "Expecting " + expectedReversalCount + " reversals but found " + reversalTransactions
              .size());
    } else {
      Assert.assertEquals(reversalTransactions.size(), 1,
          "Expecting " + expectedReversalCount + " reversals but found " + reversalTransactions
              .size());
    }

    login.logOutAdmin();

    List<BatchItem> depositItemsAfterRefund;

    //get depositItemId/proc_id for reversals from the batch items table.
    List<Transaction> transactionIdsList = dbBase.getReversalTransactions(connection,
        String.valueOf(transactionId));

    List<Long> reversalTransactionIds = new ArrayList<>();
    for (Transaction trans : transactionIdsList) {
      Long reversalDepositId = trans.getTransactionId();
      reversalTransactionIds.add(reversalDepositId);
    }

    depositItemsAfterRefund = dbBase.getBatchItems(reversalTransactionIds,
        connection);

    //Validate that there is an logging for each integration request except when it is fee item.
    List<IntegrationLoggingDto> integrationLogRecords = getIntegrationLogRecords(connection,
        Integer.parseInt(transactionId));

    //Validate that there is always a external batch ID for the integration logging record.
    for (IntegrationLoggingDto integrationLogging:integrationLogRecords) {
      String externalBatchId = integrationLogging.getExternalBatchId();
      Assert.assertFalse(externalBatchId.isEmpty(),"The External batch Id is empty");
    }

    //validate that for reversals , ImportResidentTransactions_Login is the method used
    for (BatchItem depositItem : depositItemsAfterRefund) {
      int depositItemId = depositItem.getProcId();
      long deposit = depositItem.getDepositId();
      Thread.sleep(1000);

      boolean isCollectingFee = 1 == depositItem.getCollectingFee();
      boolean hasDepositItemRecord = logHasDepositItemRecord(integrationLogRecords, depositItemId, deposit,
          "ImportResidentTransactions_Login");
      if (isCollectingFee) {
        Assert.assertFalse(hasDepositItemRecord,
            "Deposit Item for fee has an integration_logging record for deposit item: "
                + depositItemId);
      } else {
        Assert.assertEquals(getMethodFromIntegrationLogging(connection, depositItemId),
            "ImportResidentTransactions_Login",
            "The method is not 'ImportResidentTransactions_Login' " + depositItemId);
      }

      //Validate the externalBatchId for the integegrated Transaction.
      Assert.assertEquals(getExternalBatchIdFromIntegrationLogging(connection, depositItemId),
          "REVERSAL", " The external batch Id is not REVERSAL");

      //Validate the amount that has been integrated.
      switch (reversalType) {
        case ADMIN_REFUND_PARTIAL:
          Assert.assertTrue(
              getRawRequestFromIntegrationLogging(connection, depositItemId).contains("1.00"),
              "The reversal amount is not equal to the amount refunded");
          break;

        case ADMIN_REFUND_MAX:
          String unitAmount = String
              .valueOf(dbBase.getAmountFromBatchItemsTable(depositItemId, connection));
          Assert.assertTrue(
              getRawRequestFromIntegrationLogging(connection, depositItemId).contains(unitAmount));
          break;

        default:
          break;
      }
    }

    kafkaConsumerClient.finish();
    dataBaseConnector.closeConnection();
  }

  @Test(groups = {"manual"})
  public void returnedBatchItemsProfitStarsTest() throws Exception {

    BatchItemDao batchItemDao = new BatchItemDao();

    ExternalTransactionsDao externalTransactionsDao = new ExternalTransactionsDao();

    IntegrationLoggingDao integrationLoggingDao = new IntegrationLoggingDao();

    TransactionPaymentFieldDao transactionPaymentFieldDao = new TransactionPaymentFieldDao();

    SshUtil sshUtil = new SshUtil();

    DataBaseConnector dataBaseConnector = new DataBaseConnector();

    dataBaseConnector.createConnection();
    Connection connection = dataBaseConnector.getConnection();

    PmLoginPage pmLoginPage = new PmLoginPage();
    ReturnTransactionPage returnTransactionPage = new ReturnTransactionPage();

    String testSetup = "createYavoPmWithManagedDepositsV2";
    String paymentType = NEW_BANK;
    boolean useResidentList = false;
    boolean expressPay = false;

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testSetup);
    testSetupPage.open();
    final String pmEmail = testSetupPage.getString("pmEmail");
    final String residentId = testSetupPage.getString("residentId");
    final String topicName = testSetupPage.getString("kafkaTopic");
    KafkaConsumerClient kafkaConsumerClient = new KafkaConsumerClient(AppConstant.KAFKA_BROKER_URL,
        AppConstant.USERNAME, AppConstant.PASSWORD, topicName, UUID.randomUUID().toString());
    KafkaEventBase kafkaEventBase = new KafkaEventBase(kafkaConsumerClient);

    ArrayList<String> paymentFieldList = new ArrayList<>();
    paymentFieldList.add(testSetupPage.getString("paymentField1Label"));
    paymentFieldList.add(testSetupPage.getString("paymentField2Label"));
    paymentFieldList.add(testSetupPage.getString("paymentField3Label"));
    Collections.sort(paymentFieldList);

    pmLoginPage.open();
    pmLoginPage.login(pmEmail);

    PaymentBase paymentBase = new PaymentBase();

    String transIdStr = paymentBase.pmOtPaymentActions(paymentType, useResidentList, expressPay,
        residentId, paymentFieldList);

    Logger.info(transIdStr);

    kafkaConsumerClient.start();
    sshUtil.runIpnWorker();

    kafkaEventBase.waitForEvent(KafkaEventBase.ALL_DEPOSIT_ITEMS_CREATED, transIdStr);

    ArrayList<BatchItem> originalBatchItems = batchItemDao
        .findByTransId(connection, Long.parseLong(transIdStr), 50);

    Assert.assertEquals(originalBatchItems.size(), paymentFieldList.size() + 1,
        "There should be a batch item for each payment field plus 1 for the fee");

    BatchItem batchItem = originalBatchItems.get(0);

    String procId = Integer.toString(batchItem.getProcId());

    Assert.assertEquals(batchItem.getCollectingFee(), 0,
        "Sanity check that chosen batch item is not for a fee");

    TransactionPaymentField transactionPaymentField = transactionPaymentFieldDao
        .findById(connection, batchItem.getTransactionPaymentFieldId());

    kafkaEventBase.waitForEvent(KafkaEventBase.DEPOSIT_ITEM_INTEGRATED, procId);

    // Mark batch items as returned
    returnTransactionPage.open();
    returnTransactionPage.partialReturn();
    returnTransactionPage.returnTransaction(transIdStr);

    kafkaEventBase.waitForEvent(KafkaEventBase.BATCH_ITEM_RETURNED, procId);
    kafkaEventBase.waitForEvent(KafkaEventBase.DEPOSIT_ITEM_RETURNED, procId);
    kafkaEventBase.waitForEventToProcess(KafkaEventBase.DEPOSIT_ITEM_RETURNED, procId);

    ArrayList<ExternalTransactionsDto> externalTransactions = externalTransactionsDao
        .findByTransId(connection, Integer.parseInt(transIdStr), 50);

    Assert.assertEquals(externalTransactions.size(), paymentFieldList.size() + 1,
        "Expected 1 call per payment field integrations + 1 for reversal integration");

    ExternalTransactionsDto externalTransaction = externalTransactions
        .get(externalTransactions.size() - 1);

    Assert.assertEquals(externalTransaction.getProcId(), Integer.parseInt(procId),
        "Unexpected proc_id for external transaction " + externalTransaction.getId());

    Assert.assertEquals(externalTransaction.getType(), "RETURN",
        "Unexpected 'type' for external transaction " + externalTransaction.getId());

    ArrayList<IntegrationLoggingDto> integrationLogs = integrationLoggingDao
        .findByTransId(connection, Integer.parseInt(transIdStr), 50);

    Assert.assertEquals(integrationLogs.size(), paymentFieldList.size() + 2,
        "There should be 1 log per payment field, 1 for original open deposit, and 1 for reversal");

    IntegrationLoggingDto reversalLog = integrationLogs.get(integrationLogs.size() - 1);

    Assert.assertEquals(reversalLog.getMethod(), "ImportResidentTransactions_Login",
        "Wrong method call for Yardi reversal");

    Assert.assertTrue(reversalLog.getRequestRaw().contains("NSF"), "Raw Request should be for NSF");

    Assert.assertTrue(reversalLog.getRequestRaw().contains(Double.toString(batchItem.getAmount())),
        "Raw Request should contain the $ amount of the original batch item");

    Assert.assertTrue(reversalLog.getRequestRaw().contains(transactionPaymentField.getExtRefId()),
        "Raw Request should contain the ext_ref_id of the transaction payment field");

    Assert.assertTrue(reversalLog.getRequestRaw().contains("RETURN"),
        "Raw Request should say RETURN in the comment node");

    Assert.assertTrue(reversalLog.getResponseRaw().contains("\"status_code\":200"),
        "Raw Response should have an HTTP status of 200");

    // TODO: Update the following assertion once we have a way to close the batch with Yardi
    Assert.assertTrue(reversalLog.getResponseRaw().contains("Could not locate receipt to reverse"),
        "Expected Yardi to fail in finding the original receipt since batch is not closed");

    kafkaConsumerClient.finish();
    dataBaseConnector.closeConnection();
  }

  //--------------------------------TEST METHOD-----------------------------------------------------


  public List<BatchItem> isTransactionIntegrated(String processor, Long transactionId, Connection connection, DbBase dbBase, KafkaConsumerClient kafkaConsumerClient) throws Exception {
        List<BatchItem> depositItems;

    //get depositItemId/proc_id from batch_items table.
    if (processor.equals("Profitstars")) {
      depositItems = getDepositItemIdsFromBatchItemsTable(transactionId, connection);
    } else {
      List<Long> transactionIdsList = dbBase
          .getTransactionListForParentTransId(transactionId, connection);
      depositItems = dbBase.getBatchItems(transactionIdsList, connection);
    }

    //wait for deposit item integrated event for each deposit item
    for (BatchItem depositItem : depositItems) {
      waitForDepositItemIntegrated(kafkaConsumerClient, depositItem);
    }

    //retrieve external transaction on the transaction_id
    ExternalTransactionsDao externalTransactionsDAO = new ExternalTransactionsDao();
    List<ExternalTransactionsDto> externalTransactions = externalTransactionsDAO
        .findByTransId(connection, transactionId.intValue(), 10);

    Assert.assertFalse(externalTransactions.isEmpty(), "External Transactions missing");

    //validate that the transaction has integrated successfully and has a type of "PAYMENT" and has a not null external_id
    for (ExternalTransactionsDto externalTransaction : externalTransactions) {
      Assert.assertEquals(externalTransaction.getStatus(), "SUCCESS",
          "The transaction did not integrate successfully");
      Assert.assertEquals(externalTransaction.getType(), "PAYMENT", "The type of the transaction is not PAYMENT");
      Assert.assertFalse(externalTransaction.getExternalId().isEmpty(),
          "There is no external_Id associated with the transaction");

    }
    return depositItems;
  }

  private Transaction createTransaction(String residentEmail,
      ArrayList<String> paymentFieldList, String paymentType, Connection connection) {

    DataBaseConnector dataBaseConnector = new DataBaseConnector();

    com.paylease.app.qa.e2e.tests.resident.OneTimePaymentTest oneTimePaymentTest = new com.paylease.app.qa.e2e.tests.resident.OneTimePaymentTest();

    String transactionId = oneTimePaymentTest
        .residentOtPaymentActions(residentEmail, paymentType, false, false, paymentFieldList);

    TransactionDao transactionDao = new TransactionDao();
    Transaction transaction = new Transaction();

    try {
      ArrayList<Transaction> transactionsList = transactionDao
          .findById(connection, Long.parseLong(transactionId), 1);
      transaction = transactionsList.get(0);
    } catch (Exception se) {
      se.getMessage();
      dataBaseConnector.closeConnection();
    }
    return transaction;
  }

  private List<BatchItem> getDepositItemIdsFromBatchItemsTable(long transactionId,
      Connection connection) {
    BatchItemDao batchItemDao = new BatchItemDao();

    return batchItemDao.findByTransId(connection, transactionId, 50);
  }

  private void waitForDepositItemIntegrated(KafkaConsumerClient kafkaConsumerClient,
      BatchItem expectedDepositItem) throws Exception {
    KafkaEventBase kafkaEventBase = new KafkaEventBase(kafkaConsumerClient);

    kafkaEventBase.waitForEvent(KafkaEventBase.DEPOSIT_ITEM_INTEGRATED,
        String.valueOf(expectedDepositItem.getProcId()));
  }

  private List<IntegrationLoggingDto> getIntegrationLogRecords(Connection connection,
      int transactionId) {
    IntegrationLoggingDao integrationLoggingDao = new IntegrationLoggingDao();

    return integrationLoggingDao.findByTransId(connection, transactionId, 50);
  }

  private boolean logHasDepositItemRecord(List<IntegrationLoggingDto> integrationLogging,
      int depositItemId, long depositId, String method) {

    IntegrationLoggingDto dto = integrationLogging.stream()
        .filter(log -> log.getProcId() == depositItemId)
        .filter(log -> log.getMethod().equals(method))
        .filter(log -> log.getStatus().equals("success"))
        .filter(log -> log.getDepositId() == depositId)
        .findAny()
        .orElse(null);

    return null != dto;
  }

  private String getMethodFromIntegrationLogging(Connection connection, int depositId) {
    String integrationMethod = "";

    IntegrationLoggingDao integrationLoggingDao = new IntegrationLoggingDao();

    List<IntegrationLoggingDto> methods = integrationLoggingDao
        .findByProcId(connection, depositId, 10);
    for (IntegrationLoggingDto method : methods) {
      integrationMethod = method.getMethod();
    }
    return integrationMethod;
  }

  private String getRawRequestFromIntegrationLogging(Connection connection, int depositId) {
    String rawRequest = "";

    IntegrationLoggingDao integrationLoggingDao = new IntegrationLoggingDao();

    List<IntegrationLoggingDto> procIds = integrationLoggingDao
        .findByProcId(connection, depositId, 10);
    for (IntegrationLoggingDto procId : procIds) {
      rawRequest = procId.getRequestRaw();
    }
    return rawRequest;
  }

  private String getExternalBatchIdFromIntegrationLogging(Connection connection, int depositId) {
    String externalBatchId = "";

    IntegrationLoggingDao integrationLoggingDao = new IntegrationLoggingDao();

    List<IntegrationLoggingDto> procIds = integrationLoggingDao
        .findByProcId(connection, depositId, 10);
    for (IntegrationLoggingDto procId : procIds) {
      externalBatchId = procId.getExternalBatchId();
    }
    return externalBatchId;
  }

  private void getBatchItemsAndTriggerDepositItemCreatedEvent(String processor, String topicName,
      String groupId, Connection connection, String transactionId) {

    List<BatchItem> depositItems;

    if (processor.equals("Profitstars")) {
      depositItems = getDepositItemIdsFromBatchItemsTable(Long.parseLong(transactionId),
          connection);
    } else {
      DbBase dbBase = new DbBase();

      List<Long> transactionIdsList = dbBase.getTransactionListForParentTransId(
          Long.parseLong(transactionId), connection);
      depositItems = dbBase.getBatchItems(transactionIdsList, connection);
    }

    //fire the event for each of the batch items so that they can be integrated.
    for (BatchItem depositItem : depositItems) {
      KafkaProducerClient kafkaProducerClient = new KafkaProducerClient(
          AppConstant.KAFKA_BROKER_URL, AppConstant.USERNAME, AppConstant.PASSWORD, topicName, groupId);

      Producer producer = kafkaProducerClient.createProducer();
      String integratedEvent = "{\"context\":{\"transaction_id\":" + depositItem.getTransId()
          + ",\"type_of_transaction\":5,\"deposit_item_id\":" + depositItem.getProcId()
          + "},\"eventName\":\"DEPOSIT_ITEM_CREATED\",\"correlationId\":\"d0187f23-3940-44b7-8464-2b2545ff9547\"}";
      ProducerRecord producerRecord = new ProducerRecord<String, String>(topicName,
          integratedEvent);
      producer.send(producerRecord);
    }
  }
}