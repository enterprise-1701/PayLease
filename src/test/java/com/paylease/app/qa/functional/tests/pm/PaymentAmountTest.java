package com.paylease.app.qa.functional.tests.pm;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.features.PaymentFlow;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.paymentflow.PaymentAmountPage;
import com.paylease.app.qa.framework.utility.listeners.Retry;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class PaymentAmountTest extends ScriptBase {

  private static final String REGION = "pm";
  private static final String FEATURE = "paymentAmount";
  private static final String PM_FIXED_AMOUNT_AND_DISALLOW_PRE_PAYMENTS_DISABLED = "PM is able "
      + "to edit and can overpay with property level customizations FIXED_AMOUNT and "
      + "DISALLOW_PRE_PAYMENTS disabled";
  private static final String PM_FIXED_AMOUNT_ENABLED_DISALLOW_PRE_PAYMENTS_DISABLED = "PM is "
      + "able to edit and can overpay with property level customizations FIXED_AMOUNT enabled"
      + "and DISALLOW_PRE_PAYMENTS disabled";
  private static final String PM_FIXED_AMOUNT_AND_DISALLOW_PRE_PAYMENTS_ENABLED = "PM is "
      + "able to edit and is able to overpay with property level customizations FIXED_AMOUNT enabled"
      + "and DISALLOW_PRE_PAYMENTS disabled";
  private static final String PM_FIXED_AMOUNT_DISABLED_DISALLOW_PRE_PAYMENTS_ENABLED = "PM is "
      + "able to edit and is able to overpay with property level customizations FIXED_AMOUNT enabled"
      + "and DISALLOW_PRE_PAYMENTS disabled";


  @Test
  public void enterValidAmount() {
    Logger.info("PM is able to enter a valid total amount and select the continue button");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();
    final String transId = testSetupPage.getString("transactionId");
    final String paymentFieldLabel = testSetupPage.getString("payFieldRent");

    PaymentFlow paymentFlow = openPaymentAmountPage(transId);

    PaymentAmountPage paymentAmountPage = new PaymentAmountPage();
    paymentAmountPage.setPaymentFieldAmount(paymentFieldLabel, "100.00");
    paymentAmountPage.clickContinueButton();
    Assert.assertEquals(
        paymentFlow.getCurrentStep(), PaymentFlow.STEP_METHOD,
        "Should advance to Payment Method page");
  }

  @Test
  public void amountRequired() {
    Logger.info("Error message appears when amount is not entered");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc2");
    testSetupPage.open();
    final String transId = testSetupPage.getString("transactionId");

    PaymentFlow paymentFlow = openPaymentAmountPage(transId);

    PaymentAmountPage paymentAmountPage = new PaymentAmountPage();
    paymentAmountPage.clickContinueButton();
    Assert.assertEquals(
        paymentFlow.getCurrentStep(), PaymentFlow.STEP_AMOUNT,
        "Should stay on Payment Amount page");
    Assert.assertTrue(
        paymentAmountPage.getErrorMessage().equals(
            "Choose at least one payment type and enter an amount"
        ),
        "Expect error message when amount not filled in"
    );
  }

  @Test
  public void pmCanEditLockedAmount() {
    Logger.info("PM is able to edit the amount even when the amount is locked");

    testPmCanEditAmount("tc3");
  }

  @Test
  public void pmCanEditPrepopulatedAmount() {
    Logger.info("PM is able to edit the amount even with the amount is pre-populated but unlocked");

    testPmCanEditAmount("tc4");
  }

  @Test
  public void pmCanEditMultipleFieldsWithPrepopulatedAmount() {
    Logger.info("PM is able to edit pre-populated amount on all payment fields when unlocked");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc5");
    testSetupPage.open();
    final String transId = testSetupPage.getString("transactionId");
    final String rentFieldLabel = testSetupPage.getString("payFieldRent");
    final String lateFieldLabel = testSetupPage.getString("payFieldLate");
    final String rentAmount = testSetupPage.getString("amountRent");
    final String lateAmount = testSetupPage.getString("amountLate");

    openPaymentAmountPage(transId);

    PaymentAmountPage paymentAmountPage = new PaymentAmountPage();

    String newAmountStr = setAmountInField(paymentAmountPage, rentFieldLabel, rentAmount);
    Assert.assertTrue(
        paymentAmountPage.getPaymentFieldAmount(rentFieldLabel).equals(newAmountStr),
        "Field Amount should have been updated to new amount");

    String newAmountStr2 = setAmountInField(paymentAmountPage, lateFieldLabel, lateAmount);
    Assert.assertTrue(
        paymentAmountPage.getPaymentFieldAmount(lateFieldLabel).equals(newAmountStr2),
        "Second field amount should have been updated to new amount");
  }

  @Test
  public void pmCanEditNonPrepopulatedAmount() {
    Logger.info("PM is able to enter an amount into the non pre-populated payment field "
        + "when there exists another payment field with pre-populated amount");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6");
    testSetupPage.open();
    final String transId = testSetupPage.getString("transactionId");
    final String lateFieldLabel = testSetupPage.getString("payFieldLate");

    PaymentFlow paymentFlow = openPaymentAmountPage(transId);

    PaymentAmountPage paymentAmountPage = new PaymentAmountPage();

    String newAmountStr = setAmountInField(paymentAmountPage, lateFieldLabel, "0");
    Assert.assertTrue(
        paymentAmountPage.getPaymentFieldAmount(lateFieldLabel).equals(newAmountStr),
        "Field Amount should have been updated to new amount");
    paymentAmountPage.clickContinueButton();
    Assert.assertEquals(
        paymentFlow.getCurrentStep(), PaymentFlow.STEP_METHOD,
        "Should advance to Payment Method page");
  }

  @Test
  public void showExistingAutoPayWarning() {
    Logger.info("A warning message appears if the resident already has a pre-existing auto pay");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc7");
    testSetupPage.open();
    final String transId = testSetupPage.getString("transactionId");

    openPaymentAmountPage(transId);

    PaymentAmountPage paymentAmountPage = new PaymentAmountPage();
    Assert.assertTrue(
        paymentAmountPage.getFirstWarningMessage().contains("You have an existing AutoPay."),
        "Warning about existing autopay is present");
  }

  @Test
  public void showBlockedPayments() {
    Logger.info("When PM has 'Stop Accepting Payments' set, there is an error message");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc8");
    testSetupPage.open();
    final String transId = testSetupPage.getString("transactionId");

    openPaymentAmountPage(transId);

    PaymentAmountPage paymentAmountPage = new PaymentAmountPage();
    Assert.assertTrue(
        paymentAmountPage.hasBlockedPaymentsWarning(), "Warning about blocked payments is present");
  }

  @Test
  public void showAmountOwed() {
    Logger.info("Amount Owed is displayed");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc9");
    testSetupPage.open();
    final String transId = testSetupPage.getString("transactionId");
    final String rentAmount = testSetupPage.getString("amountRent");

    openPaymentAmountPage(transId);

    PaymentAmountPage paymentAmountPage = new PaymentAmountPage();
    Assert.assertTrue(
        paymentAmountPage.isAmountOwedPresent(), "Amount Owed information is present");
    Assert.assertTrue(
        paymentAmountPage.getAmountInfoAmount().startsWith("$" + rentAmount),
        "Amount Owed should match rent amount");
  }

  @Test
  public void showAmountUpdatedDate() {
    Logger.info("'As of:' timestamp is updated when a resident is locked for an amount");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc10");
    testSetupPage.open();
    final String transId = testSetupPage.getString("transactionId");
    final String rentFieldLabel = testSetupPage.getString("payFieldRent");
    final String rentAmountUpdated = testSetupPage.getString("rentAmountUpdated");

    openPaymentAmountPage(transId);

    PaymentAmountPage paymentAmountPage = new PaymentAmountPage();
    Assert.assertTrue(
        paymentAmountPage.getPaymentFieldUpdated(rentFieldLabel).equals(rentAmountUpdated),
        "Payment Field with pre-populated amount should show updated date");
  }

  @DataProvider(name = "pmUiTestSetUp")
  private Object[][] pmUiTestSetUp() {
    return new Object[][] {
        // testCaseId, loggerInfo
        {
          "tc6770FixedAmount", PM_FIXED_AMOUNT_AND_DISALLOW_PRE_PAYMENTS_DISABLED
        },
        {
          "tc6770OverPay", PM_FIXED_AMOUNT_AND_DISALLOW_PRE_PAYMENTS_DISABLED
        },
        {
          "tc6771FixedAmount", PM_FIXED_AMOUNT_ENABLED_DISALLOW_PRE_PAYMENTS_DISABLED
        },
        {
          "tc6771OverPay", PM_FIXED_AMOUNT_ENABLED_DISALLOW_PRE_PAYMENTS_DISABLED
        },
        {
         "tc6772FixedAmount", PM_FIXED_AMOUNT_AND_DISALLOW_PRE_PAYMENTS_ENABLED
        },
        {
         "tc6772OverPay", PM_FIXED_AMOUNT_AND_DISALLOW_PRE_PAYMENTS_ENABLED
        },
        {
         "tc6773FixedAmount", PM_FIXED_AMOUNT_DISABLED_DISALLOW_PRE_PAYMENTS_ENABLED
        },
        {
         "tc6773OverPay", PM_FIXED_AMOUNT_DISABLED_DISALLOW_PRE_PAYMENTS_ENABLED
        }
    };
  }

  @Test(dataProvider = "pmUiTestSetUp", retryAnalyzer = Retry.class)
  public void testPmUiPropertyCustomizations(String testCaseId, String info) {
    Logger.info(info);

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCaseId);
    testSetupPage.open();
    final String transId = testSetupPage.getString("transactionId");
    final String paymentFieldLabel = testSetupPage.getString("payFieldRent");

    PaymentFlow paymentFlow = openPaymentAmountPage(transId);

    PaymentAmountPage paymentAmountPage = new PaymentAmountPage();
    paymentAmountPage.setPaymentFieldAmount(paymentFieldLabel, "100.00");
    paymentAmountPage.clickContinueButton();
    Assert.assertEquals(
        paymentFlow.getCurrentStep(), PaymentFlow.STEP_METHOD,
        "Should advance to Payment Method page");
  }


  private void testPmCanEditAmount(String testCase) {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();
    final String transId = testSetupPage.getString("transactionId");
    final String paymentFieldLabel = testSetupPage.getString("payFieldRent");
    final String amount = testSetupPage.getString("amountRent");

    openPaymentAmountPage(transId);

    PaymentAmountPage paymentAmountPage = new PaymentAmountPage();

    String newAmountStr = setAmountInField(paymentAmountPage, paymentFieldLabel, amount);

    Assert.assertTrue(
        paymentAmountPage.getPaymentFieldAmount(paymentFieldLabel).equals(newAmountStr),
        "Field Amount should have been updated to new amount");
  }

  private PaymentFlow openPaymentAmountPage(String transId) {
    PaymentFlow paymentFlow = new PaymentFlow(
        PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_ONE_TIME, transId
    );
    paymentFlow.openStep(PaymentFlow.STEP_AMOUNT);

    return paymentFlow;
  }

  private String setAmountInField(
      PaymentAmountPage paymentAmountPage, String fieldLabel, String origAmount) {
    Float amountNum = Float.valueOf(origAmount);
    Float newAmount = amountNum + 100;
    String newAmountStr = String.format("%.2f", newAmount);

    paymentAmountPage.setPaymentFieldAmount(fieldLabel, newAmountStr);

    return newAmountStr;
  }
}
