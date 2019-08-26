package com.paylease.app.qa.testbase.dataproviders;

import static com.paylease.app.qa.framework.pages.admin.TransactionDetailPage.ADMIN_REFUND_MAX;
import static com.paylease.app.qa.framework.pages.admin.TransactionDetailPage.ADMIN_REFUND_OVER_MAX;
import static com.paylease.app.qa.framework.pages.admin.TransactionDetailPage.ADMIN_REFUND_PARTIAL;
import static com.paylease.app.qa.framework.pages.admin.TransactionDetailPage.REFUND_ENTITY_PM;
import static com.paylease.app.qa.framework.pages.admin.TransactionDetailPage.REFUND_ENTITY_RESIDENT;
import static com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage.NEW_BANK;
import static com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage.NEW_CREDIT;
import static com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage.NEW_DEBIT;
import static com.paylease.app.qa.framework.pages.pm.PmPaymentHistoryPage.REFUND_TYPE_FULL;
import static com.paylease.app.qa.framework.pages.pm.PmPaymentHistoryPage.REFUND_TYPE_PARTIAL_MAX;
import static com.paylease.app.qa.framework.pages.pm.PmPaymentHistoryPage.REFUND_TYPE_PARTIAL_OVER_LIMIT;
import static com.paylease.app.qa.framework.pages.pm.PmPaymentHistoryPage.REFUND_TYPE_PARTIAL_PARTIAL;

import org.testng.annotations.DataProvider;

public class RefundTestDataProvider {

  public static final String REFUND_INELIGIBLE_NOT_PAID_OUT = "Not Paid Out";
  public static final String REFUND_INELIGIBLE_REFUNDED = "Refunded";
  public static final String REFUND_INELIGIBLE_LESS_THAN_MIN_DAYS = "Less than minimum number of"
      + " days";
  public static final String REFUND_INELIGIBLE_PAST_REFUND_WINDOW = "Past refund window";
  public static final String REFUND_INELIGIBLE_OVER_REFUND_LIMIT = "Transaction is over the limit"
      + " for refunds.";
  private static final String REFUND_TESTSETUP_01 = "testSetupRefund_01";
  private static final String REFUND_TESTSETUP_02 = "testSetupRefund_02";
  private static final String REFUND_TESTSETUP_03 = "testSetupRefund_03";
  private static final String REFUND_TESTSETUP_04 = "testSetupRefund_04";
  private static final String REFUND_TESTSETUP_05 = "testSetupRefund_05";
  private static final String REFUND_TESTSETUP_06 = "testSetupRefund_06";

  /**
   * Provides data required to run various kinds of test combinations for testing the refund link.
   *
   * @return data
   */
  @DataProvider(name = "refundLinkData", parallel = true)
  public Object[][] dataRefundLink() {

    return new Object[][]{
        //testVariationNo, testCaseSetup, isMultiPaymentFields, pmHasDiffBankAccts, isRefundEnabled,
        // isRefundEligible,

        {"tc01", REFUND_TESTSETUP_04, false, false, false, true},
        {"tc02", REFUND_TESTSETUP_06, true, true, false, true},
        {"tc03", REFUND_TESTSETUP_01, false, false, true, true},
        {"tc04", REFUND_TESTSETUP_01, false, false, true, false},
        {"tc05", REFUND_TESTSETUP_04, false, false, false, false},
        {"tc06", REFUND_TESTSETUP_05, true, false, false, false},
        {"tc07", REFUND_TESTSETUP_03, true, true, true, false},
        {"tc08", REFUND_TESTSETUP_06, true, true, false, false},
        {"tc09", REFUND_TESTSETUP_03, true, true, true, true},
        {"tc10", REFUND_TESTSETUP_02, true, false, true, true},
        {"tc11", REFUND_TESTSETUP_02, true, false, true, false},
        {"tc12", REFUND_TESTSETUP_05, true, false, false, true},
    };
  }

