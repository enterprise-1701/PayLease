package com.paylease.app.qa.testbase.dataproviders;

import static com.paylease.app.qa.functional.tests.admin.FlexibleFeeCustomSettingEditTest.ANY_PAYMENT_FIELD_VALUE;

import com.paylease.app.qa.framework.components.datatable.AdminFlexibleFeeCustomSettings.Columns;
import com.paylease.app.qa.framework.pages.admin.FlexibleFeeAddAndEdit.FormFields;
import com.paylease.app.qa.functional.tests.admin.FlexibleFeeStructureTest.Amounts;
import org.testng.annotations.DataProvider;

public class FlexibleFeeStructureDataProvider {

  /**
   * Dataprovider to provide test case ids and type of invalid data.
   *
   * @return object array of testcase ids and type of invalid data
   */
  @DataProvider(parallel = true)
  public Object[][] provideInvalidInput() {
    return new Object[][]{
        //Test case id, amountType
        {"tc6173", Amounts.BLANK},
        {"tc6174", Amounts.NEGATIVE},
        {"tc6175", Amounts.CHARS},
    };
  }

  /**
   * To provide different set up to the test cases.
   *
   * @return object array of test case ids
   */
  @DataProvider(parallel = true)
  public Object[][] providePmsForReading() {
    return new Object[][]{
        //Test case id
        {"tc6150"},
        {"tc6169"}
    };
  }

  /**
   * To provide different set up of the PM Level Settings to the test cases.
   *
   * @return object array of test case ids
   */
  @DataProvider(parallel = true)
  public Object[][] providePmLevelSettings() {
    return new Object[][]{
        //Test case id
        {"tc6507"},
        {"tc6507a"},
        {"tc6507b"},
        {"tc6507c"},
        {"tc6507d"},
        {"tc6507e"},
        {"tc6507f"},
        {"tc6507g"},
        {"tc6507h"},
    };
  }

  /**
   * To provide different set up of the PM Level Settings to the test cases.
   *
   * @return object array of test case ids
   */
  @DataProvider(parallel = true)
  public Object[][] providePmLevelSettingsToUpdate() {
    return new Object[][]{
        //Test case id
        {"tc6508"},
        {"tc6508a"},
    };
  }

  /**
   * To provide invalid data to the pm level settings area of the flexible fee form.
   *
   * @return object array of test case ids
   */
  @DataProvider(parallel = true)
  public Object[][] provideInvalidPmLevelSettings() {
    return new Object[][]{
        //Test case id, type of invalid data
        {"tc6510", Amounts.BLANK},
        {"tc6511", Amounts.NEGATIVE},
        {"tc6512", Amounts.CHARS},
    };
  }

  /**
   * To provide different set up to the test cases.
   *
   * @return object array of test case ids
   */
  @DataProvider(parallel = true)
  public Object[][] providePaymentFields() {
    return new Object[][]{
        //Test case id, Test case description
        {"tc6604",
            "To validate Payment Fields drop down shows all payment fields only related to the property"},
        {"tc6605",
            "To validate Payment Fields drop down shows all distinct payment field options for all properties."}
    };
  }

  /**
   * To provide different set up to the test cases.
   *
   * @return object array of test case ids
   */
  @DataProvider(parallel = true)
  public Object[][] provideInvalidFormData() {
    return new Object[][]{
        //Test case id, Form Field, value
        {"tc6607", FormFields.PAYMENT_TYPE, ""},
        {"tc6607", FormFields.ACCOUNT_TYPE, ""},
        {"tc6607", FormFields.FEE_TYPE, ""},
        {"tc6607", FormFields.FEE_AMOUNT, ""},
        {"tc6607", FormFields.FEE_AMOUNT, "-8.65"},
        {"tc6607", FormFields.FEE_AMOUNT, "joey"},
        {"tc6607", FormFields.INCURRED_BY, ""},
        {"tc6607", FormFields.TIER_AMOUNT, ""},
        {"tc6607", FormFields.TIER_AMOUNT, "-7.34"},
        {"tc6607", FormFields.TIER_AMOUNT, "david"},
        {"tc6607", FormFields.PAYMENT_FIELD, ""},
    };
  }

  /**
   * To provide different set up to the test cases.
   *
   * @return object array of test case ids
   */
  @DataProvider(parallel = true)
  public Object[][] provideInvalidFormDataToAdd() {
    return new Object[][]{
        //Test case id, Form Field, value
        {"tc6779", FormFields.PAYMENT_TYPE, ""},
        {"tc6779a", FormFields.ACCOUNT_TYPE, ""},
        {"tc6779b", FormFields.FEE_TYPE, ""},
        {"tc6779c", FormFields.FEE_AMOUNT, ""},
        {"tc6779d", FormFields.FEE_AMOUNT, "-8.65"},
        {"tc6779e", FormFields.FEE_AMOUNT, "joey"},
        {"tc6779f", FormFields.INCURRED_BY, ""},
        {"tc6779g", FormFields.TIER_AMOUNT, ""},
        {"tc6779h", FormFields.TIER_AMOUNT, "-7.34"},
        {"tc6779i", FormFields.TIER_AMOUNT, "david"},
        {"tc6779j", FormFields.PAYMENT_FIELD, ""},
        {"tc6779k", FormFields.PROPERTY_NAME, ""},
    };
  }

