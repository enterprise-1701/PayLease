package com.paylease.app.qa.testbase.dataproviders;

import static com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage.NEW_BANK;
import static com.paylease.app.qa.functional.tests.integration.ResmanSplitTransactionTest.DEPOSIT_TRANS;
import static com.paylease.app.qa.functional.tests.integration.ResmanSplitTransactionTest.PAYMENT_TRANS;
import static com.paylease.app.qa.functional.tests.integration.ResmanSplitTransactionTest.RETURN_DEPOSIT_TRANS;
import static com.paylease.app.qa.functional.tests.integration.ResmanSplitTransactionTest.RETURN_PAYMENT_TRANS;

import org.testng.annotations.DataProvider;

public class ResmanSplitReturnDataProvider {

  /**
   * Data provider for Resman Split Returns.
   * @return Object with parameters for Resman Split Returns(Partial and full)
   */
  @DataProvider(name = "resmanSplitReturn", parallel = true)
  public static Object[][] fullFlowReturn() {
    return new Object[][]{
        //----------------------------------------Return--------------------------------------------
        /* Only Bank(ACH) transactions can be returned */

        {"tc01", "Return Split 1 pay 1 dep OTP from Res UI", "fullFlowOnePaymentOneDeposit",
            NEW_BANK, "res", false,
            new String[]{PAYMENT_TRANS, RETURN_PAYMENT_TRANS, DEPOSIT_TRANS, RETURN_DEPOSIT_TRANS}},
        {"tc02", "Return Split 2 pay 2 dep OTP from Res UI", "fullFlowTwoPaymentTwoDeposit",
            NEW_BANK, "res", false,
            new String[]{PAYMENT_TRANS, RETURN_PAYMENT_TRANS, DEPOSIT_TRANS, RETURN_DEPOSIT_TRANS}},
        {"tc03", "Return Split 2 pay 1 dep OTP from Res UI", "fullFlowTwoPaymentOneDeposit",
            NEW_BANK, "res", false,
            new String[]{PAYMENT_TRANS, RETURN_PAYMENT_TRANS, DEPOSIT_TRANS, RETURN_DEPOSIT_TRANS}},
        {"tc04", "Return Split 1 pay 2 dep OTP from Res UI", "fullFlowOnePaymentTwoDeposit",
            NEW_BANK, "res", false,
            new String[]{PAYMENT_TRANS, RETURN_PAYMENT_TRANS, DEPOSIT_TRANS, RETURN_DEPOSIT_TRANS}},
        {"tc05", "Return Split 1 pay 0 dep OTP from Res UI", "fullFlowOnePayment", NEW_BANK, "res",
            false, new String[]{PAYMENT_TRANS, RETURN_PAYMENT_TRANS}},
        {"tc06", "Return Split 0 pay 1 dep OTP from Res UI", "fullFlowOneDeposit", NEW_BANK, "res",
            false, new String[]{DEPOSIT_TRANS, RETURN_DEPOSIT_TRANS}},
        {"tc07", "Return Split 2 pay 0 dep OTP from Res UI", "fullFlowTwoPayment", NEW_BANK, "res",
            false, new String[]{PAYMENT_TRANS, RETURN_PAYMENT_TRANS}},
        {"tc08", "Return Split 0 pay 2 dep OTP from Res UI", "fullFlowTwoDeposit", NEW_BANK, "res",
            false, new String[]{DEPOSIT_TRANS, RETURN_DEPOSIT_TRANS}},

        {"tc9", "Return Split 1 pay 1 dep OTP from PM UI", "fullFlowOnePaymentOneDeposit", NEW_BANK,
            "pm", false,
            new String[]{PAYMENT_TRANS, RETURN_PAYMENT_TRANS, DEPOSIT_TRANS, RETURN_DEPOSIT_TRANS}},
        {"tc10", "Return Split 2 pay 2 dep OTP from PM UI", "fullFlowTwoPaymentTwoDeposit",
            NEW_BANK, "pm", false,
            new String[]{PAYMENT_TRANS, RETURN_PAYMENT_TRANS, DEPOSIT_TRANS, RETURN_DEPOSIT_TRANS}},
        {"tc11", "Return Split 2 pay 1 dep OTP from PM UI", "fullFlowTwoPaymentOneDeposit",
            NEW_BANK, "pm", false,
            new String[]{PAYMENT_TRANS, RETURN_PAYMENT_TRANS, DEPOSIT_TRANS, RETURN_DEPOSIT_TRANS}},
        {"tc12", "Return Split 1 pay 2 dep OTP from PM UI", "fullFlowOnePaymentTwoDeposit",
            NEW_BANK, "pm", false,
            new String[]{PAYMENT_TRANS, RETURN_PAYMENT_TRANS, DEPOSIT_TRANS, RETURN_DEPOSIT_TRANS}},
        {"tc13", "Return Split 1 pay 0 dep OTP from PM UI", "fullFlowOnePayment", NEW_BANK, "pm",
            false, new String[]{PAYMENT_TRANS, RETURN_PAYMENT_TRANS}},
        {"tc14", "Return Split 0 pay 1 dep OTP from PM UI", "fullFlowOneDeposit", NEW_BANK, "pm",
            false, new String[]{DEPOSIT_TRANS, RETURN_DEPOSIT_TRANS}},
        {"tc15", "Return Split 2 pay 0 dep OTP from PM UI", "fullFlowTwoPayment", NEW_BANK, "pm",
            false, new String[]{PAYMENT_TRANS, RETURN_PAYMENT_TRANS}},
        {"tc16", "Return Split 0 pay 2 dep OTP from PM UI", "fullFlowTwoDeposit", NEW_BANK, "pm",
            false, new String[]{DEPOSIT_TRANS, RETURN_DEPOSIT_TRANS}},
        //-------------------------------------Partial Return---------------------------------------
        /* Only Bank(ACH) transactions can be returned */

        {"tc17", "Partial Return Split 1 pay 1 dep OTP from Res UI", "fullFlowOnePaymentOneDeposit",
            NEW_BANK, "res", true, new String[]{PAYMENT_TRANS, DEPOSIT_TRANS}},
        {"tc18", "Partial Return Split 2 pay 2 dep OTP from Res UI", "fullFlowTwoPaymentTwoDeposit",
            NEW_BANK, "res", true, new String[]{PAYMENT_TRANS, DEPOSIT_TRANS}},
        {"tc19", "Partial Return Split 2 pay 1 dep OTP from Res UI", "fullFlowTwoPaymentOneDeposit",
            NEW_BANK, "res", true, new String[]{PAYMENT_TRANS, DEPOSIT_TRANS}},
        {"tc20", "Partial Return Split 1 pay 2 dep OTP from Res UI", "fullFlowOnePaymentTwoDeposit",
            NEW_BANK, "res", true, new String[]{PAYMENT_TRANS, DEPOSIT_TRANS}},
        {"tc21", "Partial Return Split 2 pay 0 dep OTP from Res UI", "fullFlowTwoPayment", NEW_BANK,
            "res", true, new String[]{PAYMENT_TRANS}},
        {"tc22", "Partial Return Split 0 pay 2 dep OTP from Res UI", "fullFlowTwoDeposit", NEW_BANK,
            "res", true, new String[]{DEPOSIT_TRANS}},

        {"tc23", "Partial Return Split 1 pay 1 dep OTP from PM UI", "fullFlowOnePaymentOneDeposit",
            NEW_BANK, "pm", true, new String[]{PAYMENT_TRANS, DEPOSIT_TRANS}},
        {"tc24", "Partial Return Split 2 pay 2 dep OTP from PM UI", "fullFlowTwoPaymentTwoDeposit",
            NEW_BANK, "pm", true, new String[]{PAYMENT_TRANS, DEPOSIT_TRANS}},
        {"tc25", "Partial Return Split 2 pay 1 dep OTP from PM UI", "fullFlowTwoPaymentOneDeposit",
            NEW_BANK, "pm", true, new String[]{PAYMENT_TRANS, DEPOSIT_TRANS}},
        {"tc26", "Partial Return Split 1 pay 2 dep OTP from PM UI", "fullFlowOnePaymentTwoDeposit",
            NEW_BANK, "pm", true, new String[]{PAYMENT_TRANS, DEPOSIT_TRANS}},
        {"tc27", "Partial Return Split 2 pay 0 dep OTP from PM UI", "fullFlowTwoPayment", NEW_BANK,
            "pm", true, new String[]{PAYMENT_TRANS}},
        {"tc28", "Partial Return Split 0 pay 2 dep OTP from PM UI", "fullFlowTwoDeposit", NEW_BANK,
            "pm", true, new String[]{DEPOSIT_TRANS}},
    };
  }
}
