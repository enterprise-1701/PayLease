package com.paylease.app.qa.e2e.tests.pm;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.pm.DepositsAndDebitsPage;
import com.paylease.app.qa.framework.pages.pm.PmHomePage;
import com.paylease.app.qa.framework.pages.pm.PmLoginPage;
import com.paylease.app.qa.framework.pages.pm.PmMenu;
import com.paylease.app.qa.framework.utility.listeners.Retry;
import com.paylease.app.qa.testbase.dataproviders.PmDashboardDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DashboardTest extends ScriptBase {

    //This is setup directory in PHP
    private static final String REGION = "Pm";

    //This is the class in PHP
    private static final String FEATURE = "PmDashboard";

    //--------------------------------VARIABLE DASHBOARD TESTS------------------------------------------


    @Test(dataProvider = "pmDashboard", dataProviderClass = PmDashboardDataProvider.class,
            groups = {"e2e"}, retryAnalyzer = Retry.class)
    public void checkDashboard(String testVariationNo, String testCase, String entryPoint){
        Logger.info("PM Dashboard Test " + testVariationNo);

        TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
        testSetupPage.open();
        final String pmEmail = testSetupPage.getString("pmEmail");

        PmLoginPage pmLoginPage = new PmLoginPage();
        pmLoginPage.open();
        pmLoginPage.login(pmEmail);
        PmHomePage pmHomePage = new PmHomePage();

        if (entryPoint.equals("menu")) {

            pmHomePage.clickDepositTab();
            DepositsAndDebitsPage depositsAndDebitsPage = new DepositsAndDebitsPage();
            depositsAndDebitsPage.clickOptOut();
            depositsAndDebitsPage.clickDashboardTab();
            PmMenu pmMenu = new PmMenu();
            pmMenu.clickAccountOverview();

        }

            Assert.assertTrue(pmHomePage.pageIsLoaded(), "Should display homepage " + testVariationNo);
            Assert.assertTrue(pmHomePage.isPaymentAccountChartPresent(), "Should display payment account chart " + testVariationNo);
            Assert.assertTrue(pmHomePage.isTopPropertyChartPresent(), "Should display top property chart " + testVariationNo);
            Assert.assertTrue(pmHomePage.isViewAllTransactionsPresent(), "Should display all transaction " + testVariationNo);
            Assert.assertTrue(pmHomePage.isTopUtilizationChartPresent(),"Should display top utilization chart" + testVariationNo);
            Assert.assertTrue(pmHomePage.isRecentTransactionPresent(),"Should display recent transaction" + testVariationNo);

    }

}
