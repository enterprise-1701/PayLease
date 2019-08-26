package com.paylease.app.qa.functional.tests.admin;

import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.admin.ConfigureIntegrationPage;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Class ConfigureIntegrationTest.
 */
public class ConfigureIntegrationTest extends ScriptBase {

  public static final String REGION = "admin";
  public static final String FEATURE = "configureIntegration";

  @Test
  public void ftpFieldsNotShownWhenBrickFtpCustomizationIsOn() {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc3028");
    testSetupPage.open();

    final String pmId = testSetupPage.getString("pm_id");

    ConfigureIntegrationPage integrationPage = new ConfigureIntegrationPage(pmId);
    integrationPage.open();

    integrationPage.selectIntegrationType("TOPS_ONE");

    Assert.assertFalse(integrationPage.isHasFtpVisible(),
        "Has Ftp Checkbox should not be visible for this integration software.");
    Assert.assertFalse(integrationPage.isFtpEmailVisible(),
        "Ftp Email input should not be visible for this integration software.");
    Assert.assertFalse(integrationPage.isFtpInstructionsVisible(),
        "Ftp Instructions should not be visible for this integration software.");
  }

  @Test
  public void ftpFieldsShownWhenBrickFtpCustomizationIsOn() {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc3029");
    testSetupPage.open();

    final String pmId = testSetupPage.getString("pm_id");

    ConfigureIntegrationPage integrationPage = new ConfigureIntegrationPage(pmId);
    integrationPage.open();

    integrationPage.selectIntegrationType("ASYST");

    Assert.assertTrue(integrationPage.isHasFtpVisible(),
        "Has Ftp Checkbox should be visible for this integration software.");
    Assert.assertTrue(integrationPage.isFtpEmailVisible(),
        "Ftp Email input should be visible for this integration software.");
    Assert.assertTrue(integrationPage.isFtpInstructionsVisible(),
        "Ftp Instructions should be visible for this integration software.");
  }

  @Test
  public void ftpEmailNotShownWhenBrickFtpCustomizationIsOff() {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc3034");
    testSetupPage.open();

    final String pmId = testSetupPage.getString("pm_id");

    ConfigureIntegrationPage integrationPage = new ConfigureIntegrationPage(pmId);
    integrationPage.open();

    integrationPage.selectIntegrationType("ASYST");

    Assert.assertTrue(integrationPage.isHasFtpVisible(),
        "Has Ftp Checkbox should be visible for this integration software.");
    Assert.assertFalse(integrationPage.isFtpEmailVisible(),
        "Ftp Email input should not be visible for this integration software.");
    Assert.assertFalse(integrationPage.isFtpInstructionsVisible(),
        "Ftp Instructions should not be visible for this integration software.");
  }

  @Test
  public void ftpPasswordRowNotVisibleTest() {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc3276");
    testSetupPage.open();

    final String pmId = testSetupPage.getString("pm_id");

    ConfigureIntegrationPage integrationPage = new ConfigureIntegrationPage(pmId);
    integrationPage.open();
    integrationPage.selectFtpTab();

    Assert.assertFalse(integrationPage.isPasswordPresentInFtpSettingsTable(),
        "Password should not be present");
  }

  @Test
  public void ftpPasswordRowVisibleTest() {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc3277");
    testSetupPage.open();

    final String pmId = testSetupPage.getString("pm_id");

    ConfigureIntegrationPage integrationPage = new ConfigureIntegrationPage(pmId);
    integrationPage.open();
    integrationPage.selectFtpTab();

    Assert.assertTrue(integrationPage.isPasswordPresentInFtpSettingsTable(),
        "Password should be present");
  }
}
