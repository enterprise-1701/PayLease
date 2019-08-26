package com.paylease.app.qa.functional.tests.resident;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.features.PaymentFlow;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.paymentflow.PaymentAmountPage;
import com.paylease.app.qa.framework.components.CreditReporting;
import com.paylease.app.qa.framework.utility.listeners.Retry;
import java.security.KeyStore.TrustedCertificateEntry;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class PaymentAmountTest extends ScriptBase {

  public static final String AMOUNT_WAS_EDITABLE = "The amount was editable";
  private static final String REGION = "resident";
  private static final String FEATURE = "paymentAmount";
  private static final String LEARN_MORE_TEXT = "Build your credit history by opting in to Credit "
      + "Reporting. Learn The Benefits";
  private static final String ADVANCE_TO_PAYMENT_METHOD_PAGE = "Should advance to Payment Method page";
  private static final String FIXED_AMOUNT_AND_DISALLOW_PRE_PAYMENTS_DISABLED = "Resident is able "
      + "to edit and can overpay with property level customizations FIXED_AMOUNT and "
      + "DISALLOW_PRE_PAYMENTS disabled";
  private static final String FIXED_AMOUNT_ENABLED_DISALLOW_PRE_PAYMENTS_DISABLED = "Resident is "
      + "unable to edit and can overpay with property level customizations FIXED_AMOUNT enabled"
      + "and DISALLOW_PRE_PAYMENTS disabled";
  private static final String FIXED_AMOUNT_AND_DISALLOW_PRE_PAYMENTS_ENABLED = "Resident is "
      + "unable to edit and is unable to overpay with property level customizations FIXED_AMOUNT enabled"
      + "and DISALLOW_PRE_PAYMENTS disabled";
  private static final String FIXED_AMOUNT_DISABLED_DISALLOW_PRE_PAYMENTS_ENABLED = "Resident is "
      + "able to edit and is unable to overpay with property level customizations FIXED_AMOUNT enabled"
      + "and DISALLOW_PRE_PAYMENTS disabled";

  //------------------------------------Tests-------------------------------------------------------

  @Test
  public void enterValidAmount() {
    Logger.info("PM is able to enter a valid total amount and select the continue button");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();
    final String paymentFieldLabel = testSetupPage.getString("payFieldRent");

    PaymentFlow paymentFlow = openPaymentAmountPage(null);

    PaymentAmountPage paymentAmountPage = new PaymentAmountPage();
    paymentAmountPage.setPaymentFieldAmount(paymentFieldLabel, "100.00");
    paymentAmountPage.clickContinueButton();
    Assert.assertEquals(paymentFlow.getCurrentStep(), PaymentFlow.STEP_METHOD,
        "Should advance to Payment Method page");
  }

  @Test
  public void amountRequired() {
    Logger.info("Error message appears when amount is not entered");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();

    PaymentFlow paymentFlow = openPaymentAmountPage(null);

    PaymentAmountPage paymentAmountPage = new PaymentAmountPage();
    paymentAmountPage.clickContinueButton();
    Assert.assertEquals(
        paymentFlow.getCurrentStep(), PaymentFlow.STEP_AMOUNT,
        "Should stay on Payment Amount page");
    Assert.assertTrue(
        paymentAmountPage.getErrorMessage()
            .equals("Choose at least one payment type and enter an amount"),
        "Expect error message when amount not filled in"
    );
  }

  @Test
  public void residentCanEditLockedAmount() {
    Logger.info("To validate that resident is not able to edit locked payment amount");
    Assert.assertFalse(testResidentCanEditAmount("tc3"), AMOUNT_WAS_EDITABLE);
  }

  @Test
  public void residentCanEditPrePopulatedAmount() {
    Logger.info(
        "To validate that the resident can edit a pre-populated but un-locked payment amount");
    Assert.assertTrue(testResidentCanEditAmount("tc4"), "The amount is not editable");
  }

  @Test
  public void residentCanEditNonPrepopulatedAmount() {
    Logger.info("Resident is able to enter an amount into the non pre-populated payment field "
        + "when there exists another payment field with pre-populated amount and is able to proceed"
        + " to Payment Method Page");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc5");
    testSetupPage.open();

    final String lateFieldLabel = testSetupPage.getString("payFieldLate");

    PaymentFlow paymentFlow = openPaymentAmountPage(null);

    PaymentAmountPage paymentAmountPage = new PaymentAmountPage();

    String newAmountStr = setAmountInField(paymentAmountPage, lateFieldLabel, "3");
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

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6");
    testSetupPage.open();
    final String transId = testSetupPage.getString("transactionId");

    openPaymentAmountPage(transId);

    PaymentAmountPage paymentAmountPage = new PaymentAmountPage();
    Assert.assertTrue(
        paymentAmountPage.getFirstWarningMessage().equals("You have an existing AutoPay."),
        "Warning about existing autopay is not present");
  }

  @Test
  public void showBlockedPayments() {
    Logger.info("When PM has 'Stop Accepting Payments' set, there is an error message on Res UI");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc7");
    testSetupPage.open();
    final String transId = testSetupPage.getString("transactionId");

    openPaymentAmountPage(transId);

    PaymentAmountPage paymentAmountPage = new PaymentAmountPage();
    Assert.assertTrue(
        paymentAmountPage.hasBlockedPaymentsWarning(), "Warning about blocked payments is present");
  }

  @Test
  public void showAmountOwed() {
    Logger.info("To validate that the Amount Owed is displayed on the Payment amount page");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc8");
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
    Logger.info("'\"As of:\" timestamp is updated when a resident is locked for an amount on any of"
        + " the payment fields");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc9");
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

  @Test
  public void showRecentlyProcessedTransactionMessage() {
    Logger.info("To validate notification when resident had a recently processing transaction");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc12");
    testSetupPage.open();
    final String paymentFieldLabel = testSetupPage.getString("payFieldRent");

    PaymentFlow paymentFlow = openPaymentAmountPage(null);

    PaymentAmountPage paymentAmountPage = new PaymentAmountPage();

    Assert.assertTrue(
        paymentAmountPage.getFirstWarningMessage()
            .equals("A payment was recently scheduled."),
        "Warning about a recently processed transaction is not present");
  }

  @Test
  public void tc17() {
    Logger.info("Verify that the message to learn more and credit reporting container is present");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc17");
    testSetupPage.open();

    PaymentFlow paymentFlow = new PaymentFlow(
        PaymentFlow.UI_RES, PaymentFlow.SCHEDULE_ONE_TIME, null);
    paymentFlow.openStep(PaymentFlow.STEP_AMOUNT);

    CreditReporting creditReporting = new CreditReporting();

    Assert.assertEquals(creditReporting.getLearnMoreText(), LEARN_MORE_TEXT,
        "Learn More text should show");
  }

  @DataProvider(name = "resUiTestSetUp")
  public Object[][] resUiTestSetUp() {
    return new Object[][]{
        // testCaseId, loggerInfo, canEdit, overPayAssertion
        {
            "tc6744",
            FIXED_AMOUNT_AND_DISALLOW_PRE_PAYMENTS_DISABLED,
            true,
            true
        },
        {
            "tc6745NoEditFixed",
            FIXED_AMOUNT_ENABLED_DISALLOW_PRE_PAYMENTS_DISABLED,
            false,
            true
        },
        {
            "tc6745CanOverPay",
            FIXED_AMOUNT_ENABLED_DISALLOW_PRE_PAYMENTS_DISABLED,
            true,
            true
        },
        {
            "tc6746NoEditFixed",
            FIXED_AMOUNT_AND_DISALLOW_PRE_PAYMENTS_ENABLED,
            false,
            true
        },
        {
            "tc6746CanNotOverPay",
            FIXED_AMOUNT_AND_DISALLOW_PRE_PAYMENTS_ENABLED,
            false,
            false
        },
        {
            "tc6747CanEdit",
            FIXED_AMOUNT_DISABLED_DISALLOW_PRE_PAYMENTS_ENABLED,
            true,
            true
        },
        {
            "tc6747CanNotOverpay",
            FIXED_AMOUNT_DISABLED_DISALLOW_PRE_PAYMENTS_ENABLED,
            false,
            false
        }
    };
  }

  @Test(dataProvider = "resUiTestSetUp", retryAnalyzer = Retry.class)
  public void testResUiPropertyCustomizations(String testCaseId, String info, boolean canEdit,
      boolean overPayAssertion) {
    Logger.info(info);

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCaseId);
    testSetupPage.open();
    final String transId = testSetupPage.getString("transactionId");
    final String paymentFieldLabel = testSetupPage.getString("payFieldRent");
    final String amount = testSetupPage.getString("amountRent");

    PaymentFlow paymentFlow = openPaymentAmountPage(transId);
    PaymentAmountPage paymentAmountPage = new PaymentAmountPage();
    String newAmountStr = setAmountInField(paymentAmountPage, paymentFieldLabel, amount);

    paymentAmountPage.clickContinueButton();

    boolean isNewAmount = paymentAmountPage.getPaymentFieldAmount(paymentFieldLabel)
        .equals(newAmountStr);

    if (canEdit) {
      Assert.assertTrue(isNewAmount, AMOUNT_WAS_EDITABLE);
    } else {
      Assert.assertFalse(isNewAmount, AMOUNT_WAS_EDITABLE);
    }

    if (overPayAssertion) {
      Assert.assertEquals(
          paymentFlow.getCurrentStep(), PaymentFlow.STEP_METHOD,
          ADVANCE_TO_PAYMENT_METHOD_PAGE);
    } else {
      Assert.assertEquals(
          paymentFlow.getCurrentStep(), PaymentFlow.STEP_AMOUNT,
          ADVANCE_TO_PAYMENT_METHOD_PAGE);
    }

  }

  //------------------------------------Test Methods-------------------------------------------------------
  private boolean testResidentCanEditAmount(String testCase) {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();
    final String transId = testSetupPage.getString("transactionId");
    final String paymentFieldLabel = testSetupPage.getString("payFieldRent");
    final String amount = testSetupPage.getString("amountRent");

    openPaymentAmountPage(transId);

    PaymentAmountPage paymentAmountPage = new PaymentAmountPage();

    String newAmountStr = setAmountInField(paymentAmountPage, paymentFieldLabel, amount);

    return paymentAmountPage.getPaymentFieldAmount(paymentFieldLabel).equals(newAmountStr);
  }


  private PaymentFlow openPaymentAmountPage(String transId) {
    PaymentFlow paymentFlow = new PaymentFlow(
        PaymentFlow.UI_RES, PaymentFlow.SCHEDULE_ONE_TIME, transId
    );
    paymentFlow.openStep(PaymentFlow.STEP_AMOUNT);

    return paymentFlow;
  }

  public static String setAmountInField(
      PaymentAmountPage paymentAmountPage, String fieldLabel, String origAmount) {
    Float amountNum = Float.valueOf(origAmount);
    Float newAmount = amountNum + 100;
    String newAmountStr = String.format("%.2f", newAmount);

    paymentAmountPage.setPaymentFieldAmount(fieldLabel, newAmountStr);
    return newAmountStr;
  }

}
