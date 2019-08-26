package com.paylease.app.qa.framework.pages.pm;

import com.paylease.app.qa.framework.pages.PageBase;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Class FtpCredentialsPage.
 */
public class FtpCredentialsPage extends PageBase {

  private static final String URL = BASE_URL + "pm/dashboard/ftp_credentials";

  /**
   * open the page.
   */
  public void open() {
    openAndWait(URL);
  }

  /**
   * Find the label.
   *
   * @param searchLabel string of label to find
   * @return if label is found
   */
  public boolean isLabelPresent(String searchLabel) {
    List<WebElement> labels = driver.findElements(By.cssSelector("#pm_table .col_label"));

    for (WebElement label : labels) {
      if (label.getText().equals(searchLabel)) {
        return true;
      }
    }

    return false;
  }
}
