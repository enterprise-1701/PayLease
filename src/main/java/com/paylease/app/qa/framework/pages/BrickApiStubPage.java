package com.paylease.app.qa.framework.pages;

import org.openqa.selenium.By;

/**
 * Page Object to interact with Brick Api Stub page.
 */
public class BrickApiStubPage extends PageBase {

  public static final String ALT_BRICK_FTP_URI = "https://synergy.paylease.net/david/";

  /**
   * Initialize the stub - set the expected failure count and id.
   *
   * @param failureCount number of requests to fail
   * @param testId id to include in error message
   */
  public void init(int failureCount, String testId) {
    openAndWait(
        ALT_BRICK_FTP_URI + "index.php?mode=setup&times_to_fail=" + failureCount + "&id=" + testId);
  }

  /**
   * Get the number of requests made during test execution.
   *
   * @return int value from page
   */
  public int getRequestCount() {
    openAndWait(ALT_BRICK_FTP_URI + "index.php?mode=query");

    return Integer.parseInt(driver.findElement(By.tagName("body")).getText());
  }
}
