package com.paylease.app.qa.testbase.dataproviders;

import static com.paylease.app.qa.framework.pages.admin.TransactionDetailPage.ADMIN_REFUND_MAX;
import static com.paylease.app.qa.framework.pages.admin.TransactionDetailPage.ADMIN_REFUND_OVER_MAX;
import static com.paylease.app.qa.framework.pages.admin.TransactionDetailPage.ADMIN_REFUND_PARTIAL;
import static com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage.NEW_BANK;
import static com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage.NEW_CREDIT;
import static com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage.NEW_DEBIT;
import static com.paylease.app.qa.framework.pages.paymentflow.SchedulePage.SELECT_ANNUALLY;
import static com.paylease.app.qa.framework.pages.paymentflow.SchedulePage.SELECT_BI_ANNUALLY;
import static com.paylease.app.qa.framework.pages.paymentflow.SchedulePage.SELECT_MONTHLY;
import static com.paylease.app.qa.framework.pages.paymentflow.SchedulePage.SELECT_QUARTERLY;
import static com.paylease.app.qa.framework.pages.pm.PmPaymentHistoryPage.REFUND_TYPE_FULL;
import static com.paylease.app.qa.framework.pages.pm.PmPaymentHistoryPage.REFUND_TYPE_PARTIAL_MAX;
import static com.paylease.app.qa.framework.pages.pm.PmPaymentHistoryPage.REFUND_TYPE_PARTIAL_PARTIAL;
import static com.paylease.app.qa.framework.pages.pm.PmPaymentHistoryPage.REFUND_TYPE_PARTIAL_SINGLE_FIELD;

import org.testng.annotations.DataProvider;

public class ManagedDepositsOneTimePaymentDataProvider {

  /**
   * Provides data required to run various kinds of test combinations for PM.
   *
   * @return data
   */
  @DataProvider(name = "otpData")
  public Object[][] dataPm() {

    return new Object[][]{
        //Test variation no., TestCase, PaymentType, UseResidentsList, ExpressPay, isManagedDepositsV2On

        {"otpPmTc1", "createYavoPmWithManagedDepositsV2", NEW_BANK, false, false, true, true},
        {"otpPmTc2", "createYavoPmWithManagedDepositsV2", NEW_CREDIT, false, false, true, true},
        {"otpPmTc3", "createYavoPmWithManagedDepositsV2", NEW_DEBIT, false, false, true, true},

        {"otpPmTc4", "createYavoPmWithManagedDepositsV2", NEW_BANK, false, true, true, true},
        {"otpPmTc5", "createYavoPmWithManagedDepositsV2", NEW_CREDIT, false, true, true, true},
        {"otpPmTc6", "createYavoPmWithManagedDepositsV2", NEW_DEBIT, false, true, true, true},

        {"otpPmTc7", "createYavoPmWithManagedDepositsV2", NEW_BANK, true, false, true, true},
        {"otpPmTc8", "createYavoPmWithManagedDepositsV2", NEW_CREDIT, true, false, true, true},
        {"otpPmTc9", "createYavoPmWithManagedDepositsV2", NEW_DEBIT, true, false, true, true},

        {"otpPmTc10", "createYavoPmWithManagedDepositsV2", NEW_BANK, true, true, true, true},
        {"otpPmTc11", "createYavoPmWithManagedDepositsV2", NEW_CREDIT, true, true, true, true},
        {"otpPmTc12", "createYavoPmWithManagedDepositsV2", NEW_DEBIT, true, true, true, true},

        {"otpPmTc13", "createYavoPmforFnboProcessingWithManagedDepositsV2", NEW_BANK, false, false, true, true},
        {"otpPmTc14", "createYavoPmforFnboProcessingWithManagedDepositsV2", NEW_CREDIT, false, false, true, true},
        {"otpPmTc15", "createYavoPmforFnboProcessingWithManagedDepositsV2", NEW_DEBIT, false, false, true, true},

        {"otpPmTc16", "createYavoPmforFnboProcessingWithManagedDepositsV2", NEW_BANK, false, true, true, true},
        {"otpPmTc17", "createYavoPmforFnboProcessingWithManagedDepositsV2", NEW_CREDIT, false, true, true, true},
        {"otpPmTc18", "createYavoPmforFnboProcessingWithManagedDepositsV2", NEW_DEBIT, false, true, true, true},

        {"otpPmTc19", "createYavoPmforFnboProcessingWithManagedDepositsV2", NEW_BANK, true, false, true, true},
        {"otpPmTc20", "createYavoPmforFnboProcessingWithManagedDepositsV2", NEW_CREDIT, true, false, true, true},
        {"otpPmTc21", "createYavoPmforFnboProcessingWithManagedDepositsV2", NEW_DEBIT, true, false, true, true},

        {"otpPmTc22", "createYavoPmforFnboProcessingWithManagedDepositsV2", NEW_BANK, true, true, true, true},
        {"otpPmTc23", "createYavoPmforFnboProcessingWithManagedDepositsV2", NEW_CREDIT, true, true, true, true},
        {"otpPmTc24", "createYavoPmforFnboProcessingWithManagedDepositsV2", NEW_DEBIT, true, true, true, true},

        {"otpPmTc25", "createYavoPm", NEW_BANK, false, false, false, false},
        {"otpPmTc26", "createYavoPm", NEW_CREDIT, false, false, false, false},
        {"otpPmTc27", "createYavoPm", NEW_DEBIT, false, false, false, false},

        {"otpPmTc28", "createYavoPm", NEW_BANK, false, true, false, false},
        {"otpPmTc29", "createYavoPm", NEW_CREDIT, false, true, false, false},
        {"otpPmTc30", "createYavoPm", NEW_DEBIT, false, true, false, false},

        {"otpPmTc31", "createYavoPm", NEW_BANK, true, false, false, false},
        {"otpPmTc32", "createYavoPm", NEW_CREDIT, true, false, false, false},
        {"otpPmTc33", "createYavoPm", NEW_DEBIT, true, false, false, false},

        {"otpPmTc34", "createYavoPm", NEW_BANK, true, true, false, false},
        {"otpPmTc35", "createYavoPm", NEW_CREDIT, true, true, false, false},
        {"otpPmTc36", "createYavoPm", NEW_DEBIT, true, true, false, false},

        {"otpPmTc37", "createYavoPmforFnboProcessing", NEW_BANK, false, false, false, false},
        {"otpPmTc38", "createYavoPmforFnboProcessing", NEW_CREDIT, false, false, false, false},
        {"otpPmTc39", "createYavoPmforFnboProcessing", NEW_DEBIT, false, false, false, false},

        {"otpPmTc40", "createYavoPmforFnboProcessing", NEW_BANK, false, true, false, false},
        {"otpPmTc41", "createYavoPmforFnboProcessing", NEW_CREDIT, false, true, false, false},
        {"otpPmTc42", "createYavoPmforFnboProcessing", NEW_DEBIT, false, true, false, false},

        {"otpPmTc43", "createYavoPmforFnboProcessing", NEW_BANK, true, false, false, false},
        {"otpPmTc44", "createYavoPmforFnboProcessing", NEW_CREDIT, true, false, false, false},
        {"otpPmTc45", "createYavoPmforFnboProcessing", NEW_DEBIT, true, false, false, false},

        {"otpPmTc43", "createYavoPmforFnboProcessing", NEW_BANK, true, false, false, false},
        {"otpPmTc44", "createYavoPmforFnboProcessing", NEW_CREDIT, true, false, false, false},
        {"otpPmTc45", "createYavoPmforFnboProcessing", NEW_DEBIT, true, false, false, false},

        {"otpPmTc46", "createYavoPmforFnboProcessing", NEW_BANK, true, true, false, false},
        {"otpPmTc47", "createYavoPmforFnboProcessing", NEW_CREDIT, true, true, false, false},
        {"otpPmTc48", "createYavoPmforFnboProcessing", NEW_DEBIT, true, true, false, false},

    };
  }

