package h2_basic;

import java.sql.Connection;
import java.sql.DriverManager;

// http://www.h2database.com/html/cheatSheet.html
// http://www.h2database.com/html/quickstart.html
// https://www.tutorialspoint.com/h2_database/h2_database_quick_guide.htm 完整的测试教程

// H2 is a JAVA database
// H2 is an opensource and lightweight database that can be used in an in-memory manner.
// Embedded database it is not used for production development,
//   but mostly used for development and testing.
public class BaseH2Database {

    public static void main(String[] a) throws Exception {
        // Class.forName("org.h2.Driver");

        Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa");
        // add application code here
        String sql = "Insert into t_test (id, name) values (5, 'item05');";
        conn.createStatement().execute(sql);
        conn.close();
    }
}
