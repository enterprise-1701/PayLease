package com.paylease.app.qa.framework.utility.database.client.dao;

import com.paylease.app.qa.framework.utility.database.client.dto.ExternalTransactionsDto;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ExternalTransactionsDao {

  private static final int LIMIT = 10;
  private static final String TABLE_NAME = "external_transactions";
  private Statement statement;
  private String tableSQL;
  private ResultSet resultSet;

  public static final String TYPE_RETURN = "RETURN";
  public static final String TYPE_REVERSAL = "REVERSAL";

  //******************
  //Create Method
  //******************

  public int create(Connection connection, ExternalTransactionsDto externalTransactionsDTO) {
    int rowsAffected = 0;
    try {
      String sql = "INSERT INTO " + TABLE_NAME
          + " (id, pm_id, type, status, trans_id, proc_id, external_id, external_batch_id, created_at, updated_at, voided_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setLong(0, externalTransactionsDTO.getId());
      statement.setInt(1, externalTransactionsDTO.getPmId());
      statement.setString(2, externalTransactionsDTO.getType());
      statement.setString(3, externalTransactionsDTO.getStatus());
      statement.setInt(4, externalTransactionsDTO.getTransId());
      statement.setInt(5, externalTransactionsDTO.getProcId());
      statement.setString(6, externalTransactionsDTO.getExternalId());
      statement.setString(7, externalTransactionsDTO.getExternalBatchId());
      statement.setDate(8, externalTransactionsDTO.getCreatedAt());
      statement.setDate(9, externalTransactionsDTO.getUpdatedAt());
      statement.setDate(10, externalTransactionsDTO.getVoidedAt());

      rowsAffected = statement.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return rowsAffected;
  }

  //******************
  //Update Method
  //******************


  public int update(Connection connection, ExternalTransactionsDto externalTransactionsDTO,
      String filterColumnName) {
    int rowsAffected = 0;
    try {
      String sql = "UPDATE " + TABLE_NAME
          + " SET id=? , pm_id=? , type=? , status=? , trans_id=? , proc_id=? , external_id=? , external_batch_id=? , created_at=? , updated_at=? , voided_at=? WHERE "
          + filterColumnName + "=?";

      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setLong(0, externalTransactionsDTO.getId());
      statement.setInt(1, externalTransactionsDTO.getPmId());
      statement.setString(2, externalTransactionsDTO.getType());
      statement.setString(3, externalTransactionsDTO.getStatus());
      statement.setInt(4, externalTransactionsDTO.getTransId());
      statement.setInt(5, externalTransactionsDTO.getProcId());
      statement.setString(6, externalTransactionsDTO.getExternalId());
      statement.setString(7, externalTransactionsDTO.getExternalBatchId());
      statement.setDate(8, externalTransactionsDTO.getCreatedAt());
      statement.setDate(9, externalTransactionsDTO.getUpdatedAt());
      statement.setDate(10, externalTransactionsDTO.getVoidedAt());

      rowsAffected = statement.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return rowsAffected;
  }

  //******************
  //Delete Methods
  //******************

 public int removeByTransId(Connection connection, int transId) {
    return delete(connection, "trans_id", transId, "int");
  }

  //******************
  //Read Methods
  //******************


  public ArrayList<ExternalTransactionsDto> findById(Connection connection, long id, int limit) {
    return read(connection, "id", id, LIMIT);
  }

  public ArrayList<ExternalTransactionsDto> findByPmId(Connection connection, int pmId, int limit) {
    return read(connection, "pm_id", pmId, LIMIT);
  }

  public ArrayList<ExternalTransactionsDto> findByType(Connection connection, String type,
      int limit) {
    return read(connection, "type", type, LIMIT);
  }

  public ArrayList<ExternalTransactionsDto> findByStatus(Connection connection, String status,
      int limit) {
    return read(connection, "status", status, LIMIT);
  }

  public ArrayList<ExternalTransactionsDto> findByTransId(Connection connection, int transId,
      int limit) {
    return read(connection, "trans_id", transId, LIMIT);
  }

  public ArrayList<ExternalTransactionsDto> findByProcId(Connection connection, int procId,
      int limit) {
    return read(connection, "proc_id", procId, LIMIT);
  }

  public ArrayList<ExternalTransactionsDto> findByExternalId(Connection connection,
      String externalId, int limit) {
    return read(connection, "external_id", externalId, LIMIT);
  }

  public ArrayList<ExternalTransactionsDto> findByExternalBatchId(Connection connection,
      String externalBatchId, int limit) {
    return read(connection, "external_batch_id", externalBatchId, LIMIT);
  }

  public ArrayList<ExternalTransactionsDto> findByCreatedAt(Connection connection, Date createdAt,
      int limit) {
    return read(connection, "created_at", createdAt, LIMIT);
  }

  public ArrayList<ExternalTransactionsDto> findByUpdatedAt(Connection connection, Date updatedAt,
      int limit) {
    return read(connection, "updated_at", updatedAt, LIMIT);
  }

  public ArrayList<ExternalTransactionsDto> findByVoidedAt(Connection connection, Date voidedAt,
      int limit) {
    return read(connection, "voided_at", voidedAt, LIMIT);
  }

  /**
   * Helper method to get all external transaction rows for a given transaction id and type (payment, return, reversal)
   * 
   * @param connection
   * @param transId
   * @param type
   * @return
   */
  public ArrayList<ExternalTransactionsDto> findByTransIdAndType(Connection connection, int transId, String type) {
    ArrayList<ExternalTransactionsDto> resultsArrayList = null;
    try {
      statement = connection.createStatement();
      tableSQL =
          "SELECT * FROM " + TABLE_NAME + " WHERE trans_id = '" + transId + "' AND type = '"
              + type + "'";
      resultSet = statement.executeQuery(tableSQL);
      resultsArrayList = processResultSet(resultSet);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return resultsArrayList;
  }
  //******************
  //Helper Methods
  //******************


  public ArrayList<ExternalTransactionsDto> read(Connection connection, String columnName,
      Object columnValue, int limit) {
    ArrayList<ExternalTransactionsDto> resultsArrayList = null;
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
      tableSQL = "DELETE FROM " + TABLE_NAME + " WHERE " + columnName + " = " +columnValue;
      PreparedStatement statement = connection.prepareStatement(tableSQL);
      rowsDeleted = statement.executeUpdate(tableSQL);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return rowsDeleted;
  }


  private ArrayList<ExternalTransactionsDto> processResultSet(ResultSet resultSet)
      throws SQLException {
    ArrayList<ExternalTransactionsDto> resultsArrayList = new ArrayList<ExternalTransactionsDto>();
    while (resultSet.next()) {
      ExternalTransactionsDto externalTransactionsDTO = new ExternalTransactionsDto();
      externalTransactionsDTO.setId(resultSet.getLong("id"));
      externalTransactionsDTO.setPmId(resultSet.getInt("pm_id"));
      externalTransactionsDTO.setType(resultSet.getString("type"));
      externalTransactionsDTO.setStatus(resultSet.getString("status"));
      externalTransactionsDTO.setTransId(resultSet.getInt("trans_id"));
      externalTransactionsDTO.setProcId(resultSet.getInt("proc_id"));
      externalTransactionsDTO.setExternalId(resultSet.getString("external_id"));
      externalTransactionsDTO.setExternalBatchId(resultSet.getString("external_batch_id"));
      externalTransactionsDTO.setCreatedAt(resultSet.getDate("created_at"));
      externalTransactionsDTO.setUpdatedAt(resultSet.getDate("updated_at"));
      externalTransactionsDTO.setVoidedAt(resultSet.getDate("voided_at"));
      resultsArrayList.add(externalTransactionsDTO);
    }
    return resultsArrayList;
  }

}