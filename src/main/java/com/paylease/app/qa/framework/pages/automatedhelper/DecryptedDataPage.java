package com.paylease.app.qa.framework.pages.automatedhelper;

import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.By;

public class DecryptedDataPage extends PageBase {

  public static final String URL = BASE_URL + "testing/automated_helper/get_decrypted_data";

  /**
   * Open the page with given parameters.
   *
   * @param link Encrypted data
   */
  public void open(String link) {
    openAndWait(URL + "?link=" + link);
  }

  /**
   * Get value from the page by the key.
   *
   * @param key to find
   * @return value found
   */
  public String getValue(String key) {
    return getTextBySelector(By.id(key));
  }
}
