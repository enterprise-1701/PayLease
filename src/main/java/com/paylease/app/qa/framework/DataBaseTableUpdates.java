package com.paylease.app.qa.framework;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class DataBaseTableUpdates {

    private static int count;
    private static int limit;
    private static int TARGET_COUNT = 2600;
    private static int numOfRowsAffected = 0;

    public static void main(String args[]) throws java.sql.SQLException {

        String sqlQuery = "SELECT count(tei_id) from tax_entity_info WHERE validation_status = \"NEW\"";
        DataBaseConnector dataBaseConnector = new DataBaseConnector();
        dataBaseConnector.createConnection();
        Connection connection = dataBaseConnector.getConnection();
        Statement statement = connection.createStatement();
        ResultSet query = statement.executeQuery(sqlQuery);

        while (query.next()) {
            count = query.getInt(1);
            Logger.info("Number of rows before update " + count);
        }

        if (count > TARGET_COUNT) {
            limit = count - TARGET_COUNT;
            Logger.info("limit being set is " + limit);
            String sqlQueryUpdate = "UPDATE tax_entity_info SET validation_status = \"VALIDATED\" where tei_id in (select tei_id from (select tei_id from tax_entity_info where validation_status = \"NEW\" order by tei_id asc LIMIT " + limit +") tmp)";
            Logger.info("SQL " + sqlQueryUpdate);
            PreparedStatement ps = connection.prepareStatement(sqlQueryUpdate);
            numOfRowsAffected = ps.executeUpdate();
            Logger.info("Number of rows updated " + numOfRowsAffected);

        } else{
            if(TARGET_COUNT == count) {
                Logger.info("No updates done since " + TARGET_COUNT + " = " + count);
            }else if (TARGET_COUNT > count){
                Logger.info("No updates done since " + TARGET_COUNT + " > " + count);
            }
        }

        dataBaseConnector.closeConnection();
    }
}