package com.paylease.app.qa.testbase;

import static com.paylease.app.qa.framework.features.PaymentFlow.SCHEDULE_FIXED_AUTO;
import static com.paylease.app.qa.framework.features.PaymentFlow.SCHEDULE_ONE_TIME;
import static com.paylease.app.qa.framework.features.PaymentFlow.SCHEDULE_VARIABLE_AUTO;
import static com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage.NEW_BANK;
import static com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage.NEW_CREDIT;
import static com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage.NEW_DEBIT;
import static com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage.PAYPAL;

import com.paylease.app.qa.framework.pages.automatedhelper.ProcessTransactionPage;
import com.paylease.app.qa.framework.pages.paymentflow.BankAccountDetailsPage;
import com.paylease.app.qa.framework.pages.paymentflow.CardAccountDetailsPage;
import com.paylease.app.qa.framework.pages.paymentflow.PayPalCheckoutPage;
import com.paylease.app.qa.framework.pages.paymentflow.PaymentAmountPage;
import com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage;
import com.paylease.app.qa.framework.pages.paymentflow.ReceiptPage;
import com.paylease.app.qa.framework.pages.paymentflow.ReviewAndSubmitPage;
import com.paylease.app.qa.framework.pages.paymentflow.SelectResidentPage;
import com.paylease.app.qa.framework.pages.pm.PmLogoutBar;
import com.paylease.app.qa.framework.pages.pm.PmMenu;
import com.paylease.app.qa.framework.pages.pm.PmPaymentHistoryPage;
import com.paylease.app.qa.framework.pages.pm.PmResidentListPage;
import com.paylease.app.qa.framework.pages.resident.ResLoginPage;
import com.paylease.app.qa.framework.pages.resident.ResidentLogoutBar;
import com.paylease.app.qa.framework.pages.resident.ResidentMenuItems;
import java.util.ArrayList;
import java.util.Arrays;
import org.testng.Assert;

public class PaymentBase {

  private static final String LEASE_PAYMENT_FIELD = "Lease Payment";
  private static final String SECURITY_PAYMENT_FIELD = "Security Deposit";
  private static final String APT_HOLD_PAYMENT_FIELD = "Apt Hold Fee";
  private static final String APPLICATION_PAYMENT_FIELD = "Application Fee";

  /**
   * Fill and submit payment amount into specified payment fields on payment amount page.
   *
   * @param paymentFields Array of payment fields
   */
  public void fillAndSubmitPaymentAmount(boolean residentHomePage,
      String[] paymentFields) {
    ArrayList<String> paymentFieldsList = new ArrayList<>(Arrays.asList(paymentFields));
    fillAndSubmitPaymentAmount(residentHomePage, paymentFieldsList);
  }

  /**
   * Fill and submit payment amount into specified payment fields on payment amount page.
   *
   * @param paymentFields Array of payment fields
   */
  public void fillAndSubmitPaymentAmount(boolean residentHomePage,
      ArrayList<String> paymentFields) {
    PaymentAmountPage paymentAmountPage = new PaymentAmountPage();

    if (residentHomePage) {
      paymentAmountPage.fillAndSubmitResHomePage(paymentFields);
    } else {
      paymentAmountPage.fillAndSubmit(paymentFields);
    }
  }

  /**
   * Fill and submit payment amount.
   */
  public void fillAndSubmitPaymentAmount(boolean residentHomePage) {
    PaymentAmountPage paymentAmountPage = new PaymentAmountPage();

    if (residentHomePage) {
      paymentAmountPage.fillAndSubmitResHomePage();
    } else {
      paymentAmountPage.fillAndSubmit();
    }
  }

