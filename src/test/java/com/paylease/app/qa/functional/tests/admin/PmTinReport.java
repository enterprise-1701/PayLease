package com.paylease.app.qa.functional.tests.admin;

import com.paylease.app.qa.framework.ExcelUtil;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.admin.LoginPageAdmin;
import com.paylease.app.qa.framework.pages.admin.TinReportPage;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import java.io.File;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

public class PmTinReport extends ScriptBase {

  public static final String REGION = "admin";
  public static final String FEATURE = "NewTinReport";

  private static final String VALID_TIN_LENGTH_INFO = "Verify TINs with leading zeros have a length of 9 digits";
  private static final String INVALID_TIN_UPLOAD_INFO = "Verify TINs with an invalid length will not be uploaded.";
  private static final String TAX_ID_COLUMN = "Taxid";
  private static final String UPLOAD_FILE_NAME = "test_invalid_length_tin-report.xlsx";
  private static final int TAX_ID_LENGTH = 9;
  private static final String MAX_EXECUTION_TIME_OCCURED = "Skipping this test, max execution time occured.";

  @Test
  public void testNewTinReportLengthLeadingZeros() {
    Logger.info(VALID_TIN_LENGTH_INFO);

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();

    // Should include leading zeros from test up.
    String taxId = testSetupPage.getString("taxId");

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    TinReportPage tinReportPage = new TinReportPage();
    tinReportPage.open();

    if (tinReportPage.checkMaxExecutionTimeErrorExists()) {
      throw new SkipException(MAX_EXECUTION_TIME_OCCURED);
    }

    String downloadPath = setUpDownloadPath(this.getClass().getName(),
        "testNewTinReportLengthLeadingZeros");
    tinReportPage.downloadTinReport(downloadPath);
    String fileName = tinReportPage.getTinReportName(downloadPath, TinReportPage.TIN_REPORT);

    if (fileName == null) {
      Assert.fail("Unable to find Tin Report.");
    }

    String filePath = downloadPath + "/" + fileName;
    final String[][] excelReport = ExcelUtil.getExcelData(filePath, "sheet1");
    final int taxIdColumnNum = ExcelUtil.columnNum(excelReport[0], TAX_ID_COLUMN);

    // Starting from the end of the list, because the record should be towards the end of the list.
    // This will speed up processing.

    boolean foundTin = false;

    for (int i = excelReport.length - 1; i >= 0; i--) {
      if (excelReport[i][taxIdColumnNum].equals(taxId)) {
        String INVALID_LEADING_ZERO_MESSAGE = "TIN is an invalid length: ";

        Assert.assertEquals(TAX_ID_LENGTH, taxId.length(),
            INVALID_LEADING_ZERO_MESSAGE + taxId.length());

        foundTin = true;
      }
    }

    if (foundTin) {
      Assert.fail("Unable to find TIN for testing.");
    }
  }

  @Test
  public void testInvalidTinUpload() {
    Logger.info(INVALID_TIN_UPLOAD_INFO);

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    TinReportPage tinReportPage = new TinReportPage();
    tinReportPage.open();

    if (tinReportPage.checkMaxExecutionTimeErrorExists()) {
      throw new SkipException(MAX_EXECUTION_TIME_OCCURED);
    }

    File uploadFile = new File(UPLOAD_DIR_PATH + File.separator + UPLOAD_FILE_NAME);
    tinReportPage.chooseTinFile(uploadFile);
    tinReportPage.submitTinReport();

    String invalidSpanText = tinReportPage.getInvalidLengthSpan();
    String invalidLengthMessage = "Invalid length for TIN";

    if (!invalidSpanText.contains(invalidLengthMessage)) {
      Assert.fail("Failed to check for an invalid TIN length in the New Tin Report");
    }

  }
}