  /**
   * To provide values to form to add a Custom setting.
   *
   * @return object array of test case ids
   */
  @DataProvider(parallel = true)
  public Object[][] provideValidFormDataToAdd() {
    return new Object[][]{
        //Test case id, Form Field, value
        {"tc6779", "one_time", "ach", "fixed", "resident", "One-Time Payment", "ACH", "Fixed",
            "Resident"},
        {"tc6779a", "autopay_fixed", "credit", "percent", "pm", "Fixed AutoPay", "Credit",
            "Percent", "PM"},
        {"tc6779b", "autopay_variable", "debit", "fixed", "resident", "Variable AutoPay", "Debit",
            "Fixed", "Resident"},
    };
  }

  /**
   * To provide different set up to the test cases.
   *
   * @return object array of test case ids
   */
  @DataProvider(parallel = true)
  public Object[][] provideValidFormData() {
    return new Object[][]{
        //Test case id, Form Field, value to choose, expected display value in table
        {"tc6609", FormFields.PAYMENT_TYPE, "autopay_fixed", "Fixed AutoPay"},
        {"tc6609", FormFields.ACCOUNT_TYPE, "credit", "Credit"},
        {"tc6609", FormFields.FEE_TYPE, "percent", "Percent"},
        {"tc6609", FormFields.FEE_AMOUNT, "10.95", "10.95"},
        {"tc6609", FormFields.INCURRED_BY, "pm", "PM"},
        {"tc6609", FormFields.TIER_AMOUNT, "500.00", "500.00"},
        {"tc6609", FormFields.PAYMENT_FIELD, ANY_PAYMENT_FIELD_VALUE, ""},
    };
  }

  /**
   * To provide invalid data to the pm level settings area of the flexible fee form.
   *
   * @return object array of test case ids
   */
  @DataProvider(parallel = true)
  public Object[][] provideSearchTerms() {
    return new Object[][]{
        //search term, should it be found
        {"All Properties", true},
        {"Bar Tab", true},
        {"One-Time Payment", true},
        {"ACH", true},
        {"Percent", true},
        {"1.69", true},
        {"Resident", true},
        {"123.00", true},
        {"Amey rules!", false},
    };
  }

  /**
   * To provide data for the Custom Settings table.
   *
   * @return object array of test cases
   */
  @DataProvider(parallel = true)
  public Object[][] provideCustomSettingData() {
    return new Object[][]{
        //testCaseId, column, expectedValueField, expected Value if blank
        {"tc6592_01a", Columns.PROPERTY_NAME, "propertyName", "All Properties"},
        {"tc6592_01b", Columns.PROPERTY_NAME, "propertyName", "All Properties"},
        {"tc6592_02a", Columns.PAYMENT_FIELD, "paymentFieldName", ""},
        {"tc6592_02b", Columns.PAYMENT_FIELD, "paymentFieldName", ""},
        {"tc6592_02c", Columns.PAYMENT_FIELD, "paymentFieldName", ""},
        {"tc6592_03a", Columns.PAYMENT_TYPE, "paymentType", "One-Time Payment"},
        {"tc6592_03b", Columns.PAYMENT_TYPE, "paymentType", "Fixed AutoPay"},
        {"tc6592_03c", Columns.PAYMENT_TYPE, "paymentType", "Variable AutoPay"},
        {"tc6592_03d", Columns.PAYMENT_TYPE, "paymentType", ""},
        {"tc6592_04a", Columns.ACCOUNT_TYPE, "paymentMethod", "ACH"},
        {"tc6592_04b", Columns.ACCOUNT_TYPE, "paymentMethod", "Credit"},
        {"tc6592_04c", Columns.ACCOUNT_TYPE, "paymentMethod", "Debit"},
        {"tc6592_04d", Columns.ACCOUNT_TYPE, "paymentMethod", ""},
        {"tc6592_05a", Columns.FEE_TYPE, "feeType", "Fixed"},
        {"tc6592_05b", Columns.FEE_TYPE, "feeType", "Percent"},
        {"tc6592_05c", Columns.FEE_TYPE, "feeType", ""},
        {"tc6592_06a", Columns.FEE_AMOUNT, "feeAmount", ""},
        {"tc6592_07a", Columns.INCURRED_BY, "feeIncur", "Resident"},
        {"tc6592_07b", Columns.INCURRED_BY, "feeIncur", "PM"},
        {"tc6592_07c", Columns.INCURRED_BY, "feeIncur", ""},
        {"tc6592_08a", Columns.TIER_AMOUNT, "tierAmount", ""},
        {"tc6592_08b", Columns.TIER_AMOUNT, "tierAmount", ""},
    };
  }
}