  /**
   * Select payment method.
   *
   * @param paymentType type of payment to select.
   * @param expressPay select express pay if true.
   */
  public void selectPaymentMethod(String paymentType, boolean expressPay) {
    PaymentMethodPage paymentMethodPage = new PaymentMethodPage();
    if (paymentType.equals(NEW_DEBIT)) {
      paymentMethodPage.selectPaymentMethod(NEW_CREDIT);
    } else {
      paymentMethodPage.selectPaymentMethod(paymentType);
    }

    if (expressPay && (paymentType.equals(NEW_DEBIT) || paymentType.equals(NEW_CREDIT))) {
      paymentMethodPage.setExpressPayCheckbox(NEW_CREDIT);
    }

    if (expressPay && (paymentType.equals(NEW_BANK))) {
      paymentMethodPage.setExpressPayCheckbox(NEW_BANK);
    }

    if (paymentType.equals(PAYPAL)) {
      paymentMethodPage.clickContinueButtonNoWait();
    } else {
      paymentMethodPage.clickContinueButton();
    }

    CardAccountDetailsPage cardAccountDetailsPage = new CardAccountDetailsPage();

    switch (paymentType) {
      case NEW_BANK:
        BankAccountDetailsPage bankAccountDetailsPage = new BankAccountDetailsPage();

        bankAccountDetailsPage.fillBankDetailsAndSubmit();
        break;

      case NEW_CREDIT:
        cardAccountDetailsPage.prepCardType(CardAccountDetailsPage.CARD_TYPE_CREDIT_VALID);
        cardAccountDetailsPage.fillAndSubmitCardDetails();
        break;

      case NEW_DEBIT:
        cardAccountDetailsPage.prepCardType(CardAccountDetailsPage.CARD_TYPE_DEBIT_VALID);
        cardAccountDetailsPage.fillAndSubmitCardDetails();
        break;

      case PAYPAL:
        PayPalCheckoutPage payPalCheckoutPage = new PayPalCheckoutPage();

        payPalCheckoutPage.fillAndSubmitPayPalPaymentDetails();
        break;

      default:
        throw new IllegalArgumentException("Payment Type" + paymentType + "Unknown");
    }
  }

  /**
   * Click submit button on Review and Submit page.
   */
  public void reviewAndSubmit() {
    ReviewAndSubmitPage reviewAndSubmitPage = new ReviewAndSubmitPage();

    reviewAndSubmitPage.clickSubmitButton();
  }

  /**
   * Select resident from payments dropdown begin payment process.
   *
   * @param residentId residentId
   * @param schedule type of payment schedule
   */
  public void selectResidentFromPaymentsDropDownAndBeginPayment(String residentId,
      String schedule) {
    PmMenu pmMenu = new PmMenu();

    switch (schedule) {
      case SCHEDULE_ONE_TIME:
        pmMenu.clickOneTimePaymentTab();
        break;

      case SCHEDULE_FIXED_AUTO:
        pmMenu.clickCreateFixedAutoPayTab();
        break;

      case SCHEDULE_VARIABLE_AUTO:
        pmMenu.clickCreateVariableAutoPayTab();
        break;

      default:
        throw new IllegalArgumentException("Schedule " + schedule + " unknown");
    }

    SelectResidentPage selectResidentPage = new SelectResidentPage();

    selectResidentPage.clickToBeginPayment(residentId);
  }

  /**
   * Select resident from resident list and begin payment process.
   *
   * @param residentId residentId
   * @param schedule type of payment schedule
   */
  public void selectResidentFromResidentListAndBeginPayment(String residentId, String schedule) {
    PmResidentListPage pmResidentListPage = new PmResidentListPage();

    pmResidentListPage.open();
    pmResidentListPage.clickMakePayment(residentId, true);

    PmPaymentHistoryPage pmPaymentHistoryPage = new PmPaymentHistoryPage();

    switch (schedule) {
      case SCHEDULE_ONE_TIME:
        pmPaymentHistoryPage.clickOnetimePaymentButton();
        break;

      case SCHEDULE_FIXED_AUTO:
        pmPaymentHistoryPage.clickCreateFixedAutoPayButton();
        break;

      case SCHEDULE_VARIABLE_AUTO:
        pmPaymentHistoryPage.clickCreateVariableAutoPayButton();
        break;

      default:
        throw new IllegalArgumentException("Schedule " + schedule + " unknown");
    }
  }

