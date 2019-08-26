package com.paylease.app.qa.testbase.dataproviders;

import static com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage.NEW_BANK;
import static com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage.NEW_CREDIT;
import static com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage.NEW_DEBIT;
import static com.paylease.app.qa.framework.pages.paymentflow.SchedulePage.SELECT_ANNUALLY;
import static com.paylease.app.qa.framework.pages.paymentflow.SchedulePage.SELECT_BI_ANNUALLY;
import static com.paylease.app.qa.framework.pages.paymentflow.SchedulePage.SELECT_MONTHLY;
import static com.paylease.app.qa.framework.pages.paymentflow.SchedulePage.SELECT_QUARTERLY;
import static com.paylease.app.qa.framework.pages.pm.PmMenu.PAYMENTS_DROPDOWN;
import static com.paylease.app.qa.framework.pages.pm.PmResidentListPage.RESIDENTS_LIST;
import static com.paylease.app.qa.framework.pages.resident.ResHomePage.CREATE_AUTOPAY_LINK;
import static com.paylease.app.qa.framework.pages.resident.ResHomePage.GET_STARTED_AUTOPAY_LINK;
import static com.paylease.app.qa.framework.pages.resident.ResidentMenuItems.AUTOPAY_TAB;

import org.testng.annotations.DataProvider;

public class FixedAutoPayTestDataProvider {

