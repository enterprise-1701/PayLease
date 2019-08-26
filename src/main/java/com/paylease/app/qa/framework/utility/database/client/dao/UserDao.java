package com.paylease.app.qa.framework.utility.database.client.dao;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.utility.database.client.dto.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class UserDao implements Dao<User> {

  private static final String TABLE_NAME = "users";

  private Statement statement;
  private String tableSQL;
  private ResultSet resultSet;

  @Override
  public boolean create(Connection connection, User user) {
    return false;
  }

  @Override
  public ArrayList<User> findById(Connection connection, long id, int limit) {
    ArrayList<User> userList = new ArrayList<>();
    try {
      statement = connection.createStatement();
      tableSQL =
          "SELECT * FROM " + TABLE_NAME + " WHERE user_id = '" + id + "' LIMIT " + limit;
      ResultSet resultSet = statement.executeQuery(tableSQL);

      userList = processResultSet(resultSet);

    } catch (Exception e) {
      Logger.error(e.getMessage());
    }

    return userList;
  }

  @Override
  public int update(Connection connection, User user) {
    int numOfRowsAffected = 0;

    try {
      PreparedStatement ps = connection
          .prepareStatement("UPDATE " + TABLE_NAME
              + " SET pm_company=?, prop_id=?, first_name=?, last_name=? , user=?, rent_amount=?, rent_amount_last_updated=?, late_amount=?, late_amount_last_updated=?  "
              + "WHERE user_id=?");

      ps.setString(1, user.getPmCompany());
      ps.setString(2, user.getPropId());
      ps.setString(3, user.getFirstName());
      ps.setString(4, user.getLastName());
      ps.setString(5, user.getUser());
      ps.setString(6, user.getRentAmount());
      ps.setString(7, user.getRentAmountLastUpdated());
      ps.setString(8, user.getLateAmount());
      ps.setString(9, user.getLateAmountLastUpdated());
      ps.setString(10, user.getUserId());
      numOfRowsAffected = ps.executeUpdate();

      Logger.debug(ps.toString());

    } catch (Exception e) {
      Logger.error(e.getMessage());
    }
    return numOfRowsAffected;
  }

  @Override
  public boolean delete(Connection connection, User user) {
    return false;
  }

  /**
   * Get latest integrated resident by pm and integration user id.
   *
   * @param connection database connection
   * @param pmId pm id
   * @param integrationUserId integration user id
   * @return resident
   */
  public User findByPmIdAndIntegrationUserId(Connection connection, String pmId,
      String integrationUserId) {
    try {
      statement = connection.createStatement();
      tableSQL = "SELECT * FROM " + TABLE_NAME + " WHERE outside_integration_id = '" + pmId
          + "' AND integration_user_id = '" + integrationUserId
          + "' ORDER BY user_id desc LIMIT 1";

      Logger.debug(tableSQL);
      resultSet = statement.executeQuery(tableSQL);

      ArrayList<User> userList = processResultSet(resultSet);

      return userList.get(0);

    } catch (Exception e) {
      Logger.error(e.getMessage());
    }

    return null;
  }

  /**
   * Get pm company using pm id.
   *
   * @param connection database connection
   * @param userId user id
   * @return users
   */
  public User findByPmId(Connection connection, String userId) {
    try {
      statement = connection.createStatement();
      tableSQL = "SELECT * FROM " + TABLE_NAME + " WHERE user_id = '" + userId + "'";

      Logger.debug("SQL script: " + tableSQL);

      resultSet = statement.executeQuery(tableSQL);

      ArrayList<User> userList = processResultSet(resultSet);

      return userList.get(0);

    } catch (Exception e) {
      Logger.error(e.getMessage());
    }

    return null;
  }

  /**
   * Get User by username/email
   *
   * @param connection database connection
   * @param username username/email
   * @return User user
   */
  public User findByUsername(Connection connection, String username) {

    try {
      statement = connection.createStatement();
      tableSQL = "SELECT * FROM " + TABLE_NAME + " WHERE user = '" + username + "' LIMIT 1";

      Logger.debug(tableSQL);
      ResultSet resultSet = statement.executeQuery(tableSQL);

      ArrayList<User> userList = processResultSet(resultSet);

      return userList.get(0);
    } catch (Exception e) {
      Logger.error(e.getMessage());
    }

    return null;
  }

  /**
   * Process result set from the query.
   *
   * @param resultSet Set of results from query
   * @return ArrayList of Users
   * @throws Exception The exception when reading the resultSet
   */
  private ArrayList<User> processResultSet(ResultSet resultSet) throws Exception {
    ArrayList<User> list = new ArrayList<>();

    while (resultSet.next()) {
      User user = new User();
      user.setUserId(resultSet.getString("user_id"));
      user.setPropId(resultSet.getString("prop_id"));
      user.setPmCompany(resultSet.getString("pm_company"));
      user.setFirstName(resultSet.getString("first_name"));
      user.setLastName(resultSet.getString("last_name"));
      user.setUser(resultSet.getString("user"));
      user.setRentAmount(resultSet.getString("rent_amount"));
      user.setRentAmountLastUpdated(resultSet.getString("rent_amount_last_updated"));
      user.setLateAmount(resultSet.getString("late_amount"));
      user.setLateAmountLastUpdated(resultSet.getString("late_amount_last_updated"));

      list.add(user);
    }

    return list;
  }

  /**
   * Get userList.
   *
   * @param connection database connection
   * @param propId property Id
   * @param limit limit
   * @return usersList
   */
  public ArrayList<User> findByPropId(Connection connection, String propId, int limit) {
    ArrayList<User> userList = new ArrayList<>();
    try {
      statement = connection.createStatement();

      tableSQL = "SELECT * FROM " + TABLE_NAME + " WHERE prop_id = '" + propId + "' LIMIT "
          + limit;

      resultSet = statement.executeQuery(tableSQL);

      userList = processResultSet(resultSet);

    } catch (Exception e) {
      Logger.error(e.getMessage());
    }

    return userList;
  }
}
