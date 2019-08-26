package com.paylease.app.qa.functional.tests.admin;

import static com.paylease.app.qa.functional.tests.admin.FlexibleFeeStructureTest.assertLogEntry;
import static com.paylease.app.qa.functional.tests.admin.FlexibleFeeStructureTest.assertNoLogEntries;
import static com.paylease.app.qa.functional.tests.admin.FlexibleFeeStructureTest.fixAmount;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.components.datatable.AdminFlexibleFeeCustomSettings.Columns;
import com.paylease.app.qa.framework.pages.admin.FlexibleFeeCustomSettingEditPage;
import com.paylease.app.qa.framework.pages.admin.FlexibleFeeAddAndEdit.FormFields;
import com.paylease.app.qa.framework.pages.admin.FlexibleFeeStructure;
import com.paylease.app.qa.framework.pages.admin.LoginPageAdmin;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.utility.listeners.Retry;
import com.paylease.app.qa.testbase.dataproviders.FlexibleFeeStructureDataProvider;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FlexibleFeeCustomSettingEditTest extends ScriptBase {

  public static final String REGION = "admin";
  public static final String FEATURE = "flexibleFeeCustomSettingEdit";

  public static final String ANY_PAYMENT_FIELD_VALUE = "__any";
  public static final String ANY_PAYMENT_FIELD_LABEL = "Any Payment Field";

  public static final String PLEASE_CHOOSE_TEXT = "Please Choose";

  private static final String FIELD_PREFIX = "\n * ";
  private static final String VALUE_PREFIX = "\n    * ";

  @Test
  public void prepopulateFieldsForEdit() {
    Logger.info("To validate the form fields are prepopulated with data for the selected fee.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6602");
    testSetupPage.open();

    final String pmId = testSetupPage.getString("pmId");
    final String feeId = testSetupPage.getString("feeId");
    final String propId = testSetupPage.getString("propId");
    final String propName = testSetupPage.getString("propName");
    final String varName = testSetupPage.getString("varName");
    final String paymentType = testSetupPage.getString("paymentType");
    final String paymentMethod = testSetupPage.getString("paymentMethod");
    final String feeValue = testSetupPage.getString("feeValue");
    final String feeType = testSetupPage.getString("feeType");
    final String feeIncur = testSetupPage.getString("feeIncur");
    final String tierAmount = testSetupPage.getString("tierAmount");

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeCustomSettingEditPage editPage = new FlexibleFeeCustomSettingEditPage(pmId, feeId);
    editPage.open();

    String expectedVarName = varName.isEmpty() ? ANY_PAYMENT_FIELD_VALUE : varName;

    String expectedPropName =
        propId.equals("0") ? FlexibleFeeStructureTest.ALL_PROPERTIES : propName;

    Assert.assertEquals(editPage.getPaymentTypeValue(), paymentType,
        "Payment Type value should match what is in the database");

    Assert.assertEquals(editPage.getAccountTypeValue(), paymentMethod,
        "Account Type value should match what is in the database");

    Assert.assertEquals(editPage.getFeeTypeValue(), feeType,
        "Fee Type value should match what is in the database");

    Assert.assertEquals(editPage.getFeeAmount(), fixAmount(feeValue),
        "Fee Amount should match what is in the database");

    Assert.assertEquals(editPage.getIncurredByValue(), feeIncur,
        "Incurred By value should match what is in the database");

    Assert.assertEquals(editPage.getPaymentFieldValue(), expectedVarName,
        "Payment Field value should match what is in the database");

    Assert.assertEquals(editPage.getTierAmount(), fixAmount(tierAmount),
        "Tier Amount should match what is in the database");

    Assert.assertEquals(editPage.getPropertyName(), expectedPropName,
        "Property Name should match what is in the database");
  }

  @Test
  public void editFormDoesNotIncludePropertiesTable() {
    Logger.info("To validate the edit form does not include the properties table.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6602");
    testSetupPage.open();

    final String pmId = testSetupPage.getString("pmId");
    final String feeId = testSetupPage.getString("feeId");

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeCustomSettingEditPage editPage = new FlexibleFeeCustomSettingEditPage(pmId, feeId);
    editPage.open();

    Assert.assertFalse(editPage.isPropertiesTablePresent(),
        "Properties table should not be present on the edit form");
  }

  @Test
  public void cancelReturnsToFlexibleFeePage() {
    Logger.info("To validate the cancel link returns user to Flexible Fee page for the given PM");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6602");
    testSetupPage.open();

    final String pmId = testSetupPage.getString("pmId");
    final String feeId = testSetupPage.getString("feeId");

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeCustomSettingEditPage editPage = new FlexibleFeeCustomSettingEditPage(pmId, feeId);
    editPage.open();

    editPage.clickCancel();

    FlexibleFeeStructure feesPage = new FlexibleFeeStructure(Integer.valueOf(pmId));
    Assert.assertTrue(feesPage.pageIsLoaded(), "Click cancel returns to fee page");
  }

  @Test(
      dataProvider = "providePaymentFields",
      dataProviderClass = FlexibleFeeStructureDataProvider.class,
      retryAnalyzer = Retry.class
  )
  public void paymentFieldsForPropertyDisplayed(String testCaseId, String testCaseDesc) {
    Logger.info(testCaseId + " - " + testCaseDesc);

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCaseId);
    testSetupPage.open();

    final String pmId = testSetupPage.getString("pmId");
    final String feeId = testSetupPage.getString("feeId");
    final List<HashMap<String, Object>> paymentFields = testSetupPage.getTable("paymentFields");

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeCustomSettingEditPage editPage = new FlexibleFeeCustomSettingEditPage(pmId, feeId);
    editPage.open();

    HashMap<String, String> paymentFieldOptions = editPage.getPaymentFieldOptions();

    Assert.assertEquals(paymentFieldOptions.size(), paymentFields.size() + 2,
        "Payment field options include all payment fields for property plus empty and "
            + ANY_PAYMENT_FIELD_VALUE);

    Assert.assertTrue(paymentFieldOptions.containsKey(""),
        "Payment field options should include empty option");
    Assert.assertEquals(paymentFieldOptions.get(""), PLEASE_CHOOSE_TEXT,
        "Empty (" + PLEASE_CHOOSE_TEXT + ") payment field option should be present in select box");

    Assert.assertTrue(paymentFieldOptions.containsKey(ANY_PAYMENT_FIELD_VALUE),
        "Payment field options should include any option");
    Assert.assertEquals(paymentFieldOptions.get(ANY_PAYMENT_FIELD_VALUE), "Any Payment Field",
        "'Any Payment Field' payment field option should be present in select box");

    for (HashMap<String, Object> paymentField : paymentFields) {
      String varName = (String) paymentField.get("varName");
      String fieldName = (String) paymentField.get("fieldName");

      Assert.assertTrue(paymentFieldOptions.containsKey(varName),
          "Payment field options should include '" + varName + "' option");
      Assert.assertEquals(paymentFieldOptions.get(varName), fieldName,
          "'" + fieldName + "' payment field option should be present in select box");
    }
  }

  @Test(
      dataProvider = "provideInvalidFormData",
      dataProviderClass = FlexibleFeeStructureDataProvider.class,
      retryAnalyzer = Retry.class
  )
  public void formDoesNotGetSubmitted(
      String testCaseId, FormFields fieldName, String invalidValue
  ) {
    Logger.info(
        "To validate form does not submit and error message displayed for " + fieldName.toString()
            + " with '" + invalidValue + "'"
    );

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCaseId);
    testSetupPage.open();

    final String pmId = testSetupPage.getString("pmId");
    final String feeId = testSetupPage.getString("feeId");

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeCustomSettingEditPage editPage = new FlexibleFeeCustomSettingEditPage(pmId, feeId);
    editPage.open();

    final String originalFieldValue = editPage.getField(fieldName);

    editPage.setField(fieldName, invalidValue);
    editPage.preparePageUnload();
    editPage.clickSaveNoWait();

    Assert.assertFalse(editPage.isPageChanged(), "Form should not be submitted");

    editPage.open();

    Assert.assertEquals(editPage.getField(fieldName), originalFieldValue,
        "Field should have original value");
  }

  @Test(
      dataProvider = "provideInvalidFormData",
      dataProviderClass = FlexibleFeeStructureDataProvider.class,
      retryAnalyzer = Retry.class
  )
  public void noAuditLogWhenFormDoesNotSave(
      String testCaseId, FormFields fieldName, String invalidValue
  ) {
    Logger.info(
        "To validate form does not submit and there is no audit log for " + fieldName.toString()
            + " with '" + invalidValue + "'"
    );

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCaseId);
    testSetupPage.open();

    final String pmId = testSetupPage.getString("pmId");
    final String feeId = testSetupPage.getString("feeId");

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeCustomSettingEditPage editPage = new FlexibleFeeCustomSettingEditPage(pmId, feeId);
    editPage.open();

    editPage.setField(fieldName, invalidValue);
    editPage.preparePageUnload();
    editPage.clickSaveNoWait();

    assertNoLogEntries(pmId, "Should be no log when form was not submitted");
  }

  @Test
  public void javascriptConfirmationIsDisplayed() {
    Logger.info("To validate a javascript confirmation presented when form is to be submitted");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6607");
    testSetupPage.open();

    final String pmId = testSetupPage.getString("pmId");
    final String feeId = testSetupPage.getString("feeId");

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeCustomSettingEditPage editPage = new FlexibleFeeCustomSettingEditPage(pmId, feeId);
    editPage.open();

    editPage.clickSaveNoWait();

    Assert.assertTrue(editPage.isAlertPresent(), "Alert should be present");
  }

  @Test
  public void cancelConfirmationDoesNotSubmitForm() {
    Logger.info(
        "To validate the form does not submit when user clicks cancel in the javascript confirmation");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6607");
    testSetupPage.open();

    final String pmId = testSetupPage.getString("pmId");
    final String feeId = testSetupPage.getString("feeId");

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeCustomSettingEditPage editPage = new FlexibleFeeCustomSettingEditPage(pmId, feeId);
    editPage.open();

    final String originalFieldValue = editPage.getField(FormFields.FEE_AMOUNT);

    editPage.setField(FormFields.FEE_AMOUNT, "6.66");
    editPage.preparePageUnload();
    editPage.clickSaveNoWait();
    editPage.clickCancelOnConfirm();

    Assert.assertFalse(editPage.isPageChanged(), "Form should not be submitted");

    editPage.open();

    Assert.assertEquals(editPage.getField(FormFields.FEE_AMOUNT), originalFieldValue,
        "Field should have original value");
  }

  @Test(
      dataProvider = "provideValidFormData",
      dataProviderClass = FlexibleFeeStructureDataProvider.class,
      retryAnalyzer = Retry.class
  )
  public void customSettingGetsSaved(String testCaseId, FormFields fieldName, String validValue,
      String expectedTableValue) {
    Logger.info("To validate form saves when updating field " + fieldName.toString()
        + " with '" + validValue + "'");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCaseId);
    testSetupPage.open();

    final String pmId = testSetupPage.getString("pmId");
    final String feeId = testSetupPage.getString("feeId");

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeCustomSettingEditPage editPage = new FlexibleFeeCustomSettingEditPage(pmId, feeId);
    editPage.open();

    editPage.setField(fieldName, validValue);
    editPage.clickSaveAndConfirm();

    FlexibleFeeStructure feesPage = new FlexibleFeeStructure(Integer.parseInt(pmId));

    Assert.assertTrue(
        feesPage
            .customSettingsTableRowHasValueInColumn(0, fieldName.getColumn(), expectedTableValue),
        "Saved value is displayed in custom settings table");

    Assert.assertEquals(feesPage.getSuccessMessage(), "Custom Setting changes saved",
        "Should see success message");
  }

  @Test
  public void auditLogWhenCustomSettingGetsSaved() {
    Logger.info("Verify one Audit Log entry when Custom Setting changed on form save");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6609");
    testSetupPage.open();

    final String pmId = testSetupPage.getString("pmId");
    final String feeId = testSetupPage.getString("feeId");

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeCustomSettingEditPage editPage = new FlexibleFeeCustomSettingEditPage(pmId, feeId);
    editPage.open();

    final Map<String, String> originalFormData = editPage.getFormDisplayValues();

    editPage.setField(FormFields.PAYMENT_FIELD, ANY_PAYMENT_FIELD_VALUE);
    editPage.setField(FormFields.PAYMENT_TYPE, "autopay_fixed");
    editPage.setField(FormFields.ACCOUNT_TYPE, "credit");
    editPage.setField(FormFields.FEE_TYPE, "percent");
    editPage.setField(FormFields.INCURRED_BY, "pm");
    editPage.setField(FormFields.FEE_AMOUNT, "10.95");
    editPage.setField(FormFields.TIER_AMOUNT, "500.00");

    Map<String, String> newFormData = editPage.getFormDisplayValues();

    editPage.clickSaveAndConfirm();

    assertCustomSettingAuditLogMessage(pmId, feeId, originalFormData, newFormData);
  }

  @Test
  public void auditLogWhenCustomSettingGetsSavedPartial() {
    Logger.info("Verify one Audit Log entry when Custom Setting partially changed on form save");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6609");
    testSetupPage.open();

    final String pmId = testSetupPage.getString("pmId");
    final String feeId = testSetupPage.getString("feeId");

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeCustomSettingEditPage editPage = new FlexibleFeeCustomSettingEditPage(pmId, feeId);
    editPage.open();

    final Map<String, String> originalFormData = editPage.getFormDisplayValues();

    editPage.setField(FormFields.PAYMENT_FIELD, ANY_PAYMENT_FIELD_VALUE);
    editPage.setField(FormFields.ACCOUNT_TYPE, "credit");
    editPage.setField(FormFields.INCURRED_BY, "pm");
    editPage.setField(FormFields.TIER_AMOUNT, "500.00");

    Map<String, String> newFormData = editPage.getFormDisplayValues();

    editPage.clickSaveAndConfirm();

    assertCustomSettingAuditLogMessage(pmId, feeId, originalFormData, newFormData);
  }

  @Test
  public void noAuditLogWhenFormSavedWithNoChanges() {
    Logger.info("To validate no audit log is created when form saved with no changes");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6607");
    testSetupPage.open();

    final String pmId = testSetupPage.getString("pmId");
    final String feeId = testSetupPage.getString("feeId");

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeCustomSettingEditPage editPage = new FlexibleFeeCustomSettingEditPage(pmId, feeId);
    editPage.open();

    editPage.clickSaveAndConfirm();

    assertNoLogEntries(pmId, "There should be no audit log entry for no changes");
  }

  @Test
  public void customSettingDoesNotSave() {
    Logger.info(
        "To validate that there is a warning message when unable to save data and the form is pre-populated with post data.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6607");
    testSetupPage.open();

    final String pmId = testSetupPage.getString("pmId");
    final String feeId = testSetupPage.getString("feeId");

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeCustomSettingEditPage editPage = new FlexibleFeeCustomSettingEditPage(pmId, feeId);
    editPage.open();

    final Map<String, String> originalFormData = editPage.getFormDisplayValues();

    editPage.setField(FormFields.TIER_AMOUNT, "0.00");

    final Map<String, String> postedFormData = editPage.getFormDisplayValues();

    editPage.clickSaveAndConfirm();

    final Map<String, String> rePostedFormData = editPage.getFormDisplayValues();

    Assert.assertTrue(editPage.isErrorMessagePresent(),
        "There should be an error message displayed");

    for (Map.Entry<String, String> entry : postedFormData.entrySet()) {
      Assert.assertEquals(entry.getValue(), rePostedFormData.get(entry.getKey()),
          "Form field '" + entry.getKey() + "' value should be " + entry.getValue());
    }

    FlexibleFeeStructure feePage = new FlexibleFeeStructure(Integer.parseInt(pmId));
    feePage.open();

    for (Map.Entry<String, String> entry : originalFormData.entrySet()) {
      String expectedValue = entry.getValue();
      if (entry.getKey().equalsIgnoreCase(Columns.PAYMENT_FIELD.getLabel())) {
        if (expectedValue.equals(ANY_PAYMENT_FIELD_LABEL)) {
          expectedValue = "";
        }
      }
      if (entry.getKey().equalsIgnoreCase(Columns.TIER_AMOUNT.getLabel())) {
        double tierAmount = entry.getValue().isEmpty()
            ? 0.0 : Double.parseDouble(entry.getValue().replaceAll(",", ""));

        if (tierAmount == 0) {
          expectedValue = "";
        }
      }
      Assert.assertTrue(
          feePage.customSettingsTableRowHasValueInColumn(0, entry.getKey(), expectedValue),
          "Custom setting should not have changed '" + entry.getKey() + "' expected: "
              + expectedValue);
    }
  }

  @Test
  public void noAuditLogWhenCustomSettingDoesNotSave() {
    Logger.info("To validate that there is no audit log when a custom setting fails to save to db");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6607");
    testSetupPage.open();

    final String pmId = testSetupPage.getString("pmId");
    final String feeId = testSetupPage.getString("feeId");

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    FlexibleFeeCustomSettingEditPage editPage = new FlexibleFeeCustomSettingEditPage(pmId, feeId);
    editPage.open();

    editPage.setField(FormFields.TIER_AMOUNT, "0.00");

    editPage.clickSaveAndConfirm();

    assertNoLogEntries(pmId, "There should not be an audit log when custom setting fails to save");
  }

  private void assertCustomSettingAuditLogMessage(
      String pmId, String feeId, Map<String, String> originalFormData,
      Map<String, String> valuesToChange
  ) {
    String message = assertLogEntry(pmId, "PM Flexible Fee Custom Setting " + feeId + " updated:");

    for (Map.Entry<String, String> entry : valuesToChange.entrySet()) {
      String oldValue = originalFormData.get(entry.getKey());
      String newValue = entry.getValue();

      if (oldValue.equals(newValue)) {
        continue;
      }

      String expectedMessage = FIELD_PREFIX + entry.getKey() + ":";
      expectedMessage += VALUE_PREFIX + "Old value: " + oldValue;
      expectedMessage += VALUE_PREFIX + "New value: " + newValue;

      Assert.assertTrue(message.contains(expectedMessage),
          "Expected message " + expectedMessage + " should have been found in " + message);
    }
  }
}
