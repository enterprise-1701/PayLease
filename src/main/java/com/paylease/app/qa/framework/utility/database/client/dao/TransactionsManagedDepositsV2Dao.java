package com.paylease.app.qa.framework.utility.database.client.dao;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.utility.database.client.dto.TransactionsManagedDepositsV2;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TransactionsManagedDepositsV2Dao {

  private static final int LIMIT = 10;
  private static final String TABLE_NAME = "transactions_managed_deposits_v2";
  private String tableSQL;

  //******************
  //Create Method
  //******************

  /**
   * Update transactionsManagedDepositsV2 table.
   *
   * @param connection connection
   * @param transId transId
   * @return number of affected rows
   */
  public int create(Connection connection, String transId) {
    int rowsAffected = 0;
    try {
      String sql = "INSERT INTO " + TABLE_NAME + " VALUES(" + transId + ")";

      PreparedStatement statement = connection.prepareStatement(sql);

      rowsAffected = statement.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return rowsAffected;
  }

  //******************
  //Update Method
  //******************

  /**
   * Update transactionsManagedDepositsV2 table.
   *
   * @param connection connection
   * @param transactionsManagedDepositsV2 transaction
   * @param filterColumnName filterColumnName
   * @return number of affected rows
   */
  public int update(Connection connection,
      TransactionsManagedDepositsV2 transactionsManagedDepositsV2, String filterColumnName) {
    int rowsAffected = 0;
    try {
      String sql = "UPDATE " + TABLE_NAME + " SET trans_id=? WHERE " + filterColumnName + "=?";

      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setInt(0, transactionsManagedDepositsV2.getTransId());

      rowsAffected = statement.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return rowsAffected;
  }

  //******************
  //Delete Methods
  //******************

  private int removeByTransId(Connection connection, int transId) {
    return delete(connection, "trans_id", transId, "int");
  }

  //******************
  //Read Methods
  //******************

  public ArrayList<TransactionsManagedDepositsV2> findByTransId(Connection connection,
      String transId, int limit) {
    return read(connection, "trans_id", transId, LIMIT);
  }

  //******************
  //Helper Methods
  //******************

  public ArrayList<TransactionsManagedDepositsV2> read(Connection connection, String columnName,
      Object columnValue, int limit) {
    ArrayList<TransactionsManagedDepositsV2> resultsArrayList = null;
    try {
      Statement statement = connection.createStatement();
      tableSQL =
          "SELECT * FROM " + TABLE_NAME + " WHERE " + columnName + " = '" + columnValue + "' LIMIT "
              + limit;
      ResultSet resultSet = statement.executeQuery(tableSQL);
      resultsArrayList = processResultSet(resultSet);
    } catch (Exception e) {
      Logger.error(e.getMessage());
    }
    return resultsArrayList;
  }

  private int delete(Connection connection, String columnName, Object columnValue,
      String columnType) {
    int rowsDeleted = 0;
    try {
      tableSQL = "DELETE * FROM " + TABLE_NAME + " WHERE " + columnName + " = ?";
      PreparedStatement statement = connection.prepareStatement(tableSQL);
      if (columnType.equals("String")) {
        statement.setString(1, (String) columnValue);
      } else if (columnType.equals("boolean")) {
        statement.setBoolean(1, (boolean) columnValue);
      } else if (columnType.equals("Date")) {
        statement.setDate(1, (Date) columnValue);
      } else if (columnType.equals("double")) {
        statement.setDouble(1, (double) columnValue);
      } else if (columnType.equals("float")) {
        statement.setFloat(1, (float) columnValue);
      } else if (columnType.equals("long")) {
        statement.setLong(1, (long) columnValue);
      } else if (columnType.equals("int")) {
        statement.setInt(1, (int) columnValue);
      } else if (columnType.equals("short")) {
        statement.setShort(1, (short) columnValue);
      } else if (columnType.equals("byte")) {
        statement.setByte(1, (byte) columnValue);
      } else if (columnType.equals("BigDecimal")) {
        statement.setBigDecimal(1, (BigDecimal) columnValue);
      }
      rowsDeleted = statement.executeUpdate(tableSQL);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return rowsDeleted;
  }

  private ArrayList<TransactionsManagedDepositsV2> processResultSet(ResultSet resultSet)
      throws SQLException {
    ArrayList<TransactionsManagedDepositsV2> resultsArrayList = new ArrayList<TransactionsManagedDepositsV2>();
    while (resultSet.next()) {
      TransactionsManagedDepositsV2 transactionsManagedDepositsV2 = new TransactionsManagedDepositsV2();
      transactionsManagedDepositsV2.setTransId(resultSet.getInt("trans_id"));
      resultsArrayList.add(transactionsManagedDepositsV2);
    }
    return resultsArrayList;
  }
}