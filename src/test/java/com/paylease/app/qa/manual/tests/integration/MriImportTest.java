package com.paylease.app.qa.manual.tests.integration;

import static com.paylease.app.qa.framework.features.PaymentFlow.SCHEDULE_FIXED_AUTO;
import static com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage.NEW_BANK;

import com.jcraft.jsch.Session;
import com.paylease.app.qa.framework.DataBaseConnector;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ResourceFactory;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.UtilityManager;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.paymentflow.SchedulePage;
import com.paylease.app.qa.framework.pages.pm.PmLoginPage;
import com.paylease.app.qa.framework.pages.pm.ViewAutopays;
import com.paylease.app.qa.framework.utility.database.client.dao.UserDao;
import com.paylease.app.qa.framework.utility.database.client.dto.User;
import com.paylease.app.qa.framework.utility.listeners.Retry;
import com.paylease.app.qa.framework.utility.sshtool.SshUtil;
import com.paylease.app.qa.testbase.PaymentBase;
import com.paylease.app.qa.testbase.dataproviders.MriImportDataProvider;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MriImportTest extends ScriptBase {

  private static final String REGION = "Integration";
  private static final String FEATURE = "mriImport";

  private static String ftpRemoteTargetDirPath = ResourceFactory.getInstance()
      .getProperty(ResourceFactory.WEB_APP_ROOT_DIR_KEY) + "mri/import/new/";
  private static Session ftpSession;

  //--------------------------------MRI Import Test-------------------------------------------------

  @Test(dataProvider = "mriImportData", dataProviderClass = MriImportDataProvider.class, groups =
      {"manual"}, retryAnalyzer = Retry.class)
  public void mriImportTest(String tcVariationNumber, boolean cancelApLeaseEnd,
      boolean evalRetention, String templateFile, String residentStatus, String guarantor, String
      blockEpayments, String payAllowed, String vacateDate, String leaseMoveOutDate,
      boolean expectedAutoPayResult) {

    Logger.info(tcVariationNumber + ": Verify MRI import with the following configuration: "
        + "CancelAPLease set to " + cancelApLeaseEnd + ", Eval for Retention: " + evalRetention
        + "Template file is: " + templateFile + ", Resident Status set to: " + residentStatus
        + " , Guarantor set to: " + guarantor + ", Block EPayment set to: " + blockEpayments
        + ", Pay Allowed set to: " + payAllowed + ", Vacate day set to: " + vacateDate
        + " Lease move out date set to: " + leaseMoveOutDate + " and Expected Auto Pay result is"
        + expectedAutoPayResult);

    String testCase = getTestCase(cancelApLeaseEnd, evalRetention);

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();

    final String pm = testSetupPage.getString("pm");
    final String pmEmail = testSetupPage.getString("pmEmail");
    final String propertyId = testSetupPage.getString("propertyId");

    //Create unique resident Name Id
    String residentNameId = UtilityManager.getCurrentDate("yyyyMMddHHmmss");

    //Import resident for data setup
    importResidents("Current", "Y", "N", "null",
        "null", "null", pm, residentNameId);

    //Get imported resident ID
    DataBaseConnector dataBaseConnector = new DataBaseConnector();

    dataBaseConnector.createConnection();
    Connection connection = dataBaseConnector.getConnection();

    UserDao userDao = new UserDao();

    int limit = 50;
    ArrayList<User> userArrayList = userDao.findByPropId(connection, propertyId, limit);

    Logger.info(Arrays.toString(new ArrayList[]{userArrayList}));
    String residentUserId = userArrayList.get(0).getUserId();
    Logger.info("User ID: " + residentUserId);

    PmLoginPage pmLoginPage = new PmLoginPage();
    pmLoginPage.open();
    pmLoginPage.login(pmEmail);

    PaymentBase paymentBase = new PaymentBase();

    paymentBase.selectResidentFromResidentListAndBeginPayment(String.valueOf(residentUserId),
        SCHEDULE_FIXED_AUTO);
    paymentBase.fillAndSubmitPaymentAmount(false);

    SchedulePage schedulePage = new SchedulePage();
    schedulePage.prepField(SchedulePage.FIELD_FREQUENCY, "Monthly");
    schedulePage.fillAndSubmitPaymentScheduleDetails();

    paymentBase.selectPaymentMethod(NEW_BANK, false);
    paymentBase.reviewAndSubmit();

    importResidents(residentStatus, guarantor, blockEpayments, payAllowed, vacateDate,
        leaseMoveOutDate, pm, String.valueOf(residentNameId));

    ViewAutopays viewAutopays = new ViewAutopays();

    if (expectedAutoPayResult) {
      //Select cancel for Autopay status
      viewAutopays.selectAutopayStatusOption("cancelled");
    }

    viewAutopays.submitSearch();

    Assert.assertEquals(residentNameId, viewAutopays.getRefIdText());
  }

  //--------------------------------Test Methods----------------------------------------------------

  private String getTestCase(boolean cancelApLeaseEnd, boolean evalRetention) {
    if (cancelApLeaseEnd && evalRetention) {
      return "mriImport_Pm1";
    }
    if (!cancelApLeaseEnd && !evalRetention) {
      return "mriImport_Pm2";
    }
    if (cancelApLeaseEnd && !evalRetention) {
      return "mriImport_Pm3";
    }
    if (!cancelApLeaseEnd && evalRetention) {
      return "mriImport_Pm4";
    }
    return null;
  }

  private void importResidents(String residentStatus, String guarantor, String blockEPayments,
      String payAllowed, String residentVacateDate, String leaseMoveOut, String pm,
      String residentNameId) {

    String localPathTemplateFile
        = "src/test/resources/templates/20180830010102_500_RESIDENT_TEMPLATE";

    String localPathFileToFTP = "src/test/resources/files-to-ftp/20180830010102_500_RESIDENT.json";
    //Load Properties
    loadProperties(pm);

    try {
      //Read in template file
      String updatedTemplateString = readFileToString(localPathTemplateFile);

      //Prepare and format the input values that are dates
      residentVacateDate = prepareDate(residentVacateDate);
      leaseMoveOut = prepareDate(leaseMoveOut);

      //Replace tokens with input data
      updatedTemplateString = (residentNameId != null) ? updatedTemplateString
          .replace("%%ResidentNameID_TOKEN%%", residentNameId)
          : updatedTemplateString.replace("\"%%ResidentNameID_TOKEN%%\"", "null");

      updatedTemplateString = (residentStatus != null) ? updatedTemplateString
          .replace("%%ResidentStatus_TOKEN%%", residentStatus)
          : updatedTemplateString.replace("\"%%ResidentStatus_TOKEN%%\"", "null");

      updatedTemplateString =
          (guarantor != null) ? updatedTemplateString.replace("%%Guarantor_TOKEN%%", guarantor)
              : updatedTemplateString.replace("\"%%Guarantor_TOKEN%%\"", "null");

      updatedTemplateString = (blockEPayments != null) ? updatedTemplateString
          .replace("%%BlockEPayments_TOKEN%%", blockEPayments)
          : updatedTemplateString.replace("\"%%BlockEPayments_TOKEN%%\"", "null");

      updatedTemplateString =
          (payAllowed != null) ? updatedTemplateString.replace("%%PayAllowed_TOKEN%%", payAllowed)
              : updatedTemplateString.replace("\"%%PayAllowed_TOKEN%%\"", "null");

      updatedTemplateString = (residentVacateDate != null) ? updatedTemplateString
          .replace("%%ResidentVacateDate_TOKEN%%", residentVacateDate)
          : updatedTemplateString.replace("\"%%ResidentVacateDate_TOKEN%%\"", "null");

      updatedTemplateString = (leaseMoveOut != null) ? updatedTemplateString
          .replace("%%LeaseMoveOut_TOKEN%%", leaseMoveOut)
          : updatedTemplateString.replace("\"%%LeaseMoveOut_TOKEN%%\"", "null");

      //Write the new json file locally
      createFile(localPathFileToFTP, updatedTemplateString);

      //FTP the new json file to the vhost (Note: this creates the 'remoteTargetFtpDirPath' if it
      // does not already exist)
      SshUtil sshUtil = new SshUtil();
      ftpSession = sshUtil.ftpFileToRemoteServer(ftpRemoteTargetDirPath,
          localPathFileToFTP);

      //SSH to the VHost & Execute the MRI Import Script ("2-import-residents.php")
      String[] commands = {"cd " + ResourceFactory.getInstance().getProperty(ResourceFactory
          .WEB_APP_ROOT_DIR_KEY), "php batches/mri/2-import-residents.php " + pm};

      sshUtil.sshCommand(commands);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      ftpSession.disconnect();
    }
  }

  private String readFileToString(String path) {
    String fileStr = null;

    try {
      File file = new File(path);
      fileStr = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return fileStr;
  }

  private void createFile(String path, String contents) {
    try {
      File file = new File(path);
      FileWriter fileWriter = new FileWriter(file);
      fileWriter.write(contents);
      fileWriter.flush();
      fileWriter.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void loadProperties(String pm) {
    //Load Properties
    Properties prop = new Properties();
    try {
      InputStream input = new FileInputStream(
          "src/test/resources/config/AppConfig.properties");
      prop.load(input);
      prop.getProperty("SSH_PRIVATE_KEY_PATH").trim();
      prop.getProperty("SSH_USERNAME").trim();
      prop.getProperty("SSH_HOST").trim();
      prop.getProperty("SSH_PASSPHRASE").trim();
      ftpRemoteTargetDirPath = ftpRemoteTargetDirPath + pm;
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private String prepareDate(String generalDate) {
    String formattedDate = null;

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    Date todayDate = new Date();
    String today = dateFormat.format(todayDate);

    Calendar cal = Calendar.getInstance();
    cal.setTime(todayDate);
    cal.add(Calendar.DATE, -60);

    String past = dateFormat.format(cal.getTime());

    cal.setTime(todayDate);
    cal.add(Calendar.DATE, +60);
    String future = dateFormat.format(cal.getTime());

    switch (generalDate.toLowerCase()) {
      case "today":
        formattedDate = today;
        break;

      case "past":
        formattedDate = past;
        break;

      case "future":
        formattedDate = future;
        break;

      default:
        //not supported
    }

    return formattedDate;
  }
}