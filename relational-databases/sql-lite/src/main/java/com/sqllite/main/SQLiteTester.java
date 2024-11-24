package com.sqllite.main;

import java.sql.*;

public class SQLiteTester {

    public static final String CONNECTION_STRING = "jdbc:sqlite:path_to_sqlite_file" ;

    /**
     * 1. 创建数据库连接时, 如果DB不存在, 则会创建出来
     * 2. Connection和Statement(关联指定的Connection)资源需要在执行结束的时候关闭，避免资源的浪费和对性能的影响
     * 3. SQLite JDBC Driver一次只能执行一个sql statement !!!
     * 4. Statement会在执行查询的时候被编译Complied, 执行的query结尾不需要添加分号
     */
    // 代码优化1. 将关键字和指定字符串，声明成常量，避免硬编码字符串出错
    // 代码优化2. 将要直接的SQL查询语句独立出来，成单独的方法，实现指定的一个功能 !!
    // 代码优化3. 使用statement.executeQuery("sql"); 直接拿到查询的结果ResultSet
    public static void main(String[] args) throws SQLException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(CONNECTION_STRING);
            // connection.setAutoCommit(false); 是否在执行statement之后，立即auto commit changes
            Statement statement = connection.createStatement();
            statement.execute("DROP TABLE IF EXISTS contacts");
            statement.execute("CREATE TABLE IF NOT EXISTS contacts (name TEXT, phone INTEGER, email TEXT)");
            statement.execute("INSERT INTO contacts (name, phone, email) VALUES ('chen', 12345, 'chen@gmail.com')");
            statement.execute("INSERT INTO contacts (name, phone, email) VALUES ('tong', 12345, 'tong@gmail.com')");
            statement.execute("UPDATE contacts SET phone=232323 WHERE name='chen'");
            statement.execute("DELETE FROM contacts WHERE name='chen'");

            // statement只能关联一个结果数据: 不同查询数据需要使用不同statement
            statement.execute("SELECT * FROM contacts");
            ResultSet results = statement.getResultSet();
            while (results.next()) {
                String name = results.getString("name");
                int phone = results.getInt("phone");
                String email = results.getString("email");
            }
            // TODO: 这里必须确定能够关闭资源
            results.close(); // ResultSet是一个resource资源，需要关闭
            statement.close(); // 自定义关闭，注意关闭的顺序, statement的关闭同时会关闭与之关联的ResultSet
        } catch (SQLException exception) {
            // TODO: 确保连接能够关闭, 关闭连接之后, 任何的statement将不能再使用
            connection.close();
            exception.printStackTrace();
        }
    }

    // SQLite Driver对Java并没有提供Schema数据表的信息，
    // 通过ResultSet来获取table的构建信息Metadata, 不同数据库的原信息可能有所不同
    private void testTableMetaData() {
        String query = "SELECT * FROM contacts";
        try (Connection connection = DriverManager.getConnection(CONNECTION_STRING);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            // Get Schema info of table contacts
            ResultSetMetaData metaData = resultSet.getMetaData();
            int numColumn = metaData.getColumnCount();
            for (int i = 1; i < numColumn; i++) {
                System.out.format("col %d is %s \n", i, metaData.getColumnName(i));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    // 如何拿到SQL方法的结果数据：将最终的结果视为table的结果, 根据列来取值或者使用重命名
    private void testSQLFunctions() {
        String query = "SELECT COUNT(*) AS total, MIN(_id) AS min_id FROM songs";
        try (Connection connection = DriverManager.getConnection(CONNECTION_STRING);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            int count = resultSet.getInt(1);
            int minId = resultSet.getInt(2);
            count = resultSet.getInt("total");
            minId = resultSet.getInt("min_id");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    // statement.execute(sql) 先创建Views出来，然后在Java中使用
    private void testSQLViews(String title) {
        String query = "SELECT name, track FROM artist_view WHERE title = \"" + title + "\"";
    }
}
