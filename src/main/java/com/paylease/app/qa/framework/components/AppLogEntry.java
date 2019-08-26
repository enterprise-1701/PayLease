package com.paylease.app.qa.framework.components;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class AppLogEntry {

  private static final int GROUP_LOG_LEVEL = 2;
  private static final int GROUP_LOG_MESSAGE = 3;
  private static final int GROUP_LOG_CONTEXT = 4;

  private String logLevel;
  private String logMessage;
  private JSONObject logContext;

  public AppLogEntry(String logEntry) throws Exception {
    parseLogEntry(logEntry);
  }

  public String getLogLevel() {
    return logLevel;
  }

  public String getLogMessage() {
    return logMessage;
  }

  public JSONObject getLogContext() {
    return logContext;
  }

  private void parseLogEntry(String logEntry) throws Exception {
    String regex = "^\\[(.*)\\] (\\w+.)*: (.*?) (\\{.*\\}) (\\{.*\\})$";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(logEntry);

    if (!matcher.find()) {
      throw new Exception("Unable to parse log entry: " + logEntry);
    }

    logLevel = matcher.group(GROUP_LOG_LEVEL);
    logMessage = matcher.group(GROUP_LOG_MESSAGE);

    JSONParser parser = new JSONParser();

    logContext = (JSONObject) parser.parse(matcher.group(GROUP_LOG_CONTEXT));
  }
}