  /**
   * Provides data required to run various kinds of test combinations for eligible refunds.
   *
   * @return data
   */
  @DataProvider(name = "refundTransDataPm")
  public Object[][] dataRefundTrans() {
    //String testVariationNo, String testCaseSetup, String refundType, String paymentType,
    // boolean isManagedDepositsV2On
    return new Object[][]{
        {"tc97", "createYavoPmWithManagedDepositsV2", REFUND_TYPE_FULL, NEW_BANK, true},
        {"tc98", "createYavoPmWithManagedDepositsV2", REFUND_TYPE_FULL, NEW_CREDIT, true},
        {"tc99", "createYavoPmWithManagedDepositsV2", REFUND_TYPE_FULL, NEW_DEBIT, true},

        {"tc100", "createYavoPmWithManagedDepositsV2", REFUND_TYPE_PARTIAL_MAX, NEW_DEBIT, true},
        {"tc101", "createYavoPmWithManagedDepositsV2", REFUND_TYPE_PARTIAL_MAX, NEW_CREDIT, true},

        {"tc102", "createYavoPmWithManagedDepositsV2", REFUND_TYPE_PARTIAL_SINGLE_FIELD, NEW_CREDIT, true},
        {"tc103", "createYavoPmWithManagedDepositsV2", REFUND_TYPE_PARTIAL_SINGLE_FIELD, NEW_DEBIT, true},

        {"tc104", "createYavoPmWithManagedDepositsV2", REFUND_TYPE_PARTIAL_PARTIAL, NEW_CREDIT, true},
        {"tc105", "createYavoPmWithManagedDepositsV2", REFUND_TYPE_PARTIAL_PARTIAL, NEW_DEBIT, true},

        {"tc106", "createYavoPmforFnboProcessingWithManagedDepositsV2", REFUND_TYPE_FULL, NEW_BANK, true},
        {"tc107", "createYavoPmforFnboProcessingWithManagedDepositsV2", REFUND_TYPE_FULL, NEW_CREDIT, true},
        {"tc108", "createYavoPmforFnboProcessingWithManagedDepositsV2", REFUND_TYPE_FULL, NEW_DEBIT, true},

        {"tc109", "createYavoPmforFnboProcessingWithManagedDepositsV2", REFUND_TYPE_PARTIAL_MAX, NEW_DEBIT, true},
        {"tc110", "createYavoPmforFnboProcessingWithManagedDepositsV2", REFUND_TYPE_PARTIAL_MAX, NEW_BANK, true},
        {"tc111", "createYavoPmforFnboProcessingWithManagedDepositsV2", REFUND_TYPE_PARTIAL_MAX, NEW_CREDIT, true},

        {"tc112", "createYavoPmforFnboProcessingWithManagedDepositsV2", REFUND_TYPE_PARTIAL_SINGLE_FIELD, NEW_BANK, true},
        {"tc113", "createYavoPmforFnboProcessingWithManagedDepositsV2", REFUND_TYPE_PARTIAL_SINGLE_FIELD, NEW_CREDIT, true},
        {"tc114", "createYavoPmforFnboProcessingWithManagedDepositsV2", REFUND_TYPE_PARTIAL_SINGLE_FIELD, NEW_DEBIT, true},

        {"tc115", "createYavoPmforFnboProcessingWithManagedDepositsV2", REFUND_TYPE_PARTIAL_PARTIAL, NEW_BANK, true},
        {"tc116", "createYavoPmforFnboProcessingWithManagedDepositsV2", REFUND_TYPE_PARTIAL_PARTIAL, NEW_CREDIT, true},
        {"tc117", "createYavoPmforFnboProcessingWithManagedDepositsV2", REFUND_TYPE_PARTIAL_PARTIAL, NEW_DEBIT, true},

        {"tc118", "createYavoPm", REFUND_TYPE_FULL, NEW_BANK, false},
        {"tc119", "createYavoPm", REFUND_TYPE_FULL, NEW_CREDIT, false},
        {"tc120", "createYavoPm", REFUND_TYPE_FULL, NEW_DEBIT, false},

        {"tc121", "createYavoPm", REFUND_TYPE_PARTIAL_MAX, NEW_DEBIT, false},
        {"tc122", "createYavoPm", REFUND_TYPE_PARTIAL_MAX, NEW_CREDIT, false},

        {"tc123", "createYavoPm", REFUND_TYPE_PARTIAL_SINGLE_FIELD, NEW_CREDIT, false},
        {"tc124", "createYavoPm", REFUND_TYPE_PARTIAL_SINGLE_FIELD, NEW_DEBIT, false},

        {"tc125", "createYavoPm", REFUND_TYPE_PARTIAL_PARTIAL, NEW_CREDIT, false},
        {"tc126", "createYavoPm", REFUND_TYPE_PARTIAL_PARTIAL, NEW_DEBIT, false},

        {"tc127", "createYavoPmforFnboProcessing", REFUND_TYPE_FULL, NEW_BANK, false},
        {"tc128", "createYavoPmforFnboProcessing", REFUND_TYPE_FULL, NEW_CREDIT, false},
        {"tc129", "createYavoPmforFnboProcessing", REFUND_TYPE_FULL, NEW_DEBIT, false},

        {"tc130", "createYavoPmforFnboProcessing", REFUND_TYPE_PARTIAL_MAX, NEW_DEBIT, false},
        {"tc131", "createYavoPmforFnboProcessing", REFUND_TYPE_PARTIAL_MAX, NEW_BANK, false},
        {"tc132", "createYavoPmforFnboProcessing", REFUND_TYPE_PARTIAL_MAX, NEW_CREDIT, false},

        {"tc133", "createYavoPmforFnboProcessing", REFUND_TYPE_PARTIAL_SINGLE_FIELD, NEW_BANK, false},
        {"tc134", "createYavoPmforFnboProcessing", REFUND_TYPE_PARTIAL_SINGLE_FIELD, NEW_CREDIT, false},
        {"tc135", "createYavoPmforFnboProcessing", REFUND_TYPE_PARTIAL_SINGLE_FIELD, NEW_DEBIT, false},

        {"tc136", "createYavoPmforFnboProcessing", REFUND_TYPE_PARTIAL_PARTIAL, NEW_BANK, false},
        {"tc137", "createYavoPmforFnboProcessing", REFUND_TYPE_PARTIAL_PARTIAL, NEW_CREDIT, false},
        {"tc138", "createYavoPmforFnboProcessing", REFUND_TYPE_PARTIAL_PARTIAL, NEW_DEBIT, false},

    };
  }

  /**
   * Provides data required to run various kinds of test combinations for eligible refunds.
   *
   * @return data
   */
  @DataProvider(name = "refundTransDataAdmin")
  public Object[][] dataRefundTransAdmin() {
    // String testVariationNo, String testCaseSetup,String refundType, String paymentType,
    // boolean isManagedDepositsV2On, String processor
    return new Object[][]{
        {"tc181", "createYavoPmWithManagedDepositsV2", ADMIN_REFUND_MAX, NEW_BANK, true, "PROFITSTARS"},
        {"tc182", "createYavoPmWithManagedDepositsV2", ADMIN_REFUND_PARTIAL, NEW_BANK, true, "PROFITSTARS"},
        {"tc183", "createYavoPmWithManagedDepositsV2", ADMIN_REFUND_OVER_MAX, NEW_BANK, true, "PROFITSTARS"},

        {"tc184", "createYavoPmforFnboProcessingWithManagedDepositsV2", ADMIN_REFUND_MAX, NEW_BANK, true, "FNBO"},
        {"tc185", "createYavoPmforFnboProcessingWithManagedDepositsV2", ADMIN_REFUND_PARTIAL, NEW_BANK, true, "FNBO"},
        {"tc186", "createYavoPmforFnboProcessingWithManagedDepositsV2", ADMIN_REFUND_OVER_MAX, NEW_BANK, true, "FNBO"},

        {"tc187", "createYavoPm", ADMIN_REFUND_MAX, NEW_BANK, false, "PROFITSTARS"},
        {"tc188", "createYavoPm", ADMIN_REFUND_PARTIAL, NEW_BANK, false, "PROFITSTARS"},
        {"tc189", "createYavoPm", ADMIN_REFUND_OVER_MAX, NEW_BANK, false, "PROFITSTARS"},

        {"tc190", "createYavoPmforFnboProcessingWithManagedDepositsV2", ADMIN_REFUND_MAX, NEW_BANK, false, "FNBO"},
        {"tc191", "createYavoPmforFnboProcessingWithManagedDepositsV2", ADMIN_REFUND_PARTIAL, NEW_BANK, false, "FNBO"},
        {"tc192", "createYavoPmforFnboProcessingWithManagedDepositsV2", ADMIN_REFUND_OVER_MAX, NEW_BANK, false, "FNBO"},

    };
  }

