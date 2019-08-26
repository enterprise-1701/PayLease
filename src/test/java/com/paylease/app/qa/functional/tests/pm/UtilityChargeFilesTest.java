package com.paylease.app.qa.functional.tests.pm;

import com.paylease.app.qa.framework.pages.pm.UtilityChargeFiles;
import org.testng.annotations.Test;

/**
 * Tests for the Utility Charge Files Page in the PM UI.
 */
public class UtilityChargeFilesTest extends Billing {
  /**
   * Search for a property by ref id.
   */
  @Test
  public void searchByRefId() {
    UtilityChargeFiles utilityChargeFiles = new UtilityChargeFiles();
    searchByRefId(utilityChargeFiles);
  }

  /**
   * Search for a property by name.
   */
  @Test
  public void searchByPropName() {
    UtilityChargeFiles utilityChargeFiles = new UtilityChargeFiles();
    searchByPropName(utilityChargeFiles);
  }

  /**
   * Search for multiple properties.
   */
  @Test
  public void searchMultipleProps() {
    UtilityChargeFiles utilityChargeFiles = new UtilityChargeFiles();
    searchMultipleProps(utilityChargeFiles);
  }
}
