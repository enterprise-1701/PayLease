package com.paylease.app.qa.functional.tests.pm;

import com.paylease.app.qa.framework.pages.pm.UtilityBillSearch;
import org.testng.annotations.Test;

public class UtilityBillSearchTest extends ExpenseManagement {
  /**
   * Search for a property by ref id.
   */
  @Test
  public void searchByRefId() {
    UtilityBillSearch uem = new UtilityBillSearch();
    searchByRefId(uem);
  }

  /**
   * Search for a property by name.
   */
  @Test
  public void searchByPropName() {
    UtilityBillSearch uem = new UtilityBillSearch();
    searchByPropName(uem);
  }

  /**
   * Search for multiple properties.
   */
  @Test
  public void searchMultipleProps() {
    UtilityBillSearch uem = new UtilityBillSearch();
    searchMultipleProps(uem);
  }

}
