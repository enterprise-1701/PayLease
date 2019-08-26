package com.paylease.app.qa.framework.pages.admin;

import com.paylease.app.qa.framework.pages.PageBase;
import java.io.File;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TinReportPage extends PageBase {

  private static final String URL = BASE_URL + "admin2.php?action=tin_report";
  public static final String TIN_REPORT = "tin-report";

  @FindBy(id = "excel_tin_report")
  private WebElement excelTinReport;

  @FindBy(id = "choose_tin_report")
  private WebElement uploadTinReportInput;

  @FindBy(id = "submit_tin_report")
  private WebElement submitTinReportInput;

  @FindBy(xpath = "//*[@id=\"content\"]/div/span[1]")
  private WebElement invalidLengthSpan;

  public void open() {
    openAndWait(URL);
  }

  /**
   * The PM Helper we use in test set ups creates a large amount of tin records each day.
   * The tin report page will timeout if it tries to load a large amount out of records.
   * There is a script to modify the validation status of the tin records to keep the page loading.
   * This ensures the test will skip in case the page reaches max execution time. The test will pass
   * whenever the validation script is ran.
   * @return boolean
   */
  public boolean checkMaxExecutionTimeErrorExists() {
    return driver.findElements(By.cssSelector(".xdebug-error.xe-fatal-error")).size() != 0;
  }

  public void downloadTinReport(String downloadPath) {
    excelTinReport.click();
    waitForFileToDownloadBySearch(downloadPath, TIN_REPORT);
  }

  public String getInvalidLengthSpan() {
    return invalidLengthSpan.getText();
  }

  public void chooseTinFile(File file) {
    String path = file.getAbsolutePath();
    uploadTinReportInput.sendKeys(path);
  }

  public void submitTinReport() {
    clickAndWait(submitTinReportInput);
  }

  public String getTinReportName(String path, String fileName) {
    File[] files = searchDirectory(path, fileName);
    if (files.length != 0) {
      return files[0].getName();
    }

    return null;
  }
}
