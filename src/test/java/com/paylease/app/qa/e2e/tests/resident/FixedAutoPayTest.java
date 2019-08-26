package com.paylease.app.qa.e2e.tests.resident;

import static com.paylease.app.qa.framework.pages.resident.ResHomePage.CREATE_AUTOPAY_LINK;
import static com.paylease.app.qa.framework.pages.resident.ResHomePage.GET_STARTED_AUTOPAY_LINK;
import static com.paylease.app.qa.framework.pages.resident.ResidentMenuItems.AUTOPAY_TAB;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.paymentflow.SchedulePage;
import com.paylease.app.qa.framework.pages.resident.ResAutoPayListPage;
import com.paylease.app.qa.framework.pages.resident.ResFapListPage;
import com.paylease.app.qa.framework.pages.resident.ResHomePage;
import com.paylease.app.qa.framework.pages.resident.ResLoginPage;
import com.paylease.app.qa.framework.pages.resident.ResidentChooseAutopayPage;
import com.paylease.app.qa.framework.pages.resident.ResidentLogoutBar;
import com.paylease.app.qa.framework.pages.resident.ResidentMenuItems;
import com.paylease.app.qa.framework.utility.listeners.Retry;
import com.paylease.app.qa.testbase.PaymentBase;
import com.paylease.app.qa.testbase.dataproviders.FixedAutoPayTestDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FixedAutoPayTest extends ScriptBase {

  private static final String REGION = "resident";
  private static final String FEATURE = "fixedAutopay";

  //---------------------------------------------FAP TESTS------------------------------------------

  @Test(dataProvider = "fapDataResident", dataProviderClass = FixedAutoPayTestDataProvider.class,
      groups = {"e2e"}, retryAnalyzer = Retry.class)
  public void fapResident(String testVariationNo, String entryPoint, String frequency,
      boolean isIndefiniteChecked, String paymentType, boolean expressPay) {
    Logger.info("Resident fap, where test variation: " + testVariationNo + " with " + paymentType
        + ", entry point: " + entryPoint + ", express pay: " + expressPay + ", frequency: "
        + frequency + ", indefinite: " + isIndefiniteChecked);

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "testSetupFapFlow");
    testSetupPage.open();
    final String residentEmail = testSetupPage.getString("residentEmail");

    ResLoginPage resLoginPage = new ResLoginPage();

    resLoginPage.open();
    resLoginPage.login(residentEmail, null);

    residentFapPaymentActions(entryPoint, frequency, isIndefiniteChecked, paymentType,
        expressPay);
  }

  //--------------------------------TEST METHOD-----------------------------------------------------

  /**
   * Perform Resident fixed autopay actions.
   *
   * @param entryPoint entry point
   * @param frequency frequency of payment
   * @param isIndefiniteChecked boolean if indefinite is checked or not
   * @param paymentType type of payment
   * @param expressPay using express pay or not
   */
  public void residentFapPaymentActions(String entryPoint, String frequency,
      boolean isIndefiniteChecked, String paymentType, boolean expressPay) {
    PaymentBase paymentBase = new PaymentBase();

    ResHomePage resHomePage = new ResHomePage();
    ResAutoPayListPage resAutoPayListPage = new ResAutoPayListPage();
    ResidentChooseAutopayPage residentChooseAutopayPage = new ResidentChooseAutopayPage();

    switch (entryPoint) {
      case AUTOPAY_TAB:
        ResidentMenuItems residentMenuItems = new ResidentMenuItems();

        residentMenuItems.clickAutopayTab();
        resAutoPayListPage.clickCreateNewAutoPayButton();
        residentChooseAutopayPage.clickCreateFixedAutoPayButton();
        break;

      case CREATE_AUTOPAY_LINK:
        resHomePage.clickSetUpNewAutopay();
        residentChooseAutopayPage.clickCreateFixedAutoPayButton();
        break;

      case GET_STARTED_AUTOPAY_LINK:
        resHomePage.clickGetStartedAutoPayButton();
        residentChooseAutopayPage.clickCreateFixedAutoPayButton();
        break;

      default:
        throw new IllegalArgumentException("Entry point" + paymentType + "Unknown");
    }

    paymentBase.fillAndSubmitPaymentAmount(false);

    SchedulePage schedulePage = new SchedulePage();
    schedulePage.prepField(SchedulePage.FIELD_FREQUENCY, frequency);
    schedulePage.prepField(SchedulePage.FIELD_INDEFINITE, isIndefiniteChecked);
    schedulePage.fillAndSubmitPaymentScheduleDetails();

    paymentBase.selectPaymentMethod(paymentType, expressPay);
    paymentBase.reviewAndSubmit();

    Assert.assertTrue(resAutoPayListPage.isSuccessMessagePresent(), "Success message should be "
        + "present");

    ResFapListPage resFapListPage = new ResFapListPage();
    Assert.assertTrue(
        resFapListPage.hasRowMatchingData("Active", null, null, null, frequency,
            null, null), "Row with matching data is present");
    ResidentLogoutBar residentLogoutBar = new ResidentLogoutBar();

    residentLogoutBar.clickLogoutButton();
  }
}
