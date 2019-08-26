package com.paylease.app.qa.functional.tests.admin;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ResourceFactory;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.admin.PmCcGatewayPage;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.utility.envwriter.EnvWriterUtil;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VantivOnboardingTest extends ScriptBase {

  private static final String REGION = "admin";
  private static final String FEATURE = "vantivOnboarding";
  private static final String CURRENCY_CODE_USD = "USD";
  private static final String BASE_URL = ResourceFactory.getInstance().getProperty(ResourceFactory.STUB_HOST) + "/stub-web-service/stub?service=vantiv&response_name=";
  private static final String URL_VARIABLE_NAME = "LITLE_ONBOARDING_URL";
  private static final String ENV_FILENAME = "litle_onboarding.env";

  // This is the ID for Vantiv.  Note that Vantiv is Litle is WorldPay (10/15/2018)
  private static final String VANTIV = "2";

  @BeforeClass(alwaysRun = true)
  public void setUpStub() {
    EnvWriterUtil writerUtil = new EnvWriterUtil();
    writerUtil.replaceEnvFileValue(ENV_FILENAME, URL_VARIABLE_NAME, BASE_URL);
  }

  @AfterClass(alwaysRun = true)
  public void breakDown() {
    EnvWriterUtil writerUtil = new EnvWriterUtil();
    writerUtil.replaceEnvFileValue(ENV_FILENAME, null, null);
  }

  @Test
  public void tc4299() {
    Logger.info(
        "Verify that admin sees the listeners button when vantiv onboarding sends back "
            + "listeners code.");

    PmCcGatewayPage gatewayPage = submitVantivForm("tc4299");
    gatewayPage.clickSpecifiedButton(PmCcGatewayPage.CREATE_LEGAL_ENTITY_SUBMIT);

    Assert.assertFalse(gatewayPage.areButtonsVisible());
  }

  @Test
  public void tc4300() {
    Logger
        .info("Verify that admin sees the retrieve legal entity button when put in manual review");

    PmCcGatewayPage gatewayPage = submitVantivForm("tc4300");
    boolean actual = gatewayPage
        .isOnlyThisButtonVisible(PmCcGatewayPage.RETRIEVE_LEGAL_ENTITY_SUBMIT);

    Assert.assertTrue(actual);
  }

  @Test
  public void tc4301() {
    Logger.info("Verify that admin sees the listeners legal entity button when put in listeners "
        + "status");

    PmCcGatewayPage gatewayPage = submitVantivForm("tc4301");
    boolean actual = gatewayPage
        .isOnlyThisButtonVisible(PmCcGatewayPage.RETRY_LEGAL_ENTITY_SUBMIT);

    Assert.assertTrue(actual);
  }

  @Test
  public void tc6008() {
    Logger.info(
        "Verify that when a PM is in manual review status, and the admin clicks the Retrieve Legal"
            + " Entity Button and the response is now approved, that the buttons are no longer"
            + " visible.");

    PmCcGatewayPage gatewayPage = submitVantivForm("tc4300");
    gatewayPage.clickSpecifiedButton(PmCcGatewayPage.RETRIEVE_LEGAL_ENTITY_SUBMIT);

    String response = gatewayPage.getLitleResponseDescription();
    Assert.assertEquals("Approved", response);
    Assert.assertFalse(gatewayPage.areButtonsVisible());
  }

  @Test
  public void tc6009() {
    Logger.info(
        "Verify that when a PM is in manual review status, and the admin clicks the Retrieve Legal"
            + " Entity Button and the response is now listeners, that the only button visible is "
            + "the listeners legal entity button.");

    PmCcGatewayPage gatewayPage = submitVantivForm("tc4303");
    gatewayPage.clickSpecifiedButton(PmCcGatewayPage.RETRIEVE_LEGAL_ENTITY_SUBMIT);

    String response = gatewayPage.getLitleResponseDescription();
    Assert.assertEquals(response, "Retry");
    Assert
        .assertTrue(gatewayPage.isOnlyThisButtonVisible(PmCcGatewayPage.RETRY_LEGAL_ENTITY_SUBMIT));
  }

  @Test
  public void tc6010() {
    Logger.info(
        "If the legal entity is declined, we do not show any buttons.");

    PmCcGatewayPage gatewayPage = submitVantivForm("tc4304");

    String response = gatewayPage.getLitleResponseDescription();
    Assert.assertEquals(response, "Declined");
    Assert.assertFalse(gatewayPage.areButtonsVisible());
  }

  //----------------------------------------Test Methods--------------------------------------------

  private PmCcGatewayPage submitVantivForm(String testCase) {
    TestSetupPage testPage = new TestSetupPage(REGION, FEATURE, testCase);
    testPage.open();

    String pmId = testPage.getString("pmId");

    PmCcGatewayPage pmCcGatewayPage = new PmCcGatewayPage(pmId);
    pmCcGatewayPage.open();

    pmCcGatewayPage.selectUsdProcessor(VANTIV);
    pmCcGatewayPage.clickSettingsModal(CURRENCY_CODE_USD);
    pmCcGatewayPage.clickLitleModal();

    return pmCcGatewayPage;
  }
}
