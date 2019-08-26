package com.paylease.app.qa.functional.tests.admin;

import com.paylease.app.qa.framework.AppConstant;
import com.paylease.app.qa.framework.DataBaseConnector;
import com.paylease.app.qa.framework.DataHelper;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.components.datatable.AdminAuditLogEntries;
import com.paylease.app.qa.framework.components.datatable.AdminFlexibleFeeCustomSettings;
import com.paylease.app.qa.framework.components.datatable.AdminFlexibleFeeCustomSettings.Columns;
import com.paylease.app.qa.framework.pages.admin.FlexibleFeeCustomSettingAddPage;
import com.paylease.app.qa.framework.pages.admin.FlexibleFeeCustomSettingEditPage;
import com.paylease.app.qa.framework.pages.admin.FlexibleFeeStructure;
import com.paylease.app.qa.framework.pages.admin.FlexibleFeeStructure.PaymentMethod;
import com.paylease.app.qa.framework.pages.admin.FlexibleFeeStructure.PaymentType;
import com.paylease.app.qa.framework.pages.admin.LoginPageAdmin;
import com.paylease.app.qa.framework.pages.admin.LogsPage;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.utility.database.client.dao.PmFeeTierDao;
import com.paylease.app.qa.framework.utility.database.client.dto.PmFeeTier;
import com.paylease.app.qa.framework.utility.listeners.Retry;
import com.paylease.app.qa.testbase.dataproviders.FlexibleFeeStructureDataProvider;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FlexibleFeeStructureTest extends ScriptBase {

  public static final String REGION = "admin";
  public static final String FEATURE = "FlexibleFeeStructure";

  static final String ALL_PROPERTIES = "All Properties";

  public enum Amounts {
    VALID,
    CHARS,
    NEGATIVE,
    BLANK
  }

  private static final Map<PaymentType, PaymentTypeSetup> DEFAULT_PAYMENT_TYPES = createPaymentTypeMap();

  private static Map<PaymentType, PaymentTypeSetup> createPaymentTypeMap() {
    Map<PaymentType, PaymentTypeSetup> map = new HashMap<>();
    map.put(PaymentType.ONE_TIME, PaymentTypeSetup.ONE_TIME);
    map.put(PaymentType.FIXED_AUTOPAY, PaymentTypeSetup.FIXED_AUTOPAY);
    map.put(PaymentType.VARIABLE_AUTOPAY, PaymentTypeSetup.VARIABLE_AUTOPAY);

    return map;
  }

  private static final Map<PaymentMethod, PaymentMethodSetup> DEFAULT_PAYMENT_METHODS = createPaymentMethodMap();

  private static Map<PaymentMethod, PaymentMethodSetup> createPaymentMethodMap() {
    Map<PaymentMethod, PaymentMethodSetup> map = new HashMap<>();
    map.put(PaymentMethod.ACH, PaymentMethodSetup.ACH);
    map.put(PaymentMethod.CREDIT, PaymentMethodSetup.CREDIT);
    map.put(PaymentMethod.DEBIT, PaymentMethodSetup.DEBIT);

    return map;
  }

  public enum PaymentTypeSetup {
    ONE_TIME("1"),
    FIXED_AUTOPAY("2"),
    VARIABLE_AUTOPAY("3");

    private final String paymentType;

    PaymentTypeSetup(String paymentType) {
      this.paymentType = paymentType;
    }

    String getValue() {
      return paymentType;
    }
  }

  public enum PaymentMethodSetup {
    ACH("1"),
    CREDIT("2"),
    DEBIT("3");

    private final String paymentMethod;

    PaymentMethodSetup(String paymentMethod) {
      this.paymentMethod = paymentMethod;
    }

    String getValue() {
      return paymentMethod;
    }
  }

  /**
   * Format the amount to pad with zeros and add the decimal.
   *
   * @param amount the amount
   * @return the formatted amount
   */
  static String fixAmount(String amount) {
    int zerosToAdd = 3 - amount.length();
    for (int i = zerosToAdd; i > 0; --i) {
      amount = "0" + amount;
    }
    int decimalIndex = amount.length() - 2;

    return amount.substring(0, decimalIndex) + "." + amount.substring(decimalIndex);
  }

  /**
   * Assert no audit log entries present for given PM for Setup Fees Changed event.
   *
   * @param pmId PM ID to filter
   * @param assertMsg Message to display in case of failure
   */
  static void assertNoLogEntries(String pmId, String assertMsg) {
    // open Audit Log page
    LogsPage logsPage = new LogsPage();
    logsPage.open();

    // filter by event and Pm ID
    logsPage.filter(null, null, null, pmId, null, "Setup Fees Changed");

    // assert no entries found
    Assert.assertEquals(logsPage.getLogEntryCount(), 0, assertMsg);
  }

  /**
   * Assert log entry in audit log table.
   *
   * @param pmId the pmId to filter on
   * @param expectedMessage the message to verify
   * @return the audit log message from the page
   */
  static String assertLogEntry(String pmId, String expectedMessage) {
    // open Audit Log page
    LogsPage logsPage = new LogsPage();
    logsPage.open();

    // filter by event and Pm ID
    logsPage.filter(null, null, null, pmId, null, "Setup Fees Changed");

    Assert.assertEquals(logsPage.getLogEntryCount(), 1,
        "Should be one log entry for PM Default Settings Updated");

    Map<String, String> rowData = logsPage.getRowData(0);

    String message = rowData.get(AdminAuditLogEntries.Columns.MESSAGE.getLabel());

    Assert.assertTrue(
        rowData.get(AdminAuditLogEntries.Columns.UPDATED_BY.getLabel())
            .startsWith(AppConstant.QA_ADMIN_NAME),
        "Update performed by logged in admin user.");
    Assert.assertEquals(rowData.get(AdminAuditLogEntries.Columns.TRANS_ID.getLabel()), "",
        "Update not related to a transaction.");
    Assert.assertEquals(rowData.get(AdminAuditLogEntries.Columns.AUTOPAY_ID.getLabel()), "",
        "Update not related to autopay.");
    Assert.assertTrue(
        rowData.get(AdminAuditLogEntries.Columns.USER_ID.getLabel()).contains("(" + pmId + ")"),
        "Update affects our test PM.");
    Assert.assertEquals(rowData.get(AdminAuditLogEntries.Columns.PROP_ID.getLabel()), "",
        "Update not related to a property.");
    Assert.assertTrue(
        message.startsWith(expectedMessage),
        "Update identifies all changed values with old and new values: actual: " + message
            + "\nexpected: " + expectedMessage);

    return message;
  }

  @Test
  public void pmWithNoSettingsGetsDefaults() {
    Logger.info(
        "To verify that the flexible fee structure page adds default fees."
    );

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6145");
    testSetupPage.open();

    final int pmId = Integer.parseInt(testSetupPage.getString("pmId"));

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeStructure flexibleFeeStructure = new FlexibleFeeStructure(pmId);
    flexibleFeeStructure.open();

    DataBaseConnector dataBaseConnector = new DataBaseConnector();
    dataBaseConnector.createConnection();
    Connection connection = dataBaseConnector.getConnection();

    PmFeeTierDao pmFeeTierDao = new PmFeeTierDao();

    Assert.assertEquals(pmFeeTierDao.getCountByPmId(connection, pmId), 9,
        "There should be nine rows for this pm: " + pmId);

    Object[][] defaultTiers = new Object[][]{
        {1, 1, "fixed", 295},
        {1, 2, "percent", 350},
        {1, 3, "fixed", 995},
        {2, 1, "fixed", 295},
        {2, 2, "percent", 350},
        {2, 3, "fixed", 995},
        {3, 1, "fixed", 295},
        {3, 2, "fixed", 4995},
        {3, 3, "fixed", 995},
    };

    for (Object[] defaultTier : defaultTiers) {
      PmFeeTier pmFeeTier = getPopulatedPmFeeTier(pmId, 0, (int) defaultTier[0],
          (int) defaultTier[1], "", 0, (String) defaultTier[2], (int) defaultTier[3], "resident");

      Assert.assertEquals(pmFeeTierDao.getCountOfPmFeeTier(connection, pmFeeTier), 1,
          "There should be one row with " + pmFeeTier);
    }
  }

  @Test
  public void pmWithExistingSettingsIsNotChanged() {
    Logger.info(
        "To verify the flexible fee structure page doesn't change PMs that are already configured."
    );

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6146");
    testSetupPage.open();

    final int pmId = Integer.parseInt(testSetupPage.getString("pmId"));
    final int propId = Integer.parseInt(testSetupPage.getString("propId"));
    final int paymentTypeId = Integer.parseInt(testSetupPage.getString("paymentTypeId"));
    final int paymentMethodId = Integer.parseInt(testSetupPage.getString("paymentMethodId"));
    final String varName = testSetupPage.getString("varName");
    final int tierAmount = Integer.parseInt(testSetupPage.getString("tierAmount"));
    final String feeType = testSetupPage.getString("feeType");
    final int feeValue = Integer.parseInt(testSetupPage.getString("feeValue"));
    final String feeIncur = testSetupPage.getString("feeIncur");

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeStructure flexibleFeeStructure = new FlexibleFeeStructure(pmId);
    flexibleFeeStructure.open();

    DataBaseConnector dataBaseConnector = new DataBaseConnector();
    dataBaseConnector.createConnection();
    Connection connection = dataBaseConnector.getConnection();

    PmFeeTierDao pmFeeTierDao = new PmFeeTierDao();

    Assert.assertEquals(pmFeeTierDao.getCountByPmId(connection, pmId), 1,
        "There should be one row for this pm: " + pmId);

    PmFeeTier pmFeeTier = getPopulatedPmFeeTier(pmId, propId, paymentTypeId, paymentMethodId,
        varName, tierAmount, feeType, feeValue, feeIncur);

    Assert.assertEquals(pmFeeTierDao.getCountOfPmFeeTier(connection, pmFeeTier), 1,
        "There should be one row with " + pmFeeTier);
  }

  @Test(
      dataProvider = "providePmsForReading",
      dataProviderClass = FlexibleFeeStructureDataProvider.class,
      retryAnalyzer = Retry.class
  )
  public void flexibleFeeStructurePageShowsCorrectValues(String testCaseId) throws Exception {
    Logger.info(testCaseId
        + " -To verify that the Flexible Fee Structure page displays correct values from the database."
    );

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCaseId);
    testSetupPage.open();

    final int pmId = Integer.valueOf(testSetupPage.getString("pmId"));
    final Map<String, String> expectedPageData = readTable(testSetupPage);

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeStructure flexibleFeeStructure = openFlexibleFeeStructurePage(pmId);

    assertDefaultValuesOnPage(flexibleFeeStructure, expectedPageData, true);
  }

  @Test
  public void testFlexibleFeeStructurePageUpdatesValues() throws Exception {
    Logger.info(
        "To verify that the Flexible Fee Structure page saves all values "
            + "to the database when they were changed."
    );

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6151");
    testSetupPage.open();

    final int pmId = Integer.valueOf(testSetupPage.getString("pmId"));

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeStructure flexibleFeeStructure = openFlexibleFeeStructurePage(pmId);

    Map<String, String> expectedValues = setDefaultsValuesOnPage(
        flexibleFeeStructure, Amounts.VALID, false, true
    );

    Assert.assertEquals(
        flexibleFeeStructure.getSuccessMessage(),
        "All Default Property Settings were successfully saved",
        "Default success message should be displayed"
    );

    assertDefaultValuesOnPage(flexibleFeeStructure, expectedValues, false);

    flexibleFeeStructure.open();

    assertDefaultValuesOnPage(flexibleFeeStructure, expectedValues, false);
  }

  @Test(
      dataProvider = "provideInvalidInput",
      dataProviderClass = FlexibleFeeStructureDataProvider.class,
      retryAnalyzer = Retry.class
  )
  public void defaultsFormWithInvalidInputCannotBeSubmitted(String testCaseId, Amounts amountType)
      throws Exception {
    Logger.info(
        testCaseId + " - To verify defaults form with invalid in amounts cannot be submitted"
    );

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCaseId);
    testSetupPage.open();

    final int pmId = Integer.valueOf(testSetupPage.getString("pmId"));
    final Map<String, String> expectedOriginalFormData = readTable(testSetupPage);

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeStructure flexibleFeeStructure = openFlexibleFeeStructurePage(pmId);

    setDefaultsValuesOnPage(flexibleFeeStructure, amountType, false, true);

    Assert.assertFalse(flexibleFeeStructure.isPageChanged(), "Page should not have been submitted");

    flexibleFeeStructure.open();

    assertDefaultValuesOnPage(flexibleFeeStructure, expectedOriginalFormData, true);
  }

  @Test(
      dataProvider = "provideInvalidInput",
      dataProviderClass = FlexibleFeeStructureDataProvider.class,
      retryAnalyzer = Retry.class
  )
  public void defaultsFormWithInvalidInputCannotBeSaved(String testCaseId, Amounts amountType)
      throws Exception {
    Logger.info(
        testCaseId + " - To verify defaults form with invalid amounts cannot be saved"
    );

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCaseId);
    testSetupPage.open();

    final int pmId = Integer.valueOf(testSetupPage.getString("pmId"));
    final Map<String, String> expectedOriginalFormData = readTable(testSetupPage);

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeStructure flexibleFeeStructure = openFlexibleFeeStructurePage(pmId);

    setDefaultsValuesOnPage(flexibleFeeStructure, amountType, true, true);

    Assert.assertTrue(flexibleFeeStructure.isErrorShown(),
        "Error should be shown");
    Assert.assertEquals(flexibleFeeStructure.getErrorMessage(), "Invalid data provided",
        "Unexpected error message");

    flexibleFeeStructure.open();

    assertDefaultValuesOnPage(flexibleFeeStructure, expectedOriginalFormData, true);
  }

  @Test
  public void pmLevelSaveButtonDoesNotSubmitOtherForms() throws Exception {
    Logger.info(
        "To validate Default Property Settings and Custom Settings are not saved when PM Level Settings \"Save\" button is clicked."
    );

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6594");
    testSetupPage.open();

    final int pmId = Integer.valueOf(testSetupPage.getString("pmId"));
    final String paymentField = testSetupPage.getString("paymentField1");
    final Map<String, String> expectedOriginalFormData = readTable(testSetupPage);

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeStructure flexibleFeeStructure = openFlexibleFeeStructurePage(pmId);

    setDefaultValues(flexibleFeeStructure, Amounts.VALID, false, true);

    HashMap<String, String> rowData = new HashMap<>();
    rowData.put(Columns.PAYMENT_FIELD.getLabel().toLowerCase(), paymentField);

    try {
      flexibleFeeStructure.toggleRowByRowData(rowData);
    } catch (Exception e) {
      Assert.fail(
          "Unable to select row for payment field '" + paymentField + "', exception: "
              + e.getMessage()
      );
    }

    flexibleFeeStructure.clickSaveSettingsBtnAndWait();

    openFlexibleFeeStructurePage(pmId);

    assertDefaultValuesOnPage(flexibleFeeStructure, expectedOriginalFormData, true);

    Assert.assertTrue(flexibleFeeStructure.isRowByRowDataPresent(rowData),
        "Undeleted row should be present");
  }

  @Test(
      dataProvider = "providePmLevelSettings",
      dataProviderClass = FlexibleFeeStructureDataProvider.class,
      retryAnalyzer = Retry.class
  )
  public void pmLevelSettingsDisplayed(String testCaseId) {
    Logger.info(testCaseId + " - To validate PM Level Settings are populated from the database.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCaseId);
    testSetupPage.open();

    final int pmId = Integer.valueOf(testSetupPage.getString("pmId"));
    final boolean simpleFeeEnabled = testSetupPage.getFlag("simpleFeeEnabled");
    final boolean roundingEnabled = testSetupPage.getFlag("roundingEnabled");
    final String pmIncurAchEnabled = testSetupPage.getString("pmIncurAchEnabled");
    final String pmIncurCcEnabled = testSetupPage.getString("pmIncurCcEnabled");
    final boolean phoneFeeDisabled = testSetupPage.getFlag("phoneFeeDisabled");
    final String phoneFeeAmount = testSetupPage.getString("phoneFeeAmount");
    final boolean expressFeeAchDisabled = testSetupPage.getFlag("expressFeeAchDisabled");
    final boolean expressFeeCcDisabled = testSetupPage.getFlag("expressFeeCcDisabled");
    final String expressFeeAmount = testSetupPage.getString("expressFeeAmount");

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeStructure flexibleFeeStructure = openFlexibleFeeStructurePage(pmId);

    assertPmLevelSettingsValuesOnPage(
        flexibleFeeStructure,
        !simpleFeeEnabled,
        roundingEnabled,
        pmIncurAchEnabled.equals("1") || pmIncurCcEnabled.equals("1"),
        !phoneFeeDisabled,
        phoneFeeAmount,
        !expressFeeAchDisabled || !expressFeeCcDisabled,
        expressFeeAmount
    );
  }

  @Test(
      dataProvider = "providePmLevelSettingsToUpdate",
      dataProviderClass = FlexibleFeeStructureDataProvider.class,
      retryAnalyzer = Retry.class
  )
  public void pmLevelSettingsUpdated(String testCaseId) {
    Logger.info(testCaseId
        + " - To verify that the Flexible Fee Structure page saves all PM Level Settings values to the database when they are changed.");

    DataHelper dataHelper = new DataHelper();

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCaseId);
    testSetupPage.open();

    final int pmId = Integer.valueOf(testSetupPage.getString("pmId"));

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeStructure flexibleFeeStructure = openFlexibleFeeStructurePage(pmId);

    final boolean ccAchFeeChecked = flexibleFeeStructure.isCcAchFeeEnabled();
    final boolean roundingChecked = flexibleFeeStructure.isRoundingEnabled();
    final boolean pmOneTimeFeeIncurChecked = flexibleFeeStructure.isPmOneTimeFeeIncurEnabled();
    final boolean phoneFeeChecked = flexibleFeeStructure.isPhoneFeeEnabled();
    final boolean expressPayChecked = flexibleFeeStructure.isExpressPayEnabled();

    String expectedPhoneFee = dataHelper.getMoneyAmount(0, 10);
    String expectedExpressPayFee = dataHelper.getMoneyAmount(0, 10);

    setPmLevelSettingsValuesOnPage(flexibleFeeStructure, expectedPhoneFee, expectedExpressPayFee);
    flexibleFeeStructure.clickSaveSettingsBtnAndWait();

    Assert.assertEquals(
        flexibleFeeStructure.getSuccessMessage(),
        "All PM Level Settings were successfully saved",
        "PM Settings success message should be displayed"
    );

    assertPmLevelSettingsValuesOnPage(
        flexibleFeeStructure,
        !ccAchFeeChecked,
        !roundingChecked,
        !pmOneTimeFeeIncurChecked,
        !phoneFeeChecked,
        expectedPhoneFee,
        !expressPayChecked,
        expectedExpressPayFee
    );

    flexibleFeeStructure = openFlexibleFeeStructurePage(pmId);

    assertPmLevelSettingsValuesOnPage(
        flexibleFeeStructure,
        !ccAchFeeChecked,
        !roundingChecked,
        !pmOneTimeFeeIncurChecked,
        !phoneFeeChecked,
        expectedPhoneFee,
        !expressPayChecked,
        expectedExpressPayFee
    );
  }

  @Test(
      dataProvider = "provideInvalidPmLevelSettings",
      dataProviderClass = FlexibleFeeStructureDataProvider.class,
      retryAnalyzer = Retry.class
  )
  public void pmLevelSettingsFormWithInvalidFieldsCannotBeSubmitted(String testCaseId,
      Amounts amountType) {
    Logger.info(testCaseId
        + " - To verify the PM Level Settings form with some invalid amounts cannot be submitted and an error is presented."
    );

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCaseId);
    testSetupPage.open();

    final int pmId = Integer.valueOf(testSetupPage.getString("pmId"));

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeStructure flexibleFeeStructure = openFlexibleFeeStructurePage(pmId);

    final boolean ccAchFeeChecked = flexibleFeeStructure.isCcAchFeeEnabled();
    final boolean roundingChecked = flexibleFeeStructure.isRoundingEnabled();
    final boolean pmOneTimeFeeIncurChecked = flexibleFeeStructure.isPmOneTimeFeeIncurEnabled();
    final boolean phoneFeeChecked = flexibleFeeStructure.isPhoneFeeEnabled();
    final boolean expressPayChecked = flexibleFeeStructure.isExpressPayEnabled();
    final String phoneFeeAmount = flexibleFeeStructure.getPhoneFee();
    final String expressPayAmount = flexibleFeeStructure.getExpressPayFee();

    String enteredPhoneFee = getAmountByType(amountType, phoneFeeAmount);
    String enteredExpressPayFee = getAmountByType(amountType, expressPayAmount);

    flexibleFeeStructure.setPhoneFee(enteredPhoneFee);
    flexibleFeeStructure.setExpressFee(enteredExpressPayFee);
    flexibleFeeStructure.preparePageUnload();
    flexibleFeeStructure.clickSaveSettingsBtnNoWait();

    Assert.assertFalse(flexibleFeeStructure.isPageChanged(), "Page should not have submitted");

    flexibleFeeStructure.open();

    assertPmLevelSettingsValuesOnPage(
        flexibleFeeStructure,
        ccAchFeeChecked,
        roundingChecked,
        pmOneTimeFeeIncurChecked,
        phoneFeeChecked,
        phoneFeeAmount,
        expressPayChecked,
        expressPayAmount
    );
  }

  @Test(
      dataProvider = "provideInvalidPmLevelSettings",
      dataProviderClass = FlexibleFeeStructureDataProvider.class,
      retryAnalyzer = Retry.class
  )
  public void pmLevelSettingsFormWithInvalidFieldsCannotBeSaved(String testCaseId,
      Amounts amountType) {
    Logger.info(testCaseId
        + " - To verify the PM Level Settings form with some invalid amounts cannot be saved and an error is presented."
    );

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCaseId);
    testSetupPage.open();

    final int pmId = Integer.valueOf(testSetupPage.getString("pmId"));

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeStructure flexibleFeeStructure = openFlexibleFeeStructurePage(pmId);

    final boolean ccAchFeeChecked = flexibleFeeStructure.isCcAchFeeEnabled();
    final boolean roundingChecked = flexibleFeeStructure.isRoundingEnabled();
    final boolean pmOneTimeFeeIncurChecked = flexibleFeeStructure.isPmOneTimeFeeIncurEnabled();
    final boolean phoneFeeChecked = flexibleFeeStructure.isPhoneFeeEnabled();
    final boolean expressPayChecked = flexibleFeeStructure.isExpressPayEnabled();
    final String phoneFeeAmount = flexibleFeeStructure.getPhoneFee();
    final String expressPayAmount = flexibleFeeStructure.getExpressPayFee();

    String enteredPhoneFee = getAmountByType(amountType, phoneFeeAmount);
    String enteredExpressPayFee = getAmountByType(amountType, expressPayAmount);

    flexibleFeeStructure.disableValidationPhoneFeeInput();
    flexibleFeeStructure.disableValidationExpressFeeInput();
    flexibleFeeStructure.setPhoneFee(enteredPhoneFee);
    flexibleFeeStructure.setExpressFee(enteredExpressPayFee);
    flexibleFeeStructure.clickSaveSettingsBtnAndWait();

    Assert.assertTrue(flexibleFeeStructure.isErrorShown(),
        "Error should be shown");

    Assert.assertEquals(flexibleFeeStructure.getErrorMessage(), "Invalid data provided",
        "Unexpected error message");

    flexibleFeeStructure.open();

    assertPmLevelSettingsValuesOnPage(
        flexibleFeeStructure,
        ccAchFeeChecked,
        roundingChecked,
        pmOneTimeFeeIncurChecked,
        phoneFeeChecked,
        phoneFeeAmount,
        expressPayChecked,
        expressPayAmount
    );
  }

  @Test
  public void defaultSaveButtonDoesNotSubmitOtherForms() {
    Logger.info(
        "To validate PM Level Settings and Custom Settings are not saved when default property settings \"Save\" button is clicked.");

    DataHelper dataHelper = new DataHelper();

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6594");
    testSetupPage.open();

    final int pmId = Integer.valueOf(testSetupPage.getString("pmId"));
    final String paymentField = testSetupPage.getString("paymentField1");

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeStructure flexibleFeeStructure = openFlexibleFeeStructurePage(pmId);

    final boolean ccAchFeeChecked = flexibleFeeStructure.isCcAchFeeEnabled();
    final boolean roundingChecked = flexibleFeeStructure.isRoundingEnabled();
    final boolean pmOneTimeFeeIncurChecked = flexibleFeeStructure.isPmOneTimeFeeIncurEnabled();
    final boolean phoneFeeChecked = flexibleFeeStructure.isPhoneFeeEnabled();
    final boolean expressPayChecked = flexibleFeeStructure.isExpressPayEnabled();
    final String phoneFeeAmount = flexibleFeeStructure.getPhoneFee();
    final String expressPayAmount = flexibleFeeStructure.getExpressPayFee();

    String enteredPhoneFee = dataHelper.getMoneyAmount(0, 10);
    String enteredExpressPayFee = dataHelper.getMoneyAmount(0, 10);

    setPmLevelSettingsValuesOnPage(flexibleFeeStructure, enteredPhoneFee, enteredExpressPayFee);

    HashMap<String, String> rowData = new HashMap<>();
    rowData.put(Columns.PAYMENT_FIELD.getLabel().toLowerCase(), paymentField);

    try {
      flexibleFeeStructure.toggleRowByRowData(rowData);
    } catch (Exception e) {
      Assert.fail(
          "Unable to select row for payment field '" + paymentField + "', exception: "
              + e.getMessage()
      );
    }

    // Click the other save button
    flexibleFeeStructure.clickSaveDefaultsBtnAndWait();

    assertPmLevelSettingsValuesOnPage(
        flexibleFeeStructure,
        ccAchFeeChecked,
        roundingChecked,
        pmOneTimeFeeIncurChecked,
        phoneFeeChecked,
        phoneFeeAmount,
        expressPayChecked,
        expressPayAmount
    );

    flexibleFeeStructure = openFlexibleFeeStructurePage(pmId);

    assertPmLevelSettingsValuesOnPage(
        flexibleFeeStructure,
        ccAchFeeChecked,
        roundingChecked,
        pmOneTimeFeeIncurChecked,
        phoneFeeChecked,
        phoneFeeAmount,
        expressPayChecked,
        expressPayAmount
    );

    Assert.assertTrue(flexibleFeeStructure.isRowByRowDataPresent(rowData),
        "Undeleted row should be present");
  }

  @Test
  public void pmLevelSettingsNoAuditLogInvalidForm() {
    Logger.info("Verify no Audit Log entry when invalid PM Level Settings form cannot be saved.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6575");
    testSetupPage.open();

    final int pmId = Integer.valueOf(testSetupPage.getString("pmId"));

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeStructure flexibleFeeStructure = openFlexibleFeeStructurePage(pmId);

    flexibleFeeStructure.setPhoneFee("");
    flexibleFeeStructure.clickSaveSettingsBtnNoWait();

    assertNoLogEntries(String.valueOf(pmId), "Should be no log when form was not submitted");
  }

  @Test
  public void defaultSettingsNoAuditLogInvalidForm() throws Exception {
    Logger.info(
        "Verify no Audit Log entry when invalid Default Property Settings form cannot be saved.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6575");
    testSetupPage.open();

    final int pmId = Integer.valueOf(testSetupPage.getString("pmId"));

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeStructure flexibleFeeStructure = openFlexibleFeeStructurePage(pmId);

    flexibleFeeStructure.setAmount(PaymentType.ONE_TIME, PaymentMethod.ACH, "");
    flexibleFeeStructure.clickSaveDefaultsBtnNoWait();

    assertNoLogEntries(String.valueOf(pmId), "Should be no log when form was not submitted");
  }

  @Test
  public void pmLevelSettingsNoAuditLogNoChanges() {
    Logger
        .info("Verify no Audit Log entry when PM Level Settings form save results in no changes.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6575");
    testSetupPage.open();

    final int pmId = Integer.valueOf(testSetupPage.getString("pmId"));

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeStructure flexibleFeeStructure = openFlexibleFeeStructurePage(pmId);

    flexibleFeeStructure.clickSaveSettingsBtnAndWait();

    assertNoLogEntries(String.valueOf(pmId),
        "Should be no log when form was saved with no changes");
  }

  @Test
  public void defaultSettingsNoAuditLogNoChanges() {
    Logger.info(
        "Verify no Audit Log entry when Default Property Settings form save results in no changes.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6575");
    testSetupPage.open();

    final int pmId = Integer.valueOf(testSetupPage.getString("pmId"));

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeStructure flexibleFeeStructure = openFlexibleFeeStructurePage(pmId);

    flexibleFeeStructure.clickSaveDefaultsBtnNoWait();

    assertNoLogEntries(String.valueOf(pmId),
        "Should be no log when form was saved with no changes");
  }

  @Test
  public void pmLevelSettingsAuditLogShowsAllChangesUpdated() {
    Logger.info("Verify one Audit Log entry with all PM Level Settings changed on form save.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6575");
    testSetupPage.open();

    final int pmId = Integer.valueOf(testSetupPage.getString("pmId"));

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeStructure flexibleFeeStructure = openFlexibleFeeStructurePage(pmId);

    final boolean ccAchFeeChecked = flexibleFeeStructure.isCcAchFeeEnabled();
    final boolean roundingChecked = flexibleFeeStructure.isRoundingEnabled();
    final boolean pmOneTimeFeeIncurChecked = flexibleFeeStructure.isPmOneTimeFeeIncurEnabled();
    final boolean phoneFeeChecked = flexibleFeeStructure.isPhoneFeeEnabled();
    final boolean expressPayChecked = flexibleFeeStructure.isExpressPayEnabled();
    final String phoneFeeAmount = flexibleFeeStructure.getPhoneFee();
    final String expressPayAmount = flexibleFeeStructure.getExpressPayFee();

    DataHelper dataHelper = new DataHelper();
    String expectedPhoneFee = dataHelper.getMoneyAmount(0, 10);
    String expectedExpressPayFee = dataHelper.getMoneyAmount(0, 10);

    setPmLevelSettingsValuesOnPage(flexibleFeeStructure, expectedPhoneFee, expectedExpressPayFee);
    flexibleFeeStructure.clickSaveSettingsBtnAndWait();

    assertPmLevelAuditLogMessage(
        String.valueOf(pmId), true, ccAchFeeChecked, true,
        roundingChecked, true, pmOneTimeFeeIncurChecked,
        true, phoneFeeChecked, true, phoneFeeAmount, expectedPhoneFee,
        true, expressPayChecked, true, expressPayAmount, expectedExpressPayFee
    );
  }

  @Test
  public void defaultSettingsAuditLogShowsAllChangesUpdated() throws Exception {
    Logger.info(
        "Verify one Audit Log entry with all Default Property Settings changed on form save.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6575");
    testSetupPage.open();

    final int pmId = Integer.valueOf(testSetupPage.getString("pmId"));
    final Map<String, String> expectedOriginalFormData = readTable(testSetupPage);

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeStructure flexibleFeeStructure = openFlexibleFeeStructurePage(pmId);

    Map<String, String> expectedValues = setDefaultsValuesOnPage(
        flexibleFeeStructure, Amounts.VALID, false, true
    );

    assertDefaultSettingsAuditLogMessage(
        String.valueOf(pmId), "PM Flexible Fee Default Property Settings updated:",
        expectedOriginalFormData, expectedValues
    );
  }

  @Test
  public void pmLevelSettingsAuditLogShowsOnlyChangedValues() {
    Logger.info("Verify one Audit Log entry with each PM Level Setting changed on form save.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6575");
    testSetupPage.open();

    final int pmId = Integer.valueOf(testSetupPage.getString("pmId"));

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeStructure flexibleFeeStructure = openFlexibleFeeStructurePage(pmId);

    final boolean ccAchFeeChecked = flexibleFeeStructure.isCcAchFeeEnabled();
    final boolean roundingChecked = flexibleFeeStructure.isRoundingEnabled();
    final boolean pmOneTimeFeeIncurChecked = flexibleFeeStructure.isPmOneTimeFeeIncurEnabled();
    final boolean phoneFeeChecked = flexibleFeeStructure.isPhoneFeeEnabled();
    final boolean expressPayChecked = flexibleFeeStructure.isExpressPayEnabled();
    final String phoneFeeAmount = flexibleFeeStructure.getPhoneFee();
    final String expressPayAmount = flexibleFeeStructure.getExpressPayFee();

    DataHelper dataHelper = new DataHelper();
    String expectedPhoneFee = dataHelper.getMoneyAmount(0, 10);
    String expectedExpressPayFee = dataHelper.getMoneyAmount(0, 10);

    boolean updateCcAchFee = dataHelper.getBoolean();
    boolean updateRounding = dataHelper.getBoolean();
    boolean updatePmOneTimeFeeIncur = dataHelper.getBoolean();
    boolean updatePhoneFee = dataHelper.getBoolean();
    boolean updatePhoneFeeAmount = dataHelper.getBoolean();
    boolean updateExpressPay = dataHelper.getBoolean();
    boolean updateExpressPayAmount = dataHelper.getBoolean();

    // make sure we update SOMETHING
    if (!updateCcAchFee && !updateRounding && !updatePmOneTimeFeeIncur && !updatePhoneFee
        && !updatePhoneFeeAmount && !updateExpressPay && !updateExpressPayAmount) {
      updateCcAchFee = true;
    }

    if (updateCcAchFee) {
      flexibleFeeStructure.clickCcAchFeeCheckbox();
    }
    if (updateRounding) {
      flexibleFeeStructure.clickRoundingCheckbox();
    }
    if (updatePmOneTimeFeeIncur) {
      flexibleFeeStructure.clickPmOneTimeFeeIncurCheckbox();
    }
    if (updatePhoneFee) {
      flexibleFeeStructure.clickPhoneFeeCheckbox();
    }
    if (updatePhoneFeeAmount) {
      flexibleFeeStructure.setPhoneFee(expectedPhoneFee);
    }
    if (updateExpressPay) {
      flexibleFeeStructure.clickExpressPayCheckbox();
    }
    if (updateExpressPayAmount) {
      flexibleFeeStructure.setExpressFee(expectedExpressPayFee);
    }
    flexibleFeeStructure.clickSaveSettingsBtnAndWait();

    assertPmLevelAuditLogMessage(
        String.valueOf(pmId), updateCcAchFee, ccAchFeeChecked, updateRounding,
        roundingChecked, updatePmOneTimeFeeIncur, pmOneTimeFeeIncurChecked,
        updatePhoneFee, phoneFeeChecked, updatePhoneFeeAmount, phoneFeeAmount, expectedPhoneFee,
        updateExpressPay, expressPayChecked, updateExpressPayAmount, expressPayAmount,
        expectedExpressPayFee
    );
  }

  @Test
  public void defaultSettingsAuditLogShowsOnlyChangedValues() throws Exception {
    Logger.info(
        "Verify one Audit Log entry with each Default Property Setting changed on form save.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6575");
    testSetupPage.open();

    final int pmId = Integer.valueOf(testSetupPage.getString("pmId"));
    final Map<String, String> expectedOriginalFormData = readTable(testSetupPage);

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeStructure flexibleFeeStructure = openFlexibleFeeStructurePage(pmId);

    Map<String, String> expectedValues = setDefaultsValuesOnPage(
        flexibleFeeStructure, Amounts.VALID, false, false
    );

    assertDefaultSettingsAuditLogMessage(
        String.valueOf(pmId), "PM Flexible Fee Default Property Settings updated:",
        expectedOriginalFormData, expectedValues);
  }

  @Test
  public void auditLogOnInitializeDefaults() {
    Logger.info(
        "Verify one Audit Log entry when Default Property Settings initialized on page load.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6588");
    testSetupPage.open();

    final int pmId = Integer.valueOf(testSetupPage.getString("pmId"));

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    openFlexibleFeeStructurePage(pmId);

    assertDefaultSettingsInitializedAuditLogMessage(String.valueOf(pmId));
  }

  @Test
  public void noAuditLogAlreadyInitialized() {
    Logger.info(
        "Verify no Audit Log entry when Default Property Settings already initialized prior to page load.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6575");
    testSetupPage.open();

    final int pmId = Integer.valueOf(testSetupPage.getString("pmId"));

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    openFlexibleFeeStructurePage(pmId);

    assertNoLogEntries(String.valueOf(pmId),
        "Should be no log when default initialization already occurred");
  }

  @Test
  public void customSettingsShowsTable() {
    Logger.info("To validate the custom settings table is displayed.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6593");
    testSetupPage.open();

    final int pmId = Integer.valueOf(testSetupPage.getString("pmId"));

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeStructure flexibleFeeStructure = openFlexibleFeeStructurePage(pmId);

    Assert.assertTrue(flexibleFeeStructure.isCustomSettingsTableSearchPresent(),
        "Custom Settings search should be present on top of table");

    Assert.assertTrue(flexibleFeeStructure.isCustomSettingsTablePaginationPresent(),
        "Custom Settings pagination should be present on top and bottom of table");

    Assert.assertTrue(flexibleFeeStructure.isCustomSettingsTablePageSizeSelectorPresent(),
        "Custom Settings page size selector should be present on top and bottom of table");

    Assert.assertTrue(flexibleFeeStructure.isCustomSettingsTableInfoLabelPresent(),
        "Custom Settings table info label should be present on bottom of table");

    Assert.assertTrue(flexibleFeeStructure.isCustomSettingsTableHeaderPresent(),
        "Custom Settings table header columns should be present");

    Assert.assertFalse(flexibleFeeStructure.doesCustomSettingsTableHeaderAllowSorting(),
        "Custom Settings table header columns should not allow sorting");

    Assert.assertTrue(flexibleFeeStructure.isCustomSettingsDeleteButtonPresent(),
        "Custom Settings delete button should be present");

    List<String> labels = flexibleFeeStructure.getCustomSettingsSelectFilterButtons();

    Assert.assertEquals(labels.size(), 3, "There should be three select filter buttons present");

    Assert.assertEquals(labels.get(0), "All",
        "Custom Settings table should have the select 'All' filter button");
    Assert.assertEquals(labels.get(1), "Selected",
        "Custom Settings table should have the select 'Select' filter button");
    Assert.assertEquals(labels.get(2), "Not Selected",
        "Custom Settings table should have the select 'Not Selected' filter button");
  }

  @Test
  public void customSettingsTableShowsNoDataAvailable() {
    Logger.info(
        "To verify that when there are no Custom Settings for a PM the table shows 'No Data Available'");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6590");
    testSetupPage.open();

    final int pmId = Integer.valueOf(testSetupPage.getString("pmId"));

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeStructure flexibleFeeStructure = openFlexibleFeeStructurePage(pmId);

    Assert.assertTrue(flexibleFeeStructure.isCustomSettingsTableNoDataAvailableMessagePresent(),
        "Custom Settings table body should show 'No Data Available' message when there is no data");

    Assert.assertTrue(flexibleFeeStructure.doesCustomSettingsTableInfoLabelShowZeroEntries(),
        "Custom Settings table info label should show zero entries when there is no data");
  }

  @Test(
      dataProvider = "provideSearchTerms",
      dataProviderClass = FlexibleFeeStructureDataProvider.class,
      retryAnalyzer = Retry.class
  )
  public void customSettingsTableSearch(String searchTerm, boolean expectToFind) {
    Logger.info("To validate that rows can be found via a search term");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6594");
    testSetupPage.open();

    final int pmId = Integer.valueOf(testSetupPage.getString("pmId"));

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeStructure flexibleFeeStructure = openFlexibleFeeStructurePage(pmId);

    int originalRowCount = flexibleFeeStructure.getCustomSettingsTableRowCount();

    flexibleFeeStructure.customSettingsTableEnterSearchTerm(searchTerm);

    int afterSearchRowCount = flexibleFeeStructure.getCustomSettingsTableRowCount();

    if (expectToFind) {
      Assert.assertEquals(originalRowCount - afterSearchRowCount, 1,
          "Custom Settings table should have removed rows that do not contain the search term.");
    } else {
      Assert.assertEquals(afterSearchRowCount, 0,
          "Custom Settings table should be empty when searching for something that is not in the table.");
    }
  }

  @Test(
      dataProvider = "provideCustomSettingData",
      dataProviderClass = FlexibleFeeStructureDataProvider.class,
      retryAnalyzer = Retry.class
  )
  public void customSettingsTableContainsData(
      String testCaseId, AdminFlexibleFeeCustomSettings.Columns column, String expectedValueField,
      String expectedValueIfBlank
  ) {
    Logger.info("To verify that the table displays all custom settings values");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCaseId);
    testSetupPage.open();

    final int pmId = Integer.valueOf(testSetupPage.getString("pmId"));
    String expectedValue;
    try {
      expectedValue = testSetupPage.getString(expectedValueField);
    } catch (Exception e) {
      expectedValue = "";
    }

    if (expectedValue.isEmpty()) {
      expectedValue = expectedValueIfBlank;
    }

    if (column == AdminFlexibleFeeCustomSettings.Columns.FEE_AMOUNT) {
      expectedValue = fixAmount(expectedValue);
    }

    if (column == AdminFlexibleFeeCustomSettings.Columns.TIER_AMOUNT) {
      if (!expectedValue.isEmpty() && Integer.parseInt(expectedValue) > 0) {
        expectedValue = fixAmount(expectedValue);
      } else {
        expectedValue = expectedValueIfBlank;
      }
    }

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeStructure flexibleFeeStructure = openFlexibleFeeStructurePage(pmId);

    Assert.assertTrue(
        flexibleFeeStructure.customSettingsTableRowHasValueInColumn(0, column, expectedValue),
        "Custom Settings table shows '" + expectedValue + "' for column: " + column.getLabel());
  }

  @Test
  public void customSettingsTableIsSorted() {
    Logger.info("To verify that the data in the table is shown in this sorted order:\n"
        + "\n"
        + "Property Name (with \"All Properties\" first) alpha order\n"
        + "Payment Field alpha order\n"
        + "Payment Type alpha order\n"
        + "Tier Amount ascending order");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6591");
    testSetupPage.open();

    final int pmId = Integer.valueOf(testSetupPage.getString("pmId"));

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeStructure flexibleFeeStructure = openFlexibleFeeStructurePage(pmId);

    List<Map<String, String>> tableRowData = flexibleFeeStructure
        .getCustomSettingsTableRowContent();

    String lastPropertyName = "";
    String lastPaymentField = "";
    String lastPaymentType = "";
    double lastTierAmount = 0.0;

    for (int i = 0; i < tableRowData.size(); i++) {
      Map<String, String> rowData = tableRowData.get(i);

      String currentPropertyName = rowData
          .get(AdminFlexibleFeeCustomSettings.Columns.PROPERTY_NAME.getLabel().toLowerCase());
      String currentPaymentField = rowData
          .get(AdminFlexibleFeeCustomSettings.Columns.PAYMENT_FIELD.getLabel().toLowerCase());
      String currentPaymentType = rowData
          .get(AdminFlexibleFeeCustomSettings.Columns.PAYMENT_TYPE.getLabel().toLowerCase());
      String tierAmount = rowData
          .get(AdminFlexibleFeeCustomSettings.Columns.TIER_AMOUNT.getLabel().toLowerCase());

      double currentTierAmount =
          tierAmount.isEmpty() ? 0.0 : Double.parseDouble(tierAmount.replaceAll(",", ""));

      if (lastPropertyName.isEmpty()) {
        lastPropertyName = currentPropertyName;
        lastPaymentField = currentPaymentField;
        lastPaymentType = currentPaymentType;
        lastTierAmount = currentTierAmount;
        continue;
      }

      if (currentPropertyName.compareToIgnoreCase(lastPropertyName) > 0) {
        if (currentPropertyName.equalsIgnoreCase(ALL_PROPERTIES)) {
          Assert.fail("All 'All Properties' rows must come at the beginning");
        }

        lastPropertyName = currentPropertyName;
        lastPaymentField = currentPaymentField;
        lastPaymentType = currentPaymentType;
        lastTierAmount = currentTierAmount;
        continue;
      }

      if (currentPropertyName.compareToIgnoreCase(lastPropertyName) < 0) {
        if (lastPropertyName.equalsIgnoreCase(ALL_PROPERTIES)) {
          lastPropertyName = currentPropertyName;
          lastPaymentField = currentPaymentField;
          lastPaymentType = currentPaymentType;
          lastTierAmount = currentTierAmount;
          continue;
        }
        Assert.fail(
            "Property Name out of order in row " + (i + 1) + ": '" + currentPropertyName
                + "' should have come before '" + lastPropertyName + "'");
      }

      if (currentPaymentField.compareToIgnoreCase(lastPaymentField) > 0) {
        lastPropertyName = currentPropertyName;
        lastPaymentField = currentPaymentField;
        lastPaymentType = currentPaymentType;
        lastTierAmount = currentTierAmount;
        continue;
      }
      if (currentPaymentField.compareToIgnoreCase(lastPaymentField) < 0) {
        Assert.fail(
            "Payment Field out of order in row " + (i + 1) + ": '" + currentPaymentField
                + "' should have come before '" + lastPaymentField + "'");
      }

      if (currentPaymentType.compareToIgnoreCase(lastPaymentType) > 0) {
        lastPropertyName = currentPropertyName;
        lastPaymentField = currentPaymentField;
        lastPaymentType = currentPaymentType;
        lastTierAmount = currentTierAmount;
        continue;
      }
      if (currentPaymentType.compareToIgnoreCase(lastPaymentType) < 0) {
        Assert.fail(
            "Payment Type out of order in row " + (i + 1) + ": '" + currentPaymentType
                + "' should have come before '" + lastPaymentType + "'");
      }

      if (currentTierAmount > lastTierAmount) {
        lastPropertyName = currentPropertyName;
        lastPaymentField = currentPaymentField;
        lastPaymentType = currentPaymentType;
        lastTierAmount = currentTierAmount;
        continue;
      }
      if (currentTierAmount < lastTierAmount) {
        Assert.fail(
            "Tier Amount out of order in row " + (i + 1) + ": '" + currentTierAmount
                + "' should have come before '" + lastTierAmount + "'");
      }
    }
    Assert.assertTrue(true, "All Custom Settings table rows sorted properly");
  }

  @Test
  public void customSettingsTableHasEditLinks() {
    Logger.info(
        "To validate that there is an edit link for each custom setting that takes the user to the edit form for that fee");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6594");
    testSetupPage.open();

    final String pmId = testSetupPage.getString("pmId");
    final String feeId1 = testSetupPage.getString("feeId1");
    final String feeId2 = testSetupPage.getString("feeId2");
    final String paymentField1 = testSetupPage.getString("paymentField1");
    final String paymentField2 = testSetupPage.getString("paymentField2");

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    assertCustomSettingRowHasValidEditLink(pmId, feeId1, paymentField1);
    assertCustomSettingRowHasValidEditLink(pmId, feeId2, paymentField2);
  }

  @Test
  public void customSettingsTableHasCheckboxForDelete() {
    Logger.info("To validate there is a checkbox for each row that is selectable");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6594");
    testSetupPage.open();

    final String pmId = testSetupPage.getString("pmId");
    final String paymentField1 = testSetupPage.getString("paymentField1");
    final String paymentField2 = testSetupPage.getString("paymentField2");

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    assertCustomSettingRowHasSelectableCheckbox(pmId, paymentField1);
    assertCustomSettingRowHasSelectableCheckbox(pmId, paymentField2);
  }

  @Test
  public void customSettingDeleteShowsConfirmation() {
    Logger.info(
        "To validate a javascript confirmation presented when user is trying to delete a custom setting"
    );

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6594");
    testSetupPage.open();

    final String pmId = testSetupPage.getString("pmId");
    final String paymentField = testSetupPage.getString("paymentField1");

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeStructure flexibleFeeStructure = new FlexibleFeeStructure(Integer.valueOf(pmId));
    flexibleFeeStructure.open();

    HashMap<String, String> rowData = new HashMap<>();
    rowData.put(Columns.PAYMENT_FIELD.getLabel().toLowerCase(), paymentField);

    try {
      flexibleFeeStructure.toggleRowByRowData(rowData);
    } catch (Exception e) {
      Assert.fail(
          "Unable to select row for payment field '" + paymentField + "', exception: "
              + e.getMessage()
      );
    }

    flexibleFeeStructure.clickDelete();

    Assert.assertTrue(flexibleFeeStructure.isAlertPresent(), "Alert should be present");

    Pattern pattern = Pattern.compile(
        "You are about to delete (\\d)+ custom settings?\\. Are you sure you want to continue\\?");
    Matcher matcher = pattern.matcher(flexibleFeeStructure.getAlertText());

    Assert.assertTrue(matcher.find(), "Confirmation message should be properly formatted");
    Assert.assertEquals(matcher.group(1), "1", "There should be one row deleted");
  }

  @Test
  public void customSettingDeleteShowsAlertWhenNoRowsSelected() {
    Logger.info(
        "To validate there is a javascript alert that lets the user know to select some rows to delete when no rows are selected");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6594");
    testSetupPage.open();

    final String pmId = testSetupPage.getString("pmId");

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeStructure flexibleFeeStructure = new FlexibleFeeStructure(Integer.valueOf(pmId));
    flexibleFeeStructure.open();

    flexibleFeeStructure.clickDelete();

    Assert.assertTrue(flexibleFeeStructure.isAlertPresent(), "Alert should be present");

    Assert.assertEquals(flexibleFeeStructure.getAlertText(), "Please select some rows to delete.",
        "Alert message should be properly formatted");
  }

  @Test
  public void customSettingsTableCancelConfirmationDoesNotDeleteRows() {
    Logger.info(
        "To validate the custom settings have not been deleted when user clicks cancel button on javascript confirmation");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6594");
    testSetupPage.open();

    final String pmId = testSetupPage.getString("pmId");
    final String paymentField1 = testSetupPage.getString("paymentField1");
    final String paymentField2 = testSetupPage.getString("paymentField2");

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeStructure flexibleFeeStructure = new FlexibleFeeStructure(Integer.valueOf(pmId));
    flexibleFeeStructure.open();

    HashMap<String, String> rowDataPaymentField1 = new HashMap<>();
    rowDataPaymentField1.put(Columns.PAYMENT_FIELD.getLabel().toLowerCase(), paymentField1);

    HashMap<String, String> rowDataPaymentField2 = new HashMap<>();
    rowDataPaymentField2.put(Columns.PAYMENT_FIELD.getLabel().toLowerCase(), paymentField2);

    try {
      flexibleFeeStructure.toggleRowByRowData(rowDataPaymentField1);
    } catch (Exception e) {
      Assert.fail(
          "Unable to select row for payment field '" + paymentField1 + "', exception: " + e
              .getMessage()
      );
    }

    flexibleFeeStructure.preparePageUnload();
    flexibleFeeStructure.clickDelete();
    flexibleFeeStructure.clickCancelOnConfirm();

    Assert.assertFalse(flexibleFeeStructure.isPageChanged(), "Form should not be submitted");

    flexibleFeeStructure.open();

    List<HashMap<String, String>> rowsToFind = new ArrayList<>();
    rowsToFind.add(rowDataPaymentField1);
    rowsToFind.add(rowDataPaymentField2);
    assertCustomSettingRowsArePresent(flexibleFeeStructure, rowsToFind);
  }

  @Test
  public void customSettingNoAuditLogWhenUserCancelsDelete() {
    Logger.info(
        "To validate no audit log is created when form does not submit due to user clicks cancel on javascript confirmation"
    );

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6594");
    testSetupPage.open();

    final String pmId = testSetupPage.getString("pmId");
    final String paymentField = testSetupPage.getString("paymentField1");

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeStructure flexibleFeeStructure = new FlexibleFeeStructure(Integer.valueOf(pmId));
    flexibleFeeStructure.open();

    HashMap<String, String> rowDataPaymentField = new HashMap<>();
    rowDataPaymentField.put(Columns.PAYMENT_FIELD.getLabel().toLowerCase(), paymentField);

    try {
      flexibleFeeStructure.toggleRowByRowData(rowDataPaymentField);
    } catch (Exception e) {
      Assert.fail(
          "Unable to select row for payment field '" + paymentField + "', exception: "
              + e.getMessage()
      );
    }

    flexibleFeeStructure.clickDelete();
    flexibleFeeStructure.clickCancelOnConfirm();

    assertNoLogEntries(pmId, "There should be no audit log entry for no changes");
  }

  @Test
  public void customSettingNoAuditLogWhenUserSelectsNoRowsToDelete() {
    Logger.info(
        "To validate no audit log is created when form does not submit due to no rows selected to delete"
    );

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6594");
    testSetupPage.open();

    final String pmId = testSetupPage.getString("pmId");

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeStructure flexibleFeeStructure = new FlexibleFeeStructure(Integer.valueOf(pmId));
    flexibleFeeStructure.open();

    flexibleFeeStructure.clickDelete();
    flexibleFeeStructure.clickOkayOnAlert();

    assertNoLogEntries(pmId, "There should be no audit log entry for no changes");
  }

  @Test
  public void customSettingsRowsDeleted() {
    Logger.info("To validate custom settings rows get deleted");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6594");
    testSetupPage.open();

    final String pmId = testSetupPage.getString("pmId");
    final String paymentField1 = testSetupPage.getString("paymentField1");
    final String paymentField2 = testSetupPage.getString("paymentField2");

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeStructure flexibleFeeStructure = new FlexibleFeeStructure(Integer.valueOf(pmId));
    flexibleFeeStructure.open();

    HashMap<String, String> rowDataPaymentField1 = new HashMap<>();
    rowDataPaymentField1.put(Columns.PAYMENT_FIELD.getLabel().toLowerCase(), paymentField1);

    HashMap<String, String> rowDataPaymentField2 = new HashMap<>();
    rowDataPaymentField2.put(Columns.PAYMENT_FIELD.getLabel().toLowerCase(), paymentField2);

    try {
      flexibleFeeStructure.toggleRowByRowData(rowDataPaymentField1);
    } catch (Exception e) {
      Assert.fail(
          "Unable to select row for payment field '" + paymentField1 + "', exception: "
              + e.getMessage()
      );
    }

    flexibleFeeStructure.clickDeleteAndOkayOnConfirm();

    Assert.assertEquals(flexibleFeeStructure.getSuccessMessage(),
        "1 PM Flexible Fee Custom Setting deleted",
        "Success message should be present with number of rows deleted");

    Assert.assertFalse(flexibleFeeStructure.isRowByRowDataPresent(rowDataPaymentField1),
        "Row should have been deleted");

    Assert.assertTrue(flexibleFeeStructure.isRowByRowDataPresent(rowDataPaymentField2),
        "Undeleted row should be present");
  }

  @Test
  public void customSettingDeletionInsertsAuditLogEntry() {
    Logger.info("Verify one Audit Log entry when Custom Setting(s) deleted");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6594");
    testSetupPage.open();

    final String pmId = testSetupPage.getString("pmId");
    final String paymentField = testSetupPage.getString("paymentField1");

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeStructure flexibleFeeStructure = new FlexibleFeeStructure(Integer.valueOf(pmId));
    flexibleFeeStructure.open();

    HashMap<String, String> rowDataPaymentField = new HashMap<>();
    rowDataPaymentField.put(Columns.PAYMENT_FIELD.getLabel().toLowerCase(), paymentField);

    try {
      flexibleFeeStructure.toggleRowByRowData(rowDataPaymentField);
    } catch (Exception e) {
      Assert.fail(
          "Unable to select row for payment field '" + paymentField + "', exception: "
              + e.getMessage()
      );
    }

    flexibleFeeStructure.clickDeleteAndOkayOnConfirm();

    assertLogEntry(pmId, "1 PM Flexible Fee Custom Setting deleted");
  }

  @Test
  public void deleteCustomSettingsAcrossMultiplePages() {
    Logger.info(
        "To validate custom settings rows get deleted when selecting across multiple pages");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6719");
    testSetupPage.open();

    final String pmId = testSetupPage.getString("pmId");

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeStructure flexibleFeeStructure = new FlexibleFeeStructure(Integer.valueOf(pmId));
    flexibleFeeStructure.open();

    List<Map<String, String>> tableData = flexibleFeeStructure.getCustomSettingsTableRowContent();
    Random random = new Random();
    int rowToChoose = random.nextInt(tableData.size());

    Map<String, String> rowData1 = tableData.get(rowToChoose);

    try {
      flexibleFeeStructure.toggleRowByRowData(rowData1);
    } catch (Exception e) {
      Assert.fail("Unable to select row number " + rowToChoose + ", exception: " + e.getMessage());
    }

    flexibleFeeStructure.clickNext();

    tableData = flexibleFeeStructure.getCustomSettingsTableRowContent();

    rowToChoose = random.nextInt(tableData.size());

    Map<String, String> rowData2 = tableData.get(rowToChoose);

    try {
      flexibleFeeStructure.toggleRowByRowData(rowData2);
    } catch (Exception e) {
      Assert.fail("Unable to select row number " + rowToChoose + ", exception: " + e.getMessage());
    }

    flexibleFeeStructure.clickDeleteAndOkayOnConfirm();

    Assert.assertFalse(flexibleFeeStructure.isRowByRowDataPresent(rowData1),
        "Row should have been deleted");

    Assert.assertFalse(flexibleFeeStructure.isRowByRowDataPresent(rowData2),
        "Row should have been deleted");
  }

  @Test
  public void customSettingsTableFiltersBySelected() {
    Logger.info("To validate custom settings table allows filter by selected rows");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6719");
    testSetupPage.open();

    final String pmId = testSetupPage.getString("pmId");

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeStructure flexibleFeeStructure = new FlexibleFeeStructure(Integer.valueOf(pmId));
    flexibleFeeStructure.open();

    final int totalNumberOfRows = flexibleFeeStructure.getTotalNumberOfCustomSettings();

    List<Map<String, String>> tableData = flexibleFeeStructure.getCustomSettingsTableRowContent();
    Random random = new Random();
    int rowToChoose = random.nextInt(tableData.size());

    Map<String, String> rowData = tableData.get(rowToChoose);

    try {
      flexibleFeeStructure.toggleRowByRowData(rowData);
    } catch (Exception e) {
      Assert.fail("Unable to select row number " + rowToChoose + ", exception: " + e.getMessage());
    }

    flexibleFeeStructure.clickSelectedFilter(1);

    Assert.assertEquals(1, flexibleFeeStructure.getCustomSettingsTableRowCount(),
        "Expect to see only the one selected row");

    Assert.assertTrue(flexibleFeeStructure.isRowByRowDataPresent(rowData),
        "Selected row should be present after applying filter");

    flexibleFeeStructure.clickNotSelectedFilter(totalNumberOfRows - 1);

    Assert.assertFalse(flexibleFeeStructure.isRowByRowDataPresent(rowData),
        "Selected row should not be visible");

    flexibleFeeStructure.clickAllFilter(totalNumberOfRows);

    Assert.assertTrue(flexibleFeeStructure.isRowByRowDataPresent(rowData),
        "All rows should be present");
  }

  @Test
  public void customSettingsDeleteDoesNotSaveOtherFormChanges() throws Exception {
    Logger.info(
        "To validate that deleting custom settings does not submit the other forms on the page."
    );

    DataHelper dataHelper = new DataHelper();

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6594");
    testSetupPage.open();

    final int pmId = Integer.valueOf(testSetupPage.getString("pmId"));
    final String paymentField = testSetupPage.getString("paymentField1");
    final Map<String, String> expectedOriginalFormData = readTable(testSetupPage);

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeStructure flexibleFeeStructure = openFlexibleFeeStructurePage(pmId);

    final boolean ccAchFeeChecked = flexibleFeeStructure.isCcAchFeeEnabled();
    final boolean roundingChecked = flexibleFeeStructure.isRoundingEnabled();
    final boolean pmOneTimeFeeIncurChecked = flexibleFeeStructure.isPmOneTimeFeeIncurEnabled();
    final boolean phoneFeeChecked = flexibleFeeStructure.isPhoneFeeEnabled();
    final boolean expressPayChecked = flexibleFeeStructure.isExpressPayEnabled();
    final String phoneFeeAmount = flexibleFeeStructure.getPhoneFee();
    final String expressPayAmount = flexibleFeeStructure.getExpressPayFee();

    String enteredPhoneFee = dataHelper.getMoneyAmount(0, 10);
    String enteredExpressPayFee = dataHelper.getMoneyAmount(0, 10);

    setPmLevelSettingsValuesOnPage(flexibleFeeStructure, enteredPhoneFee, enteredExpressPayFee);
    setDefaultValues(flexibleFeeStructure, Amounts.VALID, false, true);

    HashMap<String, String> rowData = new HashMap<>();
    rowData.put(Columns.PAYMENT_FIELD.getLabel().toLowerCase(), paymentField);

    try {
      flexibleFeeStructure.toggleRowByRowData(rowData);
    } catch (Exception e) {
      Assert.fail(
          "Unable to select row for payment field '" + paymentField + "', exception: "
              + e.getMessage()
      );
    }

    flexibleFeeStructure.clickDeleteAndOkayOnConfirm();

    openFlexibleFeeStructurePage(pmId);

    assertPmLevelSettingsValuesOnPage(
        flexibleFeeStructure,
        ccAchFeeChecked,
        roundingChecked,
        pmOneTimeFeeIncurChecked,
        phoneFeeChecked,
        phoneFeeAmount,
        expressPayChecked,
        expressPayAmount
    );

    assertDefaultValuesOnPage(flexibleFeeStructure, expectedOriginalFormData, true);
  }

  @Test
  public void customSettingsAddButtonOpensAddForm() {
    Logger.info(
        "To validateCreate Custom Setting button takes you to the add custom settings form."
    );

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6593");
    testSetupPage.open();

    final String pmId = testSetupPage.getString("pmId");

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeStructure flexibleFeeStructure = openFlexibleFeeStructurePage(
        Integer.parseInt(pmId));
    flexibleFeeStructure.clickCreateCustomSetting();

    FlexibleFeeCustomSettingAddPage addPage = new FlexibleFeeCustomSettingAddPage(pmId);

    Assert.assertTrue(addPage.pageIsLoaded(), "Add page is loaded for correct fee");
  }

  private Map<String, String> setDefaultsValuesOnPage(
      FlexibleFeeStructure flexibleFeeStructure,
      Amounts amountType,
      boolean disableValidation,
      boolean updateAll
  ) throws Exception {
    Map<String, String> valuesToSet = setDefaultValues(flexibleFeeStructure, amountType,
        disableValidation, updateAll);

    flexibleFeeStructure.preparePageUnload();

    if (amountType == Amounts.VALID || disableValidation) {
      flexibleFeeStructure.clickSaveDefaultsBtnAndWait();
    } else {
      flexibleFeeStructure.clickSaveDefaultsBtnNoWait();
    }

    return valuesToSet;
  }

  private void setPmLevelSettingsValuesOnPage(
      FlexibleFeeStructure flexibleFeeStructure, String enteredPhoneFee, String enteredExpressPayFee
  ) {
    flexibleFeeStructure.clickCcAchFeeCheckbox();
    flexibleFeeStructure.clickRoundingCheckbox();
    flexibleFeeStructure.clickPmOneTimeFeeIncurCheckbox();
    flexibleFeeStructure.clickPhoneFeeCheckbox();
    flexibleFeeStructure.clickExpressPayCheckbox();
    flexibleFeeStructure.setPhoneFee(enteredPhoneFee);
    flexibleFeeStructure.setExpressFee(enteredExpressPayFee);
  }

  private FlexibleFeeStructure openFlexibleFeeStructurePage(int pmId) {
    FlexibleFeeStructure flexibleFeeStructure = new FlexibleFeeStructure(pmId);
    flexibleFeeStructure.open();

    return flexibleFeeStructure;
  }

  private void assertDefaultValuesOnPage(
      FlexibleFeeStructure flexibleFeeStructure,
      Map<String, String> expectedPageData,
      boolean translateAmount
  ) throws Exception {
    for (Map.Entry<PaymentType, PaymentTypeSetup> paymentTypeEntry : DEFAULT_PAYMENT_TYPES
        .entrySet()) {
      for (Map.Entry<PaymentMethod, PaymentMethodSetup> paymentMethodEntry : DEFAULT_PAYMENT_METHODS
          .entrySet()) {
        String rawType = expectedPageData.get(
            paymentTypeEntry.getValue().getValue() + paymentMethodEntry.getValue().getValue()
                + "type"
        );

        String expectedType;
        if (null == rawType) {
          expectedType = "";
        } else {
          switch (rawType) {
            case "fixed":
              expectedType = "$";
              break;
            case "percent":
              expectedType = "%";
              break;
            default:
              expectedType = "";
              break;
          }
        }

        String actualType = flexibleFeeStructure
            .getFeeType(paymentTypeEntry.getKey(), paymentMethodEntry.getKey());
        Assert.assertEquals(actualType, expectedType, "");

        String rawAmount = expectedPageData.get(
            paymentTypeEntry.getValue().getValue() + paymentMethodEntry.getValue().getValue()
                + "amount"
        );

        String expectedAmount;
        if (null == rawAmount) {
          expectedAmount = "";
        } else {
          expectedAmount = rawAmount;
          if (translateAmount) {
            int zerosToAdd = 3 - rawAmount.length();
            for (int i = zerosToAdd; i > 0; --i) {
              expectedAmount = "0" + expectedAmount;
            }
            int decimalIndex = expectedAmount.length() - 2;
            expectedAmount = expectedAmount.substring(0, decimalIndex) + "." + expectedAmount
                .substring(decimalIndex);
          }
        }

        String actualAmount = flexibleFeeStructure
            .getAmount(paymentTypeEntry.getKey(), paymentMethodEntry.getKey());
        Assert.assertEquals(actualAmount, expectedAmount, "");

        String rawIncur = expectedPageData.get(
            paymentTypeEntry.getValue().getValue() + paymentMethodEntry.getValue().getValue()
                + "incur"
        );

        String expectedIncur;
        if (null == rawIncur) {
          expectedIncur = "";
        } else {
          switch (rawIncur) {
            case "resident":
              expectedIncur = "Resident";
              break;
            case "pm":
              expectedIncur = "PM";
              break;
            default:
              expectedIncur = "";
              break;
          }
        }

        String actualIncur = flexibleFeeStructure
            .getIncur(paymentTypeEntry.getKey(), paymentMethodEntry.getKey());
        Assert.assertEquals(actualIncur, expectedIncur, "");
      }
    }
  }

  private void assertPmLevelSettingsValuesOnPage(
      FlexibleFeeStructure flexibleFeeStructure,
      boolean expectedCcAchEnabled,
      boolean expectedRoundingEnabled,
      boolean expectedPmOneTimeFeeIncurEnabled,
      boolean expectedPhoneFeeEnabled,
      String expectedPhoneFeeAmount,
      boolean expectedExpressPayEnabled,
      String expectedExpressPayAmount
  ) {
    Assert.assertEquals(
        flexibleFeeStructure.isCcAchFeeEnabled(), expectedCcAchEnabled,
        "CC + ACH Fee checkbox should match expected"
    );
    Assert.assertEquals(
        flexibleFeeStructure.isRoundingEnabled(), expectedRoundingEnabled,
        "Round to $0.95 checkbox should match expected"
    );
    Assert.assertEquals(
        flexibleFeeStructure.isPmOneTimeFeeIncurEnabled(), expectedPmOneTimeFeeIncurEnabled,
        "PM One-Time Fee Incur checkbox should match expected"
    );
    Assert.assertEquals(
        flexibleFeeStructure.isPhoneFeeEnabled(), expectedPhoneFeeEnabled,
        "Phone Fee checkbox should match expected"
    );
    Assert.assertEquals(
        flexibleFeeStructure.getPhoneFee(), expectedPhoneFeeAmount,
        "Phone Fee Amount should match expected"
    );
    Assert.assertEquals(
        flexibleFeeStructure.isExpressPayEnabled(), expectedExpressPayEnabled,
        "Express Pay checkbox should match expected"
    );
    Assert.assertEquals(
        flexibleFeeStructure.getExpressPayFee(), expectedExpressPayAmount,
        "Express Fee Amount should match expected"
    );
  }

  private Map<String, String> readTable(TestSetupPage testPage) {
    final List<HashMap<String, Object>> expectedPageData = testPage.getTable("tieredFeeData");
    HashMap<String, String> map = new HashMap<>();

    String[] attributeName = {"type", "amount", "incur"};

    for (HashMap<String, Object> row : expectedPageData) {
      for (String attribute : attributeName) {
        String key = row.get("paymentType") + (String) row.get("paymentMethod") + attribute;
        map.put(key, (String) row.get(attribute));
      }
    }

    return map;
  }

  private String getDataFromMap(
      Map<String, String> sourceTable,
      PaymentType paymentType,
      PaymentMethod paymentMethod,
      String attribute
  ) {
    String key = DEFAULT_PAYMENT_TYPES.get(paymentType).getValue() + DEFAULT_PAYMENT_METHODS
        .get(paymentMethod).getValue() + attribute;

    if (null == sourceTable) {
      return "";
    }

    return sourceTable.get(key);
  }

  private String getExpectedAuditLogAmountValue(
      Map<String, String> sourceTable,
      PaymentType paymentType,
      PaymentMethod paymentMethod,
      boolean fixAmount
  ) {
    String type = getDataFromMap(sourceTable, paymentType, paymentMethod, "type");
    String amount = getDataFromMap(sourceTable, paymentType, paymentMethod, "amount");

    if (null == type || null == amount) {
      return "";
    }

    if (fixAmount) {
      amount = fixAmount(amount);
    }

    return (type.equals("fixed") ? "$" : "") + amount + (type.equals("percent") ? "%" : "");
  }

  private String getExpectedAuditLogIncurValue(
      Map<String, String> sourceTable,
      PaymentType paymentType,
      PaymentMethod paymentMethod
  ) {
    String incur = getDataFromMap(sourceTable, paymentType, paymentMethod, "incur");

    if (null == incur || incur.equals("")) {
      return "";
    }

    return incur.equals("pm") ? "PM" : "Resident";
  }

  private void assertPmLevelAuditLogMessage(
      String pmId, boolean updateCcAchFee, boolean ccAchFeeChecked, boolean updateRounding,
      boolean roundingChecked, boolean updatePmOneTimeFeeIncur, boolean pmOneTimeFeeIncurChecked,
      boolean updatePhoneFee, boolean phoneFeeChecked, boolean updatePhoneFeeAmount,
      String phoneFeeAmount, String expectedPhoneFee, boolean updateExpressPay,
      boolean expressPayChecked, boolean updateExpressPayAmount, String expressPayAmount,
      String expectedExpressPayFee
  ) {
    String expectedMessage = "PM Flexible Fee Settings updated:";
    if (updateCcAchFee) {
      expectedMessage += "\n * CC + ACH Fee:";
      expectedMessage += "\n    * Old value: " + (ccAchFeeChecked ? "ON" : "OFF");
      expectedMessage += "\n    * New value: " + (ccAchFeeChecked ? "OFF" : "ON");
    }
    if (updateRounding) {
      expectedMessage += "\n * Round to $0.95:";
      expectedMessage += "\n    * Old value: " + (roundingChecked ? "ON" : "OFF");
      expectedMessage += "\n    * New value: " + (roundingChecked ? "OFF" : "ON");
    }
    if (updatePmOneTimeFeeIncur) {
      expectedMessage += "\n * PM One-Time Fee Incur:";
      expectedMessage += "\n    * Old value: " + (pmOneTimeFeeIncurChecked ? "ON" : "OFF");
      expectedMessage += "\n    * New value: " + (pmOneTimeFeeIncurChecked ? "OFF" : "ON");
    }
    if (updatePhoneFee) {
      expectedMessage += "\n * Phone Fee:";
      expectedMessage += "\n    * Old value: " + (phoneFeeChecked ? "ON" : "OFF");
      expectedMessage += "\n    * New value: " + (phoneFeeChecked ? "OFF" : "ON");
    }
    if (updatePhoneFeeAmount) {
      expectedMessage += "\n * Phone Fee Amount:";
      expectedMessage += "\n    * Old value: $" + phoneFeeAmount;
      expectedMessage += "\n    * New value: $" + expectedPhoneFee;
    }
    if (updateExpressPay) {
      expectedMessage += "\n * Express Pay:";
      expectedMessage += "\n    * Old value: " + (expressPayChecked ? "ON" : "OFF");
      expectedMessage += "\n    * New value: " + (expressPayChecked ? "OFF" : "ON");
    }
    if (updateExpressPayAmount) {
      expectedMessage += "\n * Express Pay Amount:";
      expectedMessage += "\n    * Old value: $" + expressPayAmount;
      expectedMessage += "\n    * New value: $" + expectedExpressPayFee;
    }

    // perform assertions
    assertLogEntry(pmId, expectedMessage);
  }

  private void assertDefaultSettingsAuditLogMessage(
      String pmId, String description, Map<String, String> expectedOriginalFormData,
      Map<String, String> expectedValues
  ) {
    String expectedMessage = description;
    String oldValue;
    String newValue;
    oldValue = getExpectedAuditLogAmountValue(expectedOriginalFormData, PaymentType.ONE_TIME,
        PaymentMethod.ACH, true);
    newValue = getExpectedAuditLogAmountValue(expectedValues, PaymentType.ONE_TIME,
        PaymentMethod.ACH, false);
    if (!oldValue.equals(newValue)) {
      expectedMessage += "\n * One-Time Payment - ACH - Fee Amount:";
      if (null != expectedOriginalFormData) {
        expectedMessage += "\n    * Old value: " + oldValue;
      }
      expectedMessage += "\n    * New value: " + newValue;
    }

    oldValue = getExpectedAuditLogIncurValue(expectedOriginalFormData, PaymentType.ONE_TIME,
        PaymentMethod.ACH);
    newValue = getExpectedAuditLogIncurValue(expectedValues, PaymentType.ONE_TIME,
        PaymentMethod.ACH);
    if (!oldValue.equals(newValue)) {
      expectedMessage += "\n * One-Time Payment - ACH - Incurred By:";
      if (null != expectedOriginalFormData) {
        expectedMessage += "\n    * Old value: " + oldValue;
      }
      expectedMessage += "\n    * New value: " + newValue;
    }

    oldValue = getExpectedAuditLogAmountValue(expectedOriginalFormData, PaymentType.ONE_TIME,
        PaymentMethod.CREDIT, true);
    newValue = getExpectedAuditLogAmountValue(expectedValues, PaymentType.ONE_TIME,
        PaymentMethod.CREDIT, false);
    if (!oldValue.equals(newValue)) {
      expectedMessage += "\n * One-Time Payment - Credit - Fee Amount:";
      if (null != expectedOriginalFormData) {
        expectedMessage += "\n    * Old value: " + oldValue;
      }
      expectedMessage += "\n    * New value: " + newValue;
    }

    oldValue = getExpectedAuditLogIncurValue(expectedOriginalFormData, PaymentType.ONE_TIME,
        PaymentMethod.CREDIT);
    newValue = getExpectedAuditLogIncurValue(expectedValues, PaymentType.ONE_TIME,
        PaymentMethod.CREDIT);
    if (!oldValue.equals(newValue)) {
      expectedMessage += "\n * One-Time Payment - Credit - Incurred By:";
      if (null != expectedOriginalFormData) {
        expectedMessage += "\n    * Old value: " + oldValue;
      }
      expectedMessage += "\n    * New value: " + newValue;
    }

    oldValue = getExpectedAuditLogAmountValue(expectedOriginalFormData, PaymentType.ONE_TIME,
        PaymentMethod.DEBIT, true);
    newValue = getExpectedAuditLogAmountValue(expectedValues, PaymentType.ONE_TIME,
        PaymentMethod.DEBIT, false);
    if (!oldValue.equals(newValue)) {
      expectedMessage += "\n * One-Time Payment - Debit - Fee Amount:";
      if (null != expectedOriginalFormData) {
        expectedMessage += "\n    * Old value: " + oldValue;
      }
      expectedMessage += "\n    * New value: " + newValue;
    }

    oldValue = getExpectedAuditLogIncurValue(expectedOriginalFormData, PaymentType.ONE_TIME,
        PaymentMethod.DEBIT);
    newValue = getExpectedAuditLogIncurValue(expectedValues, PaymentType.ONE_TIME,
        PaymentMethod.DEBIT);
    if (!oldValue.equals(newValue)) {
      expectedMessage += "\n * One-Time Payment - Debit - Incurred By:";
      if (null != expectedOriginalFormData) {
        expectedMessage += "\n    * Old value: " + oldValue;
      }
      expectedMessage += "\n    * New value: " + newValue;
    }

    oldValue = getExpectedAuditLogAmountValue(expectedOriginalFormData, PaymentType.FIXED_AUTOPAY,
        PaymentMethod.ACH, true);
    newValue = getExpectedAuditLogAmountValue(expectedValues, PaymentType.FIXED_AUTOPAY,
        PaymentMethod.ACH, false);
    if (!oldValue.equals(newValue)) {
      expectedMessage += "\n * Fixed AutoPay - ACH - Fee Amount:";
      if (null != expectedOriginalFormData) {
        expectedMessage += "\n    * Old value: " + oldValue;
      }
      expectedMessage += "\n    * New value: " + newValue;
    }

    oldValue = getExpectedAuditLogIncurValue(expectedOriginalFormData, PaymentType.FIXED_AUTOPAY,
        PaymentMethod.ACH);
    newValue = getExpectedAuditLogIncurValue(expectedValues, PaymentType.FIXED_AUTOPAY,
        PaymentMethod.ACH);
    if (!oldValue.equals(newValue)) {
      expectedMessage += "\n * Fixed AutoPay - ACH - Incurred By:";
      if (null != expectedOriginalFormData) {
        expectedMessage += "\n    * Old value: " + oldValue;
      }
      expectedMessage += "\n    * New value: " + newValue;
    }

    oldValue = getExpectedAuditLogAmountValue(expectedOriginalFormData, PaymentType.FIXED_AUTOPAY,
        PaymentMethod.CREDIT, true);
    newValue = getExpectedAuditLogAmountValue(expectedValues, PaymentType.FIXED_AUTOPAY,
        PaymentMethod.CREDIT, false);
    if (!oldValue.equals(newValue)) {
      expectedMessage += "\n * Fixed AutoPay - Credit - Fee Amount:";
      if (null != expectedOriginalFormData) {
        expectedMessage += "\n    * Old value: " + oldValue;
      }
      expectedMessage += "\n    * New value: " + newValue;
    }

    oldValue = getExpectedAuditLogIncurValue(expectedOriginalFormData, PaymentType.FIXED_AUTOPAY,
        PaymentMethod.CREDIT);
    newValue = getExpectedAuditLogIncurValue(expectedValues, PaymentType.FIXED_AUTOPAY,
        PaymentMethod.CREDIT);
    if (!oldValue.equals(newValue)) {
      expectedMessage += "\n * Fixed AutoPay - Credit - Incurred By:";
      if (null != expectedOriginalFormData) {
        expectedMessage += "\n    * Old value: " + oldValue;
      }
      expectedMessage += "\n    * New value: " + newValue;
    }

    oldValue = getExpectedAuditLogAmountValue(expectedOriginalFormData, PaymentType.FIXED_AUTOPAY,
        PaymentMethod.DEBIT, true);
    newValue = getExpectedAuditLogAmountValue(expectedValues, PaymentType.FIXED_AUTOPAY,
        PaymentMethod.DEBIT, false);
    if (!oldValue.equals(newValue)) {
      expectedMessage += "\n * Fixed AutoPay - Debit - Fee Amount:";
      if (null != expectedOriginalFormData) {
        expectedMessage += "\n    * Old value: " + oldValue;
      }
      expectedMessage += "\n    * New value: " + newValue;
    }

    oldValue = getExpectedAuditLogIncurValue(expectedOriginalFormData, PaymentType.FIXED_AUTOPAY,
        PaymentMethod.DEBIT);
    newValue = getExpectedAuditLogIncurValue(expectedValues, PaymentType.FIXED_AUTOPAY,
        PaymentMethod.DEBIT);
    if (!oldValue.equals(newValue)) {
      expectedMessage += "\n * Fixed AutoPay - Debit - Incurred By:";
      if (null != expectedOriginalFormData) {
        expectedMessage += "\n    * Old value: " + oldValue;
      }
      expectedMessage += "\n    * New value: " + newValue;
    }

    oldValue = getExpectedAuditLogAmountValue(expectedOriginalFormData,
        PaymentType.VARIABLE_AUTOPAY, PaymentMethod.ACH, true);
    newValue = getExpectedAuditLogAmountValue(expectedValues, PaymentType.VARIABLE_AUTOPAY,
        PaymentMethod.ACH, false);
    if (!oldValue.equals(newValue)) {
      expectedMessage += "\n * Variable AutoPay - ACH - Fee Amount:";
      if (null != expectedOriginalFormData) {
        expectedMessage += "\n    * Old value: " + oldValue;
      }
      expectedMessage += "\n    * New value: " + newValue;
    }

    oldValue = getExpectedAuditLogIncurValue(expectedOriginalFormData, PaymentType.VARIABLE_AUTOPAY,
        PaymentMethod.ACH);
    newValue = getExpectedAuditLogIncurValue(expectedValues, PaymentType.VARIABLE_AUTOPAY,
        PaymentMethod.ACH);
    if (!oldValue.equals(newValue)) {
      expectedMessage += "\n * Variable AutoPay - ACH - Incurred By:";
      if (null != expectedOriginalFormData) {
        expectedMessage += "\n    * Old value: " + oldValue;
      }
      expectedMessage += "\n    * New value: " + newValue;
    }

    oldValue = getExpectedAuditLogAmountValue(expectedOriginalFormData,
        PaymentType.VARIABLE_AUTOPAY, PaymentMethod.CREDIT, true);
    newValue = getExpectedAuditLogAmountValue(expectedValues, PaymentType.VARIABLE_AUTOPAY,
        PaymentMethod.CREDIT, false);
    if (!oldValue.equals(newValue)) {
      expectedMessage += "\n * Variable AutoPay - Credit - Fee Amount:";
      if (null != expectedOriginalFormData) {
        expectedMessage += "\n    * Old value: " + oldValue;
      }
      expectedMessage += "\n    * New value: " + newValue;
    }

    oldValue = getExpectedAuditLogIncurValue(expectedOriginalFormData, PaymentType.VARIABLE_AUTOPAY,
        PaymentMethod.CREDIT);
    newValue = getExpectedAuditLogIncurValue(expectedValues, PaymentType.VARIABLE_AUTOPAY,
        PaymentMethod.CREDIT);
    if (!oldValue.equals(newValue)) {
      expectedMessage += "\n * Variable AutoPay - Credit - Incurred By:";
      if (null != expectedOriginalFormData) {
        expectedMessage += "\n    * Old value: " + oldValue;
      }
      expectedMessage += "\n    * New value: " + newValue;
    }

    oldValue = getExpectedAuditLogAmountValue(expectedOriginalFormData,
        PaymentType.VARIABLE_AUTOPAY, PaymentMethod.DEBIT, true);
    newValue = getExpectedAuditLogAmountValue(expectedValues, PaymentType.VARIABLE_AUTOPAY,
        PaymentMethod.DEBIT, false);
    if (!oldValue.equals(newValue)) {
      expectedMessage += "\n * Variable AutoPay - Debit - Fee Amount:";
      if (null != expectedOriginalFormData) {
        expectedMessage += "\n    * Old value: " + oldValue;
      }
      expectedMessage += "\n    * New value: " + newValue;
    }

    oldValue = getExpectedAuditLogIncurValue(expectedOriginalFormData, PaymentType.VARIABLE_AUTOPAY,
        PaymentMethod.DEBIT);
    newValue = getExpectedAuditLogIncurValue(expectedValues, PaymentType.VARIABLE_AUTOPAY,
        PaymentMethod.DEBIT);
    if (!oldValue.equals(newValue)) {
      expectedMessage += "\n * Variable AutoPay - Debit - Incurred By:";
      if (null != expectedOriginalFormData) {
        expectedMessage += "\n    * Old value: " + oldValue;
      }
      expectedMessage += "\n    * New value: " + newValue;
    }

    // perform assertions
    assertLogEntry(pmId, expectedMessage);
  }

  private void assertDefaultSettingsInitializedAuditLogMessage(String pmId) {
    String expectedMessage;

    expectedMessage = "PM Flexible Fee Default Property Settings initialized:";
    assertDefaultSettingsAuditLogMessage(
        pmId, expectedMessage, null, getInitialDefaultValues()
    );
  }

  /**
   * Constructs PmFeeTier object.
   *
   * @param pmId pmId
   * @param propId propId
   * @param paymentTypeId paymentTypeId
   * @param paymentMethodId paymentMethodId
   * @param varName varName
   * @param tierAmount tierAmount
   * @param feeType feeType
   * @param feeValue feeValue
   * @param feeIncur feeIncur
   * @return PmFeeTier populated with passed values
   */
  private PmFeeTier getPopulatedPmFeeTier(int pmId, int propId, int paymentTypeId,
      int paymentMethodId, String varName, int tierAmount, String feeType,
      int feeValue, String feeIncur) {
    PmFeeTier pmFeeTier = new PmFeeTier();
    pmFeeTier.setPmId(pmId);
    pmFeeTier.setPropId(propId);
    pmFeeTier.setPaymentTypeId(paymentTypeId);
    pmFeeTier.setPaymentMethodId(paymentMethodId);
    pmFeeTier.setVarName(varName);
    pmFeeTier.setTierAmount(tierAmount);
    pmFeeTier.setFeeType(feeType);
    pmFeeTier.setFeeValue(feeValue);
    pmFeeTier.setFeeIncur(feeIncur);

    return pmFeeTier;
  }

  private Map<String, String> setDefaultValues(
      FlexibleFeeStructure flexibleFeeStructure,
      Amounts data,
      boolean disableValidation,
      boolean updateAll
  ) throws Exception {
    Map<String, String> values = new HashMap<>();
    DataHelper dataHelper = new DataHelper();

    boolean hasAnyChanges = false;
    for (Map.Entry<PaymentType, PaymentTypeSetup> paymentTypeEntry : DEFAULT_PAYMENT_TYPES
        .entrySet()) {
      for (Map.Entry<PaymentMethod, PaymentMethodSetup> paymentMethodEntry : DEFAULT_PAYMENT_METHODS
          .entrySet()) {
        String typeKey =
            paymentTypeEntry.getValue().getValue() + paymentMethodEntry.getValue().getValue()
                + "type";
        String typeValue;
        if (updateAll || dataHelper.getBoolean()) {
          if (paymentTypeEntry.getKey() == PaymentType.VARIABLE_AUTOPAY) {
            typeValue = "$";

          } else {
            typeValue = flexibleFeeStructure
                .getFeeType(paymentTypeEntry.getKey(), paymentMethodEntry.getKey()).equals("%")
                ? "$"
                : "%";
            hasAnyChanges = true;
          }
        } else {
          typeValue = flexibleFeeStructure.getFeeType(
              paymentTypeEntry.getKey(), paymentMethodEntry.getKey()
          );
        }
        values.put(typeKey, typeValue.equals("$") ? "fixed" : "percent");
        flexibleFeeStructure.setFeeType(
            paymentTypeEntry.getKey(), paymentMethodEntry.getKey(), typeValue
        );

        String amount;
        String amountKey =
            paymentTypeEntry.getValue().getValue() + paymentMethodEntry.getValue().getValue()
                + "amount";
        if (updateAll || dataHelper.getBoolean()) {
          amount = fixAmount(dataHelper.getAmount());

          if (data == Amounts.CHARS) {
            amount = "zgjdks";
          } else if (data == Amounts.NEGATIVE) {
            amount = "-" + amount;
          } else if (data == Amounts.BLANK) {
            amount = "";
          }
          hasAnyChanges = true;
        } else {
          amount = flexibleFeeStructure.getAmount(
              paymentTypeEntry.getKey(), paymentMethodEntry.getKey()
          );
        }
        values.put(amountKey, amount);
        if (disableValidation) {
          flexibleFeeStructure.disableValidationDefaultAmount(paymentTypeEntry.getKey(),
              paymentMethodEntry.getKey());
        }
        flexibleFeeStructure.setAmount(
            paymentTypeEntry.getKey(), paymentMethodEntry.getKey(), amount
        );

        String incurValue;
        String incurKey =
            paymentTypeEntry.getValue().getValue() + paymentMethodEntry.getValue().getValue()
                + "incur";
        if (updateAll || dataHelper.getBoolean()) {
          incurValue =
              flexibleFeeStructure.getIncur(paymentTypeEntry.getKey(), paymentMethodEntry.getKey())
                  .equalsIgnoreCase("PM") ? "Resident" : "PM";
          hasAnyChanges = true;
        } else {
          incurValue = flexibleFeeStructure.getIncur(
              paymentTypeEntry.getKey(), paymentMethodEntry.getKey()
          );
        }
        values.put(incurKey, incurValue.toLowerCase());
        flexibleFeeStructure.setIncur(
            paymentTypeEntry.getKey(), paymentMethodEntry.getKey(), incurValue
        );
      }
    }

    // in case we went through all the fields and somehow came out without having changed anything,
    // try again
    if (!hasAnyChanges) {
      setDefaultValues(flexibleFeeStructure, data, disableValidation, updateAll);
    }
    return values;
  }

  private HashMap<String, String> getInitialDefaultValues() {
    HashMap<String, String> defaults = new HashMap<>();

    String keyBase;

    keyBase = DEFAULT_PAYMENT_TYPES.get(PaymentType.ONE_TIME).getValue()
        + DEFAULT_PAYMENT_METHODS.get(PaymentMethod.ACH).getValue();
    defaults.put(keyBase + "type", "fixed");
    defaults.put(keyBase + "amount", "2.95");
    defaults.put(keyBase + "incur", "resident");

    keyBase = DEFAULT_PAYMENT_TYPES.get(PaymentType.ONE_TIME).getValue()
        + DEFAULT_PAYMENT_METHODS.get(PaymentMethod.CREDIT).getValue();
    defaults.put(keyBase + "type", "percent");
    defaults.put(keyBase + "amount", "3.50");
    defaults.put(keyBase + "incur", "resident");

    keyBase = DEFAULT_PAYMENT_TYPES.get(PaymentType.ONE_TIME).getValue()
        + DEFAULT_PAYMENT_METHODS.get(PaymentMethod.DEBIT).getValue();
    defaults.put(keyBase + "type", "fixed");
    defaults.put(keyBase + "amount", "9.95");
    defaults.put(keyBase + "incur", "resident");

    keyBase = DEFAULT_PAYMENT_TYPES.get(PaymentType.FIXED_AUTOPAY).getValue()
        + DEFAULT_PAYMENT_METHODS.get(PaymentMethod.ACH).getValue();
    defaults.put(keyBase + "type", "fixed");
    defaults.put(keyBase + "amount", "2.95");
    defaults.put(keyBase + "incur", "resident");

    keyBase = DEFAULT_PAYMENT_TYPES.get(PaymentType.FIXED_AUTOPAY).getValue()
        + DEFAULT_PAYMENT_METHODS.get(PaymentMethod.CREDIT).getValue();
    defaults.put(keyBase + "type", "percent");
    defaults.put(keyBase + "amount", "3.50");
    defaults.put(keyBase + "incur", "resident");

    keyBase = DEFAULT_PAYMENT_TYPES.get(PaymentType.FIXED_AUTOPAY).getValue()
        + DEFAULT_PAYMENT_METHODS.get(PaymentMethod.DEBIT).getValue();
    defaults.put(keyBase + "type", "fixed");
    defaults.put(keyBase + "amount", "9.95");
    defaults.put(keyBase + "incur", "resident");

    keyBase = DEFAULT_PAYMENT_TYPES.get(PaymentType.VARIABLE_AUTOPAY).getValue()
        + DEFAULT_PAYMENT_METHODS.get(PaymentMethod.ACH).getValue();
    defaults.put(keyBase + "type", "fixed");
    defaults.put(keyBase + "amount", "2.95");
    defaults.put(keyBase + "incur", "resident");

    keyBase = DEFAULT_PAYMENT_TYPES.get(PaymentType.VARIABLE_AUTOPAY).getValue()
        + DEFAULT_PAYMENT_METHODS.get(PaymentMethod.CREDIT).getValue();
    defaults.put(keyBase + "type", "fixed");
    defaults.put(keyBase + "amount", "49.95");
    defaults.put(keyBase + "incur", "resident");

    keyBase = DEFAULT_PAYMENT_TYPES.get(PaymentType.VARIABLE_AUTOPAY).getValue()
        + DEFAULT_PAYMENT_METHODS.get(PaymentMethod.DEBIT).getValue();
    defaults.put(keyBase + "type", "fixed");
    defaults.put(keyBase + "amount", "9.95");
    defaults.put(keyBase + "incur", "resident");

    return defaults;
  }

  private String getAmountByType(Amounts amountType, String amount) {
    switch (amountType) {
      case BLANK:
        return "";
      case NEGATIVE:
        return "-" + amount;
      case CHARS:
        return "sean";
      default:
        return amount;
    }
  }

  private void assertCustomSettingRowHasValidEditLink(
      String pmId, String feeId, String paymentField
  ) {
    FlexibleFeeStructure flexibleFeeStructure = new FlexibleFeeStructure(Integer.valueOf(pmId));
    flexibleFeeStructure.open();

    HashMap<String, String> rowData = new HashMap<>();
    rowData.put(Columns.PAYMENT_FIELD.getLabel().toLowerCase(), paymentField);

    try {
      flexibleFeeStructure.clickEditLinkByRowData(rowData);
    } catch (Exception e) {
      Assert.fail(
          "Unable to click edit link for fee ID " + feeId + ", exception: " + e.getMessage());
    }
    FlexibleFeeCustomSettingEditPage editPage = new FlexibleFeeCustomSettingEditPage(pmId, feeId);
    Assert.assertTrue(editPage.pageIsLoaded(), "Edit page is loaded for correct fee");
  }

  private void assertCustomSettingRowHasSelectableCheckbox(String pmId, String paymentField) {
    FlexibleFeeStructure flexibleFeeStructure = new FlexibleFeeStructure(Integer.valueOf(pmId));
    flexibleFeeStructure.open();

    HashMap<String, String> rowData = new HashMap<>();
    rowData.put(Columns.PAYMENT_FIELD.getLabel().toLowerCase(), paymentField);

    try {
      boolean isRowSelected = flexibleFeeStructure.isRowSelected(rowData);
      flexibleFeeStructure.toggleRowByRowData(rowData);
      Assert.assertEquals(flexibleFeeStructure.isRowSelected(rowData), !isRowSelected,
          "Row should have been toggled");
      flexibleFeeStructure.toggleRowByRowData(rowData);
      Assert.assertEquals(flexibleFeeStructure.isRowSelected(rowData), isRowSelected,
          "Row should have been toggled back");
    } catch (Exception e) {
      Assert.fail(
          "Unable to handle row for payment field '" + paymentField + "', exception: "
              + e.getMessage()
      );
    }
  }

  private void assertCustomSettingRowsArePresent(
      FlexibleFeeStructure flexibleFeeStructure, List<HashMap<String, String>> rowsToFind
  ) {
    for (HashMap<String, String> rowData : rowsToFind) {
      try {
        flexibleFeeStructure.isRowByRowDataPresent(rowData);
      } catch (Exception e) {
        String values = "";
        for (HashMap.Entry<String, String> entry : rowData.entrySet()) {
          values += entry.getKey() + "=" + entry.getValue() + ", ";
        }

        Assert.fail("Unable to find row for '" + values + "', exception: " + e.getMessage());
      }
    }
  }
}
