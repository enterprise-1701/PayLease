package com.paylease.app.qa.functional.tests.admin;

import static com.paylease.app.qa.framework.UtilityManager.YEAR_MONTH_DAY_DASH;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import com.paylease.app.qa.framework.ExcelUtil;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.UtilityManager;
import com.paylease.app.qa.framework.pages.admin.InvoiceDetails;
import com.paylease.app.qa.framework.pages.admin.InvoicePaymentSearch;
import com.paylease.app.qa.framework.pages.admin.invoiceconfig.FormCreatePage;
import com.paylease.app.qa.framework.pages.admin.invoiceconfig.FormEditPage;
import com.paylease.app.qa.framework.pages.admin.invoiceconfig.FormPage;
import com.paylease.app.qa.framework.pages.admin.invoiceconfig.ListPage;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by Mahfuz Alam on 10/18/2017.
 */

public class InvoiceTest extends ScriptBase {

  private static final String REGION = "admin";
  private static final String FEATURE = "invoices";

  private static final String EXPECTED_MODE = "mode";
  private static final String EXPECTED_RUN_ONCE = "runOnce";
  private static final String EXPECTED_INCURRED = "incurred";
  private static final String EXPECTED_PAYDIRECT = "paydirect";
  private static final String EXPECTED_NSF = "nsf";
  private static final String EXPECTED_PROPERTY = "property";
  private static final String EXPECTED_BANK_ACCOUNT = "bankAccount";
  private static final String EXPECTED_DEBIT_DAY = "debitDay";
  private static final String EXPECTED_CREATED_ON = "createdOn";
  private static final String EXPECTED_LAST_RUN = "lastRun";

  private static final String INVOICE_OPTION_DIRECT_DEBIT = "Direct Debit";
  private static final String INVOICE_OPTION_MAIL = "Mail";

  private static final String EXCEL_PRIMARY_ACCOUNT_NUM = "Primary Account #";
  private static final String EXCEL_SECONDARY_ACCOUNT_NUM = "Secondary Account #";

  private static final String INVOICE_PAYMENT_SEARCH_PRIMARY = "Primary Acct #";
  private static final String INVOICE_PAYMENT_SEARCH_SECONDARY = "Secondary Acct #";

  private static final String ACH_PAYMENT_TYPE = "1";
  private static final String CC_PAYMENT_TYPE = "2";
  private static final String RDC_PAYMENT_TYPE = "3";

  private static final String PRIMARY_ACCOUNT_TABLE_NAME = "integration_user_id";
  private static final String SECONDARY_ACCOUNT_TABLE_NAME = "yardi_account";

  @Test
  public void createInvoiceDirectDebit() throws Exception {
    createInvoice(INVOICE_OPTION_DIRECT_DEBIT);
  }

  @Test
  public void createInvoiceMail() throws Exception {
    createInvoice(INVOICE_OPTION_MAIL);
  }

  @Test
  public void tc2000() {
    Logger.info("Excel Spreadsheet has Primary & Secondary Account # column headers");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc2000");
    testSetupPage.open();

    final String invoiceId = testSetupPage.getString("invoiceId");

    InvoiceDetails invoiceDetails = new InvoiceDetails(invoiceId);
    invoiceDetails.open();

    String downloadPath = setUpDownloadPath(this.getClass().getName(), "tc2000");
    invoiceDetails.clickDownload(downloadPath);

    String fileName = downloadPath + "/pm-fees-invoice-" + invoiceId + ".xlsx";

    final String[][] excelReport = ExcelUtil.getExcelData(fileName, "Sheet1");

    final Boolean isPrimaryShown = hasColumnHeader(excelReport[0], EXCEL_PRIMARY_ACCOUNT_NUM);
    final Boolean isSecondaryShown = hasColumnHeader(excelReport[0], EXCEL_SECONDARY_ACCOUNT_NUM);

    Assert.assertTrue((isPrimaryShown && isSecondaryShown),
        "Primary/Secondary Account # is in table header");
  }

