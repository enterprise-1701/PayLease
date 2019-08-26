package com.paylease.app.qa.framework.utility.database.client.dao;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.utility.database.client.dto.YavoLogging;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class YavoLoggingDao implements Dao<YavoLogging> {

  private static final String TABLE_NAME = "yavo_logging";

  private ResultSet resultSet;
  private ArrayList<YavoLogging> yavoLoggingArrayList;

  public boolean create(Connection connection, YavoLogging yavoLogging) {
    return false;
  }

  @Override
  public ArrayList<YavoLogging> findById(Connection connection, long id,
      int limit) {
    return null;
  }

  public int update(Connection connection, YavoLogging yavoLogging) {
    return 0;
  }

  public boolean delete(Connection connection, YavoLogging yavoLogging) {
    return false;
  }

  public ArrayList<YavoLogging> findByPmBatchMethod(Connection connection, long pmId, long batchId,
      String method, int limit) {
    try {
      PreparedStatement ps = connection
          .prepareStatement("SELECT * FROM " + TABLE_NAME + " "
              + "WHERE pm_id = ? AND batch_id = ? "
              + "AND method = ? LIMIT ?");
      ps.setLong(1, pmId);
      ps.setLong(2, batchId);
      ps.setString(3, method);
      ps.setInt(4, limit);

      Logger.info("SQL script: " + ps.toString());
      resultSet = ps.executeQuery();

      processResultSet(resultSet);

    } catch (SQLException e) {
      Logger.error(e.getMessage());
    }

    return yavoLoggingArrayList;
  }

  private ArrayList<YavoLogging> processResultSet(ResultSet resultSet) throws SQLException {
    yavoLoggingArrayList = new ArrayList<>();

    while (resultSet.next()) {
      YavoLogging yavoLogging = new YavoLogging();

      yavoLogging.setMethod(resultSet.getString("method"));
      yavoLogging.setPmId(resultSet.getInt("pm_id"));
      yavoLogging.setBatchId(resultSet.getInt("batch_id"));

      yavoLoggingArrayList.add(yavoLogging);
    }

    return yavoLoggingArrayList;
  }
}