  /**
   * Provides data required to run various kinds of test combinations for ineligible refunds.
   *
   * @return data
   */
  @DataProvider(name = "refundIneligiblelData", parallel = true)
  public Object[][] dataIneligibleTrans() {
    return new Object[][]{
        //testVariationNo, testCaseSetup, isMultiPaymentFields, pmHasDiffBankAccts, ineligibleReason

        {"tc13", REFUND_TESTSETUP_01, false, false, REFUND_INELIGIBLE_LESS_THAN_MIN_DAYS},
        {"tc14", REFUND_TESTSETUP_03, true, true, REFUND_INELIGIBLE_LESS_THAN_MIN_DAYS},
        {"tc15", REFUND_TESTSETUP_02, true, false, REFUND_INELIGIBLE_LESS_THAN_MIN_DAYS},

        {"tc16", REFUND_TESTSETUP_01, false, false, REFUND_INELIGIBLE_NOT_PAID_OUT},
        {"tc17", REFUND_TESTSETUP_03, true, true, REFUND_INELIGIBLE_NOT_PAID_OUT},
        {"tc18", REFUND_TESTSETUP_02, true, false, REFUND_INELIGIBLE_NOT_PAID_OUT},

        {"tc19", REFUND_TESTSETUP_01, false, false, REFUND_INELIGIBLE_PAST_REFUND_WINDOW},
        {"tc20", REFUND_TESTSETUP_03, true, true, REFUND_INELIGIBLE_PAST_REFUND_WINDOW},
        {"tc21", REFUND_TESTSETUP_02, true, false, REFUND_INELIGIBLE_PAST_REFUND_WINDOW},

        {"tc22", REFUND_TESTSETUP_01, false, false, REFUND_INELIGIBLE_REFUNDED},
        {"tc23", REFUND_TESTSETUP_03, true, true, REFUND_INELIGIBLE_REFUNDED},
        {"tc24", REFUND_TESTSETUP_02, true, false, REFUND_INELIGIBLE_REFUNDED},

        {"tc25", REFUND_TESTSETUP_01, false, false, REFUND_INELIGIBLE_OVER_REFUND_LIMIT},
        {"tc26", REFUND_TESTSETUP_03, true, true, REFUND_INELIGIBLE_OVER_REFUND_LIMIT},
        {"tc27", REFUND_TESTSETUP_02, true, false, REFUND_INELIGIBLE_OVER_REFUND_LIMIT},

    };
  }

