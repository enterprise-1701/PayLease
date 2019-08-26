package com.paylease.app.qa.functional.tests.resident;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.components.CreditReporting;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.resident.ResAutoPayListPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AutoPayListTest extends ScriptBase {

  private static final String REGION = "resident";
  private static final String FEATURE = "autoPayList";

  //-----------------------------------TESTS--------------------------------------------------------

  @Test
  public void tc1() {
    Logger.info("Verify credit reporting optin container is not present");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();

    ResAutoPayListPage resAutoPayListPage = new ResAutoPayListPage();

    resAutoPayListPage.open();

    CreditReporting creditReporting = new CreditReporting();

    Assert.assertFalse(creditReporting.isOptinContainerPresent(),
        "Optin container should not be present");
  }
}
