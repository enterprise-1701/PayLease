package com.paylease.app.qa.manual.tests.managedDeposits;

import static com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage.NEW_BANK;

import com.paylease.app.qa.e2e.tests.resident.OneTimePaymentTest;
import com.paylease.app.qa.framework.AppConstant;
import com.paylease.app.qa.framework.DataBaseConnector;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.utility.database.client.dao.BatchItemDao;
import com.paylease.app.qa.framework.utility.database.client.dao.DeadMessagesDao;
import com.paylease.app.qa.framework.utility.database.client.dao.TransactionsManagedDepositsV2Dao;
import com.paylease.app.qa.framework.utility.database.client.dao.YavoCredentialsDao;
import com.paylease.app.qa.framework.utility.database.client.dto.BatchItem;
import com.paylease.app.qa.framework.utility.database.client.dto.TransactionsManagedDepositsV2;
import com.paylease.app.qa.framework.utility.kafka.KafkaConsumerClient;
import com.paylease.app.qa.framework.utility.sshtool.SshUtil;
import com.paylease.app.qa.testbase.KafkaEventBase;
import com.paylease.app.qa.testbase.dataproviders.HandleRetryMdDataProvider;
import java.sql.Connection;
import java.util.ArrayList;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HandleRetryEvent extends ScriptBase {

  private static final String REGION = "Integration";
  private static final String FEATURE = "ManagedDepositsForYavoIntegration";

  //----------------------------------------TESTS---------------------------------------------------

  @Test(dataProvider = "retry", dataProviderClass = HandleRetryMdDataProvider.class, groups =
      {"manual"})
  public void testRetry(String testCase, boolean nonRetryable, boolean maxRetry) throws Exception {
    Logger.info("Test for handling retry event where test case is: " + testCase
        + " where non-retryable: " + nonRetryable + "max retry is " + maxRetry);

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE,
        "createYavoPmWithManagedDepositsV2");
    testSetupPage.open();

    final String pmId = testSetupPage.getString("pmId");
    final String residentEmail = testSetupPage.getString("residentEmail");
    final String topicName = testSetupPage.getString("kafkaTopic");
    final String groupId = testSetupPage.getString("kafkaGroupId");

    com.paylease.app.qa.e2e.tests.resident.OneTimePaymentTest oneTimePaymentTest =
        new OneTimePaymentTest();

    String transactionId = oneTimePaymentTest.residentOtPaymentActions(residentEmail, NEW_BANK,
        false, false, null);

    Logger.info("Transaction ID is: " + transactionId);

    DataBaseConnector dataBaseConnector = new DataBaseConnector();

    dataBaseConnector.createConnection();
    Connection connection = dataBaseConnector.getConnection();

    YavoCredentialsDao yavoCredentialsDao = new YavoCredentialsDao();

    if (nonRetryable) {
      yavoCredentialsDao.updatePassword(connection, pmId, "1234");
    } else {
      yavoCredentialsDao.updateUrl(connection, pmId, "https://www.yardiyc1.com/8223tp7s/");
    }

    //Start the Consumer
    KafkaConsumerClient kafkaConsumerClient = new KafkaConsumerClient(AppConstant.KAFKA_BROKER_URL,
        AppConstant.USERNAME, AppConstant.PASSWORD, topicName, groupId);

    KafkaConsumerClient kafkaConsumerClientRetry = new KafkaConsumerClient(
        AppConstant.KAFKA_BROKER_URL, AppConstant.USERNAME, AppConstant.PASSWORD,
        topicName + "_retry_1", groupId);

    kafkaConsumerClient.start();
    if (!nonRetryable || maxRetry) {
      kafkaConsumerClientRetry.start();
    }

    SshUtil sshUtil = new SshUtil();

    sshUtil.runIpnWorker();

    TransactionsManagedDepositsV2Dao transactionsManagedDepositsV2Dao =
        new TransactionsManagedDepositsV2Dao();

    ArrayList<TransactionsManagedDepositsV2> managedDepositsV2ArrayList =
        transactionsManagedDepositsV2Dao.findByTransId(connection, transactionId, 1);

    //Assert transaction that is created is inserted into transactions_managed_deposits_v2
    Assert.assertEquals(1, managedDepositsV2ArrayList.size(),
        "Did not find the transaction in the transactions_managed_deposits_v2 table");

    KafkaEventBase kafkaEventBase = new KafkaEventBase(kafkaConsumerClient);
    KafkaEventBase kafkaEventBaseRetry = new KafkaEventBase(kafkaConsumerClientRetry);

    // Wait for batch items to be created
    kafkaEventBase.waitForEvent(KafkaEventBase.ALL_DEPOSIT_ITEMS_CREATED, transactionId);

    BatchItemDao batchItemDao = new BatchItemDao();

    ArrayList<BatchItem> batchItems = batchItemDao
        .findByTransId(connection, Long.parseLong(transactionId), 50);

    BatchItem batchItem = batchItems.get(0);

    String eventTriggered;
    String retryEventTriggered;

    DeadMessagesDao deadMessagesDao = new DeadMessagesDao();

    if (nonRetryable) {
      kafkaEventBase.waitForEventToProcess(KafkaEventBase.DEPOSIT_ITEM_CREATED,
          String.valueOf(batchItem.getProcId()));

      Thread.sleep(5000);

      Assert.assertTrue(deadMessagesDao.isTransactionIdPresent(connection, transactionId),
          "Transaction Id is not present in the message");
    } else {

      eventTriggered = kafkaEventBase.waitForEvent(KafkaEventBase.DEPOSIT_ITEM_CREATED,
          String.valueOf(batchItem.getProcId()));

      retryEventTriggered = kafkaEventBaseRetry
          .waitForEvent(KafkaEventBase.DEPOSIT_ITEM_CREATED, String.valueOf(batchItem.getProcId()));

      JSONParser parser = new JSONParser();

      JSONObject originalEventObj = (JSONObject) parser.parse(eventTriggered);
      JSONObject retryEventObj = (JSONObject) parser.parse(retryEventTriggered);

      Assert.assertEquals(retryEventObj.get("context").toString(),
          originalEventObj.get("context").toString(), "Message context should match.");

      Assert.assertEquals(retryEventObj.get("correlationId").toString(),
          originalEventObj.get("correlationId").toString(), "Message correlationId should match.");

      Assert.assertEquals(retryEventObj.get("retryAttempt").toString(), "1",
          "Message retryAttempt should be 1.");
    }

    if (maxRetry) {
      //You have to start listening to the retry topic in the integration-service in order to
      // assert this
      kafkaEventBaseRetry.waitForEventToProcess(KafkaEventBase.DEPOSIT_ITEM_CREATED,
          String.valueOf(batchItem.getProcId()));

      Assert.assertTrue(deadMessagesDao.isTransactionIdPresent(connection, transactionId),
          "Transaction Id is not present in the message");
    }

    kafkaConsumerClient.finish();
    kafkaConsumerClientRetry.finish();
    dataBaseConnector.closeConnection();
  }
}