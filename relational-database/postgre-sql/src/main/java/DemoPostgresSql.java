import java.sql.*;

public class DemoPostgresSql {

    public static void main(String[] args) throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/my_database?reWriteBatchedInserts=true";
        Connection connection = DriverManager.getConnection(url, "postgres", "admin");
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
        connection.commit();
        connection.setAutoCommit(true);
    }
}
