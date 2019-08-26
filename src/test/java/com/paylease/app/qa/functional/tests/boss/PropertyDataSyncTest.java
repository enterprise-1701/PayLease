package com.paylease.app.qa.functional.tests.boss;

import com.paylease.app.qa.AppLogTester;
import com.paylease.app.qa.framework.AppConstant;
import com.paylease.app.qa.framework.DataHelper;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ResourceFactory;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.components.AppLogEntry;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.utility.listeners.Retry;
import com.paylease.app.qa.framework.utility.sshtool.SshUtil;
import com.paylease.app.qa.testbase.dataproviders.PropertyDataSyncDataProvider;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

@SuppressWarnings("unchecked")
public class PropertyDataSyncTest extends ScriptBase {

  public static final String REGION = "boss";
  public static final String FEATURE = "propDataSync";

  private static final String QUEUE_WORKER_NAME = "prop_data_sync";

  private static final String BEGIN_QUEUE_LOG_MESSAGE_ID = "prop-data-sync-start-message";
  private static final String END_QUEUE_LOG_MESSAGE_ID = "prop-data-sync-end-message";
  private static final String SKIPPED_PROPS_LOG_MESSAGE_ID = "prop-data-sync-skipped-props";
  private static final String SKIPPED_PM_LOG_MESSAGE_ID = "prop-data-sync-skipped-pm";

  private static final String CUST_CODE_FIXED_AMOUNT = "FIXED_AMOUNT";
  private static final String CUST_CODE_LOCK_EVERY_AMOUNT = "PM_LOCK_EVERY_AMOUNT";
  private static final String CUST_CODE_DISALLOW_PRE_PAYMENTS = "DISALLOW_PRE_PAYMENTS";
  private static final String[] PROPERTY_CUSTOMIZATION_CODES = new String[]{
      CUST_CODE_FIXED_AMOUNT,
      CUST_CODE_LOCK_EVERY_AMOUNT,
      CUST_CODE_DISALLOW_PRE_PAYMENTS,
  };

  private static final String INCUR_TYPE_PM = "pm";
  private static final String INCUR_TYPE_RESIDENT = "resident";

  private static final String FEE_TYPE_FIXED = "fixed";
  private static final String FEE_TYPE_PERCENT = "percent";

  private static final String BOOMI_REQUEST_LOG_MESSAGE = "Boomi request";

  @Test(groups = {"boss", "manual", "queue"})
  public void scriptSendsRequestForSingleProp() throws Exception {
    Logger.info(
        "To validate the property sync script sends a request to Boomi with a single active property");

    DataHelper dataHelper = new DataHelper();
    String testId = dataHelper.getReferenceId();

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();

    final String apiEnvVarName = testSetupPage.getString("apiEnvVarName");
    final String propId = testSetupPage.getString("propId");
    final String logSubsystem = testSetupPage.getString("logSubsystem");

    ResourceFactory resFactory = ResourceFactory.getInstance();
    String fakeApiUrl = resFactory.getProperty(ResourceFactory.STUB_HOST)
        + "/stub-web-service/stub?service=boomi&response_name=";

    String logFilePath = getLogFilePath(testId);
    SshUtil sshUtil = getSshUtil(logFilePath, apiEnvVarName, fakeApiUrl);
    runPropDataSyncScript(sshUtil, propId);

    runPropDataSyncQueueWorker(sshUtil);

    String logFileName = logFilePath + "/default.log";

    waitUntilSyncForPropIsComplete(sshUtil, logFileName);

    String initialLogMsg = sshUtil
        .getStringMatchFromFile(BEGIN_QUEUE_LOG_MESSAGE_ID, logFileName, 0);
    assertConsumerInitialLogMessagePresent(initialLogMsg, propId, logSubsystem);

    String boomiRequest = sshUtil
        .getStringMatchFromFile(getRegexForPropertyRequest(propId), logFileName, 0);
    assertBoomiRequestLogMessage(testSetupPage, boomiRequest, fakeApiUrl, propId);

    String terminatingLogMsg = sshUtil
        .getStringMatchFromFile(END_QUEUE_LOG_MESSAGE_ID, logFileName, 0);
    assertConsumerEndLogMessagePresent(terminatingLogMsg, propId, logSubsystem);
  }