  /**
   * Provides data required to run various kinds of test combinations for eligible refunds.
   *
   * @return data
   */
  @DataProvider(name = "refundTransDataPm", parallel = true)
  public Object[][] dataRefundTrans() {
    // testVariationNo, testCaseSetup, isMultiPaymentFields, pmHasDiffBankAccounts, refundType,
    // cancelInitiateRefund, cancelContinueRefund
    return new Object[][]{
        {"tc28", REFUND_TESTSETUP_01, false, false, REFUND_TYPE_FULL, true, false},
        {"tc29", REFUND_TESTSETUP_01, false, false, REFUND_TYPE_FULL, false, true},
        {"tc30", REFUND_TESTSETUP_01, false, false, REFUND_TYPE_FULL, false, false},

        {"tc31", REFUND_TESTSETUP_03, true, true, REFUND_TYPE_FULL, true, false},
        {"tc32", REFUND_TESTSETUP_03, true, true, REFUND_TYPE_FULL, false, true},
        {"tc33", REFUND_TESTSETUP_03, true, true, REFUND_TYPE_FULL, false, false},

        {"tc34", REFUND_TESTSETUP_02, true, false, REFUND_TYPE_FULL, true, false},
        {"tc35", REFUND_TESTSETUP_02, true, false, REFUND_TYPE_FULL, false, true},
        {"tc36", REFUND_TESTSETUP_02, true, false, REFUND_TYPE_FULL, false, false},

        {"tc37", REFUND_TESTSETUP_01, false, false, REFUND_TYPE_PARTIAL_PARTIAL, true, false},
        {"tc38", REFUND_TESTSETUP_01, false, false, REFUND_TYPE_PARTIAL_PARTIAL, false, true},
        {"tc39", REFUND_TESTSETUP_01, false, false, REFUND_TYPE_PARTIAL_PARTIAL, false, false},

        {"tc40", REFUND_TESTSETUP_03, true, true, REFUND_TYPE_PARTIAL_PARTIAL, true, false},
        {"tc41", REFUND_TESTSETUP_03, true, true, REFUND_TYPE_PARTIAL_PARTIAL, false, true},
        {"tc42", REFUND_TESTSETUP_03, true, true, REFUND_TYPE_PARTIAL_PARTIAL, false, false},

        {"tc43", REFUND_TESTSETUP_02, true, false, REFUND_TYPE_PARTIAL_PARTIAL, true, false},
        {"tc44", REFUND_TESTSETUP_02, true, false, REFUND_TYPE_PARTIAL_PARTIAL, false, true},
        {"tc45", REFUND_TESTSETUP_02, true, false, REFUND_TYPE_PARTIAL_PARTIAL, false, false},

        {"tc46", REFUND_TESTSETUP_01, false, false, REFUND_TYPE_PARTIAL_MAX, true, false},
        {"tc47", REFUND_TESTSETUP_01, false, false, REFUND_TYPE_PARTIAL_MAX, false, true},
        {"tc48", REFUND_TESTSETUP_01, false, false, REFUND_TYPE_PARTIAL_MAX, false, false},

        {"tc49", REFUND_TESTSETUP_03, true, true, REFUND_TYPE_PARTIAL_MAX, true, false},
        {"tc50", REFUND_TESTSETUP_03, true, true, REFUND_TYPE_PARTIAL_MAX, false, true},
        {"tc51", REFUND_TESTSETUP_03, true, true, REFUND_TYPE_PARTIAL_MAX, false, false},

        {"tc52", REFUND_TESTSETUP_02, true, false, REFUND_TYPE_PARTIAL_MAX, true, false},
        {"tc53", REFUND_TESTSETUP_02, true, false, REFUND_TYPE_PARTIAL_MAX, false, true},
        {"tc54", REFUND_TESTSETUP_02, true, false, REFUND_TYPE_PARTIAL_MAX, false, false},

        {"tc55", REFUND_TESTSETUP_01, false, false, REFUND_TYPE_PARTIAL_OVER_LIMIT, true, false},
        {"tc56", REFUND_TESTSETUP_01, false, false, REFUND_TYPE_PARTIAL_OVER_LIMIT, false, true},
        {"tc57", REFUND_TESTSETUP_01, false, false, REFUND_TYPE_PARTIAL_OVER_LIMIT, false, false},

        {"tc58", REFUND_TESTSETUP_03, true, true, REFUND_TYPE_PARTIAL_OVER_LIMIT, true, false},
        {"tc59", REFUND_TESTSETUP_03, true, true, REFUND_TYPE_PARTIAL_OVER_LIMIT, false, true},
        {"tc60", REFUND_TESTSETUP_03, true, true, REFUND_TYPE_PARTIAL_OVER_LIMIT, false, false},

        {"tc61", REFUND_TESTSETUP_02, true, false, REFUND_TYPE_PARTIAL_OVER_LIMIT, true, false},
        {"tc62", REFUND_TESTSETUP_02, true, false, REFUND_TYPE_PARTIAL_OVER_LIMIT, false, true},
        {"tc63", REFUND_TESTSETUP_02, true, false, REFUND_TYPE_PARTIAL_OVER_LIMIT, false, false},
    };
  }