  @Test
  public void tc1454() {
    isExcelInvoiceAccountNumCorrect(PRIMARY_ACCOUNT_TABLE_NAME, EXCEL_PRIMARY_ACCOUNT_NUM,
        "tc1454");
  }

  @Test
  public void tc1665() {
    isExcelInvoiceAccountNumCorrect(SECONDARY_ACCOUNT_TABLE_NAME, EXCEL_SECONDARY_ACCOUNT_NUM,
        "tc1665");
  }


  @Test
  public void tc2001() {
    checkTableHeaders(INVOICE_PAYMENT_SEARCH_PRIMARY, ACH_PAYMENT_TYPE, "", "tc2001");
  }

  @Test
  public void tc2003() {
    checkTableHeaders(INVOICE_PAYMENT_SEARCH_PRIMARY, CC_PAYMENT_TYPE, "5", "tc2003");
  }

  @Test
  public void tc2004() {
    checkTableHeaders(INVOICE_PAYMENT_SEARCH_PRIMARY, RDC_PAYMENT_TYPE, "", "tc2004");
  }

  @Test
  public void tc2005() {
    checkTableHeaders(INVOICE_PAYMENT_SEARCH_PRIMARY, "", "", "tc2000");
  }

  @Test
  public void tc2006() {
    checkTableHeaders(INVOICE_PAYMENT_SEARCH_SECONDARY, ACH_PAYMENT_TYPE, "", "tc2001");
  }

  @Test
  public void tc2007() {
    checkTableHeaders(INVOICE_PAYMENT_SEARCH_SECONDARY, RDC_PAYMENT_TYPE, "", "tc2004");
  }

  @Test
  public void tc2008() {
    checkTableHeaders(INVOICE_PAYMENT_SEARCH_SECONDARY, CC_PAYMENT_TYPE, "5", "tc2003");
  }

  @Test
  public void tc2009() {
    checkTableHeaders(INVOICE_PAYMENT_SEARCH_SECONDARY, "", "", "tc2000");
  }

  @Test
  public void tc2010() {
    invoicePaymentSearchAccountNum(PRIMARY_ACCOUNT_TABLE_NAME, ACH_PAYMENT_TYPE, "",
        INVOICE_PAYMENT_SEARCH_PRIMARY, "tc2001");
  }

  @Test
  public void tc2011() {
    invoicePaymentSearchAccountNum(PRIMARY_ACCOUNT_TABLE_NAME, CC_PAYMENT_TYPE, "5",
        INVOICE_PAYMENT_SEARCH_PRIMARY,
        "tc2003");
  }

  @Test
  public void tc2012() {
    invoicePaymentSearchAccountNum(PRIMARY_ACCOUNT_TABLE_NAME, RDC_PAYMENT_TYPE, "",
        INVOICE_PAYMENT_SEARCH_PRIMARY, "tc2004");
  }

  @Test
  public void tc2013() {
    invoicePaymentSearchAccountNum(PRIMARY_ACCOUNT_TABLE_NAME, "", "",
        INVOICE_PAYMENT_SEARCH_PRIMARY, "tc2000");
  }

  @Test
  public void tc2014() {
    invoicePaymentSearchAccountNum(SECONDARY_ACCOUNT_TABLE_NAME, ACH_PAYMENT_TYPE, "",
        INVOICE_PAYMENT_SEARCH_SECONDARY,
        "tc2001");
  }

  @Test
  public void tc2015() {
    invoicePaymentSearchAccountNum(SECONDARY_ACCOUNT_TABLE_NAME, CC_PAYMENT_TYPE, "5",
        INVOICE_PAYMENT_SEARCH_SECONDARY,
        "tc2003");
  }

