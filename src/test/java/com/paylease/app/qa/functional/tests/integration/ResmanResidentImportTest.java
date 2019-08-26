package com.paylease.app.qa.functional.tests.integration;

import com.paylease.app.qa.framework.DataBaseConnector;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ResourceFactory;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.utility.database.client.dao.ResidentUserTypeDao;
import com.paylease.app.qa.framework.utility.database.client.dao.UserDao;
import com.paylease.app.qa.framework.utility.database.client.dto.ResidentUserType;
import com.paylease.app.qa.framework.utility.database.client.dto.User;
import com.paylease.app.qa.framework.utility.listeners.Retry;
import com.paylease.app.qa.framework.utility.sshtool.SshUtil;
import java.sql.Connection;
import java.util.ArrayList;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ResmanResidentImportTest extends ScriptBase {

  private static final String REGION = "integration";
  private static final String FEATURE = "ResmanResidentImport";

  @Test(dataProvider = "provideStatus", retryAnalyzer = Retry.class)
  public void statusImported(String testCaseId, String status) {
    Logger.info("Verify resident_user_type.integration_status is set to '" + status
        + "' for this resident");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCaseId);
    testSetupPage.open();

    final String pmId = testSetupPage.getString("pmId");
    final String integrationUserId = testSetupPage.getString("integrationUserId");

    SshUtil sshUtil = new SshUtil();
    String command =
        "php " + ResourceFactory.getInstance().getProperty(ResourceFactory.WEB_APP_ROOT_DIR_KEY)
            + "resman/2-import-residents.php " + pmId;
    sshUtil.runCommand(command);

    DataBaseConnector dataBaseConnector = new DataBaseConnector();
    dataBaseConnector.createConnection();
    Connection connection = dataBaseConnector.getConnection();

    UserDao userDao = new UserDao();
    User user = userDao.findByPmIdAndIntegrationUserId(connection, pmId, integrationUserId);

    Assert.assertNotNull(user, "User should have been imported");

    ResidentUserTypeDao residentUserTypeDao = new ResidentUserTypeDao();
    ArrayList<ResidentUserType> residentUserTypeList = residentUserTypeDao
        .findById(connection, Long.parseLong(user.getUserId()), 1);

    Assert.assertNotNull(residentUserTypeList, "Resident user type should be found");

    Assert.assertEquals(residentUserTypeList.get(0).getIntegrationStatus(), status,
        "Resman status saved to resident_user_type table on import");
  }

  @DataProvider(name = "provideStatus", parallel = true)
  private Object[][] provideStatus() {
    return new Object[][]{
        //testCaseId, status
        {"tc4138", "Pending"},
        {"tc4139", "Pending Transfer"},
        {"tc4140", "Approved"},
        {"tc4141", "Denied"},
        {"tc4142", "Cancelled"},
        {"tc4143", "Current"},
        {"tc4144", "Pending Renewal"},
        {"tc4145", "Under Eviction"},
        {"tc4146", "Former"},
        {"tc4147", "Evicted"},
        {"tc4148", "Prospect"},
        {"tc4149", "Non-Resident"},
        {"tc4150", "WOIT Account"},
    };
  }
}