  /**
   * Provides data required to run various kinds of test combinations for eligible reversals.
   *
   * @return data
   */
  @DataProvider(name = "reversalTransDataAdmin")
  public Object[][] dataReversalTransAdmin() {
    // String testVariationNo, String testCaseSetup, String reversalType, String paymentType,
    // boolean isManagedDepositsV2On, String processor
    return new Object[][]{
        {"tc205", "createYavoPmWithManagedDepositsV2", ADMIN_REFUND_MAX, NEW_BANK, true, "PROFITSTARS", true},
        {"tc206", "createYavoPmWithManagedDepositsV2", ADMIN_REFUND_PARTIAL, NEW_BANK, true, "PROFITSTARS", true},
        {"tc207", "createYavoPmWithManagedDepositsV2", ADMIN_REFUND_OVER_MAX, NEW_BANK, true, "PROFITSTARS", true},

        {"tc208", "createYavoPmforFnboProcessingWithManagedDepositsV2", ADMIN_REFUND_MAX, NEW_BANK, true, "FNBO", true},
        {"tc209", "createYavoPmforFnboProcessingWithManagedDepositsV2", ADMIN_REFUND_MAX, NEW_CREDIT, true, "FNBOCC", true},
        {"tc210", "createYavoPmforFnboProcessingWithManagedDepositsV2", ADMIN_REFUND_MAX, NEW_DEBIT, true, "FNBO", true},

        {"tc211", "createYavoPmforFnboProcessingWithManagedDepositsV2", ADMIN_REFUND_PARTIAL, NEW_BANK, true, "FNBO", true},
        {"tc212", "createYavoPmforFnboProcessingWithManagedDepositsV2", ADMIN_REFUND_PARTIAL, NEW_CREDIT, true, "FNBOCC", true},
        {"tc213", "createYavoPmforFnboProcessingWithManagedDepositsV2", ADMIN_REFUND_PARTIAL, NEW_DEBIT, true, "FNBOCC", true},

        {"tc214", "createYavoPmforFnboProcessingWithManagedDepositsV2", ADMIN_REFUND_OVER_MAX, NEW_BANK, true, "FNBO", true},
        {"tc215", "createYavoPmforFnboProcessingWithManagedDepositsV2", ADMIN_REFUND_OVER_MAX, NEW_CREDIT, true, "FNBOCC", true},
        {"tc216", "createYavoPmforFnboProcessingWithManagedDepositsV2", ADMIN_REFUND_OVER_MAX, NEW_DEBIT, true, "FNBOCC", true},

        {"tc217", "createYavoPm", ADMIN_REFUND_MAX, NEW_BANK, false, "PROFITSTARS", false},
        {"tc218", "createYavoPm", ADMIN_REFUND_PARTIAL, NEW_BANK, false, "PROFITSTARS", false},
        {"tc219", "createYavoPm", ADMIN_REFUND_OVER_MAX, NEW_BANK, false, "PROFITSTARS", false},

        {"tc220", "createYavoPmforFnboProcessing", ADMIN_REFUND_MAX, NEW_BANK, false, "FNBO", false},
        {"tc221", "createYavoPmforFnboProcessing", ADMIN_REFUND_MAX, NEW_CREDIT, false, "FNBOCC", false},
        {"tc222", "createYavoPmforFnboProcessing", ADMIN_REFUND_MAX, NEW_DEBIT, false, "FNBOCC", false},

        {"tc223", "createYavoPmforFnboProcessing", ADMIN_REFUND_PARTIAL, NEW_BANK, false, "FNBO", false},
        {"tc224", "createYavoPmforFnboProcessing", ADMIN_REFUND_PARTIAL, NEW_CREDIT, false, "FNBOCC", false},
        {"tc225", "createYavoPmforFnboProcessing", ADMIN_REFUND_PARTIAL, NEW_DEBIT, false, "FNBOCC", false},

        {"tc226", "createYavoPmforFnboProcessing", ADMIN_REFUND_OVER_MAX, NEW_BANK, false, "FNBO", false},
        {"tc227", "createYavoPmforFnboProcessing", ADMIN_REFUND_OVER_MAX, NEW_CREDIT, false, "FNBOCC", false},
        {"tc228", "createYavoPmforFnboProcessing", ADMIN_REFUND_OVER_MAX, NEW_DEBIT, false, "FNBOCC", false},

        {"tc229", "createYavoPmforFnboProcessingWithManagedDepositsV2", ADMIN_REFUND_MAX, NEW_BANK, true, "FNBO", false},
    };
  }

