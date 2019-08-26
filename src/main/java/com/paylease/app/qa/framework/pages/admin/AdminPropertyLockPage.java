package com.paylease.app.qa.framework.pages.admin;

import com.paylease.app.qa.framework.UtilityManager;
import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.Select;

public class AdminPropertyLockPage extends PageBase {

  private static final String URL = BASE_URL + "admin2.php?action=property_lock&user_id={pmId}";
  private String url;
  private static final String FREQUENCY_MONTHLY = "M";
  private static final String FREQUENCY_ONE_TIME = "O";
  private static final String FREQUENCY_QUARTERLY = "Q";
  private static final String FREQUENCY_BIANNUALLY = "B";
  private static final String FREQUENCY_ANNUALLY = "A";

  /**
   * AdminPmDetailPage constructor set the pm id for the page.
   *
   * @param pmId test pm id
   */
  public AdminPropertyLockPage(String pmId) {
    super();
    this.url = URL.replace("{pmId}", pmId);
  }

  // ********************************************Action*********************************************

  /**
   * open the page.
   */
  public void open() {
    openAndWait(this.url);
  }

  private void selectProperty(String propertyId) {
    Select select = new Select(driver.findElement(By.id("property_lock_select")));
    select.selectByValue(propertyId);
  }

  public void scheduleNextEarliestLock(String propertyId) {
    if(null!=propertyId){
      selectProperty(propertyId);
    }
    selectLockDateToday();
    selectLockTimeNowPlusOneMin();
    selectLockFrequency(FREQUENCY_ONE_TIME);
    click(By.id("schedule_property_lock"));
    waitUntilElementIsClickable(By.id("property_lock_message"));
  }



  private void selectLockDateToday() {
    String date = UtilityManager.getCurrentDate(UtilityManager.MONTH_DAY_YEAR_SLASH);
    setDateFieldValue("lock_date", date);
  }

  private void selectLockTimeNowPlusOneMin() {
    String time = UtilityManager.getCurrentTimePlusOneMin("hh:mm a");
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("document.getElementById('lock_time').value = '" + time + "'");
  }

  private void selectLockFrequency(String frequency) {
    Select select = new Select(driver.findElement(By.id("property_lock_frequency")));
    select.selectByValue(frequency);
  }

  public String getPropertyLockMessage() {
    return driver.findElement(By.id("property_lock_message")).getText();
  }
}
