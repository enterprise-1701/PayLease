package com.paylease.app.qa.framework.utility.database.client.dao;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.utility.database.client.dto.Deposit;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Deposits Data Access Object.
 *
 * @author Jeffrey Walker
 */
public class DepositDao implements Dao<Deposit> {

  private static final String TABLE_NAME = "deposits";

  public static final String STATUS_OPEN = "open";
  public static final String STATUS_CLOSED = "closed";

  private ArrayList<Deposit> depositList;

  @Override
  public ArrayList<Deposit> findById(Connection connection, long depositId, int limit) {
    try {
      Statement statement = connection.createStatement();

      String tableSQL = "SELECT * FROM " + TABLE_NAME + " WHERE id = '" + depositId
          + "' LIMIT " + limit;

      ResultSet resultSet = statement.executeQuery(tableSQL);

      processResultSet(resultSet);

    } catch (Exception e) {
      Logger.error(e.getMessage());
    }

    return depositList;
  }

  public Deposit findById(Connection connection, long depositId) {
    return findById(connection, depositId, 1).get(0);
  }

  @Override
  public boolean create(Connection connection, Deposit deposit) {
    return true;
  }

  @Override
  public int update(Connection connection, Deposit deposit) {
    return 0;
  }

  @Override
  public boolean delete(Connection connection, Deposit deposit) {
    return true;
  }

  private void processResultSet(ResultSet resultSet) throws SQLException {
    depositList = new ArrayList<Deposit>();

    while (resultSet.next()) {
      Deposit deposit = new Deposit();
      deposit.setDepositId(resultSet.getInt("id"));
      deposit.setStatus(resultSet.getString("status"));
      deposit.setAccountNumber(resultSet.getString("account_number"));
      deposit.setProcessingTime(resultSet.getDate("processing_time"));
      deposit.setDepositDate(resultSet.getDate("deposit_date"));
      deposit.setCreditOrDebit(resultSet.getString("credit_or_debit"));
      deposit.setPropId(resultSet.getInt("prop_id"));
      deposit.setPaymentType(resultSet.getString("payment_type"));
      deposit.setDepositType(resultSet.getString("deposit_type"));
      deposit.setDeletedAt(resultSet.getDate("deleted_at"));
      //TODO: Set the other columns here

      depositList.add(deposit);
    }
  }
}
