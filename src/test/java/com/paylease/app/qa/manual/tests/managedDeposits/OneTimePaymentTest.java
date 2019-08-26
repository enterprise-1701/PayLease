package com.paylease.app.qa.manual.tests.managedDeposits;

import static com.paylease.app.qa.framework.features.PaymentFlow.SCHEDULE_ONE_TIME;
import static com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage.NEW_BANK;
import static com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage.NEW_CREDIT;
import static com.paylease.app.qa.framework.pages.pm.PmPaymentHistoryPage.REFUND_TYPE_FULL;
import static com.paylease.app.qa.framework.pages.pm.PmPaymentHistoryPage.REFUND_TYPE_PARTIAL_MAX;
import static com.paylease.app.qa.framework.pages.pm.PmPaymentHistoryPage.REFUND_TYPE_PARTIAL_OVER_LIMIT;
import static com.paylease.app.qa.framework.pages.pm.PmPaymentHistoryPage.REFUND_TYPE_PARTIAL_PARTIAL;
import static com.paylease.app.qa.framework.pages.pm.PmPaymentHistoryPage.REFUND_TYPE_PARTIAL_SINGLE_FIELD;

import com.paylease.app.qa.framework.AppConstant;
import com.paylease.app.qa.framework.DataBaseConnector;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.UtilityManager;
import com.paylease.app.qa.framework.components.Login;
import com.paylease.app.qa.framework.components.Login.UserType;
import com.paylease.app.qa.framework.pages.PageBase;
import com.paylease.app.qa.framework.pages.admin.TransactionDetailPage;
import com.paylease.app.qa.framework.pages.admin.TransactionRefundPage;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.paymentflow.ReceiptPage;
import com.paylease.app.qa.framework.pages.pm.PmLoginPage;
import com.paylease.app.qa.framework.pages.pm.PmLogoutBar;
import com.paylease.app.qa.framework.pages.pm.PmPaymentHistoryPage;
import com.paylease.app.qa.framework.pages.resident.ResPaymentHistoryPage;
import com.paylease.app.qa.framework.utility.database.client.dao.BatchItemDao;
import com.paylease.app.qa.framework.utility.database.client.dao.ExternalBatchesDao;
import com.paylease.app.qa.framework.utility.database.client.dao.ExternalTransactionsDao;
import com.paylease.app.qa.framework.utility.database.client.dao.IntegrationLoggingDao;
import com.paylease.app.qa.framework.utility.database.client.dao.TransactionDao;
import com.paylease.app.qa.framework.utility.database.client.dao.TransactionsManagedDepositsV2Dao;
import com.paylease.app.qa.framework.utility.database.client.dto.BatchItem;
import com.paylease.app.qa.framework.utility.database.client.dto.ExternalBatchesDto;
import com.paylease.app.qa.framework.utility.database.client.dto.ExternalTransactionsDto;
import com.paylease.app.qa.framework.utility.database.client.dto.IntegrationLoggingDto;
import com.paylease.app.qa.framework.utility.database.client.dto.Transaction;
import com.paylease.app.qa.framework.utility.database.client.dto.TransactionsManagedDepositsV2;
import com.paylease.app.qa.framework.utility.envwriter.EnvWriterUtil;
import com.paylease.app.qa.framework.utility.kafka.KafkaConsumerClient;
import com.paylease.app.qa.framework.utility.sshtool.SshUtil;
import com.paylease.app.qa.testbase.DbBase;
import com.paylease.app.qa.testbase.KafkaEventBase;
import com.paylease.app.qa.testbase.PaymentBase;
import com.paylease.app.qa.testbase.dataproviders.ManagedDepositsOneTimePaymentDataProvider;
import com.paylease.app.qa.testbase.dataproviders.PaymentProcessingDataProvider;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;


public class OneTimePaymentTest extends ScriptBase {

  private static final String REGION_YAVO = "Integration";
  private static final String FEATURE_YAVO = "ManagedDepositsForYavoIntegration";
  private static final int VOID_DEPOSIT_ID = -999;
  private static final int VOID_FILE_ID = -999;

  //--------------------------------ONE TIME PAYMENT FLOW TESTS-------------------------------------

  @Test(dataProvider = "otpData", dataProviderClass = ManagedDepositsOneTimePaymentDataProvider
      .class, groups = {"manual"})
  public void transactionCreatedEventTest(String testVariationNo, String testCase,
      String paymentType, boolean useResidentList, boolean expressPay,
      boolean isManagedDepositsV2On) throws Exception {
    Logger.info("PM otp, where test variation: " + testVariationNo + " with " + paymentType
            + " where resident list page being used is " + useResidentList
            + " and using express pay is " + expressPay + "and has new world set to "
            + isManagedDepositsV2On
    );

    TestSetupPage testSetupPage;

    //Change kafka topic so the test runs continuously, but not parallel
    EnvWriterUtil envWriterUtil = new EnvWriterUtil();

    String oldContents = "";

    testSetupPage = new TestSetupPage(REGION_YAVO, FEATURE_YAVO, testCase);
    oldContents = envWriterUtil.replaceEnvFileValue("kafka.env", "KAFKA_TOPIC",
        "'managed_deposits_" + testVariationNo + "_" + UtilityManager
            .getCurrentDate("yyyyMMddHHmmss") + "'");

    testSetupPage.open();
    final String pmEmail = testSetupPage.getString("pmEmail");
    final String residentId = testSetupPage.getString("residentId");
    final String topicName = testSetupPage.getString("kafkaTopic");
    final String groupId = testSetupPage.getString("kafkaGroupId");

    PmLoginPage pmLoginPage = new PmLoginPage();

    pmLoginPage.open();
    pmLoginPage.login(pmEmail);

    String transId = pmOtPaymentActions(residentId, paymentType, useResidentList, expressPay);
    Logger.info("Transaction ID: " + transId);

    //Start the Consumer
    KafkaConsumerClient kafkaConsumerClient = new KafkaConsumerClient(AppConstant.KAFKA_BROKER_URL,
        AppConstant.USERNAME, AppConstant.PASSWORD, topicName, groupId);

    kafkaConsumerClient.start();

    DataBaseConnector dataBaseConnector = new DataBaseConnector();

    dataBaseConnector.createConnection();
    Connection connection = dataBaseConnector.getConnection();

    SshUtil sshUtil = new SshUtil();
    sshUtil.runIpnWorker();

    TransactionsManagedDepositsV2Dao transactionsManagedDepositsV2Dao = new TransactionsManagedDepositsV2Dao();

    ArrayList<TransactionsManagedDepositsV2> managedDepositsV2ArrayList = transactionsManagedDepositsV2Dao
        .findByTransId(connection, transId, 1);

    //Assert transaction that is created is inserted into transactions_managed_deposits_v2
    if (isManagedDepositsV2On) {
      Assert.assertTrue(managedDepositsV2ArrayList.size() == 1,
          "Did not find the transaction in the transactions_managed_deposits_v2 table. Test case variation: "
              + testVariationNo);

    } else {
      Assert.assertTrue(managedDepositsV2ArrayList.size() == 0,
          "Found transaction " + transId
              + "exists in transactions_managed_deposits_v2. table Test case variation: "
              + testVariationNo);
    }

    KafkaEventBase kafkaEventBase = new KafkaEventBase(kafkaConsumerClient);

    String eventTriggered = kafkaEventBase.getEventNameFromMessageOnTopic(transId,
        kafkaConsumerClient, isManagedDepositsV2On);

    if (isManagedDepositsV2On) {
      Assert.assertEquals(eventTriggered, "TRANSACTION_CREATED",
          "The event that published this message is not transaction created event. Test case variation: "
              + testVariationNo);
    } else {
      Assert.assertNull(eventTriggered,
          "The transaction was published to the topic. Test case variation: " + testVariationNo);
    }
    //Replace contents of kafka.env
    envWriterUtil.replaceEnvFileValue("kafka.env", null, oldContents);

    dataBaseConnector.closeConnection();
  }

