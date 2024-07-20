package fectch;

import java.sql.*;

// Fetching 抓取数据
// The process of grabbing data from the db and making it available to the application.
// - Fetching too much data adds overhead in terms of both JDBC communication and ResultSet processing.
// - Fetching too little data might cause additional fetching to be needed.
//
// TODO. Fetching Types: Eager & Lazy (Lazy Fetch must work inside transaction)
// 在事务之外的延迟获取可能造成timeout (如果返回Stream<>且数据量较大, 流的操作时间过长)
public class DemoFetching {

    // 如果设置AutoCommit=false, 则不能设置fetch size的参数
    public void testFetchSize() throws SQLException {
        Connection connection = DriverManager.getConnection("url");
        connection.setAutoCommit(false);

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("query");
        resultSet.setFetchSize(50);

        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
    }
}
