package com.sqllite.main;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQLiteExample {

    // TODO. 必须提供SQLite DB的完整路径
    public static final String DB_FILE_PATH = "relational-databases\\sql-lite\\src\\main\\resources\\session.db";

    public static void main(String[] args) {
        Path dbPath = FileSystems.getDefault().getPath(DB_FILE_PATH);
        System.out.println(dbPath);
        System.out.println(dbPath.toAbsolutePath());

        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath.toAbsolutePath());
            System.out.println(connection.getAutoCommit());
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM t_role");
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("id"));
                System.out.println(resultSet.getString("name"));
            }
            statement.close();
            connection.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
