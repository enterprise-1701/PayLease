package com.paylease.app.qa.manual.tests.managedDeposits;

import com.paylease.app.qa.framework.AppConstant;
import com.paylease.app.qa.framework.DataBaseConnector;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.UtilityManager;
import com.paylease.app.qa.framework.components.Login;
import com.paylease.app.qa.framework.components.Login.UserType;
import com.paylease.app.qa.framework.pages.admin.AdminPropertyLockPage;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.pm.PropertyLockPage;
import com.paylease.app.qa.framework.utility.database.client.dao.DepositDao;
import com.paylease.app.qa.framework.utility.database.client.dao.ExternalBatchesDao;
import com.paylease.app.qa.framework.utility.database.client.dao.IntegrationLoggingDao;
import com.paylease.app.qa.framework.utility.database.client.dao.PropertyLockScheduleDao;
import com.paylease.app.qa.framework.utility.database.client.dao.TransactionsPropertyLockDao;
import com.paylease.app.qa.framework.utility.database.client.dto.BatchItem;
import com.paylease.app.qa.framework.utility.database.client.dto.ExternalBatchesDto;
import com.paylease.app.qa.framework.utility.database.client.dto.PropertyLockSchedule;
import com.paylease.app.qa.framework.utility.database.client.dto.TransactionsPropertyLock;
import com.paylease.app.qa.framework.utility.kafka.KafkaConsumerClient;
import com.paylease.app.qa.framework.utility.sshtool.SshUtil;
import com.paylease.app.qa.testbase.DbBase;
import com.paylease.app.qa.testbase.KafkaEventBase;
import com.paylease.app.qa.testbase.PaymentBase;
import com.paylease.app.qa.testbase.dataproviders.ManagedDepositsOneTimePaymentDataProvider;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PropertyLockTest extends ScriptBase {

  private static final String REGION = "Integration";
  private static final String FEATURE = "ManagedDepositsForYavoIntegration";
  private static final String PROCESSOR_PROFITSTARS = "Profitstars";

  //-----------------------------PROPERTY LOCK with MANAGED DEPOSITS TESTS--------------------------

  @Test(dataProvider = "otpDataIntegrationServiceWithPropertyLock", dataProviderClass = ManagedDepositsOneTimePaymentDataProvider.class, groups =
      {"manual"})
  public void otpDataIntegrationServiceWithPropertyLock(String testVariationNo, String testSetup, String paymentType, String processor, boolean isPropertyLockScheduled) throws Exception {
    Logger.info(
        "PM property lock, where test variation: " + testVariationNo + " and isPropertyLockScheduled: " + isPropertyLockScheduled);

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testSetup);
    testSetupPage.open();
    final String pmEmail = testSetupPage.getString("pmEmail");
    final String residentId = testSetupPage.getString("residentId");
    final String topicName = testSetupPage.getString("kafkaTopic");
    final String groupId = testSetupPage.getString("kafkaGroupId");
    final String propertyId = testSetupPage.getString("propertyId");
    final String pmId = testSetupPage.getString("pmId");

    ArrayList<String> paymentFieldList = new ArrayList<>();
    paymentFieldList.add(testSetupPage.getString("paymentField1Label"));
    paymentFieldList.add(testSetupPage.getString("paymentField2Label"));
    paymentFieldList.add(testSetupPage.getString("paymentField3Label"));
    Collections.sort(paymentFieldList);

    Login login = new Login();
    login.logInUser(pmEmail, UserType.PM);

    IntegrationServiceTest integrationServiceTest = new IntegrationServiceTest();

    String transactionId = createPaymentTransaction(residentId, pmEmail, paymentType, paymentFieldList);

    Logger.info("Transaction ID: " + transactionId);

    DataBaseConnector dataBaseConnector = new DataBaseConnector();
    dataBaseConnector.createConnection();
    Connection connection = dataBaseConnector.getConnection();

    //Start the Consumer
    KafkaConsumerClient kafkaConsumerClient = new KafkaConsumerClient(AppConstant.KAFKA_BROKER_URL,
        AppConstant.USERNAME,
        AppConstant.PASSWORD, topicName, groupId);
    kafkaConsumerClient.start();

    DbBase dbBase = new DbBase();

    SshUtil sshUtil = new SshUtil();
    sshUtil.runIpnWorker();

    KafkaEventBase kafkaEventBase = new KafkaEventBase(kafkaConsumerClient);
    kafkaEventBase.waitForEventToProcess(KafkaEventBase.ALL_DEPOSIT_ITEMS_CREATED,
        transactionId);

    List<BatchItem> depositItems = integrationServiceTest
        .isTransactionIntegrated(processor, Long.parseLong(transactionId), connection, dbBase, kafkaConsumerClient);

    ArrayList<Long> depositIds = new ArrayList<>();

    for (BatchItem depositItem : depositItems) {
      if (depositItem.getDepositId() != 0) {
        depositIds.add(depositItem.getDepositId());
      }
    }

    for (Long depositId : depositIds) {
      Assert.assertTrue(
          dbBase
              .checkDepositsTableForStatus(depositId, connection, DepositDao.STATUS_OPEN),
          "Expected the deposit status to be open");
    }

    //Enter property lock
    Logger.info("Processing lock for property: " + propertyId);
    if(isPropertyLockScheduled) {
      scheduleAndLockProperty(pmId, propertyId, connection);
    } else {
      lockPropertyOnPmUi(pmEmail, propertyId);
    }

    Assert.assertTrue(
        validateEnteringPropertyLock(propertyId, transactionId, depositIds, connection,
            kafkaConsumerClient, false, pmId), "Validation failed after entering property lock");

    kafkaConsumerClient.finish();
  }


  @Test(dataProvider = "otpDataIntegrationServiceWithPropertyLockBatchByBankAccount", dataProviderClass = ManagedDepositsOneTimePaymentDataProvider.class, groups =
      {"manual"})
  public void otpDataIntegrationServiceWithPropertyLockBatchByBankAccount(String testVariationNo,
      String testSetup, String paymentType, String processor, boolean isPropertyLockScheduled) throws Exception {
    Logger.info(
        "PM property lock, where test variation: " + testVariationNo + " and isPropertyLockScheduled: " + isPropertyLockScheduled);

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testSetup);
    testSetupPage.open();
    final String pmEmail = testSetupPage.getString("pmEmail");
    final String residentId = testSetupPage.getString("residentId");
    final String residentTwoId = testSetupPage.getString("residentTwoId");
    final String topicName = testSetupPage.getString("kafkaTopic");
    final String groupId = testSetupPage.getString("kafkaGroupId");
    final String propertyId = testSetupPage.getString("propertyId");
    final String propertyTwoId = testSetupPage.getString("propertyTwoId");
    final String pmId = testSetupPage.getString("pmId");

    ArrayList<String> paymentFieldList = new ArrayList<>();
    paymentFieldList.add(testSetupPage.getString("paymentField1Label"));
    paymentFieldList.add(testSetupPage.getString("paymentField2Label"));
    paymentFieldList.add(testSetupPage.getString("paymentField3Label"));
    Collections.sort(paymentFieldList);

    IntegrationServiceTest integrationServiceTest = new IntegrationServiceTest();

    //Create payment transactions
    String transactionId = createPaymentTransaction(residentId, pmEmail, paymentType, paymentFieldList);
    String transactionTwoId = createPaymentTransaction(residentTwoId, pmEmail, paymentType, paymentFieldList);

    Logger.info("Transaction 1 ID: " + transactionId);
    Logger.info("Transaction 2 ID: " + transactionTwoId);

    //Create db connection
    DataBaseConnector dataBaseConnector = new DataBaseConnector();
    dataBaseConnector.createConnection();
    Connection connection = dataBaseConnector.getConnection();

    //Start the Consumer
    KafkaConsumerClient kafkaConsumerClient = new KafkaConsumerClient(AppConstant.KAFKA_BROKER_URL,
        AppConstant.USERNAME,
        AppConstant.PASSWORD, topicName, groupId);
    kafkaConsumerClient.start();

    DbBase dbBase = new DbBase();

    SshUtil sshUtil = new SshUtil();
    sshUtil.runIpnWorker();

    KafkaEventBase kafkaEventBase = new KafkaEventBase(kafkaConsumerClient);

    //Wait until event "ALL_DEPOSIT_ITEMS_CREATED" exists for each transaction
    kafkaEventBase.waitForEventToProcess(KafkaEventBase.ALL_DEPOSIT_ITEMS_CREATED,
        transactionId);
    kafkaEventBase.waitForEventToProcess(KafkaEventBase.ALL_DEPOSIT_ITEMS_CREATED,
        transactionTwoId);

    //Verify that transactions were integrated
    List<BatchItem> depositItems = integrationServiceTest
        .isTransactionIntegrated(processor, Long.parseLong(transactionId), connection, dbBase, kafkaConsumerClient);

    List<BatchItem> depositItemsTwo = integrationServiceTest
        .isTransactionIntegrated(processor, Long.parseLong(transactionTwoId), connection, dbBase,
            kafkaConsumerClient);

    //Get the deposit id's for each transaction
    ArrayList<Long> depositIds = new ArrayList<>();
    ArrayList<Long> depositTwoIds = new ArrayList<>();

    for (BatchItem depositItem : depositItems) {
      if (depositItem.getDepositId() != 0) {
        depositIds.add(depositItem.getDepositId());
      }
    }

    for (BatchItem depositItem : depositItemsTwo) {
      if (depositItem.getDepositId() != 0) {
        depositTwoIds.add(depositItem.getDepositId());
      }
    }

    for (Long depositId : depositIds) {
      Assert.assertTrue(
          dbBase
              .checkDepositsTableForStatus(depositId, connection, DepositDao.STATUS_OPEN),
          "Expected the deposit status to be open");
    }

    for (Long depositId : depositTwoIds) {
      Assert.assertTrue(
          dbBase
              .checkDepositsTableForStatus(depositId, connection, DepositDao.STATUS_OPEN),
          "Expected the deposit status to be open");
    }

    //Enter property lock
    Logger.info("Processing lock for properties: " + propertyId + ", " + propertyTwoId);
    if(isPropertyLockScheduled) {
      schedulAndLockPropertiesBatchByBank(pmId, connection);
    } else {
      lockAllPropertiesOnPmUi(pmEmail);
    }

    //Verify property lock event exists. deposits and batches are closed after property is locked
    Assert.assertTrue(
        validateEnteringPropertyLock(propertyId, transactionId, depositIds, connection,
            kafkaConsumerClient, true, pmId),
        "Validation failed after entering property lock for first property");
    Assert.assertTrue(
        validateEnteringPropertyLock(propertyTwoId, transactionTwoId, depositTwoIds, connection,
            kafkaConsumerClient, true, pmId),
        "Validation failed after entering property lock for second property");


    kafkaConsumerClient.finish();
  }

  @Test(dataProvider = "otpDataIntegrationServiceWithPropertyLock", dataProviderClass = ManagedDepositsOneTimePaymentDataProvider.class, groups =
      {"manual"})
  public void duringPropertyLockTest(String testVariationNo, String testSetup, String paymentType, String processor, boolean isPropertyLockScheduled) throws Exception {
    Logger.info(
        "PM property lock, where test variation: " + testVariationNo  + " and processor: " + processor);

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testSetup);
    testSetupPage.open();
    final String pmEmail = testSetupPage.getString("pmEmail");
    final String residentId = testSetupPage.getString("residentId");
    final String topicName = testSetupPage.getString("kafkaTopic");
    final String groupId = testSetupPage.getString("kafkaGroupId");
    final String propertyId = testSetupPage.getString("propertyId");
    final String pmId = testSetupPage.getString("pmId");

    ArrayList<String> paymentFieldList = new ArrayList<>();
    paymentFieldList.add(testSetupPage.getString("paymentField1Label"));
    paymentFieldList.add(testSetupPage.getString("paymentField2Label"));
    paymentFieldList.add(testSetupPage.getString("paymentField3Label"));
    Collections.sort(paymentFieldList);

    KafkaConsumerClient kafkaConsumerClient = new KafkaConsumerClient(AppConstant.KAFKA_BROKER_URL,
        AppConstant.USERNAME,
        AppConstant.PASSWORD, topicName, groupId);
    kafkaConsumerClient.start();

    KafkaEventBase kafkaEventBase = new KafkaEventBase(kafkaConsumerClient);

    DataBaseConnector dataBaseConnector = new DataBaseConnector();
    dataBaseConnector.createConnection();
    Connection connection = dataBaseConnector.getConnection();

    //Enter property lock
    Logger.info("Processing lock for property: " + propertyId);
    if(isPropertyLockScheduled) {
      scheduleAndLockProperty(pmId, propertyId, connection);
    } else {
      lockPropertyOnPmUi(pmEmail, propertyId);
    }

    kafkaEventBase.waitForEvent(KafkaEventBase.PROPERTY_LOCK_ENTERED, propertyId);

    String transactionId = createPaymentTransaction(residentId, pmEmail, paymentType, paymentFieldList);

    SshUtil sshUtil = new SshUtil();
    sshUtil.runIpnWorker();

    TransactionsPropertyLockDao transactionsPropertyLockDao = new TransactionsPropertyLockDao();

    ArrayList<TransactionsPropertyLock> transactionsPropertyLockArrayList = transactionsPropertyLockDao.findById(connection, Long.parseLong(transactionId), 1);

    Assert.assertTrue(transactionsPropertyLockArrayList.size() == 1);

    Assert.assertNull(
        kafkaEventBase.getEventNameFromMessageOnTopic(transactionId, kafkaConsumerClient, false),
        "Did not expected any event fired for this transaction as property is locked");

    kafkaConsumerClient.finish();
  }

  @Test(dataProvider = "otpDataIntegrationServiceWithPropertyLock", dataProviderClass = ManagedDepositsOneTimePaymentDataProvider.class, groups =
      {"manual"})
  public void exitingPropertyLockTest(String testVariationNo, String testSetup, String paymentType, String processor, boolean isPropertyLockScheduled) throws Exception{
    Logger.info(
        "PM property lock, where test variation: " + testVariationNo + " and processor: " + processor);
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testSetup);
    testSetupPage.open();
    final String pmEmail = testSetupPage.getString("pmEmail");
    final String residentId = testSetupPage.getString("residentId");
    final String topicName = testSetupPage.getString("kafkaTopic");
    final String groupId = testSetupPage.getString("kafkaGroupId");
    final String propertyId = testSetupPage.getString("propertyId");
    final String pmId = testSetupPage.getString("pmId");

    ArrayList<String> paymentFieldList = new ArrayList<>();
    paymentFieldList.add(testSetupPage.getString("paymentField1Label"));
    paymentFieldList.add(testSetupPage.getString("paymentField2Label"));
    paymentFieldList.add(testSetupPage.getString("paymentField3Label"));
    Collections.sort(paymentFieldList);

    KafkaConsumerClient kafkaConsumerClient = new KafkaConsumerClient(AppConstant.KAFKA_BROKER_URL,
        AppConstant.USERNAME,
        AppConstant.PASSWORD, topicName, groupId);
    kafkaConsumerClient.start();

    KafkaEventBase kafkaEventBase = new KafkaEventBase(kafkaConsumerClient);

    DataBaseConnector dataBaseConnector = new DataBaseConnector();
    dataBaseConnector.createConnection();
    Connection connection = dataBaseConnector.getConnection();

    //Enter property lock
    Logger.info("Processing lock for property: " + propertyId);
    if(isPropertyLockScheduled) {
      scheduleAndLockProperty(pmId, propertyId, connection);
    } else {
      lockPropertyOnPmUi(pmEmail, propertyId);
    }

    kafkaEventBase.waitForEvent(KafkaEventBase.PROPERTY_LOCK_ENTERED, propertyId);

    //Create a payment transaction
    String transactionId = createPaymentTransaction(residentId, pmEmail, paymentType, paymentFieldList);

    Logger.info("Transaction ID: " + transactionId);

    //Run ipn worker
    SshUtil sshUtil = new SshUtil();
    sshUtil.runIpnWorker();

    //Verify transactions are saved in transactions_property_lock table
    TransactionsPropertyLockDao transactionsPropertyLockDao = new TransactionsPropertyLockDao();

    ArrayList<TransactionsPropertyLock> transactionsPropertyLockArrayList = transactionsPropertyLockDao.findById(connection, Long.parseLong(transactionId), 1);

    Assert.assertTrue(transactionsPropertyLockArrayList.size() == 1);

    //Verify an event is not triggered for this transaction id
    Assert.assertNull(
        kafkaEventBase.getEventNameFromMessageOnTopic(transactionId, kafkaConsumerClient, false),
        "Did not expected any event fired for this transaction as property is locked");

    //run unlock script
    sshUtil.runScriptByDirectoryName("unlockProperties", null, "managed_deposits/");

    //start a new ipn worker
    sshUtil.runIpnWorker();

    //Verify transaction_created event is triggered
    kafkaEventBase.waitForEvent(KafkaEventBase.TRANSACTION_CREATED, transactionId);

    //Verify transactions_property_lock is removed from the table
    transactionsPropertyLockArrayList = transactionsPropertyLockDao.findById(connection, Long.parseLong(transactionId), 1);

    Assert.assertTrue(transactionsPropertyLockArrayList.size() == 0);

    kafkaConsumerClient.finish();
  }

  //--------------------------------TEST METHOD-----------------------------------------------------

  /**
   * Helper test method to do all the validation after a property goes into property lock.
   *
   * @param propertyId Property Id
   * @param transactionId Transaction Id
   * @param depositIds List of Deposit Ids
   * @return boolean
   */
  private boolean validateEnteringPropertyLock(String propertyId, String transactionId,
      ArrayList<Long> depositIds, Connection connection, KafkaConsumerClient kafkaConsumerClient, boolean batchByBankAccount, String pmId)
      throws Exception {
    KafkaEventBase kafkaEventBase = new KafkaEventBase(kafkaConsumerClient);

    kafkaEventBase.waitForEvent(KafkaEventBase.PROPERTY_LOCK_ENTERED, pmId);

    String propId = kafkaEventBase.getContextFromPayload(KafkaEventBase.PROPERTY_LOCK_ENTERED, "prop_id");

    if(batchByBankAccount){
      Assert.assertNull(propId," the prop id is not null");
    }else {
      Assert.assertEquals(propId,propertyId,"The prop ids match");
    }


    IntegrationLoggingDao integrationLoggingDao = new IntegrationLoggingDao();
    ExternalBatchesDao externalBatchesDao = new ExternalBatchesDao();

    DbBase dbBase = new DbBase();
    List<String> externalBatchIdsList =
        dbBase.getExternalBatchIdfromExternalTransactionsTable(connection,
            Integer.parseInt(transactionId));

    for (Long depositId : depositIds) {
      kafkaEventBase.waitForEvent(KafkaEventBase.DEPOSIT_CLOSED, String.valueOf(depositId));
      kafkaEventBase
          .waitForEventToProcess(KafkaEventBase.DEPOSIT_CLOSED, String.valueOf(depositId));
      Assert.assertTrue(
          dbBase
              .checkDepositsTableForStatus(depositId, connection, DepositDao.STATUS_CLOSED),
          "Expected the deposit status to be closed");
      Assert.assertFalse(integrationLoggingDao.findByDepositIdAndMethod(connection, depositId, 1,
          IntegrationLoggingDao.METHOD_CLOSE_BATCH).isEmpty(),
          "Expected to find a call to close the external batch");
    }

    for (String externalBatchId : externalBatchIdsList) {
      ExternalBatchesDto externalBatchRow = externalBatchesDao
          .findById(connection, Long.parseLong(externalBatchId));

      Assert.assertEquals(externalBatchRow.getStatus(), ExternalBatchesDao.STATUS_CLOSED,
          "The status of the depositId is not closed ");
    }
    return true;
  }

  private int updatePropertyLockScheduleByPropId(String propertyId, Connection connection) {
    PropertyLockScheduleDao propertyLockScheduleDao = new PropertyLockScheduleDao();
    PropertyLockSchedule propertyLockSchedule;

    ArrayList<PropertyLockSchedule> propertyLockSchedules = propertyLockScheduleDao.findByPropId(connection, Long.parseLong(propertyId), 1);

    propertyLockSchedule = propertyLockSchedules.get(0);

    int updatedRows = 0;

    propertyLockSchedule.setLockDate(UtilityManager.getCurrentTimeMinusTwoMin(UtilityManager.YEAR_MONTH_DAY_HH_MM_SS));

    updatedRows = propertyLockScheduleDao.updateByPropId(connection, propertyLockSchedule);

    return updatedRows;
  }

  private int updatePropertyLockScheduleByPmId(String pmId, Connection connection) {
    PropertyLockScheduleDao propertyLockScheduleDao = new PropertyLockScheduleDao();
    PropertyLockSchedule propertyLockSchedule;

    ArrayList<PropertyLockSchedule> propertyLockSchedules = propertyLockScheduleDao.findByPmId(connection, Long.parseLong(pmId), 5);

    propertyLockSchedule = propertyLockSchedules.get(0);

    int updatedRows = 0;

    propertyLockSchedule.setLockDate(UtilityManager.getCurrentTimeMinusTwoMin(UtilityManager.YEAR_MONTH_DAY_HH_MM_SS));

    updatedRows = propertyLockScheduleDao.updateByPmId(connection, propertyLockSchedule);

    return updatedRows;
  }

  private void lockAllPropertiesOnPmUi(String pmEmail) {
    Login login = new Login();
    login.logInUser(pmEmail, UserType.PM);

    PropertyLockPage propertyLockPage = new PropertyLockPage();
    propertyLockPage.open();
    propertyLockPage.lockAllProperties();

    login.logOutPm();
  }

  private void lockPropertyOnPmUi(String pmEmail, String propertyId) {
    Login login = new Login();
    login.logInUser(pmEmail, UserType.PM);

    Logger.info("Processing lock for property: " + propertyId);
    PropertyLockPage propertyLockPage = new PropertyLockPage();
    propertyLockPage.open();
    propertyLockPage.lockProperty(propertyId);

    login.logOutPm();
  }

  private void scheduleAndLockProperty(String pmId, String propertyId, Connection connection) {
    Login login = new Login();
    login.logInAdmin();
    AdminPropertyLockPage adminPropertyLockPage = new AdminPropertyLockPage(pmId);
    adminPropertyLockPage.open();
    adminPropertyLockPage.scheduleNextEarliestLock(propertyId);

    Assert.assertTrue(adminPropertyLockPage.getPropertyLockMessage().equals("Property lock successfully scheduled"));

    int updatedRows = updatePropertyLockScheduleByPropId(propertyId, connection);

    Assert.assertTrue(updatedRows == 1);

    //Run script to lock properties
    SshUtil sshUtil = new SshUtil();
    sshUtil.runBatchScript("process_scheduled_property_locks");

    login.logOutAdmin();
  }

  private void schedulAndLockPropertiesBatchByBank(String pmId, Connection connection) {
    Login login = new Login();
    login.logInAdmin();
    AdminPropertyLockPage adminPropertyLockPage = new AdminPropertyLockPage(pmId);
    adminPropertyLockPage.open();
    adminPropertyLockPage.scheduleNextEarliestLock(null);

    Assert.assertTrue(adminPropertyLockPage.getPropertyLockMessage().equals("Property lock successfully scheduled"));

    int updatedRows = updatePropertyLockScheduleByPmId(pmId, connection);

    Assert.assertTrue(updatedRows == 1);

    //Run script to lock properties
    SshUtil sshUtil = new SshUtil();
    sshUtil.runBatchScript("process_scheduled_property_locks");

    login.logOutAdmin();
  }

  private String createPaymentTransaction(String residentId, String pmEmail, String paymentType, ArrayList<String> paymentFieldList) {
    Login login = new Login();
    login.logInUser(pmEmail, UserType.PM);

    PaymentBase paymentBase = new PaymentBase();

    String transactionId = paymentBase
        .pmOtPaymentActions(paymentType, false, false,  residentId,
            paymentFieldList);

    return transactionId;
  }
}