  @Test
  public void tc2016() {
    invoicePaymentSearchAccountNum(SECONDARY_ACCOUNT_TABLE_NAME, RDC_PAYMENT_TYPE, "",
        INVOICE_PAYMENT_SEARCH_SECONDARY,
        "tc2004");
  }

  @Test
  public void tc2017() {
    invoicePaymentSearchAccountNum(SECONDARY_ACCOUNT_TABLE_NAME, "", "",
        INVOICE_PAYMENT_SEARCH_SECONDARY,
        "tc2000");
  }

  /**
   * Create a Direct Debit and a Mail invoiceconfig.
   */
  private void createInvoice(String invoiceOption) throws Exception {
    Logger.info("Create an Invoice: " + invoiceOption);

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc01");
    testSetupPage.open();
    final String pmId = testSetupPage.getString("pmId");

    ListPage invoiceConfigList = new ListPage(pmId);
    invoiceConfigList.open();

    FormCreatePage invoiceConfigForm = invoiceConfigList.clickCreateNewInvoice();

    assertFormDefaults(invoiceConfigForm);

    // set some values on form
    invoiceConfigForm.setDebitOrMail(invoiceOption);
    invoiceConfigForm.setCheckbox(FormPage.CHECKBOX_INCURRED);
    invoiceConfigForm.setCheckbox(FormPage.CHECKBOX_PAYDIRECT);
    invoiceConfigForm.setCheckbox(FormPage.CHECKBOX_NSF);
    String property = invoiceConfigForm.setSelectedPropertyName();

    String bankAccount = "";
    if (invoiceOption.equals(INVOICE_OPTION_DIRECT_DEBIT)) {
      bankAccount = invoiceConfigForm.setSelectedBankAccount();
    }

    FormEditPage formEditPage = invoiceConfigForm.clickSave();

    assertSavedDataDisplayed(formEditPage, property, invoiceOption, "1",
        UtilityManager.getCurrentDate(YEAR_MONTH_DAY_DASH), "n/a");

    final String configId = formEditPage.getConfigId();

    invoiceConfigList.open();

    HashMap<String, String> expectedValues = new HashMap<>();
    expectedValues.put(EXPECTED_PROPERTY, property);
    expectedValues.put(EXPECTED_MODE, invoiceOption);
    if (invoiceOption.equals(INVOICE_OPTION_DIRECT_DEBIT)) {
      expectedValues.put(EXPECTED_BANK_ACCOUNT, bankAccount);
    } else {
      expectedValues.put(EXPECTED_BANK_ACCOUNT, "n/a");
    }

    HashMap<String, String> actualValues = invoiceConfigList.getTableRowMap(configId);
    assertExpectedMap(actualValues, expectedValues);
  }

