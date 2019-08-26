package com.paylease.app.qa.functional.tests.pm;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.pm.EditSubAccountPage;
import com.paylease.app.qa.framework.pages.pm.ViewSubAccountsPage;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EditSubAccountsTest extends ScriptBase {

  private static final String REGION = "pm";
  private static final String FEATURE = "ViewSubAccounts";

  //-----------------------------------TESTS--------------------------------------------------------

  @Test
  public void addRoleTest() {
    Logger.info("Verify roles for the sub-accounts can be added");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();
    final String subAccId = testSetupPage.getString("subAccId");

    String roleActual = addRole("Shield", subAccId);

    Assert.assertEquals("Shield", roleActual, "Sub-account roles do not match");
  }

  /**
   * Enters role for the sub-account and saves it. Opens the sub-account and verifies if the role
   * got successfully saved. return the role in the field.
   */
  private String addRole(String role, String subAccId) {
    ViewSubAccountsPage subAcc = new ViewSubAccountsPage();
    EditSubAccountPage editAcc = new EditSubAccountPage(subAccId);

    editAcc.open();
    editAcc.addRole(role);
    editAcc.clickSave();

    String successMessage = subAcc.getMessage();

    Assert.assertTrue(successMessage.contains("Sub Account Saved successfully"),
        "Sub-account did not get saved properly");

    editAcc.open();
    String roleActual = editAcc.getRole();

    return roleActual;
  }

  @Test
  public void roleIsOptionalTest() {
    Logger.info("Verify roles for the sub-accounts are optional");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();

    final String subAccId = testSetupPage.getString("subAccId");

    ViewSubAccountsPage subAcc = new ViewSubAccountsPage();
    EditSubAccountPage editAcc = new EditSubAccountPage(subAccId);

    editAcc.open();
    editAcc.clickSave();

    String successMessage = subAcc.getMessage();

    Assert.assertTrue(successMessage.contains("Sub Account Saved successfully"),
        "Sub-account did not get saved successfully, when role was added");
  }

  @Test
  public void editRoleTest() {
    Logger.info("Verify roles for the sub-accounts are editable");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc3");
    testSetupPage.open();

    final String subAccId = testSetupPage.getString("subAccId");
    String roleActual = addRole("Shield", subAccId);

    Assert.assertEquals("Shield", roleActual, "Sub-account roles do not match");
  }

  @Test
  public void maxCharLimitToRoleTest() {
    Logger.info("Verify no more than 50 characters can be added to role field");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();

    final String subAccId = testSetupPage.getString("subAccId");

    String longRole = RandomStringUtils.randomAlphanumeric(55);
    String roleExpected = longRole.substring(0, 50);

    // Adding a role with 55 characters
    String roleActual = addRole(longRole, subAccId);

    // Verifying only 50 characters were set
    Assert.assertEquals(roleActual, roleExpected, "Sub-account roles do not match");
  }
}
