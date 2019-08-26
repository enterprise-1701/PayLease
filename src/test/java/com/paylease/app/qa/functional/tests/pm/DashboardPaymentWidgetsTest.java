package com.paylease.app.qa.functional.tests.pm;

import static com.paylease.app.qa.framework.utility.sshtool.SshUtil.LOG_FILE_TYPE_ACCESS;
import static com.paylease.app.qa.framework.utility.sshtool.SshUtil.LOG_FILE_TYPE_ERROR;

import com.paylease.app.qa.framework.DataBaseConnector;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.components.Login;
import com.paylease.app.qa.framework.components.Login.UserType;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.pm.PmHomePage;
import com.paylease.app.qa.framework.utility.database.client.dao.UserDashboardConfigDao;
import com.paylease.app.qa.framework.utility.database.client.dto.UserDashboardConfig;
import com.paylease.app.qa.framework.utility.sshtool.SshUtil;
import com.paylease.app.qa.testbase.dataproviders.DashboardPaymentWidgetsTestDataProvider;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DashboardPaymentWidgetsTest extends ScriptBase {

  private static final String REGION = "pm";
  private static final String FEATURE = "dashboardPaymentWidgets";

  //--------------------------------BANK DETAILS TESTS----------------------------------------------

  @Test(dataProvider = "dashboardTestData", dataProviderClass = DashboardPaymentWidgetsTestDataProvider.class, groups = {
      "e2e"})
  public void pmDashboardWidgets(String testCase, String testCaseInfo) {
    Logger.info("Test case no. " + testCase + ": " + testCaseInfo);

    TestSetupPage testSetupPage = dashboardPaymentWidgetsTestSetup(testCase);
    String pmId = testSetupPage.getString("pmId");
    String pmEmail = testSetupPage.getString("pmEmail");

    //Populate user_dashboard_config table for this user, to show dashboard widgets
    DataBaseConnector connector = new DataBaseConnector();
    connector.createConnection();
    Connection connection = connector.getConnection();
    UserDashboardConfigDao dashboardConfigDao = new UserDashboardConfigDao();

    dashboardConfigDao.createDashboardConfigSetup(connection, pmId, null);

    //Get logfile contents before loading pm dashboard
    SshUtil sshUtil = new SshUtil();
    String errorLogsPreDashboard = sshUtil.readLogFile(LOG_FILE_TYPE_ERROR);
    String accessLogsPreDashboard = sshUtil.readLogFile(LOG_FILE_TYPE_ACCESS);

    Login login = new Login();
    login.logInUser(pmEmail, UserType.PM);

    verifyErrorLogs(errorLogsPreDashboard, accessLogsPreDashboard);
  }

  @Test(dataProvider = "dashboardTestData", dataProviderClass = DashboardPaymentWidgetsTestDataProvider.class, groups = {
      "e2e"})
  public void pmDashboardPaymentWidgetsOnly(String testCase, String testCaseInfo) {
    Logger.info("Test case no. " + testCase + ": " + testCaseInfo);

    TestSetupPage testSetupPage = dashboardPaymentWidgetsTestSetup(testCase);
    String pmId = testSetupPage.getString("pmId");
    String pmEmail = testSetupPage.getString("pmEmail");

    //Populate user_dashboard_config table for this user, to show dashboard widgets
    DataBaseConnector connector = new DataBaseConnector();
    connector.createConnection();
    Connection connection = connector.getConnection();
    UserDashboardConfigDao dashboardConfigDao = new UserDashboardConfigDao();

    ArrayList<String> reportTypeIdList = new ArrayList<>();
    reportTypeIdList.add("1");
    reportTypeIdList.add("2");
    reportTypeIdList.add("3");
    reportTypeIdList.add("4");

    dashboardConfigDao.createDashboardConfigSetup(connection, pmId, reportTypeIdList);

    //Get logfile contents before loading pm dashboard
    SshUtil sshUtil = new SshUtil();
    String errorLogsPreDashboard = sshUtil.readLogFile(LOG_FILE_TYPE_ERROR);
    String accessLogsPreDashboard = sshUtil.readLogFile(LOG_FILE_TYPE_ACCESS);

    Login login = new Login();
    login.logInUser(pmEmail, UserType.PM);

    verifyErrorLogs(errorLogsPreDashboard, accessLogsPreDashboard);
  }

  @Test(dataProvider = "dashboardTestData", dataProviderClass = DashboardPaymentWidgetsTestDataProvider.class, groups = {
      "e2e"})
  public void pmDashboardNoWidgets(String testCase, String testCaseInfo) {
    Logger.info("Test case no. " + testCase + ": " + testCaseInfo);

    TestSetupPage testSetupPage = dashboardPaymentWidgetsTestSetup(testCase);
    String pmEmail = testSetupPage.getString("pmEmail");

    //Get logfile contents before loading pm dashboard
    SshUtil sshUtil = new SshUtil();
    String errorLogsPreDashboard = sshUtil.readLogFile(LOG_FILE_TYPE_ERROR);
    String accessLogsPreDashboard = sshUtil.readLogFile(LOG_FILE_TYPE_ACCESS);

    Login login = new Login();
    login.logInUser(pmEmail, UserType.PM);

    verifyErrorLogs(errorLogsPreDashboard, accessLogsPreDashboard);
  }

  @Test(dataProvider = "dashboardAddWidgetTestData", dataProviderClass = DashboardPaymentWidgetsTestDataProvider.class, groups = {
      "e2e"})
  public void pmDashboardAddWidgetsTest(String testCase, String testCaseInfo, int reportTypeId) {
    Logger.info("Test case no. " + testCase + ": " + testCaseInfo  + "added.");

    TestSetupPage testSetupPage = dashboardPaymentWidgetsTestSetup(testCase);
    String pmId = testSetupPage.getString("pmId");
    String pmEmail = testSetupPage.getString("pmEmail");

    //Populate user_dashboard_config table for this user, to show dashboard widgets
    DataBaseConnector connector = new DataBaseConnector();
    connector.createConnection();
    Connection connection = connector.getConnection();
    UserDashboardConfigDao dashboardConfigDao = new UserDashboardConfigDao();
    dashboardConfigDao.createDashboardConfigSetup(connection, pmId, null);

    //delete the config in the database before adding through the UI
    UserDashboardConfig dashboardConfigToAdd = new UserDashboardConfig();
    dashboardConfigToAdd = dashboardConfigDao
        .findByUserIdAndReportTypeId(connection, pmId, reportTypeId, 1).get(0);

    dashboardConfigDao.deleteConfigs(connection, dashboardConfigToAdd);

    //Get logfile contents before loading pm dashboard
    SshUtil sshUtil = new SshUtil();
    String errorLogsPreDashboard = sshUtil.readLogFile(LOG_FILE_TYPE_ERROR);
    String accessLogsPreDashboard = sshUtil.readLogFile(LOG_FILE_TYPE_ACCESS);

    Login login = new Login();
    login.logInUser(pmEmail, UserType.PM);

    PmHomePage pmHomePage = new PmHomePage();

    pmHomePage.scrollToPageBottom();

    //add the widget through db
    boolean isAdded = dashboardConfigDao.create(connection, dashboardConfigToAdd);

    Assert.assertTrue(isAdded, "Widget was not added.");

    pmHomePage.open();

    ArrayList<UserDashboardConfig> addedWidgets = dashboardConfigDao
        .findByUserIdAndReportTypeId(connection, pmId, reportTypeId, 1);

    Assert.assertEquals(addedWidgets.size(), 1);

    connector.closeConnection();

    verifyErrorLogs(errorLogsPreDashboard, accessLogsPreDashboard);
  }

  @Test(dataProvider = "dashboardAddWidgetTestData", dataProviderClass = DashboardPaymentWidgetsTestDataProvider.class, groups = {
      "e2e"})
  public void pmDashboardRemoveWidgetsTest(String testCase, String testCaseInfo, int reportTypeId) {
    Logger.info("Test case no. " + testCase + ": " + testCaseInfo  + "removed.");

    TestSetupPage testSetupPage = dashboardPaymentWidgetsTestSetup(testCase);
    String pmId = testSetupPage.getString("pmId");
    String pmEmail = testSetupPage.getString("pmEmail");

    //Populate user_dashboard_config table for this user, to show dashboard widgets
    DataBaseConnector connector = new DataBaseConnector();
    connector.createConnection();
    Connection connection = connector.getConnection();
    UserDashboardConfigDao dashboardConfigDao = new UserDashboardConfigDao();
    dashboardConfigDao.createDashboardConfigSetup(connection, pmId, null);

    //Get logfile contents before loading pm dashboard
    SshUtil sshUtil = new SshUtil();
    String errorLogsPreDashboard = sshUtil.readLogFile(LOG_FILE_TYPE_ERROR);
    String accessLogsPreDashboard = sshUtil.readLogFile(LOG_FILE_TYPE_ACCESS);

    Login login = new Login();
    login.logInUser(pmEmail, UserType.PM);

    verifyErrorLogs(errorLogsPreDashboard, accessLogsPreDashboard);
  }


  //--------------------------------TEST METHOD-----------------------------------------------------

  private TestSetupPage dashboardPaymentWidgetsTestSetup(String testCase) {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();

    return testSetupPage;
  }

  private void verifyErrorLogs(String errorLogsPreDashboard, String accessLogsPreDashboard) {
    SshUtil sshUtil = new SshUtil();

    PmHomePage pmHomePage = new PmHomePage();

    pmHomePage.scrollToPageBottom();

    try {
      if (pmHomePage.pageIsLoaded()) {
        pmHomePage.waitUntilAjaxEnds();
      }
    } catch (Exception e) {
      Logger.error(e.getMessage());
      Logger.error("Failed to wait for ajax on page to end.");
    } finally {
      String errorLogsPostDashboard = sshUtil.readLogFile(LOG_FILE_TYPE_ERROR);
      String accessLogsPostDashboard = sshUtil.readLogFile(LOG_FILE_TYPE_ACCESS);

      String errorLogs = errorLogsPostDashboard.substring(errorLogsPreDashboard.length());
      String accessLogs = accessLogsPostDashboard.substring(accessLogsPreDashboard.length());

      if(errorLogs.length() > 0) {
        Logger.info("Error Logs: " + errorLogs);
      } else {
        Logger.info("No Error Logs found.");
      }

      Assert.assertTrue(!doesLogContainMessage("PHP Warning:  session_start(): Unable to clear session lock record", errorLogs), "Logs contain errors.");
      Assert.assertTrue(!doesLogContainMessage("PHP Warning:  session_start(): Failed to read session data: memcached", errorLogs), "Logs contain errors.");

      if(accessLogs.length() > 0) {
        Logger.info("Access Logs: " + accessLogs);
      } else {
        Logger.info("No Access Logs found.");
      }

      Assert.assertTrue(doesLogContainMessage("GET \\/assets\\/js\\/language\\/Messages_en.properties\\?_=[\\d]* HTTP\\/1.1\" 200", accessLogs), "Pattern not found: GET \\/assets\\/js\\/language\\/Messages_en.properties\\?_=[\\d]* HTTP\\/1.1\" 200");
      Assert.assertTrue(doesLogContainMessage("POST \\/login\\/login_proc\\/index HTTP\\/1.1\" 303", accessLogs), "Pattern not found: POST \\/login\\/login_proc\\/index HTTP\\/1.1\" 303");
      Assert.assertTrue(doesLogContainMessage("GET \\/pm\\/dashboard HTTP\\/1.1\" 200", accessLogs), "Pattern not found: GET \\/pm\\/dashboard HTTP\\/1.1\" 200");
      Assert.assertTrue(doesLogContainMessage("GET \\/assets\\/js\\/language\\/Messages_en\\.properties\\?_=[\\d]* HTTP\\/1.1\" 200", accessLogs), "Pattern not found: GET \\/assets\\/js\\/language\\/Messages_en\\.properties\\?_=[\\d]* HTTP\\/1.1\" 200");
    }
  }

  private boolean doesLogContainMessage(String message, String logContent) {
    Pattern pattern = Pattern.compile(message, Pattern.MULTILINE);
    Matcher matcher = pattern.matcher(logContent);

    return matcher.find();
  }
}
