package com.paylease.app.qa.functional.tests.admin;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.admin.PmTabsPage;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PmEditKycTabTest extends ScriptBase {

  private static final String REGION = "admin";
  private static final String FEATURE = "PmEditKycTab";
  private static final String DRIVERS_LICENSE = "Driver's License";
  private static final String SSN_DOCUMENT = "SSN Document";

  private static final String DRIVERS_FILE_NAME = "test.pdf";
  private static final String SSN_FILE_NAME = "test.jpg";

  @Test
  public void tc4289() {
    Logger.info(
        "Verify that admin can see and download one document uploaded by the PM."
    );

    TestSetupPage testPage = new TestSetupPage(REGION, FEATURE, "tc4289");
    testPage.open();

    String pmId = testPage.getString("pmId");
    PmTabsPage pmTabsPage = new PmTabsPage(pmId);
    pmTabsPage.open();

    Assert.assertTrue(pmTabsPage.isKycTabVisible(),
        "The KYC tab was not visible in the Admin UI.");

    pmTabsPage.clickKycTab();

    WebElement documentLink = pmTabsPage.getDocumentDownload();

    String fileName = getFileNameFromWebElement(documentLink);
    Assert.assertNotEquals("", fileName, "File name was empty.");

    String downloadPath = setUpDownloadPath(this.getClass().getName(), "tc4289");
    // This performs an assertion in PageBase.java
    pmTabsPage.clickDocDownload(documentLink, downloadPath, fileName);
  }

  @Test
  public void tc4290() {
    Logger.info(
        "Verify that admin can see and download two documents uploaded by the PM."
    );

    TestSetupPage testPage = new TestSetupPage(REGION, FEATURE, "tc4290");
    testPage.open();

    String pmId = testPage.getString("pmId");
    PmTabsPage pmTabsPage = new PmTabsPage(pmId);
    pmTabsPage.open();

    Assert.assertTrue(pmTabsPage.isKycTabVisible(),
        "The KYC tab was not visible in the Admin UI.");

    pmTabsPage.clickKycTab();

    List<WebElement> documentLinks = pmTabsPage.getDocumentDownloads();

    for (WebElement documentLink : documentLinks) {

      String fileName = getFileNameFromWebElement(documentLink);
      Assert.assertNotEquals("", fileName, "File name was empty.");

      String downloadPath = setUpDownloadPath(this.getClass().getName(), "tc4290");
      // This performs an assertion in PageBase.java
      pmTabsPage.clickDocDownload(documentLink, downloadPath, fileName);
    }
  }

  //-----------------------------------------Test Method--------------------------------------------

  private String getFileNameFromWebElement(WebElement element) {
    String text = element.getText();

    switch (text) {
      case DRIVERS_LICENSE:
        return DRIVERS_FILE_NAME;

      case SSN_DOCUMENT:
        return SSN_FILE_NAME;

      default:
        return "";
    }
  }
}
