package com.paylease.app.qa.functional.tests.backend;

import static com.paylease.app.qa.framework.AppConstant.PATH_TO_LOG_FILES;
import static com.paylease.app.qa.functional.tests.integration.ResmanIpnTest.assertRequestData;
import static com.paylease.app.qa.functional.tests.integration.ResmanIpnTest.assertResponseData;
import static com.paylease.app.qa.functional.tests.integration.ResmanIpnTest.getRetryEntry;
import static com.paylease.app.qa.functional.tests.integration.ResmanIpnTest.waitForCheckImageNotificationJobProcessed;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.UtilityManager;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.utility.database.client.dto.CheckImageNotificationRetry;
import com.paylease.app.qa.framework.utility.sshtool.SshUtil;
import java.util.Date;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RdcCheckImageNotificationRetryTest extends ScriptBase {

  public static final String REGION = "backend";
  public static final String FEATURE = "rdcCheckImageNotificationRetry";

  private static final String SCRIPT_NAME = "check_image_notification_retry";

  @Test(groups = {"manual", "queue"})
  public void logRequestResponseForEachResmanRetry() throws Exception {
    Logger.info(
        "Validate the log message contains the request and response variables for each image.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc4317");
    testSetupPage.open();

    final String propNumber = testSetupPage.getString("propNumber");
    final String transId = testSetupPage.getString("transId");
    final String imageType = testSetupPage.getString("imageType");

    runProcessingScript();

    waitForCheckImageNotificationJobProcessed(transId, new String[]{imageType});

    assertRequestData(transId, imageType, propNumber);
    assertResponseData(transId, imageType);
  }

  @Test(groups = {"manual", "queue", "resman"})
  public void removedDbRetryEntryOnSuccess() throws Exception {
    Logger.info("Validate the database entry has been soft deleted and logged.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc4317");
    testSetupPage.open();

    final String transId = testSetupPage.getString("transId");
    final String imageType = testSetupPage.getString("imageType");

    runProcessingScript();

    waitForCheckImageNotificationJobProcessed(transId, new String[]{imageType});

    CheckImageNotificationRetry cinRetry = getRetryEntry(transId, imageType);

    Assert.assertNotNull(cinRetry, "Retry entry should be present on success but soft deleted");
    Assert.assertNotNull(cinRetry.getDeletedAt(), "The deleted_at date should be set");

    SshUtil sshUtil = new SshUtil();

    int logMessageCount = sshUtil.getStringMatchCountFromFile(
        "CheckImageNotification::processResponse removed retry entry for transaction '" + transId
            + "-" + imageType + "'", PATH_TO_LOG_FILES + "*_check_image_notification.log"
    );
    Assert.assertEquals(logMessageCount, 1, "Expect log entry to be present");
  }

  @Test(groups = {"manual", "queue", "resman"})
  public void updateDbRetryEntryOnFailure() throws Exception {
    Logger.info("Validate the database entry attempts has been incremented and logged.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc4317");
    testSetupPage.open();

    final String transId = testSetupPage.getString("transId");
    final String imageType = testSetupPage.getString("imageType");
    final String attemptCount = testSetupPage.getString("attempts");

    runProcessingScript();

    waitForCheckImageNotificationJobProcessed(transId, new String[]{imageType}, true);

    CheckImageNotificationRetry cinRetry = getRetryEntry(transId, imageType);

    Assert.assertNotNull(cinRetry, "Retry entry should be present");
    Assert.assertEquals(cinRetry.getAttempts(), Integer.parseInt(attemptCount) + 1,
        "Attempts should be incremented");
    Assert.assertNull(cinRetry.getDeletedAt(), "The deleted_at date should be null");

    SshUtil sshUtil = new SshUtil();

    int logMessageCount = sshUtil.getStringMatchCountFromFile(
        "CheckImageNotification::processResponse updated attempts on retry entry for transaction '"
            + transId + "-" + imageType + "'",
        PATH_TO_LOG_FILES + "*_check_image_notification.log"
    );
    Assert.assertEquals(logMessageCount, 1, "Expect log entry to be present");
  }


  @Test(groups = {"manual", "queue", "resman"})
  public void removeExpiredRetryEntry() {
    Logger.info("Validate that the database entry has been soft deleted, no job created, logged");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc4323");
    testSetupPage.open();

    final String integrationType = testSetupPage.getString("integrationType");
    final String transId = testSetupPage.getString("transId");
    final String imageType = testSetupPage.getString("imageType");
    final String expireDays = testSetupPage.getString("expireDays");

    UtilityManager utilityManager = new UtilityManager();

    final String expirationDate = utilityManager.dateToString(utilityManager
        .addDays(new Date(), 0 - Integer.parseInt(expireDays)), "yyyy-MM-dd");

    String output = runProcessingScript();

    CheckImageNotificationRetry cinRetry = getRetryEntry(transId, imageType);
    Assert.assertNotNull(cinRetry.getDeletedAt(), "The deleted_at date should be set");

    SshUtil sshUtil = new SshUtil();

    int logMessageCount = sshUtil.getStringMatchCountFromFile(
        "Adding Gearman job: name=check_image_notification, data={\"integration_type\":\""
            + integrationType + "\",\"trans_id\":" + transId + ",\"image_type\":\"" + imageType
            + "\"}",
        PATH_TO_LOG_FILES + "*_check_image_notification_producer.log"
    );
    Assert.assertEquals(logMessageCount, 0, "Expect log entry not to be present");

    Assert.assertTrue(
        utilityManager.doesLogContainMessage(
            "Deleted all entries created before " + expirationDate, output
        ),
        "Expect log entry not to be present"
    );
  }

  private String runProcessingScript() {
    SshUtil sshUtil = new SshUtil();

    return sshUtil.runBatchScript(SCRIPT_NAME);
  }
}
