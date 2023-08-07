package com.psql.main;

import java.sql.*;

// TODO. PostgreSQL JDBC Connection默认开启AutoCommit
public class DemoPostgresJDBC {

    private static String url = "jdbc:postgresql://localhost:5432/my_database?reWriteBatchedInserts=true";
    // private static String url = "jdbc:postgresql://dell1451xxx:5432/tpk0002795_56979469";

    public static void main(String[] args) throws SQLException {
        try (Connection connection = DriverManager.getConnection(url, "postgres", "admin")) {
            // 获取默认的schema: public
            connection.getSchema();
            System.out.println(connection.getAutoCommit());

            // 注意这里不同DB的列名称有所区别，在匹配namePattern时需要动态匹配
            ResultSet resultSet = connection.getMetaData()
                    .getColumns(null, null, "t_batching_comment", "Not_Exist");
            System.out.println(resultSet.next());

            // 返回的是受影响的行数，准确的被修改的行数
            String query = "DELETE FROM t_batching_comment WHERE id<5";
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(query);
            }
        }
    }
}
