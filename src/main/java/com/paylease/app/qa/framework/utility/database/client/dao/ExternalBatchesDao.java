package com.paylease.app.qa.framework.utility.database.client.dao;

import com.paylease.app.qa.framework.utility.database.client.dto.ExternalBatchesDto;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ExternalBatchesDao {

  private static final int LIMIT = 10;
  private static final String TABLE_NAME = "external_batches";
  private Statement statement;
  private String tableSQL;
  private ResultSet resultSet;

  public static final String STATUS_CLOSED = "closed";

  //******************
  //Create Method
  //******************

  public int create(Connection connection, ExternalBatchesDto externalBatchesDTO) {
    int rowsAffected = 0;
    try {
      String sql = "INSERT INTO " + TABLE_NAME
          + " (id, deposit_id, status, external_id, created_at, updated_at, closed_at, deleted_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setLong(0, externalBatchesDTO.getId());
      statement.setInt(1, externalBatchesDTO.getDepositId());
      statement.setString(2, externalBatchesDTO.getStatus());
      statement.setString(3, externalBatchesDTO.getExternalId());
      statement.setDate(4, externalBatchesDTO.getCreatedAt());
      statement.setDate(5, externalBatchesDTO.getUpdatedAt());
      statement.setDate(6, externalBatchesDTO.getClosedAt());
      statement.setDate(7, externalBatchesDTO.getDeletedAt());

      rowsAffected = statement.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return rowsAffected;
  }

  //******************
  //Update Method
  //******************

  public int update(Connection connection, ExternalBatchesDto externalBatchesDTO,
      String filterColumnName) {
    int rowsAffected = 0;
    try {
      String sql = "UPDATE " + TABLE_NAME
          + " SET id=? , deposit_id=? , status=? , external_id=? , created_at=? , updated_at=? , closed_at=? , deleted_at=? WHERE "
          + filterColumnName + "=?";

      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setLong(0, externalBatchesDTO.getId());
      statement.setInt(1, externalBatchesDTO.getDepositId());
      statement.setString(2, externalBatchesDTO.getStatus());
      statement.setString(3, externalBatchesDTO.getExternalId());
      statement.setDate(4, externalBatchesDTO.getCreatedAt());
      statement.setDate(5, externalBatchesDTO.getUpdatedAt());
      statement.setDate(6, externalBatchesDTO.getClosedAt());
      statement.setDate(7, externalBatchesDTO.getDeletedAt());

      rowsAffected = statement.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return rowsAffected;
  }

  //******************
  //Delete Methods
  //******************


  public int removeById(Connection connection, long id) {
    return delete(connection, "id", id, "long");
  }

  public int removeByDepositId(Connection connection, int depositId) {
    return delete(connection, "deposit_id", depositId, "int");
  }

  public int removeByStatus(Connection connection, String status) {
    return delete(connection, "status", status, "String");
  }

  public int removeByExternalId(Connection connection, String externalId) {
    return delete(connection, "external_id", externalId, "String");
  }

  public int removeByCreatedAt(Connection connection, Date createdAt) {
    return delete(connection, "created_at", createdAt, "Date");
  }

  public int removeByUpdatedAt(Connection connection, Date updatedAt) {
    return delete(connection, "updated_at", updatedAt, "Date");
  }

  public int removeByClosedAt(Connection connection, Date closedAt) {
    return delete(connection, "closed_at", closedAt, "Date");
  }

  public int removeByDeletedAt(Connection connection, Date deletedAt) {
    return delete(connection, "deleted_at", deletedAt, "Date");
  }
  //******************
  //Read Methods
  //******************

  public ExternalBatchesDto findById(Connection connection, long id) {
    return read(connection, "id", id, 1).get(0);
  }

  public ArrayList<ExternalBatchesDto> findByDepositId(Connection connection, long depositId,
      int limit) {
    return read(connection, "deposit_id", depositId, LIMIT);
  }

  public ArrayList<ExternalBatchesDto> findByStatus(Connection connection, String status,
      int limit) {
    return read(connection, "status", status, LIMIT);
  }

  public ArrayList<ExternalBatchesDto> findByExternalId(Connection connection, String externalId,
      int limit) {
    return read(connection, "external_id", externalId, LIMIT);
  }

  public ArrayList<ExternalBatchesDto> findByCreatedAt(Connection connection, Date createdAt,
      int limit) {
    return read(connection, "created_at", createdAt, LIMIT);
  }

  public ArrayList<ExternalBatchesDto> findByUpdatedAt(Connection connection, Date updatedAt,
      int limit) {
    return read(connection, "updated_at", updatedAt, LIMIT);
  }

  public ArrayList<ExternalBatchesDto> findByClosedAt(Connection connection, Date closedAt,
      int limit) {
    return read(connection, "closed_at", closedAt, LIMIT);
  }

  public ArrayList<ExternalBatchesDto> findByDeletedAt(Connection connection, Date deletedAt,
      int limit) {
    return read(connection, "deleted_at", deletedAt, LIMIT);
  }
  //******************
  //Helper Methods
  //******************

  public ArrayList<ExternalBatchesDto> read(Connection connection, String columnName,
      Object columnValue, int limit) {
    ArrayList<ExternalBatchesDto> resultsArrayList = null;
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

  private ArrayList<ExternalBatchesDto> processResultSet(ResultSet resultSet) throws SQLException {
    ArrayList<ExternalBatchesDto> resultsArrayList = new ArrayList<ExternalBatchesDto>();
    while (resultSet.next()) {
      ExternalBatchesDto externalBatchesDTO = new ExternalBatchesDto();
      externalBatchesDTO.setId(resultSet.getLong("id"));
      externalBatchesDTO.setDepositId(resultSet.getInt("deposit_id"));
      externalBatchesDTO.setStatus(resultSet.getString("status"));
      externalBatchesDTO.setExternalId(resultSet.getString("external_id"));
      externalBatchesDTO.setCreatedAt(resultSet.getDate("created_at"));
      externalBatchesDTO.setUpdatedAt(resultSet.getDate("updated_at"));
      externalBatchesDTO.setClosedAt(resultSet.getDate("closed_at"));
      externalBatchesDTO.setDeletedAt(resultSet.getDate("deleted_at"));
      resultsArrayList.add(externalBatchesDTO);
    }
    return resultsArrayList;
  }
}