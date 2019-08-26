package com.paylease.app.qa.framework.pages.backend;

import com.paylease.app.qa.framework.pages.PageBase;

public class BrickFtpWebhookRequestPage extends PageBase {

  protected static final String URL = BASE_URL + "webhook/brickftp/v1";

  /**
   * The url.
   */
  private String url;

  /**
   * Constructor.
   *
   * @param action Action
   * @param iface Interface
   * @param path Path
   * @param destination Destination
   * @param at At
   * @param username Username
   * @param type Type
   */
  public BrickFtpWebhookRequestPage(String action, String iface, String path,
      String destination, String at, String username, String type) {
    String url = "?";

    if (null != action) {
      url += "&action=" + action;
    }

    if (null != iface) {
      url += "&interface=" + iface;
    }

    if (null != path) {
      url += "&path=" + path;
    }

    if (null != destination) {
      url += "&destination=" + destination;
    }

    if (null != at) {
      url += "&at=" + at;
    }

    if (null != username) {
      url += "&username=" + username;
    }

    if (null != type) {
      url += "&type=" + type;
    }

    this.url = URL + url;
  }

  public void open() {
    openAndWait(url);
  }
}
