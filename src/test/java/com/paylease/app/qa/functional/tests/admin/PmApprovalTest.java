package com.paylease.app.qa.functional.tests.admin;

import com.paylease.app.qa.framework.DataHelper;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.admin.LoginPageAdmin;
import com.paylease.app.qa.framework.pages.admin.PmApprovalPage;
import com.paylease.app.qa.framework.pages.admin.PmPrincipalProfileEditPage;
import com.paylease.app.qa.framework.pages.pm.PmLoginPage;
import com.paylease.app.qa.framework.pages.registration.PmRegistrationPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by Mahfuz Alam on 09/26/2017.
 */

public class PmApprovalTest extends ScriptBase {

  /**
   * Register a PM, Approve as Admin, Verify PM can login.
   */
  @Test
  public void pmCanLoginAfterApproval() {
    DataHelper dataHelper = new DataHelper();
    String email = dataHelper.getUniqueEmail();

    Logger.info("Register a new PM " + email);

    PmRegistrationPage pmRegistrationPage = new PmRegistrationPage();
    pmRegistrationPage.open();
    pmRegistrationPage.fillAndSubmit(email);

    LoginPageAdmin adminLoginPagePm = new LoginPageAdmin();
    adminLoginPagePm.login();

    PmApprovalPage pmApprovalPage = new PmApprovalPage();
    pmApprovalPage.open();
    String pmId = pmApprovalPage.getPmId(email);
    Logger.info("New PM ID: " + pmId);

    String approvalMessage = pmApprovalPage.clickApprove(email);
    Assert.assertTrue(approvalMessage.contains("cannot be approved"),
        "New PM must not be approved without entering Tax ID");

    PmPrincipalProfileEditPage pmProfilePage = new PmPrincipalProfileEditPage(pmId);
    pmProfilePage.open();
    pmProfilePage.enterTaxId();
    pmProfilePage.save();

    pmApprovalPage.open();
    approvalMessage = pmApprovalPage.clickApprove(email);
    Assert.assertTrue(approvalMessage.contains("Approved PM: " + pmId),
        "PM should be approved once Tax ID saved");

    PmLoginPage pmLoginPage = new PmLoginPage();
    pmLoginPage.open();
    pmLoginPage.login(email);

    Assert.assertTrue(pmLoginPage.isLogoutButtonPresent(), "Logout button should be present");
  }
}