  /**
   * Provides data required to run various kinds of test combinations for Resident.
   *
   * @return data
   */
  @DataProvider(name = "fapDataResident", parallel = true)
  public Object[][] dataResident() {

    return new Object[][]{
        //Test variation no., EntryPoint, Frequency, Indefinite, PaymentType, ExpressPay

        //EntryPoint = AUTOPAY_TAB
        {"tc1", AUTOPAY_TAB, SELECT_MONTHLY, false, NEW_BANK, false},
        {"tc2", AUTOPAY_TAB, SELECT_QUARTERLY, false, NEW_BANK, false},
        {"tc3", AUTOPAY_TAB, SELECT_BI_ANNUALLY, false, NEW_BANK, false},
        {"tc4", AUTOPAY_TAB, SELECT_ANNUALLY, false, NEW_BANK, false},

        {"tc5", AUTOPAY_TAB, SELECT_MONTHLY, false, NEW_CREDIT, false},
        {"tc6", AUTOPAY_TAB, SELECT_QUARTERLY, false, NEW_CREDIT, false},
        {"tc7", AUTOPAY_TAB, SELECT_BI_ANNUALLY, false, NEW_CREDIT, false},
        {"tc8", AUTOPAY_TAB, SELECT_ANNUALLY, false, NEW_CREDIT, false},

        {"tc9", AUTOPAY_TAB, SELECT_MONTHLY, false, NEW_DEBIT, false},
        {"tc10", AUTOPAY_TAB, SELECT_QUARTERLY, false, NEW_DEBIT, false},
        {"tc11", AUTOPAY_TAB, SELECT_BI_ANNUALLY, false, NEW_DEBIT, false},
        {"tc12", AUTOPAY_TAB, SELECT_ANNUALLY, false, NEW_DEBIT, false},

        {"tc13", AUTOPAY_TAB, SELECT_MONTHLY, false, NEW_CREDIT, true},
        {"tc14", AUTOPAY_TAB, SELECT_QUARTERLY, false, NEW_CREDIT, true},
        {"tc15", AUTOPAY_TAB, SELECT_BI_ANNUALLY, false, NEW_CREDIT, true},
        {"tc16", AUTOPAY_TAB, SELECT_ANNUALLY, false, NEW_CREDIT, true},

        {"tc17", AUTOPAY_TAB, SELECT_MONTHLY, false, NEW_DEBIT, true},
        {"tc18", AUTOPAY_TAB, SELECT_QUARTERLY, false, NEW_DEBIT, true},
        {"tc19", AUTOPAY_TAB, SELECT_BI_ANNUALLY, false, NEW_DEBIT, true},
        {"tc20", AUTOPAY_TAB, SELECT_ANNUALLY, false, NEW_DEBIT, true},

        {"tc21", AUTOPAY_TAB, SELECT_MONTHLY, true, NEW_BANK, false},
        {"tc22", AUTOPAY_TAB, SELECT_QUARTERLY, true, NEW_BANK, false},
        {"tc23", AUTOPAY_TAB, SELECT_BI_ANNUALLY, true, NEW_BANK, false},
        {"tc24", AUTOPAY_TAB, SELECT_ANNUALLY, true, NEW_BANK, false},

        {"tc25", AUTOPAY_TAB, SELECT_MONTHLY, true, NEW_CREDIT, false},
        {"tc26", AUTOPAY_TAB, SELECT_QUARTERLY, true, NEW_CREDIT, false},
        {"tc27", AUTOPAY_TAB, SELECT_BI_ANNUALLY, true, NEW_CREDIT, false},
        {"tc28", AUTOPAY_TAB, SELECT_ANNUALLY, true, NEW_CREDIT, false},

        {"tc29", AUTOPAY_TAB, SELECT_MONTHLY, true, NEW_DEBIT, false},
        {"tc30", AUTOPAY_TAB, SELECT_QUARTERLY, true, NEW_DEBIT, false},
        {"tc31", AUTOPAY_TAB, SELECT_BI_ANNUALLY, true, NEW_DEBIT, false},
        {"tc32", AUTOPAY_TAB, SELECT_ANNUALLY, true, NEW_DEBIT, false},

        {"tc33", AUTOPAY_TAB, SELECT_MONTHLY, true, NEW_CREDIT, true},
        {"tc34", AUTOPAY_TAB, SELECT_QUARTERLY, true, NEW_CREDIT, true},
        {"tc35", AUTOPAY_TAB, SELECT_BI_ANNUALLY, true, NEW_CREDIT, true},
        {"tc36", AUTOPAY_TAB, SELECT_ANNUALLY, true, NEW_CREDIT, true},

        {"tc37", AUTOPAY_TAB, SELECT_MONTHLY, true, NEW_DEBIT, true},
        {"tc38", AUTOPAY_TAB, SELECT_QUARTERLY, true, NEW_DEBIT, true},
        {"tc39", AUTOPAY_TAB, SELECT_BI_ANNUALLY, true, NEW_DEBIT, true},
        {"tc40", AUTOPAY_TAB, SELECT_ANNUALLY, true, NEW_DEBIT, true},

        //EntryPoint = CREATE_AUTOPAY_LINK
        {"tc41", CREATE_AUTOPAY_LINK, SELECT_MONTHLY, false, NEW_BANK, false},
        {"tc42", CREATE_AUTOPAY_LINK, SELECT_QUARTERLY, false, NEW_BANK, false},
        {"tc43", CREATE_AUTOPAY_LINK, SELECT_BI_ANNUALLY, false, NEW_BANK, false},
        {"tc44", CREATE_AUTOPAY_LINK, SELECT_ANNUALLY, false, NEW_BANK, false},

        {"tc45", CREATE_AUTOPAY_LINK, SELECT_MONTHLY, false, NEW_CREDIT, false},
        {"tc46", CREATE_AUTOPAY_LINK, SELECT_QUARTERLY, false, NEW_CREDIT, false},
        {"tc47", CREATE_AUTOPAY_LINK, SELECT_BI_ANNUALLY, false, NEW_CREDIT, false},
        {"tc48", CREATE_AUTOPAY_LINK, SELECT_ANNUALLY, false, NEW_CREDIT, false},

        {"tc49", CREATE_AUTOPAY_LINK, SELECT_MONTHLY, false, NEW_DEBIT, false},
        {"tc50", CREATE_AUTOPAY_LINK, SELECT_QUARTERLY, false, NEW_DEBIT, false},
        {"tc51", CREATE_AUTOPAY_LINK, SELECT_BI_ANNUALLY, false, NEW_DEBIT, false},
        {"tc52", CREATE_AUTOPAY_LINK, SELECT_ANNUALLY, false, NEW_DEBIT, false},

        {"tc53", CREATE_AUTOPAY_LINK, SELECT_MONTHLY, false, NEW_CREDIT, true},
        {"tc54", CREATE_AUTOPAY_LINK, SELECT_QUARTERLY, false, NEW_CREDIT, true},
        {"tc55", CREATE_AUTOPAY_LINK, SELECT_BI_ANNUALLY, false, NEW_CREDIT, true},
        {"tc56", CREATE_AUTOPAY_LINK, SELECT_ANNUALLY, false, NEW_CREDIT, true},

        {"tc57", CREATE_AUTOPAY_LINK, SELECT_MONTHLY, false, NEW_DEBIT, true},
        {"tc58", CREATE_AUTOPAY_LINK, SELECT_QUARTERLY, false, NEW_DEBIT, true},
        {"tc59", CREATE_AUTOPAY_LINK, SELECT_BI_ANNUALLY, false, NEW_DEBIT, true},
        {"tc60", CREATE_AUTOPAY_LINK, SELECT_ANNUALLY, false, NEW_DEBIT, true},

        {"tc61", CREATE_AUTOPAY_LINK, SELECT_MONTHLY, true, NEW_BANK, false},
        {"tc62", CREATE_AUTOPAY_LINK, SELECT_QUARTERLY, true, NEW_BANK, false},
        {"tc63", CREATE_AUTOPAY_LINK, SELECT_BI_ANNUALLY, true, NEW_BANK, false},
        {"tc64", CREATE_AUTOPAY_LINK, SELECT_ANNUALLY, true, NEW_BANK, false},

        {"tc65", CREATE_AUTOPAY_LINK, SELECT_MONTHLY, true, NEW_CREDIT, false},
        {"tc66", CREATE_AUTOPAY_LINK, SELECT_QUARTERLY, true, NEW_CREDIT, false},
        {"tc67", CREATE_AUTOPAY_LINK, SELECT_BI_ANNUALLY, true, NEW_CREDIT, false},
        {"tc68", CREATE_AUTOPAY_LINK, SELECT_ANNUALLY, true, NEW_CREDIT, false},

        {"tc69", CREATE_AUTOPAY_LINK, SELECT_MONTHLY, true, NEW_DEBIT, false},
        {"tc70", CREATE_AUTOPAY_LINK, SELECT_QUARTERLY, true, NEW_DEBIT, false},
        {"tc71", CREATE_AUTOPAY_LINK, SELECT_BI_ANNUALLY, true, NEW_DEBIT, false},
        {"tc72", CREATE_AUTOPAY_LINK, SELECT_ANNUALLY, true, NEW_DEBIT, false},

        {"tc73", CREATE_AUTOPAY_LINK, SELECT_MONTHLY, true, NEW_CREDIT, true},
        {"tc74", CREATE_AUTOPAY_LINK, SELECT_QUARTERLY, true, NEW_CREDIT, true},
        {"tc75", CREATE_AUTOPAY_LINK, SELECT_BI_ANNUALLY, true, NEW_CREDIT, true},
        {"tc76", CREATE_AUTOPAY_LINK, SELECT_ANNUALLY, true, NEW_CREDIT, true},

        {"tc77", CREATE_AUTOPAY_LINK, SELECT_MONTHLY, true, NEW_DEBIT, true},
        {"tc78", CREATE_AUTOPAY_LINK, SELECT_QUARTERLY, true, NEW_DEBIT, true},
        {"tc79", CREATE_AUTOPAY_LINK, SELECT_BI_ANNUALLY, true, NEW_DEBIT, true},
        {"tc80", CREATE_AUTOPAY_LINK, SELECT_ANNUALLY, true, NEW_DEBIT, true},

        //EntryPoint = GET_STARTED_AUTOPAY_LINK
        {"tc81", GET_STARTED_AUTOPAY_LINK, SELECT_MONTHLY, false, NEW_BANK, false},
        {"tc82", GET_STARTED_AUTOPAY_LINK, SELECT_QUARTERLY, false, NEW_BANK, false},
        {"tc83", GET_STARTED_AUTOPAY_LINK, SELECT_BI_ANNUALLY, false, NEW_BANK, false},
        {"tc84", GET_STARTED_AUTOPAY_LINK, SELECT_ANNUALLY, false, NEW_BANK, false},

        {"tc85", GET_STARTED_AUTOPAY_LINK, SELECT_MONTHLY, false, NEW_CREDIT, false},
        {"tc86", GET_STARTED_AUTOPAY_LINK, SELECT_QUARTERLY, false, NEW_CREDIT, false},
        {"tc87", GET_STARTED_AUTOPAY_LINK, SELECT_BI_ANNUALLY, false, NEW_CREDIT, false},
        {"tc88", GET_STARTED_AUTOPAY_LINK, SELECT_ANNUALLY, false, NEW_CREDIT, false},

        {"tc89", GET_STARTED_AUTOPAY_LINK, SELECT_MONTHLY, false, NEW_DEBIT, false},
        {"tc90", GET_STARTED_AUTOPAY_LINK, SELECT_QUARTERLY, false, NEW_DEBIT, false},
        {"tc91", GET_STARTED_AUTOPAY_LINK, SELECT_BI_ANNUALLY, false, NEW_DEBIT, false},
        {"tc92", GET_STARTED_AUTOPAY_LINK, SELECT_ANNUALLY, false, NEW_DEBIT, false},

        {"tc93", GET_STARTED_AUTOPAY_LINK, SELECT_MONTHLY, false, NEW_CREDIT, true},
        {"tc94", GET_STARTED_AUTOPAY_LINK, SELECT_QUARTERLY, false, NEW_CREDIT, true},
        {"tc95", GET_STARTED_AUTOPAY_LINK, SELECT_BI_ANNUALLY, false, NEW_CREDIT, true},
        {"tc96", GET_STARTED_AUTOPAY_LINK, SELECT_ANNUALLY, false, NEW_CREDIT, true},

        {"tc97", GET_STARTED_AUTOPAY_LINK, SELECT_MONTHLY, false, NEW_DEBIT, true},
        {"tc98", GET_STARTED_AUTOPAY_LINK, SELECT_QUARTERLY, false, NEW_DEBIT, true},
        {"tc99", GET_STARTED_AUTOPAY_LINK, SELECT_BI_ANNUALLY, false, NEW_DEBIT, true},
        {"tc100", GET_STARTED_AUTOPAY_LINK, SELECT_ANNUALLY, false, NEW_DEBIT, true},

        {"tc101", GET_STARTED_AUTOPAY_LINK, SELECT_MONTHLY, true, NEW_BANK, false},
        {"tc102", GET_STARTED_AUTOPAY_LINK, SELECT_QUARTERLY, true, NEW_BANK, false},
        {"tc103", GET_STARTED_AUTOPAY_LINK, SELECT_BI_ANNUALLY, true, NEW_BANK, false},
        {"tc104", GET_STARTED_AUTOPAY_LINK, SELECT_ANNUALLY, true, NEW_BANK, false},

        {"tc105", GET_STARTED_AUTOPAY_LINK, SELECT_MONTHLY, true, NEW_CREDIT, false},
        {"tc106", GET_STARTED_AUTOPAY_LINK, SELECT_QUARTERLY, true, NEW_CREDIT, false},
        {"tc107", GET_STARTED_AUTOPAY_LINK, SELECT_BI_ANNUALLY, true, NEW_CREDIT, false},
        {"tc108", GET_STARTED_AUTOPAY_LINK, SELECT_ANNUALLY, true, NEW_CREDIT, false},

        {"tc109", GET_STARTED_AUTOPAY_LINK, SELECT_MONTHLY, true, NEW_DEBIT, false},
        {"tc110", GET_STARTED_AUTOPAY_LINK, SELECT_QUARTERLY, true, NEW_DEBIT, false},
        {"tc111", GET_STARTED_AUTOPAY_LINK, SELECT_BI_ANNUALLY, true, NEW_DEBIT, false},
        {"tc112", GET_STARTED_AUTOPAY_LINK, SELECT_ANNUALLY, true, NEW_DEBIT, false},

        {"tc113", GET_STARTED_AUTOPAY_LINK, SELECT_MONTHLY, true, NEW_CREDIT, true},
        {"tc114", GET_STARTED_AUTOPAY_LINK, SELECT_QUARTERLY, true, NEW_CREDIT, true},
        {"tc115", GET_STARTED_AUTOPAY_LINK, SELECT_BI_ANNUALLY, true, NEW_CREDIT, true},
        {"tc116", GET_STARTED_AUTOPAY_LINK, SELECT_ANNUALLY, true, NEW_CREDIT, true},

        {"tc117", GET_STARTED_AUTOPAY_LINK, SELECT_MONTHLY, true, NEW_DEBIT, true},
        {"tc118", GET_STARTED_AUTOPAY_LINK, SELECT_QUARTERLY, true, NEW_DEBIT, true},
        {"tc119", GET_STARTED_AUTOPAY_LINK, SELECT_BI_ANNUALLY, true, NEW_DEBIT, true},
        {"tc120", GET_STARTED_AUTOPAY_LINK, SELECT_ANNUALLY, true, NEW_DEBIT, true}
    };
  }

