package com.paylease.app.qa.framework.utility.database.client.dao;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.utility.database.client.dto.ResidentUserType;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ResidentUserTypeDao implements Dao<ResidentUserType> {

  private static final String TABLE_NAME = "resident_user_type";

  @Override
  public boolean create(Connection connection, ResidentUserType residentUserType) {
    return false;
  }

  @Override
  public ArrayList<ResidentUserType> findById(Connection connection, long id, int limit) {
    try {
      Statement statement = connection.createStatement();
      String tableSql =
          "SELECT * FROM " + TABLE_NAME + " WHERE user_id = '" + id + "'";
      ResultSet resultSet = statement.executeQuery(tableSql);

      return processResultSet(resultSet);
    } catch (Exception e) {
      Logger.error(e.getMessage());
    }

    return null;
  }

  @Override
  public int update(Connection connection, ResidentUserType residentUserType) {
    return 0;
  }

  @Override
  public boolean delete(Connection connection, ResidentUserType residentUserType) {
    return false;
  }

  /**
   * Process result set from the query.
   *
   * @param resultSet Set of results from query
   * @return ArrayList of Users
   * @throws Exception The exception when reading the resultSet
   */
  private ArrayList<ResidentUserType> processResultSet(ResultSet resultSet) throws Exception {
    ArrayList<ResidentUserType> list = new ArrayList<>();

    while (resultSet.next()) {
      ResidentUserType residentUserType = new ResidentUserType();
      residentUserType.setIntegrationStatus(resultSet.getString("integration_status"));

      list.add(residentUserType);
    }

    return list;
  }
}
