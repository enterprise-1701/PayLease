package com.paylease.app.qa.functional.tests.resident;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.components.CreditReporting;
import com.paylease.app.qa.framework.pages.PageBase;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.resident.ResLoginPage;
import com.paylease.app.qa.framework.pages.resident.ResLogoutBar;
import com.paylease.app.qa.framework.pages.resident.ResPaymentHistoryPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PaymentHistoryTest extends ScriptBase {

  private static final String REGION = "resident";
  private static final String FEATURE = "paymentHistory";

  //-----------------------------------TESTS--------------------------------------------------------

  @Test
  public void tc14() {
    Logger.info("Verify credit reporting optin container is not present");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc14");
    testSetupPage.open();

    ResPaymentHistoryPage resPaymentHistoryPage = new ResPaymentHistoryPage();

    resPaymentHistoryPage.open();

    CreditReporting creditReporting = new CreditReporting();

    Assert.assertFalse(creditReporting.isOptinContainerPresent(),
        "Optin container should not be present");
  }

  @Test
  public void ideResuiGeneralTc03() {
    Logger.info("Verify that a resident cannot void a transaction that is not in their payment history.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "ide_resui_general_tc03");
    testSetupPage.open();

    String username = testSetupPage.getString("userToLogin");
    String transactionId1 = testSetupPage.getString("transactionId1");

    ResLoginPage resLoginPage = new ResLoginPage();
    resLoginPage.open();
    resLoginPage.login(username, null);

    ResPaymentHistoryPage resPaymentHistoryPage = new ResPaymentHistoryPage();
    resPaymentHistoryPage.openAndWait(PageBase.BASE_URL + "resident/payment_history/void/" + transactionId1);

    Assert.assertEquals(resPaymentHistoryPage.getErrorMessages().get(0),"You're not allowed to void this payment.");

    ResLogoutBar resLogoutBar = new ResLogoutBar();
    resLogoutBar.clickLogoutButton();
  }
}
