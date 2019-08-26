package com.paylease.app.qa.framework.utility.database.client.dao;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.utility.database.client.dto.CheckImageNotificationRetry;
import com.paylease.app.qa.framework.utility.database.client.dto.Invoice;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class CheckImageNotificationRetryDao implements Dao<CheckImageNotificationRetry> {

  private static final String TABLE_NAME = "check_image_notification_retry";

  @Override
  public boolean create(Connection connection,
      CheckImageNotificationRetry checkImageNotificationRetry) {
    return false;
  }

  @Override
  public ArrayList<CheckImageNotificationRetry> findById(Connection connection, long id,
      int limit) {
    return null;
  }

  @Override
  public int update(Connection connection,
      CheckImageNotificationRetry checkImageNotificationRetry) {
    return 0;
  }

  @Override
  public boolean delete(Connection connection,
      CheckImageNotificationRetry checkImageNotificationRetry) {
    return false;
  }

  /**
   * Find the row with the given transId and imageType.
   *
   * @param connection connection to database
   * @param transId transId to find
   * @param imageType imageType to find
   * @return checkImageNotificationRetry object or null if not found
   */
  public CheckImageNotificationRetry findByTransIdAndImageType(Connection connection, int transId,
      String imageType) {
    try {
      Statement statement = connection.createStatement();
      String tableSql =
          "SELECT * FROM " + TABLE_NAME + " WHERE trans_id = '" + transId + "' AND image_type = '"
              + imageType + "' LIMIT 1";
      ResultSet resultSet = statement.executeQuery(tableSql);

      ArrayList<CheckImageNotificationRetry> list = processResultSet(resultSet);

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
   * @return ArrayList of CheckImageNotificationRetry
   * @throws Exception The exception when reading the resultSet
   */
  private ArrayList<CheckImageNotificationRetry> processResultSet(ResultSet resultSet) throws Exception {
    ArrayList<CheckImageNotificationRetry> list = new ArrayList<>();

    while (resultSet.next()) {
      CheckImageNotificationRetry checkImageNotificationRetry = new CheckImageNotificationRetry();
      checkImageNotificationRetry.setId(resultSet.getInt("id"));
      checkImageNotificationRetry.setIntegrationType(resultSet.getString("integration_type"));
      checkImageNotificationRetry.setTransId(resultSet.getInt("trans_id"));
      checkImageNotificationRetry.setImageType(resultSet.getString("image_type"));
      checkImageNotificationRetry.setAttempts(resultSet.getInt("attempts"));
      checkImageNotificationRetry.setCreatedAt(resultSet.getString("created_at"));
      checkImageNotificationRetry.setUpdatedAt(resultSet.getString("updated_at"));
      checkImageNotificationRetry.setDeletedAt(resultSet.getString("deleted_at"));

      list.add(checkImageNotificationRetry);
    }

    return list;
  }
}
