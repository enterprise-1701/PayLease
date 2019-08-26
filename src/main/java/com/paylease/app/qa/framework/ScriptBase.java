package com.paylease.app.qa.framework;

import static com.paylease.app.qa.framework.ResourceFactory.BROWSER_KEY;
import static com.paylease.app.qa.framework.ResourceFactory.USE_VANTIV_STUB;
import static com.paylease.app.qa.framework.pages.pm.CustomerIdVerificationPage.GIACT_ENV_FILE;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import com.assertthat.selenium_shutterbug.core.Shutterbug;
import com.assertthat.selenium_shutterbug.utils.web.ScrollStrategy;
import com.paylease.app.qa.framework.utility.emailtool.MessagePart;
import com.paylease.app.qa.framework.utility.envwriter.EnvWriterUtil;
import java.io.BufferedReader;
import com.paylease.app.qa.framework.utility.sshtool.SshUtil;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * Created by Mahfuz Alam on 09/25/2017.
 */
public class ScriptBase {

  private HashMap<String, String> downloadPaths = new HashMap<>();

  private static final String DEFAULT_BROWSER = "Chrome";

  protected static final String DOWNLOAD_DIR_PATH =
      System.getProperty("user.dir") + File.separator + "downloads";
  protected static final String UPLOAD_DIR_PATH =
      System.getProperty("user.dir") + File.separator + "uploads";

  protected static final String EMAIL_ENV_VAR_NAME = "DEV_SYSTEM_DEVELOPER";
  protected static final String SERVER_DOMAIN_NAME_ENV_VAR_NAME = "SERVER_DOMAIN_NAME";

  public enum TestFileType {
    PDF("pdf"),
    JPG("jpg");

    private final String fileType;

    TestFileType(String fileType) {
      this.fileType = fileType;
    }

    String getValue() {
      return fileType;
    }
  }

  /** Store previous WebDriver instances when new windows are opened. */
  private ArrayDeque<WebDriver> driverStack = new ArrayDeque<>();

  @BeforeSuite(alwaysRun = true)
  public void suiteSetUp() {
    ResourceFactory properties = ResourceFactory.getInstance();

    if (properties.keyExists(USE_VANTIV_STUB)) {
      if (properties.getFlag(USE_VANTIV_STUB)) {
        EnvWriterUtil envWriterUtil = new EnvWriterUtil();

        envWriterUtil.replaceVantivFileWithStub();
      }
    }
  }

  @Parameters({"browser"})
  @BeforeMethod(alwaysRun = true)
  public void setUp(@Optional(DEFAULT_BROWSER) String browser, Method method) {
    ResourceFactory properties = ResourceFactory.getInstance();
    if (properties.keyExists(BROWSER_KEY)) {
      browser = properties.getProperty(BROWSER_KEY);
    }

    DataHelper dataHelper = new DataHelper();

    String downloadPathRoot = DOWNLOAD_DIR_PATH;

    File downloadRoot = new File(downloadPathRoot);
    if (!downloadRoot.exists()) {
      downloadRoot.mkdir();
    }

    String downloadPathKey = getDownloadPathKey(method);
    String downloadPathSpecific = downloadPathKey + "-" + dataHelper.getReferenceId();

    String fullDownloadPath = browser.equals("Zalenium") ? ResourceFactory.getInstance()
        .getProperty(ResourceFactory.ZALENIUM_MOUNTED_DIR)
        : downloadPathRoot + File.separator + downloadPathSpecific;

    Logger.debug(fullDownloadPath);
    downloadPaths.put(downloadPathKey, fullDownloadPath);

    WebDriver driver = createWebDriver(browser, fullDownloadPath);
    DriverManager.setWebDriver(driver);
  }

  @AfterMethod(alwaysRun = true)
  public void tearDown(ITestResult result) {

    if (ITestResult.FAILURE == result.getStatus()) {
      try {
        UtilityManager utilityManager = new UtilityManager();
        String user = System.getProperty("user.name");
        Shutterbug.shootPage(DriverManager.getDriver(), ScrollStrategy.BOTH_DIRECTIONS, 500, true)
            .save(utilityManager.makeDirectory("./screenshots/screenshot-" + user + "-"));
        Logger.info("Screenshot captured and saved under screenshots folder");
      } catch (Exception e) {
        Logger.warning("Exception while taking screenshot " + e.getMessage());
      }
    }

    try {
      Method method = result.getMethod().getConstructorOrMethod().getMethod();

      String downloadPath = downloadPaths.get(getDownloadPathKey(method));
      File downloadFolder = new File(downloadPath);
      if (downloadFolder.listFiles() != null) {
        for (File file : downloadFolder.listFiles()) {
          file.delete();
        }
      }

      downloadFolder.delete();

    } catch (Exception e) {
      Logger.debug("Exception found: " + e);
    } finally {
      DriverManager.closeDriver();
    }
  }

  protected void assertExpectedMap(HashMap<String, String> actual,
      HashMap<String, String> expected) {
    for (Map.Entry<String, String> entry : expected.entrySet()) {
      assertMapEntry(actual.get(entry.getKey()), entry.getValue(), entry.getKey(), false);
    }
  }

