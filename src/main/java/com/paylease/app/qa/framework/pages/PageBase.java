package com.paylease.app.qa.framework.pages;

import com.paylease.app.qa.framework.DriverManager;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ResourceFactory;
import com.paylease.app.qa.framework.UtilityManager;
import com.paylease.app.qa.framework.utility.sshtool.SshUtil;
import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.awaitility.Awaitility;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.SkipException;

public class PageBase {

  public static final String BASE_URL = ResourceFactory.getInstance()
      .getProperty(ResourceFactory.APP_URL_KEY);
  protected static final String STANDARD_PASS = "Test1234";
  private static final int DEFAULT_TIMEOUT = 15;
  public static final String PHP_DEBUG_PARAM = "XDEBUG_SESSION_START=PHPSTORM";

  protected WebDriver driver;
  protected WebDriverWait wait;

  private boolean pageHasBeenPreparedForUnload = false;

  public PageBase() {
    this(DriverManager.getDriver());
  }

  protected PageBase(WebDriver driver) {
    this.driver = driver;
    this.wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
    PageFactory.initElements(new AjaxElementLocatorFactory(driver, DEFAULT_TIMEOUT), this);
  }

  protected void waitForAjax() {
    waitForAjaxBegin();
    waitForAjaxEnd();
  }

  private void waitForAjaxBegin() {
    wait.until(new ExpectedCondition<Boolean>() {
      public Boolean apply(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return (Boolean) js.executeScript("return jQuery.active != 0");
      }
    });
  }

  private void waitForAjaxEnd() {
    wait.until(new ExpectedCondition<Boolean>() {
      public Boolean apply(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return (Boolean) js.executeScript("return jQuery.active == 0");
      }
    });
  }

