package com.paylease.app.qa;

import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.components.AppLogEntry;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public class AppLogTester extends ScriptBase {
  
  public static final String LOG_LEVEL_INFO = "INFO";
  public static final String LOG_LEVEL_WARNING = "WARNING";
  public static final String LOG_LEVEL_ERROR = "ERROR";

  /**
   * Assert the logEntry has matching log level, message and context.
   *
   * @param logEntry Log Entry to test
   * @param expectedLogLevel Expected log level
   * @param expectedMessage Expected message - exact match
   * @param expectedLogContext Expected context object - asserts given object keys present with
   *                           matching values - context may include more content
   */
  public void assertLogEntry(
      AppLogEntry logEntry, String expectedLogLevel, String expectedMessage,
      JSONObject expectedLogContext
  ) {
    SoftAssert softAssert = new SoftAssert();

    softAssert.assertEquals(logEntry.getLogLevel(), expectedLogLevel,
        "Log level for initial log entry should be " + expectedLogLevel);
    softAssert.assertEquals(logEntry.getLogMessage(), expectedMessage,
        "Log message does not match");

    JSONObject actualLogContext = logEntry.getLogContext();

    softAssertJsonObjectEquals(softAssert, expectedLogContext, actualLogContext);

    softAssert.assertAll();
  }

  /**
   * Assert the log entry context does not contain the given key.
   *
   * @param logEntry Log Entry to test
   * @param key Key that should not be present
   */
  public void assertLogContextKeyNotPresent(AppLogEntry logEntry, String key) {
    Assert.assertFalse(logEntry.getLogContext().containsKey(key),
        "The key " + key + " should not be included in log context");
  }

  /**
   * Put a numeric value into a JSONObject.
   *
   * @param object JSONObject to put the numeric value into
   * @param key The location to put the numeric value at
   * @param number The numeric value to put
   */
  @SuppressWarnings("unchecked")
  public void putNumericInJsonObject(JSONObject object, String key, double number) {
    if (number % 1 == 0) {
      object.put(key, Math.round(number));
    } else {
      object.put(key, number);
    }
  }

  private void softAssertJsonObjectEquals(SoftAssert softAssert, JSONObject expected,
      JSONObject actual) {
    for (Iterator iter = expected.keySet().iterator(); iter.hasNext(); ) {
      String key = (String) iter.next();
      try {
        if (!actual.containsKey(key)) {
          softAssert.fail("Actual JSON Object missing key: " + key);
        } else {
          Object expectedValue = expected.get(key);
          Object actualValue = actual.get(key);

          if (actualValue instanceof JSONObject && expectedValue instanceof JSONObject) {
            softAssertJsonObjectEquals(softAssert, (JSONObject) expected.get(key),
                (JSONObject) actualValue);
          } else if (actualValue instanceof JSONArray && expectedValue instanceof JSONArray) {
            softAssertJsonArrayEquals(softAssert, (JSONArray) expectedValue,
                (JSONArray) actualValue);
          } else {
            softAssert.assertEquals(actualValue, expectedValue,
                "Values at key: " + key + " should be equal");
          }
        }
      } catch (Exception e) {
        softAssert.fail("Something went horribly wrong: " + e);
      }
    }
  }

  private void softAssertJsonArrayEquals(SoftAssert softAssert, JSONArray expected,
      JSONArray actual) {
    if (expected.size() != actual.size()) {
      softAssert.fail("JSON arrays should have the same size");
    }

    for (int i = 0; i < expected.size(); ++i) {
      Object actualValue = actual.get(i);
      Object expectedValue = expected.get(i);
      if (actualValue instanceof JSONObject && expectedValue instanceof JSONObject) {
        softAssertJsonObjectEquals(softAssert, (JSONObject) expectedValue,
            (JSONObject) actualValue);
      } else if (actualValue instanceof JSONArray && expectedValue instanceof JSONArray) {
        softAssertJsonArrayEquals(softAssert, (JSONArray) expectedValue, (JSONArray) actualValue);
      } else {
        softAssert.assertEquals(actual.get(i), expected.get(i),
            "Values at index: " + i + " should be equal");
      }
    }
  }
}
