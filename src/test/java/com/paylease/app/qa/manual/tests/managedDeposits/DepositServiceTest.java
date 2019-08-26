package com.paylease.app.qa.manual.tests.managedDeposits;

import static com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage.NEW_BANK;
import static com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage.NEW_CREDIT;

import com.paylease.app.qa.framework.AppConstant;
import com.paylease.app.qa.framework.DataBaseConnector;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.UtilityManager;
import com.paylease.app.qa.framework.pages.automatedhelper.ReturnTransactionPage;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.pm.PmLoginPage;
import com.paylease.app.qa.framework.utility.database.client.dao.BankAccountDao;
import com.paylease.app.qa.framework.utility.database.client.dao.BatchItemDao;
import com.paylease.app.qa.framework.utility.database.client.dao.DepositDao;
import com.paylease.app.qa.framework.utility.database.client.dao.TransactionDao;
import com.paylease.app.qa.framework.utility.database.client.dto.BankAccount;
import com.paylease.app.qa.framework.utility.database.client.dto.BatchItem;
import com.paylease.app.qa.framework.utility.database.client.dto.Deposit;
import com.paylease.app.qa.framework.utility.database.client.dto.Transaction;
import com.paylease.app.qa.framework.utility.kafka.KafkaConsumerClient;
import com.paylease.app.qa.framework.utility.sshtool.SshUtil;
import com.paylease.app.qa.testbase.DbBase;
import com.paylease.app.qa.testbase.KafkaEventBase;
import com.paylease.app.qa.testbase.PaymentBase;
import com.paylease.app.qa.testbase.dataproviders.ManagedDepositsOneTimePaymentDataProvider;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DepositServiceTest extends ScriptBase {

  private static final String REGION = "Integration";
  private static final String FEATURE = "ManagedDepositsForYavoIntegration";
  public static final String EXPECTED_DEPOSIT_DATE_PROFITSTARS = "2050-01-04";
  public static final String EXPECTED_DEPOSIT_DATE_FNBO = "2050-01-06";
  public static final String EXPECTED_PROCESSING_DATE = "2050-01-03";
  public static final String TRANSACTION_DATE = "2050-01-03 12:00:00";
  public static final String PROFITSTARS_LOCATION_ID = "profitstars_locationid";
  public static final String ACCOUNT_ID = "account_id";
  public static final int ACH_PROCESSOR_FNBO = 1;
  private ArrayList<String> paymentFieldList = new ArrayList<>();

  //--------------------------------DEPOSIT SERVICE FLOW TESTS-------------------------------------

  @Test(dataProvider = "otpDataDepositService", dataProviderClass =
      ManagedDepositsOneTimePaymentDataProvider.class, groups = {"manual"})
  public void createDepositTest(String testVariationNo, String testSetup, String paymentType,
      boolean useResidentList, boolean expressPay, String processor, boolean multipleBankAccounts,
      boolean batchByBankAccount, boolean creditCardExpressPayout, boolean missingProfitStarsLid)
      throws Exception {
    Logger.info(
        "PM otp, where test variation: " + testVariationNo + " with " + paymentType
            + " where resident list page being used is " + useResidentList
            + " and using express pay is " + expressPay
            + " and is setup with" + batchByBankAccount + "for batch by bank account");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testSetup);
    testSetupPage.open();
    final String pmEmail = testSetupPage.getString("pmEmail");
    final String pmId = testSetupPage.getString("pmId");
    final String residentId = testSetupPage.getString("residentId");
    final String topicName = testSetupPage.getString("kafkaTopic");
    final String groupId = testSetupPage.getString("kafkaGroupId");
    String residentTwoId = null;

    paymentFieldList.add(testSetupPage.getString("paymentField1Label"));
    paymentFieldList.add(testSetupPage.getString("paymentField2Label"));
    paymentFieldList.add(testSetupPage.getString("paymentField3Label"));
    Collections.sort(paymentFieldList);

    if(batchByBankAccount){
      residentTwoId = testSetupPage.getString("residentTwoId");
    }

    PmLoginPage pmLoginPage = new PmLoginPage();

    pmLoginPage.open();
    pmLoginPage.login(pmEmail);

    PaymentBase paymentBase = new PaymentBase();

    String expectedDepositDate;

    if (expressPay || processor.equals("Profitstars")||creditCardExpressPayout||missingProfitStarsLid) {
      expectedDepositDate = EXPECTED_DEPOSIT_DATE_PROFITSTARS;
    } else {
      expectedDepositDate = EXPECTED_DEPOSIT_DATE_FNBO;
    }

    //Start the Consumer
    KafkaConsumerClient kafkaConsumerClient = new KafkaConsumerClient(AppConstant.KAFKA_BROKER_URL,
        AppConstant.USERNAME, AppConstant.PASSWORD, topicName, groupId);

    kafkaConsumerClient.start();

    SshUtil sshUtil = new SshUtil();
    DbBase dbBase = new DbBase();

    DataBaseConnector dataBaseConnector = new DataBaseConnector();

    dataBaseConnector.createConnection();
    Connection connection = dataBaseConnector.getConnection();

    String transIdString = paymentBase.pmOtPaymentActions(paymentType, useResidentList, expressPay,
        residentId, paymentFieldList);
    long transactionId = Long.parseLong(transIdString);

    Logger.info(transIdString);

    BankAccountDao bankAccountDao = new BankAccountDao();

    if(missingProfitStarsLid){
      ArrayList<BankAccount> bankAccounts = bankAccountDao.findByUserId(connection, String.valueOf(pmId),5);
      BankAccount firstEntry = bankAccounts.get(0);
      bankAccountDao.updateFieldValue(connection, PROFITSTARS_LOCATION_ID, null, ACCOUNT_ID, firstEntry.getAccountId());

      }

    getParentTransactionAndRunIpn(sshUtil, connection, transactionId);

    KafkaEventBase kafkaEventBase = new KafkaEventBase(kafkaConsumerClient);

    kafkaEventBase.waitForEvent(KafkaEventBase.ALL_DEPOSIT_ITEMS_CREATED,
        String.valueOf(transactionId));
    kafkaEventBase.waitForEventToProcess(KafkaEventBase.ALL_DEPOSIT_ITEMS_CREATED,
        String.valueOf(transactionId));

    String processSameDayOnPayload = kafkaEventBase.getContextFromPayload(KafkaEventBase.ALL_DEPOSIT_ITEMS_CREATED,"processSameDay");
    boolean processSameDayOnPayloadboolean = Boolean.parseBoolean(processSameDayOnPayload);
    if (expressPay || processor.equals("Profitstars") || creditCardExpressPayout||missingProfitStarsLid) {
      Assert.assertTrue(processSameDayOnPayloadboolean,
          "The transaction is not getting processed on  the same day");
    } else {
      Assert.assertFalse(processSameDayOnPayloadboolean,
          "The transaction is getting processed on the same day when it should be regular 2 day processing");
    }

    //Get all the transactions for the parent transaction Id
    List<Long> transactionIdsList = new ArrayList<>();
    transactionIdsList = dbBase.getTransactionIdList(processor, connection, transactionId, dbBase,
        transactionIdsList, missingProfitStarsLid);

    for (long expectedTransactionId : transactionIdsList) {
      kafkaEventBase.waitForEvent(KafkaEventBase.ALL_DEPOSIT_ITEMS_CREATED,
          String.valueOf(expectedTransactionId));
    }

    //Get deposit Ids for all the transactions recieved from the parent trans Id
    List<Long> depositIds = dbBase.getDepositIdList(connection,transactionIdsList);

    //Verify that the batch items are added to different deposits.
    if (multipleBankAccounts) {

      long firstDepositIdInDepositIds = depositIds.get(0);
      long secondDepositIdInDepositIds = depositIds.get(1);
      long thirdDepositIdInDepositIds = depositIds.get(2);

      Assert.assertNotEquals(firstDepositIdInDepositIds, secondDepositIdInDepositIds,
          "The transactions are added to the same deposit even though they are going to different bank accounts");
      Assert.assertNotEquals(secondDepositIdInDepositIds, thirdDepositIdInDepositIds,
          "The transactions are added to the same deposit even though they are going to different bank accounts");
      Assert.assertNotEquals(thirdDepositIdInDepositIds, firstDepositIdInDepositIds,
          "The transactions are added to the same deposit even though they are going to different bank accounts");
    }

    //Check that the parent transaction has a batch item associated if it is a Profitstars transaction.
    int numPayoutTransaction = checkPayoutTransaction(transactionId, connection);

    switch (processor) {
      case ("Profitstars"):

        Assert.assertFalse(depositIds.isEmpty(),
            "There is no Batch Item created for the transaction");
        for (long depositId : depositIds) {
          Assert.assertTrue(dbBase.checkDepositsTableForStatus(depositId, connection, "open"));
        }
        Assert.assertEquals(numPayoutTransaction, 0,
            "There are payouts created for the ProfitStars parent transaction");
        break;

      case ("FNBO"):
        Assert.assertFalse(depositIds.isEmpty(),
            "There is no Batch Item created for the transaction");
        for (long depositId : depositIds) {
          Assert.assertTrue(dbBase.checkDepositsTableForStatus(depositId, connection, "open"));
          Assert.assertEquals(numPayoutTransaction, paymentFieldList.size(),
              "The number of payouts created is not equal to the number of Payment Fields");
        }
        break;

      case ("FNBOCC"):
        Assert.assertFalse(depositIds.isEmpty(),
            "There is a batch item created for the Pay in transaction");
        Assert.assertEquals(numPayoutTransaction, paymentFieldList.size(),
            "The number of payouts created is not equal to the number of Payment Fields");
        break;
    }

    for (long depositId : depositIds) {
      Deposit deposit = dbBase.getDeposit(depositId, connection);
      Assert.assertEquals(expectedDepositDate, deposit.getDepositDate().toString(),
          "The deposit date is not what was expected");
      if(expressPay||creditCardExpressPayout) {
        Assert.assertEquals(deposit.getProcessingTime().toString(), EXPECTED_PROCESSING_DATE,
            "The transaction is not being processed on the same day for express pay transactions");
      }
      //validate that when PM has batch by bank account, the prop_id for the deposit is 0.
      if (batchByBankAccount) {
        Assert.assertEquals(deposit.getPropId(), 0,
            "The propId for the deposit is not 0 when the PM is set to batch by bank account");
      }
    }

    if(paymentType.equals(NEW_CREDIT)){
      String today = UtilityManager.getCurrentDate("yyyy-MM-dd");

      for(Long payoutTransId: transactionIdsList){
        Transaction payoutTransaction = getTransaction(payoutTransId, connection);
        //Set transaction date to today, time has to be the earliest so that it will get processed
        setTransactionDate(payoutTransaction, today + " 00:00:00", connection);
      }

      //DEV-42804 run batch script to move cc transaction status to settled
      sshUtil.runBatchScript("br_process_cc");

      //Assert that the status of the transaction is updated to 3 in case of CC
      Assert.assertEquals(checkTransactionsTable(transactionId, connection), "3",
          "The transaction status is not 3");
    } else {
      //Assert that the status of the transaction is updated to 2 in case of ACH
      Assert.assertEquals(checkTransactionsTable(transactionId, connection), "2",
          "The transaction status is not 2");
    }

    pmLoginPage.open();
    pmLoginPage.login(pmEmail);

    //Validate that a new deposit is not created when there is an existing deposit open for the bank account.
    String secondTransId = paymentBase.pmOtPaymentActions(paymentType, useResidentList, expressPay,
        residentId, paymentFieldList);

    long transId2 = Long.parseLong(secondTransId);

    getParentTransactionAndRunIpn(sshUtil,connection,transId2);

    kafkaEventBase.waitForEventToProcess(KafkaEventBase.ALL_DEPOSIT_ITEMS_CREATED,
        String.valueOf(transId2));

    //Get all the transactions for the parent transaction Id
    List<Long> transactionIdsList2 = new ArrayList<>();
    transactionIdsList2 = dbBase.getTransactionIdList(processor, connection, transId2, dbBase,
        transactionIdsList2,missingProfitStarsLid );

    for (long expectedTransactionId : transactionIdsList2) {
      kafkaEventBase.waitForEvent(KafkaEventBase.ALL_DEPOSIT_ITEMS_CREATED,
          String.valueOf(expectedTransactionId));
    }

    //Get deposit Ids for all the transactions recieved from the parent trans Id
    List<Long> depositIds2 = dbBase.getDepositIdList(connection,transactionIdsList2);


    //Assert that payments going to the same bank account are added to the same deposit.
    Assert
        .assertTrue(depositIds2.equals(depositIds),
            " The deposit ID's for both the transaction is not same even though they are going to the same bank account "
                + depositIds.toString() + " and deposit 2 is " + depositIds2.toString());

   //validate that when batch by bank account is enabled, all transactions for different properties are added to the same deposit if they are going to the same bank account.
    if(batchByBankAccount){

      pmLoginPage.open();
      pmLoginPage.login(pmEmail);
      String thirdTransId = paymentBase.pmOtPaymentActions(paymentType, useResidentList, expressPay,
          residentTwoId, paymentFieldList);

      long transId3 = Long.parseLong(thirdTransId);

      getParentTransactionAndRunIpn(sshUtil,connection,transId3);

      kafkaEventBase.waitForEventToProcess(KafkaEventBase.ALL_DEPOSIT_ITEMS_CREATED,
          String.valueOf(transId3));

      List<Long> transactionIdsList3 = new ArrayList<>();
      transactionIdsList3 = dbBase.getTransactionIdList(processor, connection, transId3, dbBase,
          transactionIdsList3, missingProfitStarsLid);

      for (long expectedTransactionId : transactionIdsList3) {
        kafkaEventBase.waitForEvent(KafkaEventBase.ALL_DEPOSIT_ITEMS_CREATED,
            String.valueOf(expectedTransactionId));
      }

      //Get deposit Ids for all the transactions recieved from the parent trans Id
      List<Long> depositIds3 = dbBase.getDepositIdList(connection,transactionIdsList3);

      Assert.assertTrue(depositIds3.equals(depositIds), " All the transactions going to the same bank account but different property are not added to the same deposit");

    }

    if(missingProfitStarsLid){
      List<BatchItem> list = dbBase.getBatchItems(transactionIdsList,connection);
      for (BatchItem batchItem:list) {
        Assert.assertEquals(batchItem.getAchProcessor(), ACH_PROCESSOR_FNBO,"The transaction is not being processed with FNBO");
      }
    }

    kafkaConsumerClient.finish();
    dataBaseConnector.closeConnection();
  }

  @Test(dataProvider = "otpDataDepositServiceMixedPaymentTypes", dataProviderClass = ManagedDepositsOneTimePaymentDataProvider.class, groups =
      {"manual"})
  public void createDepositTestMixedPaymentTypes(String testVariationNo, String testSetup,
      boolean useResidentList,
      boolean expressPay, String processor, boolean multipleBankAccounts)
      throws Exception {
    Logger.info(
        "PM otp, where test variation: " + testVariationNo + " with mixed payment types"
            + " where resident list page being used is " + useResidentList
            + " and using express pay is " + expressPay);

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testSetup);
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

    String transIdStringAch = paymentBase.pmOtPaymentActions(NEW_BANK, useResidentList, expressPay, residentId,
        paymentFieldList);
    long transactionIdAch = Long.parseLong(transIdStringAch);

    Logger.info("Transaction ID :" + transIdStringAch);

    pmLoginPage.open();
    pmLoginPage.login(pmEmail);

    String transIdStringCc = paymentBase.pmOtPaymentActions(NEW_CREDIT, useResidentList, expressPay, residentId,
        paymentFieldList);
    long transactionIdCc = Long.parseLong(transIdStringCc);

    Logger.info("Transaction ID :" + transIdStringCc);

    DataBaseConnector dataBaseConnector = new DataBaseConnector();
    dataBaseConnector.createConnection();
    Connection connection = dataBaseConnector.getConnection();

    Transaction parentTransactionAch = getTransaction(transactionIdAch, connection);

    String transactionDate = "2050-01-03 12:00:00"; // Monday with no holidays on following days
    setTransactionDate(parentTransactionAch, transactionDate, connection);
    DbBase dbBase = new DbBase();

    Transaction parentTransactionCc = getTransaction(transactionIdCc, connection);

    setTransactionDate(parentTransactionCc, transactionDate, connection);

    String expectedDepositDate;
    if (expressPay || processor.equals("Profitstars")) {
      expectedDepositDate = "2050-01-04";
    } else {
      expectedDepositDate = "2050-01-06";
    }

    //Start the Consumer
    KafkaConsumerClient kafkaConsumerClient = new KafkaConsumerClient(AppConstant.KAFKA_BROKER_URL,
        AppConstant.USERNAME,
        AppConstant.PASSWORD, topicName, groupId);
    kafkaConsumerClient.start();

    SshUtil sshUtil = new SshUtil();
    sshUtil.runIpnWorker();

    KafkaEventBase kafkaEventBase = new KafkaEventBase(kafkaConsumerClient);

    //Wait for all deposit items created for ACH
    kafkaEventBase.waitForEventToProcess(KafkaEventBase.ALL_DEPOSIT_ITEMS_CREATED,
        transIdStringAch);
    //Wait for all deposit items created for CC
    kafkaEventBase.waitForEventToProcess(KafkaEventBase.ALL_DEPOSIT_ITEMS_CREATED,
        transIdStringCc);

    //Get payout transactions for ACH
    List<Long> transactionIdsListAch = dbBase
        .getTransactionListForParentTransId(transactionIdAch, connection);
    List<Long> depositIdsAch;

    //Get deposit ID's for ACH
    if (processor.equals("Profitstars")) {
      depositIdsAch = getDepositIdsFromBatchItemsTable(transactionIdAch, connection);
    } else {
      for (long expectedTransactionId : transactionIdsListAch) {
        kafkaEventBase.waitForEventToProcess(KafkaEventBase.ALL_DEPOSIT_ITEMS_CREATED,
            String.valueOf(expectedTransactionId));
      }
      depositIdsAch = getDepositIdsFromBatchItemsTable(transactionIdsListAch, connection);
    }

    if (multipleBankAccounts) {
      long firstDepositIdInDepositIdsAch = depositIdsAch.get(0);
      long secondDepositIdInDepositIdsAch = depositIdsAch.get(1);
      long thirdDepositIdInDepositIdsAch = depositIdsAch.get(2);
      Assert.assertNotEquals(firstDepositIdInDepositIdsAch, secondDepositIdInDepositIdsAch,
          "The transactions are added to the same deposit even though they are going to different bank accounts");
      Assert.assertNotEquals(secondDepositIdInDepositIdsAch, thirdDepositIdInDepositIdsAch,
          "The transactions are added to the same deposit even though they are going to different bank accounts");
      Assert.assertNotEquals(thirdDepositIdInDepositIdsAch, firstDepositIdInDepositIdsAch,
          "The transactions are added to the same deposit even though they are going to different bank accounts");
    }

    //Get deposit ID's for CC
    List<Long> transactionIdsListCc = dbBase
        .getTransactionListForParentTransId(transactionIdCc, connection);
    List<Long> depositIdsCc;


      for (long expectedTransactionId : transactionIdsListCc) {
        kafkaEventBase.waitForEventToProcess(KafkaEventBase.ALL_DEPOSIT_ITEMS_CREATED,
            String.valueOf(expectedTransactionId));
      }
      depositIdsCc = getDepositIdsFromBatchItemsTable(transactionIdsListCc, connection);


    if (multipleBankAccounts) {
      long firstDepositIdInDepositIdsCc = depositIdsCc.get(0);
      long secondDepositIdInDepositIdsCc = depositIdsCc.get(1);
      long thirdDepositIdInDepositIdsCc = depositIdsCc.get(2);
      Assert.assertNotEquals(firstDepositIdInDepositIdsCc, secondDepositIdInDepositIdsCc,
          "The transactions are added to the same deposit even though they are going to different bank accounts");
      Assert.assertNotEquals(secondDepositIdInDepositIdsCc, thirdDepositIdInDepositIdsCc,
          "The transactions are added to the same deposit even though they are going to different bank accounts");
      Assert.assertNotEquals(thirdDepositIdInDepositIdsCc, firstDepositIdInDepositIdsCc,
          "The transactions are added to the same deposit even though they are going to different bank accounts");

    }

    DepositDao depositDao = new DepositDao();
    //Assert that deposits have payment type of 1 for ACH
    for(long depositId : depositIdsAch) {
      Deposit deposit;
      deposit = depositDao.findById(connection, depositId);

      Assert.assertEquals(deposit.getPaymentType(), "1", "Payment type is not 1.");
    }

    //Assert that deposits have payment type of 2 for CC

    for(long depositId : depositIdsCc) {
      Deposit deposit;
      deposit = depositDao.findById(connection, depositId);

      Assert.assertEquals(deposit.getPaymentType(), "2", "Payment type is not 2.");
    }

    //Assert that there are no common deposit id's between the ach transaction and cc transaction
    Assert.assertTrue(Collections.disjoint(depositIdsAch, depositIdsCc));

    //Check that the parent transaction has a batch item associated if it is a Profitstars transaction.
    int numPayoutTransaction = checkPayoutTransaction(transactionIdAch, connection);
    switch (processor) {
      case ("Profitstars"):

        Assert.assertFalse(depositIdsAch.isEmpty(),
            "There is no Batch Item created for the transaction");
        for (long depositId : depositIdsAch) {
          Assert.assertTrue(dbBase.checkDepositsTableForStatus(depositId, connection, "open"));
        }
        Assert.assertEquals(numPayoutTransaction, 0,
            "There are payouts created for the ProfitStars parent transaction");
        break;

      case ("FNBO"):
        Assert.assertFalse(depositIdsAch.isEmpty(),
            "There is no Batch Item created for the transaction");
        for (long depositId : depositIdsAch) {
          Assert.assertTrue(dbBase.checkDepositsTableForStatus(depositId, connection, "open"));
          Assert.assertEquals(numPayoutTransaction, paymentFieldList.size(),
              "The number of payouts created is not equal to the number of Payment Fields");
        }
        break;

      case ("FNBOCC"):
        Assert.assertFalse(depositIdsAch.isEmpty(),
            "There is a batch item created for the Pay in transaction");
        Assert.assertEquals(numPayoutTransaction, paymentFieldList.size(),
            "The number of payouts created is not equal to the number of Payment Fields");
        break;
    }

    for (long depositId : depositIdsAch) {
      Deposit deposit = dbBase.getDeposit(depositId, connection);
      Assert.assertEquals(expectedDepositDate, deposit.getDepositDate().toString(),
          "The deposit date is not what was expected");
    }

    //Assert that the status of the transaction is updated to 2 in case of ACH and CC
    Assert
        .assertEquals(checkTransactionsTable(transactionIdAch, connection),
            "2", "The transaction status is not 2");


    pmLoginPage.open();
    pmLoginPage.login(pmEmail);

    //Validate that a new deposit is not created when there is an existing deposit open for the bank account.
    String secondTransIdStringAch = paymentBase.pmOtPaymentActions(NEW_BANK, useResidentList, expressPay, residentId,
        paymentFieldList);
    long transId2Ach = Long.parseLong(secondTransIdStringAch);
    Transaction parentTransaction2 = getTransaction(transId2Ach, connection);

    String transactionDate2 = "2050-01-03 12:30:00"; // Monday with no holidays on following days
    setTransactionDate(parentTransaction2, transactionDate2, connection);

    Logger.trace("Second ach transaction: " + secondTransIdStringAch);

    sshUtil.runIpnWorker();

    kafkaEventBase.waitForEventToProcess(KafkaEventBase.ALL_DEPOSIT_ITEMS_CREATED,
        secondTransIdStringAch);

    List<Long> transactionIdsList2 = dbBase
        .getTransactionListForParentTransId(transId2Ach, connection);
    List<Long> depositIds2;

    if (processor.equals("Profitstars")) {
      depositIds2 = getDepositIdsFromBatchItemsTable(transId2Ach, connection);
    } else {
      for (long expectedTransactionId : transactionIdsList2) {
        kafkaEventBase.waitForEventToProcess(KafkaEventBase.ALL_DEPOSIT_ITEMS_CREATED,
            String.valueOf(expectedTransactionId));
      }
      depositIds2 = getDepositIdsFromBatchItemsTable(transactionIdsList2, connection);
    }

    //Assert that payments going to the same bank account are added to the same deposit.
    Assert
        .assertTrue(depositIds2.equals(depositIdsAch),
            " The deposit ID's for both the transaction is not same even though they are going to the same bank account "
                + depositIdsAch.toString() + " and deposit 2 is " + depositIds2.toString());

    dataBaseConnector.closeConnection();
    kafkaConsumerClient.finish();
  }

  @Test(dataProvider = "oldWorldCcTransactionData", dataProviderClass =
      ManagedDepositsOneTimePaymentDataProvider.class, groups = {"manual"})
  public void testOldWorlCcStatusAfterBrCc(String testVariationNo, String testSetup,
      String paymentType,
      boolean useResidentList, boolean expressPay, String processor)
      throws Exception {
    Logger.info(
        "PM otp, where test variation: " + testVariationNo + " with " + paymentType
            + " where resident list page being used is " + useResidentList
            + " and using express pay is " + expressPay);

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testSetup);
    testSetupPage.open();
    final String pmEmail = testSetupPage.getString("pmEmail");
    final String residentId = testSetupPage.getString("residentId");

    //ArrayList<String> paymentFieldList = new ArrayList<>();
    paymentFieldList.add(testSetupPage.getString("paymentField1Label"));
    paymentFieldList.add(testSetupPage.getString("paymentField2Label"));
    paymentFieldList.add(testSetupPage.getString("paymentField3Label"));
    Collections.sort(paymentFieldList);

    PmLoginPage pmLoginPage = new PmLoginPage();

    pmLoginPage.open();
    pmLoginPage.login(pmEmail);

    PaymentBase paymentBase = new PaymentBase();

    String transIdString = paymentBase.pmOtPaymentActions(paymentType, useResidentList, expressPay,
        residentId, paymentFieldList);
    long transactionId = Long.parseLong(transIdString);

    Logger.info(transIdString);

    DataBaseConnector dataBaseConnector = new DataBaseConnector();

    dataBaseConnector.createConnection();
    Connection connection = dataBaseConnector.getConnection();

    SshUtil sshUtil = new SshUtil();

    //DEV-42804 run batch script to move cc transaction status to settled
    sshUtil.runBatchScript("br_process_cc");

    //Assert that the status of the transaction stays in status 2 after running the br script in
    // case of old world CC transactions
    Assert.assertEquals(checkTransactionsTable(transactionId, connection), "2",
          "The transaction status is not 2");

    dataBaseConnector.closeConnection();
  }

  @Test(dataProvider = "providerForReturnedBatchItemsProfitStarsTest",
      dataProviderClass = ManagedDepositsOneTimePaymentDataProvider.class, groups = {"manual"})
  public void returnedBatchItemsProfitStarsTest(String testVariationNo, String testSetup,
      boolean multipleBankAccounts) throws Exception {
    Logger.info(
        "Running returnedBatchItemsProfitStarsTest, where test variation is " + testVariationNo
            + " and multipleBankAccounts is " + Boolean.toString(multipleBankAccounts));

    DepositDao depositDao = new DepositDao();

    DataBaseConnector dataBaseConnector = new DataBaseConnector();

    dataBaseConnector.createConnection();

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

    PmLoginPage pmLoginPage = new PmLoginPage();
    pmLoginPage.open();
    pmLoginPage.login(pmEmail);

    PaymentBase paymentBase = new PaymentBase();

    String paymentType = NEW_BANK;
    boolean useResidentList = false;
    boolean expressPay = false;

    String transIdStr = paymentBase.pmOtPaymentActions(paymentType, useResidentList, expressPay, residentId,
        paymentFieldList);
    Logger.info(transIdStr);

    kafkaConsumerClient.start();

    SshUtil sshUtil = new SshUtil();
    sshUtil.runIpnWorker();

    kafkaEventBase.waitForEvent(KafkaEventBase.ALL_DEPOSIT_ITEMS_CREATED, transIdStr);

    BatchItemDao batchItemDao = new BatchItemDao();

    Connection connection = dataBaseConnector.getConnection();

    ArrayList<BatchItem> originalBatchItems = batchItemDao
        .findByTransId(connection, Long.parseLong(transIdStr), 50);

    Assert.assertEquals(originalBatchItems.size(), paymentFieldList.size() + 1,
        "There should be a batch item for each payment field plus 1 for the fee");

    ReturnTransactionPage returnTransactionPage = new ReturnTransactionPage();

    // Mark batch items as returned
    returnTransactionPage.open();
    returnTransactionPage.returnTransaction(transIdStr);

    for (BatchItem batchItem : originalBatchItems) {
      String procId = Integer.toString(batchItem.getProcId());
      kafkaEventBase.waitForEvent(KafkaEventBase.BATCH_ITEM_RETURNED, procId);
    }

    for (BatchItem batchItem : originalBatchItems) {
      String procId = Integer.toString(batchItem.getProcId());
      kafkaEventBase.waitForEvent(KafkaEventBase.DEPOSIT_ITEM_RETURNED, procId);
    }

    ArrayList<BatchItem> returnedBatchItems = batchItemDao
        .findByTransId(connection, Long.parseLong(transIdStr), 50);

    Assert.assertEquals(returnedBatchItems.size(), originalBatchItems.size(),
        "Number of batch items should not have changed");

    Set<Long> uniqueReturnDepositIds = new HashSet<>();

    for (int i = 0; i < originalBatchItems.size(); i++) {
      BatchItem originalItem = originalBatchItems.get(i);
      BatchItem updatedItem = returnedBatchItems.get(i);
      String procId = Long.toString(originalItem.getProcId());

      Assert.assertEquals(updatedItem.getDepositId(), originalItem.getDepositId(),
          "Deposit ID should not have changed for batch item " + procId);

      if (updatedItem.getCollectingFee() == 0) {
        uniqueReturnDepositIds.add(updatedItem.getReturnDepositId());

        Assert.assertNotEquals(updatedItem.getReturnDepositId(), originalItem.getReturnDepositId(),
            "Return Deposit ID should have changed. Batch item " + procId);

        Assert.assertTrue(updatedItem.getReturnDepositId() > 0,
            "Return Deposit ID should be greater than 0. Batch item " + procId);

        Deposit deposit = depositDao.findById(connection, updatedItem.getReturnDepositId());

        Deposit returnDeposit = depositDao.findById(connection, updatedItem.getReturnDepositId());

        Assert.assertEquals(returnDeposit.getCreditOrDebit(), "debit",
            "Return Deposit should be a debit deposit. Batch item " + procId);

        Assert.assertEquals(returnDeposit.getAccountNumber(), deposit.getAccountNumber(),
            "Deposit and Return Deposit should have the same bank account number");
      } else {
        Assert.assertEquals(updatedItem.getReturnDepositId(), 0,
            "Fee batch items should not go into a Return Deposit. Batch item " + procId);
      }
    }

    if (multipleBankAccounts) {
      Assert.assertEquals(uniqueReturnDepositIds.size(), paymentFieldList.size(),
          "There should be 1 return deposit for each bank account");
    } else {
      Assert.assertEquals(uniqueReturnDepositIds.size(), 1,
          "There should only be 1 return deposit for all batch items since they share a bank account");
    }

    // Make a new payment
    pmLoginPage.open();
    pmLoginPage.login(pmEmail);

    String newTransIdStr = paymentBase.pmOtPaymentActions(paymentType, useResidentList,
        expressPay, residentId, paymentFieldList);

    Logger.info(newTransIdStr);

    sshUtil.runIpnWorker();

    kafkaEventBase.waitForEvent(KafkaEventBase.ALL_DEPOSIT_ITEMS_CREATED, newTransIdStr);

    returnTransactionPage.open();
    returnTransactionPage.returnTransaction(newTransIdStr);

    ArrayList<BatchItem> newReturnedBatchItems = batchItemDao
        .findByTransId(connection, Long.parseLong(newTransIdStr), 50);

    // Assert the batch items from 2nd payment are similar to items from 1st payment
    Assert.assertEquals(newReturnedBatchItems.size(), returnedBatchItems.size(),
        "There should be the same number of batch items for the 1st and 2nd payment");
    for (int i = 0; i < returnedBatchItems.size(); i++) {
      BatchItem returnedItem = returnedBatchItems.get(i);
      BatchItem newReturnedItem = newReturnedBatchItems.get(i);

      Assert.assertEquals(newReturnedItem.getReturnDepositId(), returnedItem.getReturnDepositId(),
          "Batch items from 1st and 2nd payment should go into the same return deposit."
              + " Proc IDs: " + returnedItem.getProcId() + " & " + newReturnedItem.getProcId());
    }

    kafkaConsumerClient.finish();
    dataBaseConnector.closeConnection();
  }

  //--------------------------------TEST METHOD-----------------------------------------------------

  private String checkTransactionsTable(long transId, Connection connection) {
    TransactionDao transactionDao = new TransactionDao();
    String status = "0";
    ArrayList<Transaction> transactionsList = transactionDao
        .findById(connection, transId, 50);
    for (Transaction trans : transactionsList) {
      status = trans.getStatus();
    }
    return status;
  }

  private int checkPayoutTransaction(long transId, Connection connection) {
    TransactionDao transactionDao = new TransactionDao();

    ArrayList<Transaction> transactionsList = transactionDao
        .findByParentId(connection, transId, 50);

    List<Long> payouts = new ArrayList<>();
    for (Transaction trans : transactionsList) {
      if (trans.getTypeOfTransaction() == 2) {
        payouts.add(trans.getTransactionId());
      }
    }
    return payouts.size();
  }

  private List<Long> getDepositIdsFromBatchItemsTable(long transactionId, Connection connection) {
    BatchItemDao batchItemDao = new BatchItemDao();

    ArrayList<BatchItem> batchItemsList = batchItemDao.findByTransId(connection, transactionId, 50);

    List<Long> depositIds = new ArrayList<>();
    for (BatchItem batchItem : batchItemsList) {
      Long depositId = batchItem.getDepositId();
      if (depositId.intValue() == 0) {
        Assert.assertEquals(batchItem.getCollectingFee(), 1, "This is not a fee element");
      } else {
        depositIds.add(depositId);
      }
    }
    return depositIds;
  }

  private List<Long> getDepositIdsFromBatchItemsTable(List<Long> transactionIds,
      Connection connection) {
    BatchItemDao batchItemDao = new BatchItemDao();

    ArrayList<BatchItem> batchItemsList;

    List<Long> depositIds = new ArrayList<>();
    try {
      for (Long transaction : transactionIds) {
        batchItemsList = batchItemDao.findByTransId(connection, transaction, 50);
        for (BatchItem batchItem : batchItemsList) {
          Long depositId = batchItem.getDepositId();
          depositIds.add(depositId);
        }
      }
    } catch (Exception e) {
      Logger.info("depositIds list is empty");
    }
    return depositIds;
  }

  private Transaction getTransaction(long transId, Connection connection) {
    TransactionDao transactionDao = new TransactionDao();
    ArrayList<Transaction> transactionsList = transactionDao.findById(connection, transId, 1);

    return transactionsList.get(0);
  }

  private void setTransactionDate(Transaction transaction, String transactionDate,
      Connection connection) {
    TransactionDao transactionDao = new TransactionDao();

    transaction.setTransactionDate(transactionDate);
    transactionDao.update(connection, transaction);
  }

  private void getParentTransactionAndRunIpn(SshUtil sshUtil, Connection connection,
      long transactionId) throws Exception {
    Transaction parentTransaction = getTransaction(transactionId, connection);

    String transactionDate = TRANSACTION_DATE; // Monday with no holidays on following days
    setTransactionDate(parentTransaction, transactionDate, connection);

    sshUtil.runIpnWorker();
  }
}