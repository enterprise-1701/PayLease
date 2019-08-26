package com.paylease.app.qa.functional.tests.boss;

import com.paylease.app.qa.AppLogTester;
import com.paylease.app.qa.framework.AppConstant;
import com.paylease.app.qa.framework.DataHelper;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ResourceFactory;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.components.AppLogEntry;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.utility.sshtool.SshUtil;
import com.paylease.app.qa.testbase.dataproviders.BossPmDataSyncDataProvider;
import java.util.Arrays;
import java.util.HashMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

@SuppressWarnings("unchecked")
public class PmDataSyncTest extends ScriptBase {

  private static final String REGION = "boss";
  private static final String FEATURE = "pmDataSync";

  private static final String QUEUE_WORKER_NAME = "pm_data_sync";

  private static final String BEGIN_QUEUE_LOG_MESSAGE =
      "Starting to sync PM data to Boomi for PM IDs: {pmId}";
  private static final String END_QUEUE_LOG_MESSAGE =
      "Job to sync PM Data to Boomi for PM IDs: {pmId} has completed.";

  private static final String BOOMI_RESPONSE_BODY = "{\n"
      + "  \"status_code\": 200,\n"
      + "  \"body\": {\n"
      + "    \"pm_id\": \"pm_id\",\n"
      + "    \"message\": \"PM Updated Successfully\"\n"
      + "  }\n"
      + "}";
  private static final long BOOMI_RESPONSE_STATUS_CODE = 201;

  @Test(groups = {"boss", "manual", "queue"})
  public void consumerSendsPmDataToBoomi() throws Exception {
    Logger.info("To validate that the consumer sends Pm data to Boomi");

    DataHelper dataHelper = new DataHelper();
    String testId = dataHelper.getReferenceId();

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();

    final String apiEnvVarName = testSetupPage.getString("apiEnvVarName");
    final String logSubsystem = testSetupPage.getString("logSubsystem");
    final String pmId = testSetupPage.getString("pmId");

    ResourceFactory resFactory = ResourceFactory.getInstance();
    String fakeApiUrl = resFactory.getProperty(ResourceFactory.STUB_HOST)
        + "/stub-web-service/stub?service=boomi&response_name=";

    runPmDataSyncScript(pmId);

    String logFileName = runPmDataSyncQueueWorker(testId, apiEnvVarName, fakeApiUrl);

    SshUtil sshUtil = new SshUtil();

    waitUntilSyncForPmIsComplete(sshUtil, pmId, logFileName);

    String initialLogMessage = sshUtil
        .getStringMatchFromFile(BEGIN_QUEUE_LOG_MESSAGE.replace("{pmId}", pmId), logFileName, 0);
    assertConsumerInitialLogMessagePresent(initialLogMessage, pmId, logSubsystem);

    String boomiRequest = sshUtil.getStringMatchFromFile("Boomi request", logFileName, 0);
    assertBoomiRequestLogMessage(testSetupPage, boomiRequest, fakeApiUrl, pmId);
  }

  @Test(
      dataProviderClass = BossPmDataSyncDataProvider.class,
      dataProvider = "provideNonActiveMasterPms",
      groups = {"boss", "manual", "queue"}
  )
  public void consumerLogsWarningWhenPmIsNotSent(String testCaseId, String objective)
      throws Exception {
    Logger.info(objective);

    DataHelper dataHelper = new DataHelper();
    String testId = dataHelper.getReferenceId();

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCaseId);
    testSetupPage.open();

    final String apiEnvVarName = testSetupPage.getString("apiEnvVarName");
    final String logSubsystem = testSetupPage.getString("logSubsystem");
    final String pmId = testSetupPage.getString("badPmId");

    ResourceFactory resFactory = ResourceFactory.getInstance();
    String fakeApiUrl = resFactory.getProperty(ResourceFactory.STUB_HOST)
        + "/stub-web-service/stub?service=boomi&response_name=";

    runPmDataSyncScript(pmId);

    String logFileName = runPmDataSyncQueueWorker(testId, apiEnvVarName, fakeApiUrl);

