package com.paylease.app.qa.manual.tests.managedDeposits;

import static com.paylease.app.qa.framework.features.PaymentFlow.SCHEDULE_FIXED_AUTO;
import static com.paylease.app.qa.framework.features.PaymentFlow.SCHEDULE_VARIABLE_AUTO;
import static com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage.NEW_CREDIT;
import static com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage.NEW_DEBIT;
import static com.paylease.app.qa.framework.pages.paymentflow.SchedulePage.SELECT_BI_ANNUALLY;

import com.paylease.app.qa.framework.AppConstant;
import com.paylease.app.qa.framework.DataBaseConnector;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.UtilityManager;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.paymentflow.ReviewAndSubmitPage;
import com.paylease.app.qa.framework.pages.paymentflow.SchedulePage;
import com.paylease.app.qa.framework.pages.pm.PmFapListPage;
import com.paylease.app.qa.framework.pages.pm.PmLoginPage;
import com.paylease.app.qa.framework.pages.pm.PmVapListPage;
import com.paylease.app.qa.framework.utility.database.client.dao.AutopayTemplateDao;
import com.paylease.app.qa.framework.utility.database.client.dao.TransactionDao;
import com.paylease.app.qa.framework.utility.database.client.dao.TransactionsManagedDepositsV2Dao;
import com.paylease.app.qa.framework.utility.database.client.dao.UserDao;
import com.paylease.app.qa.framework.utility.database.client.dto.AutopayTemplate;
import com.paylease.app.qa.framework.utility.database.client.dto.Transaction;
import com.paylease.app.qa.framework.utility.database.client.dto.TransactionsManagedDepositsV2;
import com.paylease.app.qa.framework.utility.database.client.dto.User;
import com.paylease.app.qa.framework.utility.kafka.KafkaConsumerClient;
import com.paylease.app.qa.framework.utility.sshtool.SshUtil;
import com.paylease.app.qa.testbase.KafkaEventBase;
import com.paylease.app.qa.testbase.PaymentBase;
import com.paylease.app.qa.testbase.dataproviders.ManagedDepositsOneTimePaymentDataProvider;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import org.testng.Assert;
import org.testng.annotations.Test;


public class AutoPayTest extends ScriptBase {

  private static final String REGION_YAVO = "Integration";
  private static final String REGION_NON_YAVO = "Pm";

  private static final String FEATURE_YAVO = "ManagedDepositsForYavoIntegration";
  private static final String FEATURE_NON_YAVO = "ManagedDepositsOtp";

  //--------------------------------ONE TIME PAYMENT FLOW TESTS-------------------------------------

