package com.paylease.app.qa.framework.utility.database.client.dao;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.utility.database.client.dto.MriLogging;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class MriLoggingDao implements Dao<MriLogging> {

  private static final String TABLE_NAME = "mri_logging";

  @Override
  public boolean create(Connection connection, MriLogging mriLogging) {
    return false;
  }

  @Override
  public ArrayList<MriLogging> findById(Connection connection, long id, int limit) {
    return null;
  }

  @Override
  public int update(Connection connection, MriLogging mriLogging) {
    return 0;
  }

  @Override
  public boolean delete(Connection connection, MriLogging mriLogging) {
    return false;
  }

  /**
   * Find the most recently entered log entry for the given transaction and method.
   *
   * @param connection Database connection
   * @param method MRI API method
   * @param transId Transaction ID
   * @return MriLogging row or null if not found
   */
  public MriLogging findLatestByMethodAndTransId(
      Connection connection, String method, int transId
  ) {
    try {
      Statement statement = connection.createStatement();
      String tableSql =
          "SELECT * FROM " + TABLE_NAME + " WHERE method = '" + method
              + "' AND trans_id = '" + transId + "' ORDER BY id DESC LIMIT 1";
      ResultSet resultSet = statement.executeQuery(tableSql);

      ArrayList<MriLogging> list = processResultSet(resultSet);

      return list.get(0);
    } catch (Exception e) {
      Logger.error(e.getMessage());
    }

    return null;
  }

  /**
   * Process result set from the query.
   *
   * @param resultSet Set of results from query
   * @return ArrayList of Invoices
   * @throws Exception The exception when reading the resultSet
   */
  private ArrayList<MriLogging> processResultSet(ResultSet resultSet) throws Exception {
    ArrayList<MriLogging> list = new ArrayList<>();

    while (resultSet.next()) {
      MriLogging mriLogging = new MriLogging();
      mriLogging.setRequestRaw(resultSet.getString("request_raw"));

      list.add(mriLogging);
    }

    return list;
  }
}
