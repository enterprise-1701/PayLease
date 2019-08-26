package com.paylease.app.qa.framework.pages.admin;

import com.paylease.app.qa.framework.components.datatable.AdminPmList;
import com.paylease.app.qa.framework.components.datatable.AdminPmList.Columns;
import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Created by Adrienne Aquino on 07/12/2018.
 */

public class AdminSearchPmResultsPage extends PageBase {

  private static final String LOG_AS_LINK_TEXT = "Log As";
  private AdminPmList adminSearchResults;

  public AdminSearchPmResultsPage() {
    adminSearchResults = new AdminPmList(driver.findElement(By.cssSelector("table#pms")));
  }

  // ********************************************Action*********************************************

  /**
   * Get the row of the given pmId from the search results.
   *
   * @param pmId id of the pm from search result
   * @return row
   */
  private WebElement getPmRow(String pmId) {
    WebElement pmRow = adminSearchResults.getRowByCellText(pmId, Columns.PM_ID.getLabel());
    highlight(pmRow);
    return pmRow;
  }

  /**
   * Click on Log As link for the given pm.
   *
   * @param pmId id of pm to log as
   */
  public void logAsPm(String pmId) {
    WebElement pmTableRow = getPmRow(pmId);
    WebElement pmLogAsLink = pmTableRow.findElement(By.linkText(LOG_AS_LINK_TEXT));

    clickAndWait(pmLogAsLink);
  }
}