  @Test
  public void editInvoice() throws Exception {
    Logger.info("Edit an Invoice");

    class TestDataObj {

      private String pmId;
      private String configId;
      private String propertyName;
      private String invoiceOption;
      private String debitDayOfTheMonth;
      private String bankAccountNumber = "n/a";
      private String createdOn;
      private String lastRun;
      private boolean getIncurredFees;
      private boolean getPaydirect;
      private boolean getNsf;
      private List<HashMap<String, Object>> invoiceItems;

      private TestDataObj(TestSetupPage testSetupPage) {
        pmId = testSetupPage.getString("pmId");
        configId = testSetupPage.getString("invoiceConfigId");
        propertyName = testSetupPage.getString("propName");
        invoiceOption = testSetupPage.getString("mode");
        debitDayOfTheMonth = testSetupPage.getString("debitDay");
        if (invoiceOption.equals(INVOICE_OPTION_DIRECT_DEBIT)) {
          bankAccountNumber = testSetupPage.getString("bankAccountNumber");
        }
        createdOn = testSetupPage.getString("createdOn");
        lastRun = testSetupPage.getString("lastRun");
        getIncurredFees = testSetupPage.getFlag("getFees");
        getPaydirect = testSetupPage.getFlag("getPaydirect");
        getNsf = testSetupPage.getFlag("getNsf");
        invoiceItems = testSetupPage.getTable("invoiceItems");
      }
    }

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc03");
    testSetupPage.open();
    TestDataObj testDataObj = new TestDataObj(testSetupPage);

    ListPage invoiceConfigList = new ListPage(testDataObj.pmId);
    invoiceConfigList.open();

    HashMap<String, String> expectedTableValues = new HashMap<>();
    expectedTableValues.put(EXPECTED_PROPERTY, testDataObj.propertyName);
    expectedTableValues.put(EXPECTED_MODE, testDataObj.invoiceOption);
    expectedTableValues.put(EXPECTED_DEBIT_DAY, testDataObj.debitDayOfTheMonth);
    expectedTableValues.put(EXPECTED_BANK_ACCOUNT, testDataObj.bankAccountNumber);
    expectedTableValues.put(EXPECTED_CREATED_ON, testDataObj.createdOn);
    expectedTableValues.put(EXPECTED_LAST_RUN, testDataObj.lastRun);

    HashSet<String> partialMatches = new HashSet<>();
    partialMatches.add(EXPECTED_BANK_ACCOUNT);

    HashMap<String, String> actualValues = invoiceConfigList.getTableRowMap(testDataObj.configId);
    assertExpectedMap(actualValues, expectedTableValues, partialMatches);

    HashMap<String, Object> expectedFormValues = new HashMap<>();
    expectedFormValues.put(EXPECTED_MODE, testDataObj.invoiceOption);
    expectedFormValues.put(EXPECTED_INCURRED, testDataObj.getIncurredFees);
    expectedFormValues.put(EXPECTED_PAYDIRECT, testDataObj.getPaydirect);
    expectedFormValues.put(EXPECTED_NSF, testDataObj.getNsf);
    expectedFormValues.put(EXPECTED_PROPERTY, testDataObj.propertyName);
    if (testDataObj.invoiceOption.equals(INVOICE_OPTION_DIRECT_DEBIT)) {
      expectedFormValues.put(EXPECTED_BANK_ACCOUNT, testDataObj.bankAccountNumber);
    }

    FormEditPage invoiceConfigForm = invoiceConfigList.clickOnEditAction(testDataObj.configId);

    assertFormSetAsExpected(invoiceConfigForm, expectedFormValues);

    // verify invoice items
    assertEquals(testDataObj.invoiceItems.size(), invoiceConfigForm.getInvoiceItemCount(),
        "Expect exactly " + testDataObj.invoiceItems.size() + " invoice item rows");

    Set<String> itemRowIds = new HashSet<>();
    for (HashMap<String, Object> invoiceItem : testDataObj.invoiceItems) {
      String invoiceItemId = (String) invoiceItem.get("invoiceItemId");
      String invoiceItemName = (String) invoiceItem.get("invoiceTypeName");
      String amount = (String) invoiceItem.get("amount");

      itemRowIds.add(invoiceItemId);

      HashMap<String, String> invoiceItemValues = invoiceConfigForm
          .getInvoiceItemRow(invoiceItemId);
      assertEquals(invoiceItemName, invoiceItemValues.get(FormEditPage.ITEM_ROW_NAME),
          "Invoice Item name mismatch");
      assertEquals(amount, invoiceItemValues.get(FormEditPage.ITEM_ROW_AMOUNT),
          "Invoice amount mismatch");
    }

    // add new invoice item
    final String newItemAmount = "50.00";
    final String getInvoiceType = invoiceConfigForm.addInvoiceItem(newItemAmount);

    int newInvoiceItemCount = testDataObj.invoiceItems.size() + 1;
    assertEquals(newInvoiceItemCount, invoiceConfigForm.getInvoiceItemCount(),
        "Expect exactly " + newInvoiceItemCount + " invoice item rows");

    Set<String> itemRowIdsAfterAdd = invoiceConfigForm.getInvoiceItemRowIds();
    itemRowIdsAfterAdd.removeAll(itemRowIds);
    String newItemRowId = itemRowIdsAfterAdd.iterator().next();
    itemRowIds.add(newItemRowId);

    HashMap<String, String> newItemRow = invoiceConfigForm.getInvoiceItemRow(newItemRowId);
    assertTrue(newItemRow.get(FormEditPage.ITEM_ROW_NAME).equals(getInvoiceType));
    assertTrue(newItemRow.get(FormEditPage.ITEM_ROW_AMOUNT).equals(newItemAmount));

    // delete invoice item
    String idToDelete = itemRowIds.iterator().next();
    invoiceConfigForm.deleteInvoiceItem(idToDelete);

    newInvoiceItemCount--;
    assertEquals(newInvoiceItemCount, invoiceConfigForm.getInvoiceItemCount(),
        "Expect exactly " + newInvoiceItemCount + " invoice item rows");

    Set<String> itemRowIdsAfterDelete = invoiceConfigForm.getInvoiceItemRowIds();
    itemRowIds.removeAll(itemRowIdsAfterDelete);
    assertTrue(itemRowIds.iterator().next().equals(idToDelete),
        "Expect deleted id is the one that's no longer present");

    String newProperty = invoiceConfigForm.setSelectedPropertyName();
    Logger.trace("Edited Property to be: " + newProperty);

    invoiceConfigForm.clickSave();

    assertSavedDataDisplayed(invoiceConfigForm, newProperty, testDataObj.invoiceOption, "1",
        testDataObj.createdOn, testDataObj.lastRun);

    invoiceConfigList.open();

    expectedTableValues.put(EXPECTED_PROPERTY, newProperty);

    actualValues = invoiceConfigList.getTableRowMap(testDataObj.configId);
    assertExpectedMap(actualValues, expectedTableValues, partialMatches);
  }

