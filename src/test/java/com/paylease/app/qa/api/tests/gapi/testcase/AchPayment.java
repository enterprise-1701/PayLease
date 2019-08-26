package com.paylease.app.qa.api.tests.gapi.testcase;

import com.github.javafaker.Faker;
import com.paylease.app.qa.api.tests.ExpectedResponse;
import com.paylease.app.qa.framework.DataBaseConnector;
import com.paylease.app.qa.framework.DataHelper;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.api.Parameter;
import com.paylease.app.qa.framework.api.ParameterCollection;
import com.paylease.app.qa.framework.api.Request;
import com.paylease.app.qa.framework.api.Response;
import com.paylease.app.qa.framework.api.gapi.AchPaymentTransaction;
import com.paylease.app.qa.framework.api.gapi.GapiTransaction;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javax.xml.xpath.XPathExpressionException;

public class AchPayment extends BasicTestCase {

  private static final String SUCCESS_CODE = "2";
  private static final String RESPONSE_TRANS_ID = "TransactionId";
  private static final String RESPONSE_UNIT_AMOUNT = "UnitAmount";
  private static final String RESPONSE_TOTAL_AMOUNT = "TotalAmount";
  private static final String RESPONSE_FEE_AMOUNT = "FeeAmount";
  private static final String DB_TRANS_ID = "transId";
  private static final String DB_UNIT_AMOUNT = "unitAmount";
  private static final String DB_TOTAL_AMOUNT = "totalAmount";
  private static final String DB_PAYLEASE_FEE = "payleaseFee";
  private static final String DB_INCUR = "incur";
  private static final String DB_PM_FEE = "pmFee";

  private static final String VALID_FIELD_PAYER_REFERENCE_ID = "payerReferenceId";
  private static final String VALID_FIELD_PAYER_FIRST_NAME = "payerFirstName";
  private static final String VALID_FIELD_PAYER_LAST_NAME = "payerLastName";
  private static final String VALID_FIELD_ACCOUNT_TYPE = "accountType";
  private static final String VALID_FIELD_ACCOUNT_FULL_NAME = "accountFullName";
  private static final String VALID_FIELD_ROUTING_NUMBER = "routingNumber";
  private static final String VALID_FIELD_ACCOUNT_NUMBER = "accountNumber";
  private static final String VALID_FIELD_TOTAL_AMOUNT = "totalAmount";
  private static final String VALID_FIELD_FEE_AMOUNT = "feeAmount";
  private static final String VALID_FIELD_INCUR_FEE = "incurFee";
  private static final String VALID_FIELD_SAVE_ACCOUNT = "saveAccount";
  private static final String VALID_FIELD_MESSAGE = "message";
  private static final String VALID_FIELD_CHECK_DATE = "checkDate";

  protected String paymentReferenceId;
  protected String payerReferenceId;
  protected String payerSecondaryRefId;
  protected String payeeId;
  protected String payerFirstName;
  protected String payerLastName;
  protected String accountType;
  protected String accountFullName;
  protected String routingNumber;
  protected String accountNumber;
  protected String totalAmount;
  protected String feeAmount;
  protected String incurFee;
  protected String saveAccount;
  protected String message;
  protected String checkScanned;
  protected String check21;
  protected String checkDate;
  protected String checkNum;
  protected String auxOnUs;
  protected boolean includeImages;
  protected String currencyCode;

  protected String amount;
  private ArrayList<HashMap<String, String>> depositItems;

  /**
   * Create a basic AchPayment test case. Set remaining values to null.
   *
   * @param paymentReferenceId Key value for this test case (PaymentReferenceId)
   * @param summary Summary for this test case
   * @param expectedResponse Expected response value to be returned by GAPI
   * @param payeeId Valid PayeeId value given by test setup
   */
  public AchPayment(
      String paymentReferenceId, String summary, ExpectedResponse expectedResponse, String payeeId
  ) {
    this(paymentReferenceId, summary, expectedResponse, null, null, payeeId, null,
        null, null, null, null, null,
        null, null, null, null, null, null,
        null, null, null, null);
  }

