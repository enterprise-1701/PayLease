package com.paylease.app.qa.e2e.tests;

import static com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage.NEW_CREDIT;

import com.paylease.app.qa.framework.DataBaseConnector;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.features.PaymentFlow;
import com.paylease.app.qa.framework.pages.admin.LogAs;
import com.paylease.app.qa.framework.pages.admin.LoginPageAdmin;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.paymentflow.CardAccountDetailsPage;
import com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage;
import com.paylease.app.qa.framework.pages.paymentflow.ReceiptPage;
import com.paylease.app.qa.framework.pages.paymentflow.ReviewAndSubmitPage;
import com.paylease.app.qa.framework.pages.resident.ResLoginPage;
import com.paylease.app.qa.framework.utility.database.client.dao.AutopayTemplateDao;
import com.paylease.app.qa.framework.utility.database.client.dao.TransactionDao;
import com.paylease.app.qa.framework.utility.database.client.dto.AutopayTemplate;
import com.paylease.app.qa.framework.utility.database.client.dto.Transaction;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.List;
import org.testng.Assert;

public abstract class TieredFeeStructureBase extends ScriptBase {

  protected abstract PaymentFlow beginPayment(
      String paymentType,
      String residentId,
      String paymentFieldLabel,
      String formattedAmount
  );