  @Test(groups = {"boss", "manual", "queue"})
  public void scriptSendsRequestForAllActivePropertiesForPm() throws Exception {
    Logger.info(
        "To validate the property sync script sends a request to Boomi with all active properties for a single active PM");

    DataHelper dataHelper = new DataHelper();
    String testId = dataHelper.getReferenceId();

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc2");
    testSetupPage.open();

    final String apiEnvVarName = testSetupPage.getString("apiEnvVarName");
    final String pmId = testSetupPage.getString("pmId");
    final List<HashMap<String, Object>> activeProps = testSetupPage.getTable("activeProps");

    ResourceFactory resFactory = ResourceFactory.getInstance();
    String fakeApiUrl = resFactory.getProperty(ResourceFactory.STUB_HOST)
        + "/stub-web-service/stub?service=boomi&response_name=";

    String logFilePath = getLogFilePath(testId);
    SshUtil sshUtil = getSshUtil(logFilePath, apiEnvVarName, fakeApiUrl);
    runPropDataSyncScriptForPm(sshUtil, pmId);

    runPropDataSyncQueueWorker(sshUtil);

    String logFileName = logFilePath + "/default.log";

    waitUntilSyncForPropIsComplete(sshUtil, logFileName);

    for (HashMap<String, Object> activePropMap : activeProps) {
      String propId = (String) activePropMap.get("propId");
      String boomiRequest = sshUtil
          .getStringMatchFromFile(getRegexForPropertyRequest(propId),
              logFileName, 0);
      assertBasicBoomiPropertyRequest(boomiRequest, propId);
    }
  }

  @Test(groups = {"boss", "manual", "queue"})
  public void scriptDoesNotSendInactiveProperty() throws Exception {
    Logger.info(
        "To validate the property sync script does not send a request to Boomi when given an inactive property");

    DataHelper dataHelper = new DataHelper();
    String testId = dataHelper.getReferenceId();

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc4");
    testSetupPage.open();

    final String apiEnvVarName = testSetupPage.getString("apiEnvVarName");
    final String propId = testSetupPage.getString("propId");
    final String logSubsystem = testSetupPage.getString("logSubsystem");

    ResourceFactory resFactory = ResourceFactory.getInstance();
    String fakeApiUrl = resFactory.getProperty(ResourceFactory.STUB_HOST)
        + "/stub-web-service/stub?service=boomi&response_name=";

    String logFilePath = getLogFilePath(testId);

    SshUtil sshUtil = getSshUtil(logFilePath, apiEnvVarName, fakeApiUrl);
    runPropDataSyncScript(sshUtil, propId);

    runPropDataSyncQueueWorker(sshUtil);

    String logFileName = logFilePath + "/default.log";

    waitUntilSyncForPropIsComplete(sshUtil, logFileName);

    Assert.assertEquals(sshUtil.getStringMatchCountFromFile(BOOMI_REQUEST_LOG_MESSAGE, logFileName),
        0,
        "Request log entry should not be present.");

    String skippedPropLogMsg = sshUtil.getStringMatchFromFile(SKIPPED_PROPS_LOG_MESSAGE_ID,
        logFileName, 0);
    assertSkippedPropsLogMessagePresent(skippedPropLogMsg, propId, logSubsystem);
  }