  private AchPayment(
      String paymentReferenceId, String summary, ExpectedResponse expectedResponse,
      String payerReferenceId,
      String payerSecondaryRefId, String payeeId, String payerFirstName, String payerLastName,
      String accountType, String accountFullName, String routingNumber, String accountNumber,
      String totalAmount, String feeAmount, String incurFee, String saveAccount, String message,
      String checkScanned, String check21, String checkDate, String checkNum, String auxOnUs
  ) {
    super(summary, expectedResponse);

    this.paymentReferenceId = paymentReferenceId;

    this.payerReferenceId = payerReferenceId;
    this.payerSecondaryRefId = payerSecondaryRefId;
    this.payeeId = payeeId;
    this.payerFirstName = payerFirstName;
    this.payerLastName = payerLastName;
    this.accountType = accountType;
    this.accountFullName = accountFullName;
    this.routingNumber = routingNumber;
    this.accountNumber = accountNumber;
    this.totalAmount = totalAmount;
    this.feeAmount = feeAmount;
    this.incurFee = incurFee;
    this.saveAccount = saveAccount;
    this.message = message;
    this.checkScanned = checkScanned;
    this.check21 = check21;
    this.checkDate = checkDate;
    this.checkNum = checkNum;
    this.auxOnUs = auxOnUs;
    this.includeImages = false;
    this.depositItems = new ArrayList<>();
  }

  /**
   * Create an AchPayment test case with a given valid PayeeId and valid values for all fields
   * typically used in AchPayment (non check21) request.
   *
   * @param paymentReferenceId Key value for this test case (PaymentReferenceId)
   * @param summary Summary for this test case
   * @param expectedResponse response expected to be returned by GAPI
   * @param payeeId Valid payeeId given by test setup
   * @return AchPayment test case that can be submitted as valid
   */
  public static AchPayment createValidAchPayment(
      String paymentReferenceId, String summary, ExpectedResponse expectedResponse, String payeeId
  ) {
    AchPayment testCase = new AchPayment(paymentReferenceId, summary, expectedResponse, payeeId);

    testCase.payerReferenceId = testCase.getValidValue(VALID_FIELD_PAYER_REFERENCE_ID);
    testCase.payeeId = payeeId;
    testCase.payerFirstName = testCase.getValidValue(VALID_FIELD_PAYER_FIRST_NAME);
    testCase.payerLastName = testCase.getValidValue(VALID_FIELD_PAYER_LAST_NAME);
    testCase.accountType = testCase.getValidValue(VALID_FIELD_ACCOUNT_TYPE);
    testCase.accountFullName = testCase.getValidValue(VALID_FIELD_ACCOUNT_FULL_NAME);
    testCase.routingNumber = testCase.getValidValue(VALID_FIELD_ROUTING_NUMBER);
    testCase.accountNumber = testCase.getValidValue(VALID_FIELD_ACCOUNT_NUMBER);
    testCase.totalAmount = testCase.getValidValue(VALID_FIELD_TOTAL_AMOUNT);
    testCase.feeAmount = testCase.getValidValue(VALID_FIELD_FEE_AMOUNT);
    testCase.incurFee = testCase.getValidValue(VALID_FIELD_INCUR_FEE);
    testCase.saveAccount = testCase.getValidValue(VALID_FIELD_SAVE_ACCOUNT);
    testCase.message = testCase.getValidValue(VALID_FIELD_MESSAGE);

    return testCase;
  }

