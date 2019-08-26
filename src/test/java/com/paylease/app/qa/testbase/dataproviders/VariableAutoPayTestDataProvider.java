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

public class VariableAutoPayTestDataProvider {

  /**
   * Provides data required to run various kinds of test combinations for Resident.
   *
   * @return data
   */
  @DataProvider(name = "vapDataResident", parallel = true)
  public Object[][] dataResident() {

    return new Object[][]{
        //EntryPoint, Frequency, Indefinite, MaxLimit, PaymentType, ExpressPay

        //EntryPoint = AUTOPAY_TAB
        {"tc1", AUTOPAY_TAB, SELECT_MONTHLY, false, false, NEW_BANK, false},
        {"tc2", AUTOPAY_TAB, SELECT_QUARTERLY, false, false, NEW_BANK, false},
        {"tc3", AUTOPAY_TAB, SELECT_BI_ANNUALLY, false, false, NEW_BANK, false},
        {"tc4", AUTOPAY_TAB, SELECT_ANNUALLY, false, false, NEW_BANK, false},

        {"tc5", AUTOPAY_TAB, SELECT_MONTHLY, false, false, NEW_CREDIT, false},
        {"tc6", AUTOPAY_TAB, SELECT_QUARTERLY, false, false, NEW_CREDIT, false},
        {"tc7", AUTOPAY_TAB, SELECT_BI_ANNUALLY, false, false, NEW_CREDIT, false},
        {"tc8", AUTOPAY_TAB, SELECT_ANNUALLY, false, false, NEW_CREDIT, false},

        {"tc9", AUTOPAY_TAB, SELECT_MONTHLY, false, false, NEW_DEBIT, false},
        {"tc10", AUTOPAY_TAB, SELECT_QUARTERLY, false, false, NEW_DEBIT, false},
        {"tc11", AUTOPAY_TAB, SELECT_BI_ANNUALLY, false, false, NEW_DEBIT, false},
        {"tc12", AUTOPAY_TAB, SELECT_ANNUALLY, false, false, NEW_DEBIT, false},

        {"tc13", AUTOPAY_TAB, SELECT_MONTHLY, false, false, NEW_CREDIT, true},
        {"tc14", AUTOPAY_TAB, SELECT_QUARTERLY, false, false, NEW_CREDIT, true},
        {"tc15", AUTOPAY_TAB, SELECT_BI_ANNUALLY, false, false, NEW_CREDIT, true},
        {"tc16", AUTOPAY_TAB, SELECT_ANNUALLY, false, false, NEW_CREDIT, true},

        {"tc17", AUTOPAY_TAB, SELECT_MONTHLY, false, false, NEW_DEBIT, true},
        {"tc18", AUTOPAY_TAB, SELECT_QUARTERLY, false, false, NEW_DEBIT, true},
        {"tc19", AUTOPAY_TAB, SELECT_BI_ANNUALLY, false, false, NEW_DEBIT, true},
        {"tc20", AUTOPAY_TAB, SELECT_ANNUALLY, false, false, NEW_DEBIT, true},

        {"tc21", AUTOPAY_TAB, SELECT_MONTHLY, false, true, NEW_BANK, false},
        {"tc22", AUTOPAY_TAB, SELECT_QUARTERLY, false, true, NEW_BANK, false},
        {"tc23", AUTOPAY_TAB, SELECT_BI_ANNUALLY, false, true, NEW_BANK, false},
        {"tc24", AUTOPAY_TAB, SELECT_ANNUALLY, false, true, NEW_BANK, false},

        {"tc25", AUTOPAY_TAB, SELECT_MONTHLY, false, true, NEW_CREDIT, false},
        {"tc26", AUTOPAY_TAB, SELECT_QUARTERLY, false, true, NEW_CREDIT, false},
        {"tc27", AUTOPAY_TAB, SELECT_BI_ANNUALLY, false, true, NEW_CREDIT, false},
        {"tc28", AUTOPAY_TAB, SELECT_ANNUALLY, false, true, NEW_CREDIT, false},

        {"tc29", AUTOPAY_TAB, SELECT_MONTHLY, false, true, NEW_DEBIT, false},
        {"tc30", AUTOPAY_TAB, SELECT_QUARTERLY, false, true, NEW_DEBIT, false},
        {"tc31", AUTOPAY_TAB, SELECT_BI_ANNUALLY, false, true, NEW_DEBIT, false},
        {"tc32", AUTOPAY_TAB, SELECT_ANNUALLY, false, true, NEW_DEBIT, false},

        {"tc33", AUTOPAY_TAB, SELECT_MONTHLY, false, true, NEW_CREDIT, true},
        {"tc34", AUTOPAY_TAB, SELECT_QUARTERLY, false, true, NEW_CREDIT, true},
        {"tc35", AUTOPAY_TAB, SELECT_BI_ANNUALLY, false, true, NEW_CREDIT, true},
        {"tc36", AUTOPAY_TAB, SELECT_ANNUALLY, false, true, NEW_CREDIT, true},

        {"tc37", AUTOPAY_TAB, SELECT_MONTHLY, false, true, NEW_DEBIT, true},
        {"tc38", AUTOPAY_TAB, SELECT_QUARTERLY, false, true, NEW_DEBIT, true},
        {"tc39", AUTOPAY_TAB, SELECT_BI_ANNUALLY, false, true, NEW_DEBIT, true},
        {"tc40", AUTOPAY_TAB, SELECT_ANNUALLY, false, true, NEW_DEBIT, true},

        {"tc41", AUTOPAY_TAB, SELECT_MONTHLY, true, false, NEW_BANK, false},
        {"tc42", AUTOPAY_TAB, SELECT_QUARTERLY, true, false, NEW_BANK, false},
        {"tc43", AUTOPAY_TAB, SELECT_BI_ANNUALLY, true, false, NEW_BANK, false},
        {"tc44", AUTOPAY_TAB, SELECT_ANNUALLY, true, false, NEW_BANK, false},

        {"tc45", AUTOPAY_TAB, SELECT_MONTHLY, true, false, NEW_CREDIT, false},
        {"tc46", AUTOPAY_TAB, SELECT_QUARTERLY, true, false, NEW_CREDIT, false},
        {"tc47", AUTOPAY_TAB, SELECT_BI_ANNUALLY, true, false, NEW_CREDIT, false},
        {"tc48", AUTOPAY_TAB, SELECT_ANNUALLY, true, false, NEW_CREDIT, false},

        {"tc49", AUTOPAY_TAB, SELECT_MONTHLY, true, false, NEW_DEBIT, false},
        {"tc50", AUTOPAY_TAB, SELECT_QUARTERLY, true, false, NEW_DEBIT, false},
        {"tc51", AUTOPAY_TAB, SELECT_BI_ANNUALLY, true, false, NEW_DEBIT, false},
        {"tc52", AUTOPAY_TAB, SELECT_ANNUALLY, true, false, NEW_DEBIT, false},

        {"tc53", AUTOPAY_TAB, SELECT_MONTHLY, true, false, NEW_CREDIT, true},
        {"tc54", AUTOPAY_TAB, SELECT_QUARTERLY, true, false, NEW_CREDIT, true},
        {"tc55", AUTOPAY_TAB, SELECT_BI_ANNUALLY, true, false, NEW_CREDIT, true},
        {"tc56", AUTOPAY_TAB, SELECT_ANNUALLY, true, false, NEW_CREDIT, true},

        {"tc57", AUTOPAY_TAB, SELECT_MONTHLY, true, false, NEW_DEBIT, true},
        {"tc58", AUTOPAY_TAB, SELECT_QUARTERLY, true, false, NEW_DEBIT, true},
        {"tc59", AUTOPAY_TAB, SELECT_BI_ANNUALLY, true, false, NEW_DEBIT, true},
        {"tc60", AUTOPAY_TAB, SELECT_ANNUALLY, true, false, NEW_DEBIT, true},

        {"tc61", AUTOPAY_TAB, SELECT_MONTHLY, true, true, NEW_BANK, false},
        {"tc62", AUTOPAY_TAB, SELECT_QUARTERLY, true, true, NEW_BANK, false},
        {"tc63", AUTOPAY_TAB, SELECT_BI_ANNUALLY, true, true, NEW_BANK, false},
        {"tc64", AUTOPAY_TAB, SELECT_ANNUALLY, true, true, NEW_BANK, false},

        {"tc65", AUTOPAY_TAB, SELECT_MONTHLY, true, true, NEW_CREDIT, false},
        {"tc66", AUTOPAY_TAB, SELECT_QUARTERLY, true, true, NEW_CREDIT, false},
        {"tc67", AUTOPAY_TAB, SELECT_BI_ANNUALLY, true, true, NEW_CREDIT, false},
        {"tc68", AUTOPAY_TAB, SELECT_ANNUALLY, true, true, NEW_CREDIT, false},

        {"tc69", AUTOPAY_TAB, SELECT_MONTHLY, true, true, NEW_DEBIT, false},
        {"tc70", AUTOPAY_TAB, SELECT_QUARTERLY, true, true, NEW_DEBIT, false},
        {"tc71", AUTOPAY_TAB, SELECT_BI_ANNUALLY, true, true, NEW_DEBIT, false},
        {"tc72", AUTOPAY_TAB, SELECT_ANNUALLY, true, true, NEW_DEBIT, false},

        {"tc73", AUTOPAY_TAB, SELECT_MONTHLY, true, true, NEW_CREDIT, true},
        {"tc74", AUTOPAY_TAB, SELECT_QUARTERLY, true, true, NEW_CREDIT, true},
        {"tc75", AUTOPAY_TAB, SELECT_BI_ANNUALLY, true, true, NEW_CREDIT, true},
        {"tc76", AUTOPAY_TAB, SELECT_ANNUALLY, true, true, NEW_CREDIT, true},

        {"tc77", AUTOPAY_TAB, SELECT_MONTHLY, true, true, NEW_DEBIT, true},
        {"tc78", AUTOPAY_TAB, SELECT_QUARTERLY, true, true, NEW_DEBIT, true},
        {"tc79", AUTOPAY_TAB, SELECT_BI_ANNUALLY, true, true, NEW_DEBIT, true},
        {"tc79", AUTOPAY_TAB, SELECT_ANNUALLY, true, true, NEW_DEBIT, true},

        //EntryPoint = CREATE_AUTOPAY_LINK
        {"tc80", CREATE_AUTOPAY_LINK, SELECT_MONTHLY, false, false, NEW_BANK, false},
        {"tc81", CREATE_AUTOPAY_LINK, SELECT_QUARTERLY, false, false, NEW_BANK, false},
        {"tc82", CREATE_AUTOPAY_LINK, SELECT_BI_ANNUALLY, false, false, NEW_BANK, false},
        {"tc83", CREATE_AUTOPAY_LINK, SELECT_ANNUALLY, false, false, NEW_BANK, false},

        {"tc84", CREATE_AUTOPAY_LINK, SELECT_MONTHLY, false, false, NEW_CREDIT, false},
        {"tc85", CREATE_AUTOPAY_LINK, SELECT_QUARTERLY, false, false, NEW_CREDIT, false},
        {"tc86", CREATE_AUTOPAY_LINK, SELECT_BI_ANNUALLY, false, false, NEW_CREDIT, false},
        {"tc87", CREATE_AUTOPAY_LINK, SELECT_ANNUALLY, false, false, NEW_CREDIT, false},

        {"tc88", CREATE_AUTOPAY_LINK, SELECT_MONTHLY, false, false, NEW_DEBIT, false},
        {"tc89", CREATE_AUTOPAY_LINK, SELECT_QUARTERLY, false, false, NEW_DEBIT, false},
        {"tc90", CREATE_AUTOPAY_LINK, SELECT_BI_ANNUALLY, false, false, NEW_DEBIT, false},
        {"tc91", CREATE_AUTOPAY_LINK, SELECT_ANNUALLY, false, false, NEW_DEBIT, false},

        {"tc92", CREATE_AUTOPAY_LINK, SELECT_MONTHLY, false, false, NEW_CREDIT, true},
        {"tc93", CREATE_AUTOPAY_LINK, SELECT_QUARTERLY, false, false, NEW_CREDIT, true},
        {"tc94", CREATE_AUTOPAY_LINK, SELECT_BI_ANNUALLY, false, false, NEW_CREDIT, true},
        {"tc95", CREATE_AUTOPAY_LINK, SELECT_ANNUALLY, false, false, NEW_CREDIT, true},

        {"tc96", CREATE_AUTOPAY_LINK, SELECT_MONTHLY, false, false, NEW_DEBIT, true},
        {"tc97", CREATE_AUTOPAY_LINK, SELECT_QUARTERLY, false, false, NEW_DEBIT, true},
        {"tc98", CREATE_AUTOPAY_LINK, SELECT_BI_ANNUALLY, false, false, NEW_DEBIT, true},
        {"tc99", CREATE_AUTOPAY_LINK, SELECT_ANNUALLY, false, false, NEW_DEBIT, true},

        {"tc100", CREATE_AUTOPAY_LINK, SELECT_MONTHLY, false, true, NEW_BANK, false},
        {"tc101", CREATE_AUTOPAY_LINK, SELECT_QUARTERLY, false, true, NEW_BANK, false},
        {"tc102", CREATE_AUTOPAY_LINK, SELECT_BI_ANNUALLY, false, true, NEW_BANK, false},
        {"tc103", CREATE_AUTOPAY_LINK, SELECT_ANNUALLY, false, true, NEW_BANK, false},

        {"tc104", CREATE_AUTOPAY_LINK, SELECT_MONTHLY, false, true, NEW_CREDIT, false},
        {"tc105", CREATE_AUTOPAY_LINK, SELECT_QUARTERLY, false, true, NEW_CREDIT, false},
        {"tc106", CREATE_AUTOPAY_LINK, SELECT_BI_ANNUALLY, false, true, NEW_CREDIT, false},
        {"tc107", CREATE_AUTOPAY_LINK, SELECT_ANNUALLY, false, true, NEW_CREDIT, false},

        {"tc108", CREATE_AUTOPAY_LINK, SELECT_MONTHLY, false, true, NEW_DEBIT, false},
        {"tc109", CREATE_AUTOPAY_LINK, SELECT_QUARTERLY, false, true, NEW_DEBIT, false},
        {"tc110", CREATE_AUTOPAY_LINK, SELECT_BI_ANNUALLY, false, true, NEW_DEBIT, false},
        {"tc111", CREATE_AUTOPAY_LINK, SELECT_ANNUALLY, false, true, NEW_DEBIT, false},

        {"tc112", CREATE_AUTOPAY_LINK, SELECT_MONTHLY, false, true, NEW_CREDIT, true},
        {"tc113", CREATE_AUTOPAY_LINK, SELECT_QUARTERLY, false, true, NEW_CREDIT, true},
        {"tc114", CREATE_AUTOPAY_LINK, SELECT_BI_ANNUALLY, false, true, NEW_CREDIT, true},
        {"tc115", CREATE_AUTOPAY_LINK, SELECT_ANNUALLY, false, true, NEW_CREDIT, true},

        {"tc116", CREATE_AUTOPAY_LINK, SELECT_MONTHLY, false, true, NEW_DEBIT, true},
        {"tc117", CREATE_AUTOPAY_LINK, SELECT_QUARTERLY, false, true, NEW_DEBIT, true},
        {"tc118", CREATE_AUTOPAY_LINK, SELECT_BI_ANNUALLY, false, true, NEW_DEBIT, true},
        {"tc119", CREATE_AUTOPAY_LINK, SELECT_ANNUALLY, false, true, NEW_DEBIT, true},

        {"tc120", CREATE_AUTOPAY_LINK, SELECT_MONTHLY, true, false, NEW_BANK, false},
        {"tc121", CREATE_AUTOPAY_LINK, SELECT_QUARTERLY, true, false, NEW_BANK, false},
        {"tc122", CREATE_AUTOPAY_LINK, SELECT_BI_ANNUALLY, true, false, NEW_BANK, false},
        {"tc123", CREATE_AUTOPAY_LINK, SELECT_ANNUALLY, true, false, NEW_BANK, false},

        {"tc124", CREATE_AUTOPAY_LINK, SELECT_MONTHLY, true, false, NEW_CREDIT, false},
        {"tc125", CREATE_AUTOPAY_LINK, SELECT_QUARTERLY, true, false, NEW_CREDIT, false},
        {"tc126", CREATE_AUTOPAY_LINK, SELECT_BI_ANNUALLY, true, false, NEW_CREDIT, false},
        {"tc127", CREATE_AUTOPAY_LINK, SELECT_ANNUALLY, true, false, NEW_CREDIT, false},

        {"tc128", CREATE_AUTOPAY_LINK, SELECT_MONTHLY, true, false, NEW_DEBIT, false},
        {"tc129", CREATE_AUTOPAY_LINK, SELECT_QUARTERLY, true, false, NEW_DEBIT, false},
        {"tc130", CREATE_AUTOPAY_LINK, SELECT_BI_ANNUALLY, true, false, NEW_DEBIT, false},
        {"tc131", CREATE_AUTOPAY_LINK, SELECT_ANNUALLY, true, false, NEW_DEBIT, false},

        {"tc132", CREATE_AUTOPAY_LINK, SELECT_MONTHLY, true, false, NEW_CREDIT, true},
        {"tc133", CREATE_AUTOPAY_LINK, SELECT_QUARTERLY, true, false, NEW_CREDIT, true},
        {"tc134", CREATE_AUTOPAY_LINK, SELECT_BI_ANNUALLY, true, false, NEW_CREDIT, true},
        {"tc135", CREATE_AUTOPAY_LINK, SELECT_ANNUALLY, true, false, NEW_CREDIT, true},

        {"tc136", CREATE_AUTOPAY_LINK, SELECT_MONTHLY, true, false, NEW_DEBIT, true},
        {"tc137", CREATE_AUTOPAY_LINK, SELECT_QUARTERLY, true, false, NEW_DEBIT, true},
        {"tc138", CREATE_AUTOPAY_LINK, SELECT_BI_ANNUALLY, true, false, NEW_DEBIT, true},
        {"tc139", CREATE_AUTOPAY_LINK, SELECT_ANNUALLY, true, false, NEW_DEBIT, true},

        {"tc140", CREATE_AUTOPAY_LINK, SELECT_MONTHLY, true, true, NEW_BANK, false},
        {"tc141", CREATE_AUTOPAY_LINK, SELECT_QUARTERLY, true, true, NEW_BANK, false},
        {"tc142", CREATE_AUTOPAY_LINK, SELECT_BI_ANNUALLY, true, true, NEW_BANK, false},
        {"tc143", CREATE_AUTOPAY_LINK, SELECT_ANNUALLY, true, true, NEW_BANK, false},

        {"tc144", CREATE_AUTOPAY_LINK, SELECT_MONTHLY, true, true, NEW_CREDIT, false},
        {"tc145", CREATE_AUTOPAY_LINK, SELECT_QUARTERLY, true, true, NEW_CREDIT, false},
        {"tc146", CREATE_AUTOPAY_LINK, SELECT_BI_ANNUALLY, true, true, NEW_CREDIT, false},
        {"tc147", CREATE_AUTOPAY_LINK, SELECT_ANNUALLY, true, true, NEW_CREDIT, false},

        {"tc148", CREATE_AUTOPAY_LINK, SELECT_MONTHLY, true, true, NEW_DEBIT, false},
        {"tc149", CREATE_AUTOPAY_LINK, SELECT_QUARTERLY, true, true, NEW_DEBIT, false},
        {"tc150", CREATE_AUTOPAY_LINK, SELECT_BI_ANNUALLY, true, true, NEW_DEBIT, false},
        {"tc151", CREATE_AUTOPAY_LINK, SELECT_ANNUALLY, true, true, NEW_DEBIT, false},

        {"tc152", CREATE_AUTOPAY_LINK, SELECT_MONTHLY, true, true, NEW_CREDIT, true},
        {"tc153", CREATE_AUTOPAY_LINK, SELECT_QUARTERLY, true, true, NEW_CREDIT, true},
        {"tc154", CREATE_AUTOPAY_LINK, SELECT_BI_ANNUALLY, true, true, NEW_CREDIT, true},
        {"tc155", CREATE_AUTOPAY_LINK, SELECT_ANNUALLY, true, true, NEW_CREDIT, true},

        {"tc156", CREATE_AUTOPAY_LINK, SELECT_MONTHLY, true, true, NEW_DEBIT, true},
        {"tc157", CREATE_AUTOPAY_LINK, SELECT_QUARTERLY, true, true, NEW_DEBIT, true},
        {"tc158", CREATE_AUTOPAY_LINK, SELECT_BI_ANNUALLY, true, true, NEW_DEBIT, true},
        {"tc159", CREATE_AUTOPAY_LINK, SELECT_ANNUALLY, true, true, NEW_DEBIT, true},

        //EntryPoint = GET_STARTED_AUTOPAY_LINK
        {"tc160", GET_STARTED_AUTOPAY_LINK, SELECT_MONTHLY, false, false, NEW_BANK, false},
        {"tc161", GET_STARTED_AUTOPAY_LINK, SELECT_QUARTERLY, false, false, NEW_BANK, false},
        {"tc162", GET_STARTED_AUTOPAY_LINK, SELECT_BI_ANNUALLY, false, false, NEW_BANK, false},
        {"tc163", GET_STARTED_AUTOPAY_LINK, SELECT_ANNUALLY, false, false, NEW_BANK, false},

        {"tc164", GET_STARTED_AUTOPAY_LINK, SELECT_MONTHLY, false, false, NEW_CREDIT, false},
        {"tc165", GET_STARTED_AUTOPAY_LINK, SELECT_QUARTERLY, false, false, NEW_CREDIT, false},
        {"tc166", GET_STARTED_AUTOPAY_LINK, SELECT_BI_ANNUALLY, false, false, NEW_CREDIT, false},
        {"tc167", GET_STARTED_AUTOPAY_LINK, SELECT_ANNUALLY, false, false, NEW_CREDIT, false},

        {"tc168", GET_STARTED_AUTOPAY_LINK, SELECT_MONTHLY, false, false, NEW_DEBIT, false},
        {"tc169", GET_STARTED_AUTOPAY_LINK, SELECT_QUARTERLY, false, false, NEW_DEBIT, false},
        {"tc170", GET_STARTED_AUTOPAY_LINK, SELECT_BI_ANNUALLY, false, false, NEW_DEBIT, false},
        {"tc171", GET_STARTED_AUTOPAY_LINK, SELECT_ANNUALLY, false, false, NEW_DEBIT, false},

        {"tc172", GET_STARTED_AUTOPAY_LINK, SELECT_MONTHLY, false, false, NEW_CREDIT, true},
        {"tc173", GET_STARTED_AUTOPAY_LINK, SELECT_QUARTERLY, false, false, NEW_CREDIT, true},
        {"tc174", GET_STARTED_AUTOPAY_LINK, SELECT_BI_ANNUALLY, false, false, NEW_CREDIT, true},
        {"tc175", GET_STARTED_AUTOPAY_LINK, SELECT_ANNUALLY, false, false, NEW_CREDIT, true},

        {"tc176", GET_STARTED_AUTOPAY_LINK, SELECT_MONTHLY, false, false, NEW_DEBIT, true},
        {"tc177", GET_STARTED_AUTOPAY_LINK, SELECT_QUARTERLY, false, false, NEW_DEBIT, true},
        {"tc178", GET_STARTED_AUTOPAY_LINK, SELECT_BI_ANNUALLY, false, false, NEW_DEBIT, true},
        {"tc179", GET_STARTED_AUTOPAY_LINK, SELECT_ANNUALLY, false, false, NEW_DEBIT, true},

        {"tc180", GET_STARTED_AUTOPAY_LINK, SELECT_MONTHLY, false, true, NEW_BANK, false},
        {"tc181", GET_STARTED_AUTOPAY_LINK, SELECT_QUARTERLY, false, true, NEW_BANK, false},
        {"tc182", GET_STARTED_AUTOPAY_LINK, SELECT_BI_ANNUALLY, false, true, NEW_BANK, false},
        {"tc183", GET_STARTED_AUTOPAY_LINK, SELECT_ANNUALLY, false, true, NEW_BANK, false},

        {"tc184", GET_STARTED_AUTOPAY_LINK, SELECT_MONTHLY, false, true, NEW_CREDIT, false},
        {"tc185", GET_STARTED_AUTOPAY_LINK, SELECT_QUARTERLY, false, true, NEW_CREDIT, false},
        {"tc186", GET_STARTED_AUTOPAY_LINK, SELECT_BI_ANNUALLY, false, true, NEW_CREDIT, false},
        {"tc187", GET_STARTED_AUTOPAY_LINK, SELECT_ANNUALLY, false, true, NEW_CREDIT, false},

        {"tc188", GET_STARTED_AUTOPAY_LINK, SELECT_MONTHLY, false, true, NEW_DEBIT, false},
        {"tc189", GET_STARTED_AUTOPAY_LINK, SELECT_QUARTERLY, false, true, NEW_DEBIT, false},
        {"tc190", GET_STARTED_AUTOPAY_LINK, SELECT_BI_ANNUALLY, false, true, NEW_DEBIT, false},
        {"tc191", GET_STARTED_AUTOPAY_LINK, SELECT_ANNUALLY, false, true, NEW_DEBIT, false},

        {"tc192", GET_STARTED_AUTOPAY_LINK, SELECT_MONTHLY, false, true, NEW_CREDIT, true},
        {"tc193", GET_STARTED_AUTOPAY_LINK, SELECT_QUARTERLY, false, true, NEW_CREDIT, true},
        {"tc194", GET_STARTED_AUTOPAY_LINK, SELECT_BI_ANNUALLY, false, true, NEW_CREDIT, true},
        {"tc195", GET_STARTED_AUTOPAY_LINK, SELECT_ANNUALLY, false, true, NEW_CREDIT, true},

        {"tc196", GET_STARTED_AUTOPAY_LINK, SELECT_MONTHLY, false, true, NEW_DEBIT, true},
        {"tc197", GET_STARTED_AUTOPAY_LINK, SELECT_QUARTERLY, false, true, NEW_DEBIT, true},
        {"tc198", GET_STARTED_AUTOPAY_LINK, SELECT_BI_ANNUALLY, false, true, NEW_DEBIT, true},
        {"tc199", GET_STARTED_AUTOPAY_LINK, SELECT_ANNUALLY, false, true, NEW_DEBIT, true},

        {"tc200", GET_STARTED_AUTOPAY_LINK, SELECT_MONTHLY, true, false, NEW_BANK, false},
        {"tc201", GET_STARTED_AUTOPAY_LINK, SELECT_QUARTERLY, true, false, NEW_BANK, false},
        {"tc202", GET_STARTED_AUTOPAY_LINK, SELECT_BI_ANNUALLY, true, false, NEW_BANK, false},
        {"tc203", GET_STARTED_AUTOPAY_LINK, SELECT_ANNUALLY, true, false, NEW_BANK, false},

        {"tc204", GET_STARTED_AUTOPAY_LINK, SELECT_MONTHLY, true, false, NEW_CREDIT, false},
        {"tc205", GET_STARTED_AUTOPAY_LINK, SELECT_QUARTERLY, true, false, NEW_CREDIT, false},
        {"tc206", GET_STARTED_AUTOPAY_LINK, SELECT_BI_ANNUALLY, true, false, NEW_CREDIT, false},
        {"tc207", GET_STARTED_AUTOPAY_LINK, SELECT_ANNUALLY, true, false, NEW_CREDIT, false},

        {"tc208", GET_STARTED_AUTOPAY_LINK, SELECT_MONTHLY, true, false, NEW_DEBIT, false},
        {"tc209", GET_STARTED_AUTOPAY_LINK, SELECT_QUARTERLY, true, false, NEW_DEBIT, false},
        {"tc210", GET_STARTED_AUTOPAY_LINK, SELECT_BI_ANNUALLY, true, false, NEW_DEBIT, false},
        {"tc211", GET_STARTED_AUTOPAY_LINK, SELECT_ANNUALLY, true, false, NEW_DEBIT, false},

        {"tc212", GET_STARTED_AUTOPAY_LINK, SELECT_MONTHLY, true, false, NEW_CREDIT, true},
        {"tc213", GET_STARTED_AUTOPAY_LINK, SELECT_QUARTERLY, true, false, NEW_CREDIT, true},
        {"tc214", GET_STARTED_AUTOPAY_LINK, SELECT_BI_ANNUALLY, true, false, NEW_CREDIT, true},
        {"tc215", GET_STARTED_AUTOPAY_LINK, SELECT_ANNUALLY, true, false, NEW_CREDIT, true},

        {"tc216", GET_STARTED_AUTOPAY_LINK, SELECT_MONTHLY, true, false, NEW_DEBIT, true},
        {"tc217", GET_STARTED_AUTOPAY_LINK, SELECT_QUARTERLY, true, false, NEW_DEBIT, true},
        {"tc218", GET_STARTED_AUTOPAY_LINK, SELECT_BI_ANNUALLY, true, false, NEW_DEBIT, true},
        {"tc2", GET_STARTED_AUTOPAY_LINK, SELECT_ANNUALLY, true, false, NEW_DEBIT, true},

        {"tc219", GET_STARTED_AUTOPAY_LINK, SELECT_MONTHLY, true, true, NEW_BANK, false},
        {"tc220", GET_STARTED_AUTOPAY_LINK, SELECT_QUARTERLY, true, true, NEW_BANK, false},
        {"tc221", GET_STARTED_AUTOPAY_LINK, SELECT_BI_ANNUALLY, true, true, NEW_BANK, false},
        {"tc222", GET_STARTED_AUTOPAY_LINK, SELECT_ANNUALLY, true, true, NEW_BANK, false},

        {"tc223", GET_STARTED_AUTOPAY_LINK, SELECT_MONTHLY, true, true, NEW_CREDIT, false},
        {"tc224", GET_STARTED_AUTOPAY_LINK, SELECT_QUARTERLY, true, true, NEW_CREDIT, false},
        {"tc225", GET_STARTED_AUTOPAY_LINK, SELECT_BI_ANNUALLY, true, true, NEW_CREDIT, false},
        {"tc226", GET_STARTED_AUTOPAY_LINK, SELECT_ANNUALLY, true, true, NEW_CREDIT, false},

        {"tc227", GET_STARTED_AUTOPAY_LINK, SELECT_MONTHLY, true, true, NEW_DEBIT, false},
        {"tc228", GET_STARTED_AUTOPAY_LINK, SELECT_QUARTERLY, true, true, NEW_DEBIT, false},
        {"tc229", GET_STARTED_AUTOPAY_LINK, SELECT_BI_ANNUALLY, true, true, NEW_DEBIT, false},
        {"tc230", GET_STARTED_AUTOPAY_LINK, SELECT_ANNUALLY, true, true, NEW_DEBIT, false},

        {"tc231", GET_STARTED_AUTOPAY_LINK, SELECT_MONTHLY, true, true, NEW_CREDIT, true},
        {"tc232", GET_STARTED_AUTOPAY_LINK, SELECT_QUARTERLY, true, true, NEW_CREDIT, true},
        {"tc233", GET_STARTED_AUTOPAY_LINK, SELECT_BI_ANNUALLY, true, true, NEW_CREDIT, true},
        {"tc234", GET_STARTED_AUTOPAY_LINK, SELECT_ANNUALLY, true, true, NEW_CREDIT, true},

        {"tc235", GET_STARTED_AUTOPAY_LINK, SELECT_MONTHLY, true, true, NEW_DEBIT, true},
        {"tc236", GET_STARTED_AUTOPAY_LINK, SELECT_QUARTERLY, true, true, NEW_DEBIT, true},
        {"tc237", GET_STARTED_AUTOPAY_LINK, SELECT_BI_ANNUALLY, true, true, NEW_DEBIT, true},
        {"tc238", GET_STARTED_AUTOPAY_LINK, SELECT_ANNUALLY, true, true, NEW_DEBIT, true}
    };
  }

