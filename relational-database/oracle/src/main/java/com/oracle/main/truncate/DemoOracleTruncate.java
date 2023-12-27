package com.oracle.main.truncate;

import java.sql.Connection;
import java.sql.SQLException;

//  TODO. 如果存储字符串长度超过Oracle定义列VARCHAR字符长度，则抛出异常
//  Error Msg = ORA-12899: value too large for column "DAS_CONV_TOOL"."T_COMMENT"."TEXT"
//  (actual: 22, maximum: 10)
public class DemoOracleTruncate {

    public static void testCreateTable(Connection connection) throws SQLException {
        String query = "CREATE TABLE t_comment(id NUMBER, text VARCHAR2(10))";
        boolean result = connection.createStatement().execute(query);
        System.out.println(result);
    }

    // TODO. "Truncate"截断字符串
    // 1. 存储的普通字符串超过VARCHAR设定的长度
    // 2. 存储的特殊字符串在UTF-8编码之后导致长度超过
    public static void testInsertToComments(Connection connection) throws SQLException {
        String query = "INSERT INTO t_comment(id, text) values (3, 'azértyuiop')";
        int count = connection.createStatement().executeUpdate(query);
        System.out.println(count);
    }
}
