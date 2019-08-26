package com.paylease.app.qa.framework;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * Created by Mahfuz Alam on 09/25/2017.
 */
public class ResourceFactory {

  public static final String APP_URL_KEY = "APP_URL";
  public static final String APP_URL_ACCESS_PUBLIC_KEY = "APP_URL_ACCESS_PUBLIC";
  public static final String HIGHLIGHT_KEY = "HIGHLIGHT";
  public static final String LOG_LEVEL_KEY = "LOG_LEVEL";
  public static final String BROWSER_KEY = "BROWSER";
  public static final String DB_HOST_KEY = "DB_HOST";
  public static final String SSH_PRIVATE_KEY_PATH_KEY = "SSH_PRIVATE_KEY_PATH";
  public static final String SSH_USERNAME_KEY = "SSH_USERNAME";
  public static final String SSH_PASSPHRASE_KEY = "SSH_PASSPHRASE";
  public static final String SSH_HOST_KEY = "SSH_HOST";
  public static final String WEB_APP_ROOT_DIR_KEY = "WEB_APP_ROOT_DIR";
  public static final String STUB_HOST = "STUB_HOST";
  public static final String ZALENIUM_HUB_IP = "ZALENIUM_HUB_IP";
  public static final String USE_VANTIV_STUB = "USE_VANTIV_STUB";
  public static final String STUB_VANTIV_URL_KEY = "STUB_VANTIV_URL";
  public static final String ZALENIUM_MOUNTED_DIR = "ZALENIUM_MOUNTED_DIR";
  public static final String DEPOSIT_SERVICE_DIRECTORY_KEY = "DEPOSIT_SERVICE_DIRECTORY";

  private static final String PROPERTIES_FILE_PATH =
      "src/test/resources/config/AppConfig.properties";
  private static ResourceFactory instance = null;
  private Properties appProperties;

  private ResourceFactory() {
    appProperties = new Properties();
    try {
      appProperties.load(new FileInputStream(new File(PROPERTIES_FILE_PATH)));
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Get singleton instance.
   */
  public static ResourceFactory getInstance() {
    if (instance == null) {
      instance = new ResourceFactory();
    }
    return instance;
  }

  /**
   * Get a property from the file.
   */
  public String getProperty(String key) {
    return appProperties.getProperty(key);
  }

  /**
   * Get a flag from the file.
   */
  public Boolean getFlag(String key) {
    return Boolean.parseBoolean(appProperties.getProperty(key));
  }

  public boolean keyExists(String key) {
    return appProperties.containsKey(key);
  }
}
