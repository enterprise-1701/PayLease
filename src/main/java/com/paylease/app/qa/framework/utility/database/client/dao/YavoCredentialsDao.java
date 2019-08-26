package com.paylease.app.qa.framework.utility.database.client.dao;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.utility.database.client.dto.YavoCredentialsDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class YavoCredentialsDao {

  private static final String TABLE_NAME = "yavo_credentials";

  /**
   * Update url in DB table.
   *
   * @param connection connection
   * @param pmId pmId
   * @param url url
   * @return number of rows affected
   */
  public int updateUrl(Connection connection, String pmId, String url) throws SQLException {
    int numOfRowsAffected = 0;

    PreparedStatement ps = connection
        .prepareStatement("UPDATE " + TABLE_NAME
            + " SET url = ? WHERE pm_id = ?");

    ps.setString(1, url);
    ps.setString(2, pmId);

    numOfRowsAffected = ps.executeUpdate();

    Logger.debug(ps.toString());

    return numOfRowsAffected;
  }

  /**
   * Update password in DB table.
   *
   * @param connection connection
   * @param pmId pmId
   * @param password password
   * @return number of rows affected
   */
  public int updatePassword(Connection connection, String pmId, String password) throws SQLException {
    int numOfRowsAffected = 0;

    PreparedStatement ps = connection
        .prepareStatement("UPDATE " + TABLE_NAME
            + " SET password = ? WHERE pm_id = ?");

    ps.setString(1, password);
    ps.setString(2, pmId);

    numOfRowsAffected = ps.executeUpdate();

    Logger.debug(ps.toString());

    return numOfRowsAffected;
  }

  private ArrayList<YavoCredentialsDto> processResultSet(ResultSet resultSet) throws SQLException {
    ArrayList<YavoCredentialsDto> resultsArrayList = new ArrayList<YavoCredentialsDto>();
    while (resultSet.next()) {
      YavoCredentialsDto yavoCredentialsDto = new YavoCredentialsDto();
      yavoCredentialsDto.setPmId(resultSet.getInt("pm_id"));
      yavoCredentialsDto.setUrl(resultSet.getString("url"));
      yavoCredentialsDto.setUsername(resultSet.getString("username"));
      yavoCredentialsDto.setPassword(resultSet.getString("password"));
      yavoCredentialsDto.setServername(resultSet.getString("servername"));
      yavoCredentialsDto.setClientDb(resultSet.getString("client_db"));
      yavoCredentialsDto.setPlatform(resultSet.getString("platform"));
      yavoCredentialsDto.setInterfaceentityField(resultSet.getString("interfaceentity"));
      yavoCredentialsDto.setIsAltBatDesc(resultSet.getInt("is_alt_bat_desc"));
      yavoCredentialsDto.setAlertRecipients(resultSet.getString("alert_recipients"));
      yavoCredentialsDto.setInterfacesVersionField(resultSet.getString("interfaces_version"));
      yavoCredentialsDto.setBillingPaymentsVersion(resultSet.getString("billing_payments_version"));
      yavoCredentialsDto.setVersionsLastChange(resultSet.getDate("versions_last_change"));
      yavoCredentialsDto.setIsSsl(resultSet.getString("is_ssl"));
      yavoCredentialsDto
          .setResidentUpdateIntMinutes(resultSet.getInt("resident_update_int_minutes"));
      yavoCredentialsDto
          .setResidentUpdateSleepIntMinutes(resultSet.getInt("resident_update_sleep_int_minutes"));
      yavoCredentialsDto.setValidCustomerTypes(resultSet.getString("valid_customer_types"));
      yavoCredentialsDto.setSocketTimeout(resultSet.getInt("socket_timeout"));
      resultsArrayList.add(yavoCredentialsDto);
    }
    return resultsArrayList;
  }
}
