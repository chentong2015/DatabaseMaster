package com.oracle.main.truncate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// TODO. 测试存储的字符串长度超过Oracle定义列VARCHAR字符长度，需要使用"Truncate"截断字符串
//   Error Msg = ORA-12899: value too large for column "DAS_CONV_TOOL"."T_COMMENT"."TEXT"
//  (actual: 22, maximum: 10)
public class DemoOracleTruncate {

    public static void main(String[] args) throws SQLException {
        Connection connection = null;
        try {
            String url = "jdbc:oracle:thin:@//dell719srv:1521/DELL719SRV";
            connection = DriverManager.getConnection(url, "DAS_CONV_TOOL", "DAS_CONV_TOOL");
            connection.setAutoCommit(false);
            testInsertToComments(connection);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public static void testCreateTable(Connection connection) throws SQLException {
        String query = "CREATE TABLE t_comment(id NUMBER, text VARCHAR(10))";
        boolean result = connection.createStatement().execute(query);
        System.out.println(result);
    }

    public static void testInsertToComments(Connection connection) throws SQLException {
        String query = "INSERT INTO t_comment(id, text) values (2, 'test test ok')";
        int count = connection.createStatement().executeUpdate(query);
        System.out.println(count);
    }
}