  @Test(groups = {"boss", "manual", "queue"})
  public void scriptDoesNotSendPropertiesForInactivePm() throws Exception {
    Logger.info(
        "To validate the property sync script does not send a request to Boomi when given an inactive PM");

    DataHelper dataHelper = new DataHelper();
    String testId = dataHelper.getReferenceId();

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc5");
    testSetupPage.open();

    final String apiEnvVarName = testSetupPage.getString("apiEnvVarName");
    final String pmId = testSetupPage.getString("pmId");
    final String logSubsystem = testSetupPage.getString("logSubsystem");

    String logFilePath = getLogFilePath(testId);
    SshUtil sshUtil = getSshUtil(logFilePath, apiEnvVarName, null);

    runPropDataSyncScriptForPm(sshUtil, pmId);

    String logFileName = logFilePath + "/default.log";

    String skippedPmLogMsg = sshUtil.getStringMatchFromFile(SKIPPED_PM_LOG_MESSAGE_ID,
        logFileName, 0);
    assertSkippedPmLogMessagePresent(skippedPmLogMsg, pmId, logSubsystem);

    Assert.assertEquals(sshUtil.getStringMatchCountFromFile(BOOMI_REQUEST_LOG_MESSAGE, logFileName),
        0,
        "Request log entry should not be present.");
  }

  @Test(groups = {"boss", "manual", "queue"})
  public void propSyncPayloadHasGapiPricingWhenPropertyUsesGapi() throws Exception {
    Logger.info("Verify that gapi pricing is reported when a property uses gapi");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6");
    testSetupPage.open();

    String boomiRequest = processProperty(testSetupPage);
    assertBoomiRequestLogMessageHasGapiPricing(testSetupPage, boomiRequest);
  }

  @Test(
      groups = {"boss", "manual", "queue"},
      dataProviderClass = PropertyDataSyncDataProvider.class,
      dataProvider = "provideNoGapi",
      retryAnalyzer = Retry.class
  )
  public void propSyncPayloadDoesNotHaveGapiPricingWhenPropertyDoesNotUseGapi(String testCase,
      String objective) throws Exception {
    Logger.info(objective);

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();

    String boomiRequest = processProperty(testSetupPage);
    assertBoomiRequestLogMessageDoesNotHaveGapiPricing(boomiRequest);
  }

  @Test(groups = {"boss", "manual", "queue"})
  public void propSyncPayloadDoesNotHaveFeeStructureWhenPropertyUsesRentManager() throws Exception {
    Logger.info("Verify that non gapi fees are not reported when a Gapi Pm uses Rent Manager");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc8");
    testSetupPage.open();

    String boomiRequest = processProperty(testSetupPage);
    assertBoomiRequestLogMessageDoesNotHaveWebPricing(boomiRequest);
  }

  @Test(groups = {"boss", "manual", "queue"})
  public void propSyncPayloadReportsClassicFeesWhenPmDoesNotUseTieredFees() throws Exception {
    Logger.info("Verify that classic fees are reported when tiered fee structure is disabled");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc9");
    testSetupPage.open();

    String boomiRequest = processProperty(testSetupPage);
    assertBoomiRequestLogMessageHasClassicPricing(testSetupPage, boomiRequest);
  }

  @Test(
      groups = {"boss", "manual", "queue"},
      dataProviderClass = PropertyDataSyncDataProvider.class,
      dataProvider = "provideFlexible",
      retryAnalyzer = Retry.class
  )
  public void propSyncPayloadReportsFlexibleFeesWhenPmDoesUseTieredFees(String testCase,
      String objective) throws Exception {
    Logger.info(objective);

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();

    String boomiRequest = processProperty(testSetupPage);
    assertBoomiRequestLogMessageHasFlexiblePricing(testSetupPage, boomiRequest);
  }

  private void runPropDataSyncScript(SshUtil sshUtil, String propId) {
    sshUtil.runBatchScriptWithArgs("boss/prop_data_sync", "--prop_id=" + propId);
  }

  private void runPropDataSyncScriptForPm(SshUtil sshUtil, String pmId) {
    sshUtil.runBatchScriptWithArgs("boss/prop_data_sync", "--pm_id=" + pmId);
  }

