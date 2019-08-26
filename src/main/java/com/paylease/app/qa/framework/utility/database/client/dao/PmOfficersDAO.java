package com.paylease.app.qa.framework.utility.database.client.dao;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.utility.database.client.dto.PmOfficersDTO;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class PmOfficersDAO {

  private static final int LIMIT = 10;
  private static final String TABLE_NAME = "pm_officers";
  private Statement statement;
  private String tableSQL;
  private ResultSet resultSet;

  //******************
  //Create Method
  //******************


  public int create(Connection connection, PmOfficersDTO pmOfficersDTO) {
    int rowsAffected = 0;
    try {
      String sql = "INSERT INTO " + TABLE_NAME
          + " (id, pm_id, officer_type, first_name, last_name, title, role, email, percent_stake, date_of_birth, ssn, address, city, state, zip, license_number, license_state, verification_last_submitted_date, verification_approval_date, verification_attempts) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setInt(0, pmOfficersDTO.getId());
      statement.setInt(1, pmOfficersDTO.getPmId());
      statement.setString(2, pmOfficersDTO.getOfficerType());
      statement.setString(3, pmOfficersDTO.getFirstName());
      statement.setString(4, pmOfficersDTO.getLastName());
      statement.setString(5, pmOfficersDTO.getTitle());
      statement.setString(6, pmOfficersDTO.getRole());
      statement.setString(7, pmOfficersDTO.getEmail());
      statement.setInt(8, pmOfficersDTO.getPercentStake());
      statement.setString(9, pmOfficersDTO.getDateOfBirth());
      statement.setString(10, pmOfficersDTO.getSsn());
      statement.setString(11, pmOfficersDTO.getAddress());
      statement.setString(12, pmOfficersDTO.getCity());
      statement.setString(13, pmOfficersDTO.getState());
      statement.setString(14, pmOfficersDTO.getZip());
      statement.setString(15, pmOfficersDTO.getLicenseNumber());
      statement.setString(16, pmOfficersDTO.getLicenseState());
      statement.setDate(17, pmOfficersDTO.getVerificationLastSubmittedDate());
      statement.setDate(18, pmOfficersDTO.getVerificationApprovalDate());
      statement.setInt(19, pmOfficersDTO.getVerificationAttempts());

      rowsAffected = statement.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return rowsAffected;
  }

  //******************
  //Update Method
  //******************


  public int update(Connection connection, PmOfficersDTO pmOfficersDTO, String filterColumnName) {
    int rowsAffected = 0;
    try {
      String sql = "UPDATE " + TABLE_NAME
          + " SET id=? , pm_id=? , officer_type=? , first_name=? , last_name=? , title=? , role=? , email=? , percent_stake=? , date_of_birth=? , ssn=? , address=? , city=? , state=? , zip=? , license_number=? , license_state=? , verification_last_submitted_date=? , verification_approval_date=? , verification_attempts=? WHERE "
          + filterColumnName + "=?";

      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setInt(0, pmOfficersDTO.getId());
      statement.setInt(1, pmOfficersDTO.getPmId());
      statement.setString(2, pmOfficersDTO.getOfficerType());
      statement.setString(3, pmOfficersDTO.getFirstName());
      statement.setString(4, pmOfficersDTO.getLastName());
      statement.setString(5, pmOfficersDTO.getTitle());
      statement.setString(6, pmOfficersDTO.getRole());
      statement.setString(7, pmOfficersDTO.getEmail());
      statement.setInt(8, pmOfficersDTO.getPercentStake());
      statement.setString(9, pmOfficersDTO.getDateOfBirth());
      statement.setString(10, pmOfficersDTO.getSsn());
      statement.setString(11, pmOfficersDTO.getAddress());
      statement.setString(12, pmOfficersDTO.getCity());
      statement.setString(13, pmOfficersDTO.getState());
      statement.setString(14, pmOfficersDTO.getZip());
      statement.setString(15, pmOfficersDTO.getLicenseNumber());
      statement.setString(16, pmOfficersDTO.getLicenseState());
      statement.setDate(17, pmOfficersDTO.getVerificationLastSubmittedDate());
      statement.setDate(18, pmOfficersDTO.getVerificationApprovalDate());
      statement.setInt(19, pmOfficersDTO.getVerificationAttempts());

      rowsAffected = statement.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return rowsAffected;
  }

  //******************
  //Delete Methods
  //******************


  public int removeById(Connection connection, int id) {
    return delete(connection, "id", id, "int");
  }

  public int removeByPmId(Connection connection, int pmId) {
    return delete(connection, "pm_id", pmId, "int");
  }

  public int removeByOfficerType(Connection connection, String officerType) {
    return delete(connection, "officer_type", officerType, "String");
  }

  public int removeByFirstName(Connection connection, String firstName) {
    return delete(connection, "first_name", firstName, "String");
  }

  public int removeByLastName(Connection connection, String lastName) {
    return delete(connection, "last_name", lastName, "String");
  }

  public int removeByTitle(Connection connection, String title) {
    return delete(connection, "title", title, "String");
  }

  public int removeByRole(Connection connection, String role) {
    return delete(connection, "role", role, "String");
  }

  public int removeByEmail(Connection connection, String email) {
    return delete(connection, "email", email, "String");
  }

  public int removeByPercentStake(Connection connection, int percentStake) {
    return delete(connection, "percent_stake", percentStake, "int");
  }

  public int removeByDateOfBirth(Connection connection, String dateOfBirth) {
    return delete(connection, "date_of_birth", dateOfBirth, "String");
  }

  public int removeBySsn(Connection connection, String ssn) {
    return delete(connection, "ssn", ssn, "String");
  }

  public int removeByAddress(Connection connection, String address) {
    return delete(connection, "address", address, "String");
  }

  public int removeByCity(Connection connection, String city) {
    return delete(connection, "city", city, "String");
  }

  public int removeByState(Connection connection, String state) {
    return delete(connection, "state", state, "String");
  }

  public int removeByZip(Connection connection, String zip) {
    return delete(connection, "zip", zip, "String");
  }

  public int removeByLicenseNumber(Connection connection, String licenseNumber) {
    return delete(connection, "license_number", licenseNumber, "String");
  }

  public int removeByLicenseState(Connection connection, String licenseState) {
    return delete(connection, "license_state", licenseState, "String");
  }

  public int removeByVerificationLastSubmittedDate(Connection connection,
      Date verificationLastSubmittedDate) {
    return delete(connection, "verification_last_submitted_date", verificationLastSubmittedDate,
        "Date");
  }

  public int removeByVerificationApprovalDate(Connection connection,
      Date verificationApprovalDate) {
    return delete(connection, "verification_approval_date", verificationApprovalDate, "Date");
  }

  public int removeByVerificationAttempts(Connection connection, int verificationAttempts) {
    return delete(connection, "verification_attempts", verificationAttempts, "int");
  }
  //******************
  //Read Methods
  //******************


  public ArrayList<PmOfficersDTO> findById(Connection connection, int id, int limit) {
    return read(connection, "id", id, LIMIT);
  }

  public ArrayList<PmOfficersDTO> findByPmId(Connection connection, int pmId, int limit) {
    return read(connection, "pm_id", pmId, LIMIT);
  }

  public ArrayList<PmOfficersDTO> findByOfficerType(Connection connection, String officerType,
      int limit) {
    return read(connection, "officer_type", officerType, LIMIT);
  }

  public ArrayList<PmOfficersDTO> findByFirstName(Connection connection, String firstName,
      int limit) {
    return read(connection, "first_name", firstName, LIMIT);
  }

  public ArrayList<PmOfficersDTO> findByLastName(Connection connection, String lastName,
      int limit) {
    return read(connection, "last_name", lastName, LIMIT);
  }

  public ArrayList<PmOfficersDTO> findByTitle(Connection connection, String title, int limit) {
    return read(connection, "title", title, LIMIT);
  }

  public ArrayList<PmOfficersDTO> findByRole(Connection connection, String role, int limit) {
    return read(connection, "role", role, LIMIT);
  }

  public ArrayList<PmOfficersDTO> findByEmail(Connection connection, String email, int limit) {
    return read(connection, "email", email, LIMIT);
  }

  public ArrayList<PmOfficersDTO> findByPercentStake(Connection connection, int percentStake,
      int limit) {
    return read(connection, "percent_stake", percentStake, LIMIT);
  }

  public ArrayList<PmOfficersDTO> findByDateOfBirth(Connection connection, String dateOfBirth,
      int limit) {
    return read(connection, "date_of_birth", dateOfBirth, LIMIT);
  }

  public ArrayList<PmOfficersDTO> findBySsn(Connection connection, String ssn, int limit) {
    return read(connection, "ssn", ssn, LIMIT);
  }

  public ArrayList<PmOfficersDTO> findByAddress(Connection connection, String address, int limit) {
    return read(connection, "address", address, LIMIT);
  }

  public ArrayList<PmOfficersDTO> findByCity(Connection connection, String city, int limit) {
    return read(connection, "city", city, LIMIT);
  }

  public ArrayList<PmOfficersDTO> findByState(Connection connection, String state, int limit) {
    return read(connection, "state", state, LIMIT);
  }

  public ArrayList<PmOfficersDTO> findByZip(Connection connection, String zip, int limit) {
    return read(connection, "zip", zip, LIMIT);
  }

  public ArrayList<PmOfficersDTO> findByLicenseNumber(Connection connection, String licenseNumber,
      int limit) {
    return read(connection, "license_number", licenseNumber, LIMIT);
  }

  public ArrayList<PmOfficersDTO> findByLicenseState(Connection connection, String licenseState,
      int limit) {
    return read(connection, "license_state", licenseState, LIMIT);
  }

  public ArrayList<PmOfficersDTO> findByVerificationLastSubmittedDate(Connection connection,
      Date verificationLastSubmittedDate, int limit) {
    return read(connection, "verification_last_submitted_date", verificationLastSubmittedDate,
        LIMIT);
  }

  public ArrayList<PmOfficersDTO> findByVerificationApprovalDate(Connection connection,
      Date verificationApprovalDate, int limit) {
    return read(connection, "verification_approval_date", verificationApprovalDate, LIMIT);
  }

  public ArrayList<PmOfficersDTO> findByVerificationAttempts(Connection connection,
      int verificationAttempts, int limit) {
    return read(connection, "verification_attempts", verificationAttempts, LIMIT);
  }
  //******************
  //Helper Methods
  //******************


  public ArrayList<PmOfficersDTO> read(Connection connection, String columnName, Object columnValue,
      int limit) {
    ArrayList<PmOfficersDTO> resultsArrayList = null;
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


  private ArrayList<PmOfficersDTO> processResultSet(ResultSet resultSet) throws SQLException {
    ArrayList<PmOfficersDTO> resultsArrayList = new ArrayList<PmOfficersDTO>();
    while (resultSet.next()) {
      PmOfficersDTO pmOfficersDTO = new PmOfficersDTO();
      pmOfficersDTO.setId(resultSet.getInt("id"));
      pmOfficersDTO.setPmId(resultSet.getInt("pm_id"));
      pmOfficersDTO.setOfficerType(resultSet.getString("officer_type"));
      pmOfficersDTO.setFirstName(resultSet.getString("first_name"));
      pmOfficersDTO.setLastName(resultSet.getString("last_name"));
      pmOfficersDTO.setTitle(resultSet.getString("title"));
      pmOfficersDTO.setRole(resultSet.getString("role"));
      pmOfficersDTO.setEmail(resultSet.getString("email"));
      pmOfficersDTO.setPercentStake(resultSet.getInt("percent_stake"));
      pmOfficersDTO.setDateOfBirth(resultSet.getString("date_of_birth"));
      pmOfficersDTO.setSsn(resultSet.getString("ssn"));
      pmOfficersDTO.setAddress(resultSet.getString("address"));
      pmOfficersDTO.setCity(resultSet.getString("city"));
      pmOfficersDTO.setState(resultSet.getString("state"));
      pmOfficersDTO.setZip(resultSet.getString("zip"));
      pmOfficersDTO.setLicenseNumber(resultSet.getString("license_number"));
      pmOfficersDTO.setLicenseState(resultSet.getString("license_state"));
      pmOfficersDTO
          .setVerificationLastSubmittedDate(resultSet.getDate("verification_last_submitted_date"));
      pmOfficersDTO.setVerificationApprovalDate(resultSet.getDate("verification_approval_date"));
      pmOfficersDTO.setVerificationAttempts(resultSet.getInt("verification_attempts"));
      resultsArrayList.add(pmOfficersDTO);
    }
    return resultsArrayList;
  }

}