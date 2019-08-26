package com.paylease.app.qa.framework.utility.database.client.dao;

import com.paylease.app.qa.framework.utility.database.client.dto.DeadMessagesDto;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DeadMessagesDao {

  private static final String TABLE_NAME = "dead_messages";
  private static final String COLUMN_NAME = "message";

  private Statement statement;
  private String tableSQL;
  private ResultSet resultSet;

  /**
   * Check if transaction ID is present in the message.
   *
   * @param connection connection
   * @param transactionId transaction ID
   * @return true if transaction id is present
   * @throws SQLException exception
   */
  public boolean isTransactionIdPresent(Connection connection, String transactionId) {
    boolean transactionIdPresent = false;
    try {
      statement = connection.createStatement();
      tableSQL = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME + " like '%"
              + transactionId + "%'";

      resultSet = statement.executeQuery(tableSQL);

      transactionIdPresent = resultSet.next();

    } catch (Exception e) {
      e.printStackTrace();
    }
    return transactionIdPresent;
  }

  private ArrayList<DeadMessagesDto> processResultSet(ResultSet resultSet) throws SQLException {
    ArrayList<DeadMessagesDto> resultsArrayList = new ArrayList<DeadMessagesDto>();
    while (resultSet.next()) {
      DeadMessagesDto deadMessagesDto = new DeadMessagesDto();
      deadMessagesDto.setUrl(resultSet.getString("message"));
      resultsArrayList.add(deadMessagesDto);
    }
    return resultsArrayList;
  }
}