  private String getLogFilePath(String testId) {
    return ResourceFactory.getInstance().getProperty(ResourceFactory.WEB_APP_ROOT_DIR_KEY)
        + "../logs/application/" + testId;
  }

  private SshUtil getSshUtil(String logFilePath, String apiEnvVarName, String url) {
    SshUtil sshUtil = new SshUtil();

    HashMap<String, String> envVars = new HashMap<>();
    envVars.put(apiEnvVarName, "\"" + url + "\"");
    envVars.put(AppConstant.APP_LOG_PATH_ENV_VARNAME, logFilePath);

    sshUtil.setEnvVars(envVars);

    return sshUtil;
  }

  private void runPropDataSyncQueueWorker(SshUtil sshUtil) throws Exception {
    sshUtil
        .runQueueWorkerUntilComplete("Boss/" + QUEUE_WORKER_NAME, "boss_property_data_sync", false);
  }

  private void waitUntilSyncForPropIsComplete(SshUtil sshUtil, String logFileName) {
    sshUtil.waitForLogMessage(END_QUEUE_LOG_MESSAGE_ID, logFileName);
  }

  private void assertConsumerInitialLogMessagePresent(String logMessage, String propId,
      String logSubsystem)
      throws Exception {
    String message = "Starting to sync Prop data to Boomi for Prop IDs: {prop_ids}"
        .replace("{prop_ids}", propId);

    assertConsumerLogMessagePresent(logMessage, propId, message, BEGIN_QUEUE_LOG_MESSAGE_ID,
        logSubsystem);
  }

  private void assertConsumerEndLogMessagePresent(String logMessage, String propId,
      String logSubsystem) throws Exception {
    String message = "Job to sync Property Data to Boomi for Prop IDs: {prop_ids} has completed."
        .replace("{prop_ids}", propId);

    assertConsumerLogMessagePresent(logMessage, propId, message, END_QUEUE_LOG_MESSAGE_ID,
        logSubsystem);
  }

  private void assertConsumerLogMessagePresent(String logMessage, String propId, String message,
      String logId, String logSubsystem) throws Exception {
    final AppLogTester appLogTester = new AppLogTester();

    final AppLogEntry jobFinishedLogEntry = new AppLogEntry(logMessage);

    JSONArray expectPmidsValue = new JSONArray();
    expectPmidsValue.add(Long.parseLong(propId));
    JSONObject expectedLogContext = new JSONObject();
    expectedLogContext.put("prop_ids", expectPmidsValue);
    expectedLogContext.put("subsystem", logSubsystem);
    expectedLogContext.put("process_checkpoint", logId);

    appLogTester.assertLogEntry(jobFinishedLogEntry, AppLogTester.LOG_LEVEL_INFO, message,
        expectedLogContext);
  }

