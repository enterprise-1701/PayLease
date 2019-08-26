package com.paylease.app.qa.testbase.dataproviders;

import static com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage.NEW_BANK;
import static com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage.NEW_CREDIT;
import static com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage.NEW_DEBIT;

import org.testng.annotations.DataProvider;

public class IntegratedPmPaymentFieldsDataProvider {

  /**
   * Provides data required to run various kinds of test combinations for integrated PM.
   *
   * @return data
   */
  @DataProvider(name = "otpDataIntegratedPm")
  public Object[][] dataIntegratedPm() {

    return new Object[][]{
        //Test variation no., TestCase, PaymentType, UseResidentsList, ExpressPay

        {"otpPmTc1", "createResmanPm", NEW_BANK, false, false, "Resman"},
        {"otpPmTc2", "createResmanPm", NEW_CREDIT, false, false, "Resman"},
        {"otpPmTc3", "createResmanPm", NEW_DEBIT, false, false, "Resman"},

        {"otpPmTc4", "createResmanPm", NEW_BANK, false, true, "Resman"},

        {"otpPmTc5", "createResmanPm", NEW_BANK, true, false, "Resman"},
        {"otpPmTc6", "createResmanPm", NEW_CREDIT, true, false, "Resman"},
        {"otpPmTc7", "createResmanPm", NEW_DEBIT, true, false, "Resman"},

        {"otpPmTc8", "createResmanPm", NEW_BANK, true, true, "Resman"},

        {"otpPmTc9", "createMriPm", NEW_BANK, false, false, "MRI"},
        {"otpPmTc10", "createMriPm", NEW_CREDIT, false, false, "MRI"},
        {"otpPmTc11", "createMriPm", NEW_DEBIT, false, false, "MRI"},

        {"otpPmTc12", "createMriPm", NEW_BANK, false, true, "MRI"},
        {"otpPmTc13", "createMriPm", NEW_CREDIT, false, true, "MRI"},
        {"otpPmTc14", "createMriPm", NEW_DEBIT, false, true, "MRI"},

        {"otpPmTc15", "createMriPm", NEW_BANK, true, false, "MRI"},
        {"otpPmTc16", "createMriPm", NEW_CREDIT, true, false, "MRI"},
        {"otpPmTc17", "createMriPm", NEW_DEBIT, true, false, "MRI"},

        {"otpPmTc18", "createMriPm", NEW_BANK, true, true, "MRI"},
        {"otpPmTc19", "createMriPm", NEW_CREDIT, true, true, "MRI"},
        {"otpPmTc20", "createMriPm", NEW_DEBIT, true, true, "MRI"},

        {"otpPmTc21", "createTopsPm", NEW_BANK, false, false, "TOPS"},
        {"otpPmTc22", "createTopsPm", NEW_CREDIT, false, false, "TOPS"},
        {"otpPmTc23", "createTopsPm", NEW_DEBIT, false, false, "TOPS"},

        {"otpPmTc24", "createTopsPm", NEW_BANK, false, true, "TOPS"},
        {"otpPmTc25", "createTopsPm", NEW_CREDIT, false, true, "TOPS"},
        {"otpPmTc26", "createTopsPm", NEW_DEBIT, false, true, "TOPS"},

        {"otpPmTc27", "createTopsPm", NEW_BANK, true, false, "TOPS"},
        {"otpPmTc28", "createTopsPm", NEW_CREDIT, true, false, "TOPS"},
        {"otpPmTc29", "createTopsPm", NEW_DEBIT, true, false, "TOPS"},

        {"otpPmTc30", "createTopsPm", NEW_BANK, true, true, "TOPS"},
        {"otpPmTc31", "createTopsPm", NEW_CREDIT, true, true, "TOPS"},
        {"otpPmTc32", "createTopsPm", NEW_DEBIT, true, true, "TOPS"},

        {"otpPmTc33", "createYavoPm", NEW_BANK, false, false, "YAVO"},
        {"otpPmTc34", "createYavoPm", NEW_CREDIT, false, false, "YAVO"},
        {"otpPmTc35", "createYavoPm", NEW_DEBIT, false, false, "YAVO"},

        {"otpPmTc36", "createYavoPm", NEW_BANK, false, true, "YAVO"},
        {"otpPmTc37", "createYavoPm", NEW_CREDIT, false, true, "YAVO"},
        {"otpPmTc38", "createYavoPm", NEW_DEBIT, false, true, "YAVO"},

        {"otpPmTc39", "createYavoPm", NEW_BANK, true, false, "YAVO"},
        {"otpPmTc40", "createYavoPm", NEW_CREDIT, true, false, "YAVO"},
        {"otpPmTc41", "createYavoPm", NEW_DEBIT, true, false, "YAVO"},

        {"otpPmTc42", "createYavoPm", NEW_BANK, true, true, "YAVO"},
        {"otpPmTc43", "createYavoPm", NEW_CREDIT, true, true, "YAVO"},
        {"otpPmTc44", "createYavoPm", NEW_DEBIT, true, true, "YAVO"},

        {"otpPmTc45", "createAmsiPm", NEW_BANK, false, false, "AMSI"},
        {"otpPmTc46", "createAmsiPm", NEW_CREDIT, false, false, "AMSI"},
        {"otpPmTc47", "createAmsiPm", NEW_DEBIT, false, false, "AMSI"},

        {"otpPmTc48", "createAmsiPm", NEW_BANK, false, true, "AMSI"},
        {"otpPmTc49", "createAmsiPm", NEW_CREDIT, false, true, "AMSI"},
        {"otpPmTc50", "createAmsiPm", NEW_DEBIT, false, true, "AMSI"},

        {"otpPmTc51", "createAmsiPm", NEW_BANK, true, false, "AMSI"},
        {"otpPmTc52", "createAmsiPm", NEW_CREDIT, true, false, "AMSI"},
        {"otpPmTc53", "createAmsiPm", NEW_DEBIT, true, false, "AMSI"},

        {"otpPmTc54", "createAmsiPm", NEW_BANK, true, true, "AMSI"},
        {"otpPmTc55", "createAmsiPm", NEW_CREDIT, true, true, "AMSI"},
        {"otpPmTc56", "createAmsiPm", NEW_DEBIT, true, true, "AMSI"},

        {"otpPmTc57", "createOnesitePm", NEW_BANK, false, false, "OneSite"},
        {"otpPmTc58", "createOnesitePm", NEW_CREDIT, false, false, "OneSite"},
        {"otpPmTc59", "createOnesitePm", NEW_DEBIT, false, false, "OneSite"},

        {"otpPmTc60", "createOnesitePm", NEW_BANK, false, true, "OneSite"},
        {"otpPmTc61", "createOnesitePm", NEW_CREDIT, false, true, "OneSite"},
        {"otpPmTc62", "createOnesitePm", NEW_DEBIT, false, true, "OneSite"},

        {"otpPmTc63", "createOnesitePm", NEW_BANK, true, false, "OneSite"},
        {"otpPmTc64", "createOnesitePm", NEW_CREDIT, true, false, "OneSite"},
        {"otpPmTc65", "createOnesitePm", NEW_DEBIT, true, false, "OneSite"},

        {"otpPmTc66", "createOnesitePm", NEW_BANK, true, true, "OneSite"},
        {"otpPmTc67", "createOnesitePm", NEW_CREDIT, true, true, "OneSite"},
        {"otpPmTc68", "createOnesitePm", NEW_DEBIT, true, true, "OneSite"},
    };
  }

