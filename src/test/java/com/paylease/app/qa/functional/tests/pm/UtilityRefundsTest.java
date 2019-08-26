package com.paylease.app.qa.functional.tests.pm;

import com.paylease.app.qa.framework.pages.pm.UtilityRefunds;
import org.testng.annotations.Test;

public class UtilityRefundsTest extends ExpenseManagement {

  /**
   * Search for a property by ref id.
   */
  @Test
  public void searchByRefId() {
    UtilityRefunds uem = new UtilityRefunds();
    searchByRefId(uem);
  }

  /**
   * Search for a property by name.
   */
  @Test
  public void searchByPropName() {
    UtilityRefunds uem = new UtilityRefunds();
    searchByPropName(uem);
  }

  /**
   * Search for multiple properties.
   */
  @Test
  public void searchMultipleProps() {
    UtilityRefunds uem = new UtilityRefunds();
    searchMultipleProps(uem);
  }

}
