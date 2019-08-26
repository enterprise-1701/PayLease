package com.paylease.app.qa.framework;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

class DriverFactory {

  /**
   * Get driver from thread.
   *
   * @return driver
   */
  static WebDriver createInstance(String driverName, String downloadPath) {
    WebDriver driver;

    URL seleniumHub = null;
    URL zaleniumHub = null;

    try {
      seleniumHub = new URL(
      "http://10.100.0.37:4444/wd/hub"); //URL may change if ip address changes
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }

    try {
      zaleniumHub = new URL(
          ResourceFactory.getInstance().getProperty(ResourceFactory.ZALENIUM_HUB_IP)
              + ":4444/wd/hub");
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }

    System.setProperty("wdm.chromeDriverVersion", "2.35");
    HashMap<String, Object> chromePrefs;
    DesiredCapabilities capability;

    switch (driverName) {
      //If you are making any changes to Chrome please make the same change for ChromeHeadless too.
      case "Chrome":
        WebDriverManager.chromedriver().setup();

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--incognito");
        chromeOptions.addArguments("--start-maximized");

        chromePrefs = new HashMap<>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", downloadPath);

        chromeOptions.setExperimentalOption("prefs", chromePrefs);

        capability = DesiredCapabilities.chrome();
        capability.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

        driver = new ChromeDriver(capability);

        return driver;

      case "ChromeHeadless":
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--test-type");
        options.addArguments("--headless");
        options.addArguments("--disable-extensions");

        //Instantiate above options in driverService
        ChromeDriverService driverService = ChromeDriverService.createDefaultService();
        driver = new ChromeDriver(driverService, options);

        Map<String, Object> commandParams = new HashMap<>();
        commandParams.put("cmd", "Page.setDownloadBehavior");

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("behavior", "allow");
        params.put("downloadPath", downloadPath);
        params.put("cmd", "Page.setDownloadBehavior");

        commandParams.put("params", params);
        ObjectMapper om = new ObjectMapper();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String command = null;
        try {
          command = om.writeValueAsString(commandParams);
        } catch (JsonProcessingException jpe) {
          jpe.printStackTrace();
        }
        String postURL = driverService.getUrl().toString() + "/session/"
            + ((ChromeDriver) driver).getSessionId() + "/chromium" + "/send_command";
        HttpPost postRequest = new HttpPost(postURL);
        postRequest.addHeader("content-type", "application/json");
        postRequest.addHeader("accept", "*.*");
        try {
          postRequest.setEntity(new StringEntity(command));
          httpClient.execute(postRequest);
        } catch (UnsupportedEncodingException uee) {
          uee.printStackTrace();
        } catch (IOException ioe) {
          ioe.printStackTrace();
        }

        return driver;

      case "Firefox":
        WebDriverManager.firefoxdriver().setup();

        DesiredCapabilities capabilities = DesiredCapabilities.firefox();

        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("browser.download.folderList", 2);
        profile.setPreference("browser.download.manager.showWhenStarting", false);
        profile.setPreference("browser.helperApps.alwaysAsk.force", false);
        profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/vnd.ms-excel");
        profile.setPreference("browser.download.dir", downloadPath);
        profile.setPreference("browser.download.manager.focusWhenStarting", false);
        FirefoxOptions optionsF = new FirefoxOptions();
        optionsF.setProfile(profile);
        optionsF.addCapabilities(capabilities);

        driver = new FirefoxDriver(optionsF);

        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();

        return driver;

      case "Selenium-Grid":
        driver = new RemoteWebDriver(seleniumHub, remoteChrome(downloadPath));

        return driver;

      case "Zalenium":
        RemoteWebDriver driverRemote = new RemoteWebDriver(zaleniumHub, remoteChrome(downloadPath));

        driverRemote.setFileDetector(new LocalFileDetector());
        driverRemote.manage().window().maximize();

        return driverRemote;

      default:
        //returning chromedriver as default
        return new ChromeDriver();
    }
  }

  private static DesiredCapabilities remoteChrome(String downloadPath) {
    ChromeOptions chromeOptionsRemote = new ChromeOptions();
    chromeOptionsRemote.addArguments("--headless");
    chromeOptionsRemote.addArguments("--incognito");
    chromeOptionsRemote.addArguments("--no-sandbox");
    chromeOptionsRemote.addArguments("--disable-dev-shm-usage");

    HashMap<String, Object> chromePrefs = new HashMap<>();
    chromePrefs.put("profile.default_content_settings.popups", 0);
    chromePrefs.put("download.default_directory", downloadPath);

    chromeOptionsRemote.setExperimentalOption("prefs", chromePrefs);

    DesiredCapabilities capabilities = DesiredCapabilities.chrome();
    capabilities.setBrowserName("chrome");
    capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
    capabilities.setPlatform(org.openqa.selenium.Platform.ANY);
    capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptionsRemote);
    capabilities.setCapability("idleTimeout", 300);

    return capabilities;
  }
}