  /**
   * Provides data required to run various kinds of test combinations for eligible refunds.
   *
   * @return data
   */
  @DataProvider(name = "refundTransDataAdmin", parallel = true)
  public Object[][] dataRefundTransAdmin() {
    // testVariationNo, testCaseSetup, isMultiPaymentFields, pmHasDiffBankAccounts, refundType, paymentType, refundEntity, isPaidOut, transactionType
    return new Object[][]{
        {"tc64", REFUND_TESTSETUP_01, false, false, ADMIN_REFUND_MAX, NEW_BANK, REFUND_ENTITY_RESIDENT, false},
        {"tc65", REFUND_TESTSETUP_01, false, false, ADMIN_REFUND_MAX, NEW_BANK, REFUND_ENTITY_PM, false},
        {"tc66", REFUND_TESTSETUP_01, false, false, ADMIN_REFUND_MAX, NEW_CREDIT, REFUND_ENTITY_PM, false},
        {"tc67", REFUND_TESTSETUP_01, false, false, ADMIN_REFUND_MAX, NEW_DEBIT, REFUND_ENTITY_PM, false},

        {"tc68", REFUND_TESTSETUP_03, true, true, ADMIN_REFUND_MAX, NEW_BANK, REFUND_ENTITY_RESIDENT, false},
        {"tc69", REFUND_TESTSETUP_03, true, true, ADMIN_REFUND_MAX, NEW_BANK, REFUND_ENTITY_PM, false},
        {"tc70", REFUND_TESTSETUP_03, true, true, ADMIN_REFUND_MAX, NEW_CREDIT, REFUND_ENTITY_PM, false},
        {"tc71", REFUND_TESTSETUP_03, true, true, ADMIN_REFUND_MAX, NEW_DEBIT, REFUND_ENTITY_PM, false},

        {"tc72", REFUND_TESTSETUP_02, true, false, ADMIN_REFUND_MAX, NEW_BANK, REFUND_ENTITY_RESIDENT, false},
        {"tc73", REFUND_TESTSETUP_02, true, false, ADMIN_REFUND_MAX, NEW_BANK, REFUND_ENTITY_PM, false},
        {"tc74", REFUND_TESTSETUP_02, true, false, ADMIN_REFUND_MAX, NEW_CREDIT, REFUND_ENTITY_PM, false},
        {"tc75", REFUND_TESTSETUP_02, true, false, ADMIN_REFUND_MAX, NEW_DEBIT, REFUND_ENTITY_PM, false},

        {"tc76", REFUND_TESTSETUP_01, false, false, ADMIN_REFUND_PARTIAL, NEW_BANK, REFUND_ENTITY_RESIDENT, false},
        {"tc77", REFUND_TESTSETUP_01, false, false, ADMIN_REFUND_PARTIAL, NEW_BANK, REFUND_ENTITY_PM, false},
        {"tc78", REFUND_TESTSETUP_01, false, false, ADMIN_REFUND_PARTIAL, NEW_CREDIT, REFUND_ENTITY_PM, false},
        {"tc79", REFUND_TESTSETUP_01, false, false, ADMIN_REFUND_PARTIAL, NEW_DEBIT, REFUND_ENTITY_PM, false},

        {"tc80", REFUND_TESTSETUP_03, true, true, ADMIN_REFUND_PARTIAL, NEW_BANK, REFUND_ENTITY_RESIDENT, false},
        {"tc81", REFUND_TESTSETUP_03, true, true, ADMIN_REFUND_PARTIAL, NEW_BANK, REFUND_ENTITY_PM, false},
        {"tc82", REFUND_TESTSETUP_03, true, true, ADMIN_REFUND_PARTIAL, NEW_CREDIT, REFUND_ENTITY_PM, false},
        {"tc83", REFUND_TESTSETUP_03, true, true, ADMIN_REFUND_PARTIAL, NEW_DEBIT, REFUND_ENTITY_PM, false},

        {"tc84", REFUND_TESTSETUP_02, true, false, ADMIN_REFUND_PARTIAL, NEW_BANK, REFUND_ENTITY_RESIDENT, false},
        {"tc85", REFUND_TESTSETUP_02, true, false, ADMIN_REFUND_PARTIAL, NEW_BANK, REFUND_ENTITY_PM, false},
        {"tc86", REFUND_TESTSETUP_02, true, false, ADMIN_REFUND_PARTIAL, NEW_CREDIT, REFUND_ENTITY_PM, false},
        {"tc87", REFUND_TESTSETUP_02, true, false, ADMIN_REFUND_PARTIAL, NEW_DEBIT, REFUND_ENTITY_PM, false},

        {"tc88", REFUND_TESTSETUP_01, false, false, ADMIN_REFUND_OVER_MAX, NEW_BANK, REFUND_ENTITY_RESIDENT, false},
        {"tc89", REFUND_TESTSETUP_01, false, false, ADMIN_REFUND_OVER_MAX, NEW_BANK, REFUND_ENTITY_PM, false},
        {"tc90", REFUND_TESTSETUP_01, false, false, ADMIN_REFUND_OVER_MAX, NEW_CREDIT, REFUND_ENTITY_PM, false},
        {"tc91", REFUND_TESTSETUP_01, false, false, ADMIN_REFUND_OVER_MAX, NEW_DEBIT, REFUND_ENTITY_PM, false},

        {"tc92", REFUND_TESTSETUP_03, true, true, ADMIN_REFUND_OVER_MAX, NEW_BANK, REFUND_ENTITY_RESIDENT, false},
        {"tc93", REFUND_TESTSETUP_03, true, true, ADMIN_REFUND_OVER_MAX, NEW_BANK, REFUND_ENTITY_PM, false},
        {"tc94", REFUND_TESTSETUP_03, true, true, ADMIN_REFUND_OVER_MAX, NEW_CREDIT, REFUND_ENTITY_PM, false},
        {"tc95", REFUND_TESTSETUP_03, true, true, ADMIN_REFUND_OVER_MAX, NEW_DEBIT, REFUND_ENTITY_PM, false},

        {"tc96", REFUND_TESTSETUP_02, true, false, ADMIN_REFUND_OVER_MAX, NEW_BANK, REFUND_ENTITY_RESIDENT, false},
        {"tc97", REFUND_TESTSETUP_02, true, false, ADMIN_REFUND_OVER_MAX, NEW_BANK, REFUND_ENTITY_PM, false},
        {"tc98", REFUND_TESTSETUP_02, true, false, ADMIN_REFUND_OVER_MAX, NEW_CREDIT, REFUND_ENTITY_PM, false},
        {"tc99", REFUND_TESTSETUP_02, true, false, ADMIN_REFUND_OVER_MAX, NEW_DEBIT, REFUND_ENTITY_PM, false},

        {"tc100", REFUND_TESTSETUP_01, false, false, ADMIN_REFUND_MAX, NEW_BANK, REFUND_ENTITY_RESIDENT, true},
        {"tc101", REFUND_TESTSETUP_01, false, false, ADMIN_REFUND_MAX, NEW_BANK, REFUND_ENTITY_PM, true},
        {"tc102", REFUND_TESTSETUP_01, false, false, ADMIN_REFUND_MAX, NEW_CREDIT, REFUND_ENTITY_PM, true},
        {"tc103", REFUND_TESTSETUP_01, false, false, ADMIN_REFUND_MAX, NEW_DEBIT, REFUND_ENTITY_PM, true},

        {"tc104", REFUND_TESTSETUP_03, true, true, ADMIN_REFUND_MAX, NEW_BANK, REFUND_ENTITY_RESIDENT, true},
        {"tc105", REFUND_TESTSETUP_03, true, true, ADMIN_REFUND_MAX, NEW_BANK, REFUND_ENTITY_PM, true},
        {"tc106", REFUND_TESTSETUP_03, true, true, ADMIN_REFUND_MAX, NEW_CREDIT, REFUND_ENTITY_PM, true},
        {"tc107", REFUND_TESTSETUP_03, true, true, ADMIN_REFUND_MAX, NEW_DEBIT, REFUND_ENTITY_PM, true},

        {"tc108", REFUND_TESTSETUP_02, true, false, ADMIN_REFUND_MAX, NEW_BANK, REFUND_ENTITY_RESIDENT, true},
        {"tc109", REFUND_TESTSETUP_02, true, false, ADMIN_REFUND_MAX, NEW_BANK, REFUND_ENTITY_PM, true},
        {"tc110", REFUND_TESTSETUP_02, true, false, ADMIN_REFUND_MAX, NEW_CREDIT, REFUND_ENTITY_PM, true},
        {"tc111", REFUND_TESTSETUP_02, true, false, ADMIN_REFUND_MAX, NEW_DEBIT, REFUND_ENTITY_PM, true},

        {"tc112", REFUND_TESTSETUP_01, false, false, ADMIN_REFUND_PARTIAL, NEW_BANK, REFUND_ENTITY_RESIDENT, true},
        {"tc113", REFUND_TESTSETUP_01, false, false, ADMIN_REFUND_PARTIAL, NEW_BANK, REFUND_ENTITY_PM, true},
        {"tc114", REFUND_TESTSETUP_01, false, false, ADMIN_REFUND_PARTIAL, NEW_CREDIT, REFUND_ENTITY_PM, true},
        {"tc115", REFUND_TESTSETUP_01, false, false, ADMIN_REFUND_PARTIAL, NEW_DEBIT, REFUND_ENTITY_PM, true},

        {"tc116", REFUND_TESTSETUP_03, true, true, ADMIN_REFUND_PARTIAL, NEW_BANK, REFUND_ENTITY_RESIDENT, true},
        {"tc117", REFUND_TESTSETUP_03, true, true, ADMIN_REFUND_PARTIAL, NEW_BANK, REFUND_ENTITY_PM, true},
        {"tc118", REFUND_TESTSETUP_03, true, true, ADMIN_REFUND_PARTIAL, NEW_CREDIT, REFUND_ENTITY_PM, true},
        {"tc119", REFUND_TESTSETUP_03, true, true, ADMIN_REFUND_PARTIAL, NEW_DEBIT, REFUND_ENTITY_PM, true},

        {"tc120", REFUND_TESTSETUP_02, true, false, ADMIN_REFUND_PARTIAL, NEW_BANK, REFUND_ENTITY_RESIDENT, true},
        {"tc121", REFUND_TESTSETUP_02, true, false, ADMIN_REFUND_PARTIAL, NEW_BANK, REFUND_ENTITY_PM, true},
        {"tc122", REFUND_TESTSETUP_02, true, false, ADMIN_REFUND_PARTIAL, NEW_CREDIT, REFUND_ENTITY_PM, true},
        {"tc123", REFUND_TESTSETUP_02, true, false, ADMIN_REFUND_PARTIAL, NEW_DEBIT, REFUND_ENTITY_PM, true},

        {"tc124", REFUND_TESTSETUP_01, false, false, ADMIN_REFUND_OVER_MAX, NEW_BANK, REFUND_ENTITY_RESIDENT, true},
        {"tc125", REFUND_TESTSETUP_01, false, false, ADMIN_REFUND_OVER_MAX, NEW_BANK, REFUND_ENTITY_PM, true},
        {"tc126", REFUND_TESTSETUP_01, false, false, ADMIN_REFUND_OVER_MAX, NEW_CREDIT, REFUND_ENTITY_PM, true},
        {"tc127", REFUND_TESTSETUP_01, false, false, ADMIN_REFUND_OVER_MAX, NEW_DEBIT, REFUND_ENTITY_PM, true},

        {"tc128", REFUND_TESTSETUP_03, true, true, ADMIN_REFUND_OVER_MAX, NEW_BANK, REFUND_ENTITY_RESIDENT, true},
        {"tc129", REFUND_TESTSETUP_03, true, true, ADMIN_REFUND_OVER_MAX, NEW_BANK, REFUND_ENTITY_PM, true},
        {"tc130", REFUND_TESTSETUP_03, true, true, ADMIN_REFUND_OVER_MAX, NEW_CREDIT, REFUND_ENTITY_PM, true},
        {"tc131", REFUND_TESTSETUP_03, true, true, ADMIN_REFUND_OVER_MAX, NEW_DEBIT, REFUND_ENTITY_PM, true},

        {"tc132", REFUND_TESTSETUP_02, true, false, ADMIN_REFUND_OVER_MAX, NEW_BANK, REFUND_ENTITY_RESIDENT, true},
        {"tc133", REFUND_TESTSETUP_02, true, false, ADMIN_REFUND_OVER_MAX, NEW_BANK, REFUND_ENTITY_PM, true},
        {"tc134", REFUND_TESTSETUP_02, true, false, ADMIN_REFUND_OVER_MAX, NEW_CREDIT, REFUND_ENTITY_PM, true},
        {"tc135", REFUND_TESTSETUP_02, true, false, ADMIN_REFUND_OVER_MAX, NEW_DEBIT, REFUND_ENTITY_PM, true}
    };
  }
}
