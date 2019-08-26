package com.paylease.app.qa.framework.utility.database.client.dao;

import com.paylease.app.qa.framework.utility.database.client.dto.Holidays;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class HolidaysDao {

  private static final int LIMIT = 10;
  private static final String TABLE_NAME = "holidays";
  private Statement statement;
  private String tableSQL;
  private ResultSet resultSet;

  //******************
  //Create Method
  //******************

  /**
   * Insert holidays into Holidays table.
   *
   * @param connection connection
   * @param holidays holidays
   * @return rows affected
   */
  public int create(Connection connection, Holidays holidays) {
    int rowsAffected = 0;
    try {
      String sql = "INSERT INTO " + TABLE_NAME + " (h_date, h_title) VALUES (?, ?)";

      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setDate(1, holidays.getHDate());
      statement.setString(2, holidays.getHTitle());

      rowsAffected = statement.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return rowsAffected;
  }

  //******************
  //Read Methods
  //******************

  public ArrayList<Holidays> findByHDate(Connection connection, Date hDate, int limit) {
    return read(connection, "h_date", hDate, LIMIT);
  }

  public ArrayList<Holidays> findByHTitle(Connection connection, String hTitle, int limit) {
    return read(connection, "h_title", hTitle, LIMIT);
  }
  //******************
  //Helper Methods
  //******************

  /**
   * Read from holidays table.
   *
   * @param connection connection
   * @param columnName column name
   * @param columnValue column value
   * @param limit limit
   * @return rows affected
   */
  public ArrayList<Holidays> read(Connection connection, String columnName, Object columnValue,
      int limit) {
    ArrayList<Holidays> resultsArrayList = null;
    try {
      statement = connection.createStatement();
      tableSQL =
          "SELECT * FROM " + TABLE_NAME + " WHERE " + columnName + " = '" + columnValue + "' LIMIT "
              + limit;
      resultSet = statement.executeQuery(tableSQL);
      resultsArrayList = processResultSet(resultSet);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return resultsArrayList;
  }

  /**
   * Delete from table.
   *
   * @param columnName column name
   * @param columnValue column value
   * @return number of rows deleted
   */
  public int delete(String columnName, String columnValue) {
    int rowsDeleted = 0;
    try {
      tableSQL = "DELETE FROM " + TABLE_NAME + " WHERE " + columnName + " ='" + columnValue + "'";
      rowsDeleted = statement.executeUpdate(tableSQL);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return rowsDeleted;
  }

  private ArrayList<Holidays> processResultSet(ResultSet resultSet) throws SQLException {
    ArrayList<Holidays> resultsArrayList = new ArrayList<Holidays>();
    while (resultSet.next()) {
      Holidays holidays = new Holidays();
      holidays.sethDate(resultSet.getDate("h_date"));
      holidays.sethTitle(resultSet.getString("h_title"));
      resultsArrayList.add(holidays);
    }
    return resultsArrayList;
  }

}