  private void assertBoomiRequestLogMessage(
      TestSetupPage testSetupPage, String logEntryAsString, String boomiUrl, String propId
  ) throws Exception {
    final String pmId = testSetupPage.getString("pmId");
    final String propName = testSetupPage.getString("propName");

    final String addrFirstLine = testSetupPage.getString("addrFirstLine");
    final String addrCity = testSetupPage.getString("addrCity");
    final String addrState = testSetupPage.getString("addrState");
    final String addrZip = testSetupPage.getString("addrZip");
    final String addrCountry = testSetupPage.getString("addrCountry");

    final Long units = Long.parseLong(testSetupPage.getString("units"));

    final boolean propertyHasBilling = testSetupPage.getFlag("propertyHasBilling");
    final boolean propertyHasUem = testSetupPage.getFlag("propertyHasUem");
    final boolean productsEnabledAch = !testSetupPage.getFlag("achDisabled");
    final boolean productsEnabledCc = !testSetupPage.getFlag("ccDisabled");
    final boolean productsEnabledCheckScanning = testSetupPage
        .getFlag("productsEnabledCheckScanning");

    final List<HashMap<String, Object>> customizations = testSetupPage.getTable("customizations");

    final String exceptionNote = testSetupPage.getString("exceptionNote");
    final String delinquentNote = testSetupPage.getString("delinquentNote");

    final String propertyCode = testSetupPage.getString("propertyCode");

    final boolean custBilling = testSetupPage.getFlag("custBilling");
    final boolean custUem = testSetupPage.getFlag("custUem");
    final boolean custVcr = testSetupPage.getFlag("custVcr");

    final boolean productsEnabledBilling = propertyHasBilling && custBilling;
    final boolean productsEnabledUem = propertyHasUem && custUem;
    final boolean productsEnabledVcr = propertyHasUem && custVcr;

    final Map<String, Boolean> custProps = new HashMap<>();
    for (String custPropCode : PROPERTY_CUSTOMIZATION_CODES) {
      custProps.put(custPropCode, testSetupPage.getFlag("custProp_" + custPropCode));
    }

    final AppLogTester appLogTester = new AppLogTester();

    final AppLogEntry logEntry = new AppLogEntry(logEntryAsString);

    JSONObject request = new JSONObject();
    request.put("method", "post");
    request.put("url", boomiUrl + "property_data_sync");

    JSONObject headers = new JSONObject();
    headers.put("Accept", "application/json");
    headers.put("Content-Type", "application/json");
    request.put("headers", headers);

    JSONObject property = new JSONObject();
    property.put("id", Long.parseLong(propId));
    property.put("pm_id", Long.parseLong(pmId));

    property.put("name", propName);

    JSONObject address = new JSONObject();
    address.put("address", addrFirstLine);
    address.put("city", addrCity);
    address.put("state", addrState);
    address.put("zip", addrZip);
    address.put("country", addrCountry);
    property.put("address", address);

    property.put("units", units);

    JSONObject productsEnabled = new JSONObject();
    productsEnabled.put("billing", productsEnabledBilling);
    productsEnabled.put("uem", productsEnabledUem);
    productsEnabled.put("vcr", productsEnabledVcr);
    productsEnabled.put("payments_ach", productsEnabledAch);
    productsEnabled.put("payments_cc", productsEnabledCc);
    productsEnabled.put("payments_checkscanning", productsEnabledCheckScanning);
    property.put("products_enabled", productsEnabled);

    JSONObject customizationsEnabled = new JSONObject();
    for (HashMap<String, Object> custMap : customizations) {
      String code = (String) custMap.get("code");
      boolean enabled = (boolean) custMap.get("enabled");
      if (custProps.containsKey(code)) {
        enabled = enabled && custProps.get(code);
      }
      customizationsEnabled.put(code, enabled);
    }
    property.put("customizations", customizationsEnabled);

    JSONObject accountNotes = new JSONObject();
    accountNotes.put("exception", exceptionNote);
    accountNotes.put("delinquent", delinquentNote);
    property.put("account_notes", accountNotes);

    property.put("property_code", propertyCode);

    JSONArray propsArray = new JSONArray();
    propsArray.add(property);

    JSONObject body = new JSONObject();
    body.put("properties", propsArray);

    request.put("body", body);

    appLogTester
        .assertLogEntry(logEntry, AppLogTester.LOG_LEVEL_INFO, BOOMI_REQUEST_LOG_MESSAGE, request);
  }

  private void assertBoomiRequestLogMessageDoesNotHaveGapiPricing(String logEntryAsString)
      throws Exception {
    JSONObject pricing = new JSONObject();
    pricing.put("gapi", null);

    JSONObject property = new JSONObject();
    property.put("pricing", pricing);

    JSONArray properties = new JSONArray();
    properties.add(property);

    JSONObject body = new JSONObject();
    body.put("properties", properties);

    JSONObject request = new JSONObject();
    request.put("body", body);

    final AppLogTester appLogTester = new AppLogTester();

    final AppLogEntry logEntry = new AppLogEntry(logEntryAsString);

    appLogTester
        .assertLogEntry(logEntry, AppLogTester.LOG_LEVEL_INFO, BOOMI_REQUEST_LOG_MESSAGE, request);
  }