  @Test(dataProvider = "refundTransDataPm", dataProviderClass = ManagedDepositsOneTimePaymentDataProvider.class,
      groups = {"e2e"})
  public void refundTransactionPm(String testVariationNo, String testCaseSetup,
      String refundType, String paymentType, boolean isManagedDepositsV2On) {
    Logger.info("Pm, where test variation no and testcase: " + testVariationNo + "," + testCaseSetup
        + ". Verify refund transaction when "
        + " and isusingPaymenttype: " + paymentType
        + " and refundType: " + refundType
        + " and testCaseSetup: " + testCaseSetup
        + " and has Managed Deposits V2: " + isManagedDepositsV2On);

    TestSetupPage testSetupPage;

    EnvWriterUtil envWriterUtil = new EnvWriterUtil();
    String oldContents = "";

    testSetupPage = new TestSetupPage(REGION_YAVO, FEATURE_YAVO, testCaseSetup);
    //Change kafka topic so the test runs continuously, but not parallel
    oldContents = envWriterUtil.replaceEnvFileValue("kafka.env", "KAFKA_TOPIC",
        "'managed_deposits_" + testVariationNo + "_" + UtilityManager
            .getCurrentDate("yyyyMMddHHmmss") + "'");

    testSetupPage.open();

    ArrayList<String> paymentFieldList = new ArrayList<>();

    //Assemble the list of payment fields and get the size of the list.
    paymentFieldList.add(testSetupPage.getString("paymentField1Label"));
    paymentFieldList.add(testSetupPage.getString("paymentField2Label"));
    int numberOfPaymentFields = paymentFieldList.size();

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

    //Make the transaction paid out
    DbBase dbBase = new DbBase();

    ArrayList<Transaction> payoutTransactionsList = dbBase
        .makeTransactionPaidOut(transactionId, connection);

    //Make the transaction refundable
    String batchFileSentDate = dbBase.makeTransactionRefundable(payoutTransactionsList, connection);

    //Login as the pm and click the refund link on the transaction history page
    PmLoginPage pmLoginPage = new PmLoginPage();

    pmLoginPage.open();
    pmLoginPage.login(pmEmail);

    PmPaymentHistoryPage pmPaymentHistoryPage = new PmPaymentHistoryPage();
    pmPaymentHistoryPage.open(true);

    KafkaConsumerClient kafkaConsumerClient = new KafkaConsumerClient(AppConstant.KAFKA_BROKER_URL,
        AppConstant.USERNAME, AppConstant.PASSWORD, topicName, groupId);

    //Start the Consumer to listen to the refund triggered event.
    kafkaConsumerClient.start();

    pmPaymentHistoryPage.clickRefundLink(transactionId);

    //Select the refund type
    //Prepare data for refund, will use for filling out inputs (in the case of partial refunds)
    // and for assertions at the end of the test
    HashMap<Transaction, String> refundsToPayoutsList = new HashMap<>();
    ArrayList<HashMap<String, String>> refundsDetailsList;
    ArrayList<Transaction> refundedPayoutsList = new ArrayList<>();

    if (refundType.equals(REFUND_TYPE_FULL)) {
      pmPaymentHistoryPage.clickFullRefundOption();

      for (Transaction payoutTransaction : payoutTransactionsList) {
        refundsToPayoutsList
            .put(payoutTransaction, String.valueOf(payoutTransaction.getUnitAmount()));
      }
      //assemble list of refunds to bank accounts and payment fields
      refundsDetailsList = dbBase.createRefundDetails(refundsToPayoutsList, connection);

      refundedPayoutsList = payoutTransactionsList;
    } else {
      pmPaymentHistoryPage.clickPartialRefundOption();
      //If partial refund, amount will depend on whether it is partial, max or over the limit
      switch (refundType) {

        case REFUND_TYPE_PARTIAL_PARTIAL:
          for (Transaction payoutTransaction : payoutTransactionsList) {
            refundsToPayoutsList.put(payoutTransaction, "1.00");
          }
          refundedPayoutsList = payoutTransactionsList;
          break;

        case REFUND_TYPE_PARTIAL_MAX:
          for (Transaction payoutTransaction : payoutTransactionsList) {
            refundsToPayoutsList
                .put(payoutTransaction, String.valueOf(payoutTransaction.getUnitAmount()));
            refundedPayoutsList = payoutTransactionsList;
          }
          break;

        case REFUND_TYPE_PARTIAL_SINGLE_FIELD:
          refundsToPayoutsList.put(payoutTransactionsList.get(0), "1.00");
          refundedPayoutsList.add(payoutTransactionsList.get(0));
          break;

        case REFUND_TYPE_PARTIAL_OVER_LIMIT:
          for (Transaction payoutTransaction : payoutTransactionsList) {
            String overLimitAmount = String
                .format("%2f", Float.parseFloat(payoutTransaction.getUnitAmount()) + 1.00);
            refundsToPayoutsList.put(payoutTransaction, overLimitAmount);
          }
          break;
      }
      //assemble list of refunds to bank accounts and payment fields
      refundsDetailsList = dbBase.createRefundDetails(refundsToPayoutsList, connection);

      //enter the amounts on the page (not needed for full refund)
      pmPaymentHistoryPage.enterPartialRefundAmount(refundsDetailsList);
    }

    //Click 'Initiate'
    pmPaymentHistoryPage.clickInitiateRefund();

    if (refundType.equals(REFUND_TYPE_PARTIAL_OVER_LIMIT)) {
      //If refund amount is over the payment amount, expect error message and refund did not
      // proceed
      ArrayList<String> errorMessages = pmPaymentHistoryPage.getpartialRefundInputErrorMessages();
      Assert.assertEquals(errorMessages.size(), payoutTransactionsList.size());
      for (HashMap map : refundsDetailsList) {
        Assert.assertTrue(errorMessages
            .contains("The amount should not be more than " + map.get("paymentAmount")));
      }
      //Cancel the refund after verifying the error message
      pmPaymentHistoryPage.clickCancelInitiateRefund();
    }

    //If cancelContinueRefund = false then click 'Continue'
    pmPaymentHistoryPage.clickContinueRefund();

    dbBase.verifyTransactionStatus(transactionId, payoutTransactionsList, "Refunded",
        batchFileSentDate, refundsToPayoutsList);

    PmLogoutBar pmLogoutBar = new PmLogoutBar();
    pmLogoutBar.clickLogoutButton();

    ArrayList<Transaction> childTransactionsList = getAllTransactionsForParentId(connection,
        transactionId);

    String eventTriggered = checkMessageForTransactionCreatedEvent(transactionId,
        kafkaConsumerClient, isManagedDepositsV2On, childTransactionsList);

    if (isManagedDepositsV2On) {
      Assert.assertEquals(eventTriggered, "TRANSACTION_CREATED",
          "The event triggered is not TRANSACTION_CREATED. Test case variation: "
              + testVariationNo);
    } else {
      Assert.assertNull(eventTriggered,
          "The transaction was published to the topic.. Test case variation: " + testVariationNo);
    }

    //Replace contents of kafka.env
    envWriterUtil.replaceEnvFileValue("kafka.env", null, oldContents);

    TransactionsManagedDepositsV2Dao transactionsManagedDepositsV2Dao = new TransactionsManagedDepositsV2Dao();

    ArrayList<TransactionsManagedDepositsV2> managedDepositsV2ArrayList = transactionsManagedDepositsV2Dao
        .findByTransId(connection, transactionId, 1);

    //Assert transaction that is created is inserted into transactions_managed_deposits_v2
    if (isManagedDepositsV2On) {
      Assert.assertTrue(managedDepositsV2ArrayList.size() == 1,
          "Did not find the transaction in the transactions_managed_deposits_v2 table. Test case variation: "
              + testVariationNo);

    } else {
      Assert.assertTrue(managedDepositsV2ArrayList.size() == 0,
          "Found transaction " + transactionId
              + "exists in transactions_managed_deposits_v2 table. Test case variation: "
              + testVariationNo);
    }

    int countOfRefunds = 0;
    countOfRefunds = getRefundAndReversalCount(Long.parseLong(transactionId), connection, 8);
    ArrayList<String> refundDescription = getDescriptionOfRefundAndReversedTransaction(
        Long.parseLong(transactionId), connection, 8);
    for (String refund : refundDescription) {
      Assert.assertTrue(refund.contains("refund"),
          "The description does not specify that this is a refund. Test case variation: "
              + testVariationNo);
    }

    int countOfReversals;
    ArrayList<String> reversalDescription;

    if (paymentType.equals("NewBank")) {
      countOfReversals = getRefundAndReversalCount(Long.parseLong(transactionId), connection, 5);
      reversalDescription = getDescriptionOfRefundAndReversedTransaction(
          Long.parseLong(transactionId), connection, 5);

    } else {
      countOfReversals = getRefundAndReversalCount(Long.parseLong(transactionId), connection, 15);
      reversalDescription = getDescriptionOfRefundAndReversedTransaction(
          Long.parseLong(transactionId), connection, 15);
    }

    switch (refundType) {
      case REFUND_TYPE_FULL:
      case REFUND_TYPE_PARTIAL_PARTIAL:
      case REFUND_TYPE_PARTIAL_MAX:

        if (paymentType.equals("NewBank")) {
          Assert.assertEquals(countOfRefunds, 1,
              "There are more than 1 refunds created for this parent transaction Id. Test case variation: "
                  + testVariationNo);
        } else {
          Assert.assertEquals(countOfRefunds, 0,
              "There are more than 1 refunds created for this parent transaction Id. Test case variation: "
                  + testVariationNo);
        }

        Assert.assertEquals(countOfReversals, numberOfPaymentFields,
            "All batch items were not reversed. Test case variation: " + testVariationNo);
        for (String reversal : reversalDescription
        ) {
          Assert.assertTrue(reversal.contains("reversal"),
              "The description does not specify that this is a refund. Test case variation: "
                  + testVariationNo);
        }
        break;

      case REFUND_TYPE_PARTIAL_SINGLE_FIELD:

        if (paymentType.equals("NewBank")) {
          Assert.assertEquals(countOfRefunds, 1,
              "There are more than 1 refunds created for this parent transaction Id. Test case variation: "
                  + testVariationNo);
        } else {
          Assert.assertEquals(countOfRefunds, 0,
              "There are more than 1 refunds created for this parent transaction Id. Test case variation: "
                  + testVariationNo);
        }

        Assert.assertEquals(countOfReversals, 1,
            "All batch items were not reversed. Test case variation: " + testVariationNo);
        for (String reversal : reversalDescription
        ) {
          Assert.assertTrue(reversal.contains("reversal"),
              "The description does not specify that this is a refund. Test case variation: "
                  + testVariationNo);
        }
        break;

    }

    kafkaConsumerClient.finish();
    dataBaseConnector.closeConnection();
  }