  /**
   * Provides data required to run various kinds of test combinations autopay.
   *
   * @return data
   */
  @DataProvider(name = "autoPayPm")
  public Object[][] dataAutoPayPm() {
    //String testVariationNo, String testCaseSetup, String frequency, String paymentType,
    // boolean expressPay, boolean isYavoPm, boolean isManagedDepositsV2On
    return new Object[][] {
        {"tc253", "createYavoPmWithManagedDepositsV2", SELECT_MONTHLY, NEW_BANK, true, true, true},
        {"tc255", "createYavoPmWithManagedDepositsV2", SELECT_MONTHLY, NEW_BANK, false, true, true},
        {"tc254", "createYavoPmWithManagedDepositsV2", SELECT_MONTHLY, NEW_CREDIT, true, true, true},
        {"tc255", "createYavoPmWithManagedDepositsV2", SELECT_MONTHLY, NEW_CREDIT, false, true, true},
        {"tc256", "createYavoPmWithManagedDepositsV2", SELECT_MONTHLY, NEW_DEBIT, true, true, true},
        {"tc257", "createYavoPmWithManagedDepositsV2", SELECT_MONTHLY, NEW_DEBIT, false, true, true},

        {"tc258", "createYavoPmforFnboProcessingWithManagedDepositsV2", SELECT_MONTHLY, NEW_BANK, true, true, true},
        {"tc259", "createYavoPmforFnboProcessingWithManagedDepositsV2", SELECT_MONTHLY, NEW_BANK, false, true, true},
        {"tc260", "createYavoPmforFnboProcessingWithManagedDepositsV2", SELECT_MONTHLY, NEW_CREDIT, true, true, true},
        {"tc261", "createYavoPmforFnboProcessingWithManagedDepositsV2", SELECT_MONTHLY, NEW_CREDIT, false, true, true},
        {"tc262", "createYavoPmforFnboProcessingWithManagedDepositsV2", SELECT_MONTHLY, NEW_DEBIT, true, true, true},
        {"tc263", "createYavoPmforFnboProcessingWithManagedDepositsV2", SELECT_MONTHLY, NEW_DEBIT, false, true, true},

        {"tc264", "createYavoPmWithManagedDepositsV2", SELECT_QUARTERLY, NEW_BANK, true, true, true},
        {"tc265", "createYavoPmWithManagedDepositsV2", SELECT_QUARTERLY, NEW_BANK, false, true, true},
        {"tc266", "createYavoPmWithManagedDepositsV2", SELECT_QUARTERLY, NEW_CREDIT, true, true, true},
        {"tc267", "createYavoPmWithManagedDepositsV2", SELECT_QUARTERLY, NEW_CREDIT, false, true, true},
        {"tc268", "createYavoPmWithManagedDepositsV2", SELECT_QUARTERLY, NEW_DEBIT, true, true, true},
        {"tc269", "createYavoPmWithManagedDepositsV2", SELECT_QUARTERLY, NEW_DEBIT, false, true, true},

        {"tc270", "createYavoPmforFnboProcessingWithManagedDepositsV2", SELECT_QUARTERLY, NEW_BANK, true, true, true},
        {"tc271", "createYavoPmforFnboProcessingWithManagedDepositsV2", SELECT_QUARTERLY, NEW_BANK, false, true, true},
        {"tc272", "createYavoPmforFnboProcessingWithManagedDepositsV2", SELECT_QUARTERLY, NEW_CREDIT, true, true, true},
        {"tc273", "createYavoPmforFnboProcessingWithManagedDepositsV2", SELECT_QUARTERLY, NEW_CREDIT, false, true, true},
        {"tc274", "createYavoPmforFnboProcessingWithManagedDepositsV2", SELECT_QUARTERLY, NEW_DEBIT, true, true, true},
        {"tc275", "createYavoPmforFnboProcessingWithManagedDepositsV2", SELECT_QUARTERLY, NEW_DEBIT, false, true, true},

        {"tc276", "createYavoPmWithManagedDepositsV2", SELECT_BI_ANNUALLY, NEW_BANK, true, true, true},
        {"tc277", "createYavoPmWithManagedDepositsV2", SELECT_BI_ANNUALLY, NEW_BANK, false, true, true},
        {"tc278", "createYavoPmWithManagedDepositsV2", SELECT_BI_ANNUALLY, NEW_CREDIT, true, true, true},
        {"tc279", "createYavoPmWithManagedDepositsV2", SELECT_BI_ANNUALLY, NEW_CREDIT, false, true, true},
        {"tc280", "createYavoPmWithManagedDepositsV2", SELECT_BI_ANNUALLY, NEW_DEBIT, true, true, true},
        {"tc281", "createYavoPmWithManagedDepositsV2", SELECT_BI_ANNUALLY, NEW_DEBIT, false, true, true},

        {"tc282", "createYavoPmforFnboProcessingWithManagedDepositsV2", SELECT_BI_ANNUALLY, NEW_BANK, true, true, true},
        {"tc283", "createYavoPmforFnboProcessingWithManagedDepositsV2", SELECT_BI_ANNUALLY, NEW_BANK, false, true, true},
        {"tc284", "createYavoPmforFnboProcessingWithManagedDepositsV2", SELECT_BI_ANNUALLY, NEW_CREDIT, true, true, true},
        {"tc285", "createYavoPmforFnboProcessingWithManagedDepositsV2", SELECT_BI_ANNUALLY, NEW_CREDIT, false, true, true},
        {"tc286", "createYavoPmforFnboProcessingWithManagedDepositsV2", SELECT_BI_ANNUALLY, NEW_DEBIT, true, true, true},
        {"tc287", "createYavoPmforFnboProcessingWithManagedDepositsV2", SELECT_BI_ANNUALLY, NEW_DEBIT, false, true, true},

        {"tc288", "createYavoPmWithManagedDepositsV2", SELECT_ANNUALLY, NEW_BANK, true, true, true},
        {"tc289", "createYavoPmWithManagedDepositsV2", SELECT_ANNUALLY, NEW_BANK, false, true, true},
        {"tc290", "createYavoPmWithManagedDepositsV2", SELECT_ANNUALLY, NEW_CREDIT, true, true, true},
        {"tc291", "createYavoPmWithManagedDepositsV2", SELECT_ANNUALLY, NEW_CREDIT, false, true, true},
        {"tc292", "createYavoPmWithManagedDepositsV2", SELECT_ANNUALLY, NEW_DEBIT, true, true, true},
        {"tc293", "createYavoPmWithManagedDepositsV2", SELECT_ANNUALLY, NEW_DEBIT, false, true, true},

        {"tc294", "createYavoPmforFnboProcessingWithManagedDepositsV2", SELECT_ANNUALLY, NEW_BANK, true, true, true},
        {"tc295", "createYavoPmforFnboProcessingWithManagedDepositsV2", SELECT_ANNUALLY, NEW_BANK, false, true, true},
        {"tc296", "createYavoPmforFnboProcessingWithManagedDepositsV2", SELECT_ANNUALLY, NEW_CREDIT, true, true, true},
        {"tc297", "createYavoPmforFnboProcessingWithManagedDepositsV2", SELECT_ANNUALLY, NEW_CREDIT, false, true, true},
        {"tc298", "createYavoPmforFnboProcessingWithManagedDepositsV2", SELECT_ANNUALLY, NEW_DEBIT, true, true, true},
        {"tc299", "createYavoPmforFnboProcessingWithManagedDepositsV2", SELECT_ANNUALLY, NEW_DEBIT, false, true, true},

        //Yavo Pm, Not enabled for ManagedDepositsV2

        {"tc300", "createYavoPm", SELECT_MONTHLY, NEW_BANK, true, true, false},
        {"tc301", "createYavoPm", SELECT_MONTHLY, NEW_BANK, false, true, false},
        {"tc302", "createYavoPm", SELECT_MONTHLY, NEW_CREDIT, true, true, false},
        {"tc303", "createYavoPm", SELECT_MONTHLY, NEW_CREDIT, false, true, false},
        {"tc304", "createYavoPm", SELECT_MONTHLY, NEW_DEBIT, true, true, false},
        {"tc305", "createYavoPm", SELECT_MONTHLY, NEW_DEBIT, false, true, false},

        {"tc306", "createYavoPmforFnboProcessing", SELECT_MONTHLY, NEW_BANK, true, true, false},
        {"tc307", "createYavoPmforFnboProcessing", SELECT_MONTHLY, NEW_BANK, false, true, false},
        {"tc308", "createYavoPmforFnboProcessing", SELECT_MONTHLY, NEW_CREDIT, true, true, false},
        {"tc309", "createYavoPmforFnboProcessing", SELECT_MONTHLY, NEW_CREDIT, false, true, false},
        {"tc310", "createYavoPmforFnboProcessing", SELECT_MONTHLY, NEW_DEBIT, true, true, false},
        {"tc311", "createYavoPmforFnboProcessing", SELECT_MONTHLY, NEW_DEBIT, false, true, false},

        {"tc312", "createYavoPm", SELECT_QUARTERLY, NEW_BANK, true, true, false},
        {"tc313", "createYavoPm", SELECT_QUARTERLY, NEW_BANK, false, true, false},
        {"tc314", "createYavoPm", SELECT_QUARTERLY, NEW_CREDIT, true, true, false},
        {"tc315", "createYavoPm", SELECT_QUARTERLY, NEW_CREDIT, false, true, false},
        {"tc316", "createYavoPm", SELECT_QUARTERLY, NEW_DEBIT, true, true, false},
        {"tc317", "createYavoPm", SELECT_QUARTERLY, NEW_DEBIT, false, true, false},

        {"tc318", "createYavoPmforFnboProcessing", SELECT_QUARTERLY, NEW_BANK, true, true, false},
        {"tc319", "createYavoPmforFnboProcessing", SELECT_QUARTERLY, NEW_BANK, false, true, false},
        {"tc320", "createYavoPmforFnboProcessing", SELECT_QUARTERLY, NEW_CREDIT, true, true, false},
        {"tc321", "createYavoPmforFnboProcessing", SELECT_QUARTERLY, NEW_CREDIT, false, true, false},
        {"tc322", "createYavoPmforFnboProcessing", SELECT_QUARTERLY, NEW_DEBIT, true, true, false},
        {"tc323", "createYavoPmforFnboProcessing", SELECT_QUARTERLY, NEW_DEBIT, false, true, false},

        {"tc324", "createYavoPm", SELECT_BI_ANNUALLY, NEW_BANK, true, true, false},
        {"tc325", "createYavoPm", SELECT_BI_ANNUALLY, NEW_BANK, false, true, false},
        {"tc326", "createYavoPm", SELECT_BI_ANNUALLY, NEW_CREDIT, true, true, false},
        {"tc327", "createYavoPm", SELECT_BI_ANNUALLY, NEW_CREDIT, false, true, false},
        {"tc328", "createYavoPm", SELECT_BI_ANNUALLY, NEW_DEBIT, true, true, false},
        {"tc329", "createYavoPm", SELECT_BI_ANNUALLY, NEW_DEBIT, false, true, false},

        {"tc330", "createYavoPmforFnboProcessing", SELECT_BI_ANNUALLY, NEW_BANK, true, true, false},
        {"tc331", "createYavoPmforFnboProcessing", SELECT_BI_ANNUALLY, NEW_BANK, false, true, false},
        {"tc332", "createYavoPmforFnboProcessing", SELECT_BI_ANNUALLY, NEW_CREDIT, true, true, false},
        {"tc333", "createYavoPmforFnboProcessing", SELECT_BI_ANNUALLY, NEW_CREDIT, false, true, false},
        {"tc334", "createYavoPmforFnboProcessing", SELECT_BI_ANNUALLY, NEW_DEBIT, true, true, false},
        {"tc335", "createYavoPmforFnboProcessing", SELECT_BI_ANNUALLY, NEW_DEBIT, false, true, false},

        {"tc336", "createYavoPm", SELECT_ANNUALLY, NEW_BANK, true, true, false},
        {"tc337", "createYavoPm", SELECT_ANNUALLY, NEW_BANK, false, true, false},
        {"tc338", "createYavoPm", SELECT_ANNUALLY, NEW_CREDIT, true, true, false},
        {"tc339", "createYavoPm", SELECT_ANNUALLY, NEW_CREDIT, false, true, false},
        {"tc340", "createYavoPm", SELECT_ANNUALLY, NEW_DEBIT, true, true, false},
        {"tc341", "createYavoPm", SELECT_ANNUALLY, NEW_DEBIT, false, true, false},

        {"tc342", "createYavoPmforFnboProcessing", SELECT_ANNUALLY, NEW_BANK, true, true, false},
        {"tc343", "createYavoPmforFnboProcessing", SELECT_ANNUALLY, NEW_BANK, false, true, false},
        {"tc344", "createYavoPmforFnboProcessing", SELECT_ANNUALLY, NEW_CREDIT, true, true, false},
        {"tc345", "createYavoPmforFnboProcessing", SELECT_ANNUALLY, NEW_CREDIT, false, true, false},
        {"tc346", "createYavoPmforFnboProcessing", SELECT_ANNUALLY, NEW_DEBIT, true, true, false},
        {"tc347", "createYavoPmforFnboProcessing", SELECT_ANNUALLY, NEW_DEBIT, false, true, false},

        //Non-Yavo PM, with ManagedDepositsV2 enabled

        {"tc348", "createPmWithManagedDepositsV2", SELECT_MONTHLY, NEW_BANK, true, false, true},
        {"tc349", "createPmWithManagedDepositsV2", SELECT_MONTHLY, NEW_BANK, false, false, true},
        {"tc350", "createPmWithManagedDepositsV2", SELECT_MONTHLY, NEW_CREDIT, true, false, true},
        {"tc351", "createPmWithManagedDepositsV2", SELECT_MONTHLY, NEW_CREDIT, false, false, true},
        {"tc352", "createPmWithManagedDepositsV2", SELECT_MONTHLY, NEW_DEBIT, true, false, true},
        {"tc353", "createPmWithManagedDepositsV2", SELECT_MONTHLY, NEW_DEBIT, false, false, true},

        {"tc354", "createPmforFnboProcessingWithManagedDepositsV2", SELECT_MONTHLY, NEW_BANK, true, false, true},
        {"tc355", "createPmforFnboProcessingWithManagedDepositsV2", SELECT_MONTHLY, NEW_BANK, false, false, true},
        {"tc356", "createPmforFnboProcessingWithManagedDepositsV2", SELECT_MONTHLY, NEW_CREDIT, true, false, true},
        {"tc357", "createPmforFnboProcessingWithManagedDepositsV2", SELECT_MONTHLY, NEW_CREDIT, false, false, true},
        {"tc358", "createPmforFnboProcessingWithManagedDepositsV2", SELECT_MONTHLY, NEW_DEBIT, true, false, true},
        {"tc359", "createPmforFnboProcessingWithManagedDepositsV2", SELECT_MONTHLY, NEW_DEBIT, false, false, true},

        {"tc360", "createPmWithManagedDepositsV2", SELECT_QUARTERLY, NEW_BANK, true, false, true},
        {"tc361", "createPmWithManagedDepositsV2", SELECT_QUARTERLY, NEW_BANK, false, false, true},
        {"tc362", "createPmWithManagedDepositsV2", SELECT_QUARTERLY, NEW_CREDIT, true, false, true},
        {"tc363", "createPmWithManagedDepositsV2", SELECT_QUARTERLY, NEW_CREDIT, false, false, true},
        {"tc364", "createPmWithManagedDepositsV2", SELECT_QUARTERLY, NEW_DEBIT, true, false, true},
        {"tc365", "createPmWithManagedDepositsV2", SELECT_QUARTERLY, NEW_DEBIT, false, false, true},

        {"tc366", "createPmforFnboProcessingWithManagedDepositsV2", SELECT_QUARTERLY, NEW_BANK, true, false, true},
        {"tc367", "createPmforFnboProcessingWithManagedDepositsV2", SELECT_QUARTERLY, NEW_BANK, false, false, true},
        {"tc368", "createPmforFnboProcessingWithManagedDepositsV2", SELECT_QUARTERLY, NEW_CREDIT, true, false, true},
        {"tc369", "createPmforFnboProcessingWithManagedDepositsV2", SELECT_QUARTERLY, NEW_CREDIT, false, false, true},
        {"tc370", "createPmforFnboProcessingWithManagedDepositsV2", SELECT_QUARTERLY, NEW_DEBIT, true, false, true},
        {"tc371", "createPmforFnboProcessingWithManagedDepositsV2", SELECT_QUARTERLY, NEW_DEBIT, false, false, true},

        {"tc372", "createPmWithManagedDepositsV2", SELECT_BI_ANNUALLY, NEW_BANK, true, false, true},
        {"tc373", "createPmWithManagedDepositsV2", SELECT_BI_ANNUALLY, NEW_BANK, false, false, true},
        {"tc374", "createPmWithManagedDepositsV2", SELECT_BI_ANNUALLY, NEW_CREDIT, true, false, true},
        {"tc375", "createPmWithManagedDepositsV2", SELECT_BI_ANNUALLY, NEW_CREDIT, false, false, true},
        {"tc376", "createPmWithManagedDepositsV2", SELECT_BI_ANNUALLY, NEW_DEBIT, true, false, true},
        {"tc377", "createPmWithManagedDepositsV2", SELECT_BI_ANNUALLY, NEW_DEBIT, false, false, true},

        {"tc378", "createPmforFnboProcessingWithManagedDepositsV2", SELECT_BI_ANNUALLY, NEW_BANK, true, false, true},
        {"tc379", "createPmforFnboProcessingWithManagedDepositsV2", SELECT_BI_ANNUALLY, NEW_BANK, false, false, true},
        {"tc380", "createPmforFnboProcessingWithManagedDepositsV2", SELECT_BI_ANNUALLY, NEW_CREDIT, true, false, true},
        {"tc381", "createPmforFnboProcessingWithManagedDepositsV2", SELECT_BI_ANNUALLY, NEW_CREDIT, false, false, true},
        {"tc382", "createPmforFnboProcessingWithManagedDepositsV2", SELECT_BI_ANNUALLY, NEW_DEBIT, true, false, true},
        {"tc383", "createPmforFnboProcessingWithManagedDepositsV2", SELECT_BI_ANNUALLY, NEW_DEBIT, false, false, true},

        {"tc384", "createPmWithManagedDepositsV2", SELECT_ANNUALLY, NEW_BANK, true, false, true},
        {"tc385", "createPmWithManagedDepositsV2", SELECT_ANNUALLY, NEW_BANK, false, false, true},
        {"tc386", "createPmWithManagedDepositsV2", SELECT_ANNUALLY, NEW_CREDIT, true, false, true},
        {"tc387", "createPmWithManagedDepositsV2", SELECT_ANNUALLY, NEW_CREDIT, false, false, true},
        {"tc388", "createPmWithManagedDepositsV2", SELECT_ANNUALLY, NEW_DEBIT, true, false, true},
        {"tc399", "createPmWithManagedDepositsV2", SELECT_ANNUALLY, NEW_DEBIT, false, false, true},

        {"tc400", "createPmforFnboProcessingWithManagedDepositsV2", SELECT_ANNUALLY, NEW_BANK, true, false, true},
        {"tc401", "createPmforFnboProcessingWithManagedDepositsV2", SELECT_ANNUALLY, NEW_BANK, false, false, true},
        {"tc402", "createPmforFnboProcessingWithManagedDepositsV2", SELECT_ANNUALLY, NEW_CREDIT, true, false, true},
        {"tc403", "createPmforFnboProcessingWithManagedDepositsV2", SELECT_ANNUALLY, NEW_CREDIT, false, false, true},
        {"tc404", "createPmforFnboProcessingWithManagedDepositsV2", SELECT_ANNUALLY, NEW_DEBIT, true, false, true},
        {"tc405", "createPmforFnboProcessingWithManagedDepositsV2", SELECT_ANNUALLY, NEW_DEBIT, false, false, true},

        //Non-Yavo Pm, Not enabled for ManagedDepositsV2

        {"tc406", "createPm", SELECT_MONTHLY, NEW_BANK, true, false, false},
        {"tc407", "createPm", SELECT_MONTHLY, NEW_BANK, false, false, false},
        {"tc408", "createPm", SELECT_MONTHLY, NEW_CREDIT, true, false, false},
        {"tc409", "createPm", SELECT_MONTHLY, NEW_CREDIT, false, false, false},
        {"tc410", "createPm", SELECT_MONTHLY, NEW_DEBIT, true, false, false},
        {"tc411", "createPm", SELECT_MONTHLY, NEW_DEBIT, false, false, false},

        {"tc412", "createPmforFnboProcessing", SELECT_MONTHLY, NEW_BANK, true, false, false},
        {"tc413", "createPmforFnboProcessing", SELECT_MONTHLY, NEW_BANK, false, false, false},
        {"tc414", "createPmforFnboProcessing", SELECT_MONTHLY, NEW_CREDIT, true, false, false},
        {"tc415", "createPmforFnboProcessing", SELECT_MONTHLY, NEW_CREDIT, false, false, false},
        {"tc416", "createPmforFnboProcessing", SELECT_MONTHLY, NEW_DEBIT, true, false, false},
        {"tc2417", "createPmforFnboProcessing", SELECT_MONTHLY, NEW_DEBIT, false, false, false},

        {"tc418", "createPmforFnboProcessing", SELECT_QUARTERLY, NEW_BANK, true, false, false},
        {"tc419", "createPmforFnboProcessing", SELECT_QUARTERLY, NEW_BANK, true, false, false},
        {"tc425", "createPmforFnboProcessing", SELECT_QUARTERLY, NEW_BANK, false, false, false},
        {"tc426", "createPmforFnboProcessing", SELECT_QUARTERLY, NEW_CREDIT, true, false, false},
        {"tc427", "createPmforFnboProcessing", SELECT_QUARTERLY, NEW_CREDIT, false, false, false},
        {"tc428", "createPmforFnboProcessing", SELECT_QUARTERLY, NEW_DEBIT, true, false, false},
        {"tc429", "createPmforFnboProcessing", SELECT_QUARTERLY, NEW_DEBIT, false, false, false},

        {"tc430", "createPm", SELECT_BI_ANNUALLY, NEW_BANK, true, false, false},
        {"tc431", "createPm", SELECT_BI_ANNUALLY, NEW_BANK, false, false, false},
        {"tc432", "createPm", SELECT_BI_ANNUALLY, NEW_CREDIT, true, false, false},
        {"tc433", "createPm", SELECT_BI_ANNUALLY, NEW_CREDIT, false, false, false},
        {"tc434", "createPm", SELECT_BI_ANNUALLY, NEW_DEBIT, true, false, false},
        {"tc435", "createPm", SELECT_BI_ANNUALLY, NEW_DEBIT, false, false, false},

        {"tc436", "createPmforFnboProcessing", SELECT_BI_ANNUALLY, NEW_BANK, true, false, false},
        {"tc437", "createPmforFnboProcessing", SELECT_BI_ANNUALLY, NEW_BANK, false, false, false},
        {"tc438", "createPmforFnboProcessing", SELECT_BI_ANNUALLY, NEW_CREDIT, true, false, false},
        {"tc439", "createPmforFnboProcessing", SELECT_BI_ANNUALLY, NEW_CREDIT, false, false, false},
        {"tc440", "createPmforFnboProcessing", SELECT_BI_ANNUALLY, NEW_DEBIT, true, false, false},
        {"tc441", "createPmforFnboProcessing", SELECT_BI_ANNUALLY, NEW_DEBIT, false, false, false},

        {"tc442", "createPm", SELECT_ANNUALLY, NEW_BANK, true, false, false},
        {"tc443", "createPm", SELECT_ANNUALLY, NEW_BANK, false, false, false},
        {"tc444", "createPm", SELECT_ANNUALLY, NEW_CREDIT, true, false, false},
        {"tc445", "createPm", SELECT_ANNUALLY, NEW_CREDIT, false, false, false},
        {"tc446", "createPm", SELECT_ANNUALLY, NEW_DEBIT, true, false, false},
        {"tc447", "createPm", SELECT_ANNUALLY, NEW_DEBIT, false, false, false},

        {"tc448", "createPmforFnboProcessing", SELECT_ANNUALLY, NEW_BANK, true, false, false},
        {"tc449", "createPmforFnboProcessing", SELECT_ANNUALLY, NEW_BANK, false, false, false},
        {"tc450", "createPmforFnboProcessing", SELECT_ANNUALLY, NEW_CREDIT, true, false, false},
        {"tc451", "createPmforFnboProcessing", SELECT_ANNUALLY, NEW_CREDIT, false, false, false},
        {"tc452", "createPmforFnboProcessing", SELECT_ANNUALLY, NEW_DEBIT, true, false, false},
        {"tc453", "createPmforFnboProcessing", SELECT_ANNUALLY, NEW_DEBIT, false, false, false},

    };
  }

