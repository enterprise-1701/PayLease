package com.paylease.app.qa.framework.utility.sshtool;

import static com.paylease.app.qa.framework.AppConstant.PATH_TO_LOG_FILES;
import static com.paylease.app.qa.framework.AppConstant.PATH_TO_USER_LOG;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ResourceFactory;

public class LogUtil extends SshUtil {

  /**
   * Get the file path of the provided log in the logs/application folder.
   *
   * @param logName name of log
   * @return String
   */
  public String getPathToApplicationsLog(String logName) {
    return PATH_TO_LOG_FILES + "*_" + logName + ".log";
  }

  /**
   * Get the file path to your error log.
   *
   * @return String
   */
  public String getPathToUserLog() {
    String username = ResourceFactory.getInstance().getProperty(ResourceFactory.SSH_USERNAME_KEY);
    return PATH_TO_USER_LOG + username + "_error_log";
  }
}
