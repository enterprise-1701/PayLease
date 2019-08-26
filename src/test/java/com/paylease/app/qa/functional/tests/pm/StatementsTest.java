package com.paylease.app.qa.functional.tests.pm;

import com.paylease.app.qa.framework.pages.pm.Statements;
import org.testng.annotations.Test;

/**
 * Tests for the Statements Page in the PM UI.
 */
public class StatementsTest extends Billing {

  /**
   * Search for a property by ref id.
   */
  @Test
  public void searchByRefId() {
    Statements statements = new Statements();
    searchByRefId(statements);
  }

  /**
   * Search for a property by name.
   */
  @Test
  public void searchByPropName() {
    Statements statements = new Statements();
    searchByPropName(statements);
  }

  /**
   * Search for multiple properties.
   */
  @Test
  public void searchMultipleProps() {
    Statements statements = new Statements();
    searchMultipleProps(statements);
  }
}
