package com.paylease.app.qa.framework;

import static com.paylease.app.qa.framework.utility.sshtool.SshDriver.CHAR_ENCODING_UTF_8;

import com.github.javafaker.Faker;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class DataHelper {

  Faker faker;

  public DataHelper() {
    faker = new Faker();
  }

  /**
   * Get an email address and make it unique.
   *
   * @return unique email
   */
  public String getUniqueEmail() {
    String email = faker.internet().emailAddress();

    Date date = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    String dateString = dateFormat.format(date.getTime());
    email = email.replace("@", "+" + dateString + "@");

    return email;
  }

  /**
   * Get a random bank account number.
   *
   * @return bank account number
   */
  public int getAccountNumber() {
    return faker.number().numberBetween(112312478, 599999999);
  }

  /**
   * Get a random first name.
   *
   * @return first name
   */
  public String getFirstName() {
    String firstName = faker.name().firstName();

    Logger.trace("First Name entered as: " + firstName);

    return firstName;
  }

  /**
   * Get a random last name.
   *
   * @return last name
   */
  public String getLastName() {
    String lastName = faker.name().lastName();

    Logger.trace("Last Name entered as: " + lastName);

    return lastName;
  }

  public String getReferenceId() {
    return String.valueOf(faker.number().numberBetween(1000000000, 2000000000));
  }

  public String getPaymentTraceId() {
    return getReferenceId();
  }

  public String getPayerReferenceId() {
    return getReferenceId();
  }

  public String getExtTransactionId() {
    return getReferenceId();
  }

  public String getSequenceNumber() {
    return String.valueOf(faker.number().randomNumber(26, false));
  }

  public String getInvoiceId() {
    return getReferenceId();
  }

  public String getAuxOnUs() {
    return String.valueOf(faker.number().randomNumber(18, false));
  }

  public String getCheckNum() {
    return String.valueOf(faker.number().numberBetween(1, 1400000000));
  }

  private int luhnTest(String str) {
    int sum = 0;
    boolean isEven = false;
    for (int i = str.length(); i > 0; i--) {
      int k = Integer.parseInt(str.substring(i - 1, i));
      if (isEven) {
        k = k * 2;
        if (k / 10 != 0) {
          k = k / 10 + k % 10;
        }
      }

      isEven = !isEven;
      sum += k;
    }
    return sum;
  }

  private int getCheckDigit(String str) {
    int k = luhnTest(str + "0");
    int i = 0;
    if (k % 10 != 0) {
      i = 10 - k % 10;
    }
    return i;
  }

  /**
   * Return the input number with check digit appended.
   *
   * @return Luhn valid number
   **/
  public String getLuhnCardNumber() {
    String str = String.valueOf(faker.number().numberBetween(800000000, 999999999));

    return str + getCheckDigit(str);
  }

  /**
   * Generate a random alphanumeric string.
   *
   * @param len length of the string
   * @return String
   */
  public String generateAlphanumericString(int len) {
    String alphanumeric = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    Random random = new Random();
    String s = "";

    for (int i = 0; i < len; i++) {
      char c = alphanumeric.charAt(random.nextInt(alphanumeric.length()));
      s += c;
    }

    return s;
  }

  /**
   * Generate a random alpha string.
   *
   * @param len length of the string.
   * @return String
   */
  public String generateAlphaString(int len) {
    String alpha = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    Random random = new Random();
    String s = "";
    StringBuilder builder = new StringBuilder();

    for (int i = 0; i < len; i++) {
      char c = alpha.charAt(random.nextInt(alpha.length()));
      s = builder.append(c).toString();
    }

    return s;
  }

  /**
   * Generate an amount.
   *
   * @return amount between 1000 and 4000.
   **/
  public String getAmount() {
    return String.valueOf(faker.number().numberBetween(1000, 4000));
  }


  /**
   * Generate boolean value.
   *
   * @return true/false randomly
   */
  public boolean getBoolean() {
    return faker.bool().bool();
  }

  /**
   * Generate an amount within provided range.
   *
   * @param lowerBound lower bound
   * @param upperBound upper bound
   * @return amount between upper bound and lower bound
   */
  public String getMoneyAmount(int lowerBound, int upperBound) {
    DecimalFormat formatter = new DecimalFormat("0.00");

    return formatter.format(faker.number().randomDouble(2, lowerBound, upperBound));
  }

  /**
   * Double encodes a string.
   *
   * @param string String to be double encoded
   * @return String that is double encoded
   */
  public String doubleEncoder(String string) throws UnsupportedEncodingException {
    string = URLEncoder.encode(string, CHAR_ENCODING_UTF_8);
    return URLEncoder.encode(string, CHAR_ENCODING_UTF_8);
  }
}
