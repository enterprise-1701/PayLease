package com.paylease.app.qa.framework;

import static com.paylease.app.qa.framework.pages.PageBase.BASE_URL;

public class AppConstant {

  public static final String QA_ADMIN_USER = "quality.engr@paylease.com";
  public static final String QA_ADMIN_NAME = "quality engr";
  public static final String QA_ADMIN_PASSWORD = "Password123!";

  public static final String DB_USERNAME = "loadui";
  public static final String DB_PASSWORD = "YBfimmhEUtW42TY";

  public static final String FAQ_LINK = "https://paylease.zendesk.com/hc/en-us/categories/202633718-Residents";

  public static final String QA_MAILBOX = "quality.engr@paylease.com";
  public static final String QA_MAILBOX_PASSWORD = "rahxvykriaongemx";

  public static final String PATH_TO_LOG_FILES = "/var/paylease/logs/application/";
  public static final String APP_LOG_PATH_ENV_VARNAME = "LOG_BASEDIR";
  public static final String PATH_TO_USER_LOG = "/var/log/httpd/";

  public static final String CHECK_IMAGE_BASE_URL = BASE_URL + "resources/check_image/v1?link";

  public static final String KAFKA_BROKER_URL = "heavy-dogsled-01.srvs.cloudkafka.com:9094, "
      + "heavy-dogsled-02.srvs.cloudkafka.com:9094, heavy-dogsled-03.srvs.cloudkafka.com:9094";
  public static final String USERNAME = "oyrwnzyt";
  public static final String PASSWORD = "7BwUoS3SbuDBM8rLkAGnTmJ9argtMr4k";
}
