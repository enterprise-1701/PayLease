package com.paylease.app.qa.functional.tests.pm;

import com.paylease.app.qa.framework.pages.pm.MoveOutCalculator;
import org.testng.annotations.Test;

/**
 * Tests for the Move Out Calculator Page in the PM UI.
 */
public class MoveOutCalculatorTest extends Billing {

  /**
   * Search for a property by ref id.
   */
  @Test
  public void searchByRefId() {
    MoveOutCalculator moveOutCalculator = new MoveOutCalculator();
    searchByRefId(moveOutCalculator);
  }

  /**
   * Search for a property by name.
   */
  @Test
  public void searchByPropName() {
    MoveOutCalculator moveOutCalculator = new MoveOutCalculator();
    searchByPropName(moveOutCalculator);
  }

  /**
   * Search for multiple properties.
   */
  @Test
  public void searchMultipleProps() {
    MoveOutCalculator moveOutCalculator = new MoveOutCalculator();
    searchMultipleProps(moveOutCalculator);
  }

}