  /**
   * Provides data required to run various kinds of test combinations for deposit service.
   *
   * @return data
   */
  @DataProvider(name = "otpDataDepositService")
  public Object[][] otpDataDepositService() {

    return new Object[][]{
        //String testVariationNo, String testSetup, String paymentType,boolean useResidentList,
        //boolean expressPay, String processor, boolean multipleBankAccounts, boolean batchByBankAccount, boolean creditCardExpressPayout, boolean missingPSLid

        //Batch by Property and Express Pay = False
        {"otpPmTc1", "createYavoPmWithManagedDepositsV2", NEW_BANK, false, false, "Profitstars", false, false, false, false},
        {"otpPmTc2", "createYavoPmforFnboProcessingWithManagedDepositsV2", NEW_BANK, false, false, "FNBO", false, false, false, false},
        {"otpPmTc3", "createYavoPmforFnboProcessingWithManagedDepositsV2", NEW_CREDIT, false, false, "FNBOCC", false, false, false, false},
        {"otpPmTc4", "createYavoPmMultipleBankAccounts", NEW_BANK, false, false, "Profitstars", true, false, false, false},
        {"otpPmTc5", "createYavoFnboPmMultipleBankAccounts", NEW_BANK, false, false, "FNBO", true, false, false, false},
        {"otpPmTc6", "createYavoFnboPmMultipleBankAccounts", NEW_CREDIT, false, false, "FNBOCC", true, false, false, false},

        //Batch by Bank Account and Express Pay = False
        {"otpPmTc7", "createYavoPmWithManagedDepositsV2BatchByBankAccount", NEW_BANK, false, false, "Profitstars", false, true, false, false},
        {"otpPmTc8", "createYavoPmWithManagedDepositsV2BatchByBankAccountFnbo", NEW_BANK, false, false, "FNBO", false, false, false, false},
        {"otpPmTc9", "createYavoPmWithManagedDepositsV2BatchByBankAccountFnbo", NEW_CREDIT, false, false, "FNBOCC", false, true, false, false},
        {"otpPmTc10", "createYavoPmWithManagedDepositsV2AndMultipleBankAccountsBatchByBankAccount", NEW_BANK, true, false, "Profitstars", true, true, false, false},
        {"otpPmTc11", "createYavoPmWithManagedDepositsV2AndMultipleBankAccountsBatchByBankAccountFnbo", NEW_BANK, false, false, "FNBO", true, true, false, false},
        {"otpPmTc12", "createYavoPmWithManagedDepositsV2AndMultipleBankAccountsBatchByBankAccountFnbo", NEW_CREDIT, false, false, "FNBOCC", true, true, false, false},

        //Batch by Property and Express Pay = true
        {"otpPmTc13", "createYavoPmWithManagedDepositsV2", NEW_CREDIT, false, true, "FNBO", false, false, false, false},
        {"otpPmTc14", "createYavoPmforFnboProcessingWithManagedDepositsV2", NEW_BANK, false, true, "FNBO", false, false, false, false},
        {"otpPmTc15", "createYavoPmforFnboProcessingWithManagedDepositsV2", NEW_CREDIT, false, true, "FNBOCC", false, false, false, false},
        {"otpPmTc16", "createYavoFnboPmMultipleBankAccounts", NEW_BANK, false, true, "FNBO", true, false, false, false},
        {"otpPmTc17", "createYavoFnboPmMultipleBankAccounts", NEW_CREDIT, false, true, "FNBOCC", true, false, false, false},

          //PM setting credit_card_express_payout = true
        {"otpPmTc18", "createYavoPmWithManagedDepositsV2AndCcExpressOn", NEW_CREDIT, false, false, "FNBOCC", false, false, true, false},
        {"otpPmTc19", "createYavoPmForFnboProcessingWithManagedDepositsV2AndExpressCCOn", NEW_BANK, false, true, "FNBO", false, false, true, false},
        {"otpPmTc20", "createYavoPmForFnboProcessingWithManagedDepositsV2AndExpressCCOn", NEW_BANK, false, false, "FNBO", false, false, false, false},
        {"otpPmTc21", "createYavoPmForFnboProcessingWithManagedDepositsV2AndExpressCCOn", NEW_CREDIT, false, false, "FNBOCC", false, false, true, false},
        {"otpPmTc22", "createYavoPmWithManagedDepositsV2AndCcExpressOn", NEW_BANK, false, false, "Profitstars", false, false, true, false},

        //PM with FNBO but set to have same day processing
        {"otpPmTc23", "createYavoPmForFnboWithManagedDepositsV2WithSameDayProcessingCcExpressOn", NEW_BANK, false, false, "FNBO", false, false, true, false},
        {"otpPmTc24", "createYavoPmForFnboWithManagedDepositsV2WithSameDayProcessingCcExpressOn", NEW_CREDIT, false, false, "FNBOCC", false, false, true, false},
        {"otpPmTc25", "createYavoPmForFnboWithManagedDepositsV2WithSameDayProcessing", NEW_CREDIT, false, true, "FNBOCC", false, false, false, false},
        {"otpPmTc26", "createYavoPmForFnboWithManagedDepositsV2WithSameDayProcessing", NEW_CREDIT, false, false, "FNBOCC", false, false, false, false},

        //PM set with PS but missing LID should be processed with FNBO
        {"otpPmTc27", "createYavoPmMultipleBankAccounts", NEW_BANK, false, false, "FNBO", true, false, false, true},

    };
  }

