package com.paylease.app.qa.functional.tests.backend;

import static com.paylease.app.qa.framework.AppConstant.PATH_TO_LOG_FILES;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ResourceFactory;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.utility.listeners.Retry;
import com.paylease.app.qa.framework.utility.sshtool.SshUtil;
import com.paylease.app.qa.testbase.dataproviders.ProcessingRdcSendJsonFileDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProcessingRdcSendJsonFileTest extends ScriptBase {

  public static final String REGION = "backend";
  public static final String FEATURE = "rdcProcessingSendJson";

  private static final String SCRIPT_NAME = "processing_rdc_send_json_file";

  @Test(
      dataProvider = "provideNoJobs",
      dataProviderClass = ProcessingRdcSendJsonFileDataProvider.class,
      retryAnalyzer = Retry.class,
      groups = {"manual"}
  )
  public void batchFileProducesNoJob(String testCaseId, String objective) {
    Logger.info(objective);

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCaseId);
    testSetupPage.open();

    final String batchFileName = testSetupPage.getString("batchFileName");

    runBatchScript();

    assertNoJobProduced(batchFileName);
  }

  @Test(groups = {"manual"})
  public void validBatchFileProducesJob() {
    Logger.info(
        "Validate a job is created in the CHECK_IMAGE_PROCESSING queue with the path to the json file"
    );

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6560");
    testSetupPage.open();

    final String batchFileName = testSetupPage.getString("batchFileName");

    runBatchScript();

    assertJobProduced(batchFileName);
  }

  private void runBatchScript() {
    SshUtil sshUtil = new SshUtil();
    sshUtil.runBatchScript(SCRIPT_NAME);
  }

  private void assertNoJobProduced(String batchFileName) {
    assertExpectedJobCount(batchFileName, 0);
  }

  private void assertJobProduced(String batchFileName) {
    assertExpectedJobCount(batchFileName, 1);
  }

  private void assertExpectedJobCount(String batchFileName, int expectedCount) {
    SshUtil sshUtil = new SshUtil();

    String root = ResourceFactory.getInstance().getProperty(ResourceFactory.WEB_APP_ROOT_DIR_KEY);
    String filePath = root + "batches/files/rdc/x937/sent/" + batchFileName + ".json";

    filePath = filePath.replace("/", "\\\\\\\\/");

    int logMessageCount = sshUtil.getStringMatchCountFromFile(
        "Adding Gearman job: name=check_image_processing, data={\\\"file_path\\\":\\\"" + filePath
            + "\\\"}",
        PATH_TO_LOG_FILES + "*_check_image_processing_producer.log"
    );
    Assert.assertEquals(logMessageCount, expectedCount,
        "Expect " + expectedCount + " log entries to be present");
  }
}