  private void assertBoomiRequestLogMessageHasGapiPricing(
      TestSetupPage testSetupPage, String logEntryAsString
  ) throws Exception {

    final double achFeeAmount = Double.parseDouble(testSetupPage.getString("achFee"));
    final boolean pmIncursAch = testSetupPage.getFlag("pmIncursAch");
    final double mcFeeAmount = Double.parseDouble(testSetupPage.getString("mcFee"));
    final boolean pmIncursCc = testSetupPage.getFlag("pmIncursCc");

    final AppLogTester appLogTester = new AppLogTester();

    final AppLogEntry logEntry = new AppLogEntry(logEntryAsString);

    JSONObject achFee = new JSONObject();
    appLogTester.putNumericInJsonObject(achFee, "amount", achFeeAmount);
    achFee.put("incur", (pmIncursAch) ? INCUR_TYPE_PM : INCUR_TYPE_RESIDENT);
    achFee.put("type", FEE_TYPE_FIXED);

    JSONObject ccFee = new JSONObject();
    appLogTester.putNumericInJsonObject(ccFee, "amount", mcFeeAmount);
    ccFee.put("incur", (pmIncursCc) ? INCUR_TYPE_PM : INCUR_TYPE_RESIDENT);
    ccFee.put("type", FEE_TYPE_PERCENT);

    JSONObject gapiPricing = new JSONObject();
    gapiPricing.put("ach", achFee);
    gapiPricing.put("cc", ccFee);

    JSONObject pricing = new JSONObject();
    pricing.put("gapi", gapiPricing);

    JSONObject property = new JSONObject();
    property.put("pricing", pricing);

    JSONArray properties = new JSONArray();
    properties.add(property);

    JSONObject body = new JSONObject();
    body.put("properties", properties);

    JSONObject request = new JSONObject();
    request.put("body", body);

    appLogTester
        .assertLogEntry(logEntry, AppLogTester.LOG_LEVEL_INFO, BOOMI_REQUEST_LOG_MESSAGE, request);
  }

  private void assertBoomiRequestLogMessageHasClassicPricing(
      TestSetupPage testSetupPage, String logEntryAsString
  ) throws Exception {
    final boolean passTransFee = testSetupPage.getFlag("passTransFee");
    final double transFee = Double.parseDouble(testSetupPage.getString("transFee"));
    final boolean ccPassTransFee = testSetupPage.getFlag("ccPassTransFee");
    final double ccFee = Double.parseDouble(testSetupPage.getString("ccFee"));
    final double ccTaxFee = Double.parseDouble(testSetupPage.getString("ccTaxFee"));

    final AppLogTester appLogTester = new AppLogTester();

    final AppLogEntry logEntry = new AppLogEntry(logEntryAsString);

    final double classicAchFeeAmount = transFee;
    final String classicAchFeeIncur = (passTransFee) ? INCUR_TYPE_PM : INCUR_TYPE_RESIDENT;
    final String classicAchFeeType = FEE_TYPE_FIXED;

    final double classicCcFeeAmount = (ccFee > 0.0) ? ccFee : ccTaxFee;
    final String classicCcFeeIncur = (ccPassTransFee) ? INCUR_TYPE_PM : INCUR_TYPE_RESIDENT;
    final String classicCcFeeType = (ccFee > 0.0) ? FEE_TYPE_FIXED : FEE_TYPE_PERCENT;

    JSONObject achFee = new JSONObject();
    appLogTester.putNumericInJsonObject(achFee, "amount", classicAchFeeAmount);
    achFee.put("incur", classicAchFeeIncur);
    achFee.put("type", classicAchFeeType);

    JSONObject ccFeeObj = new JSONObject();
    appLogTester.putNumericInJsonObject(ccFeeObj, "amount", classicCcFeeAmount);
    ccFeeObj.put("incur", classicCcFeeIncur);
    ccFeeObj.put("type", classicCcFeeType);

    JSONObject webFees = new JSONObject();
    webFees.put("ach", achFee);
    webFees.put("cc", ccFeeObj);
    webFees.put("debit", null);

    JSONObject pricing = new JSONObject();
    pricing.put("web", webFees);

    JSONObject property = new JSONObject();
    property.put("pricing", pricing);

    JSONArray properties = new JSONArray();
    properties.add(property);

    JSONObject body = new JSONObject();
    body.put("properties", properties);

    JSONObject request = new JSONObject();
    request.put("body", body);

    appLogTester
        .assertLogEntry(logEntry, AppLogTester.LOG_LEVEL_INFO, BOOMI_REQUEST_LOG_MESSAGE, request);
  }