  /**
   * Provides data required to run various kinds of test combinations for PM.
   *
   * @return data
   */
  @DataProvider(name = "fapDataPm", parallel = true)
  public Object[][] dataPm() {

    return new Object[][]{
        //Test variation no., EntryPoint, Frequency, Indefinite, PaymentType, ExpressPay

        //EntryPoint = RESIDENTS_LIST
        {"tc1", RESIDENTS_LIST, SELECT_MONTHLY, false, NEW_BANK, false},
        {"tc2", RESIDENTS_LIST, SELECT_QUARTERLY, false, NEW_BANK, false},
        {"tc3", RESIDENTS_LIST, SELECT_BI_ANNUALLY, false, NEW_BANK, false},
        {"tc4", RESIDENTS_LIST, SELECT_ANNUALLY, false, NEW_BANK, false},

        {"tc5", RESIDENTS_LIST, SELECT_MONTHLY, false, NEW_CREDIT, false},
        {"tc6", RESIDENTS_LIST, SELECT_QUARTERLY, false, NEW_CREDIT, false},
        {"tc7", RESIDENTS_LIST, SELECT_BI_ANNUALLY, false, NEW_CREDIT, false},
        {"tc8", RESIDENTS_LIST, SELECT_ANNUALLY, false, NEW_CREDIT, false},

        {"tc9", RESIDENTS_LIST, SELECT_MONTHLY, false, NEW_DEBIT, false},
        {"tc10", RESIDENTS_LIST, SELECT_QUARTERLY, false, NEW_DEBIT, false},
        {"tc11", RESIDENTS_LIST, SELECT_BI_ANNUALLY, false, NEW_DEBIT, false},
        {"tc12", RESIDENTS_LIST, SELECT_ANNUALLY, false, NEW_DEBIT, false},

        {"tc13", RESIDENTS_LIST, SELECT_MONTHLY, false, NEW_CREDIT, true},
        {"tc14", RESIDENTS_LIST, SELECT_QUARTERLY, false, NEW_CREDIT, true},
        {"tc15", RESIDENTS_LIST, SELECT_BI_ANNUALLY, false, NEW_CREDIT, true},
        {"tc16", RESIDENTS_LIST, SELECT_ANNUALLY, false, NEW_CREDIT, true},

        {"tc17", RESIDENTS_LIST, SELECT_MONTHLY, false, NEW_DEBIT, true},
        {"tc18", RESIDENTS_LIST, SELECT_QUARTERLY, false, NEW_DEBIT, true},
        {"tc19", RESIDENTS_LIST, SELECT_BI_ANNUALLY, false, NEW_DEBIT, true},
        {"tc20", RESIDENTS_LIST, SELECT_ANNUALLY, false, NEW_DEBIT, true},

        {"tc21", RESIDENTS_LIST, SELECT_MONTHLY, true, NEW_BANK, false},
        {"tc22", RESIDENTS_LIST, SELECT_QUARTERLY, true, NEW_BANK, false},
        {"tc23", RESIDENTS_LIST, SELECT_BI_ANNUALLY, true, NEW_BANK, false},
        {"tc24", RESIDENTS_LIST, SELECT_ANNUALLY, true, NEW_BANK, false},

        {"tc25", RESIDENTS_LIST, SELECT_MONTHLY, true, NEW_CREDIT, false},
        {"tc26", RESIDENTS_LIST, SELECT_QUARTERLY, true, NEW_CREDIT, false},
        {"tc27", RESIDENTS_LIST, SELECT_BI_ANNUALLY, true, NEW_CREDIT, false},
        {"tc28", RESIDENTS_LIST, SELECT_ANNUALLY, true, NEW_CREDIT, false},

        {"tc29", RESIDENTS_LIST, SELECT_MONTHLY, true, NEW_DEBIT, false},
        {"tc30", RESIDENTS_LIST, SELECT_QUARTERLY, true, NEW_DEBIT, false},
        {"tc31", RESIDENTS_LIST, SELECT_BI_ANNUALLY, true, NEW_DEBIT, false},
        {"tc32", RESIDENTS_LIST, SELECT_ANNUALLY, true, NEW_DEBIT, false},

        {"tc33", RESIDENTS_LIST, SELECT_MONTHLY, true, NEW_CREDIT, true},
        {"tc34", RESIDENTS_LIST, SELECT_QUARTERLY, true, NEW_CREDIT, true},
        {"tc35", RESIDENTS_LIST, SELECT_BI_ANNUALLY, true, NEW_CREDIT, true},
        {"tc36", RESIDENTS_LIST, SELECT_ANNUALLY, true, NEW_CREDIT, true},

        {"tc37", RESIDENTS_LIST, SELECT_MONTHLY, true, NEW_DEBIT, true},
        {"tc38", RESIDENTS_LIST, SELECT_QUARTERLY, true, NEW_DEBIT, true},
        {"tc39", RESIDENTS_LIST, SELECT_BI_ANNUALLY, true, NEW_DEBIT, true},
        {"tc40", RESIDENTS_LIST, SELECT_ANNUALLY, true, NEW_DEBIT, true},

        //EntryPoint = PAYMENTS_DROPDOWN
        {"tc41", PAYMENTS_DROPDOWN, SELECT_MONTHLY, false, NEW_BANK, false},
        {"tc42", PAYMENTS_DROPDOWN, SELECT_QUARTERLY, false, NEW_BANK, false},
        {"tc43", PAYMENTS_DROPDOWN, SELECT_BI_ANNUALLY, false, NEW_BANK, false},
        {"tc44", PAYMENTS_DROPDOWN, SELECT_ANNUALLY, false, NEW_BANK, false},

        {"tc45", PAYMENTS_DROPDOWN, SELECT_MONTHLY, false, NEW_CREDIT, false},
        {"tc46", PAYMENTS_DROPDOWN, SELECT_QUARTERLY, false, NEW_CREDIT, false},
        {"tc47", PAYMENTS_DROPDOWN, SELECT_BI_ANNUALLY, false, NEW_CREDIT, false},
        {"tc48", PAYMENTS_DROPDOWN, SELECT_ANNUALLY, false, NEW_CREDIT, false},

        {"tc49", PAYMENTS_DROPDOWN, SELECT_MONTHLY, false, NEW_DEBIT, false},
        {"tc50", PAYMENTS_DROPDOWN, SELECT_QUARTERLY, false, NEW_DEBIT, false},
        {"tc51", PAYMENTS_DROPDOWN, SELECT_BI_ANNUALLY, false, NEW_DEBIT, false},
        {"tc52", PAYMENTS_DROPDOWN, SELECT_ANNUALLY, false, NEW_DEBIT, false},

        {"tc53", PAYMENTS_DROPDOWN, SELECT_MONTHLY, false, NEW_CREDIT, true},
        {"tc54", PAYMENTS_DROPDOWN, SELECT_QUARTERLY, false, NEW_CREDIT, true},
        {"tc55", PAYMENTS_DROPDOWN, SELECT_BI_ANNUALLY, false, NEW_CREDIT, true},
        {"tc56", PAYMENTS_DROPDOWN, SELECT_ANNUALLY, false, NEW_CREDIT, true},

        {"tc57", PAYMENTS_DROPDOWN, SELECT_MONTHLY, false, NEW_DEBIT, true},
        {"tc58", PAYMENTS_DROPDOWN, SELECT_QUARTERLY, false, NEW_DEBIT, true},
        {"tc59", PAYMENTS_DROPDOWN, SELECT_BI_ANNUALLY, false, NEW_DEBIT, true},
        {"tc60", PAYMENTS_DROPDOWN, SELECT_ANNUALLY, false, NEW_DEBIT, true},

        {"tc61", PAYMENTS_DROPDOWN, SELECT_MONTHLY, true, NEW_BANK, false},
        {"tc62", PAYMENTS_DROPDOWN, SELECT_QUARTERLY, true, NEW_BANK, false},
        {"tc63", PAYMENTS_DROPDOWN, SELECT_BI_ANNUALLY, true, NEW_BANK, false},
        {"tc64", PAYMENTS_DROPDOWN, SELECT_ANNUALLY, true, NEW_BANK, false},

        {"tc65", PAYMENTS_DROPDOWN, SELECT_MONTHLY, true, NEW_CREDIT, false},
        {"tc66", PAYMENTS_DROPDOWN, SELECT_QUARTERLY, true, NEW_CREDIT, false},
        {"tc67", PAYMENTS_DROPDOWN, SELECT_BI_ANNUALLY, true, NEW_CREDIT, false},
        {"tc68", PAYMENTS_DROPDOWN, SELECT_ANNUALLY, true, NEW_CREDIT, false},

        {"tc69", PAYMENTS_DROPDOWN, SELECT_MONTHLY, true, NEW_DEBIT, false},
        {"tc70", PAYMENTS_DROPDOWN, SELECT_QUARTERLY, true, NEW_DEBIT, false},
        {"tc71", PAYMENTS_DROPDOWN, SELECT_BI_ANNUALLY, true, NEW_DEBIT, false},
        {"tc72", PAYMENTS_DROPDOWN, SELECT_ANNUALLY, true, NEW_DEBIT, false},

        {"tc73", PAYMENTS_DROPDOWN, SELECT_MONTHLY, true, NEW_CREDIT, true},
        {"tc74", PAYMENTS_DROPDOWN, SELECT_QUARTERLY, true, NEW_CREDIT, true},
        {"tc75", PAYMENTS_DROPDOWN, SELECT_BI_ANNUALLY, true, NEW_CREDIT, true},
        {"tc76", PAYMENTS_DROPDOWN, SELECT_ANNUALLY, true, NEW_CREDIT, true},

        {"tc77", PAYMENTS_DROPDOWN, SELECT_MONTHLY, true, NEW_DEBIT, true},
        {"tc78", PAYMENTS_DROPDOWN, SELECT_QUARTERLY, true, NEW_DEBIT, true},
        {"tc79", PAYMENTS_DROPDOWN, SELECT_BI_ANNUALLY, true, NEW_DEBIT, true},
        {"tc80", PAYMENTS_DROPDOWN, SELECT_ANNUALLY, true, NEW_DEBIT, true}
    };
  }
}
