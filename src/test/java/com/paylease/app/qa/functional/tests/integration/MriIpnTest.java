package com.paylease.app.qa.functional.tests.integration;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.paylease.app.qa.api.tests.aapi.testcase.RdcTransactionCallback;
import com.paylease.app.qa.api.tests.gapi.testcase.AchPayment;
import com.paylease.app.qa.framework.DataBaseConnector;
import com.paylease.app.qa.framework.DataHelper;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.UtilityManager;
import com.paylease.app.qa.framework.api.Credentials;
import com.paylease.app.qa.framework.api.aapi.RdcRequest;
import com.paylease.app.qa.framework.api.gapi.GapiRequest;
import com.paylease.app.qa.framework.api.gapi.GapiResponse;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.utility.database.client.dao.MriLoggingDao;
import com.paylease.app.qa.framework.utility.database.client.dao.TransactionDao;
import com.paylease.app.qa.framework.utility.database.client.dto.MriLogging;
import com.paylease.app.qa.framework.utility.database.client.dto.Transaction;
import com.paylease.app.qa.framework.utility.sshtool.SshUtil;
import java.sql.Connection;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MriIpnTest extends ScriptBase {

  private static final String REGION = "integration";
  private static final String FEATURE = "mriIpn";

  @Test(groups = {"manual", "queue"})
  public void descriptionNotSetForSendDescCustDisabled() throws Exception {
    Logger.info(
        "Description not set for PM with MRI_NOTIFICATION_SEND_DESCRIPTION customization disabled");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc4110");
    testSetupPage.open();

    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String payeeId = testSetupPage.getString("payeeId");

    DataHelper dataHelper = new DataHelper();
    GapiRequest gapiRequest = new GapiRequest(new Credentials(gatewayId, username, password));

    AchPayment testCase = AchPayment.createValidAchPayment(
        dataHelper.getReferenceId(),
        "ACH transaction",
        null,
        payeeId
    );

    testCase.addTransaction(gapiRequest);

    GapiResponse response = gapiRequest.sendRequest();
    response.setIndex("1");

    String transId = response.getSpecificElementValue("TransactionId");

    runIpnWorker();

    JsonObject jsonObject = getLoggingRequestJson(transId);

    Assert.assertNull(jsonObject.get("Description"), "Description is not set");
  }

  @Test(groups = {"manual", "queue"})
  public void descriptionHasCheckNumberForCheck21Trans() throws Exception {
    Logger.info(
        "Description has check number for check21 transaction for PM with MRI_NOTIFICATION_SEND_DESCRIPTION customization enabled");

    DataHelper dataHelper = new DataHelper();

    final String checkNum = dataHelper.getCheckNum();

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc4111");
    testSetupPage.open();

    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String payeeId = testSetupPage.getString("payeeId");

    GapiRequest gapiRequest = new GapiRequest(new Credentials(gatewayId, username, password));

    AchPayment testCase = AchPayment.createValidCheck21(
        dataHelper.getReferenceId(),
        "Check21 transaction",
        null,
        payeeId
    ).setCheckNum(checkNum).setIncludeImages();

    testCase.addTransaction(gapiRequest);

    GapiResponse response = gapiRequest.sendRequest();
    response.setIndex("1");

    String transId = response.getSpecificElementValue("TransactionId");

    runIpnWorker();

    JsonObject jsonObject = getLoggingRequestJson(transId);

    Assert.assertEquals(jsonObject.get("Description").getAsString(), "Chk# " + checkNum,
        "Description includes check number");
  }

  @Test(groups = {"manual", "queue"})
  public void descriptionHasCheckNumberForRdcTrans() throws Exception {
    Logger.info(
        "Description has check number for RDC transaction for PM with MRI_NOTIFICATION_SEND_DESCRIPTION customization enabled");

    DataHelper dataHelper = new DataHelper();

    final String checkNum = dataHelper.getCheckNum();
    final String amount = "113.36";

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc4115");
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

    runIpnWorker();

    UtilityManager utilityManager = new UtilityManager();
    String transId = utilityManager.getLastTransIdForResident(tenantId, pmId);

    Assert.assertNotEquals(transId, "", "Transaction should be found");

    JsonObject jsonObject = getLoggingRequestJson(transId);

    Assert.assertEquals(jsonObject.get("Description").getAsString(), "Chk# " + checkNum,
        "Description includes check number");
  }

  @Test(groups = {"manual", "queue"})
  public void descriptionHasTransIdForNonCheckTrans() throws Exception {
    Logger.info(
        "Description has transaction id for non-check transaction for PM with MRI_NOTIFICATION_SEND_DESCRIPTION customization enabled");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc4114");
    testSetupPage.open();

    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String payeeId = testSetupPage.getString("payeeId");

    DataHelper dataHelper = new DataHelper();
    GapiRequest gapiRequest = new GapiRequest(new Credentials(gatewayId, username, password));

    AchPayment testCase = AchPayment.createValidAchPayment(
        dataHelper.getReferenceId(),
        "ACH transaction",
        null,
        payeeId
    );

    testCase.addTransaction(gapiRequest);

    GapiResponse response = gapiRequest.sendRequest();
    response.setIndex("1");

    String transId = response.getSpecificElementValue("TransactionId");

    runIpnWorker();

    JsonObject jsonObject = getLoggingRequestJson(transId);

    Assert.assertEquals(jsonObject.get("Description").getAsString(), "Transaction ID: " + transId,
        "Description includes transaction id");
  }

  /**
   * Get the logging request as json object.
   *
   * @param transId transaction id
   * @return JsonObject
   */
  private JsonObject getLoggingRequestJson(String transId) {
    DataBaseConnector dataBaseConnector = new DataBaseConnector();
    dataBaseConnector.createConnection();
    Connection connection = dataBaseConnector.getConnection();

    MriLoggingDao mriLoggingDao = new MriLoggingDao();
    MriLogging mriLogging = mriLoggingDao
        .findLatestByMethodAndTransId(connection, "lease", Integer.parseInt(transId));

    Assert.assertNotNull(mriLogging, "There should be a MRI logging entry");

    JsonParser jsonParser = new JsonParser();
    JsonElement jsonElement = jsonParser.parse(mriLogging.getRequestRaw());
    JsonObject jsonObject = jsonElement.getAsJsonObject();
    Logger.debug(jsonObject.toString());

    return jsonObject;
  }

  /**
   * Run the ipn worker in the background.
   */
  private void runIpnWorker() throws Exception {
    SshUtil sshUtil = new SshUtil();

    sshUtil.runIpnWorker();
  }
}
