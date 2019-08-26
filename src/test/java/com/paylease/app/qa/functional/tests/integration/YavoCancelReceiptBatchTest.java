package com.paylease.app.qa.functional.tests.integration;

import com.paylease.app.qa.framework.DataBaseConnector;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.utility.database.client.dao.YavoLoggingDao;
import com.paylease.app.qa.framework.utility.database.client.dto.YavoLogging;
import com.paylease.app.qa.framework.utility.sshtool.SshUtil;
import java.sql.Connection;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;

public class YavoCancelReceiptBatchTest extends ScriptBase {

  private static final String REGION = "Integration";
  private static final String FEATURE = "YavoCancelReceiptBatch";
  private static final String CANCEL_RECEIPT_BATCH_METHOD = "cancelReceiptBatch";
  private static final String REVIEW_RECEIPT_BATCH_METHOD = "reviewReceiptBatch";
  private static final String CANCEL_INCORRECTLY_CALLED = "Cancel Receipt Batch was called when "
      + "it should not have been.";
  private static final String CANCEL_NOT_CALLED = "Cancel Receipt Batch should have been called "
      + "but was not.";
  private static final String AUTOMATED_BATCH_CLEANUP_SCRIPT = "automatedBatchCleanup";
  private static final String CANCEL_ZERO_BATCHES_SCRIPT = "cancel-zero-batches";
  private static final String YAVO_DIRECTORY = "yavo/";
  private static final String YAVO_TOOLS_DIRECTORY = "yavo/tools/";

  @Test
  public void tc6564() {
    Logger
        .info("Stub service: test running the automatedBatchCleanup script for a file that contains"
            + " transactions  and is correctly formatted. Assert that the batch is not cancelled.");
    boolean isCancelCalled = cancelReceiptBatchTestSetup("tc6564",
        AUTOMATED_BATCH_CLEANUP_SCRIPT, YAVO_DIRECTORY, 0);

    Assert.assertTrue(isCancelCalled, CANCEL_INCORRECTLY_CALLED);
  }

  @Test
  public void tc6566() {
    Logger
        .info("Stub service: test running the automatedBatchCleanup script for a file that does not"
            + " contain transactions and is correctly formatted. Assert that the batch is cancelled.");
    boolean isCancelCalled = cancelReceiptBatchTestSetup("tc6566",
        AUTOMATED_BATCH_CLEANUP_SCRIPT, YAVO_DIRECTORY, 2);

    Assert.assertTrue(isCancelCalled, CANCEL_NOT_CALLED);
  }

  @Test
  public void tc6629() {
    Logger.info("Stub service: test running the automatedBatchCleanup script for a file that is"
        + " incorrectly formatted. Assert that the batch is not cancelled.");
    boolean isCancelCalled = cancelReceiptBatchTestSetup("tc6629",
        AUTOMATED_BATCH_CLEANUP_SCRIPT, YAVO_DIRECTORY, 0);

    Assert.assertTrue(isCancelCalled, CANCEL_INCORRECTLY_CALLED);
  }

  @Test
  public void tc6632() {
    Logger.info("Stub service: test running the automatedBatchCleanup script for a file that is"
        + " completely empty (no response). Assert that the batch is not cancelled.");
    boolean isCancelCalled = cancelReceiptBatchTestSetup("tc6632",
        AUTOMATED_BATCH_CLEANUP_SCRIPT, YAVO_DIRECTORY, 0);

    Assert.assertTrue(isCancelCalled, CANCEL_INCORRECTLY_CALLED);
  }

  @Test
  public void tc6635() {
    Logger.info("Stub service: test running the cancel-zero-batches script for a file that contains"
        + " transactions and is correctly formatted. Assert that the batch is not cancelled.");
    boolean isCancelCalled = cancelReceiptBatchTestSetup("tc6564",
        CANCEL_ZERO_BATCHES_SCRIPT, YAVO_TOOLS_DIRECTORY, 0);

    Assert.assertTrue(isCancelCalled, CANCEL_INCORRECTLY_CALLED);
  }

  @Test
  public void tc6636() {
    Logger.info("Stub service: test running the cancel-zero-batches script for a file that does not"
        + " contain transactions and is correctly formatted. Assert that the batch is cancelled.");
    boolean isCancelCalled = cancelReceiptBatchTestSetup("tc6566",
        CANCEL_ZERO_BATCHES_SCRIPT, YAVO_TOOLS_DIRECTORY, 2);

    Assert.assertTrue(isCancelCalled, CANCEL_NOT_CALLED);
  }

