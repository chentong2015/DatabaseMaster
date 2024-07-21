import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CommonsDbcp2ProjectDemo {

    public static void main(String[] args) throws InterruptedException {
        String query = "insert into test (id, value) values (20, 'test')";
        DataSource dataSource = CommonsDbcp2Handler.getDatasource(2);

        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {
            int row = statement.executeUpdate();
            System.out.println("Done Insert");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        Thread.sleep(60000);
        System.out.println("Done");
    }
}