  @Test(dataProvider = "refundTransDataAdmin", dataProviderClass =
      ManagedDepositsOneTimePaymentDataProvider.class, groups = {"e2e"})
  public void refundBatchItemAdmin(String testVariationNo, String testCaseSetup,
      String refundType, String paymentType, boolean isManagedDepositsV2On, String processor) {
    Logger.info("Admin, where test variation no and testcase: " + testVariationNo + "," + testCaseSetup
            + ". Verify refund transaction when "
            + " and refundType: " + refundType
            + " and paymentType: " + paymentType
            + " and testCaseSetup: " + testCaseSetup
            + " and isManagedDepositsV2On: " + isManagedDepositsV2On
    );

    DataBaseConnector dataBaseConnector = new DataBaseConnector();

    dataBaseConnector.createConnection();
    Connection connection = dataBaseConnector.getConnection();

    TestSetupPage testSetupPage;
    EnvWriterUtil envWriterUtil = new EnvWriterUtil();
    String oldContents = "";

    //Change kafka topic so the test runs continuously, but not parallel
    oldContents = envWriterUtil.replaceEnvFileValue("kafka.env", "KAFKA_TOPIC",
        "'managed_deposits_" + testVariationNo + "_" + UtilityManager
            .getCurrentDate("yyyyMMddHHmmss") + "'");
    testSetupPage = new TestSetupPage(REGION_YAVO, FEATURE_YAVO, testCaseSetup);

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

    //Start the Consumer to listen to the refund triggered event.
    KafkaConsumerClient kafkaConsumerClient = new KafkaConsumerClient(AppConstant.KAFKA_BROKER_URL,
        AppConstant.USERNAME, AppConstant.PASSWORD, topicName, groupId);

    kafkaConsumerClient.start();

    //login to admin UI and refund the transaction.
    Login login = new Login();
    login.logInAdmin();

    TransactionDetailPage transactionDetailPage = new TransactionDetailPage(transactionId);

    transactionDetailPage.open();

    int expectedRefundsCount = 0;

    expectedRefundsCount = dbBase.triggerRefundsAndGetExpectedRefundsCount(refundType,
        transactionDetailPage, expectedRefundsCount, processor);

    ArrayList<Transaction> refundTransactions = getRefundTransactions(connection, transactionId);

    //assert refund appears in database
    if (paymentType.equals("NewBank")) {
      Assert.assertEquals(refundTransactions.size(), 1,
          "Expecting " + expectedRefundsCount + " refunds but found " + refundTransactions.size()
              + ". Test case variation: " + testVariationNo);
    } else {
      Assert.assertEquals(refundTransactions.size(), 0,
          "Expecting " + expectedRefundsCount + " refunds but found " + refundTransactions.size()
              + ". Test case variation: " + testVariationNo);
    }

    //Assert the refunds we got from the database appear in the Admin UI
    if (processor.equals("FNBO")) {
      for (Transaction refundTransaction : refundTransactions) {
        Assert.assertTrue(transactionDetailPage
                .isRefundTransactionPresent(String.valueOf(refundTransaction.getTransactionId()),
                    processor),
            "Refund transaction not found. Test case variation: " + testVariationNo);
      }

    }
    login.logOutAdmin();

    ArrayList<Transaction> childTransactionsList = getAllTransactionsForParentId(connection,
        transactionId);

    String eventTriggered = checkMessageForTransactionCreatedEvent(transactionId,
        kafkaConsumerClient, isManagedDepositsV2On, childTransactionsList);

    if (isManagedDepositsV2On) {
      Assert.assertEquals(eventTriggered, "TRANSACTION_CREATED",
          "The event triggered is not TRANSACTION_CREATED. Test case variation: "
              + testVariationNo);
    } else {
      Assert.assertNull(eventTriggered,
          "The transaction was published to the topic. Test case variation: " + testVariationNo);
    }

    TransactionsManagedDepositsV2Dao transactionsManagedDepositsV2Dao = new TransactionsManagedDepositsV2Dao();
    ArrayList<TransactionsManagedDepositsV2> managedDepositsV2ArrayList = transactionsManagedDepositsV2Dao
        .findByTransId(connection, transactionId, 1);

    //Assert transaction that is created is inserted into transactions_managed_deposits_v2
    if (isManagedDepositsV2On) {
      Assert.assertTrue(managedDepositsV2ArrayList.size() == 1,
          "Did not find the transaction in the transactions_managed_deposits_v2 table. Test case variation: "
              + testVariationNo);

    } else {
      Assert.assertTrue(managedDepositsV2ArrayList.size() == 0,
          "Found transaction " + transactionId
              + "exists in transactions_managed_deposits_v2 table. Test case variation: "
              + testVariationNo);
      //Replace contents of kafka.env
      envWriterUtil.replaceEnvFileValue("kafka.env", null, oldContents);
    }

    kafkaConsumerClient.finish();
    dataBaseConnector.closeConnection();
  }

