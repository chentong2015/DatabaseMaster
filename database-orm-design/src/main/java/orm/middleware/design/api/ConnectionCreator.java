package orm.middleware.design.api;

import java.sql.Connection;

public interface ConnectionCreator {

    String getUrl();

    Connection createConnection();
}
