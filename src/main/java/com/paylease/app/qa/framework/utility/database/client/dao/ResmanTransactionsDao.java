package com.paylease.app.qa.framework.utility.database.client.dao;

import com.paylease.app.qa.framework.utility.database.client.dto.ResmanTransactionsDto;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ResmanTransactionsDao {

  private static final int LIMIT = 10;
  private static final String TABLE_NAME = "resman_transactions";
  private Statement statement;
  private String tableSQL;
  private ResultSet resultSet;

  //******************
  //Create Method
  //******************

  public int create(Connection connection, ResmanTransactionsDto resmanTransactionsDto) {
    int rowsAffected = 0;
    try {
      String sql = "INSERT INTO " + TABLE_NAME
          + " (id, trans_id, notification_type, pm_id, request_msg, response_msg, integration_status, attempts, created_on, updated_on, reviewed, amount, pm_bank_last_four) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setInt(0, resmanTransactionsDto.getId());
      statement.setInt(1, resmanTransactionsDto.getTransId());
      statement.setString(2, resmanTransactionsDto.getNotificationType());
      statement.setInt(3, resmanTransactionsDto.getPmId());
      statement.setString(4, resmanTransactionsDto.getRequestMsg());
      statement.setString(5, resmanTransactionsDto.getResponseMsg());
      statement.setString(6, resmanTransactionsDto.getIntegrationStatus());
      statement.setByte(7, resmanTransactionsDto.getAttempts());
      statement.setDate(8, resmanTransactionsDto.getCreatedOn());
      statement.setDate(9, resmanTransactionsDto.getUpdatedOn());
      statement.setString(10, resmanTransactionsDto.getReviewed());
      statement.setBigDecimal(11, resmanTransactionsDto.getAmount());
      statement.setString(12, resmanTransactionsDto.getPmBankLastFour());

      rowsAffected = statement.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return rowsAffected;
  }

  //******************
  //Update Method
  //******************

  public int update(Connection connection, ResmanTransactionsDto resmanTransactionsDto,
      String filterColumnName) {
    int rowsAffected = 0;
    try {
      String sql = "UPDATE " + TABLE_NAME
          + " SET id=? , trans_id=? , notification_type=? , pm_id=? , request_msg=? , response_msg=? , integration_status=? , attempts=? , created_on=? , updated_on=? , reviewed=? , amount=? , pm_bank_last_four=? WHERE "
          + filterColumnName + "=?";

      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setInt(0, resmanTransactionsDto.getId());
      statement.setInt(1, resmanTransactionsDto.getTransId());
      statement.setString(2, resmanTransactionsDto.getNotificationType());
      statement.setInt(3, resmanTransactionsDto.getPmId());
      statement.setString(4, resmanTransactionsDto.getRequestMsg());
      statement.setString(5, resmanTransactionsDto.getResponseMsg());
      statement.setString(6, resmanTransactionsDto.getIntegrationStatus());
      statement.setByte(7, resmanTransactionsDto.getAttempts());
      statement.setDate(8, resmanTransactionsDto.getCreatedOn());
      statement.setDate(9, resmanTransactionsDto.getUpdatedOn());
      statement.setString(10, resmanTransactionsDto.getReviewed());
      statement.setBigDecimal(11, resmanTransactionsDto.getAmount());
      statement.setString(12, resmanTransactionsDto.getPmBankLastFour());

      rowsAffected = statement.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return rowsAffected;
  }

  //******************
  //Delete Methods
  //******************

  public int removeById(Connection connection, int id) {
    return delete(connection, "id", id, "int");
  }

  public int removeByTransId(Connection connection, int transId) {
    return delete(connection, "trans_id", transId, "int");
  }

  public int removeByNotificationType(Connection connection, String notificationType) {
    return delete(connection, "notification_type", notificationType, "String");
  }

  public int removeByPmId(Connection connection, int pmId) {
    return delete(connection, "pm_id", pmId, "int");
  }

  public int removeByRequestMsg(Connection connection, String requestMsg) {
    return delete(connection, "request_msg", requestMsg, "String");
  }

  public int removeByResponseMsg(Connection connection, String responseMsg) {
    return delete(connection, "response_msg", responseMsg, "String");
  }

  public int removeByIntegrationStatus(Connection connection, String integrationStatus) {
    return delete(connection, "integration_status", integrationStatus, "String");
  }

  public int removeByAttempts(Connection connection, byte attempts) {
    return delete(connection, "attempts", attempts, "byte");
  }

  public int removeByCreatedOn(Connection connection, Date createdOn) {
    return delete(connection, "created_on", createdOn, "Date");
  }

  public int removeByUpdatedOn(Connection connection, Date updatedOn) {
    return delete(connection, "updated_on", updatedOn, "Date");
  }

  public int removeByReviewed(Connection connection, String reviewed) {
    return delete(connection, "reviewed", reviewed, "String");
  }

  public int removeByAmount(Connection connection, BigDecimal amount) {
    return delete(connection, "amount", amount, "BigDecimal");
  }

  public int removeByPmBankLastFour(Connection connection, String pmBankLastFour) {
    return delete(connection, "pm_bank_last_four", pmBankLastFour, "String");
  }

  //******************
  //Read Methods
  //******************

  public ArrayList<ResmanTransactionsDto> findById(Connection connection, int id, int limit) {
    return read(connection, "id", id, LIMIT);
  }

  public ArrayList<ResmanTransactionsDto> findByTransId(Connection connection, int transId,
      int limit) {
    return read(connection, "trans_id", transId, LIMIT);
  }

  public ArrayList<ResmanTransactionsDto> findByNotificationType(Connection connection,
      String notificationType, int limit) {
    return read(connection, "notification_type", notificationType, LIMIT);
  }

  public ArrayList<ResmanTransactionsDto> findByPmId(Connection connection, int pmId, int limit) {
    return read(connection, "pm_id", pmId, LIMIT);
  }

  public ArrayList<ResmanTransactionsDto> findByRequestMsg(Connection connection, String requestMsg,
      int limit) {
    return read(connection, "request_msg", requestMsg, LIMIT);
  }

  public ArrayList<ResmanTransactionsDto> findByResponseMsg(Connection connection,
      String responseMsg, int limit) {
    return read(connection, "response_msg", responseMsg, LIMIT);
  }

  public ArrayList<ResmanTransactionsDto> findByIntegrationStatus(Connection connection,
      String integrationStatus, int limit) {
    return read(connection, "integration_status", integrationStatus, LIMIT);
  }

  public ArrayList<ResmanTransactionsDto> findByAttempts(Connection connection, byte attempts,
      int limit) {
    return read(connection, "attempts", attempts, LIMIT);
  }

  public ArrayList<ResmanTransactionsDto> findByCreatedOn(Connection connection, Date createdOn,
      int limit) {
    return read(connection, "created_on", createdOn, LIMIT);
  }

  public ArrayList<ResmanTransactionsDto> findByUpdatedOn(Connection connection, Date updatedOn,
      int limit) {
    return read(connection, "updated_on", updatedOn, LIMIT);
  }

  public ArrayList<ResmanTransactionsDto> findByReviewed(Connection connection, String reviewed,
      int limit) {
    return read(connection, "reviewed", reviewed, LIMIT);
  }

  public ArrayList<ResmanTransactionsDto> findByAmount(Connection connection, BigDecimal amount,
      int limit) {
    return read(connection, "amount", amount, LIMIT);
  }

  public ArrayList<ResmanTransactionsDto> findByPmBankLastFour(Connection connection,
      String pmBankLastFour, int limit) {
    return read(connection, "pm_bank_last_four", pmBankLastFour, LIMIT);
  }
  //******************
  //Helper Methods
  //******************

  public ArrayList<ResmanTransactionsDto> read(Connection connection, String columnName,
      Object columnValue, int limit) {
    ArrayList<ResmanTransactionsDto> resultsArrayList = null;
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

  public int delete(Connection connection, String columnName, Object columnValue,
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

  private ArrayList<ResmanTransactionsDto> processResultSet(ResultSet resultSet)
      throws SQLException {
    ArrayList<ResmanTransactionsDto> resultsArrayList = new ArrayList<ResmanTransactionsDto>();
    while (resultSet.next()) {
      ResmanTransactionsDto resmanTransactionsDto = new ResmanTransactionsDto();
      resmanTransactionsDto.setId(resultSet.getInt("id"));
      resmanTransactionsDto.setTransId(resultSet.getInt("trans_id"));
      resmanTransactionsDto.setNotificationType(resultSet.getString("notification_type"));
      resmanTransactionsDto.setPmId(resultSet.getInt("pm_id"));
      resmanTransactionsDto.setRequestMsg(resultSet.getString("request_msg"));
      resmanTransactionsDto.setResponseMsg(resultSet.getString("response_msg"));
      resmanTransactionsDto.setIntegrationStatus(resultSet.getString("integration_status"));
      resmanTransactionsDto.setAttempts(resultSet.getByte("attempts"));
      resmanTransactionsDto.setCreatedOn(resultSet.getDate("created_on"));
      resmanTransactionsDto.setUpdatedOn(resultSet.getDate("updated_on"));
      resmanTransactionsDto.setReviewed(resultSet.getString("reviewed"));
      resmanTransactionsDto.setAmount(resultSet.getBigDecimal("amount"));
      resmanTransactionsDto.setPmBankLastFour(resultSet.getString("pm_bank_last_four"));
      resultsArrayList.add(resmanTransactionsDto);
    }
    return resultsArrayList;
  }
}