package com.paylease.app.qa.testbase.dataproviders;

import static com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage.NEW_BANK;
import static com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage.NEW_CREDIT;
import static com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage.NEW_DEBIT;
import static com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage.PAYPAL;

import org.testng.annotations.DataProvider;

public class OneTimePaymentDataProvider {

  /**
   * Provides data required to run various kinds of test combinations for Resident.
   *
   * @return data
   */
  @DataProvider(name = "otpDataResident", parallel = true)
  public Object[][] dataResident() {

    return new Object[][]{
        //Test variation no., TestCase, PaymentType, UseMakePayment, ExpressPay

        {"otpTc1", "testSetupForOtpFlow", NEW_BANK, false, false},
        {"otpTc2", "testSetupForOtpFlow", NEW_CREDIT, false, false},
        {"otpTc3", "testSetupForOtpFlow", NEW_DEBIT, false, false},
        {"otpTc4", "testSetupForOtpFlow", PAYPAL, false, false},

        {"otpTc5", "testSetupForOtpFlow", NEW_CREDIT, false, true},
        {"otpTc6", "testSetupForOtpFlow", NEW_DEBIT, false, true},
        {"otpTc7", "testSetupForOtpFlow", PAYPAL, false, true},

        {"otpTc8", "testSetupForOtpFlow", NEW_BANK, true, false},
        {"otpTc9", "testSetupForOtpFlow", NEW_CREDIT, true, false},
        {"otpTc10", "testSetupForOtpFlow", NEW_DEBIT, true, false},
        {"otpTc11", "testSetupForOtpFlow", PAYPAL, true, false},

        {"otpTc12", "testSetupForOtpFlow", NEW_CREDIT, true, true},
        {"otpTc13", "testSetupForOtpFlow", NEW_DEBIT, true, true},
        {"otpTc14", "testSetupForOtpFlow", PAYPAL, true, true}

    };
  }

  /**
   * Provides data required to run various kinds of test combinations for PM.
   *
   * @return data
   */
  @DataProvider(name = "otpDataPm", parallel = true)
  public Object[][] dataPm() {

    return new Object[][]{
        //Test variation no., TestCase, PaymentType, UseResidentsList, ExpressPay

        {"otpPmTc1", "testSetupForOtpFlow", NEW_BANK, false, false},
        {"otpPmTc2", "testSetupForOtpFlow", NEW_CREDIT, false, false},
        {"otpPmTc3", "testSetupForOtpFlow", NEW_DEBIT, false, false},

        {"otpPmTc4", "testSetupForOtpFlow", NEW_CREDIT, false, true},
        {"otpPmTc5", "testSetupForOtpFlow", NEW_DEBIT, false, true},

        {"otpPmTc6", "testSetupForOtpFlow", NEW_BANK, true, false},
        {"otpPmTc7", "testSetupForOtpFlow", NEW_CREDIT, true, false},
        {"otpPmTc8", "testSetupForOtpFlow", NEW_DEBIT, true, false},

        {"otpPmTc9", "testSetupForOtpFlow", NEW_CREDIT, true, true},
        {"otpPmTc10", "testSetupForOtpFlow", NEW_DEBIT, true, true}
    };
  }

  /**
   * Provides data required to run various kinds of test combinations for PM through Admin.
   *
   * @return data
   */
  @DataProvider(name = "otpDataAdminPm", parallel = true)
  public Object[][] dataAdminPm() {

    return new Object[][]{
        //Test variation no., TestCase, PaymentType, UseResidentsList, ExpressPay

        {"otpPmTc1", "testSetupForOtpFlowPm", NEW_BANK, false, false},
        {"otpPmTc2", "testSetupForOtpFlowPm", NEW_CREDIT, false, false},
        {"otpPmTc3", "testSetupForOtpFlowPm", NEW_DEBIT, false, false},

        {"otpPmTc4", "testSetupForOtpFlowPm", NEW_CREDIT, false, true},
        {"otpPmTc5", "testSetupForOtpFlowPm", NEW_DEBIT, false, true},

        {"otpPmTc6", "testSetupForOtpFlowPm", NEW_BANK, true, false},
        {"otpPmTc7", "testSetupForOtpFlowPm", NEW_CREDIT, true, false},
        {"otpPmTc8", "testSetupForOtpFlowPm", NEW_DEBIT, true, false},

        {"otpPmTc9", "testSetupForOtpFlowPm", NEW_CREDIT, true, true},
        {"otpPmTc10", "testSetupForOtpFlowPm", NEW_DEBIT, true, true}
    };
  }

  /**
   * Provides data required to run various kinds of test combinations for PM through Admin.
   *
   * @return data
   */
  @DataProvider(name = "otpDataAdminRes", parallel = true)
  public Object[][] dataAdminRes() {

    return new Object[][]{
        //Test variation no., TestCase, PaymentType, UseResidentsList, ExpressPay

        {"otpPmTc1", "testSetupForOtpFlowResident", NEW_BANK, false, false},
        {"otpPmTc2", "testSetupForOtpFlowResident", NEW_CREDIT, false, false},
        {"otpPmTc3", "testSetupForOtpFlowResident", NEW_DEBIT, false, false},

        {"otpPmTc4", "testSetupForOtpFlowResident", NEW_CREDIT, false, true},
        {"otpPmTc5", "testSetupForOtpFlowResident", NEW_DEBIT, false, true},

        {"otpPmTc6", "testSetupForOtpFlowResident", NEW_BANK, true, false},
        {"otpPmTc7", "testSetupForOtpFlowResident", NEW_CREDIT, true, false},
        {"otpPmTc8", "testSetupForOtpFlowResident", NEW_DEBIT, true, false},

        {"otpPmTc9", "testSetupForOtpFlowResident", NEW_CREDIT, true, true},
        {"otpPmTc10", "testSetupForOtpFlowResident", NEW_DEBIT, true, true}
    };
  }
}
