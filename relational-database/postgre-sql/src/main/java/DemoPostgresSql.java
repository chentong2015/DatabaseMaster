import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// TODO. PostgreSQL JDBC Connection默认是开启AutoCommit的
public class DemoPostgresSql {

    private static String url = "jdbc:postgresql://localhost:5432/my_database?reWriteBatchedInserts=true";
    // private static String url = "jdbc:postgresql://dell1451xxx:5432/tpk0002795_56979469";

    public static void main(String[] args) throws SQLException, InterruptedException {
        Connection connection = DriverManager.getConnection(url, "postgres", "postgres");
        System.out.println(connection.getAutoCommit());

        connection.close();
    }
}
