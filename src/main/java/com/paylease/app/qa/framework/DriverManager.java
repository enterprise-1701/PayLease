package com.paylease.app.qa.framework;

import org.openqa.selenium.WebDriver;

public class DriverManager {

  private static ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();

  static void setWebDriver(WebDriver driver) {
    webDriver.set(driver);
  }

  public static WebDriver getDriver() {
    return webDriver.get();
  }

  static void closeDriver() {
    if (webDriver != null) {
      webDriver.get().quit();
    }
  }
}

