package com.paylease.app.qa.e2e.tests.admin;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.admin.LoginPageAdmin;
import com.paylease.app.qa.framework.pages.admin.PropertyUploadPage;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.utility.listeners.Retry;
import com.paylease.app.qa.testbase.dataproviders.PropertyUploadDataProvider;
import java.io.File;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PropertyUploadTest extends ScriptBase {

  private static final String REGION = "admin";
  private static final String FEATURE = "PropertyUpload";


  @Test(
      dataProvider = "propertyUploadDataProvider",
      dataProviderClass = PropertyUploadDataProvider.class,
      groups = {"e2e"},
      retryAnalyzer = Retry.class
  )
  public void propertyUploadTest(
      String testTitle,
      String expectedResultString,
      String errorExplanation,
      String phpTestCase,
      String javaTestData
  ) {
    Logger.info("Running " + testTitle);
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, phpTestCase);
    testSetupPage.open();
    final String pmId = testSetupPage.getString("pmId");
    final String pmIdReplacementToken = "{{pm_id}}";

    javaTestData = javaTestData.replace(pmIdReplacementToken, pmId);
    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    PropertyUploadPage propertyUploadPage = new PropertyUploadPage();
    propertyUploadPage.open();

    propertyUploadPage.setPropertyUploadPmIdVerificationInput(pmId);

    File file = this.createUploadTestFile(javaTestData);
    propertyUploadPage.setFileForTest(file.getAbsolutePath());

    String result = propertyUploadPage.submit();
    Logger.info(result);

    Assert.assertTrue(result.contains(expectedResultString), errorExplanation);

    if (!file.delete()) {
      Logger.info("Failed to clean up test file");
    }
  }
}
