package com.paylease.app.qa.framework.utility.database.client.dao;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.utility.database.client.dto.UserDashboardConfig;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * BatchItems Data Access Object.
 *
 * @author Jeffrey Walker
 */
public class UserDashboardConfigDao implements Dao<UserDashboardConfig>{

  private static final String TABLE_NAME = "user_dashboard_config";

  private ArrayList<UserDashboardConfig> dashboardConfigs;

  public int createDashboardConfigSetup(Connection connection, String pmId,
      ArrayList<String> reportTypeIdList) {
    int totalNumOfRowsAffected = 0;
    try {
      //Check if pm already has entries, and if yes then delete them
      ArrayList<UserDashboardConfig> currentDashboardConfigs = findByUserId(connection, pmId, 100);

      if (currentDashboardConfigs.size() > 0) {
        for (UserDashboardConfig dashboardConfig : currentDashboardConfigs) {
          deleteConfigs(connection, dashboardConfig);
        }
      }

      if (reportTypeIdList == null) {
        reportTypeIdList = new ArrayList<>();
        Statement statement = connection.createStatement();
        String tableSQL = "SELECT * FROM report_type";
        Logger.debug("SQL script: " + tableSQL);
        ResultSet reportTypeIdResultSet = statement.executeQuery(tableSQL);

        String reportId = "";
        while (reportTypeIdResultSet.next()) {
          reportId = reportTypeIdResultSet.getString("id");
          reportTypeIdList.add(reportId);
        }
      }

      PreparedStatement ps = connection.prepareStatement("INSERT INTO " + TABLE_NAME
          + " (`user_id`, `report_type_id`, `display_order`) VALUES (?, ?, ?)");

      for (String reportTypeId : reportTypeIdList) {
        ps.setString(1, pmId);
        ps.setString(2, reportTypeId);
        ps.setString(3, String.valueOf(reportTypeIdList.indexOf(reportTypeId) + 1));
        totalNumOfRowsAffected = totalNumOfRowsAffected + ps.executeUpdate();
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    return totalNumOfRowsAffected;
  }

  @Override
  public ArrayList<UserDashboardConfig> findById(Connection connection, long id, int limit) {
    try {
      Statement statement = connection.createStatement();

      String tableSQL = "SELECT * FROM " + TABLE_NAME + " WHERE id = '" + id + "' LIMIT "
          + limit;

      ResultSet resultSet = statement.executeQuery(tableSQL);

      processResultSet(resultSet);

    } catch (Exception e) {
      Logger.error(e.getMessage());
    }

    return dashboardConfigs;
  }

  private ArrayList<UserDashboardConfig> findByUserId(Connection connection, String userId,
      int limit) {
    try {
      Statement statement = connection.createStatement();

      String tableSQL = "SELECT * FROM " + TABLE_NAME + " WHERE user_id = '" + userId + "' LIMIT "
          + limit;

      ResultSet resultSet = statement.executeQuery(tableSQL);

      processResultSet(resultSet);
    } catch (Exception e) {
      Logger.error(e.getMessage());
    }

    return dashboardConfigs;
  }

  public ArrayList<UserDashboardConfig> findByUserIdAndReportTypeId(Connection connection,
      String userId, int reportTypeId, int limit) {
    try {
      Statement statement = connection.createStatement();

      String tableSQL =
          "SELECT * FROM " + TABLE_NAME + " WHERE user_id = '" + userId + "' AND report_type_id = '"
              + reportTypeId + "'LIMIT "
              + limit;

      ResultSet resultSet = statement.executeQuery(tableSQL);

      processResultSet(resultSet);
    } catch (Exception e) {
      Logger.error(e.getMessage());
    }

    return dashboardConfigs;
  }


  @Override
  public int update(Connection connection, UserDashboardConfig dashboardConfig) {
    int numOfRowsAffected = 0;

    try {
      PreparedStatement ps = connection.prepareStatement("UPDATE " + TABLE_NAME
          + " SET user_id=?, report_type_id=?,custom_report_id=?, display_order=? WHERE id=?");
      ps.setString(1, dashboardConfig.getUserId());
      ps.setString(2, dashboardConfig.getReportTypeId());
      ps.setString(3, dashboardConfig.getCustomReportId());
      ps.setString(4, dashboardConfig.getDisplayOrder());

      numOfRowsAffected = ps.executeUpdate();
    } catch (Exception e) {
      Logger.error(e.getMessage());
    }

    return numOfRowsAffected;
  }


  @Override
  public boolean create(Connection connection, UserDashboardConfig dashboardConfig) {
    int numOfRowsAffected = 0;
    try {
      PreparedStatement ps = connection.prepareStatement("INSERT INTO " + TABLE_NAME
          + " (user_id,report_type_id,custom_report_id,display_order) VALUES (?,?,?,?)");
      ps.setString(1, dashboardConfig.getUserId());
      ps.setString(2, dashboardConfig.getReportTypeId());
      ps.setString(3, dashboardConfig.getCustomReportId());
      ps.setString(4, dashboardConfig.getDisplayOrder());
      numOfRowsAffected = ps.executeUpdate();
    } catch (Exception e) {
      Logger.error(e.getMessage());
    }
    if (numOfRowsAffected == 0) {
      return false;
    }

    return true;
  }

  @Override
  public boolean delete(Connection connection, UserDashboardConfig dashboardConfig) {
    return false;
  }


  public int deleteConfigs(Connection connection, UserDashboardConfig dashboardConfig) {
    int numOfRowsAffected = 0;

    try {
      PreparedStatement ps = connection
          .prepareStatement("DELETE FROM " + TABLE_NAME + " WHERE user_id=? AND report_type_id=?");
      ps.setString(1, dashboardConfig.getUserId());
      ps.setString(2, dashboardConfig.getReportTypeId());

      numOfRowsAffected = ps.executeUpdate();
    } catch (Exception e) {
      Logger.error(e.getMessage());
    }
    return numOfRowsAffected;
  }

  private void processResultSet(ResultSet resultSet) throws SQLException {

    dashboardConfigs = new ArrayList<>();

    while (resultSet.next()) {
      UserDashboardConfig dashboardConfig = new UserDashboardConfig();
      dashboardConfig.setId(resultSet.getString("id"));
      dashboardConfig.setUserId(resultSet.getString("user_id"));
      dashboardConfig.setReportTypeId(resultSet.getString("report_type_id"));
      dashboardConfig.setCustomReportId(resultSet.getString("custom_report_id"));
      dashboardConfig.setDisplayOrder(resultSet.getString("display_order"));

      dashboardConfigs.add(dashboardConfig);
    }
  }

}
