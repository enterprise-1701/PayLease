package com.paylease.app.qa.framework;

import static com.paylease.app.qa.framework.ResourceFactory.LOG_LEVEL_KEY;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.testng.Reporter;

public class Logger {

  private static final int LOG_LEVEL_ERROR = 0;
  private static final int LOG_LEVEL_WARNING = 1;
  private static final int LOG_LEVEL_INFO = 2;
  private static final int LOG_LEVEL_TRACE = 5;
  private static final int LOG_LEVEL_DEBUG = 10;

  private static void log(String message, int level) {
    String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    message = timestamp + " - " + message;

    ResourceFactory properties = ResourceFactory.getInstance();
    if (properties.keyExists(LOG_LEVEL_KEY)) {
      int maxLevel = Integer.parseInt(ResourceFactory.getInstance().getProperty(LOG_LEVEL_KEY));
      if (level <= maxLevel) {
        Reporter.log(message, true);
      }
    } else {
      Reporter.log(message, level, true);
    }
  }

  public static void error(String message) {
    message = "[ERROR] - " + message;
    log(message, LOG_LEVEL_ERROR);
  }

  public static void warning(String message) {
    message = "[WARNING] - " + message;
    log(message, LOG_LEVEL_WARNING);
  }

  public static void info(String message) {
    message = "[INFO] - " + message;
    log(message, LOG_LEVEL_INFO);
  }

  public static void trace(String message) {
    message = "[TRACE] - " + message;
    log(message, LOG_LEVEL_TRACE);
  }

  public static void debug(String message) {
    message = "[DEBUG] - " + message;
    log(message, LOG_LEVEL_DEBUG);
  }
}
