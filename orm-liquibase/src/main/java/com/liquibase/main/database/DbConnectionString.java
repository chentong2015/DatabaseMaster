package com.liquibase.main.database;

// 这里的数据库连接信息可以使用ConfigHeler从属性文件中指定获取
public class DbConnectionString {

    public static String psqlConnectStr = "jdbc:postgresql://localhost:5432/my_database?user=postgres&password=admin";

    public static String sqlServerConnectStr = "jdbc:sqlserver://LCTON01:1433;databaseName=my_database;Trusted_Connection=true;user=test;password=TCHong16";
}