  @Test
  public void canadaInvoiceDirectDebit() {
    Logger.info("Invoice can be set up for a PM in CAD with Direct Debit");

    testBasicInvoice("tc04", INVOICE_OPTION_DIRECT_DEBIT, "CAD", true);
  }

  @Test
  public void canadaInvoiceMail() {
    Logger.info("Invoice can be set up for a PM in CAD with Mail");

    testBasicInvoice("tc04", INVOICE_OPTION_MAIL, "CAD", true);
  }

  @Test
  public void canadaPmCannotCreateUSInvoice() {
    Logger.info("Invoice cannot be saved if we choose currency as USD for Canada PM");

    testBasicInvoice("tc04", INVOICE_OPTION_MAIL, "USD", false);
  }

  @Test
  public void usPmCannotCreateCanadaInvoice() {
    Logger.info("Invoice cannot be saved if we choose currency as CAD for US PM");

    testBasicInvoice("tc01", INVOICE_OPTION_MAIL, "CAD", false);
  }

  @Test
  public void canadaPmSeesCurrencyCodeOnBankAccounts() {
    Logger.info("Currency is shown for bank accounts dropdown - Canada");

    testBankAccountCurrencyCode("tc04", "CAD");
  }

  @Test
  public void usPmSeesCurrencyCodeOnBankAccounts() {
    Logger.info("Currency is shown for bank accounts dropdown - US");

    testBankAccountCurrencyCode("tc01", "USD");
  }

  @Test
  public void canadaPmSeesCurrencyCodeOnProperties() {
    Logger.info("Currency is shown for properties dropdown - Canada");

    testPropertiesCurrencyCode("tc04", "CAD");
  }

  @Test
  public void usPmSeesCurrencyCodeOnProperties() {
    Logger.info("Currency is shown for properties dropdown - US");

    testPropertiesCurrencyCode("tc01", "USD");
  }

