package com.paylease.app.qa.framework.pages.admin;

import com.paylease.app.qa.framework.pages.PageBase;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class PmTabsPage extends PageBase {

  private static final String URL = BASE_URL
      + "admin2.php?action=user_details_pm&selected_tab=user_info&user_id={pmId}";

  private String url;

  private List<WebElement> documents;

  /**
   * Page object representing the PM tabs page in admin.
   *
   * @param pmId PM ID of the profile to edit
   */
  public PmTabsPage(String pmId) {
    super();
    this.url = URL.replace("{pmId}", pmId);
  }

  public void open() {
    openAndWait(url);
  }

  // ********************** KYC Tab **********************

  public boolean isKycTabVisible() {
    return isElementDisplayed(By.id("kyc_tab"));
  }

  public void clickKycTab() {
    WebElement tab = driver.findElement(By.id("kyc_tab"));
    tab.click();
  }

  public WebElement getDocumentDownload() {
    return driver.findElement(By.className("document_downloads"));
  }

  public List<WebElement> getDocumentDownloads() {
    documents = driver.findElements(By.className("document_downloads"));
    return documents;
  }

  public void clickDocDownload(WebElement element, String downloadPath, String fileName) {
    element.click();
    waitForFileToDownload(downloadPath, fileName);
  }
}
