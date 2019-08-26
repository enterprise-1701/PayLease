package com.paylease.app.qa.functional.tests.admin;

import com.paylease.app.qa.framework.DataBaseConnector;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.admin.ApiPage;
import com.paylease.app.qa.framework.pages.admin.LoginPageAdmin;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.utility.database.client.dao.GatewayFeesDao;
import com.paylease.app.qa.framework.utility.database.client.dto.GatewayFees;
import com.paylease.app.qa.framework.utility.listeners.Retry;
import java.sql.Connection;
import java.util.ArrayList;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ApiTest extends ScriptBase {

  private static final String REGION = "admin";
  private static final String FEATURE = "api";

  @Test
  public void nsfCheckFeeLabelAndTextTest() {
    Logger.info("Validate PM Incurred Check Scan NSF Fee label and text field");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc4091");
    testSetupPage.open();

    final String pmId = testSetupPage.getString("pmId");
    final String gatewayId = testSetupPage.getString("gatewayId");

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    ApiPage apiPage = new ApiPage(pmId);
    apiPage.open();

    Assert.assertEquals(apiPage.getNsfCheckFeeLabel(), "PM Incurred Check Scan NSF Fee",
        "NSF Check Fee label should be present");
    Assert.assertTrue(apiPage.isNsfCheckFeeInputPresent(),
        "NSF Check Fee input field should be present");

    DataBaseConnector dataBaseConnector = new DataBaseConnector();
    dataBaseConnector.createConnection();
    Connection connection = dataBaseConnector.getConnection();

    GatewayFeesDao gatewayFeesDao = new GatewayFeesDao();
    ArrayList<GatewayFees> list = gatewayFeesDao.findById(connection, Long.parseLong(gatewayId), 1);

    Assert.assertNotNull(list, "There should be a list with the GatewayFees");

    GatewayFees gatewayFees = list.get(0);

    Assert.assertEquals(
        Double.parseDouble(apiPage.getNsfCheckFeeInputValue()),
        gatewayFees.getNsfChkscanFee(),
        "The NSF Check Fee on the page should match in the gateway_fees table");
  }

  @Test(dataProvider = "nsfCheckFeeProvider", retryAnalyzer = Retry.class)
  public void nsfCheckFeeSavesInDatabaseTest(
      String testCaseDesc, String nsfCheckFeeToEnter, double expectedSavedCheckFee
  ) {
    Logger.info(
        "validate the updated NSF Check Fee saves to the gateway_fees table with " + testCaseDesc);

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc4092");
    testSetupPage.open();

    final String pmId = testSetupPage.getString("pmId");
    final String gatewayId = testSetupPage.getString("gatewayId");

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    ApiPage apiPage = new ApiPage(pmId);
    apiPage.open();
    apiPage.enterNsfCheckFeeValue(nsfCheckFeeToEnter);
    apiPage.clickSaveSettingsBtn();

    DataBaseConnector dataBaseConnector = new DataBaseConnector();
    dataBaseConnector.createConnection();
    Connection connection = dataBaseConnector.getConnection();

    GatewayFeesDao gatewayFeesDao = new GatewayFeesDao();
    ArrayList<GatewayFees> list = gatewayFeesDao.findById(connection, Long.parseLong(gatewayId), 1);

    Assert.assertNotNull(list, "There should be a list with the GatewayFees");

    GatewayFees gatewayFees = list.get(0);

    Assert.assertEquals(gatewayFees.getNsfChkscanFee(), expectedSavedCheckFee,
        "Value entered for NSF Check Fee should be saved in the database");
  }

  @Test
  public void achCheckFeeLabelTest() {
    Logger.info("Validate Check Scan Fee is labeled 'Check Scan Fee'");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc4106");
    testSetupPage.open();

    final String pmId = testSetupPage.getString("pmId");

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    ApiPage apiPage = new ApiPage(pmId);
    apiPage.open();

    Assert.assertEquals(apiPage.getCheckFeeLabel(), "Check Scan Fee",
        "Check Scan Fee label should be present");
  }

  @DataProvider(name = "nsfCheckFeeProvider", parallel = true)
  private Object[][] nsfCheckFeeProvider() {
    return new Object[][]{
        {"valid input", "5.00", 5},
        {"invalid input", "a", 0},
    };
  }
}
