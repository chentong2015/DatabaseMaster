import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DemoSqlServerJDBC {

    public static void main(String[] args) throws SQLException {
        String url = "jdbc:sqlserver://server:1433;databaseName=DAS_CONV_TOOL;trustServerCertificate=true";
        Connection connection = DriverManager.getConnection(url, "INSTAL", "INSTALL");
        String query = "insert into test_table (code, value1, value2) values (?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, 4);

        // TODO. 如果数据库中的Table Column Type定义为Numeric, 使用Java String类型插入数据则会报错
        // ERROR: column "sc_actif" is of type numeric but expression is of type character varying

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


