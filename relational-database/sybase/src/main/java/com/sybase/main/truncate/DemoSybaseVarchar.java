package com.sybase.main.truncate;

import java.sql.Connection;
import java.sql.SQLException;

public class DemoSybaseVarchar {

    private static void testTruncateChars(Connection connection) throws SQLException {
        String query = "insert into t_comment (code, des, test) values (10, 'aaaaaa', 'bbbb bbbb ccc')";
        int result = connection.createStatement().executeUpdate(query);
        System.out.println(result);
    }
}

