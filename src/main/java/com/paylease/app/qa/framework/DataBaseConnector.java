package com.paylease.app.qa.framework;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DataBaseConnector {

  private Connection connection = null;
  private ResultSet rs = null;
  private static final String DB_URL =
      "jdbc:mysql://" + ResourceFactory.getInstance().getProperty(ResourceFactory.DB_HOST_KEY)
          + ".paylease.local/netlease_rent?serverTimezone=America/Los_Angeles";

  /**
   * Get a single column value from a single row.
   *
   * @param sqlQuery query to execute
   * @return Column value for first column in first row returned by query or empty string
   */
  public static String getDbValue(String sqlQuery) {
    String dbValue;

    DataBaseConnector dataBaseConnector = new DataBaseConnector();
    dataBaseConnector.createConnection();

    ResultSet assertQuery = dataBaseConnector.executeSqlQuery(sqlQuery);

    try {
      assertQuery.next();
      dbValue = assertQuery.getString(1);
    } catch (SQLException e) {
      Logger.error(e.getMessage());
      dbValue = "";
    }

    dataBaseConnector.closeConnection();
    return dbValue;

  }

  /** Create a database connection. */
  public void createConnection() {
    try {
      connection = DriverManager
          .getConnection(DB_URL, AppConstant.DB_USERNAME, AppConstant.DB_PASSWORD);
      if (connection != null) {
        Logger.debug("Database connection established");
      } else {
        Logger.debug("Database connection failed");
      }

    } catch (SQLException sqlEx) {
      Logger.error("SQL Exception: " + sqlEx.getSQLState());
    }
  }

  /**
   * Execute a SQL query on the previously opened connection.
   *
   * @param sqlQuery Query string
   * @return ResultSet from query execution
   */
  public ResultSet executeSqlQuery(String sqlQuery) {

    try {
      Statement stmt = connection.createStatement();
      rs = stmt.executeQuery(sqlQuery);
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return rs;
  }

  public Connection getConnection() {
    return connection;
  }

  /** Close connection if opened. */
  public void closeConnection() {
    if (connection != null) {
      try {
        connection.close();
      } catch (Exception e) {
        Logger.error("SQL Exception: " + e.getMessage());
      }
    }
  }
}