  @Test(dataProvider = "autoPayPm",
      dataProviderClass = ManagedDepositsOneTimePaymentDataProvider.class, groups = {"manual"})
  public void fapTransactionInsertedTest(String testVariationNo, String testCaseSetup,
      String frequency, String paymentType, boolean expressPay, boolean isYavoPm,
      boolean isManagedDepositsV2On) throws Exception {
    Logger.info("PM vap, where test variation: " + testVariationNo + " with " + paymentType
            + " where testCaseSetup" + testCaseSetup
            + " and frequency is: " + frequency
            + " and using express pay is " + expressPay
            + " and isManagedDepositsV2On set to " + isManagedDepositsV2On
            + " and isYavoPm is " + isYavoPm);

    TestSetupPage testSetupPage;

    if (isYavoPm) {
      testSetupPage = new TestSetupPage(REGION_YAVO, FEATURE_YAVO, testCaseSetup);
    } else {
      testSetupPage = new TestSetupPage(REGION_NON_YAVO, FEATURE_NON_YAVO, testCaseSetup);
    }

    testSetupPage.open();
    final String pmEmail = testSetupPage.getString("pmEmail");
    final String residentId = testSetupPage.getString("residentId");
    final String topicName = testSetupPage.getString("kafkaTopic");
    final String groupId = testSetupPage.getString("kafkaGroupId");

    PmLoginPage pmLoginPage = new PmLoginPage();

    pmLoginPage.open();
    pmLoginPage.login(pmEmail);

    String autopayTransId = pmFapPaymentActions(residentId, frequency, paymentType, expressPay);
    Logger.info("Transaction ID: " + autopayTransId);

    DataBaseConnector dataBaseConnector = new DataBaseConnector();
    dataBaseConnector.createConnection();
    Connection connection = dataBaseConnector.getConnection();

    //Start the Consumer
    KafkaConsumerClient kafkaConsumerClient = new KafkaConsumerClient(AppConstant.KAFKA_BROKER_URL,
        AppConstant.USERNAME, AppConstant.PASSWORD, topicName, groupId);

    kafkaConsumerClient.start();

    //Update auto_pay_start_date and auto_pay_debit_day to today's date
    Transaction autopayTransaction;

    TransactionDao transactionDao = new TransactionDao();
    autopayTransaction = transactionDao.findById(connection, Long.parseLong(autopayTransId), 1)
        .get(0);

    String yearMonthDay = UtilityManager.getCurrentDate(UtilityManager.YEAR_MONTH_DAY_DASH);
    String currentDate = yearMonthDay.substring(yearMonthDay.length() - 2);

    autopayTransaction.setAutoPayStartDate(yearMonthDay);
    autopayTransaction.setAutoPayDebitDay(currentDate);

    int rowsUpdated = transactionDao.update(connection, autopayTransaction);

    Assert.assertEquals(rowsUpdated, 1,
        "Rows updated is not equal to 1. Test case variation: " + testVariationNo);

    //Run "process_auto_pays" script
    SshUtil sshUtil = new SshUtil();

    if (paymentType.equals(NEW_CREDIT) || paymentType.equals(NEW_DEBIT)) {
      sshUtil.runBatchScriptWithArgs("process_autopay_cc_payments", yearMonthDay);
    } else {
      if (frequency.equals(SELECT_BI_ANNUALLY)) {
        sshUtil.runBatchScriptWithArgs("process_auto_pays_biyearly", yearMonthDay);
      } else {
        sshUtil.runBatchScriptWithArgs("process_auto_pays", yearMonthDay);
      }
    }

    TransactionsManagedDepositsV2Dao transactionsManagedDepositsV2Dao = new TransactionsManagedDepositsV2Dao();

    ArrayList<TransactionsManagedDepositsV2> managedDepositsV2ArrayList = transactionsManagedDepositsV2Dao
        .findByTransId(connection, autopayTransId, 1);

    //Assert transaction that is created is inserted into transactions_managed_deposits_v2
    if (isManagedDepositsV2On) {
      Assert.assertEquals(managedDepositsV2ArrayList.size(), 1,
          "Did not find 1 transaction in the transactions_managed_deposits_v2 table "
              + "with transaction id "
              + autopayTransId + ". Test case variation: " + testVariationNo);

    } else {
      Assert.assertEquals(managedDepositsV2ArrayList.size(), 0,
          "Found transaction " + autopayTransId
              + "exists in transactions_managed_deposits_v2 tableTest case variation: "
              + testVariationNo);
    }

    ArrayList<Transaction> transactionTriggeredByAutoPay = new ArrayList<>();

    transactionTriggeredByAutoPay = transactionDao
        .findByPaymentMadeBy(connection, Long.parseLong(autopayTransId), 10);


    sshUtil.runIpnWorker();

    for (Transaction trans : transactionTriggeredByAutoPay) {
      long transactionId = trans.getTransactionId();

      KafkaEventBase kafkaEventBase = new KafkaEventBase(kafkaConsumerClient);
      String eventTriggered = kafkaEventBase
          .getEventNameFromMessageOnTopic(String.valueOf(transactionId),
              kafkaConsumerClient, isManagedDepositsV2On);

      if (isManagedDepositsV2On) {
        Assert.assertEquals(eventTriggered, "TRANSACTION_CREATED",
            "The event that published this message is not transaction created event. Test case variation: "
                + testVariationNo);
      } else {
        Assert.assertNull(eventTriggered,
            "The transaction was published to the topic. Test case variation: " + testVariationNo);
      }
    }

    kafkaConsumerClient.finish();
    dataBaseConnector.closeConnection();
  }