  @Test
  public void invoiceForDeletedProperty() {
    Logger.info(
        "Verify that when editing the invoice config it correctly shows the deactivated property selected in the property dropdown.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1490");
    testSetupPage.open();

    final String pmId = testSetupPage.getString("pmId");
    final String invoiceConfigId = testSetupPage.getString("invoiceConfigId");
    final String deletedPropertyName = testSetupPage.getString("deletedPropertyName");

    FormEditPage invoiceConfigForm = new FormEditPage(pmId, invoiceConfigId);
    invoiceConfigForm.open();

    Assert.assertEquals(invoiceConfigForm.getPropertyName(), deletedPropertyName,
        "Deleted property should be selected");
  }

  private void testBankAccountCurrencyCode(String testCase, String currencyCode) {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();
    final String pmId = testSetupPage.getString("pmId");

    FormCreatePage invoiceCreatePage = new FormCreatePage(pmId);
    invoiceCreatePage.open();

    String[] bankAccountNames = invoiceCreatePage.getAllBankAccountNames();
    Assert.assertTrue(bankAccountNames.length > 0, "There is at least one bank account");
    for (String bankAccountName : bankAccountNames) {
      Assert.assertTrue(
          bankAccountName.endsWith("(" + currencyCode + ")"),
          "All bank accounts show correct currency code"
      );
    }
  }

  private void testPropertiesCurrencyCode(String testCase, String currencyCode) {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();
    final String pmId = testSetupPage.getString("pmId");

    FormCreatePage invoiceCreatePage = new FormCreatePage(pmId);
    invoiceCreatePage.open();

    String[] propertyNames = invoiceCreatePage.getAllPropertyNames(false);
    Assert.assertTrue(propertyNames.length > 0, "There is at least one bank account");
    for (String propertyName : propertyNames) {
      Assert.assertTrue(
          propertyName.endsWith("(" + currencyCode + ")"),
          "All properties show correct currency code"
      );
    }
  }

  private void testBasicInvoice(
      String testCase, String invoiceOption, String currencyCode, boolean expectSuccess) {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();
    final String pmId = testSetupPage.getString("pmId");

    FormCreatePage invoiceCreatePage = new FormCreatePage(pmId);
    invoiceCreatePage.open();

    invoiceCreatePage.setDebitOrMail(invoiceOption);
    invoiceCreatePage.setCurrencyCode(currencyCode);
    String property = invoiceCreatePage.setSelectedPropertyName();
    if (invoiceOption.equals(INVOICE_OPTION_DIRECT_DEBIT)) {
      invoiceCreatePage.setSelectedBankAccount();
    }
    FormEditPage formEditPage = invoiceCreatePage.clickSave();

    if (expectSuccess) {
      assertSavedDataDisplayed(formEditPage, property, invoiceOption, "1",
          UtilityManager.getCurrentDate(YEAR_MONTH_DAY_DASH), "n/a");
    } else {
      Assert.assertFalse(formEditPage.getErrorMessage().isEmpty(), "Expect error message");
      Assert.assertTrue(null == formEditPage.getConfigId(), "Config Id not created");
    }
  }

  private void assertFormDefaults(FormPage invoiceConfigForm) throws Exception {
    HashMap<String, Object> expectedValues = new HashMap<>();
    expectedValues.put(EXPECTED_MODE, "Direct Debit");
    expectedValues.put(EXPECTED_RUN_ONCE, false);
    expectedValues.put(EXPECTED_INCURRED, false);
    expectedValues.put(EXPECTED_PAYDIRECT, false);
    expectedValues.put(EXPECTED_NSF, false);

    assertFormSetAsExpected(invoiceConfigForm, expectedValues);
  }

