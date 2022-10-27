package truncate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DemoSybaseVarchar {

    private static String url = "jdbc:sybase:Tds:LCTON01:5000/tempdb";

    public static void main(String[] args) throws SQLException {
        Connection connection = null;
        try {
            Class.forName("com.sybase.jdbc4.jdbc.SybDriver");
            connection = DriverManager.getConnection(url, "tech_user", "root123");
            testTruncateChars(connection);
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    private static void testTruncateChars(Connection connection) throws SQLException {
        String query = "insert into t_comment (code, des, test) values (10, 'aaaaaa', 'bbbb bbbb ccc')";
        int result = connection.createStatement().executeUpdate(query);
        System.out.println(result);
    }
}

