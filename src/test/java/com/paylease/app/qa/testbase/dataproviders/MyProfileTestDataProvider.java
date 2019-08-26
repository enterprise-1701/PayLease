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

public class MyProfileTestDataProvider {

  /**
   * Provides data required to run various kinds of test combinations for Resident.
   *
   * @return data
   */
  @DataProvider(name = "myProfileData", parallel = true)
  public Object[][] data() {

    return new Object[][]{
        //TestCase, myProfileMenuDisabled

        {"ide_resui_general_tc01", true},
        {"ide_resui_general_tc02", false},
    };
  }
}