  private void assertFormSetAsExpected(FormPage invoiceConfigForm,
      HashMap<String, Object> expectedValues) throws Exception {
    assertEquals(invoiceConfigForm.getDebitOrMail(), expectedValues.get(EXPECTED_MODE),
        "Mode not set properly on form");

    if (expectedValues.containsKey(EXPECTED_RUN_ONCE)) {
      assertEquals(invoiceConfigForm.isCheckBoxChecked(FormPage.CHECKBOX_RUN),
          (boolean) expectedValues.get(EXPECTED_RUN_ONCE), "Run Once checkbox should be unchecked");
    }
    assertEquals(invoiceConfigForm.isCheckBoxChecked(FormPage.CHECKBOX_INCURRED),
        (boolean) expectedValues.get(EXPECTED_INCURRED),
        "Get Incurred Fees checkbox should be unchecked");
    assertEquals(invoiceConfigForm.isCheckBoxChecked(FormPage.CHECKBOX_PAYDIRECT),
        (boolean) expectedValues.get(EXPECTED_PAYDIRECT),
        "Get PayDirect Fees checkbox should be unchecked");
    assertEquals(invoiceConfigForm.isCheckBoxChecked(FormPage.CHECKBOX_NSF),
        (boolean) expectedValues.get(EXPECTED_NSF), "Get NSF Fees checkbox should be unchecked");

    if (expectedValues.containsKey(EXPECTED_PROPERTY)) {
      assertEquals(invoiceConfigForm.getPropertyName(), expectedValues.get(EXPECTED_PROPERTY),
          "Property name not set as expected");
    }
    if (expectedValues.containsKey(EXPECTED_BANK_ACCOUNT)) {
      assertTrue(invoiceConfigForm.getBankName()
              .contains((String) expectedValues.get(EXPECTED_BANK_ACCOUNT)),
          "Bank Account not set as expected");
    }
  }

  private void assertSavedDataDisplayed(FormEditPage formEditPage, String property,
      String invoiceOption, String debitDay, String createdOn, String lastRun) {
    assertTrue(formEditPage.getSavedProperty().equals(property),
        "Selected Property should be displayed after save");
    assertTrue(formEditPage.getSavedPaymentMode().equals(invoiceOption),
        "Selected Payment mode should be displayed after save");
    assertTrue(formEditPage.getSavedDebitDay().equals(debitDay),
        "Debit Day should be displayed after save");
    assertTrue(formEditPage.getSavedCreatedOn().equals(createdOn),
        "Created on date should be displayed after save");
    assertTrue(formEditPage.getLastRunDate().equals(lastRun),
        "Last Run date should be displayed after save");
  }

  private void isExcelInvoiceAccountNumCorrect(String accountTableName, String excelColumnName,
      String functionName) {
    Logger.info("Excel Spreadsheet shows the correct " + excelColumnName
        + " for each transaction on the report");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc2000");
    testSetupPage.open();

    final String invoiceId = testSetupPage.getString("invoiceId");
    final List<HashMap<String, Object>> transactions = testSetupPage
        .getTable("invoiceTransactions");

    HashMap<String, String> transAccountNum = getAccountNum(transactions, accountTableName);
    InvoiceDetails invoiceDetails = new InvoiceDetails(invoiceId);
    invoiceDetails.open();

    String downloadPath = setUpDownloadPath(this.getClass().getName(), functionName);
    invoiceDetails.clickDownload(downloadPath);
    String fileName = downloadPath + "/pm-fees-invoice-" + invoiceId + ".xlsx";

    final String[][] excelReport = ExcelUtil.getExcelData(fileName, "Sheet1");

    final boolean isAccountCorrect = isAccountNumCorrect(excelReport, transAccountNum,
        excelColumnName);

    Assert.assertTrue(isAccountCorrect, "Primary Account Num is shown as expected on Excel Report");
  }

