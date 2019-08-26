package com.paylease.app.qa.functional.tests.pm;

import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.pm.FtpCredentialsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FtpCredentialsTest extends ScriptBase {

  public static final String REGION = "pm";
  public static final String FEATURE = "ftpCredentials";

  @Test
  public void hidePasswordTest() {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc3278");
    testSetupPage.open();

    FtpCredentialsPage ftpCredentialsPage = new FtpCredentialsPage();
    ftpCredentialsPage.open();

    Assert.assertFalse(ftpCredentialsPage.isLabelPresent("Password:"),
        "Password should not be present");
    Assert.assertFalse(ftpCredentialsPage.isLabelPresent("Phonetic Password:"),
        "Phonetic Password should not be present");
  }

  @Test
  public void showPasswordTest() {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc3279");
    testSetupPage.open();

    FtpCredentialsPage ftpCredentialsPage = new FtpCredentialsPage();
    ftpCredentialsPage.open();

    Assert.assertTrue(ftpCredentialsPage.isLabelPresent("Password:"), "Password should be present");
    Assert.assertTrue(ftpCredentialsPage.isLabelPresent("Phonetic Password:"),
        "Phonetic Password should be present");
  }
}
