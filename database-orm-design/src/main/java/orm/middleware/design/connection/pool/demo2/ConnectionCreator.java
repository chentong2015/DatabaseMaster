package orm.middleware.design.connection.pool.demo2;

import java.sql.Connection;

public interface ConnectionCreator {

    String getUrl();

    Connection createConnection();
}