  /**
   * Handle testing all the fees.
   *
   * @param region region
   * @param feature feature
   * @param testCaseId the test case id
   * @param paymentMethod the payment method
   * @param includeBaseFeeForResident use true to include base fee to resident
   * @param includePhoneFee use true to include phone fee
   * @param logAsFromAdmin use true to login from admin, false to login to UI directly
   * @param paymentType the payment type
   * @param baseFeeRounded use true to round the base fee
   * @param includeAch expect the ACH fee to be added to the base fee
   * @param choosePmIncur PM will choose to incur resident fees for given payment
   * @param useResidentToLogin use true to login as resident, false to login as pm
   */
  protected void handlesFees(
      String region,
      String feature,
      String testCaseId,
      String paymentMethod,
      boolean includeBaseFeeForResident,
      boolean includePhoneFee,
      boolean logAsFromAdmin,
      String paymentType,
      boolean baseFeeRounded,
      boolean useExpressPay,
      boolean includeAch,
      boolean choosePmIncur,
      boolean useResidentToLogin
  ) {
    Logger.info("Check fees for " + paymentType + ": "
        + testCaseId + " - " + paymentMethod
        + " - Log as from admin: " + (logAsFromAdmin ? "YES" : "NO")
        + " - includeBaseFeeForResident: " + (includeBaseFeeForResident ? "YES" : "NO")
        + " - includePhoneFee: " + (includePhoneFee ? "YES" : "NO")
        + " - baseFeeRounded: " + (baseFeeRounded ? "YES" : "NO")
        + " - useResidentToLogin: " + (useResidentToLogin ? "YES" : "NO")
        + " - useExpressPay: " + (useExpressPay ? "YES" : "NO")
        + " - includeAch: " + (includeAch ? "YES" : "NO")
        + " - choosePmIncur: " + (choosePmIncur ? "YES" : "NO")
    );

    if (choosePmIncur && paymentType.equals(PaymentFlow.SCHEDULE_VARIABLE_AUTO)) {
      Logger.info("Skipping VAP with pm incurs test due to bug DEV-42708");

      return;
    }

    TestSetupPage testSetupPage = new TestSetupPage(region, feature, testCaseId);
    testSetupPage.open();

    final String pmId = testSetupPage.getString("pmId");
    final String pmEmail = testSetupPage.getString("pmEmail");
    final String residentId = testSetupPage.getString("residentId");
    final String residentEmail = testSetupPage.getString("residentEmail");
    final String baseFee = testSetupPage.getString("baseFee");
    final boolean feeTypeFixed = testSetupPage.getFlag("feeTypeFixed");
    final String achFee = testSetupPage.getString("achFee");
    final String phoneFee = testSetupPage.getString("phoneFee");
    final boolean expressPayVisible = testSetupPage.getFlag("expressPayVisible");
    final String expressPayFee = testSetupPage.getString("expressPayFee");
    final String paymentFieldLabel = testSetupPage.getString("paymentFieldLabel");
    final boolean pmIncurOption = testSetupPage.getFlag("pmIncurOption");

    // The condition on the payment type can be removed with the resolution of DEV-42677
    final boolean expectExpressPayUsed = useExpressPay
        && !paymentType.equals(PaymentFlow.SCHEDULE_VARIABLE_AUTO);
    final boolean expectPmIncurOptionPresent =
        pmIncurOption && !logAsFromAdmin && !useResidentToLogin && includeBaseFeeForResident;

    int paymentAmount = 9900;
    DecimalFormat amountFormat = new DecimalFormat("0.00");
    String formattedAmount = amountFormat.format(paymentAmount / 100);

    int baseFeeInt = Integer.parseInt(baseFee);
    if (!feeTypeFixed) {
      baseFeeInt = (int) Math.round((paymentAmount * baseFeeInt) / 10000.0);
    }

    int achFeeInt = Integer.parseInt(achFee);
    if (includeAch) {
      baseFeeInt += achFeeInt;
    }

    if (baseFeeRounded && baseFeeInt > 0) {
      int baseFeeIntRounded = ((baseFeeInt + 99) / 100) * 100; // round to next highest hundred
      baseFeeIntRounded -= 5;
      if (baseFeeIntRounded < baseFeeInt) {
        baseFeeIntRounded += 100; // up to next 95
      }
      baseFeeInt = baseFeeIntRounded;
    }

    int pmBaseFeeInt = baseFeeInt;
    if (includeBaseFeeForResident) {
      pmBaseFeeInt = 0;
    } else {
      baseFeeInt = 0;
    }

    int phoneFeeInt = includePhoneFee ? Integer.parseInt(cleanFeeAmount(phoneFee)) : 0;
    int expressPayFeeInt = expectExpressPayUsed
        ? Integer.parseInt(cleanFeeAmount(expressPayFee)) : 0;

    DecimalFormat moneyFormat = new DecimalFormat("000");

    final String expectedResidentFeeInitial = moneyFormat.format(
        Float.valueOf(baseFeeInt + phoneFeeInt)
    );
    String expectedResidentFeeFinal = moneyFormat.format(
        Float.valueOf(baseFeeInt + phoneFeeInt + expressPayFeeInt)
    );
    String expectedPmFee = moneyFormat.format(Float.valueOf(pmBaseFeeInt));

    if (choosePmIncur) {
      expectedPmFee = expectedResidentFeeFinal;
      expectedResidentFeeFinal = "000";
    }

    String userId = residentId;
    String userEmail = residentEmail;
    if (!useResidentToLogin) {
      userId = pmId;
      userEmail = pmEmail;
    }
    loginUser(logAsFromAdmin, userId, userEmail);

    PaymentFlow paymentFlow =
        beginPayment(paymentType, residentId, paymentFieldLabel, formattedAmount);

    paymentFlow.advanceToStep(PaymentFlow.STEP_METHOD);

    PaymentMethodPage paymentMethodPage = new PaymentMethodPage();
    String feeAmt = cleanFeeAmount(paymentMethodPage.getFeeAmount(paymentMethod));

    Assert.assertEquals(feeAmt, expectedResidentFeeInitial,
        "Verify fee amount on Payment Method page for " + paymentMethod + " (baseFeeInt="
            + baseFeeInt + ", phoneFeeInt=" + phoneFeeInt + ", achFeeInt=" + achFeeInt + ")");

    Assert.assertEquals(paymentMethodPage.isExpressPayPresent(paymentMethod), expressPayVisible,
        "Express pay visibility should match test case setup");

    if (expressPayVisible) {
      String expressPayAmt = cleanFeeAmount(paymentMethodPage.getExpressPayText(paymentMethod));

      Assert.assertEquals(expressPayAmt, cleanFeeAmount(expressPayFee),
          "Express pay fee should match pm configuration");
    }

    if (useExpressPay) {
      paymentMethodPage.setExpressPayCheckbox(paymentMethod);
    }

    // check if PM incur option is present
    Assert.assertEquals(paymentMethodPage.isPmIncurOptionPresent(paymentMethod),
        expectPmIncurOptionPresent, "PM's option to incur should match setup");

    // check the PM incur checkbox
    if (choosePmIncur) {
      paymentMethodPage.setPmIncursCheckbox(paymentMethod);
    }

    paymentFlow.setPaymentMethod(paymentMethod);

    paymentFlow.setCardType(
        paymentMethod.equals(NEW_CREDIT) ? CardAccountDetailsPage.CARD_TYPE_CREDIT_VALID
            : CardAccountDetailsPage.CARD_TYPE_DEBIT_VALID);

    paymentFlow.advanceToStep(PaymentFlow.STEP_REVIEW);

    ReviewAndSubmitPage reviewAndSubmitPage = new ReviewAndSubmitPage();
    feeAmt = cleanFeeAmount(reviewAndSubmitPage.getFeeAmount());

    Assert.assertEquals(feeAmt, expectedResidentFeeFinal,
        "Verify fee amount on Review and Submit page (baseFeeInt=" + baseFeeInt + ", phoneFeeInt="
            + phoneFeeInt + ", expressPayFeeInt=" + expressPayFeeInt + ")");

    paymentFlow.advanceToStep(PaymentFlow.STEP_COMPLETE);

    ReceiptPage receiptPage = new ReceiptPage();
    if (receiptPage.pageIsLoaded()) {
      feeAmt = cleanFeeAmount(receiptPage.getProcessingFeeAmount());

      Assert.assertEquals(feeAmt, expectedResidentFeeFinal,
          "Verify fee amount on Receipt page (baseFeeInt=" + baseFeeInt + ", phoneFeeInt="
              + phoneFeeInt + ", expressPayFeeInt=" + expressPayFeeInt + ")");
    }

    String transId = paymentFlow.getPaymentId();

    DataBaseConnector dataBaseConnector = new DataBaseConnector();
    dataBaseConnector.createConnection();
    Connection connection = dataBaseConnector.getConnection();

    String actualPayleaseFee;
    String actualPmFeeAmount;
    boolean actualExpressPay;

    if (paymentType.equals(PaymentFlow.SCHEDULE_VARIABLE_AUTO)) {
      AutopayTemplateDao autopayTemplateDao = new AutopayTemplateDao();
      List<AutopayTemplate> autopayTemplates = autopayTemplateDao
          .findById(connection, Integer.parseInt(transId), 1);

      Assert.assertFalse(autopayTemplates.isEmpty(),
          "Autopay Template should be in the database (baseFeeInt=" + baseFeeInt + ", phoneFeeInt="
              + phoneFeeInt + ")");

      AutopayTemplate autopayTemplate = autopayTemplates.get(0);

      actualPayleaseFee = autopayTemplate.getFeeFixed();
      actualPmFeeAmount = autopayTemplate.getPmFeeFixed();
      actualExpressPay = autopayTemplate.isExpressPay();
    } else {
      TransactionDao transactionDao = new TransactionDao();
      List<Transaction> transactions = transactionDao
          .findById(connection, Integer.parseInt(transId), 1);

      Assert.assertFalse(transactions.isEmpty(), "Transaction should be in the database");

      Transaction transaction = transactions.get(0);

      actualPayleaseFee = transaction.getPayleaseFee();
      actualPmFeeAmount = transaction.getPmFeeAmount();
      actualExpressPay = transaction.isExpressPay();
    }

    Assert.assertEquals(cleanFeeAmount(actualPayleaseFee), expectedResidentFeeFinal,
        "Database should show same paylease fee as resident sees");

    Assert.assertEquals(cleanFeeAmount(actualPmFeeAmount), expectedPmFee,
        "Database should have pm fee set to base fee when pm incurs fee");

    Assert.assertEquals(actualExpressPay, expectExpressPayUsed,
        "Database should show same express pay setting as chosen during payment flow");
  }

  /**
   * Get only the digits in the fee amount. If we're left with nothing, return "000".
   *
   * @param feeAmount fee amount to clean
   * @return only the digits from the string
   */
  protected String cleanFeeAmount(String feeAmount) {
    feeAmount = feeAmount.replaceAll("[^\\d]", "");

    return feeAmount.isEmpty() ? "000" : feeAmount;
  }

  /**
   * Login.
   *
   * @param logAsFromAdmin use true to login to admin
   * @param userId user id
   * @param userEmail user email
   */
  protected void loginUser(boolean logAsFromAdmin, String userId, String userEmail) {
    if (logAsFromAdmin) {
      LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
      loginPageAdmin.login();

      LogAs logAs = new LogAs(userId);
      logAs.open();
    } else {
      ResLoginPage loginPage = new ResLoginPage();
      loginPage.open();
      loginPage.login(userEmail);
    }
  }
}