  @Test(dataProvider = "autoPayPm", dataProviderClass =
      ManagedDepositsOneTimePaymentDataProvider.class, groups = {"manual"})
  public void vapTransactionInsertedTest(String testVariationNo, String testCaseSetup,
      String frequency, String paymentType, boolean expressPay, boolean isYavoPm,
      boolean isManagedDepositsV2On) throws Exception {
    Logger.info("PM vap, where test variation: " + testVariationNo + " with " + paymentType
        + " where testCaseSetup" + testCaseSetup
        + " and frequency is: " + frequency
        + " and using express pay is " + expressPay
        + " and isManagedDepositsV2On set to " + isManagedDepositsV2On
        + " and isYavoPm is " + isYavoPm);

    TestSetupPage testSetupPage;

    if (isYavoPm) {
      testSetupPage = new TestSetupPage(REGION_YAVO, FEATURE_YAVO, testCaseSetup);
    } else {
      testSetupPage = new TestSetupPage(REGION_NON_YAVO, FEATURE_NON_YAVO, testCaseSetup);
    }

    testSetupPage.open();
    final String pmEmail = testSetupPage.getString("pmEmail");
    final String residentId = testSetupPage.getString("residentId");
    final String topicName = testSetupPage.getString("kafkaTopic");
    final String groupId = testSetupPage.getString("kafkaGroupId");

    PmLoginPage pmLoginPage = new PmLoginPage();

    pmLoginPage.open();
    pmLoginPage.login(pmEmail);

    String autopayTransId = pmVapPaymentActions(residentId, frequency, paymentType, expressPay);
    Logger.info("Autopay Transaction ID: " + autopayTransId);

    DataBaseConnector dataBaseConnector = new DataBaseConnector();

    dataBaseConnector.createConnection();
    Connection connection = dataBaseConnector.getConnection();

    //Start the Consumer
    KafkaConsumerClient kafkaConsumerClient = new KafkaConsumerClient(
        AppConstant.KAFKA_BROKER_URL,
        AppConstant.USERNAME,
        AppConstant.PASSWORD, topicName, groupId);

    kafkaConsumerClient.start();

    //Update auto_pay_start_date and auto_pay_debit_day to today's date

    AutopayTemplateDao autopayTemplateDao = new AutopayTemplateDao();
    AutopayTemplate autopayTemplate = autopayTemplateDao
        .findById(connection, Long.parseLong(autopayTransId), 1).get(0);

    // Get one month ago
    LocalDate lastMonth = LocalDate.now().minus(1, ChronoUnit.MONTHS);

    DateTimeFormatter yearMonthDayFormatter = DateTimeFormatter.ofPattern(UtilityManager.YEAR_MONTH_DAY_DASH);
    autopayTemplate.setStartDate(yearMonthDayFormatter.format(lastMonth));

    DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern(UtilityManager.DAY);

    autopayTemplate.setLastExecutedDate(null);
    autopayTemplate.setDebitDay(dayFormatter.format(lastMonth));

    //Update rent_amount and late_amount and rent_amount_last_updated and late_amount_last_updated of resident in users table

    UserDao userDao = new UserDao();

    User user = userDao.findById(connection, Long.parseLong(residentId), 1).get(0);

    user.setRentAmount("1500.00");
    user.setLateAmount("1500.00");
    user.setRentAmountLastUpdated(yearMonthDayFormatter.format(lastMonth) + " 00:00:00");
    user.setLateAmountLastUpdated(yearMonthDayFormatter.format(lastMonth) + " 00:00:00");

    int rowsUpdatedAutopayTemplate = autopayTemplateDao.update(connection, autopayTemplate);
    int rowsUpdatedUsers = userDao.update(connection, user);

    Assert.assertTrue(rowsUpdatedAutopayTemplate == 1 && rowsUpdatedUsers == 1,
        "Rows updated are not equal to 1. Test case variation: " + testVariationNo);

    //Run "process_auto_pays" script
    SshUtil sshUtil = new SshUtil();

    sshUtil.runBatchScriptWithArgs("run-autopays", yearMonthDayFormatter.format(lastMonth), autopayTemplate.getAutopayId());

    //Get the transaction id of the transaction generated from the autopay
    TransactionDao transactionDao = new TransactionDao();
    Transaction transaction = transactionDao
        .findByAutopayId(connection, Long.parseLong(autopayTransId), 1).get(0);

    Logger.info("Payment Transaction ID: " + transaction.getTransactionId());
    if (paymentType.equals(NEW_CREDIT) || paymentType.equals(NEW_DEBIT)) {
      sshUtil.runBatchScriptWithArgs("process_autopay_cc_payments", yearMonthDayFormatter.format(lastMonth));
    }

    TransactionsManagedDepositsV2Dao transactionsManagedDepositsV2Dao = new TransactionsManagedDepositsV2Dao();

    ArrayList<TransactionsManagedDepositsV2> managedDepositsV2ArrayList = transactionsManagedDepositsV2Dao
        .findByTransId(connection, String.valueOf(transaction.getTransactionId()), 1);

    //Assert transaction that is created is inserted into transactions_managed_deposits_v2
    if (isManagedDepositsV2On) {
      Assert.assertEquals(managedDepositsV2ArrayList.size(), 1,
          "Did not find the transaction in the transactions_managed_deposits_v2 table. Test case variation: "
              + testVariationNo);
    } else {
      Assert.assertEquals(managedDepositsV2ArrayList.size(), 0,
          "Found transaction " + transaction.getTransactionId()
              + "exists in transactions_managed_deposits_v2 table. Test case variation: "
              + testVariationNo);
    }

    sshUtil.runIpnWorker();

    KafkaEventBase kafkaEventBase = new KafkaEventBase(kafkaConsumerClient);
    String eventTriggered = kafkaEventBase.getEventNameFromMessageOnTopic(String.valueOf(transaction.getTransactionId()),
        kafkaConsumerClient, isManagedDepositsV2On);

    if (isManagedDepositsV2On) {
      Assert.assertEquals(eventTriggered, "TRANSACTION_CREATED",
          "The event that published this message is not transaction created event. Test case variation: "
              + testVariationNo);
    } else {
      Assert.assertNull(eventTriggered,
          "The transaction was published to the topic. Test case variation: " + testVariationNo);
    }

    kafkaConsumerClient.finish();
    dataBaseConnector.closeConnection();
  }

