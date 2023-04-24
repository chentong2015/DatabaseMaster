package database.design.fectching;

import java.sql.*;

// 什么是Fetching(抓取数据):
// The process of grabbing data from the db and making it available to the application.
// - Fetching too much data adds overhead in terms of both JDBC communication and ResultSet processing.
// - Fetching too little data might cause additional fetching to be needed.

// TODO. Fetching一般分成两种: Lazy & Eager
//  Lazy Fetch doesn't work outside transaction 在事务之外的延迟获取可能造成timeout(如果返回Stream<>且数据量较大)
public class DemoFetching {

    // 定义查询结果的fetch size
    public void testFetchSize() throws SQLException {
        Connection connection = DriverManager.getConnection("url");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("query");
        resultSet.setFetchSize(50);
    }
}