  @Test(dataProvider = "reversalTransDataAdmin", dataProviderClass =
      ManagedDepositsOneTimePaymentDataProvider.class, groups = {"manual"})
  public void reverseBatchItemAdmin(
      String testVariationNo,
      String testCaseSetup,
      String reversalType,
      String paymentType,
      boolean isManagedDepositsV2On,
      String processor,
      boolean runConsumer
  ) {
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

    //Change kafka topic so the test runs continuously, but not parallel
    EnvWriterUtil envWriterUtil = new EnvWriterUtil();

    String oldContents = "";

    testSetupPage = new TestSetupPage(REGION_YAVO, FEATURE_YAVO, testCaseSetup);
    oldContents = envWriterUtil.replaceEnvFileValue("kafka.env", "KAFKA_TOPIC",
        "'managed_deposits_" + testVariationNo + "_" + UtilityManager
            .getCurrentDate("yyyyMMddHHmmss") + "'");

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
              .size() + ". Test case variation: " + testVariationNo);
    } else {
      Assert.assertEquals(reversalTransactions.size(), 1,
          "Expecting " + expectedReversalCount + " reversals but found " + reversalTransactions
              .size() + ". Test case variation: " + testVariationNo);
    }

    login.logOutAdmin();
    ArrayList<Transaction> childTransactionsList = getAllTransactionsForParentId(connection, transactionId);
    if (runConsumer) {
      String eventTriggered = verifyEventTriggered(topicName, groupId, transactionId, isManagedDepositsV2On, childTransactionsList);
      Assert.assertEquals(
          eventTriggered,
          "TRANSACTION_CREATED",
          "The event triggered is not TRANSACTION_CREATED. Test case variation: " + testVariationNo
      );
    } else {
      // run br_pt and make sure no batch_items get created for the reversal transactions
      SshUtil sshUtil = new SshUtil();
      sshUtil.sshCommand(new String[]{PaymentProcessingDataProvider.BR_PT});

      for (Transaction childTransaction : childTransactionsList) {
        // Only verify for reversals
        if (!String.valueOf(childTransaction.getTypeOfTransaction()).equals("5")) {
          continue;
        }

        if (isManagedDepositsV2On) {
          dbBase.checkBatchItemsAndTransactionsDbTableForNewWorldChanges(
              dbBase,
              connection,
              String.valueOf(childTransaction.getTransactionId()),
              childTransaction.getStatus(),
              childTransaction.getTypeOfTransaction()
          );
        } else {
          Double total = Double.valueOf(childTransaction.getTotalAmount());
          ArrayList<Double> amounts = new ArrayList<>();
          amounts.add(total);
          dbBase.checkBatchItemsDbTable(
              connection,
              String.valueOf(childTransaction.getTransactionId()),
              1,
              amounts
          );
        }
      }
    }
    //Replace contents of kafka.env
    envWriterUtil.replaceEnvFileValue("kafka.env", null, oldContents);

    TransactionsManagedDepositsV2Dao transactionsManagedDepositsV2Dao = new TransactionsManagedDepositsV2Dao();

    ArrayList<TransactionsManagedDepositsV2> managedDepositsV2ArrayList = transactionsManagedDepositsV2Dao
        .findByTransId(connection, transactionId, 1);

    //Assert transaction that is created is inserted into transactions_managed_deposits_v2
    if (isManagedDepositsV2On) {
      Assert.assertTrue(managedDepositsV2ArrayList.size() == 1,
          "Did not find the transaction in the transactions_managed_deposits_v2 table. Test case variation: "
              + testVariationNo);

    } else {
      Assert.assertTrue(managedDepositsV2ArrayList.size() == 0,
          "Found transaction " + transactionId
              + "exists in transactions_managed_deposits_v2 table. Test case variation: "
              + testVariationNo);
    }

    dataBaseConnector.closeConnection();
  }

  private String verifyEventTriggered(String topicName,
      String groupId,
      String transactionId,
      boolean isManagedDepositsV2On,
      ArrayList<Transaction> childTransactionsList
  ) {
    KafkaConsumerClient kafkaConsumerClient = new KafkaConsumerClient(
        AppConstant.KAFKA_BROKER_URL,
        AppConstant.USERNAME,
        AppConstant.PASSWORD,
        topicName,
        groupId
    );
    //Start the Consumer to listen to the refund triggered event.
    kafkaConsumerClient.start();

    String eventTriggered = checkMessageForTransactionCreatedEvent(
        transactionId,
        kafkaConsumerClient,
        isManagedDepositsV2On,
        childTransactionsList
    );

    kafkaConsumerClient.finish();

    return eventTriggered;
  }

  @Test(dataProvider = "voidTransactionWebApp", dataProviderClass = ManagedDepositsOneTimePaymentDataProvider.class,
      groups = {"manual"})
  public void voidTransaction(String testVariationNo, String testCaseSetup, String voidLocation,
      String paymentType, String processor, boolean newworld)
      throws Exception {
    Logger.info(
        "Admin, where test variation no and testcase: " + testVariationNo + "," + testCaseSetup
            + ". Verify refund transaction when "
            + " and paymentType: " + paymentType
            + " and testCaseSetup: " + testCaseSetup);

    TestSetupPage testSetupPage;

    testSetupPage = new TestSetupPage(REGION_YAVO, FEATURE_YAVO, testCaseSetup);
    testSetupPage.open();

    ArrayList<String> paymentFieldList = new ArrayList<>();

    //Assemble the list of payment fields
    paymentFieldList.add(testSetupPage.getString("paymentField1Label"));
    paymentFieldList.add(testSetupPage.getString("paymentField2Label"));

    //Get test data strings
    String residentEmail = testSetupPage.getString("residentEmail");
    String pmEmail = testSetupPage.getString("pmEmail");
    final String topicName = testSetupPage.getString("kafkaTopic");
    final String groupId = testSetupPage.getString("kafkaGroupId");

    //Start the Consumer to listen to the refund triggered event.
    KafkaConsumerClient kafkaConsumerClient = new KafkaConsumerClient(AppConstant.KAFKA_BROKER_URL,
        AppConstant.USERNAME, AppConstant.PASSWORD, topicName, groupId);

    kafkaConsumerClient.start();

    DataBaseConnector dataBaseConnector = new DataBaseConnector();

    dataBaseConnector.createConnection();
    Connection connection = dataBaseConnector.getConnection();

    //Create the transaction
    Transaction transaction = createTransaction(residentEmail, paymentFieldList, paymentType,
        connection);
    String transactionId = String.valueOf(transaction.getTransactionId());
    int intTransactionId = Integer.parseInt(transactionId);

    DbBase dbBase = new DbBase();

    SshUtil sshUtil = new SshUtil();

    sshUtil.runIpnWorker();

    KafkaEventBase kafkaEventBase = new KafkaEventBase(kafkaConsumerClient);

    String eventTriggered = kafkaEventBase.getEventNameFromMessageOnTopic(transactionId,
        kafkaConsumerClient, true);

    if (newworld) {
      Assert.assertEquals(eventTriggered, "TRANSACTION_CREATED",
          "The event that published this message is not transaction created event");
    }

    //Trigger void for the transaction.
    Login login = new Login();

    switch (voidLocation) {
      case "pm":
        login.logInUser(pmEmail, UserType.PM);
        voidPm(transactionId);
        break;
      case "res":
        login.logInUser(residentEmail, UserType.RESIDENT);
        voidRes(transactionId);
        break;
      case "admin":
        login.logInAdmin();
        voidAdmin(transactionId, paymentType, processor);
        break;
      default:
        break;
    }

    //Get the payouts for FNBO ACH and CC
    List<Integer> transactionIds = new ArrayList<>();
    ArrayList<Transaction> transactionIdForPayouts = getAllTransactionsForParentId(connection,
        transactionId);
    for (Transaction transId : transactionIdForPayouts) {
      int payoutTransId = (int) transId.getTransactionId();
      transactionIds.add(payoutTransId);
    }

    //get the batch items for the transaction id
    List<Integer> procIds = new ArrayList<>();
    if (processor.equals("Profitstars")) {
      procIds = dbBase.getProcIdsFromBatchItemsTable(connection, transactionId);
    } else if (processor.equals("FNBO") && paymentType.equals(NEW_CREDIT)) {
      for (int trns : transactionIds) {
        int procId = dbBase.getProcIdFromBatchItemsTable(connection, String.valueOf(trns));
        procIds.add(procId);
      }
    } else {
      transactionIds.add(intTransactionId);
      for (int trns : transactionIds) {
        int procId = dbBase.getProcIdFromBatchItemsTable(connection, String.valueOf(trns));
        procIds.add(procId);
      }
    }

    //validate that BATCH_ITEM_VOIDED event is triggered for the transaction when voided.
    if (newworld) {
      for (int procId : procIds) {
        Assert.assertTrue(
            checkMessageForDepositVoidedEvent(String.valueOf(procId), kafkaConsumerClient));
        kafkaEventBase.waitForEvent(KafkaEventBase.DEPOSIT_ITEM_VOIDED, String.valueOf(procId));
        long fileId = dbBase.getFileIdsFromBatchItemsTable(connection, procId);
        long depositId = dbBase.getDepositIdFromBatchItemsTable(connection, procId);
        Assert.assertEquals(fileId, VOID_FILE_ID, "The file_id is not -999");
        Assert.assertEquals(depositId, VOID_DEPOSIT_ID, "The deposit_id is not -999");
      }
    } else {
      Assert.assertFalse(
          checkMessageForDepositVoidedEvent(String.valueOf(transactionId), kafkaConsumerClient));
    }

    //validate that the void link is disabled for the transaction if already voided.
    switch (voidLocation) {
      case "pm":
        login.logInUser(pmEmail, UserType.PM);
        PmPaymentHistoryPage pmPaymentHistoryPage = new PmPaymentHistoryPage();

        Assert.assertFalse(pmPaymentHistoryPage.hasVoidLink(transactionId));
        break;
      case "res":
        login.logInUser(residentEmail, UserType.RESIDENT);
        ResPaymentHistoryPage resPaymentHistoryPage = new ResPaymentHistoryPage();

        Assert.assertFalse(resPaymentHistoryPage.hasVoidLink(transactionId));
        break;
      case "admin":
        login.logInAdmin();
        TransactionDetailPage transactionDetailPage = new TransactionDetailPage(transactionId);

        Assert.assertFalse(transactionDetailPage.hasVoidLink());
        break;
      default:
        break;
    }

    //validate that when the transaction is voided, all payment fields are voided with the integration.
    if (newworld) {
      IntegrationLoggingDao integrationLoggingDao = new IntegrationLoggingDao();

      String method = "ImportResidentTransactions_Login";
      int countOfProcIdsVoided = integrationLoggingDao
          .getCountOfMethod(connection, Long.parseLong(transactionId), 50, method);

      Assert.assertEquals(countOfProcIdsVoided, paymentFieldList.size(),
          "All the batch items are not voided");

      //Validate that all voids are sent as reversals and are voided for the complete amount of the batch item.
      ArrayList<IntegrationLoggingDto> requestList = integrationLoggingDao
          .findByTransIdAndMethod(connection, Long.parseLong(transactionId), 50, method);

      for (IntegrationLoggingDto request : requestList) {
        String requestRaw = request.getRequestRaw();
        Assert.assertTrue(requestRaw.contains("Reverse"));
        int procId = request.getProcId();
        String unitAmount = String
            .valueOf(dbBase.getAmountFromBatchItemsTable(procId, connection));
        Assert.assertTrue(requestRaw.contains(unitAmount), "The complete amount was not voided");
      }
    }

    kafkaConsumerClient.finish();
    dataBaseConnector.closeConnection();
  }

  @Test(dataProvider = "voidTransactionAfterBatchClosed", dataProviderClass =
      ManagedDepositsOneTimePaymentDataProvider.class, groups = {"manual"})
  public void voidTransactionAfterBatchClosed(String testVariationNo, String testSetup,
      String processor, String paymentType, boolean multipleTransactions) throws Exception {
    Logger.info(
        "Verify that we run through the void flow again for voided transactions after the batch closes for " + testVariationNo);

    TestSetupPage testSetupPage = new TestSetupPage(REGION_YAVO, FEATURE_YAVO,
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

    DataBaseConnector dataBaseConnector = new DataBaseConnector();

    dataBaseConnector.createConnection();
    Connection connection = dataBaseConnector.getConnection();

    //Trigger a OTP for the resident
    PaymentBase paymentBase = new PaymentBase();
    String transIdString = paymentBase
        .residentOtPaymentActions(residentEmail, paymentType, paymentFieldList);
    long transactionId = Long.parseLong(transIdString);

    //Start the Consumer
    KafkaConsumerClient kafkaConsumerClient = new KafkaConsumerClient(AppConstant.KAFKA_BROKER_URL,
        AppConstant.USERNAME, AppConstant.PASSWORD, topicName, groupId);
    kafkaConsumerClient.start();

    DbBase dbBase = new DbBase();

    SshUtil sshUtil = new SshUtil();
    sshUtil.runIpnWorker();

    KafkaEventBase kafkaEventBase = new KafkaEventBase(kafkaConsumerClient);

    kafkaEventBase.waitForEventToProcess(KafkaEventBase.ALL_DEPOSIT_ITEMS_CREATED, transIdString);

    //Get all the transactions for the parent transaction Id(FNBO) and add it to the transactionlist
    List<Long> transactionIdsList = new ArrayList<>();
    transactionIdsList = dbBase.getTransactionIdList(processor, connection, transactionId, dbBase,
        transactionIdsList, false);

    for (long expectedTransactionId : transactionIdsList) {
      kafkaEventBase.waitForEvent(KafkaEventBase.ALL_DEPOSIT_ITEMS_CREATED,
          String.valueOf(expectedTransactionId));
    }

    //Create a new transaction that will get into the deposit, to verify the batch if there are transactions that did not get voided
    if (multipleTransactions) {
      String transIdString2 = paymentBase
          .residentOtPaymentActions(residentEmail, paymentType, paymentFieldList);
      long transactionId2 = Long.parseLong(transIdString2);

      sshUtil.runIpnWorker();

      kafkaEventBase.waitForEventToProcess(KafkaEventBase.ALL_DEPOSIT_ITEMS_CREATED, transIdString2);

      List<Long> transactionIdsList2 = new ArrayList<>();
      transactionIdsList2 = dbBase.getTransactionIdList(processor, connection, transactionId2, dbBase,
          transactionIdsList2, false);

      for (long expectedTransactionId : transactionIdsList2) {
        kafkaEventBase.waitForEvent(KafkaEventBase.ALL_DEPOSIT_ITEMS_CREATED,
            String.valueOf(expectedTransactionId));
      }
    }

    //Get deposit Ids for all the transactions recieved from the parent trans Id
    List<Long> depositIds = dbBase.getDepositIdList(connection, transactionIdsList);

    //Get all the deposit items for the transactions in the transactionIdsList
    List<BatchItem> batchItems = dbBase.getBatchItems(transactionIdsList, connection);
    Assert.assertTrue(batchItems.size() > 0);

    //Trigger void for the transaction
    Login login = new Login();
    login.logInAdmin();

    voidAdmin(transIdString, paymentType, processor);

    for (BatchItem batchItem : batchItems) {
      kafkaEventBase
          .waitForEventToProcess("BATCH_ITEM_VOIDED", String.valueOf(batchItem.getProcId()));
      kafkaEventBase
          .waitForEventToProcess("DEPOSIT_ITEM_VOIDED", String.valueOf(batchItem.getProcId()));
    }

    ExternalTransactionsDao externalTransactionsDao = new ExternalTransactionsDao();
    ArrayList<ExternalTransactionsDto> externalTransList;
    externalTransList = externalTransactionsDao
        .findByTransId(connection, Integer.parseInt(transIdString), 5);

    for (ExternalTransactionsDto externalTransactionsDto : externalTransList) {
      Assert.assertEquals(externalTransactionsDto.getStatus(), "VOIDED_ERROR",
          "Did not see status VOIDED_ERROR external transaction " + externalTransactionsDto
              .getId());
    }

    //Get deposit Ids for all the transactions recieved from the parent trans Id
    for (long expectedTransactionId : transactionIdsList) {
      kafkaEventBase.waitForEvent(KafkaEventBase.ALL_DEPOSIT_ITEMS_CREATED,
          String.valueOf(expectedTransactionId));
    }

    //stop the existing consumer and create a new consumer to verify DEPOSIT_ITEM_VOIDED is published again for all deposit items
    kafkaConsumerClient.finish();

    KafkaConsumerClient kafkaConsumerClient2 = new KafkaConsumerClient(AppConstant.KAFKA_BROKER_URL,
        AppConstant.USERNAME, AppConstant.PASSWORD, topicName, groupId);
    kafkaConsumerClient2.start();
    KafkaEventBase kafkaEventBase2 = new KafkaEventBase(kafkaConsumerClient2);

    for (long depositId : depositIds) {
      Logger.trace("Deposit ID: " + depositId);
      closeDepositOnLocal(String.valueOf(depositId));
      sshUtil.runIpnWorker();
      kafkaEventBase2.waitForEventToProcess("DEPOSIT_CLOSED", String.valueOf(depositId));
      ExternalBatchesDao externalBatchesDao = new ExternalBatchesDao();
      ExternalBatchesDto externalBatch = externalBatchesDao
          .findByDepositId(connection, depositId, 1).get(0);
      if (multipleTransactions) {
        kafkaEventBase2.waitForEventToProcess("EXTERNAL_BATCH_CLOSED",
            String.valueOf(externalBatch.getId()));
        Assert.assertEquals(externalBatch.getStatus(), "closed");

        for (BatchItem batchItem : batchItems) {
          Logger.trace("Bank account id: " + batchItem.getToBankAcctId());
          if (!batchItem.getToBankAcctId().equals("0")) {
            kafkaEventBase2.waitForEventToProcess("DEPOSIT_ITEM_VOIDED",
                String.valueOf(batchItem.getProcId()));
          }
        }
      } else {
        kafkaEventBase2.waitForEventToProcess("EXTERNAL_BATCH_DELETED", String.valueOf(externalBatch.getId()));
        Assert.assertEquals(externalBatch.getStatus(), "deleted");
      }
    }

    ArrayList<ExternalTransactionsDto> externalTransListAfterClose;
    externalTransListAfterClose = externalTransactionsDao
        .findByTransId(connection, Integer.parseInt(transIdString), 5);

    for (ExternalTransactionsDto externalTransactionsDto : externalTransListAfterClose) {
      Assert.assertEquals(externalTransactionsDto.getStatus(), ("VOIDED"),
          "Did not find status VOIDED for external transaction " + externalTransactionsDto.getId());
    }

    kafkaConsumerClient2.finish();
    dataBaseConnector.closeConnection();
  }

  /**
   * Test to check if NSF transactions are flowing through the Managed Deposits v2 system if the
   * parent transaction is new world.
   *
   * Note: When running this test on the dev database, edit the query (line 82) on
   * br_process_nsf_fees.php so that t.transaction_date is more recent (ex. 1 week in the past),
   * to avoid having too many results in the query and prevent memory issues
   *
   * @param testVariationNo
   * @param testCase
   * @param paymentType
   * @param useResidentList
   * @param expressPay
   * @param processor
   * @param newWorld
   * @throws Exception
   */
  @Test(dataProvider = "dataInsufficientFunds",
      dataProviderClass = ManagedDepositsOneTimePaymentDataProvider.class, groups = {"manual"})
  public void insufficientFundsTransaction(String testVariationNo, String testCase,
      String paymentType, boolean useResidentList, boolean expressPay, String processor,
      boolean newWorld) throws Exception {
    Logger.info("PM otp, where test variation: " + testVariationNo + " with " + paymentType
        + " where resident list page being used is " + useResidentList
        + " and using express pay is " + expressPay + "and has new world set to "
    );

    TestSetupPage testSetupPage;

    testSetupPage = new TestSetupPage(REGION_YAVO, FEATURE_YAVO, testCase);
    testSetupPage.open();

    final String pmEmail = testSetupPage.getString("pmEmail");
    final String residentId = testSetupPage.getString("residentId");
    final String topicName = testSetupPage.getString("kafkaTopic");
    final String groupId = testSetupPage.getString("kafkaGroupId");

    ArrayList<String> paymentFieldList = new ArrayList<>();
    paymentFieldList.add(testSetupPage.getString("paymentField1Label"));
    paymentFieldList.add(testSetupPage.getString("paymentField2Label"));
    paymentFieldList.add(testSetupPage.getString("paymentField3Label"));
    Collections.sort(paymentFieldList);

    PmLoginPage pmLoginPage = new PmLoginPage();

    pmLoginPage.open();
    pmLoginPage.login(pmEmail);

    PaymentBase paymentBase = new PaymentBase();

    String transId = paymentBase.pmOtPaymentActions(paymentType, useResidentList, expressPay,
        residentId, paymentFieldList);

    Logger.info("Transaction ID: " + transId);

    //Start the Consumer
    KafkaConsumerClient kafkaConsumerClient = new KafkaConsumerClient(AppConstant.KAFKA_BROKER_URL,
        AppConstant.USERNAME, AppConstant.PASSWORD, topicName, groupId);

    kafkaConsumerClient.start();

    DataBaseConnector dataBaseConnector = new DataBaseConnector();

    dataBaseConnector.createConnection();
    Connection connection = dataBaseConnector.getConnection();

    SshUtil sshUtil = new SshUtil();
    sshUtil.runIpnWorker();

    if (!newWorld) {
      if (processor.equals("Profitstars")) {
        sshUtil.runBatchScript("br_pt_profit_stars");
      } else {
        sshUtil.runBatchScript("br_pt");
      }
    } else {
      KafkaEventBase kafkaEventBase = new KafkaEventBase(kafkaConsumerClient);
      kafkaEventBase.waitForEventToProcess(KafkaEventBase.ALL_DEPOSIT_ITEMS_CREATED, transId);
    }

    TransactionDao transactionDao = new TransactionDao();
    //Change transaction status to 5
    ArrayList<Transaction> transactionsList = transactionDao
        .findById(connection, Long.parseLong(transId), 1);
    Logger.info("transactionsList size: " + transactionsList.size());
    Transaction transaction = transactionsList.get(0);
    Logger.info("Transaction type: " + transaction.getTypeOfTransaction());
    transaction.setStatus("5");

    transactionDao.update(connection, transaction);

    BatchItemDao batchItemDao = new BatchItemDao();
    ArrayList<BatchItem> batchItemsList;

    //Change batch_items return_code to R01
    batchItemsList = batchItemDao.findByTransId(connection, Long.parseLong(transId), 5);
    skipTestIfBrScriptIsRunning();
    if (processor.equals("Profitstars")) {
      //assert there is a batch item for each payment field and for the parent transaction
      Assert.assertEquals(batchItemsList.size(), paymentFieldList.size() + 1, "Expecting "
          + (paymentFieldList.size() + 1) + " batch items but found " + batchItemsList.size());
    } else {
      Assert.assertEquals(batchItemsList.size(), 1,
          "Expecting 1 batch item but found" + batchItemsList.size());
    }

    for (BatchItem batchItem : batchItemsList) {
      batchItem.setReturnCode("R01");
      batchItemDao.updateReturnCode(connection, batchItem);
    }

    //Run batch script to process nsf transactions
    sshUtil.runBatchScript("br_process_nsf_fees");

    //Assert that a new transaction got created with transaction type of 9 for NSF
    ArrayList<Transaction> childTransList = transactionDao
        .findByParentId(connection, Long.parseLong(transId), 5);
    Transaction nsfTransaction = new Transaction();

    for (Transaction childTrans : childTransList) {
      if (childTrans.getTypeOfTransaction() == 9) {
        nsfTransaction = childTrans;
      }
    }

    Assert.assertNotNull(nsfTransaction);

    Logger.info("NSF Transaction: " + nsfTransaction.getTransactionId());

    sshUtil.runIpnWorker();
    KafkaEventBase kafkaEventBase = new KafkaEventBase(kafkaConsumerClient);

    if (newWorld) {
      kafkaEventBase.waitForEventToProcess("ALL_DEPOSIT_ITEMS_CREATED",
          String.valueOf(nsfTransaction.getTransactionId()));
    } else {
      String eventTriggered = kafkaEventBase
          .getEventNameFromMessageOnTopic(String.valueOf(nsfTransaction.getTransactionId()),
              kafkaConsumerClient, false);
       Assert.assertNull(eventTriggered, "The transaction was published to the topic. Test case variation: " + testVariationNo);
    }

    ArrayList<BatchItem> nsfBatchItem = batchItemDao
        .findByTransId(connection, nsfTransaction.getTransactionId(), 5);

    //Assert one batch item got created for the NSF transaction if new world
    if(newWorld) {
      Assert.assertEquals(nsfBatchItem.size(), 1,
          "Expecting 1 batch item created but found: " + nsfBatchItem.size());

      Assert.assertEquals(nsfBatchItem.get(0).getToAcctNum(), "");
      Assert.assertEquals(nsfBatchItem.get(0).getToRoutingNum(), "");
      Assert.assertEquals(nsfBatchItem.get(0).getToBankAcctId(), "0");
      Assert.assertEquals(nsfBatchItem.get(0).getAchProcessor(), 1);
      Assert.assertEquals(String.valueOf(nsfBatchItem.get(0).getDepositId()), "0");
    }

    kafkaConsumerClient.finish();
    dataBaseConnector.closeConnection();
  }
  //--------------------------------TEST METHOD-----------------------------------------------------

  /**
   * Perform PM otp actions.
   *
   * @param residentId resident Id
   * @param paymentType payment type
   * @param useResidentList boolean use resident list or not
   * @param expressPay express pay
   */
  private String pmOtPaymentActions(String residentId, String paymentType, boolean useResidentList,
      boolean expressPay) {

    PaymentBase paymentBase = new PaymentBase();

    if (useResidentList) {
      paymentBase.selectResidentFromResidentListAndBeginPayment(residentId, SCHEDULE_ONE_TIME);
    } else {
      paymentBase.selectResidentFromPaymentsDropDownAndBeginPayment(residentId, SCHEDULE_ONE_TIME);
    }

    paymentBase.fillAndSubmitPaymentAmount(false);
    paymentBase.selectPaymentMethod(paymentType, expressPay);
    paymentBase.reviewAndSubmit();

    ReceiptPage receiptPage = new ReceiptPage();

    Assert.assertTrue(receiptPage.pageIsLoaded(), "Should be on Receipt page");

    Assert.assertEquals((receiptPage.getTransactionStatus()), "Processing",
        "The transaction did not process successfully");
    String transactionId = receiptPage.getTransactionId();

    PmLogoutBar pmLogoutBar = new PmLogoutBar();

    pmLogoutBar.clickLogoutButton();

    return transactionId;
  }

  private String checkMessageForTransactionCreatedEvent(String transId,
      KafkaConsumerClient kafkaConsumerClient, boolean newWorld,
      ArrayList<Transaction> childTrans) {

    String eventName = "";
    String tId;
    String typeOfTransaction;
    new JSONObject();
    JSONObject obj;

    for (int i = 0; i < 20; i++) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      ArrayList<String> consumerMessages = kafkaConsumerClient.getConsumerMessageList();
      HashMap<String, String> transIdAndType = new HashMap<>();
      HashMap<String, String> messageTransIdTypeMap = new HashMap<>();

      DataBaseConnector dataBaseConnector = new DataBaseConnector();
      dataBaseConnector.createConnection();

      for (Transaction child : childTrans) {
        transIdAndType.put(Long.toString(child.getTransactionId()),
            Integer.toString(child.getTypeOfTransaction()));
      }

      if (newWorld) {
        for (String message : consumerMessages) {
          JSONParser parser = new JSONParser();
          try {
            obj = ((JSONObject) parser.parse(message));
            JSONObject context = (JSONObject) obj.get("context");
            tId = context.get("transaction_id").toString();
            typeOfTransaction = context.get("type_of_transaction").toString();
            messageTransIdTypeMap.put(tId, typeOfTransaction);
            eventName = obj.get("eventName").toString();
            Assert.assertEquals(eventName, "TRANSACTION_CREATED",
                "The event is not transaction created event");
          } catch (ParseException e) {
            e.printStackTrace();
          }

        }
        if (messageTransIdTypeMap.equals(transIdAndType)) {
          Assert.assertEquals(messageTransIdTypeMap.values(), transIdAndType.values(),
              "the type of transaction is not the same");
        }
      } else {
        if (consumerMessages.size() >= 0) {
          JSONParser parser = new JSONParser();
          if (consumerMessages.size() == 0) {
            eventName = null;
            break;
          } else {
            try {
              for (String message1 : consumerMessages) {
                obj = ((JSONObject) parser.parse(message1));
                JSONObject context = (JSONObject) obj.get("context");
                tId = context.get("transaction_id").toString();
                Assert.assertNotEquals(transId, tId, "the transaction exists on the topic");
                eventName = null;
                break;
              }
            } catch (ParseException e) {
              e.printStackTrace();
            }
          }

        }
      }
    }
    return eventName;
  }

  private boolean checkMessageForDepositVoidedEvent(String procId,
      KafkaConsumerClient kafkaConsumerClient) {

    String eventName;
    String tId;
    new JSONObject();
    JSONObject obj;

    for (int i = 0; i < 10; i++) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      ArrayList<String> consumerMessages = kafkaConsumerClient.getConsumerMessageList();

      for (String message : consumerMessages) {
        JSONParser parser = new JSONParser();
        try {
          obj = ((JSONObject) parser.parse(message));
          JSONObject context = (JSONObject) obj.get("context");
          if (context.containsKey("proc_id")) {
            tId = context.get("proc_id").toString();
            if (tId.equals(procId)) {
              eventName = obj.get("eventName").toString();
              if (eventName.equals("BATCH_ITEM_VOIDED")) {
                return true;
              }
            }
          }
        } catch (ParseException e) {
          e.printStackTrace();
        }
      }
    }
    return false;
  }

  private Transaction createTransaction(String residentEmail,
      ArrayList<String> paymentFieldList, String paymentType, Connection connection) {
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
    }
    return transaction;
  }

  private int getRefundAndReversalCount(long transId, Connection connection,
      int typeOfTransaction) {
    TransactionDao transactionDao = new TransactionDao();
    int noOfRefundsOrReversals = 0;

    noOfRefundsOrReversals = transactionDao
        .findCountOfRefundsandReversals(connection, transId, typeOfTransaction);

    return noOfRefundsOrReversals;
  }

  private ArrayList<String> getDescriptionOfRefundAndReversedTransaction(long transId,
      Connection connection, int typeOfTransaction) {
    TransactionDao transactionDao = new TransactionDao();
    ArrayList<String> description = new ArrayList<>();
    ArrayList<Transaction> transactionList = transactionDao.findByParentId(connection, transId, 10);
    for (Transaction childTransaction : transactionList) {
      int typeOfTrans = childTransaction.getTypeOfTransaction();
      if (typeOfTrans == typeOfTransaction) {
        description.add(childTransaction.getDescription());
      }
    }
    return description;
  }

  private ArrayList<Transaction> getRefundTransactions(Connection conn,
      String parentTransactionId) {
    Logger.info("Parent Transaction ID: " + parentTransactionId);
    TransactionDao transactionDao = new TransactionDao();
    ArrayList<Transaction> transactionsList;

    transactionsList = transactionDao.findByParentId(conn, Long.parseLong(parentTransactionId), 10);

    ArrayList<Transaction> refundTransactionsList = new ArrayList<>();
    for (Transaction transaction : transactionsList) {
      if (8 == transaction.getTypeOfTransaction()) {
        refundTransactionsList.add(transaction);
      }
    }
    return refundTransactionsList;
  }

  private ArrayList<Transaction> getAllTransactionsForParentId(Connection conn,
      String parentTransactionId) {
    Logger.info("Parent Transaction ID: " + parentTransactionId);
    TransactionDao transactionDao = new TransactionDao();
    ArrayList<Transaction> allTransactionsList;

    allTransactionsList = transactionDao
        .findByParentId(conn, Long.parseLong(parentTransactionId), 10);

    ArrayList<Transaction> allTransactionsForParentIdList = new ArrayList<>(allTransactionsList);
    return allTransactionsForParentIdList;
  }

  private void voidPm(String transId) {
    PmPaymentHistoryPage pmPaymentHistoryPage = new PmPaymentHistoryPage();
    pmPaymentHistoryPage.open(true);
    if (pmPaymentHistoryPage.hasVoidLink(transId)) {
      pmPaymentHistoryPage.clickVoidLink(transId);
      pmPaymentHistoryPage.acceptVoidAlert();
    }
  }

  private void voidRes(String transId) {
    ResPaymentHistoryPage resPaymentHistoryPage = new ResPaymentHistoryPage();
    resPaymentHistoryPage.open();
    if (resPaymentHistoryPage.hasVoidLink(transId)) {
      resPaymentHistoryPage.clickVoidLink(transId);
      resPaymentHistoryPage.acceptVoidAlert();
    }
  }

  private void voidAdmin(String transId, String paymentType, String processor) {
    TransactionDetailPage transactionDetailPage = new TransactionDetailPage(transId);
    PageBase pageBase = new PageBase();
    transactionDetailPage.open();
    if (NEW_BANK.equals(paymentType) && processor.equals("FNBO")) {
      transactionDetailPage.clickVoidLink();
      transactionDetailPage.acceptVoid();
    } else if (NEW_BANK.equals(paymentType) && processor.equals("Profitstars")) {
      transactionDetailPage.clickVoidLinkProfitstars();
      pageBase.acceptVoidAlert();
    } else {
      TransactionRefundPage transactionRefundPage = transactionDetailPage.clickVoidRefund();
      transactionRefundPage.submitVoid();
    }
  }
}