  private void assertBoomiRequestLogMessageDoesNotHaveWebPricing(String logEntryAsString)
      throws Exception {
    final AppLogTester appLogTester = new AppLogTester();

    final AppLogEntry logEntry = new AppLogEntry(logEntryAsString);

    JSONObject pricing = new JSONObject();
    pricing.put("web", null);

    JSONObject property = new JSONObject();
    property.put("pricing", pricing);

    JSONArray properties = new JSONArray();
    properties.add(property);

    JSONObject body = new JSONObject();
    body.put("properties", properties);

    JSONObject request = new JSONObject();
    request.put("body", body);

    appLogTester
        .assertLogEntry(logEntry, AppLogTester.LOG_LEVEL_INFO, BOOMI_REQUEST_LOG_MESSAGE, request);
  }

  private void assertBoomiRequestLogMessageHasFlexiblePricing(TestSetupPage testSetupPage,
      String logEntryAsString) throws Exception {
    final AppLogTester appLogTester = new AppLogTester();

    final AppLogEntry logEntry = new AppLogEntry(logEntryAsString);

    final double achAmount = Double.parseDouble(testSetupPage.getString("achAmount"));
    final String achIncur = testSetupPage.getString("achIncur");
    final String achType = testSetupPage.getString("achType");

    final double ccAmount = Double.parseDouble(testSetupPage.getString("ccAmount"));
    final String ccIncur = testSetupPage.getString("ccIncur");
    final String ccType = testSetupPage.getString("ccType");

    final double debitAmount = Double.parseDouble(testSetupPage.getString("debitAmount"));
    final String debitIncur = testSetupPage.getString("debitIncur");
    final String debitType = testSetupPage.getString("debitType");

    JSONObject achFee = new JSONObject();
    appLogTester.putNumericInJsonObject(achFee, "amount", achAmount);
    achFee.put("incur", achIncur);
    achFee.put("type", achType);

    JSONObject ccFeeObj = new JSONObject();
    appLogTester.putNumericInJsonObject(ccFeeObj, "amount", ccAmount);
    ccFeeObj.put("incur", ccIncur);
    ccFeeObj.put("type", ccType);

    JSONObject debitFeeObj = new JSONObject();
    appLogTester.putNumericInJsonObject(debitFeeObj, "amount", debitAmount);
    debitFeeObj.put("incur", debitIncur);
    debitFeeObj.put("type", debitType);

    JSONObject webFees = new JSONObject();
    webFees.put("ach", achFee);
    webFees.put("cc", ccFeeObj);
    webFees.put("debit", debitFeeObj);

    JSONObject pricing = new JSONObject();
    pricing.put("web", webFees);

    JSONObject property = new JSONObject();
    property.put("pricing", pricing);

    JSONArray properties = new JSONArray();
    properties.add(property);

    JSONObject body = new JSONObject();
    body.put("properties", properties);

    JSONObject request = new JSONObject();
    request.put("body", body);

    appLogTester
        .assertLogEntry(logEntry, AppLogTester.LOG_LEVEL_INFO, BOOMI_REQUEST_LOG_MESSAGE, request);
  }