  protected void assertExpectedMap(HashMap<String, String> actual, HashMap<String, String> expected,
      HashSet<String> partialMatchKeys) {
    for (Map.Entry<String, String> entry : expected.entrySet()) {
      String key = entry.getKey();
      assertMapEntry(actual.get(key), entry.getValue(), key, partialMatchKeys.contains(key));
    }
  }

  protected String setUpDownloadPath(String className, String methodName) {
    String downloadPath = downloadPaths.get(getDownloadPathKey(className, methodName));
    Logger.info("Download path is: " + downloadPath);

    File downloadDir = new File(downloadPath);
    Logger.info("Creating download directory " + downloadPath);
    downloadDir.mkdir();
    return downloadPath;
  }

  protected File getUploadTestFile(TestFileType fileType) {
    return new File(UPLOAD_DIR_PATH + File.separator + "test." + fileType.getValue());
  }

  protected File createUploadTestFile(String content) {
    DataHelper dataHelper = new DataHelper();

    String path = UPLOAD_DIR_PATH + File.separator;
    String fileName = dataHelper.generateAlphanumericString(12) + ".txt";

    try {
      Files.write(Paths.get(path + fileName), content.getBytes());

      return new File(path + fileName);
    } catch (Exception e) {
      Logger.error(e.getMessage());

      return null;
    }
  }

  private String getDownloadPathKey(String className, String methodName) {
    String downloadPathSpecific = className + "." + methodName;
    downloadPathSpecific = downloadPathSpecific.replace(".", "-");
    return downloadPathSpecific;
  }

  private String getDownloadPathKey(Method method) {
    return getDownloadPathKey(method.getDeclaringClass().getTypeName(), method.getName());
  }

  private void assertMapEntry(String actual, String expected, String key, boolean partialMatch) {
    if (partialMatch) {
      assertTrue(actual.contains(expected), key + " value does not contain expected");
    } else {
      assertEquals(actual, expected, key + " value does not match expected");
    }
  }

  /**
   * Create a WebDriver instance for the given / configured browser.
   *
   * @param browser type of browser to use
   * @return a WebDriver instance for the browser
   */
  private static WebDriver createWebDriver(String browser, String downloadPath) {
    ResourceFactory properties = ResourceFactory.getInstance();
    if (properties.keyExists(BROWSER_KEY)) {
      browser = properties.getProperty(BROWSER_KEY);
    }

    return DriverFactory.createInstance(browser, downloadPath);
  }

  /**
   * Create a new WebDriver instance and open a new window.
   *
   * <p>This should always be paired with a call to restorePreviousWindow() using
   * a try { ... } finally { restorePreviousWindow() } block.
   */
  protected void openNewWindow() {
    WebDriver currentDriver = DriverManager.getDriver();
    driverStack.addFirst(currentDriver);

    WebDriver newDriver = createWebDriver(DEFAULT_BROWSER, null);
    DriverManager.setWebDriver(newDriver);
  }

  /**
   * Close the current window and restore the previous WebDriver instance.
   *
   * <p>This should always be paired with a call to openNewWindow() using
   * a try { ... } finally { restorePreviousWindow() } block.
   */
  protected void restorePreviousWindow() {
    DriverManager.closeDriver();
    if (!driverStack.isEmpty()) {
      WebDriver oldDriver = driverStack.removeFirst();
      DriverManager.setWebDriver(oldDriver);
    }
  }

  /**
   * Returns the file path for Email attachments.
   *
   * @param emailAttachmentFileLocation The MessagePart that contains the file location
   * @return String of the attachment's file path
   */
  protected String getEmailAttachmentFilePath(MessagePart emailAttachmentFileLocation) {
    return System.getProperty("user.dir") + File.separator + emailAttachmentFileLocation
        .getContent().substring(27);
  }

  /**
   * Close a deposit.
   *
   * @param deposit
   * @throws IOException
   * @throws InterruptedException
   */
  protected void closeDepositOnLocal(String deposit) throws IOException, InterruptedException {
    String artisan = ResourceFactory.getInstance().getProperty(ResourceFactory.DEPOSIT_SERVICE_DIRECTORY_KEY).concat("artisan");
    String[] cmd = {"php", artisan, "deposits:close", "--id", deposit};
    Process p = Runtime.getRuntime().exec(cmd);
    p.waitFor();
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(p.getInputStream()));
    String line;
    while ((line = bufferedReader.readLine()) != null) {
      System.out.println(line);
    }
    bufferedReader.close();
  }

  /** Skip test if br script is running. */
  protected void skipTestIfBrScriptIsRunning() {
    SshUtil sshUtil = new SshUtil();

    if (sshUtil.checkIfAnyBrPtScriptIsRunning()) {
      Logger.trace("Script is running - Skipping test");
      throw new SkipException("Skipping this test");
    }
  }

  @AfterSuite(alwaysRun = true)
  public void suiteTearDown() {
    ResourceFactory properties = ResourceFactory.getInstance();

    if (properties.keyExists(USE_VANTIV_STUB)) {
      if (properties.getFlag(USE_VANTIV_STUB)) {
        EnvWriterUtil envWriterUtil = new EnvWriterUtil();

        envWriterUtil.renameDotenvFile(EnvWriterUtil.VANTIV_ENV_FILENAME + ".old",
            EnvWriterUtil.VANTIV_ENV_FILENAME);
      }
    }
  }
}