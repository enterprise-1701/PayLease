package com.paylease.app.qa.e2e.tests.pm;

import static com.paylease.app.qa.framework.features.PaymentFlow.SCHEDULE_VARIABLE_AUTO;
import static com.paylease.app.qa.framework.pages.pm.PmMenu.PAYMENTS_DROPDOWN;
import static com.paylease.app.qa.framework.pages.pm.PmResidentListPage.RESIDENTS_LIST;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.paymentflow.SchedulePage;
import com.paylease.app.qa.framework.pages.pm.PmLoginPage;
import com.paylease.app.qa.framework.pages.pm.PmLogoutBar;
import com.paylease.app.qa.framework.pages.pm.PmVapListPage;
import com.paylease.app.qa.framework.utility.listeners.Retry;
import com.paylease.app.qa.testbase.PaymentBase;
import com.paylease.app.qa.testbase.dataproviders.VariableAutoPayTestDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

public class VariableAutoPayTest extends ScriptBase {

  private static final String REGION = "Pm";
  private static final String FEATURE = "createVap";

  //--------------------------------VARIABLE AUTOPAY TESTS------------------------------------------

  @Test(dataProvider = "vapDataPm", dataProviderClass = VariableAutoPayTestDataProvider.class,
      groups = {"e2e"}, retryAnalyzer = Retry.class)
  public void vapPm(String testVariationNo, String entryPoint, String frequency,
      boolean isIndefiniteChecked, boolean isMaxLimitChecked, String paymentType,
      boolean expressPay) {
    Logger.info("PM vap, where test variation: " + testVariationNo + " with " + paymentType
        + ", entry point: " + entryPoint + ", express pay: " + expressPay + ", frequency: "
        + frequency + ", indefinite: " + isIndefiniteChecked + " and Max Limit: "
        + isMaxLimitChecked);

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "testSetupVapFlow");
    testSetupPage.open();
    final String pmEmail = testSetupPage.getString("pmEmail");
    final String residentId = testSetupPage.getString("residentId");

    PmLoginPage pmLoginPage = new PmLoginPage();

    pmLoginPage.open();
    pmLoginPage.login(pmEmail);

    pmVapPaymentActions(residentId, entryPoint, frequency, isIndefiniteChecked,
        isMaxLimitChecked, paymentType, expressPay);
  }

  //--------------------------------TEST METHOD-----------------------------------------------------

  /**
   * Perform PM vap payment actions.
   *
   * @param residentId resident ID
   * @param entryPoint entry point
   * @param frequency frequency
   * @param isIndefiniteChecked boolean
   * @param isMaxLimitChecked boolean
   * @param paymentType payment type
   * @param expressPay express pay
   */
  public void pmVapPaymentActions(String residentId, String entryPoint, String frequency,
      boolean isIndefiniteChecked, boolean isMaxLimitChecked, String paymentType,
      boolean expressPay) {
    PaymentBase paymentBase = new PaymentBase();

    if (entryPoint.equals(RESIDENTS_LIST)) {
      paymentBase.selectResidentFromResidentListAndBeginPayment(residentId, SCHEDULE_VARIABLE_AUTO);
    } else if (entryPoint.equals(PAYMENTS_DROPDOWN)) {
      paymentBase
          .selectResidentFromPaymentsDropDownAndBeginPayment(residentId, SCHEDULE_VARIABLE_AUTO);
    }

    SchedulePage schedulePage = new SchedulePage();
    schedulePage.prepField(SchedulePage.FIELD_FREQUENCY, frequency);
    schedulePage.prepField(SchedulePage.FIELD_INDEFINITE, isIndefiniteChecked);
    schedulePage.prepField(SchedulePage.FIELD_MAX_LIMIT_ENABLED, isMaxLimitChecked);
    schedulePage.fillAndSubmitPaymentScheduleDetails();

    paymentBase.selectPaymentMethod(paymentType, expressPay);
    paymentBase.reviewAndSubmit();

    PmVapListPage pmVapListPage = new PmVapListPage();

    Assert.assertTrue(pmVapListPage.pageIsLoaded(), "Should be on Variable AutoPay list page");

    PmLogoutBar pmLogoutBar = new PmLogoutBar();

    pmLogoutBar.clickLogoutButton();
  }
}
