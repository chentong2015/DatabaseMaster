package das_middleware.connection.pool.demo2;

import java.sql.Connection;

public interface ConnectionCreator {

    String getUrl();

    Connection createConnection();
}
