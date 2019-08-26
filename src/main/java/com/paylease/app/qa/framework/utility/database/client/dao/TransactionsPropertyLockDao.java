package com.paylease.app.qa.framework.utility.database.client.dao;


import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.newApi.Transactions;
import com.paylease.app.qa.framework.utility.database.client.dto.PropertyLockSchedule;
import com.paylease.app.qa.framework.utility.database.client.dto.TransactionsPropertyLock;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TransactionsPropertyLockDao implements Dao<TransactionsPropertyLock> {

  private static final String TABLE_NAME = "transactions_property_lock";
  private ArrayList<TransactionsPropertyLock> transactionsPropertyLockList;

  @Override
  public boolean create(Connection connection, TransactionsPropertyLock transactionsPropertyLock) {
    return false;
  }

  @Override
  public ArrayList<TransactionsPropertyLock> findById(Connection connection, long transId, int limit){
    try {
      PreparedStatement ps = connection
          .prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE trans_id = ? LIMIT ?");

      ps.setLong(1, transId);
      ps.setInt(2, limit);

      ResultSet resultSet = ps.executeQuery();

      processResultSet(resultSet);

    } catch (Exception e) {
      Logger.error(e.getMessage());
    }

    return transactionsPropertyLockList;
  }

  @Override
  public int update(Connection connection, TransactionsPropertyLock transactionsPropertyLock) {
    return 0;
  }

  @Override
  public boolean delete(Connection connection, TransactionsPropertyLock transactionsPropertyLock) {
    return false;
  }

  private void processResultSet(ResultSet resultSet) throws SQLException {
    transactionsPropertyLockList = new ArrayList<>();

    while(resultSet.next()) {
      TransactionsPropertyLock transactionsPropertyLock = new TransactionsPropertyLock();
      transactionsPropertyLock.setPropId(resultSet.getInt("prop_id"));
      transactionsPropertyLock.setTransId(resultSet.getInt("trans_id"));

      transactionsPropertyLockList.add(transactionsPropertyLock);
    }
  }

}
