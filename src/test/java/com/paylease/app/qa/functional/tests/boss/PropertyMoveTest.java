package com.paylease.app.qa.functional.tests.boss;

import com.paylease.app.qa.framework.AppConstant;
import com.paylease.app.qa.framework.DataHelper;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ResourceFactory;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.components.AppLogEntry;
import com.paylease.app.qa.framework.pages.admin.LoginPageAdmin;
import com.paylease.app.qa.framework.pages.advancedadmin.PropertyMoveFromPm;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.utility.sshtool.SshUtil;
import java.util.HashMap;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class PropertyMoveTest extends ScriptBase {

  public static final String REGION = "boss";
  public static final String FEATURE = "propertyMove";

  private static final String JIRA_NUM = "TEST-1234";
  private static final String JIRA_NOTES = "This is an automation test for property move.";
  private static final String END_QUEUE_LOG_MESSAGE = "Job to move property, {propId}, has completed.";
  private static final String QUEUE_WORKER_NAME = "property_move";
  private static final String REQUEST_LOG_MESSAGE = "Boomi request";
  private static final String RESPONSE_LOG_MESSAGE = "Boomi response";
  private static final long SUCCESS_STATUS_CODE = 201;

  @Test(groups = {"boss", "manual", "queue"})
  public void movePropertyRequestSentToBoomi() throws Exception {
    Logger.info("To validate that moving a property from one pm to other in advanced admin sends a "
        + "moveProperty call to Boomi");

    final SshUtil sshUtil = new SshUtil();

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc01");
    testSetupPage.open();

    final String apiEnvVarName = testSetupPage.getString("apiEnvVarName");
    final String fromPmId = testSetupPage.getString("fromPmId");
    final String toPmId = testSetupPage.getString("toPmId");
    final String propId = testSetupPage.getString("propId");

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    PropertyMoveFromPm propMove = new PropertyMoveFromPm(fromPmId);
    propMove.open();

    // New pm with property should only have one property on the account
    // No need to pass in the prop id
    propMove.movePropFromOldPmToNewPm(toPmId, JIRA_NUM, JIRA_NOTES);

    ResourceFactory resFactory = ResourceFactory.getInstance();
    String fakeApiUrl = resFactory.getProperty(ResourceFactory.STUB_HOST)
        + "/stub-web-service/stub?service=boomi&response_name=";

    DataHelper dataHelper = new DataHelper();
    String testId = dataHelper.getReferenceId();

    String fileName = runMovePropertyQueueWorker(sshUtil, testId, apiEnvVarName, fakeApiUrl);

    waitUntilPropMoveIsComplete(sshUtil, propId, fileName);

    String requestLogEntry = sshUtil
        .getStringMatchFromFile(REQUEST_LOG_MESSAGE, fileName, 0);
    AppLogEntry requestAppLog = new AppLogEntry(requestLogEntry);
    assertRequestMessage(requestAppLog, propId, fakeApiUrl);

    String responseLogEntry = sshUtil
        .getStringMatchFromFile(RESPONSE_LOG_MESSAGE, fileName, 0);
    AppLogEntry responseAppLog = new AppLogEntry(responseLogEntry);
    assertResponseMessage(responseAppLog, SUCCESS_STATUS_CODE);
  }

  @Test(groups = {"boss", "manual", "queue"})
  public void noRequestIsSentToBoomiWhenErrorInMoving() throws Exception {
    Logger.info("To validate that 'moveProperty' call is not made to Boomi when there is an error "
        + "to move a property in advanced admin");

    final SshUtil sshUtil = new SshUtil();

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc02");
    testSetupPage.open();

    final String fromPmId = testSetupPage.getString("fromPmId");

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    PropertyMoveFromPm propMove = new PropertyMoveFromPm(fromPmId);
    propMove.open();

    String invalidPmId = "sdfq3w5";
    propMove.movePropFromOldPmToNewPm(invalidPmId, JIRA_NUM, JIRA_NOTES);

    Assert.assertEquals(sshUtil.getNumberOfJobsForWorker("boss_" + QUEUE_WORKER_NAME), 0,
        "No job should have been added because of invalid new pm id");
  }

  private String runMovePropertyQueueWorker(
      SshUtil sshUtil, String testId, String apiEnvVarName, String url
  ) throws Exception {

    String logPath =
        ResourceFactory.getInstance().getProperty(ResourceFactory.WEB_APP_ROOT_DIR_KEY)
            + "../logs/application/" + testId;

    HashMap<String, String> envVars = new HashMap<>();
    envVars.put(apiEnvVarName, "\"" + url + "\"");
    envVars.put(AppConstant.APP_LOG_PATH_ENV_VARNAME, logPath);

    sshUtil.setEnvVars(envVars);

    sshUtil.runQueueWorkerUntilComplete(
        "Boss/" + QUEUE_WORKER_NAME,
        "boss_" + QUEUE_WORKER_NAME,
        false
    );

    return logPath + "/default.log";
  }

  private void waitUntilPropMoveIsComplete(SshUtil sshUtil, String propId, String logFileName) {
    sshUtil.waitForLogMessage(END_QUEUE_LOG_MESSAGE.replace("{propId}", propId), logFileName);
  }

  private void assertRequestMessage(AppLogEntry appLogEntry, String propId, String boomiUrl) {
    SoftAssert softAssert = new SoftAssert();

    softAssert.assertEquals(appLogEntry.getLogLevel(), "INFO",
        "Log level for the request should have been info.");

    JSONObject logContext = appLogEntry.getLogContext();

    softAssert.assertEquals(logContext.get("method"), "post",
        "Method should have been post.");
    softAssert.assertEquals(logContext.get("url"), boomiUrl + "property_move",
        "The url should have been included in the log context.");

    JSONObject headers = (JSONObject) logContext.get("headers");
    if (null == headers) {
      softAssert.fail("Headers not included in Log Context");
    } else {
      softAssert.assertEquals((String) headers.get("Content-Type"), "application/json",
          "Sending JSON request");
    }

    JSONObject body = (JSONObject) logContext.get("body");
    if (null == body) {
      softAssert.fail("Body not included in Log Context");
    } else {
      long actualPropId = (Long) body.get("prop_id");
      softAssert.assertEquals(actualPropId, Long.parseLong(propId),
          "Prop id should have been in the request body.");
    }

    softAssert.assertAll();
  }

  private void assertResponseMessage(AppLogEntry appLogEntry, long statusCode) {
    SoftAssert softAssert = new SoftAssert();

    softAssert.assertEquals(appLogEntry.getLogLevel(), "INFO",
        "Log level for the response should have been info.");

    JSONObject logContext = appLogEntry.getLogContext();
    softAssert.assertEquals((long) logContext.get("status_code"), statusCode,
        "Response status code is in log context");

    softAssert.assertAll();
  }
}
