import java.math.BigDecimal;
import java.sql.*;

public class DemoSqlServerJDBC {

    public static void main(String[] args) throws SQLException {
        String url = "jdbc:sqlserver://server:1433;instanceName=MSSQLSERVER;databaseName=QA_FMM412_WJI";
        Connection connection = DriverManager.getConnection(url, "Q_WJI", "Hello00");
        String query = "create table test(id_num int, username nvarchar(100));";

        Statement statement = connection.createStatement();
        statement.executeUpdate(query);

        connection.close();
        System.out.println("Finish");
    }

    // TODO. PreparedStatement替换的参数实际值需要满足类型匹配
    public static void testMain() throws SQLException {
        String url = "jdbc:sqlserver://server:1433;databaseName=DAS_CONV_TOOL;trustServerCertificate=true";
        Connection connection = DriverManager.getConnection(url, "INSTAL", "INSTALL");
        String query = "insert into test_table (code, value1, value2) values (?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, 4);
        // preparedStatement.setString(2, "20");
        // preparedStatement.setString(3, "7");
        preparedStatement.setBigDecimal(2, BigDecimal.valueOf(21));
        preparedStatement.setBigDecimal(3, BigDecimal.valueOf(8));

        int rows = preparedStatement.executeUpdate();
        System.out.println(rows);
        preparedStatement.close();
        connection.close();
    }
}