  @Test
  public void tc6633() {
    Logger.info("Stub service: test running the cancel-zero-batches script for a file that is"
        + " incorrectly formatted. Assert that the batch is not cancelled.");
    boolean isCancelCalled = cancelReceiptBatchTestSetup("tc6629",
        CANCEL_ZERO_BATCHES_SCRIPT, YAVO_TOOLS_DIRECTORY, 0);

    Assert.assertTrue(isCancelCalled, CANCEL_INCORRECTLY_CALLED);
  }

  @Test
  public void tc6634() {
    Logger.info("Stub service: test running the cancel-zero-batches script for a file that is"
        + " completely empty (no response). Assert that the batch is not cancelled.");
    boolean isCancelCalled = cancelReceiptBatchTestSetup("tc6632",
        CANCEL_ZERO_BATCHES_SCRIPT, YAVO_TOOLS_DIRECTORY, 0);

    Assert.assertTrue(isCancelCalled, CANCEL_INCORRECTLY_CALLED);
  }

  @Test(groups = {"manual"})
  public void tc6655() {
    Logger.info("Yardi: test running the automatedBatchCleanup script for a file that contains"
        + " transactions and is correctly formatted. Assert that the batch is not cancelled.");
    boolean isCancelCalled = cancelReceiptBatchTestSetup("tc6655",
        AUTOMATED_BATCH_CLEANUP_SCRIPT, YAVO_DIRECTORY, 0);

    Assert.assertTrue(isCancelCalled, CANCEL_INCORRECTLY_CALLED);
  }

  @Test(groups = {"manual"})
  public void tc6656() {
    Logger.info("Yardi: test running the automatedBatchCleanup script for a file that does not"
        + " contain transactions and is correctly formatted. Assert that the batch is cancelled.");
    boolean isCancelCalled = cancelReceiptBatchTestSetup("tc6656",
        AUTOMATED_BATCH_CLEANUP_SCRIPT, YAVO_DIRECTORY, 2);

    Assert.assertTrue(isCancelCalled, CANCEL_NOT_CALLED);
  }

  @Test(groups = {"manual"})
  public void tc6659() {
    Logger.info("Yardi: test running the cancel-zero-batches script for a file that contains"
        + " transactions and is correctly formatted. Assert that the batch is not cancelled.");
    boolean isCancelCalled = cancelReceiptBatchTestSetup("tc6655",
        CANCEL_ZERO_BATCHES_SCRIPT, YAVO_TOOLS_DIRECTORY, 0);

    Assert.assertTrue(isCancelCalled, CANCEL_INCORRECTLY_CALLED);
  }

  @Test(groups = {"manual"})
  public void tc6660() {
    Logger
        .info("Yardi: test running the cancel-zero-batches script for a file that does not contain"
            + " transactions and is correctly formatted. Assert that the batch is cancelled.");
    boolean isCancelCalled = cancelReceiptBatchTestSetup("tc6656",
        CANCEL_ZERO_BATCHES_SCRIPT, YAVO_TOOLS_DIRECTORY, 2);

    Assert.assertTrue(isCancelCalled, CANCEL_NOT_CALLED);
  }

  private boolean cancelReceiptBatchTestSetup(String testCaseNumber, String script,
      String directory, int expectedCancelCount) {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCaseNumber);
    testSetupPage.open();
    String pmId = testSetupPage.getString("pm_id");
    int batchId1 = Integer.valueOf(testSetupPage.getString("batch_id_1"));
    int batchId2 = Integer.valueOf(testSetupPage.getString("batch_id_2"));

    SshUtil sshUtil = new SshUtil();
    sshUtil.runScriptByDirectoryName(script, pmId, directory);

    int reviewCounter = 0;
    int cancelCounter = 0;

    if (isYavoMethodCalled(REVIEW_RECEIPT_BATCH_METHOD, pmId, batchId1)) {
      reviewCounter++;
    }

    if (isYavoMethodCalled(REVIEW_RECEIPT_BATCH_METHOD, pmId, batchId2)) {
      reviewCounter++;
    }

    if (isYavoMethodCalled(CANCEL_RECEIPT_BATCH_METHOD, pmId, batchId1)) {
      cancelCounter++;
    }

    if (isYavoMethodCalled(CANCEL_RECEIPT_BATCH_METHOD, pmId, batchId2)) {
      cancelCounter++;
    }

    return (reviewCounter == 2 && cancelCounter == expectedCancelCount);
  }

  private boolean isYavoMethodCalled(String yavoMethod, String pmId, int batchId) {

    DataBaseConnector dataBaseConnector = new DataBaseConnector();
    dataBaseConnector.createConnection();
    Connection connection = dataBaseConnector.getConnection();

    YavoLoggingDao yavoLoggingDao = new YavoLoggingDao();

    List<YavoLogging> yavoMethodLog = yavoLoggingDao
        .findByPmBatchMethod(connection, Long.valueOf(pmId), batchId, yavoMethod, 1);

    return (yavoMethodLog.size() == 1);
  }
}
