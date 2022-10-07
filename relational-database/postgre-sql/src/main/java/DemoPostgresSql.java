import java.sql.*;

public class DemoPostgresSql {

    // TODO. PostgreSQL JDBC Connection默认是开启AutoCommit的
    public static void main(String[] args) throws SQLException, InterruptedException {
        // String url = "jdbc:postgresql://localhost:5432/my_database?reWriteBatchedInserts=true";
        String url = "jdbc:postgresql://dell1451xxx:5432/tpk0002795_56979469";
        Connection connection = DriverManager.getConnection(url, "postgres", "postgres");
        System.out.println(connection.getAutoCommit());

        connection.setAutoCommit(false);
        String query = "select setting from pg_settings where name = 'edb_redwood_date'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        if (resultSet.next()) {
            String setting = resultSet.getString(1);
            if ("on".equals(setting)) {
                System.out.println("edb_redwood_date=true");
            }
        } else {
            System.out.println("Can not find");
        }
        statement.close();
        resultSet.close();
        // connection.commit();
        // connection.setAutoCommit(true);
        Thread.sleep(30000);
        connection.close();
    }
}
