package com.paylease.app.qa.testbase.dataproviders;

import static com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage.NEW_CREDIT;
import static com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage.NEW_DEBIT;
import static com.paylease.app.qa.functional.tests.integration.ResmanSplitTransactionTest.DEPOSIT_TRANS;
import static com.paylease.app.qa.functional.tests.integration.ResmanSplitTransactionTest.PAYMENT_TRANS;
import static com.paylease.app.qa.functional.tests.integration.ResmanSplitTransactionTest.RETURN_DEPOSIT_TRANS;
import static com.paylease.app.qa.functional.tests.integration.ResmanSplitTransactionTest.RETURN_PAYMENT_TRANS;

import org.testng.annotations.DataProvider;

public class ResmanSplitChargebackDataProvider {

  /**
   * Data Provider for Resman Split Chargebacks.
   * @return parameters for Resman Split Chargebacks
   */
  @DataProvider(name = "resmanSplitChargeback", parallel = true)
  public static Object[][] fullFlowChargeback() {
    return new Object[][]{
        {"tc01", "Chargeback Split 1 pay 1 dep PM UI Otp Credit", "fullFlowOnePaymentOneDeposit",
            NEW_CREDIT, "pm",
            new String[]{PAYMENT_TRANS, RETURN_PAYMENT_TRANS, DEPOSIT_TRANS, RETURN_DEPOSIT_TRANS}},
        {"tc02", "Chargeback Split 2 pay 2 dep PM UI Otp Credit", "fullFlowTwoPaymentTwoDeposit",
            NEW_CREDIT, "pm",
            new String[]{PAYMENT_TRANS, RETURN_PAYMENT_TRANS, DEPOSIT_TRANS, RETURN_DEPOSIT_TRANS}},
        {"tc03", "Chargeback Split 2 pay 1 dep PM UI Otp Credit", "fullFlowTwoPaymentOneDeposit",
            NEW_CREDIT, "pm",
            new String[]{PAYMENT_TRANS, RETURN_PAYMENT_TRANS, DEPOSIT_TRANS, RETURN_DEPOSIT_TRANS}},
        {"tc04", "Chargeback Split 1 pay 2 dep PM UI Otp Credit", "fullFlowOnePaymentTwoDeposit",
            NEW_CREDIT, "pm",
            new String[]{PAYMENT_TRANS, RETURN_PAYMENT_TRANS, DEPOSIT_TRANS, RETURN_DEPOSIT_TRANS}},
        {"tc05", "Chargeback Split 1 pay 0 dep PM UI Otp Credit", "fullFlowOnePayment", NEW_CREDIT,
            "pm", new String[]{PAYMENT_TRANS, RETURN_PAYMENT_TRANS}},
        {"tc06", "Chargeback Split 0 pay 1 dep PM UI Otp Credit", "fullFlowOneDeposit", NEW_CREDIT,
            "pm", new String[]{DEPOSIT_TRANS, RETURN_DEPOSIT_TRANS}},
        {"tc07", "Chargeback Split 2 pay 0 dep PM UI Otp Credit", "fullFlowTwoPayment", NEW_CREDIT,
            "pm", new String[]{PAYMENT_TRANS, RETURN_PAYMENT_TRANS}},
        {"tc08", "Chargeback Split 0 pay 2 dep PM UI Otp Credit", "fullFlowTwoDeposit", NEW_CREDIT,
            "pm", new String[]{DEPOSIT_TRANS, RETURN_DEPOSIT_TRANS}},
        {"tc09", "Chargeback Split 1 pay 1 dep PM UI Otp Debit", "fullFlowOnePaymentOneDeposit",
            NEW_DEBIT, "pm",
            new String[]{PAYMENT_TRANS, RETURN_PAYMENT_TRANS, DEPOSIT_TRANS, RETURN_DEPOSIT_TRANS}},
        {"tc10", "Chargeback Split 2 pay 2 dep PM UI Otp Debit", "fullFlowTwoPaymentTwoDeposit",
            NEW_DEBIT, "pm",
            new String[]{PAYMENT_TRANS, RETURN_PAYMENT_TRANS, DEPOSIT_TRANS, RETURN_DEPOSIT_TRANS}},
        {"tc11", "Chargeback Split 2 pay 1 dep PM UI Otp Debit", "fullFlowTwoPaymentOneDeposit",
            NEW_DEBIT, "pm",
            new String[]{PAYMENT_TRANS, RETURN_PAYMENT_TRANS, DEPOSIT_TRANS, RETURN_DEPOSIT_TRANS}},
        {"tc12", "Chargeback Split 1 pay 2 dep PM UI Otp Debit", "fullFlowOnePaymentTwoDeposit",
            NEW_DEBIT, "pm",
            new String[]{PAYMENT_TRANS, RETURN_PAYMENT_TRANS, DEPOSIT_TRANS, RETURN_DEPOSIT_TRANS}},
        {"tc13", "Chargeback Split 1 pay 0 dep PM UI Otp Debit", "fullFlowOnePayment", NEW_DEBIT,
            "pm", new String[]{PAYMENT_TRANS, RETURN_PAYMENT_TRANS}},
        {"tc14", "Chargeback Split 0 pay 1 dep PM UI Otp Debit", "fullFlowOneDeposit", NEW_DEBIT,
            "pm", new String[]{DEPOSIT_TRANS, RETURN_DEPOSIT_TRANS}},
        {"tc15", "Chargeback Split 2 pay 0 dep PM UI Otp Debit", "fullFlowTwoPayment", NEW_DEBIT,
            "pm", new String[]{PAYMENT_TRANS, RETURN_PAYMENT_TRANS}},
        {"tc16", "Chargeback Split 0 pay 2 dep PM UI Otp Debit", "fullFlowTwoDeposit", NEW_DEBIT,
            "pm", new String[]{DEPOSIT_TRANS, RETURN_DEPOSIT_TRANS}},
        {"tc17", "Chargeback Split 1 pay 1 dep Res UI Otp Credit", "fullFlowOnePaymentOneDeposit",
            NEW_CREDIT, "res",
            new String[]{PAYMENT_TRANS, RETURN_PAYMENT_TRANS, DEPOSIT_TRANS, RETURN_DEPOSIT_TRANS}},
        {"tc18", "Chargeback Split 2 pay 2 dep Res UI Otp Credit", "fullFlowTwoPaymentTwoDeposit",
            NEW_CREDIT, "res",
            new String[]{PAYMENT_TRANS, RETURN_PAYMENT_TRANS, DEPOSIT_TRANS, RETURN_DEPOSIT_TRANS}},
        {"tc19", "Chargeback Split 2 pay 1 dep Res UI Otp Credit", "fullFlowTwoPaymentOneDeposit",
            NEW_CREDIT, "res",
            new String[]{PAYMENT_TRANS, RETURN_PAYMENT_TRANS, DEPOSIT_TRANS, RETURN_DEPOSIT_TRANS}},
        {"tc20", "Chargeback Split 1 pay 2 dep Res UI Otp Credit", "fullFlowOnePaymentTwoDeposit",
            NEW_CREDIT, "res",
            new String[]{PAYMENT_TRANS, RETURN_PAYMENT_TRANS, DEPOSIT_TRANS, RETURN_DEPOSIT_TRANS}},
        {"tc21", "Chargeback Split 1 pay 0 dep Res UI Otp Credit", "fullFlowOnePayment", NEW_CREDIT,
            "res", new String[]{PAYMENT_TRANS, RETURN_PAYMENT_TRANS}},
        {"tc22", "Chargeback Split 0 pay 1 dep Res UI Otp Credit", "fullFlowOneDeposit", NEW_CREDIT,
            "res", new String[]{DEPOSIT_TRANS, RETURN_DEPOSIT_TRANS}},
        {"tc23", "Chargeback Split 2 pay 0 dep Res UI Otp Credit", "fullFlowTwoPayment", NEW_CREDIT,
            "res", new String[]{PAYMENT_TRANS, RETURN_PAYMENT_TRANS}},
        {"tc24", "Chargeback Split 0 pay 2 dep Res UI Otp Credit", "fullFlowTwoDeposit", NEW_CREDIT,
            "res", new String[]{DEPOSIT_TRANS, RETURN_DEPOSIT_TRANS}},
        {"tc25", "Chargeback Split 1 pay 1 dep Res UI Otp Debit", "fullFlowOnePaymentOneDeposit",
            NEW_DEBIT, "res",
            new String[]{PAYMENT_TRANS, RETURN_PAYMENT_TRANS, DEPOSIT_TRANS, RETURN_DEPOSIT_TRANS}},
        {"tc26", "Chargeback Split 2 pay 2 dep Res UI Otp Debit", "fullFlowTwoPaymentTwoDeposit",
            NEW_DEBIT, "res",
            new String[]{PAYMENT_TRANS, RETURN_PAYMENT_TRANS, DEPOSIT_TRANS, RETURN_DEPOSIT_TRANS}},
        {"tc27", "Chargeback Split 2 pay 1 dep Res UI Otp Debit", "fullFlowTwoPaymentOneDeposit",
            NEW_DEBIT, "res",
            new String[]{PAYMENT_TRANS, RETURN_PAYMENT_TRANS, DEPOSIT_TRANS, RETURN_DEPOSIT_TRANS}},
        {"tc28", "Chargeback Split 1 pay 2 dep Res UI Otp Debit", "fullFlowOnePaymentTwoDeposit",
            NEW_DEBIT, "res",
            new String[]{PAYMENT_TRANS, RETURN_PAYMENT_TRANS, DEPOSIT_TRANS, RETURN_DEPOSIT_TRANS}},
        {"tc29", "Chargeback Split 1 pay 0 dep Res UI Otp Debit", "fullFlowOnePayment", NEW_DEBIT,
            "res", new String[]{PAYMENT_TRANS, RETURN_PAYMENT_TRANS}},
        {"tc30", "Chargeback Split 0 pay 1 dep Res UI Otp Debit", "fullFlowOneDeposit", NEW_DEBIT,
            "res", new String[]{DEPOSIT_TRANS, RETURN_DEPOSIT_TRANS}},
        {"tc31", "Chargeback Split 2 pay 0 dep Res UI Otp Debit", "fullFlowTwoPayment", NEW_DEBIT,
            "res", new String[]{PAYMENT_TRANS, RETURN_PAYMENT_TRANS}},
        {"tc32", "Chargeback Split 0 pay 2 dep Res UI Otp Debit", "fullFlowTwoDeposit", NEW_DEBIT,
            "res", new String[]{DEPOSIT_TRANS, RETURN_DEPOSIT_TRANS}}
    };
  }
}
