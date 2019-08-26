package com.paylease.app.qa.functional.tests.pm;

import com.paylease.app.qa.framework.pages.pm.VacantCostRecovery;
import org.testng.annotations.Test;

public class VacantCostRecoveryTest extends ExpenseManagement {

  /**
   * Search for a property by ref id.
   */
  @Test
  public void searchByRefId() {
    VacantCostRecovery uem = new VacantCostRecovery();
    searchByRefId(uem);
  }

  /**
   * Search for a property by name.
   */
  @Test
  public void searchByPropName() {
    VacantCostRecovery uem = new VacantCostRecovery();
    searchByPropName(uem);
  }

  /**
   * Search for multiple properties.
   */
  @Test
  public void searchMultipleProps() {
    VacantCostRecovery uem = new VacantCostRecovery();
    searchMultipleProps(uem);
  }
}
