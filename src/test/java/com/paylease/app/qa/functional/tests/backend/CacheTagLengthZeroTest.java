package com.paylease.app.qa.functional.tests.backend;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.automatedhelper.LogInProcPage;
import com.paylease.app.qa.framework.utility.sshtool.LogUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CacheTagLengthZeroTest extends ScriptBase {

  private static final String ERROR = "Cache tag length must be greater than zero";

  @Test
  public void tc6863() {
    Logger
        .info("Run the admin login proc and confirm that the log does not have a 'cache tag length"
            + " cannot be zero' error");

    boolean actualResult = doesLogCacheTagLengthErrorCountMatchPreviousCount("admin");

    Assert.assertTrue(actualResult, "Cache tag length error appeared when it should not have");
  }

  @Test
  public void tc6864() {
    Logger.info("Run the pm login proc and confirm that the log does not have a 'cache tag length"
        + " cannot be zero' error");

    boolean actualResult = doesLogCacheTagLengthErrorCountMatchPreviousCount("pm");

    Assert.assertTrue(actualResult, "Cache tag length error appeared when it should not have");
  }

  @Test
  public void tc6865() {
    Logger.info(
        "Run the resident login proc and confirm that the log does not have a 'cache tag length"
            + " cannot be zero' error");

    boolean actualResult = doesLogCacheTagLengthErrorCountMatchPreviousCount("resident");

    Assert.assertTrue(actualResult, "Cache tag length error appeared when it should not have");
  }

  @Test
  public void tc6866() {
    Logger.info(
        "Run the index login proc and confirm that the log does not have a 'cache tag length"
            + " cannot be zero' error");

    boolean actualResult = doesLogCacheTagLengthErrorCountMatchPreviousCount("index");

    Assert.assertTrue(actualResult, "Cache tag length error appeared when it should not have");
  }

  @Test
  public void tc6867() {
    Logger.info(
        "Run the login proc without an additional variable and confirm that the log does not have a"
            + " 'cache tag length cannot be zero' error");

    boolean actualResult = doesLogCacheTagLengthErrorCountMatchPreviousCount("");

    Assert.assertTrue(actualResult, "Cache tag length error appeared when it should not have");
  }

  private boolean doesLogCacheTagLengthErrorCountMatchPreviousCount(String type) {
    LogInProcPage logInProcPage = new LogInProcPage(type);

    LogUtil logUtil = new LogUtil();
    String logFilePath = logUtil.getPathToUserLog();

    /**
     * Determine if the error log already has the error message before opening the
     * login/login_proc page.
     */
    int errorStartingCount = logUtil.getStringMatchCountFromFile(ERROR, logFilePath);

    logInProcPage.open();

    /**
     * Review the error log and search for the error message after opening the
     * login/login_proc page.
     */
    int errorEndingCount = logUtil.getStringMatchCountFromFile(ERROR, logFilePath);

    return errorStartingCount == errorEndingCount;
  }
}
