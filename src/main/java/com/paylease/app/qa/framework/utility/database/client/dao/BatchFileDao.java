package com.paylease.app.qa.framework.utility.database.client.dao;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.utility.database.client.dto.BatchFile;
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
public class BatchFileDao implements Dao<BatchFile> {

  private static final String TABLE_NAME = "batch_files";

  private ArrayList<BatchFile> batchFilesList;

  @Override
  public ArrayList<BatchFile> findById(Connection connection, long fileId, int limit) {
    try {
      Statement statement = connection.createStatement();

      String tableSQL = "SELECT * FROM " + TABLE_NAME + " WHERE file_id = '" + fileId + "' LIMIT "
          + limit;

      ResultSet resultSet = statement.executeQuery(tableSQL);

      processResultSet(resultSet);

    } catch (Exception e) {
      Logger.error(e.getMessage());
    }

    return batchFilesList;
  }

  @Override
  public boolean create(Connection connection, BatchFile batchFile) {
    return true;
  }

  @Override
  public int update(Connection connection, BatchFile batchFile) {
    int numOfRowsAffected = 0;

    try {
      PreparedStatement ps = connection.prepareStatement("UPDATE " + TABLE_NAME
          + " SET has_been_sent=?, sent_date=?,has_been_processed=? WHERE file_id=?");
      ps.setInt(1, batchFile.getHasBeenSent());
      ps.setString(2, batchFile.getSentDate());
      ps.setInt(3, batchFile.getHasBeenProcessed());
      ps.setLong(4, batchFile.getFileId());
      numOfRowsAffected = ps.executeUpdate();

    } catch (Exception e) {
      Logger.error(e.getMessage());
    }
    return numOfRowsAffected;
  }

  @Override
  public boolean delete(Connection connection, BatchFile batchFile) {
    return true;
  }

  private void processResultSet(ResultSet resultSet) throws SQLException {
    batchFilesList = new ArrayList<BatchFile>();

    while (resultSet.next()) {

      BatchFile batchFile = new BatchFile();
      batchFile.setFileId(resultSet.getLong("file_id"));
      batchFile.setHasBeenSent(resultSet.getInt("has_been_sent"));
      batchFile.setSentDate(resultSet.getString("sent_date"));
      batchFile.setHasBeenProcessed(resultSet.getInt("has_been_processed"));

      batchFilesList.add(batchFile);
    }
  }
}