  /**
   * Get payment fields based on the type of test.
   *
   * @param testCase type of test (which payment fields to have)
   * @return Payment fields as an array
   */
  public String[] getPaymentFields(String testCase) {

    switch (testCase) {
      case "fullFlowOnePaymentOneDeposit":
        return new String[]{LEASE_PAYMENT_FIELD, SECURITY_PAYMENT_FIELD};

      case "fullFlowTwoPaymentTwoDeposit":
        return new String[]{LEASE_PAYMENT_FIELD, APPLICATION_PAYMENT_FIELD, SECURITY_PAYMENT_FIELD,
            APT_HOLD_PAYMENT_FIELD};

      case "fullFlowTwoPaymentOneDeposit":
        return new String[]{LEASE_PAYMENT_FIELD, APPLICATION_PAYMENT_FIELD, SECURITY_PAYMENT_FIELD};

      case "fullFlowOnePaymentTwoDeposit":
        return new String[]{LEASE_PAYMENT_FIELD, SECURITY_PAYMENT_FIELD, APT_HOLD_PAYMENT_FIELD};

      case "fullFlowOnePayment":
        return new String[]{LEASE_PAYMENT_FIELD};

      case "fullFlowOneDeposit":
        return new String[]{SECURITY_PAYMENT_FIELD};

      case "fullFlowTwoPayment":
        return new String[]{LEASE_PAYMENT_FIELD, APPLICATION_PAYMENT_FIELD};

      case "fullFlowTwoDeposit":
        return new String[]{SECURITY_PAYMENT_FIELD, APT_HOLD_PAYMENT_FIELD};

      default:
        return new String[]{};
    }
  }

  /**
   * Process transaction.
   *
   * @param transId transaction ID.
   */
  public void processTransaction(String transId) {
    ProcessTransactionPage processTransactionPage = new ProcessTransactionPage();
    processTransactionPage.open();
    processTransactionPage.processTransaction(transId);
  }

  /**
   * Perform PM otp actions.
   *
   * @param residentId resident Id
   * @param paymentType payment type
   * @param useResidentList boolean use resident list or not
   * @param expressPay express pay
   */
  public String pmOtPaymentActions(String paymentType, boolean useResidentList, boolean expressPay,
      String residentId, ArrayList<String> paymentFieldList) {

    PaymentBase paymentBase = new PaymentBase();

    if (useResidentList) {
      paymentBase.selectResidentFromResidentListAndBeginPayment(residentId, SCHEDULE_ONE_TIME);
    } else {
      paymentBase.selectResidentFromPaymentsDropDownAndBeginPayment(residentId, SCHEDULE_ONE_TIME);
    }

    paymentBase.fillAndSubmitPaymentAmount(false, paymentFieldList);
    paymentBase.selectPaymentMethod(paymentType, expressPay);
    paymentBase.reviewAndSubmit();

    ReceiptPage receiptPage = new ReceiptPage();

    String transId = receiptPage.getTransactionId();

    Assert.assertTrue(receiptPage.pageIsLoaded(), "Should be on Receipt page");

    Assert.assertEquals((receiptPage.getTransactionStatus()), "Processing",
        "The transaction did not process successfully");

    PmLogoutBar pmLogoutBar = new PmLogoutBar();

    pmLogoutBar.clickLogoutButton();

    return transId;
  }

  /**
   * Perform Resident otp actions.
   *
   * @param paymentType payment type
   */
  public String residentOtPaymentActions(String residentEmail, String paymentType,
      ArrayList<String> paymentFieldList) {
    if (null != residentEmail) {
      ResLoginPage resLoginPage = new ResLoginPage();

      resLoginPage.open();
      resLoginPage.login(residentEmail, null);
    }

    PaymentBase paymentBase = new PaymentBase();

    ResidentMenuItems residentMenuItems = new ResidentMenuItems();

    residentMenuItems.clickMakePaymentTab();

    paymentBase.fillAndSubmitPaymentAmount(false, paymentFieldList);

    paymentBase.selectPaymentMethod(paymentType, false);
    paymentBase.reviewAndSubmit();

    ReceiptPage receiptPage = new ReceiptPage();

    Assert.assertTrue(receiptPage.pageIsLoaded(), "Should be on Receipt page");

    Assert.assertEquals((receiptPage.getTransactionStatus()), "Processing",
        "The transaction did not process successfully");

    String transId = receiptPage.getTransactionId();

    ResidentLogoutBar residentLogoutBar = new ResidentLogoutBar();

    residentLogoutBar.clickLogoutButton();
    return transId;
  }
}
