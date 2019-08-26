package com.paylease.app.qa.testbase.dataproviders;

import org.testng.annotations.DataProvider;

public class PmDashboardDataProvider {

    /**
     * Provides data required to run various kinds of test combinations for PM Dashboard
     *
     * @return data
     */
    @DataProvider(name = "pmDashboard", parallel = true)
    public Object[][] dataResident() {
        return new Object[][]{
                //Test variation no., TestCase, Entry point
                {"pmdTc1", "tc1", ""},
                {"pmcTc2", "tc1", "menu"}
        };
    }

}