  /**
   * Create a Check21 test case with a given valid PayeeId and valid values for all fields typically
   * used in Check21 request.
   *
   * @param paymentReferenceId Key value for this test case (PaymentReferenceId)
   * @param summary Summary for this test case
   * @param expectedResponse response expected to be returned by GAPI
   * @param payeeId Valid payeeId given by test setup
   * @return AchPayment test case that can be submitted as valid for check21 (check images not
   * included)
   */
  public static AchPayment createValidCheck21(
      String paymentReferenceId, String summary, ExpectedResponse expectedResponse, String payeeId
  ) {
    AchPayment testCase = new AchPayment(paymentReferenceId, summary, expectedResponse, payeeId);

    testCase.payerReferenceId = testCase.getValidValue(VALID_FIELD_PAYER_REFERENCE_ID);
    testCase.payeeId = payeeId;
    testCase.payerFirstName = testCase.getValidValue(VALID_FIELD_PAYER_FIRST_NAME);
    testCase.payerLastName = testCase.getValidValue(VALID_FIELD_PAYER_LAST_NAME);
    testCase.accountType = testCase.getValidValue(VALID_FIELD_ACCOUNT_TYPE);
    testCase.accountFullName = testCase.getValidValue(VALID_FIELD_ACCOUNT_FULL_NAME);
    testCase.routingNumber = testCase.getValidValue(VALID_FIELD_ROUTING_NUMBER);
    testCase.accountNumber = testCase.getValidValue(VALID_FIELD_ACCOUNT_NUMBER);
    testCase.totalAmount = testCase.getValidValue(VALID_FIELD_TOTAL_AMOUNT);
    testCase.feeAmount = "0";
    testCase.incurFee = testCase.getValidValue(VALID_FIELD_INCUR_FEE);
    testCase.saveAccount = testCase.getValidValue(VALID_FIELD_SAVE_ACCOUNT);
    testCase.checkScanned = "Yes";
    testCase.check21 = "Yes";
    testCase.checkDate = testCase.getValidValue(VALID_FIELD_CHECK_DATE);

    return testCase;
  }

  protected void initValidValues() {
    super.initValidValues();
    validValues.put(VALID_FIELD_PAYER_REFERENCE_ID, new String[]{
        "GAPITesterACH", "GAPITesterACH1",
    });
    validValues.put(VALID_FIELD_PAYER_FIRST_NAME, new String[]{
        "Foxy", "Bryon", "K!a1@t#h$r4%y^n&", "Kaylee", "JeffersonBarwickBarrickNorthst",
        "Tammy", "Susan", "Brian", "Eugene", "Jose", "Jonathan", "Albert", "Mary", "Kurt",
        "N_-+=eil", "Kenneth",
    });
    validValues.put(VALID_FIELD_PAYER_LAST_NAME, new String[]{
        "Shuttle", "Hendrix", "B4*r(o4)wn", "Lanier", "AlistaireBenjaminLafayetteTrya",
        "Murphy", "Bell", "Baker", "Carter", "Edwards", "Martin", "Powell", "Myers",
        "Wil_-+=son", "Evans",
    });
    validValues.put(VALID_FIELD_ACCOUNT_TYPE, new String[]{
        "Checking", "Savings", "Business Checking",
    });
    validValues.put(VALID_FIELD_ACCOUNT_FULL_NAME, new String[]{
        "Foxy Shuttle", "Bryon Hendrix", "Kathryn Brown", "K!a@y#l$e%e^ L&a*n(i)e_r+",
        "JeffersonBarwickBarrickNorthstAlistaireBenjamin LafayetteTryaJeffersonBarwickBarrickNorthstAlistaire",
        "Tammy Murphy", "Susan Bell", "Brian Baker", "Eugene Carter", "Jose Edwards",
        "Jonathan Martin", "Albert Bell", "Kenneth Evans",
        "JeffersonBarwickBarrickNorthst AlistaireBenjaminLafayetteTrya", "Mary Powell",
        "Kur_-+=t A. Myers", "Neil Wilson",
    });
    validValues.put(VALID_FIELD_ROUTING_NUMBER, new String[]{
        "490000018",
    });
    validValues.put(VALID_FIELD_ACCOUNT_NUMBER, new String[]{
        "614800811", "61480081",
    });

    Faker faker = new Faker();

    String[] totals = new String[10];
    for (int i = 0; i < 10; i++) {
      totals[i] = faker.commerce().price(100, 500);
    }
    String[] fees = new String[10];
    for (int i = 0; i < 10; i++) {
      fees[i] = faker.commerce().price(3, 50);
    }
    validValues.put(VALID_FIELD_TOTAL_AMOUNT, totals);
    validValues.put(VALID_FIELD_FEE_AMOUNT, fees);

    validValues.put(VALID_FIELD_INCUR_FEE, new String[]{
        "Yes", "No",
    });
    validValues.put(VALID_FIELD_SAVE_ACCOUNT, new String[]{
        "Yes", "No",
    });
    validValues.put(VALID_FIELD_MESSAGE, new String[]{
        "QA GAPI TEST SUITE",
    });

    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    Date today = new Date();
    validValues.put(VALID_FIELD_CHECK_DATE, new String[]{
        dateFormat.format(today),
    });
  }

