package com.paylease.app.qa.e2e.tests.resident;

import static com.paylease.app.qa.framework.pages.resident.ResHomePage.CREATE_AUTOPAY_LINK;
import static com.paylease.app.qa.framework.pages.resident.ResHomePage.GET_STARTED_AUTOPAY_LINK;
import static com.paylease.app.qa.framework.pages.resident.ResidentMenuItems.AUTOPAY_TAB;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.paymentflow.SchedulePage;
import com.paylease.app.qa.framework.pages.resident.ResAutoPayListPage;
import com.paylease.app.qa.framework.pages.resident.ResHomePage;
import com.paylease.app.qa.framework.pages.resident.ResLoginPage;
import com.paylease.app.qa.framework.pages.resident.ResVapListPage;
import com.paylease.app.qa.framework.pages.resident.ResidentChooseAutopayPage;
import com.paylease.app.qa.framework.pages.resident.ResidentLogoutBar;
import com.paylease.app.qa.framework.pages.resident.ResidentMenuItems;
import com.paylease.app.qa.framework.utility.listeners.Retry;
import com.paylease.app.qa.testbase.PaymentBase;
import com.paylease.app.qa.testbase.dataproviders.VariableAutoPayTestDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

public class VariableAutoPayTest extends ScriptBase {

  private static final String REGION = "resident";
  private static final String FEATURE = "variableAutopay";

  //--------------------------------VARIABLE AUTOPAY TESTS------------------------------------------

  @Test(dataProvider = "vapDataResident", dataProviderClass = VariableAutoPayTestDataProvider.class,
      groups = {"e2e"}, retryAnalyzer = Retry.class)
  public void vapResident(String testVariationNo, String entryPoint, String frequency,
      boolean isIndefiniteChecked, boolean isMaxLimitChecked, String paymentType,
      boolean expressPay) {
    Logger.info("Resident vap, where test variation: " + testVariationNo + " with "
        + paymentType + ", entry point: " + entryPoint + ", express pay: " + expressPay
        + ", frequency: " + frequency + ", indefinite: " + isIndefiniteChecked + " and Max Limit: "
        + isMaxLimitChecked);

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "testSetupVapFlow");
    testSetupPage.open();
    final String residentEmail = testSetupPage.getString("residentEmail");

    ResLoginPage resLoginPage = new ResLoginPage();

    resLoginPage.open();
    resLoginPage.login(residentEmail, null);

    residentVapPaymentActions(entryPoint, frequency, isIndefiniteChecked, isMaxLimitChecked,
        paymentType, expressPay);
  }

  //--------------------------------TEST METHOD-----------------------------------------------------

  /**
   * Perform PM VAP actions.
   *
   * @param entryPoint entry point
   * @param frequency frequency
   * @param isIndefiniteChecked boolean
   * @param isMaxLimitChecked boolean
   * @param paymentType payment type
   * @param expressPay express pay
   */
  public void residentVapPaymentActions(String entryPoint, String frequency,
      boolean isIndefiniteChecked, boolean isMaxLimitChecked, String paymentType,
      boolean expressPay) {

    ResHomePage resHomePage = new ResHomePage();
    ResAutoPayListPage resAutoPayListPage = new ResAutoPayListPage();
    ResidentChooseAutopayPage residentChooseAutopayPage = new ResidentChooseAutopayPage();

    switch (entryPoint) {
      case AUTOPAY_TAB:
        ResidentMenuItems residentMenuItems = new ResidentMenuItems();

        residentMenuItems.clickAutopayTab();
        resAutoPayListPage.clickCreateNewAutoPayButton();
        residentChooseAutopayPage.clickCreateVariableAutoPayButton();
        break;

      case CREATE_AUTOPAY_LINK:
        resHomePage.clickSetUpNewAutopay();
        residentChooseAutopayPage.clickCreateVariableAutoPayButton();
        break;

      case GET_STARTED_AUTOPAY_LINK:
        resHomePage.clickGetStartedAutoPayButton();
        residentChooseAutopayPage.clickCreateVariableAutoPayButton();
        break;

      default:
        throw new IllegalArgumentException("Entry point" + entryPoint + "Unknown");
    }

    SchedulePage schedulePage = new SchedulePage();
    schedulePage.prepField(SchedulePage.FIELD_FREQUENCY, frequency);
    schedulePage.prepField(SchedulePage.FIELD_INDEFINITE, isIndefiniteChecked);
    schedulePage.prepField(SchedulePage.FIELD_MAX_LIMIT_ENABLED, isMaxLimitChecked);
    schedulePage.fillAndSubmitPaymentScheduleDetails();

    PaymentBase paymentBase = new PaymentBase();

    paymentBase.selectPaymentMethod(paymentType, expressPay);
    paymentBase.reviewAndSubmit();

    Assert.assertTrue(resAutoPayListPage.isSuccessMessagePresent(), "Success message should be "
        + "present");

    ResVapListPage resVapListPage = new ResVapListPage();
    Assert.assertTrue(
        resVapListPage.hasRowMatchingData("Active", null, null, null, frequency,
            null, null), "Row with matching data is present");

    ResidentLogoutBar residentLogoutBar = new ResidentLogoutBar();

    residentLogoutBar.clickLogoutButton();
  }
}