  protected void delayFor(int secInMili) {
    try {
      Thread.sleep(secInMili);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * Highlight the given element with a red border.
   *
   * @param element Element to highlight
   */
  public void highlight(WebElement element) {
    if (!ResourceFactory.getInstance().getFlag(ResourceFactory.HIGHLIGHT_KEY)) {
      return;
    }
    for (int i = 0; i < 2; i++) {
      JavascriptExecutor js = (JavascriptExecutor) driver;

      js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element,
          "border: 2px solid red;");
      delayFor(200);
      js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
      delayFor(200);
    }
  }

  /**
   * Open the given URL and wait for the new page to load.
   *
   * @param url URL to open
   */
  public void openAndWait(String url) {
    WebElement oldPage = driver.findElement(By.tagName("html"));

    try {
      if (System.getProperty("intellij.debug.agent") != null
          && System.getProperty("intellij.debug.agent").equals("true")) {
        url += (((url.contains("?")) ? "&" : "?") + PHP_DEBUG_PARAM);
      }
    } catch (Exception exception) {
      System.out.println("Your version of IntelliJ does not support PHP Debugging\n"
          + "The minimum version that supports PHP Debugging is 2018.2.0");
    }

    driver.get(url);

    waitForPageToLoad(oldPage);
  }

  /** Add some javascript to the page that sets a flag that is changed on unload or ajaxStart. */
  public void preparePageUnload() {
    JavascriptExecutor js = (JavascriptExecutor) driver;

    js.executeScript(
        "$(document).ready(function() {"
            + "window.selenium_pageHasChanged = false;"
            + "$(window).unload(function() {"
            + "window.selenium_pageHasChanged = true;"
            + "});"
            + "$(window).ajaxStart(function() {"
            + "window.selenium_pageHasChanged = true;"
            + "});"
            + "});");
    pageHasBeenPreparedForUnload = true;
  }

  protected void openAndWaitForAjax(String url) {
    openAndWait(url);
    waitForAjaxEnd();
  }

  /**
   * Click the given element and wait for a new page to load.
   *
   * @param element Element to click
   */
  public void clickAndWait(WebElement element) {
    WebElement oldPage = driver.findElement(By.tagName("html"));

    wait.until(ExpectedConditions.elementToBeClickable(element));

    element.click();

    waitForPageToLoad(oldPage);
  }

  protected String getCurrentPageUrl() {
    return driver.getCurrentUrl();
  }

  /**
   * Check if the current page has been changed since a baseline was established with a call to
   * preparePageUnload().
   *
   * @return True if the page has been unloaded or an ajax call was made
   * @throws IllegalStateException If preparePageUnload was not called first
   */
  public boolean isPageChanged() throws IllegalStateException {
    if (!pageHasBeenPreparedForUnload) {
      throw new IllegalStateException("Page was not properly prepared for change check");
    }
    JavascriptExecutor js = (JavascriptExecutor) driver;
    Object result = js.executeScript("return window.selenium_pageHasChanged");

    return result == null || result.equals("true");
  }

  /**
   * Get a list of error messages.
   *
   * @return error messages as a list
   */
  public List<String> getErrorMessages() {
    List<WebElement> errors = driver.findElements(By.cssSelector("label.error_server"));
    List<String> errorMessages = new ArrayList<>();

    for (WebElement error : errors) {
      errorMessages.add(error.getText());
    }

    return errorMessages;
  }

  protected void clickAndWaitForAjax(WebElement element) {
    element.click();
    waitForAjax();
  }

  protected void waitForPageToLoad(WebElement oldPageElement) {
    ExpectedCondition<Boolean> pageLoad = new
        ExpectedCondition<Boolean>() {
          public Boolean apply(WebDriver driver) {
            return ((JavascriptExecutor) driver).executeScript("return document.readyState")
                .equals("complete");
          }
        };

    try {
      wait.until(ExpectedConditions.stalenessOf(oldPageElement));
      wait.until(pageLoad);
    } catch (Throwable pageLoadWaitError) {
      Assert.assertFalse(true, "Timeout during page load");
    }
  }

  /**
   * Iterates through a given directory until a given file has been found.
   *
   * @param downloadPath
   * @param fileName
   */
  protected void waitForFileToDownloadBySearch(String downloadPath, String fileName) {
    Path path = Paths.get(downloadPath);
    Logger.info("Searching for file " + fileName);

    try {
      Awaitility.await()
          .atMost(20, TimeUnit.SECONDS)
          .until(() -> searchDirectory(path.toString(), fileName).length > 0);
    } catch (Throwable fileDownloadError) {
      Assert.fail("Timeout waiting for file to download: " + fileDownloadError);
    }
  }

  protected File[] searchDirectory(String path, String fileName) {
    File dir = new File(path);

    return dir.listFiles((dir1, name) -> name.contains(fileName) && name.endsWith(".xlsx"));
  }

  protected void waitForFileToDownload(String downloadPath, String fileName) {
    String fullPath = downloadPath + File.separator + fileName;
    Path path = Paths.get(fullPath);
    Logger.info("Checking for file " + fullPath);

    try {
      Awaitility.await()
          .atMost(20, TimeUnit.SECONDS)
          .until(() -> path.toFile().exists());
    } catch (Throwable fileDownloadError) {
      Assert.assertFalse(true, "Timeout waiting for file to download: " + fileDownloadError);
    }
  }
  
  /**
   * Get a valid xPath string used to match an element with a given class name.
   *
   * @param className Class name to find
   * @return xPath string that can be used to find elements with the given class name
   */
  protected String xPathMatchClassName(String className) {
    return "contains(concat(' ',normalize-space(@class),' '),' " + className + " ')";
  }

  protected String xPathMatchNotClassName(String className) {
    return "not(" + xPathMatchClassName(className) + ")";
  }

  protected void setDateFieldValue(String id, String date) {
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("document.getElementById('" + id + "').value = '" + date + "'");
  }

  protected void forceLastElementBlur() {
    ((JavascriptExecutor) driver)
        .executeScript("!!document.activeElement ? document.activeElement.blur() : 0");
  }

  protected void mouseHover(WebElement element) {
    Actions action = new Actions(driver);
    action.moveToElement(element).build().perform();
  }

  protected String getTextBySelector(By byLocator) {
    String text = "";
    List<WebElement> elements = driver.findElements(byLocator);

    for (WebElement element : elements) {
      if (element.isDisplayed()) {
        highlight(element);

        return element.getText();
      }
    }

    return text;
  }

  protected boolean isElementPresentBySelector(By byLocator) {
    return !driver.findElements(byLocator).isEmpty();
  }

  protected WebElement getElementBySelectorIfPresent(WebElement context, By byLocator) {
    List<WebElement> elements = context.findElements(byLocator);
    if (elements.isEmpty()) {
      return null;
    }

    return elements.get(0);
  }

  protected boolean isImagePresentBySelector(By byLocator) {
    List<WebElement> elements = driver.findElements(byLocator);
    if (elements.isEmpty()) {
      return false;
    }
    WebElement element = elements.get(0);
    return element.getTagName().toLowerCase().equals("img");
  }

  protected boolean isElementDisplayed(By bySelector) {
    List<WebElement> elements = driver.findElements(bySelector);
    return !elements.isEmpty() && elements.get(0).isDisplayed();
  }

  protected boolean isElementDisabled(By bySelector) {
    List<WebElement> elements = driver.findElements(bySelector);
    return !elements.isEmpty() && !elements.get(0).isEnabled();
  }

  protected String getTitle() {
    return getTextBySelector(By.className("page_title"));
  }

  protected void enterText(WebElement element, String text) {
    highlight(element);
    element.click();
    element.sendKeys(text);
  }

  protected void scrollInToView(WebElement element) {
    JavascriptExecutor jse2 = (JavascriptExecutor) driver;
    jse2.executeScript("arguments[0].scrollIntoView()", element);
  }

  public void scrollToPageBottom() {
    JavascriptExecutor jse2 = (JavascriptExecutor)driver;
    jse2.executeScript("window.scrollTo(0, document.body.scrollHeight)");
  }

  protected void setCookieOptOutRefundTutorial() {
    Cookie cookie = new Cookie.Builder("refund_tutorial_opt_out", "setByTest").path("/").build();
    driver.manage().addCookie(cookie);
  }

  protected WebElement waitUntilElementIsClickable(By byLocator) {
    return wait.until(ExpectedConditions.elementToBeClickable(byLocator));
  }

  protected WebElement fluentWaitForElementToBeClickable(By byLocator) {
    return new FluentWait<WebDriver>(driver)
        .withTimeout(30, TimeUnit.SECONDS)
        .pollingEvery(2, TimeUnit.MILLISECONDS)
        .ignoring(WebDriverException.class)
        .until(ExpectedConditions.elementToBeClickable(byLocator));
  }

  protected void setKeys(WebElement element, String key) {
    element.click();
    element.clear();
    element.sendKeys(key);
  }

  protected void click(By byLocator) {
    WebElement element = waitUntilElementIsClickable(byLocator);

    element.click();
  }

  /** Accept Void Alert. */
  public void acceptVoidAlert() {
    driver.switchTo().alert().accept();
  }

  /** Skip test if it's weekend. */
  public void skipTestIfItsWeekend() {
    UtilityManager utilityManager = new UtilityManager();

    if (utilityManager.isDuringWeekend()) {
      Logger.trace("It's the weekend - Skipping test");
      throw new SkipException("Skipping this test");
    }
  }

  /** Skip test if br script is running. */
  protected void skipTestIfBrScriptIsRunning() {
    SshUtil sshUtil = new SshUtil();

    if (sshUtil.checkIfAnyBrPtScriptIsRunning()) {
      Logger.trace("Script is running - Skipping test");
      throw new SkipException("Skipping this test");
    }
  }

  /** Wait until ajax ends with Fluent wait. */
  public void waitUntilAjaxEnds() {
    try {
      new FluentWait<>(driver)
          .withTimeout(30, TimeUnit.SECONDS)
          .pollingEvery(150, TimeUnit.MILLISECONDS)
          .ignoring(WebDriverException.class)
          .until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
              JavascriptExecutor js = (JavascriptExecutor) driver;
              return (Boolean) js.executeScript("return jQuery.active == 0");
            }
          })
      ;
    } catch (Throwable pageLoadWaitError) {
      Assert.assertFalse(true, "Timeout during page load");
    }
  }
}
