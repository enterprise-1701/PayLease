package com.paylease.app.qa.framework.utility.database.client.dao;


import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.utility.database.client.dto.PropertyLockSchedule;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PropertyLockScheduleDao implements Dao<PropertyLockSchedule> {

  private static final String TABLE_NAME = "property_lock_schedule";
  private ArrayList<PropertyLockSchedule> propertyLockSchedules;

  @Override
  public boolean create(Connection connection, PropertyLockSchedule propertyLockSchedule) {
    return false;
  }

  @Override
  public ArrayList<PropertyLockSchedule> findById(Connection connection, long propId, int limit){
    return null;
  }

  public ArrayList<PropertyLockSchedule> findByPropId(Connection connection, long propId, int limit) {
    try {
      Statement statement = connection.createStatement();

      String tableSQL = "SELECT * FROM " + TABLE_NAME + " WHERE prop_id = '" + propId + "' LIMIT "
          + limit;

      ResultSet resultSet = statement.executeQuery(tableSQL);

      processResultSet(resultSet);

    } catch (Exception e) {
      Logger.error(e.getMessage());
    }

    return propertyLockSchedules;
  }

  public ArrayList<PropertyLockSchedule> findByPmId(Connection connection, long pmId, int limit) {
    try {
      Statement statement = connection.createStatement();

      String tableSQL = "SELECT * FROM " + TABLE_NAME + " WHERE pm_id = '" + pmId + "' LIMIT "
          + limit;

      ResultSet resultSet = statement.executeQuery(tableSQL);

      processResultSet(resultSet);

    } catch (Exception e) {
      Logger.error(e.getMessage());
    }

    return propertyLockSchedules;
  }

  @Override
  public int update(Connection connection, PropertyLockSchedule propertyLockSchedule) {
    return 0;
  }

  public int updateByPropId(Connection connection, PropertyLockSchedule propertyLockSchedule) {

    int numOfRowsAffected = 0;

    try {
      PreparedStatement ps = connection.prepareStatement("UPDATE " + TABLE_NAME
          + " SET lock_date=? WHERE prop_id=?");
      ps.setString(1, propertyLockSchedule.getLockDate());
      ps.setInt(2, propertyLockSchedule.getPropId());
      numOfRowsAffected = ps.executeUpdate();

    } catch (Exception e) {
      Logger.error(e.getMessage());
    }
    return numOfRowsAffected;
  }

  public int updateByPmId(Connection connection, PropertyLockSchedule propertyLockSchedule) {
    int numOfRowsAffected = 0;
    try {
      PreparedStatement ps = connection.prepareStatement("UPDATE " + TABLE_NAME
          + " SET lock_date=? WHERE pm_id=?");
      ps.setString(1, propertyLockSchedule.getLockDate());
      ps.setInt(2, propertyLockSchedule.getPmId());
      numOfRowsAffected = ps.executeUpdate();

    } catch (Exception e) {
      Logger.error(e.getMessage());
    }
    return numOfRowsAffected;
  }

  @Override
  public boolean delete(Connection connection, PropertyLockSchedule propertyLockSchedule) {
    return false;
  }

  private void processResultSet(ResultSet resultSet) throws SQLException {
    propertyLockSchedules = new ArrayList<>();

    while(resultSet.next()) {
      PropertyLockSchedule propertyLockSchedule = new PropertyLockSchedule();
      propertyLockSchedule.setPropLockSchedId(resultSet.getInt("prop_lock_sched_id"));
      propertyLockSchedule.setPmId(resultSet.getInt("pm_id"));
      propertyLockSchedule.setPropId(resultSet.getInt("prop_id"));
      propertyLockSchedule.setFrequency(resultSet.getString("frequency"));
      propertyLockSchedule.setLockDate(resultSet.getString("lock_date"));
      propertyLockSchedule.setLastLockDate(resultSet.getString("last_lock_date"));

      propertyLockSchedules.add(propertyLockSchedule);
    }
  }

}