    SshUtil sshUtil = new SshUtil();

    waitUntilSyncForPmIsComplete(sshUtil, pmId, logFileName);

    String skippedPmLogMessage = sshUtil.getStringMatchFromFile(
        "Some requested Pm IDs for Boomi Data Sync are not active, master PMs and will not be sent",
        logFileName, 0);
    assertSkippedPmsLogMessagePresent(skippedPmLogMessage, pmId, logSubsystem);

    Assert.assertEquals(sshUtil.getStringMatchCountFromFile("Boomi request", logFileName), 0,
        "Request log entry should not be present.");

    String endLogMessage = sshUtil
        .getStringMatchFromFile(END_QUEUE_LOG_MESSAGE.replace("{pmId}", pmId), logFileName, 0);
    assertConsumerEndLogMessagePresent(endLogMessage, pmId, logSubsystem);
  }

  @Test(groups = {"boss", "manual", "queue"})
  public void responseFromBoomiIsLogged() throws Exception {
    Logger.info("To validate that we log response from Boomi");

    DataHelper dataHelper = new DataHelper();
    String testId = dataHelper.getReferenceId();

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();

    final String apiEnvVarName = testSetupPage.getString("apiEnvVarName");
    final String pmId = testSetupPage.getString("pmId");

    ResourceFactory resFactory = ResourceFactory.getInstance();
    String fakeApiUrl = resFactory.getProperty(ResourceFactory.STUB_HOST)
        + "/stub-web-service/stub?service=boomi&response_name=response.json&request=";

    runPmDataSyncScript(pmId);

    String logFileName = runPmDataSyncQueueWorker(testId, apiEnvVarName, fakeApiUrl);

    SshUtil sshUtil = new SshUtil();

    waitUntilSyncForPmIsComplete(sshUtil, pmId, logFileName);

    String boomiResponse = sshUtil.getStringMatchFromFile("Boomi response", logFileName, 0);

    assertBoomiResponseLogMessage(boomiResponse, BOOMI_RESPONSE_STATUS_CODE, BOOMI_RESPONSE_BODY);
  }

  private void runPmDataSyncScript(String pmId) {
    SshUtil sshUtil = new SshUtil();

    sshUtil.runBatchScriptWithArgs("boss/pm_data_sync", "--pm_id=" + pmId);
  }

  private String runPmDataSyncQueueWorker(String testId, String apiEnvVarName, String url)
      throws Exception {

    String logBaseDir = ResourceFactory.getInstance().getProperty(ResourceFactory.WEB_APP_ROOT_DIR_KEY)
            + "../logs/application/" + testId;

    String logFileName = logBaseDir + "/default.log";

    SshUtil sshUtil = new SshUtil();

    HashMap<String, String> envVars = new HashMap<>();
    envVars.put(apiEnvVarName, "\"" + url + "\"");
    envVars.put(AppConstant.APP_LOG_PATH_ENV_VARNAME, logBaseDir);

    sshUtil.setEnvVars(envVars);

    sshUtil.runQueueWorkerUntilComplete("Boss/" + QUEUE_WORKER_NAME, "boss_pm_data_sync", false);

    return logFileName;
  }

  private void assertConsumerInitialLogMessagePresent(String logMessage, String pmId,
      String logSubsystem)
      throws Exception {
    String message = BEGIN_QUEUE_LOG_MESSAGE.replace("{pmId}", pmId);

    assertConsumerLogMessagePresent(logMessage, pmId, message, logSubsystem);
  }

  private void assertConsumerEndLogMessagePresent(String logMessage, String pmId,
      String logSubsystem) throws Exception {
    String message = END_QUEUE_LOG_MESSAGE.replace("{pmId}", pmId);

    assertConsumerLogMessagePresent(logMessage, pmId, message, logSubsystem);
  }

  private void assertConsumerLogMessagePresent(String logMessage, String pmId, String message,
      String logSubsystem) throws Exception {
    final AppLogTester appLogTester = new AppLogTester();

    final AppLogEntry jobFinishedLogEntry = new AppLogEntry(logMessage);

    JSONArray expectPmidsValue = new JSONArray();
    expectPmidsValue.add(Long.parseLong(pmId));
    JSONObject expectedLogContext = new JSONObject();
    expectedLogContext.put("pm_ids", expectPmidsValue);
    expectedLogContext.put("subsystem", logSubsystem);

    appLogTester.assertLogEntry(jobFinishedLogEntry, AppLogTester.LOG_LEVEL_INFO, message,
        expectedLogContext);
  }

  private void assertBoomiRequestLogMessage(
      TestSetupPage testSetupPage, String logEntryAsString2, String boomiUrl, String pmId
  ) throws Exception {
    final String salesForceId = testSetupPage.getString("salesForceId");
    final String salesForceGrandparentId = testSetupPage.getString("salesForceGrandparentId");
    final String pmFirstName = testSetupPage.getString("pmFirstName");
    final String pmLastName = testSetupPage.getString("pmLastName");
    final String companyName = testSetupPage.getString("companyName");
    final String portfolioTypes = testSetupPage.getString("portfolioType");
    final String[] portfolioTypeArr = portfolioTypes.split(",");
    final String billingAddr = testSetupPage.getString("billingAddr");
    final String billingCity = testSetupPage.getString("billingCity");
    final String billingState = testSetupPage.getString("billingState");
    final String billingZip = testSetupPage.getString("billingZip");
    final double achLimit = Double.parseDouble(testSetupPage.getString("achLimit"));
    final String accountingSoftware = testSetupPage.getString("accountingSoftware");
    final String integrationType1 = testSetupPage.getString("integrationType1");
    final String integrationType2 = testSetupPage.getString("integrationType2");
    final String integrationType3 = testSetupPage.getString("integrationType3");
    final long part1Id = Long.parseLong(testSetupPage.getString("partner1Id"));
    final String part1Name = testSetupPage.getString("partner1Name");
    final long part2Id = Long.parseLong(testSetupPage.getString("partner2Id"));
    final String part2Name = testSetupPage.getString("partner2Name");
    final String exceptionNote = testSetupPage.getString("exceptionNote");
    final String delinquentNote = testSetupPage.getString("delinquentNote");
    final boolean creditReporting = testSetupPage.getFlag("creditReporting");
    final boolean hasCheckScanning = testSetupPage.getFlag("hasCheckScanning");
    final double checkScanningFee = Double.parseDouble(testSetupPage.getString("checkScanningFee"));
    final String invoiceName1 = testSetupPage.getString("invoiceName1");
    final double invoicePrice1 = Double.parseDouble(testSetupPage.getString("invoicePrice1"));
    final String invoiceName2 = testSetupPage.getString("invoiceName2");
    final double invoicePrice2 = Double.parseDouble(testSetupPage.getString("invoicePrice2"));

    final AppLogTester appLogTester = new AppLogTester();

    final AppLogEntry logEntry = new AppLogEntry(logEntryAsString2);

    JSONObject request = new JSONObject();
    request.put("method", "post");
    request.put("url", boomiUrl + "pm_data_sync");

    JSONObject headers = new JSONObject();
    headers.put("Accept", "application/json");
    headers.put("Content-Type", "application/json");
    request.put("headers", headers);

    JSONObject pm = new JSONObject();
    pm.put("pm_id", Long.parseLong(pmId));
    pm.put("salesforce_id", salesForceId);
    pm.put("salesforce_grandparent_id", salesForceGrandparentId);

    pm.put("pm_first_name", pmFirstName);
    pm.put("pm_last_name", pmLastName);
    pm.put("company_name", companyName);
    pm.put("portfolio_types", Arrays.asList(portfolioTypeArr));

    JSONObject billingAddress = new JSONObject();
    billingAddress.put("address", billingAddr);
    billingAddress.put("city", billingCity);
    billingAddress.put("state", billingState);
    billingAddress.put("zip", billingZip);
    billingAddress.put("country", "US");
    pm.put("billing_addr", billingAddress);

    appLogTester.putNumericInJsonObject(pm, "ach_limit", achLimit);

    pm.put("main_accounting_software", accountingSoftware);
    pm.put("partner_field_1", integrationType1);
    pm.put("partner_field_2", integrationType2);
    pm.put("partner_field_3", integrationType3);

    JSONArray partners = new JSONArray();
    JSONObject partner1 = new JSONObject();
    partner1.put("id", part1Id);
    partner1.put("name", part1Name);
    partners.add(partner1);

    JSONObject partner2 = new JSONObject();
    partner2.put("id", part2Id);
    partner2.put("name", part2Name);
    partners.add(partner2);

    pm.put("partners", partners);

    JSONObject pmNotes = new JSONObject();
    pmNotes.put("exception", exceptionNote);
    pmNotes.put("delinquent", delinquentNote);
    pm.put("pm_notes", pmNotes);

    pm.put("credit_reporting", creditReporting);

    JSONObject productsEnabled = new JSONObject();
    productsEnabled.put("payments", true);
    productsEnabled.put("payments_ach", true);
    productsEnabled.put("payments_cc", true);
    productsEnabled.put("check_scanning", hasCheckScanning);
    pm.put("products_enabled", productsEnabled);

    appLogTester.putNumericInJsonObject(pm, "check_scanning_fee", checkScanningFee);

    JSONArray invoices = new JSONArray();
    JSONObject invoiceItem1 = new JSONObject();
    invoiceItem1.put("name", invoiceName1);
    appLogTester.putNumericInJsonObject(invoiceItem1, "amount", invoicePrice1);
    invoices.add(invoiceItem1);

    JSONObject invoiceItem2 = new JSONObject();
    invoiceItem2.put("name", invoiceName2);
    appLogTester.putNumericInJsonObject(invoiceItem2, "amount", invoicePrice2);
    invoices.add(invoiceItem2);

    pm.put("invoices", invoices);

    JSONArray pmsArray = new JSONArray();
    pmsArray.add(pm);

    JSONObject body = new JSONObject();
    body.put("pms", pmsArray);

    request.put("body", body);

    appLogTester.assertLogEntry(logEntry, AppLogTester.LOG_LEVEL_INFO, "Boomi request", request);
  }

  private void assertSkippedPmsLogMessagePresent(
      String logMessage, String pmId, String logSubsystem
  ) throws Exception {
    final AppLogTester appLogTester = new AppLogTester();

    final AppLogEntry logEntry = new AppLogEntry(logMessage);

    final String message =
        "Some requested Pm IDs for Boomi Data Sync are not active, master PMs and will not be sent";

    JSONArray expectPmidsValue = new JSONArray();
    expectPmidsValue.add(Long.parseLong(pmId));
    JSONObject expectedLogContext = new JSONObject();
    expectedLogContext.put("pm_ids", expectPmidsValue);
    expectedLogContext.put("subsystem", logSubsystem);

    appLogTester
        .assertLogEntry(logEntry, AppLogTester.LOG_LEVEL_WARNING, message, expectedLogContext);
    appLogTester.assertLogContextKeyNotPresent(logEntry, "exception");
  }

  private void assertBoomiResponseLogMessage(
      String logMessage, long statusCode, String responseBody
  ) throws Exception {
    AppLogTester appLogTester = new AppLogTester();

    AppLogEntry logEntry = new AppLogEntry(logMessage);

    JSONObject expectedLogContext = new JSONObject();
    expectedLogContext.put("status_code", statusCode);
    expectedLogContext.put("body", responseBody);

    appLogTester.assertLogEntry(logEntry, AppLogTester.LOG_LEVEL_INFO, "Boomi response",
        expectedLogContext);
  }

  private void waitUntilSyncForPmIsComplete(SshUtil sshUtil, String pmId, String logFileName) {
    sshUtil.waitForLogMessage(END_QUEUE_LOG_MESSAGE.replace("{pmId}", pmId), logFileName);
  }
}
