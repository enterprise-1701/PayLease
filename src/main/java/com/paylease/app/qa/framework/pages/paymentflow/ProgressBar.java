package com.paylease.app.qa.framework.pages.paymentflow;

import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProgressBar extends PageBase {

  public static final String CURRENT = "Active and Unselectable";
  public static final String DISABLED = "Inactive and Unclickable";
  public static final String ENABLED = "Active and Clickable";

  @FindBy(className = "progress_bar")
  private WebElement progressBar;

  // ********************************************Action*********************************************

  /**
   * Get the Step Number using index.
   *
   * @return step number
   */
  public String getStepNumber(int index) {
    WebElement element = progressBar
        .findElement(By.cssSelector("li:nth-child(" + index + ") .num_dot"));

    return element.getText();
  }

  /**
   * Get the Step Name using index.
   *
   * @return step name
   */
  public String getStepName(int index) {
    WebElement element = progressBar
        .findElement(By.cssSelector("li:nth-child(" + index + ") .step_name"));

    return element.getText();
  }

  /**
   * Get the Step Status using index.
   *
   * @return status of the requested step
   */
  public String getStepStatus(int index) {
    WebElement element = progressBar
        .findElement(By.cssSelector("li:nth-child(" + index + ")"));

    if (!element.findElements(By.cssSelector("a.selected_disabled")).isEmpty()) {
      return CURRENT;
    }
    if (!element.findElements(By.cssSelector("a.disabled")).isEmpty()) {
      return DISABLED;
    }

    return ENABLED;
  }

  /**
   * Get the number of steps in the progressbar.
   *
   * @return count
   */
  public int getStepCount() {
    return progressBar.findElements(By.cssSelector("li .step_name")).size();
  }
}
