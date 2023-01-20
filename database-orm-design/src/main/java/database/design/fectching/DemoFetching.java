package database.design.fectching;

import java.sql.*;

public class DemoFetching {

    // 定义查询结果的fetch size
    public void testFetchSize() throws SQLException {
        Connection connection = DriverManager.getConnection("url");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("query");
        resultSet.setFetchSize(50);
    }
}