  /**
   * Provides data required to run various kinds of test combinations for PM.
   *
   * @return data
   */
  @DataProvider(name = "vapDataPm", parallel = true)
  public Object[][] dataPm() {

    return new Object[][]{
        //Test variation no., EntryPoint, Frequency, Indefinite, MaxLimit, PaymentType, ExpressPay

        //EntryPoint = RESIDENTS_LIST
        {"tc1", RESIDENTS_LIST, SELECT_MONTHLY, false, false, NEW_BANK, false},
        {"tc2", RESIDENTS_LIST, SELECT_QUARTERLY, false, false, NEW_BANK, false},
        {"tc3", RESIDENTS_LIST, SELECT_BI_ANNUALLY, false, false, NEW_BANK, false},
        {"tc4", RESIDENTS_LIST, SELECT_ANNUALLY, false, false, NEW_BANK, false},

        {"tc5", RESIDENTS_LIST, SELECT_MONTHLY, false, false, NEW_CREDIT, false},
        {"tc6", RESIDENTS_LIST, SELECT_QUARTERLY, false, false, NEW_CREDIT, false},
        {"tc7", RESIDENTS_LIST, SELECT_BI_ANNUALLY, false, false, NEW_CREDIT, false},
        {"tc8", RESIDENTS_LIST, SELECT_ANNUALLY, false, false, NEW_CREDIT, false},

        {"tc9", RESIDENTS_LIST, SELECT_MONTHLY, false, false, NEW_DEBIT, false},
        {"tc10", RESIDENTS_LIST, SELECT_QUARTERLY, false, false, NEW_DEBIT, false},
        {"tc11", RESIDENTS_LIST, SELECT_BI_ANNUALLY, false, false, NEW_DEBIT, false},
        {"tc12", RESIDENTS_LIST, SELECT_ANNUALLY, false, false, NEW_DEBIT, false},

        {"tc13", RESIDENTS_LIST, SELECT_MONTHLY, false, false, NEW_CREDIT, true},
        {"tc14", RESIDENTS_LIST, SELECT_QUARTERLY, false, false, NEW_CREDIT, true},
        {"tc15", RESIDENTS_LIST, SELECT_BI_ANNUALLY, false, false, NEW_CREDIT, true},
        {"tc16", RESIDENTS_LIST, SELECT_ANNUALLY, false, false, NEW_CREDIT, true},

        {"tc17", RESIDENTS_LIST, SELECT_MONTHLY, false, false, NEW_DEBIT, true},
        {"tc18", RESIDENTS_LIST, SELECT_QUARTERLY, false, false, NEW_DEBIT, true},
        {"tc19", RESIDENTS_LIST, SELECT_BI_ANNUALLY, false, false, NEW_DEBIT, true},
        {"tc20", RESIDENTS_LIST, SELECT_ANNUALLY, false, false, NEW_DEBIT, true},

        {"tc21", RESIDENTS_LIST, SELECT_MONTHLY, false, true, NEW_BANK, false},
        {"tc22", RESIDENTS_LIST, SELECT_QUARTERLY, false, true, NEW_BANK, false},
        {"tc23", RESIDENTS_LIST, SELECT_BI_ANNUALLY, false, true, NEW_BANK, false},
        {"tc24", RESIDENTS_LIST, SELECT_ANNUALLY, false, true, NEW_BANK, false},

        {"tc25", RESIDENTS_LIST, SELECT_MONTHLY, false, true, NEW_CREDIT, false},
        {"tc26", RESIDENTS_LIST, SELECT_QUARTERLY, false, true, NEW_CREDIT, false},
        {"tc27", RESIDENTS_LIST, SELECT_BI_ANNUALLY, false, true, NEW_CREDIT, false},
        {"tc28", RESIDENTS_LIST, SELECT_ANNUALLY, false, true, NEW_CREDIT, false},

        {"tc29", RESIDENTS_LIST, SELECT_MONTHLY, false, true, NEW_DEBIT, false},
        {"tc30", RESIDENTS_LIST, SELECT_QUARTERLY, false, true, NEW_DEBIT, false},
        {"tc31", RESIDENTS_LIST, SELECT_BI_ANNUALLY, false, true, NEW_DEBIT, false},
        {"tc32", RESIDENTS_LIST, SELECT_ANNUALLY, false, true, NEW_DEBIT, false},

        {"tc33", RESIDENTS_LIST, SELECT_MONTHLY, false, true, NEW_CREDIT, true},
        {"tc34", RESIDENTS_LIST, SELECT_QUARTERLY, false, true, NEW_CREDIT, true},
        {"tc35", RESIDENTS_LIST, SELECT_BI_ANNUALLY, false, true, NEW_CREDIT, true},
        {"tc36", RESIDENTS_LIST, SELECT_ANNUALLY, false, true, NEW_CREDIT, true},

        {"tc37", RESIDENTS_LIST, SELECT_MONTHLY, false, true, NEW_DEBIT, true},
        {"tc38", RESIDENTS_LIST, SELECT_QUARTERLY, false, true, NEW_DEBIT, true},
        {"tc39", RESIDENTS_LIST, SELECT_BI_ANNUALLY, false, true, NEW_DEBIT, true},
        {"tc40", RESIDENTS_LIST, SELECT_ANNUALLY, false, true, NEW_DEBIT, true},

        {"tc41", RESIDENTS_LIST, SELECT_MONTHLY, true, false, NEW_BANK, false},
        {"tc42", RESIDENTS_LIST, SELECT_QUARTERLY, true, false, NEW_BANK, false},
        {"tc43", RESIDENTS_LIST, SELECT_BI_ANNUALLY, true, false, NEW_BANK, false},
        {"tc44", RESIDENTS_LIST, SELECT_ANNUALLY, true, false, NEW_BANK, false},

        {"tc45", RESIDENTS_LIST, SELECT_MONTHLY, true, false, NEW_CREDIT, false},
        {"tc46", RESIDENTS_LIST, SELECT_QUARTERLY, true, false, NEW_CREDIT, false},
        {"tc47", RESIDENTS_LIST, SELECT_BI_ANNUALLY, true, false, NEW_CREDIT, false},
        {"tc48", RESIDENTS_LIST, SELECT_ANNUALLY, true, false, NEW_CREDIT, false},

        {"tc49", RESIDENTS_LIST, SELECT_MONTHLY, true, false, NEW_DEBIT, false},
        {"tc50", RESIDENTS_LIST, SELECT_QUARTERLY, true, false, NEW_DEBIT, false},
        {"tc51", RESIDENTS_LIST, SELECT_BI_ANNUALLY, true, false, NEW_DEBIT, false},
        {"tc52", RESIDENTS_LIST, SELECT_ANNUALLY, true, false, NEW_DEBIT, false},

        {"tc53", RESIDENTS_LIST, SELECT_MONTHLY, true, false, NEW_CREDIT, true},
        {"tc54", RESIDENTS_LIST, SELECT_QUARTERLY, true, false, NEW_CREDIT, true},
        {"tc55", RESIDENTS_LIST, SELECT_BI_ANNUALLY, true, false, NEW_CREDIT, true},
        {"tc56", RESIDENTS_LIST, SELECT_ANNUALLY, true, false, NEW_CREDIT, true},

        {"tc57", RESIDENTS_LIST, SELECT_MONTHLY, true, false, NEW_DEBIT, true},
        {"tc58", RESIDENTS_LIST, SELECT_QUARTERLY, true, false, NEW_DEBIT, true},
        {"tc59", RESIDENTS_LIST, SELECT_BI_ANNUALLY, true, false, NEW_DEBIT, true},
        {"tc60", RESIDENTS_LIST, SELECT_ANNUALLY, true, false, NEW_DEBIT, true},

        {"tc61", RESIDENTS_LIST, SELECT_MONTHLY, true, true, NEW_BANK, false},
        {"tc62", RESIDENTS_LIST, SELECT_QUARTERLY, true, true, NEW_BANK, false},
        {"tc63", RESIDENTS_LIST, SELECT_BI_ANNUALLY, true, true, NEW_BANK, false},
        {"tc64", RESIDENTS_LIST, SELECT_ANNUALLY, true, true, NEW_BANK, false},

        {"tc65", RESIDENTS_LIST, SELECT_MONTHLY, true, true, NEW_CREDIT, false},
        {"tc66", RESIDENTS_LIST, SELECT_QUARTERLY, true, true, NEW_CREDIT, false},
        {"tc67", RESIDENTS_LIST, SELECT_BI_ANNUALLY, true, true, NEW_CREDIT, false},
        {"tc68", RESIDENTS_LIST, SELECT_ANNUALLY, true, true, NEW_CREDIT, false},

        {"tc69", RESIDENTS_LIST, SELECT_MONTHLY, true, true, NEW_DEBIT, false},
        {"tc70", RESIDENTS_LIST, SELECT_QUARTERLY, true, true, NEW_DEBIT, false},
        {"tc71", RESIDENTS_LIST, SELECT_BI_ANNUALLY, true, true, NEW_DEBIT, false},
        {"tc72", RESIDENTS_LIST, SELECT_ANNUALLY, true, true, NEW_DEBIT, false},

        {"tc73", RESIDENTS_LIST, SELECT_MONTHLY, true, true, NEW_CREDIT, true},
        {"tc74", RESIDENTS_LIST, SELECT_QUARTERLY, true, true, NEW_CREDIT, true},
        {"tc75", RESIDENTS_LIST, SELECT_BI_ANNUALLY, true, true, NEW_CREDIT, true},
        {"tc76", RESIDENTS_LIST, SELECT_ANNUALLY, true, true, NEW_CREDIT, true},

        {"tc77", RESIDENTS_LIST, SELECT_MONTHLY, true, true, NEW_DEBIT, true},
        {"tc78", RESIDENTS_LIST, SELECT_QUARTERLY, true, true, NEW_DEBIT, true},
        {"tc79", RESIDENTS_LIST, SELECT_BI_ANNUALLY, true, true, NEW_DEBIT, true},
        {"tc80", RESIDENTS_LIST, SELECT_ANNUALLY, true, true, NEW_DEBIT, true},

        //EntryPoint = PAYMENTS_DROPDOWN
        {"tc81", PAYMENTS_DROPDOWN, SELECT_MONTHLY, false, false, NEW_BANK, false},
        {"tc82", PAYMENTS_DROPDOWN, SELECT_QUARTERLY, false, false, NEW_BANK, false},
        {"tc83", PAYMENTS_DROPDOWN, SELECT_BI_ANNUALLY, false, false, NEW_BANK, false},
        {"tc84", PAYMENTS_DROPDOWN, SELECT_ANNUALLY, false, false, NEW_BANK, false},

        {"tc85", PAYMENTS_DROPDOWN, SELECT_MONTHLY, false, false, NEW_CREDIT, false},
        {"tc86", PAYMENTS_DROPDOWN, SELECT_QUARTERLY, false, false, NEW_CREDIT, false},
        {"tc87", PAYMENTS_DROPDOWN, SELECT_BI_ANNUALLY, false, false, NEW_CREDIT, false},
        {"tc88", PAYMENTS_DROPDOWN, SELECT_ANNUALLY, false, false, NEW_CREDIT, false},

        {"tc89", PAYMENTS_DROPDOWN, SELECT_MONTHLY, false, false, NEW_DEBIT, false},
        {"tc90", PAYMENTS_DROPDOWN, SELECT_QUARTERLY, false, false, NEW_DEBIT, false},
        {"tc91", PAYMENTS_DROPDOWN, SELECT_BI_ANNUALLY, false, false, NEW_DEBIT, false},
        {"tc92", PAYMENTS_DROPDOWN, SELECT_ANNUALLY, false, false, NEW_DEBIT, false},

        {"tc93", PAYMENTS_DROPDOWN, SELECT_MONTHLY, false, false, NEW_CREDIT, true},
        {"tc94", PAYMENTS_DROPDOWN, SELECT_QUARTERLY, false, false, NEW_CREDIT, true},
        {"tc95", PAYMENTS_DROPDOWN, SELECT_BI_ANNUALLY, false, false, NEW_CREDIT, true},
        {"tc96", PAYMENTS_DROPDOWN, SELECT_ANNUALLY, false, false, NEW_CREDIT, true},

        {"tc97", PAYMENTS_DROPDOWN, SELECT_MONTHLY, false, false, NEW_DEBIT, true},
        {"tc98", PAYMENTS_DROPDOWN, SELECT_QUARTERLY, false, false, NEW_DEBIT, true},
        {"tc99", PAYMENTS_DROPDOWN, SELECT_BI_ANNUALLY, false, false, NEW_DEBIT, true},
        {"tc100", PAYMENTS_DROPDOWN, SELECT_ANNUALLY, false, false, NEW_DEBIT, true},

        {"tc101", PAYMENTS_DROPDOWN, SELECT_MONTHLY, false, true, NEW_BANK, false},
        {"tc102", PAYMENTS_DROPDOWN, SELECT_QUARTERLY, false, true, NEW_BANK, false},
        {"tc103", PAYMENTS_DROPDOWN, SELECT_BI_ANNUALLY, false, true, NEW_BANK, false},
        {"tc104", PAYMENTS_DROPDOWN, SELECT_ANNUALLY, false, true, NEW_BANK, false},

        {"tc105", PAYMENTS_DROPDOWN, SELECT_MONTHLY, false, true, NEW_CREDIT, false},
        {"tc106", PAYMENTS_DROPDOWN, SELECT_QUARTERLY, false, true, NEW_CREDIT, false},
        {"tc107", PAYMENTS_DROPDOWN, SELECT_BI_ANNUALLY, false, true, NEW_CREDIT, false},
        {"tc108", PAYMENTS_DROPDOWN, SELECT_ANNUALLY, false, true, NEW_CREDIT, false},

        {"tc109", PAYMENTS_DROPDOWN, SELECT_MONTHLY, false, true, NEW_DEBIT, false},
        {"tc110", PAYMENTS_DROPDOWN, SELECT_QUARTERLY, false, true, NEW_DEBIT, false},
        {"tc111", PAYMENTS_DROPDOWN, SELECT_BI_ANNUALLY, false, true, NEW_DEBIT, false},
        {"tc112", PAYMENTS_DROPDOWN, SELECT_ANNUALLY, false, true, NEW_DEBIT, false},

        {"tc113", PAYMENTS_DROPDOWN, SELECT_MONTHLY, false, true, NEW_CREDIT, true},
        {"tc114", PAYMENTS_DROPDOWN, SELECT_QUARTERLY, false, true, NEW_CREDIT, true},
        {"tc115", PAYMENTS_DROPDOWN, SELECT_BI_ANNUALLY, false, true, NEW_CREDIT, true},
        {"tc116", PAYMENTS_DROPDOWN, SELECT_ANNUALLY, false, true, NEW_CREDIT, true},

        {"tc117", PAYMENTS_DROPDOWN, SELECT_MONTHLY, false, true, NEW_DEBIT, true},
        {"tc118", PAYMENTS_DROPDOWN, SELECT_QUARTERLY, false, true, NEW_DEBIT, true},
        {"tc119", PAYMENTS_DROPDOWN, SELECT_BI_ANNUALLY, false, true, NEW_DEBIT, true},
        {"tc120", PAYMENTS_DROPDOWN, SELECT_ANNUALLY, false, true, NEW_DEBIT, true},

        {"tc121", PAYMENTS_DROPDOWN, SELECT_MONTHLY, true, false, NEW_BANK, false},
        {"tc122", PAYMENTS_DROPDOWN, SELECT_QUARTERLY, true, false, NEW_BANK, false},
        {"tc123", PAYMENTS_DROPDOWN, SELECT_BI_ANNUALLY, true, false, NEW_BANK, false},
        {"tc124", PAYMENTS_DROPDOWN, SELECT_ANNUALLY, true, false, NEW_BANK, false},

        {"tc125", PAYMENTS_DROPDOWN, SELECT_MONTHLY, true, false, NEW_CREDIT, false},
        {"tc126", PAYMENTS_DROPDOWN, SELECT_QUARTERLY, true, false, NEW_CREDIT, false},
        {"tc127", PAYMENTS_DROPDOWN, SELECT_BI_ANNUALLY, true, false, NEW_CREDIT, false},
        {"tc128", PAYMENTS_DROPDOWN, SELECT_ANNUALLY, true, false, NEW_CREDIT, false},

        {"tc129", PAYMENTS_DROPDOWN, SELECT_MONTHLY, true, false, NEW_DEBIT, false},
        {"tc130", PAYMENTS_DROPDOWN, SELECT_QUARTERLY, true, false, NEW_DEBIT, false},
        {"tc131", PAYMENTS_DROPDOWN, SELECT_BI_ANNUALLY, true, false, NEW_DEBIT, false},
        {"tc132", PAYMENTS_DROPDOWN, SELECT_ANNUALLY, true, false, NEW_DEBIT, false},

        {"tc133", PAYMENTS_DROPDOWN, SELECT_MONTHLY, true, false, NEW_CREDIT, true},
        {"tc134", PAYMENTS_DROPDOWN, SELECT_QUARTERLY, true, false, NEW_CREDIT, true},
        {"tc135", PAYMENTS_DROPDOWN, SELECT_BI_ANNUALLY, true, false, NEW_CREDIT, true},
        {"tc136", PAYMENTS_DROPDOWN, SELECT_ANNUALLY, true, false, NEW_CREDIT, true},

        {"tc137", PAYMENTS_DROPDOWN, SELECT_MONTHLY, true, false, NEW_DEBIT, true},
        {"tc138", PAYMENTS_DROPDOWN, SELECT_QUARTERLY, true, false, NEW_DEBIT, true},
        {"tc139", PAYMENTS_DROPDOWN, SELECT_BI_ANNUALLY, true, false, NEW_DEBIT, true},
        {"tc140", PAYMENTS_DROPDOWN, SELECT_ANNUALLY, true, false, NEW_DEBIT, true},

        {"tc141", PAYMENTS_DROPDOWN, SELECT_MONTHLY, true, true, NEW_BANK, false},
        {"tc142", PAYMENTS_DROPDOWN, SELECT_QUARTERLY, true, true, NEW_BANK, false},
        {"tc143", PAYMENTS_DROPDOWN, SELECT_BI_ANNUALLY, true, true, NEW_BANK, false},
        {"tc144", PAYMENTS_DROPDOWN, SELECT_ANNUALLY, true, true, NEW_BANK, false},

        {"tc145", PAYMENTS_DROPDOWN, SELECT_MONTHLY, true, true, NEW_CREDIT, false},
        {"tc146", PAYMENTS_DROPDOWN, SELECT_QUARTERLY, true, true, NEW_CREDIT, false},
        {"tc147", PAYMENTS_DROPDOWN, SELECT_BI_ANNUALLY, true, true, NEW_CREDIT, false},
        {"tc148", PAYMENTS_DROPDOWN, SELECT_ANNUALLY, true, true, NEW_CREDIT, false},

        {"tc149", PAYMENTS_DROPDOWN, SELECT_MONTHLY, true, true, NEW_DEBIT, false},
        {"tc150", PAYMENTS_DROPDOWN, SELECT_QUARTERLY, true, true, NEW_DEBIT, false},
        {"tc151", PAYMENTS_DROPDOWN, SELECT_BI_ANNUALLY, true, true, NEW_DEBIT, false},
        {"tc152", PAYMENTS_DROPDOWN, SELECT_ANNUALLY, true, true, NEW_DEBIT, false},

        {"tc153", PAYMENTS_DROPDOWN, SELECT_MONTHLY, true, true, NEW_CREDIT, true},
        {"tc154", PAYMENTS_DROPDOWN, SELECT_QUARTERLY, true, true, NEW_CREDIT, true},
        {"tc155", PAYMENTS_DROPDOWN, SELECT_BI_ANNUALLY, true, true, NEW_CREDIT, true},
        {"tc156", PAYMENTS_DROPDOWN, SELECT_ANNUALLY, true, true, NEW_CREDIT, true},

        {"tc157", PAYMENTS_DROPDOWN, SELECT_MONTHLY, true, true, NEW_DEBIT, true},
        {"tc158", PAYMENTS_DROPDOWN, SELECT_QUARTERLY, true, true, NEW_DEBIT, true},
        {"tc159", PAYMENTS_DROPDOWN, SELECT_BI_ANNUALLY, true, true, NEW_DEBIT, true},
        {"tc160", PAYMENTS_DROPDOWN, SELECT_ANNUALLY, true, true, NEW_DEBIT, true}
    };
  }
}
