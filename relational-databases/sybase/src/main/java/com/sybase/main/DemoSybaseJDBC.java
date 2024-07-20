package com.sybase.main;

import com.sybase.main.batching.DemoSybaseBatching;

import java.sql.Connection;
import java.sql.DriverManager;

public class DemoSybaseJDBC {

    // String url = "jdbc:sybase:Tds:LCTON01:5000/tempdb";
    // Class.forName("com.sybase.jdbc4.jdbc.SybDriver");
    // connection = DriverManager.getConnection(url, "tech_user", "root123");
    public static void main(String[] args) {
        try {
            Class.forName("com.sybase.jdbc4.jdbc.SybDriver");
            String url = "jdbc:sybase:Tds:server:4100/JAVA_INT_TESTS";
            Connection connection = DriverManager.getConnection(url, "INSTAL", "***");

            // JDBC提供的获取主版本的API, 无论什么版本都能返回正确值
            int version = connection.getMetaData().getDatabaseMajorVersion();

            connection.setAutoCommit(false);
            int[] countLines = DemoSybaseBatching.testPrepareStatementInsert(connection);
            for (int count : countLines) {
                System.out.println(count);
            }

            connection.commit();
            connection.setAutoCommit(true);
            connection.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