  /**
   * Provides data required to run tests for old world cc transaction.
   *
   * @return data
   */
  @DataProvider(name = "oldWorldCcTransactionData")
  public Object[][] oldWorldCcTransactionData() {

    return new Object[][]{
        //String testVariationNo, String testSetup, String paymentType,boolean useResidentList,
        //boolean expressPay, String processor

        {"otpPmTc3", "createYavoPmforFnboProcessing", NEW_CREDIT, false, false, "FNBOCC"},
    };
  }

  /**
   * Provides data required to run various kinds of test combinations for integration service.
   *
   * @return data
   */
  @DataProvider(name = "otpDataDepositServiceMixedPaymentTypes")
  public Object[][] otpDataDepositServiceMixedPaymentTypes() {

    return new Object[][]{
//    String testVariationNo, String testSetup, boolean useResidentList,
//    boolean expressPay, String processor, boolean multipleBankAccounts

        {"otpPmTc1", "createYavoPmforFnboProcessingWithManagedDepositsV2", false, false, "FNBO",
            false},
        {"otpPmTc2", "createYavoPmforFnboProcessingWithManagedDepositsV2", false, false, "FNBOCC",
        false},
        {"otpPmTc2", "createYavoPmWithManagedDepositsV2", false, false, "Profitstars",
            false},

        {"otpPmTc4", "createYavoFnboPmMultipleBankAccounts", false, false, "FNBO", true},
        {"otpPmTc5", "createYavoFnboPmMultipleBankAccounts", false, false, "FNBOCC", true},
        {"otpPmTc5", "createYavoPmMultipleBankAccounts", false, false, "Profitstars", true},
    };
  }

