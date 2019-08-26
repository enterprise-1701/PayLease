package com.paylease.app.qa.e2e.tests.integration;

import static com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage.NEW_BANK;

import com.paylease.app.qa.e2e.tests.resident.OneTimePaymentTest;
import com.paylease.app.qa.framework.DataBaseConnector;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ResourceFactory;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.UtilityManager;
import com.paylease.app.qa.framework.pages.automatedhelper.ProcessTransactionPage;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.utility.database.client.dao.BatchFileDao;
import com.paylease.app.qa.framework.utility.database.client.dao.BatchItemDao;
import com.paylease.app.qa.framework.utility.database.client.dao.HolidaysDao;
import com.paylease.app.qa.framework.utility.database.client.dao.TopsTransactionsDao;
import com.paylease.app.qa.framework.utility.database.client.dto.BatchFile;
import com.paylease.app.qa.framework.utility.database.client.dto.BatchItem;
import com.paylease.app.qa.framework.utility.database.client.dto.Holidays;
import com.paylease.app.qa.framework.utility.database.client.dto.TopsTransactions;
import com.paylease.app.qa.framework.utility.listeners.Retry;
import com.paylease.app.qa.framework.utility.sshtool.SshUtil;
import com.paylease.app.qa.testbase.dataproviders.TopsIntegrationTestDataProvider;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TopsIntegrationTest extends ScriptBase {

  private static final String REGION = "Integration";
  private static final String FEATURE = "topsIntegration";
  private static final String DATE_FORMAT = "yyyy-MM-dd";
  private static final String HOLIDAY_NAME = "Tops Export Test Holiday";

  @Test(dataProvider = "topsIntegrationData", dataProviderClass = TopsIntegrationTestDataProvider
      .class, groups = {"e2e"}, retryAnalyzer = Retry.class)
  public void integrateTopsTransactions(String tc, Boolean today, Boolean yesterday, Boolean
      date, Boolean useSetDate, int setDate, String command) {

    SshUtil sshUtil = new SshUtil();
    UtilityManager utilityManager = new UtilityManager();
    Holidays holidaysDto = new Holidays();
    HolidaysDao holidaysDao = new HolidaysDao();

    String[] integrateCommand = {"cd " + ResourceFactory.getInstance().getProperty(ResourceFactory
        .WEB_APP_ROOT_DIR_KEY), "php batches/" + command};

    String[] refreshCommands = {
        "cd " + ResourceFactory.getInstance().getProperty(ResourceFactory
            .WEB_APP_ROOT_DIR_KEY), "php batches/cache_manager.php refresh hol"};

    DataBaseConnector dataBaseConnector = new DataBaseConnector();
    dataBaseConnector.createConnection();
    Connection connection = dataBaseConnector.getConnection();

    //Check if test holiday is in table. If so, delete it.
    ArrayList<Holidays> list = holidaysDao.findByHTitle(connection, HOLIDAY_NAME, 5);

    if(list.size() > 0) {
      holidaysDao.delete("h_title", HOLIDAY_NAME);
      sshUtil.sshCommand(refreshCommands);
    }

    try {
      Logger.info(tc + ": Verify Tops transaction is integrated properly");

      //Get current date in string format
      String todayStringFormat = utilityManager.getCurrentDate(DATE_FORMAT);
      Date todayDateFormat = utilityManager.stringToDate(todayStringFormat, DATE_FORMAT);

      //Output generated after attempting to integrate transactions
      String integrationOutput = "";

      //Set variable for test holiday
      String newHoliday = newHoliday(today, yesterday, date);

      String[] integrateCommandWithDate = {"cd " + ResourceFactory.getInstance().getProperty
          (ResourceFactory
              .WEB_APP_ROOT_DIR_KEY), "php batches/" + command + " " + newHoliday};

      //Insert test holiday
      if (newHoliday != null) {

        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        java.util.Date date2 = format.parse(newHoliday);
        long longDate = date2.getTime();
        java.sql.Date sqlDate = new java.sql.Date(longDate);

        holidaysDto.sethDate(sqlDate);
        holidaysDto.sethTitle(HOLIDAY_NAME);

        int rowsAffected = holidaysDao.create(connection, holidaysDto);

        Logger.info("Number of rows affected are: " + rowsAffected);

        sshUtil.sshCommand(refreshCommands);

      }

      if (checkIfHoliday(todayStringFormat) || checkIfWeekend(
          todayDateFormat)) { //Check if holiday or weekend - Expeced: Script exists
        integrationOutput = sshUtil.sshCommand(integrateCommand);
        Assert.assertTrue(integrationOutput.contains("HOLIDAY detected, do not run"));
        Logger.info("Today is a weekend or holiday. Script existed as expected");
      } else {

        TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc01");
        testSetupPage.open();

        String resEmail = testSetupPage.getString("resEmail");

        //Create one time payment
        OneTimePaymentTest oneTimePaymentTest = new OneTimePaymentTest();
        String tran1 = oneTimePaymentTest.residentOtPaymentActions(resEmail, NEW_BANK, false,
            false, null);

        //Payout Transaction
        ProcessTransactionPage processTransactionPage = new ProcessTransactionPage();
        processTransactionPage.open();
        processTransactionPage.processTransaction(tran1);

        //Change sent date to last businessday
        String currentDate = utilityManager.getCurrentDate(DATE_FORMAT);

        if (!useSetDate) { //use get last business day to set sent_date
          //Get number of days to last business day
          int daysBack = getLastBusinessDay(
              utilityManager.stringToDate(todayStringFormat, DATE_FORMAT));

          Date newDate = utilityManager
              .addDays(utilityManager.stringToDate(currentDate, DATE_FORMAT), daysBack);

          updateBatchFileSentDate(utilityManager.dateToString(newDate, DATE_FORMAT), tran1);
        } else { //use input parameter to set sent date
          Date newDate = utilityManager
              .addDays(utilityManager.stringToDate(currentDate, DATE_FORMAT), setDate);

          updateBatchFileSentDate(utilityManager.dateToString(newDate, DATE_FORMAT), tran1);
        }

        if (!date) {
          sshUtil.sshCommand(integrateCommand);
        } else { //integrate transactions with date parameter
          sshUtil.sshCommand(integrateCommandWithDate);
        }

        Assert.assertTrue(checkTransactionIntegrated(connection, tran1));
      }
    } catch (Exception e) {
      Logger.debug(e.getMessage());
    } finally {
      //Cleanup holidayList
      holidaysDto.sethTitle(HOLIDAY_NAME);
      holidaysDao.delete("h_title", HOLIDAY_NAME);

      sshUtil.sshCommand(refreshCommands);
      dataBaseConnector.closeConnection();
    }
  }

  private String newHoliday(Boolean today, Boolean yesterday, Boolean date) throws
      ParseException {
    UtilityManager utilityManager = new UtilityManager();

    String todayDate = UtilityManager.getCurrentDate(DATE_FORMAT);
    Date todayDateFormat = utilityManager.stringToDate(todayDate, DATE_FORMAT);
    Date yesterdayDate = utilityManager.addDays(todayDateFormat, -1);

    if (today) {
      //return todayDate;
      return utilityManager.dateToString(todayDateFormat, DATE_FORMAT);
    } else if (yesterday || date) {
      return utilityManager.dateToString(yesterdayDate, DATE_FORMAT);
    }

    return null;
  }

  private boolean checkTransactionIntegrated(Connection connection, String tran1) {

    boolean pass = false;

    if (getTopsTransactionId(connection, tran1) == null) {
      Logger.debug("Transaction not found: " + tran1);

    } else {
      Logger.info("Transaction " + tran1 + " found in tops_transactions table");
      pass = true;
    }
    return pass;
  }

  private String updateBatchFileSentDate(String date, String trans) {
    DataBaseConnector dataBaseConnector = new DataBaseConnector();
    BatchItemDao batchItemDao = new BatchItemDao();
    BatchFileDao batchFileDao = new BatchFileDao();

    try {
      dataBaseConnector.createConnection();
      Connection connection = dataBaseConnector.getConnection();

      ArrayList<BatchItem> batchItem = batchItemDao
          .findByTransId(connection, Long.parseLong(trans), 50);
      for (BatchItem batchItems : batchItem) {
        ArrayList<BatchFile> batchFileList = batchFileDao
            .findById(connection, batchItems.getFileId(), 50);
        for (BatchFile batchFile : batchFileList) {
          batchFile.setSentDate(date);
          int numOfRowsAffected = batchFileDao.update(connection, batchFile);
          Logger.debug("Batch File UPDATE - Rows Affected: " + numOfRowsAffected);
          Logger.debug("Batch File sent date updated to:" + date);
        }
      }
      dataBaseConnector.closeConnection();
    } catch (Exception se) {
      se.getMessage();
      dataBaseConnector.closeConnection();
    }
    return date;
  }

  private Long getTopsTransactionId(Connection connection, String transactionId) {

    TopsTransactionsDao topsTransactionsDao = new TopsTransactionsDao();

    ArrayList<TopsTransactions> transactionsList = topsTransactionsDao.findIntegratedTransaction
        (connection, Long.parseLong(transactionId));

    Long tranId = null;

    for (TopsTransactions transaction : transactionsList) {
      tranId = transaction.getTopsTransactionId();

      Logger.info("Transaction ID is: " + tranId);
    }

    return tranId;
  }

  private boolean checkIfWeekend(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);

    if ((calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
        || (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)) {
      return true;
    } else {
      return false;
    }
  }

  private boolean checkIfHoliday(String today) throws ParseException {

    HolidaysDao holidaysDAO = new HolidaysDao();

    DataBaseConnector dataBaseConnector = new DataBaseConnector();
    dataBaseConnector.createConnection();
    Connection connection = dataBaseConnector.getConnection();

    SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
    java.util.Date date2 = format.parse(today);
    long longDate = date2.getTime();
    java.sql.Date sqlDate = new java.sql.Date(longDate);

    ArrayList<Holidays> holiday = holidaysDAO.findByHDate(connection, sqlDate, 5);

    if (holiday.size() == 0) {
      return false;
    } else {
      return true;
    }
  }

  private int getLastBusinessDay(Date date) throws ParseException {

    UtilityManager utilityManager = new UtilityManager();

    date = utilityManager.addDays(date, -1);
    int lastBusinessDay = -1;
    boolean keepGoing = true;
    Date date2 = date;
    String inputDate = utilityManager.dateToString(date2, DATE_FORMAT);

    while (keepGoing) {
      if (checkIfHoliday(inputDate) || checkIfWeekend(date2)) {
        lastBusinessDay -= 1;
        date2 = utilityManager.addDays(date2, -1);
        inputDate = utilityManager.dateToString(date2, DATE_FORMAT);
      } else {
        keepGoing = false;
      }
    }
    return lastBusinessDay;
  }

}
