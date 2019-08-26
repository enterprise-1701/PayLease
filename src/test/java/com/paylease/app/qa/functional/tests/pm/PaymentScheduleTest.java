package com.paylease.app.qa.functional.tests.pm;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.features.PaymentFlow;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.paymentflow.SchedulePage;
import java.util.Calendar;
import java.util.Date;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PaymentScheduleTest extends ScriptBase {

  private static final String REGION = "pm";
  private static final String FEATURE = "paymentSchedule";

  @Test
  public void indefiniteBoxCheckedByDefault() {
    Logger.info("By default, the indefinite checkbox is checked");

    SchedulePage schedulePage = initTestFap("tc2");
    Assert.assertTrue(schedulePage.isIndefiniteChecked(), "Indefinite checkbox is checked");
  }

  @Test
  public void currentAndPastDatesNotSelectableOnStart() {
    Logger.info("Should not be able to select a current or past Recurring Payment date");

    SchedulePage schedulePage = initTestFap("tc3");
    schedulePage.openCalendar(SchedulePage.DATE_PICKER_START_DATE);

    Calendar dateToCheck = Calendar.getInstance();

    dateToCheck.setTime(new Date());
    Assert.assertFalse(schedulePage.isDayInMonthEnabled(dateToCheck),
        "Today can not be selected as start date");

    dateToCheck.add(Calendar.MONTH, -1);
    Assert.assertFalse(schedulePage.isDayInMonthEnabled(dateToCheck),
        "Previous date can not be selected as start date");
  }

  @Test
  public void pastDatesNotSelectableOnEnd() {
    Logger.info("Should not be able to select a current or past Recurring Payment date");

    SchedulePage schedulePage = initTestFap("tc4");
    Calendar dateToCheck = Calendar.getInstance();
    dateToCheck.add(Calendar.MONTH, -1);

    schedulePage.openCalendar(SchedulePage.DATE_PICKER_END_DATE);
    Assert.assertFalse(schedulePage.isMonthEnabled(dateToCheck),
        "Previous date can not be selected as end date");
  }

  @Test
  public void startDateRequired() {
    Logger.info("Error messages appear when Recurring Payment Date is not selected");

    SchedulePage schedulePage = initTestFap("tc9");
    schedulePage.prepField(SchedulePage.FIELD_START_DATE, SchedulePage.FIELD_VALUE_EMPTY);
    schedulePage.fillAndSubmitWithErrors();

    Assert.assertEquals(schedulePage.getStartDateError(), "Payment Start Date is required",
        "Expect message that start date is required");
  }

  @Test
  public void frequencyRequired() {
    Logger.info("Error messages appear when Payment Frequency is not selected");

    SchedulePage schedulePage = initTestFap("tc10");
    schedulePage.prepField(SchedulePage.FIELD_FREQUENCY, SchedulePage.FIELD_VALUE_EMPTY);
    schedulePage.fillAndSubmitWithErrors();

    Assert.assertEquals(schedulePage.getFrequencyError(), "Payment Frequency is required",
        "Expect message that frequency is required");
  }

  @Test
  public void endDateRequiredWhenNotIndefinite() {
    Logger.info("Error messages appears when Final payment month/year is not selected");

    SchedulePage schedulePage = initTestFap("tc11");
    schedulePage.prepField(SchedulePage.FIELD_END_DATE, SchedulePage.FIELD_VALUE_EMPTY);
    schedulePage.prepField(SchedulePage.FIELD_INDEFINITE, false);
    schedulePage.fillAndSubmitWithErrors();

    Assert.assertEquals(schedulePage.getEndDateError(), "Final Payment Date is required",
        "Expect message that end date is required");
  }

  @Test
  public void mutlipleErrorMessagesForRequiredFields() {
    Logger.info("Error messages appear when no fields are filled out or selected");

    SchedulePage schedulePage = initTestFap("tc12");
    schedulePage.clickContinueWithErrors();

    Assert.assertEquals(schedulePage.getStartDateError(), "Payment Start Date is required",
        "Expect message that start date is required");
    Assert.assertEquals(schedulePage.getFrequencyError(), "Payment Frequency is required",
        "Expect message that frequency is required");
  }

  @Test
  public void existingAutoPayWarning() {
    Logger.info("Existing AutoPay message appears while setting up a new AutoPay");

    SchedulePage schedulePage = initTestVap("tc16");
    Assert.assertTrue(schedulePage.hasExistingAutoPayWarning(),
        "Existing AutoPay warning should be present");
  }

  @Test
  public void recentPaymentWarning() {
    Logger.info("Recently scheduled payment message appears while setting up a new AutoPay");

    SchedulePage schedulePage = initTestVap("tc17");
    Assert.assertTrue(schedulePage.hasRecentPaymentWarning(),
        "Recent Payment warning should be present");

    schedulePage.hoverRecentPaymentWarningHelp();
    Assert.assertEquals(schedulePage.getTooltipText(),
        "A payment was made on this PayLease account within the past 5 days. Previous payments can be viewed on the Payment History page. To make another payment, click Continue.",
        "Correct tooltip message is displayed");
  }

  @Test
  public void tooManyDecimalsInvalidMax() {
    Logger.info("Entering two decimals in the Maximum Limit field will generate an error message");

    SchedulePage schedulePage = initTestVap("tc19");
    schedulePage.prepField(SchedulePage.FIELD_MAX_LIMIT_ENABLED, true);
    schedulePage.prepField(SchedulePage.FIELD_MAX_LIMIT_VALUE, "1.1.1");
    schedulePage.fillAndSubmitWithErrors();

    Assert.assertEquals(schedulePage.getMaxLimitValueError(),
        "Please enter a numeric value for Maximum Limit",
        "Warning for invalid max limit is present");
  }

  @Test
  public void variableAutoPayFillValidAdvancesToPaymentMethod() {
    Logger
        .info("After all fields filled out, PM is able to press continue to navigate to next page");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc20");
    testSetupPage.open();
    final String transId = testSetupPage.getString("transactionId");

    PaymentFlow paymentFlow = new PaymentFlow(
        PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_VARIABLE_AUTO, transId);
    paymentFlow.openStep(PaymentFlow.STEP_SCHEDULE);

    SchedulePage schedulePage = new SchedulePage();
    schedulePage.prepField(SchedulePage.FIELD_MAX_LIMIT_ENABLED, true);
    schedulePage.fillAndSubmitPaymentScheduleDetails();

    Assert.assertEquals(paymentFlow.getCurrentStep(), PaymentFlow.STEP_METHOD,
        "Advanced to payment method page");
  }

  private SchedulePage initTestFap(String testCase) {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();
    final String transId = testSetupPage.getString("transactionId");

    PaymentFlow paymentFlow = new PaymentFlow(
        PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_FIXED_AUTO, transId);
    paymentFlow.openStep(PaymentFlow.STEP_SCHEDULE);

    return new SchedulePage();
  }

  private SchedulePage initTestVap(String testCase) {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();
    final String transId = testSetupPage.getString("transactionId");

    PaymentFlow paymentFlow = new PaymentFlow(
        PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_VARIABLE_AUTO, transId);
    paymentFlow.openStep(PaymentFlow.STEP_SCHEDULE);

    return new SchedulePage();
  }
}
