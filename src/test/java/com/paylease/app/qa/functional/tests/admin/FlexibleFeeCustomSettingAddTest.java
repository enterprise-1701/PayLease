package com.paylease.app.qa.functional.tests.admin;

import static com.paylease.app.qa.functional.tests.admin.FlexibleFeeCustomSettingEditTest.ANY_PAYMENT_FIELD_LABEL;
import static com.paylease.app.qa.functional.tests.admin.FlexibleFeeCustomSettingEditTest.ANY_PAYMENT_FIELD_VALUE;
import static com.paylease.app.qa.functional.tests.admin.FlexibleFeeCustomSettingEditTest.PLEASE_CHOOSE_TEXT;
import static com.paylease.app.qa.functional.tests.admin.FlexibleFeeStructureTest.ALL_PROPERTIES;
import static com.paylease.app.qa.functional.tests.admin.FlexibleFeeStructureTest.assertLogEntry;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.components.datatable.AdminFlexibleFeeCustomSettingsAdd.Columns;
import com.paylease.app.qa.framework.pages.admin.FlexibleFeeAddAndEdit.FormFields;
import com.paylease.app.qa.framework.pages.admin.FlexibleFeeCustomSettingAddPage;
import com.paylease.app.qa.framework.pages.admin.FlexibleFeeStructure;
import com.paylease.app.qa.framework.pages.admin.LoginPageAdmin;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.utility.listeners.Retry;
import com.paylease.app.qa.testbase.dataproviders.FlexibleFeeStructureDataProvider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FlexibleFeeCustomSettingAddTest extends ScriptBase {

  public static final String REGION = "admin";
  public static final String FEATURE = "flexibleFeeCustomSettingAdd";

  private static final String FIELD_PREFIX = "\n * ";
  private static final String VALUE_PREFIX = "\n    * ";

  @Test
  public void saveAnyProperty() {
    Logger.info("To validate the fee saves for any property (prop id 0)");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6777");
    testSetupPage.open();

    final String pmId = testSetupPage.getString("pmId");

    final String paymentType = "autopay_fixed";
    final String accountType = "credit";
    final String feeType = "percent";
    final String incurredBy = "pm";
    final String feeAmount = "10.95";
    final String tierAmount = "500.00";

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeCustomSettingAddPage addPage = new FlexibleFeeCustomSettingAddPage(pmId);
    addPage.open();

    addPage.setField(FormFields.PAYMENT_FIELD, ANY_PAYMENT_FIELD_VALUE);
    addPage.setField(FormFields.PAYMENT_TYPE, paymentType);
    addPage.setField(FormFields.ACCOUNT_TYPE, accountType);
    addPage.setField(FormFields.FEE_TYPE, feeType);
    addPage.setField(FormFields.INCURRED_BY, incurredBy);
    addPage.setField(FormFields.FEE_AMOUNT, feeAmount);
    addPage.setField(FormFields.TIER_AMOUNT, tierAmount);
    addPage.setAnyPropertyCheckBox();

    addPage.clickSaveAndConfirm();

    FlexibleFeeStructure flexibleFeeStructure = new FlexibleFeeStructure(Integer.parseInt(pmId));

    flexibleFeeStructure.getCustomSettingsTableRowContent();

    Assert.assertTrue(flexibleFeeStructure.pageIsLoaded(),
        "User should be navigated to Flexible Fee Structure page");

    List<Map<String, String>> tableData = flexibleFeeStructure.getCustomSettingsTableRowContent();

    Assert.assertEquals(tableData.size(), 1,
        "There should be exactly one row inserted in Custom Settings Table");

    Map<String, String> expectedRowData = new HashMap<>();
    expectedRowData.put(FormFields.PAYMENT_FIELD.getColumn().getLabel().toLowerCase(), "");
    expectedRowData
        .put(FormFields.PAYMENT_TYPE.getColumn().getLabel().toLowerCase(), "Fixed AutoPay");
    expectedRowData.put(FormFields.ACCOUNT_TYPE.getColumn().getLabel().toLowerCase(), "Credit");
    expectedRowData.put(FormFields.FEE_TYPE.getColumn().getLabel().toLowerCase(), "Percent");
    expectedRowData.put(FormFields.INCURRED_BY.getColumn().getLabel().toLowerCase(), "PM");
    expectedRowData.put(FormFields.FEE_AMOUNT.getColumn().getLabel().toLowerCase(), feeAmount);
    expectedRowData.put(FormFields.TIER_AMOUNT.getColumn().getLabel().toLowerCase(), tierAmount);
    expectedRowData
        .put(FormFields.PROPERTY_NAME.getColumn().getLabel().toLowerCase(), ALL_PROPERTIES);

    Logger.debug(tableData.toString());
    Logger.debug(expectedRowData.toString());

    Assert.assertTrue(tableData.contains(expectedRowData),
        "Table should contain the added custom setting");
  }

  @Test(
      dataProvider = "provideValidFormDataToAdd",
      dataProviderClass = FlexibleFeeStructureDataProvider.class/*,
      retryAnalyzer = Retry.class*/
  )
  public void saveOneProperty(
      String testCaseId, String paymentType, String accountType, String feeType, String incurredBy,
      String expectedPaymentType, String expectedAccountType, String expectedFeeType,
      String expectedIncurredBy
  ) {
    Logger.info(testCaseId + " - To validate the fee saves for the selected property");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6775");
    testSetupPage.open();

    final String pmId = testSetupPage.getString("pmId");
    final String propId = testSetupPage.getString("propId");
    final String propName = testSetupPage.getString("propName");

    final String feeAmount = "10.95";
    final String tierAmount = "500.00";

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeCustomSettingAddPage addPage = new FlexibleFeeCustomSettingAddPage(pmId);
    addPage.open();

    addPage.setField(FormFields.PAYMENT_FIELD, ANY_PAYMENT_FIELD_VALUE);
    addPage.setField(FormFields.PAYMENT_TYPE, paymentType);
    addPage.setField(FormFields.ACCOUNT_TYPE, accountType);
    addPage.setField(FormFields.FEE_TYPE, feeType);
    addPage.setField(FormFields.INCURRED_BY, incurredBy);
    addPage.setField(FormFields.FEE_AMOUNT, feeAmount);
    addPage.setField(FormFields.TIER_AMOUNT, tierAmount);

    HashMap<String, String> rowData = new HashMap<>();
    rowData.put(Columns.PROP_ID.getLabel().toLowerCase(), propId);

    try {
      addPage.toggleRowByRowData(rowData);
    } catch (Exception e) {
      Assert.fail(
          "Unable to select row for Property ID '" + propId + "', exception: " + e.getMessage()
      );
    }

    addPage.clickSaveAndConfirm();

    FlexibleFeeStructure flexibleFeeStructure = new FlexibleFeeStructure(Integer.parseInt(pmId));

    Assert.assertTrue(flexibleFeeStructure.pageIsLoaded(),
        "User should be navigated to Flexible Fee Structure page");

    List<Map<String, String>> tableData = flexibleFeeStructure.getCustomSettingsTableRowContent();

    Assert.assertEquals(tableData.size(), 1,
        "There should be exactly one row inserted in Custom Settings Table");

    Map<String, String> expectedRowData = new HashMap<>();
    expectedRowData.put(FormFields.PAYMENT_FIELD.getColumn().getLabel().toLowerCase(), "");
    expectedRowData
        .put(FormFields.PAYMENT_TYPE.getColumn().getLabel().toLowerCase(), expectedPaymentType);
    expectedRowData
        .put(FormFields.ACCOUNT_TYPE.getColumn().getLabel().toLowerCase(), expectedAccountType);
    expectedRowData.put(FormFields.FEE_TYPE.getColumn().getLabel().toLowerCase(), expectedFeeType);
    expectedRowData
        .put(FormFields.INCURRED_BY.getColumn().getLabel().toLowerCase(), expectedIncurredBy);
    expectedRowData.put(FormFields.FEE_AMOUNT.getColumn().getLabel().toLowerCase(), feeAmount);
    expectedRowData.put(FormFields.TIER_AMOUNT.getColumn().getLabel().toLowerCase(), tierAmount);
    expectedRowData.put(FormFields.PROPERTY_NAME.getColumn().getLabel().toLowerCase(), propName);

    Logger.debug(expectedRowData.toString());
    Logger.debug(tableData.toString());

    Assert.assertTrue(flexibleFeeStructure.isRowByRowDataPresent(expectedRowData),
        "Table should contain the added custom setting");
  }

  @Test
  public void customSettingsAddShowsPropertiesTable() {
    Logger.info("To validate the properties table is displayed.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6777");
    testSetupPage.open();

    final String pmId = testSetupPage.getString("pmId");

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeCustomSettingAddPage addPage = new FlexibleFeeCustomSettingAddPage(pmId);
    addPage.open();

    Assert.assertTrue(addPage.isPropertiesTableSearchPresent(),
        "Properties search should be present on top of table");

    Assert.assertTrue(addPage.isPropertiesTablePaginationPresent(),
        "Properties pagination should be present on top and bottom of table");

    Assert.assertTrue(addPage.isPropertiesTablePageSizeSelectorPresent(),
        "Properties page size selector should be present on top and bottom of table");

    Assert.assertTrue(addPage.isPropertiesTableInfoLabelPresent(),
        "Properties table info label should be present on bottom of table");

    Assert.assertTrue(addPage.isPropertiesTableHeaderPresent(),
        "Properties table header columns should be present");

    Assert.assertTrue(addPage.doesPropertiesTableHeaderAllowSorting(),
        "Properties table header columns should allow sorting");

    List<String> labels = addPage.getPropertiesSelectFilterButtons();

    Assert.assertEquals(labels.size(), 3, "There should be three select filter buttons present");

    Assert.assertEquals(labels.get(0), "All",
        "Properties table should have the select 'All' filter button");
    Assert.assertEquals(labels.get(1), "Selected",
        "Properties table should have the select 'Select' filter button");
    Assert.assertEquals(labels.get(2), "Not Selected",
        "Properties table should have the select 'Not Selected' filter button");
  }

  @Test
  public void propertiesTableShowActiveProperties() {
    Logger.info("To validate the properties table is displayed.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6791");
    testSetupPage.open();

    final String pmId = testSetupPage.getString("pmId");
    final String propId = testSetupPage.getString("propId");
    final String propName = testSetupPage.getString("propName");
    final String city = testSetupPage.getString("propCity");
    final String state = testSetupPage.getString("propState");
    final String zip = testSetupPage.getString("propZip");

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeCustomSettingAddPage addPage = new FlexibleFeeCustomSettingAddPage(pmId);
    addPage.open();

    List<Map<String, String>> tableData = addPage.getPropertiesTableRowContent();

    Assert.assertEquals(tableData.size(), 3,
        "There should be exactly one active property listed in Properties Table");

    Map<String, String> expectedRowData = new HashMap<>();
    expectedRowData.put(Columns.PROP_ID.getLabel().toLowerCase(), propId);
    expectedRowData.put(Columns.PROPERTY_NAME_ADDRESS.getLabel().toLowerCase(), propName);
    expectedRowData.put(Columns.CITY.getLabel().toLowerCase(), city);
    expectedRowData.put(Columns.STATE.getLabel().toLowerCase(), state);
    expectedRowData.put(Columns.ZIP.getLabel().toLowerCase(), zip);

    Assert.assertTrue(tableData.contains(expectedRowData),
        "Table should contain the active property details");
  }

  @Test(
      dataProvider = "provideInvalidFormDataToAdd",
      dataProviderClass = FlexibleFeeStructureDataProvider.class,
      retryAnalyzer = Retry.class
  )
  public void blankAndInvalidFieldsDoesNotSubmitForm(
      String testCaseId, FormFields fieldName, String invalidValue
  ) {
    Logger.info(
        testCaseId + " - To validate form does not submit and error message displayed for "
            + fieldName.toString() + " with '" + invalidValue + "'"
    );

    final String paymentType = "autopay_fixed";
    final String accountType = "credit";
    final String feeType = "percent";
    final String incurredBy = "pm";
    final String feeAmount = "10.95";
    final String tierAmount = "500.00";

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6777");
    testSetupPage.open();

    final String pmId = testSetupPage.getString("pmId");

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeCustomSettingAddPage addPage = new FlexibleFeeCustomSettingAddPage(pmId);
    addPage.open();

    addPage.setField(FormFields.PAYMENT_FIELD, ANY_PAYMENT_FIELD_VALUE);
    addPage.setField(FormFields.PAYMENT_TYPE, paymentType);
    addPage.setField(FormFields.ACCOUNT_TYPE, accountType);
    addPage.setField(FormFields.FEE_TYPE, feeType);
    addPage.setField(FormFields.INCURRED_BY, incurredBy);
    addPage.setField(FormFields.FEE_AMOUNT, feeAmount);
    addPage.setField(FormFields.TIER_AMOUNT, tierAmount);
    addPage.setAnyPropertyCheckBox();

    addPage.setField(fieldName, invalidValue);
    addPage.preparePageUnload();
    addPage.clickSaveNoWait();

    if (FormFields.PROPERTY_NAME == fieldName) {
      addPage.acceptVoidAlert();
    }

    Assert.assertFalse(addPage.isPageChanged(), "Form should not be submitted");
  }

  @Test
  public void auditLogWhenCustomSettingGetsSaved() {
    Logger.info("Verify one Audit Log entry when one or more Custom Settings added");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6778");
    testSetupPage.open();

    final String pmId = testSetupPage.getString("pmId");
    final String propId1 = testSetupPage.getString("propId");
    final String propId2 = testSetupPage.getString("propId2");

    final String paymentType = "autopay_fixed";
    final String accountType = "credit";
    final String feeType = "percent";
    final String incurredBy = "pm";
    final String feeAmount = "10.95";
    final String tierAmount = "500.00";

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeCustomSettingAddPage addPage = new FlexibleFeeCustomSettingAddPage(pmId);
    addPage.open();

    addPage.setField(FormFields.PAYMENT_FIELD, ANY_PAYMENT_FIELD_VALUE);
    addPage.setField(FormFields.PAYMENT_TYPE, paymentType);
    addPage.setField(FormFields.ACCOUNT_TYPE, accountType);
    addPage.setField(FormFields.FEE_TYPE, feeType);
    addPage.setField(FormFields.INCURRED_BY, incurredBy);
    addPage.setField(FormFields.FEE_AMOUNT, feeAmount);
    addPage.setField(FormFields.TIER_AMOUNT, tierAmount);

    HashMap<String, String> rowData = new HashMap<>();
    rowData.put(Columns.PROP_ID.getLabel().toLowerCase(), propId1);

    try {
      addPage.toggleRowByRowData(rowData);
    } catch (Exception e) {
      Assert.fail(
          "Unable to select row for Property ID '" + propId1 + "', exception: " + e.getMessage()
      );
    }

    rowData = new HashMap<>();
    rowData.put(Columns.PROP_ID.getLabel().toLowerCase(), propId2);

    try {
      addPage.toggleRowByRowData(rowData);
    } catch (Exception e) {
      Assert.fail(
          "Unable to select row for Property ID '" + propId2 + "', exception: " + e.getMessage()
      );
    }

    Map<String, String> newFormData = addPage.getFormDisplayValues();

    addPage.clickSaveAndConfirm();

    assertCustomSettingAuditLogMessage(pmId, 2, propId1 + ", " + propId2, newFormData);
  }

  @Test
  public void duplicateCustomSettingsDoNotGetSaved() {
    Logger.info("Pm with multiple properties and at least one custom setting "
        + "for some specific property");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6791");
    testSetupPage.open();

    final String pmId = testSetupPage.getString("pmId");
    final String paymentType = testSetupPage.getString("paymentType");
    final String paymentMethod = testSetupPage.getString("paymentMethod");
    final String feeType = testSetupPage.getString("feeType");
    final String incurredBy = testSetupPage.getString("incurredBy");
    final String varName = testSetupPage.getString("varName");
    final String propId1 = testSetupPage.getString("propId");
    final String propId2 = testSetupPage.getString("propId2");
    final String customSettingsNum = testSetupPage.getString("customSettingsNum");

    String tierAmount = testSetupPage.getString("tierAmount");
    String feeAmount = testSetupPage.getString("feeAmount");

    feeAmount = convertToDecimal(feeAmount);
    tierAmount = convertToDecimal(tierAmount);

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeCustomSettingAddPage addPage = new FlexibleFeeCustomSettingAddPage(pmId);
    addPage.open();

    addPage.setField(FormFields.PAYMENT_FIELD, varName);
    addPage.setField(FormFields.PAYMENT_TYPE, paymentType);
    addPage.setField(FormFields.ACCOUNT_TYPE, paymentMethod);
    addPage.setField(FormFields.FEE_TYPE, feeType);
    addPage.setField(FormFields.INCURRED_BY, incurredBy);
    addPage.setField(FormFields.FEE_AMOUNT, feeAmount);
    addPage.setField(FormFields.TIER_AMOUNT, tierAmount);

    final Map<String, String> postedFormData = addPage.getFormDisplayValues();

    HashMap<String, String> rowData = new HashMap<>();
    rowData.put(Columns.PROP_ID.getLabel().toLowerCase(), propId1);

    try {
      addPage.toggleRowByRowData(rowData);
    } catch (Exception e) {
      Assert.fail(
          "Unable to select row for Property ID '" + propId1 + "', exception: " + e.getMessage()
      );
    }

    rowData = new HashMap<>();
    rowData.put(Columns.PROP_ID.getLabel().toLowerCase(), propId2);

    try {
      addPage.toggleRowByRowData(rowData);
    } catch (Exception e) {
      Assert.fail(
          "Unable to select row for Property ID '" + propId2 + "', exception: " + e.getMessage()
      );
    }

    addPage.clickSaveAndConfirm();

    final Map<String, String> rePostedFormData = addPage.getFormDisplayValues();

    Assert.assertTrue(addPage.isErrorMessagePresent(),
        "There should be an error message displayed");

    for (Map.Entry<String, String> entry : postedFormData.entrySet()) {
      Assert.assertEquals(entry.getValue(), rePostedFormData.get(entry.getKey()),
          "Form field '" + entry.getKey() + "' value should be " + entry.getValue());
    }

    Assert.assertFalse(addPage.isAnyPropertyChecked(), "Any properties check box is not checked.");
    addPage.clickSelectedFilter(2);

    Assert.assertTrue(addPage.isRowByRowDataPresent(rowData),
        "Property row for " + propId2 + " should be selected");

    rowData.put(Columns.PROP_ID.getLabel().toLowerCase(), propId1);

    Assert.assertTrue(addPage.isRowByRowDataPresent(rowData),
        "Property row for " + propId1 + " should be selected");

    FlexibleFeeStructure feePage = new FlexibleFeeStructure(Integer.parseInt(pmId));
    feePage.open();

    Assert.assertEquals(
        feePage.getTotalNumberOfCustomSettings(), Integer.parseInt(customSettingsNum),
        "No custom setting should be added"
    );
  }

  @Test
  public void settingNotSavedWhenPaymentFieldPropertyMismatch() {
    Logger.info("Verify that there is a warning message when there is a mismatch between "
        + "property selected and payment field selected.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6792");
    testSetupPage.open();

    final String pmId = testSetupPage.getString("pmId");
    final String paymentField = testSetupPage.getString("paymentFieldProp2");
    final String customSettingsNum = testSetupPage.getString("customSettingsNum");

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeCustomSettingAddPage addPage = new FlexibleFeeCustomSettingAddPage(pmId);
    addPage.open();

    addPage.setField(FormFields.PAYMENT_FIELD, paymentField);
    addPage.setField(FormFields.PAYMENT_TYPE, "autopay_fixed");
    addPage.setField(FormFields.ACCOUNT_TYPE, "credit");
    addPage.setField(FormFields.FEE_TYPE, "percent");
    addPage.setField(FormFields.INCURRED_BY, "pm");
    addPage.setField(FormFields.FEE_AMOUNT, "10.95");
    addPage.setField(FormFields.TIER_AMOUNT, "500.00");

    addPage.selectAllVisibleProperties();

    final Map<String, String> postedFormData = addPage.getFormDisplayValues();

    addPage.clickSaveAndConfirm();

    final Map<String, String> rePostedFormData = addPage.getFormDisplayValues();

    Assert.assertTrue(addPage.isErrorMessagePresent(),
        "There should be an error message displayed");

    for (Map.Entry<String, String> entry : postedFormData.entrySet()) {
      Assert.assertEquals(entry.getValue(), rePostedFormData.get(entry.getKey()),
          "Form field '" + entry.getKey() + "' value should be " + entry.getValue());
    }

    FlexibleFeeStructure feePage = new FlexibleFeeStructure(Integer.parseInt(pmId));
    feePage.open();

    Assert.assertEquals(
        feePage.getTotalNumberOfCustomSettings(), Integer.parseInt(customSettingsNum),
        "No custom settings should have been saved");
  }

  @Test
  public void cancelConfirmationDoesNotSubmitForm() {
    Logger.info("To validate the form does not submit when user clicks cancel "
        + "in the javascript confirmation");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6777");
    testSetupPage.open();

    final String pmId = testSetupPage.getString("pmId");

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeCustomSettingAddPage addPage = new FlexibleFeeCustomSettingAddPage(pmId);
    addPage.open();

    addPage.setField(FormFields.PAYMENT_FIELD, ANY_PAYMENT_FIELD_VALUE);
    addPage.setField(FormFields.PAYMENT_TYPE, "autopay_fixed");
    addPage.setField(FormFields.ACCOUNT_TYPE, "credit");
    addPage.setField(FormFields.FEE_TYPE, "percent");
    addPage.setField(FormFields.INCURRED_BY, "pm");
    addPage.setField(FormFields.FEE_AMOUNT, "10.95");
    addPage.setField(FormFields.TIER_AMOUNT, "500.00");
    addPage.setAnyPropertyCheckBox();

    addPage.preparePageUnload();
    addPage.clickSaveNoWait();
    addPage.clickCancelOnConfirm();

    Assert.assertFalse(addPage.isPageChanged(), "Form should not be submitted");
  }

  @Test
  public void cancelReturnsToFlexibleFeePage() {
    Logger.info("To validate the cancel link returns user to Flexible Fee page for the given PM");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6777");
    testSetupPage.open();

    final String pmId = testSetupPage.getString("pmId");

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeCustomSettingAddPage addPage = new FlexibleFeeCustomSettingAddPage(pmId);
    addPage.open();

    addPage.clickCancel();

    FlexibleFeeStructure feesPage = new FlexibleFeeStructure(Integer.valueOf(pmId));
    Assert.assertTrue(feesPage.pageIsLoaded(), "Click cancel returns to fee page");
  }

  @Test
  public void saveCustomSettingForMultipleProperties() {
    Logger.info("To validate a separate fee is saved for each selected property");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6778");
    testSetupPage.open();

    final String pmId = testSetupPage.getString("pmId");
    final String propId1 = testSetupPage.getString("propId");
    final String propId2 = testSetupPage.getString("propId2");
    final String propName1 = testSetupPage.getString("propName");
    final String propName2 = testSetupPage.getString("propName2");

    final String paymentType = "autopay_fixed";
    final String accountType = "credit";
    final String feeType = "percent";
    final String incurredBy = "pm";
    final String feeAmount = "10.95";
    final String tierAmount = "500.00";

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeCustomSettingAddPage addPage = new FlexibleFeeCustomSettingAddPage(pmId);
    addPage.open();

    addPage.setField(FormFields.PAYMENT_FIELD, ANY_PAYMENT_FIELD_VALUE);
    addPage.setField(FormFields.PAYMENT_TYPE, paymentType);
    addPage.setField(FormFields.ACCOUNT_TYPE, accountType);
    addPage.setField(FormFields.FEE_TYPE, feeType);
    addPage.setField(FormFields.INCURRED_BY, incurredBy);
    addPage.setField(FormFields.FEE_AMOUNT, feeAmount);
    addPage.setField(FormFields.TIER_AMOUNT, tierAmount);

    HashMap<String, String> rowData = new HashMap<>();
    rowData.put(Columns.PROP_ID.getLabel().toLowerCase(), propId1);

    try {
      addPage.toggleRowByRowData(rowData);
    } catch (Exception e) {
      Assert.fail(
          "Unable to select row for Property ID '" + propId1 + "', exception: " + e.getMessage()
      );
    }

    rowData = new HashMap<>();
    rowData.put(Columns.PROP_ID.getLabel().toLowerCase(), propId2);

    try {
      addPage.toggleRowByRowData(rowData);
    } catch (Exception e) {
      Assert.fail(
          "Unable to select row for Property ID '" + propId2 + "', exception: " + e.getMessage()
      );
    }

    addPage.clickSaveAndConfirm();

    FlexibleFeeStructure flexibleFeeStructure = new FlexibleFeeStructure(Integer.parseInt(pmId));

    Assert.assertTrue(flexibleFeeStructure.pageIsLoaded(),
        "User should be navigated to Flexible Fee Structure page");

    Assert.assertEquals(flexibleFeeStructure.getCustomSettingsTableRowCount(), 2,
        "There should be two rows inserted in Custom Settings Table");

    Map<String, String> expectedRowData = new HashMap<>();
    expectedRowData.put(FormFields.PAYMENT_FIELD.getColumn().getLabel().toLowerCase(), "");
    expectedRowData
        .put(FormFields.PAYMENT_TYPE.getColumn().getLabel().toLowerCase(), "Fixed AutoPay");
    expectedRowData.put(FormFields.ACCOUNT_TYPE.getColumn().getLabel().toLowerCase(), "Credit");
    expectedRowData.put(FormFields.FEE_TYPE.getColumn().getLabel().toLowerCase(), "Percent");
    expectedRowData.put(FormFields.INCURRED_BY.getColumn().getLabel().toLowerCase(), "PM");
    expectedRowData.put(FormFields.FEE_AMOUNT.getColumn().getLabel().toLowerCase(), feeAmount);
    expectedRowData.put(FormFields.TIER_AMOUNT.getColumn().getLabel().toLowerCase(), tierAmount);
    expectedRowData.put(FormFields.PROPERTY_NAME.getColumn().getLabel().toLowerCase(), propName1);

    Assert.assertTrue(flexibleFeeStructure.isRowByRowDataPresent(expectedRowData),
        "Table should contain the added custom setting for first property");

    expectedRowData.put(FormFields.PROPERTY_NAME.getColumn().getLabel().toLowerCase(), propName2);

    Assert.assertTrue(flexibleFeeStructure.isRowByRowDataPresent(expectedRowData),
        "Table should contain the added custom setting for second property");
  }

  @Test
  public void propertiesFiltersBySelected() {
    Logger.info("To validate Properties table allows filter by selected rows");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6774");
    testSetupPage.open();

    final String pmId = testSetupPage.getString("pmId");

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeCustomSettingAddPage addPage = new FlexibleFeeCustomSettingAddPage(pmId);
    addPage.open();

    final int totalNumberOfRows = addPage.getTotalNumberOfProperties();

    List<Map<String, String>> tableData = addPage.getPropertiesTableRowContent();
    Random random = new Random();
    int rowToChoose = random.nextInt(tableData.size());

    Map<String, String> rowData = tableData.get(rowToChoose);

    try {
      addPage.toggleRowByRowData(rowData);
    } catch (Exception e) {
      Assert.fail("Unable to select row number " + rowToChoose + ", exception: " + e.getMessage());
    }

    addPage.clickSelectedFilter(1);

    Assert.assertEquals(1, addPage.getPropertiesTableRowCount(),
        "Expect to see only the one selected row");

    Assert.assertTrue(addPage.isRowByRowDataPresent(rowData),
        "Selected row should be present after applying filter");

    addPage.clickNotSelectedFilter(totalNumberOfRows - 1);

    Assert.assertFalse(addPage.isRowByRowDataPresent(rowData),
        "Selected row should not be visible");

    addPage.clickAllFilter(totalNumberOfRows);

    Assert.assertTrue(addPage.isRowByRowDataPresent(rowData),
        "All rows should be present");
  }

  @Test
  public void paymentFieldDropdownShowsAllUniquePaymentFields() {
    Logger.info("To validate Payment Fields drop down shows all distinct payment "
        + "field options for all properties.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6755");
    testSetupPage.open();

    final String pmId = testSetupPage.getString("pmId");
    final String paymentFieldName1 = testSetupPage.getString("paymentFieldName1");
    final String paymentFieldName2 = testSetupPage.getString("paymentFieldName2");
    final String paymentFieldName3 = testSetupPage.getString("paymentFieldName3");
    final String paymentFieldName4 = testSetupPage.getString("paymentFieldName4");

    List<String> expectedPaymentFields = new ArrayList<>();

    expectedPaymentFields.add(paymentFieldName1);
    expectedPaymentFields.add(paymentFieldName2);
    expectedPaymentFields.add(paymentFieldName3);
    expectedPaymentFields.add(paymentFieldName4);
    expectedPaymentFields.add(PLEASE_CHOOSE_TEXT);
    expectedPaymentFields.add(ANY_PAYMENT_FIELD_LABEL);

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeCustomSettingAddPage addPage = new FlexibleFeeCustomSettingAddPage(pmId);
    addPage.open();

    Map<String, String> actualPaymentFields = addPage.getPaymentFieldOptions();

    Assert.assertEquals(actualPaymentFields.size(), expectedPaymentFields.size(),
        "Payment field options include all payment fields for property plus empty and "
            + ANY_PAYMENT_FIELD_VALUE);

    for (Map.Entry<String, String> entry : actualPaymentFields.entrySet()) {
      Assert.assertTrue(expectedPaymentFields.contains(entry.getValue()),
          "Dropdown should show all unique payment fields across all properties");
    }
  }

  private String convertToDecimal(String rawAmount) {
    String expectedAmount = rawAmount;
    int zerosToAdd = 3 - rawAmount.length();
    for (int i = zerosToAdd; i > 0; --i) {
      expectedAmount = "0" + expectedAmount;
    }
    int decimalIndex = expectedAmount.length() - 2;

    return expectedAmount.substring(0, decimalIndex) + "." + expectedAmount
        .substring(decimalIndex);
  }

  private void assertCustomSettingAuditLogMessage(
      String pmId, int count, String propIds, Map<String, String> formData
  ) {
    String message = assertLogEntry(pmId, count + " PM Flexible Fee Custom Setting(s) added:");
    String expectedMessage = FIELD_PREFIX + "Property IDs:";
    expectedMessage += VALUE_PREFIX + propIds;

    for (Map.Entry<String, String> entry : formData.entrySet()) {

      expectedMessage += FIELD_PREFIX + entry.getKey() + ":";
      expectedMessage += VALUE_PREFIX + "New value: " + entry.getValue();
    }

    Assert.assertTrue(message.contains(expectedMessage),
        "Expected message " + expectedMessage + " should have been found in " + message);
  }
}
