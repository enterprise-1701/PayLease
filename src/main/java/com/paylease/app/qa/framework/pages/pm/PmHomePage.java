package com.paylease.app.qa.framework.pages.pm;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PmHomePage extends PageBase {

    private static final String URL = BASE_URL + "pm/dashboard";

    @FindBy(id = "logout")
    private WebElement logOut;

    @FindBy(css = "a[title='Deposits & Debits']")
    private WebElement depositTab;

    // ********************************************Action*********************************************

    public void open() {
        openAndWait(URL);
    }

    /**
     * Check if navigation from old page has occurred and new page has loaded.
     *
     * @return true when page title is correct and recent transactions table is present
     */
    public boolean pageIsLoaded() {
        return getTitle().equalsIgnoreCase("Account Overview")
                && isElementPresentBySelector(By.cssSelector("table.full_tbl"));
    }

    /**
     * Click logout
     **/
    public void clickLogOut() {

        highlight(logOut);
        logOut.click();
        Logger.trace("log out clicked");

    }

    /**
     * Check chart display
     *
     * @return
     */
    public boolean isPaymentAccountChartPresent() {
        return isElementDisplayed(By.cssSelector("#no_data_img > img[src*=\"pay_method_no_data\"]"));

    }

    /**
     * Top property display
     *
     * @return
     */
    public boolean isTopPropertyChartPresent() {
        return isElementDisplayed(By.cssSelector("div:nth-child(2) > h5"));
    }

    /**
     * Top utilization chart display
     *
     * @return
     */
    public boolean isTopUtilizationChartPresent() {
        return isElementDisplayed(By.cssSelector("div:nth-child(3) > h5"));
    }

    /**
     * Recent Transaction display
     *
     * @return
     */
    public boolean isRecentTransactionPresent() {
        return isElementDisplayed(By.cssSelector("div.fatty_col > h5"));
    }

    /**
     * View All Transactions
     *
     * @return
     */
    public boolean isViewAllTransactionsPresent() {
        return isElementDisplayed(By.cssSelector("div.fatty_col > a.sm_green_btn"));
    }

    /**
     * Click Deposit Tab
     **/
    public void clickDepositTab() {
        highlight(depositTab);
        depositTab.click();
        Logger.trace("deposit tab clicked");
    }

}