package com.psql.main.settings;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// TODO. PostgresSQL Database每个数据库都有一个Setting配置的表
//      pg_settings view cannot be inserted into or deleted from, but it can be updated
// https://www.postgresql.org/docs/current/view-pg-settings.html
//
// > SHOW ALL;            Displays the current setting of run-time parameters in 3 columns.
// > TABLE pg_settings;   pg_settings view shows all setting details
// > select setting from pg_settings;
public class PostgresSqlSettingsTable {

    public void getPsqlSettingsTable(Connection connection) throws SQLException {
        String query = "select setting from pg_settings where name = 'edb_redwood_date'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        if (resultSet.next()) {
            String setting = resultSet.getString(1);
            if ("on".equals(setting)) {
                System.out.println("edb_redwood_date=true");
            }
        } else {
            System.out.println("Can not find");
        }
        statement.close();
        resultSet.close();
    }
}
