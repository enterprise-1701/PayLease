package com.paylease.app.qa.functional.tests.integration;

import static com.paylease.app.qa.framework.AppConstant.CHECK_IMAGE_BASE_URL;
import static com.paylease.app.qa.framework.AppConstant.PATH_TO_LOG_FILES;
import static com.paylease.app.qa.framework.pages.paymentflow.BankAccountDetailsPage.CHECKING_ACCOUNT;
import static com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage.NEW_BANK;

import com.paylease.app.qa.api.tests.aapi.testcase.RdcTransactionCallback;
import com.paylease.app.qa.api.tests.gapi.testcase.AchPayment;
import com.paylease.app.qa.framework.DataBaseConnector;
import com.paylease.app.qa.framework.DataHelper;
import com.paylease.app.qa.framework.DriverManager;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ResourceFactory;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.UtilityManager;
import com.paylease.app.qa.framework.api.Credentials;
import com.paylease.app.qa.framework.api.aapi.RdcRequest;
import com.paylease.app.qa.framework.api.gapi.GapiRequest;
import com.paylease.app.qa.framework.api.gapi.GapiResponse;
import com.paylease.app.qa.framework.features.PaymentFlow;
import com.paylease.app.qa.framework.pages.automatedhelper.DecryptedDataPage;
import com.paylease.app.qa.framework.pages.automatedhelper.ResmanTransactionPage;
import com.paylease.app.qa.framework.pages.automatedhelper.ResmanTransactionProcessingPage;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.automatedhelper.TransactionPage;
import com.paylease.app.qa.framework.pages.automatedhelper.TransactionProcessingPage;
import com.paylease.app.qa.framework.pages.paymentflow.ReceiptPage;
import com.paylease.app.qa.framework.utility.database.client.dao.CheckImageNotificationRetryDao;
import com.paylease.app.qa.framework.utility.database.client.dto.CheckImageNotificationRetry;
import com.paylease.app.qa.framework.utility.listeners.Retry;
import com.paylease.app.qa.framework.utility.sshtool.SshUtil;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ResmanIpnTest extends ScriptBase {

  private static final String REGION = "integration";
  private static final String FEATURE = "resmanIpn";

  @Test(groups = {"manual", "queue"})
  public void transactionWithoutBillingAccountIdFromGapi() throws Exception {
    Logger.info("Don't integrate transaction from GAPI with no BillingAccountId");

    String transId = createTransaction("tc4041");

    runIpnWorker();

    assertNumTrans(transId, 0);
  }

  @Test(groups = {"manual", "queue"})
  public void transactionWithoutBillingAccountIdFromSso() throws Exception {
    Logger.info("Don't integrate transaction from SSO with no BillingAccountId");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc4050");
    testSetupPage.open();

    final String ssoUrl = testSetupPage.getString("ssoUrl");
    final String paymentField = testSetupPage.getString("paymentField");

    DriverManager.getDriver().get(ssoUrl);

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_RES, PaymentFlow.SCHEDULE_ONE_TIME);
    paymentFlow.addAmount(paymentField, "100.00");
    paymentFlow.setPaymentMethod(NEW_BANK);
    paymentFlow.setBankType(CHECKING_ACCOUNT);
    paymentFlow.openStep(PaymentFlow.STEP_AMOUNT);
    paymentFlow.advanceToStep(PaymentFlow.STEP_RECEIPT);

    ReceiptPage receiptPage = new ReceiptPage();
    String transId = receiptPage.getTransactionId();

    Logger.trace("Transaction Id was: " + transId);

    runIpnWorker();

    assertNumTrans(transId, 0);
  }

  @Test(groups = {"resman"})
  public void transactionWithoutBillingAccountIdFromCatchUpScript() {
    Logger.info(
        "Attempt to integrate transaction from catch up script with BillingAccountId from Resman");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc4053");
    testSetupPage.open();

    final String transId = testSetupPage.getString("transId");
    final String pmId = testSetupPage.getString("pmId");

    runCatchUpScript(pmId);

    assertNumTrans(transId, 1);
  }

  @Test(groups = {"manual", "queue", "resman"})
  public void getBillingAccountIdForValidResidentViaIpn() throws Exception {
    Logger.info("Attempt to integrate transaction from GAPI with BillingAccountId from Resman");

    String transId = createTransaction("tc4057");

    runIpnWorker();

    assertNumTrans(transId, 1);
  }

  @Test
  public void getBillingAccountIdForInvalidResidentViaCatchUpScript() {
    Logger.info(
        "Don't integrate transaction from catch up script with no BillingAccountId from Resman");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc4060");
    testSetupPage.open();

    final String transId = testSetupPage.getString("transId");
    final String pmId = testSetupPage.getString("pmId");

    runCatchUpScript(pmId);

    assertNumTrans(transId, 0);
  }

  @Test(groups = {"manual", "queue", "resman"})
  public void clearBillingAccountIdForResidentViaIpn() throws Exception {
    Logger.info(
        "Resident yardi_account will be cleared when Resman integration fails due to invalid BillingAccountID via IPN");

    String transId = createTransaction("tc4083");

    runIpnWorker();

    TransactionPage transactionPage = new TransactionPage();
    transactionPage.open();

    TransactionProcessingPage transactionProcessingPage = transactionPage
        .getDataForTransactionId(transId);

    String yardiAccount = transactionProcessingPage.getFromUserYardiAccount();

    Assert.assertEquals(yardiAccount, "", "There should be no yardi account for this resident");
  }

  @Test(groups = {"resman"})
  public void clearBillingAccountIdForResidentViaCatchUpScript() {
    Logger.info(
        "Resident yardi_account will be cleared when Resman integration fails due to invalid BillingAccountID via catch up script");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc4084");
    testSetupPage.open();

    final String transId = testSetupPage.getString("transId");
    final String pmId = testSetupPage.getString("pmId");

    runCatchUpScript(pmId);

    TransactionPage transactionPage = new TransactionPage();
    transactionPage.open();

    TransactionProcessingPage transactionProcessingPage = transactionPage
        .getDataForTransactionId(transId);

    String yardiAccount = transactionProcessingPage.getFromUserYardiAccount();

    Assert.assertEquals(yardiAccount, "", "There should be no yardi account for this resident");
  }

  @Test(groups = {"manual", "queue", "resman"})
  public void showLogMessageForRdcCheckImagesAfterIpnSuccessful() throws Exception {
    Logger.info("Validate log entry indicating ready to send check images links");

    String transId = createAndProcessRdcWithIpn("tc4269");

    waitForCheckImageNotificationJobProcessed(transId, new String[]{"front", "back"});

    assertCheckImageNotificationJobStarted(transId);
  }

  @Test(groups = {"manual", "queue", "resman"})
  public void noLogMessageForRdcCheckImagesAfterIpnUnsuccessful() throws Exception {
    Logger.info("Validate log entry indicating ready to send check images links not present");

    String transId = createAndProcessRdcWithIpn("tc4273");

    assertNoCheckImageNotificationJobs(transId);
  }

  @Test(groups = {"manual", "queue", "resman"})
  public void noLogMessageForNonRdcAfterIpnSuccessful() throws Exception {
    Logger.info(
        "Validate log entry indicating ready to send check images links not present for non RDC transaction");

    String transId = createTransaction("tc4274");

    runIpnWorker();

    SshUtil sshUtil = new SshUtil();

    sshUtil.waitForLogMessage(
        ": Completed IPN for transaction '" + transId + "'.",
        PATH_TO_LOG_FILES + "*_ipn_queue_consumer.log"
    );

    assertNoCheckImageNotificationJobs(transId);
  }

  @Test(groups = {"manual", "queue", "resman"})
  public void showRequestResponseLogMessageForEachRdcCheckImage() throws Exception {
    Logger.info("Validate log shows request and response for RDC check image");

    String testCaseId = "tc4281";
    String transId = createAndProcessRdcWithIpn(testCaseId);

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCaseId);
    final String propNumber = testSetupPage.getString("propNumber");

    waitForCheckImageNotificationJobProcessed(transId, new String[]{"front", "back"});

    assertRequestData(transId, "front", propNumber);
    assertRequestData(transId, "back", propNumber);

    assertResponseData(transId, "front");
    assertResponseData(transId, "back");
  }

  @DataProvider
  public Object[][] provideNonIntegratedTransactions() {
    return new Object[][]{
        //Test variation no.
        {"tc4306"},
        {"tc4060"} // tc4308 uses same setup as 4060
    };
  }

  @Test(dataProvider = "provideNonIntegratedTransactions", retryAnalyzer = Retry.class)
  public void noCheckImageNotificationJobsOnSendNotificationsFailure(String testCaseId) {
    Logger.info("Validate there should be no job added to the check image notification queue");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCaseId);
    testSetupPage.open();

    final String transId = testSetupPage.getString("transId");
    final String pmId = testSetupPage.getString("pmId");

    runCatchUpScript(pmId);

    assertNumTrans(transId, 0);

    assertNoCheckImageNotificationJobs(transId);
  }

  @Test(groups = {"manual", "queue", "resman"})
  public void showLogMessageForRdcCheckImagesAfterSendNotificationsSuccessful() throws Exception {
    Logger.info("Validate log entry indicating ready to send check images links");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc4307");
    testSetupPage.open();

    final String transId = testSetupPage.getString("transId");
    final String pmId = testSetupPage.getString("pmId");

    runCatchUpScript(pmId);

    waitForCheckImageNotificationJobProcessed(transId, new String[]{"front", "back"});

    assertCheckImageNotificationJobStarted(transId);
  }

  @DataProvider
  public Object[][] provideImageTypesScript() {
    return new Object[][]{
        //TestCaseId, ImageType
        {"tc4307", "front"},
        {"tc4307", "back"},
    };
  }

  @Test(groups = {"manual", "queue", "resman"}, dataProvider = "provideImageTypesScript")
  public void noRetryDbEntryOnCheckImageNotificationSuccessFromSendNotificationScript(
      String testCaseId, String imageType) throws Exception {
    Logger.info(
        "Verify that there is no database entry for the RDC transaction from send_notifications script - "
            + imageType);

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCaseId);
    testSetupPage.open();

    final String transId = testSetupPage.getString("transId");
    final String pmId = testSetupPage.getString("pmId");

    runCatchUpScript(pmId);

    waitForCheckImageNotificationJobProcessed(transId, new String[]{imageType});

    CheckImageNotificationRetry cinRetry = getRetryEntry(transId, imageType);

    Assert.assertNull(cinRetry, "Retry entry should not be inserted on success");
  }

  @Test(groups = {"manual", "queue", "resman"}, dataProvider = "provideImageTypesScript")
  public void showRetryDbEntryOnCheckImageNotificationFailureFromSendNotificationScript(
      String testCaseId, String imageType) throws Exception {
    Logger.info(
        "Verify that there is a database entry for the RDC transaction from send_notifications script - "
            + imageType);

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCaseId);
    testSetupPage.open();

    final String transId = testSetupPage.getString("transId");
    final String pmId = testSetupPage.getString("pmId");

    runCatchUpScript(pmId);

    waitForCheckImageNotificationJobProcessed(transId, new String[]{imageType}, true);

    assertRetryEntry(transId, imageType);
  }

  @DataProvider
  public Object[][] provideImageTypesIpn() {
    return new Object[][]{
        //TestCaseId, ImageType
        {"tc4269", "front"},
        {"tc4269", "back"}
    };
  }

  @Test(groups = {"manual", "queue", "resman"}, dataProvider = "provideImageTypesIpn")
  public void noRetryDbEntryOnCheckImageNotificationSuccessFromIpn(String testCaseId,
      String imageType) throws Exception {
    Logger.info(
        "Verify that there is no database entry for the RDC transaction from IPN - " + imageType);

    String transId = createAndProcessRdcWithIpn(testCaseId);

    waitForCheckImageNotificationJobProcessed(transId, new String[]{imageType});

    CheckImageNotificationRetry cinRetry = getRetryEntry(transId, imageType);

    Assert.assertNull(cinRetry, "Retry entry should not be inserted on success");
  }

  @Test(groups = {"manual", "queue", "resman"}, dataProvider = "provideImageTypesIpn")
  public void showRetryDbEntryOnCheckImageNotificationFailureFromIpn(String testCaseId,
      String imageType) throws Exception {
    Logger.info(
        "Verify that there is a database entry for the RDC transaction from IPN - " + imageType);

    String transId = createAndProcessRdcWithIpn(testCaseId);

    waitForCheckImageNotificationJobProcessed(transId, new String[]{imageType}, true);

    assertRetryEntry(transId, imageType);
  }

  private String createAndProcessRdcWithIpn(String testCaseId) throws Exception {
    DataHelper dataHelper = new DataHelper();

    final String checkNum = dataHelper.getCheckNum();
    final String amount = "113.36";

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCaseId);
    testSetupPage.open();

    final String pmId = testSetupPage.getString("pmId");
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String propId = testSetupPage.getString("propId");
    final String tenantId = testSetupPage.getString("tenantId"); // user->user_id
    final String residentId = testSetupPage.getString("residentId"); // user->integration_user_id
    final String bankAccountId = testSetupPage.getString("bankAccountId");
    final String locationId = testSetupPage.getString("locationId");

    RdcRequest request = new RdcRequest(
        new Credentials(gatewayId, username, password), "Test", "wfs"
    );

    RdcTransactionCallback testCase = new RdcTransactionCallback("RDC transaction", null);

    String batchSequence = testCase
        .createBatch(pmId, locationId, "P" + bankAccountId, amount, "1589635972", "11000028");

    testCase.addCheckToBatch(
        batchSequence, propId, tenantId, residentId, "Harry",
        "Dresden", amount, "267376382670", checkNum, "51000017"
    );

    testCase.addTransaction(request);
    request.sendRequest();

    UtilityManager utilityManager = new UtilityManager();
    String transId = utilityManager.getLastTransIdForResident(tenantId, pmId);

    Logger.trace("Transaction ID: " + transId);
    Assert.assertNotEquals(transId, "", "Transaction should be found");

    runIpnWorker();

    SshUtil sshUtil = new SshUtil();
    sshUtil.waitForLogMessage(
        ": Completed IPN for transaction '" + transId + "'.",
        PATH_TO_LOG_FILES + "*_ipn_queue_consumer.log"
    );

    return transId;
  }

  private String createTransaction(String testCaseName) throws Exception {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCaseName);
    testSetupPage.open();

    // Credentials
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String payeeId = testSetupPage.getString("payeeId");
    final String payerReferenceId = testSetupPage.getString("payerReferenceId");

    DataHelper dataHelper = new DataHelper();
    GapiRequest gapiRequest = new GapiRequest(new Credentials(gatewayId, username, password));

    AchPayment testCase = AchPayment.createValidAchPayment(
        dataHelper.getReferenceId(),
        "Resman transaction - with valid Billing Account ID",
        null,
        payeeId).setPayerReferenceId(payerReferenceId);

    testCase.addTransaction(gapiRequest);

    GapiResponse response = gapiRequest.sendRequest();
    response.setIndex("1");

    return response.getSpecificElementValue("TransactionId");
  }

  private void runIpnWorker() throws Exception {
    SshUtil sshUtil = new SshUtil();

    sshUtil.runIpnWorker();
  }

  private void runCatchUpScript(String pmId) {
    SshUtil sshUtil = new SshUtil();

    String webRoot = ResourceFactory.getInstance()
        .getProperty(ResourceFactory.WEB_APP_ROOT_DIR_KEY);

    sshUtil.runCommand("php " + webRoot + "resman/send_notifications.php --pm_id=" + pmId);
  }

  /**
   * Wait for the given check image notification job to be completed.
   *
   * @param transId transId to wait for
   * @param imageTypes array of image types to wait for
   * @throws Exception if something dun broke
   */
  public static void waitForCheckImageNotificationJobProcessed(String transId, String[] imageTypes) throws Exception {
    waitForCheckImageNotificationJobProcessed(transId, imageTypes, false);
  }

  /**
   * Wait for the given check image notification job to be completed - with the option to force a failure.
   *
   * @param transId transId to wait for
   * @param imageTypes array of image types to wait for
   * @param forceFail set to true to force the notification integration to fail
   * @throws Exception if something dun broke
   */
  public static void waitForCheckImageNotificationJobProcessed(
      String transId, String[] imageTypes, boolean forceFail
  ) throws Exception {
    SshUtil sshUtil = new SshUtil();

    HashMap<String, String> envVars = new HashMap<>();

    if (forceFail) {
      envVars.put(SERVER_DOMAIN_NAME_ENV_VAR_NAME,
          new String(new char[500]).replace("\0", "A") + ".paylease.local");
    }

    sshUtil.setEnvVars(envVars);

    sshUtil.runQueueWorkerUntilComplete("check_image_notification");

    for (String imageType : imageTypes) {
      sshUtil.waitForLogMessage(
          "Completed check image notification for transaction '" + transId + "-" + imageType + "'.",
          PATH_TO_LOG_FILES + "*_check_image_notification_consumer.log"
      );
    }
  }

  private void assertNumTrans(String transId, int expectedNumTrans) {
    ResmanTransactionPage transactionPage = new ResmanTransactionPage();
    transactionPage.open();

    ResmanTransactionProcessingPage processingPage = transactionPage
        .getDataForTransactionId(transId);
    int numTrans = Integer.parseInt(processingPage.getResmanTransCount());

    String assertMsg = "a transaction";
    if (expectedNumTrans == 0) {
      assertMsg = "no transactions";
    }

    Assert.assertEquals(numTrans, expectedNumTrans,
        "There should be " + assertMsg + " with missing BillingAccountID");
  }

  /**
   * Assert the Resman-specific Documents/Attach request details are found in the log.
   *
   * @param transId trans id of the log request
   * @param imageType image type of the log request
   * @param propNumber property number expected in the log request
   * @throws Exception if log message not found
   */
  public static void assertRequestData(String transId, String imageType, String propNumber)
      throws Exception {
    SshUtil sshUtil = new SshUtil();
    String message = sshUtil.getStringMatchFromFile(
        "ResmanCheckImageNotification::notify attach document '" + transId + "-" + imageType
            + "' request:",
        PATH_TO_LOG_FILES + "*_check_image_notification.log"
    );

    message = message.replace("[", "").replace("]", "");

    String[] parameters = message.split(",");
    Map<String, String> parametersMap = new HashMap<>();

    for (String parameter : parameters) {
      String[] tokens = parameter.split("=", 2);
      parametersMap.put(tokens[0], tokens[1]);
    }

    Assert.assertEquals(
        parametersMap.get("PropertyID"), propNumber, "PropertyID is set to prop number"
    );
    Assert.assertEquals(
        parametersMap.get("RecordID"), transId, "RecordID is set to transaction id"
    );
    Assert.assertEquals(
        parametersMap.get("RecordType"), "ProcessorPayment",
        "RecordType is set to ProcessorPayment"
    );
    Assert.assertEquals(
        parametersMap.get("FileName"), transId + "_" + imageType + ".png",
        "FileName is set to transaction id with _" + imageType + ".png"
    );

    String splitLink[] = parametersMap.get("URL").split("=", 2);

    Assert.assertEquals(splitLink.length, 2, "URL should contain one equal sign");
    Assert.assertEquals(splitLink[0], CHECK_IMAGE_BASE_URL, "Image resource URL does not match");

    DecryptedDataPage decryptedDataPage = new DecryptedDataPage();
    decryptedDataPage.open(splitLink[1]);

    Assert.assertEquals(
        decryptedDataPage.getValue("image"), imageType, imageType + " image included in link"
    );
    Assert.assertEquals(
        decryptedDataPage.getValue("transId"), transId, "Trans ID included in link"
    );
  }

  /**
   * Assert the Resman-specific Documents/Attach response details are found in the log.
   *
   * @param transId trans id of the log request
   * @param imageType image type of the log request
   */
  public static void assertResponseData(String transId, String imageType) {
    SshUtil sshUtil = new SshUtil();

    int logMessageCount = sshUtil.getStringMatchCountFromFile(
        "ResmanCheckImageNotification::notify attach document '" + transId + "-" + imageType
            + "' response:",
        PATH_TO_LOG_FILES + "*_check_image_notification.log"
    );

    Assert.assertEquals(logMessageCount, 1, "Expect log entry to be present");
  }

  private void assertCheckImageNotificationJobStarted(String transId) {
    SshUtil sshUtil = new SshUtil();

    int logMessageCount = sshUtil.getStringMatchCountFromFile(
        "ResmanCheckImageNotification::notify called for transaction '" + transId + "-front'.",
        PATH_TO_LOG_FILES + "*_check_image_notification.log"
    );
    Assert.assertEquals(logMessageCount, 1, "Expect log entry to be present");

    logMessageCount = sshUtil.getStringMatchCountFromFile(
        "ResmanCheckImageNotification::notify called for transaction '" + transId + "-back'.",
        PATH_TO_LOG_FILES + "*_check_image_notification.log"
    );
    Assert.assertEquals(logMessageCount, 1, "Expect log entry to be present");
  }

  private void assertNoCheckImageNotificationJobs(String transId) {
    SshUtil sshUtil = new SshUtil();

    int logMessageCount = sshUtil.getStringMatchCountFromFile(
        "Adding Gearman job: name=check_image_notification, data={\"integration_type\":\"RESMAN\",\"trans_id\":"
            + transId + ",\"image_type\":\"front\"}",
        PATH_TO_LOG_FILES + "*_check_image_notification_producer.log"
    );
    Assert.assertEquals(logMessageCount, 0, "Expect log entry not to be present");

    logMessageCount = sshUtil.getStringMatchCountFromFile(
        "Adding Gearman job: name=check_image_notification, data={\"integration_type\":\"RESMAN\",\"trans_id\":"
            + transId + ",\"image_type\":\"back\"}",
        PATH_TO_LOG_FILES + "*_check_image_notification_producer.log"
    );
    Assert.assertEquals(logMessageCount, 0, "Expect log entry not to be present");

    logMessageCount = sshUtil.getStringMatchCountFromFile(
        "ResmanCheckImageNotification::notify called for transaction '" + transId + "-front'.",
        PATH_TO_LOG_FILES + "*_check_image_notification.log"
    );
    Assert.assertEquals(logMessageCount, 0, "Expect log entry not to be present");

    logMessageCount = sshUtil.getStringMatchCountFromFile(
        "ResmanCheckImageNotification::notify called for transaction '" + transId + "-back'.",
        PATH_TO_LOG_FILES + "*_check_image_notification.log"
    );
    Assert.assertEquals(logMessageCount, 0, "Expect log entry not to be present");
  }

  /**
   * Find the retry entry based on transId and imageType.
   *
   * @param transId the transaction id
   * @param imageType theee image type
   * @return the retry entry
   */
  public static CheckImageNotificationRetry getRetryEntry(String transId, String imageType) {
    DataBaseConnector dataBaseConnector = new DataBaseConnector();
    dataBaseConnector.createConnection();
    Connection connection = dataBaseConnector.getConnection();

    CheckImageNotificationRetryDao cinRetryDao = new CheckImageNotificationRetryDao();
    return cinRetryDao.findByTransIdAndImageType(connection, Integer.parseInt(transId), imageType);
  }

  private void assertRetryEntry(String transId, String imageType) {
    CheckImageNotificationRetry cinRetry = getRetryEntry(transId, imageType);

    Assert.assertNotNull(cinRetry, "Retry entry should be inserted on failure");

    Assert.assertEquals(cinRetry.getIntegrationType(), "RESMAN",
        "Resman should be the integration type");
    Assert.assertEquals(cinRetry.getAttempts(), 1, "There should be 1 attempt");
    Assert.assertNull(cinRetry.getDeletedAt(), "There should be no deleted_at date");

    SshUtil sshUtil = new SshUtil();

    int logMessageCount = sshUtil.getStringMatchCountFromFile(
        "CheckImageNotification::processResponse added retry entry for transaction '" + transId + "-"
            + imageType + "'",
        PATH_TO_LOG_FILES + "*_check_image_notification.log"
    );
    Assert.assertEquals(logMessageCount, 1, "Expect log entry to be present");
  }
}