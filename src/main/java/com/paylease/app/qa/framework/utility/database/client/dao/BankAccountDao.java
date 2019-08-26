package com.paylease.app.qa.framework.utility.database.client.dao;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.utility.database.client.dto.BankAccount;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * BatchItems Data Access Object.
 *
 * @author Jeffrey Walker
 */
public class BankAccountDao implements Dao<BankAccount> {

  private static final String TABLE_NAME = "bank_accounts";
  private ArrayList<BankAccount> bankAccountsList;
  private String tableSQL;

  @Override
  public ArrayList<BankAccount> findById(Connection connection, long accountId, int limit) {
    try {
      Statement statement = connection.createStatement();
      String tableSQL =
          "SELECT * FROM " + TABLE_NAME + " WHERE account_id = '" + accountId + "' LIMIT " + limit;
      Logger.debug("SQL script: " + tableSQL);
      ResultSet resultSet = statement.executeQuery(tableSQL);
      processResultSet(resultSet);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return bankAccountsList;
  }

  /**
   * Find bank accounts by user id.
   *
   * @param connection connection
   * @param userId user id to find by
   * @param limit number of results to return
   * @return list of bank accounts
   */
  public ArrayList<BankAccount> findByUserId(Connection connection, String userId, int limit) {
    try {
      Statement statement = connection.createStatement();
      String tableSQL =
          "SELECT * FROM " + TABLE_NAME + " WHERE user_id = '" + userId + "' LIMIT " + limit;
      Logger.debug("SQL script: " + tableSQL);
      ResultSet resultSet = statement.executeQuery(tableSQL);
      processResultSet(resultSet);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return bankAccountsList;
  }

  @Override
  public boolean create(Connection connection, BankAccount bankAccount) {
    return true;
  }

  @Override
  public int update(Connection connection, BankAccount bankAccount) {
    int numOfRowsAffected = 0;

    try {
      PreparedStatement ps = connection
          .prepareStatement("UPDATE " + TABLE_NAME + " SET routing_number=? WHERE account_id=?");
      ps.setString(1, bankAccount.getRoutingNum());
      ps.setString(2, bankAccount.getAccountId());
      numOfRowsAffected = ps.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return numOfRowsAffected;
  }

  public int updateFieldValue(Connection connection, String updateColumnName,String updateColumnValue, String columnName, Object columnValue) {
    int numOfRowsAffected = 0;

    try {
      tableSQL = "UPDATE " + TABLE_NAME + " SET " +updateColumnName+ " = " +updateColumnValue+ " WHERE " +columnName+ " = " +columnValue;
      PreparedStatement ps = connection.prepareStatement(tableSQL);
      numOfRowsAffected = ps.executeUpdate(tableSQL);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return numOfRowsAffected;
  }

  @Override
  public boolean delete(Connection connection, BankAccount bankAccount) {
    return true;
  }

  private void processResultSet(ResultSet resultSet) throws SQLException {
    bankAccountsList = new ArrayList<>();

    while (resultSet.next()) {
      BankAccount bankAccount = new BankAccount();
      bankAccount.setAccountId(resultSet.getString("account_id"));
      bankAccount.setAccountNum(resultSet.getString("account_number"));
      bankAccount.setBankName(resultSet.getString("bank_name"));
      bankAccount.setName(resultSet.getString("name"));
      bankAccount.setRoutingNum(resultSet.getString("routing_number"));
      bankAccount.setUserId(resultSet.getString("user_id"));

      bankAccountsList.add(bankAccount);
    }
  }
}
