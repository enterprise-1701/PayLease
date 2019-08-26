package com.paylease.app.qa.e2e.tests.pm;

import static com.paylease.app.qa.framework.features.PaymentFlow.SCHEDULE_FIXED_AUTO;
import static com.paylease.app.qa.framework.pages.pm.PmMenu.PAYMENTS_DROPDOWN;
import static com.paylease.app.qa.framework.pages.pm.PmResidentListPage.RESIDENTS_LIST;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.paymentflow.SchedulePage;
import com.paylease.app.qa.framework.pages.pm.PmFapListPage;
import com.paylease.app.qa.framework.pages.pm.PmLoginPage;
import com.paylease.app.qa.framework.pages.pm.PmLogoutBar;
import com.paylease.app.qa.framework.utility.listeners.Retry;
import com.paylease.app.qa.testbase.PaymentBase;
import com.paylease.app.qa.testbase.dataproviders.FixedAutoPayTestDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FixedAutoPayTest extends ScriptBase {

  private static final String REGION = "Pm";
  private static final String FEATURE = "createFap";

  //--------------------------------FIXED AUTOPAY TESTS---------------------------------------------

  @Test(dataProvider = "fapDataPm", dataProviderClass = FixedAutoPayTestDataProvider.class, groups =
      {"e2e"}, retryAnalyzer = Retry.class)
  public void fapPm(String testVariationNo, String entryPoint, String frequency,
      boolean isIndefiniteChecked, String paymentType, boolean expressPay) {
    Logger.info("PM fap, where test variation: " + testVariationNo + " with " + paymentType
        + ", entry point: " + entryPoint + ", " + "express pay: " + expressPay + ", frequency: "
        + frequency + ", indefinite: " + isIndefiniteChecked);

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "testSetupFapFlow");
    testSetupPage.open();
    final String pmEmail = testSetupPage.getString("pmEmail");
    final String residentId = testSetupPage.getString("residentId");

    PmLoginPage pmLoginPage = new PmLoginPage();

    pmLoginPage.open();
    pmLoginPage.login(pmEmail);

    pmFapPaymentActions(residentId, entryPoint, frequency, isIndefiniteChecked, paymentType,
        expressPay);
  }

  //--------------------------------TEST METHOD-----------------------------------------------------

  /**
   * Perform PM fixed autopay actions.
   *
   * @param residentId resident ID
   * @param entryPoint entry point
   * @param frequency frequency of payment
   * @param isIndefiniteChecked boolean if indefinite is checked or not
   * @param paymentType type of payment
   * @param expressPay using express pay or not
   */
  public void pmFapPaymentActions(String residentId, String entryPoint, String frequency,
      boolean isIndefiniteChecked, String paymentType, boolean expressPay) {
    PaymentBase paymentBase = new PaymentBase();

    if (entryPoint.equals(RESIDENTS_LIST)) {
      paymentBase.selectResidentFromResidentListAndBeginPayment(residentId, SCHEDULE_FIXED_AUTO);
    } else if (entryPoint.equals(PAYMENTS_DROPDOWN)) {
      paymentBase
          .selectResidentFromPaymentsDropDownAndBeginPayment(residentId, SCHEDULE_FIXED_AUTO);
    }

    paymentBase.fillAndSubmitPaymentAmount(false);

    SchedulePage schedulePage = new SchedulePage();
    schedulePage.prepField(SchedulePage.FIELD_FREQUENCY, frequency);
    schedulePage.prepField(SchedulePage.FIELD_INDEFINITE, isIndefiniteChecked);
    schedulePage.fillAndSubmitPaymentScheduleDetails();

    paymentBase.selectPaymentMethod(paymentType, expressPay);
    paymentBase.reviewAndSubmit();

    PmFapListPage pmFapListPage = new PmFapListPage();

    Assert.assertTrue(pmFapListPage.pageIsLoaded(), "Should be on Fixed AutoPay list page");

    PmLogoutBar pmLogoutBar = new PmLogoutBar();

    pmLogoutBar.clickLogoutButton();
  }
}
