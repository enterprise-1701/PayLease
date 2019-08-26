package com.paylease.app.qa.framework;

import com.github.javafaker.Faker;
import com.paylease.app.qa.framework.utility.database.client.dao.TransactionDao;
import com.paylease.app.qa.framework.utility.database.client.dto.Transaction;
import java.io.File;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.openqa.selenium.WebElement;

public class UtilityManager {

  public static final String YEAR_MONTH_DAY_DASH = "yyyy-MM-dd";
  public static final String  MONTH_DAY_YEAR_SLASH = "MM/dd/yyyy";
  public static final String YEAR_MONTH_DAY_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
  public static final String DAY = "dd";

  public static final DateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  public static final DateFormat DATE_WITH_DASH_FORMAT = new SimpleDateFormat("MM-dd-yyyy");
  public static final DateFormat US_FORMAT = new SimpleDateFormat("MM/dd/yyyy");

  /**
   * Get the current date.
   *
   * @param dateFormat date format
   */
  public static String getCurrentDate(String dateFormat) {
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
    return now.format(formatter);
  }

  /**
   * Get a unique string composed of the current time in milliseconds and a random integer.
   *
   * @return Unique (hopefully) string
   */
  public static String getUniqueString() {
    long time = System.currentTimeMillis();
    Faker faker = new Faker();
    int random = faker.random().nextInt(999999);
    return String.valueOf(time) + "_" + String.valueOf(random);
  }

  /**
   * For a given WebElement, determine if that element has been assigned the given class name.
   *
   * @param element Element to search
   * @param className Classname to match
   * @return True if element has given class
   */
  public static boolean elementHasClass(WebElement element, String className) {
    String[] classes = element.getAttribute("class").split(" ");
    return Arrays.asList(classes).contains(className);
  }

  /**
   * Return date object after adding or subtracting any given days to it.
   *
   * @param date any given date
   * @param daysToAdd days to add or subtract
   * @return date object with added or subtracted days
   **/
  public Date addDays(Date date, int daysToAdd) {

    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.add(Calendar.DATE, daysToAdd);

    return cal.getTime();
  }

  /**
   * Converts any date in string to time object.
   *
   * @param dateInString any given date in string
   * @param dateFormat the format the date should be converted to
   * @return date object
   * @throws ParseException parse exception
   */
  public Date stringToDate(String dateInString, String dateFormat) throws ParseException {

    SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

    return formatter.parse(dateInString);
  }

  /**
   * Converts any date in date object to string.
   *
   * @param date any given date as time object
   * @param dateFormat the format the date should be converted to
   * @return date string
   */
  public String dateToString(Date date, String dateFormat) {

    SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

    return formatter.format(date);
  }

  /**
   * Gets last four character of any given string.
   *
   * @param value string to get the last four character of
   * @return last four character of the given string
   */
  public String getLastFourChar(String value) {
    return value.substring(Math.max(0, value.length() - 4));
  }

  /**
   * Turn a slugified string into a display label.
   *
   * @param slug slugified string
   * @return parsed string
   */
  public String unslugify(String slug) {
    slug = slug.replace('_', ' ');

    return slug.substring(0, 1).toUpperCase() + slug.substring(1).toLowerCase();
  }

  /**
   * Get the latest transaction ID from the database by Resident integration_user_id and PmID.
   *
   * @param tenantId Resident integration_user_id to find
   * @param pmId PM ID
   * @return Transaction ID
   */
  public String getLastTransIdForResident(String tenantId, String pmId) {
    DataBaseConnector dataBaseConnector = new DataBaseConnector();
    dataBaseConnector.createConnection();
    Connection connection = dataBaseConnector.getConnection();

    TransactionDao transactionDao = new TransactionDao();
    Transaction transaction = transactionDao
        .findLatestByResidentAndPm(connection, Integer.parseInt(tenantId), Integer.parseInt(pmId));

    if (null == transaction) {

      return "";
    }

    return String.valueOf(transaction.getTransactionId());
  }

  /**
   * Create a folder with timestamp.
   *
   * @param path path where folder is to be created
   * @return filepath
   */
  String makeDirectory(String path) {
    String filePath = path + new SimpleDateFormat("yyyy_MM_dd")
        .format(Calendar.getInstance().getTime());

    File dir = new File(filePath);
    dir.mkdir();

    return filePath;
  }

  /**
   * Get amount in dollar format with two decimal places.
   *
   * @param amount double amount
   * @return amount in dollar format
   */
  public String formatToDollarAmount(Double amount) {
    DecimalFormat formatter = new DecimalFormat("#,###.00");

    String moneyAmount = "$" + formatter.format(amount);

   return moneyAmount;
  }

  /**
   * Checks if it's a weekend.
   *
   * @return true if weekend
   */
  public boolean isDuringWeekend() {
    boolean isWeekend = false;

    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.MINUTE, 5);
    cal.getTime();

    if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
        || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
      isWeekend = true;
    }

    return isWeekend;
  }

  /**
   * Search for a message in the given string.
   *
   * @param message message to find
   * @param logContent content to search in
   * @return true if message found in logContent
   */
  public boolean doesLogContainMessage(String message, String logContent) {
    Pattern pattern = Pattern.compile(message, Pattern.MULTILINE);
    Matcher matcher = pattern.matcher(logContent);

    return matcher.find();
  }

  public static String getCurrentTimePlusOneMin(String timeFormat) {
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime nowPlusOneMin = now.plus(1, ChronoUnit.MINUTES);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(timeFormat);
    return nowPlusOneMin.format(formatter);
  }

  public static String getCurrentTimeMinusTwoMin(String timeFormat) {
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime nowPlusOneMin = now.plus(-2, ChronoUnit.MINUTES);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(timeFormat);
    return nowPlusOneMin.format(formatter);
  }
}