  @DataProvider(name = "otpDataIntegrationService")
  public Object[][] otpDataIntegrationService() {

    return new Object[][]{
        //String testVariationNo, String testSetup, String paymentType, boolean useResidentList,
        //boolean expressPay, String processor

        {"otpPmTc1", "createYavoPmWithManagedDepositsV2", NEW_BANK, false, false, "Profitstars"},
        {"otpPmTc2", "createYavoPmforFnboProcessingWithManagedDepositsV2", NEW_BANK, false, false, "FNBO"},
        {"otpPmTc3", "createYavoPmforFnboProcessingWithManagedDepositsV2", NEW_CREDIT, false, false, "FNBOCC"},
        {"otpPmTc4", "createYavoPmMultipleBankAccounts", NEW_BANK, false, false, "Profitstars"},
        {"otpPmTc5", "createYavoFnboPmMultipleBankAccounts", NEW_BANK, false, false, "FNBO"},
        {"otpPmTc6", "createYavoFnboPmMultipleBankAccounts", NEW_CREDIT, false, false, "FNBOCC"},
    };
  }

  @DataProvider(name = "otpDataIntegrationServiceWithPropertyLock")
  public Object[][] otpDataIntegrationServiceWithPropertyLock() {

    return new Object[][]{
//        String testVariationNo, String testSetup, String paymentType,String processor, boolean isPropertyLockScheduled
        {"propLockTc1", "createYavoPmWithManagedDepositsV2", NEW_BANK, "Profitstars", true},
        {"propLockTc2", "createYavoPmWithManagedDepositsV2", NEW_BANK, "Profitstars", false},
        {"propLockTc3", "createYavoPmforFnboProcessingWithManagedDepositsV2", NEW_CREDIT, "FNBOCC", true},
        {"propLockTc4", "createYavoPmforFnboProcessingWithManagedDepositsV2", NEW_CREDIT, "FNBOCC", false},
    };
  }

  @DataProvider(name = "otpDataIntegrationServiceWithPropertyLockBatchByBankAccount")
  public Object[][] otpDataIntegrationServiceWithPropertyLockBatchByBankAccount() {

    return new Object[][]{
//        String testVariationNo, String testSetup, String paymentType, String processor, boolean isPropertyLockScheduled
        {"propLockTc5", "createYavoPmWithManagedDepositsV2BatchByBankAccount", NEW_BANK, "Profitstars", true},
        {"propLockTc6", "createYavoPmWithManagedDepositsV2BatchByBankAccount", NEW_BANK, "Profitstars", false},
        {"propLockTc7", "createYavoPmWithManagedDepositsV2BatchByBankAccountFnbo", NEW_CREDIT, "FNBOCC", true},
        {"propLockTc8", "createYavoPmWithManagedDepositsV2BatchByBankAccountFnbo", NEW_CREDIT, "FNBOCC", false},
    };
  }

  /**
   * Provides data required to run various kinds of test combinations for refund transaction
   * integration service.
   *
   * @return data
   */
  @DataProvider(name = "refundTransIntegrationService")
  public Object[][] otpDataRefundIntegrationService() {

    return new Object[][]{
//      String testVariationNo, String testCaseSetup,
//      String refundType, String paymentType, String processor

        {"otpPmTc1", "createYavoPmWithManagedDepositsV2", REFUND_TYPE_FULL, NEW_BANK, "Profitstars"},
        {"otpPmTc2", "createYavoPmforFnboProcessingWithManagedDepositsV2", REFUND_TYPE_FULL, NEW_BANK, "FNBO"},
        {"otpPmTc3", "createYavoPmforFnboProcessingWithManagedDepositsV2", REFUND_TYPE_FULL, NEW_CREDIT, "FNBO"},
        {"otpPmTc4", "createYavoPmforFnboProcessingWithManagedDepositsV2", REFUND_TYPE_FULL, NEW_DEBIT, "FNBO"},

        {"otpPmTc6", "createYavoPmforFnboProcessingWithManagedDepositsV2", REFUND_TYPE_PARTIAL_MAX, NEW_BANK, "FNBO"},
        {"otpPmTc7", "createYavoPmforFnboProcessingWithManagedDepositsV2", REFUND_TYPE_PARTIAL_MAX, NEW_CREDIT, "FNBO"},
        {"otpPmTc8", "createYavoPmforFnboProcessingWithManagedDepositsV2", REFUND_TYPE_PARTIAL_MAX, NEW_DEBIT, "FNBO"},

        {"otpPmTc9", "createYavoPmforFnboProcessingWithManagedDepositsV2", REFUND_TYPE_PARTIAL_PARTIAL, NEW_BANK, "FNBO"},
        {"otpPmTc10", "createYavoPmforFnboProcessingWithManagedDepositsV2", REFUND_TYPE_PARTIAL_PARTIAL, NEW_CREDIT, "FNBO"},
        {"otpPmTc11", "createYavoPmforFnboProcessingWithManagedDepositsV2", REFUND_TYPE_PARTIAL_PARTIAL, NEW_DEBIT, "FNBO"},

        {"otpPmTc12", "createYavoPmforFnboProcessingWithManagedDepositsV2", REFUND_TYPE_PARTIAL_SINGLE_FIELD, NEW_BANK, "FNBO"},
        {"otpPmTc13", "createYavoPmforFnboProcessingWithManagedDepositsV2", REFUND_TYPE_PARTIAL_SINGLE_FIELD, NEW_CREDIT, "FNBO"},
        {"otpPmTc14", "createYavoPmforFnboProcessingWithManagedDepositsV2", REFUND_TYPE_PARTIAL_SINGLE_FIELD, NEW_DEBIT, "FNBO"},
    };
  }

