package com.paylease.app.qa.framework;

public class BrickFtpUtil extends FtpUtil {

  /**
   * Constructor.
   *
   * @param username the username
   * @param password the password
   */
  public BrickFtpUtil(String username, String password) {
    super(username, password, "files.paylease.com");
  }
}
