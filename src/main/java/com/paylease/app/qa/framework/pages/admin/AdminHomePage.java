package com.paylease.app.qa.framework.pages.admin;

import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Adrienne Aquino on 08/17/2018.
 */

public class AdminHomePage extends PageBase {

  private static final String URL = BASE_URL + "admin2.php";

  @FindBy(id = "reportsDropDown")
  private WebElement reportsDropDown;

  // ********************************************Action*********************************************

  /**
   * PM search on admin.
   *
   * @param pmId of pm to search
   */
  public void searchPm(String pmId) {
    WebElement searchInput = driver.findElement(By.id("s_pms"));
    searchInput.sendKeys(pmId);

    clickAndWait(driver.findElement(By.cssSelector("input[value='PMs']")));
  }

  /**
   * Search for resident in admin user search.
   *
   * @param residentId id of resident to search
   */
  public void searchResident(String residentId) {
    driver.findElement(By.name("s_users")).sendKeys(residentId);

    clickAndWait(driver.findElement(By.cssSelector("input[value='Residents']")));
  }

  /** open the page. */
  public void open() {
    openAndWait(URL);
  }

  /**
   * Check if Compliance KYC Report link is visible in Reports list.
   *
   * @return true if found, false if not
   */
  public boolean isComplianceKycReportLinkVisible() {
    reportsDropDown.click();

    return isElementDisplayed(By.id("complianceKycReport"));

  }
}
