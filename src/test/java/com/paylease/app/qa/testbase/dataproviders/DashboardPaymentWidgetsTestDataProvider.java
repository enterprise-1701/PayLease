package com.paylease.app.qa.testbase.dataproviders;

import org.testng.annotations.DataProvider;

public class DashboardPaymentWidgetsTestDataProvider {

  /**
   * Test data for viewing the report widgets on the pm dashboard.
   *
   * @return Object data
   */
  @DataProvider(name = "dashboardTestData", parallel = true)
  public Object[][] dashboardTestData() {

    return new Object[][]{
        //testCase, testCaseInfo

        {"tc01", "Test case data where pm has UEM enabled."},
        {"tc02", "Test case data where pm has both UEM and Billing enabled."},
        {"tc03", "Test case data where pm does not have UEM or Billing enabled."},
        {"tc04", "Test case data where pm has Billing enabled."},
    };
  }

  /**
   * Test data for adding a report widget on the PM Dashboard.
   * This is dependent on the report types in the report_type table.
   *
   * @return Object data
   */
  @DataProvider(name = "dashboardAddWidgetTestData", parallel = true)
  public Object[][] dashboardAddWidgetTestData() {
    return new Object[][]{
        //testCase, testCaseInfo, reportIdToAdd

        //report type: GRAPH_PAYMENT_METHOD
        {"tc01", "Test case data where pm has UEM enabled report type "
            + "GRAPH_PAYMENT_METHOD is ", 1},
        {"tc02", "Test case data where pm has both UEM and Billing enabled and report type "
            + "GRAPH_PAYMENT_METHOD is ", 1},
        {"tc03", "Test case data where pm does not have UEM or Billing enabled and report type "
            + "GRAPH_PAYMENT_METHOD is .", 1},
        {"tc04", "Test case data where pm has Billing enabled and report type "
            + "GRAPH_PAYMENT_METHOD is ", 1},

        //report type: GRAPH_UTILIZATION_PROPERTIES
        {"tc01", "Test case data where pm has UEM enabled and report type "
            + "GRAPH_UTILIZATION_PROPERTIES is ", 2},
        {"tc02", "Test case data where pm has both UEM and Billing enabled and report type "
            + "GRAPH_UTILIZATION_PROPERTIES is ", 2},
        {"tc03", "Test case data where pm does not have UEM or Billing enabled and report type "
            + "GRAPH_UTILIZATION_PROPERTIES is ", 2},
        {"tc04", "Test case data where pm has Billing enabled and report type "
            + "GRAPH_UTILIZATION_PROPERTIES is ", 2},

        //report type: GRAPH_UTILIZATION_TOTAL
        {"tc01", "Test case data where pm has UEM enabled and report type "
            + "GRAPH_UTILIZATION_TOTAL is ", 3},
        {"tc02", "Test case data where pm has both UEM and Billing enabled and report type "
            + "GRAPH_UTILIZATION_TOTAL is ", 3},
        {"tc03", "Test case data where pm does not have UEM or Billing enabled and report type "
            + "GRAPH_UTILIZATION_TOTAL is ", 3},
        {"tc04", "Test case data where pm has Billing enabled and report type "
            + "GRAPH_UTILIZATION_TOTAL is ", 3},

        //report type: GRAPH_RECENT_TRANSACTION
        {"tc01", "Test case data where pm has UEM enabled and report type "
            + "GRAPH_RECENT_TRANSACTION is ", 4},
        {"tc02", "Test case data where pm has both UEM and Billing enabled and report type "
            + "GRAPH_RECENT_TRANSACTION is ", 4},
        {"tc03", "Test case data where pm does not have UEM or Billing enabled and report type "
            + "GRAPH_RECENT_TRANSACTION is ", 4},
        {"tc04", "Test case data where pm has Billing enabled and report type "
            + "GRAPH_RECENT_TRANSACTION is ", 4},

        //report type: GRAPH_MARKETING_INCENTIVE
        {"tc01", "Test case data where pm has UEM enabled and report type "
            + "GRAPH_MARKETING_INCENTIVE is ", 5},
        {"tc02", "Test case data where pm has both UEM and Billing enabled and report type "
            + "GRAPH_MARKETING_INCENTIVE is ", 5},
        {"tc03", "Test case data where pm does not have UEM or Billing enabled and report type "
            + "GRAPH_MARKETING_INCENTIVE is ", 5},
        {"tc04", "Test case data where pm has Billing enabled and report type "
            + "GRAPH_MARKETING_INCENTIVE is ", 5},

        //report type: GRAPH_RECOUP_SNAPSHOT
        {"tc01", "Test case data where pm has UEM enabled and report type "
            + "GRAPH_RECOUP_SNAPSHOT is ", 6},
        {"tc02", "Test case data where pm has both UEM and Billing enabled and report type "
            + "GRAPH_RECOUP_SNAPSHOT is ", 6},
        {"tc03", "Test case data where pm does not have UEM or Billing enabled and report type "
            + "GRAPH_RECOUP_SNAPSHOT is ", 6},
        {"tc04", "Test case data where pm has Billing enabled and report type "
            + "GRAPH_RECOUP_SNAPSHOT is ", 6},

        //report type: GRAPH_RECOUP_TREND
        {"tc01", "Test case data where pm has UEM enabled and report type "
            + "GRAPH_RECOUP_TREND is ", 7},
        {"tc02", "Test case data where pm has both UEM and Billing enabled and report type "
            + "GRAPH_RECOUP_TREND is ", 7},
        {"tc03", "Test case data where pm does not have UEM or Billing enabled and report type "
            + "GRAPH_RECOUP_TREND is ", 7},
        {"tc04", "Test case data where pm has Billing enabled and report type "
            + "GRAPH_RECOUP_TREND is ", 7},

        //report type: GRAPH_SUB_METER_HEALTH_PORTFOLIO_SNAPSHOT
        {"tc01", "Test case data where pm has UEM enabled and report type "
            + "GRAPH_SUB_METER_HEALTH_PORTFOLIO_SNAPSHOT is ", 8},
        {"tc02", "Test case data where pm has both UEM and Billing enabled and report type "
            + "GRAPH_SUB_METER_HEALTH_PORTFOLIO_SNAPSHOT is ", 8},
        {"tc03", "Test case data where pm does not have UEM or Billing enabled and report type "
            + "GRAPH_SUB_METER_HEALTH_PORTFOLIO_SNAPSHOT is ", 8},
        {"tc04", "Test case data where pm has Billing enabled and report type "
            + "GRAPH_SUB_METER_HEALTH_PORTFOLIO_SNAPSHOT is ", 8},

        //report type: GRAPH_SUB_METER_HEALTH_PROPERTY_SNAPSHOT
        {"tc01", "Test case data where pm has UEM enabled and report type "
            + "GRAPH_SUB_METER_HEALTH_PROPERTY_SNAPSHOT is ", 9},
        {"tc02", "Test case data where pm has both UEM and Billing enabled and report type "
            + "GRAPH_SUB_METER_HEALTH_PROPERTY_SNAPSHOT is ", 9},
        {"tc03", "Test case data where pm does not have UEM or Billing enabled and report type "
            + "GRAPH_SUB_METER_HEALTH_PROPERTY_SNAPSHOT is ", 9},
        {"tc04", "Test case data where pm has Billing enabled and report type "
            + "GRAPH_SUB_METER_HEALTH_PROPERTY_SNAPSHOT is ", 9},

        //report type: GRAPH_SUB_METER_HEALTH_TREND
        {"tc01", "Test case data where pm has UEM enabled and report type "
            + "GRAPH_SUB_METER_HEALTH_TREND is ", 10},
        {"tc02", "Test case data where pm has both UEM and Billing enabled and report type "
            + "GRAPH_SUB_METER_HEALTH_TREND is ", 10},
        {"tc03", "Test case data where pm does not have UEM or Billing enabled and report type "
            + "GRAPH_SUB_METER_HEALTH_TREND is ", 10},
        {"tc04", "Test case data where pm has Billing enabled and report type "
            + "GRAPH_SUB_METER_HEALTH_TREND is ", 10},

        //report type: GRAPH_SUB_METER_USAGE_SNAPSHOT
        {"tc01", "Test case data where pm has UEM enabled and report type "
            + "GRAPH_SUB_METER_USAGE_SNAPSHOT is ", 11},
        {"tc02", "Test case data where pm has both UEM and Billing enabled and report type "
            + "GRAPH_SUB_METER_USAGE_SNAPSHOT is ", 11},
        {"tc03", "Test case data where pm does not have UEM or Billing enabled and report type "
            + "GRAPH_SUB_METER_USAGE_SNAPSHOT is ", 11},
        {"tc04", "Test case data where pm has Billing enabled and report type "
            + "GRAPH_SUB_METER_USAGE_SNAPSHOT is ", 11},

        //report type: GRAPH_SUB_METER_USAGE_TREND
        {"tc01", "Test case data where pm has UEM enabled and report type "
            + "GRAPH_SUB_METER_USAGE_TREND is ", 12},
        {"tc02", "Test case data where pm has both UEM and Billing enabled and report type "
            + "GRAPH_SUB_METER_USAGE_TREND is ", 12},
        {"tc03", "Test case data where pm does not have UEM or Billing enabled and report type "
            + "GRAPH_SUB_METER_USAGE_TREND is ", 12},
        {"tc04", "Test case data where pm has Billing enabled and report type "
            + "GRAPH_SUB_METER_USAGE_TREND is ", 12},

        //report type: GRAPH_VIOLATION_SNAPSHOT
        {"tc01", "Test case data where pm has UEM enabled and report type "
            + "GRAPH_VIOLATION_SNAPSHOT is ", 13},
        {"tc02", "Test case data where pm has both UEM and Billing enabled and report type "
            + "GRAPH_VIOLATION_SNAPSHOT is ", 13},
        {"tc03", "Test case data where pm does not have UEM or Billing enabled and report type "
            + "GRAPH_VIOLATION_SNAPSHOT is ", 13},
        {"tc04", "Test case data where pm has Billing enabled and report type "
            + "GRAPH_VIOLATION_SNAPSHOT is ", 13},

        //report type: GRAPH_VIOLATION_TREND
        {"tc01", "Test case data where pm has UEM enabled and report type "
            + "GRAPH_VIOLATION_TREND is ", 14},
        {"tc02", "Test case data where pm has both UEM and Billing enabled and report type "
            + "GRAPH_VIOLATION_TREND is ", 14},
        {"tc03", "Test case data where pm does not have UEM or Billing enabled and report type "
            + "GRAPH_VIOLATION_TREND is ", 14},
        {"tc04", "Test case data where pm has Billing enabled and report type "
            + "GRAPH_VIOLATION_TREND is ", 14},

        //report type: GRAPH_EXPENSE_MANAGEMENT_COST_TREND
        {"tc01", "Test case data where pm has UEM enabled and report type "
            + "GRAPH_EXPENSE_MANAGEMENT_COST_TREND is ", 15},
        {"tc02", "Test case data where pm has both UEM and Billing enabled and report type "
            + "GRAPH_EXPENSE_MANAGEMENT_COST_TREND is ", 15},
        {"tc03", "Test case data where pm does not have UEM or Billing enabled and report type "
            + "GRAPH_EXPENSE_MANAGEMENT_COST_TREND is ", 15},
        {"tc04", "Test case data where pm has Billing enabled and report type "
            + "GRAPH_EXPENSE_MANAGEMENT_COST_TREND is ", 15},

        //report type: GRAPH_EXPENSE_MANAGEMENT_BILL_TREND
        {"tc01", "Test case data where pm has UEM enabled and report type "
            + "GRAPH_EXPENSE_MANAGEMENT_BILL_TREND is ", 16},
        {"tc02", "Test case data where pm has both UEM and Billing enabled and report type "
            + "GRAPH_EXPENSE_MANAGEMENT_BILL_TREND is ", 16},
        {"tc03", "Test case data where pm does not have UEM or Billing enabled and report type "
            + "GRAPH_EXPENSE_MANAGEMENT_BILL_TREND is ", 16},
        {"tc04", "Test case data where pm has Billing enabled and report type "
            + "GRAPH_EXPENSE_MANAGEMENT_BILL_TREND is ", 16},

        //report type: GRAPH_MOVE_OUT_SNAPSHOT
        {"tc01", "Test case data where pm has UEM enabled and report type "
            + "GRAPH_MOVE_OUT_SNAPSHOT is ", 17},
        {"tc02", "Test case data where pm has both UEM and Billing enabled and report type "
            + "GRAPH_MOVE_OUT_SNAPSHOT is ", 17},
        {"tc03", "Test case data where pm does not have UEM or Billing enabled and report type "
            + "GRAPH_MOVE_OUT_SNAPSHOT is ", 17},
        {"tc04", "Test case data where pm has Billing enabled and report type "
            + "GRAPH_MOVE_OUT_SNAPSHOT is ", 17},

        //report type: GRAPH_MOVE_OUT_TREND
        {"tc01", "Test case data where pm has UEM enabled and report type "
            + "GRAPH_MOVE_OUT_TREND is ", 18},
        {"tc02", "Test case data where pm has both UEM and Billing enabled and report type "
            + "GRAPH_MOVE_OUT_TREND is ", 18},
        {"tc03", "Test case data where pm does not have UEM or Billing enabled and report type "
            + "GRAPH_MOVE_OUT_TREND is ", 18},
        {"tc04", "Test case data where pm has Billing enabled and report type "
            + "GRAPH_MOVE_OUT_TREND is ", 18}
    };
  }
}