  private Boolean isAccountNumCorrect(String[][] excelReport,
      HashMap<String, String> transAccountNum, String accountColumnName) {
    final int primaryColumnNum = ExcelUtil.columnNum(excelReport[0], accountColumnName);

    HashMap<String, String> excelTransAccountNum = new HashMap<>();
    String[] transactionList = new String[excelReport.length - 1];
    for (int i = 1; i < excelReport[0].length + 1; i++) {
      excelTransAccountNum.put(excelReport[i][0], excelReport[i][primaryColumnNum]);
      transactionList[i - 1] = excelReport[i][0];
    }
    Boolean isAccountCorrect = true;
    for (String trans : transactionList) {
      if (!transAccountNum.get(trans).equals(excelTransAccountNum.get(trans))) {
        isAccountCorrect = false;
      }
    }
    return isAccountCorrect;
  }

  /**
   * Checks if Excel report show the expected column header
   *
   * @param excelReport excel report
   * @param columnName column name
   * @return boolean
   */
  private boolean hasColumnHeader(String[] excelReport, String columnName) {
    for (String cell : excelReport) {
      if (cell.equals(columnName)) {
        return true;
      }
    }
    return false;
  }


  private HashMap<String, String> getAccountNum(List<HashMap<String, Object>> transactions,
      String columnName) {
    HashMap<String, String> transAccountNum = new HashMap<>();
    for (HashMap<String, Object> transaction : transactions) {
      transAccountNum
          .put(transaction.get("transId").toString(), transaction.get(columnName).toString());
    }
    return transAccountNum;
  }

  private void checkTableHeaders(String expectedTableHeader, String paymentType, String cctype,
      String testCase) {
    Logger.info("Table header ");
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();

    final String invoiceId = testSetupPage.getString("invoiceId");

    InvoicePaymentSearch invoicePaymentSearch = new InvoicePaymentSearch(invoiceId, paymentType,
        cctype);
    invoicePaymentSearch.open();
    String[] tableHeaders = invoicePaymentSearch.getTableHeaders();
    Boolean isAccountHeaderShown = false;
    for (String tableHeader : tableHeaders) {
      if (tableHeader.equals(expectedTableHeader)) {
        isAccountHeaderShown = true;
      }
    }

    Assert.assertTrue(isAccountHeaderShown, expectedTableHeader
        + " is shown on table");
  }

  private void invoicePaymentSearchAccountNum(String excelAccountTableHeader, String paymentType,
      String ccType, String uiColumnName, String testCase) {
    String tableHead = (excelAccountTableHeader.equals(INVOICE_PAYMENT_SEARCH_PRIMARY)) ? "Primary "
        : "Secondary ";
    String pageType;
    switch (paymentType) {
      case "1":
        pageType = "ACH";
        break;
      case "2":
        pageType = "Credit Card";
        break;
      case "3":
        pageType = "RDC";
        break;
      default:
        pageType = "All types of";
        break;
    }
    Logger.info("The " + tableHead + "Account values are correct in Invoice Payment Search page for"
        + pageType + "transactions.");
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();

    final String invoiceId = testSetupPage.getString("invoiceId");
    final List<HashMap<String, Object>> transactions = testSetupPage
        .getTable("invoiceTransactions");

    HashMap<String, String> transAccountNum = getAccountNum(transactions, excelAccountTableHeader);

    InvoicePaymentSearch invoicePaymentSearch = new InvoicePaymentSearch(invoiceId, paymentType,
        ccType);
    invoicePaymentSearch.open();

    String[][] invoiceTransactions = invoicePaymentSearch.getTransactions();
    String[] tableHeaders = invoicePaymentSearch.getTableHeaders();

    int colNum = ExcelUtil.columnNum(tableHeaders, uiColumnName);

    boolean isAccountCorrect = true;
    for (String[] invoiceTransaction : invoiceTransactions) {
      if (!invoiceTransaction[colNum].equals(transAccountNum.get(invoiceTransaction[0]))) {
        isAccountCorrect = false;
      }
    }
    Assert.assertTrue(isAccountCorrect, "Account numbers are correct on UI");
  }

}