package com.paylease.app.qa.functional.tests.pm;

import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.pm.BillingAndUem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.testng.Assert;

abstract public class BillingAndExpenseManagement extends ScriptBase {

  /**
   * Holds the properties being searched.
   */
  abstract protected List<HashMap<String, Object>> openAppWithSetupData(BillingAndUem billingAndUem,
      String logMessage);

  //------------------------------------Test Method----------------------------------------------

  /**
   * This test is to verify if we can search a property by property ref ID.
   */
  public void searchByRefId(BillingAndUem billingAndExpenseManagement) {
    List<HashMap<String, Object>> properties = openAppWithSetupData(billingAndExpenseManagement,
        "Searching property by id");
    HashMap<String, Object> prop = properties.get(0);
    String expectedProp = prop.get("name").toString() + " | " + prop.get("refId").toString();
    String actualProp = billingAndExpenseManagement.findProp(prop.get("refId").toString());

    Assert.assertEquals(actualProp, expectedProp,
        "Unexpected property found when searched by reference Id");
  }

  /**
   * This test is to verify if we can search a property by property property name.
   */
  public void searchByPropName(BillingAndUem billingAndExpenseManagement) {
    List<HashMap<String, Object>> properties = openAppWithSetupData(billingAndExpenseManagement,
        "Searching property by name");
    HashMap<String, Object> prop = properties.get(0);
    String propToSearch = prop.get("name").toString() + " | " + prop.get("refId").toString();

    Assert.assertEquals(billingAndExpenseManagement.findProp(prop.get("name").toString()),
        propToSearch,
        "Unexpected property found when searched by property name");
  }

  /**
   * This test will verify if all properties with the name starting with portion of a string
   * entered, show in suggestion.
   */
  public void searchMultipleProps(BillingAndUem billingAndExpenseManagement) {
    List<HashMap<String, Object>> properties = openAppWithSetupData(billingAndExpenseManagement,
        "Searching for multiple properties by ref id");

    ArrayList<String> expectedProperties = new ArrayList<>();
    HashMap<String, Object> prop = new HashMap<>();
    for (int i = 0; i < 2; ++i) {
      prop = properties.get(i);
      expectedProperties.add(prop.get("name").toString() + " | " + prop.get("refId").toString());
    }

    ArrayList<String> actualProperties = billingAndExpenseManagement
        .findProps((prop.get("refId").toString()).substring(0, 2));
    boolean flag = actualProperties.size() >= expectedProperties.size();

    Assert.assertTrue(flag,
        "Properties showed in search suggestion are less the expected properties");

    for (int i = 0; i < 2; i++) {
      String tmp = expectedProperties.get(i);
      if (!(actualProperties.contains(tmp))) {
        flag = false;
      }
    }

    Assert.assertTrue(flag, "Not all expected properties are showed in search suggestion");
  }
}