  //--------------------------------TEST METHOD-----------------------------------------------------

  /**
   * Perform PM fap payment actions.
   *
   * @param residentId resident ID
   * @param frequency frequency
   * @param paymentType payment type
   * @param expressPay express pay
   */
  private String pmFapPaymentActions(String residentId, String frequency, String paymentType,
      boolean expressPay) {
    PaymentBase paymentBase = new PaymentBase();

    paymentBase.selectResidentFromPaymentsDropDownAndBeginPayment(residentId, SCHEDULE_FIXED_AUTO);

    paymentBase.fillAndSubmitPaymentAmount(false);

    SchedulePage schedulePage = new SchedulePage();
    schedulePage.prepField(SchedulePage.FIELD_FREQUENCY, frequency);
    schedulePage.prepField(SchedulePage.FIELD_INDEFINITE, false);
    schedulePage.fillAndSubmitPaymentScheduleDetails();

    paymentBase.selectPaymentMethod(paymentType, expressPay);

    ReviewAndSubmitPage reviewAndSubmitPage = new ReviewAndSubmitPage();

    String autopayTransId = reviewAndSubmitPage.getAutopayId();

    paymentBase.reviewAndSubmit();

    PmFapListPage pmFapListPage = new PmFapListPage();

    Assert.assertTrue(pmFapListPage.pageIsLoaded(), "Should be on Fixed AutoPay list page");

    return autopayTransId;
  }

  /**
   * Perform PM vap payment actions.
   *
   * @param residentId resident ID
   * @param frequency frequency
   * @param paymentType payment type
   * @param expressPay express pay
   */
  private String pmVapPaymentActions(String residentId, String frequency,
      String paymentType, boolean expressPay) {
    PaymentBase paymentBase = new PaymentBase();

    paymentBase
        .selectResidentFromPaymentsDropDownAndBeginPayment(residentId, SCHEDULE_VARIABLE_AUTO);

    SchedulePage schedulePage = new SchedulePage();
    schedulePage.prepField(SchedulePage.FIELD_FREQUENCY, frequency);
    schedulePage.prepField(SchedulePage.FIELD_INDEFINITE, false);

    schedulePage.prepField(SchedulePage.FIELD_MAX_LIMIT_ENABLED, false);
    schedulePage.fillAndSubmitPaymentScheduleDetails();

    paymentBase.selectPaymentMethod(paymentType, expressPay);

    ReviewAndSubmitPage reviewAndSubmitPage = new ReviewAndSubmitPage();

    String autopayTransId = reviewAndSubmitPage.getAutopayId();

    paymentBase.reviewAndSubmit();

    PmVapListPage pmVapListPage = new PmVapListPage();

    Assert.assertTrue(pmVapListPage.pageIsLoaded(), "Should be on Variable AutoPay list page");

    return autopayTransId;
  }
}