  /**
   * Provides data required to run various transactions coming in via GAPI.
   *
   * @return data
   */
  @DataProvider(name = "otpDataIntegratedPmGapi")
  public Object[][] dataIntegratedPmGapi() {

    return new Object[][]{
        //Test variation no., TestCase, Transaction Type

        {"gapiPmTc1", "resmanGapiPm", "ACH"},
        {"gapiPmTc2", "mriGapiPm", "ACH"},
        {"gapiPmTc3", "topsGapiPm", "ACH"},
        {"gapiPmTc4", "yavoGapiPm", "ACH"},
        {"gapiPmTc5", "amsiGapiPm", "ACH"},
        {"gapiPmTc5", "onesiteGapiPm", "ACH"},

        {"gapiPmTc7", "resmanGapiPm", "CC"},
        {"gapiPmTc8", "mriGapiPm", "CC"},
        {"gapiPmTc9", "topsGapiPm", "CC"},
        {"gapiPmTc10", "yavoGapiPm", "CC"},
        {"gapiPmTc11", "amsiGapiPm", "CC"},
        {"gapiPmTc12", "onesiteGapiPm", "CC"},

        {"gapiPmTc13", "resmanGapiPm", "AccountPayment"},
        {"gapiPmTc14", "mriGapiPm", "AccountPayment"},
        {"gapiPmTc15", "topsGapiPm", "AccountPayment"},
        {"gapiPmTc16", "yavoGapiPm", "AccountPayment"},
        {"gapiPmTc17", "amsiGapiPm", "AccountPayment"},
        {"gapiPmTc18", "onesiteGapiPm", "AccountPayment"},

        {"gapiPmTc19", "resmanGapiPm", "Check21"},
        {"gapiPmTc20", "mriGapiPm", "Check21"},
        {"gapiPmTc21", "topsGapiPm", "Check21"},
        {"gapiPmTc22", "yavoGapiPm", "Check21"},
        {"gapiPmTc23", "amsiGapiPm", "Check21"},
        {"gapiPmTc24", "onesiteGapiPm", "Check21"},

        {"gapiPmTc25", "mriGapiPm", "Rdc"},
        {"gapiPmTc26", "mriGapiPm", "Rdc"},
        {"gapiPmTc27", "topsGapiPm", "Rdc"},
        {"gapiPmTc28", "yavoGapiPm", "Rdc"},
        {"gapiPmTc29", "amsiGapiPm", "Rdc"},
        {"gapiPmTc30", "onesiteGapiPm", "Rdc"},
    };
  }
}