package com.paylease.app.qa.framework.pages.admin;

import com.paylease.app.qa.framework.components.datatable.AdminUserSearchResultsList;
import com.paylease.app.qa.framework.components.datatable.AdminUserSearchResultsList.Columns;
import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Created by Adrienne Aquino on 08/17/2018.
 */

public class AdminSearchUsersResultsPage extends PageBase {

  private static final String LOG_AS_LINK_TEXT = "Log As";
  private AdminUserSearchResultsList adminSearchResults;

  public AdminSearchUsersResultsPage() {
    adminSearchResults = new AdminUserSearchResultsList(
        driver.findElement(By.cssSelector("table#s_users_table")));
  }

  // ********************************************Action*********************************************

  /**
   * Get the row of the given userId from the search results.
   *
   * @param id of the user from search result
   * @return row
   */
  private WebElement getUserRow(String id) {
    WebElement userRow = adminSearchResults.getRowByCellText(id, Columns.USER_ID.getLabel());
    highlight(userRow);
    return userRow;
  }

  /**
   * Click on Log As link for the given user.
   *
   * @param id of user to log as
   */
  public void logAsUser(String id) {
    WebElement userTableRow = getUserRow(id);
    WebElement userLogAsLink = userTableRow.findElement(By.linkText(LOG_AS_LINK_TEXT));

    clickAndWait(userLogAsLink);
  }
}