package com.paylease.app.qa.framework.utility.database.client.dao;

import com.paylease.app.qa.framework.utility.database.client.dto.CustomizationsPropertyDTO;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class CustomizationsPropertyDAO
{

  private static final int LIMIT=10;
  private static final String TABLE_NAME = "customizations_property";
  private Statement statement;
  private String tableSQL;
  private ResultSet resultSet;

  //******************
  //Create Method
  //******************


  public int create(Connection connection, CustomizationsPropertyDTO customizationsPropertyDTO)
  {
    int rowsAffected = 0;
    try
    {
      String sql = "INSERT INTO " + TABLE_NAME + " (id, prop_id, customization_id, override_value) VALUES (?, ?, ?, ?)";

      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setInt(0,customizationsPropertyDTO.getId());
      statement.setInt(1,customizationsPropertyDTO.getPropId());
      statement.setInt(2,customizationsPropertyDTO.getCustomizationId());
      statement.setString(3,customizationsPropertyDTO.getOverrideValue());

      rowsAffected =  statement.executeUpdate();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    return rowsAffected;
  }


  //******************
  //Update Method
  //******************


  public int update(Connection connection, CustomizationsPropertyDTO customizationsPropertyDTO, String filterColumnName)
  {
    int rowsAffected = 0;
    try
    {
      String sql = "UPDATE " + TABLE_NAME + " SET id=? , prop_id=? , customization_id=? , override_value=? WHERE " + filterColumnName + "=?";

      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setInt(0,customizationsPropertyDTO.getId());
      statement.setInt(1,customizationsPropertyDTO.getPropId());
      statement.setInt(2,customizationsPropertyDTO.getCustomizationId());
      statement.setString(3,customizationsPropertyDTO.getOverrideValue());

      rowsAffected =  statement.executeUpdate();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    return rowsAffected;
  }


  //******************
  //Delete Methods
  //******************


  public int removeById(Connection connection, int id)
  {
    return delete(connection, "id", id, "int");
  }
  public int removeByPropId(Connection connection, int propId)
  {
    return delete(connection, "prop_id", propId, "int");
  }
  public int removeByCustomizationId(Connection connection, int customizationId)
  {
    return delete(connection, "customization_id", customizationId, "int");
  }
  public int removeByOverrideValue(Connection connection, String overrideValue)
  {
    return delete(connection, "override_value", overrideValue, "String");
  }
  //******************
  //Read Methods
  //******************


  public ArrayList<CustomizationsPropertyDTO> findById(Connection connection, int id, int limit)
  {
    return read(connection, "id", id, LIMIT);
  }
  public ArrayList<CustomizationsPropertyDTO> findByPropId(Connection connection, int propId, int limit)
  {
    return read(connection, "prop_id", propId, LIMIT);
  }
  public ArrayList<CustomizationsPropertyDTO> findByCustomizationId(Connection connection, int customizationId, int limit)
  {
    return read(connection, "customization_id", customizationId, LIMIT);
  }
  public ArrayList<CustomizationsPropertyDTO> findByOverrideValue(Connection connection, String overrideValue, int limit)
  {
    return read(connection, "override_value", overrideValue, LIMIT);
  }
  //******************
  //Helper Methods
  //******************


  public ArrayList<CustomizationsPropertyDTO> read(Connection connection, String columnName, Object columnValue, int limit)
  {
    ArrayList<CustomizationsPropertyDTO> resultsArrayList = null;
    try
    {
      statement = connection.createStatement();
      tableSQL = "SELECT * FROM " + TABLE_NAME + " WHERE "+columnName+" = '"+columnValue+"' LIMIT " + limit;
      resultSet =  statement.executeQuery(tableSQL);
      resultsArrayList = processResultSet(resultSet);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    return resultsArrayList;
  }

  public ArrayList<CustomizationsPropertyDTO> findByPropIdAndCustomizationId(Connection connection, int propId, int customizationId, int limit) {
    ArrayList<CustomizationsPropertyDTO> resultsArrayList = null;
    try
    {
      statement = connection.createStatement();
      tableSQL = "SELECT * FROM " + TABLE_NAME + " WHERE prop_id = "+propId+" AND customization_id = "+customizationId+" LIMIT "+limit;
      resultSet =  statement.executeQuery(tableSQL);
      resultsArrayList = processResultSet(resultSet);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    return resultsArrayList;
  }


  public int delete(Connection connection, String columnName, Object columnValue, String columnType)
  {
    int rowsDeleted = 0;
    try
    {
      tableSQL = "DELETE * FROM " + TABLE_NAME + " WHERE "+columnName+" = ?";
      PreparedStatement statement = connection.prepareStatement(tableSQL);
      if(columnType.equals("String"))
      {
        statement.setString(1, (String)columnValue);
      }
      else if(columnType.equals("boolean"))
      {
        statement.setBoolean(1, (boolean)columnValue);
      }
      else if(columnType.equals("Date"))
      {
        statement.setDate(1, (Date)columnValue);
      }
      else if(columnType.equals("double"))
      {
        statement.setDouble(1, (double)columnValue);
      }
      else if(columnType.equals("float"))
      {
        statement.setFloat(1, (float)columnValue);
      }
      else if(columnType.equals("long"))
      {
        statement.setLong(1, (long)columnValue);
      }
      else if(columnType.equals("int"))
      {
        statement.setInt(1, (int)columnValue);
      }
      else if(columnType.equals("short"))
      {
        statement.setShort(1, (short)columnValue);
      }
      else if(columnType.equals("byte"))
      {
        statement.setByte(1, (byte)columnValue);
      }
      else if(columnType.equals("BigDecimal"))
      {
        statement.setBigDecimal(1, (BigDecimal)columnValue);
      }
      rowsDeleted =  statement.executeUpdate(tableSQL);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    return rowsDeleted;
  }


  private ArrayList<CustomizationsPropertyDTO>  processResultSet(ResultSet resultSet) throws SQLException
  {
    ArrayList<CustomizationsPropertyDTO> resultsArrayList = new ArrayList<CustomizationsPropertyDTO>();
    while (resultSet.next())
    {
      CustomizationsPropertyDTO customizationsPropertyDTO = new CustomizationsPropertyDTO();
      customizationsPropertyDTO.setId(resultSet.getInt("id"));
      customizationsPropertyDTO.setPropId(resultSet.getInt("prop_id"));
      customizationsPropertyDTO.setCustomizationId(resultSet.getInt("customization_id"));
      customizationsPropertyDTO.setOverrideValue(resultSet.getString("override_value"));
      resultsArrayList.add(customizationsPropertyDTO);
    }
    return resultsArrayList;
  }

}