  private void assertSkippedPropsLogMessagePresent(
      String logMessage, String propId, String logSubsystem
  ) throws Exception {
    final AppLogTester appLogTester = new AppLogTester();

    final AppLogEntry logEntry = new AppLogEntry(logMessage);

    final String message =
        "Some requested properties for Boomi Data Sync are not active and will not be sent";

    JSONArray expectPropIdsValue = new JSONArray();
    expectPropIdsValue.add(Long.parseLong(propId));
    JSONObject expectedLogContext = new JSONObject();
    expectedLogContext.put("prop_ids", expectPropIdsValue);
    expectedLogContext.put("subsystem", logSubsystem);
    expectedLogContext.put("process_checkpoint", SKIPPED_PROPS_LOG_MESSAGE_ID);

    appLogTester
        .assertLogEntry(logEntry, AppLogTester.LOG_LEVEL_WARNING, message, expectedLogContext);
    appLogTester.assertLogContextKeyNotPresent(logEntry, "exception");
  }

  private void assertSkippedPmLogMessagePresent(String logMessage, String pmId, String logSubsystem)
      throws Exception {
    final AppLogTester appLogTester = new AppLogTester();

    final AppLogEntry logEntry = new AppLogEntry(logMessage);

    final String message =
        "Requested PM is not active and property data will not be synced";

    JSONObject expectedLogContext = new JSONObject();
    expectedLogContext.put("pm_id", Long.parseLong(pmId));
    expectedLogContext.put("subsystem", logSubsystem);
    expectedLogContext.put("process_checkpoint", SKIPPED_PM_LOG_MESSAGE_ID);

    appLogTester
        .assertLogEntry(logEntry, AppLogTester.LOG_LEVEL_ERROR, message, expectedLogContext);
    appLogTester.assertLogContextKeyNotPresent(logEntry, "exception");
  }

  private void assertBasicBoomiPropertyRequest(String logMessage, String propId)
      throws Exception {
    JSONObject property = new JSONObject();
    property.put("id", Long.parseLong(propId));

    JSONArray propsArray = new JSONArray();
    propsArray.add(property);

    JSONObject body = new JSONObject();
    body.put("properties", propsArray);

    JSONObject request = new JSONObject();
    request.put("body", body);

    assertBoomiRequestForProperty(logMessage, request);
  }

  private void assertBoomiRequestForProperty(String logMessage, JSONObject request)
      throws Exception {
    final AppLogTester appLogTester = new AppLogTester();

    final AppLogEntry logEntry = new AppLogEntry(logMessage);

    appLogTester
        .assertLogEntry(logEntry, AppLogTester.LOG_LEVEL_INFO, BOOMI_REQUEST_LOG_MESSAGE, request);
  }

  private String getRegexForPropertyRequest(String propId) {
    return "Boomi request.*\\\"id\\\":" + propId;
  }

  private String processProperty(TestSetupPage testSetupPage) throws Exception {
    final String apiEnvVarName = testSetupPage.getString("apiEnvVarName");
    final String propId = testSetupPage.getString("propId");

    DataHelper dataHelper = new DataHelper();
    String testId = dataHelper.getReferenceId();

    ResourceFactory resFactory = ResourceFactory.getInstance();
    String fakeApiUrl = resFactory.getProperty(ResourceFactory.STUB_HOST)
        + "/stub-web-service/stub?service=boomi&response_name=";

    String logFilePath = getLogFilePath(testId);
    SshUtil sshUtil = getSshUtil(logFilePath, apiEnvVarName, fakeApiUrl);
    runPropDataSyncScript(sshUtil, propId);

    runPropDataSyncQueueWorker(sshUtil);

    String logFileName = logFilePath + "/default.log";

    waitUntilSyncForPropIsComplete(sshUtil, logFileName);

    return sshUtil.getStringMatchFromFile(getRegexForPropertyRequest(propId), logFileName, 0);
  }
}
