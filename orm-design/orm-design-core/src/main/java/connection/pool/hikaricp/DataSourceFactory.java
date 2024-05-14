package connection.pool.hikaricp;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.HikariPool;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.text.MessageFormat;

// TODO. 使用第三方线程池技术HikariDataSource, 每次从线程池中获取DB连接
public class DataSourceFactory {

    private static final String SEND_STRING_PARAMS_AS_UNICODE = "sendStringParametersAsUnicode";
    private static final String UNKNOWN_URL_ERR_MSG = "Unknown JDBC url: {0}";

    private static final String JDBC_ORACLE = "jdbc:oracle:thin:@";
    private static final String ORACLE_DRIVER = "oracle.jdbc.driver.OracleDriver";

    private static final String JDBC_SQL_SERVER = "jdbc:sqlserver://";
    private static final String SQL_SERVER_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

    private DataSourceFactory() {
    }

    public static DataSource getDataSource(String name, String url, String usr, String psw, int threads) throws SQLException {
        HikariDataSource dataSource = new HikariDataSource();
        if (url.startsWith(JDBC_ORACLE)) {
            dataSource.setDriverClassName(ORACLE_DRIVER);
        } else if (url.startsWith(JDBC_SQL_SERVER)) {
            dataSource.setDriverClassName(SQL_SERVER_DRIVER);
            dataSource.addDataSourceProperty(SEND_STRING_PARAMS_AS_UNICODE, Boolean.FALSE);
        } else {
            throw new SQLException(MessageFormat.format(UNKNOWN_URL_ERR_MSG, url));
        }

        dataSource.setPoolName(name);
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(usr);
        dataSource.setPassword(psw);

        // 线程池中的连接数量和并发的线程数量有关
        dataSource.setMaximumPoolSize(threads < 5 ? 10 : threads + threads / 3);

        // 从线程池中获取的连接必须是有效的
        try {
            // Returns true if the connection has not been closed and is still valid.
            dataSource.getConnection().isValid(0);
        } catch (HikariPool.PoolInitializationException ex) {
            throw new SQLException(ex.getMessage());
        }
        return dataSource;
    }
}
