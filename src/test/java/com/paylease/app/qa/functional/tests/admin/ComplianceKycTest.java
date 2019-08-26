package com.paylease.app.qa.functional.tests.admin;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.admin.AdminHomePage;
import com.paylease.app.qa.framework.pages.admin.ComplianceKycPage;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ComplianceKycTest extends ScriptBase {

  private static final String REGION = "admin";
  private static final String FEATURE = "Kyc";

  //-----------------------------------TESTS--------------------------------------------------------

  @Test
  public void tc01() {
    Logger.info(
        "Verify Compliance Admin can see the Compliance KYC report when GIACT_ID_VERIFICATION_ENABLED is true");

    setUp("tc01");

    boolean isKycLinkDisplayed = isKycLinkDisplayed();
    boolean isKycReportDisplayed = isKycReportDisplayed();

    Assert.assertTrue(isKycLinkDisplayed, "KYC link should be displayed");
    Assert.assertTrue(isKycReportDisplayed, "KYC report should be displayed");

  }

  @Test
  public void tc02() {
    Logger.info(
        "Verify Compliance Admin can see the Compliance KYC report when GIACT_ID_VERIFICATION_ENABLED is false");

    setUp("tc02");

    boolean isKycLinkDisplayed = isKycLinkDisplayed();
    boolean isKycReportDisplayed = isKycReportDisplayed();

    Assert.assertTrue(isKycLinkDisplayed, "KYC link should be displayed");
    Assert.assertTrue(isKycReportDisplayed, "KYC report should be displayed");

  }

  @Test
  public void tc03() {
    Logger.info(
        "Verify Non-Compliance Admin cannot see the Compliance KYC report when GIACT_ID_VERIFICATION_ENABLED is true");

    setUp("tc03");

    boolean isKycLinkDisplayed = isKycLinkDisplayed();
    boolean isKycReportDisplayed = isKycReportDisplayed();

    Assert.assertTrue(!isKycLinkDisplayed, "KYC link should not be displayed");
    Assert.assertTrue(!isKycReportDisplayed, "KYC report should not be displayed");
  }

  @Test
  public void tc04() {
    Logger.info(
        "Verify Non-Compliance Admin cannot see the Compliance KYC report when GIACT_ID_VERIFICATION_ENABLED is false");

    setUp("tc04");

    boolean isKycLinkDisplayed = isKycLinkDisplayed();
    boolean isKycReportDisplayed = isKycReportDisplayed();

    Assert.assertTrue(!isKycLinkDisplayed, "KYC link should not be displayed");
    Assert.assertTrue(!isKycReportDisplayed, "KYC report should not be displayed");
  }

  //------------------------------------TEST METHODS------------------------------------------------

  private void setUp(String testCase) {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();
  }

  private boolean isKycLinkDisplayed() {
    AdminHomePage adminHomePage = new AdminHomePage();
    adminHomePage.open();

    return adminHomePage.isComplianceKycReportLinkVisible();
  }

  private boolean isKycReportDisplayed() {
    ComplianceKycPage complianceKycPage = new ComplianceKycPage();
    complianceKycPage.open();

    return complianceKycPage.pageIsLoaded();
  }

}