  public AchPayment setPayerReferenceId(String payerReferenceId) {
    this.payerReferenceId = payerReferenceId;
    return this;
  }

  public AchPayment setPayerSecondaryReferenceId(String payerSecondaryReferenceId) {
    this.payerSecondaryRefId = payerSecondaryReferenceId;
    return this;
  }

  public AchPayment setPayerFirstName(String payerFirstName) {
    this.payerFirstName = payerFirstName;
    return this;
  }

  public AchPayment setPayerLastName(String payerLastName) {
    this.payerLastName = payerLastName;
    return this;
  }

  public AchPayment setAccountType(String accountType) {
    this.accountType = accountType;
    return this;
  }

  public AchPayment setAccountFullName(String accountFullName) {
    this.accountFullName = accountFullName;
    return this;
  }

  public AchPayment setRoutingNumber(String routingNumber) {
    this.routingNumber = routingNumber;
    return this;
  }

  public AchPayment setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
    return this;
  }

  public AchPayment setTotalAmount(String totalAmount) {
    this.totalAmount = totalAmount;
    return this;
  }

  public AchPayment setFeeAmount(String feeAmount) {
    this.feeAmount = feeAmount;
    return this;
  }

  public AchPayment setIncurFee(String incurFee) {
    this.incurFee = incurFee;
    return this;
  }

  public AchPayment setSaveAccount(String saveAccount) {
    this.saveAccount = saveAccount;
    return this;
  }

  public AchPayment setMessage(String message) {
    this.message = message;
    return this;
  }

  public AchPayment setCheckScanned(String checkScanned) {
    this.checkScanned = checkScanned;
    return this;
  }

  public AchPayment setCheck21(String check21) {
    this.check21 = check21;
    return this;
  }

  public AchPayment setCheckDate(String checkDate) {
    this.checkDate = checkDate;
    return this;
  }

  public AchPayment setCheckNum(String checkNum) {
    this.checkNum = checkNum;
    return this;
  }

  public AchPayment setAuxOnUs(String auxOnUs) {
    this.auxOnUs = auxOnUs;
    return this;
  }

  public AchPayment setIncludeImages() {
    includeImages = true;
    return this;
  }

  public AchPayment setCurrencyCode(String currencyCode) {
    this.currencyCode = currencyCode;
    return this;
  }

  @Override
  public boolean test(Response response) {
    boolean initialResult = super.test(response);

    try {
      String code = response.getSpecificCode();
      if (!code.equals(SUCCESS_CODE)) {
        return initialResult;
      }

      String transactionId = response.getSpecificElementValue(RESPONSE_TRANS_ID);
      String totalAmount = response.getSpecificElementValue(RESPONSE_TOTAL_AMOUNT);
      String unitAmount = response.getSpecificElementValue(RESPONSE_UNIT_AMOUNT);
      String feeAmount = response.getSpecificElementValue(RESPONSE_FEE_AMOUNT);

      HashMap<String, String> dbTransaction = getTransFromDb(transactionId);
      if (dbTransaction.isEmpty()) {
        Logger.error("Expected to find transaction from response in database");
        return false;
      }

      boolean resultTotal = totalAmount.equals(dbTransaction.get(DB_TOTAL_AMOUNT));
      boolean resultUnit = unitAmount.equals(dbTransaction.get(DB_UNIT_AMOUNT));
      boolean resultFeeIncur;
      boolean resultFeeAmount;
      String whoIncurs;
      String actualFeeAmount = dbTransaction.get(DB_PAYLEASE_FEE).isEmpty()
          ? dbTransaction.get(DB_PM_FEE) : dbTransaction.get(DB_PAYLEASE_FEE);

      float intendedFeeAmount = this.feeAmount == null || this.feeAmount.isEmpty()
          ? 0 : Float.valueOf(this.feeAmount);

      if (incurFee.toLowerCase().equals("yes") && intendedFeeAmount > 0) {
        // if we specified that the PM should incur - check that this was reflected in the DB
        resultFeeIncur = dbTransaction.get(DB_INCUR).equals("1");
        resultFeeAmount = feeAmount.equals(dbTransaction.get(DB_PM_FEE));
        whoIncurs = "PM";
      } else if (incurFee.toLowerCase().equals("no") && intendedFeeAmount > 0) {
        // else if we specified that the PM should not incur
        // - check that this was reflected in the DB, but ONLY if the fee amount is not ALSO 0
        resultFeeIncur = dbTransaction.get(DB_INCUR).equals("0");
        resultFeeAmount = feeAmount.equals(dbTransaction.get(DB_PAYLEASE_FEE));
        whoIncurs = "PayLease";
      } else {
        // PM is not set to incur and there is no fee amount - in this case there is no checking
        resultFeeIncur = true;
        resultFeeAmount = true;
        whoIncurs = "";
      }

      boolean result = resultTotal && resultUnit && resultFeeIncur && resultFeeAmount;
      if (!result) {
        if (initialResult) {
          Logger.error(summary);
        }
        if (!resultTotal) {
          Logger.error(
              "Expected total amount " + totalAmount + " but found "
                  + dbTransaction.get(DB_TOTAL_AMOUNT));
        }
        if (!resultUnit) {
          Logger.error(
              "Expected unit amount " + unitAmount + " but found "
                  + dbTransaction.get(DB_UNIT_AMOUNT));
        }
        if (!resultFeeIncur) {
          Logger.error("Expected fee incurred by " + whoIncurs);
        }
        if (!resultFeeAmount) {
          Logger.error("Expected fee amount " + feeAmount + " but found " + actualFeeAmount);
        }
      }

      return result && initialResult;

    } catch (XPathExpressionException e) {
      Logger.error("Unable to find item " + index + " in response");
      return false;
    }
  }

  private HashMap<String, String> getTransFromDb(String transId) {
    String sqlQuery = "SELECT trans_id, unit_amount, total_amount, "
        + "paylease_fee, PM_pay_fee, pm_fee_amount "
        + "FROM transactions "
        + "WHERE trans_id = " + transId + " LIMIT 1";

    DataBaseConnector dataBaseConnector = new DataBaseConnector();
    dataBaseConnector.createConnection();

    ResultSet query = dataBaseConnector.executeSqlQuery(sqlQuery);

    HashMap<String, String> transRow = new HashMap<>();
    try {
      if (query.first()) {
        transRow.put(DB_TRANS_ID, query.getString("trans_id"));
        transRow.put(DB_UNIT_AMOUNT, query.getString("unit_amount"));
        transRow.put(DB_TOTAL_AMOUNT, query.getString("total_amount"));
        transRow.put(DB_PAYLEASE_FEE, query.getString("paylease_fee"));
        transRow.put(DB_INCUR, query.getString("PM_pay_fee"));
        transRow.put(DB_PM_FEE, query.getString("pm_fee_amount"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    dataBaseConnector.closeConnection();

    return transRow;
  }

  @Override
  public void addTransaction(Request apiRequest) {
    DataHelper dataHelper = new DataHelper();
    ParameterCollection parameters = getTransactionParameterCollection();

    String paymentTraceId = dataHelper.getPaymentTraceId();
    parameters.put(AchPaymentTransaction.PAYMENT_REFERENCE_ID, paymentReferenceId);
    parameters.put(AchPaymentTransaction.PAYMENT_TRACE_ID, paymentTraceId);
    parameters.put(AchPaymentTransaction.PAYER_REFERENCE_ID, payerReferenceId);
    parameters.put(AchPaymentTransaction.PAYEE_ID, payeeId);
    parameters.put(AchPaymentTransaction.PAYER_FIRST_NAME, payerFirstName);
    parameters.put(AchPaymentTransaction.PAYER_LAST_NAME, payerLastName);

    Parameter splitDeposit = new Parameter();
    for (HashMap<String, String> item : depositItems) {
      Parameter deposit = new Parameter();
      deposit.addParameter(AchPaymentTransaction.PAYEE_ID, item.get("payeeId"));
      deposit.addParameter(AchPaymentTransaction.AMOUNT, item.get("amount"));
      splitDeposit.addParameter(AchPaymentTransaction.DEPOSIT, deposit);
    }

    parameters.put(AchPaymentTransaction.SPLIT_DEPOSIT, splitDeposit);

    parameters.put(AchPaymentTransaction.ACCOUNT_TYPE, accountType);
    parameters.put(AchPaymentTransaction.ACCOUNT_FULL_NAME, accountFullName);
    parameters.put(AchPaymentTransaction.ROUTING_NUMBER, routingNumber);
    parameters.put(AchPaymentTransaction.ACCOUNT_NUMBER, accountNumber);
    parameters.put(AchPaymentTransaction.TOTAL_AMOUNT, totalAmount);
    parameters.put(AchPaymentTransaction.FEE_AMOUNT, feeAmount);
    parameters.put(AchPaymentTransaction.INCUR_FEE, incurFee);
    parameters.put(AchPaymentTransaction.SAVE_ACCOUNT, saveAccount);
    parameters.put(AchPaymentTransaction.MESSAGE, message);
    parameters.put(AchPaymentTransaction.CHECK_SCANNED, checkScanned);
    parameters.put(AchPaymentTransaction.CHECK_21, check21);
    parameters.put(AchPaymentTransaction.CHECK_DATE, checkDate);
    parameters.put(AchPaymentTransaction.CHECK_NUM, checkNum);
    parameters.put(AchPaymentTransaction.AUX_ON_US, auxOnUs);
    parameters.put(AchPaymentTransaction.CURRENCY_CODE, currencyCode);

    if (includeImages) {
      parameters.put(AchPaymentTransaction.IMAGE_FRONT, AchPaymentTransaction.IMAGE_FRONT_BASE64);
      parameters.put(AchPaymentTransaction.IMAGE_BACK, AchPaymentTransaction.IMAGE_BACK_BASE64);
    }

    GapiTransaction transaction = new AchPaymentTransaction(parameters);
    apiRequest.addTransaction(transaction);

  }

  /**
   * Function to add Line item elements to ListItems.
   *
   * @param payeeId chargeDays
   * @param amount amount
   * @return a Hashmap of list items
   */
  public AchPayment addDepositItem(String payeeId, String amount) {

    HashMap<String, String> depositItemValues = new HashMap<>();
    depositItemValues.put("payeeId", payeeId);
    depositItemValues.put("amount", amount);

    depositItems.add(depositItemValues);

    return this;
  }

}