  /**
   * Provides data required to run various kinds of test combinations for eligible reversals.
   *
   * @return data
   */
  @DataProvider(name = "reversalTransIntegrationDataAdmin")
  public Object[][] dataReversalIntegrationTransAdmin() {
    // String testVariationNo, String testCaseSetup,
    //      String reversalType, String paymentType, String processor
    return new Object[][]{
        {"tc1", "createYavoPmWithManagedDepositsV2", ADMIN_REFUND_MAX, NEW_BANK, "PROFITSTARS"},
        {"tc2", "createYavoPmWithManagedDepositsV2", ADMIN_REFUND_PARTIAL, NEW_BANK, "PROFITSTARS"},
        {"tc3", "createYavoPmWithManagedDepositsV2", ADMIN_REFUND_OVER_MAX, NEW_BANK, "PROFITSTARS"},

        {"tc4", "createYavoPmforFnboProcessingWithManagedDepositsV2", ADMIN_REFUND_MAX, NEW_BANK, "FNBO"},
        {"tc5", "createYavoPmforFnboProcessingWithManagedDepositsV2", ADMIN_REFUND_MAX, NEW_CREDIT, "FNBOCC"},
        {"tc6", "createYavoPmforFnboProcessingWithManagedDepositsV2", ADMIN_REFUND_MAX, NEW_DEBIT, "FNBO"},

        {"tc7", "createYavoPmforFnboProcessingWithManagedDepositsV2", ADMIN_REFUND_PARTIAL, NEW_BANK, "FNBO"},
        {"tc8", "createYavoPmforFnboProcessingWithManagedDepositsV2", ADMIN_REFUND_PARTIAL, NEW_CREDIT,
            "FNBOCC"},
        {"tc9", "createYavoPmforFnboProcessingWithManagedDepositsV2", ADMIN_REFUND_PARTIAL, NEW_DEBIT, "FNBOCC"},


    };
  }

  /**
   * Provides data required to run various kinds of test combinations for eligible reversals.
   *
   * @return data
   */
  @DataProvider(name = "voidTransactionWebApp")
  public Object[][] dataVoidTransWebApp() {
//    String testVariationNo, String testCaseSetup, String voidLocation,
//        String paymentType, String processor, boolean newWorld
    return new Object[][]{
        //Old world flow
        {"tc1", "createYavoPm", "pm", NEW_BANK, "Profitstars", false},
        {"tc2", "createYavoPm", "res", NEW_BANK,"Profitstars", false},
        {"tc3", "createYavoPm", "admin",NEW_BANK, "Profitstars", false},
        {"tc4", "createYavoPmforFnboProcessing", "pm", NEW_BANK, "FNBO", false},
        {"tc5", "createYavoPmforFnboProcessing", "res", NEW_BANK,"FNBO", false},
        {"tc6", "createYavoPmforFnboProcessing", "admin", NEW_BANK,"FNBO", false},
        {"tc7", "createYavoPmforFnboProcessing", "admin", NEW_CREDIT, "FNBO",false},

        //new World flow
        {"tc8", "createYavoPmWithManagedDepositsV2", "pm", NEW_BANK, "Profitstars", true},
        {"tc9", "createYavoPmWithManagedDepositsV2", "res", NEW_BANK,"Profitstars", true},
        {"tc10", "createYavoPmWithManagedDepositsV2", "admin",NEW_BANK, "Profitstars", true},
        {"tc11", "createYavoPmforFnboProcessingWithManagedDepositsV2", "pm", NEW_BANK, "FNBO", true},
        {"tc12", "createYavoPmforFnboProcessingWithManagedDepositsV2", "res", NEW_BANK,"FNBO", true},
        {"tc13", "createYavoPmforFnboProcessingWithManagedDepositsV2", "admin", NEW_BANK,"FNBO", true},
        {"tc14", "createYavoPmforFnboProcessingWithManagedDepositsV2", "admin", NEW_CREDIT, "FNBO",true},
    };
  }

  /**
   * Provides data required to run various kinds of test combinations for returned payments.
   *
   * @return data
   */
  @DataProvider
  public Object[][] providerForReturnedBatchItemsProfitStarsTest() {
    // String testVariationNo, String testCaseSetup, boolean multipleBankAccounts
    return new Object[][]{
        {"tc1", "createYavoPmWithManagedDepositsV2", false},
        {"tc2", "createYavoPmMultipleBankAccounts", true},
    };
  }

  /**
   * Provides data required to run various kinds of test combinations for returned payments.
   *
   * @return data
   */
  @DataProvider
  public Object[][] cancelEmptyBatches() {
    // String testVariationNo, String testCaseSetup, String processor, boolean emptyBatch, boolean emptyExternalTransaction, boolean deleteError
    return new Object[][]{
        {"tc1", "createYavoPmOnManagedDepositsV2FakeYardiAccount", "Profitstars",true, false, false},
        {"tc2", "createYavoPmOnManagedDepositsV2FakeYardiAccountFnbo", "FNBO",true, false, false},
        {"tc3", "createYavoPmMultipleBankAccounts", "Profitstars", false, false, false},
        {"tc4", "createYavoFnboPmMultipleBankAccounts", "FNBO", false, false, false},
        {"tc5", "createYavoPmMultipleBankAccounts", "Profitstars", false, true, false},
        {"tc6", "createYavoFnboPmMultipleBankAccounts", "FNBO", false, true, false},
        {"tc7", "createYavoPmMultipleBankAccountsStubService", "Profitstars", true, true, true},
    };
  }

  /**
   * Provides data required to run the various kinds of test combinations for voided transactions
   * @return
   */
  @DataProvider
  public Object[][] voidTransactionAfterBatchClosed() {

    //String testVariationNo, String testSetup, String processor, String paymentType, boolean multipleTransactions
    return new Object[][] {
        {"tc1", "createYavoPmWithManagedDepositsV2", "Profitstars", NEW_BANK, false,},
        {"tc2", "createYavoPmforFnboProcessingWithManagedDepositsV2", "FNBO", NEW_BANK, false},
        {"tc3", "createYavoPmforFnboProcessingWithManagedDepositsV2", "FNBO", NEW_CREDIT, false},

        {"tc4", "createYavoPmWithManagedDepositsV2", "Profitstars", NEW_BANK, true,},
        {"tc5", "createYavoPmforFnboProcessingWithManagedDepositsV2", "FNBO", NEW_BANK, true},
        {"tc6", "createYavoPmforFnboProcessingWithManagedDepositsV2", "FNBO", NEW_CREDIT, true},

    };
  }

  /**
   * Provides data required to run various kinds of test combinations for PM.
   *
   * @return data
   */
  @DataProvider(name = "dataInsufficientFunds")
  public Object[][] dataInsufficientFunds() {

    return new Object[][]{
        //Test variation no., TestCase, PaymentType, UseResidentsList, ExpressPay, Processor, NewWorld

        //newWorld
        {"otpPmTc1", "createYavoPmWithManagedDepositsV2", NEW_BANK, false, false, "Profitstars", true},
        {"otpPmTc2", "createYavoPmforFnboProcessingWithManagedDepositsV2", NEW_BANK, false, false, "FNBO", true},

        //oldWorld
        {"otpPmTc1", "createYavoPm", NEW_BANK, false, false, "Profitstars", false},
        {"otpPmTc2", "createYavoPmforFnboProcessing", NEW_BANK, false, false, "FNBO", false},
    };
  }

  /**
   * Provides data required to run various kinds of test combinations for returned payments.
   *
   * @return data
   */
  @DataProvider
  public Object[][] addNonIntegratedTransactionsToExceptionDeposit() {
    // String testVariationNo, String testCaseSetup, String processor, boolean emptyBatch, boolean emptyExternalTransaction, boolean deleteError
    return new Object[][]{
        {"tc1", "createYavoPmOnManagedDepositsV2FakeYardiAccountBatchByBankAccount", "Profitstars"},
    };
  }
}
