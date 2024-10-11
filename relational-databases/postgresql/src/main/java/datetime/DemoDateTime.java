package datetime;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DemoDateTime {

    // 2024-01-10 18:46:46.161081
    // 2024-01-11 18:46:46.161081
    // 2024-01-14 18:46:46.161081
    public static void main(String[] args) throws Exception {
        List<Timestamp> timestamps = getAvailableSlots(LocalDate.now());
        for (Timestamp timestamp: timestamps) {
            System.out.println(timestamp);
        }
    }

    // TODO. 提供的输入为Date日期，只需要指明那一天即可，返回的是slots时间戳
    // Input: java.util.Date (有两种类型) or java.util.LocalDate
    // Output: List of java.sql.Timestamp
    public static List<Timestamp> getAvailableSlots(LocalDate localDate) throws Exception {
        List<Timestamp> slotsList = new ArrayList<>();
        String url = "jdbc:postgresql://localhost:5432/my_database";
        String query = "select * from t_calendar where kind = 'opening' and timestamp >= ? and timestamp <= ?";
        try (Connection connection = DriverManager.getConnection(url, "postgres", "");
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setDate(1, Date.valueOf(localDate));
            statement.setDate(2, Date.valueOf(localDate.plusDays(8)));

            // 根据传递的类型直接设置时间戳
            // statement.setTimestamp(3, Timestamp.valueOf(localDate.atStartOfDay()));

            ResultSet results = statement.executeQuery();
            while (results.next()) {
                // 获取纯粹的Date日期时间, 对应Date JDBC Type类型
                // results.getDate("timestamp");

                // 获取数据表中Timestamp时间戳JDBC Type类型
                slotsList.add(results.getTimestamp("timestamp"));
            }
        } catch (SQLException exception) {
            throw new Exception("Can not get available slots: " + exception.getMessage());
        }
        return slotsList;
    }
}
