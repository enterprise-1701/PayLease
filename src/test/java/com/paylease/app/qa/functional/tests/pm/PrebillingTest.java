package com.paylease.app.qa.functional.tests.pm;

import com.paylease.app.qa.framework.pages.pm.PrebillPage;
import org.testng.annotations.Test;

/**
 * Tests for the Prebilling Page in the PM UI.
 */
public class PrebillingTest extends Billing {

  /**
   * Search for a property by ref id.
   */
  @Test
  public void searchByRefId() {
    PrebillPage preBill = new PrebillPage();
    searchByRefId(preBill);
  }

  /**
   * Search for a property by name.
   */
  @Test
  public void searchByPropName() {
    PrebillPage preBill = new PrebillPage();
    searchByPropName(preBill);
  }

  /**
   * Search for multiple properties.
   */
  @Test
  public void searchMultipleProps() {
    PrebillPage preBill = new PrebillPage();
    searchMultipleProps(preBill);
  }
}
