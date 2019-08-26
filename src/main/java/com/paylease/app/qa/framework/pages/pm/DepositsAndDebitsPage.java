package com.paylease.app.qa.framework.pages.pm;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.components.datatable.PmDepositsDebits;
import com.paylease.app.qa.framework.components.datatable.PmDepositsDebits.Columns;
import com.paylease.app.qa.framework.pages.PageBase;
import java.util.HashMap;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class DepositsAndDebitsPage extends PageBase {

    private static final String URL = BASE_URL + "pm/deposits_debits";
    private PmDepositsDebits pmDepositsDebits;

    @FindBy(id = "submit_button")
    private WebElement submitButton;

    @FindBy(id = "search_field")
    private WebElement searchField;

    @FindBy(id = "search_value")
    private WebElement searchTransaction;

    @FindBy(id = "deposits_debits")
    private WebElement table;

    @FindBy(id = "expand")
    private WebElement expandAllBtn;

    @FindBy(id = "nav_dashboards")
    private WebElement dashboardTab;

    @FindBy(id = "dep_deb_opt_out")
    private WebElement optOut;

    // ********************************************Action*********************************************

    /**
     * Opens the page.
     */
    public void open() {
        openAndWait(URL);
        pmDepositsDebits = new PmDepositsDebits(table);
    }

    /**
     * Return batch id.
     */
    public String getBatchId() {
        return getTextBySelector(By.cssSelector("tr.search_highlight_row td.batch_id"));
    }

    /**
     * Return column name left to the Amount column.
     */
    public String getColumnLeftToAmount() {
        return getTextBySelector(By.xpath(".//*[@id='amount']/preceding::th[1]"));
    }

    /**
     * Search for a transaction.
     */
    public void searchTransaction(String transId) {
        new Select(searchField).selectByVisibleText("Trans #");
        searchTransaction.sendKeys(transId);
        Logger.debug("Searching for transaction " + transId);
        clickAndWait(submitButton);
    }

    /**
     * Click the expand all button on page.
     */
    public void clickExpandAll() {
        expandAllBtn.click();
    }

    /**
     * Returns bill type for a given transaction.
     *
     * @param transId Id of the transaction
     * @return billType
     */
    public String getBillType(String transId) {
        WebElement transRow = pmDepositsDebits.getRowByCellText(transId, Columns.TRANS_NUM.getLabel());
        HashMap<String, String> rowData = pmDepositsDebits.getMapOfTableRow(transRow);

        return rowData.get(Columns.BILL_TYPE.getLabel());
    }

    /**
     * Click Dashboard Tab
     **/
    public void clickDashboardTab() {
        highlight(dashboardTab);
        dashboardTab.click();
        Logger.trace("dashboard tab clicked");
    }


    /**
     * Click optout
     **/
    public void clickOptOut() {
        highlight(optOut);
        optOut.click();
        delayFor(3000);
        Logger.trace("optout clicked");
    }
}
