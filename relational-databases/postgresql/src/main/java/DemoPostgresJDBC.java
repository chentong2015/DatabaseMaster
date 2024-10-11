import java.sql.*;

public class DemoPostgresJDBC {

    public static void main(String[] args) throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/my_database";
        try (Connection connection = DriverManager.getConnection(url, "postgres", "")) {
            connection.getSchema(); // Default Schema: public
            System.out.println(connection.getAutoCommit());

            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO t_comment(id, review) values (1002, 'test')");

            Thread.sleep(10000);
            System.out.println("Connection Done");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
